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

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
