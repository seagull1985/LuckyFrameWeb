package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.ProjectCaseDao;
import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.util.DateLib;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
		if (projectcase.getProjectid()!=0&&projectcase.getProjectid()!=PublicConst.STATUS99) {
			where += " projectid =:projectid  and ";
		}if (projectcase.getModuleid()!=0&&projectcase.getModuleidarr().length>0) {
			where += " moduleid in (:moduleid)  and ";
		}if (null!=projectcase.getSign()&&!"".equals(projectcase.getSign())) {
			where += " (sign like :sign  or ";
		}if (null!=projectcase.getName()&&!"".equals(projectcase.getName())) {
			where += " name like :name  or ";
		}if (null!=projectcase.getOperationer()&&!"".equals(projectcase.getOperationer())) {
			where += " operationer like :operationer  or ";
		}if (null!=projectcase.getRemark()&&!"".equals(projectcase.getRemark())) {
			where += " remark like :remark)  or ";
		}
		if (where.length() == PublicConst.WHERENUM) {
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
	
	@Override
	public String getBeforeDayRows(int beforedays) throws Exception {
		// TODO Auto-generated method stub
		String date=DateLib.beforNdFormat("yyyy-MM-dd", beforedays);
		return this.projectcaseDao.getCaseMaxIndex("select count(*) from project_case t where t.time<'"+date+"'");
	}
}
