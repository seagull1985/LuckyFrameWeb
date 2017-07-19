package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectCase;

@Transactional
public interface ProjectCaseService {
	public int add(ProjectCase projectcase)throws Exception;
	public void delete(int id)throws Exception;
	public void modify(ProjectCase projectcase)throws Exception;
	public ProjectCase load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(ProjectCase projectcase) ;
	public ProjectCase getCaseBySign(String sign) throws Exception;
	public String getCaseMaxIndex(int projectid) throws Exception;
}
