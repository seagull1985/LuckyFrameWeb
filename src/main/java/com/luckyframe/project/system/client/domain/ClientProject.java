package com.luckyframe.project.system.client.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 客户端与项目关联表 sys_client_project
 * 
 * @author luckyframe
 * @date 2019-02-20
 */
public class ClientProject extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 客户端ID */
	private int clientId;
	/** 项目ID */
	private int projectId;

	public void setClientId(int clientId) 
	{
		this.clientId = clientId;
	}

	public int getClientId() 
	{
		return clientId;
	}
	public void setProjectId(int projectId) 
	{
		this.projectId = projectId;
	}

	public int getProjectId() 
	{
		return projectId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("clientId", getClientId())
            .append("projectId", getProjectId())
            .toString();
    }
}
