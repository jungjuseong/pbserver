package com.clbee.pbcms.dao;

import com.clbee.pbcms.vo.LogVO;
import java.util.Date;
import java.util.List;

public abstract interface LogDao
{
  public abstract int insertLogInfo(LogVO paramLogVO);
  
  public abstract Object selectLogInfo(String paramString1, String paramString2, Integer paramInteger, String paramString3, String paramString4);
  
  public abstract List<LogVO> selectLogList(int paramInt, String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2);
  
  public abstract int selectLogListCount(String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2);
  
  public abstract LogVO selectLogInfo(int paramInt);
  
  public abstract List<LogVO> selectLogAllList(String paramString1, String paramString2, String paramString3, Date paramDate1, Date paramDate2);
}
