package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="projectCase")
public class ProjectCase implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String sign;
    private String name;
	private int projectid;
	private int moduleid;
	private String modulename;
	private int projectindex;
	private String projectname;
    private String remark;
    private int casetype;
    private String time;
    private String operationer;
    private int priority;
    private int checktype;
    
    
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public int getModuleid() {
		return moduleid;
	}
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}
	public int getProjectindex() {
		return projectindex;
	}
	public void setProjectindex(int projectindex) {
		this.projectindex = projectindex;
	}
	public int getChecktype() {
		return checktype;
	}
	public void setChecktype(int checktype) {
		this.checktype = checktype;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCasetype() {
		return casetype;
	}
	public void setCasetype(int casetype) {
		this.casetype = casetype;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	
    
}
