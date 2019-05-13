package com.luckyframe.project.testmanagmt.projectPlan.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlanCase;	

/**
 * 测试计划用例 数据层
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
public interface ProjectPlanCaseMapper 
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
     * 删除测试计划用例
     * 
     * @param planCaseId 测试计划用例ID
     * @return 结果
     */
	public int deleteProjectPlanCaseById(Integer planCaseId);
	
	/**
     * 批量删除测试计划用例
     * 
     * @param planCaseIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectPlanCaseByIds(String[] planCaseIds);
	
	/**
	 * 根据planId删除计划中所有用例
	 * @param planId
	 * @return
	 * @author Seagull
	 * @date 2019年4月12日
	 */
	public int deleteProjectPlanCaseByPlanId(Integer planId);
	
    /**
     * 查询该测试用例有没有绑定测试计划
     * @param caseId
     * @return
     * @author Seagull
     * @date 2019年4月12日
     */
    public int selectProjectPlanCaseCountByCaseId(Integer caseId);
    
    /**
     * 查询测试计划下绑定用例总数
     * @param caseId
     * @return
     * @author Seagull
     * @date 2019年5月13日
     */
    public int selectProjectPlanCaseCountByPlanId(Integer planId);
}