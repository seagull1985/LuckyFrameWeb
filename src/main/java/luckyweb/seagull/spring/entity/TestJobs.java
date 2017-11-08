package luckyweb.seagull.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TestJobs")
public class TestJobs implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String taskName;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private Timestamp runTime;
	private String remark;

	private String planproj;
	private String state;
	private String state_str;
	private int threadCount;// 线程数
	private int time;// 自定义时间
	private String timeType;

	private String isSendMail;// 是否发送邮件
	private String emailer;// 收件人
	private String testlinkname;

	private String isbuilding;// 是否构建
	private String buildname;// 构建在JENKINS中的名称

	private String isrestart;// 是否重
	private String restartcomm;// 构建在JENKINS中的名称
	private int extype; // 0:接口自动化 1:WebDriver自动化
	private Integer browsertype; // 0:ie 1:火狐 2:谷歌 3：Edge
	private Integer timeout; // 任务超时时间 单位：分钟
	private String clientip; // 客户端IP
	private String clientpath; // 客户端测试驱动桩路径
	private Integer projecttype; // 项目类型 0testlink 1系统内项目
	private Integer projectid; // 系统内项目ID
	private Integer planid; // 系统内项目关联计划ID
	private String taskType;
	private String startTimestr;
	private String endTimestr;
	private Timestamp createTime;
	private String noEndDate;

	public String getClientpath() {
		return clientpath;
	}

	public void setClientpath(String clientpath) {
		this.clientpath = clientpath;
	}

	public Integer getProjecttype() {
		return projecttype;
	}

	public void setProjecttype(Integer projecttype) {
		this.projecttype = projecttype;
	}

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public Integer getPlanid() {
		return planid;
	}

	public void setPlanid(Integer planid) {
		this.planid = planid;
	}

	public String getClientip() {
		return clientip;
	}

	public void setClientip(String clientip) {
		this.clientip = clientip;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getBrowsertype() {
		return browsertype;
	}

	public void setBrowsertype(Integer browsertype) {
		this.browsertype = browsertype;
	}

	public int getExtype() {
		return extype;
	}

	public void setExtype(int extype) {
		this.extype = extype;
	}

	public String getIsrestart() {
		return isrestart;
	}

	public void setIsrestart(String isrestart) {
		this.isrestart = isrestart;
	}

	public String getRestartcomm() {
		return restartcomm;
	}

	public void setRestartcomm(String restartcomm) {
		this.restartcomm = restartcomm;
	}

	public String getIsbuilding() {
		return isbuilding;
	}

	public void setIsbuilding(String isbuilding) {
		this.isbuilding = isbuilding;
	}

	public String getBuildname() {
		return buildname;
	}

	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getTestlinkname() {
		return testlinkname;
	}

	public void setTestlinkname(String testlinkname) {
		this.testlinkname = testlinkname;
	}

	public String getIsSendMail() {
		return isSendMail;
	}

	public void setIsSendMail(String isSendMail) {
		this.isSendMail = isSendMail;
	}

	public String getEmailer() {
		return emailer;
	}

	public void setEmailer(String emailer) {
		this.emailer = emailer;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getState_str() {
		return state_str;
	}

	public void setState_str(String state_str) {
		this.state_str = state_str;
	}

	private boolean showRun = true;

	public TestJobs() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// @NotEmpty(message="任务名称不能为空")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	// @NotEmpty(message="开始日期的时间格式应为：yyyy-MM-dd")
	// @DateTimeFormat(pattern="yyyy-MM-dd")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	// @NotEmpty(message="开始时间的时间格式应为：HH:mm:ss")
	// @DateTimeFormat(pattern="HH:mm:ss")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Timestamp getRunTime() {
		return runTime;
	}

	public void setRunTime(Timestamp runTime) {
		this.runTime = runTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlanproj() {
		return planproj;
	}

	public void setPlanproj(String planproj) {
		this.planproj = planproj;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStartTimestr() {
		return startTimestr;
	}

	public void setStartTimestr(String startTimestr) {
		this.startTimestr = startTimestr;
	}

	public String getEndTimestr() {
		return endTimestr;
	}

	public void setEndTimestr(String endTimestr) {
		this.endTimestr = endTimestr;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getNoEndDate() {
		return noEndDate;
	}

	public void setNoEndDate(String noEndDate) {
		this.noEndDate = noEndDate;
	}

	public boolean isShowRun() {
		return showRun;
	}

	public void setShowRun(boolean showRun) {
		this.showRun = showRun;
	}

}
