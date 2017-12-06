package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TempCasestepDebug;

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
public interface TempCasestepDebugDao {
	/**
	 * 获取WEB端用例调试日志实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TempCasestepDebug load(int id) throws Exception;

	/**
	 * 增加WEB端用例调试日志
	 * @param tcd
	 * @return
	 * @throws Exception
	 */
	public int add(TempCasestepDebug tcd) throws Exception;
	/**
	 * WEB端用例调试日志分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	/**
	 * WEB端用例调试日志分页条数
	 * @param tcd
	 * @param hql
	 * @return
	 */
	public int findRows(TempCasestepDebug tcd, String hql); 
	/**
	 * 删除WEB端用例调试日志
	 * @param hql
	 * @throws Exception
	 */
	public void delete(String hql) throws Exception;
	/**
	 * 删除WEB端用例调试日志
	 * @param tcd
	 * @throws Exception
	 */
	public void deleteforob(TempCasestepDebug tcd) throws Exception;
	/**
	 * 修改WEB端用例调试日志
	 * @param tcd
	 * @throws Exception
	 */
	public void modify(TempCasestepDebug tcd) throws Exception;
	/**
	 * 获取WEB端用例调试日志列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<TempCasestepDebug> getParamsList(String sql) throws Exception;
}
