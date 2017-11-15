package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectTemplateParams;


@Transactional
public interface ProjectTemplateParamsService {	
	public int add(ProjectTemplateParams ptp)throws Exception;
	public ProjectTemplateParams load(int id)throws Exception;
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(ProjectTemplateParams ptp);
	public void delete(int templateid) throws Exception;
	public void deleteforob(ProjectTemplateParams ptp) throws Exception;
	public void modify(ProjectTemplateParams ptp) throws Exception;
	public List<ProjectTemplateParams> getParamsList(int templateid) throws Exception;
}
