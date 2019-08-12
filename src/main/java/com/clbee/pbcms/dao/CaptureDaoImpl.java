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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.CaptureVO;



@Repository
public class CaptureDaoImpl implements CaptureDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insert(CaptureVO vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			//session.createCriteria(arg0);		
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
	public List<CaptureVO> selectListByBoardSeqWithGb(CaptureVO vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<CaptureVO> list = null;
		
		try {
			tx = session.beginTransaction();
	
			System.out.println("vo.getCaptureGb()====="+vo.getCaptureGb());
			System.out.println("vo.getBoardSeq()====="+vo.getBoardSeq());
			Criteria cr = session.createCriteria(CaptureVO.class).add(Restrictions.and(Restrictions.eq( "captureGb", vo.getCaptureGb()), Restrictions.eq( "boardSeq", vo.getBoardSeq())));
			cr.addOrder(Order.desc("captureSeq"));
			list =  cr.list();
			System.out.println("List<CaptureVO> list size====="+list.size());
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
	public void delete(CaptureVO captureVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			//session.createCriteria(arg0);
			CaptureVO vo = (CaptureVO)session.get(CaptureVO.class, captureVO.getCaptureSeq());
			session.delete(vo);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}
}
