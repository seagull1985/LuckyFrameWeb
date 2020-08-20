package com.luckyframe.common.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luckyframe.framework.config.LuckyFrameConfig;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.service.IJobService;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.service.IClientService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

@Component("ServerHandler")
@ChannelHandler.Sharable
public class ServerHandler extends ChannelHandlerAdapter {

    @Autowired
    private LuckyFrameConfig lfConfig;

    @Autowired
    private IClientService clientService;

    @Autowired
    private NettyChannelMap nettyChannelMap;

    @Autowired
    private IJobService jobService;

    private static String file_dir = System.getProperty("user.dir") + "/tmp";

    protected ChannelHandlerContext ctx;

    private CountDownLatch latch;

    /**
     * 消息的唯一ID
     */
    private String unidId = "";
    /**
     * 同步标志
     */
    protected int rec;
    /**
     * 客户端返回的结果
     */
    private Result clientResult;
    /**
     * 心跳丢失次数
     */
    private int counter = 0;
    /**
     * 客户端Json图片Key
     */
    private static final String IMG_NAME = "imgName";
    /**
     * 客户端Json请求方法
     */
    private static final String CLIENT_METHOD = "method";
    /**
     * 客户端Json请求名称
     */
    private static final String CLIENT_HOSTNAME = "hostName";
    /**
     * 客户端Json请求消息内容
     */
    private static final String CLIENT_MESSAGE = "message";
    /**
     * 客户端Json注册状态
     */
    private static final String CLIENT_SUCCESS = "success";
    /**
     * 客户端Json注册版本
     */
    private static final String CLIENT_VERSION = "version";
    /**
     * 客户端Json请求返回内容
     */
    private static final String CLIENT_RETURN = "return";


    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //统一转编码
        JSONObject json = JSON.parseObject(msg.toString());
        /*
         * ClientUp客户端启动，自动注册到服务端中
         * */
        if ("clientUp".equals(json.get(CLIENT_METHOD))) {
            String hostName = json.get(CLIENT_HOSTNAME).toString();
            String clientName = json.get("clientName").toString();
            if (nettyChannelMap.get(hostName) != null) {
                JSONObject tmp = new JSONObject();
                tmp.put(CLIENT_METHOD, CLIENT_RETURN);
                tmp.put(CLIENT_MESSAGE, "客户端名称重复，自动注册失败");
                tmp.put(CLIENT_SUCCESS, "-1");
                sendMessage(ctx, tmp.toString());
                log.error("客户端host.name重复，注册失败:" + hostName);
                //登录失败，断开连接
                ctx.close();
                return;
            }
            ChannelMap.setChannel(hostName, ctx.channel());
            ChannelMap.setChannelLock(hostName, new ReentrantLock());

            //接收到客户端上线消息
            log.info("#############客户端上线##############");
            log.info("上线客户端名称：" + json.get("clientName") + "，主机名：" + json.get(CLIENT_HOSTNAME) + ", IP地址：" + json.get("ip"));
            //检查客户端是否已经注册入库
            Client client = clientService.selectClientByClientIP(hostName);
            if (null == client) {
                client = clientService.selectClientByClientIP(json.get("ip").toString());
                int result;
                //通过IP找到客户端
                if (null!=client&&Objects.equals(client.getClientType(), 0)) {
                    //删除原调度表中的数据
                    Job job = jobService.selectJobById(client.getJobId().longValue());
                    if(null!=job){
                        result = jobService.deleteJob(job);
                        if (result < 1) {
                            //登录失败，断开连接
                            ctx.close();
                            throw new Exception("客户端时无法删除任务调度表");
                        }
                    }

                    client.setClientIp(hostName);
                    client.setClientName(clientName);
                    client.setStatus(2);
                    client.setRemark("客户端兼容更新NETTY通信注册成功");
                    clientService.updateClientForNetty(client);
                }else{
                    client = new Client();
                    client.setClientIp(hostName);
                    client.setClientName(clientName);
                    client.setCheckinterval(30);
                    client.setClientPath("/TestDriven");
                    client.setStatus(2);
                    client.setRemark("客户端NETTY通信方式成功注册");

                    /*在调度预约表中插入数据*/
                    clientService.insertClientForNetty(client);
                    log.info("主机名为：" + json.get(CLIENT_HOSTNAME) + "自动注册成功");
                }
            } else {
                //兼容HTTP方式增加的客户端
                int result;
                if (Objects.equals(client.getClientType(), 0)) {
                    //删除原调度表中的数据
                    Job job = jobService.selectJobById(client.getJobId().longValue());
                    if(null!=job){
                        result = jobService.deleteJob(job);
                        if (result < 1) {
                            //登录失败，断开连接
                            ctx.close();
                            throw new Exception("客户端时无法删除任务调度表");
                        }
                    }

                    client.setClientIp(hostName);
                    client.setClientName(clientName);
                    client.setStatus(2);
                    client.setRemark("客户端兼容NETTY通信注册成功");
                    clientService.updateClientForNetty(client);
                }else {
                    client.setClientName(clientName);
                    client.setClientIp(hostName);
                    client.setRemark("客户端NETTY通信链接成功");
                }
            }
            if (lfConfig.getVersion().equals(json.get(CLIENT_VERSION))) {
                //版本号一致
                client.setStatus(0);
                if (client.getClientId() != null) {
                    clientService.updateClientForNetty(client);
                } else {
                    clientService.insertClientForNetty(client);
                }

                //登录成功,把channel存到服务端的map中
                nettyChannelMap.add(hostName, (SocketChannel) ctx.channel());
                //登陆成功，放入map中用于心跳
                NettyServer.clientMap.put(hostName, "0");
                //返回接受成功消息
                JSONObject tmp = new JSONObject();
                tmp.put(CLIENT_METHOD, "loginReturn");
                tmp.put(CLIENT_MESSAGE, "自动注册成功");
                tmp.put("clientId", client.getClientId());
                tmp.put(CLIENT_SUCCESS, "1");
                sendMessage(ctx, tmp.toString());
            } else {
                client.setRemark("客户端(" + json.get(CLIENT_VERSION) + ")与服务器(" + lfConfig.getVersion() + ")版本不一致");
                client.setStatus(1);
                if (client.getClientId() != null) {
                    clientService.updateClientForNetty(client);
                } else {
                    clientService.insertClientForNetty(client);
                }

                //登陆失败，删除心跳map中的数据
                NettyServer.clientMap.remove(hostName);
                //返回接受成功消息
                JSONObject tmp = new JSONObject();
                tmp.put(CLIENT_METHOD, CLIENT_RETURN);
                tmp.put(CLIENT_MESSAGE, "自动注册失败，" + "客户端(" +
                        json.get(CLIENT_VERSION) + ")与服务器(" + lfConfig.getVersion() + ")版本不一致");
                tmp.put(CLIENT_SUCCESS, "0");
                sendMessage(ctx, tmp.toString());
                //登录失败，断开连接
                ctx.close();
            }
        } else if (CLIENT_RETURN.equals(json.get(CLIENT_METHOD))) {
            /*
             * 向客户端请求后返回的数据
             * */
            Result re = JSONObject.parseObject(json.get("data").toString(), Result.class);
            //校验返回的信息是否是同一个信息
            if (unidId.equals(re.getUniId())) {
                latch.countDown();//消息返回完毕，释放同步锁，具体业务需要判断指令是否匹配
                rec = 0;
                clientResult = re;
            }
        } else if ("ping".equals(json.get(CLIENT_METHOD))) {
            /*
             * 客户端心跳检测
             * */
            String hostName = json.get(CLIENT_HOSTNAME).toString();

            if (NettyServer.clientMap.get(hostName) == null || (!"0".equals(NettyServer.clientMap.get(hostName)))) {
                //检查客户端是否已经注册入库
                Client client = clientService.selectClientByClientIP(hostName);
                //版本号一致
                client.setClientIp(hostName);
                client.setRemark("检测客户端状态成功");
                client.setStatus(0);
                clientService.updateClientForNetty(client);
                //更新客户端状态成功
                NettyServer.clientMap.put(hostName, "0");
            }
        } else if ("upload".equals(json.get(CLIENT_METHOD))) {
            Result re = JSONObject.parseObject(json.get("data").toString(), Result.class);
            int start = Integer.parseInt(json.get("start").toString());
            FileUploadFile ef = re.getFileUploadFile();
            byte[] bytes = ef.getBytes();
            int byteRead = ef.getEndPos();
            //String md5 = ef.getFile_md5();//文件名
            String path = file_dir + File.separator + json.get(IMG_NAME);
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");//r: 只读模式 rw:读写模式
            randomAccessFile.seek(start);//移动文件记录指针的位置,
            randomAccessFile.write(bytes);//调用了seek（start）方法，是指把文件的记录指针定位到start字节的位置。也就是说程序将从start字节开始写数据
            start = start + byteRead;
            JSONObject tmp = new JSONObject();
            tmp.put(CLIENT_METHOD, "upload");
            tmp.put(CLIENT_SUCCESS, "1");
            tmp.put("uuid", json.get("uuid").toString());
            tmp.put("start", start);
            Map<String, Object> jsonparams = new HashMap<>();
            jsonparams.put(IMG_NAME, json.get(IMG_NAME));
            tmp.put("data", jsonparams);
            sendMessage(ctx, tmp.toString());
            randomAccessFile.close();
            log.info("处理完毕,文件路径:" + path + "," + byteRead);
        } else {
            log.info("客户端请求方法没有定义，请检查..." +CLIENT_METHOD+": " + json.get(CLIENT_METHOD));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        //channel失效，从Map中移除
        nettyChannelMap.remove((SocketChannel) ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                // 空闲40s之后触发 (心跳包丢失)
                if (counter >= 3) {
                    // 连续丢失3个心跳包 (断开连接)
                    ctx.channel().close().sync();
                    log.error("已与" + ctx.channel().remoteAddress() + "断开连接");
                } else {
                    counter++;
                    log.debug(ctx.channel().remoteAddress() + "丢失了第 " + counter + " 个心跳包");
                }
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public void resetSync(CountDownLatch latch, int rec) {
        this.latch = latch;
        this.rec = rec;
    }

    public void setUnidId(String s) {
        this.unidId = s;
    }

    public Result getResult() {
        return clientResult;
    }

    private static void sendMessage(ChannelHandlerContext ctx, String json) {
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer((json + "$_").getBytes()));
    }

}
