package luckyweb.seagull.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


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
	
	
	public static void main(String[] args) throws Exception {
		
//		Session s=HibernateSessionFactoryUtil.getCurrentSession();
//		Transaction tx=s.beginTransaction();
//		tx.commit();
//		closeSession();
		new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();
	}
	
	public static void closeSession() throws Exception{
		try {
			if(threadLocal.get().isOpen()){
				threadLocal.get().close();
				sessionFactory.close();
				System.out.println("close  session");
			}
		} catch (Exception e) {
			throw new Exception("关闭sessionFacory 异常："+e.getMessage());
		}
	}
}
