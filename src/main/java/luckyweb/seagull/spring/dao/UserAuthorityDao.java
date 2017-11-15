package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.UserAuthority;


@Transactional
public interface UserAuthorityDao {

	public UserAuthority load(int id) throws Exception;
	public List<UserAuthority> listall() throws Exception;
	public List<Object[]> get(String sql) throws Exception;

}
