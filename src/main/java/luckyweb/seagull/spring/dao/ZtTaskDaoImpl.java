package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.ProjectVersion;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.spring.entity.ZtTask;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("zttaskDao")
public class ZtTaskDaoImpl extends HibernateDaoSupport implements ZtTaskDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public ZtTask load(int id) throws Exception {
		return (ZtTask) this.getHibernateTemplate().get(ZtTask.class, id);
	}

	@Override
	public int add(ZtTask zt) throws Exception {
		this.getHibernateTemplate().save(zt);
		return zt.getId();
	}
	
	@Override
	public void delete(String sql) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session.createSQLQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	private void whereParameter(ZtTask zt, Query query) {
		if (zt.getVersionid()!=0) {
			query.setParameter("versionid", zt.getVersionid());
		}
		if (zt.getDelaystatus()!=0&&!"".equals(zt.getDelaystatus())) {
			query.setParameter("delaystatus", zt.getDelaystatus());
		}
		if (zt.getVersionname()!=null&&!"".equals(zt.getVersionname())) {
			query.setParameter("versionname", "%"+zt.getVersionname()+"%");
		}
		if (zt.getFinishedname()!=null&&!"".equals(zt.getFinishedname())) {
			query.setParameter("finishedname", "%"+zt.getFinishedname()+"%");
		}
		if (zt.getAssstartDate()!=null&&!"".equals(zt.getAssstartDate())) {
			query.setParameter("assstartDate", zt.getAssstartDate()+" 00:00:00.0");
		}
		if (zt.getAssendDate()!=null&&!"".equals(zt.getAssendDate())) {
			query.setParameter("assendDate", zt.getAssendDate()+" 24:59:59.59");
		}
	}
	
	@Override
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((ZtTask)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ZtTask zt, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(zt, query);
			s= Integer.valueOf(
					query
					.list().get(0)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return s;
	}
	
	@Override
	public int findRowsreport(String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			/*Query query=session.createQuery(hql);*/
			Query query= session.createSQLQuery(hql);
			s= Integer.valueOf(
					query.list().size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return s;
	}
	
	@Override
	public List findByPagereport(final String hql,final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				//Query query= session.createQuery(hql);
				Query query= session.createSQLQuery(hql);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}
}
