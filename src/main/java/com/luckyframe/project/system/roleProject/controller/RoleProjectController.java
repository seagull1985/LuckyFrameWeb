package com.luckyframe.project.system.roleProject.controller;

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
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.project.system.roleProject.domain.RoleProject;
import com.luckyframe.project.system.roleProject.service.IRoleProjectService;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.common.utils.poi.ExcelUtil;

/**
 * 角色和项目关联 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-02-13
 */
@Controller
@RequestMapping("/system/roleProject")
public class RoleProjectController extends BaseController
{
    private String prefix = "system/roleProject";
	
	@Autowired
	private IRoleProjectService roleProjectService;
	
	@RequiresPermissions("system:roleProject:view")
	@GetMapping()
	public String roleProject()
	{
	    return prefix + "/roleProject";
	}
	
	/**
	 * 查询角色和项目关联列表
	 */
	@RequiresPermissions("system:roleProject:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(RoleProject roleProject)
	{
		startPage();
        List<RoleProject> list = roleProjectService.selectRoleProjectList(roleProject);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出角色和项目关联列表
	 */
	@RequiresPermissions("system:roleProject:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RoleProject roleProject)
    {
    	List<RoleProject> list = roleProjectService.selectRoleProjectList(roleProject);
        ExcelUtil<RoleProject> util = new ExcelUtil<RoleProject>(RoleProject.class);
        return util.exportExcel(list, "roleProject");
    }
	
	/**
	 * 新增角色和项目关联
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存角色和项目关联
	 */
	@RequiresPermissions("system:roleProject:add")
	@Log(title = "角色和项目关联", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(RoleProject roleProject)
	{		
		return toAjax(roleProjectService.insertRoleProject(roleProject));
	}

	/**
	 * 修改角色和项目关联
	 */
	@GetMapping("/edit/{roleId}")
	public String edit(@PathVariable("roleId") Integer roleId, ModelMap mmap)
	{
		RoleProject roleProject = roleProjectService.selectRoleProjectById(roleId);
		mmap.put("roleProject", roleProject);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存角色和项目关联
	 */
	@RequiresPermissions("system:roleProject:edit")
	@Log(title = "角色和项目关联", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(RoleProject roleProject)
	{		
		return toAjax(roleProjectService.updateRoleProject(roleProject));
	}
	
	/**
	 * 删除角色和项目关联
	 */
	@RequiresPermissions("system:roleProject:remove")
	@Log(title = "角色和项目关联", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(roleProjectService.deleteRoleProjectByIds(ids));
	}
	
}
