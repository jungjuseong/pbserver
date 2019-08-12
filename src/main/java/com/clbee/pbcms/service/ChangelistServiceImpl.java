package com.clbee.pbcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.ChangelistDao;
import com.clbee.pbcms.vo.ChangelistVO;

@Service
public class ChangelistServiceImpl implements ChangelistService{

	@Autowired ChangelistDao changelilstDao;
	
	@Override
	public int getSeqAfterInsertChangelist(ChangelistVO changelistVO) {
		// TODO Auto-generated method stub
		return changelilstDao.getSeqAfterInsertChangelist(changelistVO);
	}
}
