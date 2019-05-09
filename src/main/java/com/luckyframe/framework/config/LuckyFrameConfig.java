package com.luckyframe.framework.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目中相关的配置
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年2月12日
 */
@Component
@ConfigurationProperties(prefix = "luckyframe")
public class LuckyFrameConfig
{
    /** 项目名称 */
    private String name;
    /** 版本 */
    private String version;
    /** 版权年份 */
    private String copyrightYear;
    /** 上传路径 */
    private static String profile;
    /** 获取地址开关 */
    private static boolean addressEnabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
    	setProfile();
        return profile;
    }

    public static void setProfile()
    {
		// 文件目录
		String pathName = System.getProperty("user.dir")+File.separator+"profile";
		File file = new File(pathName);
		if (file.exists()) {
			if (!file.isDirectory()) {
				file.mkdir();
			}
		} else {
				file.mkdir();
		}
    	LuckyFrameConfig.profile = file.getPath()+File.separator;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
    	LuckyFrameConfig.addressEnabled = addressEnabled;
    }

    public static String getAvatarPath()
    {
        return profile + "avatar"+File.separator;
    }

    public static String getDownloadPath()
    {
        return profile + "download"+File.separator;
    }
    
    public static String getUploadPath()
    {
        return profile + "upload"+File.separator;
    }
}
