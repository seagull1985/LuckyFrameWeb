package com.luckyframe.project.testmanagmt.projectSuite.domain;

import com.luckyframe.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProjectSuitePlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 聚合计划ID */
    private Integer suitePlanId;

    /** 计划ID */
    private Integer planId;

    /** 聚合ID */
    private Integer suiteId;
    /** 用例优先级 数字越小，优先级越高 */
    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getSuitePlanId() {
        return suitePlanId;
    }

    public void setSuitePlanId(Integer suitePlanId) {
        this.suitePlanId = suitePlanId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("suitePlanId",getSuitePlanId())
                .append("planId",getPlanId())
                .append("suiteId",getSuiteId())
                .append("priority",getPriority())
                .toString();
    }
}
