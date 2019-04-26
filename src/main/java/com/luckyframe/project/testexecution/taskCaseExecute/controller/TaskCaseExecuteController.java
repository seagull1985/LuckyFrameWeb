package com.luckyframe.project.testexecution.taskCaseExecute.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.constant.TaskSchedulingConstants;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.common.utils.client.RunBatchCaseEntity;
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
import com.luckyframe.project.testexecution.taskCaseLog.domain.TaskCaseLog;
import com.luckyframe.project.testexecution.taskCaseLog.service.ITaskCaseLogService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseSteps;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;

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
	private ITaskSchedulingService taskSchedulingService;
	
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IProjectCaseStepsService projectCaseStepsService;
	
	@Autowired
	private ITaskCaseLogService taskCaseLogService;
	
	@RequiresPermissions("testexecution:taskCaseExecute:view")
	@GetMapping()
	public String taskCaseExecute(HttpServletRequest req, ModelMap mmap)
	{
		String taskIdStr = req.getParameter("taskId");
		
        List<Project> projects=projectService.selectProjectAll(0);
        mmap.put("projects", projects);
        
        Integer taskId=0;
        Integer projectId=0;
		TaskExecute taskExecute = new TaskExecute();
        if(StringUtils.isNotEmpty(taskIdStr)){
        	taskId = Integer.valueOf(taskIdStr);
        	if(StringUtils.isNotEmpty(taskId)){
        		projectId = taskExecuteService.selectTaskExecuteById(taskId).getProjectId();
        	}
        }else{
        	TaskExecute te = taskExecuteService.selectTaskExecuteLastRecord();
        	taskId = te.getTaskId();
        	projectId = te.getProjectId();
        }
        
		taskExecute.setProjectId(projectId);
    	mmap.put("defaultProjectId", projectId);
    	mmap.put("defaultTaskId", taskId);
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
	 * 运行所有非成功用例
	 * @param taskCaseExecute
	 * @return
	 * @author Seagull
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @date 2019年4月23日
	 */
	@RequiresPermissions("testmanagmt:projectCase:edit")
	@Log(title = "同步测试结果到用例步骤", businessType = BusinessType.UPDATE)
	@PostMapping("/synchronousTestResults")
	@ResponseBody
	public AjaxResult synchronousTestResults(Integer logId)
	{
		Integer result=0;
		try {
			TaskCaseLog taskCaseLog = taskCaseLogService.selectTaskCaseLogById(logId);
			TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteById(taskCaseLog.getTaskCaseId());
			ProjectCaseSteps projectCaseSteps = new ProjectCaseSteps();
			projectCaseSteps.setCaseId(taskCaseExecute.getCaseId());
			projectCaseSteps.setStepSerialNumber(Integer.valueOf(taskCaseLog.getLogStep()));
			ProjectCaseSteps pcs = projectCaseStepsService.selectProjectCaseStepsByCaseIdAndStepNum(projectCaseSteps);
			String testResult = taskCaseLog.getLogDetail().substring(taskCaseLog.getLogDetail().lastIndexOf("测试结果：") + 5);
			pcs.setExpectedResult(testResult);
			
			result = projectCaseStepsService.updateProjectCaseSteps(pcs);
		} catch (Exception e) {
			// TODO: handle exception
			return AjaxResult.error("同步测试结果出现异常");
		}		
		return toAjax(result);
	}
	
	/**
	 * 运行所有非成功用例
	 * @param taskCaseExecute
	 * @return
	 * @author Seagull
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @date 2019年4月23日
	 */
	@RequiresPermissions("testexecution:taskScheduling:execution")
	@Log(title = "运行所有非成功用例", businessType = BusinessType.RUNCASE)
	@PostMapping("/runAllFailCase")
	@ResponseBody
	public AjaxResult runAllFailCase(Integer taskId) throws UnsupportedEncodingException, IOException
	{		
		TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(taskExecute.getSchedulingId());
		
		String url= "http://"+taskScheduling.getClient().getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/runBatchCase";
		RunBatchCaseEntity runBatchCaseEntity = new RunBatchCaseEntity();
		runBatchCaseEntity.setProjectname(taskScheduling.getProject().getProjectName());
		runBatchCaseEntity.setTaskid(taskId.toString());
		runBatchCaseEntity.setLoadpath(taskScheduling.getClientDriverPath());
		runBatchCaseEntity.setBatchcase(TaskSchedulingConstants.RUNCASEALLFAIL);
		try {
			HttpRequest.httpClientPost(url, JSONObject.toJSONString(runBatchCaseEntity));
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			return AjaxResult.error("唤起客户端失败，请检查原因");
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AjaxResult.error("执行用例出现异常，请检查原因");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AjaxResult.error("执行用例出现异常，请检查原因");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return AjaxResult.error("执行用例链接客户端超时，请检查原因");
		}
		
		return AjaxResult.success("正在运行全部失败用例...");
	}
	
	/**
	 * 运行选择的用例
	 * @param ids
	 * @return
	 * @author Seagull
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @date 2019年4月23日
	 */
	@RequiresPermissions("testexecution:taskScheduling:execution")
	@Log(title = "运行选择的用例", businessType = BusinessType.RUNCASE)
	@PostMapping( "/runSelectCase")
	@ResponseBody
	public AjaxResult runSelectCase(String ids,Integer taskId) throws UnsupportedEncodingException, IOException
	{
		TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(taskExecute.getSchedulingId());
		String batchCase = ids.replace(",", "#");
        
		String url= "http://"+taskScheduling.getClient().getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/runBatchCase";
		RunBatchCaseEntity runBatchCaseEntity = new RunBatchCaseEntity();
		runBatchCaseEntity.setProjectname(taskScheduling.getProject().getProjectName());
		runBatchCaseEntity.setTaskid(taskId.toString());
		runBatchCaseEntity.setLoadpath(taskScheduling.getClientDriverPath());
		runBatchCaseEntity.setBatchcase(batchCase);
		
		try {
			HttpRequest.httpClientPost(url, JSONObject.toJSONString(runBatchCaseEntity));
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			return AjaxResult.error("唤起客户端失败，请检查原因");
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			return AjaxResult.error("执行用例出现异常，请检查原因");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return AjaxResult.error("执行用例出现异常，请检查原因");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return AjaxResult.error("执行用例链接客户端超时，请检查原因");
		}
		return AjaxResult.success("开始运行您选择的失败用例...");
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
    	
    	if (null != taskExecute && null!=taskExecute.getCaseTotalCount()) {
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
    	}
    	
		return JSONObject.toJSONString(taskExecute);
	}

	/**
	 * 展示日志中的图片
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/showImage.do")
	public void showImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = request.getParameter("fileName");
		Integer logId = Integer.valueOf(request.getParameter("logId"));
		TaskCaseLog taskCaseLog = taskCaseLogService.selectTaskCaseLogById(logId);
		TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskCaseLog.getTaskId());
		TaskScheduling TaskScheduling = taskSchedulingService.selectTaskSchedulingById(taskExecute.getSchedulingId());

		String newName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");

		Map<String, Object> params = new HashMap<String, Object>(0);
		params.put("imgName", newName);
		byte[] bfis = HttpRequest.getFile(
				"http://" + TaskScheduling.getClient().getClientIp() + ":" + ClientConstants.CLIENT_MONITOR_PORT + "/getLogImg", params);

		if (bfis.length != 0) {
			response.setContentType("image/jpeg");
			String path = System.getProperty("user.dir") + "\\";
			String pathName = path + newName;
			File file = new File(pathName);
			try {
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
				os.write(bfis);
				os.flush();
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FileInputStream fis = new FileInputStream(pathName);
			System.out.println(pathName);
			OutputStream os = response.getOutputStream();
			try {
				int count = 0;
				byte[] buffer = new byte[1024 * 1024];
				while ((count = fis.read(buffer)) != -1) {
					os.write(buffer, 0, count);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					os.flush();
				}
				os.close();
				if (fis != null) {
					fis.close();
				}
				if (file.exists()) {
					file.delete();
				}

			}
		} else {
			response.setHeader("content-type", "text/html");
			OutputStream eos = response.getOutputStream();
			String notes = "获取图片异常，请在客户端项目所在的目录\\log\\ScreenShot\\下检查【" + fileName + "】是否存在！";
			eos.write(notes.getBytes());
			eos.flush();
			eos.close();
		}
	}
	
}
