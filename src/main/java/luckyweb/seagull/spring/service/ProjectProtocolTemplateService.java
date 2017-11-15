package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;


@Transactional
public interface ProjectProtocolTemplateService {	
	public int add(ProjectProtocolTemplate ppt)throws Exception;
	public void modify(ProjectProtocolTemplate ppt) throws Exception;
	public ProjectProtocolTemplate load(int id)throws Exception;
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(ProjectProtocolTemplate ppt);
	public void delete(int id) throws Exception;
	public void deleteforob(ProjectProtocolTemplate ppt) throws Exception;
	public List findstepsparamList(int steptype,int parentid,String fieldname) throws Exception;
}
