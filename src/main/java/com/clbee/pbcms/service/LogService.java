package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.LogList;
import com.clbee.pbcms.vo.LogVO;

public interface LogService {
	public abstract int insertLogInfo(LogVO paramLogVO);
	  
	public abstract Object selectLogInfo(String paramString1, String paramString2, Integer paramInteger, String paramString3, String paramString4);
	  
	public abstract LogList selectLogList(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
	  
	public abstract LogVO selectLogInfo(int paramInt);
	  
	public abstract List<LogVO> selectLogAllList(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
}
