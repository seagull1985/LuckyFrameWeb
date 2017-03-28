package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.Accident;


@Transactional
public interface AccidentDao {
	public int add(Accident accident) throws Exception;

	public void modify(Accident accident) throws Exception;

	public void modifyState(Accident accident) throws Exception;

	public void modifyInfo(Accident accident) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Accident> list(Accident accident) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Accident> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List listavgpro(String sql) throws Exception;

	public Accident load(int id) throws Exception;

	public List<Accident> load(String name, String cmdType, String planPath)
			throws Exception;

	public Accident get(int id) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(Accident accident,String hql);
	
	public List<Accident> findJobsList();
}
