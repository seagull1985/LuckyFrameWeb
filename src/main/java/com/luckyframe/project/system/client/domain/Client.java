package com.luckyframe.project.system.client.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 客户端管理表 sys_client
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
public class Client extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 客户端ID */
	private Integer clientId;
	/** 心跳任务ID */
	private Integer jobId;
	/** 客户端名称 */
	private String clientName;
	/** 客户端IP */
	private String clientIp;
	/** 客户端状态 0 正常 1 链接失败 2 状态未知 */
	private Integer status;
	/** 检查客户端状态心跳间隔时间 单位:秒 */
	private Integer checkinterval;
	/** 客户端测试驱动桩路径 多个;做分隔 */
	private String clientPath;
	/** 备注 */
	private String remark;
    /** 所属项目组 */
    private Integer[] projectIds;
	/** 客户端状态 0 正常 1 链接失败 2 状态未知 */
	private String statusStr;
	/** 客户端状态 0 正常 1 链接失败 2 状态未知 */
	private List<String> clientPathList;

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public List<String> getClientPathList() {
		return clientPathList;
	}

	public void setClientPathList(List<String> clientPathList) {
		this.clientPathList = clientPathList;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public void setClientId(Integer clientId) 
	{
		this.clientId = clientId;
	}

	public Integer getClientId() 
	{
		return clientId;
	}
	public void setClientName(String clientName) 
	{
		this.clientName = clientName;
	}

	public String getClientName() 
	{
		return clientName;
	}
	public void setClientIp(String clientIp) 
	{
		this.clientIp = clientIp;
	}

	public String getClientIp() 
	{
		return clientIp;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setCheckinterval(Integer checkinterval) 
	{
		this.checkinterval = checkinterval;
	}

	public Integer getCheckinterval() 
	{
		return checkinterval;
	}
	public void setClientPath(String clientPath) 
	{
		this.clientPath = clientPath;
	}

	public String getClientPath() 
	{
		return clientPath;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public Integer[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(Integer[] projectIds) {
		this.projectIds = projectIds;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("clientId", getClientId())
            .append("clientName", getClientName())
            .append("clientIp", getClientIp())
            .append("status", getStatus())
            .append("checkinterval", getCheckinterval())
            .append("clientPath", getClientPath())
            .append("remark", getRemark())
            .toString();
    }
}
