package com.luckyframe.common.utils.client;

import java.io.Serializable;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 注意对象必须继承Serializable
 * @author seagull
 */
public class RunTaskEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/*调度任务名称*/
	private String schedulingName;
	/*任务执行ID*/
    private String taskId;
    /*驱动加载路径*/
    private String loadPath;
    
	public String getSchedulingName() {
		return schedulingName;
	}
	public void setSchedulingName(String schedulingName) {
		this.schedulingName = schedulingName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getLoadPath() {
		return loadPath;
	}
	public void setLoadPath(String loadPath) {
		this.loadPath = loadPath;
	}

}
