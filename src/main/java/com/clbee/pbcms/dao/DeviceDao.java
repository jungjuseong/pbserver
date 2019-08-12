package com.clbee.pbcms.dao;

import java.util.List;

import com.clbee.pbcms.vo.DeviceVO;

public interface DeviceDao {
	int insertDeviceInfo( DeviceVO deviceVO );
	int updateDeviceInfo( DeviceVO deviceVO );
	DeviceVO selectDeviceInfo ( int deviceSeq );
	List<DeviceVO> selectDeviceList(int startNo, int companySeq, String searchType, String searchValue);
	int selectDeviceListCount( int companySeq, String searchType, String searchValue);
	int checkIfExistUUID ( String deviceUuid, int companySeq );
	int countDeviceIsAvailable ( int companySeq );
}
