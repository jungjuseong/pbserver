package com.clbee.pbcms.dao;

import com.clbee.pbcms.vo.MemberVO;

import java.util.List;

public abstract interface MemberDao
{
	void addMember( MemberVO m );
	int updateMemberInfo( MemberVO updatedVO, int userNum );
	int selectItselfForExisting( String DBName, String itSelf );
	List<MemberVO> getListMember( int startNo, int companySeq, int MaxResult, String searchType, String searchValue, String isAvailable, boolean isMember);
	int getListMemberCount( int companySeq, String searchType, String searchValue, String isAvailable, boolean isMember );
	List<MemberVO> logInVerify( String username, String password );
	MemberVO findByUserName(String username);
	MemberVO findByCustomInfo(String DBName, String value);
	MemberVO findByCustomInfo(String DBName, int value);
	MemberVO selectMemberSuccessYn(MemberVO memberVO);
	Integer selectMemberCount(MemberVO memberVO);
	MemberVO selectMemberSuccessYn_(MemberVO memberVO);
	Integer selectMemberCount_(MemberVO memberVO);
	void updateMemberPw(MemberVO memberVO);
	int selectCountWithPermisionUserByCompanySeq( int companySeq);
	int selectCountByCompanySeq(int companySeq);
	MemberVO findCompanyMemberIdByCompanySeqAndUserGb( int companySeq );
	List<MemberVO> getUserList( int companySeq, String[] useS, String searchValue, String searchType );
	List<MemberVO> getPermitList ( int companySeq, String[] useS);

	//20180213 - lsy : user delete
	int deleteMemberInfo(int userNum);
	
	//20180619 - lsy : when app request(Library), load inapp info
	int getCompanySeqForInappContentCopy(String DBName, int userSeq);
	
	String selectCompanyName(String DBName, int company_seq);
}