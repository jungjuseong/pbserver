package com.clbee.pbcms.dao;

import java.util.List;

import com.clbee.pbcms.vo.GroupMenuVO;
import com.clbee.pbcms.vo.GroupUserVO;

public interface GroupViewMenuDao {
	GroupUserVO selectViewMenuInfo( String DBName, String groupSeq );
	List<GroupMenuVO> selectMenu( String DBName, String menu_type, List<Integer> menu_large );
}