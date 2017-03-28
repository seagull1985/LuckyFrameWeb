package rmi.model;

import java.io.Serializable;

//注意对象必须继承Serializable
public class RunCaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectname;
    private String taskid;
    private String testCaseExternalId;
    private String version;
    
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
	public String getTestCaseExternalId() {
		return testCaseExternalId;
	}
	public void setTestCaseExternalId(String testCaseExternalId) {
		this.testCaseExternalId = testCaseExternalId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

}
