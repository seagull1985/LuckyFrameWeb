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
import luckyweb.seagull.spring.entity.ProjectPlanCase;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("projectPlanCaseDao")
public class ProjectPlanCaseDaoImpl extends HibernateDaoSupport implements ProjectPlanCaseDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(ProjectPlanCase projectplancase) throws Exception {
		try{
		    this.getHibernateTemplate().delete(projectplancase);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(String hql, int id) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.setInteger("planId", id);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}
	
	private void whereParameter(ProjectPlanCase projectplancase, Query query) {
		if (projectplancase.getCaseid()!=0) {
			query.setParameter("caseid", projectplancase.getCaseid());
		}
		if (projectplancase.getPlanid()!=0) {
			query.setParameter("planid", projectplancase.getPlanid());
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
				whereParameter((ProjectPlanCase)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ProjectPlanCase projectplancase, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(projectplancase, query);
			if(0==query.list().size()){
				return 0;
			}
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
	public int add(ProjectPlanCase projectplancase) throws Exception {
		this.getHibernateTemplate().save(projectplancase);
		return projectplancase.getId();
	}



	@Override
	public void modify(ProjectPlanCase projectplancase) throws Exception {
		this.getHibernateTemplate().update(projectplancase);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectPlanCase> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public ProjectPlanCase load(int id) throws Exception {
		
		return (ProjectPlanCase) this.getHibernateTemplate().get(ProjectPlanCase.class, id);
		//return (ProjectCase) this.getSession().load(ProjectCase.class, id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProjectPlanCase> getList(String sql) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectPlanCase> count=null;
		Session session=this.getSession(true);
		try {
			count = session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return count;
	}
}
