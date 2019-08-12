package com.clbee.pbcms.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.BundleDao;
import com.clbee.pbcms.dao.ProvisionDao;
import com.clbee.pbcms.vo.BundleVO;
import com.clbee.pbcms.vo.ProvisionVO;

@Service
public class BundleServiceImpl implements BundleService {
	@Autowired BundleDao dao;
	
	@Override
	public void delete(int seq) {
		// TODO Auto-generated method stub
		dao.deleteByAppSeq(seq);
	}
	
	@Override
	public void insert(BundleVO vo) {
		// TODO Auto-generated method stub
		dao.insert(vo);
	}
	
	@Override
	public int getListCount(BundleVO vo, int companySeq ) {
		// TODO Auto-generated method stub
		return dao.getListCount(vo, companySeq);
	}	
	
	@Override
	public List listByAppSeq(Integer appSeq) {
		// TODO Auto-generated method stub
		return dao.listByAppSeq(appSeq);
	}
	
	@Override
	public List<BundleVO> selectBeforeBundleInfoByAppSeqForNewVersion(int app_seq) {
		// TODO Auto-generated method stub
		return dao.selectBeforeBundleInfoByAppSeqForNewVersion("selectBeforeBundleInfoByAppSeqForNewVersion", app_seq);
	}
	
	@Override
	public void insertNewVersionAppBundleInfo(BundleVO newVersionAppBundleInfo) {
		// TODO Auto-generated method stub
		dao.insertNewVersionAppBundleInfo("insertNewVersionAppBundleInfo", newVersionAppBundleInfo);
	}

	@Override
	public List<HashMap> selectBundleInfoByAppSeqForMakePbapp(BundleVO bundleParam) {
		// TODO Auto-generated method stub
		return dao.selectBundleInfoByAppSeqForMakePbapp("selectBundleInfoByAppSeqForMakePbapp", bundleParam);
	}

}