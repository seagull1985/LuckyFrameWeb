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
		List<TestLogdetail> list = this
				.getSession()
				.createQuery(
						" from TestLogdetail where caseid=?  order by id asc")
				.setInteger(0, caseDetail.getTestCasedetail().getId()).list();
		return list;
	}

	@Override
	public TestLogdetail get(int id) {
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

	/*
	 * 使用hql语句进行分页查询
	 * 
	 * @param hql 需要查询的hql语句
	 * 
	 * @param offset 第一条记录索引
	 * 
	 * @param pageSize 每页需要显示的记录条数
	 * 
	 * @return 当前页的所有记录
	 */
	/*
	 * public List findByPage(final TestLogdetail tast, final String hql, final
	 * int offset, final int pageSize) { // 通过一个HibernateCallback 对象来执行查询
	 * 
	 * List list = getHibernateTemplate().executeFind(new HibernateCallback() {
	 * // 实现hibernateCallback接口必须实现的方法
	 * 
	 * public Object doInHibernate(Session session) throws HibernateException {
	 * // 执行hibernate 分页查询 List result =null; try{ result=
	 * session.createQuery(hql) .setInteger(0, tast.getTestCasedetail().getId())
	 * .setFirstResult(offset).setMaxResults(pageSize).list(); } catch
	 * (Exception e) { e.printStackTrace(); }finally{ session.close(); } return
	 * result; }
	 * 
	 * }); return list;
	 * 
	 * }
	 */

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
	/*
	 * public List findByPage(final String hql, final Object value, final int
	 * offset, final int pageSize) { // 通过一个HibernateCallback 对象来执行查询
	 * 
	 * List list = getHibernateTemplate().executeFind(new HibernateCallback() {
	 * // 实现hibernateCallback接口必须实现的方法
	 * 
	 * public Object doInHibernate(Session session) throws HibernateException {
	 * // 执行hibernate 分页查询 List result=null; try{ result =
	 * session.createQuery(hql).setParameter(0, value)
	 * .setFirstResult(offset).setMaxResults(pageSize).list();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }finally{ session.close();
	 * }return result; }
	 * 
	 * }); return list;
	 * 
	 * }
	 */

	/**
	 * 使用hql语句进行分页查询
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param values
	 *            如果Hql有多个参数需要传入，values 就是传入hql的参数组
	 * @param offset
	 *            第一条记录索引
	 * 
	 * @param pageSize
	 *            每页需要显示的记录条数
	 * @return 当前页的所有记录
	 */
	/*
	 * public List findByPage(final String hql, final Object[] values, final int
	 * offset, final int pageSize) { // 通过一个HibernateCallback 对象来执行查询
	 * 
	 * List list = getHibernateTemplate().executeFind(new HibernateCallback() {
	 * // 实现hibernateCallback接口必须实现的方法
	 * 
	 * public Object doInHibernate(Session session) throws HibernateException {
	 * // 执行hibernate 分页查询 Query query = session.createQuery(hql); // 为hql语句传入参数
	 * for (int i = 0; i < values.length; i++) { query.setParameter(i,
	 * values[i]); } List result = query.setFirstResult(offset)
	 * .setMaxResults(pageSize).list(); return result; }
	 * 
	 * }); return list;
	 * 
	 * }
	 */

	/*
	 * public int findRows(String hql, TestLogdetail tast) { int s =
	 * Integer.valueOf(this.getSession().createQuery(hql) .setInteger(0,
	 * tast.getTestCasedetail().getId()).list().get(0) .toString()); return s; }
	 */

}
