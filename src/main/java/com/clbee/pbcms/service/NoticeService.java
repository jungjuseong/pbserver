package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.NoticeList;
import com.clbee.pbcms.vo.NoticeSubVO;
import com.clbee.pbcms.vo.NoticeVO;
import com.clbee.pbcms.vo.NoticeappSubVO;

public interface NoticeService {
	int insertNoticeInfo( NoticeVO noticeVO );
	int insertNoticeSubInfo( NoticeSubVO noticeSubVO );
	List<NoticeSubVO> selectNoticeSubList ( int noticeSeq );
	void deleteNoticeSubInfo ( NoticeSubVO noticeSubVO);
	void deleteNoticeSubAppSeqInfo ( int appSeq);
	int updateNoticeInfo( NoticeVO updatedVO );
	NoticeVO selectNoticeInfo( int noticeSeq );
	NoticeList selectNoticeList ( int currentPage, int companySeq, String searchType, String searchValue );
	int insertNoticeappSubInfo(NoticeappSubVO noticeappSubVO);
	void deleteNoticeappSubInfo(NoticeappSubVO noticeappSubVO);
	List<NoticeappSubVO> selectNoticeappSubList( int noticeSeq );
	List<NoticeVO> getListAvailableNoticeByCompany(int companySeq, int userSeq, String storeBundleId );
}