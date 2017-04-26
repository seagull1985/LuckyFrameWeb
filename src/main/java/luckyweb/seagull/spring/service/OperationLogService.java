package luckyweb.seagull.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.OperationLog;


@Transactional
public interface OperationLogService {	
	public int add(HttpServletRequest req,String tablename,int tableid,int id,String operation_description)throws Exception;
	public OperationLog load(int id)throws Exception;
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(OperationLog oplog);
	public void delete(int id) throws Exception;
}
