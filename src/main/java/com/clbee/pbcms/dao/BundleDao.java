package com.clbee.pbcms.dao;

import java.util.HashMap;
import java.util.List;


import com.clbee.pbcms.vo.BundleVO;
import com.clbee.pbcms.vo.ProvisionVO;;

public interface BundleDao {
	void insert(BundleVO vo);
	void deleteByAppSeq(int seq);
	List listByAppSeq(Integer appSeq);
	int getListCount(BundleVO vo, int companySeqe);

	List<BundleVO> selectBeforeBundleInfoByAppSeqForNewVersion(String DBName, int app_seq);
	void insertNewVersionAppBundleInfo(String DBName, BundleVO newVersionAppBundleInfo);
	
	List<HashMap> selectBundleInfoByAppSeqForMakePbapp(String DBName, BundleVO bundleParam);
}
