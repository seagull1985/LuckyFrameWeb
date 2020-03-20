package com.luckyframe.common.utils.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;

/**
 * 远程唤起客户端工具类
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年4月13日
 */
public class ClientRun {
	
	@Resource
	private ITaskSchedulingService taskSchedulingService;
	
	@Resource
	private ITaskExecuteService taskExecuteService;
	
	public static ClientRun clientRun;

	@PostConstruct
	public void init() {
		clientRun = this;
	}

	public String toRunTask(String schedulingId,int autoType){
		String result="启动失败！";
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(Integer.valueOf(schedulingId));
		
		TaskExecute taskExecute = new TaskExecute();
		String taskName = "【"+taskScheduling.getSchedulingName()+ "】_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		taskExecute.setTaskName(taskName);
		taskExecute.setSchedulingId(taskScheduling.getSchedulingId());
		taskExecute.setProjectId(taskScheduling.getProjectId());
		taskExecute.setTaskStatus(0);
		
		taskExecuteService.insertTaskExecute(taskExecute);
		try{			
    		RunTaskEntity runTaskEntity = new RunTaskEntity();
    		runTaskEntity.setSchedulingName(taskScheduling.getSchedulingName());
    		runTaskEntity.setTaskId(taskExecute.getTaskId().toString());   		
    		if(StringUtils.isEmpty(taskScheduling.getClientDriverPath())){
    			runTaskEntity.setLoadPath("/TestDriven");
    		}else{
    			runTaskEntity.setLoadPath(taskScheduling.getClientDriverPath());
    		}

			result=HttpRequest.httpClientPost("http://"+taskScheduling.getClient().getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/runtask", taskScheduling.getClient(),JSONObject.toJSONString(runTaskEntity),3000);
    		System.out.println(result);
    		return result;
		}catch (Exception e) {
			//执行失败时,写入任务表记录为执行失败
			taskExecute.setTaskStatus(0);
			taskExecuteService.updateTaskExecute(taskExecute);
			return result;
		}
			
	}

}
