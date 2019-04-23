package com.luckyframe.project.api;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;
import com.luckyframe.project.testexecution.taskCaseExecute.service.ITaskCaseExecuteService;
import com.luckyframe.project.testexecution.taskCaseLog.domain.TaskCaseLog;
import com.luckyframe.project.testexecution.taskCaseLog.service.ITaskCaseLogService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseDebugService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseStepsService;
import com.luckyframe.project.testmanagmt.projectCaseParams.service.IProjectCaseParamsService;
import com.luckyframe.project.testmanagmt.projectPlan.service.IProjectPlanService;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/openPostApi")
public class OpenPostApiController
{
    private static final Logger log = LoggerFactory.getLogger(OpenPostApiController.class);
	
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private IProjectCaseDebugService projectCaseDebugService;
	
	@Autowired
	private ITaskCaseExecuteService taskCaseExecuteService;
	
	@Autowired
	private ITaskCaseLogService taskCaseLogService;
	
	/**
	 * 根据项目ID获取公共参数列表
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@PostMapping("/clientPostCaseDebugLog")
	@ResponseBody
	public String clientPostCaseDebugLog(@RequestBody JSONObject jsonObject) {
		// 更新实体
		int result = 0;
		try {
			ProjectCaseDebug projectCaseDebug = JSONObject.parseObject(jsonObject.toJSONString(), ProjectCaseDebug.class);
			result = projectCaseDebugService.insertProjectCaseDebug(projectCaseDebug);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>0){
			return "提交调试日志成功！";
		}else{
			return "提交调试日志失败！";
		}		
	}
	
	/**
	 * 增加用例执行明细
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@PostMapping("/clientPostTaskCaseExecute")
	@ResponseBody
	public String clientPostTaskCaseExecute(@RequestBody JSONObject jsonObject) {
		// 更新实体
		int result = 0;
		try {
			TaskCaseExecute taskCaseExecute = JSONObject.parseObject(jsonObject.toJSONString(), TaskCaseExecute.class);
			result = taskCaseExecuteService.insertTaskCaseExecute(taskCaseExecute);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>0){
			return "提交用例执行明细成功！";
		}else{
			return "提交用例执行明细失败！";
		}		
	}

	/**
	 * 修改用例执行状态
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@PostMapping("/clientUpdateTaskCaseExecuteStatus")
	@ResponseBody
	public String clientUpdateTaskCaseExecuteStatus(@RequestBody JSONObject jsonObject) {
		// 更新实体
		int result = 0;
		try {
			TaskCaseExecute taskCaseExecute = JSONObject.parseObject(jsonObject.toJSONString(), TaskCaseExecute.class);
			TaskCaseExecute tce = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(taskCaseExecute);
			tce.setCaseStatus(taskCaseExecute.getCaseStatus());
			tce.setUpdateTime(taskCaseExecute.getUpdateTime());
			taskCaseExecuteService.updateTaskCaseExecute(tce);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>0){
			return "修改用例执行状态成功！";
		}else{
			return "修改用例执行状态失败！";
		}		
	}
	
	/**
	 * 增加用例日志明细
	 * @param req
	 * @param rsp
	 * @author Seagull
	 * @date 2019年4月16日
	 */
	@PostMapping("/clientPostTaskCaseLog")
	@ResponseBody
	public String clientPostTaskCaseLog(@RequestBody JSONObject jsonObject) {
		// 更新实体
		int result = 0;
		try {
			TaskCaseLog taskCaseLog = JSONObject.parseObject(jsonObject.toJSONString(), TaskCaseLog.class);
			TaskCaseExecute tce = new TaskCaseExecute();
			tce.setTaskId(taskCaseLog.getTaskId());
			tce.setCaseId(taskCaseLog.getCaseId());
			TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(tce);
			
			taskCaseLog.setTaskCaseId(taskCaseExecute.getTaskCaseId());
			result = taskCaseLogService.insertTaskCaseLog(taskCaseLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>0){
			return "提交用例执行日志成功！";
		}else{
			return "提交用例执行日志失败！";
		}		
	}
	
	/**
	 * 更新任务执行数据
	 * @param jsonObject
	 * @return
	 * @author Seagull
	 * @date 2019年4月22日
	 */
	@PostMapping("/clientUpdateTaskExecuteData")
	@ResponseBody
	public String clientUpdateTaskExecuteData(@RequestBody JSONObject jsonObject) {
		// 更新实体
		int casesuc = 0;
		int casefail = 0;
		int caselock = 0;
		int casenoexec = 0;
		Integer taskId = jsonObject.getInteger("taskId");
		Integer caseCount = jsonObject.getInteger("caseCount");
		try {
			TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);

			TaskCaseExecute taskCaseExecute = new TaskCaseExecute();
			taskCaseExecute.setTaskId(taskId);
			taskCaseExecute.setCaseStatus(0);
			casesuc = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			taskCaseExecute.setCaseStatus(1);
			casefail = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			taskCaseExecute.setCaseStatus(2);
			caselock = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			taskCaseExecute.setCaseStatus(4);
			casenoexec = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			
			if (caseCount == 0) {
				caseCount = casesuc + casefail + caselock + casenoexec;
			}
			
			taskExecute.setCaseTotalCount(caseCount);
			taskExecute.setCaseSuccCount(casesuc);
			taskExecute.setCaseLockCount(caselock);
			taskExecute.setCaseNoexecCount(casenoexec);
			taskExecute.setUpdateTime(new Date());
			taskExecute.setFinishTime(new Date());
			taskExecuteService.updateTaskExecute(taskExecute);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"caseCount\":"+caseCount+",\"caseSuc\":"+casesuc+",\"caseFail\":"+casefail+",\"caseLock\":"+caselock+",\"caseNoExec\":"+casenoexec+"}";
	}
	
	/**
	 * 更新任务执行数据以及状态
	 * @param jsonObject
	 * @return
	 * @author Seagull
	 * @date 2019年4月22日
	 */
	@PostMapping("/clientUpdateTaskExecuteStatus")
	@ResponseBody
	public String clientUpdateTaskExecuteStatus(@RequestBody JSONObject jsonObject) {
		// 更新实体
		int casesuc = 0;
		int casefail = 0;
		int caselock = 0;
		int casenoexec = 0;
		Integer taskId = jsonObject.getInteger("taskId");
		Integer caseCount = jsonObject.getInteger("caseCount");
		Integer taskStatus = jsonObject.getInteger("taskStatus");
		try {
			TaskExecute taskExecute = taskExecuteService.selectTaskExecuteById(taskId);

			TaskCaseExecute taskCaseExecute = new TaskCaseExecute();
			taskCaseExecute.setTaskId(taskId);
			taskCaseExecute.setCaseStatus(0);
			casesuc = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			taskCaseExecute.setCaseStatus(1);
			casefail = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			taskCaseExecute.setCaseStatus(2);
			caselock = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			taskCaseExecute.setCaseStatus(4);
			casenoexec = taskCaseExecuteService.selectTaskCaseExecuteList(taskCaseExecute).size();
			
			if (caseCount == 0) {
				caseCount = casesuc + casefail + caselock + casenoexec;
				taskExecute.setUpdateTime(new Date());
				taskExecute.setFinishTime(new Date());
			}
			
			taskExecute.setCaseTotalCount(caseCount);
			taskExecute.setCaseSuccCount(casesuc);
			taskExecute.setCaseLockCount(caselock);
			taskExecute.setCaseNoexecCount(casenoexec);
			taskExecute.setTaskStatus(taskStatus);
			taskExecuteService.updateTaskExecute(taskExecute);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\"caseCount\":"+caseCount+",\"caseSuc\":"+casesuc+",\"caseFail\":"+casefail+",\"caseLock\":"+caselock+",\"caseNoExec\":"+casenoexec+"}";
	}
	
	/**
	 * 删除用例执行日志
	 * @param jsonObject
	 * @return
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	@PostMapping("/clientDeleteTaskCaseLog")
	@ResponseBody
	public String clientDeleteTaskCaseLog(@RequestBody JSONObject jsonObject) {
		Integer taskId = jsonObject.getInteger("taskId");
		Integer caseId = jsonObject.getInteger("caseId");
		Integer result = 0;
		try {
			TaskCaseExecute tce = new TaskCaseExecute();
			tce.setTaskId(taskId);
			tce.setCaseId(caseId);
			TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(tce);
			result = taskCaseLogService.deleteTaskCaseLogByTaskCaseId(taskCaseExecute.getTaskCaseId());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result>0){
			return "删除用例执行日志成功！";
		}else{
			return "删除用例执行日志失败！";
		}	
	}
	
}
