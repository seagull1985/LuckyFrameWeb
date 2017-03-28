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
	
	
}
