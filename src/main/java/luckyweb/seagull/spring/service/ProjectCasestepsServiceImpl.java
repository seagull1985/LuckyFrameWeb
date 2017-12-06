package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ProjectCasestepsDao;
import luckyweb.seagull.spring.entity.ProjectCasesteps;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
		return this.casestepsDao.steps(" from ProjectCasesteps where caseid="+caseid+" order by stepnum");
	}
	
	@Override
	public void delforcaseid(int id) throws Exception {
		String hql="delete from ProjectCasesteps where caseid=:caseId";
		this.casestepsDao.delete(hql, id);
	}
}
