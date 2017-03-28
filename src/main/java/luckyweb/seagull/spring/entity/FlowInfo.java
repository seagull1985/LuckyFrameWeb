package luckyweb.seagull.spring.entity;

import javax.persistence.Entity;

@Entity(name="flowInfo")
public class FlowInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int phaseid;
	private String phasename;
	private int phasenodeid;
	private String phasenodename;
	private int checkentryid;
	private String checkentry;
	private String remark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPhaseid() {
		return phaseid;
	}
	public void setPhaseid(int phaseid) {
		this.phaseid = phaseid;
	}
	public String getPhasename() {
		return phasename;
	}
	public void setPhasename(String phasename) {
		this.phasename = phasename;
	}
	public int getPhasenodeid() {
		return phasenodeid;
	}
	public void setPhasenodeid(int phasenodeid) {
		this.phasenodeid = phasenodeid;
	}
	public String getPhasenodename() {
		return phasenodename;
	}
	public void setPhasenodename(String phasenodename) {
		this.phasenodename = phasenodename;
	}
	public int getCheckentryid() {
		return checkentryid;
	}
	public void setCheckentryid(int checkentryid) {
		this.checkentryid = checkentryid;
	}
	public String getCheckentry() {
		return checkentry;
	}
	public void setCheckentry(String checkentry) {
		this.checkentry = checkentry;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
