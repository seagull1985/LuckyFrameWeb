package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestTaskexcute;


public interface TestTastExcuteDao {
	public void add(TestTaskexcute tjob);

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestTaskexcute> list(TestTaskexcute tjob);
	
	public TestTaskexcute load(int id);

	public TestTaskexcute get(int id);
	

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(String hql,TestTaskexcute tast);
	
	public List findTastList(String sql);
	
	public void delete(int id) throws Exception;
	
	public List listtastinfo(String sql) throws Exception;
	
	public void delete_forjobid(String hql, int jobid) throws Exception;

}
