package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.UserInfoDao;
import luckyweb.seagull.spring.entity.UserInfo;



@Service("userinfoService")
public class UserInfoServiceImpl implements UserInfoService{
	
	private UserInfoDao userinfodao;
	
	public UserInfoDao getUserInfoDao() {
		return userinfodao;
	}

	@Resource(name = "userinfoDao")
	public void setUserInfoDao(UserInfoDao userinfoDao) {
		this.userinfodao = userinfoDao;
	}
	
	@Override
	public UserInfo load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.userinfodao.load(id);
	}
	
	public int add(UserInfo userinfo) throws Exception{
		return this.userinfodao.add(userinfo);
	}
	
	public void modify(UserInfo userinfo) throws Exception{
		this.userinfodao.modify(userinfo);
	}
	
	public void delete(int id) throws Exception{
		this.userinfodao.delete(id);
	}
	
	@Override
	public int getid(String usercode,String password) throws Exception {
		return this.userinfodao.get("select id from USERINFO where usercode='"+usercode+"' and password='"+password+"'");
	}

	private String where(UserInfo userinfo) {
		String where = " where ";
		if (null!=userinfo.getUsercode()&&!"".equals(userinfo.getUsercode())) {
			where += " usercode like :usercode  or ";
		}
		if (null!=userinfo.getUsername()&&!"".equals(userinfo.getUsername())) {
			where += " username like :username  or ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by id desc";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from UserInfo  "+where((UserInfo)value)+orderBy;
		List list= userinfodao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(UserInfo userinfo) {
		String hql="select count(*) from UserInfo "+where(userinfo);
		return userinfodao.findRows(userinfo, hql);
	}
	
	@Override
	public int findUsecode(String usercode) {
		String sql="select * from UserInfo where usercode='"+usercode+"'";
		return userinfodao.sqlfindRows(sql);
	}
	
	@Override
	public UserInfo getUseinfo(String usercode) {
		String sql = "select id from UserInfo where usercode='" + usercode + "'";
		@SuppressWarnings("unchecked")
		List list;
		try {
			list = userinfodao.listsql(sql);
			int id = Integer.valueOf(list.get(0).toString());
			UserInfo ur = userinfodao.load(id);
			return ur;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
