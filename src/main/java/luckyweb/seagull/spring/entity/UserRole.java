package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="userrole")
public class UserRole implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String role;
	private String permission;
	private String opprojectid;
	
	public String getOpprojectid() {
		return opprojectid;
	}
	public void setOpprojectid(String opprojectid) {
		this.opprojectid = opprojectid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}



}
