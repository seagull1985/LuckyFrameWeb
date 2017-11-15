package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="projectModule")
public class ProjectModule implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int projectid;
	private int mprojectid;
	private String modulename;
	private int pid;
	
	public int getMprojectid() {
		return mprojectid;
	}
	public void setMprojectid(int mprojectid) {
		this.mprojectid = mprojectid;
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
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
    
}
