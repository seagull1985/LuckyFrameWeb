package com.luckyframe.project.monitor.server.domain.set;

import java.math.BigDecimal;

/**
 * 內存相关信息
 * 
 * @author ruoyi
 */
public class SetMem
{
    /**
     * 内存总量
     */
    private BigDecimal total;

    /**
     * 已用内存
     */
    private BigDecimal used;

    /**
     * 剩余内存
     */
    private BigDecimal free;

    private BigDecimal usage;
    
    public BigDecimal getTotal()
    {
        return 	total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public BigDecimal getUsed()
    {
        return used;
    }

    public void setUsed(BigDecimal used)
    {
        this.used = used;
    }

    public BigDecimal getFree()
    {
        return free;
    }

    public void setFree(BigDecimal free)
    {
        this.free = free;
    }

	public BigDecimal getUsage() {
		return usage;
	}

	public void setUsage(BigDecimal usage) {
		this.usage = usage;
	}

}
