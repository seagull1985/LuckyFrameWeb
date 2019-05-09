package com.luckyframe.project.testmanagmt.projectPlan.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 测试计划用例表 project_plan_case
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
public class ProjectPlanCase extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 计划用例ID */
	private Integer planCaseId;
	/** 用例ID */
	private Integer caseId;
	/** 测试计划ID */
	private Integer planId;
	/** 用例优先级 数字越小，优先级越高 */
	private Integer priority;

	public void setPlanCaseId(Integer planCaseId) 
	{
		this.planCaseId = planCaseId;
	}

	public Integer getPlanCaseId() 
	{
		return planCaseId;
	}
	public void setCaseId(Integer caseId) 
	{
		this.caseId = caseId;
	}

	public Integer getCaseId() 
	{
		return caseId;
	}
	public void setPlanId(Integer planId) 
	{
		this.planId = planId;
	}

	public Integer getPlanId() 
	{
		return planId;
	}
	public void setPriority(Integer priority) 
	{
		this.priority = priority;
	}

	public Integer getPriority() 
	{
		return priority;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("planCaseId", getPlanCaseId())
            .append("caseId", getCaseId())
            .append("planId", getPlanId())
            .append("priority", getPriority())
            .toString();
    }
}
