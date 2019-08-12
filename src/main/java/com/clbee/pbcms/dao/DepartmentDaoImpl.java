package com.clbee.pbcms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.DepartmentVO;
import com.clbee.pbcms.vo.InappcategoryVO;


@Repository
public class DepartmentDaoImpl implements DepartmentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int insertDepartmentInfo( DepartmentVO departmentVO ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int DepartmentSeq = 0;
		
		try {
			tx = session.beginTransaction();
	
			session.save(departmentVO);
			DepartmentSeq = departmentVO.getDepartmentSeq();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return DepartmentSeq;
	}

	@Override
	public void updateDepartmentInfo( DepartmentVO departmentVO ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
	
			String hql = "UPDATE DepartmentVO set "
					+ "departmentName  	   = :departmentName, "
					+ "depth	 		   = :depth, "
					+ "useGb		       = :useGb "
					+" WHERE departmentSeq = :departmentSeq";
	
			Query query1 = session.createQuery(hql)
					.setParameter("departmentName", departmentVO.getDepartmentName())
					.setParameter("depth", departmentVO.getDepth())
					.setParameter("useGb", departmentVO.getUseGb())
					.setParameter("departmentSeq", departmentVO.getDepartmentSeq());
	
			query1.executeUpdate();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public List<DepartmentVO> selectList( int companySeq, String toUse ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<DepartmentVO> result = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(DepartmentVO.class);
			
			cr.add(
				Restrictions.and(
					Restrictions.eq("departmentParent", 0),
					Restrictions.eq("companySeq", companySeq)
				)
			);
			if("true".equals(toUse)) cr.add(Restrictions.eq("useGb", "1")); 
	
			result = cr.list(); 
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return result;
	}

	
	@Override
	public Object[] selectChildList(int parentSeq, int companySeq, String toUse) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Object[] result = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(DepartmentVO.class);
			
			cr.add(
				Restrictions.and(
						Restrictions.eq("departmentParent", parentSeq),
						Restrictions.eq("companySeq", companySeq)
				)
			);
			
			if("true".equals(toUse)) cr.add(Restrictions.eq("useGb", "1")); 
			
			result = cr.list().toArray(); 
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return result;
	}

	@Override
	public List<DepartmentVO> selectChildArrayList(int parentSeq, int companySeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List result = null;
		try {
			tx = session.beginTransaction();

			Criteria cr = session.createCriteria(DepartmentVO.class);
			
			Criterion categoryParent = Restrictions.eq("departmentParent", parentSeq);
			Criterion appSeq       = Restrictions.eq("companySeq", companySeq);
			LogicalExpression andGate = Restrictions.and(categoryParent, appSeq);
			cr.add( andGate );		
			
			result = cr.list();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return result;
	}
}
