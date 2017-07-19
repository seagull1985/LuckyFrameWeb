package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ProjectModuleDao;
import luckyweb.seagull.spring.entity.ProjectModule;

@Service("projectModuleService")
public class ProjectModuleServiceImpl implements ProjectModuleService{
	
	private ProjectModuleDao projectmoduleDao;
	
	public ProjectModuleDao getProjectModuleDao() {
		return projectmoduleDao;
	}

	@Resource(name = "projectModuleDao")
	public void setProjectModuleDao(ProjectModuleDao projectmoduleDao) {
		this.projectmoduleDao = projectmoduleDao;
	}
	
	@Override
	public int add(ProjectModule projectmodule) throws Exception {
		return this.projectmoduleDao.add(projectmodule);
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.projectmoduleDao.delete(id);		
	}
	
	@Override
	public void modify(ProjectModule projectmodule) throws Exception {
		this.projectmoduleDao.modify(projectmodule);
		
	}
	
	@Override
	public ProjectModule load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.projectmoduleDao.load(id);
	}

	private String where(ProjectModule projectmodule) {
		String where = " where ";
		if (projectmodule.getProjectid()!=0&&projectmodule.getProjectid()!=99) {
			where += " projectid =:projectid  and ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by id desc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from ProjectModule  "+where((ProjectModule)value)+orderBy;
		List list= projectmoduleDao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(ProjectModule projectmodule) {
		String hql="select count(*) from ProjectModule "+where(projectmodule);
		return projectmoduleDao.findRows(projectmodule, hql);
	}
	
	@Override
	public List<ProjectModule> getModuleList(){
		// TODO Auto-generated method stub
		List<ProjectModule> list= null;
		try {
			list=this.projectmoduleDao.getList(" from ProjectModule order by id");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ProjectModule> getModuleListByProjectid(int projectid){
		// TODO Auto-generated method stub
		List<ProjectModule> list= null;
		try {
			list=this.projectmoduleDao.getList(" from ProjectModule where projectid="+projectid+" order by id");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
