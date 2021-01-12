package com.luckyframe.project.api;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.project.monitor.job.domain.Job;
import com.luckyframe.project.monitor.job.service.IJobService;
import com.luckyframe.project.testexecution.taskCaseExecute.domain.TaskCaseExecute;
import com.luckyframe.project.testexecution.taskCaseExecute.service.ITaskCaseExecuteService;
import com.luckyframe.project.testexecution.taskCaseLog.domain.TaskCaseLog;
import com.luckyframe.project.testexecution.taskCaseLog.service.ITaskCaseLogService;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCase;
import com.luckyframe.project.testmanagmt.projectCase.domain.ProjectCaseDebug;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseDebugService;
import com.luckyframe.project.testmanagmt.projectCase.service.IProjectCaseService;

/**
 * 统一API对外操作类
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年10月28日
 */
@Controller
@RequestMapping("/openPostApi")
public class OpenPostApiController
{
    private static final Logger log = LoggerFactory.getLogger(OpenPostApiController.class);
	
	@Resource
	private ITaskExecuteService taskExecuteService;
	
	@Resource
	private IProjectCaseDebugService projectCaseDebugService;
	
	@Resource
	private ITaskCaseExecuteService taskCaseExecuteService;
	
	@Resource
	private ITaskCaseLogService taskCaseLogService;
	
	@Resource
	private IProjectCaseService projectCaseService;
	
	@Resource
	private ITaskSchedulingService taskSchedulingService;
	
    @Resource
    private IJobService jobService;
	
	/**
	 * 提交用例调试日志内容
	 * @param jsonObject 请求json对象
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
			log.error("提交调试日志出现异常", e);
		}
		if(result>0){
			return "提交调试日志成功！";
		}else{
			return "提交调试日志失败！";
		}		
	}
	
	/**
	 * 增加用例执行明细
	 * @param jsonObject 请求json对象
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
	 * @param jsonObject 请求json对象
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
			result = taskCaseExecuteService.updateTaskCaseExecute(tce);
		} catch (Exception e) {
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
	 * @param jsonObject 请求json对象
	 * @return 返回增加日志结果
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
	 * @param jsonObject 请求json对象
	 * @return 返回任务更新结果
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
			log.error("更新任务执行数据出现异常", e);
		}
		return "{\"caseCount\":"+caseCount+",\"caseSuc\":"+casesuc+",\"caseFail\":"+casefail+",\"caseLock\":"+caselock+",\"caseNoExec\":"+casenoexec+"}";
	}
	
	
	/**
	 * 删除用例执行日志
	 * @param jsonObject 请求json对象
	 * @return 返回删除日志结果
	 * @author Seagull
	 * @date 2019年4月23日
	 */
	@PostMapping("/clientDeleteTaskCaseLog")
	@ResponseBody
	public String clientDeleteTaskCaseLog(@RequestBody JSONObject jsonObject) {
		Integer taskId = jsonObject.getInteger("taskId");
		Integer caseId = jsonObject.getInteger("caseId");
		int result = 0;
		try {
			TaskCaseExecute tce = new TaskCaseExecute();
			tce.setTaskId(taskId);
			tce.setCaseId(caseId);
			TaskCaseExecute taskCaseExecute = taskCaseExecuteService.selectTaskCaseExecuteByTaskIdAndCaseId(tce);
			result = taskCaseLogService.deleteTaskCaseLogByTaskCaseId(taskCaseExecute.getTaskCaseId());			
		} catch (Exception e) {
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
	 * @param jsonObject 请求json对象
	 * @return 返回获取详细日志结果
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
			log.error("通过接口提取测试结果日志出现异常！", e);
		}
		return result;
	}
	
	/**
	 * 对外接口,根据调度任务的名称，触发测试任务
	 * @param jsonObject 请求json对象
	 * @return 返回触发测试任务的结果
	 * @author Seagull
	 * @date 2019年9月4日
	 */
	@PostMapping("/runTaskBySchedulingName")
	@ResponseBody
	public String runTaskBySchedulingName(@RequestBody JSONObject jsonObject) {
		String schedulingName = jsonObject.getString("schedulingName");
		try {
			TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingByName(schedulingName);
			if(null==taskScheduling){
				return "没有找到任务名【"+schedulingName+"】的调度任务";
			}else{
				Job job = new Job();
				job.setJobId(taskScheduling.getJobId().longValue());
				jobService.run(job);
			}
		} catch (Exception e) {
			log.error("接口运行调度任务出现异常", e);
		}
		return "已触发调度任务【"+schedulingName+"】";
	}
	
}
