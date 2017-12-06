package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.FlowCheckDao;
import luckyweb.seagull.spring.entity.FlowCheck;
import luckyweb.seagull.spring.entity.FlowInfo;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Service("flowcheckService")
public class FlowCheckServiceImpl implements FlowCheckService{
	
	private FlowCheckDao flowcheckdao;
	
	public FlowCheckDao getFlowCheckDao() {
		return flowcheckdao;
	}

	@Resource(name = "flowcheckDao")
	public void setFlowCheckDao(FlowCheckDao flowcheckDao) {
		this.flowcheckdao = flowcheckDao;
	}

	@Override
	public int add(FlowCheck flowcheck) throws Exception {
		return this.flowcheckdao.add(flowcheck);
	}

	@Override
	public void modify(FlowCheck flowcheck) throws Exception {
		this.flowcheckdao.modify(flowcheck);
		
	}

	@Override
	public void delete(int id) throws Exception {
		this.flowcheckdao.delete(id);
		
	}

	@Override
	public FlowCheck load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.flowcheckdao.load(id);
	}

	@Override
	public int getid(int projectid,int checkid,String entry) throws Exception {
		// TODO Auto-generated method stub
		int id = Integer.valueOf(this.flowcheckdao.get("select id from QA_FLOWCHECK t where checkid = "+checkid+" and projectid = "+projectid+" and checkentry = "+entry)); 
		return   id;   	
	}
	
	@Override
	public String getversionnum(int projectid,int checkid) throws Exception {
		// TODO Auto-generated method stub
		return this.flowcheckdao.get("select versionnum from QA_FLOWCHECK t where checkid = "+checkid+" and projectid = "+projectid);   	
	}
	
	@Override
	public int getcheckid(int projectid) throws Exception {
		// TODO Auto-generated method stub
		int id = Integer.valueOf(this.flowcheckdao.get("select IFNULL(max(checkid),0) from QA_FLOWCHECK where projectid = "+projectid));
		return id;
	}
	
	@Override
	public boolean determinerecord(int projectid,int checkid,String checkentry) throws Exception {
		// TODO Auto-generated method stub
		int count = Integer.valueOf(this.flowcheckdao.get("select count(*) from QA_FLOWCHECK "
				+ "where projectid = "+projectid+" and checkid = "+checkid+" and checkentry = "+checkentry+""));
		if(count==0){
			return true;
		}else{
			return false;
		}
	}

	private String where(FlowCheck fc) {
		String where = " where ";
/*		if (!StrLib.isEmpty(String.valueOf(ss.getSectorid()))) {
			where += " sectorid=:sectorid  and ";
		}*/
		if (fc.getId()!=0) {
			where += " id=:id  and ";
		}
		if (fc.getProjectid()!=0) {
			where += " projectid=:projectid  and ";
		}
		if (fc.getCheckid()!=0) {
			where += " checkid=:checkid  and ";
		}
		if (fc.getVersionnum()!=null&&!fc.getVersionnum().equals("")) {
			where += " versionnum=:versionnum  and ";
		}
		if (fc.getCheckenddate()!=null&&fc.getCheckstartdate()!=null&&
				!fc.getCheckenddate().equals("")&&!fc.getCheckstartdate().equals("")) {
			where += " checkdate>=:checkstartdate  and checkdate<=:checkenddate  and ";
		}
		if (where.length() == PublicConst.WHERENUM) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy="order by checkdate desc";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql="select projectid,checkid,min(checkdate),sum(case when checkresult in ('未通过','通过') then sum else 0 end) as sum,"
				+ "sum(case checkresult when '通过' then sum else 0 end) as pass,sum(case checkresult when '未通过' then sum else 0 end) as fail,versionnum,projectid as name,projectid as per,"
				+ "sum(case checkresult when '未检查' then sum else 0 end) as uncheck"
				+ " from (select projectid,versionnum,checkid,checkdate ,checkresult,COUNT(*) sum from QA_FLOWCHECK t "+where((FlowCheck)value)+" group by projectid,versionnum,checkid,checkresult,checkdate) as aa"
				+" group by projectid,checkid,versionnum order by max(checkdate) desc";
		List list= flowcheckdao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public List findByPageTable(Object value, int offset, int pageSize) {
		String	hql=" from FlowCheck "+where((FlowCheck)value)+orderBy;
		List list= flowcheckdao.findByPageTable(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public List reportList(Object value, int offset, int pageSize) {
		String	hql="select projectid,sum(case when checkresult in ('未通过','通过') then sum else 0 end) as sum,"
				+ "sum(case checkresult when '通过' then sum else 0 end) as pass,sum(case checkresult when '未通过' then sum else 0 end) as fail,projectid as name,projectid as per "
				+ "from (select projectid,checkresult,COUNT(*) sum from QA_FLOWCHECK t "+where((FlowCheck)value)+" group by projectid,checkresult) as aa "
				+" group by projectid";
		List list= flowcheckdao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public int findRowsReport(FlowCheck flowcheck) {
		String sql="select projectid,sum(case when checkresult in ('未通过','通过') then sum else 0 end) as sum,"
				+ "sum(case checkresult when '通过' then sum else 0 end) as pass,sum(case checkresult when '未通过' then sum else 0 end) as fail,projectid as name,projectid as per "
				+ "from (select projectid,checkresult,COUNT(*) sum from QA_FLOWCHECK t "+where(flowcheck)+" group by projectid,checkresult)  as aa "
				+" group by projectid";
		return flowcheckdao.findRows(flowcheck,sql);
	}

	@Override
	public int findRows(FlowCheck flowcheck) {
		String sql="select projectid,checkid,versionnum from QA_FLOWCHECK "+where(flowcheck)+" group by projectid,checkid,versionnum";
		return flowcheckdao.findRows(flowcheck,sql);
	}
	
	@Override
	public int findRowsTable(FlowCheck flowcheck)
	{
		String hql = "select count(*) from FlowCheck " + where(flowcheck);
		return flowcheckdao.findRowsTable(flowcheck, hql);
	}

	@Override
	public void updateversion(int proid,String verold,String vernew) throws Exception {
		// TODO Auto-generated method stub
		this.flowcheckdao.modifyVersion("update FlowCheck set versionnum='"+vernew+"' where projectid="+proid+" and versionnum='"+verold+"'");
	}
	
	@Override
	public List listcheckinfo(int projectid,int checkid) throws Exception {
		// TODO Auto-generated method stub
		return this.flowcheckdao.listcheckinfo("select id,phasename,phasenodename,checkentry from QA_FLOWINFO where id not in(select checkentry from QA_FLOWCHECK  "
				+ "where projectid = "+projectid+" and checkid = "+checkid+") "
				+ "order by phaseid,phasenodeid,checkentryid");	
	}
	
	@Override
	public List listdateper(String startdate,String enddate) throws Exception {
		// TODO Auto-generated method stub
		return this.flowcheckdao.listcheckinfo("select projectid,sum(case checkresult when '通过' then sum else 0 end) as pass,sum(case checkresult when '未通过' then sum else 0 end) as fail "
				+ "from (select projectid,checkresult,COUNT(*) sum from QA_FLOWCHECK  "
				+ "where checkdate >= '"+startdate+"' and checkdate <= '"+enddate+"' group by projectid,checkresult)  as aa "
						+ "group by projectid");	
	}

}
