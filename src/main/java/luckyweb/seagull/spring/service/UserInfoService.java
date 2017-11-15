package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.UserInfo;


@Transactional
public interface UserInfoService {
	
	public UserInfo load(int id)throws Exception;
	public int add(UserInfo userinfo) throws Exception;
	public void modify(UserInfo userinfo) throws Exception;
	public void delete(int id) throws Exception;
	
	public int getid(String name,String password)throws Exception;
	
	public List findByPage( final Object value,final int offset, final int pageSize);

	public int findRows(UserInfo userinfo) ;
	public int findUsecode(String usercode);
	public UserInfo getUseinfo(String usercode) throws Exception;
}
