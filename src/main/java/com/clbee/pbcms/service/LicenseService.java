package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.Json.ConnectLicenseInfo;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.LicenseList;
import com.clbee.pbcms.vo.LicenseSubList;
import com.clbee.pbcms.vo.LicenseVO;

public interface LicenseService {
	int checkUseLicense( int userSeq );
	LicenseList selectList( LicenseList licenseList );
	int makeLicense( LicenseVO licenseVO );
	int disposalLicense( Entity param );
	int licenseRegistCheck( String license );
	void licenseRegist( int userSeq, String license);
	
	LicenseList selectMyList( LicenseList licenseList, int userSeq );
	List<LicenseVO> selectLicenseForRenew( int userSeq );
	void licenseExpire( int licenseSeq );
	
	LicenseSubList selectLicenseUseDevice( LicenseSubList licenseUseDevice );
	int deleteLicenseUseDevice( int licensesubSeq );
	
	ConnectLicenseInfo licenseAuthCheckWithAccount( ConnectLicenseInfo connectLicenseInfo );
	ConnectLicenseInfo licenseUserRegist( ConnectLicenseInfo connectLicenseInfo );
	ConnectLicenseInfo licenseAuthCheckWithDevice( ConnectLicenseInfo connectLicenseInfo );
	
	String selectLicenseDisposalReason( int licenseSeq );
	
	String selectLicenseUseCompanyName( int userSeq );
}