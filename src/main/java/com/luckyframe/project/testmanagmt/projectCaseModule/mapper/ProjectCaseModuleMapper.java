package com.luckyframe.project.testmanagmt.projectCaseModule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import org.springframework.stereotype.Component;

/**
 * 测试用例模块管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Component
public interface ProjectCaseModuleMapper 
{
	/**
     * 根据ID查询测试用例模块管理信息
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 测试用例模块管理信息
     */
	ProjectCaseModule selectProjectCaseModuleById(Integer moduleId);
	
	/**
     * 查询测试用例模块管理信息列表
     * 
     * @param parentId 测试用例模块管理父节点ID
     * @return 测试用例模块管理信息
     */
	List<ProjectCaseModule> selectProjectCaseModuleByParentId(Integer parentId);
	
	/**
     * 根据项目ID查询测试用例模块管理信息
     * 
     * @param projectId 项目ID
     * @return 测试用例模块管理信息
     */
	ProjectCaseModule selectProjectCaseModuleParentZeroByProjectId(Integer projectId);
	
	/**
     * 查询测试用例模块管理列表
     * 
     * @param projectCaseModule 测试用例模块管理对象
     * @return 测试用例模块管理集合
     */
	List<ProjectCaseModule> selectProjectCaseModuleList(ProjectCaseModule projectCaseModule);
	
	/**
     * 新增测试用例模块管理
     * 
     * @param projectCaseModule 测试用例模块管理对象
     * @return 结果
     */
	int insertProjectCaseModule(ProjectCaseModule projectCaseModule);
	
	/**
     * 修改测试用例模块管理
     * 
     * @param projectCaseModule 测试用例模块管理对象
     * @return 结果
     */
	int updateProjectCaseModule(ProjectCaseModule projectCaseModule);
	
	/**
     * 删除测试用例模块管理
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 结果
     */
	int deleteProjectCaseModuleById(Integer moduleId);
	
	/**
     * 批量删除测试用例模块管理
     * 
     * @param moduleIds 需要删除的数据ID数组
     * @return 结果
     */
	int deleteProjectCaseModuleByIds(String[] moduleIds);
	
	/**
	 * 根据projectIds批量删除测试用例模块管理
	 * @param projectIds 项目ID数组
	 * @author Seagull
	 * @date 2019年2月28日
	 */
	int deleteProjectCaseModuleByProjectIds(String[] projectIds);
	
    /**
     * 修改子元素关系
     * @param projectCaseModules 项目模块对象集合
     * @author Seagull
     * @date 2019年2月27日
     */
    int updateModuleChildren(@Param("modules") List<ProjectCaseModule> projectCaseModules);
    
    /**
     * 查询当前父节点下排序的最大数
     * @param moduleId 模块ID
     * @author Seagull
     * @date 2019年2月28日
     */
    int selectProjectCaseModuleMaxOrderNumByParentId(Integer moduleId);
    
    /**
     * 查询项目下有没有子模块
     * @param projectId 项目ID
     * @author Seagull
     * @date 2019年2月28日
     */
    int selectProjectCaseModuleCountByProjectId(Integer projectId);

	/**
	 * 精准查询测试用例模块管理列表
	 *
	 * @param projectCaseModule 测试用例模块管理信息
	 * @return 测试用例模块管理集合
	 * @author summer
	 * @date 2020年2月26日
	 */
	List<ProjectCaseModule> selectProjectCaseModuleListPrecise(ProjectCaseModule projectCaseModule);
    
}