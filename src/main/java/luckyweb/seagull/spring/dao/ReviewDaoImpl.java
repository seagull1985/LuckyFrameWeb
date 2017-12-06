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

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
		if (null!=review.getVersion()&&!"".equals(review.getVersion())) {
			query.setParameter("version", "%"+review.getVersion()+"%");
		}
		if (null!=review.getReview_type()&&!"".equals(review.getReview_type())) {
			query.setParameter("review_type", "%"+review.getReview_type()+"%");
		}
		if (null!=review.getReview_result()&&!"".equals(review.getReview_result())) {
			query.setParameter("review_result", "%"+review.getReview_result()+"%");
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
	public Review get(int id) throws Exception {
		// TODO Auto-generated method stub
		return (Review) this.getHibernateTemplate().get(Review.class, id);
	}

}
