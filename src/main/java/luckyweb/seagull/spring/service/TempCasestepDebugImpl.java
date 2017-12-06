package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.TempCasestepDebugDao;
import luckyweb.seagull.spring.entity.TempCasestepDebug;
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
@Service("tempcasestepdebugService")
public class TempCasestepDebugImpl implements TempCasestepDebugService{
	
	private TempCasestepDebugDao tempcasestepdebugdao;
	
	public TempCasestepDebugDao getTempCasestepDebugDao() {
		return tempcasestepdebugdao;
	}

	@Resource(name = "tempcasestepdebugDao")
	public void setTempCasestepDebugDao(TempCasestepDebugDao tempcasestepdebugdao) {
		this.tempcasestepdebugdao = tempcasestepdebugdao;
	}
	
	@Override
	public TempCasestepDebug load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.tempcasestepdebugdao.load(id);
	}
	
	private String where(TempCasestepDebug tcd) {
		String where = " where ";
		if (!StrLib.isEmpty(tcd.getSign())) {
			where += " sign=:sign  and ";
		}
		if (!StrLib.isEmpty(tcd.getExecutor())) {
			where += " executor=:executor  and ";
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
		String	hql=" from TempCasestepDebug  "+where((TempCasestepDebug)value)+orderBy;
		List list= tempcasestepdebugdao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public int findRows(TempCasestepDebug tcd) {
		String hql="select count(*) from TempCasestepDebug "+where(tcd);
		return tempcasestepdebugdao.findRows(tcd, hql);
	}
	
	
	@Override
	public int add(TempCasestepDebug tcd) throws Exception {
		return this.tempcasestepdebugdao.add(tcd);
	}
	
	@Override
	public void delete(String sign,String executor) throws Exception {
		String hql="delete from TempCasestepDebug where sign='"+sign+"' and executor='"+executor+"'";
		this.tempcasestepdebugdao.delete(hql);
	}
	
	@Override
	public void deleteforob(TempCasestepDebug tcd) throws Exception{
		this.tempcasestepdebugdao.deleteforob(tcd);
	}
	
	@Override
	public void modify(TempCasestepDebug tcd) throws Exception{
		this.tempcasestepdebugdao.modify(tcd);
	}
	
	@Override
	public List<TempCasestepDebug> getList(String sign,String executor) throws Exception {
		// TODO Auto-generated method stub
		return this.tempcasestepdebugdao.getParamsList(" from TempCasestepDebug where sign='"+sign+"' and executor='"+executor+"' order by id");
	}
}
