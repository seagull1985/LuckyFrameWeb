package com.luckyframe.project.testmanagmt.projectCase.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;

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
	 * 通过用例ID以及步骤序号获取步骤实体
	 * @param projectCaseSteps
	 * @return
	 * @author Seagull
	 * @date 2019年4月25日
	 */
	public ProjectCaseSteps selectProjectCaseStepsByCaseIdAndStepNum(ProjectCaseSteps projectCaseSteps);
	
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
	 * 删除单条用例中的所有步骤
	 * @param listSteps
	 * @return
	 * @author Seagull
	 * @date 2019年3月13日
	 */
	public int deleteProjectCaseStepsByIds(List<ProjectCaseSteps> listSteps);
	
}
