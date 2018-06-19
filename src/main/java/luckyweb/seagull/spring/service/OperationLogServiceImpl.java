package luckyweb.seagull.spring.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.OperationLogDao;
import luckyweb.seagull.spring.entity.OperationLog;
import luckyweb.seagull.spring.entity.SectorProjects;


/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
			where += " (operationer like :operationer  or ";
		}
		if (oplog.getOperation_description()!=null&&!oplog.getOperation_description().equals("")) {
			where += " operation_description like :operation_description)  or ";
		}
		if (where.length() == PublicConst.WHERENUM) {
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
	public int add(HttpServletRequest req,String tablename,int tableid,int id,int integral,String operationDescription) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OperationLog opLog = new OperationLog();
		String operationTime = df.format(new Date()).toString();	
		opLog.setTableid(tableid);
		opLog.setOperation_integral(integral);
		opLog.setOperation_time(operationTime);
		opLog.setOperationer(req.getSession().getAttribute("username").toString());			
		opLog.setTablename(tablename);
		SectorProjects p = new SectorProjects();
		p.setProjectid(id);
		opLog.setSectorProjects(p);
		opLog.setOperation_description(operationDescription);
		return this.operationlogdao.add(opLog);
	}
	
	@Override
	public void delete(int id) throws Exception {
		String hql="delete from OperationLog where projectid="+id;
		this.operationlogdao.delete(hql);
	}
	
	@Override
	public List getSumIntegral() throws Exception{
		return this.operationlogdao.listinfo("select operationer,SUM(t.operation_integral) as sumi from operation_log t GROUP BY t.operationer ORDER BY sumi");
	}
}
