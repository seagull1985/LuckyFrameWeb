package luckyweb.seagull.comm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.web.context.ContextLoaderListener;

import luckyweb.seagull.spring.dao.TestJobsDaoImpl;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.TestClient;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.util.HibernateSessionFactoryUtil;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 * 
 */
public class QueueListener extends ContextLoaderListener implements
		ServletContextListener {

	private static final Logger log = Logger.getLogger(QueueListener.class);
	public static List<TestJobs> list = new ArrayList<TestJobs>();
	public static List<SectorProjects> projlist = new ArrayList<SectorProjects>();
	public static List<SectorProjects> qa_projlist = new ArrayList<SectorProjects>();
	public static List<TestClient> listen_Clientlist = new ArrayList<TestClient>();
	public static Session session = HibernateSessionFactoryUtil.getCurrentSession();

	public static SchedulerFactory gSchedulerFactory = null;
	public static Scheduler sched = null;
	public static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	public static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// Session session=HibernateSessionFactoryUtil.getCurrentSession();
		
		// 加载定时任务，tomcat启动时则启动
		try {
			TestJobsDaoImpl.init(session);
			log.info("服务器启动时则启动,加载定时任务");
		} catch (Exception e) {
			log.error(" 服务启动异常 ：" + e);
			try {
				HibernateSessionFactoryUtil.closeSession();
			} catch (Exception e1) {
				log.error(" 关闭sessionFactory 异常 ：" + e);
			}
		}

	}
		
		
		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			// 服务关闭时，定时任务关闭

			try {
				if (QueueListener.sched.isStarted()) {
					QueueListener.sched.shutdown();
					Thread.sleep(1000); 
				}
			} catch (SchedulerException | InterruptedException e2) {
				e2.printStackTrace();
			}

			try {
				TestJobsDaoImpl.stop(session);
				log.info(" 服务关闭时，定时任务关闭");
			} catch (Exception e) {
				log.error(" 服务关闭异常 ：" + e);
				
			}

		}



}
