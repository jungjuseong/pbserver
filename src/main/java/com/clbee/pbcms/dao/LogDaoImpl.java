package com.clbee.pbcms.dao;

import com.clbee.pbcms.Json.SpaceObject;
import com.clbee.pbcms.vo.LogVO;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl implements LogDao
{
  @Autowired
  private SessionFactory sessionFactory;
  
  public LogDaoImpl() {}
  
  public int insertLogInfo(LogVO logVO)
  {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try
    {
      tx = session.beginTransaction();
      
      if ("".equals(logVO.getInappSeq())) {
        logVO.setInappSeq(null);
      }
      
      session.save(logVO);
      
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    
    return logVO.getLogSeq().intValue();
  }
  


  public Object selectLogInfo(String storeBundleId, String inappSeq, Integer userSeq, String pageGb, String dataGb)
  {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    List<LogVO> result = null;
    try {
      tx = session.beginTransaction();
      
      Criteria cr = session.createCriteria(LogVO.class);
      
      cr.add(
        Restrictions.and(
        Restrictions.eq("storeBundleId", storeBundleId), 
        Restrictions.eq("regUserSeq", userSeq)));
      


      if ((inappSeq != null) && (!"".equals(inappSeq)))
        cr.add(Restrictions.eq("inappSeq", inappSeq));
      if ((pageGb != null) && (!"".equals(pageGb)))
        cr.add(Restrictions.eq("pageGb", pageGb));
      if ((dataGb != null) && (!"".equals(dataGb))) {
        cr.add(Restrictions.eq("dataGb", dataGb));
      }
      
      cr.addOrder(Order.desc("regDt"));
      

      result = cr.list();
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    
    if ((result != null) && (result.size() > 0))
      return result.get(0);
    return new SpaceObject();
  }
  


  public List<LogVO> selectLogList(int startNo, String storeBundleId, String searchValue, String searchType, Date startDate, Date endDate)
  {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    List<LogVO> result = null;
    String dbKey;
    try
    {
      dbKey = "inappSeq";
      Integer.parseInt(storeBundleId);
    }
    catch (NumberFormatException e) {
      dbKey = "storeBundleId";
    }
    

    System.out.println("dbKey =" + dbKey);
    System.out.println("storeBundleId =" + storeBundleId);
    
    try
    {
      tx = session.beginTransaction();
      
      Criteria cr = session.createCriteria(LogVO.class, "logVO");
      cr.createAlias("logVO.regMemberVO", "memberVO", JoinType.LEFT_OUTER_JOIN);
      cr.createAlias("logVO.inappVO", "inappVO", JoinType.LEFT_OUTER_JOIN);
      
      cr.setFirstResult(startNo).add(
        Restrictions.and(
        Restrictions.eq("dataGb", "5"), 
        Restrictions.eq(dbKey, storeBundleId)));
      
      if ((searchValue != null) && (!"".equals(searchValue))) {
        System.out.println("hello i'm in searchValue");
        System.out.println("searchType = " + searchType);
        System.out.println("searchValue = " + searchValue);
        cr.add(
          Restrictions.like(searchType, "%" + searchValue + "%"));
      }
      

      if ((startDate != null) && (!"".equals(startDate))) {
        System.out.println("hello i'm in startDate");
        cr.add(
          Restrictions.and(
          Restrictions.ge("regDt", startDate), 
          Restrictions.le("regDt", endDate)));
      }
      




      cr.setMaxResults(10).addOrder(Order.desc("regDt"));
      
      result = cr.list();
      
      System.out.println("cr.list().size() =  " + cr.list().size());
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return result;
  }
  

  public int selectLogListCount(String storeBundleId, String searchValue, String searchType, Date startDate, Date endDate)
  {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    List<LogVO> result = null;
    
    String dbKey = "";
    
    try
    {
      dbKey = "inappSeq";
      Integer.parseInt(storeBundleId);
    }
    catch (NumberFormatException e) {
      System.out.println("i'm occured Exception");
      dbKey = "storeBundleId";
    }
    

    System.out.println("dbKey =" + dbKey);
    System.out.println("storeBundleId =" + storeBundleId);
    try
    {
      tx = session.beginTransaction();
      
      Criteria cr = session.createCriteria(LogVO.class, "logVO");
      cr.createAlias("logVO.regMemberVO", "memberVO", JoinType.LEFT_OUTER_JOIN);
      cr.createAlias("logVO.inappVO", "inappVO", JoinType.LEFT_OUTER_JOIN);
      

      cr.add(
        Restrictions.and(
        Restrictions.eq("dataGb", "5"), 
        Restrictions.eq(dbKey, storeBundleId)));
      


      if ((searchValue != null) && (!"".equals(searchValue))) {
        cr.add(
          Restrictions.and(new Criterion[] {
          Restrictions.eq(searchType, searchValue) }));
      }
      


      if ((startDate != null) && (!"".equals(startDate))) {
        cr.add(
          Restrictions.and(
          Restrictions.ge("regDt", startDate), 
          Restrictions.le("regDt", endDate)));
      }
      


      result = cr.list();
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    
    return result.size();
  }
  

  public LogVO selectLogInfo(int logSeq)
  {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    LogVO result = null;
    try {
      tx = session.beginTransaction();
      
      Criteria cr = session.createCriteria(LogVO.class);
      
      cr.add(
        Restrictions.eq("logSeq", Integer.valueOf(logSeq)));
      
      result = (LogVO)cr.uniqueResult();
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return result;
  }
  

  public List<LogVO> selectLogAllList(String storeBundleId, String searchValue, String searchType, Date startDate, Date endDate)
  {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    List<LogVO> result = null;
    

    String dbKey = "";
    
    try
    {
      dbKey = "inappSeq";
      Integer.parseInt(storeBundleId);
    }
    catch (NumberFormatException e) {
      System.out.println("i'm occured Exception");
      dbKey = "storeBundleId";
    }
    

    System.out.println("dbKey =" + dbKey);
    System.out.println("storeBundleId =" + storeBundleId);
    try
    {
      tx = session.beginTransaction();
      
      Criteria cr = session.createCriteria(LogVO.class);
      cr.createAlias("regMemberVO", "memberVO", JoinType.LEFT_OUTER_JOIN);
      cr.createAlias("inappVO", "inappVO", JoinType.LEFT_OUTER_JOIN);
      
      cr.add(
        Restrictions.and(
        Restrictions.eq("dataGb", "5"), 
        Restrictions.eq(dbKey, storeBundleId)));
      



      if ((searchValue != null) && (!"".equals(searchValue))) {
        System.out.println("hello i'm in searchValue");
        System.out.println("searchType = " + searchType);
        System.out.println("searchValue = " + searchValue);
        cr.add(
          Restrictions.like(searchType, "%" + searchValue + "%"));
      }
      

      if ((startDate != null) && (!"".equals(startDate))) {
        System.out.println("hello i'm in startDate");
        cr.add(
          Restrictions.and(
          Restrictions.ge("regDt", startDate), 
          Restrictions.le("regDt", endDate)));
      }
      


      cr.addOrder(Order.desc("regDt"));
      
      result = cr.list();
      
      System.out.println("cr.list().size() =  " + cr.list().size());
      tx.commit();
    } catch (Exception e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return result;
  }
}