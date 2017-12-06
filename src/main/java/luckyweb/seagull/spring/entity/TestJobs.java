package luckyweb.seagull.spring.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
	/**
	 * 线程数
	 */
	private int threadCount;
	/**
	 * 自定义时间
	 */
	private int time;
	private String timeType;
	/**
	 * 是否发送邮件
	 */
	private String isSendMail;
	/**
	 * 收件人
	 */
	private String emailer;
	private String testlinkname;
	/**
	 * 是否构建
	 */
	private String isbuilding;
	/**
	 * 构建在JENKINS中的名称
	 */
	private String buildname;
	/**
	 * 是否重启
	 */
	private String isrestart;
	/**
	 * 构建在JENKINS中的名称
	 */
	private String restartcomm;
	/**
	 * 0:接口自动化 1:WebDriver自动化
	 */
	private int extype;
	/**
	 * 0:ie 1:火狐 2:谷歌 3：Edge
	 */
	private Integer browsertype; 
	/**
	 * 任务超时时间 单位：分钟
	 */
	private Integer timeout;
	/**
	 * 客户端IP
	 */
	private String clientip; 
	/**
	 * 客户端测试驱动桩路径
	 */
	private String clientpath;
	/**
	 * 项目类型 0testlink 1系统内项目
	 */
	private Integer projecttype; 
	/**
	 * 系统内项目ID
	 */
	private Integer projectid; 
	/**
	 * 系统内项目关联计划ID
	 */
	private Integer planid;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @param"开始日期的时间格式应为：yyyy-MM-dd"
	 */
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param"开始时间的时间格式应为：HH:mm:ss"
	 */
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
