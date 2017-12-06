package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestTaskexcute;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
public interface TestTastExcuteDao {
	/**
	 * 增加任务执行
	 * @param tjob
	 */
	public void add(TestTaskexcute tjob);

	/**
	 * 获取任务执行列表
	 * @param tjob
	 * @return
	 */
	public List<TestTaskexcute> list(TestTaskexcute tjob);
	/**
	 * 获取任务执行实体
	 * @param id
	 * @return
	 */
	public TestTaskexcute load(int id);

	/**
	 * 获取任务执行实体
	 * @param id
	 * @return
	 */
	public TestTaskexcute get(int id);
	
	/**
	 * 任务执行分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	/**
	 * 任务执行分页条数
	 * @param hql
	 * @param tast
	 * @return
	 */
	public int findRows(String hql,TestTaskexcute tast);
	
	/**
	 * 任务执行列表
	 * @param sql
	 * @return
	 */
	public List findTastList(String sql);
	
	/**
	 * 删除任务执行
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	
	/**
	 * 任务执行列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List listtastinfo(String sql) throws Exception;
	
	/**
	 * 删除任务执行
	 * @param hql
	 * @param jobid
	 * @throws Exception
	 */
	public void deleteForJobid(String hql, int jobid) throws Exception;

}
