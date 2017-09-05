package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;


@Transactional
public interface ProjectProtocolTemplateDao {

	public ProjectProtocolTemplate load(int id) throws Exception;

	public int add(ProjectProtocolTemplate ppt) throws Exception;
	public void modify(ProjectProtocolTemplate ppt) throws Exception;
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	
	public int findRows(ProjectProtocolTemplate ppt, String hql); 
	public void delete(String hql) throws Exception;
	public void deleteforob(ProjectProtocolTemplate ppt) throws Exception;
	public List findstepsparamList(String hql);
}
