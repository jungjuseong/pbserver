package com.clbee.pbcms.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.InAppList;
import com.clbee.pbcms.vo.InappMetaVO;
import com.clbee.pbcms.vo.InappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.InappcategoryVO;
import com.clbee.pbcms.vo.MemberVO;


@Repository
public class InAppDaoImpl implements InAppDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	//20180327 - lsy : develop version managemenet -> mybatis
	@Autowired
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	//20180327 - lsy : develop version managemenet -> mybatis - end

	@Override
	public InappVO findByCustomInfo(String DBName, int intValue) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappVO inappVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappVO.class);
			Criterion user = Restrictions.eq(DBName, intValue);
			
			cr.add(user);
			inappVO = (InappVO) cr.uniqueResult();
			
			Hibernate.initialize(inappVO.getInappSubVO());
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return inappVO;
	}


	@Override
	public InappVO findByCustomInfo(String DBName, String value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappVO inappVO = null;

		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			inappVO = (InappVO) cr.uniqueResult();
			Hibernate.initialize(inappVO.getInappSubVO());
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inappVO;
	}

	@Override
	public List findListByCustomInfo(String DBName, String value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;
		
			try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			list = cr.list();
	
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
			
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
	public List findListByCustomInfo(String DBName, int value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(InappVO.class);
	
			cr.add(
				Restrictions.and(
					Restrictions.eq(DBName, value),
					Restrictions.eq("completGb", "1"),
					Restrictions.eq("useGb", "1"),
					Restrictions.eq("limitGb", "2")
				)
			);
	
			list = cr.list();
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
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
	public List<InappVO> getListInappVO(String DBName, String storeBundleId, int userSeq) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(InappVO.class,"inappVO");
			cr.createAlias("inappVO.inappSubVO", "Sub", JoinType.LEFT_OUTER_JOIN);
			
			cr.add(
				Restrictions.or(
					Restrictions.and(
						Restrictions.eq("limitGb","2"),
						Restrictions.eq("completGb", "1"),
						Restrictions.eq(DBName, storeBundleId),
						Restrictions.eq("useGb", "1"),
						Restrictions.eq("useUserGb", "1")
					),
					Restrictions.and(
						Restrictions.eq("limitGb","2"),
						Restrictions.eq("completGb", "1"),
						Restrictions.eq(DBName, storeBundleId),
						Restrictions.eq("useGb", "1"),
						Restrictions.eq("useUserGb", "2"),
						Restrictions.eq("Sub.userSeq", userSeq)
					)
				)
			);
		
			list = cr.list();
	
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
	
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
	public List<InappVO> getListInappVO(String DBName, String value) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;

		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(InappVO.class);
			Criterion user = Restrictions.eq(DBName, value);
	
			cr.add(user);
			list = cr.list();
	
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
	
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
	public int getListCntByBundleId(InappVO vo, InAppList inAppList, MemberVO memberVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number number = null;
		try {
			tx = session.beginTransaction();
			
			
			System.out.println("storeBundleId for InAP = " + vo.getStoreBundleId());
			Criteria appCr = session.createCriteria(InappVO.class); //.createCriteria(AppVO.class);
			appCr.createAlias("regMemberVO", "memberVO");
			appCr.add(Restrictions.eq("storeBundleId", vo.getStoreBundleId()));
			appCr.add(
				Restrictions.or(
					Restrictions.and(
						Restrictions.eq("memberVO.companyGb", "2"), 
						Restrictions.eq("memberVO.userSeq", memberVO.getUserSeq())
					),
					Restrictions.and(
						Restrictions.eq("memberVO.companyGb", "1"), 
						Restrictions.eq("memberVO.companySeq", memberVO.getCompanySeq())
					)
				)
			);

			if(inAppList.getIsAvailable() != null && "false".equals(inAppList.getIsAvailable())) {
				appCr.add(
						Restrictions.or(
								Restrictions.eq("limitGb", "1"), 
								Restrictions.eq("useGb", "1"))
						);
			}
			
			if(inAppList.getSearchValue()!=null&&inAppList.getSearchValue().length()>0){
				if(inAppList.getSearchType()!=null&&inAppList.getSearchType().length()>0){
					appCr.add(Restrictions.and(Restrictions.like(inAppList.getSearchType(), "%"+inAppList.getSearchValue()+"%")));
				}else if((inAppList.getSearchType()==null||inAppList.getSearchType().length()==0)||"".equals(inAppList.getSearchType())){
					System.out.println("appList.getSearchType()====="+inAppList.getSearchType());
					System.out.println("appList.getSearchValue()====="+inAppList.getSearchValue());
					appCr.add(Restrictions.and(Restrictions.or(Restrictions.like("inappName", "%"+inAppList.getSearchValue()+"%"), Restrictions.like("descriptionText", "%"+inAppList.getSearchValue()+"%"))));
				}
			}
			number = (Number)appCr.setProjection(Projections.rowCount()).uniqueResult();
			
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
	public List<InappVO> getListByBundleId(InappVO vo, InAppList inAppList, MemberVO memberVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;

		try {
			tx = session.beginTransaction();
			System.out.println("Hello in Select List");
	
	
			Criteria appCr = session.createCriteria(InappVO.class); //.createCriteria(AppVO.class);
			appCr.createAlias("regMemberVO", "memberVO");
			appCr.add(Restrictions.eq("storeBundleId", vo.getStoreBundleId()));
			appCr.add(Restrictions.or(Restrictions.and(Restrictions.eq("memberVO.companyGb", "2"), Restrictions.eq("memberVO.userSeq", memberVO.getUserSeq())),Restrictions.and(Restrictions.eq("memberVO.companyGb", "1"), Restrictions.eq("memberVO.companySeq", memberVO.getCompanySeq()))));

			if(inAppList.getIsAvailable() != null && "false".equals(inAppList.getIsAvailable())) {
				appCr.add(
						Restrictions.or(
								Restrictions.eq("limitGb", "1"), 
								Restrictions.eq("useGb", "1"))
						);
			}
			
			if(inAppList.getSearchValue()!=null&&inAppList.getSearchValue().length()>0){
				if(inAppList.getSearchType()!=null&&inAppList.getSearchType().length()>0){
					appCr.add(Restrictions.and(Restrictions.like(inAppList.getSearchType(), "%"+inAppList.getSearchValue()+"%")));
				}else if((inAppList.getSearchType()==null||inAppList.getSearchType().length()==0)||"".equals(inAppList.getSearchType())){
					appCr.add(Restrictions.and(Restrictions.or(Restrictions.like("inappName", "%"+inAppList.getSearchValue()+"%"), Restrictions.like("descriptionText", "%"+inAppList.getSearchValue()+"%"))));
				}
			}
	
			appCr.addOrder(Order.desc("chgDt"));
			list = appCr.list();
	
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
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
	public int getSeqAfterInsertAppInfo(InappVO vo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int inappSeq = 0;

		try {
			tx = session.beginTransaction();
			/*session.createCriteria(arg0)*/
			session.save(vo);
			inappSeq = vo.getInappSeq();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return vo.getInappSeq();
	}

	@Override
	public InappVO selectForUpdate(InappVO ivo, MemberVO memberVO) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappVO vo = null;

		try {
			tx = session.beginTransaction();
	
			System.out.println("storeBUndle Id = " + ivo.getStoreBundleId());
			Criteria appCr = session.createCriteria(InappVO.class, "inappVO"); //.createCriteria(AppVO.class);
			appCr.createAlias("regMemberVO", "memberVO");
			appCr.add(Restrictions.eq("inappVO.storeBundleId", ivo.getStoreBundleId()));
			appCr.add(Restrictions.eq("inappVO.inappSeq", ivo.getInappSeq()));
	
			appCr.add(Restrictions.or(Restrictions.and(Restrictions.eq("memberVO.companyGb", "2"), Restrictions.eq("memberVO.userSeq", memberVO.getUserSeq())),Restrictions.and(Restrictions.eq("memberVO.companyGb", "1"), Restrictions.eq("memberVO.companySeq", memberVO.getCompanySeq()))));
			
			vo = (InappVO) appCr.uniqueResult();
			
			Hibernate.initialize(vo.getInappSubVO());
		
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
	public void updateInAppMetaInfo(InappMetaVO updatedVO, int inappMetaSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
	
			InappMetaVO inappMetaVO = (InappMetaVO)session.get(InappMetaVO.class, inappMetaSeq);
			//appVO.setAppVO( updatedVO );
	
			if(updatedVO.getInappMetaAuthor() != null && !"".equals(updatedVO.getInappMetaAuthor()))
				inappMetaVO.setInappMetaAuthor(updatedVO.getInappMetaAuthor());
			
			if(updatedVO.getInappMetaBody() != null && !"".equals(updatedVO.getInappMetaBody()))
				inappMetaVO.setInappMetaBody(updatedVO.getInappMetaBody());
			
			if(updatedVO.getInappMetaBuildflag() != null && !"".equals(updatedVO.getInappMetaBuildflag()))
				inappMetaVO.setInappMetaBuildflag(updatedVO.getInappMetaBuildflag());
			
			if(updatedVO.getInappMetaCover1() != null && !"".equals(updatedVO.getInappMetaCover1()))
				inappMetaVO.setInappMetaCover1(updatedVO.getInappMetaCover1());
			
			if(updatedVO.getInappMetaCover2() != null && !"".equals(updatedVO.getInappMetaCover2()))
				inappMetaVO.setInappMetaCover2(updatedVO.getInappMetaCover2());
			
			if(updatedVO.getInappMetaCover3() != null && !"".equals(updatedVO.getInappMetaCover3()))
				inappMetaVO.setInappMetaCover3( updatedVO.getInappMetaCover3());
			
			if(updatedVO.getInappMetaCover4() != null && !"".equals(updatedVO.getInappMetaCover4()))
				inappMetaVO.setInappMetaCover4( updatedVO.getInappMetaCover4());
			
			if(updatedVO.getInappMetaDescription() != null && !"".equals(updatedVO.getInappMetaDescription()))
				inappMetaVO.setInappMetaDescription( updatedVO.getInappMetaDescription());
			
			if(updatedVO.getInappMetaDistributor() != null && !"".equals(updatedVO.getInappMetaDistributor()))
				inappMetaVO.setInappMetaDistributor( updatedVO.getInappMetaDistributor());
			
			if(updatedVO.getInappMetaISBN() != null && !"".equals(updatedVO.getInappMetaISBN()))
				inappMetaVO.setInappMetaISBN(updatedVO.getInappMetaISBN());
	
			if(updatedVO.getInappMetaPage() != null && !"".equals(updatedVO.getInappMetaPage()))
				inappMetaVO.setInappMetaPage(updatedVO.getInappMetaPage());
			
			if(updatedVO.getInappMetaPrice() != null && !"".equals(updatedVO.getInappMetaPrice()))
				inappMetaVO.setInappMetaPrice( updatedVO.getInappMetaPrice());
			
			if(updatedVO.getInappMetaSize() != null && !"".equals(updatedVO.getInappMetaSize()))
				inappMetaVO.setInappMetaSize( updatedVO.getInappMetaSize());
			
			if(updatedVO.getInappMetaStatus() != null && !"".equals(updatedVO.getInappMetaStatus()))
				inappMetaVO.setInappMetaStatus( updatedVO.getInappMetaStatus());
			
			if(updatedVO.getInappMetaSubtitle() != null && !"".equals(updatedVO.getInappMetaSubtitle()))
				inappMetaVO.setInappMetaSubtitle( updatedVO.getInappMetaSubtitle());
			
			if(updatedVO.getInappMetaTranslator() != null && !"".equals(updatedVO.getInappMetaTranslator()))
				inappMetaVO.setInappMetaTranslator( updatedVO.getInappMetaTranslator());
	
			if(updatedVO.getInappSeq() != null)
				inappMetaVO.setInappSeq( updatedVO.getInappSeq() );
				
			session.update(inappMetaVO);
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}
	
	@Override
	public void updateInAppInfo(InappVO updatedVO, int inappSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
	
			InappVO inappVO = (InappVO)session.get(InappVO.class, inappSeq);
			//appVO.setAppVO( updatedVO );
	
			if(updatedVO.getInappName() != null && !"".equals(updatedVO.getInappName() ))
			{inappVO.setInappName(updatedVO.getInappName());}
			if(updatedVO.getCategorySeq() != null && !"".equals(updatedVO.getCategorySeq()))
			{inappVO.setCategorySeq(updatedVO.getCategorySeq());}
			if(updatedVO.getCategoryName() != null && !"".equals(updatedVO.getCategoryName()))
			{inappVO.setCategoryName(updatedVO.getCategoryName());}
			if(updatedVO.getChgUserGb() != null && !"".equals(updatedVO.getChgUserGb() ))
			{inappVO.setChgUserGb(updatedVO.getChgUserGb());}
			if(updatedVO.getChgUserId() != null && !"".equals(updatedVO.getChgUserId() ))
			{inappVO.setChgUserId(updatedVO.getChgUserId());}
			if(updatedVO.getChgUserSeq() != null && !"".equals(updatedVO.getChgUserSeq() ))
			{inappVO.setChgUserSeq(updatedVO.getChgUserSeq());}
			if(updatedVO.getCompletGb() != null && !"".equals(updatedVO.getCompletGb() ))
			{inappVO.setCompletGb(updatedVO.getCompletGb());}
			if(updatedVO.getCouponGb() != null && !"".equals(updatedVO.getCouponGb() ))
			{inappVO.setCouponGb(updatedVO.getCouponGb());}
			// ���� �ѹ��� "" üũ�� ���ϴ������� ""�϶��� ""�� �ٲ��� �Ǳ⶧��
			if(updatedVO.getCouponNum() != null)
			{inappVO.setCouponNum(updatedVO.getCouponNum());}
			if(updatedVO.getDescriptionText() != null && !"".equals(updatedVO.getDescriptionText() ))
			{inappVO.setDescriptionText(updatedVO.getDescriptionText());}
			if(updatedVO.getDistrGb() != null && !"".equals(updatedVO.getDistrGb() ))
			{inappVO.setDistrGb(updatedVO.getDistrGb());}
			if(updatedVO.getInappOrgFile() != null && !"".equals(updatedVO.getInappOrgFile() ))
			{inappVO.setInappOrgFile(updatedVO.getInappOrgFile());}
			if(updatedVO.getInappSaveFile() != null && !"".equals(updatedVO.getInappSaveFile() ))
			{inappVO.setInappSaveFile(updatedVO.getInappSaveFile());}
			if(updatedVO.getIconOrgFile() != null && !"".equals(updatedVO.getIconOrgFile() ))
			{inappVO.setIconOrgFile(updatedVO.getIconOrgFile());}
			if(updatedVO.getIconSaveFile() != null && !"".equals(updatedVO.getIconSaveFile() ))
			{inappVO.setIconSaveFile(updatedVO.getIconSaveFile());}
			if(updatedVO.getLimitDt() != null )
			{inappVO.setLimitDt(updatedVO.getLimitDt());}
			if(updatedVO.getLimitGb() != null && !"".equals(updatedVO.getLimitGb() ))
			{inappVO.setLimitGb(updatedVO.getLimitGb());}
			if(updatedVO.getMemDownAmt() != null && !"".equals(updatedVO.getMemDownAmt() ))
			{inappVO.setMemDownAmt(updatedVO.getMemDownAmt());}
			if(updatedVO.getMemDownCnt() != null && !"".equals(updatedVO.getMemDownCnt() ))
			{inappVO.setMemDownCnt(updatedVO.getMemDownCnt());}
			if(updatedVO.getMemDownEndDt() != null)
			{inappVO.setMemDownEndDt(updatedVO.getMemDownEndDt());}
			if(updatedVO.getMemDownGb() != null && !"".equals(updatedVO.getMemDownGb() ))
			{inappVO.setMemDownGb(updatedVO.getMemDownGb());}
			if(updatedVO.getMemDownStartDt() != null)
			{inappVO.setMemDownStartDt(updatedVO.getMemDownStartDt());}
			if(updatedVO.getNonmemDownAmt() != null && !"".equals(updatedVO.getNonmemDownAmt() ))
			{inappVO.setNonmemDownAmt(updatedVO.getNonmemDownAmt());}
			if(updatedVO.getNonmemDownCnt() != null && !"".equals(updatedVO.getNonmemDownCnt() ))
			{inappVO.setNonmemDownCnt(updatedVO.getNonmemDownCnt());}
			if(updatedVO.getNonmemDownEndDt() != null )
			{inappVO.setNonmemDownEndDt(updatedVO.getNonmemDownEndDt());}
			if(updatedVO.getNonmemDownGb() != null && !"".equals(updatedVO.getNonmemDownGb() ))
			{inappVO.setNonmemDownGb(updatedVO.getNonmemDownGb());}
			if(updatedVO.getNonmemDownStarDt() != null )
			{inappVO.setNonmemDownStarDt(updatedVO.getNonmemDownStarDt());}
	
			if(updatedVO.getRegDt() != null)
			{inappVO.setRegDt(updatedVO.getRegDt());}
	
			if(updatedVO.getRegUserGb() != null && !"".equals(updatedVO.getRegUserGb() ))
			{inappVO.setRegUserGb(updatedVO.getRegUserGb());}
			if(updatedVO.getRegUserId() != null && !"".equals(updatedVO.getRegUserId() ))
			{inappVO.setRegUserId(updatedVO.getRegUserId());}
			if(updatedVO.getRegUserSeq() != null && !"".equals(updatedVO.getRegUserSeq() ))
			{inappVO.setRegUserSeq(updatedVO.getRegUserSeq());}
	
			if(updatedVO.getUseAvailDt() != null )
			{inappVO.setUseAvailDt(updatedVO.getUseAvailDt());}
			if(updatedVO.getUseDisableDt() != null )
			{inappVO.setUseDisableDt(updatedVO.getUseDisableDt());}
	
			if(updatedVO.getUseGb() != null && !"".equals(updatedVO.getUseGb() ))
			{inappVO.setUseGb(updatedVO.getUseGb());}
			if(updatedVO.getVerNum() != null && !"".equals(updatedVO.getVerNum() ))
			{inappVO.setVerNum(updatedVO.getVerNum());}
			if(updatedVO.getUseUserGb() != null && !"".equals(updatedVO.getUseUserGb() ))
				inappVO.setUseUserGb(updatedVO.getUseUserGb());
			if(updatedVO.getScreenType() != null && !"".equals(updatedVO.getScreenType() ))
				inappVO.setScreenType(updatedVO.getScreenType());

			//20180403 : lsy - date update
			if(updatedVO.getChgDt() != null) {
				inappVO.setChgDt(updatedVO.getChgDt());
			}
			if(updatedVO.getDistributeReqDt() != null) {
				inappVO.setDistributeReqDt(updatedVO.getDistributeReqDt());
			}
			if(updatedVO.getChgContentsDt() != null) {
				inappVO.setChgContentsDt(updatedVO.getChgContentsDt());
			}
			if(updatedVO.getDistributeCompletDt() != null) {
				inappVO.setDistributeCompletDt(updatedVO.getDistributeCompletDt());
			}
			if(updatedVO.getDistributeAcceptId() != null) {
				inappVO.setDistributeAcceptId(updatedVO.getDistributeAcceptId());
			}
			if(updatedVO.getDistributeRequestId() != null) {
				inappVO.setDistributeRequestId(updatedVO.getDistributeRequestId());
			}
			//20180403 : lsy - date update - end
			
			//20180403 : lsy - filesize update logic 누락
			if(updatedVO.getInappSize() != null) {
				inappVO.setInappSize(updatedVO.getInappSize());
			}
			//20180403 : lsy - filesize update logic 누락 - end
	
			session.update(inappVO);
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}
	
	@Override
	public Object[] getListInAppForRelatedApp( String storeBundleId ) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Object[] result = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(InappVO.class);
	
			System.out.println("storeBundleId = " + storeBundleId ) ;
			cr.add(Restrictions.eq( "storeBundleId", storeBundleId ));
	
			List<InappVO> list = cr.list();
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
	
			result = list.toArray();
	
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
	public List<InappSubVO> selectInAppSubList(int inAppSeq) {
		// TODO Auto-generated method stub
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappSubVO.class);
			
			cr.add(
				Restrictions.eq("inappSeq", inAppSeq)
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
	public int insertInAppSubInfo(InappSubVO inAppSubVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.save(inAppSubVO);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inAppSubVO.getInappsubSeq();
	}

	@Override
	public void deleteInAppSubInfo(InappSubVO inAppSubVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
	
			String hql = "DELETE FROM InappSubVO T " + 
		             "WHERE T.inappSeq = :inappSeq ";
			
			Query query = session.createQuery(hql);
			query.setParameter("inappSeq", inAppSubVO.getInappSeq());
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
	public boolean checkInappNameIfExist(String InappName, String storeBundleId, String verNum) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;

		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappVO.class);
			
			cr.add(
				Restrictions.and(
					Restrictions.eq("storeBundleId", storeBundleId),
					Restrictions.eq("inappName", InappName),
					Restrictions.eq("verNum", verNum)
				)
			);
			list = cr.list();
	
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		if(list.size() == 0) {
			return true ;
		}else {
			return false;
		}
	}

	@Override
	public List<InappVO> getListInappIsAvailableByStoreBundleId( String storeBundleId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<InappVO> list = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(InappVO.class);
			
			cr.add(
				Restrictions.and(
					Restrictions.eq("storeBundleId", storeBundleId),
					Restrictions.eq("limitGb","2"),
					Restrictions.eq("completGb", "1"),
					Restrictions.eq("useGb", "1")
				)
			);
	
			list = cr.list();
	
			for( InappVO inappVO : list) {
				Hibernate.initialize(inappVO.getInappSubVO());
			}
	
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
	public int insertInAppMetaInfo(InappMetaVO inappMetaVO) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.save(inappMetaVO);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inappMetaVO.getInappMetaSeq();
	}

	@Override
	public InappMetaVO findByCustomInfoForMetaVO(String DBName, int intValue) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappMetaVO inappMetaVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappMetaVO.class);
			Criterion user = Restrictions.eq(DBName, intValue);
			
			cr.add(user);
			inappMetaVO = (InappMetaVO) cr.uniqueResult();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inappMetaVO;

	}

	@Override
	public InappMetaVO findByCustomInfoForMetaVO(String DBName, String value) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappMetaVO inappMetaVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(InappMetaVO.class);
			Criterion user = Restrictions.eq(DBName, value);
			
			cr.add(user);
			inappMetaVO = (InappMetaVO) cr.uniqueResult();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return inappMetaVO;
	}


	@Override
	public void deleteInAppInfo( String storeBundleId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		InappMetaVO inappMetaVO = null;
		
		try {
			tx = session.beginTransaction();
			
			String hql = "DELETE FROM InappVO T " + 
		             "WHERE T.storeBundleId = :storeBundleId ";
			
			Query query = session.createQuery(hql);
			query.setParameter("storeBundleId", storeBundleId);
			query.executeUpdate();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	//20180327 - lsy : develop version managemenet
	@Override
	public String selectCompletGbBySeq( String DBName, int inapp_seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, inapp_seq);
	}
	
	@Override
	public String getSameInappSeq ( String DBName, String inapp_name, String store_bundle_id ) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("inapp_name", inapp_name);
		paramMap.put("store_bundle_id", store_bundle_id);
		list.add(paramMap);
		
		return sqlSession.selectOne(DBName, list);
	}

	@Override
	public void insertInappHistory( String DBName, String inapp_seq ){
		sqlSession.insert(DBName, inapp_seq);
	}

	@Override
	public void deleteInAppBySeq( String DBName, String inapp_seq ){
		sqlSession.delete(DBName, inapp_seq);
	}

  	@Override
	public List<InappVO> selectForHistory(String DBName, String inapp_name, String store_bundle_id) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("inapp_name", inapp_name);
		paramMap.put("store_bundle_id", store_bundle_id);
		list.add(paramMap);
		
		return sqlSession.selectList(DBName, list);
	}

	@Override
	public InappVO selectInappBySeq( String DBName, int inapp_seq){
		return sqlSession.selectOne(DBName, inapp_seq);
	}
	//20180327 - lsy : develop version managemenet - end
	
	//20180619 - lsy : when app request(Library), load inapp info
	@Override
	public int getInAppCntByBundleId(String DBName, String store_bundle_id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, store_bundle_id);
	}
	
	@Override
	public List<InappVO> getInAppByBundleId(String DBName, String store_bundle_id) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, store_bundle_id);
	}

}