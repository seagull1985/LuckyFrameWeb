package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;

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
public interface ProjectProtocolTemplateDao {
	/**
	 * 获取协议模板实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectProtocolTemplate load(int id) throws Exception;

	/**
	 * 增加协议模板
	 * @param ppt
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectProtocolTemplate ppt) throws Exception;
	/**
	 * 修改协议模板
	 * @param ppt
	 * @throws Exception
	 */
	public void modify(ProjectProtocolTemplate ppt) throws Exception;
	/**
	 * 协议模板分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	/**
	 * 协议模板分页条数
	 * @param ppt
	 * @param hql
	 * @return
	 */
	public int findRows(ProjectProtocolTemplate ppt, String hql); 
	/**
	 * 删除协议模板
	 * @param hql
	 * @throws Exception
	 */
	public void delete(String hql) throws Exception;
	/**
	 * 删除协议模板
	 * @param ppt
	 * @throws Exception
	 */
	public void deleteforob(ProjectProtocolTemplate ppt) throws Exception;
	/**
	 * 协议模板列表
	 * @param hql
	 * @return
	 */
	public List findstepsparamList(String hql);
}
