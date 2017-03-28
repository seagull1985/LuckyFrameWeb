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
import luckyweb.seagull.spring.entity.FlowCheck;
import luckyweb.seagull.spring.entity.FlowInfo;

@Repository("flowcheckDao")
public class FlowCheckDaoImpl extends HibernateDaoSupport implements FlowCheckDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		FlowCheck flowcheck = this.load(id);
		this.getHibernateTemplate().delete(flowcheck);
		
	}

	private void whereParameter(FlowCheck flowcheck, Query query) {
		if (flowcheck.getId()!=0) {
			query.setParameter("id", flowcheck.getId());
		}
		if (flowcheck.getProjectid()!=0) {
			query.setParameter("projectid", flowcheck.getProjectid());
		}
		if (flowcheck.getCheckid()!=0) {
			query.setParameter("checkid", flowcheck.getCheckid());
		}
		if (flowcheck.getVersionnum()!=null&&!flowcheck.getVersionnum().equals("")) {
			query.setParameter("versionnum", flowcheck.getVersionnum());
		}
		if (flowcheck.getCheckenddate()!=null&&flowcheck.getCheckstartdate()!=null&&
				!flowcheck.getCheckenddate().equals("")&&!flowcheck.getCheckstartdate().equals("")) {
			query.setParameter("checkstartdate", flowcheck.getCheckstartdate());
			query.setParameter("checkenddate", flowcheck.getCheckenddate());
		}
	}
	
	private void whereParameter(FlowInfo flowinfo, Query query) {
		if (flowinfo.getId()!=0) {
			query.setParameter("projectid", flowinfo.getId());
		}
	}
	
	@Override
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				//Query query= session.createQuery(hql);
				Query query= session.createSQLQuery(hql);
				whereParameter((FlowCheck)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}
	
	@Override
	public List findByPageTable(final String hql, final Object value, final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((FlowCheck)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}


	@Override
	public int findRows(FlowCheck flowcheck, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			/*Query query=session.createQuery(hql);*/
			Query query= session.createSQLQuery(hql);
			whereParameter(flowcheck, query);
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
	public int findRowsTable(FlowCheck flowcheck, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(flowcheck, query);
			s= Integer.valueOf(
					query.list().get(0)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return s;
	}
	

	@Override
	public int add(FlowCheck flowcheck) throws Exception {
		this.getHibernateTemplate().save(flowcheck);
		return flowcheck.getCheckid();
	}



	@Override
	public void modify(FlowCheck flowcheck) throws Exception {
		this.getHibernateTemplate().update(flowcheck);
		
	}



	@Override
	public void modifyState(FlowCheck flowcheck) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(FlowCheck flowcheck) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<FlowCheck> list(FlowCheck flowcheck)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<FlowCheck> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public FlowCheck load(int id) throws Exception {
		return (FlowCheck) this.getHibernateTemplate().get(FlowCheck.class, id);
		//return (FlowCheck) this.getSession().load(FlowCheck.class, id);
	}



	@Override
	public List<FlowCheck> load(String name, String cmdType,
			String planPath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String get(String sql) throws Exception {
		String result = "";
		Session session = this.getSession(true);
		@SuppressWarnings("unchecked")
		List<Object> count = session.createSQLQuery(sql).list();
		if(count.size()!=0&&count.get(0)!=null){
			result = count.get(0).toString();
		}
		session.close();
		return result;
	}


	@Override
	public List<FlowCheck> findJobsList() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List listcheckinfo(String sql) throws Exception {
		// TODO Auto-generated method stub
		List count=null;
		Session session=this.getSession(true);
		try {
			count = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return count;
	}


}
