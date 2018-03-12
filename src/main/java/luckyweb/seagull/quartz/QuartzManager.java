package luckyweb.seagull.quartz;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import luckyweb.seagull.comm.QueueListener;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
public class QuartzManager {
	
	//private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	//private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	//private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	private static final Logger log = Logger.getLogger(QuartzManager.class);

	
	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param cls
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * 
	 */
	public static void addJob(String jobName, Class cls, String time) {
		try {
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = new JobDetail(jobName, QueueListener.JOB_GROUP_NAME, cls);
			//System.out.println("jobDetail 's  Name :"+jobDetail.getName());
			// 触发器名,触发器组
			CronTrigger trigger = new CronTrigger(jobDetail.getName(), QueueListener.TRIGGER_GROUP_NAME);
			// 触发器时间设定
			trigger.setCronExpression(time);
			QueueListener.sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!QueueListener.sched.isShutdown()) {
				System.out.println("启动。。"+jobName +" ,时间:"+time);
				QueueListener.sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException 
	 * 
	 * @Title: QuartzManager.java
	 * 
	 */
	public static void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, Class jobClass,
			String time) throws SchedulerException {
	//	QueueListener.sched=QueueListener.gSchedulerFactory.getScheduler();
		try {
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);
			// 触发器名,触发器组
			CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);
			// 触发器时间设定
			trigger.setCronExpression(time);
			QueueListener.sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * 
	 * @Title: QuartzManager.java
	 * 
	 */
	public static String modifyJobTime(String jobName, String time) {
		try {
			Scheduler sched=QueueListener.gSchedulerFactory.getScheduler();
			
			CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName,QueueListener.TRIGGER_GROUP_NAME);
			if (trigger == null) {				
				return "任务已经执行过或者不在队列中,请暂定运行后,重启再修改时间.";
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobDetail jobDetail = sched.getJobDetail(jobName, QueueListener.JOB_GROUP_NAME);
				Class objJobClass = jobDetail.getJobClass();
				removeJob(jobName);
				addJob(jobName, objJobClass, time);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "";
	}

	/**
	 * @Description: 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * 
	 */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) {
		try {
			QueueListener.sched = QueueListener.gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) QueueListener.sched.getTrigger(triggerName,
					triggerGroupName);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.setCronExpression(time);
				// 重启触发器
				QueueListener.sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void removeJob(String jobName) {
		try {
			QueueListener.sched = QueueListener.gSchedulerFactory.getScheduler();
			// 停止触发器
			QueueListener.sched.pauseTrigger(jobName, QueueListener.TRIGGER_GROUP_NAME);
			// 移除触发器
			QueueListener.sched.unscheduleJob(jobName, QueueListener.TRIGGER_GROUP_NAME);
			// 删除任务
			QueueListener.sched.deleteJob(jobName, QueueListener.TRIGGER_GROUP_NAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = QueueListener.gSchedulerFactory.getScheduler();
			// 停止触发器
			sched.pauseTrigger(triggerName, triggerGroupName);
			// 移除触发器
			sched.unscheduleJob(triggerName, triggerGroupName);
			// 删除任务
			sched.deleteJob(jobName, jobGroupName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:启动所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void startJobs() {
		try {
			Scheduler sched = QueueListener.gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 * 
	 * @author Comsys-LZP
	 * @date 2014-6-26 下午03:50:26
	 * @version V2.0
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = QueueListener.gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
