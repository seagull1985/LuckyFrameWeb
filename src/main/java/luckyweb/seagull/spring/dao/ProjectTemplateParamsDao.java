package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectTemplateParams;


@Transactional
public interface ProjectTemplateParamsDao {

	public ProjectTemplateParams load(int id) throws Exception;

	public int add(ProjectTemplateParams ptp) throws Exception;
	
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize);
	
	public int findRows(ProjectTemplateParams ptp, String hql); 
	public void delete(String hql) throws Exception;
	public void deleteforob(ProjectTemplateParams ptp) throws Exception;
	public void modify(ProjectTemplateParams ptp) throws Exception;
	public List<ProjectTemplateParams> getParamsList(String sql) throws Exception;
}
