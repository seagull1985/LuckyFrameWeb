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

import luckyweb.seagull.spring.entity.Accident;
import luckyweb.seagull.spring.entity.FlowCheck;
import luckyweb.seagull.spring.entity.FlowInfo;

@Repository("flowinfoDao")
public class FlowInfoDaoImpl extends HibernateDaoSupport implements FlowInfoDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public List<FlowInfo> list(FlowInfo flowinfo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<FlowInfo> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public FlowInfo load(int id) throws Exception {
		return (FlowInfo) this.getSession().load(FlowInfo.class, id);
	}

	@Override
	public FlowInfo get(int id) throws Exception {
		// TODO Auto-generated method stub
		return (FlowInfo) this.getHibernateTemplate().get(FlowInfo.class, id);
	}


	@Override
	public List listcheckinfo(String sql) throws Exception {
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
