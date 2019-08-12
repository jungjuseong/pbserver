package com.clbee.pbcms.controller;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clbee.pbcms.service.DistributeService;
import com.clbee.pbcms.service.GroupViewMenuService;
import com.clbee.pbcms.util.AuthenticationException;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InAppList;

/* 20180417 : lsy - distributeReqList develop first */
@Controller
public class DistributeController {

	@Autowired
	DistributeService distributeService;
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	MessageSource messageSource;

	//20180515 : lsy - GroupViewMenu Create
	@Autowired
	GroupViewMenuService groupViewMenuService;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//edit for the    format you need
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class,  true));
	}

	@RequestMapping(value = "distribute/list.html", method = RequestMethod.GET)
	public ModelAndView distribute_list(String page, String objectGb, String myListGb, String ret, HttpServletRequest request, HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		Entity param = new Entity();

		//20180516 : lsy - temp if/else
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		
		//1. parameter setting(objectGb, companyGb, currentPage, userSeq)
		if(objectGb == null || objectGb.equals("")) {
			objectGb = "app";
		}
		
		if(activeUser.getMemberVO().getCompanyGb().equals("1")) {
			param.setValue("companyGb", "1");
		}else if(activeUser.getMemberVO().getCompanyGb().equals("2")) {
			param.setValue("companyGb", "2");
		}
		param.setValue("currentPage", Integer.parseInt(page));
		param.setValue("userSeq", activeUser.getMemberVO().getUserSeq());
		param.setValue("myListGb", myListGb);
		
		try{
			//2. according to objectGb, loading a corresponding list
			if(objectGb.equals("app")) {
				AppList appList = distributeService.selectAppList( param );
				
				modelAndView.addObject("objectGb", "app");
				modelAndView.addObject("appList", appList);
			}else if(objectGb.equals("inapp")) {
				InAppList inappList = distributeService.selectInappList( param );
				
				modelAndView.addObject("objectGb", "inapp");
				modelAndView.addObject("inappList", inappList);
				if(ret != null && !ret.equals("")) {
					modelAndView.addObject("ret", ret);
				}
			}else if(objectGb.equals("contents")) {
				ContentList contentsList = distributeService.selectContentsList( param );
				
				modelAndView.addObject("objectGb", "contents");
				modelAndView.addObject("contentsList", contentsList);
			}
			//3. app/inapp/contents totalcount load
			HashMap<String, Integer> totalMap = new HashMap<String, Integer>();
			
			param.setValue("objectGb", "app");
			int totalCount = distributeService.getTotalCount(param);
			modelAndView.addObject("totalCountApp", totalCount);
			
			param.setValue("objectGb", "inapp");
			totalCount = distributeService.getTotalCount(param);
			modelAndView.addObject("totalCountInapp", totalCount);

			param.setValue("objectGb", "contents");
			totalCount = distributeService.getTotalCount(param);
			modelAndView.addObject("totalCountContents", totalCount);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		for(GrantedAuthority auth : authentication.getAuthorities()) {
			if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
				//20180515 : lsy - GroupViewMenu Util Create
				Map<String, Object> menuList = new HashMap<String, Object>();
				menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
				
				modelAndView.addObject("menuLarge", menuList.get("menuLarge"));
				modelAndView.addObject("menuFunction", menuList.get("menuFunction"));
				//20180515 : lsy - GroupViewMenu Util Create - end
			}
		}
		//20180516 : lsy - temp if/else - end

		modelAndView.addObject("myListGb", myListGb);
		modelAndView.setViewName("09_distribute/distribute_list");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "distribute/restore.html", method = RequestMethod.GET)
	public String app_modify_impl(HttpSession session, HttpServletRequest request, ModelMap modelMap, DistributeRestoreVO distributeRestoreVO) {
		try {
			myUserDetails activeUser = null;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				throw new AuthenticationException();
			}else {
				activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
			
			distributeRestoreVO.setDistributeRestoreDt(new Date());
			distributeRestoreVO.setRestoreUserId(activeUser.getMemberVO().getUserId());
			
			DistributeRestoreVO middleVO = distributeService.selectDistributeReqInfo(distributeRestoreVO);
			distributeRestoreVO.setDistributeReqDt(middleVO.getDistributeReqDt());
			distributeRestoreVO.setDistributeRequestId(middleVO.getDistributeRequestId());

			distributeService.insertDistributeRestore(distributeRestoreVO);
			
			distributeService.updateRestoreStatus(distributeRestoreVO);
			
			String url = "";
			if(request.getParameter("referer").equals("list")) {
				if(distributeRestoreVO.getType().equals("app")) {
					url="redirect:/app/list.html?currentPage=1&appSeq=&searchValue=&isAvailable=true";
				}else if(distributeRestoreVO.getType().equals("contents")) {
					url="redirect:/contents/list.html?page=1";
				}else if(distributeRestoreVO.getType().equals("inapp")) {
					url="redirect:/app/inapp/list.html?storeBundleId=" + request.getParameter("storeBundleId")+"&isAvailable=false&searchType=inappName&searchValue=";
				}
			}else {
				url = "redirect:/distribute/list.html?page=1&objectGb="+distributeRestoreVO.getType()+"&myListGb=n";
				if(distributeRestoreVO.getType().equals("inapp")) {
					url = url+"&ret=y";
				}
			}
			
			return url;
			
		}catch(Exception e){
			e.printStackTrace();
			String returnUrl = "/inc/dummy";
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
	}
	
	private String paramSet(HttpServletRequest req, String targetName) {
		String value = "";
		value = (null == req.getParameter(targetName)) ? "" : "" + req.getParameter(targetName);
		return value;
	}

	public String changeSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
}