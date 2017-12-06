package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.TempCasestepDebug;

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
public interface TempCasestepDebugService {	
	/**
	 * 增加WEB界面调试日志
	 * @param tcd
	 * @return
	 * @throws Exception
	 */
	public int add(TempCasestepDebug tcd)throws Exception;
	/**
	 * 获取WEB界面调试日志
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TempCasestepDebug load(int id)throws Exception;
	/**
	 * WEB界面调试日志分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * WEB界面调试日志分页条数
	 * @param tcd
	 * @return
	 */
	public int findRows(TempCasestepDebug tcd);
	/**
	 * 删除WEB界面调试日志
	 * @param sign
	 * @param executor
	 * @throws Exception
	 */
	public void delete(String sign,String executor) throws Exception;
	/**
	 * 删除WEB界面调试日志对象
	 * @param tcd
	 * @throws Exception
	 */
	public void deleteforob(TempCasestepDebug tcd) throws Exception;
	/**
	 * 修改WEB界面调试日志
	 * @param tcd
	 * @throws Exception
	 */
	public void modify(TempCasestepDebug tcd) throws Exception;
	/**
	 * 获取WEB界面调试日志列表
	 * @param sign
	 * @param executor
	 * @return
	 * @throws Exception
	 */
	public List<TempCasestepDebug> getList(String sign,String executor) throws Exception;
}
