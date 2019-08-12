package com.clbee.pbcms.dao;

import java.util.List;


import com.clbee.pbcms.vo.ProvisionVO;;

public interface ProvisionDao {
	void insert( ProvisionVO vo );
	int update( ProvisionVO vo );
	List<ProvisionVO> selectList(int regUserSeq, int regCompanySeq);
	int getListCount();
	void delete(int provSeq, int regUserSeq, int regCompanySeq);
	ProvisionVO selectRow(int provSeq, int regUserSeq, int regCompanySeq) throws Exception;
	List selectList(ProvisionVO vo, int appSeq);
	ProvisionVO findByCustomInfo( String DBName, int value );
	ProvisionVO findByCustomInfo( String DBName, String value );
	
	List<ProvisionVO> selectProvisionInfoByAppSeqForMakePbapp( String DBName, int appSeq );
}
