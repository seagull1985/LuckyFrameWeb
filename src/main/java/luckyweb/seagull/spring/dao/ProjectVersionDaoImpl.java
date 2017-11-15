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
import luckyweb.seagull.util.StrLib;

@Repository("projectversionDao")
public class ProjectVersionDaoImpl extends HibernateDaoSupport implements ProjectVersionDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int versionid) throws Exception {
		try{
		ProjectVersion projectversion = this.load(versionid);
		this.getHibernateTemplate().delete(projectversion);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void whereParameter(ProjectVersion projectversion, Query query) {

		if (projectversion.getProjectid()!=0&&projectversion.getProjectid()!=99) {
			query.setParameter("projectid", projectversion.getProjectid());
		}
		if (!StrLib.isEmpty(projectversion.getStartactually_launchdate())) {
			query.setParameter("startactually_launchdate", projectversion.getStartactually_launchdate());
		}
		if (!StrLib.isEmpty(projectversion.getEndactually_launchdate())) {
			query.setParameter("endactually_launchdate", projectversion.getEndactually_launchdate());
		}
		if (!StrLib.isEmpty(projectversion.getVersionnumber())) {
			query.setParameter("versionnumber", "%"+projectversion.getVersionnumber().trim()+"%");
		}
		if (!StrLib.isEmpty(projectversion.getImprint())) {
			query.setParameter("imprint", "%"+projectversion.getImprint().trim()+"%");
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
				whereParameter((ProjectVersion)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}
	
	@Override
	public List findByPagereport(final String hql,final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				//Query query= session.createQuery(hql);
				Query query= session.createSQLQuery(hql);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ProjectVersion projectversion, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(projectversion, query);
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
	public int findRowsreport(String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			/*Query query=session.createQuery(hql);*/
			Query query= session.createSQLQuery(hql);
			s= Integer.valueOf(
					query.list().size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return s;
	}


	@Override
	public int add(ProjectVersion projectversion) throws Exception {
		this.getHibernateTemplate().save(projectversion);
		return projectversion.getVersionid();
	}



	@Override
	public void modify(ProjectVersion projectversion) throws Exception {
		this.getHibernateTemplate().update(projectversion);
		
	}



	@Override
	public void modifyState(ProjectVersion projectversion) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(ProjectVersion projectversion) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<ProjectVersion> list(ProjectVersion projectversion)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectVersion> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public ProjectVersion load(int versionid) throws Exception {
		return (ProjectVersion) this.getHibernateTemplate().get(ProjectVersion.class, versionid);
		//return (ProjectVersion) this.getSession().load(ProjectVersion.class, versionid);
	}



	@Override
	public List<ProjectVersion> load(String name, String cmdType,
			String planPath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ProjectVersion get(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<ProjectVersion> findJobsList() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List listavgpro(String sql) throws Exception {
		// TODO Auto-generated method stub
//		List<TestTastexcute> list=new ArrayList<TestTastexcute>();
		List count=null;
		Session session=this.getSession(true);
		try {
			count = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return count;
	}

}
