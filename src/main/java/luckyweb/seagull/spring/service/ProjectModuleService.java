package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectModule;

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
public interface ProjectModuleService {
	/**
	 * 增加项目模块
	 * @param projectmodule
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectModule projectmodule)throws Exception;
	/**
	 * 删除项目模块
	 * @param projectmodule
	 * @throws Exception
	 */
	public void delete(ProjectModule projectmodule)throws Exception;
	/**
	 * 修改项目模块
	 * @param projectmodule
	 * @throws Exception
	 */
	public void modify(ProjectModule projectmodule)throws Exception;
	/**
	 * 获取项目模块实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectModule load(int id)throws Exception;
	/**
	 * 项目模块分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 项目模块分页条数
	 * @param projectmodule
	 * @return
	 */
	public int findRows(ProjectModule projectmodule) ;
	/**
	 * 获取项目模块列表
	 * @return
	 */
	public List<ProjectModule> getModuleList();
	/**
	 * 按项目获取项目模块列表
	 * @param projectid
	 * @param id
	 * @return
	 */
	public List<ProjectModule> getModuleListByProjectid(int projectid,int id);
	/**
	 * 判断项目模块
	 * @param id
	 * @return
	 */
	public boolean getModuleIsParent(int id);
	/**
	 * 按项目获取项目模块列表
	 * @param projectid
	 * @return
	 */
	public List<ProjectModule> getModuleAllListByProjectid(int projectid);
	/**
	 * 按名称获取项目模块
	 * @param modulename
	 * @return
	 * @throws Exception
	 */
	public int getModuleIdByName(String modulename) throws Exception;
}
