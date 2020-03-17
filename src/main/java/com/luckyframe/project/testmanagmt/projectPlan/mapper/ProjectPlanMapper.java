package com.luckyframe.project.testmanagmt.projectPlan.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import org.springframework.stereotype.Component;

/**
 * 测试计划 数据层
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
@Component
public interface ProjectPlanMapper 
{
	/**
     * 查询测试计划信息
     * 
     * @param planId 测试计划ID
     * @return 测试计划信息
     */
	ProjectPlan selectProjectPlanById(Integer planId);
	
	/**
	 * 根据计划名称查询测试计划信息
	 * @param planName 测试计划名称
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	ProjectPlan selectProjectPlanByPlanName(String planName);
	
	/**
     * 查询测试计划列表
     * 
     * @param projectPlan 测试计划信息
     * @return 测试计划集合
     */
	List<ProjectPlan> selectProjectPlanList(ProjectPlan projectPlan);
	
	/**
     * 新增测试计划
     * 
     * @param projectPlan 测试计划信息
     * @return 结果
     */
	int insertProjectPlan(ProjectPlan projectPlan);
	
	/**
     * 修改测试计划
     * 
     * @param projectPlan 测试计划信息
     * @return 结果
     */
	int updateProjectPlan(ProjectPlan projectPlan);
	
	/**
     * 删除测试计划
     * 
     * @param planId 测试计划ID
     * @return 结果
     */
	int deleteProjectPlanById(Integer planId);
	
	/**
     * 批量删除测试计划
     * 
     * @param planIds 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectPlanByIds(String[] planIds);
	
	/**
	 * 检查测试计划名称唯一性
	 * @param planName 测试计划名称
	 * @author Seagull
	 * @date 2019年3月18日
	 */
	ProjectPlan checkProjectPlanNameUnique(String planName);
	
}