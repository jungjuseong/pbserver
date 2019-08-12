package com.clbee.pbcms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clbee.pbcms.dao.GroupViewMenuDao;
import com.clbee.pbcms.vo.GroupMenuVO;
import com.clbee.pbcms.vo.GroupUserVO;

//20180515 : lsy - GroupViewMenu Util Create

@Service
public class GroupViewMenuServiceImpl implements GroupViewMenuService {

	@Autowired
	GroupViewMenuDao viewMenuDao;
	
	@Override
	public Map<String, Object> selectViewMenu( String groupSeq, Map<String, Object> menuList ) {
		ArrayList<Object> menuLargeList = new ArrayList<Object>();
		
		GroupUserVO userVO = viewMenuDao.selectViewMenuInfo("selectViewMenuInfo", groupSeq);
		
		if(userVO.getMenuFunction() != null) {
			menuList.put("menuFunction", userVO.getMenuFunction());
		}
		
		String[] menu_large = userVO.getMenuLarge().split(",");
		List<Integer> menuLarge = new ArrayList<Integer>();
		
		for(int i=0;i<menu_large.length;i++) {
			menuLarge.add( Integer.parseInt(menu_large[i]) );
		}
		List<GroupMenuVO> listLarge = viewMenuDao.selectMenu("selectViewMenu", "1", menuLarge);
		
		for(int i=0;i<listLarge.size();i++) {
			Map<String, Object> map_large = new HashMap<String, Object>();
			map_large.put("menuName", listLarge.get(i).getMenuName());
			
			if(listLarge.get(i).getPageUrl() == null || listLarge.get(i).getPageUrl().equals("")) {
				ArrayList<Object> menuMediumList = new ArrayList<Object>();
				
				String[] menu_medium = userVO.getMenuMedium().split(",");
				List<Integer> menuMedium = new ArrayList<Integer>();
				
				for(int j=0;j<menu_medium.length;j++) {
					menuMedium.add( Integer.parseInt(menu_medium[j]) );
				}
				
				List<GroupMenuVO> listMedium = viewMenuDao.selectMenu("selectViewMenu", "2", menuMedium);
				for(int j=0;j<listMedium.size();j++) {
					Map<String, Object> map_medium = new HashMap<String, Object>();
					map_medium.put("menuName", listMedium.get(j).getMenuName());
					map_medium.put("pageUrl", listMedium.get(j).getPageUrl());
					map_medium.put("checkMenuName", checkUrlMake(listMedium.get(j).getPageUrl(), 2));
					
					if(j==0) {
						map_large.put("pageUrl", listMedium.get(j).getPageUrl());
						map_large.put("checkMenuName", checkUrlMake(listMedium.get(j).getPageUrl(), 1));
					}
					menuMediumList.add(map_medium);
				}
				
				menuList.put("menuMedium", menuMediumList);
				menuList.put("menuMediumUse", "Y");
			}else {
				map_large.put("pageUrl", listLarge.get(i).getPageUrl());
				map_large.put("checkMenuName", checkUrlMake(listLarge.get(i).getPageUrl(), 1));

				menuList.put("menuMediumUse", "N");
			}
			
			menuLargeList.add(map_large);
		}
		
		menuList.put("menuLarge", menuLargeList);
		
		return menuList;//menuList Map -> menuLarge(List), menuMedium(List), menuMdeiumUse(String) 
	}
	
	public String checkUrlMake(String url, int menuGb) {
		String[] checkUrl = url.split("/");
		String retUrl = "";
		
		if(menuGb == 1) {
			retUrl = "/"+checkUrl[1];
		}else if(menuGb == 2) {
			retUrl = "/"+checkUrl[1]+"/"+checkUrl[2];
		}
		
		return retUrl;
	}
	
}