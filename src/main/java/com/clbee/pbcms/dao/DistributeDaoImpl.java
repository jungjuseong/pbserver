package com.clbee.pbcms.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InappVO;

@Repository
public class DistributeDaoImpl implements DistributeDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<AppVO> selectAppList( String DBName, Entity param){
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, param);
	}
	
	@Override
	public List<InappVO> selectInappList( String DBName, Entity param){
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, param);
	}

	@Override
	public List<ContentVO> selectContentsList( String DBName, Entity param){
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, param);
	}

	@Override
	public int totalCount( String DBName, Entity param ){
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, param);
	}
/*
	@Override
	public List<AppVO> selectAppList( String DBName, int startNum){
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, startNum);
	}
	
	@Override
	public List<InappVO> selectInappList( String DBName, int startNum){
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, startNum);
	}

	@Override
	public List<ContentVO> selectContentsList( String DBName, int startNum){
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, startNum);
	}

	@Override
	public int totalCount( String DBName, String obj ){
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, obj);
	}
*/
	
	@Override
	public DistributeRestoreVO selectDistributeReqInfo( String DBName, DistributeRestoreVO distributeRestoreVo ) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, distributeRestoreVo);
	}
	
	@Override
	public void insertDistributeRestore( String DBName, DistributeRestoreVO distributeRestoreVo ) {
		// TODO Auto-generated method stub
		sqlSession.insert(DBName, distributeRestoreVo);
	}
	
	@Override
	public void updateRestoreStatus( String DBName, DistributeRestoreVO distributeRestoreVo ) {
		// TODO Auto-generated method stub
		sqlSession.update(DBName, distributeRestoreVo);
	}
	
  	@Override
	public List<DistributeRestoreVO> selectForRestore(String DBName, DistributeRestoreVO distributeRestoreVo ) {
		// TODO Auto-generated method stub		
		return sqlSession.selectList(DBName, distributeRestoreVo);
	}
	
}