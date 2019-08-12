package com.clbee.pbcms.service;

import java.util.HashMap;
import java.util.List;

import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.BundleVO;
import com.clbee.pbcms.vo.ProvisionVO;

public interface BundleService {	
	void delete(int seq);
	void insert(BundleVO vo);
	List listByAppSeq(Integer appSeq);
	int getListCount(BundleVO vo, int companySeq);
	
	List<BundleVO> selectBeforeBundleInfoByAppSeqForNewVersion(int app_seq);
	void insertNewVersionAppBundleInfo(BundleVO newVersionAppBundleInfo);
	
	List<HashMap> selectBundleInfoByAppSeqForMakePbapp(BundleVO bundleParam);
}