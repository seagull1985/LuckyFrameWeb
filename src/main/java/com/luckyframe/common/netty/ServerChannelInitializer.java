package com.luckyframe.common.netty;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel channel) {
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));
        channel.pipeline().addLast("decoder",new StringDecoder(Charset.forName("GBK")));
        channel.pipeline().addLast("encoder",new StringEncoder(StandardCharsets.UTF_8));
        channel.pipeline().addLast(new IdleStateHandler(30,0,0,TimeUnit.SECONDS));
        channel.pipeline().addLast("handler", serverHandler);

    }
}