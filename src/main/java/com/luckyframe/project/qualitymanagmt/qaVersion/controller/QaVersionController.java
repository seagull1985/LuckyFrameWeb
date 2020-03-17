package com.luckyframe.project.qualitymanagmt.qaVersion.controller;

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

import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.qualitymanagmt.qaVersion.domain.QaVersion;
import com.luckyframe.project.qualitymanagmt.qaVersion.service.IQaVersionService;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;

/**
 * 质量管理-版本管理 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-08-05
 */
@Controller
@RequestMapping("/qualitymanagmt/qaVersion")
public class QaVersionController extends BaseController
{
	@Autowired
	private IQaVersionService qaVersionService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("qualitymanagmt:qaVersion:view")
	@GetMapping()
	public String qaVersion(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(projects.size()>0){
            if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
            }
        }
        
	    return "qualitymanagmt/qaVersion/qaVersion";
	}
	
	/**
	 * 查询质量管理-版本管理列表
	 */
	@RequiresPermissions("qualitymanagmt:qaVersion:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(QaVersion qaVersion)
	{
		startPage();
        List<QaVersion> list = qaVersionService.selectQaVersionList(qaVersion);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出质量管理-版本管理列表
	 */
	@RequiresPermissions("qualitymanagmt:qaVersion:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QaVersion qaVersion)
    {
    	List<QaVersion> list = qaVersionService.selectQaVersionList(qaVersion);
        ExcelUtil<QaVersion> util = new ExcelUtil<>(QaVersion.class);
        return util.exportExcel(list, "qaVersion");
    }
	
	/**
	 * 新增质量管理-版本管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		List<Project> projects = projectService.selectProjectAll(0);
		mmap.put("projects", projects);
		if (projects.size() > 0) {
			if (StringUtils.isNotEmpty(ShiroUtils.getProjectId())) {
				mmap.put("defaultProjectId", ShiroUtils.getProjectId());
			}
		}
		
	    return "qualitymanagmt/qaVersion/add";
	}
	
	/**
	 * 新增保存质量管理-版本管理
	 */
	@RequiresPermissions("qualitymanagmt:qaVersion:add")
	@Log(title = "质量管理-版本管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(QaVersion qaVersion)
	{		
		return toAjax(qaVersionService.insertQaVersion(qaVersion));
	}

	/**
	 * 修改质量管理-版本管理
	 */
	@GetMapping("/edit/{versionId}")
	public String edit(@PathVariable("versionId") Integer versionId, ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
		QaVersion qaVersion = qaVersionService.selectQaVersionById(versionId);
		mmap.put("qaVersion", qaVersion);
	    return "qualitymanagmt/qaVersion/edit";
	}
	
	/**
	 * 修改保存质量管理-版本管理
	 */
	@RequiresPermissions("qualitymanagmt:qaVersion:edit")
	@Log(title = "质量管理-版本管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(QaVersion qaVersion)
	{		
		return toAjax(qaVersionService.updateQaVersion(qaVersion));
	}
	
	/**
	 * 展示版本详细信息
	 */
	@GetMapping("/showVersionDetail/{versionId}")
	public String showVersionDetail(@PathVariable("versionId") Integer versionId, ModelMap mmap)
	{
		QaVersion qaVersion = qaVersionService.selectQaVersionById(versionId);
		mmap.put("qaVersion", qaVersion);
	    return "qualitymanagmt/qaVersion/detail";
	}
	
	/**
	 * 删除质量管理-版本管理
	 */
	@RequiresPermissions("qualitymanagmt:qaVersion:remove")
	@Log(title = "质量管理-版本管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(qaVersionService.deleteQaVersionByIds(ids));
	}
	
}
