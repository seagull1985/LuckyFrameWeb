package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectPlan;

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
public interface ProjectPlanService {
	/**
	 * 增加测试计划
	 * @param projectplan
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectPlan projectplan)throws Exception;
	/**
	 * 删除测试计划
	 * @param projectplan
	 * @throws Exception
	 */
	public void delete(ProjectPlan projectplan)throws Exception;
	/**
	 * 修改测试计划
	 * @param projectplan
	 * @throws Exception
	 */
	public void modify(ProjectPlan projectplan)throws Exception;
	/**
	 * 获取测试计划实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectPlan load(int id)throws Exception;
	/**
	 * 获取测试计划分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 测试计划分页条数
	 * @param projectplan
	 * @return
	 */
	public int findRows(ProjectPlan projectplan) ;
	/**
	 * 获取测试计划实体
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public ProjectPlan getcases(String name) throws Exception;
}
