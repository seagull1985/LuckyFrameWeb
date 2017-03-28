package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestJobs;

@Transactional
public interface TestJobsDao {
	public int add(TestJobs tjob) throws Exception;

	public void modify(TestJobs tjob) throws Exception;

	public void modifyState(TestJobs tjob) throws Exception;

	public void modifyInfo(TestJobs tjob) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<TestJobs> list(TestJobs tjob) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<TestJobs> list() throws Exception;

	public TestJobs load(int id) throws Exception;

	public List<TestJobs> load(String name, String cmdType, String planPath)
			throws Exception;

	public TestJobs get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(TestJobs jobs,String hql);
	
	public List findJobsList(String hql);
	
}
