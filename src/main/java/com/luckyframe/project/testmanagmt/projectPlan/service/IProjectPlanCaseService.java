package com.luckyframe.project.testmanagmt.projectPlan.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlanCase;

/**
 * 测试计划用例 服务层
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
public interface IProjectPlanCaseService 
{
	/**
     * 查询测试计划用例信息
     * 
     * @param planCaseId 测试计划用例ID
     * @return 测试计划用例信息
     */
	public ProjectPlanCase selectProjectPlanCaseById(Integer planCaseId);
	
	/**
     * 查询测试计划用例列表
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 测试计划用例集合
     */
	public List<ProjectPlanCase> selectProjectPlanCaseList(ProjectPlanCase projectPlanCase);
	
	/**
	 * 根据计划ID查询用例集合
	 * @param planId
	 * @return
	 * @author Seagull
	 * @date 2019年3月18日
	 */
	public List<ProjectPlanCase> selectProjectPlanCaseListByPlanId(Integer planId);
	
	/**
     * 新增测试计划用例
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 结果
     */
	public int insertProjectPlanCase(ProjectPlanCase projectPlanCase);
	
	/**
     * 修改测试计划用例
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 结果
     */
	public int updateProjectPlanCase(ProjectPlanCase projectPlanCase);
		
	/**
     * 删除测试计划用例信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectPlanCaseByIds(String[] planCaseIds);
	
	/**
	 * 删除单个测试计划用例对象
	 * @param planCaseId
	 * @return
	 * @author Seagull
	 * @date 2019年3月20日
	 */
	public int deleteProjectPlanCaseById(Integer planCaseId);
	
	/**
	 * 查询指定测试计划下绑定用例总数
	 * @param planId
	 * @return
	 * @author Seagull
	 * @date 2019年5月13日
	 */
	public Integer selectProjectPlanCaseCountByPlanId(Integer planId);
}
