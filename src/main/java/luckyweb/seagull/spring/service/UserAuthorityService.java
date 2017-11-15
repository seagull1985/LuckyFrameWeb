package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.UserAuthority;


@Transactional
public interface UserAuthorityService {
	
	public UserAuthority load(int id)throws Exception;
	public List<UserAuthority> listall() throws Exception;
}
