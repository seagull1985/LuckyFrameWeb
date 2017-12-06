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
