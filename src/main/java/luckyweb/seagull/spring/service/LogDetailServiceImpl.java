package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.TestLogdetailDao;
import luckyweb.seagull.spring.entity.TestLogdetail;

@Service("logdetailService")
public class LogDetailServiceImpl implements LogDetailService {

	@Resource(name="logdetailDao")
	private TestLogdetailDao logdetailDao;
	

	public TestLogdetailDao getLogdetailDao() {
		return logdetailDao;
	}

	public void setLogdetailDao(TestLogdetailDao logdetailDao) {
		this.logdetailDao = logdetailDao;
	}
	
	@Override
	public TestLogdetail load(int id) {
		return this.logdetailDao.load(id);
	}

	@Override
	public void add(TestLogdetail log) {
		this.logdetailDao.add(log);
	}

	@Override
	public List<TestLogdetail> list(TestLogdetail log) {
		List<TestLogdetail> list=this.logdetailDao.list(log);
		
		return list;
	}
	
	private static String	orderBy	= " order by id ";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TestLogdetail> findByPage(Object value, int offset, int pageSize)
	{
		String hql = " from TestLogdetail " + where((TestLogdetail) value) + orderBy;
		List<TestLogdetail> list = logdetailDao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	public int findRows(TestLogdetail logs)
	{
		String hql = "select count(*) from TestLogdetail " + where(logs);
		return logdetailDao.findRows(hql, logs);
	}
	

	@Override
	public void delete(int id) throws Exception {
		String hql="delete from TestLogdetail where taskId=:taskId ";
		this.logdetailDao.delete(hql,id);
	}

	private String where(TestLogdetail logs)
	{
		String where = " where ";
		if (logs.getCaseid()!=0){
			where += " caseid=:caseid  and ";
		}
		if (logs.getTaskid()!=0){
			where += " taskid=:taskid  and ";
		}
		if (where.length() == 7)
		{
			where = "";
		}
		else
		{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
}
