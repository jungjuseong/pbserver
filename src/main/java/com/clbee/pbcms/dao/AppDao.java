package com.clbee.pbcms.dao;

import java.util.List;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppHistoryVO;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.DownloadList;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;

public interface AppDao {
	int insertAppInfo( AppVO app );
	int insertAppHistoryInfo( AppHistoryVO app );
	void updateAppInfo( AppVO updatedVO, int appNum ) throws Exception;
	void updateAppSubInfo ( AppSubVO updatedVO, int subNum );
	List<AppVO> selectList( int startNo, String user_id );
	int getListCount( String user_id );
	AppVO selectById( int appNum );
	AppVO selectByStoreId( String storeBundleId );
	Object selectByBundleId( Entity param );
	List<AppVO> selectByUserId(String user_id);
	void updateAppByIdentifier ( AppVO updatedVO, String store_bundle_id );
	List<AppVO> selectByUserIdAndIdenty( String user_id, String bundle_identy);
	int getSeqAfterInsertAppInfo(AppVO app);
	int getListCount(AppList appList, MemberVO memberVO);
	List<AppVO> selectList(AppList appList, MemberVO memberVO);
	AppVO selectForUpdate(AppVO appVO, MemberVO memberVO);
	int getListCount(DownloadList downloadList, MemberVO memberVO);
	List<?> selectList(DownloadList downloadList, MemberVO memberVO);
	List selectList(Entity param);
	List getSelectListCount(Entity param);
	List getSelectCouponList(Entity param);
	String getSelectTodayDate();
	List getListNotComplte(MemberVO memberVO);
	List getCountOfIdenticalCouponNumForAll( Entity param );
	List getRowIsCompletedByBundleId( Entity param);
	void deleteAppInfo( int appSeq );
	void deleteAppHistoryInfo( int appSeq );
	void deleteAppSubAppSeqInfo(int appSeq);
	List<AppSubVO> selectAppSubList ( int appSeq );
	int insertAppSubInfo ( AppSubVO appSubVO );
	void deleteAppSubInfo ( AppSubVO appSubVO );
	List<AppVO> getNotPermmitList(int companySeq, Integer[] useA, String searchValue, String searchType);
	List<AppVO> getPermitList(int companySeq, Integer[] useA);
	int checkIfAvailableAppByBundleId ( int userSeq, String ostype, String storeBundleId);
	List<AppVO> getListIsAvailableByCompanySeq( int companySeq );
	AppVO selectByBundleIdAndOstype ( String ostype, String storeBundleId );
	
	//20180327 - lsy : develop version managemenet
	List<AppVO> selectAppForHistory( String DBName, String store_bundle_id );
	AppVO selectAppBySeq( String DBName, int appSeq );
	//20180327 - lsy : develop version managemenet - end
	
	int duplicateVerCheck(String DBName, Entity param);
	
	AppVO selectBeforAppInfoBySeqForNewVersion ( String DBName, int app_seq );
	int insertNewVersionAppInfo(String DBName, AppVO newVersionAppInfo);
	AppVO selectAppByAppSeqForMakeJobTicket(String DBName, int app_seq);
	
	String selectAppVersionMax(String DBName, int app_seq);
}
