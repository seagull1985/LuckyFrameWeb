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

import luckyweb.seagull.spring.entity.SecondarySector;
import luckyweb.seagull.util.StrLib;

@Repository("secondarysectorDao")
public class SecondarySectorDaoImpl extends HibernateDaoSupport implements SecondarySectorDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public int add(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(sector);
		return sector.getSectorid();
	}

	@Override
	public void modify(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(sector);
	}

	@Override
	public void modifyState(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyInfo(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(sector);
	}

	@Override
	public List<SecondarySector> list(SecondarySector sector) throws Exception {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<SecondarySector> list = this.getHibernateTemplate().find(" from SecondarySector");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecondarySector> list() throws Exception {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(SecondarySector.class);
	}

	@Override
	public SecondarySector load(int id) throws Exception {
		// TODO Auto-generated method stub
		return (SecondarySector) this.getHibernateTemplate().get(SecondarySector.class, id);
	}

	@Override
	public List<SecondarySector> load(String name, String cmdType,
			String planPath) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private void whereParameter(SecondarySector ss, Query query) {

		if (!StrLib.isEmpty(ss.getDepartmenthead())) {
			query.setParameter("departmenthead", "%"+ss.getDepartmenthead().trim()+"%");
		}
		if (!StrLib.isEmpty(ss.getDepartmentname())) {
			query.setParameter("departmentname","%"+ss.getDepartmentname().trim()+"%");
		}

	}
	
	@Override
	public List findByPage(final String hql, final Object value, final int offset, final int pageSize) {
		// TODO Auto-generated method stub
		// 通过一个HibernateCallback 对象来执行查询
		//System.out.println(hql);
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			// 实现hibernateCallback接口必须实现的方法
			
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 执行hibernate 分页查询
				Query query= session.createQuery(hql);
				whereParameter((SecondarySector)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(SecondarySector sector, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(sector, query);
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<SecondarySector> findSectorList() throws Exception {
		// TODO Auto-generated method stub
		List<SecondarySector> list=null;
		Session session=this.getSession(true);
		try {
			list = session.createQuery(" from SecondarySector order by sectorid").list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return list;
	}
	
}
