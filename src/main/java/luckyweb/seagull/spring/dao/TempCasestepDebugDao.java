package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TempCasestepDebug;


@Transactional
public interface TempCasestepDebugDao {

	public TempCasestepDebug load(int id) throws Exception;

	public int add(TempCasestepDebug tcd) throws Exception;
	
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	
	public int findRows(TempCasestepDebug tcd, String hql); 
	public void delete(String hql) throws Exception;
	public void deleteforob(TempCasestepDebug tcd) throws Exception;
	public void modify(TempCasestepDebug tcd) throws Exception;
	public List<TempCasestepDebug> getParamsList(String sql) throws Exception;
}
