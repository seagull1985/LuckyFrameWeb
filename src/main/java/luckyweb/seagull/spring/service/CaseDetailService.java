package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestCasedetail;

@Transactional
public interface CaseDetailService {
	public void add(TestCasedetail tast);

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestCasedetail> list(TestCasedetail tast);
	
	public TestCasedetail load(int id);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(TestCasedetail jobs ) ;
	
	public void delete(int id) throws Exception;

}
