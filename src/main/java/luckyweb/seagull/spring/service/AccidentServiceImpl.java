package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.AccidentDao;
import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.ProjectVersion;

@Service("accidentService")
public class AccidentServiceImpl implements AccidentService{
	
	private AccidentDao accidentdao;
	
	public AccidentDao getAccidentDao() {
		return accidentdao;
	}

	@Resource(name = "accidentDao")
	public void setAccidentDao(AccidentDao accidentdao) {
		this.accidentdao = accidentdao;
	}
	
	@Override
	public int add(Accident accident) throws Exception {
		return this.accidentdao.add(accident);
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.accidentdao.delete(id);		
	}
	
	@Override
	public void modify(Accident accident) throws Exception {
		this.accidentdao.modify(accident);
		
	}
	
	@Override
	public Accident load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.accidentdao.load(id);
	}

	private String where(Accident accident) {
		String where = " where ";
		if (accident.getProjectid()!=0) {
			where += " projectid=:projectid  and ";
		}	
		if (accident.getAccstatus()!=null&&!accident.getAccstatus().equals("")&&!accident.getAccstatus().equals("00")) {
			where += " accstatus=:accstatus  and ";
		}
		if (accident.getAccstarttime()!=null&&!accident.getAccstarttime().equals("")) {
			where += " eventtime>=:accstarttime  and ";
		}
		if (accident.getAccendtime()!=null&&!accident.getAccendtime().equals("")) {
			where += " eventtime<=:accendtime  and ";
		}
		if (null!=accident.getAccdescription()&&!"".equals(accident.getAccdescription())) {
			where += " (accdescription like :accdescription  or ";
		}
        if (null!=accident.getCausaltype()&&!"".equals(accident.getCausaltype())) {
			where += " causaltype like :causaltype)  or ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by IFNULL(9,resolutiontime) desc,eventtime desc,id desc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from Accident  "+where((Accident)value)+orderBy;
		List list= accidentdao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public List listcausaltype(String startdate,String enddate,int projectid,String type) throws Exception {
		String where = " where ";
		String sql = "";
		if(startdate!=""&&startdate!=null){
			where += " eventtime>='"+startdate+"' and ";
		}
		if(enddate!=""&&enddate!=null){
			where += " eventtime<='"+enddate+"' and ";
		}
		if(projectid!=0&&projectid!=99){
			where += " projectid="+projectid+" and ";
		}
		
		if (where.length() == 7) {
			where = "";
		}else{
			where = where.substring(0, where.length() - 5);
		}
		if(type.equals("count")){
		 sql="select causaltype,count(causaltype) from QA_ACCIDENT  "+where+" group by causaltype";
		}
		if(type.equals("sumimpact")){
		 sql="select causaltype,sum(impact_time) from QA_ACCIDENT  "+where+" group by causaltype";
		}
		
		List list= accidentdao.listavgpro(sql);
		return list;
	}

	@Override
	public int findRows(Accident accident) {
		String hql="select count(*) from Accident "+where(accident);
		return accidentdao.findRows(accident, hql);
	}

}
