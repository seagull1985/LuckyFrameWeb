package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectModule;


@Transactional
public interface ProjectModuleDao {
	public int add(ProjectModule projectmodule) throws Exception;

	public void modify(ProjectModule projectmodule) throws Exception;

	public void modifyState(ProjectModule projectmodule) throws Exception;

	public void modifyInfo(ProjectModule projectmodule) throws Exception;

	public void delete(ProjectModule projectmodule) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectModule> list(ProjectModule projectmodule) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectModule> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectModule> getList(String sql) throws Exception;

	public ProjectModule load(int id) throws Exception;

	public List<ProjectModule> load(String name, String cmdType, String planPath)
			throws Exception;

	public ProjectModule get(int id) throws Exception;

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(ProjectModule projectmodule,String hql);
	
	public List<ProjectModule> findJobsList();
	public String getModuleIdByName(String sql) throws Exception ;
}
