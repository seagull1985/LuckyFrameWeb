package com.luckyframe.project.monitor.server.domain.set;

import java.math.BigDecimal;

/**
 * CPU相关信息
 * 
 * @author ruoyi
 */
public class SetCpu
{
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private BigDecimal total;

    /**
     * CPU系统使用率
     */
    private BigDecimal sys;

    /**
     * CPU用户使用率
     */
    private BigDecimal used;

    /**
     * CPU当前等待率
     */
    private BigDecimal wait;

    /**
     * CPU当前空闲率
     */
    private BigDecimal free;

    public int getCpuNum()
    {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum)
    {
        this.cpuNum = cpuNum;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public BigDecimal getSys()
    {
        return sys;
    }

    public void setSys(BigDecimal sys)
    {
        this.sys = sys;
    }

    public BigDecimal getUsed()
    {
        return used;
    }

    public void setUsed(BigDecimal used)
    {
        this.used = used;
    }

    public BigDecimal getWait()
    {
        return wait;
    }

    public void setWait(BigDecimal wait)
    {
        this.wait = wait;
    }

    public BigDecimal getFree()
    {
        return free;
    }

    public void setFree(BigDecimal free)
    {
        this.free = free;
    }
}
