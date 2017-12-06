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
@Entity(name="planflowCheck")
public class PlanFlowCheck implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int projectid;
	private String versionnum;
	private String checkentryid;
	private String plandate;
	private int status;
	private String checkphase;
	private String checknode;
	
	public String getCheckphase() {
		return checkphase;
	}
	public void setCheckphase(String checkphase) {
		this.checkphase = checkphase;
	}
	public String getChecknode() {
		return checknode;
	}
	public void setChecknode(String checknode) {
		this.checknode = checknode;
	}
	private SectorProjects sectorProjects;
	
	public SectorProjects getSectorProjects() {
		return sectorProjects;
	}
	public void setSectorProjects(SectorProjects sectorProjects) {
		this.sectorProjects = sectorProjects;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getVersionnum() {
		return versionnum;
	}
	public void setVersionnum(String versionnum) {
		this.versionnum = versionnum;
	}
	public String getCheckentryid() {
		return checkentryid;
	}
	public void setCheckentryid(String checkentryid) {
		this.checkentryid = checkentryid;
	}
	public String getPlandate() {
		return plandate;
	}
	public void setPlandate(String plandate) {
		this.plandate = plandate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
