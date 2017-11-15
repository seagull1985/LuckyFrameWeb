package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.UserAuthorityDao;
import luckyweb.seagull.spring.entity.UserAuthority;



@Service("userauthorityService")
public class UserAuthorityServiceImpl implements UserAuthorityService{
	
	private UserAuthorityDao userauthoritydao;
	
	public UserAuthorityDao getUserInfoDao() {
		return userauthoritydao;
	}

	@Resource(name = "userauthorityDao")
	public void setUserAuthorityDao(UserAuthorityDao userauthoritydao) {
		this.userauthoritydao = userauthoritydao;
	}
	
	@Override
	public UserAuthority load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.userauthoritydao.load(id);
	}
	
	public List<UserAuthority> listall() throws Exception{
		return this.userauthoritydao.listall();
	}
}
