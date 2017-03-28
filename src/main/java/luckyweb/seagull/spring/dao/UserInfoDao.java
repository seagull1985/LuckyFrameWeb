package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.UserInfo;


@Transactional
public interface UserInfoDao {

	public UserInfo load(int id) throws Exception;
	public int add(UserInfo userinfo) throws Exception;
	public void modify(UserInfo userinfo) throws Exception;
	public void delete(int id) throws Exception;
	
	public int get(String sql) throws Exception;
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	public int findRows(UserInfo userinfo,String hql);
	public int sqlfindRows(String hql);
	@SuppressWarnings("rawtypes")
	public List listsql(String sql) throws Exception;
}
