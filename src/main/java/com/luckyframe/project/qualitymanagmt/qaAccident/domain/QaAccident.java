package com.luckyframe.project.qualitymanagmt.qaAccident.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;

/**
 * 生产事故登记表 qa_accident
 * 
 * @author luckyframe
 * @date 2019-07-12
 */
public class QaAccident extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 事故ID */
	private Integer accidentId;
	/** 项目ID */
	private Integer projectId;
	/** 事故状态 */
	private String accidentStatus;
	/** 事故发生时间 */
	private String accidentTime;
	/** 事故报告时间 */
	private String reportTime;
	/** 事故描述 */
	private String accidentDescription;
	/** 事故等级 */
	private String accidentLevel;
	/** 事故分析 */
	private String accidentAnalysis;
	/** 事故类型 */
	private String accidentType;
	/** 事故影响后果 */
	private String accidentConsequence;
	/** 纠正措施 */
	private String correctiveAction;
	/** 解决时间 */
	private String resolutionTime;
	/** 解决处理人 */
	private String resolutioner;
	/** 预防措施 */
	private String preventiveAction;
	/** 预防措施责任人 */
	private String preventiver;
	/** 预防措施计划完成时间 */
	private String preventivePlanDate;
	/** 预防措施实际完成时间 */
	private String preventiveOverDate;
	/** 事故持续时间 */
	private Integer durationTime;
	/** 事故影响时间 */
	private Integer impactTime;
	/** 事故报告附件路径 */
	private String accidentFileName;
	
	/** 关联项目实体 */
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setAccidentId(Integer accidentId) 
	{
		this.accidentId = accidentId;
	}

	public Integer getAccidentId() 
	{
		return accidentId;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setAccidentStatus(String accidentStatus) 
	{
		this.accidentStatus = accidentStatus;
	}

	public String getAccidentStatus() 
	{
		return accidentStatus;
	}
	public void setAccidentTime(String accidentTime) 
	{
		this.accidentTime = accidentTime;
	}

	public String getAccidentTime() 
	{
		return accidentTime;
	}
	public void setReportTime(String reportTime) 
	{
		this.reportTime = reportTime;
	}

	public String getReportTime() 
	{
		return reportTime;
	}
	public void setAccidentDescription(String accidentDescription) 
	{
		this.accidentDescription = accidentDescription;
	}

	public String getAccidentDescription() 
	{
		return accidentDescription;
	}
	public void setAccidentLevel(String accidentLevel) 
	{
		this.accidentLevel = accidentLevel;
	}

	public String getAccidentLevel() 
	{
		return accidentLevel;
	}
	public void setAccidentAnalysis(String accidentAnalysis) 
	{
		this.accidentAnalysis = accidentAnalysis;
	}

	public String getAccidentAnalysis() 
	{
		return accidentAnalysis;
	}
	public void setAccidentType(String accidentType) 
	{
		this.accidentType = accidentType;
	}

	public String getAccidentType() 
	{
		return accidentType;
	}
	public void setAccidentConsequence(String accidentConsequence) 
	{
		this.accidentConsequence = accidentConsequence;
	}

	public String getAccidentConsequence() 
	{
		return accidentConsequence;
	}
	public void setCorrectiveAction(String correctiveAction) 
	{
		this.correctiveAction = correctiveAction;
	}

	public String getCorrectiveAction() 
	{
		return correctiveAction;
	}
	public void setResolutionTime(String resolutionTime) 
	{
		this.resolutionTime = resolutionTime;
	}

	public String getResolutionTime() 
	{
		return resolutionTime;
	}
	public void setResolutioner(String resolutioner) 
	{
		this.resolutioner = resolutioner;
	}

	public String getResolutioner() 
	{
		return resolutioner;
	}
	public void setPreventiveAction(String preventiveAction) 
	{
		this.preventiveAction = preventiveAction;
	}

	public String getPreventiveAction() 
	{
		return preventiveAction;
	}
	public void setPreventiver(String preventiver) 
	{
		this.preventiver = preventiver;
	}

	public String getPreventiver() 
	{
		return preventiver;
	}
	public void setPreventivePlanDate(String preventivePlanDate) 
	{
		this.preventivePlanDate = preventivePlanDate;
	}

	public String getPreventivePlanDate() 
	{
		return preventivePlanDate;
	}
	public void setPreventiveOverDate(String preventiveOverDate) 
	{
		this.preventiveOverDate = preventiveOverDate;
	}

	public String getPreventiveOverDate() 
	{
		return preventiveOverDate;
	}
	public void setDurationTime(Integer durationTime) 
	{
		this.durationTime = durationTime;
	}

	public Integer getDurationTime() 
	{
		return durationTime;
	}
	public void setImpactTime(Integer impactTime) 
	{
		this.impactTime = impactTime;
	}

	public Integer getImpactTime() 
	{
		return impactTime;
	}
	public void setAccidentFileName(String accidentFileName) 
	{
		this.accidentFileName = accidentFileName;
	}

	public String getAccidentFileName() 
	{
		return accidentFileName;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("accidentId", getAccidentId())
            .append("projectId", getProjectId())
            .append("accidentStatus", getAccidentStatus())
            .append("accidentTime", getAccidentTime())
            .append("reportTime", getReportTime())
            .append("accidentDescription", getAccidentDescription())
            .append("accidentLevel", getAccidentLevel())
            .append("accidentAnalysis", getAccidentAnalysis())
            .append("accidentType", getAccidentType())
            .append("accidentConsequence", getAccidentConsequence())
            .append("correctiveAction", getCorrectiveAction())
            .append("resolutionTime", getResolutionTime())
            .append("resolutioner", getResolutioner())
            .append("preventiveAction", getPreventiveAction())
            .append("preventiver", getPreventiver())
            .append("preventivePlanDate", getPreventivePlanDate())
            .append("preventiveOverDate", getPreventiveOverDate())
            .append("durationTime", getDurationTime())
            .append("impactTime", getImpactTime())
            .append("accidentFileName", getAccidentFileName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
