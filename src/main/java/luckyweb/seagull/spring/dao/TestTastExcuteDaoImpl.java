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

import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.util.StrLib;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("tastExcuteDao")
public class TestTastExcuteDaoImpl extends HibernateDaoSupport implements
		TestTastExcuteDao {
	private static final Logger log = Logger
			.getLogger(TestTastExcuteDaoImpl.class);

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void add(TestTaskexcute tast) {
		this.getHibernateTemplate().save(tast);
	}

	@Override
	public List<TestTaskexcute> list(TestTaskexcute tast) {
		List<TestTaskexcute> list = null;
		Session session = this.getSession(true);
		try {
			list = session
					.createQuery(
							"from TestTastexcute where jobid=?  order by asc ")
					.setInteger(0, tast.getTestJob().getId()).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public TestTaskexcute load(int id) {
/*		return (TestTastexcute) this.getSession()
				.load(TestTastexcute.class, id);*/
		return (TestTaskexcute) this.getHibernateTemplate().get(TestTaskexcute.class, id);
	}

	@Override
	public TestTaskexcute get(int id) {
//		return (TestTastexcute) this.getSession().get(TestTastexcute.class, id);
		return (TestTaskexcute) this.getHibernateTemplate().get(TestTaskexcute.class, id);
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
	public List findByPage(final TestTaskexcute tast, final String hql,
			final int offset, final int pageSize) {
		// 通过一个HibernateCallback 对象来执行查询

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				List result = null;
				try {
					result = session.createQuery(hql)
							.setInteger(0, tast.getTestJob().getId())
							.setFirstResult(offset).setMaxResults(pageSize)
							.list();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.close();
				}
				return result;
			}

		});
		return list;

	}

	/**
	 * 使用hql语句进行分页查询
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param value
	 *            如果hql 有一个参数需要传入，value就是传入Hql语句的参数
	 * 
	 * @param offset
	 *            第一条记录索引
	 * 
	 * @param pageSize
	 *            每页需要显示的记录条数
	 * @return 当前页的所有记录
	 */
	@Override
	public List findByPage(final String hql, final Object value,
			final int offset, final int pageSize) {
		// 通过一个HibernateCallback 对象来执行查询

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				List result = null;
				try {
					Query query = session.createQuery(hql);
					whereParameter((TestTaskexcute) value, query);
					result = query.setFirstResult(offset)
							.setMaxResults(pageSize).list();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.close();
				}
				return result;
			}

		});
		return list;

	}

	@Override
	public int findRows(String hql, TestTaskexcute tast) {
		int s = 0;
		Session session = this.getSession(true);
		try {
			Query query = session.createQuery(hql);
			whereParameter(tast, query);
			s = Integer.valueOf(query.list().get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return s;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List findTastList(String sql) {
		Session session = this.getSession(true);
		List tastlist = session.createSQLQuery(sql).list();
		session.close();
		return tastlist;
	}

	private void whereParameter(TestTaskexcute tast, Query query) {
		if (!StrLib.isEmpty(tast.getStartDate())) {
			query.setParameter(0, tast.getStartDate());
		}
		if (!StrLib.isEmpty(tast.getStartDate())) {
			query.setParameter(1, tast.getEndDate());
		}
		if (tast.getJobid() != 0) {
			query.setParameter("jobId", tast.getJobid());
		}
		if (!StrLib.isEmpty(tast.getTaskId())) {
			query.setParameter("taskId", "%" + tast.getTaskId().trim() + "%");
		}
		if (!StrLib.isEmpty(tast.getTaskStatus()) ) {
			query.setParameter("taskStatus", tast.getTaskStatus());
		}
	}

	@Override
	public void delete(int id) throws Exception {
		TestTaskexcute tast = this.get(id);
		this.getHibernateTemplate().delete(tast);
	}
	
	@Override
	public List listtastinfo(String sql) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.getSession(true);
		List count = session.createSQLQuery(sql).list();
		session.close();
		return count;
	}
	
	@Override
	public void deleteForJobid(String hql, int jobid) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.setInteger("jobId", jobid);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}

}
