package com.luckyframe.project.testmanagmt.projectPlan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlanCase;
import com.luckyframe.project.testmanagmt.projectPlan.mapper.ProjectPlanCaseMapper;
import com.luckyframe.project.testmanagmt.projectPlan.mapper.ProjectPlanMapper;

/**
 * 测试计划用例 服务层实现
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
@Service
public class ProjectPlanCaseServiceImpl implements IProjectPlanCaseService 
{
	@Autowired
	private ProjectPlanCaseMapper projectPlanCaseMapper;
	
	@Autowired
	private ProjectPlanMapper projectPlanMapper;

	/**
     * 查询测试计划用例信息
     * 
     * @param planCaseId 测试计划用例ID
     * @return 测试计划用例信息
     */
    @Override
	public ProjectPlanCase selectProjectPlanCaseById(Integer planCaseId)
	{
	    return projectPlanCaseMapper.selectProjectPlanCaseById(planCaseId);
	}
	
	/**
     * 查询测试计划用例列表
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 测试计划用例集合
     */
	@Override
	public List<ProjectPlanCase> selectProjectPlanCaseList(ProjectPlanCase projectPlanCase)
	{
	    return projectPlanCaseMapper.selectProjectPlanCaseList(projectPlanCase);
	}
	
	/**
	 * 根据计划ID查询用例集合
	 * @param planId 测试计划ID
	 * @author Seagull
	 * @date 2019年3月18日
	 */
	@Override
	public List<ProjectPlanCase> selectProjectPlanCaseListByPlanId(Integer planId)
	{
		ProjectPlanCase projectPlanCase = new ProjectPlanCase();
		projectPlanCase.setPlanId(planId);
	    return projectPlanCaseMapper.selectProjectPlanCaseList(projectPlanCase);
	}
	
    /**
     * 新增测试计划用例
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 结果
     */
	@Override
	public int insertProjectPlanCase(ProjectPlanCase projectPlanCase)
	{
	    return projectPlanCaseMapper.insertProjectPlanCase(projectPlanCase);
	}
	
	/**
     * 修改测试计划用例
     * 
     * @param projectPlanCase 测试计划用例信息
     * @return 结果
     */
	@Override
	public int updateProjectPlanCase(ProjectPlanCase projectPlanCase)
	{
		ProjectPlan projectPlan = projectPlanMapper.selectProjectPlanById(projectPlanCase.getPlanId());
		projectPlan.setUpdateBy(ShiroUtils.getLoginName());
		projectPlan.setUpdateTime(new Date());
	    projectPlanMapper.updateProjectPlan(projectPlan);
		
	    return projectPlanCaseMapper.updateProjectPlanCase(projectPlanCase);
	}

	/**
     * 删除测试计划用例对象
     * 
     * @param planCaseIds 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectPlanCaseByIds(String[] planCaseIds)
	{
		return projectPlanCaseMapper.deleteProjectPlanCaseByIds(planCaseIds);
	}
	
	/**
	 * 删除单个测试计划用例对象
	 * @param planCaseId 计划用例ID
	 * @author Seagull
	 * @date 2019年3月20日
	 */
	@Override
	public int deleteProjectPlanCaseById(Integer planCaseId)
	{
		return projectPlanCaseMapper.deleteProjectPlanCaseById(planCaseId);
	}
	
	/**
	 * 查询指定测试计划下绑定用例总数
	 * @param planId 测试计划ID
	 * @author Seagull
	 * @date 2019年5月13日
	 */
	@Override
	public Integer selectProjectPlanCaseCountByPlanId(Integer planId)
	{
	    return projectPlanCaseMapper.selectProjectPlanCaseCountByPlanId(planId);
	}
}
