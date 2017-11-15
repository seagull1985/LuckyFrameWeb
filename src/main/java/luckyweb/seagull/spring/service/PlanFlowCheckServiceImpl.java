package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.PlanFlowCheckDao;
import luckyweb.seagull.spring.entity.PlanFlowCheck;


@Service("planflowcheckService")
public class PlanFlowCheckServiceImpl implements PlanFlowCheckService{
	
	private PlanFlowCheckDao planflowcheckdao;
	
	public PlanFlowCheckDao getPlanFlowCheckDao() {
		return planflowcheckdao;
	}

	@Resource(name = "planflowcheckDao")
	public void setPlanFlowCheckDao(PlanFlowCheckDao planflowcheckDao) {
		this.planflowcheckdao = planflowcheckDao;
	}

	@Override
	public int add(PlanFlowCheck pfc) throws Exception {
		return this.planflowcheckdao.add(pfc);
	}

	@Override
	public void modify(PlanFlowCheck pfc) throws Exception {
		this.planflowcheckdao.modify(pfc);
		
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.planflowcheckdao.delete(id);
		
	}

	@Override
	public PlanFlowCheck load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.planflowcheckdao.load(id);
	}

	private String where(PlanFlowCheck pfc) {
		String where = " where ";
/*		if (!StrLib.isEmpty(String.valueOf(ss.getSectorid()))) {
			where += " sectorid=:sectorid  and ";
		}*/
		if (pfc.getId()!=0) {
			where += " id=:id  and ";
		}
		if (pfc.getStatus()!=0) {
			where += " status=:status  and ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy="order by id desc";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql="from PlanFlowCheck t "+where((PlanFlowCheck)value)+orderBy;
		List list= planflowcheckdao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(PlanFlowCheck pfc) {
		String	hql="select count(*) from PlanFlowCheck t "+where(pfc)+orderBy;
		return planflowcheckdao.findRows(pfc,hql);
	}

}
