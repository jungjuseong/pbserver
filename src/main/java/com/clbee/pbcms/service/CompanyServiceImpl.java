package com.clbee.pbcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clbee.pbcms.dao.CompanyDao;
import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.MemberVO;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
	
	
	@Autowired CompanyDao dao;

	@Override
	public String id_overlap_chk(String id) {
		// TODO Auto-generated method stub
		return dao.id_overlap_chk(id);
	}

	@Override
	public CompanyVO findByCustomInfo(String DBName, String value) {
		
		return dao.findByCustomInfo(DBName, value);
	}

	@Override
	public CompanyVO findByCustomInfo(String DBName, int value) {
		
		return dao.findByCustomInfo(DBName, value);
	}
	
	@Override
	public String sendEmailForId(String lastName, String firstName, String email){
		 return dao.selectIdByUserNameAndEmail(lastName, firstName, email);
	}
	
	@Override
	public String send_pw_mail_service(String myId, String myMail){
		return dao.send_pw_mail(myId, myMail);
	}
	
	@Override
	public CompanyVO getCompanyInfo(String companyID) {
		return dao.getComInfo(companyID);
	}

	@Override
	public int updateCompanyInfo( CompanyVO companyVO, int companySeq) {
		return dao.updateCompanyInfo( companyVO , companySeq );
	}

	@Override
	public String changePwChk(MemberVO m, String userID, String inputPW) {
		return dao.changePwChk(m, userID, inputPW);
	}
	
	@Override
	public CompanyVO selectByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return dao.selectByCompanyId(companyId);
	}

	@Override
	public int insertCompanyInfoWithProcedure(CompanyVO companyVO) {
		// TODO Auto-generated method stub
		return dao.insertCompanyInfoWithProcedure(companyVO);
	}
}
