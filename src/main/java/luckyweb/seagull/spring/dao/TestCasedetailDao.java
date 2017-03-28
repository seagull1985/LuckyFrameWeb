package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestCasedetail;

@Transactional
public interface TestCasedetailDao {
	@Transactional(propagation=Propagation.REQUIRED)
	public void add(TestCasedetail caseDetail);

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestCasedetail> list(TestCasedetail caseDetail);
	
	public TestCasedetail load(int id);

	public TestCasedetail get(int id);


	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(String hql,TestCasedetail tast);
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(String hql,int id) throws Exception;

}
