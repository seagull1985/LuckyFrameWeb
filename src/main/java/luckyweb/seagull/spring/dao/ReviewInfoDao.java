package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ReviewInfo;

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
public interface ReviewInfoDao {
	/**
	 * 增加评审详细信息
	 * @param reviewinfo
	 * @return
	 * @throws Exception
	 */
	public int add(ReviewInfo reviewinfo) throws Exception;

	/**
	 * 修改评审详细信息
	 * @param reviewinfo
	 * @throws Exception
	 */
	public void modify(ReviewInfo reviewinfo) throws Exception;

	/**
	 * 删除评审详细信息
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;

	/**
	 * 获取评审详细信息实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReviewInfo get(int id) throws Exception;

	/**
	 * 评审详细信息分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	/**
	 * 评审详细信息分页条数
	 * @param reviewinfo
	 * @param hql
	 * @return
	 */
	public int findRows(ReviewInfo reviewinfo,String hql);
	
	/**
	 * 执行SQL
	 * @param hql
	 * @throws Exception
	 */
	public void update(String hql) throws Exception;
}
