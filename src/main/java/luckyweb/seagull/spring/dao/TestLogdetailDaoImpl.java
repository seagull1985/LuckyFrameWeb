package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.TestLogdetail;

@Repository("logdetailDao")
public class TestLogdetailDaoImpl extends HibernateDaoSupport implements
		TestLogdetailDao {
	private static final Logger logger = Logger
			.getLogger(TestLogdetailDaoImpl.class);

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void add(TestLogdetail caseDetail) {
		this.getHibernateTemplate().save(caseDetail);
	}

	@Override
	public List<TestLogdetail> list(TestLogdetail caseDetail) {
		Session session=this.getSession(true);
		List<TestLogdetail> list = session.createQuery(" from TestLogdetail where caseid=?  order by id asc")
				.setInteger(0, caseDetail.getTestCasedetail().getId()).list();
		session.close();
		return list;
	}

	@Override
	public TestLogdetail load(int id) {
		return (TestLogdetail) this.getHibernateTemplate().get(TestLogdetail.class, id);

	}

	@Override
	public void delete(String hql, int id) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql).setInteger("taskId", id);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

}
