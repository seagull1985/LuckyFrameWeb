package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowCheck;

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
public interface FlowCheckDao {
	/**
	 * 增加流程检查
	 * @param flowcheck
	 * @return
	 * @throws Exception
	 */
	public int add(FlowCheck flowcheck) throws Exception;
	/**
	 * 修改流程检查
	 * @param flowcheck
	 * @throws Exception
	 */
	public void modify(FlowCheck flowcheck) throws Exception;
	/**
	 * 修改流程检查状态
	 * @param flowcheck
	 * @throws Exception
	 */
	public void modifyState(FlowCheck flowcheck) throws Exception;
	/**
	 * 修改流程检查版本号
	 * @param sql
	 * @throws Exception
	 */
	public void modifyVersion(String sql) throws Exception;
	/**
	 * 删除流程检查
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	/**
	 * 取流程检查列表
	 * @param flowcheck
	 * @return
	 * @throws Exception
	 */
	public List<FlowCheck> list(FlowCheck flowcheck) throws Exception;
	/**
	 * 取流程检查列表
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<FlowCheck> list(String hql) throws Exception;
	/**
	 * 取流程检查详细列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List listcheckinfo(String sql) throws Exception;
	/**
	 * 获取流程检查实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FlowCheck load(int id) throws Exception;

	/**
	 * 取流程检查指定字段
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String get(String sql) throws Exception;
	/**
	 * 流程检查分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);
	/**
	 * 流程检查分页条数
	 * @param flowcheck
	 * @param hql
	 * @return
	 */
	public int findRows(FlowCheck flowcheck,String hql);
	/**
	 * 流程检查分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPageTable(final String hql, final Object value,final int offset, final int pageSize);
	/**
	 * 流程检查分页条数
	 * @param flowcheck
	 * @param hql
	 * @return
	 */
	public int findRowsTable(FlowCheck flowcheck,String hql);
	/**
	 * 流程检查列表
	 * @return
	 */
	public List<FlowCheck> findJobsList();
}
