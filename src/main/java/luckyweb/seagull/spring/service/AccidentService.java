package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.Accident;

@Transactional
public interface AccidentService {
	public int add(Accident accident)throws Exception;
	public void delete(int id)throws Exception;
	public void modify(Accident accident)throws Exception;
	public Accident load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);
	public List listcausaltype(String startdate,String enddate,int projectid,String type) throws Exception;
	public int findRows(Accident accident) ;
}
