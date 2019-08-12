package com.clbee.pbcms.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.ChangelistVO;

@Repository
public class ChangelistDaoImpl implements ChangelistDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int getSeqAfterInsertChangelist(ChangelistVO changelistVO) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.save(changelistVO);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return changelistVO.getChglistSeq();
	}
}