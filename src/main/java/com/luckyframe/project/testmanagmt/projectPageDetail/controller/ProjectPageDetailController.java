package com.luckyframe.project.testmanagmt.projectPageDetail.controller;

import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.user.domain.User;
import com.luckyframe.project.testmanagmt.projectPageDetail.domain.ProjectPageDetail;
import com.luckyframe.project.testmanagmt.projectPageDetail.service.IProjectPageDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 页面详情 信息操作处理
 * @author YSS陈再兴
 * @date 2022-03-10
 */
@Controller
@RequestMapping("/testmanagmt/projectPageDetail")
public class ProjectPageDetailController extends BaseController {
    private String prefix = "testmanagmt/projectPageDetail";

    @Autowired
    private IProjectPageDetailService projectPageDetailService;

    @RequiresPermissions("testmanagmt:projectPageDetail:view")
    @GetMapping()
    public String projectPageDetail() {
        return prefix + "/projectPageDetail";
    }

    /**
     * 查询页面详情列表
     */
    @RequiresPermissions("testmanagmt:projectPageDetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProjectPageDetail projectPageDetail) {
        startPage();
        List<ProjectPageDetail> list = projectPageDetailService.selectProjectPageDetailList(projectPageDetail);
        return getDataTable(list);
    }


    /**
     * 导出页面详情列表
     */
    @RequiresPermissions("testmanagmt:projectPageDetail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectPageDetail projectPageDetail) {
        List<ProjectPageDetail> list = projectPageDetailService.selectProjectPageDetailList(projectPageDetail);
        ExcelUtil<ProjectPageDetail> util = new ExcelUtil<ProjectPageDetail>(ProjectPageDetail.class);
        return util.exportExcel(list, "projectPageDetail");
    }

    /**
     * 新增页面详情
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Integer id, ModelMap mmap) {
        ProjectPageDetail projectPageDetail = new ProjectPageDetail();
        projectPageDetail.setPageId(id);
        mmap.put("projectPageDetail", projectPageDetail);
        return prefix + "/add";
    }

    /**
     * 新增保存页面详情
     */
    @RequiresPermissions("testmanagmt:projectPageDetail:add")
    @Log(title = "页面详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProjectPageDetail projectPageDetail) {

        if (checkExist(projectPageDetail)) {
            return toAjax(false);
        }
        User currentUser = ShiroUtils.getSysUser();
        projectPageDetail.setUpdateTime(new Date());
        projectPageDetail.setCreateTime(new Date());
        projectPageDetail.setCreateBy(currentUser.getUserName());
        projectPageDetail.setUpdateBy(currentUser.getUserName());
        return toAjax(projectPageDetailService.insertProjectPageDetail(projectPageDetail) > 0);
    }

    /**
     * 修改页面详情
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ProjectPageDetail projectPageDetail = new ProjectPageDetail();
        projectPageDetail.setPageId(id);
        List<ProjectPageDetail> list = projectPageDetailService.selectProjectPageDetailList(projectPageDetail);
        mmap.put("projectPageDetail", projectPageDetail);
        return prefix + "/projectPageDetail";
    }

    /**
     * 修改页面详情
     */
    @GetMapping("/editdetail/{id}")
    public String editDetail(@PathVariable("id") Integer id, ModelMap mmap) {
        ProjectPageDetail projectPageDetail = projectPageDetailService.selectProjectPageDetailById(id);
        mmap.put("projectPageDetail", projectPageDetail);
        return prefix + "/edit";
    }

    @GetMapping("/list/{id}")
    public String getListByPageId(@PathVariable("id") Integer id, ModelMap mmap) {
        startPage();
        ProjectPageDetail projectPageDetail = projectPageDetailService.selectProjectPageDetailById(id);
        List<ProjectPageDetail> list = projectPageDetailService.selectProjectPageDetailList(projectPageDetail);
        mmap.put("projectPageDetail", list);
        return prefix + "/edit";
    }

    /**
     * 修改保存页面详情
     */
    @RequiresPermissions("testmanagmt:projectPageDetail:edit")
    @Log(title = "页面详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProjectPageDetail projectPageDetail) {
        if (checkExist(projectPageDetail)) {
            return toAjax(false);
        }
        User currentUser = ShiroUtils.getSysUser();
        projectPageDetail.setUpdateTime(new Date());
        projectPageDetail.setUpdateBy(currentUser.getUserName());
        return toAjax(projectPageDetailService.updateProjectPageDetail(projectPageDetail) > 0);
    }

    /**
     * 删除页面详情
     */
    @RequiresPermissions("testmanagmt:projectPageDetail:remove")
    @Log(title = "页面详情", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try
        {
            return toAjax(projectPageDetailService.deleteProjectPageDetailByIds(ids) > 0);
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
    }


    public boolean checkExist(ProjectPageDetail projectPageDetail) {
        //新增或者修改的目标值不能存在一样
        ProjectPageDetail check = new ProjectPageDetail();
        check.setPageId(projectPageDetail.getPageId());
        check.setElement(projectPageDetail.getElement().trim());
        List<ProjectPageDetail> list = projectPageDetailService.selectProjectPageDetailList(check);
        //新增
        if (projectPageDetail.getId() == null) {
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else
        //修改
        {
            List<ProjectPageDetail> temp = projectPageDetailService.selectProjectPageDetailList(check);
            check.setPageId(null);
            for (ProjectPageDetail ppd : temp) {
                if(ppd.getId()==projectPageDetail.getId())continue;
                if (ppd.getElement().equals(projectPageDetail.getElement())) {
                    return true;
                }
            }
            return false;
        }
    }
}
