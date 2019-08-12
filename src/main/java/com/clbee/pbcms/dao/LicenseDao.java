package com.clbee.pbcms.dao;

import java.util.HashMap;
import java.util.List;

import com.clbee.pbcms.Json.ConnectLicenseInfo;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.LicenseList;
import com.clbee.pbcms.vo.LicenseSubList;
import com.clbee.pbcms.vo.LicenseSubVO;
import com.clbee.pbcms.vo.LicenseVO;
import com.clbee.pbcms.vo.MemberVO;

public interface LicenseDao {
	int checkUseLicense( String DBName, int userSeq );
	List<LicenseVO> selectList( String DBName, LicenseList licenseList );
	int totalCount( String DBName, LicenseList licenseList );
	int dupleCheck( String DBName, String license );
	void insertLicense( String DBName, LicenseVO licenseVO );
	int disposalLicense( String DBName, Entity param );
	List<LicenseVO> licenseRegistCheck( String DBName, String license );
	void licenseRegist( String DBName, LicenseVO vo);

	List<LicenseVO> selectMyLicense( String DBName, int userSeq );
	int licenseExpire( String DBName, int licenseSeq );
	void licenseExpireEveryday( String DBName );

	int totalCountDevice( String DBName, LicenseSubList licenseUseDevice );
	List<LicenseSubVO> selectListDevice( String DBName, LicenseSubList licenseUseDevice );
	int deleteLicenseUseDevice( String DBName, int licensesubSeq );
	int deleteLicenseSub( String DBName, int licenseSeq );

	List<MemberVO> checkAccountStatus( String DBName, ConnectLicenseInfo connectLicenseInfo );
	List<LicenseVO> checkRegistLicenseWithAccount( String DBName, MemberVO account );	
	void licenseUserRegist( String DBName, ConnectLicenseInfo connectLicenseInfo);
	List<ConnectLicenseInfo> licenseAuthCheckWithDevice( String DBName, ConnectLicenseInfo connectLicenseInfo );
	
	int checkAccountStatusByUserSeq( String DBName, int userSeq );
	int checkLicenseStatusByLicenseSeq( String DBName, int licenseSeq );
	
	String selectLicenseDisposalReason( String DBName, int licenseSeq );
	int getLicenseUserCount( String DBName, int licenseSeq );

	boolean checkLicenseUserExist( String DBName, int userSeq );
	void deleteLicenseSubByUserSeq( String DBName, int userSeq );
	List<HashMap<String, String>> licenseExpireAlertEveryday(String DBName);
	
	String selectLicenseUseCompanyName( String DBName, int userSeq );
}