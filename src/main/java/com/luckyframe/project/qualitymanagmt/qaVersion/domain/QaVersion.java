package com.luckyframe.project.qualitymanagmt.qaVersion.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;

/**
 * 质量管理-版本管理表 qa_version
 * 
 * @author luckyframe
 * @date 2019-08-05
 */
public class QaVersion extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 版本ID */
	private Integer versionId;
	/** 项目ID */
	private Integer projectId;
	/** 版本号 */
	private String versionNumber;
	/** 版本状态 */
	private String versionStatus;
	/** 负责人 */
	private String leader;
	/** 开发人员 */
	private String developer;
	/** 测试人员 */
	private String tester;
	/** 计划完成日期 */
	private String planFinishDate;
	/** 实际完成日期 */
	private String actuallyFinishDate;
	/** 上线日期 */
	private String launchDate;
	/** 版本工期 单位:天 */
	private Integer timeLimitVersion;
	/** 项目偏移时间 */
	private Integer projectDeviationDays;
	/** 项目偏移百分比 */
	private Float projectDeviationPercent;
	/** 计划完成需求数 */
	private Integer demandPlanFinish;
	/** 实际完成需求数 */
	private Integer demandActuallyFinish;
	/** 需求完成百分比 */
	private Float demandPercent;
	/** 测试用例数 */
	private Integer testcaseCount;
	/** 转测试打回次数 */
	private Integer testingReturn;
	/** 开发投入人力 单位:人/天 */
	private Integer devHumanResources;
	/** 测试投入人力 单位:人/天 */
	private Integer testHumanResources;
	/** 代码变动行数 */
	private Integer changeLineCode;
	/** Bug数量 级别:致命 */
	private Integer bugZm;
	/** Bug数量 级别:严重 */
	private Integer bugYz;
	/** Bug数量 级别:一般 */
	private Integer bugYb;
	/** Bug数量 级别:提示 */
	private Integer bugTs;
	/** 代码DI值 */
	private Float codeDi;
	/** 质量回溯 */
	private String qualityReview;
	/** 版本说明 */
	private String imprint;
	/** 备注 */
	private String remark;
	/** 关联项目实体 */
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public void setVersionId(Integer versionId) 
	{
		this.versionId = versionId;
	}

	public Integer getVersionId() 
	{
		return versionId;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setVersionNumber(String versionNumber) 
	{
		this.versionNumber = versionNumber;
	}

	public String getVersionNumber() 
	{
		return versionNumber;
	}
	public void setVersionStatus(String versionStatus) 
	{
		this.versionStatus = versionStatus;
	}

	public String getVersionStatus() 
	{
		return versionStatus;
	}
	public void setLeader(String leader) 
	{
		this.leader = leader;
	}

	public String getLeader() 
	{
		return leader;
	}
	public void setDeveloper(String developer) 
	{
		this.developer = developer;
	}

	public String getDeveloper() 
	{
		return developer;
	}
	public void setTester(String tester) 
	{
		this.tester = tester;
	}

	public String getTester() 
	{
		return tester;
	}
	public void setPlanFinishDate(String planFinishDate) 
	{
		this.planFinishDate = planFinishDate;
	}

	public String getPlanFinishDate() 
	{
		return planFinishDate;
	}
	public void setActuallyFinishDate(String actuallyFinishDate) 
	{
		this.actuallyFinishDate = actuallyFinishDate;
	}

	public String getActuallyFinishDate() 
	{
		return actuallyFinishDate;
	}
	public void setLaunchDate(String launchDate) 
	{
		this.launchDate = launchDate;
	}

	public String getLaunchDate() 
	{
		return launchDate;
	}
	public void setTimeLimitVersion(Integer timeLimitVersion) 
	{
		this.timeLimitVersion = timeLimitVersion;
	}

	public Integer getTimeLimitVersion() 
	{
		return timeLimitVersion;
	}
	public void setProjectDeviationDays(Integer projectDeviationDays) 
	{
		this.projectDeviationDays = projectDeviationDays;
	}

	public Integer getProjectDeviationDays() 
	{
		return projectDeviationDays;
	}
	public void setProjectDeviationPercent(Float projectDeviationPercent) 
	{
		this.projectDeviationPercent = projectDeviationPercent;
	}

	public Float getProjectDeviationPercent() 
	{
		return projectDeviationPercent;
	}
	public void setDemandPlanFinish(Integer demandPlanFinish) 
	{
		this.demandPlanFinish = demandPlanFinish;
	}

	public Integer getDemandPlanFinish() 
	{
		return demandPlanFinish;
	}
	public void setDemandActuallyFinish(Integer demandActuallyFinish) 
	{
		this.demandActuallyFinish = demandActuallyFinish;
	}

	public Integer getDemandActuallyFinish() 
	{
		return demandActuallyFinish;
	}
	public void setDemandPercent(Float demandPercent) 
	{
		this.demandPercent = demandPercent;
	}

	public Float getDemandPercent() 
	{
		return demandPercent;
	}
	public void setTestcaseCount(Integer testcaseCount) 
	{
		this.testcaseCount = testcaseCount;
	}

	public Integer getTestcaseCount() 
	{
		return testcaseCount;
	}
	public void setTestingReturn(Integer testingReturn) 
	{
		this.testingReturn = testingReturn;
	}

	public Integer getTestingReturn() 
	{
		return testingReturn;
	}
	public void setDevHumanResources(Integer devHumanResources) 
	{
		this.devHumanResources = devHumanResources;
	}

	public Integer getDevHumanResources() 
	{
		return devHumanResources;
	}
	public void setTestHumanResources(Integer testHumanResources) 
	{
		this.testHumanResources = testHumanResources;
	}

	public Integer getTestHumanResources() 
	{
		return testHumanResources;
	}
	public void setChangeLineCode(Integer changeLineCode) 
	{
		this.changeLineCode = changeLineCode;
	}

	public Integer getChangeLineCode() 
	{
		return changeLineCode;
	}
	public void setBugZm(Integer bugZm) 
	{
		this.bugZm = bugZm;
	}

	public Integer getBugZm() 
	{
		return bugZm;
	}
	public void setBugYz(Integer bugYz) 
	{
		this.bugYz = bugYz;
	}

	public Integer getBugYz() 
	{
		return bugYz;
	}
	public void setBugYb(Integer bugYb) 
	{
		this.bugYb = bugYb;
	}

	public Integer getBugYb() 
	{
		return bugYb;
	}
	public void setBugTs(Integer bugTs) 
	{
		this.bugTs = bugTs;
	}

	public Integer getBugTs() 
	{
		return bugTs;
	}
	public void setCodeDi(Float codeDi) 
	{
		this.codeDi = codeDi;
	}

	public Float getCodeDi() 
	{
		return codeDi;
	}
	public void setQualityReview(String qualityReview) 
	{
		this.qualityReview = qualityReview;
	}

	public String getQualityReview() 
	{
		return qualityReview;
	}
	public void setImprint(String imprint) 
	{
		this.imprint = imprint;
	}

	public String getImprint() 
	{
		return imprint;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("versionId", getVersionId())
            .append("projectId", getProjectId())
            .append("versionNumber", getVersionNumber())
            .append("versionStatus", getVersionStatus())
            .append("leader", getLeader())
            .append("developer", getDeveloper())
            .append("tester", getTester())
            .append("planFinishDate", getPlanFinishDate())
            .append("actuallyFinishDate", getActuallyFinishDate())
            .append("launchDate", getLaunchDate())
            .append("timeLimitVersion", getTimeLimitVersion())
            .append("projectDeviationDays", getProjectDeviationDays())
            .append("projectDeviationPercent", getProjectDeviationPercent())
            .append("demandPlanFinish", getDemandPlanFinish())
            .append("demandActuallyFinish", getDemandActuallyFinish())
            .append("demandPercent", getDemandPercent())
            .append("testcaseCount", getTestcaseCount())
            .append("testingReturn", getTestingReturn())
            .append("devHumanResources", getDevHumanResources())
            .append("testHumanResources", getTestHumanResources())
            .append("changeLineCode", getChangeLineCode())
            .append("bugZm", getBugZm())
            .append("bugYz", getBugYz())
            .append("bugYb", getBugYb())
            .append("bugTs", getBugTs())
            .append("codeDi", getCodeDi())
            .append("qualityReview", getQualityReview())
            .append("imprint", getImprint())
            .append("remark", getRemark())
            .toString();
    }
}
