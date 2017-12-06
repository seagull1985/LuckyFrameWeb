package luckyweb.seagull.spring.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import luckyweb.seagull.spring.entity.ProjectCase;

/**
 * =================================================================
 * 这是一个受限制的自由软件！您不能在任何未经允许的前提下对程序代码进行修改和用于商业用途；也不允许对程序代码修改后以任何形式任何目的的再发布。
 * 为了尊重作者的劳动成果，LuckyFrame关键版权信息严禁篡改
 * 有任何疑问欢迎联系作者讨论。 QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author seagull
 */
@Repository("projectCaseDao")
public class ProjectCaseDaoImpl extends HibernateDaoSupport implements ProjectCaseDao{
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}



	@Override
	public void delete(int id) throws Exception {
		try{
			ProjectCase projectcase = this.load(id);
		    this.getHibernateTemplate().delete(projectcase);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void whereParameter(ProjectCase projectcase, Query query) {		
		if (projectcase.getProjectid()!=0&&projectcase.getProjectid()!=PublicConst.STATUS99) {
			query.setParameter("projectid", projectcase.getProjectid());
		}
		if (projectcase.getModuleid()!=0&&projectcase.getModuleidarr().length>0) {
			Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(projectcase.getModuleidarr()));
			query.setParameterList("moduleid", collection);
		}
		if (null!=projectcase.getSign()&&!"".equals(projectcase.getSign())) {
			query.setParameter("sign", "%"+projectcase.getSign()+"%");
		}
		if (null!=projectcase.getName()&&!"".equals(projectcase.getName())) {
			query.setParameter("name", "%"+projectcase.getName()+"%");
		}
		if (null!=projectcase.getOperationer()&&!"".equals(projectcase.getOperationer())) {
			query.setParameter("operationer", "%"+projectcase.getOperationer()+"%");
		}
		if (null!=projectcase.getRemark()&&!"".equals(projectcase.getRemark())) {
			query.setParameter("remark", "%"+projectcase.getRemark()+"%");
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
				whereParameter((ProjectCase)value, query);
				List result =query
						.setFirstResult(offset).setMaxResults(pageSize).list();
				session.close();
				return result;
			}

		});
		return list;
	}

	@Override
	public int findRows(ProjectCase projectcase, String hql) {
		int s=0;
		Session session=this.getSession(true);
		try {
			Query query=session.createQuery(hql);
			whereParameter(projectcase, query);
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
	public int add(ProjectCase projectcase) throws Exception {
		this.getHibernateTemplate().save(projectcase);
		return projectcase.getId();
	}



	@Override
	public void modify(ProjectCase projectcase) throws Exception {
		this.getHibernateTemplate().update(projectcase);
		
	}



	@Override
	public void modifyState(ProjectCase projectcase) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void modifyInfo(ProjectCase projectcase) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<ProjectCase> list(ProjectCase projectcase)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectCase> list(String hql) throws Exception {
		 return this.getHibernateTemplate().find(hql);
	}



	@Override
	public ProjectCase load(int id) throws Exception {		
		return (ProjectCase) this.getHibernateTemplate().get(ProjectCase.class, id);
	}


	@Override
	public List<ProjectCase> findJobsList() {
		// TODO Auto-generated method stub
		return null;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public List<ProjectCase> getList(String hql) throws Exception {
		// TODO Auto-generated method stub
		List<ProjectCase> list=null;
		Session session=this.getSession(true);
		try {
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}	
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getCaseMaxIndex(String sql) throws Exception {
		// TODO Auto-generated method stub
		List list=null;
		Session session=this.getSession(true);
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		if(list.size()==0){
			return "0";
		}else{
			return list.get(0).toString();
		}

	}

}
