package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.PublicCaseParamsDao;
import luckyweb.seagull.spring.entity.PublicCaseParams;
import luckyweb.seagull.spring.entity.TestClient;



@Service("publiccaseparamsService")
public class PublicCaseParamsServiceImpl implements PublicCaseParamsService{
	
	private PublicCaseParamsDao pcpdao;
	
	public PublicCaseParamsDao getPublicCaseParamsDao() {
		return pcpdao;
	}

	@Resource(name = "publiccaseparamsDao")
	public void setPublicCaseParamsDao(PublicCaseParamsDao pcpDao) {
		this.pcpdao = pcpDao;
	}
	
	@Override
	public PublicCaseParams load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.pcpdao.load(id);
	}
	
	public int add(PublicCaseParams pcp) throws Exception{
		return this.pcpdao.add(pcp);
	}
	
	public void modify(PublicCaseParams pcp) throws Exception{
		this.pcpdao.modify(pcp);
	}
	
	public void delete(PublicCaseParams pcp) throws Exception{
		this.pcpdao.delete(pcp);
	}

	private String where(PublicCaseParams pcp) {
		String where = " where ";
		if (pcp.getProjectid()!=0&&pcp.getProjectid()!=99) {
			where += " projectid =:projectid  and ";
		}
		if (null!=pcp.getParamsname()&&!"".equals(pcp.getParamsname())) {
			where += " (paramsname like :paramsname  or ";
		}
		if (null!=pcp.getParamsvalue()&&!"".equals(pcp.getParamsvalue())) {
			where += " paramsvalue like :paramsvalue  or ";
		}
		if (null!=pcp.getRemark()&&!"".equals(pcp.getRemark())) {
			where += " remark like :remark)  or ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by id desc";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from PublicCaseParams  "+where((PublicCaseParams)value)+orderBy;
		List list= pcpdao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(PublicCaseParams pcp) {
		String hql="select count(*) from PublicCaseParams "+where(pcp);
		return pcpdao.findRows(pcp, hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PublicCaseParams getParamByName(String paramsname,String projectid) throws Exception {
		String hql="from PublicCaseParams where projectid='"+projectid+"' and paramsname='"+paramsname+"' order by id asc";
		List<PublicCaseParams> lpcp=pcpdao.listsql(hql);
		if(lpcp.size()==0){
			return null;
		}
		return lpcp.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PublicCaseParams> getParamListByProjectid(String projectid) throws Exception {
		String hql="from PublicCaseParams where projectid='"+projectid+"' order by id asc";
		List<PublicCaseParams> lpcp=pcpdao.listsql(hql);
		return lpcp;
	}
}
