package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.TestCasedetailDao;
import luckyweb.seagull.spring.entity.TestCasedetail;
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
@Service("casedetailService")
public class CaseDetailServiceImpl implements CaseDetailService {

	@Resource(name="casedetailDao")
	private TestCasedetailDao casedetailDao;
	

	@Override
	public void add(TestCasedetail task) {
		this.casedetailDao.add(task);
	}

	@Override
	public List<TestCasedetail> list(TestCasedetail tjob) {
		return this.casedetailDao.list(tjob);
	}

	@Override
	public TestCasedetail load(int id) {
		return this.casedetailDao.load(id);
	}

	public TestCasedetailDao getCasedetailDao() {
		return casedetailDao;
	}

	public void setCasedetailDao(TestCasedetailDao casedetailDao) {
		this.casedetailDao = casedetailDao;
	}

	private static String  orderBy=" order by id asc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
	String	hql=" from TestCasedetail  "+where((TestCasedetail)value)+orderBy;
		@SuppressWarnings("rawtypes")
		List list= casedetailDao.findByPage(hql, value, offset, pageSize);
		for(TestCasedetail caseDetail :(List<TestCasedetail>)list){
			caseDetail.setCasestatus_str(setStatus(caseDetail.getCasestatus()));
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
			String suc="0";
			String fail="1";
			String lock="2";
			String unexe="4";
			if(suc.equals(status)){
				return "成功";
			}else if(fail.equals(status)){
				return "失败";
			}else if(lock.equals(status)){
				return "锁定";
			}else if(unexe.equals(status)){
				return "未执行";
			}
		}
		return "其他";
	}


	private String where(TestCasedetail caseDetail) {
		String where = " where ";
		if (!StrLib.isEmpty(caseDetail.getStartDate())&&!StrLib.isEmpty(caseDetail.getEndDate())) {
			//	where += " to_char(caseTime,'YYYY-MM-dd')=? and ";
				where += " date_format(caseTime,'%Y-%m-%d')>=:startDate  and  date_format(caseTime,'%Y-%m-%d')<=:endDate  and ";
		}
		if (caseDetail.getTaskId()!=0) {
			where += " taskId=:taskId  and ";
		} 
		if (!StrLib.isEmpty(caseDetail.getCasestatus())) {
			where += " casestatus=:casestatus  and ";
		}
		if (!StrLib.isEmpty(caseDetail.getCaseno())) {
			where += " (caseno like :caseno  or ";
		}
		if (!StrLib.isEmpty(caseDetail.getCasename())) {
			where += " casename like :casename)  or ";
		}

		if (where.length() == PublicConst.WHERENUM) {
			where = "";
		} else {
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	@Override
	public int findRows(TestCasedetail caseDetail) {
		String hql="select count(*) from TestCasedetail "+where(caseDetail);
		return casedetailDao.findRows(hql,caseDetail);
	}
	
	@Override
	public void delete(int id) throws Exception {
		String hql="delete from TestCasedetail where taskId=:taskId";
		this.casedetailDao.delete(hql, id);
	}



}
