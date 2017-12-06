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

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.entity.Accident;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("accidentDao")
public class AccidentDaoImpl extends HibernateDaoSupport implements AccidentDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		try{
			Accident accident = this.load(id);
		    this.getHibernateTemplate().delete(accident);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void whereParameter(Accident accident, Query query) {

		if (accident.getProjectid()!=0) {
			query.setParameter("projectid", accident.getProjectid());
		}
		if (accident.getAccstatus()!=null&&!accident.getAccstatus().equals("")&&!accident.getAccstatus().equals(PublicConst.STATUSSTR00)) {
			query.setParameter("accstatus", accident.getAccstatus());
		}
		if (accident.getAccstarttime()!=null&&!"".equals(accident.getAccstarttime())) {
			query.setParameter("accstarttime", accident.getAccstarttime());
		}
		if (accident.getAccendtime()!=null&&!"".equals(accident.getAccendtime())) {
			query.setParameter("accendtime", accident.getAccendtime());
		}
		if (null!=accident.getAccdescription()&&!"".equals(accident.getAccdescription())) {
			query.setParameter("accdescription", "%"+accident.getAccdescription()+"%");
		}
		if (null!=accident.getCausaltype()&&!"".equals(accident.getCausaltype())) {
			query.setParameter("causaltype", "%"+accident.getCausaltype()+"%");
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
				whereParameter((Accident)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(Accident accident, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(accident, query);
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
	public int add(Accident accident) throws Exception {
		this.getHibernateTemplate().save(accident);
		return accident.getId();
	}



	@Override
	public void modify(Accident accident) throws Exception {
		this.getHibernateTemplate().update(accident);
		
	}



	@Override
	public void modifyState(Accident accident) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(Accident accident) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<Accident> list(Accident accident)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Accident> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public Accident load(int id) throws Exception {
		
		return (Accident) this.getHibernateTemplate().get(Accident.class, id);
		//return (Accident) this.getSession().load(Accident.class, id);
	}


	@Override
	public Accident get(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listavgpro(String sql) throws Exception {
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
