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
import luckyweb.seagull.spring.entity.ProjectProtocolTemplate;
import luckyweb.seagull.spring.entity.ProjectTemplateParams;
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
@Repository("projectprotocoltemplateDao")
public class ProjectProtocolTemplateImpl extends HibernateDaoSupport implements ProjectProtocolTemplateDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public ProjectProtocolTemplate load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (ProjectProtocolTemplate) this.getHibernateTemplate().get(ProjectProtocolTemplate.class, id);
	}

	@Override
	public int add(ProjectProtocolTemplate ppt) throws Exception {
		this.getHibernateTemplate().save(ppt);
		return ppt.getId();
	}
	
	@Override
	public void modify(ProjectProtocolTemplate ppt) throws Exception {
		this.getHibernateTemplate().update(ppt);		
	}
	
	@Override
	public void deleteforob(ProjectProtocolTemplate ppt) throws Exception {
		try{
		    this.getHibernateTemplate().delete(ppt);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void whereParameter(ProjectProtocolTemplate ppt, Query query) {
		if (ppt.getProjectid()!=0&&ppt.getProjectid()!=PublicConst.STATUS99) {
			query.setParameter("projectid", ppt.getProjectid());
		}
		if (!StrLib.isEmpty(ppt.getProtocoltype())) {
			query.setParameter("protocoltype", ppt.getProtocoltype());
		}
		if (ppt.getOperationer()!=null&&!ppt.getOperationer().equals("")) {
			query.setParameter("operationer", "%"+ppt.getOperationer()+"%");
		}
		if (ppt.getName()!=null&&!ppt.getName().equals("")) {
			query.setParameter("name", "%"+ppt.getName()+"%");
		}
		if (ppt.getRemark()!=null&&!ppt.getRemark().equals("")) {
			query.setParameter("remark", "%"+ppt.getRemark()+"%");
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
				whereParameter((ProjectProtocolTemplate)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}


	@Override
	public int findRows(ProjectProtocolTemplate ppt, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(ppt, query);
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
	
	@Override
	public List findstepsparamList(String hql) {
		Session session = this.getSession(true);
		List count=null;
		try {
			count = session.createSQLQuery(hql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return count;
	}
	
}
