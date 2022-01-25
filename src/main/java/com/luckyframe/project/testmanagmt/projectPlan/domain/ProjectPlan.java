package com.luckyframe.project.testmanagmt.projectPlan.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;

import java.util.Date;

/**
 * 测试计划表 project_plan
 *
 * @author luckyframe
 * @date 2019-03-15
 */
public class ProjectPlan extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 测试计划ID */
	private Integer planId;
	/** 测试计划名称 */
	private String planName;
	/** 计划中用例总数 */
	private Integer planCaseCount;
	/** 项目ID */
	private Integer projectId;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;
	/** 关联项目实体 */
	private Project project;
	/** 计划选中标记 */
	private boolean flag = false;
	/** 用例优先级 */
	private int priority;
	private Integer suitePlanId;

	/**
	 * 聚合计划ID
	 */
	private Integer suiteId;

	public Integer getSuitePlanId() {
		return suitePlanId;
	}

	public void setSuitePlanId(Integer suitePlanId) {
		this.suitePlanId = suitePlanId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}


	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getSuiteId() {
		return suiteId;
	}

	public void setSuiteId(Integer suiteId) {
		this.suiteId = suiteId;
	}



	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setPlanId(Integer planId)
	{
		this.planId = planId;
	}

	public Integer getPlanId()
	{
		return planId;
	}
	public void setPlanName(String planName)
	{
		this.planName = planName;
	}

	public String getPlanName()
	{
		return planName;
	}
	public void setPlanCaseCount(Integer planCaseCount)
	{
		this.planCaseCount = planCaseCount;
	}

	public Integer getPlanCaseCount()
	{
		return planCaseCount;
	}
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}

	public Integer getProjectId()
	{
		return projectId;
	}
	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}

	public String getCreateBy()
	{
		return createBy;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getCreateTime()
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy)
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy()
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getRemark()
	{
		return remark;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "ProjectPlan{" +
				"planId=" + planId +
				", planName='" + planName + '\'' +
				", planCaseCount=" + planCaseCount +
				", projectId=" + projectId +
				", createBy='" + createBy + '\'' +
				", createTime=" + createTime +
				", updateBy='" + updateBy + '\'' +
				", updateTime=" + updateTime +
				", remark='" + remark + '\'' +
				", project=" + project +
				", flag=" + flag +
				", priority=" + priority +
				", suitePlanId=" + suitePlanId +
				", suiteId=" + suiteId +
				'}';
	}

}
