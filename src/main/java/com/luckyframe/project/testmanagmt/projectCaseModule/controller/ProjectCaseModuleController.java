package com.luckyframe.project.testmanagmt.projectCaseModule.controller;

import java.util.List;
import java.util.Map;

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
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.projectCaseModule.domain.ProjectCaseModule;
import com.luckyframe.project.testmanagmt.projectCaseModule.service.IProjectCaseModuleService;

/**
 * 测试用例模块管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-26
 */
@Controller
@RequestMapping("/testmanagmt/projectCaseModule")
public class ProjectCaseModuleController extends BaseController
{
    private String prefix = "testmanagmt/projectCaseModule";
	
	@Autowired
	private IProjectCaseModuleService projectCaseModuleService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("testmanagmt:projectCaseModule:view")
	@GetMapping()
	public String projectCaseModule(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
	    return prefix + "/projectCaseModule";
	}
	
	/**
	 * 查询测试用例模块管理列表
	 */
	@RequiresPermissions("testmanagmt:projectCaseModule:list")
	@GetMapping("/list")
	@ResponseBody
	public List<ProjectCaseModule> list(ProjectCaseModule projectCaseModule)
	{
        List<ProjectCaseModule> list = projectCaseModuleService.selectProjectCaseModuleList(projectCaseModule);
		return list;
	}
	
	
	/**
	 * 导出测试用例模块管理列表
	 */
	@RequiresPermissions("testmanagmt:projectCaseModule:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectCaseModule projectCaseModule)
    {
    	List<ProjectCaseModule> list = projectCaseModuleService.selectProjectCaseModuleList(projectCaseModule);
        ExcelUtil<ProjectCaseModule> util = new ExcelUtil<ProjectCaseModule>(ProjectCaseModule.class);
        return util.exportExcel(list, "projectCaseModule");
    }
	
	/**
	 * 新增测试用例模块管理
	 */
	@GetMapping("/add/{moduleId}")
	public String add(@PathVariable("moduleId") int moduleId, ModelMap mmap)
	{
		ProjectCaseModule projectCaseModule = projectCaseModuleService.selectProjectCaseModuleById(moduleId);
		mmap.put("projectCaseModule",projectCaseModule);
		mmap.put("projectId",projectCaseModule.getProjectId());
		mmap.put("maxOrderNum", projectCaseModuleService.selectProjectCaseModuleMaxOrderNumByParentId(moduleId));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存测试用例模块管理
	 */
	@RequiresPermissions("testmanagmt:projectCaseModule:add")
	@Log(title = "测试用例模块管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProjectCaseModule projectCaseModule)
	{
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCaseModule.getProjectId())){
			return error("没有此项目保存用例模块权限");
		}
		
		return toAjax(projectCaseModuleService.insertProjectCaseModule(projectCaseModule));
	}

	/**
	 * 修改测试用例模块管理
	 */
	@GetMapping("/edit/{moduleId}")
	public String edit(@PathVariable("moduleId") Integer moduleId, ModelMap mmap)
	{
		ProjectCaseModule projectCaseModule=projectCaseModuleService.selectProjectCaseModuleById(moduleId);
		mmap.put("projectCaseModule", projectCaseModule);
		mmap.put("parentProjectCaseModule", projectCaseModuleService.selectProjectCaseModuleById(projectCaseModule.getParentId()));
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存测试用例模块管理
	 */
	@RequiresPermissions("testmanagmt:projectCaseModule:edit")
	@Log(title = "测试用例模块管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProjectCaseModule projectCaseModule)
	{	
		if(!PermissionUtils.isProjectPermsPassByProjectId(projectCaseModule.getProjectId())){
			return error("没有此项目修改用例模块权限");
		}
		
		return toAjax(projectCaseModuleService.updateProjectCaseModule(projectCaseModule));
	}
	
	/**
	 * 删除测试用例模块管理
	 */
	@RequiresPermissions("testmanagmt:projectCaseModule:remove")
	@Log(title = "测试用例模块管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove/{moduleId}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("moduleId") Integer moduleId)
	{
        if (projectCaseModuleService.selectProjectCaseModuleByParentId(moduleId).size() > 0)
        {
            return error(1, "存在下级模块,不允许删除");
        }
        try
        {
        	return toAjax(projectCaseModuleService.deleteProjectCaseModuleById(moduleId));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}
	
    /**
     * 选择模块树
     * @param moduleId
     * @param mmap
     * @return
     * @author Seagull
     * @date 2019年2月26日
     */
    @GetMapping("/selectProjectCaseModuleTree/{moduleId}")
    public String selectProjectCaseModuleTree(@PathVariable("moduleId") Integer moduleId, ModelMap mmap)
    {
        mmap.put("projectCaseModule", projectCaseModuleService.selectProjectCaseModuleById(moduleId));
        return prefix + "/tree";
    }

    /**
     * 加载模块列表树
     */
    @GetMapping("/treeData/{projectId}")
    @ResponseBody
    public List<Map<String, Object>> treeData(@PathVariable("projectId") Integer projectId)
    {
    	ProjectCaseModule projectCaseModule=new ProjectCaseModule();
    	projectCaseModule.setProjectId(projectId);
        List<Map<String, Object>> tree = projectCaseModuleService.selectProjectCaseModuleTree(projectCaseModule);
        return tree;
    }

    /**
     * 根据项目ID获取根节点模块实体
     */
    @GetMapping("/getModuleByProjectId/{projectId}")
    @ResponseBody
    public ProjectCaseModule getModuleByProjectId(@PathVariable("projectId") Integer projectId)
    {
    	ProjectCaseModule projectCaseModule = projectCaseModuleService.selectProjectCaseModuleParentZeroByProjectId(projectId);
        return projectCaseModule;
    }
}
