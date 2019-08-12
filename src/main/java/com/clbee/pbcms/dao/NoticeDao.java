package com.clbee.pbcms.dao;

import java.util.List;

import com.clbee.pbcms.vo.NoticeSubVO;
import com.clbee.pbcms.vo.NoticeVO;
import com.clbee.pbcms.vo.NoticeappSubVO;

public interface NoticeDao {
	int insertNoticeInfo( NoticeVO noticeVO );
	int insertNoticeSubInfo ( NoticeSubVO noticeSubVO);
	void deleteNoticeSubInfo ( NoticeSubVO noticeSubVO);
	List<NoticeSubVO> selectNoticeSubList ( int noticeSeq );
	int updateNoticeInfo( NoticeVO updatedVO );
	NoticeVO selectNoticeInfo( int noticeSeq );
	List<NoticeVO> selectNoticeList ( int startNo, int companySeq, String searchType, String searchValue );
	int selectNoticeListCount ( int companySeq, String searchType, String searchValue );
	int insertNoticeappSubInfo(NoticeappSubVO noticeappSubVO);
	void deleteNoticeappSubInfo(NoticeappSubVO noticeappSubVO);
	List<NoticeappSubVO> selectNoticeappSubList( int noticeSeq );
	List<NoticeVO> getListNoticeByCompany(int companySeq, int userSeq, String storeBundleId );
	void deleteNoticeappSubInfo(int appSeq);
}