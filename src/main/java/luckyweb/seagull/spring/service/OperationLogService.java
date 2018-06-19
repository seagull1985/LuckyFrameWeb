package luckyweb.seagull.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.OperationLog;

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
public interface OperationLogService {	
	/**
	 * 增加操作日志
	 * @param req
	 * @param tablename
	 * @param tableid
	 * @param id
	 * @param operationDescription
	 * @return
	 * @throws Exception
	 */
	public int add(HttpServletRequest req,String tablename,int tableid,int id,int integral,String operationDescription)throws Exception;
	/**
	 * 获取操作日志实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OperationLog load(int id)throws Exception;
	/**
	 * 获取操作日志分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 获取操作日志分页条数
	 * @param oplog
	 * @return
	 */
	public int findRows(OperationLog oplog);
	/**
	 * 删除操作日志
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
	/**
	 * 获取操作积分
	 * @return
	 * @throws Exception
	 */
	public List getSumIntegral() throws Exception;
}
