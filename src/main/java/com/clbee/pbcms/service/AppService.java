package com.clbee.pbcms.service;

import java.util.HashMap;
import java.util.List;

import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppHistoryVO;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.DownloadList;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;

public interface AppService {
	int insertAppInfo( AppVO app );
	int insertAppHistoryInfo( AppHistoryVO app );
	void updateAppInfo( AppVO updatedVO, int appNum) throws Exception;
	void updateAppSubInfo ( AppSubVO updatedVO, int subNum );
	AppVO selectById( int appNum );
	AppVO selectByStoreId( String storeBundleId );
	AppList selectList(int currentPage, String user_id);
	int getListCount( String user_id );
	List<AppVO> selectByUserId( String user_id );
	void updateAppByIdentifier ( AppVO updatedVO, String store_bundle_id);
	List<AppVO> selectByUserIdAndIdenty( String user_id, String bundle_identy);
	Object selectByBundleId( Entity param);
	int getSeqAfterInsertAppInfo(AppVO app );
	AppList selectList(MemberVO memberVO, AppList appList);
	AppVO selectForUpdate(AppVO appVO, MemberVO memberVO);
	DownloadList selectList(MemberVO memberVO, DownloadList downloadList);
	List<AppVO> selectAppListForRelatedApp(MemberVO memberVO);
	List getSelectList(Entity param);
	List getSelectListCount(Entity param);
	List getSelectCouponList(Entity param);
	String getSelectTodayDate();
	List getListNotComplte(MemberVO memberVO);
	List getCountOfIdenticalCouponNumForAll( Entity param );
	List getRowIsCompletedByBundleId( Entity param);
	void deleteAppInfo( int appSeq );
	void deleteAppHistoryInfo( int appSeq );
	void deleteAppSubAppSeqInfo(int appSeq);
	List<AppSubVO> selectAppSubList ( int appSeq);
	int insertAppSubInfo ( AppSubVO appSubVO );
	void deleteAppSubInfo ( AppSubVO appSubVO);
	List<AppVO> getNotPermmitList(int companySeq, Integer[] useA, String searchValue, String searchType);
	List<AppVO> getPermitList(int companySeq, Integer[] useA);
	int checkIfAvailableAppByBundleId ( int userSeq, String ostype, String storeBundleId);
	List<AppVO >getListIsAvailableByCompanySeq( int companySeq);
	HashMap<Object, Object>  selectByBundleIdAndOstype ( String ostype, String storeBundleId );
	
	//20180327 - lsy : develop version managemenet
	List<AppVO> selectAppForHistory( String store_bundle_id );
	int infoUpdateCheck( AppVO appFormVO, int app_seq);
	//20180327 - lsy : develop version managemenet - end
	
	int duplicateVerCheck(Entity param);
	
	AppVO selectBeforAppInfoBySeqForNewVersion(int app_seq);
	int insertNewVersionAppInfo(AppVO newVersionAppInfo);
	AppVO selectAppByAppSeqForMakeJobTicket(int app_seq);
	
	String selectAppVersionMax(int app_seq);
}