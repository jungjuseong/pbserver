package com.clbee.pbcms.dao;

import java.util.List;


import com.clbee.pbcms.vo.CaptureVO;;

public interface CaptureDao {
	void insert(CaptureVO vo);
	List<CaptureVO> selectListByBoardSeqWithGb(CaptureVO vo);
	void delete(CaptureVO captureVO);
}
