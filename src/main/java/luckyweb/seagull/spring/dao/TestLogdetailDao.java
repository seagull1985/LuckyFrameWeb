package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestLogdetail;

@Transactional
public interface TestLogdetailDao {
	public void add(TestLogdetail logDetail);

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<TestLogdetail> list(TestLogdetail logDetail);

	public TestLogdetail get(int id);

	public void delete(String hql,int id) throws Exception;

/*	public List findByPage(final TestLogdetail tast,final String hql, final int offset,final int pageSize);

	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(String hql,TestLogdetail tast);*/

}
