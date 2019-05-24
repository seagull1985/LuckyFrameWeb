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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.DateUtils;
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
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
        	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
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
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
        	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
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
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectProtocolTemplate.getProjectId())){
			return error("没有此项目保存协议模板权限");
		}
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
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectProtocolTemplate.getProjectId())){
			return error("没有此项目修改协议模板权限");
		}
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
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectProtocolTemplate.getProjectId())){
			return error("没有此项目复制协议模板权限");
		}
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
        try
        {
        	return toAjax(projectProtocolTemplateService.deleteProjectProtocolTemplateByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }		
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
    
	/**
	 * Ajax前台获取协议模板列表
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年3月12日
	 */
    @GetMapping("/getTemplateListByProjectId/{projectId}")
    @ResponseBody
	public String getTemplateListByProjectId(@PathVariable("projectId") Integer projectId) {
		String str = "{\"message\": \"\",\"value\": ,\"code\": 200,\"redirect\": \"\" }";
		try {
			JSONObject json = new JSONObject();
			ProjectProtocolTemplate projectProtocolTemplate=new ProjectProtocolTemplate();
			projectProtocolTemplate.setProjectId(projectId);
			List<ProjectProtocolTemplate> ptlist = projectProtocolTemplateService.selectProjectProtocolTemplateList(projectProtocolTemplate);
			JSONArray jsonarr = new JSONArray();
			for(ProjectProtocolTemplate obppt:ptlist){
				JSONObject jo = new JSONObject();
                jo.put("templateName", "【"+obppt.getTemplateId()+"】"+obppt.getTemplateName());
                jo.put("updateBy", obppt.getUpdateBy());                
                jo.put("updateTime", DateUtils.parseDateToStr("yy-MM-dd HH:mm:ss", obppt.getUpdateTime()));
                jsonarr.add(jo);
			}
			String recordJson = jsonarr.toString();
					
			json = JSONObject.parseObject("{\"message\": \"\",\"value\": "+recordJson+",\"code\": 200,\"redirect\": \"\" }");
			str=json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
