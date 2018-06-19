package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.OperationLog;

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
public interface OperationLogDao {
	/**
	 * 操作日志实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OperationLog load(int id) throws Exception;
	/**
	 * 增加操作日志
	 * @param oplog
	 * @return
	 * @throws Exception
	 */
	public int add(OperationLog oplog) throws Exception;
	/**
	 * 操作日志分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	/**
	 * 操作日志分页条数
	 * @param oplog
	 * @param hql
	 * @return
	 */
	public int findRows(OperationLog oplog, String hql); 
	/**
	 * 删除操作日志
	 * @param hql
	 * @throws Exception
	 */
	public void delete(String hql) throws Exception;
	
	/**
	 * 
	 * @param sql
	 * @return 根据SQL查询数据
	 * @throws Exception
	 */
	public List listinfo(String sql) throws Exception;
}
