package com.luckyframe.project.testmanagmt.projectPlan.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlanCase;
import org.springframework.stereotype.Component;

/**
 * 测试计划用例 数据层
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
@Component
public interface ProjectPlanCaseMapper 
{
	/**
     * 查询测试计划用例信息
     * 
     * @param planCaseId 测试计划用例ID
     * @return 测试计划用例信息
     */
	ProjectPlanCase selectProjectPlanCaseById(Integer planCaseId);
	
	/**
     * 查询测试计划用例列表
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 测试计划用例集合
     */
	List<ProjectPlanCase> selectProjectPlanCaseList(ProjectPlanCase projectPlanCase);
	
	/**
     * 新增测试计划用例
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 结果
     */
	int insertProjectPlanCase(ProjectPlanCase projectPlanCase);
	
	/**
     * 修改测试计划用例
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 结果
     */
	int updateProjectPlanCase(ProjectPlanCase projectPlanCase);
	
	/**
     * 删除测试计划用例
     * 
     * @param planCaseId 测试计划用例ID
     * @return 结果
     */
	int deleteProjectPlanCaseById(Integer planCaseId);
	
	/**
     * 批量删除测试计划用例
     * 
     * @param planCaseIds 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectPlanCaseByIds(String[] planCaseIds);
	
	/**
	 * 根据planId删除计划中所有用例
	 * @param planId 测试计划ID
	 * @author Seagull
	 * @date 2019年4月12日
	 */
	int deleteProjectPlanCaseByPlanId(Integer planId);
	
    /**
     * 查询该测试用例有没有绑定测试计划
     * @param caseId 用例ID
     * @author Seagull
     * @date 2019年4月12日
     */
    int selectProjectPlanCaseCountByCaseId(Integer caseId);
    
    /**
     * 查询测试计划下绑定用例总数
     * @param planId 测试计划ID
     * @author Seagull
     * @date 2019年5月13日
     */
    int selectProjectPlanCaseCountByPlanId(Integer planId);
}