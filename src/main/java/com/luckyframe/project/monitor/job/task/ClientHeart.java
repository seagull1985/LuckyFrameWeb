package com.luckyframe.project.monitor.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.framework.config.LuckyFrameConfig;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端心跳监听 =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 *
 * @author Seagull
 * @date 2019年3月29日
 */
@Component("clientHeart")
public class ClientHeart {
    @Autowired
    private IClientService clientService;

    @Autowired
    private LuckyFrameConfig lfConfig;

    public static ClientHeart clientHeart;

    @PostConstruct
    public void init() {
        clientHeart = this;
    }

    /* 避免频繁查询数据库，记录客户端状态 0 正常 1 链接失败 2 状态未知 */
    private static Map<String, Integer> clientListen = new HashMap<>(0);

    public static void iniClientListen(String clientIp) {
        clientListen.put(clientIp, 2);
    }

    public static void removeClientListen(String clientIp) {
        clientListen.remove(clientIp);
    }

    public static Integer getClientStatus(String clientIp) {
        return clientListen.get(clientIp);
    }

    public static void setClientStatus(String clientIp, Integer status) {
        clientListen.put(clientIp, status);
    }

    public void heartTask(String params) {
        try {
            String result = HttpRequest.httpClientGet(
                    "http://" + params + ":" + ClientConstants.CLIENT_MONITOR_PORT + "/getClientStatus",new Client(),
					new HashMap<>(0), 3000);
            JSONObject jsonObject = JSON.parseObject(result);

            if (null == ClientHeart.getClientStatus(params)) {
                ClientHeart.setClientStatus(params, 1);
            }

            if ("success".equals(jsonObject.get("status"))) {
                if (ClientHeart.getClientStatus(params) != 0) {
                    Client client = new Client();
                    if (lfConfig.getVersion().equals(jsonObject.get("version"))) {
                        client.setClientIp(params);
                        client.setRemark("检测客户端状态成功");
                        client.setStatus(0);
                        clientService.updateClientStatusByIp(client);
                    } else {
                        client.setClientIp(params);
                        client.setRemark(
                                "客户端(" + jsonObject.get("version") + ")与服务器(" + lfConfig.getVersion() + ")版本不一致");
                        client.setStatus(1);
                        clientService.updateClientStatusByIp(client);
                    }
                }
            } else {
                if (ClientHeart.getClientStatus(params) != 1) {
                    Client client = new Client();
                    client.setClientIp(params);
                    client.setRemark("客户端返回状态异常");
                    client.setStatus(1);
                    clientService.updateClientStatusByIp(client);
                }
            }
        } catch (RuntimeException e) {
            if (null == ClientHeart.getClientStatus(params) || ClientHeart.getClientStatus(params) != 1) {
                Client client = new Client();
                client.setClientIp(params);
                client.setRemark("检测客户端远程异常");
                client.setStatus(1);
                clientService.updateClientStatusByIp(client);
            }
        }
    }

}
