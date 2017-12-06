package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.ProjectVersionDao;
import luckyweb.seagull.spring.dao.SectorProjectsDao;
import luckyweb.seagull.spring.entity.ProjectVersion;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.util.StrLib;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Service("projectversionService")
public class ProjectVersionServiceImpl implements ProjectsVersionService{
	
	private ProjectVersionDao projectversiondao;
	
	public ProjectVersionDao getProjectVersionDao() {
		return projectversiondao;
	}

	@Resource(name = "projectversionDao")
	public void setProjectVersionDao(ProjectVersionDao projectversiondao) {
		this.projectversiondao = projectversiondao;
	}

	@Override
	public int add(ProjectVersion projectversion) throws Exception {
		return this.projectversiondao.add(projectversion);
	}

	@Override
	public void modify(ProjectVersion projectversion) throws Exception {
		this.projectversiondao.modify(projectversion);
		
	}


	@Override
	public void delete(int id) throws Exception {
		this.projectversiondao.delete(id);
		
	}

	@Override
	public List<ProjectVersion> list(int projectid) throws Exception {
		// TODO Auto-generated method stub
		return this.projectversiondao.list("from ProjectVersion t where versiontype = 1 and projectid="+projectid+" order by versionid desc");		
	}
	
	@Override
	public List<ProjectVersion> list(int projectid,String startdate,String enddate) throws Exception {
		// TODO Auto-generated method stub
		if(startdate==""||startdate==null){
			startdate = "0";
		}
		if(enddate==""||enddate==null){
			enddate = "9999-99-99";
		}
		return this.projectversiondao.list("from ProjectVersion t where versiontype = 1 and projectid="+projectid+"and t.actually_launchdate >= '"+startdate+"' and t.actually_launchdate<='"+enddate+"' order by versionid");		
	}

	@Override
	public ProjectVersion load(int versionid) throws Exception {
		// TODO Auto-generated method stub
		return this.projectversiondao.load(versionid);
	}


	private String where(ProjectVersion pv) {
		String where = " where ";
/*		if (!StrLib.isEmpty(String.valueOf(ss.getSectorid()))) {
			where += " sectorid=:sectorid  and ";
		}*/
		if (pv.getProjectid()!=0&&pv.getProjectid()!=PublicConst.STATUS99) {
			where += " projectid=:projectid  and ";
		}
		if (!StrLib.isEmpty(pv.getStartactually_launchdate())) {
			where += " actually_launchdate>=:startactually_launchdate  and ";
		}
		if (!StrLib.isEmpty(pv.getEndactually_launchdate())) {
			where += " actually_launchdate<=:endactually_launchdate  and ";
		}
		if (!StrLib.isEmpty(pv.getVersionnumber())){
			where += " (versionnumber like :versionnumber  or ";
		}
		if (!StrLib.isEmpty(pv.getImprint())){
			where += " imprint like :imprint)  or ";
		}
		
		if (where.length() == PublicConst.WHERENUM) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by versiontype,actually_launchdate desc,versionid desc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from ProjectVersion  "+where((ProjectVersion)value)+orderBy;
		List list= projectversiondao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(ProjectVersion projectversion) {
		String hql="select count(*) from ProjectVersion "+where(projectversion);
		return projectversiondao.findRows(projectversion, hql);
	}

	@Override
	public List<ProjectVersion> findProjectsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listavgpro(String startdate,String enddate) throws Exception {
		// TODO Auto-generated method stub
		if(startdate==""||startdate==null){
			startdate = "0";
		}
		if(enddate==""||enddate==null){
			enddate = "9999-99-99";
		}
		return this.projectversiondao.listavgpro("select t.projectid,count(projectid),"
				+ "ROUND(avg(cast(t.per_dev as DECIMAL)),2),ROUND(avg(cast(t.per_test as DECIMAL)),2),ROUND(avg(cast(t.protime_deviation as DECIMAL)),2),ROUND(avg(cast(t.code_di as DECIMAL)),2),ROUND(avg(cast(t.actually_demand as DECIMAL)/cast(t.plan_demand as DECIMAL)),4)*100 "
				+ "from QA_PROJECTVERSION t where t.versiontype = 1 and t.actually_launchdate >= '"+startdate+"' and t.actually_launchdate<='"+enddate+"' group by t.projectid  order by t.projectid ");	
	}
	
	@Override
	public List findByPagereport(int offset, int pageSize,String startdate,String enddate) throws Exception {
		// TODO Auto-generated method stub
		String hql = "select projectid,sum(t.plan_demand),sum(t.actually_demand),"
				+ "COUNT(CASE WHEN cast(t.prodelay_days as SIGNED)>0 THEN 1 ELSE NULL  END) 延迟版本,"
				+ "COUNT(CASE WHEN cast(t.prodelay_days as SIGNED)<=0 THEN 1 ELSE NULL  END) 未延迟版本,"
				+ "sum(t.changetestingreturn),sum(t.bug_zm),sum(t.bug_yz),sum(t.bug_yb),sum(t.bug_ts),sum(t.human_costdev),sum(t.human_costtest),0 as perdemand,0 as perdelay "
				+ "from QA_PROJECTVERSION t where t.versiontype = 1 and t.actually_launchdate >= '"+startdate+"' and t.actually_launchdate<='"+enddate+"' group by t.projectid  order by t.projectid";
		return this.projectversiondao.findByPagereport(hql, offset, pageSize);
	}
	
	@Override
	public int findRowsreport(String startdate,String enddate) {
		String hql="select projectid from QA_PROJECTVERSION t where t.versiontype = 1 and t.actually_launchdate >= '"+startdate+"' and t.actually_launchdate<='"+enddate+"' group by t.projectid";
		return projectversiondao.findRowsreport(hql);
	}

}
