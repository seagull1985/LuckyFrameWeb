package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.TestClientDao;
import luckyweb.seagull.spring.entity.TestClient;


/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Service("testclientService")
public class TestClientServiceImpl implements TestClientService{
	
	private TestClientDao tcdao;
	
	public TestClientDao getTestClientDao() {
		return tcdao;
	}

	@Resource(name = "testclientDao")
	public void setTestClientDao(TestClientDao tcDao) {
		this.tcdao = tcDao;
	}
	
	@Override
	public TestClient load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.tcdao.load(id);
	}
	
	@Override
	public int add(TestClient tc) throws Exception{
		return this.tcdao.add(tc);
	}
	
	@Override
	public void modify(TestClient tc) throws Exception{
		this.tcdao.modify(tc);
	}
	
	@Override
	public void delete(TestClient tc) throws Exception{
		this.tcdao.delete(tc);
	}

	private String where(TestClient tc) {
		String where = " where ";
		if (null!=tc.getClientip()&&!"".equals(tc.getClientip())) {
			where += " clientip like :clientip  or ";
		}
		if (null!=tc.getName()&&!"".equals(tc.getName())) {
			where += " name like :name  or ";
		}
		if (where.length() == PublicConst.WHERENUM) {
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
		String	hql=" from TestClient  "+where((TestClient)value)+orderBy;
		List list= tcdao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(TestClient tc) {
		String hql="select count(*) from TestClient "+where(tc);
		return tcdao.findRows(tc, hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TestClient> getClientListForProid(int projectid) throws Exception {
		String hql="from TestClient where projectper like '%,"+projectid+",%' order by id asc";
		return tcdao.listsql(hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TestClient> getClientList() throws Exception {
		String hql="from TestClient order by id asc";
		return tcdao.listsql(hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TestClient getClient(String ip) throws Exception {
		String hql="from TestClient where clientip='"+ip+"'";
		List<TestClient> ltc=tcdao.listsql(hql);
		if(ltc.size()==0){
			return null;
		}
		return ltc.get(0);
	}
}
