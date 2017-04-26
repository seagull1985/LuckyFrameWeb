package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.ProjectVersion;
import luckyweb.seagull.spring.entity.SectorProjects;
import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.util.StrLib;

@Repository("sectorprojectsDao")
public class SectorProjectsDaoImpl extends HibernateDaoSupport implements SectorProjectsDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
	    this.getHibernateTemplate().delete(sectorprojects);
	}

	private void whereParameter(SectorProjects sectorprojects, Query query) {

		if (!StrLib.isEmpty(sectorprojects.getProjectmanager())) {
			query.setParameter("departmenthead", sectorprojects.getProjectmanager().trim());
		}
		if (!StrLib.isEmpty(sectorprojects.getProjectname())) {
			query.setParameter("departmentname",sectorprojects.getProjectname().trim());
		}

	}
	
	@Override
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((SectorProjects)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(SectorProjects sectorprojects, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(sectorprojects, query);
			s= Integer.valueOf(
					query
					.list().get(0)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return s;
	}



	@Override
	public int add(SectorProjects sectorprojects) throws Exception {
		this.getHibernateTemplate().save(sectorprojects);
		return sectorprojects.getProjectid();
	}



	@Override
	public void modify(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(sectorprojects);
	}



	@Override
	public void modifyState(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(SectorProjects sectorprojects) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<SectorProjects> list(SectorProjects sectorprojects)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<SectorProjects> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object findproject(int projectid) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.getSession(true);
		List list = session.createSQLQuery("select * from QA_SECTORPROJECTS where projectid="+projectid).list();
		session.close();
		return list.get(0);
	}
	
	@Override
	public int getid(String projectname) throws Exception {
		// TODO Auto-generated method stub
		int projectid = 0;
		Session session = this.getSession(true);
		@SuppressWarnings("unchecked")
		List<Object> list = session.createSQLQuery("select projectid from QA_SECTORPROJECTS where projectname like '%"+projectname+"%'").list();
		if(list.size()!=0&&list.get(0)!=null){
			projectid = Integer.valueOf(list.get(0).toString());
		}else{
			projectid = 99;
		}
		session.close();
		return projectid;
	}



	@Override
	public List<SectorProjects> load(String name, String cmdType,
			String planPath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SectorProjects load(int projectid) throws Exception {
		// TODO Auto-generated method stub
		return (SectorProjects) this.getHibernateTemplate().get(SectorProjects.class, projectid);
	}
	
	@Override
	public int projectrow(String hql) {
//		List<TestTastexcute> list=new ArrayList<TestTastexcute>();
		Session session = this.getSession(true);
		int count=0;
		try {
			count = session.createSQLQuery(hql).list().size();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return count;
		// return this.getHibernateTemplate().find("select id,name,planproj from TestJobs  order by id asc ");
	}
}
