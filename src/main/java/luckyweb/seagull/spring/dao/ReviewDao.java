package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.Review;

@Transactional
public interface ReviewDao {
	public int add(Review review) throws Exception;

	public void modify(Review review) throws Exception;

	public void delete(int id) throws Exception;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Review> list(Review review) throws Exception;

	public Review get(int id) throws Exception;

	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(Review review,String hql);
	public void update(String hql) throws Exception;
}
