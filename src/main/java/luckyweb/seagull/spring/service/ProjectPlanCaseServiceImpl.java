package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ProjectPlanCaseDao;
import luckyweb.seagull.spring.entity.ProjectPlanCase;

@Service("projectPlanCaseService")
public class ProjectPlanCaseServiceImpl implements ProjectPlanCaseService{
	
	private ProjectPlanCaseDao projectplancaseDao;
	
	public ProjectPlanCaseDao getProjectPlanDao() {
		return projectplancaseDao;
	}

	@Resource(name = "projectPlanCaseDao")
	public void setProjectPlanDao(ProjectPlanCaseDao projectplancaseDao) {
		this.projectplancaseDao = projectplancaseDao;
	}
	
	@Override
	public int add(ProjectPlanCase projectplancase) throws Exception {
		return this.projectplancaseDao.add(projectplancase);
	}
	
	@Override
	public void delete(ProjectPlanCase projectplancase) throws Exception {
		this.projectplancaseDao.delete(projectplancase);		
	}
	
	@Override
	public void delforplanid(int id) throws Exception {
		String hql="delete from ProjectPlanCase where planid=:planId";
		this.projectplancaseDao.delete(hql, id);
	}
	
	@Override
	public void modify(ProjectPlanCase projectplancase) throws Exception {
		this.projectplancaseDao.modify(projectplancase);
		
	}
	
	@Override
	public ProjectPlanCase load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.projectplancaseDao.load(id);
	}

	private String where(ProjectPlanCase projectplancase) {
		String where = " where ";
        if (projectplancase.getCaseid()!=0) {
			where += " caseid =:caseid  and ";
		}if (projectplancase.getPlanid()!=0) {
			where += " planid =:planid  and ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by caseid desc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from ProjectPlanCase  "+where((ProjectPlanCase)value)+orderBy;
		List list= projectplancaseDao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(ProjectPlanCase projectplancase) {
		String hql="select id from ProjectPlanCase "+where(projectplancase);
		return projectplancaseDao.findRows(projectplancase, hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectPlanCase> getcases(int planid) throws Exception {
		// TODO Auto-generated method stub
		return this.projectplancaseDao.getList(" from ProjectPlanCase where planid="+planid+" order by priority,caseid");
	}
	
}
