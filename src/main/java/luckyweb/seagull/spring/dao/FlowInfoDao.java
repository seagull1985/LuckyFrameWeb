package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowInfo;

@Transactional
public interface FlowInfoDao {

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FlowInfo> list(FlowInfo flowinfo) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FlowInfo> list(String hql) throws Exception;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List listcheckinfo(String sql) throws Exception;

	public FlowInfo load(int id) throws Exception;

	public FlowInfo get(int id) throws Exception;
}
