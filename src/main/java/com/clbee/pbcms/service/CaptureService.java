package com.clbee.pbcms.service;

import java.util.List;
import com.clbee.pbcms.vo.CaptureVO;

public interface CaptureService {
	void insert(CaptureVO vo);
	List<CaptureVO> selectListByBoardSeqWithGb(CaptureVO vo);
	void delete(CaptureVO captureVO);
}
