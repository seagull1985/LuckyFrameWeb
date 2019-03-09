package com.luckyframe.project.testmanagmt.projectProtocolTemplate.controller;

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
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectProtocolTemplate;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.domain.ProjectTemplateParams;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.service.IProjectProtocolTemplateService;
import com.luckyframe.project.testmanagmt.projectProtocolTemplate.service.IProjectTemplateParamsService;

/**
 * 协议模板管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-03-04
 */
@Controller
@RequestMapping("/testmanagmt/projectProtocolTemplate")
public class ProjectProtocolTemplateController extends BaseController
{
    private String prefix = "testmanagmt/projectProtocolTemplate";
	
	@Autowired
	private IProjectProtocolTemplateService projectProtocolTemplateService;
	
	@Autowired
	private IProjectTemplateParamsService projectTemplateParamsService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:view")
	@GetMapping()
	public String projectProtocolTemplate(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
	    return prefix + "/projectProtocolTemplate";
	}
	
	/**
	 * 查询协议模板管理列表
	 */
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProjectProtocolTemplate projectProtocolTemplate)
	{
		startPage();
        List<ProjectProtocolTemplate> list = projectProtocolTemplateService.selectProjectProtocolTemplateList(projectProtocolTemplate);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出协议模板管理列表
	 */
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectProtocolTemplate projectProtocolTemplate)
    {
    	List<ProjectProtocolTemplate> list = projectProtocolTemplateService.selectProjectProtocolTemplateList(projectProtocolTemplate);
        ExcelUtil<ProjectProtocolTemplate> util = new ExcelUtil<ProjectProtocolTemplate>(ProjectProtocolTemplate.class);
        return util.exportExcel(list, "projectProtocolTemplate");
    }
	
	/**
	 * 新增协议模板管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存协议模板管理
	 */
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:add")
	@Log(title = "协议模板管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProjectProtocolTemplate projectProtocolTemplate)
	{		
		return toAjax(projectProtocolTemplateService.insertProjectProtocolTemplate(projectProtocolTemplate));
	}
	
	/**
	 * 修改协议模板管理
	 */
	@GetMapping("/edit/{templateId}")
	public String edit(@PathVariable("templateId") Integer templateId, ModelMap mmap)
	{
		ProjectProtocolTemplate projectProtocolTemplate = projectProtocolTemplateService.selectProjectProtocolTemplateById(templateId);
		mmap.put("projectProtocolTemplate", projectProtocolTemplate);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存协议模板管理
	 */
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:edit")
	@Log(title = "协议模板管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProjectProtocolTemplate projectProtocolTemplate)
	{	
		return toAjax(projectProtocolTemplateService.updateProjectProtocolTemplate(projectProtocolTemplate));
	}
	
	/**
	 * 复制协议模板管理
	 * @param templateId
	 * @param mmap
	 * @return
	 * @author Seagull
	 * @date 2019年3月9日
	 */
	@GetMapping("/copy/{templateId}")
	public String copy(@PathVariable("templateId") Integer templateId, ModelMap mmap)
	{
		ProjectProtocolTemplate projectProtocolTemplate = projectProtocolTemplateService.selectProjectProtocolTemplateById(templateId);
		projectProtocolTemplate.setTemplateName("Copy【"+projectProtocolTemplate.getTemplateName()+"】");
		mmap.put("projectProtocolTemplate", projectProtocolTemplate);
        List<Project> projects=projectService.selectProjectAll(projectProtocolTemplate.getProjectId());
        mmap.put("projects", projects);
	    return prefix + "/copy";
	}
	
	/**
	 * 复制协议模板
	 * @param projectProtocolTemplate
	 * @return
	 * @author Seagull
	 * @date 2019年3月9日
	 */
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:add")
	@Log(title = "协议模板管理", businessType = BusinessType.INSERT)
	@PostMapping("/copy")
	@ResponseBody
	public AjaxResult copySave(ProjectProtocolTemplate projectProtocolTemplate)
	{
		ProjectTemplateParams projectTemplateParams = new ProjectTemplateParams();
		projectTemplateParams.setTemplateId(projectProtocolTemplate.getTemplateId());
		List<ProjectTemplateParams> templateParams = projectTemplateParamsService.selectProjectTemplateParamsList(projectTemplateParams);
		projectProtocolTemplate.setTemplateId(0);
		int num=projectProtocolTemplateService.insertProjectProtocolTemplate(projectProtocolTemplate);
		for(ProjectTemplateParams ptp:templateParams){
			ptp.setParamsId(0);
			ptp.setTemplateId(projectProtocolTemplate.getTemplateId());
			projectTemplateParamsService.insertProjectTemplateParams(ptp);
		}
		return toAjax(num+templateParams.size());
	}
	
	/**
	 * 删除协议模板管理
	 */
	@RequiresPermissions("testmanagmt:projectProtocolTemplate:remove")
	@Log(title = "协议模板管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		projectTemplateParamsService.deleteProjectTemplateParamsByTemplateId(ids);
		return toAjax(projectProtocolTemplateService.deleteProjectProtocolTemplateByIds(ids));
	}
	
	/**
	 * 校验协议模板名称在项目中的唯一性
	 * @param projectProtocolTemplate
	 * @return
	 * @author Seagull
	 * @date 2019年3月4日
	 */
    @PostMapping("/checkProjectProtocolTemplateNameUnique")
    @ResponseBody
    public String checkProjectProtocolTemplateNameUnique(ProjectProtocolTemplate projectProtocolTemplate)
    {
        return projectProtocolTemplateService.checkProjectProtocolTemplateNameUnique(projectProtocolTemplate);
    }
}