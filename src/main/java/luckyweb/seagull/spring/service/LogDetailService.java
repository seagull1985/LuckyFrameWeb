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

	public TestLogdetail load(int id);
	public List<TestLogdetail> findByPage(Object value, int offset, int pageSize);
	public int findRows(TestLogdetail logs) ;
	public void delete(int id) throws Exception;

}
