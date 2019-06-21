package com.luckyframe.project.testmanagmt.projectCase.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;

/**
 * 项目测试用例管理表 project_case
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public class ProjectCase extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 测试用例ID */
	@Excel(name = "用例序号")
	private Integer caseId;
	/** 用例编号排序 */
	private Integer caseSerialNumber;
	/** 用例标识 */
	@Excel(name = "用例编号")
	private String caseSign;
	/** 用例名称 */
	@Excel(name = "用例名称")
	private String caseName;
	/** 关联项目ID */
	@Excel(name = "项目ID")
	private Integer projectId;
	/** 关联项目模块ID */
	@Excel(name = "模块ID")
	private Integer moduleId;
	/** 默认类型 0 HTTP接口 1 Web UI 2 API驱动  3移动端 */
	@Excel(name = "用例类型")
	private Integer caseType;
	/** 前置步骤失败，后续步骤是否继续，0：中断，1：继续 */
	@Excel(name = "步骤是否继续")
	private Integer failcontinue;
	/** 关联项目实体 */
	private Project project;
	/** 关联用例模块实体 */
	private ProjectCaseModule projectCaseModule;
	/** 用例选中标记 */
	private boolean flag = false;
	/** 用例优先级 */
    private int priority;
	/** 关联计划ID标识 */
	private Integer planId;
	/** 关联计划用例ID标识 */
	private Integer planCaseId;

	public Integer getPlanCaseId() {
		return planCaseId;
	}

	public void setPlanCaseId(Integer planCaseId) {
		this.planCaseId = planCaseId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ProjectCaseModule getProjectCaseModule() {
		return projectCaseModule;
	}

	public void setProjectCaseModule(ProjectCaseModule projectCaseModule) {
		this.projectCaseModule = projectCaseModule;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setCaseId(Integer caseId) 
	{
		this.caseId = caseId;
	}

	public Integer getCaseId() 
	{
		return caseId;
	}
	public void setCaseSerialNumber(Integer caseSerialNumber) 
	{
		this.caseSerialNumber = caseSerialNumber;
	}

	public Integer getCaseSerialNumber() 
	{
		return caseSerialNumber;
	}
	public void setCaseSign(String caseSign) 
	{
		this.caseSign = caseSign;
	}

	public String getCaseSign() 
	{
		return caseSign;
	}
	public void setCaseName(String caseName) 
	{
		this.caseName = caseName;
	}

	public String getCaseName() 
	{
		return caseName;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setModuleId(Integer moduleId) 
	{
		this.moduleId = moduleId;
	}

	public Integer getModuleId() 
	{
		return moduleId;
	}
	public void setCaseType(Integer caseType) 
	{
		this.caseType = caseType;
	}

	public Integer getCaseType() 
	{
		return caseType;
	}
	public void setFailcontinue(Integer failcontinue) 
	{
		this.failcontinue = failcontinue;
	}

	public Integer getFailcontinue() 
	{
		return failcontinue;
	}
	
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("caseId", getCaseId())
            .append("caseSerialNumber", getCaseSerialNumber())
            .append("caseSign", getCaseSign())
            .append("caseName", getCaseName())
            .append("projectId", getProjectId())
            .append("moduleId", getModuleId())
            .append("caseType", getCaseType())
            .append("failcontinue", getFailcontinue())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("project", getProject())
            .append("projectCaseModule", getProjectCaseModule())            
            .toString();
    }
}
