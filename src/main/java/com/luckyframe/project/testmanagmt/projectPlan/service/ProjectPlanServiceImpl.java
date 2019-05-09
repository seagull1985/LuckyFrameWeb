package com.luckyframe.project.testmanagmt.projectPlan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.constant.ProjectPlanConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.support.Convert;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testexecution.taskScheduling.mapper.TaskSchedulingMapper;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.mapper.ProjectPlanCaseMapper;
import com.luckyframe.project.testmanagmt.projectPlan.mapper.ProjectPlanMapper;

/**
 * 测试计划 服务层实现
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
@Service
public class ProjectPlanServiceImpl implements IProjectPlanService 
{
	@Autowired
	private ProjectPlanMapper projectPlanMapper;
	
	@Autowired
	private ProjectPlanCaseMapper projectPlanCaseMapper;
	
	@Autowired
	private TaskSchedulingMapper taskSchedulingMapper;

	/**
     * 查询测试计划信息
     * 
     * @param planId 测试计划ID
     * @return 测试计划信息
     */
    @Override
	public ProjectPlan selectProjectPlanById(Integer planId)
	{
	    return projectPlanMapper.selectProjectPlanById(planId);
	}
    
	/**
     * 根据计划名称查询测试计划信息
     * 
     * @param planId 测试计划ID
     * @return 测试计划信息
     */
    @Override
	public ProjectPlan selectProjectPlanByPlanName(String planName)
	{
	    return projectPlanMapper.selectProjectPlanByPlanName(planName);
	}
	
	/**
     * 查询测试计划列表
     * 
     * @param projectPlan 测试计划信息
     * @return 测试计划集合
     */
	@Override
	public List<ProjectPlan> selectProjectPlanList(ProjectPlan projectPlan)
	{
	    return projectPlanMapper.selectProjectPlanList(projectPlan);
	}
	
	/**
	 * 根据项目ID查询测试计划列表
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年3月23日
	 */
	@Override
	public List<ProjectPlan> selectProjectPlanListByProjectId(Integer projectId)
	{
		ProjectPlan projectPlan = new ProjectPlan();
		projectPlan.setProjectId(projectId);
	    return projectPlanMapper.selectProjectPlanList(projectPlan);
	}
	
    /**
     * 新增测试计划
     * 
     * @param projectPlan 测试计划信息
     * @return 结果
     */
	@Override
	public int insertProjectPlan(ProjectPlan projectPlan)
	{
		projectPlan.setCreateBy(ShiroUtils.getLoginName());
		projectPlan.setCreateTime(new Date());
		projectPlan.setUpdateBy(ShiroUtils.getLoginName());
		projectPlan.setUpdateTime(new Date());
		
	    return projectPlanMapper.insertProjectPlan(projectPlan);
	}
	
	/**
     * 修改测试计划
     * 
     * @param projectPlan 测试计划信息
     * @return 结果
     */
	@Override
	public int updateProjectPlan(ProjectPlan projectPlan)
	{
		projectPlan.setUpdateBy(ShiroUtils.getLoginName());
		projectPlan.setUpdateTime(new Date());
		
	    return projectPlanMapper.updateProjectPlan(projectPlan);
	}

	/**
     * 删除测试计划对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectPlanByIds(String ids)
	{
		String[] planIds=Convert.toStrArray(ids);
		
		Integer result=0;
		for(String planIdStr:planIds){
			int planId=Integer.valueOf(planIdStr);
			ProjectPlan ProjectPlan = projectPlanMapper.selectProjectPlanById(planId);
			
			if(taskSchedulingMapper.selectTaskSchedulingCountByPlanId(planId)>0){
				throw new BusinessException(String.format("【%1$s】已绑定调度任务,不能删除", ProjectPlan.getPlanName()));
			}
			if(!PermissionUtils.isProjectPermsPassByProjectId(ProjectPlan.getProjectId())){	
				  throw new BusinessException(String.format("测试计划【%1$s】没有项目删除权限", ProjectPlan.getPlanName()));
			}
			
			projectPlanCaseMapper.deleteProjectPlanCaseByPlanId(planId);
			projectPlanMapper.deleteProjectPlanById(planId);
			result++;
		}
			
		return result;
	}
	
    /**
     * 校验测试用例名称是否唯一
     */
    @Override
    public String checkProjectPlanNameUnique(ProjectPlan projectPlan)
    {
        Long planId = StringUtils.isNull(projectPlan.getPlanId()) ? -1L : projectPlan.getPlanId();
        ProjectPlan info = projectPlanMapper.checkProjectPlanNameUnique(projectPlan.getPlanName());
        if (StringUtils.isNotNull(info) && info.getPlanId().longValue() != planId.longValue())
        {
            return ProjectPlanConstants.PROJECTPLAN_NAME_NOT_UNIQUE;
        }
        return ProjectPlanConstants.PROJECTPLAN_NAME_UNIQUE;
    }
    
}
