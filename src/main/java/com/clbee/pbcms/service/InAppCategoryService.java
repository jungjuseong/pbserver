package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.InappcategoryVO;

public interface InAppCategoryService {

	int insertInAppInfo(InappcategoryVO inCateVo);
	List<InappcategoryVO> selectInAppList(InappcategoryVO inCateVo);
	void updateInAppInfo(InappcategoryVO inCateVo);
	void deleteInAppInfo(InappcategoryVO inCateVo);
	Object[] selectInAppList2(InappcategoryVO inCateVo);
	InappcategoryVO findByCustomInfo( String DBName, int intValue);
	InappcategoryVO findByCustomInfo( String DBName, String Value);
	List<InappcategoryVO> getListInAppCategoryforOneDepth( String DBName, String storeBundleId);
	List<InappcategoryVO> getListInAppCategory( String DBName, String storeBundleId);
	int categoryIsDuplicated ( String appSeq, String categoryName );
}
