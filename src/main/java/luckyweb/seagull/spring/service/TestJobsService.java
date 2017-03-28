package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestJobs;

@Transactional
public interface TestJobsService {
	public int add(TestJobs tjob)throws Exception;
	public void modify(TestJobs tjob)throws Exception;
	public void modifyState(TestJobs tjob)throws Exception;
	public void delete(int id)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestJobs> list(TestJobs tjob)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestJobs> list()throws Exception;
	
	public boolean isExist(String name,String cmdType,String planPath)throws Exception;

	public TestJobs load(int id)throws Exception;
	public TestJobs get(int id)throws Exception;
	

//	public List findByPage( final int offset,final int pageSize);
//
//	public List findByPage( final Object[] values,final int offset, final int pageSize);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(TestJobs jobs ) ;
//	public List<TestJobs> getListForPage( final int offset,final int lengh);

	public List<TestJobs> findJobsList();
	public List getipList();
}
