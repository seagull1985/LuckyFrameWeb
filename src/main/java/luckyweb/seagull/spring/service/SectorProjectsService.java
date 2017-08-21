package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.SectorProjects;

@Transactional
public interface SectorProjectsService {
	public int add(SectorProjects sectorprojects)throws Exception;
	public void modify(SectorProjects sectorprojects)throws Exception;
	public void modifyState(SectorProjects sectorprojects)throws Exception;
	public void delete(SectorProjects sectorprojects)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<SectorProjects> list(SectorProjects sectorprojects)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<SectorProjects> list()throws Exception;
	
	
	public boolean isExist(String name,String cmdType,String planPath)throws Exception;

	public Object load(int projectid)throws Exception;
	
	public SectorProjects get(int id)throws Exception;
	public int getid(String projectname) throws Exception;

//	public List findByPage( final int offset,final int pageSize);
//
//	public List findByPage( final Object[] values,final int offset, final int pageSize);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(SectorProjects sectorprojects) ;
//	public List<TestJobs> getListForPage( final int offset,final int lengh);

	public List<SectorProjects> findProjectsList();
	public SectorProjects loadob(int projectid) throws Exception;
	public int projectrow(int id);
	public List<SectorProjects> getAllProject();
	public int projectrowfordmtp(int sectorid);
}
