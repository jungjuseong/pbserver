package com.clbee.pbcms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.BundleVO;

@Repository
public class BundleDaoImpl implements BundleDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	//20180705 - lsy : appRequest -> myBatis
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insert(BundleVO vo) {
		// TODO Auto-generated method stub
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
	public void deleteByAppSeq(int seq) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			String hql = "DELETE FROM BundleVO E "  + 
		             "WHERE E.appSeq = :appSeq";
			Query query = session.createQuery(hql);
			query.setParameter("appSeq", seq);
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
	public List listByAppSeq(Integer appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<BundleVO> list = null;
		
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM BundleVO E WHERE E.appSeq = :appSeq ORDER BY E.bundleSeq ASC ")
					.setParameter("appSeq", appSeq);		
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
	public int getListCount( BundleVO vo, int companySeq ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<BundleVO> bundleList = null;

		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(BundleVO.class, "bundleVO"); //.createCriteria(AppVO.class);
			cr.add(
				Restrictions.eq("bundleName", vo.getBundleName())
			);
	
			bundleList =  cr.list();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		if(bundleList != null) {
			System.out.println("bundleList.size() = " + bundleList.size());
			if(bundleList.size() != 0) {
				
				System.out.println("bundleList.get(0).getAppVO().getRegMemberVO().getCompanySeq() = " + bundleList.get(0).getAppVO().getRegMemberVO().getCompanySeq());
				System.out.println("companySeq = " + companySeq);
				if(companySeq != bundleList.get(0).getAppVO().getRegMemberVO().getCompanySeq()) return -1;
				
				if(bundleList.size() == 1) {

					if(vo.getOsType() == 4 && bundleList.get(0).getOsType()  == 4) {
						// app_resgist.html���� ��û�� ostype�� 4�̰�
						// DB���� ���� bundle�� ��ϵǾ��ִ� ostype�� 4�̸� ��� �Ұ���
						return 1;
					}else if( vo.getOsType() != 4  && bundleList.get(0).getOsType() != 4){
						// app_resgist.html���� ��û�� ostype�� 4�� �ƴϰ� ( iOS )
						// DB���� ���� bundle�� ��ϵǾ��ִ� ostype�� 4���ƴϸ� ��ϺҰ��� ( iOS )
						return 1;
					}
					// �׿� ���� �ٸ� OSTYPE�̸� ��� ����
					else return 0;
				}
				return bundleList.size();
			}else return 0;
		}else {
			return 0;
		}
	}

	@Override
	public List<BundleVO> selectBeforeBundleInfoByAppSeqForNewVersion(String DBName, int app_seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, app_seq);
	}

	@Override
	public void insertNewVersionAppBundleInfo(String DBName, BundleVO newVersionAppBundleInfo) {
		// TODO Auto-generated method stub
		sqlSession.insert(DBName, newVersionAppBundleInfo);
	}

	@Override
	public List<HashMap> selectBundleInfoByAppSeqForMakePbapp(String DBName, BundleVO bundleParam) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, bundleParam);
	}
}
