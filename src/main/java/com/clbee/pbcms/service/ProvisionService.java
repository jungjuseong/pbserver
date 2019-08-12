package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ProvisionVO;

public interface ProvisionService {
	int getListCount();
	List<ProvisionVO> selectList(int regUserSeq, int regCompanySeq);
	void delete(int provSeq, int regUserSeq, int regCompanySeq);
	ProvisionVO selectRow(int provSeq, int regUserSeq, int regCompanySeq) throws Exception;
	int update(ProvisionVO vo);
	void insert(ProvisionVO vo);
	List<ProvisionVO>  selectList(ProvisionVO vo, int appSeq);
	ProvisionVO findByCustomInfo( String DBName, String value);
	ProvisionVO findByCustomInfo( String DBName, int value);
	
	List<ProvisionVO> selectProvisionInfoByAppSeqForMakePbapp( int appSeq );
}
