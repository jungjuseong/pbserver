package com.clbee.pbcms.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.InappcategoryVO;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.TemplateList;
import com.clbee.pbcms.vo.TemplateSubVO;
import com.clbee.pbcms.vo.TemplateVO;

@Repository
public class TemplateDaoImpl implements TemplateDao {

	@Autowired
	private SessionFactory sessionFactory;	

	@Override
	public void insertTempInfo(TemplateVO templateVO) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.save(templateVO);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
	}

	@Override
	public void updateTempInfo( TemplateVO updatedVO, int temp_id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			/*session.createCriteria(arg0)*/
			
			TemplateVO templateVO = (TemplateVO)session.get(TemplateVO.class, temp_id);
			templateVO.setTemplateVO( updatedVO );
			session.update(templateVO);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public TemplateVO selectByTempId(int temp_id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		TemplateVO templateVO = null;

		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(TemplateVO.class);
			cr.add(Restrictions.eq("templateSeq", temp_id));
			templateVO = (TemplateVO) cr.uniqueResult();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}

		return templateVO;
	}





	@Override
	public List<TemplateVO> selectByAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List tempList = null;
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(TemplateVO.class);
			tempList = cr.list();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		return tempList;
	}

	@Override
	public int getListCount(TemplateList templateList, MemberVO memberVO, String shField, String shKeyword, String status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Number number = null;
		try {
			tx = session.beginTransaction();		
			
			String AndVal = "";
			if(!"".equals(shField) && !"".equals(shKeyword)){
				AndVal = " AND E."+shField+" like '%"+shKeyword+"%'";
			}else{
				AndVal = " AND (E.templateName like '%"+shKeyword+"%' OR E.descriptionText like '%"+shKeyword+"%' )";
			}
			
			if(status.equals("y")) {
				AndVal += " AND E.useGb = 1";
			}
			
			Query query = session.createQuery("FROM TemplateVO E "
					//+ "WHERE E.user_id = :id ORDER BY E.last_update DESC ")
					+ " WHERE 1=1 "
					+ AndVal
					+ " ORDER BY E.useGb ASC, E.limitGb DESC, E.chgDt DESC, E.regDt DESC ");
					//.setFirstResult(templateList.getStartNo())
					//.setMaxResults(10);
					//.setParameter("id", user_id);
					
			List<TemplateVO> list = query.list();
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
	
	public List<TemplateVO> selectList(TemplateList templateList, MemberVO memberVO, String shField, String shKeyword, String status) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<TemplateVO> list = null;
		try {
			tx = session.beginTransaction();		
			
			String AndVal = "";
			if(!"".equals(shField) && !"".equals(shKeyword)){
				AndVal = " AND E."+shField+" like '%"+shKeyword+"%'";
			}else{
				AndVal = " AND (E.templateName like '%"+shKeyword+"%' OR E.descriptionText like '%"+shKeyword+"%' )";
			}
			
			if(status.equals("y")) {
				AndVal += " AND E.useGb = 1";
			}
			
			//System.out.println("startNo = " + startNo);
			Query query = session.createQuery("FROM TemplateVO E "
					//+ "WHERE E.user_id = :id ORDER BY E.last_update DESC ")
					+ " WHERE 1=1 "
					+ AndVal
					+ " ORDER BY E.useGb ASC, E.limitGb DESC, E.chgDt DESC, E.regDt DESC ")
					//.setFirstResult(startNo)
					.setFirstResult(templateList.getStartNo())
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
	public int insertTemplate(TemplateVO tempVo) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int templateSeq = 0;
		try {
			tx = session.beginTransaction();
	
			//Query callStoredProcedure_MYSQL = session.crateSQLQuery("CALL SP_INSERT_TEMPLATE (:templateName, :ostypeGb, :verNum, :templateTypeGb, :appContentsAmt, :appContentsGb, :descriptionText, :useGb, :useUserGb, :completGb, :limitGb, :regUserSeq, :regUserId, :regUserGb, :regDt, :chgUserSeq, :chgUserId, :chgUserGb, :chgDt)");
			Query callStoredProcedure_MYSQL = session.createSQLQuery("CALL SP_INSERT_TEMPLATE (:templateName, :ostypeGb, :verNum, :templateTypeGb, :appContentsAmt, :appContentsGb, :descriptionText, :uploadOrgFile, :uploadSaveFile, :useGb, :useUserGb, :completGb, :limitGb, :regUserSeq, :regUserId, :regUserGb, :chgUserSeq, :chgUserId, :chgUserGb)");
		
			callStoredProcedure_MYSQL.setString("templateName", tempVo.getTemplateName());
			callStoredProcedure_MYSQL.setString("ostypeGb", tempVo.getOstypeGb());
			callStoredProcedure_MYSQL.setString("verNum", tempVo.getVerNum());
			callStoredProcedure_MYSQL.setString("templateTypeGb", tempVo.getTemplateTypeGb());
			callStoredProcedure_MYSQL.setString("appContentsAmt", tempVo.getAppContentsAmt());
			callStoredProcedure_MYSQL.setString("appContentsGb", tempVo.getAppContentsGb());
			callStoredProcedure_MYSQL.setString("descriptionText", tempVo.getDescriptionText());		
			callStoredProcedure_MYSQL.setString("uploadOrgFile", tempVo.getUploadOrgFile());
			callStoredProcedure_MYSQL.setString("uploadSaveFile", tempVo.getUploadSaveFile());		
			callStoredProcedure_MYSQL.setString("useGb", tempVo.getUseGb());
			callStoredProcedure_MYSQL.setString("useUserGb", tempVo.getUseUserGb());
			callStoredProcedure_MYSQL.setString("completGb", tempVo.getCompletGb());
			callStoredProcedure_MYSQL.setString("limitGb", tempVo.getLimitGb());
			callStoredProcedure_MYSQL.setLong("regUserSeq", tempVo.getRegUserSeq());
			callStoredProcedure_MYSQL.setString("regUserId", tempVo.getRegUserId());
			callStoredProcedure_MYSQL.setString("regUserGb", tempVo.getRegUserGb());
			//callStoredProcedure_MYSQL.setDate("regDt", tempVo.getRegDt());
			callStoredProcedure_MYSQL.setLong("chgUserSeq", tempVo.getChgUserSeq());
			callStoredProcedure_MYSQL.setString("chgUserId", tempVo.getChgUserId());
			callStoredProcedure_MYSQL.setString("chgUserGb", tempVo.getChgUserGb());
			//callStoredProcedure_MYSQL.setDate("chgDt", tempVo.getChgDt());				
	
			/* callStoredProcedure_MSSQL.list() will execute stored procedure and return the value */
			List<BigInteger> templateList = callStoredProcedure_MYSQL.list();
			//ystem.out.println("@@@@@@@@@@@@"+companyList.get(0).intValue());
			
			templateSeq = templateList.get(0).intValue();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
		
		return templateSeq;
	}

	@Override
	public List<TemplateVO> selectInAppList(TemplateVO tempVo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<TemplateVO> list = null;
		
		try {
			tx = session.beginTransaction();	
			Query query = session.createQuery("FROM TemplateVO T "
					+ " ORDER BY T.regDt DESC ");
					//.setFirstResult(startNo).setMaxResults(10)
					//.setParameter("id", user_id);				
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
	public List selectUserList(MemberVO memVo, String useS) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List result = null;
		
		try {
			tx = session.beginTransaction();	
			
			if(!"".endsWith(useS)){
				useS = useS.replace('|', ' ');
				useS = " AND T.userSeq Not In ("+useS+") ";
			}
			
			Query query = session.createQuery("FROM MemberVO T "
					+ " WHERE T.userGb = :userGb "
					+ useS
					+ " ORDER BY T.regDt DESC ")
					//.setFirstResult(startNo).setMaxResults(10)
					.setParameter("userGb", memVo.getUserGb());	
					//.setParameter("userSeq", useS);

			result = query.list();
			
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
	public List selectUserList3(MemberVO memVo, String useS) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List result = null;
		try {
			tx = session.beginTransaction();	
			
			if(!"".endsWith(useS)){
				useS = useS.replace('|', ' ');
				useS = " AND T.userSeq In ("+useS+") ";
			}
			
			Query query = session.createQuery("FROM MemberVO T "
					+ " WHERE T.userGb = :userGb "
					+ useS
					+ " ORDER BY T.regDt DESC ")
					//.setFirstResult(startNo).setMaxResults(10)
					.setParameter("userGb", memVo.getUserGb());	
					//.setParameter("userSeq", useS);
			result = query.list();
			
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
	public Object[] selectUserList2(MemberVO memVo, String useS) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Object[] result = null;

		try {
			tx = session.beginTransaction();
			
			if(!"".endsWith(useS)){
				useS = useS.replace('|', ' ');
				useS = " AND T.userSeq Not In ("+useS+") ";
			}	
			
			Query query = session.createQuery(" FROM MemberVO T "
					+ " WHERE 1=1 AND T.userGb = :userGb AND T.userId like :userId "
					+ useS
					+ " ORDER BY T.regDt DESC ")
					//.setFirstResult(startNo).setMaxResults(10)
					.setParameter("userGb", "127")
					.setParameter("userId","%"+memVo.getUserId()+"%");
					//.setParameter("lastName",memVo.getUserId())	
			
			result = query.list().toArray();
			
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
	public TemplateVO selectView(int thisSeq) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		TemplateVO templateVO = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(TemplateVO.class);
			cr.add(Restrictions.eq("templateSeq", thisSeq));
			templateVO = (TemplateVO) cr.uniqueResult();
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}	
		
		return templateVO;
	}

	@Override
	public void updateTemplate(TemplateVO tempVo) {
			// TODO Auto-generated method stub
			
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			
			try {
				tx = session.beginTransaction();
				/*session.createCriteria(arg0)*/
				
				
				String hql = "UPDATE TemplateVO set "
						+"template_name = :templateName,"
						+"ostype_gb = :ostypeGb,"
						+"ver_num = :verNum,"
						+"template_type_gb = :templateTypeGb,"
						+"app_contents_amt = :appContentsAmt,"
						+"app_contents_gb = :appContentsGb,"
						+"description_text = :descriptionText,"
						+"upload_org_file = :uploadOrgFile,"
						+"upload_save_file = :uploadSaveFile,"
						+"use_gb = :useGb,"
						+"use_user_gb = :useUserGb,"
						+"use_avail_dt = :useAvailDt,"
						+"use_disable_dt = :useDisableDt,"
						+"complet_gb = :completGb,"
						+"limit_dt = :limitDt,"
						+"chg_user_seq = :chgUserSeq,"
						+"chg_user_id = :chgUserId,"
						+"chg_user_gb = :chgUserGb"
						+" WHERE template_seq = :templateSeq";
				
				Query query = session.createQuery(hql)
						.setParameter("templateName", tempVo.getTemplateName())
						.setParameter("ostypeGb", tempVo.getOstypeGb())
						.setParameter("verNum", tempVo.getVerNum())
						.setParameter("templateTypeGb", tempVo.getTemplateTypeGb())
						.setParameter("appContentsAmt", tempVo.getAppContentsAmt())
						.setParameter("appContentsGb", tempVo.getAppContentsGb())
						.setParameter("descriptionText", tempVo.getDescriptionText())
						.setParameter("uploadOrgFile", tempVo.getUploadOrgFile())
						.setParameter("uploadSaveFile", tempVo.getUploadSaveFile())
						.setParameter("useGb", tempVo.getUseGb())
						.setParameter("useUserGb", tempVo.getUseUserGb())
						.setParameter("useAvailDt", tempVo.getUseAvailDt())
						.setParameter("useDisableDt", tempVo.getUseDisableDt())
						.setParameter("completGb", tempVo.getCompletGb())
						.setParameter("limitDt", tempVo.getLimitDt())
						.setParameter("chgUserSeq", tempVo.getChgUserSeq())
						.setParameter("chgUserId", tempVo.getChgUserId())
						.setParameter("chgUserGb", tempVo.getChgUserGb())
						.setParameter("templateSeq", tempVo.getTemplateSeq());

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
	public void insertTemplateSub(TemplateSubVO tempSubVo) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.save(tempSubVo);
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public List<TemplateSubVO> selectUserList2(int thisSeq) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<TemplateSubVO> list = null;
		
		try {
			tx = session.beginTransaction();	
	
			Query query = session.createQuery("FROM TemplateSubVO E "
					+ " WHERE 1=1 AND template_seq = :templateSeq ")
					.setParameter("templateSeq", thisSeq);	
					
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
	public void deleteTemplateSub(TemplateSubVO tempSubVo) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int result = 0;
		try {
			tx = session.beginTransaction();
	
			String hql = "DELETE FROM TemplateSubVO T " + 
		             "WHERE T.templateSeq = :templateSeq ";
			
			Query query = session.createQuery(hql);
			query.setParameter("templateSeq", tempSubVo.getTemplateSeq());
			result = query.executeUpdate();
	
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}

	@Override
	public void updateTemplateFile(TemplateVO tempVo) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			/*session.createCriteria(arg0)*/		
	
			String hql = "UPDATE TemplateVO set "
					+"upload_org_file = :uploadOrgFile,"
					+"upload_save_file = :uploadSaveFile"
					+" WHERE template_seq = :templateSeq";
	
			Query query = session.createQuery(hql)
					.setParameter("uploadOrgFile", null)
					.setParameter("uploadSaveFile", null)
					.setParameter("templateSeq", tempVo.getTemplateSeq());		
	
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
	public int getListCount(TemplateVO temVO, MemberVO memberVO, TemplateList templateList) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<TemplateVO> list = null;
		try {
			tx = session.beginTransaction();	
			
			
			Criteria cr = session.createCriteria(TemplateVO.class);
	
			if(temVO !=null)
			cr.add(Restrictions.eq("templateTypeGb", temVO.getTemplateTypeGb()));
			cr.add(Restrictions.eq("useGb", "1"));
			cr.add(Restrictions.eq("limitGb", "2"));
	
			cr.addOrder(Order.desc("regDt"));
	
			if(temVO !=null){
				switch(Integer.parseInt(temVO.getOstypeGb())){
					case 1 :
						cr.add(Restrictions.or(
								Restrictions.eq("ostypeGb", "1"),
								Restrictions.eq("ostypeGb", "2"),
								Restrictions.eq("ostypeGb", "3")
								)
						);
						break;
					case 2 :
						cr.add(Restrictions.or(
								Restrictions.eq("ostypeGb", "1"),
								Restrictions.eq("ostypeGb", "2")
							)
						);
						
						break;
					case 3 :
						cr.add(Restrictions.or(
								Restrictions.eq("ostypeGb", "1"),
								Restrictions.eq("ostypeGb", "3")
							)
						);
						break;
					case 4: 
						cr.add(Restrictions.eq("ostypeGb", "4"));
						break;
				}
	
				if("1".equals(temVO.getTemplateTypeGb())){
					switch(Integer.parseInt(temVO.getAppContentsGb())){
						case 1 : 
							int hell = Integer.parseInt(temVO.getAppContentsAmt());
							cr.add(
									Restrictions.or(
											Restrictions.and(
												Restrictions.or(
														Restrictions.eq("appContentsGb", "1"),
														Restrictions.eq("appContentsGb", "2")
												),
											Restrictions.sqlRestriction("app_contents_amt >=" +Integer.parseInt(temVO.getAppContentsAmt()))),
											Restrictions.eq("appContentsGb", "3")
										)
							);
							break;
						case 2 : 
							int hello = Integer.parseInt(temVO.getAppContentsAmt());
							cr.add(
									Restrictions.or(
										Restrictions.and(
											Restrictions.or(
													Restrictions.eq("appContentsGb", "1"),
													Restrictions.eq("appContentsGb", "2")
											),
										Restrictions.sqlRestriction("app_contents_amt >=" +Integer.parseInt(temVO.getAppContentsAmt()))),
										Restrictions.eq("appContentsGb", "3")
									)
							);
							break;
						case 3 : 
							cr.add(Restrictions.eq("appContentsGb", "3"));
							break;
					}
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
	public List selectList(TemplateVO temVO, MemberVO memberVO, TemplateList templateList, String flagForAll) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<TemplateVO> list = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(TemplateVO.class);
	
			
			 
			
			/*cr.createAlias( , alias)*/
			cr.createAlias("templateSubVO", "tempSubVO", JoinType.LEFT_OUTER_JOIN);
			
			cr.add(Restrictions.eq("useGb", "1"));
			cr.add(Restrictions.eq("limitGb", "2"));
			cr.add(Restrictions.or(
						Restrictions.and(
							Restrictions.eq("useUserGb", "2"),
							Restrictions.eq("tempSubVO.userSeq", memberVO.getUserSeq())
							),
						Restrictions.eq("useUserGb", "1")
					)
				);
					
					
			cr.addOrder(Order.desc("regDt"));
	
			if("All".equals(flagForAll)){
				
			}else if("Paging".equals(flagForAll)){
				cr.add(Restrictions.eq("templateTypeGb", temVO.getTemplateTypeGb()));
				switch(Integer.parseInt(temVO.getOstypeGb())){
				case 1 :
					cr.add(Restrictions.or(
							Restrictions.eq("ostypeGb", "1"),
							Restrictions.eq("ostypeGb", "2"),
							Restrictions.eq("ostypeGb", "3")
							)
					);
					break;
				case 2 :
					cr.add(Restrictions.or(
							Restrictions.eq("ostypeGb", "1"),
							Restrictions.eq("ostypeGb", "2")
						)
					);
					
					break;
				case 3 :
					cr.add(Restrictions.or(
							Restrictions.eq("ostypeGb", "1"),
							Restrictions.eq("ostypeGb", "3")
							)
					);
					break;
				case 4: 
					cr.add(Restrictions.eq("ostypeGb", "4"));
					break;
				}
		
				if("1".equals(temVO.getTemplateTypeGb())){
					switch(Integer.parseInt(temVO.getAppContentsGb())){
						case 1 : 
							int hell = Integer.parseInt(temVO.getAppContentsAmt());
							cr.add(
									Restrictions.or(
											Restrictions.and(
												Restrictions.or(
														Restrictions.eq("appContentsGb", "1"),
														Restrictions.eq("appContentsGb", "2")
												),
											Restrictions.sqlRestriction("app_contents_amt >=" +Integer.parseInt(temVO.getAppContentsAmt()))),
											Restrictions.eq("appContentsGb", "3")
										)
							);
							break;
						case 2 : 
							int hello = Integer.parseInt(temVO.getAppContentsAmt());
							cr.add(
									Restrictions.or(
											Restrictions.and(
												Restrictions.or(
														Restrictions.eq("appContentsGb", "1"),
														Restrictions.eq("appContentsGb", "2")
												),
											Restrictions.sqlRestriction("app_contents_amt >=" +Integer.parseInt(temVO.getAppContentsAmt()))),
											Restrictions.eq("appContentsGb", "3")
										)
							);
							break;
						case 3 : 
							cr.add(Restrictions.eq("appContentsGb", "3"));
							break;
					}
				}
				
				cr.setFirstResult(templateList.getStartNo());
				cr.setMaxResults(templateList.getMaxResult());
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

	//20180219 : lsy - deleteTemplate
	@Override
	public void deleteTemplate(int thisSeq) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			Criteria cr = session.createCriteria(TemplateVO.class);
			cr.add(
				Restrictions.eq("templateSeq", thisSeq)
			);
			TemplateVO templateVO = (TemplateVO)cr.uniqueResult();
			session.delete(templateVO);	
			
			tx.commit();
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();	
		}finally {
			session.close();
		}
	}
}
