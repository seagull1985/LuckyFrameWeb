package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.OperationLog;


@Transactional
public interface OperationLogDao {

	public OperationLog load(int id) throws Exception;

	public int add(OperationLog oplog) throws Exception;
	
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	
	public int findRows(OperationLog oplog, String hql); 

}
