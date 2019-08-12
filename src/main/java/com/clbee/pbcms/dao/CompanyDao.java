package com.clbee.pbcms.dao;


import java.util.List;

import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.MemberVO;

public interface CompanyDao {
	int insertCompanyInfoWithProcedure(CompanyVO companyVO);
	CompanyVO findByCustomInfo( String DBName, String Value);
	CompanyVO findByCustomInfo( String DBName, int Value);
	String id_overlap_chk(String id);
	String selectIdByUserNameAndEmail(String lastName, String firstName, String email);
	String send_pw_mail(String myId, String myMail);
	CompanyVO getComInfo(String companyID);
	int updateCompanyInfo( CompanyVO company, int companySeq);
	String changePwChk(MemberVO m, String userID, String inputPW);
	CompanyVO selectByCompanyId( String companyId );
}
