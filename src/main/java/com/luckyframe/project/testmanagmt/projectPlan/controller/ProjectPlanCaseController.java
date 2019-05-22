package com.luckyframe.project.testmanagmt.projectPlan.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import com.luckyframe.project.testmanagmt.projectCaseModule.service.IProjectCaseModuleService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlanCase;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanCaseService;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;

/**
 * 测试计划用例 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
@Controller
@RequestMapping("/testmanagmt/projectPlanCase")
public class ProjectPlanCaseController extends BaseController
{
    private String prefix = "testmanagmt/projectPlan";
	
	@Autowired
	private IProjectPlanService projectPlanService;
	
	@Autowired
	private IProjectPlanCaseService projectPlanCaseService;
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private IProjectCaseModuleService projectCaseModuleService;
	
	@RequiresPermissions("testmanagmt:projectPlan:view")
	@GetMapping("/{planId}")
	public String projectPlanCase(@PathVariable("planId") Integer planId, ModelMap mmap)
	{
		ProjectPlan projectPlan = projectPlanService.selectProjectPlanById(planId);
        ProjectCaseModule projectCaseModule = projectCaseModuleService.selectProjectCaseModuleParentZeroByProjectId(projectPlan.getProjectId());
        mmap.put("projectCaseModule", projectCaseModule);
	    return prefix + "/projectPlanCase";
	}
	
	/**
	 * 查询测试计划用例列表
	 */
	@RequiresPermissions("testmanagmt:projectPlan:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProjectCase projectCase)
	{
		if(StringUtils.isNotEmpty(projectCase.getModuleId())){
			ProjectCaseModule projectCaseModule = projectCaseModuleService.selectProjectCaseModuleById(projectCase.getModuleId());
			if(projectCaseModule.getParentId().equals(0)){
				projectCase.setModuleId(null);
			}
		}
		
		startPage();
		/**获取项目下的用例集合*/
		List<ProjectCase> projectCaseList = projectCaseService.selectProjectCaseListForPlan(projectCase);
		
		return getDataTable(projectCaseList);
	}
	
	/**
	 * 保存测试计划用例
	 */
	@RequiresPermissions("testmanagmt:projectPlan:edit")
	@Log(title = "测试计划用例", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/savePlanCase",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult savePlanCase(@RequestBody List<ProjectCase> projectCases)
	{
		int resultCount=0;
		int planId=0;
		
		if(projectCases.size()==0){
			return toAjax(1);
		}else{
			planId=projectCases.get(0).getPlanId();
		}
		
		ProjectPlan projectPlan = projectPlanService.selectProjectPlanById(planId);
		
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectPlan.getProjectId())){
			return error("没有此项目保存测试计划用例权限");
		}
		
		for(ProjectCase projectCase:projectCases){
			if(StringUtils.isNotEmpty(projectCase.getPlanCaseId())&&!projectCase.isFlag()){
				resultCount = resultCount+projectPlanCaseService.deleteProjectPlanCaseById(projectCase.getPlanCaseId());						
			}

			if(projectCase.isFlag()&&StringUtils.isEmpty(projectCase.getPlanCaseId())){
				ProjectPlanCase projectPlanCase = new ProjectPlanCase();
				projectPlanCase.setPlanId(projectCase.getPlanId());
				projectPlanCase.setCaseId(projectCase.getCaseId());
				projectPlanCase.setPriority(projectCase.getPriority());
				resultCount = resultCount+projectPlanCaseService.insertProjectPlanCase(projectPlanCase);
			}else{
				resultCount++;
			}
		}
		
		Integer planCaseCount=projectPlanCaseService.selectProjectPlanCaseCountByPlanId(planId);
		projectPlan.setPlanCaseCount(planCaseCount);
		projectPlanService.updateProjectPlan(projectPlan);
		
		return toAjax(resultCount);
	}
	
	/**
	 * 编辑测试计划用例优先级
	 */
	@RequiresPermissions("testmanagmt:projectPlan:edit")
	@Log(title = "测试计划用例", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit",method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public AjaxResult edit(@RequestBody ProjectCase projectCase)
	{
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCase.getProjectId())){
			return error("没有此项目保存计划用例优先级权限");
		}
		
		int resultCount=0;
		ProjectPlanCase projectPlanCase = new ProjectPlanCase();
		if(StringUtils.isEmpty(projectCase.getPlanCaseId())){
			projectPlanCase.setPlanId(projectCase.getPlanId());
			projectPlanCase.setCaseId(projectCase.getCaseId());
			projectPlanCase.setPriority(projectCase.getPriority());
			resultCount = projectPlanCaseService.insertProjectPlanCase(projectPlanCase);
			ProjectPlan projectPlan = projectPlanService.selectProjectPlanById(projectCase.getPlanId());
			projectPlanService.updateProjectPlan(projectPlan);
		}else{
			projectPlanCase.setPlanCaseId(projectCase.getPlanCaseId());
			projectPlanCase.setPlanId(projectCase.getPlanId());
			projectPlanCase.setCaseId(projectCase.getCaseId());
			projectPlanCase.setPriority(projectCase.getPriority());
			resultCount = projectPlanCaseService.updateProjectPlanCase(projectPlanCase);
		}
		
		return toAjax(resultCount);
	}
	
}
