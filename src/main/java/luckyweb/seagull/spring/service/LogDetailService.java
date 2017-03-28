package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestLogdetail;

@Transactional
public interface LogDetailService {
	public void add(TestLogdetail log);

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestLogdetail> list(TestLogdetail log);

	public TestLogdetail get(int id);
	/*public List findByPage( TestLogdetail tast, final int offset,final int pageSize);

	public List findByPage( final Object[] values,final int offset, final int pageSize);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(TestLogdetail jobs ) ;*/
	public void delete(int id) throws Exception;

}
