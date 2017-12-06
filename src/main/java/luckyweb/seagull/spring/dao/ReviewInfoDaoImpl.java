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

import luckyweb.seagull.spring.entity.ReviewInfo;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("reviewInfoDao")
public class ReviewInfoDaoImpl extends HibernateDaoSupport implements ReviewInfoDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		try{
			ReviewInfo reviewinfo = this.get(id);
		    this.getHibernateTemplate().delete(reviewinfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(String hql) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}
	
	private void whereParameter(ReviewInfo reviewinfo, Query query) {

		if (reviewinfo.getReview_id()!=0) {
			query.setParameter("review_id", reviewinfo.getReview_id());
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
				whereParameter((ReviewInfo)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ReviewInfo reviewinfo, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(reviewinfo, query);
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
	public int add(ReviewInfo reviewinfo) throws Exception {
		this.getHibernateTemplate().save(reviewinfo);
		return reviewinfo.getId();
	}



	@Override
	public void modify(ReviewInfo reviewinfo) throws Exception {
		this.getHibernateTemplate().update(reviewinfo);
		
	}

	@Override
	public ReviewInfo get(int id) throws Exception {
		// TODO Auto-generated method stub
		return (ReviewInfo) this.getHibernateTemplate().get(ReviewInfo.class, id);
	}

}
