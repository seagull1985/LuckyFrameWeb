package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="projectProtocolTemplate")
public class ProjectProtocolTemplate implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int projectid;
	private String name;
	private String protocoltype;
	private String contentencoding;
	private int connecttimeout;
	private String time;
	private String operationer;
	private String remark;
    private String projectname;
    private String headmsg;
	
    
	public String getHeadmsg() {
		return headmsg;
	}
	public void setHeadmsg(String headmsg) {
		this.headmsg = headmsg;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProtocoltype() {
		return protocoltype;
	}
	public void setProtocoltype(String protocoltype) {
		this.protocoltype = protocoltype;
	}
	public String getContentencoding() {
		return contentencoding;
	}
	public void setContentencoding(String contentencoding) {
		this.contentencoding = contentencoding;
	}
	public int getConnecttimeout() {
		return connecttimeout;
	}
	public void setConnecttimeout(int connecttimeout) {
		this.connecttimeout = connecttimeout;
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
