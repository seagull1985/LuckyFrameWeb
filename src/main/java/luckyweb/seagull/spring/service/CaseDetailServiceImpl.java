package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.TestCasedetailDao;
import luckyweb.seagull.spring.entity.TestCasedetail;
import luckyweb.seagull.util.StrLib;

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
			if(status.equals("0")){
				return "成功";
			}else if(status.equals("1")){
				return "失败";
			}else if(status.equals("2")){
				return "锁定";
			}else if(status.equals("4")){
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

		if (where.length() == 7) {
			where = "";
		} else {
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	public int findRows(TestCasedetail caseDetail ) {
		String hql="select count(*) from TestCasedetail "+where(caseDetail);
		return casedetailDao.findRows(hql,caseDetail);
	}

	@Override
	public void delete(int id) throws Exception {
		String hql="delete from TestCasedetail where taskId=:taskId";
		this.casedetailDao.delete(hql, id);
	}



}
