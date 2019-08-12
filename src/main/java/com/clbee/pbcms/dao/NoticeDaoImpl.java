package com.clbee.pbcms.dao;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.DeviceVO;
import com.clbee.pbcms.vo.NoticeSubVO;
import com.clbee.pbcms.vo.NoticeVO;
import com.clbee.pbcms.vo.NoticeappSubVO;

@Repository
public class NoticeDaoImpl implements NoticeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int insertNoticeInfo(NoticeVO noticeVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.save(noticeVO);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return noticeVO.getNoticeSeq();
	}

	@Override
	public NoticeVO selectNoticeInfo(int noticeSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		NoticeVO noticeVO = null;
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(NoticeVO.class);
			cr.add(
				Restrictions.eq("noticeSeq", noticeSeq)
			);
			
			noticeVO = (NoticeVO)cr.uniqueResult();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return noticeVO;
	}


	@Override
	public List<NoticeVO> selectNoticeList( int startNo, int companySeq, String searchType, String searchValue) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<NoticeVO> list = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(NoticeVO.class);
			cr.add(
				Restrictions.eq("companySeq", companySeq)
			)
			.setFirstResult(startNo)
			.setMaxResults(10)
			.addOrder(Order.desc("regDt"));
			
			/*
			 * 1. �������� �̸� �˻�
			*/
			if(searchType != null && !"".equals(searchType)) {
				switch(Integer.parseInt(searchType)) {
					case 1 :
						cr.add(Restrictions.like("noticeName", "%"+searchValue+"%"));
						break;
				}
			}
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
	public int selectNoticeListCount(  int companySeq, String searchType, String searchValue ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<NoticeVO> list = null;
		
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(NoticeVO.class);
			cr.add(
				Restrictions.eq("companySeq", companySeq)
			);
			
			/*
			 * 1. �������� �̸� �˻�
			*/
			if(searchType != null && !"".equals(searchType)) {
				switch(Integer.parseInt(searchType)) {
					case 1 :
						cr.add(Restrictions.like("noticeName", "%"+searchValue+"%"));
						break;
				}
			}
			
			list = cr.list();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return list.size();
	}

	@Override
	public int updateNoticeInfo(NoticeVO updatedVO) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			NoticeVO noticeVO = (NoticeVO)session.get(NoticeVO.class, updatedVO.getNoticeSeq());
			
			if(updatedVO.getCompanySeq() != null )
				noticeVO.setCompanySeq(updatedVO.getCompanySeq());
			if(updatedVO.getNoticeEndDt() != null )
				noticeVO.setNoticeEndDt(updatedVO.getNoticeEndDt());
			if(updatedVO.getNoticeName() != null && !"".equals(updatedVO.getNoticeName()))
				noticeVO.setNoticeName(updatedVO.getNoticeName());
			if(updatedVO.getNoticeStartDt() != null )
				noticeVO.setNoticeStartDt(updatedVO.getNoticeStartDt());
			if(updatedVO.getNoticeText() != null && !"".equals(updatedVO.getNoticeText()))
				noticeVO.setNoticeText(updatedVO.getNoticeText());
			if(updatedVO.getPublicGb() != null && !"".equals(updatedVO.getPublicGb()))
				noticeVO.setPublicGb(updatedVO.getPublicGb());
			if(updatedVO.getRegUserSeq() != null)
				noticeVO.setRegUserSeq(updatedVO.getRegUserSeq());
			if(updatedVO.getUseUserGb() != null && !"".equals(updatedVO.getUseUserGb()))
				noticeVO.setUseUserGb(updatedVO.getUseUserGb());
			if(updatedVO.getAppGb() != null && !"".equals(updatedVO.getAppGb()))
				noticeVO.setAppGb(updatedVO.getAppGb());
			session.update(noticeVO);
			tx.commit();
			return 1;
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
			return -1;
		}finally {
			session.close();
		}
	}

	@Override
	public int insertNoticeSubInfo(NoticeSubVO noticeSubVO) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
	
			session.save(noticeSubVO);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return noticeSubVO.getNoticeSubSeq();
	}

	@Override
	public void deleteNoticeSubInfo(NoticeSubVO noticeSubVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			String hql = "DELETE FROM NoticeSubVO T " + 
		             "WHERE T.noticeSeq = :noticeSeq ";
			
			Query query = session.createQuery(hql);
			query.setParameter("noticeSeq", noticeSubVO.getNoticeSeq());
			query.executeUpdate();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
	}

	@Override
	public int insertNoticeappSubInfo(NoticeappSubVO noticeappSubVO) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
	
			session.save(noticeappSubVO);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return noticeappSubVO.getNoticeappSubSeq();
	}

	@Override
	public void deleteNoticeappSubInfo(NoticeappSubVO noticeappSubVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			String hql = "DELETE FROM NoticeappSubVO T " + 
		             "WHERE T.noticeSeq = :noticeSeq ";
			
			Query query = session.createQuery(hql);
			query.setParameter("noticeSeq", noticeappSubVO.getNoticeSeq());
			query.executeUpdate();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
	}

	@Override
	public List<NoticeSubVO> selectNoticeSubList(int noticeSeq) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(NoticeSubVO.class);
			
			cr.add(
				Restrictions.eq("noticeSeq", noticeSeq)
			);
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
	public List<NoticeappSubVO> selectNoticeappSubList(int noticeSeq) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(NoticeappSubVO.class);
	
			cr.add(
				Restrictions.eq("noticeSeq", noticeSeq)
			);
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
	public List<NoticeVO> getListNoticeByCompany( int companySeq, int userSeq, String storeBundleId ) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<NoticeVO> list = null;
		try {
			tx = session.beginTransaction();
	
			System.out.println("companySeq = " + companySeq );
			System.out.println("userSeq = " + userSeq );
			System.out.println("storeBundleId = " + storeBundleId );
	
			Criteria cr = session.createCriteria(NoticeVO.class,"noticeVO");
			cr.createAlias("noticeVO.noticeappSubVO",  "appSubVO", JoinType.LEFT_OUTER_JOIN);
			cr.createAlias("noticeVO.noticeSubVO",  "subVO", JoinType.LEFT_OUTER_JOIN);
	
			cr.add(
				Restrictions.and(
					Restrictions.eq("companySeq", companySeq),
					Restrictions.eq("publicGb", "1")
				)
			);
	
			Disjunction or = Restrictions.disjunction();
			or.add(
				Restrictions.and(
				Restrictions.eq("useUserGb", "1"),
				Restrictions.eq("appGb", "1")
				)
			);
			or.add(
				Restrictions.and(
				Restrictions.eq("useUserGb", "2"),
				Restrictions.eq("appGb", "1"),
				Restrictions.eq("subVO.userSeq", userSeq)
				)
			);
	
			or.add(
				Restrictions.and(
					Restrictions.eq("useUserGb", "1"),
					Restrictions.eq("appGb", "2"),
					Restrictions.eq("appSubVO.storeBundleId", storeBundleId)
				)
			);
			or.add(
				Restrictions.and(
					Restrictions.eq("useUserGb", "2"),
					Restrictions.eq("appGb", "2"),
					Restrictions.eq("appSubVO.storeBundleId", storeBundleId),
					Restrictions.eq("subVO.userSeq", userSeq)
				)
			);
			cr.add(or);
			
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
	public void deleteNoticeappSubInfo(int appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			String hql = "DELETE FROM NoticeSubVO T " + 
		             "WHERE T.appSeq = :appSeq ";
			
			Query query = session.createQuery(hql);
			query.setParameter("appSeq",appSeq);
			query.executeUpdate();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}
}

