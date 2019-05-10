package com.luckyframe.project.testmanagmt.projectCase.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.mapper.ProjectCaseStepsMapper;

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

	@Autowired
	private IProjectCaseService projectCaseService;
	
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
	 * 通过用例ID以及步骤序号获取步骤实体
	 * @param projectCaseSteps
	 * @return
	 * @author Seagull
	 * @date 2019年4月25日
	 */
    @Override
	public ProjectCaseSteps selectProjectCaseStepsByCaseIdAndStepNum(ProjectCaseSteps projectCaseSteps)
	{
	    return projectCaseStepsMapper.selectProjectCaseStepsByCaseIdAndStepNum(projectCaseSteps);
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
		ProjectCase projectCase=projectCaseService.selectProjectCaseById(projectCaseSteps.getCaseId());
		projectCaseService.updateProjectCase(projectCase);
		
		projectCaseSteps.setCreateBy(ShiroUtils.getLoginName());
		projectCaseSteps.setCreateTime(new Date());
		projectCaseSteps.setUpdateBy(ShiroUtils.getLoginName());
		projectCaseSteps.setUpdateTime(new Date());
		
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
		projectCaseSteps.setUpdateBy(ShiroUtils.getLoginName());
		projectCaseSteps.setUpdateTime(new Date());
		
		ProjectCase projectCase=projectCaseService.selectProjectCaseById(projectCaseSteps.getCaseId());
		projectCaseService.updateProjectCase(projectCase);
		
	    return projectCaseStepsMapper.updateProjectCaseSteps(projectCaseSteps);
	}

	/**
     * 删除测试用例步骤管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProjectCaseStepsByIds(List<ProjectCaseSteps> listSteps)
	{
		int caseId=0;
		if(null!=listSteps&&listSteps.size()>0){
			if(null!=listSteps.get(0).getCaseId()){
				caseId=listSteps.get(0).getCaseId();
			}
		}
		return projectCaseStepsMapper.deleteProjectCaseStepsByCaseId(caseId);
	}
	
}
