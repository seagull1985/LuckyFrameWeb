package com.luckyframe.project.testmanagmt.projectCaseSteps.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCaseSteps.domain.ProjectCaseSteps;

/**
 * 测试用例步骤管理 服务层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
public interface IProjectCaseStepsService 
{
	/**
     * 查询测试用例步骤管理信息
     * 
     * @param stepId 测试用例步骤管理ID
     * @return 测试用例步骤管理信息
     */
	public ProjectCaseSteps selectProjectCaseStepsById(Integer stepId);
	
	/**
     * 查询测试用例步骤管理列表
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 测试用例步骤管理集合
     */
	public List<ProjectCaseSteps> selectProjectCaseStepsList(ProjectCaseSteps projectCaseSteps);
	
	/**
     * 新增测试用例步骤管理
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 结果
     */
	public int insertProjectCaseSteps(ProjectCaseSteps projectCaseSteps);
	
	/**
     * 修改测试用例步骤管理
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 结果
     */
	public int updateProjectCaseSteps(ProjectCaseSteps projectCaseSteps);
		
	/**
     * 删除测试用例步骤管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectCaseStepsByIds(String ids);
	
}
