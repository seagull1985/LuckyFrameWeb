package rmi.model;

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
public class RunBatchCaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectname;
    private String taskid;
    private String batchcase;
    
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getBatchcase() {
		return batchcase;
	}
	public void setBatchcase(String batchcase) {
		this.batchcase = batchcase;
	}

}
