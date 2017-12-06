package luckyweb.seagull.spring.dao;

import java.util.ArrayList;
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
import luckyweb.seagull.spring.entity.PublicCaseParams;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("publiccaseparamsDao")
public class PublicCaseParamsDaoImpl extends HibernateDaoSupport implements PublicCaseParamsDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public PublicCaseParams load(int id) throws Exception {
		//return (PublicCaseParams)this.getSession().get(PublicCaseParams.class, id);
		return (PublicCaseParams) this.getHibernateTemplate().get(PublicCaseParams.class, id);
	}

	@Override
	public int add(PublicCaseParams pcp) throws Exception {
		this.getHibernateTemplate().save(pcp);
		return pcp.getId();
	}

	@Override
	public void modify(PublicCaseParams pcp) throws Exception {
		this.getHibernateTemplate().update(pcp);		
	}
	
	@Override
	public void delete(PublicCaseParams pcp) throws Exception {
		try{
		    this.getHibernateTemplate().delete(pcp);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int get(String sql) throws Exception {
		int id = 0;
		Session session = this.getSession(true);
		@SuppressWarnings("unchecked")
		List<Object> count = session.createSQLQuery(sql).list();
		if(count.size()!=0){
		if(count.get(0)!=null){
			id = Integer.valueOf(count.get(0).toString());
		  }
		}
		session.close();
		return id;	
	}

	private void whereParameter(PublicCaseParams pcp, Query query) {
		if (pcp.getProjectid()!=0&&pcp.getProjectid()!=PublicConst.STATUS99) {
			query.setParameter("projectid", pcp.getProjectid());
		}
		if (null!=pcp.getParamsname()&&!"".equals(pcp.getParamsname())) {
			query.setParameter("paramsname", "%"+pcp.getParamsname()+"%");
		}
		if (null!=pcp.getParamsvalue()&&!"".equals(pcp.getParamsvalue())) {
			query.setParameter("paramsvalue", "%"+pcp.getParamsvalue()+"%");
		}
		if (null!=pcp.getRemark()&&!"".equals(pcp.getRemark())) {
			query.setParameter("remark", "%"+pcp.getRemark()+"%");
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
				whereParameter((PublicCaseParams)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(PublicCaseParams pcp, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(pcp, query);
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
	public int sqlfindRows(String hql) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PublicCaseParams> listsql(String sql) throws Exception {		
		Session session = this.getSession(true);
		List<PublicCaseParams> list = new ArrayList<>();
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
