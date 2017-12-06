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
@Entity(name="accident")
public class Accident implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int projectid;
	private String accstatus;
	private String eventtime;
	private String reporter;
	private String reporttime;
	private String accdescription;
	private String acclevel;
	private String causalanalysis;
	private String causaltype;
	private String consequenceanalysis;
	private SectorProjects sectorProjects;
	private String correctiveaction;
	private String resolutiontime;
	private String resolutioner;
	private String preventiveaction;
	private String preventiver;
	private String preventiveplandate;
	private String preventiveaccdate;
	private String accstarttime;
	private String accendtime;
	private Long trouble_duration;
	private Long impact_time;
	private String strtrouble_duration;
	private String strimpact_time;
	private String filename;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getStrtrouble_duration() {
		return strtrouble_duration;
	}
	public void setStrtrouble_duration(String strtrouble_duration) {
		this.strtrouble_duration = strtrouble_duration;
	}
	public String getStrimpact_time() {
		return strimpact_time;
	}
	public void setStrimpact_time(String strimpact_time) {
		this.strimpact_time = strimpact_time;
	}
	
	public Long getTrouble_duration() {
		return trouble_duration;
	}
	public void setTrouble_duration(Long trouble_duration) {
		this.trouble_duration = trouble_duration;
	}
	public Long getImpact_time() {
		return impact_time;
	}
	public void setImpact_time(Long impact_time) {
		this.impact_time = impact_time;
	}
	
	public String getAccstarttime() {
		return accstarttime;
	}
	public void setAccstarttime(String accstarttime) {
		this.accstarttime = accstarttime;
	}
	public String getAccendtime() {
		return accendtime;
	}
	public void setAccendtime(String accendtime) {
		this.accendtime = accendtime;
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
	public String getAccstatus() {
		return accstatus;
	}
	public void setAccstatus(String accstatus) {
		this.accstatus = accstatus;
	}
	public String getEventtime() {
		return eventtime;
	}
	public void setEventtime(String eventtime) {
		this.eventtime = eventtime;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	public String getAccdescription() {
		return accdescription;
	}
	public void setAccdescription(String accdescription) {
		this.accdescription = accdescription;
	}
	public String getAcclevel() {
		return acclevel;
	}
	public void setAcclevel(String acclevel) {
		this.acclevel = acclevel;
	}
	public String getCausalanalysis() {
		return causalanalysis;
	}
	public void setCausalanalysis(String causalanalysis) {
		this.causalanalysis = causalanalysis;
	}
	public String getCausaltype() {
		return causaltype;
	}
	public void setCausaltype(String causaltype) {
		this.causaltype = causaltype;
	}
	public String getConsequenceanalysis() {
		return consequenceanalysis;
	}
	public void setConsequenceanalysis(String consequenceanalysis) {
		this.consequenceanalysis = consequenceanalysis;
	}
	public SectorProjects getSectorProjects() {
		return sectorProjects;
	}
	public void setSectorProjects(SectorProjects sectorProjects) {
		this.sectorProjects = sectorProjects;
	}
	public String getCorrectiveaction() {
		return correctiveaction;
	}
	public void setCorrectiveaction(String correctiveaction) {
		this.correctiveaction = correctiveaction;
	}
	public String getResolutiontime() {
		return resolutiontime;
	}
	public void setResolutiontime(String resolutiontime) {
		this.resolutiontime = resolutiontime;
	}
	public String getResolutioner() {
		return resolutioner;
	}
	public void setResolutioner(String resolutioner) {
		this.resolutioner = resolutioner;
	}
	public String getPreventiveaction() {
		return preventiveaction;
	}
	public void setPreventiveaction(String preventiveaction) {
		this.preventiveaction = preventiveaction;
	}
	public String getPreventiver() {
		return preventiver;
	}
	public void setPreventiver(String preventiver) {
		this.preventiver = preventiver;
	}
	public String getPreventiveplandate() {
		return preventiveplandate;
	}
	public void setPreventiveplandate(String preventiveplandate) {
		this.preventiveplandate = preventiveplandate;
	}
	public String getPreventiveaccdate() {
		return preventiveaccdate;
	}
	public void setPreventiveaccdate(String preventiveaccdate) {
		this.preventiveaccdate = preventiveaccdate;
	}

}
