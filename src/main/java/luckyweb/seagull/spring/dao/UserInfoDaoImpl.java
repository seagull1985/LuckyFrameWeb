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

import luckyweb.seagull.spring.entity.Review;
import luckyweb.seagull.spring.entity.UserInfo;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("userinfoDao")
public class UserInfoDaoImpl extends HibernateDaoSupport implements UserInfoDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public UserInfo load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (UserInfo) this.getHibernateTemplate().get(UserInfo.class, id);
	}

	@Override
	public int add(UserInfo userinfo) throws Exception {
		this.getHibernateTemplate().save(userinfo);
		return userinfo.getId();
	}

	@Override
	public void modify(UserInfo userinfo) throws Exception {
		this.getHibernateTemplate().update(userinfo);		
	}
	
	@Override
	public void delete(int id) throws Exception {
		try{
			UserInfo userinfo = this.load(id);
		    this.getHibernateTemplate().delete(userinfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int get(String sql) throws Exception {
		int id = 0;
		Session session = this.getSession(true);
		@SuppressWarnings("unchecked")
		List<Object> count = session.createSQLQuery(sql).list();
		if(count.size()!=0){
		if(count.get(0)!=null){
			id = Integer.valueOf(count.get(0).toString());
		  }
		}
		session.close();
		return id;	
	}

	private void whereParameter(UserInfo userinfo, Query query) {
		if (null!=userinfo.getUsercode()&&!"".equals(userinfo.getUsercode())) {
			query.setParameter("usercode", "%"+userinfo.getUsercode()+"%");
		}
		if (null!=userinfo.getUsername()&&!"".equals(userinfo.getUsername())) {
			query.setParameter("username", "%"+userinfo.getUsername()+"%");
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
				whereParameter((UserInfo)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(UserInfo userinfo, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(userinfo, query);
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
	public int sqlfindRows(String hql) {
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public List listsql(String sql) throws Exception {		
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
