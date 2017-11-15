package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowInfo;

@Transactional
public interface FlowInfoService {
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listphaseinfo()throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listnodeinfo(int id)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listentryinfo(int id)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listphaseallinfo()throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listnodeallinfo()throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listentryallinfo()throws Exception;
	
	public FlowInfo load(int id) throws Exception;
}
