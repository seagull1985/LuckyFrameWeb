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
@Entity(name="review")
public class Review implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int projectid;
	private String version;
	private String review_type;
	private String review_date;
	private int bug_num;
	private int repair_num;
	private String confirm_date;
	private String review_object;
	private String review_result;
	private SectorProjects sectorProjects;
	private String result_Confirmor;
	private String remark;
	private String review_startdate;
	private String review_enddate;
	
	public String getReview_startdate() {
		return review_startdate;
	}
	public void setReview_startdate(String review_startdate) {
		this.review_startdate = review_startdate;
	}
	public String getReview_enddate() {
		return review_enddate;
	}
	public void setReview_enddate(String review_enddate) {
		this.review_enddate = review_enddate;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReview_type() {
		return review_type;
	}
	public void setReview_type(String review_type) {
		this.review_type = review_type;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}
	public int getBug_num() {
		return bug_num;
	}
	public void setBug_num(int bug_num) {
		this.bug_num = bug_num;
	}
	public int getRepair_num() {
		return repair_num;
	}
	public void setRepair_num(int repair_num) {
		this.repair_num = repair_num;
	}
	public String getConfirm_date() {
		return confirm_date;
	}
	public void setConfirm_date(String confirm_date) {
		this.confirm_date = confirm_date;
	}
	public String getReview_object() {
		return review_object;
	}
	public void setReview_object(String review_object) {
		this.review_object = review_object;
	}
	public String getReview_result() {
		return review_result;
	}
	public void setReview_result(String review_result) {
		this.review_result = review_result;
	}
	public SectorProjects getSectorProjects() {
		return sectorProjects;
	}
	public void setSectorProjects(SectorProjects sectorProjects) {
		this.sectorProjects = sectorProjects;
	}
	public String getResult_Confirmor() {
		return result_Confirmor;
	}
	public void setResult_Confirmor(String result_Confirmor) {
		this.result_Confirmor = result_Confirmor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
