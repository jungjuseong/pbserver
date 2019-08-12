package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InAppList;

//20180419 : lsy - distribute process develop

public interface DistributeService {
	AppList selectAppList( Entity param );
	InAppList selectInappList( Entity param );
	ContentList selectContentsList( Entity param );
	
	DistributeRestoreVO selectDistributeReqInfo( DistributeRestoreVO distributeRestoreVo );
	void insertDistributeRestore( DistributeRestoreVO distributeRestoreVO );
	void updateRestoreStatus( DistributeRestoreVO distributeRestoreVO );
	
	List<DistributeRestoreVO> selectForRestore( DistributeRestoreVO distributeRestoreVO );

	int getTotalCount( Entity param );
}