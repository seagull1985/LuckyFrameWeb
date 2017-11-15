package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlanCase;



@Transactional
public interface ProjectPlanCaseDao {
	public int add(ProjectPlanCase projectplancase) throws Exception;

	public void modify(ProjectPlanCase projectplancase) throws Exception;

	public void modifyState(ProjectPlanCase projectplancase) throws Exception;

	public void modifyInfo(ProjectPlanCase projectplancase) throws Exception;

	public void delete(ProjectPlanCase projectplancase) throws Exception;
	public void delete(String hql, int id) throws Exception;
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectPlanCase> list(ProjectPlanCase projectplancase) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectPlanCase> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List getList(String sql) throws Exception;

	public ProjectPlanCase load(int id) throws Exception;

	public List<ProjectPlanCase> load(String name, String cmdType, String planPath)
			throws Exception;

	public ProjectPlanCase get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(ProjectPlanCase projectplancase,String hql);
	
	public List<ProjectPlanCase> findJobsList();
}
