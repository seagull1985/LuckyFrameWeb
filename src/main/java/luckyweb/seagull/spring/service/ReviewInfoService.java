package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ReviewInfo;

@Transactional
public interface ReviewInfoService {
	public int add(ReviewInfo reviewinfo)throws Exception;
	
	public void delete(int id)throws Exception;
	
	public void modify(ReviewInfo reviewinfo)throws Exception;
	public ReviewInfo load(int id)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);

	public int findRows(ReviewInfo reviewinfo) ;
	
	public void delete_reviewid(int reviewid) throws Exception;
}
