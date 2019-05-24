package com.luckyframe.project.testmanagmt.projectCase.controller;

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
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import com.luckyframe.project.testmanagmt.projectCaseModule.service.IProjectCaseModuleService;

/**
 * 项目测试用例管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/testmanagmt/projectCase")
public class ProjectCaseController extends BaseController
{
    private String prefix = "testmanagmt/projectCase";
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
	@Autowired
	private IProjectCaseStepsService projectCaseStepsService;
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IProjectCaseModuleService projectCaseModuleService;
	
	@RequiresPermissions("testmanagmt:projectCase:view")
	@GetMapping()
	public String projectCase(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(projects.size()>0){
        	ProjectCaseModule projectCaseModule = new ProjectCaseModule();
            if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
                projectCaseModule = projectCaseModuleService.selectProjectCaseModuleParentZeroByProjectId(ShiroUtils.getProjectId());
            }
        	mmap.put("projectCaseModule", projectCaseModule);
        }
        
	    return prefix + "/projectCase";
	}
	
	/**
	 * 查询项目测试用例管理列表
	 */
	@RequiresPermissions("testmanagmt:projectCase:list")
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
        List<ProjectCase> list = projectCaseService.selectProjectCaseList(projectCase);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出项目测试用例管理列表
	 */
	@RequiresPermissions("testmanagmt:projectCase:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectCase projectCase)
    {
		if(StringUtils.isNotEmpty(projectCase.getModuleId())){
			ProjectCaseModule projectCaseModule = projectCaseModuleService.selectProjectCaseModuleById(projectCase.getModuleId());
			if(projectCaseModule.getParentId().equals(0)){
				projectCase.setModuleId(null);
			}
		}
		
    	List<ProjectCase> list = projectCaseService.selectProjectCaseList(projectCase);
        ExcelUtil<ProjectCase> util = new ExcelUtil<ProjectCase>(ProjectCase.class);
        return util.exportExcel(list, "测试用例");
    }
	
	/**
	 * 新增项目测试用例管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(projects.size()>0){
            if(projects.size()>0){
            	ProjectCaseModule projectCaseModule = new ProjectCaseModule();
                if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
                	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
                    projectCaseModule = projectCaseModuleService.selectProjectCaseModuleParentZeroByProjectId(ShiroUtils.getProjectId());
                }else{
                	projectCaseModule = projectCaseModuleService.selectProjectCaseModuleParentZeroByProjectId(projects.get(0).getProjectId());
                }
            	mmap.put("projectCaseModule", projectCaseModule);
            }
        }
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存项目测试用例管理
	 */
	@RequiresPermissions("testmanagmt:projectCase:add")
	@Log(title = "项目测试用例管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProjectCase projectCase)
	{
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCase.getProjectId())){
			return error("没有此项目保存用例权限");
		}
		
		return toAjax(projectCaseService.insertProjectCase(projectCase));
	}

	/**
	 * 修改项目测试用例管理
	 */
	@GetMapping("/edit/{caseId}")
	public String edit(@PathVariable("caseId") Integer caseId, ModelMap mmap)
	{
		ProjectCase projectCase = projectCaseService.selectProjectCaseById(caseId);
		mmap.put("projectCase", projectCase);
    	mmap.put("projectCaseModule", projectCase.getProjectCaseModule());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存项目测试用例管理
	 */
	@RequiresPermissions("testmanagmt:projectCase:edit")
	@Log(title = "项目测试用例管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProjectCase projectCase)
	{		
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCase.getProjectId())){
			return error("没有此项目修改用例权限");
		}
		
		return toAjax(projectCaseService.updateProjectCase(projectCase));
	}
	
	/**
	 * 复制用例
	 * @param caseId
	 * @param mmap
	 * @return
	 * @author Seagull
	 * @date 2019年3月13日
	 */
	@GetMapping("/copy/{caseId}")
	public String copy(@PathVariable("caseId") Integer caseId, ModelMap mmap)
	{
		ProjectCase projectCase = projectCaseService.selectProjectCaseById(caseId);
		projectCase.setCaseName("Copy【"+projectCase.getCaseName()+"】");
        List<Project> projects=projectService.selectProjectAll(projectCase.getProjectId());
        mmap.put("projects", projects);
        if(projects.size()>0){
        	ProjectCaseModule projectCaseModule = projectCaseModuleService.selectProjectCaseModuleParentZeroByProjectId(projects.get(0).getProjectId());
        	mmap.put("projectCaseModule", projectCaseModule);
        }
		mmap.put("projectCase", projectCase);
		mmap.put("projectCaseModule", projectCase.getProjectCaseModule());
	    return prefix + "/copy";
	}
	
	/**
	 * 复制用例
	 * @param projectProtocolTemplate
	 * @return
	 * @author Seagull
	 * @date 2019年3月9日
	 */
	@RequiresPermissions("testmanagmt:projectCase:add")
	@Log(title = "项目测试用例管理", businessType = BusinessType.INSERT)
	@PostMapping("/copy")
	@ResponseBody
	public AjaxResult copySave(ProjectCase projectCase)
	{
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCase.getProjectId())){
			return error("没有此项目复制用例权限");
		}
		
		ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();
		projectCaseSteps.setCaseId(projectCase.getCaseId());
		List<ProjectCaseSteps> listSteps = projectCaseStepsService.selectProjectCaseStepsList(projectCaseSteps);
		projectCase.setCaseId(0);
		int num=projectCaseService.insertProjectCase(projectCase);
		for(ProjectCaseSteps step:listSteps){
			step.setStepId(0);
			step.setCaseId(projectCase.getCaseId());
			projectCaseStepsService.insertProjectCaseSteps(step);
		}
		return toAjax(num+listSteps.size());
	}
	
	/**
	 * 删除项目测试用例管理
	 */
	@RequiresPermissions("testmanagmt:projectCase:remove")
	@Log(title = "项目测试用例管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{	
        try
        {
        	return toAjax(projectCaseService.deleteProjectCaseByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}

    /**
     * 校验测试用例名称唯一性
     * @param projectCase
     * @return
     * @author Seagull
     * @date 2019年2月28日
     */
    @PostMapping("/checkProjectCaseNameUnique")
    @ResponseBody
    public String checkProjectCaseNameUnique(ProjectCase projectCase)
    {
        return projectCaseService.checkProjectCaseNameUnique(projectCase);
    }
}
