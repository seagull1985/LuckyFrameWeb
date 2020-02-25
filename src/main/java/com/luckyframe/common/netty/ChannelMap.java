package com.luckyframe.common.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class ChannelMap {
    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static Map<String, Lock> channelLockMap = new ConcurrentHashMap<>();

    public static void setChannel(String key,Channel channel){
        channelMap.put(key,channel);
    }

    public static Channel getChannel(String key){
        return channelMap.get(key);
    }

    public static void setChannelLock(String key,Lock lock){
        channelLockMap.put(key,lock);
    }

    public static Lock getChannelLock(String key){
        return channelLockMap.get(key);
    }
}
