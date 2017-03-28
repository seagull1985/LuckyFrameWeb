package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.ReviewInfoDao;
import luckyweb.seagull.spring.entity.ReviewInfo;


@Service("reviewInfoService")
public class ReviewInfoServiceImpl implements ReviewInfoService{
	
	private ReviewInfoDao reviewinfodao;
	
	public ReviewInfoDao getReviewinfoDao() {
		return reviewinfodao;
	}

	@Resource(name = "reviewInfoDao")
	public void setAccidentDao(ReviewInfoDao reviewinfodao) {
		this.reviewinfodao = reviewinfodao;
	}
	
	@Override
	public int add(ReviewInfo reviewinfo) throws Exception {
		return this.reviewinfodao.add(reviewinfo);
	}
	
	@Override
	public void delete(int id) throws Exception {
		this.reviewinfodao.delete(id);		
	}
	
	@Override
	public void modify(ReviewInfo reviewinfo) throws Exception {
		this.reviewinfodao.modify(reviewinfo);
		
	}
	
	@Override
	public ReviewInfo load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.reviewinfodao.get(id);
	}

	private String where(ReviewInfo reviewinfo) {
		String where = " where ";
		if (reviewinfo.getReview_id()!=0) {
			where += " review_id=:review_id  and ";
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
		String	hql=" from ReviewInfo  "+where((ReviewInfo)value)+orderBy;
		List list= reviewinfodao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(ReviewInfo reviewinfo) {
		String hql="select count(*) from ReviewInfo "+where(reviewinfo);
		return reviewinfodao.findRows(reviewinfo, hql);
	}

	@Override
	public void delete_reviewid(int reviewid) throws Exception{
		String hql="delete from ReviewInfo where review_id="+reviewid;
		this.reviewinfodao.update(hql);
	}
	
}
