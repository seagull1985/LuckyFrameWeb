package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.PlanFlowCheck;


@Transactional
public interface PlanFlowCheckService {
	public int add(PlanFlowCheck pfc)throws Exception;
	public void modify(PlanFlowCheck pfc)throws Exception;
	public void delete(int id)throws Exception;
	
	
	public PlanFlowCheck load(int id)throws Exception;

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(PlanFlowCheck pfc) ;
}
