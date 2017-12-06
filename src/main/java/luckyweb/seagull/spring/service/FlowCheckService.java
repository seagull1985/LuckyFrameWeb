package luckyweb.seagull.spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import luckyweb.seagull.spring.entity.FlowCheck;

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
public interface FlowCheckService {
	/**
	 * 增加流程检查
	 * @param flowcheck
	 * @return
	 * @throws Exception
	 */
	public int add(FlowCheck flowcheck)throws Exception;
	/**
	 * 修改流程检查
	 * @param flowcheck
	 * @throws Exception
	 */
	public void modify(FlowCheck flowcheck)throws Exception;

	/**
	 * 删除流程检查
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id)throws Exception;
	/**
	 * 获取流程检查信息列表
	 * @param projectid
	 * @param checkid
	 * @return
	 * @throws Exception
	 */
	public List listcheckinfo(int projectid,int checkid)throws Exception;

	/**
	 * 流程检查列表
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws Exception
	 */
	public List listdateper(String startdate,String enddate) throws Exception;

	/**
	 * 获取流程检查ID
	 * @param projectid
	 * @param checkid
	 * @param entry
	 * @return
	 * @throws Exception
	 */
	public int getid(int projectid,int checkid,String entry) throws Exception;
	/**
	 * 获取流程检查版本号
	 * @param projectid
	 * @param checkid
	 * @return
	 * @throws Exception
	 */
	public String getversionnum(int projectid,int checkid) throws Exception;
	/**
	 * 获取流程检查实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public FlowCheck load(int id)throws Exception;
	/**
	 * 获取流程检查ID
	 * @param projectid
	 * @return
	 * @throws Exception
	 */
	public int getcheckid(int projectid)throws Exception;
	/**
	 * 判断流程检查记录
	 * @param projectid
	 * @param checkid
	 * @param checkentry
	 * @return
	 * @throws Exception
	 */
	public boolean determinerecord(int projectid,int checkid,String checkentry) throws Exception;

	/**
	 * 流程检查分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPage( final Object value,final int offset, final int pageSize);
	/**
	 * 流程检查表格分页
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List findByPageTable( final Object value,final int offset, final int pageSize);
	/**
	 * 流程检查报表
	 * @param value
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List reportList( final Object value,final int offset, final int pageSize);
	/**
	 * 更新版本号
	 * @param proid
	 * @param verold
	 * @param vernew
	 * @throws Exception
	 */
	public void updateversion(int proid,String verold,String vernew) throws Exception;
	/**
	 * 流程检查报表分页
	 * @param flowcheck
	 * @return
	 */
	public int findRowsReport(FlowCheck flowcheck) ;
	/**
	 * 流程检查分页条数
	 * @param flowcheck
	 * @return
	 */
	public int findRows(FlowCheck flowcheck) ;
	/**
	 * 流程检查报表分页条数
	 * @param flowcheck
	 * @return
	 */
	public int findRowsTable(FlowCheck flowcheck) ;
}
