package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.ProjectProtocolTemplateDao;
import luckyweb.seagull.spring.entity.ProjectPlan;
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
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
@Service("projectprotocoltemplateService")
public class ProjectProtocolTemplateImpl implements ProjectProtocolTemplateService{
	
	private ProjectProtocolTemplateDao projectprotocoltemplatedao;
	
	public ProjectProtocolTemplateDao getProjectProtocolTemplateDao() {
		return projectprotocoltemplatedao;
	}

	@Resource(name = "projectprotocoltemplateDao")
	public void setProjectProtocolTemplateDao(ProjectProtocolTemplateDao projectprotocoltemplatedao) {
		this.projectprotocoltemplatedao = projectprotocoltemplatedao;
	}
	
	@Override
	public ProjectProtocolTemplate load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.projectprotocoltemplatedao.load(id);
	}
	
	private String where(ProjectProtocolTemplate ppt) {
		String where = " where ";
		if (ppt.getProjectid()!=0&&ppt.getProjectid()!=PublicConst.STATUS99) {
			where += " projectid=:projectid  and ";
		}
		if (!StrLib.isEmpty(ppt.getProtocoltype())) {
			where += " protocoltype=:protocoltype  and ";
		}
		if (ppt.getOperationer()!=null&&!ppt.getOperationer().equals("")) {
			where += " (operationer like :operationer  or ";
		}
		if (ppt.getName()!=null&&!ppt.getName().equals("")) {
			where += " name like :name  or ";
		}
		if (ppt.getRemark()!=null&&!ppt.getRemark().equals("")) {
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
		String	hql=" from ProjectProtocolTemplate  "+where((ProjectProtocolTemplate)value)+orderBy;
		List list= projectprotocoltemplatedao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public int findRows(ProjectProtocolTemplate ppt) {
		String hql="select count(*) from ProjectProtocolTemplate "+where(ppt);
		return projectprotocoltemplatedao.findRows(ppt, hql);
	}
	
	
	@Override
	public int add(ProjectProtocolTemplate ppt) throws Exception {
		return this.projectprotocoltemplatedao.add(ppt);
	}
	
	@Override
	public void modify(ProjectProtocolTemplate ppt) throws Exception {
		this.projectprotocoltemplatedao.modify(ppt);
		
	}
	
	@Override
	public void delete(int id) throws Exception {
		String hql="delete from ProjectProtocolTemplate where templateid="+id;
		this.projectprotocoltemplatedao.delete(hql);
	}
	
	@Override
	public void deleteforob(ProjectProtocolTemplate ppt) throws Exception {
		this.projectprotocoltemplatedao.deleteforob(ppt);
	}
	
	@Override
	public List findstepsparamList(int steptype,int parentid,String fieldname) throws Exception {
		String sql="select paramvalue,description FROM project_casestepsparams where steptype="+steptype+" and parentid="+parentid+" and fieldname='"+fieldname+"'";
		return this.projectprotocoltemplatedao.findstepsparamList(sql);
	}
}
