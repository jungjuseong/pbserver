package com.clbee.pbcms.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.GroupList;
import com.clbee.pbcms.vo.GroupMenuVO;
import com.clbee.pbcms.vo.GroupUserVO;

@Repository
public class GroupDaoImpl implements GroupDao {

	@Autowired
	private SqlSession sqlSession;
	
  	@Override
	public List<GroupMenuVO> selectMenu(String DBName, String menu_type ) {
		// TODO Auto-generated method stub		
		return sqlSession.selectList(DBName, menu_type);
	}

	@Override
	public void insertGroupUser(String DBName, GroupUserVO groupUserVO) {
		// TODO Auto-generated method stub
		sqlSession.insert(DBName, groupUserVO);
	}

	@Override
	public List<GroupUserVO> selectList(String DBName, GroupList groupList ) {
		// TODO Auto-generated method stub		
		return sqlSession.selectList(DBName, groupList);
	}

	@Override
	public int totalCount( String DBName, GroupList groupList ){
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, groupList);
	}

	@Override
	public int groupNameOverlap(String DBName, String groupName) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, groupName);
	}

	@Override
	public int deleteCheck(String DBName, int numGroupSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, numGroupSeq);
	}

	@Override
	public int deleteGroup(String DBName, int numGroupSeq) {
		// TODO Auto-generated method stub
		return sqlSession.delete(DBName, numGroupSeq);
	}

	@Override
	public GroupUserVO selectGroupInfo(String DBName, int groupSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(DBName, groupSeq);
	}

	@Override
	public void updateGroupUser(String DBName, GroupUserVO groupUserVO) {
		// TODO Auto-generated method stub
		sqlSession.update(DBName, groupUserVO);
	}

	@Override
	public List<GroupUserVO> getSelectListGroup(String DBName, int companySeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(DBName, companySeq);
	}
	
}