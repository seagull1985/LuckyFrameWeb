package luckyweb.seagull.spring.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.SectorProjects;

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
public interface SectorProjectsDao {
	/**
	 * 增加项目
	 * @param sectorprojects
	 * @return
	 * @throws Exception
	 */
	public int add(SectorProjects sectorprojects) throws Exception;

	/**
	 * 修改项目
	 * @param sectorprojects
	 * @throws Exception
	 */
	public void modify(SectorProjects sectorprojects) throws Exception;

	/**
	 * 删除项目
	 * @param sectorprojects
	 * @throws Exception
	 */
	public void delete(SectorProjects sectorprojects) throws Exception;

	/**
	 * 获取项目实体
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public Object findproject(int projectid) throws Exception;
	
	/**
	 * 获取项目ID
	 * @param projectname
	 * @return
	 * @throws Exception
	 */
	public int getid(String projectname) throws Exception;

	/**
	 * 项目分页
	 * @param hql
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage(final String hql, final Object value,final int offset, final int pageSize);

	/**
	 * 项目分页条数
	 * @param sectorprojects
	 * @param hql
	 * @return
	 */
	public int findRows(SectorProjects sectorprojects,String hql);
	/**
	 * 获取项目实体
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public SectorProjects load(int projectid) throws Exception;
	/**
	 * 获取项目条数
	 * @param hql
	 * @return
	 */
	public int projectrow(String hql);
	/**
	 * 获取所有项目列表
	 * @return
	 */
	public List<SectorProjects> getAllProject();
}
