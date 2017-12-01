package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="publicCaseParams")
public class PublicCaseParams implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String paramsname;
	private String paramsvalue;
	private String remark;
	private int projectid;
	private String projectname;
	
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
	public String getParamsname() {
		return paramsname;
	}
	public void setParamsname(String paramsname) {
		this.paramsname = paramsname;
	}
	public String getParamsvalue() {
		return paramsvalue;
	}
	public void setParamsvalue(String paramsvalue) {
		this.paramsvalue = paramsvalue;
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

}
