package com.clbee.pbcms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clbee.pbcms.dao.CaptureDao;
import com.clbee.pbcms.vo.CaptureVO;

@Service
public class CaptureServiceImpl implements CaptureService {
	@Autowired CaptureDao dao;

	@Override
	public void insert(CaptureVO vo) {
		// TODO Auto-generated method stub
		dao.insert(vo);		
	}

	@Override
	public List<CaptureVO> selectListByBoardSeqWithGb(CaptureVO vo) {
		// TODO Auto-generated method stub
		return dao.selectListByBoardSeqWithGb(vo);
	}

	@Override
	public void delete(CaptureVO captureVO) {
		// TODO Auto-generated method stub
		dao.delete(captureVO);
	}
}
