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
@Entity(name="testLogdetail")
public class TestLogdetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int logid;
	private TestCasedetail testCasedetail=new TestCasedetail();
	private Timestamp logtime;
	private String detail;
	private String detailShort;
	private String step;
	private String imgname;
	private String logGrade;	
	private int taskid;
	private int caseid;
	
	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getDetailShort() {
		return detailShort;
	}

	public void setDetailShort(String detailShort) {
		this.detailShort = detailShort;
	}	
	
	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public int getCaseid() {
		return caseid;
	}

	public void setCaseid(int caseid) {
		this.caseid = caseid;
	}

	public String getLogGrade() {
		return logGrade;
	}

	public void setLogGrade(String logGrade) {
		this.logGrade = logGrade;
	}

	public TestLogdetail() {
	}

	public TestLogdetail(int logid, TestCasedetail testCasedetail,
			Timestamp logtime,  String detail) {
		this.logid = logid;
		this.testCasedetail = testCasedetail;
		this.logtime = logtime;
		this.detail = detail;
	}

	public int getLogid() {
		return this.logid;
	}

	public void setLogid(int logid) {
		this.logid = logid;
	}

	public TestCasedetail getTestCasedetail() {
		return this.testCasedetail;
	}

	public void setTestCasedetail(TestCasedetail testCasedetail) {
		this.testCasedetail = testCasedetail;
	}

	public java.sql.Timestamp getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Timestamp logtime) {
		this.logtime = logtime;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
