package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectTemplateParams;

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
public interface ProjectTemplateParamsDao {
	/**
	 * 获取模板参数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectTemplateParams load(int id) throws Exception;

	/**
	 * 增加模板参数
	 * @param ptp
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectTemplateParams ptp) throws Exception;
	
	/**
	 * 模板参数分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	/**
	 * 模板参数分页条数
	 * @param ptp
	 * @param hql
	 * @return
	 */
	public int findRows(ProjectTemplateParams ptp, String hql); 
	/**
	 * 删除模板参数
	 * @param hql
	 * @throws Exception
	 */
	public void delete(String hql) throws Exception;
	/**
	 * 删除模板参数
	 * @param ptp
	 * @throws Exception
	 */
	public void deleteforob(ProjectTemplateParams ptp) throws Exception;
	/**
	 * 修改模板参数
	 * @param ptp
	 * @throws Exception
	 */
	public void modify(ProjectTemplateParams ptp) throws Exception;
	/**
	 * 获取模板参数列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<ProjectTemplateParams> getParamsList(String sql) throws Exception;
}
