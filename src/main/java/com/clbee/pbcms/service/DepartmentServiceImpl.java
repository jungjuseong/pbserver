package com.clbee.pbcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.DepartmentDao;
import com.clbee.pbcms.vo.DepartmentVO;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentDao departmentDao;
	
	@Override
	public int insertDepartmentInfo( DepartmentVO departmentVO ) {
		// TODO Auto-generated method stub
		return departmentDao.insertDepartmentInfo( departmentVO );
	}

	@Override
	public void updateDepartmentInfo( DepartmentVO departmentVO ) {
		// TODO Auto-generated method stub
		departmentDao.updateDepartmentInfo(departmentVO);
	}

	@Override
	public List<DepartmentVO> selectList( int companySeq, String toUse ) {
		// TODO Auto-generated method stub
		
		return departmentDao.selectList( companySeq, toUse );
	}

	@Override
	public Object[] selectChildList(int parentSeq, int companySeq, String toUse) {
		// TODO Auto-generated method stub
		return departmentDao.selectChildList( parentSeq,companySeq, toUse );
	}

	@Override
	public List<DepartmentVO> selectChildArrayList(int parentSeq, int companySeq) {
		// TODO Auto-generated method stub
		return departmentDao.selectChildArrayList(parentSeq, companySeq);
	}
}
