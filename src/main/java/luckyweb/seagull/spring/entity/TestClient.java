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
	private String clientpath;
	private String remark;
	
	public String getClientpath() {
		return clientpath;
	}
	public void setClientpath(String clientpath) {
		this.clientpath = clientpath;
	}
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
