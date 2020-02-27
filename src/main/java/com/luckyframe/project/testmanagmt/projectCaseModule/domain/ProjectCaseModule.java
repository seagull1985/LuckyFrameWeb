package com.luckyframe.project.testmanagmt.projectCaseModule.domain;

import com.luckyframe.framework.aspectj.lang.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;

/**
 * 测试用例模块管理表 project_case_module
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public class ProjectCaseModule extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	/** 所属项目名称 */
	@Excel(name = "项目名称", type = Excel.Type.ALL)
	private String projectName;

	/** 模块ID */
	private Integer moduleId;
	/** 模块名字 */
	@Excel(name = "模块名称", type = Excel.Type.ALL)
	private String moduleName;
	/** 项目ID */
	private Integer projectId;
	/** 父模块id */
	private Integer parentId;

	/** 祖模块列表 */
	@Excel(name = "祖模块列表", type = Excel.Type.ALL)
	private String ancestors;
	/** 显示顺序 */
	@Excel(name = "显示顺序", type = Excel.Type.ALL)
	private Integer orderNum;

	/** 备注 */
	@Excel(name = "备注", type = Excel.Type.ALL)
	private String remark;

	/** 关联项目实体 */
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setModuleId(Integer moduleId)
	{
		this.moduleId = moduleId;
	}

	public Integer getModuleId()
	{
		return moduleId;
	}
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getModuleName()
	{
		return moduleName;
	}
	public void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}

	public Integer getProjectId()
	{
		return projectId;
	}
	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

	public Integer getParentId()
	{
		return parentId;
	}
	public void setAncestors(String ancestors)
	{
		this.ancestors = ancestors;
	}

	public String getAncestors()
	{
		return ancestors;
	}
	public void setOrderNum(Integer orderNum)
	{
		this.orderNum = orderNum;
	}

	public Integer getOrderNum()
	{
		return orderNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
				.append("moduleId", getModuleId())
				.append("moduleName", getModuleName())
				.append("projectId", getProjectId())
				.append("parentId", getParentId())
				.append("ancestors", getAncestors())
				.append("orderNum", getOrderNum())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark())
				.append("projectName", getProjectName())
				.toString();
	}
}
