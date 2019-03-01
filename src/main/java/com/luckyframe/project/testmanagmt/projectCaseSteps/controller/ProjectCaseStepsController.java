package com.luckyframe.project.testmanagmt.projectCaseSteps.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.testmanagmt.projectCaseSteps.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCaseSteps.service.IProjectCaseStepsService;

/**
 * 测试用例步骤管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/testmanagmt/projectCaseSteps")
public class ProjectCaseStepsController extends BaseController
{
    private String prefix = "testmanagmt/projectCaseSteps";
	
	@Autowired
	private IProjectCaseStepsService projectCaseStepsService;
	
	@RequiresPermissions("testmanagmt:projectCaseSteps:view")
	@GetMapping()
	public String projectCaseSteps()
	{
	    return prefix + "/projectCaseSteps";
	}
	
	/**
	 * 查询测试用例步骤管理列表
	 */
	@RequiresPermissions("testmanagmt:projectCaseSteps:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProjectCaseSteps projectCaseSteps)
	{
		startPage();
        List<ProjectCaseSteps> list = projectCaseStepsService.selectProjectCaseStepsList(projectCaseSteps);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出测试用例步骤管理列表
	 */
	@RequiresPermissions("testmanagmt:projectCaseSteps:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectCaseSteps projectCaseSteps)
    {
    	List<ProjectCaseSteps> list = projectCaseStepsService.selectProjectCaseStepsList(projectCaseSteps);
        ExcelUtil<ProjectCaseSteps> util = new ExcelUtil<ProjectCaseSteps>(ProjectCaseSteps.class);
        return util.exportExcel(list, "projectCaseSteps");
    }
	
	/**
	 * 新增测试用例步骤管理
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存测试用例步骤管理
	 */
	@RequiresPermissions("testmanagmt:projectCaseSteps:add")
	@Log(title = "测试用例步骤管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProjectCaseSteps projectCaseSteps)
	{		
		return toAjax(projectCaseStepsService.insertProjectCaseSteps(projectCaseSteps));
	}

	/**
	 * 修改测试用例步骤管理
	 */
	@GetMapping("/edit/{stepId}")
	public String edit(@PathVariable("stepId") Integer stepId, ModelMap mmap)
	{
		ProjectCaseSteps projectCaseSteps = projectCaseStepsService.selectProjectCaseStepsById(stepId);
		mmap.put("projectCaseSteps", projectCaseSteps);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存测试用例步骤管理
	 */
	@RequiresPermissions("testmanagmt:projectCaseSteps:edit")
	@Log(title = "测试用例步骤管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProjectCaseSteps projectCaseSteps)
	{		
		return toAjax(projectCaseStepsService.updateProjectCaseSteps(projectCaseSteps));
	}
	
	/**
	 * 删除测试用例步骤管理
	 */
	@RequiresPermissions("testmanagmt:projectCaseSteps:remove")
	@Log(title = "测试用例步骤管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(projectCaseStepsService.deleteProjectCaseStepsByIds(ids));
	}
	
}
