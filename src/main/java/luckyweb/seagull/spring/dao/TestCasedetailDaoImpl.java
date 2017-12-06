package luckyweb.seagull.spring.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import luckyweb.seagull.spring.entity.TestCasedetail;
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
@Repository("casedetailDao")
public class TestCasedetailDaoImpl extends HibernateDaoSupport implements TestCasedetailDao {
	private static final Logger logger = Logger.getLogger(TestCasedetailDaoImpl.class);
	
	
	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public void add(TestCasedetail caseDetail) {
		this.getHibernateTemplate().save(caseDetail);
	}


	@Override
	public List<TestCasedetail> list(TestCasedetail caseDetail) {
		List<TestCasedetail> list=this.getSession().createQuery(" from TestCasedetail where taskid=?")
				.setInteger(0, caseDetail.getTestTaskexcute().getId())
				.list();
		return list;
	}



	@Override
	public TestCasedetail load(int id) {
		return (TestCasedetail) this.getHibernateTemplate().get(TestCasedetail.class, id);
	}
	
	@Override
	/**
	 * 使用hql语句进行分页查询
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param value
	 *            如果hql 有一个参数需要传入，value就是传入Hql语句的参数
	 * 
	 * @param offset
	 *            第一条记录索引
	 * 
	 * @param pageSize
	 *            每页需要显示的记录条数
	 * @return 当前页的所有记录
	 */
	public List findByPage(final String hql, final Object value,
			final int offset, final int pageSize) {
		// 通过一个HibernateCallback 对象来执行查询

		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				@SuppressWarnings("rawtypes")
				List result=null; 
				try{
					Query query=session.createQuery(hql);
					whereParameter((TestCasedetail)value, query);
					result = query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						session.close();
					}return result;
			}

		});
		return list;

	}
	
	private void whereParameter(TestCasedetail caseDetail, Query query) {
		if (!StrLib.isEmpty(caseDetail.getStartDate())&&!StrLib.isEmpty(caseDetail.getEndDate())) {
			query.setParameter("startDate", caseDetail.getStartDate().trim());
			query.setParameter("endDate", caseDetail.getEndDate().trim());
		}if (caseDetail.getTaskId()!=0) {
			query.setParameter("taskId", caseDetail.getTaskId());
		}
		if (!StrLib.isEmpty(caseDetail.getCasestatus())) {
			query.setParameter("casestatus", caseDetail.getCasestatus().trim());
		}
		if (!StrLib.isEmpty(caseDetail.getCaseno())) {
			query.setParameter("caseno", "%"+caseDetail.getCaseno().trim()+"%");
		}
		if (!StrLib.isEmpty(caseDetail.getCasename())) {
			query.setParameter("casename", "%"+caseDetail.getCasename().trim()+"%");
		}
	}
	
	@Override
	public int findRows(String hql, TestCasedetail caseDetail) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(caseDetail, query);
			s= Integer.valueOf(query.list().get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			session.close();
			
		}		
		return s;
	}


	@Override
	public void delete(String hql, int id) throws Exception {
		Session session=this.getSession(true);
		session.beginTransaction();
		Query query =session .createQuery(hql);
		query.setInteger("taskId", id);
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}

}
