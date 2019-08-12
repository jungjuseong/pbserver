package com.clbee.pbcms.service;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.Json.ConnectLicenseInfo;
import com.clbee.pbcms.dao.LicenseDao;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.ShaPassword;
import com.clbee.pbcms.vo.LicenseList;
import com.clbee.pbcms.vo.LicenseSubList;
import com.clbee.pbcms.vo.LicenseSubVO;
import com.clbee.pbcms.vo.LicenseVO;
import com.clbee.pbcms.vo.MemberVO;

//20180906 : lsy - license menu develop
@Service
public class LicenseServiceImpl implements LicenseService {

	@Autowired
	LicenseDao licenseDao;
	
	@Autowired
	ShaPassword shaPassword;

	int pageSize = 10;
	int maxResult = 10;
	int totalCount = 0;
	int startNo = 0;
	
	int pageSize_device = 5;
	int maxResult_device = 5;

	@Override
	public int checkUseLicense(int userSeq) {
		// TODO Auto-generated method stub
		return licenseDao.checkUseLicense("checkUseLicense", userSeq);
	}

	@Override
	public LicenseList selectList( LicenseList licenseList ) {
		// TODO Auto-generated method stub
		if(licenseList.getSearchMember() == "") {
			licenseList.setSearchMember(null);
		}
		
		totalCount = licenseDao.totalCount("totalCountLicense", licenseList);
		licenseList.calc(pageSize, totalCount, licenseList.getCurrentPage(), maxResult);
		
		List<LicenseVO> vo = licenseDao.selectList("selectListLicense", licenseList);
		
		String licenseView;
		for(int i=0;i<vo.size();i++) {
			licenseView = "";
			for(int j=0;j<vo.get(i).getLicenseNum().length();j++) {
				if( (j != 0) && (j % 5 == 0) ) {
					licenseView += "-";
				}
				licenseView += vo.get(i).getLicenseNum().charAt(j);
			}
			vo.get(i).setLicenseNum(licenseView);
		}
		
		licenseList.setList(vo);
		return licenseList;
	}

	@Override
	public int makeLicense( LicenseVO licenseVO ) {
		// TODO Auto-generated method stub
		String license = "";
		int dupleCheck = 1;
		
		while(dupleCheck != 0) {
			license = "";
			license = makeLicenseNum();
			dupleCheck = licenseDao.dupleCheck("dupleCheck", license);
		}

		if( (license != null && license != "") && dupleCheck == 0 ) {
			licenseVO.setLicenseNum(license);
			licenseVO.setLicenseStatus("1");
			
			try {
				licenseDao.insertLicense("insertLicense", licenseVO);			
				return 1;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
		}else {
			return 0;	
		}
	}
	
	public String makeLicenseNum() {
		Calendar now = Calendar.getInstance();
		SecureRandom random = new SecureRandom();

		int idxLicense = 0, idxUUID = 0;
		if( (now.get(Calendar.MINUTE) % 2) == 1 ) {
			idxUUID = 1;
		}

		int[] idxRandom = new int[4];
		for(int i=0;i<idxRandom.length;i++) {
			idxRandom[i]= (i * 5) + random.nextInt(5);
		}

		String license = "", tempUUID = UUID.randomUUID().toString().replaceAll("-", ""), randomCharacter = "0123456789abcdefghijklmnopqrstuvwxyz";;
		char[] arrayUUID = tempUUID.toCharArray();
		while(idxLicense < 20) {
			if( checkIdx(idxLicense, idxRandom) ) {
				license += randomCharacter.charAt(random.nextInt(36));
			}else {
				license += arrayUUID[idxUUID];
				idxUUID += 2;
			}
			idxLicense += 1;
		}
		
		return license.toUpperCase();
	}
	
	public boolean checkIdx(int idxLicense, int[] idxRandom) {
		for(int j=0;j<idxRandom.length;j++) {
			if(idxLicense == idxRandom[j]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int disposalLicense(Entity param) {
		// TODO Auto-generated method stub
		if(licenseDao.disposalLicense("disposalLicense", param) > 0) {
			try {
				licenseDao.deleteLicenseSub("deleteLicenseSub", param.getInt("licenseSeq"));
				return 1;
			}catch(Exception e) {
				e.printStackTrace();
				return 0;
			} 
		}else {
			return 0;
		}
	}

	@Override
	public int licenseRegistCheck(String license) {
		// TODO Auto-generated method stub
		//checkResult - 0:해당 라이센스 없음, 1:정상, 2:라이센스 사용에 문제있음(중복), 3:사용 불가능한 라이센스(라이센스 상태 문제), 4:이미 쓰고있는 라이센스 
		int checkResult = 0;
		List<LicenseVO> vo = null;
		
		license = license.toUpperCase();
		vo = licenseDao.licenseRegistCheck("licenseRegistCheck", license);
		
		if(vo.size() == 1) {
			if(Integer.parseInt(vo.get(0).getLicenseStatus()) == 1) {
				checkResult = 1;
			}else if(Integer.parseInt(vo.get(0).getLicenseStatus()) == 2) {
				checkResult = 4;
			}else {
				checkResult = 3;
			}
		}else if(vo.size() > 1) {
			checkResult = 2;
		}else {
			checkResult = 0;
		}
		
		return checkResult;
	}

	@Override
	public void licenseRegist(int userSeq, String license) {
		// TODO Auto-generated method stub
		license = license.toUpperCase();
		
		LicenseVO vo = new LicenseVO();
		vo.setLicenseUserSeq(userSeq);
		vo.setLicenseNum(license);
		
		licenseDao.licenseRegist("licenseRegist", vo);
	}

	@Override
	public LicenseList selectMyList(LicenseList licenseList, int userSeq) {
		// TODO Auto-generated method stub
		List<LicenseVO> vo = null;
		vo = licenseDao.selectMyLicense("selectMyLicense", userSeq);
		
		String licenseView;
		for(int i=0;i<vo.size();i++) {
			licenseView = "";
			for(int j=0;j<vo.get(i).getLicenseNum().length();j++) {
				if( (j != 0) && (j % 5 == 0) ) {
					licenseView += "-";
				}
				licenseView += vo.get(i).getLicenseNum().charAt(j);
			}
			vo.get(i).setLicenseNum(licenseView);
		}
		
		licenseList.setList(vo);
		return licenseList;
	}

	@Override
	public List<LicenseVO> selectLicenseForRenew(int userSeq) {
		// TODO Auto-generated method stub
		return licenseDao.selectMyLicense("selectMyLicense", userSeq);
	}

	@Override
	public void licenseExpire(int licenseSeq) {
		// TODO Auto-generated method stub
		if(licenseDao.licenseExpire("licenseExpire", licenseSeq) > 0) {
			licenseDao.deleteLicenseSub("deleteLicenseSub", licenseSeq);
		}
	}

	@Override
	public LicenseSubList selectLicenseUseDevice(LicenseSubList licenseUseDevice) {
		// TODO Auto-generated method stub		
		totalCount = licenseDao.totalCountDevice("totalCountDevice", licenseUseDevice);
		licenseUseDevice.calc(pageSize_device, totalCount, licenseUseDevice.getCurrentPage(), maxResult_device);
		
		List<LicenseSubVO> vo = licenseDao.selectListDevice("selectListDevice", licenseUseDevice);		
		licenseUseDevice.setList(vo);
		
		return licenseUseDevice;
	}

	@Override
	public int deleteLicenseUseDevice(int licensesubSeq) {
		// TODO Auto-generated method stub
		return licenseDao.deleteLicenseUseDevice("deleteLicenseUseDevice", licensesubSeq);
	}

	@Override
	public ConnectLicenseInfo licenseAuthCheckWithAccount(ConnectLicenseInfo connectLicenseInfo) {
		// TODO Auto-generated method stub
		List<MemberVO> account = licenseDao.checkAccountStatus("checkAccountStatus", connectLicenseInfo);
		
		if(account.size() == 1) {//1. 계정 유무 체크
			if(account.get(0).getUserStatus().equals("4")) {//2. 계정 상태 체크(사용중인것만)
				connectLicenseInfo.setUserSeq(account.get(0).getUserSeq());
				List<LicenseVO> license = licenseDao.checkRegistLicenseWithAccount("checkRegistLicenseWithAccount", account.get(0));//3. 해당 계정에 등록되어 있는 라이센스 상태 체크
				
				if(license.size() == 1) {
					connectLicenseInfo.setLicenseSeq(license.get(0).getLicenseSeq());
					connectLicenseInfo.setUserCopyCount(license.get(0).getUserCopyCount());
					connectLicenseInfo.setResultCode("6000");
				}else if(license.size() > 1) {
					connectLicenseInfo.setResultCode("6004");
				}else {
					connectLicenseInfo.setResultCode("6003");
				}
			}else {
				connectLicenseInfo.setResultCode("6002");
			}
		}else if(account.size() > 1) {
			connectLicenseInfo.setResultCode("6002");
		}else {
			connectLicenseInfo.setResultCode("6001");
		}
		
		return connectLicenseInfo;
	}

	@Override
	public ConnectLicenseInfo licenseUserRegist(ConnectLicenseInfo connectLicenseInfo) {
		// TODO Auto-generated method stub
		try {
			//4. 해당 계정(회원/사용자)으로 tb_license_sub에 등록된 내역이 있는지 확인 -> 있다면 이전꺼 삭제 (해당 계정의 새로운 디바이스 정보로 등록할 예정)
			if(licenseDao.checkLicenseUserExist("checkLicenseUserExist", connectLicenseInfo.getUserSeq())) {
				licenseDao.deleteLicenseSubByUserSeq("deleteLicenseSubByUserSeq", connectLicenseInfo.getUserSeq());
			}
			
			//5. 해당 계정(회원)의 Copy수 체크 -> Copy 여유가 있을 때만 사용자(디바이스) 등록할 수 있음
			if( licenseDao.getLicenseUserCount("getLicenseUserCount", connectLicenseInfo.getLicenseSeq()) <	connectLicenseInfo.getUserCopyCount() ) {
				licenseDao.licenseUserRegist("licenseUserRegist", connectLicenseInfo);
				connectLicenseInfo.setResultCode("6000");
			}else {
				connectLicenseInfo.setResultCode("6005");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			connectLicenseInfo.setResultCode("6004");
		}
		return connectLicenseInfo;
	}

	@Override
	public ConnectLicenseInfo licenseAuthCheckWithDevice(ConnectLicenseInfo connectLicenseInfo) {
		// TODO Auto-generated method stub
		//1. 계정 유무 체크
		List<MemberVO> account = licenseDao.checkAccountStatus("checkAccountStatus", connectLicenseInfo);
		if(account.size() == 1) {
			//2. deviceSeq로 사용자 등록여부 확인(== Copy 체크)
			List<ConnectLicenseInfo> checkData = licenseDao.licenseAuthCheckWithDevice("licenseAuthCheckWithDevice", connectLicenseInfo); 
	
			if(checkData.size() == 1) {
				connectLicenseInfo.setResultCode("6000");
			}else {
				connectLicenseInfo.setResultCode("6006");
			}
		}else if(account.size() > 1) {
			connectLicenseInfo.setResultCode("6002");
		}else {
			connectLicenseInfo.setResultCode("6001");
		}
		
		return connectLicenseInfo;
	}

	@Override
	public String selectLicenseDisposalReason(int licenseSeq) {
		// TODO Auto-generated method stub
		return licenseDao.selectLicenseDisposalReason("selectLicenseDisposalReason", licenseSeq);
	}

	@Override
	public String selectLicenseUseCompanyName(int userSeq) {
		// TODO Auto-generated method stub
		return licenseDao.selectLicenseUseCompanyName("selectLicenseUseCompanyName", userSeq);
	}
	
}