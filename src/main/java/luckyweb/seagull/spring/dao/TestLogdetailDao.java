package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestLogdetail;

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
public interface TestLogdetailDao {
	/**
	 * 增加用例日志明细
	 * @param logDetail
	 */
	public void add(TestLogdetail logDetail);

	/**
	 * 获取用例日志明细列表
	 * @param logDetail
	 * @return
	 */
	public List<TestLogdetail> list(TestLogdetail logDetail);

	/**
	 * 获取用例日志明细实体
	 * @param id
	 * @return
	 */
	public TestLogdetail load(int id);

	/**
	 * 删除用例日志明细
	 * @param hql
	 * @param id
	 * @throws Exception
	 */
	public void delete(String hql,int id) throws Exception;

	/**
	 * 用例日志明细分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	/**
	 * 用例日志明细分页条数
	 * @param hql
	 * @param logs
	 * @return
	 */
	public int findRows(String hql,TestLogdetail logs);

}
