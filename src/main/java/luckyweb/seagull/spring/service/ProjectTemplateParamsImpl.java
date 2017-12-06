package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.ProjectTemplateParamsDao;
import luckyweb.seagull.spring.entity.ProjectTemplateParams;


/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Service("projecttemplateparamsService")
public class ProjectTemplateParamsImpl implements ProjectTemplateParamsService{
	
	private ProjectTemplateParamsDao projecttemplateparamsdao;
	
	public ProjectTemplateParamsDao getProjectTemplateParamsDao() {
		return projecttemplateparamsdao;
	}

	@Resource(name = "projecttemplateparamsDao")
	public void setProjectTemplateParamsDao(ProjectTemplateParamsDao projecttemplateparamsdao) {
		this.projecttemplateparamsdao = projecttemplateparamsdao;
	}
	
	@Override
	public ProjectTemplateParams load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.projecttemplateparamsdao.load(id);
	}
	
	private String where(ProjectTemplateParams ptp) {
		String where = " where ";
		if (ptp.getTemplateid()!=0) {
			where += " templateid=:templateid  and ";
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
		String	hql=" from ProjectTemplateParams  "+where((ProjectTemplateParams)value)+orderBy;
		List list= projecttemplateparamsdao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public int findRows(ProjectTemplateParams ptp) {
		String hql="select count(*) from ProjectTemplateParams "+where(ptp);
		return projecttemplateparamsdao.findRows(ptp, hql);
	}
	
	
	@Override
	public int add(ProjectTemplateParams ptp) throws Exception {
		return this.projecttemplateparamsdao.add(ptp);
	}
	
	@Override
	public void delete(int templateid) throws Exception {
		String hql="delete from ProjectTemplateParams where templateid="+templateid;
		this.projecttemplateparamsdao.delete(hql);
	}
	
	@Override
	public void deleteforob(ProjectTemplateParams ptp) throws Exception{
		this.projecttemplateparamsdao.deleteforob(ptp);
	}
	
	@Override
	public void modify(ProjectTemplateParams ptp) throws Exception{
		this.projecttemplateparamsdao.modify(ptp);
	}
	
	@Override
	public List<ProjectTemplateParams> getParamsList(int templateid) throws Exception {
		// TODO Auto-generated method stub
		return this.projecttemplateparamsdao.getParamsList(" from ProjectTemplateParams where templateid="+templateid+" order by id");
	}
}
