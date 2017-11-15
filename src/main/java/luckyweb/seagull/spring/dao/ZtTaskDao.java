package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ZtTask;


@Transactional
public interface ZtTaskDao {

	public ZtTask load(int id) throws Exception;
	
	public int add(ZtTask zt) throws Exception;

	public void delete(String hql) throws Exception;
	
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	
	public int findRows(ZtTask zt, String hql);
	
	public int findRowsreport(String hql);
	
	public List findByPagereport(final String hql,final int offset, final int pageSize);
}
