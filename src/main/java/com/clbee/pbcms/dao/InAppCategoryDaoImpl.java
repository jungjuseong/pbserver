package com.clbee.pbcms.dao;


import java.math.BigInteger;
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



import com.clbee.pbcms.vo.InappcategoryVO;



@Repository
public class InAppCategoryDaoImpl implements InAppCategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insertInAppInfo(InappcategoryVO inCateVo) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int categorySeq = 0;
		
		try {
			tx = session.beginTransaction();
	
			Query callStoredProcedure_MYSQL = session.createSQLQuery("CALL SP_INSERT_INAPPCATEGORY (:storeBundleId, :depth, :categoryName, :categoryParent, :regUserSeq, :regUserId, :regUserGb, :regDt, :chgUserSeq, :chgUserId, :chgUserGb, :chgDt)");
			callStoredProcedure_MYSQL.setString("storeBundleId", inCateVo.getStoreBundleId());
			callStoredProcedure_MYSQL.setString("depth", inCateVo.getDepth());
			callStoredProcedure_MYSQL.setString("categoryName", inCateVo.getCategoryName());
			callStoredProcedure_MYSQL.setLong("categoryParent", inCateVo.getCategoryParent());
			callStoredProcedure_MYSQL.setLong("regUserSeq", inCateVo.getRegUserSeq());
			callStoredProcedure_MYSQL.setString("regUserId", inCateVo.getRegUserId());
			callStoredProcedure_MYSQL.setString("regUserGb", inCateVo.getRegUserGb());
			callStoredProcedure_MYSQL.setDate("regDt", inCateVo.getRegDt());
			callStoredProcedure_MYSQL.setLong("chgUserSeq", inCateVo.getChgUserSeq());
			callStoredProcedure_MYSQL.setString("chgUserId", inCateVo.getChgUserId());
			callStoredProcedure_MYSQL.setString("chgUserGb", inCateVo.getChgUserGb());
			callStoredProcedure_MYSQL.setDate("chgDt", inCateVo.getChgDt());
	
			/* callStoredProcedure_MSSQL.list() will execute stored procedure and return the value */
			List<BigInteger> categoryList = callStoredProcedure_MYSQL.list();
			//ystem.out.println("@@@@@@@@@@@@"+companyList.get(0).intValue());
			
			categorySeq = categoryList.get(0).intValue();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return categorySeq;
	}

	@Override
	public List<InappcategoryVO> selectInAppList(InappcategoryVO inCateVo) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappcategoryVO> result = null;

		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			
			Criterion appSeq 		  = Restrictions.eq("storeBundleId", inCateVo.getStoreBundleId());
			Criterion categoryParent  = Restrictions.eq("categoryParent", 0);
			
			LogicalExpression andGate = Restrictions.and(appSeq, categoryParent);
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

	@Override
	public void updateInAppInfo(InappcategoryVO inCateVo) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try {
			tx = session.beginTransaction();
					
			String hql = "UPDATE InappcategoryVO set "
					+ "categoryName  	   = :categoryName, "
					+ "depth	 		   = :depth, "
					/*+ "categoryParent      = :categoryParent, "*/
					+ "chgUserSeq 	       = :chgUserSeq, "
					+ "chgUserId 		   = :chgUserId, "
					+ "chgUserGb 		   = :chgUserGb "
					+" WHERE categorySeq   = :categorySeq";
	
			Query query1 = session.createQuery(hql)
					.setParameter("categoryName", inCateVo.getCategoryName())
					.setParameter("depth", inCateVo.getDepth())
					/*.setParameter("categoryParent", inCateVo.getCategoryParent())*/
					.setParameter("chgUserSeq", inCateVo.getChgUserSeq())
					.setParameter("chgUserId", inCateVo.getChgUserId())
					.setParameter("chgUserGb", inCateVo.getChgUserGb())
					.setParameter("categorySeq", inCateVo.getCategorySeq());
	
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
	public void deleteInAppInfo(InappcategoryVO inCateVo) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			
			int cateGubun = inCateVo.getCategoryParent();	
			if(cateGubun == 0){
				String hql = "Delete From InappcategoryVO "
						+" WHERE categorySeq   = :categorySeq "
						+" AND   storeBundleId	   = :storeBundleId ";
				
				Query query1 = session.createQuery(hql)				
						.setParameter("categorySeq", inCateVo.getCategorySeq())		
						.setParameter("storeBundleId", inCateVo.getStoreBundleId());
				query1.executeUpdate();
				String hql2 = "Delete From InappcategoryVO "
						+" WHERE categoryParent   = :categoryParent "
						+" AND   storeBundleId	  = :storeBundleId ";
				
				Query query2 = session.createQuery(hql2)				
						.setParameter("categoryParent", inCateVo.getCategorySeq())		
						.setParameter("storeBundleId", inCateVo.getStoreBundleId());	
						
				query2.executeUpdate();
				tx.commit();
			}
			if(cateGubun > 0){
				String hql = "Delete From InappcategoryVO "
						+" WHERE categorySeq   = :categorySeq "
						+" AND   storeBundleId = :storeBundleId ";
				
				Query query1 = session.createQuery(hql)				
						.setParameter("categorySeq", inCateVo.getCategorySeq())		
						.setParameter("storeBundleId", inCateVo.getStoreBundleId());	
						
				query1.executeUpdate();
				tx.commit();
			}
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public Object[] selectInAppList2(InappcategoryVO inCateVo) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Object[] result = null;

		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			
			Criterion categoryParent = Restrictions.eq("categoryParent", inCateVo.getCategoryParent());
			Criterion storeBundleId  = Restrictions.eq("storeBundleId", inCateVo.getStoreBundleId());
			
			LogicalExpression andGate = Restrictions.and(categoryParent, storeBundleId);
			cr.add( andGate );		
			
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
	public InappcategoryVO findByCustomInfo( String DBName, int intValue){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappcategoryVO inappcategoryVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			Criterion user = Restrictions.eq(DBName, intValue);
			
			cr.add(user);
			inappcategoryVO = (InappcategoryVO) cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inappcategoryVO;
	}
	
	@Override
	public InappcategoryVO findByCustomInfo( String DBName, String value){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappcategoryVO inappcategoryVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			inappcategoryVO = (InappcategoryVO) cr.uniqueResult();
			
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inappcategoryVO;
	}
	
	@Override
	public List<InappcategoryVO> getListInAppCategoryforOneDepth( String DBName, String storeBundleId){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappcategoryVO> list = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			Criterion user = Restrictions.eq(DBName, storeBundleId);
			
			cr.add(user);
			cr.add(Restrictions.eq("depth", "1"));
			list = cr.list();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return list;
	}
	
	@Override
	public List<InappcategoryVO> getListInAppCategory( String DBName, String storeBundleId){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappcategoryVO> list = null;
		
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			Criterion user = Restrictions.eq(DBName, storeBundleId);
			
			cr.add(user);
			list = cr.list();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return list;
	}

	@Override
	public int categoryIsDuplicated( String storeBundleId, String categoryName ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappcategoryVO.class);
			cr.add(
				Restrictions.and(
					Restrictions.eq("storeBundleId", storeBundleId),
					Restrictions.eq("categoryName", categoryName)
				)
			);
			
			list = cr.list();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		
		System.out.println("list = " + list);
		if( list != null) {
			if(list.size() == 0) {
				return 1;	
			}else if( list.size() > 0) {
				return 0;
			}else {
				return -1;
			}
		}else {
			return 1;
		}
	}
}
