package com.clbee.pbcms.util;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MySpecialAuthDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public String selectFirstUrl( String groupSeq){
		return sqlSession.selectOne("selectFirstUrl", Integer.parseInt(groupSeq));
	}
	
}