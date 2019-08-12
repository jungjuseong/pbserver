package com.clbee.pbcms.dao;

import java.util.List;

import com.clbee.pbcms.vo.DepartmentVO;

public interface DepartmentDao {
	int insertDepartmentInfo( DepartmentVO departmentVO );
	void updateDepartmentInfo( DepartmentVO departmentVO );
	List<DepartmentVO> selectList( int companySeq, String toUse );
	Object[] selectChildList ( int parentSeq, int companySeq, String toUse );
	List<DepartmentVO> selectChildArrayList ( int parentSeq, int companySeq );
}