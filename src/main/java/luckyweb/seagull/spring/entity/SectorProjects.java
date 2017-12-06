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
@Entity(name="sectorProjects")
public class SectorProjects implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int projectid;
	private String projectname;
	private String projectmanager;
	private int sectorid;
	private int projecttype;
	private String projectsign;
	private String departmenthead;
	private String departmentname;
	
	public String getDepartmenthead() {
		return departmenthead;
	}
	public void setDepartmenthead(String departmenthead) {
		this.departmenthead = departmenthead;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getProjectsign() {
		return projectsign;
	}
	public void setProjectsign(String projectsign) {
		this.projectsign = projectsign;
	}
	public int getProjecttype() {
		return projecttype;
	}
	public void setProjecttype(int projecttype) {
		this.projecttype = projecttype;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getProjectmanager() {
		return projectmanager;
	}
	public void setProjectmanager(String projectmanager) {
		this.projectmanager = projectmanager;
	}
	public int getSectorid() {
		return sectorid;
	}
	public void setSectorid(int sectorid) {
		this.sectorid = sectorid;
	}

}
