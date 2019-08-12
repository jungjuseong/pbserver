package com.clbee.pbcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.DeviceDao;
import com.clbee.pbcms.vo.DeviceList;
import com.clbee.pbcms.vo.DeviceVO;

@Service
public class DeviceServiceImpl implements DeviceService {
	
	@Autowired
	DeviceDao deviceDao;

	@Override
	public int insertDeviceInfo(DeviceVO deviceVO) {
		// TODO Auto-generated method stub
		return deviceDao.insertDeviceInfo(deviceVO);
	}

	@Override
	public int updateDeviceInfo(DeviceVO deviceVO) {
		// TODO Auto-generated method stub
		return deviceDao.updateDeviceInfo(deviceVO);
	}

	@Override
	public DeviceVO selectDeviceInfo(int deviceSeq) {
		// TODO Auto-generated method stub
		return deviceDao.selectDeviceInfo(deviceSeq);
	}

	@Override
	public DeviceList selectDeviceList(int currentPage, int companySeq, String searchType, String searchValue) {
		// TODO Auto-generated method stub
		
		
		DeviceList list = null;
		// 전체 페이지 1,2,3,4,5,6,7,8,9,19밑에 파라미터 숫자가 표시될 갯수
		int pageSize = 10;
		// 한페이지에 최대 리스팅될 횟수
		int maxResult = 10; 
		int totalCount = 0;
		int startNo = 0;
		
			try{
				totalCount = deviceDao.selectDeviceListCount(companySeq, searchType, searchValue);
				System.out.println("totalCount = " + totalCount);
				
				list = new DeviceList(pageSize, totalCount, currentPage, maxResult);
			
				startNo = (currentPage-1) *maxResult;
	
				List<DeviceVO> vo = deviceDao.selectDeviceList(startNo, companySeq, searchType, searchValue);
				
				list.setList(vo);
				
				System.out.println("[ListService] - selectList method");
				System.out.println("selectList[] " + vo.size());
				System.out.println(vo.size());
			}catch(Exception e){
				System.out.println("에러");
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public int checkIfExistUUID(String deviceUuid, int companySeq) {
		// TODO Auto-generated method stub
		return deviceDao.checkIfExistUUID(deviceUuid, companySeq);
	}

	@Override
	public int countDeviceIsAvailable(int companySeq) {
		// TODO Auto-generated method stub
		return deviceDao.countDeviceIsAvailable(companySeq);
	}
}
