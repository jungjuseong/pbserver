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
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.util.AbstractDAO;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppHistoryVO;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.DownloadList;
import com.clbee.pbcms.vo.MemberVO;

@Repository
public class AppDaoImpl extends AbstractDAO implements AppDao {

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
	public void updateAppSubInfo(AppSubVO updatedVO, int subNum) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			AppSubVO subVO = (AppSubVO)session.get(AppSubVO.class, subNum);
			
			
			if( updatedVO.getAppSeq() != null )
				subVO.setAppSeq(updatedVO.getAppSeq());
			if( updatedVO.getDepartmentSeq() != null)
				subVO.setDepartmentSeq(updatedVO.getDepartmentSeq());
			if( updatedVO.getUserSeq() != null)
				subVO.setUserSeq(updatedVO.getUserSeq());
				
			session.update(subVO);
	
			tx.commit();
		}catch (Exception e) {
			if (tx!=null) tx.rollback();
		     e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	@Override
	public void updateAppInfo( AppVO updatedVO, int appNum )throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		
		
		System.out.println("updatedVO.getAppContentsGb() = " + updatedVO.getAppContentsGb() );
		try {
			AppVO appVO = (AppVO)session.get(AppVO.class, appNum);
			//appVO.setAppVO( updatedVO );
			if(updatedVO.getAppContentsAmt() != null && !"".equals(updatedVO.getAppContentsAmt() ))
			{appVO.setAppContentsAmt(updatedVO.getAppContentsAmt());}
			if(updatedVO.getAppContentsGb() != null && !"".equals(updatedVO.getAppContentsGb() ))
			{appVO.setAppContentsGb(updatedVO.getAppContentsGb());}
			if(updatedVO.getAppName() != null && !"".equals(updatedVO.getAppName() ))
			{appVO.setAppName(updatedVO.getAppName());}
			if(updatedVO.getApp_resultCode() != null && !"".equals(updatedVO.getApp_resultCode() ))
			{appVO.setApp_resultCode(updatedVO.getApp_resultCode());}
			if(updatedVO.getAppSize() != null && !"".equals(updatedVO.getAppSize() ))
			{appVO.setAppSize(updatedVO.getAppSize());}
			if(updatedVO.getChgText() != null && !"".equals(updatedVO.getChgText() ))
			{appVO.setChgText(updatedVO.getChgText());}
			if(updatedVO.getChgUserGb() != null && !"".equals(updatedVO.getChgUserGb() ))
			{appVO.setChgUserGb(updatedVO.getChgUserGb());}
			if(updatedVO.getChgUserId() != null && !"".equals(updatedVO.getChgUserId() ))
			{appVO.setChgUserId(updatedVO.getChgUserId());}
			if(updatedVO.getChgUserSeq() != null && !"".equals(updatedVO.getChgUserSeq() ))
			{appVO.setChgUserSeq(updatedVO.getChgUserSeq());}
			if(updatedVO.getCompletGb() != null && !"".equals(updatedVO.getCompletGb() ))
			{appVO.setCompletGb(updatedVO.getCompletGb());}
			if(updatedVO.getCouponGb() != null && !"".equals(updatedVO.getCouponGb() ))
			{appVO.setCouponGb(updatedVO.getCouponGb());}
	
			//������ ""�� �����..
			if(updatedVO.getCouponNum() != null)
			{appVO.setCouponNum(updatedVO.getCouponNum());}
			if(updatedVO.getDescriptionText() != null && !"".equals(updatedVO.getDescriptionText() ))
			{appVO.setDescriptionText(updatedVO.getDescriptionText());}
			if(updatedVO.getDistrGb() != null && !"".equals(updatedVO.getDistrGb() ))
			{appVO.setDistrGb(updatedVO.getDistrGb());}
			if(updatedVO.getFileName() != null && !"".equals(updatedVO.getFileName() ))
			{appVO.setFileName(updatedVO.getFileName());}
			if(updatedVO.getIconOrgFile() != null && !"".equals(updatedVO.getIconOrgFile() ))
			{appVO.setIconOrgFile(updatedVO.getIconOrgFile());}
			if(updatedVO.getIconSaveFile() != null && !"".equals(updatedVO.getIconSaveFile() ))
			{appVO.setIconSaveFile(updatedVO.getIconSaveFile());}
			if(updatedVO.getLimitDt() != null )
			{appVO.setLimitDt(updatedVO.getLimitDt());}
			if(updatedVO.getLimitGb() != null && !"".equals(updatedVO.getLimitGb() ))
			{appVO.setLimitGb(updatedVO.getLimitGb());}
			if(updatedVO.getMemDownAmt() != null && !"".equals(updatedVO.getMemDownAmt() ))
			{appVO.setMemDownAmt(updatedVO.getMemDownAmt());}
	
			if(updatedVO.getProvisionGb() != null && !"".equals(updatedVO.getProvisionGb()))
				appVO.setProvisionGb(updatedVO.getProvisionGb());
			if(updatedVO.getInstallGb() != null && !"".equals(updatedVO.getInstallGb()))
				appVO.setInstallGb(updatedVO.getInstallGb());
			if(updatedVO.getVersionCode() != null && !"".equals(updatedVO.getVersionCode()))
				appVO.setVersionCode(updatedVO.getVersionCode());
			if(updatedVO.getMemDownCnt() != null && !"".equals(updatedVO.getMemDownCnt() ))
			{appVO.setMemDownCnt(updatedVO.getMemDownCnt());}
			if(updatedVO.getMemDownEndDt() != null)
			{appVO.setMemDownEndDt(updatedVO.getMemDownEndDt());}
			if(updatedVO.getMemDownGb() != null && !"".equals(updatedVO.getMemDownGb() ))
			{appVO.setMemDownGb(updatedVO.getMemDownGb());}
			if(updatedVO.getMemDownStartDt() != null)
			{appVO.setMemDownStartDt(updatedVO.getMemDownStartDt());}
			if(updatedVO.getNonmemDownAmt() != null && !"".equals(updatedVO.getNonmemDownAmt() ))
			{appVO.setNonmemDownAmt(updatedVO.getNonmemDownAmt());}
			if(updatedVO.getNonmemDownCnt() != null && !"".equals(updatedVO.getNonmemDownCnt() ))
			{appVO.setNonmemDownCnt(updatedVO.getNonmemDownCnt());}
			if(updatedVO.getNonmemDownEndDt() != null )
			{appVO.setNonmemDownEndDt(updatedVO.getNonmemDownEndDt());}
			if(updatedVO.getNonmemDownGb() != null && !"".equals(updatedVO.getNonmemDownGb() ))
			{appVO.setNonmemDownGb(updatedVO.getNonmemDownGb());}
			if(updatedVO.getNonmemDownStarDt() != null )
			{appVO.setNonmemDownStarDt(updatedVO.getNonmemDownStarDt());}
			if(updatedVO.getOstype() != null && !"".equals(updatedVO.getOstype() ))
			{appVO.setOstype(updatedVO.getOstype());}
			if(updatedVO.getRegDt() != null)
			{appVO.setRegDt(updatedVO.getRegDt());}
			if(updatedVO.getRegGb() != null && !"".equals(updatedVO.getRegGb() ))
			{appVO.setRegGb(updatedVO.getRegGb());}
			if(updatedVO.getRegUserGb() != null && !"".equals(updatedVO.getRegUserGb() ))
			{appVO.setRegUserGb(updatedVO.getRegUserGb());}
			if(updatedVO.getRegUserId() != null && !"".equals(updatedVO.getRegUserId() ))
			{appVO.setRegUserId(updatedVO.getRegUserId());}
			if(updatedVO.getRegUserSeq() != null && !"".equals(updatedVO.getRegUserSeq() ))
			{appVO.setRegUserSeq(updatedVO.getRegUserSeq());}
			if(updatedVO.getTemplateName() != null && !"".equals(updatedVO.getTemplateName() ))
			{appVO.setTemplateName(updatedVO.getTemplateName());}
			if(updatedVO.getTemplateSeq() != null && !"".equals(updatedVO.getTemplateSeq() ))
			{appVO.setTemplateSeq(updatedVO.getTemplateSeq());}
			if(updatedVO.getUseAvailDt() != null )
			{appVO.setUseAvailDt(updatedVO.getUseAvailDt());}
			if(updatedVO.getUseDisableDt() != null )
			{appVO.setUseDisableDt(updatedVO.getUseDisableDt());}
	
			if(updatedVO.getUseGb() != null && !"".equals(updatedVO.getUseGb() ))
			{appVO.setUseGb(updatedVO.getUseGb());}
			if(updatedVO.getVerNum() != null && !"".equals(updatedVO.getVerNum() ))
			{appVO.setVerNum(updatedVO.getVerNum());}
			if(updatedVO.getUseUserGb() != null && !"".equals(updatedVO.getUseUserGb())) {
				appVO.setUseUserGb(updatedVO.getUseUserGb());}
			if(updatedVO.getLoginGb() != null &&  !"".equals(updatedVO.getLoginGb()))
				appVO.setLoginGb(updatedVO.getLoginGb());
			if(updatedVO.getLoginTime() != null && !"".equals(updatedVO.getLoginTime()))
				appVO.setLoginTime(updatedVO.getLoginTime());
			if(updatedVO.getLogoutTime() != null && !"".equals(updatedVO.getLogoutTime()))
				appVO.setLogoutTime(updatedVO.getLogoutTime());

			//20180403 : lsy - date update
			if(updatedVO.getChgDt() != null) {
				appVO.setChgDt(updatedVO.getChgDt());
			}
			if(updatedVO.getDistributeReqDt() != null) {
				appVO.setDistributeReqDt(updatedVO.getDistributeReqDt());
			}
			if(updatedVO.getChgContentsDt() != null) {
				appVO.setChgContentsDt(updatedVO.getChgContentsDt());
			}
			if(updatedVO.getDistributeCompletDt() != null) {
				appVO.setDistributeCompletDt(updatedVO.getDistributeCompletDt());
			}
			if(updatedVO.getDistributeAcceptId() != null) {
				appVO.setDistributeAcceptId(updatedVO.getDistributeAcceptId());
			}
			if(updatedVO.getDistributeRequestId() != null) {
				appVO.setDistributeRequestId(updatedVO.getDistributeRequestId());
			}
			//20180403 : lsy - date update - end
			
			session.update(appVO);
			tx.commit();
		}catch (Exception e) {
			if( tx!= null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();			
		}
	}

	@Override
	public int insertAppInfo( AppVO app ){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(app);
			tx.commit();
		}catch(Exception e) {
			if( tx!= null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();	
		}
		return app.getAppSeq();
		/*session.createCriteria(AppVO.class);*/
	}

	@Override
	public int insertAppHistoryInfo( AppHistoryVO app ){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.save(app);
			tx.commit();
		}catch(Exception e) {
			if( tx!= null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();	
		}

		return app.getAppSeq();
	}

	@Override
	public List<AppVO> selectList(int startNo, String user_id ){
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM AppVO E "
					+ "WHERE E.user_id = :id ORDER BY E.last_update DESC ")
					.setFirstResult(startNo).setMaxResults(10)
					.setParameter("id", user_id);
			list = query.list();
			tx.commit();
		}catch(Exception e) {
			if( tx!= null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();	
		}

		return list;
	}

	@Override
	public AppVO selectByStoreId( String storeBundleId ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(AppVO.class);
			cr.add(Restrictions.eq("storeBundleId", storeBundleId));
			list = cr.list();
			tx.commit();
		}catch(Exception e) {
			if( tx!= null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();	
		}

		
		if(list.size() == 0) return null;
		else return list.get(0);
	}

	@Override
	public AppVO selectById( int appNum ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		AppVO appVO = null;
		try {
			tx= session.beginTransaction();
	
			Criteria cr = session.createCriteria(AppVO.class);
			cr.add(Restrictions.eq("appSeq", appNum));
			appVO = (AppVO) cr.uniqueResult();
	
			tx.commit();
		}catch(Exception e) {
			if( tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

		return appVO;
	}
	
	@Override
	public List<AppVO> selectByUserId( String user_id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list =null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(AppVO.class);
			cr.add(Restrictions.eq("user_id", user_id));
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
	public List<AppVO> selectByUserIdAndIdenty( String user_id, String bundle_identy) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		List<AppVO> list = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(AppVO.class);
	
			Criterion username = Restrictions.eq("user_id", user_id);
			Criterion password = Restrictions.eq("store_bundle_id", bundle_identy);
	
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
	public int getListCount( String user_id ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number number = null;
		
		try {
			tx = session.beginTransaction();
			// �ش� ���̵� User�� �۵�� ����
			number = ((Number) session.createCriteria(AppVO.class).add(Restrictions.eq("user_id", user_id)).
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
	public void updateAppByIdentifier(AppVO updatedVO,
			String store_bundle_id) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			/*session.createCriteria(arg0)*/
	
			String hql = "UPDATE AppVO set ver_num = :version,"
					+"app_resultCode = :code,"
					+"chg_dt = :update"
					+" WHERE store_bundle_id = :identifier";
	
			System.out.println("updatedVO.getVerNum() = " + updatedVO.getVerNum());
			System.out.println("updatedVO.getApp_resultCode() = " + updatedVO.getApp_resultCode());
			System.out.println("store_bundle_id = " + store_bundle_id);
	
			Query query = session.createQuery(hql)
					.setParameter("version", updatedVO.getVerNum())
					.setParameter("code", updatedVO.getApp_resultCode())
					.setParameter("update", updatedVO.getChgDt())
					.setParameter("identifier", store_bundle_id);
	
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
	public Object selectByBundleId(Entity param) {
		return list("selectAppVOByBundleIdAndOstype",param);
	}

	@Override
	public int getSeqAfterInsertAppInfo(AppVO app) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction t = null;
		List<BigInteger> appList = null;
		int appSeq =0;
		try {
			t=session.beginTransaction();
	
			System.out.println("app.getUseUserGb() = " + app.getUseUserGb());
			String sql = "CALL SP_INSERT_APP (:regGb, :appName, :appContentsAmt, :appContentsGb, :fileName ";
			sql+=", :ostype, :verNum, :storeBundleId, :provisionGb, :descriptionText ";
			sql+=", :iconOrgFile, :iconSaveFile, :templateName, :templateSeq, :useGb";
			sql+=", :completGb, :memDownGb, :couponGb, :nonmemDownGb, :installGb, :versionCode, :limitGb";
			sql+=", :regUserSeq, :regUserId, :regUserGb ";
			sql+=", :chgUserSeq, :chgUserId, :chgUserGb ";
			sql+=", :useUserGb, :loginTime, :logoutTime, :loginGb )";
	
			Query callStoredProcedure_MYSQL = session.createSQLQuery(sql);
		
			callStoredProcedure_MYSQL.setString("regGb", app.getRegGb());
			callStoredProcedure_MYSQL.setString("appName", app.getAppName());
			callStoredProcedure_MYSQL.setString("appContentsAmt", app.getAppContentsAmt());
			callStoredProcedure_MYSQL.setString("appContentsGb", app.getAppContentsGb());
			callStoredProcedure_MYSQL.setString("fileName", app.getFileName());
			
			callStoredProcedure_MYSQL.setString("ostype", app.getOstype());
			callStoredProcedure_MYSQL.setString("verNum", app.getVerNum());
			callStoredProcedure_MYSQL.setString("storeBundleId", app.getStoreBundleId());
			callStoredProcedure_MYSQL.setString("provisionGb", app.getProvisionGb());
			callStoredProcedure_MYSQL.setString("descriptionText", app.getDescriptionText());
			
			callStoredProcedure_MYSQL.setString("iconOrgFile", app.getIconOrgFile());
			callStoredProcedure_MYSQL.setString("iconSaveFile", app.getIconSaveFile());
			callStoredProcedure_MYSQL.setString("templateName", app.getTemplateName());
			//callStoredProcedure_MYSQL.setInteger("templateSeq", app.getTemplateSeq());
			callStoredProcedure_MYSQL.setParameter("templateSeq", app.getTemplateSeq());
			callStoredProcedure_MYSQL.setString("useGb", app.getUseGb());
			
			callStoredProcedure_MYSQL.setString("completGb", app.getCompletGb());
			callStoredProcedure_MYSQL.setString("memDownGb", app.getMemDownGb());
			callStoredProcedure_MYSQL.setString("couponGb", app.getCouponGb());
			callStoredProcedure_MYSQL.setString("nonmemDownGb", app.getNonmemDownGb());
			callStoredProcedure_MYSQL.setString("installGb", app.getInstallGb());
			callStoredProcedure_MYSQL.setString("versionCode", app.getVersionCode());
			callStoredProcedure_MYSQL.setString("limitGb", app.getLimitGb());
			
			callStoredProcedure_MYSQL.setInteger("regUserSeq", app.getRegUserSeq());
			callStoredProcedure_MYSQL.setString("regUserId", app.getRegUserId());
			callStoredProcedure_MYSQL.setString("regUserGb", app.getRegUserGb());
			
			callStoredProcedure_MYSQL.setInteger("chgUserSeq", app.getRegUserSeq());
			callStoredProcedure_MYSQL.setString("chgUserId", app.getRegUserId());
			callStoredProcedure_MYSQL.setString("chgUserGb", app.getRegUserGb());
			callStoredProcedure_MYSQL.setString("useUserGb", app.getUseUserGb());
		    callStoredProcedure_MYSQL.setString("loginTime", app.getLoginTime());
		    callStoredProcedure_MYSQL.setString("logoutTime", app.getLogoutTime());
		    callStoredProcedure_MYSQL.setString("loginGb", app.getLoginGb());
		    
			/* callStoredProcedure_MSSQL.list() will execute stored procedure and return the value */
			appList = callStoredProcedure_MYSQL.list();
			System.out.println("@@@@@@@@@@@@"+appList.get(0).intValue());
			
			appSeq = appList.get(0).intValue();
			t.commit();
		}catch (Exception e) {
			if(t != null) t.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return appSeq;
	}
  	@Override
	public int getListCount(AppList appList, MemberVO memberVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number number = null;
		
		try {
			tx = session.beginTransaction();
			Criteria appCr = session.createCriteria(AppVO.class, "appVO"); //.createCriteria(AppVO.class);
			appCr.createAlias("regMemberVO", "memberVO");
			if(memberVO.getUserGb()!=null&&!"5".equals(memberVO.getUserGb())){
				appCr.add(Restrictions.or(Restrictions.and(Restrictions.eq("memberVO.companyGb", "2"), Restrictions.eq("memberVO.userSeq", memberVO.getUserSeq())),Restrictions.and(Restrictions.eq("memberVO.companyGb", "1"), Restrictions.eq("memberVO.companySeq", memberVO.getCompanySeq()))));			
			}
			if(appList.getIsAvailable() !=null && "true".equals(appList.getIsAvailable())) {
				appCr.add(Restrictions.or(
						Restrictions.eq("limitGb","1"),
						Restrictions.eq("useGb","1"))
						);
			}

			if(appList.getSearchValue()!=null&&appList.getSearchValue().length()>0){
				if(appList.getSearchType()!=null&&appList.getSearchType().length()>0){
					appCr.add(Restrictions.and(Restrictions.like(appList.getSearchType(), "%"+appList.getSearchValue()+"%")));
				}else if((appList.getSearchType()==null||appList.getSearchType().length()==0)||"".equals(appList.getSearchType())){
					appCr.add(Restrictions.and(Restrictions.or(Restrictions.like("appName", "%"+appList.getSearchValue()+"%"), Restrictions.like("ostype", "%"+appList.getSearchValue()+"%"))));
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
	public List<AppVO> selectList(AppList appList, MemberVO memberVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		
		
		try {
			tx = session.beginTransaction();
	
			Criteria appCr = session.createCriteria(AppVO.class, "appVO"); //.createCriteria(AppVO.class);
			appCr.createAlias("regMemberVO", "memberVO");
			if(memberVO.getUserGb()!=null&&!"255".equals(memberVO.getUserGb())){
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
			}
			if(appList != null){
				if(appList.getIsAvailable() !=null && "true".equals(appList.getIsAvailable())) {
					appCr.add(Restrictions.or(
							Restrictions.eq("limitGb","1"),
							Restrictions.eq("useGb","1"))
					);
				}
			}
			if( appList != null) {	/* appList�� Null�ΰ��� �ƹ��� ���� ����  */
				if(appList.getSearchValue()!=null&&appList.getSearchValue().length()>0){
					if(appList.getSearchType()!=null&&appList.getSearchType().length()>0){
						appCr.add(Restrictions.and(Restrictions.like(appList.getSearchType(), "%"+appList.getSearchValue()+"%")));
					}else if((appList.getSearchType()==null||appList.getSearchType().length()==0)||"".equals(appList.getSearchType())){
						System.out.println("appList.getSearchType()====="+appList.getSearchType());
						System.out.println("appList.getSearchValue()====="+appList.getSearchValue());
						appCr.add(Restrictions.and(Restrictions.or(Restrictions.like("appName", "%"+appList.getSearchValue()+"%"), Restrictions.like("ostype", "%"+appList.getSearchValue()+"%"))));
					}
				}
				appCr.setFirstResult(appList.getStartNo());
				appCr.setMaxResults(appList.getMaxResult());
				appCr.addOrder(Order.desc("chgDt"));
				appCr.addOrder(Order.desc("useGb"));
			}
			list =  (List<AppVO>)appCr.list();
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
	public AppVO selectForUpdate(AppVO appVO, MemberVO memberVO) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		AppVO vo = null;
		
		try {
			tx= session.beginTransaction();
	
			Criteria appCr = session.createCriteria(AppVO.class, "appVO"); //.createCriteria(AppVO.class);
			appCr.createAlias("regMemberVO", "memberVO");
			appCr.add(Restrictions.eq("appVO.appSeq", appVO.getAppSeq()));
			if(memberVO.getUserGb()!=null&&!"255".equals(memberVO.getUserGb())){
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
			}
			vo = (AppVO) appCr.uniqueResult();
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
	public int getListCount(DownloadList downloadList, MemberVO memberVO) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		Number number = null;
	
		try {
			tx = session.beginTransaction();		
	
			Query query = session.createQuery("FROM AppVO E "
					//+ "WHERE E.user_id = :id ORDER BY E.last_update DESC ")
					+ " WHERE 1=1 "
					//+ AndVal
					+ " ORDER BY E.regDt DESC ");
					//.setFirstResult(templateList.getStartNo())
					//.setMaxResults(10);
					//.setParameter("id", user_id);
			
					
			list = query.list();
			System.out.println("End in Select List");
			number = list.size();	
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
	public List<?> selectList(DownloadList downloadList, MemberVO memberVO) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		
		try {
			tx = session.beginTransaction();		
	
			Query query = session.createQuery("FROM AppVO E "
					//+ "WHERE E.user_id = :id ORDER BY E.last_update DESC ")
					+ " WHERE 1=1 "
					//+ AndVal
					+ " ORDER BY E.regDt DESC ")
					//.setFirstResult(startNo)
					.setFirstResult(downloadList.getStartNo())
					.setMaxResults(10);
					//.setParameter("id", user_id);
					
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
	public List selectList(Entity param) {
		//	20180523 : lsy - 그룹권한 추가
		if(param.get("menuFunction") != null && !param.get("menuFunction").equals("")) {
			if(param.get("menuFunction").toString().indexOf("131") != -1 && param.get("menuFunction").toString().indexOf("131") >= 0) {
				param.setValue("check131", "Y");
			}else {
				param.setValue("check131", "N");
			}
		}else {
			param.setValue("check131", "N");
		}
		
		if(param.get("menuFunction") != null && !param.get("menuFunction").equals("")) {
			if(param.get("menuFunction").toString().indexOf("132") != -1 && param.get("menuFunction").toString().indexOf("132") >= 0) {
				param.setValue("check132", "Y");
			}else {
				param.setValue("check132", "N");
			}
		}else {
			param.setValue("check132", "N");
		}
		
		if(param.get("menuFunction") != null && !param.get("menuFunction").equals("")) {
			if(param.get("menuFunction").toString().indexOf("134") != -1 && param.get("menuFunction").toString().indexOf("134") >= 0) {
				param.setValue("check134", "Y");
			}else {
				param.setValue("check134", "N");
			}
		}else {
			param.setValue("check134", "N");
		}
		//	20180523 : lsy - 그룹권한 추가 - end		
		return list("downSelectList",param);
	}

	@Override
	public List getSelectListCount(Entity param) {
		//	20180523 : lsy - 그룹권한 추가
		if(param.get("menuFunction") != null && !param.get("menuFunction").equals("")) {
			if(param.get("menuFunction").toString().indexOf("131") != -1 && param.get("menuFunction").toString().indexOf("131") >= 0) {
				param.setValue("check131", "Y");
			}else {
				param.setValue("check131", "N");
			}
		}else {
			param.setValue("check131", "N");
		}
		
		if(param.get("menuFunction") != null && !param.get("menuFunction").equals("")) {
			if(param.get("menuFunction").toString().indexOf("132") != -1 && param.get("menuFunction").toString().indexOf("132") >= 0) {
				param.setValue("check132", "Y");
			}else {
				param.setValue("check132", "N");
			}
		}else {
			param.setValue("check132", "N");
		}
		
		if(param.get("menuFunction") != null && !param.get("menuFunction").equals("")) {
			if(param.get("menuFunction").toString().indexOf("134") != -1 && param.get("menuFunction").toString().indexOf("134") >= 0) {
				param.setValue("check134", "Y");
			}else {
				param.setValue("check134", "N");
			}
		}else {
			param.setValue("check134", "N");
		}
		//	20180523 : lsy - 그룹권한 추가 - end
		
		return list("downSelectListCount",param);
	}

	@Override
	public List getSelectCouponList(Entity param) {
		return list("downSelectCouponList",param);
	}

	@Override
	public String getSelectTodayDate() {
		return (String) selectOne("downTodayDate", "");
	}

	@Override
	public List getCountOfIdenticalCouponNumForAll( Entity param) {
		return list("selectIfAnyIdenticalCouponNumForAll", param);
	}

	@Override
	public List getRowIsCompletedByBundleId( Entity param) {
		return list("getRowIsCompletedByBundleId", param);
	}

	@Override
	public List getListNotComplte( MemberVO memberVO ) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(AppVO.class);
			cr.createAlias("regMemberVO", "memberVO");
			cr.add(
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
			cr.add(Restrictions.eq("useGb", "1"));
			cr.add(Restrictions.eq("limitGb", "2"));
			cr.addOrder(Order.desc("chgDt"));
	
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
	public void deleteAppInfo(int appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(AppVO.class);
			cr.add(
				Restrictions.eq("appSeq", appSeq)
			);
			AppVO appVO = (AppVO)cr.uniqueResult();
			session.delete(appVO);	
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public void deleteAppHistoryInfo(int appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(AppHistoryVO.class);
			cr.add(
				Restrictions.eq("appSeq", appSeq)
			);
			AppVO appVO = (AppVO)cr.uniqueResult();
			session.delete(appVO);	
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}
	
	@Override
	public List<AppSubVO> selectAppSubList(int appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(AppSubVO.class);
			cr.add(
				Restrictions.eq("appSeq", appSeq)
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
	public int insertAppSubInfo( AppSubVO appSubVO ) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.save(appSubVO);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return appSubVO.getAppSeq();
	}

	@Override
	public void deleteAppSubInfo( AppSubVO appSubVO ) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try {
			tx = session.beginTransaction();
	
			String hql = "DELETE FROM AppSubVO T " + 
		             "WHERE T.appSeq = :appSeq ";
			
			Query query = session.createQuery(hql);
			query.setParameter("appSeq", appSubVO.getAppSeq());
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
	public List<AppVO> getNotPermmitList(int companySeq, Integer[] useA, String searchValue, String searchType) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();	
	
			Criteria cr = session.createCriteria(AppVO.class);
			Criteria alias = cr.createAlias("regMemberVO", "memberVO");
			
			alias.add(
				Restrictions.eq("memberVO.companySeq", companySeq)
			);
			
			if(useA != null && useA.length > 0) {
				cr.add(					
					Restrictions.not(
						Restrictions.in("appSeq", useA)
					)
				);
			}
	
			if(searchValue != null && searchType != null) {
				switch(Integer.parseInt(searchType)) {
					case 1:
						cr.add(Restrictions.like("", "%"+searchValue+"%"));
						break;
					case 2: 
						cr.add(Restrictions.like("", "%"+searchValue+"%"));
						break;
					case 3: 
						cr.add(Restrictions.like("", "%"+searchValue+"%"));
						break;
					case 4: 
						cr.add(Restrictions.like("", "%"+searchValue+"%"));
						break;
				}
			}
	
			cr.addOrder(Order.desc("regDt"));
	
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
	public List<AppVO> getPermitList(int companySeq, Integer[] useA) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List list = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(AppVO.class);
			Criteria alias = cr.createAlias("regMemberVO", "memberVO");
	
			alias.add(
				Restrictions.eq("memberVO.companySeq", companySeq)
			);
	
			if(useA != null && useA.length > 0) {
				cr.add(					
					Restrictions.in("appSeq", useA)
				);
			}else {
				return null;
			}
	
			cr.addOrder(Order.desc("regDt"));
	
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
	public int checkIfAvailableAppByBundleId( int userSeq, String ostype, String storeBundleId ) {
		//TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		
		try {
			tx = session.beginTransaction();
	
			Criteria cr = session.createCriteria(AppVO.class,"appVO");
			cr.createAlias("appVO.appSubVO", "Sub", JoinType.LEFT_OUTER_JOIN);
	
			if("4".equals(ostype)){
				System.out.println("i'm Android@@@@@@@@@@@@@@@@@@@@@@");
				cr.add(
					Restrictions.or(
						Restrictions.and(
							Restrictions.eq("storeBundleId", storeBundleId),
							Restrictions.eq("useUserGb", "1"),
							Restrictions.eq("ostype", "4")
						),
						Restrictions.and(
							Restrictions.eq("storeBundleId", storeBundleId),
							Restrictions.eq("useUserGb", "2"),
							Restrictions.eq("ostype", "4"),
							Restrictions.eq("Sub.userSeq", userSeq)
						)
					)
				);
			}else{
				System.out.println("i'm iOS@@@@@@@@@@@@@@@@@@@@@@");
				cr.add(
						Restrictions.or(
							Restrictions.and(
								Restrictions.eq("storeBundleId", storeBundleId),
								Restrictions.eq("useUserGb", "1"),
								Restrictions.or(
										Restrictions.eq("ostype", "1"),
										Restrictions.eq("ostype", "2"),
										Restrictions.eq("ostype", "3")										
								)
							),
							Restrictions.and(
								Restrictions.eq("storeBundleId", storeBundleId),
								Restrictions.eq("useUserGb", "2"),
								Restrictions.or(
										Restrictions.eq("ostype", "1"),
										Restrictions.eq("ostype", "2"),
										Restrictions.eq("ostype", "3")										
								),
								Restrictions.eq("Sub.userSeq", userSeq)
							)
						)
					);
			}
	
			list = cr.list();
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		if(list.size() > 0) {
			return 5000;
		}else if(list.size() == 0){
			return 5001;
		}else {
			return 9999;
		}
	}

	@Override
	public List<AppVO> getListIsAvailableByCompanySeq(int companySeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<AppVO> list = null;
		
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(AppVO.class,"appVO");
			cr.createAlias("appVO.regMemberVO", "memberVO", JoinType.LEFT_OUTER_JOIN);
	
			cr.add(
				Restrictions.and(
					Restrictions.eq("memberVO.companySeq", companySeq),
					Restrictions.eq("useGb","1"),
					Restrictions.eq("completGb", "1"),
					Restrictions.eq("limitGb", "2"),
					Restrictions.eq("app_resultCode", "1")
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
		return list;
	}

	@Override
	public AppVO selectByBundleIdAndOstype(String ostype, String storeBundleId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		AppVO appVO  = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(AppVO.class);
			
			cr.add(
				Restrictions.eq("storeBundleId", storeBundleId)
			);
			
			if(!"4".equals(ostype)) {
				cr.add(
					Restrictions.or(
						Restrictions.eq("ostype", "1"),
						Restrictions.eq("ostype", "2"),
						Restrictions.eq("ostype", "3")
					)
				);
			}else {
				cr.add(
					Restrictions.eq("ostype", "4")
				);
			}
			cr.addOrder( Order.desc("verNum") );
			//cr.setFirstResult(1);
			cr.setFirstResult(0);
			cr.setMaxResults(1);
			appVO = (AppVO)cr.uniqueResult();
			
			Hibernate.initialize(appVO.getAppSubVO());
			tx.commit();

		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
				
		return appVO;
	}

	@Override
	public void deleteAppSubAppSeqInfo(int appSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(AppSubVO.class);
			cr.add(
				Restrictions.eq("appSeq", appSeq)
			);
			AppVO appVO = (AppVO)cr.uniqueResult();
			session.delete(appVO);	
			
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
	public List<AppVO> selectAppForHistory(String DBName, String store_bundle_id) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("store_bundle_id", store_bundle_id);
		list.add(paramMap);
		
		return sqlSession.selectList(DBName, list);
	}

	@Override
	public AppVO selectAppBySeq( String DBName, int app_seq){
		return sqlSession.selectOne(DBName, app_seq);
	}
	//20180327 - lsy : develop version managemenet - end
	@Override
	public AppVO selectBeforAppInfoBySeqForNewVersion(String DBName, int app_seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, app_seq);
	}
	@Override
	public int insertNewVersionAppInfo(String DBName, AppVO newVersionAppInfo) {
		// TODO Auto-generated method stub
		sqlSession.insert(DBName, newVersionAppInfo);
		return newVersionAppInfo.getAppSeq();
	}
	@Override
	public AppVO selectAppByAppSeqForMakeJobTicket(String DBName, int app_seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, app_seq);
	}
	@Override
	public int duplicateVerCheck(String DBName, Entity param) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, param);
	}
	@Override
	public String selectAppVersionMax(String DBName, int app_seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, app_seq);
	}
}