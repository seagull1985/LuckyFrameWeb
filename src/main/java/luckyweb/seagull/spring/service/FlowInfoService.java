package luckyweb.seagull.spring.service;

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
public interface FlowInfoService {
	/**
	 * 获取流程阶段
	 * @return
	 * @throws Exception
	 */
	public List listphaseinfo()throws Exception;
	/**
	 * 获取流程节点
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List listnodeinfo(int id)throws Exception;
	/**
	 * 获取流程内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List listentryinfo(int id)throws Exception;
	/**
	 * 获取所有流程阶段
	 * @return
	 * @throws Exception
	 */
	public List listphaseallinfo()throws Exception;
	/**
	 * 获取所有节点
	 * @return
	 * @throws Exception
	 */
	public List listnodeallinfo()throws Exception;
	/**
	 * 获取所有流程内容
	 * @return
	 * @throws Exception
	 */
	public List listentryallinfo()throws Exception;
	/**
	 * 获取流程实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FlowInfo load(int id) throws Exception;
}
