package com.luckyframe.project.testexecution.taskExecute.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;

/**
 * 测试任务执行 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
@Controller
@RequestMapping("/testexecution/taskExecute")
public class TaskExecuteController extends BaseController
{
    private String prefix = "testexecution/taskExecute";
	
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private ITaskSchedulingService taskSchedulingService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("testexecution:taskExecute:view")
	@GetMapping()
	public String taskExecute(HttpServletRequest req, ModelMap mmap)
	{
		String schedulingIdStr = req.getParameter("schedulingId");
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        List<TaskScheduling> schedulings = taskSchedulingService.selectTaskSchedulingList(new TaskScheduling());
        mmap.put("schedulings", schedulings);
        if(StringUtils.isNotEmpty(schedulingIdStr)){
        	Integer schedulingId = Integer.valueOf(schedulingIdStr);
        	if(StringUtils.isNotEmpty(schedulingId)){
            	TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(schedulingId);
            	mmap.put("defaultProjectId", taskScheduling.getProjectId());
            	mmap.put("defaultSchedulingId", schedulingId);
        	}
        }else{
        	if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
            }
        }
	    return prefix + "/taskExecute";
	}
	
	/**
	 * 查询测试任务执行列表
	 */
	@RequiresPermissions("testexecution:taskExecute:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TaskExecute taskExecute)
	{
		startPage();
        List<TaskExecute> list = taskExecuteService.selectTaskExecuteList(taskExecute);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出测试任务执行列表
	 */
	@RequiresPermissions("testexecution:taskExecute:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskExecute taskExecute)
    {
    	List<TaskExecute> list = taskExecuteService.selectTaskExecuteList(taskExecute);
        ExcelUtil<TaskExecute> util = new ExcelUtil<TaskExecute>(TaskExecute.class);
        return util.exportExcel(list, "taskExecute");
    }
	
	/**
	 * 新增测试任务执行
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存测试任务执行
	 */
	@RequiresPermissions("testexecution:taskExecute:add")
	@Log(title = "测试任务执行", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TaskExecute taskExecute)
	{		
		return toAjax(taskExecuteService.insertTaskExecute(taskExecute));
	}

	/**
	 * 修改测试任务执行
	 */
	@GetMapping("/edit/{taskId}")
	public String edit(@PathVariable("taskId") Integer taskId, ModelMap mmap)
	{
		TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);
		mmap.put("taskExecute", taskExecute);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存测试任务执行
	 */
	@RequiresPermissions("testexecution:taskExecute:edit")
	@Log(title = "测试任务执行", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TaskExecute taskExecute)
	{		
		return toAjax(taskExecuteService.updateTaskExecute(taskExecute));
	}
	
	/**
	 * 删除测试任务执行
	 */
	@RequiresPermissions("testexecution:taskExecute:remove")
	@Log(title = "测试任务执行", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
        try
        {
    		return toAjax(taskExecuteService.deleteTaskExecuteByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}
	
	/**
	 * 通过项目ID获取执行任务列表
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年4月24日
	 */
    @GetMapping("/gettaskExecuteListByProjectId/{projectId}")
	@ResponseBody
	public String getSchedulingListByProjectId(@PathVariable("projectId") Integer projectId)
	{
    	TaskExecute taskExecute= new TaskExecute();
    	if(projectId!=0){
        	taskExecute.setProjectId(projectId);
    	}
    	List<TaskExecute> taskExecuteList = taskExecuteService.selectTaskExecuteList(taskExecute);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(taskExecuteList));
		return jsonArray.toJSONString();
	}
}
