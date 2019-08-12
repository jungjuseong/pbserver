package com.clbee.pbcms.dao;

import java.util.List;

import com.clbee.pbcms.vo.GroupList;
import com.clbee.pbcms.vo.GroupMenuVO;
import com.clbee.pbcms.vo.GroupUserVO;

public interface GroupDao {
	List<GroupMenuVO> selectMenu( String DBName, String menu_type );
	void insertGroupUser ( String DBName, GroupUserVO groupUserVO );
	List<GroupUserVO> selectList( String DBName, GroupList groupList );
	int totalCount( String DBName, GroupList groupList );
	int groupNameOverlap ( String DBName, String groupName );
	
	int deleteCheck( String DBName, int numGroupSeq );
	int deleteGroup( String DBName, int numGroupSeq );
	GroupUserVO selectGroupInfo( String DBName, int groupSeq );
	void updateGroupUser ( String DBName, GroupUserVO groupUserVO );
	List<GroupUserVO> getSelectListGroup( String DBName, int companySeq );
}