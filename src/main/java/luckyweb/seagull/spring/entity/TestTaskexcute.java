package luckyweb.seagull.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Entity(name = "testTaskexcute")
public class TestTaskexcute implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String taskId;
	private int casetotalCount = 0;
	private int casesuccCount = 0;
	private int casefailCount = 0;
	private int caselockCount = 0;
	/**
	 * 未执行用例数
	 */
	private int casenoexecCount = 0;
	private TestJobs testJob = new TestJobs();

	private int jobid;
	private String caseIsExec;
	private Timestamp finishtime;
	/**
	 * 0 未执行   1执行中  2 成功    4失败
	 */
	private String taskStatus;   

	private String startDate;
	private String endDate;
	private String name;
	
	private String taskName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getCasenoexecCount() {
		return casenoexecCount;
	}

	public void setCasenoexecCount(int casenoexecCount) {
		this.casenoexecCount = casenoexecCount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	private String taskStatus_str;

	public String getTaskStatus_str() {
		return taskStatus_str;
	}

	public void setTaskStatus_str(String taskStatus_str) {
		this.taskStatus_str = taskStatus_str;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaseIsExec() {
		return caseIsExec;
	}

	public void setCaseIsExec(String caseIsExec) {
		this.caseIsExec = caseIsExec;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Timestamp getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(Timestamp finishtime) {
		this.finishtime = finishtime;
	}

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public TestTaskexcute() {

	}

	public TestTaskexcute(int id, String taskId, int casetotalCount,
			int casesuccCount, int casefailCount, int caselockCount,
			TestJobs testJob, Timestamp createTime) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.casetotalCount = casetotalCount;
		this.casesuccCount = casesuccCount;
		this.casefailCount = casefailCount;
		this.caselockCount = caselockCount;
		this.testJob = testJob;
		this.createTime = createTime;
	}

	private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCasetotalCount() {
		return casetotalCount;
	}

	public void setCasetotalCount(int casetotalCount) {
		this.casetotalCount = casetotalCount;
	}

	public int getCasesuccCount() {
		return casesuccCount;
	}

	public void setCasesuccCount(int casesuccCount) {
		this.casesuccCount = casesuccCount;
	}

	public int getCasefailCount() {
		return casefailCount;
	}

	public void setCasefailCount(int casefailCount) {
		this.casefailCount = casefailCount;
	}

	public int getCaselockCount() {
		return caselockCount;
	}

	public void setCaselockCount(int caselockCount) {
		this.caselockCount = caselockCount;
	}

	public TestJobs getTestJob() {
		return testJob;
	}

	public void setTestJob(TestJobs testJob) {
		this.testJob = testJob;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
