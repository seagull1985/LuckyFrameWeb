package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.UserRoleDao;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;



@Service("userroleService")
public class UserRoleServiceImpl implements UserRoleService{
	
	private UserRoleDao userroledao;
	
	public UserRoleDao getUserRoleDao() {
		return userroledao;
	}

	@Resource(name = "userroleDao")
	public void setUserInfoDao(UserRoleDao userroleDao) {
		this.userroledao = userroleDao;
	}
	
	@Override
	public UserRole load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.userroledao.load(id);
	}
	
	public int add(UserRole userrole) throws Exception{
		return this.userroledao.add(userrole);
	}
	
	public void modify(UserRole userrole) throws Exception{
		this.userroledao.modify(userrole);
	}
	
	public void delete(int id) throws Exception{
		this.userroledao.delete(id);
	}

	@Override
	public List<Object[]> getalldata() throws Exception {
		return this.userroledao.get("select id,role,permission from USER_ROLE");
	}
	
	public List<UserRole> listall() throws Exception{
		return this.userroledao.listall();
	}
}
