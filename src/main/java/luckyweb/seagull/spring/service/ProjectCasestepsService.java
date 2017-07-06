package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectCasesteps;

@Transactional
public interface ProjectCasestepsService {
	public int add(ProjectCasesteps casesteps)throws Exception;
	public void delete(int id)throws Exception;
	public void delforcaseid(int id) throws Exception;
	public void modify(ProjectCasesteps casesteps)throws Exception;
	public ProjectCasesteps load(int id)throws Exception;
	public List<ProjectCasesteps> getSteps(int caseid)throws Exception;
}
