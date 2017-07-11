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

import luckyweb.seagull.spring.entity.ProjectPlan;


@Repository("projectPlanDao")
public class ProjectPlanDaoImpl extends HibernateDaoSupport implements ProjectPlanDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(ProjectPlan projectplan) throws Exception {
		try{
		    this.getHibernateTemplate().delete(projectplan);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void whereParameter(ProjectPlan projectplan, Query query) {
		if (projectplan.getProjectid()!=0&&projectplan.getProjectid()!=99) {
			query.setParameter("projectid", projectplan.getProjectid());
		}
		if (null!=projectplan.getName()&&!"".equals(projectplan.getName())) {
			query.setParameter("name", "%"+projectplan.getName()+"%");
		}
		if (null!=projectplan.getOperationer()&&!"".equals(projectplan.getOperationer())) {
			query.setParameter("operationer", "%"+projectplan.getOperationer()+"%");
		}
		if (null!=projectplan.getRemark()&&!"".equals(projectplan.getRemark())) {
			query.setParameter("remark", "%"+projectplan.getRemark()+"%");
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
				whereParameter((ProjectPlan)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ProjectPlan projectplan, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(projectplan, query);
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
	public int add(ProjectPlan projectplan) throws Exception {
		this.getHibernateTemplate().save(projectplan);
		return projectplan.getId();
	}



	@Override
	public void modify(ProjectPlan projectplan) throws Exception {
		this.getHibernateTemplate().update(projectplan);
		
	}



	@Override
	public void modifyState(ProjectPlan projectplan) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(ProjectPlan projectplan) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<ProjectPlan> list(ProjectPlan projectplan)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectPlan> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public ProjectPlan load(int id) throws Exception {
		
		return (ProjectPlan) this.getHibernateTemplate().get(ProjectPlan.class, id);
		//return (ProjectCase) this.getSession().load(ProjectCase.class, id);
	}



	@Override
	public List<ProjectPlan> load(String name, String cmdType,
			String planPath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ProjectPlan get(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<ProjectPlan> findJobsList() {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public List getList(String sql) throws Exception {
		// TODO Auto-generated method stub
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
