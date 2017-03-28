package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.FlowInfoDao;
import luckyweb.seagull.spring.entity.FlowInfo;

@Service("flowinfoService")
public class FlowInfoServiceImpl implements FlowInfoService{
	
	private FlowInfoDao flowinfodao;
	
	public FlowInfoDao getFlowInfoDao() {
		return flowinfodao;
	}

	@Resource(name = "flowinfoDao")
	public void setFlowInfoDao(FlowInfoDao flowinfoDao) {
		this.flowinfodao = flowinfoDao;
	}

	public List listphaseinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select min(id),phasename from QA_FLOWINFO group by phasename order by min(id)");	
	}
	
	public List listnodeinfo(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select min(id),phasenodename from QA_FLOWINFO "
				+ "where phaseid = (select phaseid from QA_FLOWINFO where id = "+id+") group by phasenodename order by min(id)");	
	}
	
	public List listentryinfo(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select min(id),checkentry from QA_FLOWINFO "
				+ "where phaseid = (select phaseid from QA_FLOWINFO where id = "+id+") and "
				+ "phasenodeid = (select phasenodeid from QA_FLOWINFO where id = "+id+") group by checkentry order by min(id)");	
	}
	
	public List listphaseallinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select id,phasename from QA_FLOWINFO");	
	}
	
	public List listnodeallinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select id,phasenodename from QA_FLOWINFO");	
	}
	
	public List listentryallinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select id,checkentry from QA_FLOWINFO");
	}

	@Override
	public FlowInfo load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.get(id);
	}
	
}
