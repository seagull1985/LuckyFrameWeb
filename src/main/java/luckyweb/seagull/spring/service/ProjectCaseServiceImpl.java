package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ProjectCaseDao;
import luckyweb.seagull.spring.entity.ProjectCase;

@Service("projectCaseService")
public class ProjectCaseServiceImpl implements ProjectCaseService{
	
	private ProjectCaseDao projectcaseDao;
	
	public ProjectCaseDao getProjectCaseDao() {
		return projectcaseDao;
	}

	@Resource(name = "projectCaseDao")
	public void setProjectCaseDao(ProjectCaseDao projectcaseDao) {
		this.projectcaseDao = projectcaseDao;
	}
	
	@Override
	public int add(ProjectCase projectcase) throws Exception {
		return this.projectcaseDao.add(projectcase);
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.projectcaseDao.delete(id);		
	}
	
	@Override
	public void modify(ProjectCase projectcase) throws Exception {
		this.projectcaseDao.modify(projectcase);
		
	}
	
	@Override
	public ProjectCase load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.projectcaseDao.load(id);
	}

	private String where(ProjectCase projectcase) {
		String where = " where ";
		if (projectcase.getProjectid()!=0&&projectcase.getProjectid()!=99) {
			where += " projectid =:projectid  and ";
		}if (projectcase.getModuleid()!=0) {
			where += " moduleid =:moduleid  and ";
		}if (null!=projectcase.getSign()&&!"".equals(projectcase.getSign())) {
			where += " sign like :sign  or ";
		}if (null!=projectcase.getName()&&!"".equals(projectcase.getName())) {
			where += " name like :name  or ";
		}if (null!=projectcase.getOperationer()&&!"".equals(projectcase.getOperationer())) {
			where += " operationer like :operationer  or ";
		}if (null!=projectcase.getRemark()&&!"".equals(projectcase.getRemark())) {
			where += " remark like :remark  or ";
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
		String	hql=" from ProjectCase  "+where((ProjectCase)value)+orderBy;
		List list= projectcaseDao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(ProjectCase projectcase) {
		String hql="select count(*) from ProjectCase "+where(projectcase);
		return projectcaseDao.findRows(projectcase, hql);
	}
	
	@Override
	public ProjectCase getCaseBySign(String sign) throws Exception {
		// TODO Auto-generated method stub
		return this.projectcaseDao.getList(" from ProjectCase where sign='"+sign+"' order by id").get(0);
	}

	@Override
	public String getCaseMaxIndex(int projectid) throws Exception {
		// TODO Auto-generated method stub
		return this.projectcaseDao.getCaseMaxIndex("select IFNULL(MAX(projectindex),0) from project_case where projectid="+projectid);
	}
}
