package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ProjectCasestepsDao;
import luckyweb.seagull.spring.entity.ProjectCasesteps;

@Service("projectCasestepsService")
public class ProjectCasestepsServiceImpl implements ProjectCasestepsService{
	
	private ProjectCasestepsDao casestepsDao;
	
	public ProjectCasestepsDao getProjectCasestepsDao() {
		return casestepsDao;
	}

	@Resource(name = "projectCasestepsDao")
	public void setProjectCasestepsDao(ProjectCasestepsDao casestepsDao) {
		this.casestepsDao = casestepsDao;
	}
	
	@Override
	public int add(ProjectCasesteps casesteps) throws Exception {
		return this.casestepsDao.add(casesteps);
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.casestepsDao.delete(id);		
	}
	
	@Override
	public void modify(ProjectCasesteps casesteps) throws Exception {
		this.casestepsDao.modify(casesteps);
		
	}
	
	@Override
	public ProjectCasesteps load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.casestepsDao.load(id);
	}

	@Override
	public List<ProjectCasesteps> getSteps(int caseid) throws Exception {
		// TODO Auto-generated method stub
		return this.casestepsDao.steps(" from ProjectCasesteps where caseid="+caseid+"order by stepnum");
	}
	
	@Override
	public void delforcaseid(int id) throws Exception {
		String hql="delete from ProjectCasesteps where caseid=:caseId";
		this.casestepsDao.delete(hql, id);
	}
}
