package com.luckyframe.project.testmanagmt.projectPageObject.controller;

import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.system.user.domain.User;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectPageObject.domain.ProjectPageObject;
import com.luckyframe.project.testmanagmt.projectPageObject.service.IProjectPageObjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面配置管理
 * @author YSS陈再兴
 * @date 2022-03-10
 */
@Controller
@RequestMapping("/testmanagmt/projectPageObject")
public class ProjectPageObjectController extends BaseController {
    private String prefix = "testmanagmt/projectPageObject";
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IProjectPageObjectService projectPageObjectService;
    @RequiresPermissions("testmanagmt:projectPageObject:view")
    @GetMapping()
    public String projectPageObject() {
        return prefix + "/projectPageObject";
    }

    /**
     * 查询页面配置管理列表
     */
    @RequiresPermissions("testmanagmt:projectPageObject:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProjectPageObject projectPageObject) {
        startPage();
        List<ProjectPageObject> list = projectPageObjectService.selectProjectPageObjectList(projectPageObject);
        List<Project> projects = projectService.selectProjectAll(0);
        Map<Integer,String> map=new HashMap<Integer,String>();
        for(Project p:projects){
            map.put(p.getProjectId(),p.getProjectName());
        }
        for(ProjectPageObject p:list){
            if(p.getProjectId()==null)continue;
            p.setProjectName(map.get(p.getProjectId()));
        }
        return getDataTable(list);
    }


    /**
     * 导出页面配置管理列表
     */
    @RequiresPermissions("testmanagmt:projectPageObject:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectPageObject projectPageObject) {
        List<ProjectPageObject> list = projectPageObjectService.selectProjectPageObjectList(projectPageObject);
        ExcelUtil<ProjectPageObject> util = new ExcelUtil<ProjectPageObject>(ProjectPageObject.class);
        return util.exportExcel(list, "projectPageObject");
    }

    /**
     * 新增页面配置管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<Project> projects = projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
        return prefix + "/add";
    }

    /**
     * 新增保存页面配置管理
     */
    @RequiresPermissions("testmanagmt:projectPageObject:add")
    @Log(title = "页面配置管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProjectPageObject projectPageObject) {
        if (checkExist(projectPageObject)) {
            return AjaxResult.error(200,"页面已经存在!");
        }
        User currentUser = ShiroUtils.getSysUser();
        projectPageObject.setCreateBy(currentUser.getUserName());
        projectPageObject.setUpdateBy(currentUser.getUserName());
        projectPageObject.setCreateTime(new Date());
        projectPageObject.setUpdateTime(new Date());
        return toAjax(projectPageObjectService.insertProjectPageObject(projectPageObject) > 0);
    }

    /**
     * 修改页面配置管理
     */
    @GetMapping("/edit/{projectId}")
    public String edit(@PathVariable("projectId") Integer projectId, ModelMap mmap) {
        List<Project> projects = projectService.selectProjectAll(0);
        Map<Integer,String> map=new HashMap<Integer,String>();
        for(Project p:projects){
            map.put(p.getProjectId(),p.getProjectName());
        }
        ProjectPageObject projectPageObject = projectPageObjectService.selectProjectPageObjectById(projectId);
        projectPageObject.setProjectName(map.get(projectPageObject.getProjectId()));
        mmap.put("projectPageObject", projectPageObject);
        mmap.put("projects", projects);
        return prefix + "/edit";
    }

    /**
     * 修改保存页面配置管理
     */
    @RequiresPermissions("testmanagmt:projectPageObject:edit")
    @Log(title = "页面配置管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProjectPageObject projectPageObject) {
        if (checkExist(projectPageObject)) {
            return AjaxResult.error(200,"页面已经存在!");
        }
        User currentUser = ShiroUtils.getSysUser();
        projectPageObject.setUpdateTime(new Date());
        projectPageObject.setUpdateBy(currentUser.getUserName());
        return toAjax(projectPageObjectService.updateProjectPageObject(projectPageObject) > 0);
    }

    /**
     * 删除页面配置管理
     */
    @RequiresPermissions("testmanagmt:projectPageObject:remove")
    @Log(title = "页面配置管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try
        {
            return toAjax(projectPageObjectService.deleteProjectPageObjectByIds(ids) > 0);
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
    }

    public boolean checkExist(ProjectPageObject projectPageObject) {
        ProjectPageObject check = new ProjectPageObject();
        check.setPageName(projectPageObject.getPageName());
        List<ProjectPageObject> list = projectPageObjectService.selectProjectPageObjectList(check);
        //新增
        if (projectPageObject.getPageId() == null) {
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else
        //修改
        {
            check.setPageId(null);
            List<ProjectPageObject> temp = projectPageObjectService.selectProjectPageObjectList(check);
            for(ProjectPageObject ppo:temp){
                if(ppo.getPageId()==projectPageObject.getPageId())continue;
                if(ppo.getPageName().equals(projectPageObject.getPageName())){
                    return true;
                }
            }
            return false;
        }
    }

    @PostMapping("/getAllPageObject")
    @ResponseBody
    public List<ProjectPageObject> getAllPageObject(@RequestBody ProjectCaseSteps projectCaseSteps) {
        List<ProjectPageObject> ppolist = projectPageObjectService.getAllPageObject2(projectCaseSteps.getProjectId());
        return ppolist;
    }
}
