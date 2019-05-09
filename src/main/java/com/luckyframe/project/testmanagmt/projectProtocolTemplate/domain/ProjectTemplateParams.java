package com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luckyframe.framework.web.domain.BaseEntity;

/**
 * 模板参数管理表 project_template_params
 * 
 * @author luckyframe
 * @date 2019-03-05
 */
public class ProjectTemplateParams extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 模板参数ID */
	private Integer paramsId;
	/** 模板ID */
	private Integer templateId;
	/** 参数名 */
	private String paramName;
	/** 参数默认值 */
	private String paramValue;
	/** 0 String 1 JSON对象 2 JSONARR对象 3 文件类型 */
	private Integer paramType;

	public void setParamsId(Integer paramsId) 
	{
		this.paramsId = paramsId;
	}

	public Integer getParamsId() 
	{
		return paramsId;
	}
	public void setTemplateId(Integer templateId) 
	{
		this.templateId = templateId;
	}

	public Integer getTemplateId() 
	{
		return templateId;
	}
	public void setParamName(String paramName) 
	{
		this.paramName = paramName;
	}

	public String getParamName() 
	{
		return paramName;
	}
	public void setParamValue(String paramValue) 
	{
		this.paramValue = paramValue;
	}

	public String getParamValue() 
	{
		return paramValue;
	}
	public void setParamType(Integer paramType) 
	{
		this.paramType = paramType;
	}

	public Integer getParamType() 
	{
		return paramType;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("paramsId", getParamsId())
            .append("templateId", getTemplateId())
            .append("paramName", getParamName())
            .append("paramValue", getParamValue())
            .append("paramType", getParamType())
            .toString();
    }
}
