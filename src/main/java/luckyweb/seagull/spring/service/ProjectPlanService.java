package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlan;


@Transactional
public interface ProjectPlanService {
	public int add(ProjectPlan projectplan)throws Exception;
	public void delete(ProjectPlan projectplan)throws Exception;
	public void modify(ProjectPlan projectplan)throws Exception;
	public ProjectPlan load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(ProjectPlan projectplan) ;
	public ProjectPlan getcases(String name) throws Exception;
}
