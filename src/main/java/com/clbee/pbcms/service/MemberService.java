package com.clbee.pbcms.service;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import com.clbee.pbcms.vo.MemberList;
import com.clbee.pbcms.vo.MemberVO;

public interface MemberService {
	void addMember( MemberVO m );
	int verifyIfExists( String DBName, String itSelf);
	int logInVerify( String username, String password );
	MemberList getListMember( int currentPage, int companySeq, int maxResult, String searchType, String searchValue, String isAvailable, boolean isMember );
	MemberVO findByUserName(String username);
	int updateMemberInfo( MemberVO m, int userNum);
	MemberVO findByCustomInfo(String DBName, String value);
	MemberVO findByCustomInfo(String DBName, int value);
	MemberVO selectMemberSuccessYn(MemberVO memberVO);
	Integer selectMemberCount(MemberVO memberVO);
	MemberVO selectMemberSuccessYn_(MemberVO memberVO);
	Integer selectMemberCount_(MemberVO memberVO);
	void updateMemberPw(MemberVO memberVO);
	String findCompanyMemberIdByCompanySeqAndUserGb( int companySeq );
	int selectCountWithPermisionUserByCompanySeq( int companySeq);
	List<MemberVO> getUserList(  int companySeq, String[] useS, String searchValue, String searchType  );
	List<MemberVO> getPermitList( int companySeq, String[] useS );

	//20180213 - lsy : user delete
	int deleteMemberInfo(int userNum);
	
	//20180619 - lsy : when app request(Library), load inapp info
	int getCompanySeqForInappContentCopy(int userSeq);
	
	String selectCompanyName(int company_seq);
}