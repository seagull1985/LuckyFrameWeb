package com.luckyframe.project.monitor.server.domain.set;

import java.util.LinkedList;
import java.util.List;

/**
 * 服务器相关信息
 * 
 * @author ruoyi
 */
public class SetServer
{
    /**
     * CPU相关信息
     */
    private SetCpu cpu = new SetCpu();

    /**
     * 內存相关信息
     */
    private SetMem mem = new SetMem();

    /**
     * JVM相关信息
     */
    private SetJvm jvm = new SetJvm();

    /**
     * 服务器相关信息
     */
    private SetSys sys = new SetSys();

    /**
     * 磁盘相关信息
     */
    private List<SetSysFile> sysFiles = new LinkedList<SetSysFile>();

    public SetCpu getCpu()
    {
        return cpu;
    }

    public void setCpu(SetCpu cpu)
    {
        this.cpu = cpu;
    }

    public SetMem getMem()
    {
        return mem;
    }

    public void setMem(SetMem mem)
    {
        this.mem = mem;
    }

    public SetJvm getJvm()
    {
        return jvm;
    }

    public void setJvm(SetJvm jvm)
    {
        this.jvm = jvm;
    }

    public SetSys getSys()
    {
        return sys;
    }

    public void setSys(SetSys sys)
    {
        this.sys = sys;
    }

    public List<SetSysFile> getSysFiles()
    {
        return sysFiles;
    }

    public void setSysFiles(List<SetSysFile> sysFiles)
    {
        this.sysFiles = sysFiles;
    }

}
