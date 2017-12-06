package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlanCase;


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
public interface ProjectPlanCaseDao {
	/**
	 * 增加计划用例
	 * @param projectplancase
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectPlanCase projectplancase) throws Exception;

	/**
	 * 修改计划用例
	 * @param projectplancase
	 * @throws Exception
	 */
	public void modify(ProjectPlanCase projectplancase) throws Exception;

	/**
	 * 删除计划用例
	 * @param projectplancase
	 * @throws Exception
	 */
	public void delete(ProjectPlanCase projectplancase) throws Exception;
	/**
	 * 删除计划用例
	 * @param hql
	 * @param id
	 * @throws Exception
	 */
	public void delete(String hql, int id) throws Exception;

	/**
	 * 获取计划用例列表
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<ProjectPlanCase> list(String hql) throws Exception;
	/**
	 * 获取计划用例列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getList(String sql) throws Exception;

	/**
	 * 获取计划用例实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectPlanCase load(int id) throws Exception;

	/**
	 * 计划用例分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	/**
	 * 计划用例分页条数
	 * @param projectplancase
	 * @param hql
	 * @return
	 */
	public int findRows(ProjectPlanCase projectplancase,String hql);
}
