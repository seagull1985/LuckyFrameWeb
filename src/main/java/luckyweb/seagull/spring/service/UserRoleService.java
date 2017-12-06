package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public interface UserRoleService {
	/**
	 * 获取用户角色对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserRole load(int id)throws Exception;
	/**
	 * 获取用户角色列表
	 * @return
	 * @throws Exception
	 */
	public List<UserRole> listall() throws Exception;
	/**
	 * 获取用户角色列表
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getalldata() throws Exception;
	/**
	 * 增加用户角色
	 * @param userrole
	 * @return
	 * @throws Exception
	 */
	public int add(UserRole userrole) throws Exception;
	/**
	 * 修改用户角色
	 * @param userrole
	 * @throws Exception
	 */
	public void modify(UserRole userrole) throws Exception;
	/**
	 * 删除用户角色
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
}
