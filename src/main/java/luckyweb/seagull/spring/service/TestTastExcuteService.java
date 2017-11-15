package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestTaskexcute;

@Transactional
public interface TestTastExcuteService {
	public void add(TestTaskexcute tast);

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestTaskexcute> list(TestTaskexcute tast);
	
	public TestTaskexcute load(int id);

	public TestTaskexcute get(int id);
	

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(TestTaskexcute jobs ) ;
	
	public List findTastList(String param,String param2, String param3);
	public void delete(int id) throws Exception;
	public void delete_forjobid(int jobid) throws Exception;
	public List getidlist(int jobid) throws Exception;
	public List listtastinfo() throws Exception;
}
