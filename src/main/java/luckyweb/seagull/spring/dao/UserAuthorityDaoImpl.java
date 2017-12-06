package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.UserAuthority;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
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
