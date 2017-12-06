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

import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.TestLogdetail;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("projectCasestepsDao")
public class ProjectCasestepsDaoImpl extends HibernateDaoSupport implements ProjectCasestepsDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		try{
			ProjectCasesteps casesteps = this.load(id);
		    this.getHibernateTemplate().delete(casesteps);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(String hql, int id) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.setInteger("caseId", id);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}
	
	private void whereParameter(ProjectCasesteps casesteps, Query query) {
		if (casesteps.getProjectid()!=0) {
			query.setParameter("projectid", casesteps.getProjectid());
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
				whereParameter((ProjectCasesteps)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ProjectCasesteps casesteps, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(casesteps, query);
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
	public int add(ProjectCasesteps casesteps) throws Exception {
		this.getHibernateTemplate().save(casesteps);
		return casesteps.getId();
	}



	@Override
	public void modify(ProjectCasesteps casesteps) throws Exception {
		this.getHibernateTemplate().update(casesteps);
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectCasesteps> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public ProjectCasesteps load(int id) throws Exception {		
		return (ProjectCasesteps) this.getHibernateTemplate().get(ProjectCasesteps.class, id);
		//return (ProjectCasesteps) this.getSession().load(ProjectCasesteps.class, id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProjectCasesteps> steps(String sql) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectCasesteps> list=null;
		Session session=this.getSession(true);
		try {
			list = session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return list;
	}

}
