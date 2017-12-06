package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestCasedetail;

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
public interface CaseDetailService {
	/**
	 * 增加用例执行情况
	 * @param tast
	 */
	public void add(TestCasedetail tast);

	/**
	 * 获取用例执行情况列表
	 * @param tast
	 * @return
	 */
	public List<TestCasedetail> list(TestCasedetail tast);
	/**
	 * 获取用例执行情况实体
	 * @param id
	 * @return
	 */
	public TestCasedetail load(int id);

	/**
	 * 获取用例执行情况分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 用例执行情况分页条数
	 * @param jobs
	 * @return
	 */
	public int findRows(TestCasedetail jobs ) ;
	/**
	 * 删除用例执行情况
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;

}
