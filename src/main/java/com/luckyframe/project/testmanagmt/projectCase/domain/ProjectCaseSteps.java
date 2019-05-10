package com.luckyframe.project.testmanagmt.projectCase.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 测试用例步骤管理表 project_case_steps
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public class ProjectCaseSteps extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 步骤ID */
	private Integer stepId;
	/** 用例ID */
	private Integer caseId;
	/** 项目ID */
	private Integer projectId;
	/** 步骤序号 */
	private Integer stepSerialNumber;
	/** 包路径|定位路径 */
	private String stepPath;
	/** 方法名|操作 */
	private String stepOperation;
	/** 参数 */
	private String stepParameters;
	/** 步骤动作 */
	private String action;
	/** 预期结果 */
	private String expectedResult;
	/** 默认类型 0 HTTP接口 1 Web UI 2 API驱动  3移动端 */
	private Integer stepType;
	/** 扩展字段，可用于备注、存储HTTP模板等 */
	private String extend;

	public void setStepId(Integer stepId) 
	{
		this.stepId = stepId;
	}

	public Integer getStepId() 
	{
		return stepId;
	}
	public void setCaseId(Integer caseId) 
	{
		this.caseId = caseId;
	}

	public Integer getCaseId() 
	{
		return caseId;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setStepSerialNumber(Integer stepSerialNumber) 
	{
		this.stepSerialNumber = stepSerialNumber;
	}

	public Integer getStepSerialNumber() 
	{
		return stepSerialNumber;
	}
	public void setStepPath(String stepPath) 
	{
		this.stepPath = stepPath;
	}

	public String getStepPath() 
	{
		return stepPath;
	}
	public void setStepOperation(String stepOperation) 
	{
		this.stepOperation = stepOperation;
	}

	public String getStepOperation() 
	{
		return stepOperation;
	}
	public void setStepParameters(String stepParameters) 
	{
		this.stepParameters = stepParameters;
	}

	public String getStepParameters() 
	{
		return stepParameters;
	}
	public void setAction(String action) 
	{
		this.action = action;
	}

	public String getAction() 
	{
		return action;
	}
	public void setExpectedResult(String expectedResult) 
	{
		this.expectedResult = expectedResult;
	}

	public String getExpectedResult() 
	{
		return expectedResult;
	}
	public void setStepType(Integer stepType) 
	{
		this.stepType = stepType;
	}

	public Integer getStepType() 
	{
		return stepType;
	}
	public void setExtend(String extend) 
	{
		this.extend = extend;
	}

	public String getExtend() 
	{
		return extend;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("stepId", getStepId())
            .append("caseId", getCaseId())
            .append("projectId", getProjectId())
            .append("stepSerialNumber", getStepSerialNumber())
            .append("stepPath", getStepPath())
            .append("stepOperation", getStepOperation())
            .append("stepParameters", getStepParameters())
            .append("action", getAction())
            .append("expectedResult", getExpectedResult())
            .append("stepType", getStepType())
            .append("extend", getExtend())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
