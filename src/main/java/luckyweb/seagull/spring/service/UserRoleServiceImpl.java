package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.UserRoleDao;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;


/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
	
	@Override
	public int add(UserRole userrole) throws Exception{
		return this.userroledao.add(userrole);
	}
	
	@Override
	public void modify(UserRole userrole) throws Exception{
		this.userroledao.modify(userrole);
	}
	
	@Override
	public void delete(int id) throws Exception{
		this.userroledao.delete(id);
	}

	@Override
	public List<Object[]> getalldata() throws Exception {
		return this.userroledao.get("select id,role,permission from USER_ROLE");
	}
	
	@Override
	public List<UserRole> listall() throws Exception{
		return this.userroledao.listall();
	}
}
