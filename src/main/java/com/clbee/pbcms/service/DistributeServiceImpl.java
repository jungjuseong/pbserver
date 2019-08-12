package com.clbee.pbcms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.DistributeDao;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InAppList;
import com.clbee.pbcms.vo.InappVO;

//20180419 : lsy - distribute process develop

@Service
public class DistributeServiceImpl implements DistributeService {

	@Autowired
	DistributeDao distributeDao;

	int pageSize = 10;
	int maxResult = 10;
	int totalCount = 0;
	int startNo = 0;
	
	@Override
	public AppList selectAppList( Entity param ) {
		// TODO Auto-generated method stub
		AppList list = null;

		try{
			param.setValue("objectGb", "app");
			totalCount = distributeDao.totalCount("totalCount", param);
			
			list = new AppList(pageSize, totalCount, param.getInt("currentPage"), maxResult);		
			startNo = (param.getInt("currentPage") - 1) * 10;
			param.setValue("startNum", startNo);

			List<AppVO> vo = distributeDao.selectAppList("selectAppList", param);
			list.setList(vo);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public InAppList selectInappList( Entity param ) {
		// TODO Auto-generated method stub
		InAppList inAppList = null;

		try{
			param.setValue("objectGb", "inapp");
			totalCount = distributeDao.totalCount("totalCount", param);
			
			inAppList = new InAppList(pageSize, totalCount, param.getInt("currentPage"), maxResult);		
			startNo = (param.getInt("currentPage")-1) * 10;
			param.setValue("startNum", startNo);

			List<InappVO> vo = distributeDao.selectInappList("selectInappList", param);			
			inAppList.setList(vo);			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return inAppList;
	}
	
	@Override
	public ContentList selectContentsList( Entity param ) {
		// TODO Auto-generated method stub
		ContentList contentsList = null;

		try{
			param.setValue("objectGb", "contents");
			totalCount = distributeDao.totalCount("totalCount", param);
			
			contentsList = new ContentList(pageSize, totalCount, param.getInt("currentPage"), maxResult);		
			startNo = (param.getInt("currentPage")-1) * 10;
			param.setValue("startNum", startNo);

			List<ContentVO> vo = distributeDao.selectContentsList("selectContentsList", param);			
			contentsList.setList(vo);			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return contentsList;
	}
	
	@Override
	public DistributeRestoreVO selectDistributeReqInfo( DistributeRestoreVO distributeRestoreVo ) {
		return distributeDao.selectDistributeReqInfo("selectDistributeReqInfo", distributeRestoreVo);
	}
	
	@Override
	public void insertDistributeRestore( DistributeRestoreVO distributeRestoreVo ) {
		distributeDao.insertDistributeRestore("insertDistributeRestore", distributeRestoreVo);
	}
	
	@Override
	public void updateRestoreStatus( DistributeRestoreVO distributeRestoreVo ) {
		distributeDao.updateRestoreStatus("updateRestoreStatus", distributeRestoreVo);
	}
	
	@Override
	public List<DistributeRestoreVO> selectForRestore( DistributeRestoreVO distributeRestoreVo ) {
		return distributeDao.selectForRestore( "selectForRestore", distributeRestoreVo );
	}

	@Override
	public int getTotalCount(Entity param) {
		// TODO Auto-generated method stub
		return distributeDao.totalCount("totalCount", param);
	}
	
}