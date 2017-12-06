package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.ProjectPlanCaseDao;
import luckyweb.seagull.spring.entity.ProjectPlanCase;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
		if (where.length() == PublicConst.WHERENUM) {
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
		String hql="select count(*) from ProjectPlanCase "+where(projectplancase);
		return projectplancaseDao.findRows(projectplancase, hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectPlanCase> getcases(int planid) throws Exception {
		// TODO Auto-generated method stub
		return this.projectplancaseDao.getList(" from ProjectPlanCase where planid="+planid+" order by priority,caseid");
	}
	
	@Override
	public ProjectPlanCase getplancase(int planid,int caseid) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectPlanCase> ppcs=this.projectplancaseDao.getList(" from ProjectPlanCase where planid="+planid+" and caseid="+caseid+" order by id desc");
		if(ppcs.size()==0){
			return new ProjectPlanCase();
		}
		return ppcs.get(0);
	}
}
