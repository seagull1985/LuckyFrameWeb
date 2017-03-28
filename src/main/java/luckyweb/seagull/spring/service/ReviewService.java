package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.Review;

@Transactional
public interface ReviewService {
	public int add(Review review)throws Exception;
	public void delete(int id)throws Exception;
	public void modify(Review review)throws Exception;
	public Review load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);

	public int findRows(Review review) ;
}
