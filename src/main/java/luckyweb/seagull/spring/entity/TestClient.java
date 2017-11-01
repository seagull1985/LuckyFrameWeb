package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="testclient")
public class TestClient implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String clientip;
	private String name;
	private String projectper;
	private String projectpername;
	private int status;
	private int checkinterval;
	private String remark;
	
	public String getProjectpername() {
		return projectpername;
	}
	public void setProjectpername(String projectpername) {
		this.projectpername = projectpername;
	}
	public int getCheckinterval() {
		return checkinterval;
	}
	public void setCheckinterval(int checkinterval) {
		this.checkinterval = checkinterval;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectper() {
		return projectper;
	}
	public void setProjectper(String projectper) {
		this.projectper = projectper;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
