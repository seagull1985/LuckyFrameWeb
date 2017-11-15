package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.UserAuthority;




@Repository("userauthorityDao")
public class UserAuthorityDaoImpl extends HibernateDaoSupport implements UserAuthorityDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public UserAuthority load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (UserAuthority) this.getHibernateTemplate().get(UserAuthority.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAuthority> listall() throws Exception {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(UserAuthority.class);
	}
	
	@Override
	public List<Object[]> get(String sql) throws Exception {
		Session session = this.getSession(true);
		@SuppressWarnings("unchecked")
		List<Object[]> ob = session.createSQLQuery(sql).list();
		session.close();
		return ob;
	}

}
