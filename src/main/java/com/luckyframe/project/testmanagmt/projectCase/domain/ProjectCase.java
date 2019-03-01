package com.luckyframe.project.testmanagmt.projectCase.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;

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
	private Integer caseId;
	/** 用例编号排序 */
	private Integer caseSerialNumber;
	/** 用例标识 */
	private String caseSign;
	/** 用例名称 */
	private String caseName;
	/** 关联项目ID */
	private Integer projectId;
	/** 关联项目模块ID */
	private Integer moduleId;
	/** 默认类型 0 API接口 1 Web UI 2 HTTP接口 3移动端 */
	private Integer caseType;
	/** 前置步骤失败，后续步骤是否继续，0：中断，1：继续 */
	private Integer failcontinue;

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
            .toString();
    }
}
