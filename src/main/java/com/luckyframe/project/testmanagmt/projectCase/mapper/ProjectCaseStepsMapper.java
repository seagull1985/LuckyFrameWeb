package com.luckyframe.project.testmanagmt.projectCase.mapper;

import java.util.List;

import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import org.springframework.stereotype.Component;

/**
 * 测试用例步骤管理 数据层
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Component
public interface ProjectCaseStepsMapper 
{
	/**
     * 查询测试用例步骤管理信息
     * 
     * @param stepId 测试用例步骤管理ID
     * @return 测试用例步骤管理信息
     */
	ProjectCaseSteps selectProjectCaseStepsById(Integer stepId);
	
	/**
	 * 通过用例ID以及步骤编号获取步骤实体
	 * @param projectCaseSteps 用例步骤对象
	 * @author Seagull
	 * @date 2019年4月25日
	 */
	ProjectCaseSteps selectProjectCaseStepsByCaseIdAndStepNum(ProjectCaseSteps projectCaseSteps);
	
	/**
     * 查询测试用例步骤管理列表
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 测试用例步骤管理集合
     */
	List<ProjectCaseSteps> selectProjectCaseStepsList(ProjectCaseSteps projectCaseSteps);
	
	/**
     * 新增测试用例步骤管理
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 结果
     */
	int insertProjectCaseSteps(ProjectCaseSteps projectCaseSteps);
	
	/**
     * 修改测试用例步骤管理
     * 
     * @param projectCaseSteps 测试用例步骤管理信息
     * @return 结果
     */
	int updateProjectCaseSteps(ProjectCaseSteps projectCaseSteps);
	
	/**
	 * 根据用例ID删除所有步骤
	 * @param caseId 测试用例ID
	 * @author Seagull
	 * @date 2019年3月13日
	 */
	int deleteProjectCaseStepsByCaseId(Integer caseId);
	
	/**
     * 批量删除测试用例步骤管理
     * 
     * @param stepIds 需要删除的数据ID
     * @return 结果
     */
	int deleteProjectCaseStepsByIds(String[] stepIds);
	
    /**
     * 根据模板ID查询关联的步骤数量
     * @param extend 模板ID
     * @author Seagull
     * @date 2019年4月12日
     */
    int selectProjectCaseStepsCountByTemplateId(Integer extend);
}