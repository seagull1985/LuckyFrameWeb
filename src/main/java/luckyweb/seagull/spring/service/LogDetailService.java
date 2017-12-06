package luckyweb.seagull.spring.service;

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
public interface LogDetailService {
	/**
	 * 增加日志明细
	 * @param log
	 */
	public void add(TestLogdetail log);

	/**
	 * 获取日志明细列表
	 * @param log
	 * @return
	 */
	public List<TestLogdetail> list(TestLogdetail log);

	/**
	 * 获取日志明细实体
	 * @param id
	 * @return
	 */
	public TestLogdetail load(int id);
	/**
	 * 日志明细分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<TestLogdetail> findByPage(Object value, int offset, int pageSize);
	/**
	 * 获取日志明细分页条数
	 * @param logs
	 * @return
	 */
	public int findRows(TestLogdetail logs) ;
	/**
	 * 删除日志明细
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;

}
