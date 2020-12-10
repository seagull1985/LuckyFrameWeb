package com.luckyframe.framework.config;

import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 读取项目中相关的配置
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2020年12月10日
 */
@Component
@Configuration
public class NettyConfig
{
    /** netty服务IP */
    @Value("${netty.url}")
    private String url;
    /** netty服务端口 */
    @Value("${netty.port}")
    private Integer port;
    /** netty通信编码格式 */
    @Value("${netty.socketChannel.encoder}")
    private String encoder;
    /** netty通信解码格式 */
    @Value("${netty.socketChannel.decoder}")
    private String decoder;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public StringEncoder getEncoder() {
        return new StringEncoder(charConvert(encoder));
    }

    public StringDecoder getDecoder() {
        return new StringDecoder(charConvert(decoder));
    }

    private Charset charConvert(String strChar){
        if("utf-8".equals(strChar.toLowerCase())){
            return StandardCharsets.UTF_8;
        }else if("iso_8859_1".equals(strChar.toLowerCase())){
            return StandardCharsets.ISO_8859_1;
        }else if("us_ascii".equals(strChar.toLowerCase())){
            return StandardCharsets.US_ASCII;
        }else if("utf_16".equals(strChar.toLowerCase())){
            return StandardCharsets.UTF_16;
        }else if("utf_16be".equals(strChar.toLowerCase())){
            return StandardCharsets.UTF_16BE;
        }else if("utf_16le".equals(strChar.toLowerCase())){
            return StandardCharsets.UTF_16LE;
        }else if("gbk".equals(strChar.toLowerCase())){
            return Charset.forName("GBK");
        }else{
            return StandardCharsets.UTF_8;
        }
    }
}
