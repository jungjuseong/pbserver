package com.clbee.pbcms.dao;

import java.util.List;

import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InappVO;

public interface DistributeDao {
	List<AppVO> selectAppList( String DBName, Entity param );
	List<InappVO> selectInappList( String DBName, Entity param );
	List<ContentVO> selectContentsList( String DBName, Entity param );
	
	int totalCount( String DBName, Entity param );
	DistributeRestoreVO selectDistributeReqInfo( String DBName, DistributeRestoreVO distributeRestoreVo );
	void insertDistributeRestore( String DBName, DistributeRestoreVO distributeRestoreVO );
	void updateRestoreStatus( String DBName, DistributeRestoreVO distributeRestoreVO );

	List<DistributeRestoreVO> selectForRestore( String DBName, DistributeRestoreVO distributeRestoreVO );
}