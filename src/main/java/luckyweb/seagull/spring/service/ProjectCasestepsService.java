package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectCasesteps;

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
public interface ProjectCasestepsService {
	/**
	 * 增加用例步骤
	 * @param casesteps
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectCasesteps casesteps)throws Exception;
	/**
	 * 删除用例步骤
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception;
	/**
	 * 删除用例步骤
	 * @param id
	 * @throws Exception
	 */
	public void delforcaseid(int id) throws Exception;
	/**
	 * 修改用例步骤
	 * @param casesteps
	 * @throws Exception
	 */
	public void modify(ProjectCasesteps casesteps)throws Exception;
	/**
	 * 获取用例步骤实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectCasesteps load(int id)throws Exception;
	/**
	 * 用例步骤列表
	 * @param caseid
	 * @return
	 * @throws Exception
	 */
	public List<ProjectCasesteps> getSteps(int caseid)throws Exception;
}
