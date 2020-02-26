package com.luckyframe;

import java.net.InetSocketAddress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.luckyframe.common.netty.NettyServer;

/**
 * 启动LuckyFrameWeb程序
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年2月12日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,FlywayAutoConfiguration.class })
@MapperScan("com.luckyframe.project.*.*.mapper")
public class LuckyFrameWebApplication  implements CommandLineRunner
{

    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    private NettyServer server;

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(url,port);
        System.out.println("服务端启动成功："+url+":"+port);
        server.start(address);
    }
    
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LuckyFrameWebApplication.class, args);
        System.out.println("LuckyFrameWeb启动成功......");
    }

}