package com.clbee.pbcms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clbee.pbcms.dao.ContentsDao;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberList;
import com.clbee.pbcms.vo.MemberVO;


@Service
public class ContentsServiceImpl implements ContentsService {
	@Autowired
	ContentsDao contentDao;

	@Override
	public int insertContentInfo(ContentVO contentVO ) {
		return contentDao.insertContentInfo( contentVO );
	}
	
	@Override
	public int insertContentsappSubInfo(ContentsappSubVO contentsappSubVO) {
		// TODO Auto-generated method stub
		return contentDao.insertContentsappSubInfo( contentsappSubVO );
	}
	@Override
	public int updateNullableContentInfo(ContentVO updatedVO) {
		
		return contentDao.updateNullableContentInfo(updatedVO);
	}

	@Override
	public int updateContentsappSubInfo ( ContentsappSubVO contentappSubVO, int contentsappSubSeq ){

		return contentDao.updateContentsappSubInfo ( contentappSubVO, contentsappSubSeq);
	}
	
	@Override
	public int updateContentInfo(ContentVO content, int contentID) {
		// TODO Auto-generated method stub
		
		return contentDao.updateContentInfo( content, contentID );
	}
	
	@Override
	public ContentList getListContents( int currentPage, int maxResult, String[] sort,  String searchSeq, Integer valueSeq, String searchType, String searchValue, boolean isMember ) {

		ContentList list = null;
		int pageSize = 10;
		int totalCount = 0;
		int startNo = 0;
			try{
				totalCount = contentDao.getListContentsCount( searchSeq, valueSeq, sort,  searchType, searchValue, isMember );
				System.out.println("totalCount = " + totalCount);
				
				list = new ContentList(pageSize, totalCount, currentPage, maxResult);
			
				startNo = (currentPage-1) *maxResult;
	
				List<ContentVO> vo = contentDao.getListContents(startNo, maxResult, sort, searchSeq, valueSeq,  searchType, searchValue, isMember);
				
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
	public ContentVO selectByContentId(int contentID) {
		// TODO Auto-generated method stub
		return contentDao.selectByContentId(contentID);
	}


	@Override
	public ContentVO selectByUltimateCondition(ContentVO cvo) {
		// TODO Auto-generated method stub
		return contentDao.selectByUltimateCondition(cvo);
	}


	@Override
	public List<ContentVO> getListByCustomInfo(String DBName, String value) {
		// TODO Auto-generated method stub
		return contentDao.getListByCustomInfo(DBName, value);
	}


	@Override
	public List<ContentVO> getListByCustomInfo(String DBName, int value) {
		// TODO Auto-generated method stub
		return contentDao.getListByCustomInfo(DBName, value);
	}

	@Override
	public void deleteContentsInfo( int contentsSeq ){
		// TODO Auto-generated method stub
		contentDao.deleteContentsInfo(contentsSeq);
	}

	//20180327 - lsy : develop version managemenet => tb_contents, tb_contentshistory search, date management
	@Override
	public int verifyIfExists( String contentsName, String ver_num){
		
		ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("contentsName", contentsName);
		paramMap.put("ver_num", ver_num);
		list.add(paramMap);
		
		int contents = contentDao.verifyIfExistsContents("verValidationContents", list);
		if(contents == 0) {
			return contentDao.verifyIfExistsHistory("verValidationHistory", list);
		}else if(contents >= 1){
			return contents;
		}else {
			return -1;
		}
	}

	@Override
	public String getSameContentsSeq( String contentsName ){
		return contentDao.getSameContentsSeq("getSameContentsSeq", contentsName);
	}

	@Override
	public void insertContentsHistory( String contents_seq ){
		contentDao.insertContentsHistory("insertContentsHistory", contents_seq);
	}

	@Override
	public void deleteContentsBySeq( String contents_seq ){
		contentDao.deleteContentsBySeq("deleteContentsBySeq", contents_seq);
	}
	
	@Override
	public List<ContentVO> selectForHistory( String contents_name ) {
		return contentDao.selectForHistory( "selectContentsForHistory", contents_name );
	}
	
	@Override
	public int infoUpdateCheck(ContentVO contentFormVO, int contents_seq) {
		// TODO Auto-generated method stub
		int infoUpdate = 0;
		ContentVO contentVO = contentDao.selectByContentId( contents_seq );
		
		if(contentVO.getCompletGb().equals("2") || contentVO.getCompletGb().equals("4")) {
			if( contentFormVO.getContentsName() != null && !"".equals(contentFormVO.getContentsName()) && !(contentFormVO.getContentsName().equals(contentVO.getContentsName())) ) {	infoUpdate += 1;	}
			if( contentFormVO.getContentsType() != null && !"".equals(contentFormVO.getContentsType()) && !(contentFormVO.getContentsType().equals(contentVO.getContentsType())) ) {	infoUpdate += 1;	}
			if( contentFormVO.getVerNum() != null && !"".equals(contentFormVO.getVerNum()) && !(contentFormVO.getVerNum().equals(contentVO.getVerNum())) ) {	infoUpdate += 1;	}
			if( contentFormVO.getAppName() != null && !"".equals(contentFormVO.getAppName()) && !(contentFormVO.getAppName().equals(contentVO.getAppName())) ) {	infoUpdate += 1;	}
			if( contentFormVO.getAppType() != null && !"".equals(contentFormVO.getAppType()) && !(contentFormVO.getAppType().equals(contentVO.getAppType())) ) {	infoUpdate += 1;	}
			if( contentFormVO.getDescriptionText() != null && !"".equals(contentFormVO.getDescriptionText()) && !(contentFormVO.getDescriptionText().equals(contentVO.getDescriptionText())) ) {	infoUpdate += 1;	}
		}
		
		if( contentFormVO.getUseGb() != null && !"".equals(contentFormVO.getUseGb()) && !(contentFormVO.getUseGb().equals(contentVO.getUseGb())) ) {	infoUpdate += 1;	}
		
		if( contentFormVO.getDistrGb() != null && !"".equals(contentFormVO.getDistrGb()) && !(contentFormVO.getDistrGb().equals(contentVO.getDistrGb())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getMemDownGb() != null  && !"".equals(contentFormVO.getMemDownGb()) && !(contentFormVO.getMemDownGb().equals(contentVO.getMemDownGb())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getMemDownAmt() != null && !"".equals(contentFormVO.getMemDownAmt()) && !(contentFormVO.getMemDownAmt().equals(contentVO.getMemDownAmt())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getMemDownStartDt() != null && !(contentFormVO.getMemDownStartDt().equals(contentVO.getMemDownStartDt())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getMemDownEndDt() != null && !(contentFormVO.getMemDownEndDt().equals(contentVO.getMemDownEndDt())) ) {	infoUpdate += 1;	}

		if( contentFormVO.getCouponGb() != null && !"".equals(contentFormVO.getCouponGb()) && !(contentFormVO.getCouponGb().equals(contentVO.getCouponGb())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getNonmemDownGb() != null && !"".equals(contentFormVO.getNonmemDownGb()) && !(contentFormVO.getNonmemDownGb().equals(contentVO.getNonmemDownGb())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getNonmemDownAmt() != null  && !"".equals(contentFormVO.getNonmemDownAmt()) && !(contentFormVO.getNonmemDownAmt().equals(contentVO.getNonmemDownAmt())) )  {	infoUpdate += 1;	}
		if( contentFormVO.getNonmemDownStarDt() != null && !(contentFormVO.getNonmemDownStarDt().equals(contentVO.getNonmemDownStarDt())) ) {	infoUpdate += 1;	}
		if( contentFormVO.getNonmemDownEndDt() != null && !(contentFormVO.getNonmemDownEndDt().equals(contentVO.getNonmemDownEndDt())) ) {	infoUpdate += 1;	}
		
		return infoUpdate;
	}
	//20180327 - lsy : develop version managemenet, date management - end
	
	@Override
	public List<ContentVO> getListContentsForAppMake(Entity param) {
		return contentDao.getListContentsForAppMake( "getListContentsForAppMake", param );
	}
	
	@Override
	public ContentVO selectRelatedAppinfo( String relatedAppName ) {
		return contentDao.selectRelatedAppinfo( "selectRelatedAppinfo", relatedAppName );
	}

	@Override
	public String selectRelatedAppSeqInfo(int contentSeq) {
		// TODO Auto-generated method stub
		return contentDao.selectRelatedAppSeqInfo( "selectRelatedAppSeqInfo", contentSeq );
	}

	@Override
	public ContentVO selectRelatedAppInfoNew(int contentSeq) {
		return contentDao.selectRelatedAppInfoNew( "selectRelatedAppInfoNew", contentSeq );
	}
	
	@Override
	public int updateRelatedAppinfo(Entity param) {
		return contentDao.updateRelatedAppinfo( "updateRelatedAppInfo", param );
	}

	@Override
	public int updateRelatedAppSubInfo(Entity param) {
		// TODO Auto-generated method stub
		return contentDao.updateRelatedAppSubInfo( "updateRelatedAppSubInfo", param );
	}

	@Override
	public int inappContentsProcessGb(Entity param) {
		// TODO Auto-generated method stub
		return contentDao.inappContentsProcessGb( "inappContentsProcessGb", param );
	}

	@Override
	public int insertContentsFromInapp(ContentVO contentVO) {
		// TODO Auto-generated method stub
		return contentDao.insertContentsFromInapp( "insertContentsFromInapp", contentVO );
	}

	@Override
	public void insertContentsSubFromInapp(ContentsappSubVO contentsAppSubVO) {
		// TODO Auto-generated method stub
		contentDao.insertContentsSubFromInapp( "insertContentsSubFromInapp", contentsAppSubVO );
	}

	@Override
	public int selectRelatedContentsSeq(Entity param) {
		// TODO Auto-generated method stub
		return contentDao.selectRelatedContentsSeq( "selectRelatedContentsSeq", param );
	}

	@Override
	public void updateContentsFromInapp(ContentVO contentVO) {
		// TODO Auto-generated method stub
		contentDao.updateContentsFromInapp( "updateContentsFromInapp", contentVO );
	}

	@Override
	public ContentVO selectBeforeContentInfo(int contents_seq) {
		// TODO Auto-generated method stub
		return contentDao.selectBeforeContentInfo( "selectBeforeContentInfo", contents_seq );
	}

	@Override
	public String selectContentsFileName(int contentSeq) {
		// TODO Auto-generated method stub
		return contentDao.selectContentsFileName( "selectContentsFileName", contentSeq );
	}

	@Override
	public void deleteContentsSubBySeq(int contentsSeq) {
		// TODO Auto-generated method stub
		contentDao.deleteContentsSubBySeq("deleteContentsSubBySeq", contentsSeq);
	}

}