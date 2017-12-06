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
import luckyweb.seagull.spring.entity.ProjectPlan;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
		if (projectplan.getProjectid()!=0&&projectplan.getProjectid()!=PublicConst.STATUS99) {
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
			@Override
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProjectPlan> getList(String sql) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectPlan> count=null;
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
