package com.clbee.pbcms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.Json.ConnectLicenseInfo;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.LicenseList;
import com.clbee.pbcms.vo.LicenseSubList;
import com.clbee.pbcms.vo.LicenseSubVO;
import com.clbee.pbcms.vo.LicenseVO;
import com.clbee.pbcms.vo.MemberVO;

@Repository
public class LicenseDaoImpl implements LicenseDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int checkUseLicense(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, userSeq);
	}

	@Override
	public List<LicenseVO> selectList(String DBName, LicenseList licenseList ) {
		// TODO Auto-generated method stub		
		return sqlSession.selectList(DBName, licenseList);
	}

	@Override
	public int totalCount( String DBName, LicenseList licenseList ){
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, licenseList);
	}

	@Override
	public int dupleCheck(String DBName, String license) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, license);
	}

	@Override
	public void insertLicense(String DBName, LicenseVO licenseVO) {
		// TODO Auto-generated method stub
		sqlSession.insert(DBName, licenseVO);
	}

	@Override
	public int disposalLicense(String DBName, Entity param) {
		// TODO Auto-generated method stub
		return sqlSession.update(DBName, param);
	}

	@Override
	public List<LicenseVO> licenseRegistCheck(String DBName, String license) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, license);
	}

	@Override
	public void licenseRegist(String DBName, LicenseVO vo) {
		// TODO Auto-generated method stub
		sqlSession.update(DBName, vo);
	}

	@Override
	public List<LicenseVO> selectMyLicense(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, userSeq);
	}

	@Override
	public int licenseExpire(String DBName, int licenseSeq) {
		// TODO Auto-generated method stub
		return sqlSession.update(DBName, licenseSeq);
	}

	@Override
	public void licenseExpireEveryday(String DBName) {
		// TODO Auto-generated method stub
		sqlSession.update(DBName);
	}

	@Override
	public int totalCountDevice(String DBName, LicenseSubList licenseUseDevice) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, licenseUseDevice);
	}

	@Override
	public List<LicenseSubVO> selectListDevice(String DBName, LicenseSubList licenseUseDevice) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, licenseUseDevice);
	}

	@Override
	public int deleteLicenseUseDevice(String DBName, int licensesubSeq) {
		// TODO Auto-generated method stub
		return sqlSession.delete(DBName, licensesubSeq);
	}

	@Override
	public int deleteLicenseSub(String DBName, int licenseSeq) {
		// TODO Auto-generated method stub
		return sqlSession.delete(DBName, licenseSeq);
	}

	@Override
	public List<MemberVO> checkAccountStatus(String DBName, ConnectLicenseInfo connectLicenseInfo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, connectLicenseInfo);
	}

	@Override
	public List<LicenseVO> checkRegistLicenseWithAccount(String DBName, MemberVO account) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, account);
	}

	@Override
	public void licenseUserRegist(String DBName, ConnectLicenseInfo connectLicenseInfo) {
		// TODO Auto-generated method stub
		sqlSession.insert(DBName, connectLicenseInfo);
	}

	@Override
	public String selectLicenseDisposalReason(String DBName, int licenseSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, licenseSeq);
	}

	@Override
	public int getLicenseUserCount(String DBName, int licenseSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, licenseSeq);
	}

	@Override
	public boolean checkLicenseUserExist(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		int checkCount = sqlSession.selectOne(DBName, userSeq);
		if(checkCount > 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void deleteLicenseSubByUserSeq(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		sqlSession.delete(DBName, userSeq);
	}

	@Override
	public List<ConnectLicenseInfo> licenseAuthCheckWithDevice(String DBName, ConnectLicenseInfo connectLicenseInfo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, connectLicenseInfo);
	}

	@Override
	public int checkAccountStatusByUserSeq(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, userSeq);
	}

	@Override
	public int checkLicenseStatusByLicenseSeq(String DBName, int licenseSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, licenseSeq);
	}

	@Override
	public List<HashMap<String, String>> licenseExpireAlertEveryday(String DBName) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName);
	}

	@Override
	public String selectLicenseUseCompanyName(String DBName, int userSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, userSeq);
	}
	
}