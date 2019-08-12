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

import com.clbee.pbcms.util.ShaPassword;
import com.clbee.pbcms.util.GetRenewPassword;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.MemberVO;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	
	@Override
	public int insertCompanyInfoWithProcedure(CompanyVO companyVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction t = null;
		int companySeq = 0;
		
		try {
			t = session.beginTransaction();
	
			/**************************************************/
			/* Call MYSQL Stored Procedure and MAP it to bean */
			/***********************************************/
			Query callStoredProcedure_MYSQL = session.createSQLQuery("CALL SP_INSERT_COMPANY (:companyName, :companyTel, :zipCode, :addrFirst, :addrDetail)");
		
			callStoredProcedure_MYSQL.setString("companyName", companyVO.getCompanyName());
			callStoredProcedure_MYSQL.setString("companyTel", companyVO.getCompanyTel());
			callStoredProcedure_MYSQL.setString("zipCode", companyVO.getZipcode());
			callStoredProcedure_MYSQL.setString("addrFirst", companyVO.getAddrFirst());
			callStoredProcedure_MYSQL.setString("addrDetail", companyVO.getAddrDetail());
	
			/* callStoredProcedure_MSSQL.list() will execute stored procedure and return the value */
			List<BigInteger> companyList = callStoredProcedure_MYSQL.list();
			System.out.println("@@@@@@@@@@@@"+companyList.get(0).intValue());
			
			companySeq = companyList.get(0).intValue();
	
			t.commit();
		}catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return companySeq;
	}


	@Override
	public CompanyVO findByCustomInfo(String DBName, String value) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		CompanyVO companyVO = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(CompanyVO.class);
			Criterion user = Restrictions.eq(DBName, value);
	
			cr.add(user);
			companyVO = (CompanyVO) cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return companyVO;
	}
	
	
	
	@Override
	public CompanyVO findByCustomInfo(String DBName, int value) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		CompanyVO companyVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(CompanyVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			companyVO = (CompanyVO) cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return companyVO;
	}

	@Override
	public String id_overlap_chk(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		 String result = "";
		try {
			tx = session.beginTransaction();
			
			 Criteria criteria= session.createCriteria(MemberVO.class);
			 criteria.add(Restrictions.like("user_id", id));
			 List list=criteria.list();
			 if(list.size()>0){
				 result="|notUsed";
			 }else{
				 result=id+"|okay";
			 }
			
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
	public String send_pw_mail(String myId, String myMail) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		String result="";
		
		try {
			tx = session.beginTransaction();
			ShaPassword shaPassword = new ShaPassword();
			GetRenewPassword getRenewPassword = new GetRenewPassword();
			
			Criteria criteria= session.createCriteria(MemberVO.class);
			Criterion eqMyId = Restrictions.eq("user_id", myId);
			Criterion eqMyMail = Restrictions.eq("user_email", myMail);
			LogicalExpression andGate = Restrictions.and(eqMyId, eqMyMail);
			criteria.add( andGate );
			
			
			if(criteria.uniqueResult() == null){
				result="noMatch";
			}else{
				MemberVO memberVO = (MemberVO)criteria.uniqueResult();
				String newPw=getRenewPassword.getRenewPassword();
				memberVO.setUserPw(shaPassword.changeSHA256(newPw));
				session.saveOrUpdate(memberVO);
				
				result=newPw;
			}
		
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
	public CompanyVO getComInfo(String companyID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		CompanyVO companyVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria criteria= session.createCriteria(CompanyVO.class);
			criteria.add(Restrictions.eq("company_id", companyID));
			
			companyVO = (CompanyVO)criteria.uniqueResult();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return companyVO;
	}

	
	@Override
	public int updateCompanyInfo(CompanyVO updatedVO, int companySeq) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try {
			tx = session.beginTransaction();
			CompanyVO companyVO = (CompanyVO)session.get(CompanyVO.class, companySeq);
			
			if(updatedVO.getAddrDetail() != null && !"".equals(updatedVO.getAddrDetail()))
				companyVO.setAddrDetail(updatedVO.getAddrDetail());
			if(updatedVO.getAddrFirst() != null && !"".equals(updatedVO.getAddrFirst()))
				companyVO.setAddrFirst(updatedVO.getAddrFirst());
			if(updatedVO.getCompanyName() != null && !"".equals(updatedVO.getCompanyName())) 
				companyVO.setCompanyName(updatedVO.getCompanyName());
			if(updatedVO.getCompanyStatus() != null && !"".equals(updatedVO.getCompanyStatus()))
				companyVO.setCompanyStatus(updatedVO.getCompanyStatus());	
			if(updatedVO.getCompanyTel() != null && !"".equals(updatedVO.getCompanyTel()))
				companyVO.setCompanyTel(updatedVO.getCompanyTel());
			if(updatedVO.getRegDt() != null )
				companyVO.setRegDt(updatedVO.getRegDt());	
			if(updatedVO.getWithdrawalDt() != null )
				companyVO.setWithdrawalDt(updatedVO.getWithdrawalDt());	
			if(updatedVO.getZipcode() != null && !"".equals(updatedVO.getZipcode()))
				companyVO.setZipcode(updatedVO.getZipcode());	
		

			session.update(companyVO);
			tx.commit();
			return 1;
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
			return 0;
		}finally {
			session.close();
		}
	}

	@Override
	public String changePwChk(MemberVO m, String userID, String inputPW) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		String result = "";
		
		try {
			tx = session.beginTransaction();
			ShaPassword shaPassword = new ShaPassword();
			
			String chPw=shaPassword.changeSHA256(inputPW);
			
	//		m.setUser_pw_1(chPw);
	//		m.setUser_id(userID);
			System.out.println("COMPANY DAO [changePwChk] = " + m.getUserId());
			System.out.println("COMPANY DAO [changePwChk] = " + m.getUserPw());
			Criteria criteria= session.createCriteria(MemberVO.class);
			Criterion user_id = Restrictions.eq("user_id", userID);
			Criterion user_pw = Restrictions.eq("user_pw_1", chPw);
			System.out.println("************ userid ="+user_id+"; userpw="+user_pw);
			LogicalExpression andGate = Restrictions.and(user_id, user_pw);
			criteria.add( andGate );
			
			if(criteria.uniqueResult() == null){
				result="noMatch";
			}else{			
				result="okMatch";			
			}	
			
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
	public CompanyVO selectByCompanyId(String companyId) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		CompanyVO companyVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(CompanyVO.class);
			cr.add(Restrictions.eq("company_id", companyId));
			companyVO = (CompanyVO) cr.uniqueResult();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return companyVO;
	}

	public String selectIdByUserNameAndEmail(String lastName, String firstName,
			String email) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		String result = "";	
		try {
			tx = session.beginTransaction();		
				
			Criteria criteria= session.createCriteria(MemberVO.class);
	
			criteria.add(Restrictions.eq("last_name", lastName));
			criteria.add(Restrictions.eq("first_name", firstName));
			criteria.add(Restrictions.eq("email", email));
					
			MemberVO memberVO = (MemberVO)criteria.uniqueResult();
			result=memberVO.getUserId();
	
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
