package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.DepartmentVO;

public interface DepartmentService {
	int insertDepartmentInfo( DepartmentVO departmentVO );
	void updateDepartmentInfo( DepartmentVO departmentVO );
	List<DepartmentVO> selectList( int companySeq, String toUse );
	Object[] selectChildList ( int parentSeq, int companySeq, String toUse );
	List<DepartmentVO> selectChildArrayList ( int parentSeq, int companySeq );
}
