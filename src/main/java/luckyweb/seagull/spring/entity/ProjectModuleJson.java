package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="projectModuleJson")
public class ProjectModuleJson implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private boolean isParent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setisParent(boolean isParent) {
		this.isParent = isParent;
	}
    
}
