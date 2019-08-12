package com.clbee.pbcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.ProvisionDao;
import com.clbee.pbcms.vo.ProvisionVO;

@Service
public class ProvisionServiceImpl implements ProvisionService {
	@Autowired ProvisionDao dao;

	@Override
	public int getListCount() {
		// TODO Auto-generated method stub
		return dao.getListCount();
	}

	@Override
	public List<ProvisionVO> selectList(int regUserSeq, int regCompanySeq) {
		// TODO Auto-generated method stub
		return dao.selectList(regUserSeq, regCompanySeq);
	}

	@Override
	public void delete(int provSeq, int regUserSeq, int regCompanySeq) {
		// TODO Auto-generated method stub
		dao.delete(provSeq, regUserSeq, regCompanySeq);		
	}

	@Override
	public ProvisionVO selectRow(int provSeq, int regUserSeq, int regCompanySeq) throws Exception {
		// TODO Auto-generated method stub
		
		return dao.selectRow(provSeq, regUserSeq, regCompanySeq);		
	}

	@Override
	public int update(ProvisionVO vo) {
		// TODO Auto-generated method stub
		return dao.update(vo);		
	}

	@Override
	public void insert(ProvisionVO vo) {
		// TODO Auto-generated method stub
		dao.insert(vo);
		
	}

	@Override
	public List<ProvisionVO> selectList(ProvisionVO vo, int appSeq) {
		// TODO Auto-generated method stub
		return dao.selectList(vo, appSeq);
	}

	@Override
	public ProvisionVO findByCustomInfo(String DBName, String value) {
		// TODO Auto-generated method stub
		return dao.findByCustomInfo(DBName, value);
	}

	@Override
	public ProvisionVO findByCustomInfo(String DBName, int value) {
		// TODO Auto-generated method stub
		return dao.findByCustomInfo(DBName, value);
	}

	@Override
	public List<ProvisionVO> selectProvisionInfoByAppSeqForMakePbapp(int appSeq) {
		// TODO Auto-generated method stub
		return dao.selectProvisionInfoByAppSeqForMakePbapp("selectProvisionInfoByAppSeqForMakePbapp", appSeq);
	}
	


}
