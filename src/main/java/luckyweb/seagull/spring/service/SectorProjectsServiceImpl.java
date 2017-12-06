package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.SectorProjectsDao;
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
@Service("sectorprojectsService")
public class SectorProjectsServiceImpl implements SectorProjectsService{
	
	private SectorProjectsDao sectorprojectsdao;
	
	public SectorProjectsDao getSectorProjectsDao() {
		return sectorprojectsdao;
	}

	@Resource(name = "sectorprojectsDao")
	public void setSectorProjectsDao(SectorProjectsDao sectorprojectsdao) {
		this.sectorprojectsdao = sectorprojectsdao;
	}

	@Override
	public int add(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		return this.sectorprojectsdao.add(sectorprojects);
	}

	@Override
	public void modify(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		this.sectorprojectsdao.modify(sectorprojects);
	}

	@Override
	public void delete(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		this.sectorprojectsdao.delete(sectorprojects);
	}

	@Override
	public Object load(int projectid) throws Exception {
		// TODO Auto-generated method stub
		return this.sectorprojectsdao.findproject(projectid);
	}
	
	@Override
	public SectorProjects loadob(int projectid) throws Exception {
		// TODO Auto-generated method stub
		return this.sectorprojectsdao.load(projectid);
	}
	
	@Override
	public int getid(String projectname) throws Exception {
		// TODO Auto-generated method stub
		return this.sectorprojectsdao.getid(projectname);
	}

	@Override
	public SectorProjects get(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private String where(SectorProjects sp) {
		String where = " where ";
/*		if (!StrLib.isEmpty(String.valueOf(ss.getSectorid()))) {
			where += " sectorid=:sectorid  and ";
		}*/
		if (!StrLib.isEmpty(sp.getProjectmanager())) {
			where += " projectname like :projectname  or ";
		}if (!StrLib.isEmpty(sp.getProjectname())) {
			where += " projectmanager like :projectmanager  or ";
		}if (!StrLib.isEmpty(sp.getProjectname())) {
			where += " projectsign like :projectsign  or ";
		}if (where.length() == PublicConst.WHERENUM) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by projectid asc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from SectorProjects  "+where((SectorProjects)value)+orderBy;
		List list= sectorprojectsdao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(SectorProjects sectorprojects) {
		String hql="select count(*) from SectorProjects "+where(sectorprojects);
		return sectorprojectsdao.findRows(sectorprojects,hql);
	}

	@Override
	public List<SectorProjects> findProjectsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int projectrow(int id) {
		int row=0;
		row=sectorprojectsdao.projectrow("SELECT projectid from qa_accident where projectid="+id);
		row=sectorprojectsdao.projectrow("SELECT projectid from qa_flowcheck where projectid="+id)+row;
		row=sectorprojectsdao.projectrow("SELECT projectid from qa_planflowcheck where projectid="+id)+row;
		row=sectorprojectsdao.projectrow("SELECT projectid from qa_projectversion where projectid="+id)+row;
		row=sectorprojectsdao.projectrow("SELECT projectid from qa_review where projectid="+id)+row;
		return row;
	}
	
	@Override
	public int projectrowfordmtp(int sectorid) {
		int row=0;
		row=sectorprojectsdao.projectrow("SELECT projectid from QA_SECTORPROJECTS where sectorid="+sectorid);
		return row;
	}
	
	@Override
	public List<SectorProjects> getAllProject(){
		return sectorprojectsdao.getAllProject();
	}
}
