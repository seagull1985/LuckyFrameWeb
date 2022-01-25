package com.luckyframe.project.testmanagmt.projectSuite.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testmanagmt.projectSuite.domain.ProjectSuite;
import com.luckyframe.project.testmanagmt.projectSuite.service.IprojectSuiteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author jerelli
 * @date 2021年1月5日
 */

@Controller
@RequestMapping("/testmanagmt/projectSuite")
public class ProjectSuiteController extends BaseController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IprojectSuiteService projectSuiteService;

    @RequiresPermissions("testmanagmt:projectSuite:view")
    @GetMapping()
    public String projectSuite(ModelMap mmap){
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
        return "testmanagmt/projectSuite/projectSuite";
    }

    /**
     * 查询聚合计划列表
     */
    @RequiresPermissions("testmanagmt:projectSuite:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProjectSuite projectSuite)
    {
        startPage();
        List<ProjectSuite> list = projectSuiteService.selectProjectSuiteList(projectSuite);
        return getDataTable(list);
    }

    /**
     * 新增聚合计划
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        }
        return "testmanagmt/projectSuite/add";
    }

    /**
     * 新增保存测试计划
     */
    @RequiresPermissions("testmanagmt:projectSuite:add")
    @Log(title = "聚合计划", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProjectSuite projectSuite)
    {
        if(!PermissionUtils.isProjectPermsPassByProjectId(projectSuite.getProjectId())){
            return error("没有此项目保存测试计划权限");
        }
        return toAjax(projectSuiteService.insertProjectSuite(projectSuite));
    }

    /**
     * 修改测试计划
     */
    @GetMapping("/edit/{suiteId}")
    public String edit(@PathVariable("suiteId") Integer suiteId, ModelMap mmap)
    {
        ProjectSuite projectSuite = projectSuiteService.selectProjectSuiteById(suiteId);
        mmap.put("projectSuite", projectSuite);
        return "testmanagmt/projectSuite/edit";
    }

    /**
     * 修改保存测试计划
     */
    @RequiresPermissions("testmanagmt:projectSuite:edit")
    @Log(title = "测试计划", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProjectSuite projectSuite)
    {
        if(!PermissionUtils.isProjectPermsPassByProjectId(projectSuite.getProjectId())){
            return error("没有此项目修改测试计划权限");
        }
        return toAjax(projectSuiteService.updateProjectSuite(projectSuite));
    }

    /**
     * 删除测试计划
     */
    @RequiresPermissions("testmanagmt:projectSuite:remove")
    @Log(title = "测试计划", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(projectSuiteService.deleteProjectSuiteByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
    }

    @PostMapping("/checkProjectSuiteNameUnique")
    @ResponseBody
    public String checkProjectSuiteNameUnique(ProjectSuite projectSuite)
    {
        return projectSuiteService.checkProjectSuiteNameUnique(projectSuite);
    }

    @GetMapping("/getProjectSuiteListByProjectId/{projectId}")
    @ResponseBody
    public String getProjectSuiteListByProjectId(@PathVariable("projectId") Integer projectId)
    {
        List<ProjectSuite> suiteList = projectSuiteService.selectProjectSuiteListByProjectId(projectId);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(suiteList));
        return jsonArray.toJSONString();
    }
}
