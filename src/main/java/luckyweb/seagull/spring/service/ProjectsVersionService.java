package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectVersion;

@Transactional
public interface ProjectsVersionService {
	public int add(ProjectVersion projectversion)throws Exception;
	public void modify(ProjectVersion projectversion)throws Exception;
	public void modifyState(ProjectVersion projectversion)throws Exception;
	public void delete(int id)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ProjectVersion> list(ProjectVersion projectversion)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ProjectVersion> list(int projectid)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<ProjectVersion> list(int projectid,String startdate,String enddate)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listavgpro(String startdate,String enddate)throws Exception;
	
	public boolean isExist(String name,String cmdType,String planPath)throws Exception;

	public ProjectVersion load(int id)throws Exception;
	public ProjectVersion get(int id)throws Exception;
	

//	public List findByPage( final int offset,final int pageSize);
//
//	public List findByPage( final Object[] values,final int offset, final int pageSize);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public List findByPagereport(int offset, int pageSize,String startdate,String enddate)throws Exception;
	
	public int findRows(ProjectVersion projectversion) ;
	
	public int findRowsreport(String startdate,String enddate) ;
//	public List<TestJobs> getListForPage( final int offset,final int lengh);

	public List<ProjectVersion> findProjectsList();
}
