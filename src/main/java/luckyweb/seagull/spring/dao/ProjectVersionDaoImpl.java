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

import luckyweb.seagull.comm.PublicConst;
import luckyweb.seagull.spring.entity.ProjectVersion;
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

		if (projectversion.getProjectid()!=0&&projectversion.getProjectid()!=PublicConst.STATUS99) {
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
			@Override
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
			@Override
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
