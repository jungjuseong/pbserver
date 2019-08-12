package com.clbee.pbcms.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.ProvisionVO;

@Repository
public class ProvisionDaoImpl implements ProvisionDao {

	@Autowired
	private SessionFactory sessionFactory;

	//20180711 - lsy : app make request -> mybatis
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int update(ProvisionVO vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int result = 0;
		
		try {
			tx = session.beginTransaction();
			
			System.out.println("�������� �ĺ��� �̸���  =  " + vo.getProvId());
			String hql = "UPDATE ProvisionVO set "  + 
		             " provName = :provName " +				
		             ", chgUserSeq = :chgUserSeq " +
		             ", chgUserId = :chgUserId " +
		             ", chgUserGb = :chgUserGb " +
		             ", provTestGb = :provTestGb " +
			 		 ", provId = :provId ";
		             if(vo.getDistrProfileName()!=null){//�Ƴ�...�Ѥ�;;;;;;
		             hql +=", distrProfileName = :distrProfileName " +
		            		 ", distrProfileSaveName = :distrProfileSaveName " +
		            		 ", distrProfile = :distrProfile ";
		             }
		             hql +=" WHERE provSeq = :provSeq ";
			Query query = session.createQuery(hql);
			query.setParameter("provName", vo.getProvName());
			if(vo.getDistrProfileName()!=null){
				query.setParameter("distrProfileName", vo.getDistrProfileName());
				query.setParameter("distrProfileSaveName", vo.getDistrProfileSaveName());
				query.setParameter("distrProfile", vo.getDistrProfile());
	        }
			query.setParameter("provId", vo.getProvId());
			query.setParameter("chgUserSeq", vo.getChgUserSeq());
			query.setParameter("chgUserId", vo.getChgUserId());
			query.setParameter("chgUserGb", vo.getChgUserGb());
			query.setParameter("provTestGb", vo.getProvTestGb());
			query.setParameter("provSeq", vo.getProvSeq());
			result = query.executeUpdate();	
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
	public void insert( ProvisionVO vo ){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
		
			session.save(vo);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}	

	@Override
	public List<ProvisionVO> selectList(int regUserSeq, int regCompanySeq){
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<ProvisionVO> list = null;
		
		try {
			tx = session.beginTransaction();
	
			
			Query query = session.createQuery("FROM ProvisionVO E "
					+ "WHERE (E.regCompanySeq = 0 and E.regUserSeq = :regUserSeq) or (E.regCompanySeq !=0 and E.regCompanySeq = :regCompanySeq)  ORDER BY E.chgDt DESC ")
					.setParameter("regUserSeq", regUserSeq).setParameter("regCompanySeq", regCompanySeq);		
			
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
	public int getListCount() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number number = null;
		try {
			tx = session.beginTransaction();		
			
			// �ش� ���̵� User�� �۵�� ����
			number = ((Number) session.createCriteria(ProvisionVO.class).
					setProjection(Projections.rowCount()).uniqueResult());		
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return number.intValue();
	}
	
	@Override
	public void delete(int provSeq, int regUserSeq, int regCompanySeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int result = 0;
		try {
			tx = session.beginTransaction();
			//ProvisionVO appVO = (ProvisionVO)session.get(ProvisionVO.class, provSeq);
			//session.delete(appVO);
			String hql = "DELETE FROM ProvisionVO E " + 
		             "WHERE E.provSeq = :provSeq AND ((E.regCompanySeq = 0 and E.regUserSeq = :regUserSeq) or (E.regCompanySeq !=0 and E.regCompanySeq = :regCompanySeq) )";
			Query query = session.createQuery(hql);
			query.setParameter("provSeq", provSeq);
			query.setParameter("regUserSeq", regUserSeq);
			query.setParameter("regCompanySeq", regCompanySeq);
			result = query.executeUpdate();
			System.out.println("Rows affected: " + result);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public ProvisionVO selectRow(int provSeq, int regUserSeq, int regCompanySeq) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ProvisionVO vo = null;
		try {
			tx = session.beginTransaction();
			//ProvisionVO appVO = (ProvisionVO)session.get(ProvisionVO.class, provSeq);
			//session.delete(appVO);
			String hql = "FROM ProvisionVO E " + 
		             "WHERE E.provSeq = :provSeq AND ((E.regCompanySeq = 0 and E.regUserSeq = :regUserSeq) or (E.regCompanySeq !=0 and E.regCompanySeq = :regCompanySeq) )";
			Query query = session.createQuery(hql);
			query.setParameter("provSeq", provSeq);
			query.setParameter("regUserSeq", regUserSeq);
			query.setParameter("regCompanySeq", regCompanySeq);
			vo = (ProvisionVO) query.uniqueResult();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return vo;
	}

	@Override
	public List<ProvisionVO>  selectList(ProvisionVO vo, int appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<ProvisionVO> list = null;
		try {
			tx = session.beginTransaction();
			String sql = "FROM ProvisionVO E WHERE ((E.regCompanySeq = 0 and E.regUserSeq = :regUserSeq) or (E.regCompanySeq !=0 and E.regCompanySeq = :regCompanySeq) )";
			sql += " and E.distrProfile = :distrProfile ";
			if(vo.getProvName()!=null&&!"".equals(vo.getProvName())){
				sql += " and ( E.provName like :provName or E.distrProfileName like :provName )";			
			}
			if(vo.getProvId()!=null&&!"".equals(vo.getProvId())){
				if(vo.getProvId().indexOf("*")>-1){
					sql += " and ( E.provId like :provId ) ";	
				}else{
					sql += " and ( E.provId = :provId or E.provId = :provId2) ";	
				}
			}
			sql += "ORDER BY E.chgDt DESC";
			Query query = session.createQuery(sql);
			query.setParameter("regUserSeq", vo.getRegUserSeq());		
			query.setParameter("regCompanySeq", vo.getRegCompanySeq());
			query.setParameter("distrProfile", vo.getDistrProfile());
//			System.out.println("vo.getRegUserSeq()==="+vo.getRegUserSeq());
//			System.out.println("vo.getRegCompanySeq()==="+vo.getRegCompanySeq());
//			System.out.println("vo.getDistrProfile()==="+vo.getDistrProfile());
			if(vo.getProvName()!=null&&!"".equals(vo.getProvName())){
				query.setParameter("provName", "%"+vo.getProvName()+"%");
				System.out.println("provName==="+vo.getProvName()+"%");
			}
			if(vo.getProvId()!=null&&!"".equals(vo.getProvId())){
				if(vo.getProvId().indexOf("*")>-1){
					String provId = vo.getProvId().substring(0, vo.getProvId().indexOf("*"));
					//System.out.println("provId==="+provId);
					query.setParameter("provId", provId+"%");
//					System.out.println("provId+'%'==="+provId+"%");
				}else{
					String provId2 = vo.getProvId().substring(0, vo.getProvId().lastIndexOf(".")+1)+"*";
					query.setParameter("provId", vo.getProvId());
					query.setParameter("provId2",provId2);
//					System.out.println("vo.getProvId()==="+vo.getProvId());
//					System.out.println("provId2==="+provId2);
				}
			}

			list = query.list();
			
			System.out.println("End in Select List");
			
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
	public ProvisionVO findByCustomInfo(String DBName, String value) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ProvisionVO provisionVO = null;
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(ProvisionVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			provisionVO = (ProvisionVO) cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return provisionVO;
	}
	
	
	
	@Override
	public ProvisionVO findByCustomInfo(String DBName, int value) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		ProvisionVO provisionVO = null;
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(ProvisionVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			provisionVO = (ProvisionVO) cr.uniqueResult();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return provisionVO;
	}

	@Override
	public List<ProvisionVO> selectProvisionInfoByAppSeqForMakePbapp(String DBName, int appSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, appSeq);
	}
}
