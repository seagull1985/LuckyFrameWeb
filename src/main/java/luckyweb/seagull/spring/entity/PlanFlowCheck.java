package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

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
