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

import luckyweb.seagull.spring.entity.ProjectCase;
import luckyweb.seagull.spring.entity.ProjectCasesteps;
import luckyweb.seagull.spring.entity.ProjectPlanCase;
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
import luckyweb.seagull.spring.entity.ProjectTemplateParams;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("projecttemplateparamsDao")
public class ProjectTemplateParamsImpl extends HibernateDaoSupport implements ProjectTemplateParamsDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public ProjectTemplateParams load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (ProjectTemplateParams) this.getHibernateTemplate().get(ProjectTemplateParams.class, id);
	}

	@Override
	public int add(ProjectTemplateParams ptp) throws Exception {
		this.getHibernateTemplate().save(ptp);
		return ptp.getId();
	}
	
	@Override
	public void deleteforob(ProjectTemplateParams ptp) throws Exception {
		try{
		    this.getHibernateTemplate().delete(ptp);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void modify(ProjectTemplateParams ptp) throws Exception {
		this.getHibernateTemplate().update(ptp);
		
	}
	
	private void whereParameter(ProjectTemplateParams ptp, Query query) {
		if (ptp.getTemplateid()!=0) {
			query.setParameter("templateid", ptp.getTemplateid());
		}
	}
	
	@Override
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((ProjectTemplateParams)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}


	@Override
	public int findRows(ProjectTemplateParams ptp, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(ptp, query);
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
	public void delete(String hql) throws Exception {
		Session session=this.getSession(true);
		try {
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.executeUpdate();
		
		session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ProjectTemplateParams> getParamsList(String sql) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectTemplateParams> list=null;
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
