package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.UserInfo;

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
public interface UserInfoService {
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserInfo load(int id)throws Exception;
	/**
	 * 增加用户信息
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public int add(UserInfo userinfo) throws Exception;
	/**
	 * 修改用户信息
	 * @param userinfo
	 * @throws Exception
	 */
	public void modify(UserInfo userinfo) throws Exception;
	/**
	 * 删除用户信息
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	/**
	 * 获取用户信息
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int getid(String name,String password)throws Exception;
	/**
	 * 用户信息分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);

	/**
	 * 用户信息分页条数
	 * @param userinfo
	 * @return
	 */
	public int findRows(UserInfo userinfo) ;
	/**
	 * 获取用户信息条数
	 * @param usercode
	 * @return
	 */
	public int findUsecode(String usercode);
	/**
	 * 获取用户信息对象
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUseinfo(String usercode) throws Exception;
}
