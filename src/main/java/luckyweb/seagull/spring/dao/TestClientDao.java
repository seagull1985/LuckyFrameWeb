package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestClient;


@Transactional
public interface TestClientDao {

	public TestClient load(int id) throws Exception;
	public int add(TestClient tc) throws Exception;
	public void modify(TestClient tc) throws Exception;
	public void delete(TestClient tc) throws Exception;
	
	public int get(String sql) throws Exception;
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(TestClient tc,String hql);
	public int sqlfindRows(String hql);
	@SuppressWarnings("rawtypes")
	public List listsql(String sql) throws Exception;
}
