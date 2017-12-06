package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.ReviewInfoDao;
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
		if (where.length() == PublicConst.WHERENUM) {
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
	public void deleteReviewid(int reviewid) throws Exception{
		String hql="delete from ReviewInfo where review_id="+reviewid;
		this.reviewinfodao.update(hql);
	}
	
}
