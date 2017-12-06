package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestJobs;

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
public interface TestJobsDao {
	/**
	 * 增加调度任务
	 * @param tjob
	 * @return
	 * @throws Exception
	 */
	public int add(TestJobs tjob) throws Exception;

	/**
	 * 修改调度任务
	 * @param tjob
	 * @throws Exception
	 */
	public void modify(TestJobs tjob) throws Exception;

	/**
	 * 修改调度任务状态
	 * @param tjob
	 * @throws Exception
	 */
	public void modifyState(TestJobs tjob) throws Exception;

	/**
	 * 修改调度任务明细
	 * @param tjob
	 * @throws Exception
	 */
	public void modifyInfo(TestJobs tjob) throws Exception;

	/**
	 * 删除调度任务
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;

	/**
	 * 获取调度任务列表
	 * @param tjob
	 * @return
	 * @throws Exception
	 */
	public List<TestJobs> list(TestJobs tjob) throws Exception;

	/**
	 * 获取调度任务列表
	 * @return
	 * @throws Exception
	 */
	public List<TestJobs> list() throws Exception;

	/**
	 * 获取调度任务实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestJobs load(int id) throws Exception;

	/**
	 * 获取调度任务列表
	 * @param name
	 * @param cmdType
	 * @param planPath
	 * @return
	 * @throws Exception
	 */
	public List<TestJobs> load(String name, String cmdType, String planPath)
			throws Exception;

	/**
	 * 获取调度任务实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestJobs get(int id) throws Exception;

	/**
	 * 调度任务分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	/**
	 * 调度任务分页条数
	 * @param jobs
	 * @param hql
	 * @return
	 */
	public int findRows(TestJobs jobs,String hql);
	
	/**
	 * 获取调度任务列表
	 * @param hql
	 * @return
	 */
	public List findJobsList(String hql);
	
}
