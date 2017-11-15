package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectCasesteps;


@Transactional
public interface ProjectCasestepsDao {
	public int add(ProjectCasesteps casesteps) throws Exception;

	public void modify(ProjectCasesteps casesteps) throws Exception;

	public void modifyState(ProjectCasesteps casesteps) throws Exception;

	public void modifyInfo(ProjectCasesteps casesteps) throws Exception;

	public void delete(int id) throws Exception;
	public void delete(String hql, int id) throws Exception;
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectCasesteps> list(ProjectCasesteps casesteps) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectCasesteps> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectCasesteps> steps(String sql) throws Exception;

	public ProjectCasesteps load(int id) throws Exception;

	public List<ProjectCasesteps> load(String name, String cmdType, String planPath)
			throws Exception;

	public ProjectCasesteps get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(ProjectCasesteps casesteps,String hql);
	
	public List<ProjectCasesteps> findJobsList();
}
