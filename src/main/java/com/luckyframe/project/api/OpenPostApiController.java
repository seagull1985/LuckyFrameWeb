package com.luckyframe.project.api;

import java.util.Date;
import java.util.List;

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
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseDebugService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;

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
	
	@Autowired
	private IProjectCaseService projectCaseService;
	
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
			log.error("根据项目ID获取公共参数列表出现异常", e);
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
			taskCaseExecute.setCreateTime(new Date());
			taskCaseExecute.setUpdateTime(new Date());
			result = taskCaseExecuteService.insertTaskCaseExecute(taskCaseExecute);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("增加用例执行明细出现异常", e);
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
			Integer caseStatus = taskCaseExecute.getCaseStatus();
			TaskCaseExecute tce = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(taskCaseExecute);
			tce.setCaseStatus(caseStatus);
			tce.setUpdateTime(new Date());
			if(caseStatus==3){
				tce.setCreateTime(new Date());
			}
			if(caseStatus==0||caseStatus==1||caseStatus==2){
				tce.setFinishTime(new Date());
			}
			taskCaseExecuteService.updateTaskCaseExecute(tce);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改用例执行状态出现异常", e);
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
			taskCaseLog.setCreateTime(new Date());
			taskCaseLog.setUpdateTime(new Date());
			
			TaskCaseExecute tce = new TaskCaseExecute();
			tce.setTaskId(taskCaseLog.getTaskId());
			tce.setCaseId(taskCaseLog.getCaseId());
			TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(tce);
			
			taskCaseLog.setTaskCaseId(taskCaseExecute.getTaskCaseId());
			result = taskCaseLogService.insertTaskCaseLog(taskCaseLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("增加用例日志明细出现异常", e);
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
			}
			
			taskExecute.setCaseTotalCount(caseCount);
			taskExecute.setCaseSuccCount(casesuc);
			taskExecute.setCaseFailCount(casefail);
			taskExecute.setCaseLockCount(caselock);
			taskExecute.setCaseNoexecCount(casenoexec);
			taskExecute.setTaskStatus(taskStatus);
			taskExecute.setUpdateTime(new Date());
			/*状态完成时,更新结束时间*/
			if(taskStatus==2||taskStatus==3){
				taskExecute.setFinishTime(new Date());	
			}
			taskExecuteService.updateTaskExecute(taskExecute);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新任务执行数据出现异常", e);
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
			log.error("删除用例执行日志出现异常", e);
		}
		if(result>0){
			return "删除用例执行日志成功！";
		}else{
			return "删除用例执行日志失败！";
		}	
	}
	
	/**
	 * 提取测试结果的详细日志
	 * @param jsonObject
	 * @return
	 * @author Seagull
	 * @date 2019年6月18日
	 */
	@PostMapping("/getLogDetailResult")
	@ResponseBody
	public String getLogDetailResult(@RequestBody JSONObject jsonObject) {
		String taskName = jsonObject.getString("taskName");
		String caseSign = jsonObject.getString("caseSign");
		String result = "未能提取到相关的测试结果...";
		try {
			TaskExecute taskExecute = taskExecuteService.selectTaskExecuteByTaskName(taskName);
			ProjectCase projectCase = projectCaseService.selectProjectCaseByCaseSign(caseSign);
			
			TaskCaseExecute tce=new TaskCaseExecute();
			tce.setTaskId(taskExecute.getTaskId());
			tce.setCaseId(projectCase.getCaseId());
			TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(tce);
			
			List<TaskCaseLog> loglist = taskCaseLogService.selectTaskCaseLogListByTaskCaseId(taskCaseExecute.getTaskCaseId());
			for(TaskCaseLog tcl:loglist){
				if(tcl.getLogDetail().contains("测试结果：")){
					result = tcl.getLogDetail();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("通过接口提取测试结果日志出现异常！", e);
		}
		return result;
	}
}
