package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.UserRole;


@Transactional
public interface UserRoleDao {

	public UserRole load(int id) throws Exception;
	public List<UserRole> listall() throws Exception;
	public List<Object[]> get(String sql) throws Exception;
	public int add(UserRole userrole) throws Exception;
	public void modify(UserRole userrole) throws Exception;
	public void delete(int id) throws Exception;

}
