package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.UserInfo;
import luckyweb.seagull.spring.entity.UserRole;



@Repository("userroleDao")
public class UserRoleDaoImpl extends HibernateDaoSupport implements UserRoleDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public UserRole load(int id) throws Exception {
		//return (UserInfo)this.getSession().get(UserInfo.class, id);
		return (UserRole) this.getHibernateTemplate().get(UserRole.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> listall() throws Exception {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(UserRole.class);
	}
	
	@Override
	public List<Object[]> get(String sql) throws Exception {
		Session session = this.getSession(true);
		@SuppressWarnings("unchecked")
		List<Object[]> ob = session.createSQLQuery(sql).list();
		session.close();
		return ob;
	}

	@Override
	public int add(UserRole userrole) throws Exception {
		this.getHibernateTemplate().save(userrole);
		return userrole.getId();
	}

	@Override
	public void modify(UserRole userrole) throws Exception {
		this.getHibernateTemplate().update(userrole);		
	}
	
	@Override
	public void delete(int id) throws Exception {
		try{
			UserRole userrole = this.load(id);
		    this.getHibernateTemplate().delete(userrole);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
