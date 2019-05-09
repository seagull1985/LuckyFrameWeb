package com.luckyframe.project.testmanagmt.projectPlan.service;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;

/**
 * 测试计划 服务层
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
public interface IProjectPlanService 
{
	/**
     * 查询测试计划信息
     * 
     * @param planId 测试计划ID
     * @return 测试计划信息
     */
	public ProjectPlan selectProjectPlanById(Integer planId);
	
	/**
     * 根据计划名称查询测试计划信息
     * 
     * @param planId 测试计划ID
     * @return 测试计划信息
     */
	public ProjectPlan selectProjectPlanByPlanName(String planName);
	
	/**
     * 查询测试计划列表
     * 
     * @param projectPlan 测试计划信息
     * @return 测试计划集合
     */
	public List<ProjectPlan> selectProjectPlanList(ProjectPlan projectPlan);
	
	/**
	 * 根据项目ID查询测试计划列表
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年3月23日
	 */
	public List<ProjectPlan> selectProjectPlanListByProjectId(Integer projectId);
	
	/**
     * 新增测试计划
     * 
     * @param projectPlan 测试计划信息
     * @return 结果
     */
	public int insertProjectPlan(ProjectPlan projectPlan);
	
	/**
     * 修改测试计划
     * 
     * @param projectPlan 测试计划信息
     * @return 结果
     */
	public int updateProjectPlan(ProjectPlan projectPlan);
		
	/**
     * 删除测试计划信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProjectPlanByIds(String ids);
	
	/**
	 * 校验测试计划名称是否唯一
	 * @param projectPlan
	 * @return
	 * @author Seagull
	 * @date 2019年3月18日
	 */
    public String checkProjectPlanNameUnique(ProjectPlan projectPlan);
}
