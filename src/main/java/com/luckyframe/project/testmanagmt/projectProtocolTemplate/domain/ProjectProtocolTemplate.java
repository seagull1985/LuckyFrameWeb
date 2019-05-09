package com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.luckyframe.framework.web.domain.BaseEntity;
import com.luckyframe.project.system.project.domain.Project;

/**
 * 协议模板管理表 project_protocol_template
 * 
 * @author luckyframe
 * @date 2019-03-04
 */
public class ProjectProtocolTemplate extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 模板ID */
	private Integer templateId;
	/** 模板名称 */
	private String templateName;
	/** 项目ID */
	private Integer projectId;
	/** 消息头 */
	private String headMsg;
	/** 客户端中的证书路径 */
	private String cerificatePath;
	/** 编码格式 */
	private String encoding;
	/** 超时时间 */
	private Integer timeout;
	/** 请求响应返回值是否带头域信息 0不带 1带 */
	private Integer isResponseHead;
	/** 请求响应返回值是否带状态码 0不带 1带 */
	private Integer isResponseCode;
	/** 关联项目实体 */
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTemplateId(Integer templateId) 
	{
		this.templateId = templateId;
	}

	public Integer getTemplateId() 
	{
		return templateId;
	}
	public void setTemplateName(String templateName) 
	{
		this.templateName = templateName;
	}

	public String getTemplateName() 
	{
		return templateName;
	}
	public void setProjectId(Integer projectId) 
	{
		this.projectId = projectId;
	}

	public Integer getProjectId() 
	{
		return projectId;
	}
	public void setHeadMsg(String headMsg) 
	{
		this.headMsg = headMsg;
	}

	public String getHeadMsg() 
	{
		return headMsg;
	}
	public void setCerificatePath(String cerificatePath) 
	{
		this.cerificatePath = cerificatePath;
	}

	public String getCerificatePath() 
	{
		return cerificatePath;
	}
	public void setEncoding(String encoding) 
	{
		this.encoding = encoding;
	}

	public String getEncoding() 
	{
		return encoding;
	}
	public void setTimeout(Integer timeout) 
	{
		this.timeout = timeout;
	}

	public Integer getTimeout() 
	{
		return timeout;
	}
	public void setIsResponseHead(Integer isResponseHead) 
	{
		this.isResponseHead = isResponseHead;
	}

	public Integer getIsResponseHead() 
	{
		return isResponseHead;
	}
	public void setIsResponseCode(Integer isResponseCode) 
	{
		this.isResponseCode = isResponseCode;
	}

	public Integer getIsResponseCode() 
	{
		return isResponseCode;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("templateName", getTemplateName())
            .append("projectId", getProjectId())
            .append("headMsg", getHeadMsg())
            .append("cerificatePath", getCerificatePath())
            .append("encoding", getEncoding())
            .append("timeout", getTimeout())
            .append("isResponseHead", getIsResponseHead())
            .append("isResponseCode", getIsResponseCode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("project", getProject())
            .toString();
    }
}
