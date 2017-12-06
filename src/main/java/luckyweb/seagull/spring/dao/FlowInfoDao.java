package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowInfo;

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
public interface FlowInfoDao {
	/**
	 * 流程检查详细信息列表
	 * @param flowinfo
	 * @return
	 * @throws Exception
	 */
	public List<FlowInfo> list(FlowInfo flowinfo) throws Exception;
	/**
	 * 流程检查详细信息列表
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<FlowInfo> list(String hql) throws Exception;
	/**
	 * 流程检查详细信息
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List listcheckinfo(String sql) throws Exception;
	/**
	 * 流程检查详细信息获取实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FlowInfo load(int id) throws Exception;
	/**
	 * 流程检查详细信息获取实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FlowInfo get(int id) throws Exception;
}
