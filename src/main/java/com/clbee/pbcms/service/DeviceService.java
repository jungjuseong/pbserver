package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.DeviceList;
import com.clbee.pbcms.vo.DeviceVO;

public interface DeviceService {

	int insertDeviceInfo( DeviceVO deviceVO );
	int updateDeviceInfo( DeviceVO deviceVO );
	DeviceVO selectDeviceInfo ( int deviceSeq );
	DeviceList selectDeviceList(int currentPage, int companySeq, String searchType, String searchValue);
	int checkIfExistUUID ( String deviceUuid, int companySeq );
	int countDeviceIsAvailable(int companySeq);
}
