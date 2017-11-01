package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TestClient;


@Transactional
public interface TestClientService {
	
	public TestClient load(int id)throws Exception;
	public int add(TestClient tc) throws Exception;
	public void modify(TestClient tc) throws Exception;
	public void delete(TestClient tc) throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);

	public int findRows(TestClient tc) ;
	public List<TestClient> getClientListForProid(int projectid)  throws Exception;
}
