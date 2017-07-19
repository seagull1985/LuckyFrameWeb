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

import luckyweb.seagull.spring.entity.ProjectModule;

@Repository("projectModuleDao")
public class ProjectModuleDaoImpl extends HibernateDaoSupport implements ProjectModuleDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		try{
			ProjectModule projectmodule = this.load(id);
		    this.getHibernateTemplate().delete(projectmodule);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void whereParameter(ProjectModule projectmodule, Query query) {
		if (projectmodule.getProjectid()!=0&&projectmodule.getProjectid()!=99) {
			query.setParameter("projectid", projectmodule.getProjectid());
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
				whereParameter((ProjectModule)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ProjectModule projectmodule, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(projectmodule, query);
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
	public int add(ProjectModule projectmodule) throws Exception {
		this.getHibernateTemplate().save(projectmodule);
		return projectmodule.getId();
	}



	@Override
	public void modify(ProjectModule projectmodule) throws Exception {
		this.getHibernateTemplate().update(projectmodule);
		
	}



	@Override
	public void modifyState(ProjectModule projectmodule) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(ProjectModule projectmodule) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<ProjectModule> list(ProjectModule projectmodule)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectModule> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public ProjectModule load(int id) throws Exception {		
		return (ProjectModule) this.getHibernateTemplate().get(ProjectModule.class, id);
	}



	@Override
	public List<ProjectModule> load(String name, String cmdType,
			String planPath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ProjectModule get(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<ProjectModule> findJobsList() {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public List<ProjectModule> getList(String hql) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectModule> list=null;
		Session session=this.getSession(true);
		try {
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getCaseMaxIndex(String sql) throws Exception {
		// TODO Auto-generated method stub
		List list=null;
		Session session=this.getSession(true);
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return list.get(0).toString();
	}

}
