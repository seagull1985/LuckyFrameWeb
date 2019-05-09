package com.luckyframe.project.system.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.dept.domain.Dept;

/**
 * 测试项目管理表 sys_project
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public class Project extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 项目ID */
	private Integer projectId;
	/** 项目名称 */
	private String projectName;
	/** 归属部门 */
	private Integer deptId;
	/** 项目标识 */
	private String projectSign;
	/** 归属部门实体 */
	private Dept dept;
	/** 项目标记 */
	private boolean flag = false;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setProjectName(String projectName) 
	{
		this.projectName = projectName;
	}

	public String getProjectName() 
	{
		return projectName;
	}
	public void setDeptId(Integer deptId) 
	{
		this.deptId = deptId;
	}

	public Integer getDeptId() 
	{
		return deptId;
	}
	public void setProjectSign(String projectSign) 
	{
		this.projectSign = projectSign;
	}

	public String getProjectSign() 
	{
		return projectSign;
	}

    public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("deptId", getDeptId())
            .append("projectSign", getProjectSign())
            .append("dept", getDept())
            .toString();
    }
}
