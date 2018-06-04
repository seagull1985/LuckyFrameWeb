package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.TestLogdetailDao;
import luckyweb.seagull.spring.entity.TestLogdetail;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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

	@Override
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
		if (null!=logs.getLogGrade()&&logs.getCaseid()!=0){
			where += " caseid=:caseid  and ";
		}
		if (null!=logs.getLogGrade()){
			where += " logGrade=:logGrade  and ";
		}
		if (null!=logs.getLogGrade()&&logs.getTaskid()!=0){
			where += " taskid=:taskid  and ";
		}
		if (where.length() == PublicConst.WHERENUM)
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
