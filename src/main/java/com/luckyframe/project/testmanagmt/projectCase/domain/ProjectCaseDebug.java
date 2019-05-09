package com.luckyframe.project.testmanagmt.projectCase.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 用例调试日志记录表 project_case_debug
 * 
 * @author luckyframe
 * @date 2019-03-14
 */
public class ProjectCaseDebug extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 调试ID */
	private Integer debugId;
	/** 用例ID */
	private Integer caseId;
	/** 用户ID */
	private Integer userId;
	/** 调试结束标识 0 进行中 1结束 2异常 */
	private Integer debugIsend;
	/** 日志级别 info 记录 warning 警告 error 异常 */
	private String logLevel;
	/** 日志 */
	private String logDetail;
	/** 客户端ID */
	private Integer clientId;
	/** 客户端驱动路径 */
	private String driverPath;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getDriverPath() {
		return driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	public void setDebugId(Integer debugId) 
	{
		this.debugId = debugId;
	}

	public Integer getDebugId() 
	{
		return debugId;
	}
	public void setCaseId(Integer caseId) 
	{
		this.caseId = caseId;
	}

	public Integer getCaseId() 
	{
		return caseId;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
	{
		return userId;
	}
	public void setDebugIsend(Integer debugIsend) 
	{
		this.debugIsend = debugIsend;
	}

	public Integer getDebugIsend() 
	{
		return debugIsend;
	}
	public void setLogLevel(String logLevel) 
	{
		this.logLevel = logLevel;
	}

	public String getLogLevel() 
	{
		return logLevel;
	}
	public void setLogDetail(String logDetail) 
	{
		this.logDetail = logDetail;
	}

	public String getLogDetail() 
	{
		return logDetail;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("debugId", getDebugId())
            .append("caseId", getCaseId())
            .append("userId", getUserId())
            .append("debugIsend", getDebugIsend())
            .append("logLevel", getLogLevel())
            .append("logDetail", getLogDetail())
            .toString();
    }
}
