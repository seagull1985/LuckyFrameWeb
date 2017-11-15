package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowCheck;

@Transactional
public interface FlowCheckService {
	public int add(FlowCheck flowcheck)throws Exception;
	public void modify(FlowCheck flowcheck)throws Exception;
	public void modifyState(FlowCheck flowcheck)throws Exception;
	public void delete(int id)throws Exception;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List listcheckinfo(int projectid,int checkid)throws Exception;

	public List listdateper(String startdate,String enddate) throws Exception;
	
	public boolean isExist(String name,String cmdType,String planPath)throws Exception;

	public int getid(int projectid,int checkid,String entry) throws Exception;
	
	public String getversionnum(int projectid,int checkid) throws Exception;
	
	public FlowCheck load(int id)throws Exception;
	
	public int getcheckid(int projectid)throws Exception;
	
	public boolean determinerecord(int projectid,int checkid,String checkentry) throws Exception;

//	public List findByPage( final int offset,final int pageSize);
//
//	public List findByPage( final Object[] values,final int offset, final int pageSize);

	public List findByPage( final Object value,final int offset, final int pageSize);
	
	public List findByPageTable( final Object value,final int offset, final int pageSize);
	
	public List reportList( final Object value,final int offset, final int pageSize);
	public void updateversion(int proid,String verold,String vernew) throws Exception;
	public int findRowsReport(FlowCheck flowcheck) ;
	public int findRows(FlowCheck flowcheck) ;
//	public List<TestJobs> getListForPage( final int offset,final int lengh);
	
	public int findRowsTable(FlowCheck flowcheck) ;

	public List<FlowCheck> findProjectsList();
}
