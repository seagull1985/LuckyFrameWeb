package luckyweb.seagull.spring.entity;

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
@Entity(name="projectVersion")
public class ProjectVersion implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int versionid;
	private String versionnumber;
	private String plan_launchdate;
	private String actually_launchdate;
	private String startactually_launchdate;
	private String endactually_launchdate;
	private String plan_devstart;
	private String plan_devend;
	private String actually_devstart;
	private String actually_devend;
	private String plan_teststart;
	private String plan_testend;
	private String actually_teststart;
	private String actually_testend;
	private int plan_demand;
	private int actually_demand;
	private int codestandard_zd;
	private int codestandard_yz;
	private int codestandard_zy;
	private double codeline;
	private int testcasenum;
	private int changetestingreturn;
	private String dev_member;
	private String test_member;
	private String human_cost;
	private String human_costdev;
	private String human_costtest;
	private String per_dev;
	private String per_test;
	private String code_DI;
	private String qualityreview;
	private String imprint;
	private String remark;
	private int projectid;
	private String devtime_deviation;
	private String devdelay_days;
	private String testtime_deviation;
	private String testdelay_days;
	private String protime_deviation;
	private String prodelay_days;
	private int bug_zm;
	private int bug_yz;
	private int bug_yb;
	private int bug_ts;
	private int versiontype;
	private double perdemand;
	private String zt_versionlink;
	private SectorProjects sectorProjects;
	
	public String getZt_versionlink() {
		return zt_versionlink;
	}

	public void setZt_versionlink(String zt_versionlink) {
		this.zt_versionlink = zt_versionlink;
	}

	public double getPerdemand() {
		return perdemand;
	}

	public void setPerdemand(double perdemand) {
		this.perdemand = perdemand;
	}
	public int getVersiontype() {
		return versiontype;
	}
	public void setVersiontype(int versiontype) {
		this.versiontype = versiontype;
	}
	
	public int getBug_zm() {
		return bug_zm;
	}
	public void setBug_zm(int bug_zm) {
		this.bug_zm = bug_zm;
	}
	public int getBug_yz() {
		return bug_yz;
	}
	public void setBug_yz(int bug_yz) {
		this.bug_yz = bug_yz;
	}
	public int getBug_yb() {
		return bug_yb;
	}
	public void setBug_yb(int bug_yb) {
		this.bug_yb = bug_yb;
	}
	public int getBug_ts() {
		return bug_ts;
	}
	public void setBug_ts(int bug_ts) {
		this.bug_ts = bug_ts;
	}

	public String getStartactually_launchdate() {
		return startactually_launchdate;
	}
	public void setStartactually_launchdate(String startactually_launchdate) {
		this.startactually_launchdate = startactually_launchdate;
	}
	public String getEndactually_launchdate() {
		return endactually_launchdate;
	}
	public void setEndactually_launchdate(String endactually_launchdate) {
		this.endactually_launchdate = endactually_launchdate;
	}

	public String getProtime_deviation() {
		return protime_deviation;
	}
	public void setProtime_deviation(String protime_deviation) {
		this.protime_deviation = protime_deviation;
	}
	public String getProdelay_days() {
		return prodelay_days;
	}
	public void setProdelay_days(String prodelay_days) {
		this.prodelay_days = prodelay_days;
	}

	public String getDevtime_deviation() {
		return devtime_deviation;
	}
	public void setDevtime_deviation(String devtime_deviation) {
		this.devtime_deviation = devtime_deviation;
	}
	public String getDevdelay_days() {
		return devdelay_days;
	}
	public void setDevdelay_days(String devdelay_days) {
		this.devdelay_days = devdelay_days;
	}
	public String getTesttime_deviation() {
		return testtime_deviation;
	}
	public void setTesttime_deviation(String testtime_deviation) {
		this.testtime_deviation = testtime_deviation;
	}
	public String getTestdelay_days() {
		return testdelay_days;
	}
	public void setTestdelay_days(String testdelay_days) {
		this.testdelay_days = testdelay_days;
	}
	
	public int getVersionid() {
		return versionid;
	}
	public void setVersionid(int versionid) {
		this.versionid = versionid;
	}
	public String getVersionnumber() {
		return versionnumber;
	}
	public void setVersionnumber(String versionnumber) {
		this.versionnumber = versionnumber;
	}
	public String getPlan_launchdate() {
		return plan_launchdate;
	}
	public void setPlan_launchdate(String plan_launchdate) {
		this.plan_launchdate = plan_launchdate;
	}
	public String getActually_launchdate() {
		return actually_launchdate;
	}
	public void setActually_launchdate(String actually_launchdate) {
		this.actually_launchdate = actually_launchdate;
	}
	public String getImprint() {
		return imprint;
	}
	public void setImprint(String imprint) {
		this.imprint = imprint;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public SectorProjects getSectorProjects() {
		return sectorProjects;
	}
	public void setSectorProjects(SectorProjects sectorProjects) {
		this.sectorProjects = sectorProjects;
	}
	public int getPlan_demand() {
		return plan_demand;
	}
	public void setPlan_demand(int plan_demand) {
		this.plan_demand = plan_demand;
	}
	public int getActually_demand() {
		return actually_demand;
	}
	public void setActually_demand(int actually_demand) {
		this.actually_demand = actually_demand;
	}

	public int getCodestandard_zd() {
		return codestandard_zd;
	}

	public void setCodestandard_zd(int codestandard_zd) {
		this.codestandard_zd = codestandard_zd;
	}

	public int getCodestandard_yz() {
		return codestandard_yz;
	}

	public void setCodestandard_yz(int codestandard_yz) {
		this.codestandard_yz = codestandard_yz;
	}

	public int getCodestandard_zy() {
		return codestandard_zy;
	}

	public void setCodestandard_zy(int codestandard_zy) {
		this.codestandard_zy = codestandard_zy;
	}

	public double getCodeline() {
		return codeline;
	}
	public void setCodeline(double codeline) {
		this.codeline = codeline;
	}
	public int getTestcasenum() {
		return testcasenum;
	}
	public void setTestcasenum(int testcasenum) {
		this.testcasenum = testcasenum;
	}
	public int getChangetestingreturn() {
		return changetestingreturn;
	}
	public void setChangetestingreturn(int changetestingreturn) {
		this.changetestingreturn = changetestingreturn;
	}
	public String getDev_member() {
		return dev_member;
	}
	public void setDev_member(String dev_member) {
		this.dev_member = dev_member;
	}
	public String getTest_member() {
		return test_member;
	}
	public void setTest_member(String test_member) {
		this.test_member = test_member;
	}
	public String getHuman_cost() {
		return human_cost;
	}
	public void setHuman_cost(String human_cost) {
		this.human_cost = human_cost;
	}
	public String getPer_dev() {
		return per_dev;
	}
	public void setPer_dev(String per_dev) {
		this.per_dev = per_dev;
	}
	public String getPer_test() {
		return per_test;
	}
	public void setPer_test(String per_test) {
		this.per_test = per_test;
	}
	public String getCode_DI() {
		return code_DI;
	}
	public void setCode_DI(String code_DI) {
		this.code_DI = code_DI;
	}
	public String getQualityreview() {
		return qualityreview;
	}
	public void setQualityreview(String qualityreview) {
		this.qualityreview = qualityreview;
	}
	public String getPlan_devstart() {
		return plan_devstart;
	}
	public void setPlan_devstart(String plan_devstart) {
		this.plan_devstart = plan_devstart;
	}
	public String getPlan_devend() {
		return plan_devend;
	}
	public void setPlan_devend(String plan_devend) {
		this.plan_devend = plan_devend;
	}
	public String getActually_devstart() {
		return actually_devstart;
	}
	public void setActually_devstart(String actually_devstart) {
		this.actually_devstart = actually_devstart;
	}
	public String getActually_devend() {
		return actually_devend;
	}
	public void setActually_devend(String actually_devend) {
		this.actually_devend = actually_devend;
	}
	public String getPlan_teststart() {
		return plan_teststart;
	}
	public void setPlan_teststart(String plan_teststart) {
		this.plan_teststart = plan_teststart;
	}
	public String getPlan_testend() {
		return plan_testend;
	}
	public void setPlan_testend(String plan_testend) {
		this.plan_testend = plan_testend;
	}
	public String getActually_teststart() {
		return actually_teststart;
	}
	public void setActually_teststart(String actually_teststart) {
		this.actually_teststart = actually_teststart;
	}
	public String getActually_testend() {
		return actually_testend;
	}
	public void setActually_testend(String actually_testend) {
		this.actually_testend = actually_testend;
	}

	public String getHuman_costdev() {
		return human_costdev;
	}

	public void setHuman_costdev(String human_costdev) {
		this.human_costdev = human_costdev;
	}

	public String getHuman_costtest() {
		return human_costtest;
	}

	public void setHuman_costtest(String human_costtest) {
		this.human_costtest = human_costtest;
	}
	
}
