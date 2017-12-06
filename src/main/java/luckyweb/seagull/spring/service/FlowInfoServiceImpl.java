package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.FlowInfoDao;
import luckyweb.seagull.spring.entity.FlowInfo;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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

	@Override
	public List listphaseinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select min(id),phasename from QA_FLOWINFO group by phasename order by min(id)");	
	}
	
	@Override
	public List listnodeinfo(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select min(id),phasenodename from QA_FLOWINFO "
				+ "where phaseid = (select phaseid from QA_FLOWINFO where id = "+id+") group by phasenodename order by min(id)");	
	}
	
	@Override
	public List listentryinfo(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select min(id),checkentry from QA_FLOWINFO "
				+ "where phaseid = (select phaseid from QA_FLOWINFO where id = "+id+") and "
				+ "phasenodeid = (select phasenodeid from QA_FLOWINFO where id = "+id+") group by checkentry order by min(id)");	
	}
	
	@Override
	public List listphaseallinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select id,phasename from QA_FLOWINFO");	
	}
	
	@Override
	public List listnodeallinfo() throws Exception {
		// TODO Auto-generated method stub
		return this.flowinfodao.listcheckinfo("select id,phasenodename from QA_FLOWINFO");	
	}
	
	@Override
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
