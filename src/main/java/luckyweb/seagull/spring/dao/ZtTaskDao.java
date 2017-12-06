package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ZtTask;

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
public interface ZtTaskDao {
	/**
	 * 获取禅道对象实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ZtTask load(int id) throws Exception;
	/**
	 * 增加禅道对象
	 * @param zt
	 * @return
	 * @throws Exception
	 */
	public int add(ZtTask zt) throws Exception;

	/**
	 * 删除禅道对象
	 * @param hql
	 * @throws Exception
	 */
	public void delete(String hql) throws Exception;
	/**
	 * 禅道对象分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	/**
	 * 禅道对象分页条数
	 * @param zt
	 * @param hql
	 * @return
	 */
	public int findRows(ZtTask zt, String hql);
	/**
	 * 禅道对象报表条数
	 * @param hql
	 * @return
	 */
	public int findRowsreport(String hql);
	/**
	 * 禅道对象报表分页
	 * @param hql
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPagereport(final String hql,final int offset, final int pageSize);
}
