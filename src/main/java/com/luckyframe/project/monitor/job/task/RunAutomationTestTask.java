package com.luckyframe.project.monitor.job.task;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.common.utils.client.RunTaskEntity;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;

/**
 * 测试任务调度客户端
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改 有任何疑问欢迎联系作者讨论。 QQ:1573584944 Seagull
 * =================================================================
 * @author Seagull
 * @date 2019年3月26日
 */
@Component("runAutomationTestTask")
public class RunAutomationTestTask
{
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private ITaskSchedulingService taskSchedulingService;
	
    private static final Logger log = LoggerFactory.getLogger(RunAutomationTestTask.class);
	
	public static RunAutomationTestTask runAutomationTestTask;

	@PostConstruct
	public void init() {
		runAutomationTestTask = this;
	}
	
    public void runTask(String params) throws IOException {
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(Integer.valueOf(params));
		
		if(null!=taskScheduling){
			String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String taskName ="【"+taskScheduling.getSchedulingName()+ "】_"+time;
			TaskExecute taskExecute = new TaskExecute();
			taskExecute.setSchedulingId(taskScheduling.getSchedulingId());
			taskExecute.setProjectId(taskScheduling.getProjectId());
			taskExecute.setTaskName(taskName);
			taskExecute.setTaskStatus(0);
			taskExecuteService.insertTaskExecute(taskExecute);
			
			String url= "http://"+taskScheduling.getClient().getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/runTask";
			RunTaskEntity runTaskEntity = new RunTaskEntity();
			runTaskEntity.setTaskId(taskExecute.getTaskId().toString());
			runTaskEntity.setSchedulingName(taskScheduling.getSchedulingName());
			runTaskEntity.setLoadPath(taskScheduling.getClientDriverPath());
			try {
				HttpRequest.httpClientPost(url, taskScheduling.getClient(), JSONObject.toJSONString(runTaskEntity),3000);
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				log.error("测试任务执行，远程链接客户端出现异常...");
				taskExecute.setTaskStatus(4);
				taskExecuteService.updateTaskExecute(taskExecute);
			}
		}
    }
    
}
