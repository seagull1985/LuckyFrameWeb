package com.luckyframe.project.testmanagmt.projectProtocolTemplate.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectProtocolTemplate;

/**
 * 协议模板管理 服务层
 * 
 * @author luckyframe
 * @date 2019-03-04
 */
public interface IProjectProtocolTemplateService 
{
	/**
     * 查询协议模板管理信息
     * 
     * @param templateId 协议模板管理ID
     * @return 协议模板管理信息
     */
	ProjectProtocolTemplate selectProjectProtocolTemplateById(Integer templateId);
	
	/**
     * 查询协议模板管理列表
     * 
     * @param projectProtocolTemplate 协议模板管理信息
     * @return 协议模板管理集合
     */
	List<ProjectProtocolTemplate> selectProjectProtocolTemplateList(ProjectProtocolTemplate projectProtocolTemplate);
	
	/**
     * 新增协议模板管理
     * 
     * @param projectProtocolTemplate 协议模板管理信息
     * @return 结果
     */
	int insertProjectProtocolTemplate(ProjectProtocolTemplate projectProtocolTemplate);
	
	/**
     * 修改协议模板管理
     * 
     * @param projectProtocolTemplate 协议模板管理信息
     * @return 结果
     */
	int updateProjectProtocolTemplate(ProjectProtocolTemplate projectProtocolTemplate);
		
	/**
     * 删除协议模板管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectProtocolTemplateByIds(String ids);
	
	/**
	 * 检验模板名称在项目中的唯一性
	 * @param projectProtocolTemplate 协议模板对象
	 * @author Seagull
	 * @date 2019年3月4日
	 */
	 String checkProjectProtocolTemplateNameUnique(ProjectProtocolTemplate projectProtocolTemplate);
}
