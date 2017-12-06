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
@Entity(name="reviewInfo")
public class ReviewInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int review_id;
	private String bug_description;
	private String status;
	private String duty_officer;
	private String confirm_date;
	private int projectid;
	private String version;
	private String review_type;
	private String review_date;
	private String review_object;
	private String review_result;
	private String remark;
	private String corrective;
	private String result_Confirmor;
	private String projectname;
	

	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getResult_Confirmor() {
		return result_Confirmor;
	}
	public void setResult_Confirmor(String result_Confirmor) {
		this.result_Confirmor = result_Confirmor;
	}
	public String getReview_result() {
		return review_result;
	}
	public void setReview_result(String review_result) {
		this.review_result = review_result;
	}
	public String getCorrective() {
		return corrective;
	}
	public void setCorrective(String corrective) {
		this.corrective = corrective;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReview_object() {
		return review_object;
	}
	public void setReview_object(String review_object) {
		this.review_object = review_object;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public String getBug_description() {
		return bug_description;
	}
	public void setBug_description(String bug_description) {
		this.bug_description = bug_description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDuty_officer() {
		return duty_officer;
	}
	public void setDuty_officer(String duty_officer) {
		this.duty_officer = duty_officer;
	}
	public String getConfirm_date() {
		return confirm_date;
	}
	public void setConfirm_date(String confirm_date) {
		this.confirm_date = confirm_date;
	}


}
