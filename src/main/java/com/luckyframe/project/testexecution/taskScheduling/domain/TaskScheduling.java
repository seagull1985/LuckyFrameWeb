package com.luckyframe.project.testexecution.taskScheduling.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.common.constant.ScheduleConstants;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;

/**
 * 测试任务调度表 task_scheduling
 * 
 * @author luckyframe
 * @date 2019-03-23
 */
public class TaskScheduling extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 预约调度ID */
	private Integer schedulingId;
	/** 预约调度名称 */
	private String schedulingName;
	/** 任务ID */
	private Integer jobId;
	/** 项目ID */
	private Integer projectId;
	/** 测试计划ID */
	private Integer planId;
	/** 客户端ID */
	private Integer clientId;
	/** 邮件通知地址 */
	private String emailAddress;
	/** 发送邮件通知时的具体逻辑, 0-全部，1-成功，-1-失败 */
	private Integer emailSendCondition;
	/** jenkins构建链接 */
	private String buildingLink;
	/** 远程执行Shell脚本 */
	private String remoteShell;
	/** 客户端执行线程数 */
	private Integer exThreadCount;
	/** 任务类型 0 接口 1 Web UI 2 移动 */
	private Integer taskType;
	/** UI自动化浏览器类型 0 IE 1 火狐 2 谷歌 3 Edge */
	private Integer browserType;
	/** 任务超时时间(分钟) */
	private Integer taskTimeout;
	/** 客户端测试驱动桩路径 */
	private String clientDriverPath;
	/** 关联项目实体 */
	private Project project;
	/** 关联系统调度 */
	private Job job;
	/** 关联项目计划 */
	private ProjectPlan projectPlan;
	/** 关联客户端 */
	private Client client;
	/** 任务名称 */
    private String jobName;
    /** cron执行表达式 */
    private String cronExpression;
    /** cron计划策略 */
    private String misfirePolicy = ScheduleConstants.MISFIRE_DO_NOTHING;
    /** 任务状态（0正常 1暂停） */
    private String status;
    /** 任务备注 */
    private String remark;

	public String getSchedulingName() {
		return schedulingName;
	}

	public void setSchedulingName(String schedulingName) {
		this.schedulingName = schedulingName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ProjectPlan getProjectPlan() {
		return projectPlan;
	}

	public void setProjectPlan(ProjectPlan projectPlan) {
		this.projectPlan = projectPlan;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setSchedulingId(Integer schedulingId) 
	{
		this.schedulingId = schedulingId;
	}

	public Integer getSchedulingId() 
	{
		return schedulingId;
	}
	public void setJobId(Integer jobId) 
	{
		this.jobId = jobId;
	}

	public Integer getJobId() 
	{
		return jobId;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setPlanId(Integer planId) 
	{
		this.planId = planId;
	}

	public Integer getPlanId() 
	{
		return planId;
	}
	public void setClientId(Integer clientId) 
	{
		this.clientId = clientId;
	}

	public Integer getClientId() 
	{
		return clientId;
	}
	public void setEmailAddress(String emailAddress) 
	{
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() 
	{
		return emailAddress;
	}
	public void setEmailSendCondition(Integer emailSendCondition) 
	{
		this.emailSendCondition = emailSendCondition;
	}

	public Integer getEmailSendCondition() 
	{
		return emailSendCondition;
	}
	public void setBuildingLink(String buildingLink) 
	{
		this.buildingLink = buildingLink;
	}

	public String getBuildingLink() 
	{
		return buildingLink;
	}
	public void setRemoteShell(String remoteShell) 
	{
		this.remoteShell = remoteShell;
	}

	public String getRemoteShell() 
	{
		return remoteShell;
	}
	public void setExThreadCount(Integer exThreadCount) 
	{
		this.exThreadCount = exThreadCount;
	}

	public Integer getExThreadCount() 
	{
		return exThreadCount;
	}
	public void setTaskType(Integer taskType) 
	{
		this.taskType = taskType;
	}

	public Integer getTaskType() 
	{
		return taskType;
	}
	public void setBrowserType(Integer browserType) 
	{
		this.browserType = browserType;
	}

	public Integer getBrowserType() 
	{
		return browserType;
	}
	public void setTaskTimeout(Integer taskTimeout) 
	{
		this.taskTimeout = taskTimeout;
	}

	public Integer getTaskTimeout() 
	{
		return taskTimeout;
	}
	public void setClientDriverPath(String clientDriverPath) 
	{
		this.clientDriverPath = clientDriverPath;
	}

	public String getClientDriverPath() 
	{
		return clientDriverPath;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("schedulingId", getSchedulingId())
            .append("jobId", getJobId())
            .append("projectId", getProjectId())
            .append("planId", getPlanId())
            .append("clientId", getClientId())
            .append("emailAddress", getEmailAddress())
            .append("emailSendCondition", getEmailSendCondition())
            .append("buildingLink", getBuildingLink())
            .append("remoteShell", getRemoteShell())
            .append("exThreadCount", getExThreadCount())
            .append("taskType", getTaskType())
            .append("browserType", getBrowserType())
            .append("taskTimeout", getTaskTimeout())
            .append("clientDriverPath", getClientDriverPath())
            .toString();
    }
}
