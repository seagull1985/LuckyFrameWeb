package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
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
public interface AccidentDao {
	/**
	 * 增加故障
	 * @param accident
	 * @return
	 * @throws Exception
	 */
	public int add(Accident accident) throws Exception;
	/**
	 * 修改故障
	 * @param accident
	 * @throws Exception
	 */
	public void modify(Accident accident) throws Exception;
	/**
	 * 修改故障状态
	 * @param accident
	 * @throws Exception
	 */
	public void modifyState(Accident accident) throws Exception;
	/**
	 * 修改故障详细
	 * @param accident
	 * @throws Exception
	 */
	public void modifyInfo(Accident accident) throws Exception;
	/**
	 * 删除故障
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	/**
	 * 故障列表
	 * @param accident
	 * @return
	 * @throws Exception
	 */
	public List<Accident> list(Accident accident) throws Exception;
	/**
	 * 故障列表
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<Accident> list(String hql) throws Exception;
	/**
	 * 算平均值
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List listavgpro(String sql) throws Exception;
	/**
	 * 取故障实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Accident load(int id) throws Exception;

	/**
	 * 取故障实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Accident get(int id) throws Exception;
	/**
	 * 分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);
	/**
	 * 取分页条数
	 * @param accident
	 * @param hql
	 * @return
	 */
	public int findRows(Accident accident,String hql);

}
