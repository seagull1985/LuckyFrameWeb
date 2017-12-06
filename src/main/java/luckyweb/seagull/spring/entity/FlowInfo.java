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
