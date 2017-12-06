package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.UserAuthorityDao;
import luckyweb.seagull.spring.entity.UserAuthority;


/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
	
	@Override
	public List<UserAuthority> listall() throws Exception{
		return this.userauthoritydao.listall();
	}
}
