package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.SecondarySector;

@Transactional
public interface SecondarySectorDao {
	public int add(SecondarySector sector) throws Exception;

	public void modify(SecondarySector sector) throws Exception;

	public void modifyState(SecondarySector sector) throws Exception;

	public void modifyInfo(SecondarySector sector) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<SecondarySector> list(SecondarySector sector) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<SecondarySector> list() throws Exception;

	public SecondarySector load(int id) throws Exception;

	public List<SecondarySector> load(String name, String cmdType, String planPath)
			throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(SecondarySector sector,String hql);
	
	public List<SecondarySector> findSectorList();
}
