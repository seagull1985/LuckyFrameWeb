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

import luckyweb.seagull.spring.entity.OperationLog;



/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("operationlogDao")
public class OperationLogDaoImpl extends HibernateDaoSupport implements OperationLogDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public OperationLog load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (OperationLog) this.getHibernateTemplate().get(OperationLog.class, id);
	}

	@Override
	public int add(OperationLog oplog) throws Exception {
		this.getHibernateTemplate().save(oplog);
		return oplog.getId();
	}
	
	private void whereParameter(OperationLog oplog, Query query) {
		if (oplog.getStarttime()!=null&&oplog.getEndtime()!=null
				&&!oplog.getStarttime().equals("")&&!oplog.getEndtime().equals("")) {
			query.setParameter("starttime", oplog.getStarttime());
			query.setParameter("endtime", oplog.getEndtime());
		}
		if (oplog.getProjectid()!=0) {
			query.setParameter("projectid", oplog.getProjectid());
		}
		if (oplog.getOperationer()!=null&&!oplog.getOperationer().equals("")) {
			query.setParameter("operationer", "%"+oplog.getOperationer()+"%");
		}
		if (oplog.getOperation_description()!=null&&!oplog.getOperation_description().equals("")) {
			query.setParameter("operation_description", "%"+oplog.getOperation_description()+"%");
		}

	}
	
	@Override
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((OperationLog)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}


	@Override
	public int findRows(OperationLog oplog, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(oplog, query);
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
	public void delete(String hql) throws Exception {
		Session session=this.getSession(true);
		try {
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.executeUpdate();
		
		session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	@Override
	public List listinfo(String sql) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.getSession(true);
		List count = session.createSQLQuery(sql).list();
		session.close();
		return count;
	}
}
