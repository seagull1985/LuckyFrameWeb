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

@Repository("reviewDao")
public class ReviewDaoImpl extends HibernateDaoSupport implements ReviewDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		try{
			Review review = this.get(id);
		    this.getHibernateTemplate().delete(review);
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
	
	private void whereParameter(Review review, Query query) {
		if (review.getProjectid()!=0) {
			query.setParameter("projectid", review.getProjectid());
		}
		if (review.getReview_startdate()!=null&&!"".equals(review.getReview_startdate())) {
			query.setParameter("review_startdate", review.getReview_startdate());
		}
		if (review.getReview_enddate()!=null&&!"".equals(review.getReview_enddate())) {
			query.setParameter("review_enddate", review.getReview_enddate());
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
				Query query= session.createQuery(hql);
				whereParameter((Review)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(Review review, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(review, query);
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
	public int add(Review review) throws Exception {
		this.getHibernateTemplate().save(review);
		return review.getId();
	}



	@Override
	public void modify(Review review) throws Exception {
		this.getHibernateTemplate().update(review);
		
	}


	@Override
	public List<Review> list(Review review)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Review get(int id) throws Exception {
		// TODO Auto-generated method stub
		return (Review) this.getHibernateTemplate().get(Review.class, id);
	}

}
