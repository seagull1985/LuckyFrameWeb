package com.luckyframe.project.testexecution.taskScheduling.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.constant.JobConstants;
import com.luckyframe.common.exception.BusinessException;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.common.utils.file.FileUploadUtils;
import com.luckyframe.common.utils.poi.ExcelUtil;
import com.luckyframe.common.utils.security.PermissionUtils;
import com.luckyframe.common.utils.security.ShiroUtils;
import com.luckyframe.framework.aspectj.lang.annotation.Log;
import com.luckyframe.framework.aspectj.lang.enums.BusinessType;
import com.luckyframe.framework.config.LuckyFrameConfig;
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
        List<Client> clients=clientService.selectClientList(new Client());
        mmap.put("clients", clients);
        if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
        	mmap.put("defaultProjectId", ShiroUtils.getProjectId());
        	List<Client> defaultClients=clientService.selectClientsByProjectId(ShiroUtils.getProjectId());
        	if(defaultClients.size()>0){
        		mmap.put("defaultClientId", defaultClients.get(0).getClientId());
        	}
        }
        
	    return "testexecution/taskScheduling/taskScheduling";
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
        ExcelUtil<TaskScheduling> util = new ExcelUtil<>(TaskScheduling.class);
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
        	Integer projectId=projects.get(0).getProjectId();
            if(StringUtils.isNotEmpty(ShiroUtils.getProjectId())){
            	projectId = ShiroUtils.getProjectId();
            	mmap.put("defaultProjectId", projectId);
            }          
        	List<ProjectPlan> planList = projectPlanService.selectProjectPlanListByProjectId(projectId);
        	mmap.put("planList", planList);
        	List<Client> clientList = clientService.selectClientsByProjectId(projectId);
        	mmap.put("clientList", clientList);
        	if(clientList.size()>0){
        		List<String> driverPathList = clientService.selectClientDriverListById(clientList.get(0).getClientId());
        		mmap.put("driverPathList", driverPathList);
        	}       	
        }       
	    return "testexecution/taskScheduling/add";
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
		if(!PermissionUtils.isProjectPermsPassByProjectId(taskScheduling.getProjectId())){
			return error("没有此项目保存任务调度权限");
		}
		
		int result;
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
	    return "testexecution/taskScheduling/edit";
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
		if(!PermissionUtils.isProjectPermsPassByProjectId(taskScheduling.getProjectId())){
			return error("没有此项目修改任务调度权限");
		}
		
		int result;
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
        try
        {
        	return toAjax(taskSchedulingService.deleteTaskSchedulingByIds(ids));
        }
        catch (BusinessException e)
        {
            return error(e.getMessage());
        }
	}
	
    /**
     * 校验调度任务名称唯一性
     * @param taskScheduling 调度任务对象
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
     * @param taskScheduling 调度任务对象
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
	 * @param projectId 项目ID
	 * @author Seagull
	 * @date 2019年4月8日
	 */
    @GetMapping("/getSchedulingListByProjectId/{projectId}")
	@ResponseBody
	public String getSchedulingListByProjectId(@PathVariable("projectId") Integer projectId)
	{
    	List<TaskScheduling> taskSchedulingList = taskSchedulingService.selectTaskSchedulingListByProjectId(projectId);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(taskSchedulingList));
		return jsonArray.toJSONString();
	}   
    
	/**
	 * 查询日志
	 * @param mmap 返回数据模型
	 * @author Seagull
	 * @date 2019年4月25日
	 */
	@GetMapping("/queryLog")
	public String queryLog(ModelMap mmap)
	{
		Client client = new Client();
		client.setStatus(0);
	    Date dt = new Date();   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Client> clientList = clientService.selectClientList(client);
		String defaultDate = sdf.format(dt);
        mmap.put("clientList", clientList);  
        mmap.put("defaultDate", defaultDate);
	    return "testexecution/taskScheduling/queryLog";
	}
	
    /**
     * 获取客户端日志
     * @param mmap 返回数据模型
     * @param request HTTP请求
     * @param response HTTP响应
     * @author Seagull
     * @date 2019年4月25日
     */
	@RequestMapping(value = "/downloadLog.do")
	public String downloadLog(ModelMap mmap, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");
		String contentType = "application/octet-stream";
		response.setContentType(contentType);
		response.setContentType("multipart/form-data");
		
		
		String queryDate = request.getParameter("queryDate");
		String clientIp = request.getParameter("clientIp");
		String logLevel = request.getParameter("logLevel");

		String storeName = logLevel+".log";
	    Date dt = new Date();   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   

		if (!sdf.format(dt).equals(queryDate))
		{
			storeName = storeName + "." + queryDate;
		}
		String result="获取日志远程链接失败！";
		try{    		
    		Map<String, Object> params = new HashMap<>(0);
    		params.put("filename", storeName);
			Client client = clientService.selectClientByClientIP(clientIp);
			result=HttpRequest.httpClientGet("http://"+clientIp+":"+ClientConstants.CLIENT_MONITOR_PORT+"/getLogdDetail", client, params,60000);
			result=result.replace("##n##", "\n");
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		mmap.put("data", result);
		return "testexecution/taskScheduling/showLog";
	}
	
	/**
	 * 上传驱动页面
	 * @param mmap 返回数据模型
	 * @author Seagull
	 * @date 2019年4月26日
	 */
	@GetMapping("/showUploadJar")
	public String showUploadJar(ModelMap mmap)
	{
		Client client = new Client();
		client.setStatus(0);
		List<Client> clientList = clientService.selectClientList(client);
    	if(clientList.size()>0){
            mmap.put("clientList", clientList);
    		List<String> driverPathList = clientService.selectClientDriverListById(clientList.get(0).getClientId());
    		mmap.put("driverPathList", driverPathList);
    	}
	    return "testexecution/taskScheduling/uploadJar";
	}
	
	/**
	 * 上传驱动到客户端
	 * @param file 文件对象
	 * @param clientIp 客户端IP
	 * @param driverPath 驱动路径
	 * @author Seagull
	 * @date 2019年7月26日
	 */
	@RequiresPermissions("testexecution:taskScheduling:add")
	@Log(title = "上传驱动到客户端", businessType = BusinessType.UPLOAD)
	@PostMapping("/uploadJar")
	@ResponseBody
	public AjaxResult uploadJar(@RequestParam("drivenfile") MultipartFile file, @RequestParam("clientIp") String clientIp,@RequestParam("driverPath") String driverPath) {
		String result;
		try {
			if (!file.isEmpty()) {
				if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(FileUploadUtils.DRIVEN_JAR_EXTENSION)&&!file.getOriginalFilename().endsWith(FileUploadUtils.DRIVEN_PYTHON_EXTENSION))
				{
					return error("驱动文件只能是.jar或.py格式");
				}
				
				File jarFile = FileUploadUtils.uploadJar(LuckyFrameConfig.getUploadPath(), file);
				
				try {
					result = HttpRequest.httpClientUploadFile(
							"http://" + clientIp + ":" + ClientConstants.CLIENT_MONITOR_PORT + "/uploadJar", clientService.selectClientByClientIP(clientIp), driverPath,
							jarFile);
				} catch (Exception e) {
					return error("获取远程链接失败！");
				}
				// 删除服务器上的文件
				if (jarFile.exists()) {
					jarFile.delete();
				}
			}else{
				return error("文件为空");
			}
		 } catch (Exception e) {
			return error(e.getMessage());
		 }
		 return success(result);
	}
}
