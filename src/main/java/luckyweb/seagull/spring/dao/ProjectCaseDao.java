package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectCase;

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
public interface ProjectCaseDao {
	/**
	 * 增加测试用例
	 * @param projectcase
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectCase projectcase) throws Exception;
	/**
	 * 修改测试用例
	 * @param projectcase
	 * @throws Exception
	 */
	public void modify(ProjectCase projectcase) throws Exception;
	/**
	 * 修改测试用例状态
	 * @param projectcase
	 * @throws Exception
	 */
	public void modifyState(ProjectCase projectcase) throws Exception;
	/**
	 * 测试用例信息
	 * @param projectcase
	 * @throws Exception
	 */
	public void modifyInfo(ProjectCase projectcase) throws Exception;
	/**
	 * 删除测试用例
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	/**
	 * 获取测试用例列表
	 * @param projectcase
	 * @return
	 * @throws Exception
	 */
	public List<ProjectCase> list(ProjectCase projectcase) throws Exception;
	/**
	 * 获取测试用例列表
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<ProjectCase> list(String hql) throws Exception;
	/**
	 * 获取测试用例列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<ProjectCase> getList(String sql) throws Exception;
	/**
	 * 获取测试用例实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectCase load(int id) throws Exception;

	/**
	 * 测试用例分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);
	/**
	 * 测试用例分页条数
	 * @param projectcase
	 * @param hql
	 * @return
	 */
	public int findRows(ProjectCase projectcase,String hql);
	/**
	 * 测试用例列表
	 * @return
	 */
	public List<ProjectCase> findJobsList();
	/**
	 * 获取测试用例最大索引
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String getCaseMaxIndex(String sql) throws Exception ;
}
