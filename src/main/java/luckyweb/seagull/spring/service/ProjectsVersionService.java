package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.ProjectVersion;

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
public interface ProjectsVersionService {
	/**
	 * 增加项目版本信息
	 * @param projectversion
	 * @return
	 * @throws Exception
	 */
	public int add(ProjectVersion projectversion)throws Exception;
	/**
	 * 修改项目版本信息
	 * @param projectversion
	 * @throws Exception
	 */
	public void modify(ProjectVersion projectversion)throws Exception;

	/**
	 * 删除项目版本信息
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception;
	/**
	 * 获取项目版本信息列表
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public List<ProjectVersion> list(int projectid)throws Exception;
	/**
	 * 获取项目版本信息列表
	 * @param projectid
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws Exception
	 */
	public List<ProjectVersion> list(int projectid,String startdate,String enddate)throws Exception;
	/**
	 * 获取项目版本信息列表
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws Exception
	 */
	public List listavgpro(String startdate,String enddate)throws Exception;

	/**
	 * 获取项目版本信息实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProjectVersion load(int id)throws Exception;

	/**
	 * 项目版本信息分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 项目版本信息报表分页
	 * @param offset
	 * @param pageSize
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws Exception
	 */
	public List findByPagereport(int offset, int pageSize,String startdate,String enddate)throws Exception;
	
	/**
	 * 项目版本信息分页条数
	 * @param projectversion
	 * @return
	 */
	public int findRows(ProjectVersion projectversion) ;
	
	/**
	 * 项目版本信息报表条数
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public int findRowsreport(String startdate,String enddate) ;

	/**
	 * 获取项目版本信息列表
	 * @return
	 */
	public List<ProjectVersion> findProjectsList();
}
