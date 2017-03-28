package luckyweb.seagull.spring.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.OperationLogDao;
import luckyweb.seagull.spring.entity.OperationLog;
import luckyweb.seagull.spring.entity.SectorProjects;



@Service("operationlogService")
public class OperationLogServiceImpl implements OperationLogService{
	
	private OperationLogDao operationlogdao;
	
	public OperationLogDao getOperationLogDao() {
		return operationlogdao;
	}

	@Resource(name = "operationlogDao")
	public void setOperationLogDao(OperationLogDao operationlogDao) {
		this.operationlogdao = operationlogDao;
	}
	
	@Override
	public OperationLog load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.operationlogdao.load(id);
	}
	
	private String where(OperationLog oplog) {
		String where = " where ";
		if (oplog.getStarttime()!=null&&oplog.getEndtime()!=null
				&&!oplog.getStarttime().equals("")&&!oplog.getEndtime().equals("")) {
			where += " operation_time>=:starttime  and operation_time<=:endtime  and ";
		}
		if (oplog.getProjectid()!=0) {
			where += " projectid=:projectid  and ";
		}
		if (oplog.getOperationer()!=null&&!oplog.getOperationer().equals("")) {
			where += " operationer like :operationer  and ";
		}
		if (oplog.getOperation_description()!=null&&!oplog.getOperation_description().equals("")) {
			where += " operation_description like :operation_description  and ";
		}
		if (where.length() == 7) {
			where = "";
		}
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by id desc ";

	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from OperationLog  "+where((OperationLog)value)+orderBy;
		List list= operationlogdao.findByPage(hql, value, offset, pageSize);
		return list;
	}
	
	@Override
	public int findRows(OperationLog oplog) {
		String hql="select count(*) from OperationLog "+where(oplog);
		return operationlogdao.findRows(oplog, hql);
	}
	
	
	@Override
	public int add(HttpServletRequest req,String tablename,int tableid,int id,String operation_description) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OperationLog op_log = new OperationLog();
		String operation_time = df.format(new Date()).toString();	
		op_log.setTableid(tableid);
		op_log.setOperation_time(operation_time);
		op_log.setOperationer(req.getSession().getAttribute("username").toString());			
		op_log.setTablename(tablename);
		SectorProjects p = new SectorProjects();
		p.setProjectid(id);
		op_log.setSectorProjects(p);
		op_log.setOperation_description(operation_description);
		return this.operationlogdao.add(op_log);
	}
	
}
