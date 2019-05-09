package com.luckyframe.project.testmanagmt.projectCaseModule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;	

/**
 * 测试用例模块管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public interface ProjectCaseModuleMapper 
{
	/**
     * 查询测试用例模块管理信息
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 测试用例模块管理信息
     */
	public ProjectCaseModule selectProjectCaseModuleById(Integer moduleId);
	
	/**
     * 查询测试用例模块管理信息
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 测试用例模块管理信息
     */
	public List<ProjectCaseModule> selectProjectCaseModuleByParentId(Integer parentId);
	
	/**
     * 查询测试用例模块管理信息
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 测试用例模块管理信息
     */
	public ProjectCaseModule selectProjectCaseModuleParentZeroByProjectId(Integer projectId);
	
	/**
     * 查询测试用例模块管理列表
     * 
     * @param projectCaseModule 测试用例模块管理信息
     * @return 测试用例模块管理集合
     */
	public List<ProjectCaseModule> selectProjectCaseModuleList(ProjectCaseModule projectCaseModule);
	
	/**
     * 新增测试用例模块管理
     * 
     * @param projectCaseModule 测试用例模块管理信息
     * @return 结果
     */
	public int insertProjectCaseModule(ProjectCaseModule projectCaseModule);
	
	/**
     * 修改测试用例模块管理
     * 
     * @param projectCaseModule 测试用例模块管理信息
     * @return 结果
     */
	public int updateProjectCaseModule(ProjectCaseModule projectCaseModule);
	
	/**
     * 删除测试用例模块管理
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 结果
     */
	public int deleteProjectCaseModuleById(Integer moduleId);
	
	/**
     * 批量删除测试用例模块管理
     * 
     * @param moduleIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseModuleByIds(String[] moduleIds);
	
	/**
	 * 根据projectIds批量删除测试用例模块管理
	 * @param projectIds
	 * @return
	 * @author Seagull
	 * @date 2019年2月28日
	 */
	public int deleteProjectCaseModuleByProjectIds(String[] projectIds);
	
    /**
     * 修改子元素关系
     * @param projectCaseModules
     * @return
     * @author Seagull
     * @date 2019年2月27日
     */
    public int updateModuleChildren(@Param("modules") List<ProjectCaseModule> projectCaseModules);
    
    /**
     * 查询当前父节点下排序的最大数
     * @param moduleId
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    public int selectProjectCaseModuleMaxOrderNumByParentId(Integer moduleId);
    
    /**
     * 查询项目下有没有子模块
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    public int selectProjectCaseModuleCountByProjectId(Integer projectId);
    
}