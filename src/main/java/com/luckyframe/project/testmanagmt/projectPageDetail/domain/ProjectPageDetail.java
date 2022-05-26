package com.luckyframe.project.testmanagmt.projectPageDetail.domain;

import com.luckyframe.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 页面详情表 project_page_detail
 * @author YSS陈再兴
 * @date 2022-03-10
 */
public class ProjectPageDetail extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 系统主键 */
	private Integer id;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;
	/** 页面唯一编号 */
	private Integer pageId;
	/** 定位类型，支持Byxpath等等 */
	private String type;
	/** 元素名称 */
	private String element;
	/** 具体的参数值 */
	private String value;

	public ProjectPageDetail(){

	}

	public ProjectPageDetail(int pageId,String element){
        this.pageId=pageId;
        this.element=element;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}
	public void setPageId(Integer pageId) 
	{
		this.pageId = pageId;
	}

	public Integer getPageId() 
	{
		return pageId;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}
	public void setElement(String element) 
	{
		this.element = element;
	}

	public String getElement() 
	{
		return element;
	}
	public void setValue(String value) 
	{
		this.value = value;
	}

	public String getValue() 
	{
		return value;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("pageId", getPageId())
            .append("type", getType())
            .append("element", getElement())
            .append("value", getValue())
            .toString();
    }
}
