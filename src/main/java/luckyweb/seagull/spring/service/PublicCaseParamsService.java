package luckyweb.seagull.spring.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.PublicCaseParams;

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
public interface PublicCaseParamsService {
	/**
	 * 获取公共参数实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PublicCaseParams load(int id)throws Exception;
	/**
	 * 增加公共参数
	 * @param pcp
	 * @return
	 * @throws Exception
	 */
	public int add(PublicCaseParams pcp) throws Exception;
	/**
	 * 修改公共参数
	 * @param pcp
	 * @throws Exception
	 */
	public void modify(PublicCaseParams pcp) throws Exception;
	/**
	 * 删除公共参数
	 * @param pcp
	 * @throws Exception
	 */
	public void delete(PublicCaseParams pcp) throws Exception;
	/**
	 * 公共参数分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);

	/**
	 * 公共参数分页条数
	 * @param pcp
	 * @return
	 */
	public int findRows(PublicCaseParams pcp) ;
	/**
	 * 获取公共参数实体
	 * @param paramsname
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public PublicCaseParams getParamByName(String paramsname,String projectid) throws Exception;
	/**
	 * 从项目ID获取公共参数列表
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public List<PublicCaseParams> getParamListByProjectid(String projectid) throws Exception;
}
