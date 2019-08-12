package com.clbee.pbcms.service;

import java.util.List;

import com.clbee.pbcms.vo.GroupList;
import com.clbee.pbcms.vo.GroupMenuVO;
import com.clbee.pbcms.vo.GroupUserVO;

//20180424 : lsy - group management create

public interface GroupService {	
	List<GroupMenuVO> selectMenu( String menu_type );
	void insertGroupUser( GroupUserVO groupUserVO );
	GroupList selectList( GroupList groupList );
	int groupNameOverlap ( String groupName );
	int deleteGroup( int numGroupSeq );
	GroupUserVO selectGroupInfo( int groupSeq );
	void updateGroupUser( GroupUserVO groupUserVO );
	List<GroupUserVO> getSelectListGroup( int companySeq );
}