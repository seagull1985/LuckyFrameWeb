package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ZtTask;


@Transactional
public interface ZtTaskService {
	
	public ZtTask load(int id)throws Exception;
	
	public int add(ZtTask zt) throws Exception;
	
	public void delete(int versionid) throws Exception;
	
	public List findByPage(Object value, int offset, int pageSize);
	
	public int findRows(ZtTask zt);
	
	public List findByPagereport(int offset, int pageSize,String startdate,String enddate,int type) throws Exception;
	
	public int findRowsreport(String startdate,String enddate,int type);
}
