package com.luckyframe.project.testmanagmt.projectProtocolTemplate.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectProtocolTemplate;	

/**
 * 协议模板管理 数据层
 * 
 * @author luckyframe
 * @date 2019-03-04
 */
public interface ProjectProtocolTemplateMapper 
{
	/**
     * 查询协议模板管理信息
     * 
     * @param templateId 协议模板管理ID
     * @return 协议模板管理信息
     */
	public ProjectProtocolTemplate selectProjectProtocolTemplateById(Integer templateId);
	
	/**
     * 查询协议模板管理列表
     * 
     * @param projectProtocolTemplate 协议模板管理信息
     * @return 协议模板管理集合
     */
	public List<ProjectProtocolTemplate> selectProjectProtocolTemplateList(ProjectProtocolTemplate projectProtocolTemplate);
	
	/**
     * 新增协议模板管理
     * 
     * @param projectProtocolTemplate 协议模板管理信息
     * @return 结果
     */
	public int insertProjectProtocolTemplate(ProjectProtocolTemplate projectProtocolTemplate);
	
	/**
     * 修改协议模板管理
     * 
     * @param projectProtocolTemplate 协议模板管理信息
     * @return 结果
     */
	public int updateProjectProtocolTemplate(ProjectProtocolTemplate projectProtocolTemplate);
	
	/**
     * 删除协议模板管理
     * 
     * @param templateId 协议模板管理ID
     * @return 结果
     */
	public int deleteProjectProtocolTemplateById(Integer templateId);
	
	/**
     * 批量删除协议模板管理
     * 
     * @param templateIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectProtocolTemplateByIds(String[] templateIds);
	
	
	/**
	 * 检查协议名称唯一性
	 * @param templateName
	 * @return
	 * @author Seagull
	 * @date 2019年3月4日
	 */
	public ProjectProtocolTemplate checkProjectProtocolTemplateNameUnique(ProjectProtocolTemplate projectProtocolTemplate);
}