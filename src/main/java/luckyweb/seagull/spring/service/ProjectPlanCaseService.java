package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlanCase;


@Transactional
public interface ProjectPlanCaseService {
	public int add(ProjectPlanCase projectplancase)throws Exception;
	public void delete(ProjectPlanCase projectplancase)throws Exception;
	public void delforplanid(int id) throws Exception;
	public void modify(ProjectPlanCase projectplancase)throws Exception;
	public ProjectPlanCase load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(ProjectPlanCase projectplancase) ;
	public List<ProjectPlanCase> getcases(int planid) throws Exception;
	public ProjectPlanCase getplancase(int planid,int caseid) throws Exception;
}
