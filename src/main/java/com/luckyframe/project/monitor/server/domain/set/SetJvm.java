package com.luckyframe.project.monitor.server.domain.set;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

import com.luckyframe.common.utils.DateUtils;

/**
 * JVM相关信息
 * 
 * @author ruoyi
 */
public class SetJvm
{
    /**
     * 当前JVM占用的内存总数(M)
     */
    private BigDecimal total;

    /**
     * JVM最大可用内存总数(M)
     */
    private BigDecimal max;

    /**
     * JVM空闲内存(M)
     */
    private BigDecimal free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;
    
    private BigDecimal used;
    
    private BigDecimal usage;

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public BigDecimal getMax()
    {
        return max;
    }

    public void setMax(BigDecimal max)
    {
        this.max = max;
    }

    public BigDecimal getFree()
    {
        return free;
    }

    public void setFree(BigDecimal free)
    {
        this.free = free;
    }

    public BigDecimal getUsed() {
		return used;
	}

	public void setUsed(BigDecimal used) {
		this.used = used;
	}

	public BigDecimal getUsage() {
		return usage;
	}

	public void setUsage(BigDecimal usage) {
		this.usage = usage;
	}

	/**
     * 获取JDK名称
     */
    public String getName()
    {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getHome()
    {
        return home;
    }

    public void setHome(String home)
    {
        this.home = home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime()
    {
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
    }

    /**
     * JDK运行时间
     */
    public String getRunTime()
    {
        return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
    }
}
