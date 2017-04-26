package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.SectorProjects;

@Transactional
public interface SectorProjectsDao {
	public int add(SectorProjects sectorprojects) throws Exception;

	public void modify(SectorProjects sectorprojects) throws Exception;

	public void modifyState(SectorProjects sectorprojects) throws Exception;

	public void modifyInfo(SectorProjects sectorprojects) throws Exception;

	public void delete(SectorProjects sectorprojects) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<SectorProjects> list(SectorProjects sectorprojects) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<SectorProjects> list() throws Exception;

	public Object findproject(int projectid) throws Exception;

	public List<SectorProjects> load(String name, String cmdType, String planPath)
			throws Exception;
	
	public int getid(String projectname) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(SectorProjects sectorprojects,String hql);
	public SectorProjects load(int projectid) throws Exception;
	public int projectrow(String hql);
}
