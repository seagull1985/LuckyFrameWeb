package luckyweb.seagull.spring.service;

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
public interface TestJobsService {
	/**
	 * 增加任务调度
	 * @param tjob
	 * @return
	 * @throws Exception
	 */
	public int add(TestJobs tjob)throws Exception;
	/**
	 * 修改任务调度
	 * @param tjob
	 * @throws Exception
	 */
	public void modify(TestJobs tjob)throws Exception;
	/**
	 * 修改任务调度状态
	 * @param tjob
	 * @throws Exception
	 */
	public void modifyState(TestJobs tjob)throws Exception;
	/**
	 * 删除任务调度
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception;
	/**
	 * 获取任务调度列表
	 * @param tjob
	 * @return
	 * @throws Exception
	 */
	public List<TestJobs> list(TestJobs tjob)throws Exception;
	/**
	 * 获取任务调度列表
	 * @return
	 * @throws Exception
	 */
	public List<TestJobs> list()throws Exception;
	/**
	 * 判断任务
	 * @param name
	 * @param cmdType
	 * @param planPath
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String name,String cmdType,String planPath)throws Exception;

	/**
	 * 获取任务调度对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestJobs load(int id)throws Exception;
	/**
	 * 获取任务调度对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TestJobs get(int id)throws Exception;

	/**
	 * 任务调度分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 任务调度分页条数
	 * @param jobs
	 * @return
	 */
	public int findRows(TestJobs jobs ) ;

	/**
	 * 获取任务调度列表
	 * @return
	 */
	public List<TestJobs> findJobsList();
	/**
	 * 获取路径
	 * @param projectid
	 * @return
	 */
	public List getpathList(int projectid);
}
