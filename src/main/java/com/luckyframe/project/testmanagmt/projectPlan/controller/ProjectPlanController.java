package com.luckyframe.project.testmanagmt.projectPlan.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;

/**
 * 测试计划 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-03-15
 */
@Controller
@RequestMapping("/testmanagmt/projectPlan")
public class ProjectPlanController extends BaseController
{
	@Autowired
	private IProjectPlanService projectPlanService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("testmanagmt:projectPlan:view")
	@GetMapping()
	public String projectPlan(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
        	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
	    return "testmanagmt/projectPlan/projectPlan";
	}
	
	/**
	 * 查询测试计划列表
	 */
	@RequiresPermissions("testmanagmt:projectPlan:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProjectPlan projectPlan)
	{
		startPage();
        List<ProjectPlan> list = projectPlanService.selectProjectPlanList(projectPlan);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出测试计划列表
	 */
	@RequiresPermissions("testmanagmt:projectPlan:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectPlan projectPlan)
    {
    	List<ProjectPlan> list = projectPlanService.selectProjectPlanList(projectPlan);
        ExcelUtil<ProjectPlan> util = new ExcelUtil<>(ProjectPlan.class);
        return util.exportExcel(list, "projectPlan");
    }
	
	/**
	 * 新增测试计划
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
        	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
	    return "testmanagmt/projectPlan/add";
	}
	
	/**
	 * 新增保存测试计划
	 */
	@RequiresPermissions("testmanagmt:projectPlan:add")
	@Log(title = "测试计划", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProjectPlan projectPlan)
	{		
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectPlan.getProjectId())){
			return error("没有此项目保存测试计划权限");
		}
		return toAjax(projectPlanService.insertProjectPlan(projectPlan));
	}

	/**
	 * 修改测试计划
	 */
	@GetMapping("/edit/{planId}")
	public String edit(@PathVariable("planId") Integer planId, ModelMap mmap)
	{
		ProjectPlan projectPlan = projectPlanService.selectProjectPlanById(planId);
		mmap.put("projectPlan", projectPlan);
	    return "testmanagmt/projectPlan/edit";
	}
	
	/**
	 * 修改保存测试计划
	 */
	@RequiresPermissions("testmanagmt:projectPlan:edit")
	@Log(title = "测试计划", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProjectPlan projectPlan)
	{		
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectPlan.getProjectId())){
			return error("没有此项目修改测试计划权限");
		}
		return toAjax(projectPlanService.updateProjectPlan(projectPlan));
	}
	
	/**
	 * 删除测试计划
	 */
	@RequiresPermissions("testmanagmt:projectPlan:remove")
	@Log(title = "测试计划", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
        try
        {
    		return toAjax(projectPlanService.deleteProjectPlanByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}
	
    /**
     * 校验测试计划名称唯一性
     * @param projectPlan 测试计划对象
     * @author Seagull
     * @date 2019年3月18日
     */
    @PostMapping("/checkProjectPlanNameUnique")
    @ResponseBody
    public String checkProjectPlanNameUnique(ProjectPlan projectPlan)
    {
        return projectPlanService.checkProjectPlanNameUnique(projectPlan);
    }
    
	/**
	 * 通过项目ID获取项目计划列表
	 * @param projectId 项目ID
	 * @author Seagull
	 * @date 2019年3月26日
	 */
    @GetMapping("/getProjectPlanListByProjectId/{projectId}")
	@ResponseBody
	public String getProjectPlanListByProjectId(@PathVariable("projectId") Integer projectId)
	{
    	List<ProjectPlan> planList = projectPlanService.selectProjectPlanListByProjectId(projectId);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(planList));
		return jsonArray.toJSONString();
	}
}
