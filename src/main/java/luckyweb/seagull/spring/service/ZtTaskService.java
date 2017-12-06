package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ZtTask;

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
public interface ZtTaskService {
	/**
	 * 获取禅道对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ZtTask load(int id)throws Exception;
	/**
	 * 增加禅道记录
	 * @param zt
	 * @return
	 * @throws Exception
	 */
	public int add(ZtTask zt) throws Exception;
	/**
	 * 删除禅道记录
	 * @param versionid
	 * @throws Exception
	 */
	public void delete(int versionid) throws Exception;
	/**
	 * 禅道记录分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(Object value, int offset, int pageSize);
	/**
	 * 禅道记录分页条数
	 * @param zt
	 * @return
	 */
	public int findRows(ZtTask zt);
	/**
	 * 禅道记录报表分页
	 * @param offset
	 * @param pageSize
	 * @param startdate
	 * @param enddate
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List findByPagereport(int offset, int pageSize,String startdate,String enddate,int type) throws Exception;
	/**
	 * 禅道记录报表分页条数
	 * @param startdate
	 * @param enddate
	 * @param type
	 * @return
	 */
	public int findRowsreport(String startdate,String enddate,int type);
}
