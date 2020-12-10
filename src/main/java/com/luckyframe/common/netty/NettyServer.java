package com.luckyframe.common.netty;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.mapper.ClientMapper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class NettyServer {

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;
	
	@Autowired
	private ClientMapper clientMapper;

    //logger
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    public static final HashMap<String,String> clientMap=new HashMap<>();

    public void start(InetSocketAddress address){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//重置客户端状态
        	List<Client> clientList = clientMapper.selectClientList(new Client());
        	for(Client client:clientList){
        		client.setStatus(1);
        		client.setRemark("服务端启动重新初始化");
        		clientMapper.updateClient(client);
        	}
        	
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(serverChannelInitializer);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(address).sync();
            log.info("服务端启动成功，监听端口 " + address.getPort());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static Result write(Object obj, String tenantId ,String uniId) {
        // 获取锁
        Lock lock = ChannelMap.getChannelLock(tenantId);
        try {
            Channel channel = ChannelMap.getChannel(tenantId);
            if(channel != null){
                lock.lock();
                if(channel.isOpen()){
                    // 设置同步
                    CountDownLatch latch = new CountDownLatch(1);
                    ServerHandler nettyServerHandler = (ServerHandler) channel.pipeline().get("handler");
                    nettyServerHandler.resetSync(latch,1);
                    nettyServerHandler.setUnidId(uniId);
                    //channel.writeAndFlush(obj).sync();
                    channel.writeAndFlush(Unpooled.copiedBuffer((obj + "$_").getBytes()));
                    //同步返回结果
                    if (latch.await(60, TimeUnit.SECONDS)){
                        // printerServerHandler.setTimeout(0);
                        return nettyServerHandler.getResult();
                    }
                    //如果超时，将超时标志设置为1
                    //printerServerHandler.setTimeout(1);
                    log.error("netty请求超时60s");
                    return new Result(2,"请求超时",null,null);
                }else{
                    return new Result(0,"客户端已关闭!",null,null);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result(0,"服务出错!",null,null);

        }finally {
            if (lock != null){
                lock.unlock();
            }
        }
        return new Result(0,"客户端没有连接!",null,null);
    }

}
