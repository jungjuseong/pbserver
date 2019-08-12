package com.clbee.pbcms.service;

import java.util.Map;

//20180424 : lsy - group management create

public interface GroupViewMenuService {	
	Map<String, Object> selectViewMenu( String menu_type, Map<String, Object> menuList );
}