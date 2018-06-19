package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.TestTastExcuteDao;
import luckyweb.seagull.spring.entity.TestTaskexcute;
import luckyweb.seagull.util.DateLib;
import luckyweb.seagull.util.StrLib;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Service("tastExcuteService")
public class TestTastExcuteServiceImpl implements TestTastExcuteService {

	@Resource(name="tastExcuteDao")
	private TestTastExcuteDao tastExcuteDao;
	public TestTastExcuteDao getTestTastExcuteDao() {
		return tastExcuteDao;
	}

	public void setTestTastExcuteDao(TestTastExcuteDao tastExcuteDao) {
		this.tastExcuteDao = tastExcuteDao;
	}

	@Override
	public void add(TestTaskexcute task) {
		this.tastExcuteDao.add(task);
	}

	@Override
	public List<TestTaskexcute> list(TestTaskexcute tjob) {
		return this.tastExcuteDao.list(tjob);
	}

	@Override
	public TestTaskexcute load(int id) {
		return this.tastExcuteDao.load(id);
	}

	@Override
	public TestTaskexcute get(int id) {
		return this.tastExcuteDao.get(id);
	}
	


	

	private String where(TestTaskexcute tast) {
		String where = " where ";
		if (!StrLib.isEmpty(tast.getStartDate())) {
			where += " date_format(createTime,'%Y-%m-%d')>=?  and  date_format(createTime,'%Y-%m-%d')<=?  and ";
		}
		if (tast.getJobid()!=0) {
			where += " jobId=:jobId  and ";
		}
		if (!StrLib.isEmpty(tast.getTaskId())) {
			where += " taskId like :taskId  and ";
		}
		if (!StrLib.isEmpty(tast.getTaskStatus())) {
			where += " taskStatus =:taskStatus and ";
		}
		if (where.length() == PublicConst.WHERENUM) {
			where = "";
		} else {
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	private static String  orderBy=" order by id desc ";
	

	@SuppressWarnings("unchecked")
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
	String 	hql=" from TestTaskexcute "+where((TestTaskexcute)value)+orderBy;
	List list= tastExcuteDao.findByPage(hql, value, offset, pageSize);
	for(TestTaskexcute tast:(List<TestTaskexcute>)list){
		tast.setTaskStatus_str(setStatus(tast.getTaskStatus()));
	}
	return list;
	}

	/**
	 * 状态
	 * @param status
	 * @return
	 */
	private String setStatus(String status){
		if(StrLib.isEmpty(status)){
			return "其他";
		}else{
			String unex="0";
			String exing="1";
			String exsuc="2";
			String exfail="3";
			if(unex.equals(status)){
				return "未执行";
			}else if(exing.equals(status)){
				return "执行中";
			}else if(exsuc.equals(status)){
				return "执行成功";
			}else if(exfail.equals(status)){
				return "调起失败|超时";
			}
		}
		return "其他";
	}

	@Override
	public int findRows(TestTaskexcute tast ) {
		String hql="select count(*) from TestTaskexcute"+where(tast);
		return tastExcuteDao.findRows(hql,tast);
	}

	@Override
	public List findTastList(String param,String param2, String param3) {
		String sql="select id,taskid from TEST_TASKEXCUTE ";
		if(!StrLib.isEmpty(param)){
			if(!StrLib.isEmpty(param3)){
				sql+=" where  date_format(createTime,'%Y-%m-%d')>='"+param+"' and  date_format(createTime,'%Y-%m-%d')<='"+param3+"'";
			}else{
				sql+=" where  date_format(createTime,'%Y-%m-%d')='"+param+"'";
			}
		}
		if(!StrLib.isEmpty(param2)){
			sql+=" and  jobid in (select id from TEST_JOBS t where t.projectid = '"+param2+"')";
		}
		sql+=" order by id desc";
		return tastExcuteDao.findTastList(sql);
	}

	@Override
	public void delete(int id) throws Exception {
		  this.tastExcuteDao.delete(id);
	}
	
	@Override
	public void deleteForjobid(int jobid) throws Exception {
		String hql="delete from TestTaskexcute where jobId=:jobId";
		this.tastExcuteDao.deleteForJobid(hql, jobid);
	}
	
	@Override
	public List getidlist(int jobid) throws Exception{
		return this.tastExcuteDao.listtastinfo("select id from TEST_TASKEXCUTE where jobid="+jobid);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List listtastinfo() throws Exception{
		return this.tastExcuteDao.listtastinfo("select t.id,b.name,t.casetotal_count,t.casesucc_count,t.casefail_count,t.caselock_count,t.casenoexec_count,date_format(t.createtime,'%Y-%m-%d %T') "
				+ "from TEST_TASKEXCUTE t left join test_jobs b on t.jobid = b.id where t.id in "
				+ "(select max(t.id) from TEST_TASKEXCUTE t  where t.taskstatus != '0' and t.taskstatus != '3' and t.taskstatus != '1' group by t.jobid) order by t.createtime desc");
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List listindexreport(int days) throws Exception{
		String date=DateLib.beforNdFormat("yyyy-MM-dd", days);
		return this.tastExcuteDao.listtastinfo("SELECT SUM(t.casetotal_count),SUM(t.casesucc_count),SUM(t.casefail_count),"
				+ "SUM(t.caselock_count),SUM(t.casenoexec_count),LEFT(t.createtime,10) as createdate "
				+ "from test_taskexcute t where t.createtime>'"+date+"' GROUP BY createdate");
	}
	
	@Override
	public List getTopTaskDate() throws Exception{
		return this.tastExcuteDao.listtastinfo("select left(min(createtime),10) from test_taskexcute");
	}
}
