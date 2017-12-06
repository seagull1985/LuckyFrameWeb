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
@Entity(name="flowCheck")
public class FlowCheck implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int checkid;
	private int projectid;
	private String projectphase;
	private String phasenode;
	private String checkentry;
	private String checkresult;
	private String checkdate;
	private String checkdescriptions;
	private String stateupdate;
	private String updatedate;
	private String remark;
	private SectorProjects sectorProjects;
	private String checkstartdate;
	private String checkenddate;
	private String versionnum;

	public String getVersionnum() {
		return versionnum;
	}
	public void setVersionnum(String versionnum) {
		this.versionnum = versionnum;
	}
	public String getCheckstartdate() {
		return checkstartdate;
	}
	public void setCheckstartdate(String checkstartdate) {
		this.checkstartdate = checkstartdate;
	}
	public String getCheckenddate() {
		return checkenddate;
	}
	public void setCheckenddate(String checkenddate) {
		this.checkenddate = checkenddate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCheckid() {
		return checkid;
	}
	public void setCheckid(int checkid) {
		this.checkid = checkid;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getProjectphase() {
		return projectphase;
	}
	public void setProjectphase(String projectphase) {
		this.projectphase = projectphase;
	}
	public String getPhasenode() {
		return phasenode;
	}
	public void setPhasenode(String phasenode) {
		this.phasenode = phasenode;
	}
	public String getCheckentry() {
		return checkentry;
	}
	public void setCheckentry(String checkentry) {
		this.checkentry = checkentry;
	}
	public String getCheckresult() {
		return checkresult;
	}
	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getCheckdescriptions() {
		return checkdescriptions;
	}
	public void setCheckdescriptions(String checkdescriptions) {
		this.checkdescriptions = checkdescriptions;
	}
	public String getStateupdate() {
		return stateupdate;
	}
	public void setStateupdate(String stateupdate) {
		this.stateupdate = stateupdate;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public SectorProjects getSectorProjects() {
		return sectorProjects;
	}
	public void setSectorProjects(SectorProjects sectorProjects) {
		this.sectorProjects = sectorProjects;
	}

}
