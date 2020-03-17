package com.luckyframe.common.netty;

import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.mapper.ClientMapper;
import com.luckyframe.project.system.client.service.IClientService;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NettyChannelMap {
    //logger
    private static final Logger log = LoggerFactory.getLogger(NettyChannelMap.class);

    private static Map<String, SocketChannel> map= new ConcurrentHashMap<>();

    @Autowired
    private IClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    public  void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }

    public  Channel get(String clientId){
        return map.get(clientId);
    }

    public  void remove(SocketChannel socketChannel){
        for (@SuppressWarnings("rawtypes") Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                log.info("#############客户端下线##############");
                log.info("下线主机名为："+entry.getKey());
                Client client=clientService.selectClientByClientIP(entry.getKey().toString());
                if(client!=null)
                {
                    client.setStatus(1);
                    clientMapper.updateClient(client);
                    //登陆失败，删除心跳map中的数据
                    NettyServer.clientMap.remove(client.getClientIp());
                }
                map.remove(entry.getKey());
                socketChannel.close();
            }
        }
    }

}