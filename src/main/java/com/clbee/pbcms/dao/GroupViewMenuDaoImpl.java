package com.clbee.pbcms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clbee.pbcms.vo.GroupMenuVO;
import com.clbee.pbcms.vo.GroupUserVO;

@Repository
public class GroupViewMenuDaoImpl implements GroupViewMenuDao {

	@Autowired
	private SqlSession sqlSession;
	
	public GroupUserVO selectViewMenuInfo( String DBName, String groupSeq ){
		return sqlSession.selectOne(DBName, Integer.parseInt(groupSeq));
	}
	
	public List<GroupMenuVO> selectMenu( String DBName, String menu_type, List<Integer> menu_large ){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuType", menu_type);
		paramMap.put("menuLarge", menu_large);
		
		return sqlSession.selectList(DBName, paramMap);
	}
	
}