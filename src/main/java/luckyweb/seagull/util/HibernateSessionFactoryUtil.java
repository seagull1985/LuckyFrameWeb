package luckyweb.seagull.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
public class HibernateSessionFactoryUtil {
	private static final Logger logger = Logger.getLogger(HibernateSessionFactoryUtil.class);
	
	public static SessionFactory sessionFactory = null;

	public static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	public HibernateSessionFactoryUtil() {
	}

	static {
		sessionFactory = new Configuration().configure("./hibernate.cfg.xml").buildSessionFactory();
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

	public static Session getCurrentSession() {

		Session session = threadLocal.get();
		if (session == null) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}
	
	public static void closeSession() throws Exception{
		try {
			if(threadLocal.get().isOpen()){
				threadLocal.get().close();
				threadLocal.remove();
				sessionFactory.close();
				System.out.println("close  session");
			}
		} catch (Exception e) {
			throw new Exception("关闭sessionFacory 异常："+e.getMessage());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
//		Session s=HibernateSessionFactoryUtil.getCurrentSession();
//		Transaction tx=s.beginTransaction();
//		tx.commit();
//		closeSession();
		new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
	}
}
