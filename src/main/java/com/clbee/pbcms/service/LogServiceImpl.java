package com.clbee.pbcms.service;

import com.clbee.pbcms.dao.LogDao;
import com.clbee.pbcms.vo.LogList;
import com.clbee.pbcms.vo.LogVO;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl
  implements LogService
{
  @Autowired
  LogDao logDao;
  
  public int insertLogInfo(LogVO logVO)
  {
    return this.logDao.insertLogInfo(logVO);
  }
  
  public Object selectLogInfo(String storeBundleId, String inappSeq, Integer userSeq, String pageGb, String dataGb)
  {
    return this.logDao.selectLogInfo(storeBundleId, inappSeq, userSeq, pageGb, dataGb);
  }
  
  public LogList selectLogList(int currentPage, String storeBundleId, String searchValue, String searchType, String startDate, String endDate)
  {
    LogList list = null;
    
    int pageSize = 10;
    
    int maxResult = 10;
    int totalCount = 0;
    int startNo = 0;
    Date startDateF = null;
    Date endDateF = null;
    
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      if ((startDate != null) && (!"".equals(startDate)) && (endDate != null) && (!"".equals(endDate)))
      {
        startDateF = transFormat.parse(startDate);
        endDateF = transFormat.parse(endDate);
        long tempEnd = endDateF.getTime();
        endDateF.setTime(tempEnd + 86399000L);
      }
      System.out.println(" startDate = " + startDateF);
      System.out.println(" endDate = " + endDateF);
      
      System.out.println(" startDate = " + startDate);
      System.out.println(" endDate = " + endDate);
      if ((searchType != null) && (!"".equals(searchType))) {
        switch (Integer.parseInt(searchType))
        {
        case 1: 
          searchType = "inappVO.inappName";
          break;
        case 2: 
          searchType = "memberVO.userId";
        }
      }
      System.out.println(" startDate = " + startDateF);
      
      System.out.println(" endDate = " + endDateF);
      System.out.println("searchValue = " + searchValue);
      System.out.println("searchType = " + searchType);
      
      totalCount = this.logDao.selectLogListCount(storeBundleId, searchValue, searchType, startDateF, endDateF);
      System.out.println("totalCount = " + totalCount);
      list = new LogList(pageSize, totalCount, currentPage, maxResult);
      
      startNo = (currentPage - 1) * maxResult;
      
      List<LogVO> vo = this.logDao.selectLogList(startNo, storeBundleId, searchValue, searchType, startDateF, endDateF);
      
      list.setList(vo);
    }
    catch (Exception e)
    {
      System.out.println("������");
      e.printStackTrace();
    }
    return list;
  }
  
  public LogVO selectLogInfo(int logSeq)
  {
    return this.logDao.selectLogInfo(logSeq);
  }
  
  public List<LogVO> selectLogAllList(String storeBundleId, String searchValue, String searchType, String startDate, String endDate)
  {
    Date startDateF = null;
    Date endDateF = null;
    
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      if ((startDate != null) && (!"".equals(startDate)) && (endDate != null) && (!"".equals(endDate)))
      {
        startDateF = transFormat.parse(startDate);
        endDateF = transFormat.parse(endDate);
        
        long tempEnd = endDateF.getTime();
        endDateF.setTime(tempEnd + 86399000L);
      }
      if ((searchType != null) && (!"".equals(searchType))) {
        switch (Integer.parseInt(searchType))
        {
        case 1: 
          searchType = "inappVO.inappName";
          break;
        case 2: 
          searchType = "memberVO.userId";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this.logDao.selectLogAllList(storeBundleId, searchValue, searchType, startDateF, endDateF);
  }
}
