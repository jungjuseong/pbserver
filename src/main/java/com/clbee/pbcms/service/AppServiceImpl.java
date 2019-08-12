package com.clbee.pbcms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.AppDao;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppHistoryVO;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.DownloadList;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;

@Service
public class AppServiceImpl implements AppService {
	@Autowired 
	AppDao dao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public int insertAppInfo( AppVO app ) {
		return dao.insertAppInfo( app );
	}
	
	@Override
	public void updateAppInfo( AppVO updatedVO, int appNum)throws Exception {
		// TODO Auto-generated method stub
		dao.updateAppInfo( updatedVO, appNum);
	}
	
	
	@Override
	public AppVO selectByStoreId( String storeBundleId ) {
		return dao.selectByStoreId( storeBundleId );
	}

	@Override
	public AppVO selectById( int appNum ) {
		return dao.selectById( appNum );
	}
	
	@Override
	public AppList selectList( int currentPage, String user_id ) {

		AppList list = null;

		int pageSize = 10;
		int maxResult = 10;
		int totalCount = 0;
		int startNo = 0;

		try{
			totalCount = dao.getListCount(user_id);
			System.out.println("totalCount = " + totalCount);
			
			list = new AppList(pageSize, totalCount, currentPage, maxResult);
		
			startNo = (currentPage-1) *10;

			List<AppVO> vo = dao.selectList(startNo, user_id);
			
			list.setList(vo);
			
			System.out.println("[ListService] - selectList method");
			System.out.println("selectList[] " + vo.size());
			System.out.println(vo.size());
			
		}catch(Exception e){
			System.out.println("����");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getListCount(String user_id) {
		return dao.getListCount(user_id);
	}

	@Override
	public List<AppVO> selectByUserId(String user_id) {
		// TODO Auto-generated method stub
		return dao.selectByUserId(user_id);
	}

	@Override
	public void updateAppByIdentifier(AppVO updatedVO,
			String store_bundle_id) {
		// TODO Auto-generated method stub
		dao.updateAppByIdentifier(updatedVO, store_bundle_id);
	}

	@Override
	public List<AppVO> selectByUserIdAndIdenty(String user_id, String bundle_identy) {
		return dao.selectByUserIdAndIdenty(user_id, bundle_identy);
	}

	@Override
	public Object selectByBundleId(Entity param) {
		// TODO Auto-generated method stub
		return dao.selectByBundleId( param);
	}

	@Override
	public int getSeqAfterInsertAppInfo(AppVO app) {
		// TODO Auto-generated method stub
		return dao.getSeqAfterInsertAppInfo(app);
	}

	@Override
	public AppList selectList(MemberVO memberVO, AppList appList) {
		// TODO Auto-generated method stub
		//AppList list = null;
		int pageSize = 10;
		int maxResult = 10;
		int totalCount = 0;
		
		try{
			totalCount = dao.getListCount(appList, memberVO);
			System.out.println("totalCount = " + totalCount);
			
			appList.calc(pageSize, totalCount, appList.getCurrentPage(), maxResult);		

			List<AppVO> vo = dao.selectList(appList, memberVO);
			
			appList.setList(vo);
			
			System.out.println("[ListService] - selectList method");
			System.out.println("selectList[] " + vo.size());
			System.out.println(vo.size());
			
		}catch(Exception e){
			System.out.println("����");
			e.printStackTrace();
		}
		return appList;
	}

	@Override
	public AppVO selectForUpdate(AppVO appVO, MemberVO memberVO) {
		// TODO Auto-generated method stub
		return dao.selectForUpdate(appVO, memberVO);
	}

	@Override
	public DownloadList selectList(MemberVO memberVO, DownloadList downloadList) {
		// TODO Auto-generated method stub
		//AppList list = null;
		int pageSize = 10;
		int maxResult = 10;
		int totalCount = 0;

		try{
			totalCount = dao.getListCount(downloadList, memberVO);
			System.out.println("totalCount = " + totalCount);
			
			downloadList.calc(pageSize, totalCount, downloadList.getCurrentPage(), maxResult);		

			List<?> vo = dao.selectList(downloadList, memberVO);
			
			downloadList.setList(vo);
			
			System.out.println("[ListService] - selectList method");
			System.out.println("selectList[] " + vo.size());
			System.out.println(vo.size());
			
		}catch(Exception e){
			System.out.println("����");
			e.printStackTrace();
		}			
			return downloadList;
	}

	@Override
	public List<AppVO> selectAppListForRelatedApp(MemberVO memberVO){
		return dao.selectList((AppList)(null), memberVO);
	}

	@Override
	public List getSelectList(Entity param) {
		return dao.selectList(param);
	}

	@Override
	public List getSelectListCount(Entity param) {
		return dao.getSelectListCount(param);
	}

	@Override
	public List getSelectCouponList(Entity param) {
		return dao.getSelectCouponList(param);		
	}

	@Override
	public String getSelectTodayDate() {
		return dao.getSelectTodayDate();		
	}

	@Override
	public List getListNotComplte(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return dao.getListNotComplte(memberVO);
	}

	@Override
	public List getCountOfIdenticalCouponNumForAll(Entity param) {
		// TODO Auto-generated method stub
		return dao.getCountOfIdenticalCouponNumForAll(param);
	}

	@Override
	public List getRowIsCompletedByBundleId(Entity param) {
		// TODO Auto-generated method stub
		return dao.getRowIsCompletedByBundleId(param);
	}

	@Override
	public void deleteAppInfo(int appSeq) {
		// TODO Auto-generated method stub
		dao.deleteAppInfo(appSeq);
	}
	
	@Override
	public void deleteAppHistoryInfo(int appSeq) {
		// TODO Auto-generated method stub
		dao.deleteAppHistoryInfo(appSeq);
	}

	@Override
	public int insertAppHistoryInfo(AppHistoryVO app) {
		// TODO Auto-generated method stub
		return dao.insertAppHistoryInfo(app);
	}

	@Override
	public List<AppSubVO> selectAppSubList(int appSeq) {
		// TODO Auto-generated method stub
		return dao.selectAppSubList(appSeq);
	}

	@Override
	public int insertAppSubInfo(AppSubVO appSubVO) {
		// TODO Auto-generated method stub
		return dao.insertAppSubInfo( appSubVO );
	}

	@Override
	public void deleteAppSubInfo(AppSubVO appSubVO) {
		// TODO Auto-generated method stub
		dao.deleteAppSubInfo(appSubVO);
	}

	@Override
	public List<AppVO> getNotPermmitList(int companySeq, Integer[] useA,
			String searchValue, String searchType) {
		// TODO Auto-generated method stub
		return dao.getNotPermmitList(companySeq, useA, searchValue, searchType);
	}

	@Override
	public List<AppVO> getPermitList(int companySeq, Integer[] useA) {
		// TODO Auto-generated method stub
		return dao.getPermitList(companySeq, useA);
	}

	@Override
	public int checkIfAvailableAppByBundleId( int userSeq, String ostype,
			String storeBundleId) {
		// TODO Auto-generated method stub
		return dao.checkIfAvailableAppByBundleId( userSeq, ostype, storeBundleId );
	}

	@Override
	public List<AppVO> getListIsAvailableByCompanySeq(int companySeq) {
		// TODO Auto-generated method stub
		List<AppVO> tempList = new ArrayList<AppVO>();
		List<String> tempStringList = new ArrayList<String>();
		List<AppVO> appVOList =  dao.getListIsAvailableByCompanySeq(companySeq);
		if(appVOList != null){
			for(AppVO item : appVOList) {
				if(!tempStringList.contains(item.getStoreBundleId())) {
					tempList.add(item);
					tempStringList.add(item.getStoreBundleId());
				}
			}
		}
		return tempList;
	}

	@Override
	public HashMap<Object, Object> selectByBundleIdAndOstype(String ostype, String storeBundleId) {
		// TODO Auto-generated method stub
		
		AppVO appVO =  dao.selectByBundleIdAndOstype(ostype, storeBundleId);
		appVO.setAppSubVO(null);
		appVO.setChgMemberVO(null);
		appVO.setRegMemberVO(null);
		HashMap<Object, Object> map = new HashMap<Object, Object>();

		

		/* 1. OK
		 * 2. ��ȿ ��¥�� ������.
		 * 3. �˼� ���� ����
		 * 4. ������ ostype�� bundleid�� ��ġ�ϴ� ���� �����ϴ�.
		 * 5. ��������� ���Դϴ�.
		 * 6. ���ѵ� ���Դϴ�.
		 * */
		//appVO.getMemDownStartDt()		
		int result = 5001;
		try {
			if(appVO == null) {
				/* �ĺ��ڿ�, ostype�� ��ġ�ϴ� ������ �����ϴ� ���� �����ϴ�. */
				result = 5004;
			}else {
				/*Restrictions.eq("useGb", "1"),
				Restrictions.eq("installGb", "1"),
				Restrictions.eq("limitGb", "2")*/
				if("2".equals(appVO.getMemDownGb()) && appVO.getMemDownStartDt() != null && appVO.getMemDownEndDt() != null){
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
					DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
					Date date = new Date();			
					Date formattedDate;
					formattedDate = inputDF.parse(format.format(date));
		
					Calendar cal = Calendar.getInstance();
					cal.setTime(formattedDate);
					
					int month = cal.get(Calendar.MONTH);
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int year = cal.get(Calendar.YEAR);
				
					
					formattedDate = inputDF.parse(format.format(appVO.getMemDownStartDt()));
					
					cal.setTime(formattedDate);
					int stMonth = cal.get(Calendar.MONTH);
					int stDay = cal.get(Calendar.DAY_OF_MONTH);
					int stYear = cal.get(Calendar.YEAR);
					
					formattedDate = inputDF.parse(format.format(appVO.getMemDownEndDt()));
					
					cal.setTime(formattedDate);
					int endMonth = cal.get(Calendar.MONTH);
					int endDay = cal.get(Calendar.DAY_OF_MONTH);
					int endYear = cal.get(Calendar.YEAR);
				
		
					// 1. year(���� �⵵)�� stYear���� Ŭ��� month�� day�� �ƹ�����̾���
					// 2. year�� stYear�� ������� month�� ����
					// 3. �̶� month�� stMonth���� Ŭ��� day�� �ƹ������ ����
					// 4. month�� stMonth�� ������� stDay�� ����
					// endYear�� 1, 2, 3, 4 �� ����
					if(year > stYear || (year == stYear && (month > stMonth || (month == stMonth && day >= stDay)))){
						if(year < endYear || (year == endYear && (month < endMonth || (month == endMonth && day <= endDay)))){
							result = 5001;
						}else {
							result = 5002;
						}
					}else {
						result = 5002;
					}
				}
				if("2".equals(appVO.getUseGb())) result =  5005;
				if("1".equals(appVO.getLimitGb())) result =  5006;
					
			}
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			result = 5003;
			return map;
		}finally{
			switch(result) {
				case 5001 :
					map.put("message", "");
					break;
				case 5002 :
					map.put("message", messageSource.getMessage("app.execute.5002", null, Locale.getDefault()));
					break;
				case 5003 :
					map.put("message", messageSource.getMessage("app.execute.5003", null, Locale.getDefault()));
					break;
				case 5004 :
					map.put("message", messageSource.getMessage("app.execute.5004", null, Locale.getDefault()));
					break;
				case 5005 :
					map.put("message", messageSource.getMessage("app.execute.5005", null, Locale.getDefault()));
					break;
				case 5006 :
					map.put("message", messageSource.getMessage("app.execute.5006", null, Locale.getDefault()));
					break;		
				}
			map.put("result", result);
			map.put("appInfo", appVO);
		}
	}

	@Override
	public void updateAppSubInfo(AppSubVO updatedVO, int subNum) {
		// TODO Auto-generated method stub
		dao.updateAppSubInfo(updatedVO, subNum);
	}

	@Override
	public void deleteAppSubAppSeqInfo(int appSeq) {
		// TODO Auto-generated method stub
		dao.deleteAppSubAppSeqInfo(appSeq);
	}
	
	//20180327 - lsy : develop version managemenet
	@Override
	public List<AppVO> selectAppForHistory( String store_bundle_id ) {
		return dao.selectAppForHistory( "selectAppForHistory", store_bundle_id );
	}
	
	@Override
	public int infoUpdateCheck(AppVO appFormVO, int app_seq) {
		// TODO Auto-generated method stub
		int infoUpdate = 0;
		AppVO appVO = dao.selectAppBySeq( "selectAppBySeq", app_seq );
		
		if(appVO.getCompletGb().equals("2") || appVO.getCompletGb().equals("4")) {
			if(appVO.getRegGb().equals("1")) {
				if( appFormVO.getAppContentsAmt() != null && !"".equals(appFormVO.getAppContentsAmt()) && !(appFormVO.getAppContentsAmt().equals(appVO.getAppContentsAmt())) ) {	infoUpdate += 1;	}
				if( appFormVO.getAppContentsGb() != null && !"".equals(appFormVO.getAppContentsGb()) && !(appFormVO.getAppContentsGb().equals(appVO.getAppContentsGb())) ) {	infoUpdate += 1;	}
			}
			if( appFormVO.getAppName() != null && !"".equals(appFormVO.getAppName()) && !(appFormVO.getAppName().equals(appVO.getAppName())) ) {	infoUpdate += 1;	}
			if( appFormVO.getFileName() != null && !"".equals(appFormVO.getFileName()) && !(appFormVO.getFileName().equals(appVO.getFileName())) ) {	infoUpdate += 1;	}
			if( appFormVO.getVerNum() != null && !"".equals(appFormVO.getVerNum()) && !(appFormVO.getVerNum().equals(appVO.getVerNum())) ) {	infoUpdate += 1;	}
			if( appFormVO.getVersionCode() != null && !"".equals(appFormVO.getVersionCode()) && !(appFormVO.getVersionCode().equals(appVO.getVersionCode())) ) {	infoUpdate += 1;	}			
			if( appFormVO.getTemplateName() != null && !"".equals(appFormVO.getTemplateName()) && !(appFormVO.getTemplateName().equals(appVO.getTemplateName())) ) {	infoUpdate += 1;	}
			if( appFormVO.getTemplateSeq() != null && !"".equals(appFormVO.getTemplateSeq()) && !(appFormVO.getTemplateSeq().equals(appVO.getTemplateSeq())) ) {	infoUpdate += 1;	}
			if( appFormVO.getDescriptionText() != null && !"".equals(appFormVO.getDescriptionText()) && !(appFormVO.getDescriptionText().equals(appVO.getDescriptionText())) ) {	infoUpdate += 1;	}
			if( appFormVO.getChgText() != null && !"".equals(appFormVO.getChgText()) && !(appFormVO.getChgText().equals(appVO.getChgText())) ) {	infoUpdate += 1;	System.out.println("test====>"+infoUpdate+",,"+appFormVO.getChgText()+",,"+appVO.getChgText());}
			if( appFormVO.getIconOrgFile() != null && !"".equals(appFormVO.getIconOrgFile()) && !(appFormVO.getIconOrgFile().equals(appVO.getIconOrgFile())) ) {	infoUpdate += 1;	}
			if( appFormVO.getIconSaveFile() != null && !"".equals(appFormVO.getIconSaveFile()) && !(appFormVO.getIconSaveFile().equals(appVO.getIconSaveFile())) ) {	infoUpdate += 1;	}
		}
		if( appFormVO.getLoginGb() != null && !"".equals(appFormVO.getLoginGb()) && !(appFormVO.getLoginGb().equals(appVO.getLoginGb())) ) {	infoUpdate += 1;	}
		if( appFormVO.getUseGb() != null && !"".equals(appFormVO.getUseGb()) && !(appFormVO.getUseGb().equals(appVO.getUseGb())) ) {	infoUpdate += 1;	}
		if( appFormVO.getUseUserGb() != null && !"".equals(appFormVO.getUseUserGb()) && !(appFormVO.getUseUserGb().equals(appVO.getUseUserGb())) ) {	infoUpdate += 1;	}
		
		if( appFormVO.getInstallGb() != null && !"".equals(appFormVO.getInstallGb()) && !(appFormVO.getInstallGb().equals(appVO.getInstallGb())) ) {	infoUpdate += 1;	}
		
		if( appFormVO.getDistrGb() != null && !"".equals(appFormVO.getDistrGb()) && !(appFormVO.getDistrGb().equals(appVO.getDistrGb())) ) {	infoUpdate += 1;	}
		if( appFormVO.getMemDownGb() != null  && !"".equals(appFormVO.getMemDownGb()) && !(appFormVO.getMemDownGb().equals(appVO.getMemDownGb())) ) {	infoUpdate += 1;	}
		if( appFormVO.getMemDownAmt() != null && !"".equals(appFormVO.getMemDownAmt()) && !(appFormVO.getMemDownAmt().equals(appVO.getMemDownAmt())) ) {	infoUpdate += 1;	}
		if( appFormVO.getMemDownStartDt() != null && !(appFormVO.getMemDownStartDt().equals(appVO.getMemDownStartDt())) ) {	infoUpdate += 1;	}
		if( appFormVO.getMemDownEndDt() != null && !(appFormVO.getMemDownEndDt().equals(appVO.getMemDownEndDt())) ) {	infoUpdate += 1;	}

		if( appFormVO.getCouponGb() != null && !"".equals(appFormVO.getCouponGb()) && !(appFormVO.getCouponGb().equals(appVO.getCouponGb())) ) {	infoUpdate += 1;	}
		if( appFormVO.getNonmemDownGb() != null && !"".equals(appFormVO.getNonmemDownGb()) && !(appFormVO.getNonmemDownGb().equals(appVO.getNonmemDownGb())) ) {	infoUpdate += 1;	}
		if( appFormVO.getNonmemDownAmt() != null  && !"".equals(appFormVO.getNonmemDownAmt()) && !(appFormVO.getNonmemDownAmt().equals(appVO.getNonmemDownAmt())) )  {	infoUpdate += 1;	}
		if( appFormVO.getNonmemDownStarDt() != null && !(appFormVO.getNonmemDownStarDt().equals(appVO.getNonmemDownStarDt())) ) {	infoUpdate += 1;	}
		if( appFormVO.getNonmemDownEndDt() != null && !(appFormVO.getNonmemDownEndDt().equals(appVO.getNonmemDownEndDt())) ) {	infoUpdate += 1;	}
		
		return infoUpdate;
	}
	//20180327 - lsy : develop version managemenet - end

	@Override
	public AppVO selectBeforAppInfoBySeqForNewVersion(int app_seq) {
		// TODO Auto-generated method stub
		return dao.selectBeforAppInfoBySeqForNewVersion( "selectBeforAppInfoBySeqForNewVersion", app_seq );
	}

	@Override
	public int insertNewVersionAppInfo(AppVO newVersionAppInfo) {
		// TODO Auto-generated method stub
		return dao.insertNewVersionAppInfo("insertNewVersionAppInfo", newVersionAppInfo);
	}

	@Override
	public AppVO selectAppByAppSeqForMakeJobTicket(int app_seq) {
		// TODO Auto-generated method stub
		return dao.selectAppByAppSeqForMakeJobTicket("selectAppByAppSeqForMakeJobTicket", app_seq);
	}

	@Override
	public int duplicateVerCheck(Entity param) {
		// TODO Auto-generated method stub
		return dao.duplicateVerCheck("duplicateVerCheck", param);
	}

	@Override
	public String selectAppVersionMax(int app_seq) {
		// TODO Auto-generated method stub
		return dao.selectAppVersionMax("selectAppVersionMax", app_seq);
	}
}
