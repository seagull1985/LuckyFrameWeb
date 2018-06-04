package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

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
@Repository("logdetailDao")
public class TestLogdetailDaoImpl extends HibernateDaoSupport implements
		TestLogdetailDao {
	private static final Logger logger = Logger
			.getLogger(TestLogdetailDaoImpl.class);

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void add(TestLogdetail caseDetail) {
		this.getHibernateTemplate().save(caseDetail);
	}

	@Override
	public List<TestLogdetail> list(TestLogdetail log) {
		Session session=this.getSession(true);
		List<TestLogdetail> list = session.createQuery(" from TestLogdetail where caseid=?  order by id asc")
				.setInteger(0, log.getCaseid()).list();
		session.close();
		return list;
	}

	@Override
	public TestLogdetail load(int id) {
		return (TestLogdetail) this.getHibernateTemplate().get(TestLogdetail.class, id);

	}

	@Override
	public void delete(String hql, int id) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql).setInteger("taskId", id);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * 使用hql语句进行分页查询
	 * 
	 * @param hql 需要查询的hql语句
	 * 
	 * @param offset 第一条记录索引
	 * 
	 * @param pageSize 每页需要显示的记录条数
	 * 
	 * @return 当前页的所有记录
	 */
	@Override
	public List findByPage(final String hql, final Object value,
			final int offset, final int pageSize) {
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((TestLogdetail)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;

	}
	
	@Override
	public int findRows(String hql, TestLogdetail log) {
		int s = 0;
		Session session = this.getSession(true);
		try {
			Query query = session.createQuery(hql);
			whereParameter(log, query);
			s = Integer.valueOf(query.list().get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return s;
	}
	
	private void whereParameter(TestLogdetail log, Query query) {
		if (null!=log.getLogGrade()&&log.getTaskid()!=0) {
			query.setParameter("taskid", log.getTaskid());
		}
		if (null!=log.getLogGrade()&&log.getCaseid()!=0) {
			query.setParameter("caseid", log.getCaseid());
		}
		if (null!=log.getLogGrade()) {
			query.setParameter("logGrade", log.getLogGrade());
		}
	}
}
