package com.luckyframe.project.testexecution.taskCaseLog.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 用例日志明细表 task_case_log
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
public class TaskCaseLog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 用例执行ID */
	private Integer logId;
	/** 用例ID */
	private Integer caseId;
	/** 用例执行ID */
	private Integer taskCaseId;
	/** 任务ID */
	private Integer taskId;
	/** 日志明细 */
	private String logDetail;
	/** 日志级别 */
	private String logGrade;
	/** 日志用例步骤 */
	private String logStep;
	/** UI自动化自动截图地址 */
	private String imgname;
	/** 创建时间 */
	private Date createTime;

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public void setLogId(Integer logId) 
	{
		this.logId = logId;
	}

	public Integer getLogId() 
	{
		return logId;
	}
	public void setTaskCaseId(Integer taskCaseId) 
	{
		this.taskCaseId = taskCaseId;
	}

	public Integer getTaskCaseId() 
	{
		return taskCaseId;
	}
	public void setTaskId(Integer taskId) 
	{
		this.taskId = taskId;
	}

	public Integer getTaskId() 
	{
		return taskId;
	}
	public void setLogDetail(String logDetail) 
	{
		this.logDetail = logDetail;
	}

	public String getLogDetail() 
	{
		return logDetail;
	}
	public void setLogGrade(String logGrade) 
	{
		this.logGrade = logGrade;
	}

	public String getLogGrade() 
	{
		return logGrade;
	}
	public void setLogStep(String logStep) 
	{
		this.logStep = logStep;
	}

	public String getLogStep() 
	{
		return logStep;
	}
	public void setImgname(String imgname) 
	{
		this.imgname = imgname;
	}

	public String getImgname() 
	{
		return imgname;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("taskCaseId", getTaskCaseId())
            .append("taskId", getTaskId())
            .append("logDetail", getLogDetail())
            .append("logGrade", getLogGrade())
            .append("logStep", getLogStep())
            .append("imgname", getImgname())
            .append("createTime", getCreateTime())
            .toString();
    }
}
