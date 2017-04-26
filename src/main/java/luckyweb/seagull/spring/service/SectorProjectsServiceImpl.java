package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.SectorProjectsDao;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.util.StrLib;

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
	public void modifyState(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		this.sectorprojectsdao.delete(sectorprojects);
	}

	@Override
	public List<SectorProjects> list(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		return this.sectorprojectsdao.list(sectorprojects);
	}

	@Override
	public List<SectorProjects> list() throws Exception {
		// TODO Auto-generated method stub
		return this.sectorprojectsdao.list();
	}

	@Override
	public boolean isExist(String name, String cmdType, String planPath)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
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
			where += " projectname=:projectname  and ";
		}
		else if (!StrLib.isEmpty(sp.getProjectname())) {
			where += " projectmanager=:projectmanager  and ";
		}
		
		
		if (where.length() == 7) {
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
}
