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

import luckyweb.seagull.spring.entity.TempCasestepDebug;
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
@Repository("tempcasestepdebugDao")
public class TempCasestepDebugImpl extends HibernateDaoSupport implements TempCasestepDebugDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public TempCasestepDebug load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (TempCasestepDebug) this.getHibernateTemplate().get(TempCasestepDebug.class, id);
	}

	@Override
	public int add(TempCasestepDebug tcd) throws Exception {
		this.getHibernateTemplate().save(tcd);
		return tcd.getId();
	}
	
	@Override
	public void deleteforob(TempCasestepDebug tcd) throws Exception {
		try{
		    this.getHibernateTemplate().delete(tcd);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void modify(TempCasestepDebug tcd) throws Exception {
		this.getHibernateTemplate().update(tcd);
		
	}
	
	private void whereParameter(TempCasestepDebug tcd, Query query) {
		if (!StrLib.isEmpty(tcd.getSign())) {
			query.setParameter("sign", tcd.getSign());
		}
		if (!StrLib.isEmpty(tcd.getExecutor())) {
			query.setParameter("executor", tcd.getExecutor());
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
				whereParameter((TempCasestepDebug)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}


	@Override
	public int findRows(TempCasestepDebug tcd, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(tcd, query);
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<TempCasestepDebug> getParamsList(String sql) throws Exception {
		// TODO Auto-generated method stub
		List<TempCasestepDebug> list=null;
		Session session=this.getSession(true);
		try {
			list = session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return list;
	}
	
}
