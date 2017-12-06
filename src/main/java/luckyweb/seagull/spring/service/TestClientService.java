package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public interface TestClientService {
	/**
	 * 获取客户端对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestClient load(int id)throws Exception;
	/**
	 * 增加客户端
	 * @param tc
	 * @return
	 * @throws Exception
	 */
	public int add(TestClient tc) throws Exception;
	/**
	 * 修改客户端
	 * @param tc
	 * @throws Exception
	 */
	public void modify(TestClient tc) throws Exception;
	/**
	 * 删除客户端
	 * @param tc
	 * @throws Exception
	 */
	public void delete(TestClient tc) throws Exception;
	/**
	 * 客户端分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);

	/**
	 * 客户端分页条数
	 * @param tc
	 * @return
	 */
	public int findRows(TestClient tc) ;
	/**
	 * 获取客户端列表
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public List<TestClient> getClientListForProid(int projectid)  throws Exception;
	/**
	 * 获取客户端列表
	 * @return
	 * @throws Exception
	 */
	public List<TestClient> getClientList() throws Exception;
	/**
	 * 获取客户端对象
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public TestClient getClient(String ip) throws Exception;
}
