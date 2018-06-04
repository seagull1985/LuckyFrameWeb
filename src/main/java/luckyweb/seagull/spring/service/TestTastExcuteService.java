package luckyweb.seagull.spring.service;

import java.util.List;

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
@Transactional(rollbackFor = Exception.class)
public interface TestTastExcuteService {
	/**
	 * 增加任务执行记录
	 * @param tast
	 */
	public void add(TestTaskexcute tast);

	/**
	 * 获取任务执行记录
	 * @param tast
	 * @return
	 */
	public List<TestTaskexcute> list(TestTaskexcute tast);
	
	/**
	 * 获取任务执行记录对象
	 * @param id
	 * @return
	 */
	public TestTaskexcute load(int id);

	/**
	 * 获取任务执行记录对象
	 * @param id
	 * @return
	 */
	public TestTaskexcute get(int id);
	
	/**
	 * 任务执行记录分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 任务执行记录分页条数
	 * @param jobs
	 * @return
	 */
	public int findRows(TestTaskexcute jobs ) ;
	/**
	 * 任务执行记录列表
	 * @param param
	 * @param param2
	 * @param param3
	 * @return
	 */
	public List findTastList(String param,String param2, String param3);
	/**
	 * 删除任务执行记录
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	/**
	 * 删除任务执行记录
	 * @param jobid
	 * @throws Exception
	 */
	public void deleteForjobid(int jobid) throws Exception;
	/**
	 * 获取任务执行记录列表
	 * @param jobid
	 * @return
	 * @throws Exception
	 */
	public List getidlist(int jobid) throws Exception;
	/**
	 * 获取任务执行记录列表
	 * @return
	 * @throws Exception
	 */
	public List listtastinfo() throws Exception;
	/**
	 * 获取N天内，任务执行每天的统计数据
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public List listindexreport(int days) throws Exception;
	/**
	 * 获取最早一条任务的执行日期
	 * @return
	 * @throws Exception
	 */
	public List getTopTaskDate() throws Exception;
}
