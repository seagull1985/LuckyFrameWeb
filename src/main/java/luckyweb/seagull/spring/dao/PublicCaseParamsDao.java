package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.PublicCaseParams;


@Transactional
public interface PublicCaseParamsDao {

	public PublicCaseParams load(int id) throws Exception;
	public int add(PublicCaseParams pcp) throws Exception;
	public void modify(PublicCaseParams pcp) throws Exception;
	public void delete(PublicCaseParams pcp) throws Exception;
	
	public int get(String sql) throws Exception;
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(PublicCaseParams pcp,String hql);
	public int sqlfindRows(String hql);
	@SuppressWarnings("rawtypes")
	public List listsql(String sql) throws Exception;
}
