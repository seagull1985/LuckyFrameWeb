package com.luckyframe.project.testexecution.taskScheduling.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.luckyframe.common.constant.JobConstants;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.web.controller.BaseController;
import com.luckyframe.framework.web.domain.AjaxResult;
import com.luckyframe.framework.web.page.TableDataInfo;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.service.IJobService;
import com.luckyframe.project.monitor.job.util.CronUtils;
import com.luckyframe.project.system.client.domain.Client;
import com.luckyframe.project.system.client.service.IClientService;
import com.luckyframe.project.system.project.domain.Project;
import com.luckyframe.project.system.project.service.IProjectService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;
import com.luckyframe.project.testmanagmt.projectPlan.domain.ProjectPlan;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;

/**
 * 测试任务调度 信息操作处理
 * 
 * @author luckyframe
 * @date 2019-03-23
 */
@Controller
@RequestMapping("/testexecution/taskScheduling")
public class TaskSchedulingController extends BaseController
{
    private String prefix = "testexecution/taskScheduling";
	
	@Autowired
	private ITaskSchedulingService taskSchedulingService;
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IProjectPlanService projectPlanService;
	
	@Autowired
	private IClientService clientService;
	
    @Autowired
    private IJobService jobService;
	
	@RequiresPermissions("testexecution:taskScheduling:view")
	@GetMapping()
	public String taskScheduling(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
	    return prefix + "/taskScheduling";
	}
	
	/**
	 * 查询测试任务调度列表
	 */
	@RequiresPermissions("testexecution:taskScheduling:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TaskScheduling taskScheduling)
	{
		startPage();
        List<TaskScheduling> list = taskSchedulingService.selectTaskSchedulingList(taskScheduling);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出测试任务调度列表
	 */
	@RequiresPermissions("testexecution:taskScheduling:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskScheduling taskScheduling)
    {
    	List<TaskScheduling> list = taskSchedulingService.selectTaskSchedulingList(taskScheduling);
        ExcelUtil<TaskScheduling> util = new ExcelUtil<TaskScheduling>(TaskScheduling.class);
        return util.exportExcel(list, "taskScheduling");
    }
	
	/**
	 * 新增测试任务调度
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(projects.size()>0){
        	List<ProjectPlan> planList = projectPlanService.selectProjectPlanListByProjectId(projects.get(0).getProjectId());
        	mmap.put("planList", planList);
        	List<Client> clientList = clientService.selectClientsByProjectId(projects.get(0).getProjectId());
        	mmap.put("clientList", clientList);
        	if(clientList.size()>0){
        		List<String> driverPathList = clientService.selectClientDriverListById(clientList.get(0).getClientId());
        		mmap.put("driverPathList", driverPathList);
        	}       	
        }       
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存测试任务调度
	 */
	@RequiresPermissions("testexecution:taskScheduling:add")
	@Log(title = "测试任务调度", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TaskScheduling taskScheduling)
	{
		int result = 0;
		Job job=new Job();
    	job.setJobName(JobConstants.JOB_JOBNAME_FOR_TASKSCHEDULING);
    	job.setJobGroup(JobConstants.JOB_GROUPNAME_FOR_TASKSCHEDULING);
    	job.setMethodName(JobConstants.JOB_METHODNAME_FOR_TASKSCHEDULING);
    	job.setCronExpression(taskScheduling.getCronExpression());
    	job.setMisfirePolicy(taskScheduling.getMisfirePolicy());
    	job.setStatus(taskScheduling.getStatus());
    	job.setRemark(taskScheduling.getRemark());
    	/*在公共调度表中插入数据*/
    	result = jobService.insertJobCron(job);
    	if(result<1){
    		return AjaxResult.error();
    	}
    	/*在调度预约表中插入数据*/
    	result=0;
    	taskScheduling.setJobId(job.getJobId().intValue());
    	result = taskSchedulingService.insertTaskScheduling(taskScheduling);
    	if(result<1){
    		return AjaxResult.error();
    	}
    	job.setMethodParams(taskScheduling.getSchedulingId().toString());
    	result = result + jobService.updateJob(job);
    	
		return toAjax(result);
	}

	/**
	 * 修改测试任务调度
	 */
	@GetMapping("/edit/{schedulingId}")
	public String edit(@PathVariable("schedulingId") Integer schedulingId, ModelMap mmap)
	{
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(schedulingId);
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        if(projects.size()>0){
        	List<ProjectPlan> planList = projectPlanService.selectProjectPlanListByProjectId(taskScheduling.getProjectId());
        	mmap.put("planList", planList);
        	List<Client> clientList = clientService.selectClientsByProjectId(taskScheduling.getProjectId());
        	mmap.put("clientList", clientList);
        	if(clientList.size()>0){
        		List<String> driverPathList = clientService.selectClientDriverListById(taskScheduling.getClientId());
        		mmap.put("driverPathList", driverPathList);
        	}       	
        }
        taskScheduling.setCronExpression(taskScheduling.getJob().getCronExpression());
        taskScheduling.setMisfirePolicy(taskScheduling.getJob().getMisfirePolicy());
        taskScheduling.setStatus(taskScheduling.getJob().getStatus());
        taskScheduling.setRemark(taskScheduling.getJob().getRemark());
		mmap.put("taskScheduling", taskScheduling);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存测试任务调度
	 */
	@RequiresPermissions("testexecution:taskScheduling:edit")
	@Log(title = "测试任务调度", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TaskScheduling taskScheduling)
	{
		int result = 0;
		Job job=new Job();
		job.setJobId(taskScheduling.getJobId().longValue());
    	job.setJobName(JobConstants.JOB_JOBNAME_FOR_TASKSCHEDULING);
    	job.setJobGroup(JobConstants.JOB_GROUPNAME_FOR_TASKSCHEDULING);
    	job.setMethodName(JobConstants.JOB_METHODNAME_FOR_TASKSCHEDULING);
    	job.setMethodParams(taskScheduling.getSchedulingId().toString());
    	job.setCronExpression(taskScheduling.getCronExpression());
    	job.setMisfirePolicy(taskScheduling.getMisfirePolicy());
    	job.setStatus(taskScheduling.getStatus());
    	job.setRemark(taskScheduling.getRemark());
    	/*在公共调度表中更新数据*/
    	result = jobService.updateJob(job);
    	if(result<1){
    		return AjaxResult.error();
    	}
    	/*在调度预约表中插入数据*/
    	result=0;
    	result = taskSchedulingService.updateTaskScheduling(taskScheduling);
    	if(result<1){
    		return AjaxResult.error();
    	}
    	
		return toAjax(result);
	}
	
	/**
	 * 删除测试任务调度
	 */
	@RequiresPermissions("testexecution:taskScheduling:remove")
	@Log(title = "测试任务调度", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(taskSchedulingService.deleteTaskSchedulingByIds(ids));
	}
	
    /**
     * 校验调度任务名称唯一性
     * @param taskScheduling
     * @return
     * @author Seagull
     * @date 2019年3月26日
     */
    @PostMapping("/checkSchedulingNameUnique")
    @ResponseBody
    public String checkSchedulingNameUnique(TaskScheduling taskScheduling)
    {
        return taskSchedulingService.checkSchedulingNameUnique(taskScheduling);
    }
    
    /**
     * 检查Cron表达式有效性
     * @param taskScheduling
     * @return
     * @author Seagull
     * @date 2019年3月28日
     */
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public String checkCronExpressionIsValid(TaskScheduling taskScheduling)
    {
    	String isValid="1";
    	if(CronUtils.isValid(taskScheduling.getCronExpression())){
    		isValid="0";
    	}
        return isValid;
    }
    
	/**
	 * 通过项目ID获取调度列表
	 * @param projectId
	 * @return
	 * @author Seagull
	 * @date 2019年4月8日
	 */
    @GetMapping("/getSchedulingListByProjectId/{projectId}")
	@ResponseBody
	public String getSchedulingListByProjectId(@PathVariable("projectId") Integer projectId)
	{
    	List<TaskScheduling> planList = taskSchedulingService.selectTaskSchedulingListByProjectId(projectId);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(planList));
		return jsonArray.toJSONString();
	}
}
