package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectVersion;

@Transactional
public interface ProjectVersionDao {
	public int add(ProjectVersion projectversion) throws Exception;

	public void modify(ProjectVersion projectversion) throws Exception;

	public void modifyState(ProjectVersion projectversion) throws Exception;

	public void modifyInfo(ProjectVersion projectversion) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectVersion> list(ProjectVersion projectversion) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectVersion> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List listavgpro(String sql) throws Exception;

	public ProjectVersion load(int id) throws Exception;

	public List<ProjectVersion> load(String name, String cmdType, String planPath)
			throws Exception;

	public ProjectVersion get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public List findByPagereport(final String hql, final int offset, final int pageSize);
	
	public int findRows(ProjectVersion projectversion,String hql);
	
	public int findRowsreport(String hql);
	
	public List<ProjectVersion> findJobsList();
}
