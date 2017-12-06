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

import luckyweb.seagull.spring.entity.PlanFlowCheck;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("planflowcheckDao")
public class PlanFlowCheckDaoImpl extends HibernateDaoSupport implements PlanFlowCheckDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		PlanFlowCheck pfc = this.load(id);
		this.getHibernateTemplate().delete(pfc);
		
	}

	private void whereParameter(PlanFlowCheck pfc, Query query) {
		if (pfc.getId()!=0) {
			query.setParameter("id", pfc.getId());
		}
		if (pfc.getStatus()!=0) {
			query.setParameter("status", pfc.getStatus());
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
				whereParameter((PlanFlowCheck)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}
	
	@Override
	public int findRows(PlanFlowCheck pfc, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(pfc, query);
			s= Integer.valueOf(
					query.list().get(0)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return s;
	}
	

	@Override
	public int add(PlanFlowCheck pfc) throws Exception {
		try{
		this.getHibernateTemplate().save(pfc);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pfc.getId();
	}



	@Override
	public void modify(PlanFlowCheck pfc) throws Exception {
		this.getHibernateTemplate().update(pfc);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanFlowCheck> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public PlanFlowCheck load(int id) throws Exception {
		return (PlanFlowCheck) this.getHibernateTemplate().get(PlanFlowCheck.class, id);
		//return (FlowCheck) this.getSession().load(FlowCheck.class, id);
	}

}
