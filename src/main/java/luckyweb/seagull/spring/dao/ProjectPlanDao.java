package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlan;



@Transactional
public interface ProjectPlanDao {
	public int add(ProjectPlan projectplan) throws Exception;

	public void modify(ProjectPlan projectplan) throws Exception;

	public void modifyState(ProjectPlan projectplan) throws Exception;

	public void modifyInfo(ProjectPlan projectplan) throws Exception;

	public void delete(ProjectPlan projectplan) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectPlan> list(ProjectPlan projectplan) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectPlan> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List getList(String sql) throws Exception;

	public ProjectPlan load(int id) throws Exception;

	public List<ProjectPlan> load(String name, String cmdType, String planPath)
			throws Exception;

	public ProjectPlan get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(ProjectPlan projectplan,String hql);
	
	public List<ProjectPlan> findJobsList();
}
