package com.luckyframe.project.testexecution.taskCaseExecute.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;
import com.luckyframe.project.testexecution.taskCaseExecute.service.ITaskCaseExecuteService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;

/**
 * 任务用例执行记录 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-04-08
 */
@Controller
@RequestMapping("/testexecution/taskCaseExecute")
public class TaskCaseExecuteController extends BaseController
{
    private String prefix = "testexecution/taskCaseExecute";
	
	@Autowired
	private ITaskCaseExecuteService taskCaseExecuteService;
	
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private IProjectService projectService;
	
	@RequiresPermissions("testexecution:taskCaseExecute:view")
	@GetMapping()
	public String taskCaseExecute(HttpServletRequest req, ModelMap mmap)
	{
		String taskIdStr = req.getParameter("taskId");
		
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        
		TaskExecute taskExecute = new TaskExecute();
        if(StringUtils.isNotEmpty(taskIdStr)){
        	Integer taskId = Integer.valueOf(taskIdStr);
        	if(StringUtils.isNotEmpty(taskId)){
        		Integer projectId = taskExecuteService.selectTaskExecuteById(taskId).getProjectId();
        		taskExecute.setProjectId(projectId);
            	mmap.put("defaultProjectId", projectId);
            	mmap.put("defaultTaskId", taskId);
        	}
        }
        
        List<TaskExecute> taskExecutes = taskExecuteService.selectTaskExecuteList(taskExecute);
        mmap.put("taskExecutes", taskExecutes);
        
	    return prefix + "/taskCaseExecute";
	}
	
	/**
	 * 查询任务用例执行记录列表
	 */
	@RequiresPermissions("testexecution:taskCaseExecute:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TaskCaseExecute taskCaseExecute)
	{
		startPage();
        List<TaskCaseExecute> list = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出任务用例执行记录列表
	 */
	@RequiresPermissions("testexecution:taskCaseExecute:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskCaseExecute taskCaseExecute)
    {
    	List<TaskCaseExecute> list = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute);
        ExcelUtil<TaskCaseExecute> util = new ExcelUtil<TaskCaseExecute>(TaskCaseExecute.class);
        return util.exportExcel(list, "taskCaseExecute");
    }
	
	/**
	 * 新增任务用例执行记录
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存任务用例执行记录
	 */
	@RequiresPermissions("testexecution:taskCaseExecute:add")
	@Log(title = "任务用例执行记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TaskCaseExecute taskCaseExecute)
	{		
		return toAjax(taskCaseExecuteService.insertTaskCaseExecute(taskCaseExecute));
	}

	/**
	 * 修改任务用例执行记录
	 */
	@GetMapping("/edit/{taskCaseId}")
	public String edit(@PathVariable("taskCaseId") Integer taskCaseId, ModelMap mmap)
	{
		TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteById(taskCaseId);
		mmap.put("taskCaseExecute", taskCaseExecute);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存任务用例执行记录
	 */
	@RequiresPermissions("testexecution:taskCaseExecute:edit")
	@Log(title = "任务用例执行记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TaskCaseExecute taskCaseExecute)
	{		
		return toAjax(taskCaseExecuteService.updateTaskCaseExecute(taskCaseExecute));
	}
	
	/**
	 * 删除任务用例执行记录
	 */
	@RequiresPermissions("testexecution:taskCaseExecute:remove")
	@Log(title = "任务用例执行记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(taskCaseExecuteService.deleteTaskCaseExecuteByIds(ids));
	}
	
	/**
	 * 根据任务ID获取任务进度条
	 * @param taskId
	 * @return
	 * @author Seagull
	 * @date 2019年4月9日
	 */
    @GetMapping("/getProgressBarByTaskId/{taskId}")
	@ResponseBody
	public String getProgressBarByTaskId(@PathVariable("taskId") Integer taskId)
	{
    	TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);
    	
    	if (null != taskExecute && taskExecute.getCaseTotalCount()!=0) {
    		List<TaskCaseExecute> taskCaseExecuteList = taskCaseExecuteService.selectTaskCaseExecuteListByTaskId(taskId);
    		Integer percent = (int)((Double.valueOf(taskCaseExecuteList.size()) / Double.valueOf(taskExecute.getCaseTotalCount())) * 100);

    		int caseSuccCount = 0;
    		int caseFailCount = 0;
    		int caseLockCount = 0;
    		int caseNoexecCount = 0;
    		for(TaskCaseExecute tce:taskCaseExecuteList){
    			if(tce.getCaseStatus()==0){
    				caseSuccCount++;
    			}else if(tce.getCaseStatus()==1){
    				caseFailCount++;
    			}else if(tce.getCaseStatus()==2){
    				caseLockCount++;
    			}else{
    				caseNoexecCount++;
    			}
    		}

    		taskExecute.setCaseSuccCount(caseSuccCount);
    		taskExecute.setCaseFailCount(caseFailCount);
    		taskExecute.setCaseLockCount(caseLockCount);
    		taskExecute.setCaseNoexecCount(caseNoexecCount);
    		taskExecute.setTaskProgress(percent);
    	}else{
    		taskExecute.setCaseTotalCount(0);
    		taskExecute.setCaseSuccCount(0);
    		taskExecute.setCaseFailCount(0);
    		taskExecute.setCaseLockCount(0);
    		taskExecute.setCaseNoexecCount(0);
    		taskExecute.setTaskProgress(100);
    		taskExecute.setTaskStatus(2);
    	}
    	
		return JSONObject.toJSONString(taskExecute);
	}

}
