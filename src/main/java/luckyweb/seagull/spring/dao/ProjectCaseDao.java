package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectCase;


@Transactional
public interface ProjectCaseDao {
	public int add(ProjectCase projectcase) throws Exception;

	public void modify(ProjectCase projectcase) throws Exception;

	public void modifyState(ProjectCase projectcase) throws Exception;

	public void modifyInfo(ProjectCase projectcase) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectCase> list(ProjectCase projectcase) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ProjectCase> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List getList(String sql) throws Exception;

	public ProjectCase load(int id) throws Exception;

	public List<ProjectCase> load(String name, String cmdType, String planPath)
			throws Exception;

	public ProjectCase get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(ProjectCase projectcase,String hql);
	
	public List<ProjectCase> findJobsList();
}
