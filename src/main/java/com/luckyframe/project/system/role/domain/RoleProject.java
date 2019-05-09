package com.luckyframe.project.system.role.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 角色和项目关联表 sys_role_project
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
public class RoleProject extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 角色ID */
	private Integer roleId;
	/** 项目ID */
	private Integer projectId;

	public void setRoleId(Integer roleId) 
	{
		this.roleId = roleId;
	}

	public Integer getRoleId() 
	{
		return roleId;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("projectId", getProjectId())
            .toString();
    }
}
