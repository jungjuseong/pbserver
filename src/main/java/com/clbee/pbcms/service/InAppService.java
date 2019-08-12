package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.InAppList;
import com.clbee.pbcms.vo.InappMetaVO;
import com.clbee.pbcms.vo.InappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;

public interface InAppService {
	InappVO findByCustomInfo( String DBName, int intValue);
	InappVO findByCustomInfo( String DBName, String Value);
	List<InappVO> getListInappVO(String DBName, String storeBundleId, int userSeq);
	List<InappVO> getListInappVO(String DBName, String value);
	InAppList getListByBundleId(InappVO vo, InAppList inAppList, MemberVO memberVO);
	int getSeqAfterInsertInAppInfo(InappVO vo );
	InappVO selectForUpdate(InappVO ivo, MemberVO memberVO);
	void updateInAppInfo(InappVO ivo, int inappSeq);
	Object[] getListInAppForRelatedApp( String appSeq );
	List findListByCustomInfo(String DBName, String value);
	List findListByCustomInfo(String DBName, int value);
	List<InappSubVO> selectInAppSubList ( int inAppSeq );
	int insertInAppSubInfo ( InappSubVO inAppSubVO );
	void deleteInAppSubInfo ( InappSubVO inAppSubVO );
	void deleteInAppInfo( String storeBundleId );
	boolean checkInappNameIfExist(String InappName, String storeBundleId, String verNum);
	List<InappVO> getListInappIsAvailableByStoreBundleId ( String storeBundleId);
	int insertInAppMetaInfo ( InappMetaVO inappMetaVO );
	InappMetaVO findByCustomInfoForMetaVO( String DBName, int intValue);
	InappMetaVO findByCustomInfoForMetaVO( String DBName, String value);
	void updateInAppMetaInfo(InappMetaVO updatedVO, int inappMetaSeq);
	
	//20180327 - lsy : develop version managemenet
	String selectCompletGbBySeq(int inapp_seq);
	String getSameInappSeq ( String inapp_name, String store_bundle_id );
	void insertInappHistory( String inapp_seq );
	void deleteInAppBySeq( String inapp_seq );
	
	List<InappVO> selectForHistory( String inapp_name, String store_bundle_id );
	
	int infoUpdateCheck( InappVO inappFormVO, int inapp_seq);
	//20180327 - lsy : develop version managemenet - end
	
	//20180619 - lsy : when app request(Library), load inapp info
	InAppList getInAppByBundleId(InAppList inAppList, String store_bundle_id);
}
