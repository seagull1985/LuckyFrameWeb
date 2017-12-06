package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.PlanFlowCheckDao;
import luckyweb.seagull.spring.entity.PlanFlowCheck;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
		if (where.length() == PublicConst.WHERENUM) {
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
