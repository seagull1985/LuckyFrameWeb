package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowCheck;

@Transactional
public interface FlowCheckDao {
	public int add(FlowCheck flowcheck) throws Exception;

	public void modify(FlowCheck flowcheck) throws Exception;

	public void modifyState(FlowCheck flowcheck) throws Exception;

	public void modifyInfo(FlowCheck flowcheck) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FlowCheck> list(FlowCheck flowcheck) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FlowCheck> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List listcheckinfo(String sql) throws Exception;

	public FlowCheck load(int id) throws Exception;

	public List<FlowCheck> load(String name, String cmdType, String planPath)
			throws Exception;

	public String get(String sql) throws Exception;

//	public List findByPage(final String hql, final int offset,final int pageSize);
//
//	public List findByPage(final String hql, final Object[] values,final int offset, final int pageSize);

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(FlowCheck flowcheck,String hql);
	
	public List findByPageTable(final String hql, final Object value,final int offset, final int pageSize);

	public int findRowsTable(FlowCheck flowcheck,String hql);
	
	/*public List findRows(String sql);*/
	
	public List<FlowCheck> findJobsList();
}
