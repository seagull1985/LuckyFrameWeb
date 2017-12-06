package luckyweb.seagull.spring.entity;

// default package
// Generated 2015-4-21 15:55:06 by Hibernate Tools 3.2.2.GA

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
@Entity(name="testCasedetail")
public class TestCasedetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private TestTaskexcute testTaskexcute=new TestTaskexcute();
	private String caseno;
	private String caseversion;
	private Timestamp casetime;
	private String casename;
	private String casestatus;
	private String casestatus_str;
	private String  projName;

	private String startDate;
	private String endDate;
	private String beforeDate;
	

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeforeDate() {
		return beforeDate;
	}

	public void setBeforeDate(String beforeDate) {
		this.beforeDate = beforeDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCasestatus_str() {
		return casestatus_str;
	}

	public void setCasestatus_str(String casestatus_str) {
		this.casestatus_str = casestatus_str;
	}

	private int taskId;


	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public TestCasedetail() {
	}

	public TestCasedetail(int id, TestTaskexcute testTaskexcute,
			String caseno,  String caseversion,
			Timestamp casetime, String casename, String casestatus) {
		this.id = id;
		this.testTaskexcute = testTaskexcute;
		this.caseno = caseno;
		this.caseversion = caseversion;
		this.casetime = casetime;
		this.casename = casename;
		this.casestatus = casestatus;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestTaskexcute getTestTaskexcute() {
		return this.testTaskexcute;
	}

	public void setTestTaskexcute(TestTaskexcute testTaskexcute) {
		this.testTaskexcute = testTaskexcute;
	}

	
	public String getCaseno() {
		return caseno;
	}

	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}

	public String getCaseversion() {
		return this.caseversion;
	}

	public void setCaseversion(String caseversion) {
		this.caseversion = caseversion;
	}

	public Timestamp getCasetime() {
		return this.casetime;
	}

	public void setCasetime(Timestamp casetime) {
		this.casetime = casetime;
	}

	public String getCasename() {
		return this.casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

	public String getCasestatus() {
		return this.casestatus;
	}

	public void setCasestatus(String casestatus) {
		this.casestatus = casestatus;
	}

	

}
