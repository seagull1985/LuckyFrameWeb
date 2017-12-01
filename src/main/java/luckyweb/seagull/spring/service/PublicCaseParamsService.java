package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.PublicCaseParams;


@Transactional
public interface PublicCaseParamsService {
	
	public PublicCaseParams load(int id)throws Exception;
	public int add(PublicCaseParams pcp) throws Exception;
	public void modify(PublicCaseParams pcp) throws Exception;
	public void delete(PublicCaseParams pcp) throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);

	public int findRows(PublicCaseParams pcp) ;
	public PublicCaseParams getParamByName(String paramsname,String projectid) throws Exception;
	public List<PublicCaseParams> getParamListByProjectid(String projectid) throws Exception;
}
