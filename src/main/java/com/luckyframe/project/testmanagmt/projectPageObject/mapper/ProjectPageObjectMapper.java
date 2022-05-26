package com.luckyframe.project.testmanagmt.projectPageObject.mapper;

import com.luckyframe.project.testmanagmt.projectPageObject.domain.ProjectPageObject;

import java.util.List;

/**
 * 页面配置管理 数据层
 * 
 * @author YSS陈再兴
 * @date 2022-03-10
 */
public interface ProjectPageObjectMapper 
{
	/**
     * 查询页面配置管理信息
     * 
     * @param pageId 页面配置管理ID 主键ID
     * @return 页面配置管理信息
     */
	public ProjectPageObject selectProjectPageObjectById(Integer pageId);
	
	/**
     * 查询页面配置管理列表
     * 
     * @param projectPageObject 页面配置管理信息 基本信息
     * @return 页面配置管理集合
     */
	public List<ProjectPageObject> selectProjectPageObjectList(ProjectPageObject projectPageObject);
	
	/**
     * 新增页面配置管理
     * 
     * @param projectPageObject 页面配置管理信息 基本信息
     * @return 结果
     */
	public int insertProjectPageObject(ProjectPageObject projectPageObject);
	
	/**
     * 修改页面配置管理
     * 
     * @param projectPageObject 页面配置管理信息 基本信息
     * @return 结果
     */
	public int updateProjectPageObject(ProjectPageObject projectPageObject);
	
	/**
     * 删除页面配置管理
     * 
     * @param projectId 页面配置管理ID 主键ID
     * @return 结果
     */
	public int deleteProjectPageObjectById(Integer projectId);
	
	/**
     * 批量删除页面配置管理
     * 
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectPageObjectByIds(String[] projectIds);
	
}