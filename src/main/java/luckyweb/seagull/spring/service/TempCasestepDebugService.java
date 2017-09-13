package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TempCasestepDebug;


@Transactional
public interface TempCasestepDebugService {	
	public int add(TempCasestepDebug tcd)throws Exception;
	public TempCasestepDebug load(int id)throws Exception;
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(TempCasestepDebug tcd);
	public void delete(String sign,String executor) throws Exception;
	public void deleteforob(TempCasestepDebug tcd) throws Exception;
	public void modify(TempCasestepDebug tcd) throws Exception;
	public List<TempCasestepDebug> getList(String sign,String executor) throws Exception;
}
