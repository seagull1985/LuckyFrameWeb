package luckyweb.seagull.quartz;

import org.apache.log4j.Logger;

import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.entity.TestJobs;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
public class QuratzJobDataMgr
{
	private static final Logger	logger	= Logger.getLogger(QuratzJobDataMgr.class);

	public void addJobRunTime(TestJobs tj, int id) throws Exception
	{
		// 比较任务的开始时间和当前时间。如果是返回1，说明任务的开始时间是还没有过，如果返回2 说明任务的开时间应过去
		try
		{
			String startTime = tj.getStartTimestr();
			// String startTime=setStartTime(tj);
			QuartzManager.addJob(id + "*JOB", QuartzJob.class, startTime);
		}
		catch (Exception e)
		{
			logger.error(e);
			throw e;
		}
	}

	public void addTestClient(TestClient tc, int id) throws Exception
	{
		// 比较任务的开始时间和当前时间。如果是返回1，说明任务的开始时间是还没有过，如果返回2 说明任务的开时间应过去
		try
		{
			int checkTime = tc.getCheckinterval();
			String startTime="0/"+checkTime+" * * * * ?";
			QuartzManager.addJob(id + "*CLIENT", QuartzJob.class, startTime);
		}
		catch (Exception e)
		{
			logger.error(e);
			throw e;
		}
	}
	
}
