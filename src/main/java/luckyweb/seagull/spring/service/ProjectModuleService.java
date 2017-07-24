package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectModule;

@Transactional
public interface ProjectModuleService {
	public int add(ProjectModule projectmodule)throws Exception;
	public void delete(ProjectModule projectmodule)throws Exception;
	public void modify(ProjectModule projectmodule)throws Exception;
	public ProjectModule load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);
	public int findRows(ProjectModule projectmodule) ;
	public List<ProjectModule> getModuleList();
	public List<ProjectModule> getModuleListByProjectid(int projectid,int id);
	public boolean getModuleIsParent(int id);
	public List<ProjectModule> getModuleAllListByProjectid(int projectid);
}
