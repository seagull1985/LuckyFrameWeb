package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.SecondarySectorDao;
import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.spring.entity.TestJobs;
import luckyweb.seagull.util.StrLib;

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
	public int add(SecondarySector Sector) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void modify(SecondarySector Sector) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyState(SecondarySector Sector) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws Exception {
		// TODO Auto-generated method stub
		
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
	public boolean isExist(String name, String cmdType, String planPath)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
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
			where += " departmenthead=:departmenthead  and ";
		}
		else if (!StrLib.isEmpty(ss.getDepartmentname())) {
			where += " departmentname=:departmentname  and ";
		}
		
		
		if (where.length() == 7) {
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
	public int findRows(SecondarySector Sector) {
		String hql="select count(*) from SecondarySector "+where(Sector);
		return secondarysectordao.findRows(Sector,hql);
	}

	@Override
	public List<SecondarySector> findSecotorList()  throws Exception {
		// TODO Auto-generated method stub
		return secondarysectordao.findSectorList();
	}

}
