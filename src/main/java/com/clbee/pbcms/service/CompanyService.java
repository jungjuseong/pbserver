package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.MemberVO;

public interface CompanyService {
	int insertCompanyInfoWithProcedure(CompanyVO companyVO);
	int updateCompanyInfo( CompanyVO updatedVO, int companySeq );
	CompanyVO findByCustomInfo( String DBName, String value );
	CompanyVO findByCustomInfo( String DBName, int Value);
	String id_overlap_chk(String id);
	String sendEmailForId(String lastName, String firstName, String email);
	String send_pw_mail_service(String myId, String myMail);
	CompanyVO getCompanyInfo(String companyId);
	String changePwChk(MemberVO m, String userID, String inputPW);
	CompanyVO selectByCompanyId( String companyId );
}
