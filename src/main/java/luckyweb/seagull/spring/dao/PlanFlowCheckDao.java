package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.PlanFlowCheck;

@Transactional
public interface PlanFlowCheckDao {
	public int add(PlanFlowCheck pfc) throws Exception;

	public void modify(PlanFlowCheck pfc) throws Exception;

	public void delete(int id) throws Exception;

	public PlanFlowCheck load(int id) throws Exception;

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(PlanFlowCheck pfc,String hql);
	
	/*public List findRows(String sql);*/

	public List<PlanFlowCheck> list(String hql) throws Exception;
}
