package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.dao.SecondarySectorDao;
import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.TestJobs;
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
@Service("secondarysectorService")
public class SecondarySectorServiceImpl implements SecondarySectorService{
	
	private SecondarySectorDao secondarysectordao;
	
	public SecondarySectorDao getTestJobsDao() {
		return secondarysectordao;
	}

	@Resource(name = "secondarysectorDao")
	public void setSecondarySectorDao(SecondarySectorDao secondarysectordao) {
		this.secondarysectordao = secondarysectordao;
	}

	@Override
	public int add(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		return this.secondarysectordao.add(sector);
	}

	@Override
	public void modify(SecondarySector sector) throws Exception {
		this.secondarysectordao.modify(sector);
		
	}

	@Override
	public void delete(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		this.secondarysectordao.delete(sector);
	}

	@Override
	public List<SecondarySector> list(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		return this.secondarysectordao.list(sector);
	}

	@Override
	public List<SecondarySector> listall() throws Exception {
		// TODO Auto-generated method stub
		return this.secondarysectordao.list();
	}

	@Override
	public SecondarySector load(int id) throws Exception {
		// TODO Auto-generated method stub
		return this.secondarysectordao.load(id);
	}

	private String where(SecondarySector ss) {
		String where = " where ";
/*		if (!StrLib.isEmpty(String.valueOf(ss.getSectorid()))) {
			where += " sectorid=:sectorid  and ";
		}*/
		if (!StrLib.isEmpty(ss.getDepartmenthead())) {
			where += " departmenthead like :departmenthead  or ";
		}
		else if (!StrLib.isEmpty(ss.getDepartmentname())) {
			where += " departmentname like :departmentname  or ";
		}
		
		
		if (where.length() == PublicConst.WHERENUM) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by sectorid asc ";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from SecondarySector  "+where((SecondarySector)value)+orderBy;
		List list= secondarysectordao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(SecondarySector sector) {
		String hql="select count(*) from SecondarySector "+where(sector);
		return secondarysectordao.findRows(sector,hql);
	}

	@Override
	public List<SecondarySector> findSecotorList()  throws Exception {
		// TODO Auto-generated method stub
		return secondarysectordao.findSectorList();
	}

}
