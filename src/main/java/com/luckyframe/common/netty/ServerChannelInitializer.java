package com.luckyframe.common.netty;
import com.luckyframe.LuckyFrameWebApplication;
import com.luckyframe.framework.config.LuckyFrameConfig;
import com.luckyframe.framework.config.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private ServerHandler serverHandler;

    @Autowired
    private LuckyFrameConfig lfConfig;

    @Autowired
    private NettyConfig nettyConfig;

    private static final Logger log = LoggerFactory.getLogger(ServerChannelInitializer.class);

    @Override
    protected void initChannel(SocketChannel channel) {
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
        log.info("Netty通信指定编码格式："+nettyConfig.getEncoder()+"  解码格式："+nettyConfig.getDecoder());
        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));
        channel.pipeline().addLast("decoder",nettyConfig.getDecoder());
        channel.pipeline().addLast("encoder",nettyConfig.getEncoder());
        channel.pipeline().addLast(new IdleStateHandler(30,0,0,TimeUnit.SECONDS));
        channel.pipeline().addLast("handler", serverHandler);

    }
}