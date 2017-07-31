package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.TestLogdetailDao;
import luckyweb.seagull.spring.entity.TestJobs;
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
	/*public static final String hql=" from TestLogdetail where caseid=? ";

	@Override
	public List findByPage(TestLogdetail  logdetail,int offset, int pageSize) {
		return logdetailDao.findByPage(logdetail, hql, offset, pageSize);
	}

	@Override
	public List findByPage( Object[] values, int offset, int pageSize) {
		return logdetailDao.findByPage(hql, values, offset, pageSize);
	}

	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		return logdetailDao.findByPage(hql, value, offset, pageSize);
	}*/

	
	/*public int findRows(TestLogdetail logdetail ) {
		String hql="select count(*) from TestLogdetail where caseid=? ";
		return logdetailDao.findRows(hql, logdetail);
	}*/
	

	@Override
	public void delete(int id) throws Exception {
		String hql="delete from TestLogdetail where taskId=:taskId ";
		this.logdetailDao.delete(hql,id);
	}

}
