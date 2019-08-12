package com.clbee.pbcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.InAppCategoryDao;
import com.clbee.pbcms.dao.InAppDao;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.InAppList;
import com.clbee.pbcms.vo.InappMetaVO;
import com.clbee.pbcms.vo.InappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;


@Service
public class InAppServiceImpl implements InAppService {

	@Autowired InAppDao inAppDao;
	
	@Override
	public InappVO findByCustomInfo(String DBName, int intValue) {
		// TODO Auto-generated method stub
		return inAppDao.findByCustomInfo(DBName, intValue);
	}

	@Override
	public InappVO findByCustomInfo(String DBName, String Value) {
		// TODO Auto-generated method stub
		return inAppDao.findByCustomInfo(DBName, Value);
	}

	@Override
	public List<InappVO> getListInappVO(String DBName, String storeBundleId, int userSeq) {
		// TODO Auto-generated method stub
		return inAppDao.getListInappVO(DBName, storeBundleId, userSeq);
	}

	@Override
	public List<InappVO> getListInappVO(String DBName, String value) {
		// TODO Auto-generated method stub
		return inAppDao.getListInappVO(DBName, value);
	}

	@Override
	public InAppList getListByBundleId(InappVO vo, InAppList inAppList, MemberVO memberVO) {
		// TODO Auto-generated method stub
		//AppList list = null;
		int pageSize = 10;
		int maxResult = 10;
		int totalCount = 0;
		
		try{
			totalCount = inAppDao.getListCntByBundleId(vo, inAppList, memberVO);
			System.out.println("totalCount = " + totalCount);
			
			inAppList.calc(pageSize, totalCount, inAppList.getCurrentPage(), maxResult);		

			List<InappVO> list = inAppDao.getListByBundleId(vo, inAppList, memberVO);
			
			inAppList.setList(list);
			
			System.out.println("[ListService] - selectList method");
			System.out.println("selectList[] " + list.size());
			System.out.println(list.size());
			
		}catch(Exception e){
			System.out.println("����");
			e.printStackTrace();
		}
		return inAppList;
	}

	@Override
	public int getSeqAfterInsertInAppInfo(InappVO vo) {
		// TODO Auto-generated method stub
		return inAppDao.getSeqAfterInsertAppInfo(vo);
	}

	@Override
	public InappVO selectForUpdate(InappVO ivo, MemberVO memberVO) {
		// TODO Auto-generated method stub
		return inAppDao.selectForUpdate(ivo, memberVO);
	}

	@Override
	public void updateInAppInfo(InappVO ivo, int inappSeq) {
		// TODO Auto-generated method stub
		inAppDao.updateInAppInfo(ivo, inappSeq);
	}

	@Override
	public Object[] getListInAppForRelatedApp(String appSeq) {
		// TODO Auto-generated method stub
		return inAppDao.getListInAppForRelatedApp( appSeq );
	}

	@Override
	public List findListByCustomInfo(String DBName, String value) {
		// TODO Auto-generated method stub
		return inAppDao.findListByCustomInfo(DBName, value);
	}

	@Override
	public List findListByCustomInfo(String DBName, int value) {
		// TODO Auto-generated method stub
		return inAppDao.findListByCustomInfo(DBName, value);
	}

	@Override
	public List<InappSubVO> selectInAppSubList(int inAppSeq) {
		// TODO Auto-generated method stub
		return inAppDao.selectInAppSubList(inAppSeq);
	}

	@Override
	public int insertInAppSubInfo(InappSubVO inAppSubVO) {
		// TODO Auto-generated method stub
		return inAppDao.insertInAppSubInfo(inAppSubVO);
	}

	@Override
	public void deleteInAppSubInfo(InappSubVO inAppSubVO) {
		// TODO Auto-generated method stub
		inAppDao.deleteInAppSubInfo(inAppSubVO);
	}

	@Override
	public boolean checkInappNameIfExist(String InappName, String storeBundleId, String verNum) {
		// TODO Auto-generated method stub
		return inAppDao.checkInappNameIfExist(InappName, storeBundleId, verNum);
	}

	@Override
	public List<InappVO> getListInappIsAvailableByStoreBundleId (String storeBundleId) {
		// TODO Auto-generated method stub
		return inAppDao.getListInappIsAvailableByStoreBundleId (storeBundleId);
	}

	@Override
	public int insertInAppMetaInfo(InappMetaVO inappMetaVO) {
		// TODO Auto-generated method stub
		return inAppDao.insertInAppMetaInfo(inappMetaVO);
	}

	@Override
	public InappMetaVO findByCustomInfoForMetaVO(String DBName, int intValue) {
		// TODO Auto-generated method stub
		return inAppDao.findByCustomInfoForMetaVO(DBName, intValue);
	}

	@Override
	public InappMetaVO findByCustomInfoForMetaVO(String DBName, String value) {
		// TODO Auto-generated method stub
		return inAppDao.findByCustomInfoForMetaVO(DBName, value);
	}

	@Override
	public void updateInAppMetaInfo(InappMetaVO updatedVO, int inappMetaSeq) {
		// TODO Auto-generated method stub
		inAppDao.updateInAppMetaInfo(updatedVO, inappMetaSeq);
	}

	@Override
	public void deleteInAppInfo( String storeBundleId ) {
		// TODO Auto-generated method stub
		inAppDao.deleteInAppInfo(storeBundleId);
	}

	//20180327 - lsy : develop version managemenet
	@Override
	public String selectCompletGbBySeq( int inapp_seq ) {
		// TODO Auto-generated method stub
		return inAppDao.selectCompletGbBySeq("selectCompletGbBySeq", inapp_seq);
	}

	@Override
	public String getSameInappSeq ( String inapp_name, String store_bundle_id ) {
		// TODO Auto-generated method stub
		return inAppDao.getSameInappSeq("getSameInappSeq", inapp_name, store_bundle_id);
	}

	@Override
	public void insertInappHistory( String inapp_seq ){
		inAppDao.insertInappHistory("insertInappHistory", inapp_seq);
	}

	@Override
	public void deleteInAppBySeq( String inapp_seq ){
		inAppDao.deleteInAppBySeq("deleteInAppBySeq", inapp_seq);
	}
	
	@Override
	public List<InappVO> selectForHistory( String inapp_name, String store_bundle_id ) {
		return inAppDao.selectForHistory( "selectInappForHistory", inapp_name, store_bundle_id );
	}
	
	@Override
	public int infoUpdateCheck(InappVO inappFormVO, int inapp_seq) {
		// TODO Auto-generated method stub
		int infoUpdate = 0;
		InappVO inappVO = inAppDao.selectInappBySeq( "selectInappBySeq", inapp_seq );
		
		if(inappVO.getCompletGb().equals("2") || inappVO.getCompletGb().equals("4")) {
			if( inappFormVO.getInappName() != null && !"".equals(inappFormVO.getInappName()) && !(inappFormVO.getInappName().equals(inappVO.getInappName())) ) {	infoUpdate += 1;	}
			if( inappFormVO.getVerNum() != null && !"".equals(inappFormVO.getVerNum()) && !(inappFormVO.getVerNum().equals(inappVO.getVerNum())) ) {	infoUpdate += 1;	}
			if( inappFormVO.getCategorySeq() != null && !"".equals(inappFormVO.getCategorySeq()) && !(inappFormVO.getCategorySeq().equals(inappVO.getCategorySeq())) ) {	infoUpdate += 1;	}
			if( inappFormVO.getCategoryName() != null && !"".equals(inappFormVO.getCategoryName()) && !(inappFormVO.getCategoryName().equals(inappVO.getCategoryName())) ) {	infoUpdate += 1;	}
			if( inappFormVO.getDescriptionText() != null && !"".equals(inappFormVO.getDescriptionText()) && !(inappFormVO.getDescriptionText().equals(inappVO.getDescriptionText())) ) {	infoUpdate += 1;	}
			
			if(inappFormVO.getIconOrgFile() != null && !"".equals(inappFormVO.getIconOrgFile()) && !(inappFormVO.getIconOrgFile().equals(inappVO.getIconOrgFile())) ) {	infoUpdate += 1;	}
			if(inappFormVO.getIconSaveFile() != null && !"".equals(inappFormVO.getIconSaveFile()) && !(inappFormVO.getIconSaveFile().equals(inappVO.getIconSaveFile())) ) {	infoUpdate += 1;	}
			
		}
		
		if( inappFormVO.getUseGb() != null && !"".equals(inappFormVO.getUseGb()) && !(inappFormVO.getUseGb().equals(inappVO.getUseGb())) ) {	infoUpdate += 1;	}
		if( inappFormVO.getUseUserGb() != null && !"".equals(inappFormVO.getUseUserGb()) && !(inappFormVO.getUseUserGb().equals(inappVO.getUseUserGb())) ) {	infoUpdate += 1;	}
		if( inappFormVO.getScreenType() != null && !"".equals(inappFormVO.getScreenType()) && !(inappFormVO.getScreenType().equals(inappVO.getScreenType())) ) {	infoUpdate += 1;	}
		
		return infoUpdate;
	}
	//20180327 - lsy : develop version managemenet - end

	//20180619 - lsy : when app request(Library), load inapp info
	@Override
	public InAppList getInAppByBundleId(InAppList inAppList, String store_bundle_id) {
		// TODO Auto-generated method stub
		int totalCount = 0;
		
		try{
			totalCount = inAppDao.getInAppCntByBundleId("getInAppCntByBundleId", store_bundle_id);
			inAppList.setTotalCount(totalCount);
			
			List<InappVO> list = inAppDao.getInAppByBundleId("getInAppByBundleId", store_bundle_id);			
			inAppList.setList(list);			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return inAppList;
	}
	//20180619 - lsy : when app request(Library), load inapp info - end
}