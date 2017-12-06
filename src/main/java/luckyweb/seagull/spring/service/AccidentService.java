package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.Accident;

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
public interface AccidentService {
	/**
	 * 增加生产故障
	 * @param accident
	 * @return
	 * @throws Exception
	 */
	public int add(Accident accident)throws Exception;
	/**
	 * 删除生产故障
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception;
	/**
	 * 修改生产故障
	 * @param accident
	 * @throws Exception
	 */
	public void modify(Accident accident)throws Exception;
	/**
	 * 获取生产故障实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Accident load(int id)throws Exception;
	/**
	 * 生产故障分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 获取生产故障列表
	 * @param startdate
	 * @param enddate
	 * @param projectid
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List listcausaltype(String startdate,String enddate,int projectid,String type) throws Exception;
	/**
	 * 生产故障分页条数
	 * @param accident
	 * @return
	 */
	public int findRows(Accident accident) ;
}
