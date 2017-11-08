package luckyweb.seagull.spring.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.TestJobsDao;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.StrLib;

@Service("testJobsService")
public class TestJobsServiceImpl implements TestJobsService
{

	private TestJobsDao	testJobsDao;

	public TestJobsDao getTestJobsDao()
	{
		return testJobsDao;
	}

	@Resource(name = "testJobsDao")
	public void setTestJobsDao(TestJobsDao testJobsDao)
	{
		this.testJobsDao = testJobsDao;
	}

	@Override
	public int add(TestJobs tjob) throws Exception
	{
		return this.testJobsDao.add(tjob);

	}

	@Override
	public void modify(TestJobs tjob) throws Exception
	{
		this.testJobsDao.modify(tjob);

	}

	@Override
	public void modifyState(TestJobs tjob) throws Exception
	{
		this.testJobsDao.modify(tjob);

	}

	@Override
	public void delete(int id) throws Exception
	{
		this.testJobsDao.delete(id);
	}

	@Override
	public List<TestJobs> list(TestJobs tjob) throws Exception
	{
		List<TestJobs> list = this.testJobsDao.list(tjob);
		for (TestJobs job : list)
		{
			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
			int res = DateLib.compare_date(job.getStartDate() + " " + job.getStartTime(), now);
			if (job.getTaskType().equals("O"))
			{
				if (res == 2)
				{
					job.setShowRun(false);
				}
			}
		}
		return list;
	}

	@Override
	public List<TestJobs> list() throws Exception
	{
		List<TestJobs> list = this.testJobsDao.list();
		for (TestJobs job : list)
		{
			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
			int res = DateLib.compare_date(job.getStartDate() + " " + job.getStartTime(), now);
			if (job.getTaskType().equals("O"))
			{
				if (res < 1)
				{
					job.setShowRun(false);
				}
			}
		}
		return list;
	}

	@Override
	public TestJobs load(int id) throws Exception
	{
		return this.testJobsDao.load(id);
	}

	@Override
	public TestJobs get(int id) throws Exception
	{

		return this.testJobsDao.get(id);
	}

	@Override
	public boolean isExist(String name, String cmdType, String planPath) throws Exception
	{
		if (this.testJobsDao.load(name, cmdType, planPath).size() == 0)
		{
			// System.out.println(false);
			return false;
		}
		return true;
	}

	private static String	orderBy	= " order by id desc ";

	@SuppressWarnings("unchecked")
	@Override
	public List findByPage(Object value, int offset, int pageSize)
	{
		String hql = " from TestJobs " + where((TestJobs) value) + orderBy;
		List list = testJobsDao.findByPage(hql, value, offset, pageSize);
		for (TestJobs jobs : (List<TestJobs>) list)
		{
			jobs.setState_str(setStatus(jobs.getState()));
		}
		return list;
	}

	public int findRows(TestJobs jobs)
	{
		String hql = "select count(*) from TestJobs " + where(jobs);
		return testJobsDao.findRows(jobs, hql);
	}

	@Override
	public List<TestJobs> findJobsList()
	{
		return testJobsDao.findJobsList("select id,name,planproj from Test_Jobs  order by id asc ");
	}

	@Override
	public List getpathList(int projectid)
	{
		return testJobsDao.findJobsList("SELECT clientpath FROM test_jobs where projectid="+projectid+" GROUP BY clientpath ");
	}
	
	/**
	 * 状态
	 * 
	 * @param status
	 * @return
	 */
	private String setStatus(String status)
	{
		if (StrLib.isEmpty(status))
		{
			return "其他";
		}
		else
		{
			if (status.equals("0"))
			{
				return "未启动";
			}
			else if (status.equals("1"))
			{
				return "任务生效中";
			}
		}
		return "其他";
	}

	private String where(TestJobs jb)
	{
		String where = " where ";
		if (jb.getProjectid()!=0&&jb.getProjectid()!=99){
			where += " projectid=:projectid  and ";
		}
		if (!StrLib.isEmpty(jb.getTaskName())){
			where += " (name like :name  or ";
		}
		if (!StrLib.isEmpty(jb.getPlanproj())){
			where += " planproj like :planproj)  or ";
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
