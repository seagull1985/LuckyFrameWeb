package com.luckyframe.project.testmanagmt.projectCaseSteps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.support.Convert;
import com.luckyframe.project.testmanagmt.projectCaseSteps.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCaseSteps.mapper.ProjectCaseStepsMapper;

/**
 * 测试用例步骤管理 服务层实现
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Service
public class ProjectCaseStepsServiceImpl implements IProjectCaseStepsService 
{
	@Autowired
	private ProjectCaseStepsMapper projectCaseStepsMapper;

	/**
     * 查询测试用例步骤管理信息
     * 
     * @param stepId 测试用例步骤管理ID
     * @return 测试用例步骤管理信息
     */
    @Override
	public ProjectCaseSteps selectProjectCaseStepsById(Integer stepId)
	{
	    return projectCaseStepsMapper.selectProjectCaseStepsById(stepId);
	}
	
	/**
     * 查询测试用例步骤管理列表
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 测试用例步骤管理集合
     */
	@Override
	public List<ProjectCaseSteps> selectProjectCaseStepsList(ProjectCaseSteps projectCaseSteps)
	{
	    return projectCaseStepsMapper.selectProjectCaseStepsList(projectCaseSteps);
	}
	
    /**
     * 新增测试用例步骤管理
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 结果
     */
	@Override
	public int insertProjectCaseSteps(ProjectCaseSteps projectCaseSteps)
	{
	    return projectCaseStepsMapper.insertProjectCaseSteps(projectCaseSteps);
	}
	
	/**
     * 修改测试用例步骤管理
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 结果
     */
	@Override
	public int updateProjectCaseSteps(ProjectCaseSteps projectCaseSteps)
	{
	    return projectCaseStepsMapper.updateProjectCaseSteps(projectCaseSteps);
	}

	/**
     * 删除测试用例步骤管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectCaseStepsByIds(String ids)
	{
		return projectCaseStepsMapper.deleteProjectCaseStepsByIds(Convert.toStrArray(ids));
	}
	
}
