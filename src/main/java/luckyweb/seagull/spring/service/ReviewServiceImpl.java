package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ReviewDao;
import luckyweb.seagull.spring.entity.Review;


@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{
	
	private ReviewDao reviewdao;
	
	public ReviewDao getReviewDao() {
		return reviewdao;
	}

	@Resource(name = "reviewDao")
	public void setAccidentDao(ReviewDao reviewdao) {
		this.reviewdao = reviewdao;
	}
	
	@Override
	public int add(Review review) throws Exception {
		return this.reviewdao.add(review);
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.reviewdao.delete(id);		
	}
	
	@Override
	public void modify(Review review) throws Exception {
		this.reviewdao.modify(review);
		
	}
	
	@Override
	public Review load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.reviewdao.get(id);
	}

	private String where(Review review) {
		String where = " where ";
		if (review.getProjectid()!=0) {
			where += " projectid=:projectid  and ";
		}
		if (review.getReview_startdate()!=null&&!"".equals(review.getReview_startdate())) {
			where += " review_date>=:review_startdate  and ";
		}
		if (review.getReview_enddate()!=null&&!"".equals(review.getReview_enddate())) {
			where += " review_date<=:review_enddate  and ";
		}
		if (review.getReview_enddate()!=null&&!"".equals(review.getReview_enddate())) {
			where += " review_date<=:review_enddate  and ";
		}
		if (null!=review.getVersion()&&!"".equals(review.getVersion())) {
			where += " (version like :version  or ";
		}
		if (null!=review.getReview_type()&&!"".equals(review.getReview_type())) {
			where += " review_type like :review_type  or ";
		}
		if (null!=review.getReview_result()&&!"".equals(review.getReview_result())) {
			where += " review_result like :review_result)  or ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by id desc";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from Review  "+where((Review)value)+orderBy;
		List list= reviewdao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(Review review) {
		String hql="select count(*) from Review "+where(review);
		return reviewdao.findRows(review, hql);
	}

}
