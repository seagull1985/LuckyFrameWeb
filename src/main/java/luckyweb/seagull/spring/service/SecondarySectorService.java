package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.SecondarySector;

@Transactional
public interface SecondarySectorService {
	public int add(SecondarySector Sector)throws Exception;
	public void modify(SecondarySector Sector)throws Exception;
	public void modifyState(SecondarySector Sector)throws Exception;
	public void delete(int id)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<SecondarySector> list(SecondarySector Sector)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<SecondarySector> listall()throws Exception;
	
	public boolean isExist(String name,String cmdType,String planPath)throws Exception;

	public SecondarySector load(int id)throws Exception;
	

//	public List findByPage( final int offset,final int pageSize);
//
//	public List findByPage( final Object[] values,final int offset, final int pageSize);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public int findRows(SecondarySector Sector) ;
//	public List<TestJobs> getListForPage( final int offset,final int lengh);

	public List<SecondarySector> findSecotorList()  throws Exception ;
}
