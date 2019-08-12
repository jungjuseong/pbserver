package com.clbee.pbcms.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.util.ShaPassword;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//20180619 - lsy : when app request(Library), load inapp info -> mybatis
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addMember( MemberVO member ) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.save(member);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public int updateMemberInfo( MemberVO updatedVO, int userNum ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
	
			MemberVO memberVO = (MemberVO)session.get(MemberVO.class, userNum);
	
			
			if(updatedVO.getChgDt() != null)
				memberVO.setChgDt(updatedVO.getChgDt());
			if(updatedVO.getChgIp() != null && !"".equals(updatedVO.getChgIp()))
				memberVO.setChgIp(updatedVO.getChgIp());
			if(updatedVO.getCompanyGb() != null && !"".equals(updatedVO.getCompanyGb())) 
				memberVO.setCompanyGb(updatedVO.getCompanyGb());
			if(updatedVO.getCompanySeq() != 0 ) 
				memberVO.setCompanySeq(updatedVO.getCompanySeq());
			
			//������ ��ĭ���� ������ �� ����.
			if(updatedVO.getEmail() != null )
				memberVO.setEmail(updatedVO.getEmail());	
			if(updatedVO.getEmailChkDt() != null )
				memberVO.setEmailChkDt(updatedVO.getEmailChkDt());
			if(updatedVO.getEmailChkGb() != null && !"".equals(updatedVO.getEmailChkGb()))
				memberVO.setEmailChkGb(updatedVO.getEmailChkGb());	
			if(updatedVO.getEmailChkSession() != null && !"".equals(updatedVO.getEmailChkSession()))
				memberVO.setEmailChkSession(updatedVO.getEmailChkSession());	
			if(updatedVO.getFirstName() != null && !"".equals(updatedVO.getFirstName()))
				memberVO.setFirstName(updatedVO.getFirstName());	
			if(updatedVO.getLastName() != null && !"".equals(updatedVO.getLastName()))
				memberVO.setLastName(updatedVO.getLastName());	
			if(updatedVO.getLoginDt() != null )
				memberVO.setLoginDt(updatedVO.getLoginDt());	
			if(updatedVO.getPhone() != null && !"".equals(updatedVO.getPhone()))
				memberVO.setPhone(updatedVO.getPhone());	
			if(updatedVO.getRegDt() != null  )
				memberVO.setRegDt(updatedVO.getRegDt());
			if(updatedVO.getRegIp() != null && !"".equals(updatedVO.getRegIp()))
				memberVO.setRegIp(updatedVO.getRegIp());
			if(updatedVO.getUserGb() != null && !"".equals(updatedVO.getUserGb()))
				memberVO.setUserGb(updatedVO.getUserGb());
			if(updatedVO.getUserId() != null && !"".equals(updatedVO.getUserId()))
				memberVO.setUserId(updatedVO.getUserId());										/*������ϰ���� �ؽ���*/
			if(updatedVO.getUserPw() != null && !"".equals(updatedVO.getUserPw()) && !"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".equals(updatedVO.getUserPw()))
				memberVO.setUserPw(updatedVO.getUserPw());
			if(updatedVO.getUserStatus() != null && !"".equals(updatedVO.getUserStatus()))
				memberVO.setUserStatus(updatedVO.getUserStatus());
			if(updatedVO.getWithdrawalDt() != null )
				memberVO.setWithdrawalDt(updatedVO.getWithdrawalDt());
			if(updatedVO.getOnedepartmentSeq() != null )
				memberVO.setOnedepartmentSeq(updatedVO.getOnedepartmentSeq());
			if(updatedVO.getTwodepartmentName() != null )
				memberVO.setTwodepartmentSeq(updatedVO.getTwodepartmentSeq());
			if(updatedVO.getDateGb() != null && !"".equals(updatedVO.getDateGb()))
				memberVO.setDateGb(updatedVO.getDateGb());
			if(updatedVO.getUserStartDt() != null )
				memberVO.setUserStartDt(updatedVO.getUserStartDt());
			if(updatedVO.getUserEndDt() != null)
				memberVO.setUserEndDt(updatedVO.getUserEndDt());
			if(updatedVO.getSessionId() != null && !"".equals(updatedVO.getSessionId()))
				memberVO.setSessionId(updatedVO.getSessionId());
			if(updatedVO.getLoginStatus() != null && !"".equals(updatedVO.getLoginStatus()))
				memberVO.setLoginStatus(updatedVO.getLoginStatus());
			if(updatedVO.getLoginDeviceuuid() != null && !"".equals(updatedVO.getLoginDeviceuuid()))
				memberVO.setLoginDeviceuuid(updatedVO.getLoginDeviceuuid());
			
			//20180511 : lsy - group add
			if(updatedVO.getGroupName() != null && !"".equals(updatedVO.getGroupName()))
				memberVO.setGroupName(updatedVO.getGroupName());

			session.update(memberVO);
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
	public int selectItselfForExisting( String DBname, String itSelf ){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(MemberVO.class);
			cr.add(Restrictions.eq( DBname, itSelf)); // where
			
			MemberVO memberVO = (MemberVO)cr.uniqueResult();
			tx.commit();
			
			if(memberVO == null){
				return 0;
			}else{
				return 1;
			}
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
			return 2;
		}finally {
			session.close();
		}
	}
	
	@Override
	public List<MemberVO> logInVerify( String ID, String pswd ){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<MemberVO> list = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(MemberVO.class);
			
			Criterion username = Restrictions.eq("userId", ID);
			Criterion password = Restrictions.eq("userPw", pswd);
	
			LogicalExpression andGate = Restrictions.and(username, password);
			cr.add( andGate );
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
	public MemberVO findByCustomInfo(String DBName, String value) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		MemberVO memberVO = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(MemberVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			memberVO = (MemberVO) cr.uniqueResult();
	
			/*	�� ������ username�� �ش��ϴ� �κ��� id�ϰ�쿡 ����
				MemberVO memberVO = (MemberVO) session.load(MemberVO.class, username); 
			*/
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return memberVO;
	}

	@Override
	public MemberVO findByUserName(String username){

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		MemberVO memberVO = null;

		try {
			tx = session.beginTransaction();

			Criteria cr = session.createCriteria(MemberVO.class);
			Criterion user = Restrictions.eq("userId", username);
	
			cr.add(user);
			memberVO = (MemberVO) cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return memberVO;
	}
	
	
	@Override
	public MemberVO findCompanyMemberIdByCompanySeqAndUserGb( int companySeq ){

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		MemberVO memberVO = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(MemberVO.class);
			/* ����ȸ�� �ϰ�� null�� �����Ѵ�.*/
			if(companySeq == 0)return null;
			else{
				cr.add(
						Restrictions.and(
								Restrictions.eq("companySeq", companySeq),
								Restrictions.eq("userGb", "127")
							)
						);
			}
			memberVO = (MemberVO) cr.uniqueResult();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return memberVO;
	}
	
	public String changeSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
	

	@Override
	public MemberVO selectMemberSuccessYn(MemberVO memberVO) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		MemberVO result = null;	
		try {
			tx = session.beginTransaction();
			Criteria criteria= session.createCriteria(MemberVO.class);		
			
			criteria.add(Restrictions.eq("firstName", memberVO.getFirstName()));
			criteria.add(Restrictions.eq("lastName", memberVO.getLastName()));
			criteria.add(Restrictions.eq("email", memberVO.getEmail()));
			
			result = (MemberVO) criteria.uniqueResult();			
			
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
	public Integer selectMemberCount(MemberVO memberVO) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number Count = null;
		try {
			tx = session.beginTransaction();	
		
			Criteria criteria= session.createCriteria(MemberVO.class);	
			Count = ((Number) session.createCriteria(MemberVO.class)
					.add(Restrictions.eq("firstName", memberVO.getFirstName()))
					.add(Restrictions.eq("lastName", memberVO.getLastName()))
					.add(Restrictions.eq("email", memberVO.getEmail()))
					.setProjection(Projections.rowCount()).uniqueResult());	
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return Count.intValue();
	}
	
	@Override
	public MemberVO selectMemberSuccessYn_(MemberVO memberVO) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		MemberVO result = null;		
		
		try {
			tx =  session.beginTransaction();

			Criteria criteria= session.createCriteria(MemberVO.class);		
			
			criteria.add(Restrictions.eq("userId", memberVO.getUserId()));
			criteria.add(Restrictions.eq("email", memberVO.getEmail()));
			
			result = (MemberVO) criteria.uniqueResult();			
			
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
	public Integer selectMemberCount_(MemberVO memberVO) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number Count = null;
		
		try {
			tx = session.beginTransaction();	
			
			Criteria criteria= session.createCriteria(MemberVO.class);	
			Count = ((Number) session.createCriteria(MemberVO.class)
					.add(Restrictions.eq("userId", memberVO.getUserId()))
					.add(Restrictions.eq("email", memberVO.getEmail()))
					.setProjection(Projections.rowCount()).uniqueResult());	
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return Count.intValue();
	}

	@Override
	public void updateMemberPw(MemberVO memberVO) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try {
			tx = session.beginTransaction();
			ShaPassword shaPassword = new ShaPassword();
			
			memberVO.setUserPw(shaPassword.changeSHA256(memberVO.getUserPw()));
			
			String hql = "UPDATE MemberVO set "
					+ "user_Pw = :user_Pw"
					+" WHERE user_Id = :user_Id";
							
			
			Query query1 = session.createQuery(hql)
					.setParameter("user_Pw", memberVO.getUserPw())
					.setParameter("user_Id", memberVO.getUserId());
					
			query1.executeUpdate();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	
	
	/**
     * ��� ȸ��, ���� �������϶� ���� ListMember�� �ҷ��´�
     *
     * @param startNo ��𼭺��� ��������
     * @param companySeq ��� ��������� �������� ( ��� ȸ�� )
     * @param MaxResult �ѹ��� ������ �ִ� ����Ʈ ����
     * @param searchType �˻��Ҷ� searchType�� �̸����� ���̵����� ��
     * @param searchValue �˻� String��
     * @param isMember �� ����� ���»���� ������ Member���� Admin_Service����
     * @return List<MemberVO>
     */
	
	
	@Override
	public List<MemberVO> getListMember(int startNo, int companySeq, int MaxResult, String searchType, String searchValue, String isAvailable, boolean isMember ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<MemberVO> list = null;
		
		try {
			tx = session.beginTransaction();	

			Query query = null;
			if(isMember == true) {//service가 아닌경우
				if("userId".equals(searchType)) {
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userId like :userId) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("userId", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userId like :userId )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("userId", "%"+searchValue+"%");
					}
				}else if("userName".equals(searchType)) {
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND concat(E.lastName, E.firstName) like :fullName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("fullName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND concat(E.lastName, E.firstName) like :fullName )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("fullName", "%"+searchValue+"%");
					}
				}else if("onedepartmentName".equals(searchType)){
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.onedepartmentName like :onedepartmentName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("onedepartmentName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.onedepartmentName like :onedepartmentName )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("onedepartmentName", "%"+searchValue+"%");
					}
				}else if("twodepartmentName".equals(searchType)){
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.twodepartmentName like :twodepartmentName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("twodepartmentName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.twodepartmentName like :twodepartmentName )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("twodepartmentName", "%"+searchValue+"%");
					}
				}else if("userGroup".equals(searchType)) {//20180525 : lsy - searchtype group add
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userGroup like :userGroup ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("userGroup", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userGroup like :userGroup )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq)
							.setParameter("userGroup", "%"+searchValue+"%");
					}
				}else{
					System.out.println("searchType Is Anonymous");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq);
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("companySeq", companySeq);
					}
				}
			}else{//service인 경우
				if("userId".equals(searchType)) {
					System.out.println("searchType Is userId");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userId like :userId ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127")
							.setParameter("userId", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userId like :userId )"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127")
							.setParameter("userId", "%"+searchValue+"%");
					}
				}else if("userName".equals(searchType)) {
					System.out.println("searchType Is userName");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND concat(E.lastName, E.firstName) like :fullName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127")
							.setParameter("fullName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND concat(E.lastName, E.firstName) like :fullName )"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127")
							.setParameter("fullName", "%"+searchValue+"%");
					}
				}else if("userGroup".equals(searchType)) {//20180525 : lsy - searchtype group add
					System.out.println("searchType Is userGroup");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userGroup like :userGroup ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127")
							.setParameter("userGroup", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userGroup like :userGroup )"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127")
							.setParameter("userGroup", "%"+searchValue+"%");
					}
				}else {
					System.out.println("searchType Is Anonymous");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb  )"
							+ " ORDER BY E.regDt DESC ")
							.setFirstResult(startNo)
							.setMaxResults(10)
							.setParameter("userGb", "127");
					}
				}
			}
			
			if(isAvailable != null && "false".equals(isAvailable)){
				query.setParameter("userStatus1", "4").setParameter("userStatus2", "5");
			}
						
			list = (List<MemberVO>)query.list();
			
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
	public int getListMemberCount(int companySeq, String searchType, String searchValue, String isAvailable, boolean isMember) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int totalNumber = 0;
		
		try {
			tx = session.beginTransaction();
			
			Query query = null;
			if(isMember == true) {
				if("userId".equals(searchType)) {
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userId like :userId ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("userId", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userId like :userId )"
							+ " AND (E.userGb = '63') "
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("userId", "%"+searchValue+"%");
					}
				}else if("userName".equals(searchType)) {
					System.out.println("searchType Is userName");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND concat(E.lastName, E.firstName) like :fullName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("fullName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND concat(E.lastName, E.firstName) like :fullName )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("fullName", "%"+searchValue+"%");
					}
				}else if("onedepartmentName".equals(searchType)){
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.onedepartmentName like :onedepartmentName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("onedepartmentName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.onedepartmentName like :onedepartmentName )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("onedepartmentName", "%"+searchValue+"%");
					}
				}else if("twodepartmentName".equals(searchType)){
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.twodepartmentName like :twodepartmentName ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("twodepartmentName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.twodepartmentName like :twodepartmentName )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("twodepartmentName", "%"+searchValue+"%");
					}
				}else if("userGroup".equals(searchType)) {//20180525 : lsy - searchtype group add
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userGroup like :userGroup ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("userGroup", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq AND E.userGroup like :userGroup )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq)
							.setParameter("userGroup", "%"+searchValue+"%");
					}
				}else {
					System.out.println("searchType Is Anonymous");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq);
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.companySeq = :companySeq )"
							+ " AND (E.userGb = '63')"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("companySeq", companySeq);
					}
				}
			}else {
				if("userId".equals(searchType)) {
					System.out.println("searchType Is userId");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userId like :userId ) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127")
							.setParameter("userId", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userId like :userId )"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127")
							.setParameter("userId", "%"+searchValue+"%");
					}
				}else if("userName".equals(searchType)) {
					System.out.println("searchType Is userName");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND concat(E.lastName, E.firstName) like :fullName) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127")
							.setParameter("fullName", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userName like :userName )"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127")
							.setParameter("userName", "%"+searchValue+"%");
					}
				}else if("userGroup".equals(searchType)) {//20180525 : lsy - searchtype group add
					System.out.println("searchType Is userGroup");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userGroup like :userGroup) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127")
							.setParameter("userGroup", "%"+searchValue+"%");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb AND E.userGroup like :userGroup)"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127")
							.setParameter("userGroup", "%"+searchValue+"%");
					}
				}else {
					System.out.println("searchType Is Anonymous");
					if(isAvailable != null && "false".equals(isAvailable)){
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb) AND (E.userStatus = :userStatus1 OR E.userStatus = :userStatus2)"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127");
					}else {
						query = session.createQuery("FROM MemberVO E "
							+ "WHERE (E.userGb = :userGb)"
							+ " ORDER BY E.regDt DESC ")
							.setParameter("userGb", "127");
					}
				}
			}
			
			if(isAvailable != null && "false".equals(isAvailable)){
				query.setParameter("userStatus1", "4").setParameter("userStatus2", "5");
			}
			
			totalNumber = query.list().size();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return totalNumber;
	}


	@Override
	public MemberVO findByCustomInfo(String DBName, int value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		MemberVO memberVO = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(MemberVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			memberVO = (MemberVO) cr.uniqueResult();
				
			/*	�� ������ username�� �ش��ϴ� �κ��� id�ϰ�쿡 ����
				MemberVO memberVO = (MemberVO) session.load(MemberVO.class, username); 
			*/
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return memberVO;
	}	
	
	@Override
	public int selectCountWithPermisionUserByCompanySeq( int companySeq){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number Count = null;
		
		try{
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(MemberVO.class);	

			Count = ((Number) session.createCriteria(MemberVO.class)
					.add(Restrictions.eq("companySeq", companySeq))
					.add(
						Restrictions.and(
							Restrictions.or(
								Restrictions.eq("userGb", "1"),
								Restrictions.eq("userGb", "5"),
								Restrictions.eq("userGb", "21"),
								Restrictions.eq("userGb", "29"),
								Restrictions.eq("userGb", "127")
							),
						Restrictions.eq("userStatus", "4")
						)
					)					
					.setProjection(Projections.rowCount()).uniqueResult());
						
			
			tx.commit();
		}catch( Exception e){
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return  Count.intValue();
	}
	
	@Override
	public int selectCountByCompanySeq(int companySeq) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number Count = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria criteria = session.createCriteria(MemberVO.class);	
	
			
			Count = ((Number) session.createCriteria(MemberVO.class)
					.add(Restrictions.eq("companySeq", companySeq))
					.add(Restrictions.eq("userGb", "1"))
					.add(Restrictions.eq("userGb", "5"))
					.add(Restrictions.eq("userGb", "21"))
					.add(Restrictions.eq("userGb", "29"))
					.setProjection(Projections.rowCount()).uniqueResult());	
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return Count.intValue();
	}

	@Override
	public List<MemberVO> getUserList(int companySeq, String[] useS, String searchValue, String searchType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		try {
			tx = session.beginTransaction();	
	
			String clause ="";
			String searchClause = "";
			
			if(useS != null && useS.length > 0){
				clause = " AND T.userSeq Not In ( ";
				for( int i =0; i< useS.length ; i ++) {
					if( i == useS.length-1) clause += useS[i]+" ";
					else if( useS.length == 1) clause += useS[i]+" ";
					else clause += useS[i]+", ";
				}
				clause += ")";
			}
			
			
			if(searchValue != null && searchType != null) {
				switch(Integer.parseInt(searchType)) {
					case 1:
						searchClause =" AND T.userId like '%" + searchValue+"%'";
						break;
					case 2: 
						searchClause =" AND concat(T.lastName, T.firstName) like '%" + searchValue+"%'";
						break;
					case 3: 
						searchClause =" AND T.onedepartmentName like '%" + searchValue+"%'";
						break;
					case 4: 
						searchClause =" AND T.twodepartmentName like '%" + searchValue+"%'";
						break;
				}
			}
			
			
			
			System.out.println("mysql Sentence = " + clause);
			System.out.println("mysql CompanySeq = " + companySeq);
			
			Query query = session.createQuery("FROM MemberVO T "
					+ " WHERE (T.userGb = '1' OR T.userGb = '5' OR T.userGb = '21' OR T.userGb = '29' OR T.userGb = '127') "
					+ " AND T.userStatus = :userStatus "
					+ " AND T.companySeq = :companySeq "
					+ clause
					+ searchClause
					+ " ORDER BY T.regDt DESC ")
					//.setFirstResult(startNo).setMaxResults(10)
					.setParameter("userStatus", "4")
					.setParameter("companySeq", companySeq);	
					//.setParameter("userSeq", useS);
			
			
			list = query.list();
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
	public List<MemberVO> getPermitList(int companySeq, String[] useS) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
			
			String clause ="";
			
			if(useS != null && useS.length > 0){
				clause = " AND T.userSeq In (";
				for( int i =0; i< useS.length ; i ++) {
					if( i == useS.length-1) clause += useS[i]+" ";
					else if( useS.length == 1) clause += useS[i]+" ";
					else clause += useS[i]+", ";
				}
				clause += ")";
			}else {
				return null;
			}
			
			
			System.out.println("mysql Sentence = " + clause);
			System.out.println("mysql CompanySeq = " + companySeq);
			
			Query query = session.createQuery("FROM MemberVO T "
					+ " WHERE (T.userGb = '1' OR T.userGb = '5' OR T.userGb = '21' OR T.userGb = '29' OR T.userGb = '127')"
					+ " AND T.companySeq = :companySeq "
					+ clause
					+ " ORDER BY T.regDt DESC ")
					//.setFirstResult(startNo).setMaxResults(10)
					.setParameter("companySeq", companySeq);	
					//.setParameter("userSeq", useS);
			
			
			list = query.list();
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
	public int deleteMemberInfo(int userNum ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
	
			MemberVO memberVO = (MemberVO)session.get(MemberVO.class, userNum);
	
			session.delete(memberVO);
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

	//20180619 - lsy : when app request(Library), load inapp info
	@Override
	public int getCompanySeqForInappContentCopy(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, userSeq);
	}

	@Override
	public String selectCompanyName(String DBName, int company_seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, company_seq);
	}

}
