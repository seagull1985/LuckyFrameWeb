package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ReviewInfo;


@Transactional
public interface ReviewInfoDao {
	public int add(ReviewInfo reviewinfo) throws Exception;

	public void modify(ReviewInfo reviewinfo) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<ReviewInfo> list(ReviewInfo reviewinfo) throws Exception;

	public ReviewInfo get(int id) throws Exception;

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(ReviewInfo reviewinfo,String hql);
	
	public void update(String hql) throws Exception;
}
