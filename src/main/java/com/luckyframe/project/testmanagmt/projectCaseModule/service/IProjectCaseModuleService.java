package com.luckyframe.project.testmanagmt.projectCaseModule.service;

import java.util.List;
import java.util.Map;

import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;

/**
 * 测试用例模块管理 服务层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public interface IProjectCaseModuleService 
{
	/**
     * 查询测试用例模块管理信息
     * 
     * @param moduleId 测试用例模块管理ID
     * @return 测试用例模块管理信息
     */
	public ProjectCaseModule selectProjectCaseModuleById(Integer moduleId);
	
	/**
	 * 通过父级ID查询子列表
	 * @param parentId
	 * @return
	 * @author Seagull
	 * @date 2019年2月26日
	 */
	public List<ProjectCaseModule> selectProjectCaseModuleByParentId(Integer parentId);
	
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
     * 删除测试用例模块管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseModuleById(Integer moduleId);
	
	/**
	 * 查询用例模块管理树
	 * @param projectCaseModule
	 * @return
	 * @author Seagull
	 * @date 2019年2月26日
	 */
    public List<Map<String, Object>> selectProjectCaseModuleTree(ProjectCaseModule projectCaseModule);
    
    /**
     * 获取当前父节点下排序号
     * @param parentModuleId
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    public int selectProjectCaseModuleMaxOrderNumByParentId(Integer parentModuleId);
    
    /**
     * 通过项目ID获取用例模块的项目根节点实体
     * @param projectId
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    public ProjectCaseModule selectProjectCaseModuleParentZeroByProjectId(Integer projectId);
}
