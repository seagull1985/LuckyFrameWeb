package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlanCase;

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
public interface ProjectPlanCaseService {
	/**
	 * 增加计划用例
	 * @param projectplancase
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectPlanCase projectplancase)throws Exception;
	/**
	 * 删除计划用例
	 * @param projectplancase
	 * @throws Exception
	 */
	public void delete(ProjectPlanCase projectplancase)throws Exception;
	/**
	 * 删除计划用例
	 * @param id
	 * @throws Exception
	 */
	public void delforplanid(int id) throws Exception;
	/**
	 * 修改计划用例
	 * @param projectplancase
	 * @throws Exception
	 */
	public void modify(ProjectPlanCase projectplancase)throws Exception;
	/**
	 * 获取计划用例实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectPlanCase load(int id)throws Exception;
	/**
	 * 计划用例分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 计划用例分页条数
	 * @param projectplancase
	 * @return
	 */
	public int findRows(ProjectPlanCase projectplancase) ;
	/**
	 * 获取计划用例列表
	 * @param planid
	 * @return
	 * @throws Exception
	 */
	public List<ProjectPlanCase> getcases(int planid) throws Exception;
	/**
	 * 获取计划用例实体
	 * @param planid
	 * @param caseid
	 * @return
	 * @throws Exception
	 */
	public ProjectPlanCase getplancase(int planid,int caseid) throws Exception;
}
