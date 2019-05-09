package com.luckyframe.project.testmanagmt.projectProtocolTemplate.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectTemplateParams;

/**
 * 模板参数管理 服务层
 * 
 * @author luckyframe
 * @date 2019-03-05
 */
public interface IProjectTemplateParamsService 
{
	/**
     * 查询模板参数管理信息
     * 
     * @param paramsId 模板参数管理ID
     * @return 模板参数管理信息
     */
	public ProjectTemplateParams selectProjectTemplateParamsById(Integer paramsId);
	
	/**
     * 查询模板参数管理列表
     * 
     * @param projectTemplateParams 模板参数管理信息
     * @return 模板参数管理集合
     */
	public List<ProjectTemplateParams> selectProjectTemplateParamsList(ProjectTemplateParams projectTemplateParams);
	
	/**
     * 新增模板参数管理
     * 
     * @param projectTemplateParams 模板参数管理信息
     * @return 结果
     */
	public int insertProjectTemplateParams(ProjectTemplateParams projectTemplateParams);
	
	/**
     * 修改模板参数管理
     * 
     * @param projectTemplateParams 模板参数管理信息
     * @return 结果
     */
	public int updateProjectTemplateParams(ProjectTemplateParams projectTemplateParams);
		
	/**
     * 删除模板参数管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectTemplateParamsByIds(List<ProjectTemplateParams> templateParams);
	
}
