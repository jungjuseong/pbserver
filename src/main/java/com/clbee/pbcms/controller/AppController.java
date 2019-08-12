package com.clbee.pbcms.controller;

import com.clbee.pbcms.service.AppService;
import com.clbee.pbcms.service.BundleService;
import com.clbee.pbcms.service.CaptureService;
import com.clbee.pbcms.service.ChangelistService;
import com.clbee.pbcms.service.ContentsService;
import com.clbee.pbcms.service.DistributeService;
import com.clbee.pbcms.service.GroupViewMenuService;
import com.clbee.pbcms.service.InAppCategoryService;
import com.clbee.pbcms.service.InAppService;
import com.clbee.pbcms.service.LogService;
import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.service.ProvisionService;
import com.clbee.pbcms.service.TemplateService;
import com.clbee.pbcms.service.NoticeService;
import com.clbee.pbcms.util.AuthenticationException;
import com.clbee.pbcms.util.DateUtil;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.FileUtil;
import com.clbee.pbcms.util.FileZipUtil;
import com.clbee.pbcms.util.Formatter;
import com.clbee.pbcms.util.ImageTask;
import com.clbee.pbcms.util.VersionCheckUtil;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.AppHistoryVO;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.BundleVO;
import com.clbee.pbcms.vo.CaptureVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InAppList;
import com.clbee.pbcms.vo.InappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.InappcategoryVO;
import com.clbee.pbcms.vo.LogList;
import com.clbee.pbcms.vo.LogVO;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.ProvisionVO;
import com.clbee.pbcms.vo.TemplateList;
import com.clbee.pbcms.vo.TemplateVO;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController{

	@Autowired
	AppService appService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ProvisionService provisionService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	InAppService inAppService;
	
	@Autowired
	CaptureService captureService;
	
	@Autowired
	InAppCategoryService inAppCategoryService;
	
	@Autowired
	BundleService bundleService;
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	ChangelistService changelistService;
	 
	@Autowired
	LogService logService;
	
	@Autowired
	NoticeService noticeService;

	//20180419 : lsy - distribute restore popup
	@Autowired
	DistributeService distributeService;

	//20180515 : lsy - GroupViewMenu Create
	@Autowired
	GroupViewMenuService groupViewMenuService;
	
	//20180607 : lsy - app make request -> contents select
	@Autowired
	ContentsService contentsService;
	
	//20180612 : lsy - app make request -> contents file make
	@Autowired
	ContentsController contentsController;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//edit for the    format you need
		dateFormat.setLenient(false);
		//binder.registerCustomEditor(Integer.class, new CustomPrimitiveFormat(Integer.class, true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class,  true));
		//binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, false));
	}

	@RequestMapping(value = "app/list.html", method = RequestMethod.GET)	//1. 앱 리스트 출력
	public String home(HttpSession session, HttpServletRequest request, ModelMap modelMap, AppList appList) throws UnsupportedEncodingException {		
//		System.out.println("currentUserName" + SecurityContextHolder.getContext().getAuthentication().getName());
		myUserDetails activeUser = null;
			
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		GrantedAuthority element = authorities.iterator().next();
		String authority = element.getAuthority();

//		System.out.println("appList.getSearchValue() = " + appList.getSearchValue());
		if(appList.getSearchValue() != null) {
			appList.setSearchValue(URLDecoder.decode(appList.getSearchValue(), "UTF-8"));
		}
		
		if("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
			return "redirect:/down/list.html";
		}else{
			if(appList.getCurrentPage()==null) {
				appList.setCurrentPage(1);
			}
			appList = appService.selectList(activeUser.getMemberVO(), appList);
			
			//20180516 : lsy - temp if/else
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			for(GrantedAuthority auth : authentication.getAuthorities()) {
				if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
					//20180515 : lsy - GroupViewMenu Util Create
					Map<String, Object> menuList = new HashMap<String, Object>();
					menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
					
					modelMap.addAttribute("menuLarge", menuList.get("menuLarge"));
					modelMap.addAttribute("menuFunction", menuList.get("menuFunction"));
					//20180515 : lsy - GroupViewMenu Util Create - end
				}
			}
			//20180516 : lsy - temp if/else - end
			
			modelMap.addAttribute("appList", appList);
			modelMap.addAttribute("authority", authority);

			return "01_app/app_list";
		}
	}

	@RequestMapping(value = "app/regist.html", method = RequestMethod.GET)	//2. 앱 등록 - 페이지 호출
	public String app_regist(HttpSession session, HttpServletRequest request, AppVO appVO, ModelMap modelMap, AppList appList) {
		try{
			if(appVO.getRegGb()==null||appVO.getRegGb().length()==0){
//				System.out.println("ppVO.getRegGb()1==="+appVO.getRegGb());
				appVO.setRegGb("2");
//				System.out.println("ppVO.getRegGb()2==="+appVO.getRegGb());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//20180516 : lsy - temp if/else
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		for(GrantedAuthority auth : authentication.getAuthorities()) {
			if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
				//20180515 : lsy - GroupViewMenu Util Create
				Map<String, Object> menuList = new HashMap<String, Object>();
				menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
				
				modelMap.addAttribute("menuLarge", menuList.get("menuLarge"));
				modelMap.addAttribute("menuFunction", menuList.get("menuFunction"));
				//20180515 : lsy - GroupViewMenu Util Create - end
			}
		}
		//20180516 : lsy - temp if/else - end
		
		modelMap.addAttribute("appVO", appVO);
		modelMap.addAttribute("appList", appList);
		return "01_app/app_write";
	}

  	@RequestMapping(value = "app/regist.html", method = RequestMethod.POST)	//2. 앱 등록 - 처리
	public String app_regist_impl(AppVO appVO, HttpServletRequest request, ModelMap modelMap) throws Exception {

		try{
			myUserDetails activeUser = null;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				throw new AuthenticationException();
			}else {
				activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}	
			//for member seq
			int regUserSeq = activeUser.getMemberVO().getUserSeq();
			//for company seq
			int regCompanySeq = activeUser.getMemberVO().getCompanySeq();
			//provSeqjavascript:appModify('682');
			String regUserId = activeUser.getMemberVO().getUserId();
			String regUserGb = activeUser.getMemberVO().getUserGb();
			appVO.setRegUserSeq(regUserSeq);
			appVO.setRegUserId(regUserId);
			appVO.setRegUserGb(regUserGb);
			appVO.setInstallGb("1");
			
			//20180403 : lsy - when regist app by cms, storebundleid omitted at app info
			String storeBundleId1 = request.getParameter("storeBundleId1");
			String storeBundleId2 = request.getParameter("storeBundleId2");
			appVO.setStoreBundleId(storeBundleId1+storeBundleId2);


			int appSeq = appService.getSeqAfterInsertAppInfo(appVO);
			//�̹��� ó��
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))	 + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));				
			//�������̹���
			String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....����
			}
			//ĸ���̹��� ó��
			String[] imgOrgFileArr = request.getParameterValues("imgOrgFile");
			String[] imgSaveFileArr = request.getParameterValues("imgSaveFile");
			String imgOrgFile = null;
			String imgSaveFile = null;
			toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.capture.file", null, localeResolver.resolveLocale(request));				
			if(imgSaveFileArr!=null&&imgSaveFileArr.length>0){
				for(int i=0;i<imgSaveFileArr.length;i++){
					imgOrgFile = imgOrgFileArr[i];
					imgSaveFile = imgSaveFileArr[i];
					if(FileUtil.movefile(imgSaveFile, tempPath, toPath)){
						//....���� tb_capture
						CaptureVO captureVO = new CaptureVO();
						captureVO.setCaptureGb("1");
						captureVO.setBoardSeq(appSeq);
						captureVO.setUserSeq(regUserSeq);
						captureVO.setImgOrgFile(imgOrgFile);
						captureVO.setImgSaveFile(imgSaveFile);
						captureService.insert(captureVO);					
					}
				}
			}

			//���κ��� �������̺� �Է� �� ����
			String[] provSeqArr = request.getParameterValues("provSeq");
			//String[] provIdArr = request.getParameterValues("provId");
			String[] provTestGbArr = request.getParameterValues("provTestGb");

			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ regist appVO.getRegGb() = " + appVO.getRegGb());

			List list =  inAppCategoryService.getListInAppCategory("storeBundleId", storeBundleId1+storeBundleId2);

			if(list == null || list.size() == 0 ){
				if("1".equals(appVO.getRegGb())){
					InappcategoryVO InCateVo = new InappcategoryVO();
		
					InCateVo.setStoreBundleId(storeBundleId1+storeBundleId2);
					InCateVo.setCategoryName("default");
					InCateVo.setCategoryParent(0);
					//InCateVo.setCategorySeq(Integer.parseInt(categorySeq1));
		
					InCateVo.setDepth("1");
					InCateVo.setRegUserSeq(activeUser.getMemberVO().getUserSeq());
					InCateVo.setRegUserId(activeUser.getMemberVO().getUserId());
					InCateVo.setRegUserGb(activeUser.getMemberVO().getUserGb());
					InCateVo.setRegDt(new Date());
					InCateVo.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
					InCateVo.setChgUserId(activeUser.getMemberVO().getUserId());
					InCateVo.setChgUserGb(activeUser.getMemberVO().getUserGb());
					InCateVo.setChgDt(new Date());
					inAppCategoryService.insertInAppInfo(InCateVo);
				}
			}

			int provSeq = 0;
			String provId = null;
			String provTestGb = null;
			int ostype = 2;
			ostype = Integer.parseInt(appVO.getOstype());
			if("4".equals(appVO.getOstype())){
				bundleService.delete(appSeq);
				provId = storeBundleId1+storeBundleId2;				 
				BundleVO bundleVO  = new BundleVO();
				bundleVO.setAppSeq(appSeq);
				bundleVO.setBundleName(provId);
				bundleVO.setOsType(ostype);
				bundleService.insert(bundleVO);
			}else{
				if(provSeqArr!=null&&provSeqArr.length>0){
					bundleService.delete(appSeq);
					for(int i=0;i<provSeqArr.length;i++){
						provSeq = Integer.parseInt(provSeqArr[i]);				 
						provId = storeBundleId1+storeBundleId2;
						provTestGb = provTestGbArr[i];
						BundleVO bundleVO  = new BundleVO();
						bundleVO.setAppSeq(appSeq);
						bundleVO.setProvSeq(provSeq);
						bundleVO.setBundleName(provId);
						bundleVO.setOsType(ostype);
						bundleVO.setProvTestGb(provTestGb);
						bundleService.insert(bundleVO);
					}
				}else{
					bundleService.delete(appSeq);
					provId = storeBundleId1+storeBundleId2;				 
					BundleVO bundleVO  = new BundleVO();
					bundleVO.setAppSeq(appSeq);
					bundleVO.setBundleName(provId);
					bundleVO.setOsType(ostype);
					bundleService.insert(bundleVO);
				}
			}
			
	    	//return "redirect:/app/list.html";
			String url = "redirect:/app/modify.html";
			String parameters = "?currentPage=1&appSeq="+appSeq+"&searchValue="+"&isAvailable="+request.getParameter("isAvailable");
			return url + parameters;
		}catch(Exception e){
			String returnUrl = "/inc/dummy";
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
 	}

  	//20180827 : lsy - app regist 방식 분리(기존 app 등록 방식:아이콘 없이 등록 + 아이콘 등록됐을 때 app 등록방식 추가) 
  	@RequestMapping(value = "app/registWithIcon.html", method = RequestMethod.POST)	//2. 앱 등록 - 처리
	public @ResponseBody HashMap<Object, Object> app_regist_impl_icon(AppVO appVO, HttpServletRequest request, ModelMap modelMap) throws Exception {
		HashMap<Object, Object> map = new HashMap<Object, Object>();

		try{
			myUserDetails activeUser = null;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				throw new AuthenticationException();
			}else {
				activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}	
			//for member seq
			int regUserSeq = activeUser.getMemberVO().getUserSeq();
			//for company seq
			int regCompanySeq = activeUser.getMemberVO().getCompanySeq();
			//provSeqjavascript:appModify('682');
			String regUserId = activeUser.getMemberVO().getUserId();
			String regUserGb = activeUser.getMemberVO().getUserGb();
			appVO.setRegUserSeq(regUserSeq);
			appVO.setRegUserId(regUserId);
			appVO.setRegUserGb(regUserGb);
			appVO.setInstallGb("1");
			
			//20180403 : lsy - when regist app by cms, storebundleid omitted at app info
			String storeBundleId1 = request.getParameter("storeBundleId1");
			String storeBundleId2 = request.getParameter("storeBundleId2");
			appVO.setStoreBundleId(storeBundleId1+storeBundleId2);

			int appSeq = appService.getSeqAfterInsertAppInfo(appVO);
			//�̹��� ó��
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))	 + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));				
			//�������̹���
			String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....����
			}

			String[] imgOrgFileArr = request.getParameterValues("imgOrgFile");
			String[] imgSaveFileArr = request.getParameterValues("imgSaveFile");
			String imgOrgFile = null;
			String imgSaveFile = null;
			toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.capture.file", null, localeResolver.resolveLocale(request));				
			if(imgSaveFileArr!=null&&imgSaveFileArr.length>0){
				for(int i=0;i<imgSaveFileArr.length;i++){
					imgOrgFile = imgOrgFileArr[i];
					imgSaveFile = imgSaveFileArr[i];
					if(FileUtil.movefile(imgSaveFile, tempPath, toPath)){
						CaptureVO captureVO = new CaptureVO();
						captureVO.setCaptureGb("1");
						captureVO.setBoardSeq(appSeq);
						captureVO.setUserSeq(regUserSeq);
						captureVO.setImgOrgFile(imgOrgFile);
						captureVO.setImgSaveFile(imgSaveFile);
						captureService.insert(captureVO);					
					}
				}
			}

			//���κ��� �������̺� �Է� �� ����
			String[] provSeqArr = request.getParameterValues("provSeq");
			String[] provTestGbArr = request.getParameterValues("provTestGb");

			List list =  inAppCategoryService.getListInAppCategory("storeBundleId", storeBundleId1+storeBundleId2);

			if(list == null || list.size() == 0 ){
				if("1".equals(appVO.getRegGb())){
					InappcategoryVO InCateVo = new InappcategoryVO();
		
					InCateVo.setStoreBundleId(storeBundleId1+storeBundleId2);
					InCateVo.setCategoryName("default");
					InCateVo.setCategoryParent(0);
		
					InCateVo.setDepth("1");
					InCateVo.setRegUserSeq(activeUser.getMemberVO().getUserSeq());
					InCateVo.setRegUserId(activeUser.getMemberVO().getUserId());
					InCateVo.setRegUserGb(activeUser.getMemberVO().getUserGb());
					InCateVo.setRegDt(new Date());
					InCateVo.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
					InCateVo.setChgUserId(activeUser.getMemberVO().getUserId());
					InCateVo.setChgUserGb(activeUser.getMemberVO().getUserGb());
					InCateVo.setChgDt(new Date());
					inAppCategoryService.insertInAppInfo(InCateVo);
				}
			}

			int provSeq = 0;
			String provId = null;
			String provTestGb = null;
			int ostype = 2;
			ostype = Integer.parseInt(appVO.getOstype());
			if("4".equals(appVO.getOstype())){
				bundleService.delete(appSeq);
				provId = storeBundleId1+storeBundleId2;				 
				BundleVO bundleVO  = new BundleVO();
				bundleVO.setAppSeq(appSeq);
				bundleVO.setBundleName(provId);
				bundleVO.setOsType(ostype);
				bundleService.insert(bundleVO);
			}else{
				if(provSeqArr!=null&&provSeqArr.length>0){
					bundleService.delete(appSeq);
					for(int i=0;i<provSeqArr.length;i++){
						provSeq = Integer.parseInt(provSeqArr[i]);				 
						provId = storeBundleId1+storeBundleId2;
						provTestGb = provTestGbArr[i];
						BundleVO bundleVO  = new BundleVO();
						bundleVO.setAppSeq(appSeq);
						bundleVO.setProvSeq(provSeq);
						bundleVO.setBundleName(provId);
						bundleVO.setOsType(ostype);
						bundleVO.setProvTestGb(provTestGb);
						bundleService.insert(bundleVO);
					}
				}else{
					bundleService.delete(appSeq);
					provId = storeBundleId1+storeBundleId2;				 
					BundleVO bundleVO  = new BundleVO();
					bundleVO.setAppSeq(appSeq);
					bundleVO.setBundleName(provId);
					bundleVO.setOsType(ostype);
					bundleService.insert(bundleVO);
				}
			}
			
			map.put("currentPage", "1");
			map.put("appSeq", appSeq);
			map.put("searchValue", "");
			map.put("isAvailable", request.getParameter("isAvailable"));			
			
		}catch(Exception e){
			String returnUrl = "/inc/dummy";
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
		}
		return map;
 	}
  	
	@RequestMapping(value = "app/modify.html", method = RequestMethod.GET)	//3. 앱 수정 - 페이지 호출
	public String app_modify(HttpSession session, HttpServletRequest request, ModelMap modelMap, InappVO inappVO, InAppList inAppList, CaptureVO vo, AppVO appVO, AppList appList) {
		
		List<?> captureList = null;
		List<?> bundleList = null;
		int inappCnt = 0;
		TemplateVO templateVO = null;
		List<AppSubVO> UserList = null;
		String useVal ="";

		try{
			myUserDetails activeUser = null;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				throw new AuthenticationException();
			}else {
				activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
			//System.out.println("modify appSeq"+appVO.getAppSeq());
			appVO = appService.selectForUpdate(appVO, activeUser.getMemberVO());
			if(appVO==null){
				//throw new Exception("�߸��� �����Դϴ�.");
				String returnUrl = "/inc/dummy";
				modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
				modelMap.addAttribute("type", "-1");
				return returnUrl;
			}
			bundleList = bundleService.listByAppSeq(appVO.getAppSeq());
			vo.setCaptureGb("1");
			vo.setBoardSeq(appVO.getAppSeq());
			captureList = captureService.selectListByBoardSeqWithGb(vo);


			if(appVO.getTemplateSeq() != null)
			templateVO = templateService.selectByTempId(appVO.getTemplateSeq());
			UserList    = appService.selectAppSubList(appVO.getAppSeq());
			//TemplateList templateList = null;
			for(int i = 0; i < UserList.size(); i++){
				if( i== 0)useVal  += UserList.get(i).getUserSeq();
				else useVal  += ","+UserList.get(i).getUserSeq();
			}

			inappVO.setStoreBundleId(appVO.getStoreBundleId());
			inAppList = inAppService.getListByBundleId(inappVO, inAppList, activeUser.getMemberVO());
			for( int i =0 ; i< inAppList.getList().size() ; i++) {
				if( "1".equals(inAppList.getList().get(i).getCompletGb()) && "1".equals(inAppList.getList().get(i).getUseGb()) ) inappCnt++;
			}
			
			//20180423 : lsy - after process, return page gubun
			if(request.getHeader("referer").indexOf("/distribute") != -1 && request.getHeader("referer").indexOf("/distribute") > 0) {
				modelMap.addAttribute("referer", "distribute");
			}else if(request.getHeader("referer").indexOf("/app") != -1 && request.getHeader("referer").indexOf("/app") > 0) {
				modelMap.addAttribute("referer", "list");
			}
			//20180423 : lsy - after process, return page gubun - end
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//20180516 : lsy - temp if/else
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		for(GrantedAuthority auth : authentication.getAuthorities()) {
			if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
				//20180515 : lsy - GroupViewMenu Util Create
				Map<String, Object> menuList = new HashMap<String, Object>();
				menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
				
				modelMap.addAttribute("menuLarge", menuList.get("menuLarge"));			
				modelMap.addAttribute("menuFunction", menuList.get("menuFunction"));
				//20180515 : lsy - GroupViewMenu Util Create - end
			}
		}
		//20180516 : lsy - temp if/else - end

		modelMap.addAttribute("inappCnt", inappCnt);
		modelMap.addAttribute("useSelVal", useVal);
		modelMap.addAttribute("inappCnt", inappCnt);
		modelMap.addAttribute("appVO", appVO);
		modelMap.addAttribute("appList", appList);
		modelMap.addAttribute("bundleList", bundleList);
		modelMap.addAttribute("captureList", captureList);
		modelMap.addAttribute("templateVO", templateVO);
		modelMap.addAttribute("myListGb", request.getParameter("myListGb"));
		
    	return "01_app/app_modify";
    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "app/modify.html", method = RequestMethod.POST)	//3. 앱 수정 - 처리
	public String app_modify_impl(HttpSession session, String[] useS, String isAvailable, HttpServletRequest request, ModelMap modelMap, CaptureVO vo, AppVO appVO, AppList appList) {
		
		//����� ���� 
		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		//for member seq
		//for company seq
		//provSeq
		Entity param = new Entity();
		AppSubVO appSubVO = new AppSubVO();

		String[] provSeqArr = request.getParameterValues("provSeq");
		//String[] provIdArr = request.getParameterValues("provId");
		String storeBundleId1 = request.getParameter("storeBundleId1");
		String storeBundleId2 = request.getParameter("storeBundleId2");
		String[] provTestGbArr = request.getParameterValues("provTestGb");
		String isCompleteNoToYes = request.getParameter("isCompleteNoToYes");

		//couponNum�� Null�� ��� ""���� �ٲ�
		if(appVO.getCouponNum() == null)appVO.setCouponNum("");
		try{
			//1. update information VS exist DB information compare + user login check
			AppVO appVO2 = appService.selectForUpdate(appVO, activeUser.getMemberVO());
			if(appVO2 == null){
				//throw new Exception("�߸��� �����Դϴ�.");
				String returnUrl = "/inc/dummy";
				modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
				modelMap.addAttribute("type", "-1");
				return returnUrl;				
			}

			//2. app usegb compare -> change usegb : update DB
			if(appVO.getUseGb() != null && !appVO.getUseGb().equals(appVO2.getUseGb())){
				if("1".equals(appVO.getUseGb())){
					appVO.setUseAvailDt(new Date());
				}else if("2".equals(appVO.getUseGb())){
					appVO.setUseDisableDt(new Date());
				}
			}

			//3. app libmitgb compare -> change libmitgb : update DB
			if(appVO.getLimitGb()!=null){
				if(!appVO.getLimitGb().equals(appVO2.getLimitGb())){
					if("1".equals(appVO.getLimitGb())){
						appVO.setLimitDt(new Date());
					}
				}
			}

			param.setValue("store_bundle_id",storeBundleId1+storeBundleId2);
			param.setValue("OSTYPE", appVO.getOstype());
			List<LinkedHashMap<Object, Object>> appVOForBundleIdList = appService.getRowIsCompletedByBundleId(param);
	
/*			//4. app history 처리 로직	-> 20180420 : lsy - complet_gb radio button delete
			if("UPDATEOTHERYES".equals(isCompleteNoToYes)){
				if(appVOForBundleIdList.size() != 0) {
					System.out.println("@@@@@@@@@@@ UPDATEOTHERYES!!!!");
					AppHistoryVO appHistoryVOForHashMap = new AppHistoryVO(appVOForBundleIdList.get(0));
					appService.insertAppHistoryInfo(appHistoryVOForHashMap);
					appService.deleteAppInfo(((Long) appVOForBundleIdList.get(0).get("appSeq")).intValue());
				}
			}
*/			
			//5. 배포요청일 경우 - 배포요청일 Update		=> 20180410 : lsy - distribute process modify 
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeReq")) {
				appVO.setDistributeReqDt(new Date());
				appVO.setDistributeRequestId(activeUser.getMemberVO().getUserId());
			}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) { 
				//6. 배포승인일 경우 - history 처리 후, 배포승인일 Update		=> 20180417 : lsy - distribute process modify(distribute Complet)
				if(appVOForBundleIdList.size() != 0) {
					System.out.println("@@@@@@@@@@@ UPDATEOTHERYES!!!!");
					AppHistoryVO appHistoryVOForHashMap = new AppHistoryVO(appVOForBundleIdList.get(0)); 
					appService.insertAppHistoryInfo(appHistoryVOForHashMap);
					appService.deleteAppInfo(((Long) appVOForBundleIdList.get(0).get("appSeq")).intValue());
				}
				
				appVO.setDistributeCompletDt(new Date());
				appVO.setDistributeAcceptId(activeUser.getMemberVO().getUserId());
			}
			
			appVO.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
			appVO.setChgUserId(activeUser.getMemberVO().getUserId());
			appVO.setChgUserGb(activeUser.getMemberVO().getUserGb());
			
			//20180405 : lsy - info change -> chg_dt update - 1
			int infoUpdateCheck = appService.infoUpdateCheck(appVO, appVO.getAppSeq());

			if("Adhoc".equals(appVO.getProvisionGb())) {
				appVO.setProvisionGb("1");
			}
			if("AppStore".equals(appVO.getProvisionGb())) {
				appVO.setProvisionGb("2");
			}

			//�̹��� ó��
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
			
			//�������̹���
			//String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....����
			}
			//ĸ���̹��� ó��
			String[] captureSeqArr = request.getParameterValues("captureSeq");
			String[] imgOrgFileArr = request.getParameterValues("imgOrgFile");
			String[] imgSaveFileArr = request.getParameterValues("imgSaveFile");
			String captureSeq = null;
			String imgOrgFile = null;
			String imgSaveFile = null;
			toPath =   messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.capture.file", null, localeResolver.resolveLocale(request));				
			if(imgSaveFileArr!=null&&imgSaveFileArr.length>0){
				for(int i=0;i<imgSaveFileArr.length;i++){
					captureSeq = captureSeqArr[i];
					imgOrgFile = imgOrgFileArr[i];				 
					imgSaveFile = imgSaveFileArr[i];				 
					if("0".equals(captureSeq)&&FileUtil.movefile(imgSaveFile, tempPath, toPath)){
						//....���� tb_capture
						CaptureVO captureVO = new CaptureVO();
						captureVO.setCaptureGb("1");
						captureVO.setBoardSeq(appVO.getAppSeq());
						captureVO.setUserSeq(activeUser.getMemberVO().getUserSeq());
						captureVO.setImgOrgFile(imgOrgFile);
						captureVO.setImgSaveFile(imgSaveFile);
						captureService.insert(captureVO);		
						captureVO = null;
					}
				}
				//20180405 : lsy - info change -> chg_dt update - 2
				infoUpdateCheck += 1;
			}
			//�������ϻ���
			//captureSeq
/*			var html = '<input typd="hidden" name="deleteFileSeq" value="'+captureSeq+'"/>';
			html += '<input typd="hidden" name="deleteSaveFileName" value="'+saveFileName+'"/>';
			html += '<input typd="hidden" name="deleteFileType" value="'+thisFileType+'"/>';*/			
			String[] deleteFileSeqArr = request.getParameterValues("deleteFileSeq");
			String[] deleteSaveFileNameArr = request.getParameterValues("deleteSaveFileName");
			String[] deleteFileTypeArr = request.getParameterValues("deleteFileType");
			Integer deleteFileSeq = null;
			String deleteSaveFileName = null;
			String deleteFileType = null;
			String deleteFilePath = null;
			if(deleteFileTypeArr!=null&&deleteFileTypeArr.length>0){
				for(int i=0;i<deleteFileTypeArr.length;i++){
					deleteFileSeq = Integer.parseInt(deleteFileSeqArr[i]);
					deleteSaveFileName = deleteSaveFileNameArr[i];				 
					deleteFileType = deleteFileTypeArr[i];
					if("capture".equals(deleteFileType)){//�������� ����
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.capture.file", null, localeResolver.resolveLocale(request));
						if(FileUtil.delete(new File(deleteFilePath+deleteSaveFileName))){
							CaptureVO captureVO = new CaptureVO();
							captureVO.setCaptureSeq(deleteFileSeq);
							captureService.delete(captureVO);
							captureVO = null;
						}
					}else if("icon".equals(deleteFileType)){//���������ϻ���
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
						FileUtil.delete(new File(deleteFilePath+deleteSaveFileName));
					}
				}
			}
			//���κ��� �������̺� �Է� �� ����
			int provSeq = 0;
			String provId = null;
			String provTestGb = null;
			int ostype = 2;
			ostype = Integer.parseInt(appVO.getOstype());
			if("4".equals(appVO.getOstype())){
				bundleService.delete(appVO.getAppSeq());
				provId = storeBundleId1+storeBundleId2;				 
				BundleVO bundleVO  = new BundleVO();
				bundleVO.setAppSeq(appVO.getAppSeq());
				bundleVO.setBundleName(provId);
				bundleVO.setOsType(ostype);
				bundleService.insert(bundleVO);
			}else{
				if(provSeqArr!=null&&provSeqArr.length>0){
					bundleService.delete(appVO.getAppSeq());
					for(int i=0;i<provSeqArr.length;i++){
						provSeq = Integer.parseInt(provSeqArr[i]);				 
						provId = storeBundleId1+storeBundleId2;				 
						provTestGb = provTestGbArr[i];
						BundleVO bundleVO  = new BundleVO();
						bundleVO.setAppSeq(appVO.getAppSeq());
						bundleVO.setProvSeq(provSeq);
						bundleVO.setBundleName(provId);
						bundleVO.setOsType(ostype);
						bundleVO.setProvTestGb(provTestGb);
						bundleService.insert(bundleVO);
					}
				}else{
					bundleService.delete(appVO.getAppSeq());
					provId = storeBundleId1+storeBundleId2;				 
					BundleVO bundleVO  = new BundleVO();
					bundleVO.setAppSeq(appVO.getAppSeq());
					bundleVO.setBundleName(provId);
					bundleVO.setOsType(ostype);
					bundleService.insert(bundleVO);
				}				
			}

			appSubVO.setAppSeq(appVO.getAppSeq());	
			if("1".equals(appVO.getUseUserGb()) && !"".equals(appVO.getAppSeq())){
				appService.deleteAppSubInfo(appSubVO);
			}

			//MemberVO memberVO = memberService.findByCustomInfo("userSeq", Integer.parseInt(useS[i]));
			List<MemberVO> memberList = memberService.getPermitList(activeUser.getMemberVO().getCompanySeq(), useS);
			if("2".equals(appVO.getUseUserGb()) && !"".equals(appVO.getAppSeq())){
				appService.deleteAppSubInfo(appSubVO);
				for(int i=0; i<useS.length; i++){
					appSubVO.setUserSeq(Integer.parseInt(useS[i]));
					for( int j=0; j < memberList.size(); j++) {
						if ( memberList.get(j).getUserSeq() == Integer.parseInt(useS[i]) ) {
							if(memberList.get(j).getTwodepartmentSeq() == null ) {
								appSubVO.setDepartmentSeq(memberList.get(j).getOnedepartmentSeq());
							}else {
								appSubVO.setDepartmentSeq(memberList.get(j).getTwodepartmentSeq());
							}
							appService.insertAppSubInfo(appSubVO);
						}
					}
				}
			}
			
			//20180405 : lsy - info change -> chg_dt update - 3
			if(infoUpdateCheck >= 1) {
				appVO.setChgDt(new Date());
			}
			appService.updateAppInfo(appVO, appVO.getAppSeq());

			String url = "redirect:/app/list.html";
			String parameters = "?currentPage=1&searchValue="+"&isAvailable="+isAvailable;
				 /*"&appSeq="+appVO.getAppSeq()+"&searchType="+appList.getSearchType()+"&searchValue="+appList.getSearchValue();*/
			//20180418 : lsy - distribute restore process modify
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) {
				if(request.getParameter("referer").equals("list")) {
					return url + parameters;
				}else{
					return "redirect:/distribute/list.html?page=1&objectGb=app&myListGb=n";					
				}
			}else {
				return url + parameters;
			}
			//20180418 : lsy - distribute restore process modify - end
		}catch(Exception e){
			e.printStackTrace();
			String returnUrl = "/inc/dummy";
			//message : ��� ����
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
    }

	@RequestMapping(value = "app/provision/list.html", method = RequestMethod.GET)	//5-1. 앱 등록/수정 - 프로비전 팝업
	public String app_provision_list(ProvisionVO vo, ModelMap modelMap, int appSeq) throws Exception {
		myUserDetails activeUser = null;
			
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}		
		//for member seq
		int regUserSeq = activeUser.getMemberVO().getUserSeq();
		//for company seq
		int regCompanySeq = activeUser.getMemberVO().getCompanySeq();
		//provSeq
		vo.setRegUserSeq(regUserSeq);
		vo.setRegCompanySeq(regCompanySeq);
		List list = provisionService.selectList(vo, appSeq);
		modelMap.addAttribute("provisionList", list);
		modelMap.addAttribute("provisionVo", vo);
		modelMap.addAttribute("appSeq", appSeq);
		return "01_app/app_pop_provision";
	}

	@RequestMapping(value = "app/template/list.html", method = {RequestMethod.GET, RequestMethod.POST})	//5-2. 앱 등록/수정 - 템플릿 팝업
	public String app_template_list(TemplateVO temVO, ModelMap modelMap, TemplateList templateList) throws Exception {
		myUserDetails activeUser = null;
			
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		if(templateList.getCurrentPage()==null||"".equals(templateList.getCurrentPage())){templateList.setCurrentPage(1);}
			
		//System.out.println("changedOsTypeGb = " + temVO.getOstypeGb());

		templateList = templateService.selectList(temVO, activeUser.getMemberVO(), templateList, "Paging");
		modelMap.addAttribute("templateList", templateList);
		modelMap.addAttribute("TemplateVO", temVO);
		return "01_app/app_pop_template";
	}

	//���ø��̹�������
	@RequestMapping(value = "app/template/capture.html", method = RequestMethod.GET)
	public @ResponseBody List man_provision_modify(HttpServletRequest request, HttpSession session, CaptureVO vo) throws Exception{
		myUserDetails activeUser = null;
			
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		List list = null;
		list = captureService.selectListByBoardSeqWithGb(vo);
		return list;
	}

	/**
	 * �������̹��� ���ε�
	 */
	@RequestMapping(value = "app/iconfileupload.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> app_icon_file_upload(HttpServletRequest request, HttpSession session, @RequestParam("iconFile") MultipartFile file) throws Exception{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try{
			String fileType = request.getParameter("fileType");
			map.put("fileType", fileType);
			map = uploadFile(map, file, request);
		}catch(Exception e){
			return map;
		}
		return map;
	}

	/**
	 * ĸ�� �̹��� ���ε�
	 */
	@RequestMapping(value = "app/deletetmpimg.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> app_delete_temp_img(HttpServletRequest request, HttpSession session){
		System.out.println("@@@@@@@@@@@@app/deletetmpimg.html");
		HashMap<Object, Object> map = new HashMap<Object, Object>();

		try{
			String saveFileName = request.getParameter("saveFileName");
			String fileStatus = request.getParameter("fileStatus");
			String savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			File file = new File(savePath+saveFileName);
			if(!FileUtil.delete(file)){
				//���� ���� ������ �߻��߽��ϴ�.
				map.put("error", messageSource.getMessage("app.control.001", null, localeResolver.resolveLocale(request)));
				return map;
			}
			map.put("error", "none" );
		}catch(Exception e){
			//���� ���� ������ �߻��߽��ϴ�.
			map.put("error", messageSource.getMessage("app.control.001", null, localeResolver.resolveLocale(request)));
			return map;
		}
		return map;
	}

	public HashMap<Object, Object> uploadFile(HashMap map, MultipartFile upLoadFile, HttpServletRequest request) throws Exception {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		float floatValue = (float)upLoadFile.getSize()/(1024*1024);
		DecimalFormat format = new DecimalFormat(".##");

		try{
			if (upLoadFile != null) {
				if (upLoadFile.getSize() > 0) {
					String savePath = "";
					String webPath = "";
					String orgFileName = upLoadFile.getOriginalFilename();
					String orgFileOnlyName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
						 
					String fileExt = orgFileName.substring(orgFileName.lastIndexOf(".") + 1, orgFileName.length());
						
					fileExt = fileExt.toLowerCase();
						
					String saveFileOnlyName = DateUtil.getDate("yyyyMMdd_hhmmss") + "_" + Formatter.getRandomNumber(100);
					String saveFileName = saveFileOnlyName + "." + fileExt;

					inputStream = upLoadFile.getInputStream();				
					map.put("orgFileName", orgFileName);
					map.put("saveFileName", saveFileName);

					if ( floatValue < 1 ) {
						map.put("fileSize", "0"+ format.format(floatValue)+"MB");
					}else {
						map.put("fileSize", format.format(floatValue)+"MB");
					}
					
					map.put("fileExt", fileExt);
					String mimeType = upLoadFile.getContentType();
					if("inappFile".equals(map.get("fileType"))){
	/*					if (!("image/jpeg".equals(mimeType)||"image/gif".equals(mimeType)||"image/png".equals(mimeType)||"image/jpg".equals(mimeType))) {					 
							map.put("error", "����� �� ���� ���� �����Դϴ�.");
							outputStream.close();
							inputStream.close();
							return map;
							//throw new Exception("����� �� ���� ���������Դϴ�.");
						}*/
						savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
						//webPath = messageSource.getMessage("file.web.path.temp.images.file", null, localeResolver.resolveLocale(request));
					}else{
						if (!("image/jpeg".equals(mimeType)||"image/gif".equals(mimeType)||"image/png".equals(mimeType)||"image/jpg".equals(mimeType))) {					 
							map.put("error", messageSource.getMessage("app.control.002", null, localeResolver.resolveLocale(request)));
							//outputStream.close();
							inputStream.close();
							return map;
							//throw new Exception("����� �� ���� ���������Դϴ�.");
						}
						savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
						webPath = messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
					}
					  
					outputStream = new FileOutputStream(savePath + saveFileName);
						 
					int readBytes = 0;
					byte[] buffer = new byte[8192];
						
					while ((readBytes = inputStream.read(buffer, 0 , 8192)) != -1) {
						outputStream.write(buffer, 0, readBytes);
					}
					if("inappFile".equals(map.get("fileType"))){
					}else{
						//���� �����˼� �� �������� 
						BufferedImage uploadImages = ImageIO.read(new File(savePath + saveFileName));	
						int orgHeight = uploadImages.getHeight();
						int orgWidth = uploadImages.getWidth();
							
						//�������϶�
						int toBeWidth = 300;
						int toBeHeight = 300;
						/*if ("iconFile".equals(map.get("fileType"))) {							
							if(orgHeight!=orgWidth){
								map.put("error", messageSource.getMessage("app.control.003", null, localeResolver.resolveLocale(request)));
								outputStream.close();
								inputStream.close();
								return map;						
							}							 
						}*/
						/*if ("captureFile".equals(map.get("fileType"))) {
								
						if(orgHeight!=orgWidth){
							map.put("error", "�̹��� ������ ���� �ʽ��ϴ�.");
							outputStream.close();
							inputStream.close();
							return map;						
						}
						 */
						//orgHeight/orgWidth=toBeHeight/toBeWidth
						/*	if(orgHeight>768){
							toBeWidth = Integer.valueOf(orgWidth/orgHeight*768);
							toBeHeight = 768;							
							}else{
								toBeWidth = orgWidth;
								toBeHeight = orgHeight;
							}
						}*/
						BufferedImage originalImage = ImageIO.read(new File(savePath + saveFileName));
						BufferedImage resizeImage = ImageTask.resizeImage(originalImage, originalImage.getType(), toBeWidth, toBeHeight);
						ImageIO.write(resizeImage, fileExt, new File(savePath + saveFileName));
					}
						
					map.put("webPath", webPath+saveFileName);
//					System.out.println("webPath+saveFileName = " + webPath+saveFileName);
					map.put("error", "none");
					//���� �����˼� �� �������� 
					outputStream.close();
					//outputStream.flush();
					inputStream.close();
					outputStream = null;
					inputStream = null;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", messageSource.getMessage("app.control.004", null, localeResolver.resolveLocale(request)));		}
		return map;
	}

	/**
	 * ĸ�� �̹��� ���ε�
	 */
	@RequestMapping(value = "app/capturefileupload.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> app_capture_file_upload(HttpServletRequest request, HttpSession session, @RequestParam("captureFile") MultipartFile file){
		System.out.println("@@@@@@@@@@@@app/capturefileupload.html");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try{
			String fileType = request.getParameter("fileType");
			map.put("fileType", fileType);
			map = uploadFile(map, file, request);
		}catch(Exception e){
			return map;			
		}
		return map;
	}
	/**
	 * provid check
	 */
	@RequestMapping(value = "app/checkprovid.html", method = RequestMethod.GET)
	public @ResponseBody int app_check_provid(HttpServletRequest request, String companySeq,  HttpSession session, BundleVO vo, int osType) throws Exception{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		vo.setOsType(osType);
		int cnt = 99;
		try{
			cnt = bundleService.getListCount(vo, Integer.parseInt(companySeq));
			return cnt;
		}catch(Exception e){
			e.printStackTrace();
			map.put("cnt", cnt);
			return cnt;
		}
	}

  	@RequestMapping(value = "app/deleteBoth.html", method = RequestMethod.POST)
	public ModelAndView appDeleteBothPOST(HttpSession session, HttpServletRequest request) {
  		
  		ModelAndView mav = new ModelAndView();
  		String currentPage, appSeq, searchValue, isAvailable, storeBundleId ;
  		
  		currentPage = paramSet(request, "currentPage");
  		searchValue = paramSet(request, "searchValue");
  		isAvailable = paramSet(request, "isAvailable");
  		appSeq = paramSet(request, "appSeq");
  		storeBundleId = paramSet(request, "storeBundleId");
  		
  		try{
  			appService.deleteAppInfo(Integer.parseInt(appSeq));
  			bundleService.delete(Integer.parseInt(appSeq));//번들 삭제
  			inAppService.deleteInAppInfo(storeBundleId);
  			mav.setViewName("redirect:/app/list.html?currentPage="+currentPage+"&appSeq="+appSeq+"&searchValue="+searchValue+"&isAvailable="+isAvailable);
  		}catch(Exception e){
  			e.printStackTrace();
  			
  			mav.setViewName("/inc/dummy");
  	  		mav.addObject("msg", messageSource.getMessage("app.control.001", null, localeResolver.resolveLocale(request)));
  	  		mav.addObject("type", "-1");
  		}

  		return mav;
  	}
  	@RequestMapping(value={"/app/log/list.html"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView manLogListGET(String startDate, String endDate, HttpSession session, HttpServletRequest request)
      throws Exception
    {
      ModelAndView mav = new ModelAndView();
      myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      
      AppVO appVO = null;
      
      int isSingle = 0;
      String storeBundleId;
      
      String page = paramSet(request, "page");
      String decodeValue = paramSet(request, "decodeValue");
      
      String searchType = paramSet(request, "searchType");
      String searchValue = paramSet(request, "searchValue");
      if ((paramSet(request, "inappSeq") != null) && (!"".equals(paramSet(request, "inappSeq"))))
      {
        storeBundleId = paramSet(request, "inappSeq");
        isSingle = 2;
      }
      else
      {
        storeBundleId = paramSet(request, "storeBundleId");
        appVO = this.appService.selectByStoreId(storeBundleId);
        isSingle = 1;
      }
      if (searchValue != null) {
        decodeValue = URLDecoder.decode(searchValue, "UTF-8");
      }
      LogList addList = this.logService.selectLogList(Integer.parseInt(page), storeBundleId, decodeValue, searchType, startDate, endDate);
      
      mav.addObject("searchValue", decodeValue);
      mav.addObject("logList", addList);
      if (appVO != null) {
        mav.addObject("isSingle", Integer.valueOf(isSingle));
      }
      mav.addObject("startDate", startDate);
      mav.addObject("endDate", endDate);
      mav.setViewName("01_app/log_list");
      
      return mav;
    }  	

  	@RequestMapping(value = "app/log/dataView.html", method = RequestMethod.POST)
    public ModelAndView logView(String startDate, String endDate, HttpSession session, HttpServletRequest request)
      throws Exception
    {
      ModelAndView mav = new ModelAndView();
      int log_seq = Integer.parseInt(paramSet(request, "log_seq"));

      LogVO logData = this.logService.selectLogInfo(log_seq);
/*      
      HashMap<String, Object> rs = new ObjectMapper().readValue(logData.getData(), HashMap.class) ;
      
      System.out.println("==hashmap 출력==") ;
      System.out.println(rs) ;
       
      System.out.println() ;
       
      String gender = (String)rs.get("gender") ;
      boolean verified = (Boolean)rs.get("verified") ;
       
      HashMap<String, Object> name = (HashMap)rs.get("name") ;
*/ 
      mav.addObject("logData", logData);
      mav.setViewName("01_app/log_data");
      return mav;
    }
  	
  	@RequestMapping(value = "app/deleteApp.html", method = RequestMethod.POST)
	public ModelAndView appDeleteAppPOST(HttpSession session, HttpServletRequest request) {
  		
  		ModelAndView mav = new ModelAndView();
  		String currentPage, appSeq, searchValue, isAvailable ;
  		
  		currentPage = paramSet(request, "currentPage");
  		searchValue = paramSet(request, "searchValue");
  		isAvailable = paramSet(request, "isAvailable");
  		appSeq = paramSet(request, "appSeq");
  		
  		try{
  			appService.deleteAppInfo(Integer.parseInt(appSeq));//앱 삭제
  			//appService.deleteAppHistoryInfo(Integer.parseInt(appSeq));//앱 히스토리 삭제
  			bundleService.delete(Integer.parseInt(appSeq));//번들 삭제
  			//noticeService.deleteNoticeSubAppSeqInfo(Integer.parseInt(appSeq));//공지 앱 삭제
  			//appService.deleteAppSubAppSeqInfo(Integer.parseInt(appSeq));//앱서브 삭제
  			//프로비젼 삭
  			
  			mav.setViewName("redirect:/app/list.html?currentPage="+currentPage+"&appSeq="+appSeq+"&searchValue="+searchValue+"&isAvailable="+isAvailable);
  		}catch(Exception e){
  			e.printStackTrace();
  			
  			mav.setViewName("/inc/dummy");
  	  		mav.addObject("msg", messageSource.getMessage("app.control.001", null, localeResolver.resolveLocale(request)));
  	  		mav.addObject("type", "-1");
  		}
  		return mav;
  	}

	@RequestMapping(value = "app/inapp/list.html", method = {RequestMethod.GET, RequestMethod.POST})	//5. 인앱콘텐츠 리스트 팝업 - 리스트 페이지
	public String app_inapp_list(HttpSession session, HttpServletRequest request, ModelMap modelMap, InAppList inAppList, InappVO vo) {		

		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		AppVO appVO = appService.selectByStoreId(vo.getStoreBundleId());
		
		if("ROLE_ADMIN_SERVICE".equals(activeUser.getAuthorities().iterator().next().getAuthority())) {
			activeUser.setMemberVO(appVO.getRegMemberVO());
		}

		vo.setStoreBundleId(appVO.getStoreBundleId());
		int completAndUsingCnt =0;
		if(inAppList.getCurrentPage()==null)inAppList.setCurrentPage(1);
		inAppList = inAppService.getListByBundleId(vo, inAppList, activeUser.getMemberVO());
		
		if(inAppList != null && inAppList.getList() != null) {
			for( int i =0 ; i< inAppList.getList().size() ; i++) {
				if( "1".equals(inAppList.getList().get(i).getCompletGb()) && "1".equals(inAppList.getList().get(i).getUseGb()) ) completAndUsingCnt++;
			}
		}

		modelMap.addAttribute("appContentsAmt", appVO.getAppContentsAmt());
		modelMap.addAttribute("appContentsGb", appVO.getAppContentsGb());
		modelMap.addAttribute("vo", vo);
		modelMap.addAttribute("inAppList", inAppList);
		modelMap.addAttribute("availableCnt", completAndUsingCnt);
		
		//20180515 : lsy - GroupViewMenu Util Create
		Map<String, Object> menuList = new HashMap<String, Object>();
		menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
		
		modelMap.addAttribute("menuFunction", menuList.get("menuFunction"));
		//20180515 : lsy - GroupViewMenu Util Create - end

		return "01_app/app_pop_inapp";
	}
  

	@RequestMapping(value = "app/inapp/regist.html", method = RequestMethod.GET)	//5-1. 인앱콘텐츠 등록 - 페이지 호출
	public String app_inapp_regist(HttpSession session, String appSeq, HttpServletRequest request, ModelMap modelMap, InAppList inAppList, InappVO vo) {
		modelMap.addAttribute("vo", vo);
		modelMap.addAttribute("inAppList", inAppList);
    	return "01_app/app_write_inapp";
    }

	@RequestMapping(value = "app/inapp/regist.html", method = RequestMethod.POST)	//5-1. 인앱콘텐츠 등록 - 처리
	public String app_inapp_regist_impl(AppVO appVO, String appSeq, String storeBundleId, HttpServletRequest request, ModelMap modelMap, InAppList inAppList, InappVO vo, @RequestParam("inappFile") MultipartFile file) throws Exception {
		//Map reqMap = WebUtil.getRequestMap(request); // Request Map ��ü ����
		//Map model = new HashMap();	
		/*
		String area_idx = StringUtil.getVConv(reqMap.get("area"), "I");
		String searchAreOverseasYn = StringUtils.defaultString(StringUtil.getVConv( reqMap.get("oversea"), "S", "N")); // ���� N , �ؿ� Y
		*/
		//WebUtil.checkParameter(request);		

		//System.out.println(appVO.toString());
		//����� ����
		String returnUrl = "redirect:/app/inapp/list.html";
		try{
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			String fileType = "inappFile";
			map.put("fileType", fileType);
			map = uploadFile(map, file, request);
			if(!"none".equals(map.get("error"))){
				returnUrl = "/inc/dummy";
				modelMap.addAttribute("msg", map.get("error"));
				modelMap.addAttribute("type", "-1");
				return returnUrl;
			}
			vo.setInappOrgFile((String)map.get("orgFileName"));
			vo.setInappSaveFile((String)map.get("saveFileName"));
/*		
			long fileSize = (Long) map.get("fileSize");
			vo.setInappSize( String.valueOf(fileSize));
*/			vo.setInappSize( (String)map.get("fileSize"));

			myUserDetails activeUser = null;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				throw new AuthenticationException();
			}else {
				activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}		
			//for member seq
			int regUserSeq = activeUser.getMemberVO().getUserSeq();
			//for company seq
			//provSeq
			String regUserId = activeUser.getMemberVO().getUserId();
			String regUserGb = activeUser.getMemberVO().getUserGb();
			vo.setRegUserSeq(regUserSeq);
			vo.setRegUserId(regUserId);
			vo.setRegUserGb(regUserGb);
			vo.setChgUserSeq(regUserSeq);
			vo.setChgUserId(regUserId);
			vo.setChgUserGb(regUserGb);
	/*		List list = provisionService.selectList(vo);
	    	modelMap.addAttribute("provisionList", list);
	    	modelMap.addAttribute("provisionVo", vo);*/
			//db�Է�
			int inappSeq = inAppService.getSeqAfterInsertInAppInfo(vo);
			//�̹��� ó��
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.icon.file", null, localeResolver.resolveLocale(request));				
			//�������̹���
			String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....����
			}
			//ĸ���̹��� ó��
			String[] imgOrgFileArr = request.getParameterValues("imgOrgFile");
			String[] imgSaveFileArr = request.getParameterValues("imgSaveFile");
			String imgOrgFile = null;
			String imgSaveFile = null;
			toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.capture.file", null, localeResolver.resolveLocale(request));				
			if(imgSaveFileArr!=null&&imgSaveFileArr.length>0){
				for(int i=0;i<imgSaveFileArr.length;i++){
					imgOrgFile = imgOrgFileArr[i];				 
					imgSaveFile = imgSaveFileArr[i];				 
					if(FileUtil.movefile(imgSaveFile, tempPath, toPath)){
						//....���� tb_capture
						CaptureVO captureVO = new CaptureVO();
						captureVO.setCaptureGb("2");
						captureVO.setBoardSeq(inappSeq);
						captureVO.setUserSeq(regUserSeq);
						captureVO.setImgOrgFile(imgOrgFile);
						captureVO.setImgSaveFile(imgSaveFile);
						captureService.insert(captureVO);					
					}
				}
			}

		  /*  XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;

		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    for(int r = 0; r < rows; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((int)c);
		                if(cell != null) {
		                    // Your code here
		                	System.out.println("@@@@@@@@@@@@@ cell value = " + cell.getStringCellValue());
		                }
		            }
		        }
		    }*/

		String url = "redirect:/app/inapp/list.html";
		String parameters = "?storeBundleId="+storeBundleId;
		returnUrl =  url + parameters;		
    	return returnUrl;
		}catch(Exception e){
			e.printStackTrace();
			returnUrl = "/inc/dummy";
			//message : ��� ����
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
    }

	@RequestMapping(value = "app/inapp/modify.html", method = RequestMethod.GET)	//5-2. 인앱콘텐츠 수정 - 페이지 호출
	public String app_inapp_modify(HttpSession session, HttpServletRequest request, String inappSeq, ModelMap modelMap, CaptureVO cvo, InAppList inAppList, InappVO ivo) {
		List captureList = null;
		List<InappSubVO> addList;
		String useS = "";
		//List bundleList = null;
		int completAndUsingCnt = 0;
		
		AppVO appVO = appService.selectByStoreId(ivo.getStoreBundleId());

		try{
			myUserDetails activeUser = null;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				throw new AuthenticationException();
			}else {
				activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
			ivo = inAppService.selectForUpdate(ivo, activeUser.getMemberVO());
			//BundleVO bvo = new BundleVO();
			//bundleList = bundleService.listByAppSeq(appVO.getAppSeq());
			cvo.setCaptureGb("2");
			cvo.setBoardSeq(ivo.getInappSeq());
			captureList = captureService.selectListByBoardSeqWithGb(cvo);

			if(inAppList.getCurrentPage()==null)inAppList.setCurrentPage(1);
			inAppList = inAppService.getListByBundleId(ivo, inAppList, activeUser.getMemberVO());
			
			//System.out.println(appVO.toString());
			for( int i =0 ; i< inAppList.getList().size() ; i++) {
				if( "1".equals(inAppList.getList().get(i).getCompletGb()) && "1".equals(inAppList.getList().get(i).getUseGb()) ) completAndUsingCnt++;
			}
			addList = inAppService.selectInAppSubList(Integer.parseInt(inappSeq));
			
			//TemplateList templateList = null;
			for(int i = 0; i < addList.size(); i++){
				if( i== 0)useS  += addList.get(i).getUserSeq();
				else useS  += ","+addList.get(i).getUserSeq();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		modelMap.addAttribute("appContentsAmt", appVO.getAppContentsAmt());
		modelMap.addAttribute("appContentsGb", appVO.getAppContentsGb());
		modelMap.addAttribute("useS", useS);
		modelMap.addAttribute("ivo", ivo);
		modelMap.addAttribute("availableCnt", completAndUsingCnt);
		modelMap.addAttribute("inAppList", inAppList);
		//modelMap.addAttribute("bundleList", bundleList);
		modelMap.addAttribute("captureList", captureList);

		//20180423 : lsy - after process, return page gubun
		if(request.getHeader("referer").indexOf("/distribute") != -1 && request.getHeader("referer").indexOf("/distribute") > 0) {
			modelMap.addAttribute("referer", "distribute");
		}else if(request.getHeader("referer").indexOf("/inapp") != -1 && request.getHeader("referer").indexOf("/inapp") > 0) {
			modelMap.addAttribute("referer", "list");
		}
		//20180423 : lsy - after process, return page gubun - end
		
    	return "01_app/app_modify_inapp";
    }

	@RequestMapping(value = "app/inapp/modify.html", method = RequestMethod.POST)	//5-2. 인앱콘텐츠 수정 - 처리
	public String app_inapp_modify_impl(HttpSession session, HttpServletRequest request, String[] useS, ModelMap modelMap, CaptureVO cvo, InappVO ivo, InAppList inappList, @RequestParam("inappFile") MultipartFile file) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		GrantedAuthority element = authorities.iterator().next();
		String authority = element.getAuthority();

		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		InappSubVO inappSubVO = new InappSubVO();

		try {
			//1. update information VS exist DB information compare + user login check
			InappVO ivo2 = inAppService.selectForUpdate(ivo, activeUser.getMemberVO());
			if(ivo2 == null){
				String returnUrl = "/inc/dummy";
				//message : �߸��� �����Դϴ�.
				modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
				modelMap.addAttribute("type", "-1");
				return returnUrl;
			}

			//2. inapp usegb compare -> change usegb : update DB
			if(ivo2.getUseGb() != null && !ivo2.getUseGb().equals(ivo.getUseGb())){
				if("1".equals(ivo.getUseGb())){
					ivo.setUseAvailDt(new Date());
				}else if("2".equals(ivo.getUseGb())){
					ivo.setUseDisableDt(new Date());
				}
			}

			if("ROLE_ADMIN_SERVICE".equals(authority)){
				//3. inapp libmitgb compare -> change libmitgb : update DB
				if(ivo2.getLimitGb() != null && !ivo2.getLimitGb().equals(ivo.getLimitGb())){
					if("1".equals(ivo.getLimitGb())){
						ivo.setLimitDt(new Date());
					}
				}
			}

			if(ivo.getInappSaveFile() == null){
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				String fileType = "inappFile";
				map.put("fileType", fileType);
				map = uploadFile(map, file, request);
				ivo.setInappOrgFile((String)map.get("orgFileName"));
				ivo.setInappSaveFile((String)map.get("saveFileName"));
				ivo.setInappSize((String)map.get("fileSize"));
				//System.out.println("map.get('error')====="+"["+map.get("error")+"]");
				if(!"none".equals(map.get("error"))){
					String returnUrl = "/inc/dummy";
					modelMap.addAttribute("msg", map.get("error"));
					modelMap.addAttribute("type", "-1");
					return returnUrl;
				}
				//20180404 : lsy - contentsUpdate OK -> chg_contents_date update
				ivo.setChgContentsDt(new Date());
			}

			ivo.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
			ivo.setChgUserId(activeUser.getMemberVO().getUserId());
			ivo.setChgUserGb(activeUser.getMemberVO().getUserGb());

			//20180327 - lsy : develop version managemenet	-> name + storebundleID + version : unique
			String inappCompletGb = inAppService.selectCompletGbBySeq(ivo.getInappSeq());
			if(inappCompletGb.equals("3") && (ivo.getCompletGb() != null && ivo.getCompletGb().equals("1"))) {		//1. completgb modify yes check (seq check)
				String historySeq = inAppService.getSameInappSeq(ivo.getInappName(), ivo.getStoreBundleId());		//2. same inapp(name + storebundleID + complet yes) exist check
				if(historySeq != null) {																			//3. inapp exist, newVersion insert to tb_inapphistory / oldVersion delete from tb_inapp
					inAppService.insertInappHistory(historySeq);
					inAppService.deleteInAppBySeq(historySeq);
				}
			}
			//20180327 - lsy : develop version managemenet - end
			
			//5. 배포요청일 경우 - 배포요청일 Update		=> 20180410 : lsy - distribute process modify 
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeReq")) {
				//20180404 : lsy - completYN : history process OK -> distribute_req_dt update
				ivo.setDistributeReqDt(new Date());
				ivo.setDistributeRequestId(activeUser.getMemberVO().getUserId());
			}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) { 
				//6. 배포승인일 경우 - 배포승인일 Update		=> 20180417 : lsy - distribute process modify(distribute Complet)
				ivo.setDistributeCompletDt(new Date());
				ivo.setDistributeAcceptId(activeUser.getMemberVO().getUserId());
			}

			//20180405 : lsy - info change -> chg_dt update - 1
			int infoUpdateCheck = inAppService.infoUpdateCheck(ivo, ivo.getInappSeq());
			
			//�̹��� ó��
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.icon.file", null, localeResolver.resolveLocale(request));				
			//�������̹���
			String iconOrgFile = ivo.getIconOrgFile();
			String iconSaveFile = ivo.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....����
			}
			//ĸ���̹��� ó��
			String[] captureSeqArr = request.getParameterValues("captureSeq");
			String[] imgOrgFileArr = request.getParameterValues("imgOrgFile");
			String[] imgSaveFileArr = request.getParameterValues("imgSaveFile");
			String captureSeq = null;
			String imgOrgFile = null;
			String imgSaveFile = null;
			toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.capture.file", null, localeResolver.resolveLocale(request));				
			if(imgSaveFileArr!=null&&imgSaveFileArr.length>0){
				for(int i=0;i<imgSaveFileArr.length;i++){
					captureSeq = captureSeqArr[i];
					imgOrgFile = imgOrgFileArr[i];
					imgSaveFile = imgSaveFileArr[i];
					if("0".equals(captureSeq)&&FileUtil.movefile(imgSaveFile, tempPath, toPath)){
						//....���� tb_capture
						CaptureVO captureVO = new CaptureVO();
						captureVO.setCaptureGb("2");
						captureVO.setBoardSeq(ivo.getInappSeq());
						captureVO.setUserSeq(activeUser.getMemberVO().getUserSeq());
						captureVO.setImgOrgFile(imgOrgFile);
						captureVO.setImgSaveFile(imgSaveFile);
						captureService.insert(captureVO);		
						captureVO = null;
					}
				}
				//20180405 : lsy - info change -> chg_dt update - 2
				infoUpdateCheck += 1;
			}

			//��������
			//captureSeq
/*			var html = '<input typd="hidden" name="deleteFileSeq" value="'+captureSeq+'"/>';
			html += '<input typd="hidden" name="deleteSaveFileName" value="'+saveFileName+'"/>';
			html += '<input typd="hidden" name="deleteFileType" value="'+thisFileType+'"/>';*/			
			String[] deleteFileSeqArr = request.getParameterValues("deleteFileSeq");
			String[] deleteSaveFileNameArr = request.getParameterValues("deleteSaveFileName");
			String[] deleteFileTypeArr = request.getParameterValues("deleteFileType");
			Integer deleteFileSeq = null;
			String deleteSaveFileName = null;
			String deleteFileType = null;
			String deleteFilePath = null;
			
			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			if(deleteFileTypeArr != null)
			for(int i =0; i < deleteFileSeqArr.length ; i ++)
			System.out.println("deleteFileSeqArr = " + deleteFileSeqArr);
			if(deleteSaveFileNameArr != null)
			for(int i =0; i < deleteSaveFileNameArr.length ; i ++)
			System.out.println("deleteSaveFileNameArr = " + deleteSaveFileNameArr);
			if(deleteSaveFileNameArr != null)
			for(int i =0; i < deleteSaveFileNameArr.length ; i ++)
			System.out.println("deleteFileTypeArr = " + deleteFileTypeArr);
			
			if(deleteFileTypeArr!=null&&deleteFileTypeArr.length>0){
				for(int i=0;i<deleteFileTypeArr.length;i++){
					deleteFileSeq = Integer.parseInt(deleteFileSeqArr[i]);
					deleteSaveFileName = deleteSaveFileNameArr[i];
					deleteFileType = deleteFileTypeArr[i];
					if("capture".equals(deleteFileType)){//�������� ����
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.capture.file", null, localeResolver.resolveLocale(request));
						if(FileUtil.delete(new File(deleteFilePath+deleteSaveFileName))){
							CaptureVO captureVO = new CaptureVO();
							captureVO.setCaptureSeq(deleteFileSeq);
							captureService.delete(captureVO);
							captureVO = null;
						}
					}else if("icon".equals(deleteFileType)){//���������ϻ���
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.icon.file", null, localeResolver.resolveLocale(request));
						FileUtil.delete(new File(deleteFilePath+deleteSaveFileName));
					}else if("inapp".equals(deleteFileType)){//�ξ����ϻ���
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
						FileUtil.delete(new File(deleteFilePath+deleteSaveFileName));
					}
				}
			}

			inappSubVO.setInappSeq(ivo.getInappSeq());	
			if("1".equals(ivo.getUseUserGb()) && !"".equals(ivo.getInappSeq())){
				inAppService.deleteInAppSubInfo(inappSubVO);
			}

			//MemberVO memberVO = memberService.findByCustomInfo("userSeq", Integer.parseInt(useS[i]));
			List<MemberVO> memberList = memberService.getPermitList(activeUser.getMemberVO().getCompanySeq(), useS);
			if("2".equals(ivo.getUseUserGb()) && !"".equals(ivo.getInappSeq())){
				inAppService.deleteInAppSubInfo(inappSubVO);
				for(int i=0; i<useS.length; i++){
					inappSubVO.setUserSeq(Integer.parseInt(useS[i]));
					for( int j=0; j < memberList.size(); j++) {
						if ( memberList.get(j).getUserSeq() == Integer.parseInt(useS[i]) ) {
							if(memberList.get(j).getTwodepartmentSeq() == null ) {
								inappSubVO.setDepartmentSeq(memberList.get(j).getOnedepartmentSeq());
							}else {
								inappSubVO.setDepartmentSeq(memberList.get(j).getTwodepartmentSeq());
							}
							inAppService.insertInAppSubInfo(inappSubVO);
						}
					}
				}
			}

			//20180405 : lsy - info change -> chg_dt update - 3
			if(infoUpdateCheck >= 1) {
				ivo.setChgDt(new Date());
			}
			
			inAppService.updateInAppInfo(ivo, ivo.getInappSeq());
			/*XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // No of columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it doesn't start from first few rows
			for(int i = 0; i < 10 || i < rows; i++) {
			    row = sheet.getRow(i);
			    if(row != null) {
			            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			            if(tmp > cols) cols = tmp;
			        }
			    }

			    for(int r = 0; r < rows; r++) {
			        row = sheet.getRow(r);
			        if(row != null) {
			            for(int c = 0; c < cols; c++) {
			                cell = row.getCell((int)c);
			                if(cell != null) {
			                    // Your code here
			                	
			                	System.out.println("@@@@@@@@@@@@@ cell value = " + cell.getStringCellValue());
			                	System.out.println("@@@@@@@@@@@@@ cell Type = " + cell.getCellType() );
			                	
			                }
			            }
			        }
			    }*/

			String url = "redirect:/app/inapp/list.html";
			String parameters = "?&storeBundleId="+ivo.getStoreBundleId();
			//20180418 : lsy - distribute restore process modify
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) {
				if(request.getParameter("referer").equals("list")) {
					return url + parameters;
				}else{
					return "redirect:/distribute/list.html?page=1&objectGb=inapp&myListGb=n&ret=y";			
				}
			}else {
				return url + parameters;
			}
			//20180418 : lsy - distribute restore process modify - end
		}catch(Exception e){
			e.printStackTrace();
			String returnUrl = "/inc/dummy";
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;			
		}
    }

	@RequestMapping(value = "app/template/appcontentsamt.html", method = RequestMethod.GET)
	public @ResponseBody TemplateVO app_get_template_app_contents_amt(HttpServletRequest request, TemplateVO vo){
		try{
			int seq = vo.getTemplateSeq();
			vo = templateService.selectByTempId(seq);
		}catch(Exception e){
			e.printStackTrace();
			return vo;			
		}
		return vo;
	}

	@RequestMapping(value="/app/category/category_write.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView categoryWrite(HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();	
		String storeBundleId, userSeq, isInapp;
		storeBundleId = userSeq =isInapp = "";
		storeBundleId 		= this.paramSet(req, "storeBundleId");
		isInapp 		= this.paramSet(req, "isInapp");
		
		//�α�������
		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		userSeq	=	String.valueOf(activeUser.getMemberVO().getUserSeq());
		
		InappcategoryVO InCateVo = new InappcategoryVO();		
		InCateVo.setStoreBundleId(storeBundleId);		
		List<InappcategoryVO> InAppList = inAppCategoryService.selectInAppList(InCateVo);
	
		
		mav.addObject("InAppList", InAppList);
		if(InAppList != null)
		mav.addObject("InAppCnt", InAppList.size());
		mav.addObject("storeBundleId", storeBundleId);
		mav.addObject("userSeq", userSeq);
		mav.addObject("isInapp", isInapp);
		mav.setViewName("/01_app/app_pop_category");
		return mav;
	}
  
  
	@RequestMapping(value="/app/category/category_writeOK.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody InappcategoryVO categoryWriteOK(HttpSession session, HttpServletRequest req) throws Exception{
		
		String storeBundleId, userId, userSeq, userGb, cateName, depth, categorySeq1;
		storeBundleId = userId = userSeq = userGb = cateName = depth = categorySeq1 = "";
		
		storeBundleId = this.paramSet(req, "storeBundleId");
		cateName 	  = this.paramSet(req, "cateName");
		depth 		  = this.paramSet(req, "depth");
		categorySeq1  = this.paramSet(req, "categorySeq1");
		//categorySeq2 = this.paramSet(req, "categorySeq2");
		
		AppVO appVO = appService.selectByStoreId(storeBundleId);
		//�α�������
		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		userSeq	=	String.valueOf(activeUser.getMemberVO().getUserSeq());
		userId	=	activeUser.getMemberVO().getUserId();
		userGb	=	activeUser.getMemberVO().getUserGb();
		
		InappcategoryVO InCateVo = new InappcategoryVO();

		InCateVo.setStoreBundleId(storeBundleId);
		InCateVo.setCategoryName(cateName);
		if(Integer.parseInt(depth) == 1){
			InCateVo.setCategoryParent(0);
			//InCateVo.setCategorySeq(Integer.parseInt(categorySeq1));
		}else{
			InCateVo.setCategoryParent(Integer.parseInt(categorySeq1));//�θ� ������ �־����.
			//InCateVo.setCategorySeq(Integer.parseInt(categorySeq2));
		}
		InCateVo.setDepth(depth);
		InCateVo.setRegUserSeq(Integer.parseInt(userSeq));
		InCateVo.setRegUserId(userId);
		InCateVo.setRegUserGb(userGb);
		InCateVo.setRegDt(new Date());
		InCateVo.setChgUserSeq(Integer.parseInt(userSeq));
		InCateVo.setChgUserId(userId);
		InCateVo.setChgUserGb(userGb);
		InCateVo.setChgDt(new Date());

		int categorySeq = inAppCategoryService.insertInAppInfo(InCateVo);
		InCateVo.setCategorySeq(categorySeq);

		return InCateVo;
	}
  
  
  	@RequestMapping(value="/app/category/category_modifyOK.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody InappcategoryVO categoryModifyOK(HttpSession session, HttpServletRequest req) throws Exception{
		String storeBundleId, userId, userSeq, userGb, cateName, depth, categorySeq1, categorySeq2;
		storeBundleId = userId = userSeq = userGb = cateName = depth = categorySeq1 = categorySeq2 = "";

		storeBundleId= this.paramSet(req, "storeBundleId");
		cateName 	 = this.paramSet(req, "cateName");
		depth 		 = this.paramSet(req, "depth");
		categorySeq1 = this.paramSet(req, "categorySeq1");
		categorySeq2 = this.paramSet(req, "categorySeq2");

		AppVO appVO = appService.selectByStoreId(storeBundleId);
		//�α�������
		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		userSeq	=	String.valueOf(activeUser.getMemberVO().getUserSeq());
		userId	=	activeUser.getMemberVO().getUserId();
		userGb	=	activeUser.getMemberVO().getUserGb();

		InappcategoryVO InCateVo = new InappcategoryVO();
		InCateVo.setStoreBundleId(storeBundleId);
		InCateVo.setCategoryName(cateName);
		if(Integer.parseInt(depth) == 1){
			InCateVo.setCategoryParent(0);
			InCateVo.setCategorySeq(Integer.parseInt(categorySeq1));
		}else{
			//InCateVo.setCategoryParent(Integer.parseInt(categorySeq2));//�θ� ������ �־����.
			InCateVo.setCategorySeq(Integer.parseInt(categorySeq2));
		}
		InCateVo.setDepth(depth);
		InCateVo.setRegUserSeq(Integer.parseInt(userSeq));
		InCateVo.setRegUserId(userId);
		InCateVo.setRegUserGb(userGb);
		InCateVo.setRegDt(new Date());
		InCateVo.setChgUserSeq(Integer.parseInt(userSeq));
		InCateVo.setChgUserId(userId);
		InCateVo.setChgUserGb(userGb);
		InCateVo.setChgDt(new Date());
		inAppCategoryService.updateInAppInfo(InCateVo);
		return InCateVo;
	}

  	@RequestMapping(value="/app/category/category_deleteOK.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody InappcategoryVO categoryDeleteOK(HttpSession session, HttpServletRequest req) throws Exception{
		
		String storeBundleId, cateName, depth, categorySeq1, categorySeq2;
		storeBundleId = cateName = depth = categorySeq1 = categorySeq2 = "";
		
		storeBundleId	= this.paramSet(req, "storeBundleId");
		cateName 	 	= this.paramSet(req, "cateName");
		depth 		 	= this.paramSet(req, "depth");
		categorySeq1 	= this.paramSet(req, "categorySeq1");
		categorySeq2 	= this.paramSet(req, "categorySeq2");
		
		AppVO appVO = appService.selectByStoreId(storeBundleId);
		
		//�α�������
		
		/*myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userSeq	=	String.valueOf(activeUser.getMemberVO().getUserSeq());
		userId	=	activeUser.getMemberVO().getUserId();
		userGb	=	activeUser.getMemberVO().getUserGb();*/

		InappcategoryVO InCateVo = new InappcategoryVO();

		InCateVo.setStoreBundleId(storeBundleId);
		InCateVo.setCategoryName(cateName);
		if(Integer.parseInt(depth) == 1){
			InCateVo.setCategoryParent(0);
			InCateVo.setCategorySeq(Integer.parseInt(categorySeq1));
		}else{
			InCateVo.setCategoryParent(Integer.parseInt(categorySeq2));//�θ� ������ �־����.
			InCateVo.setCategorySeq(Integer.parseInt(categorySeq2));
		}
		inAppCategoryService.deleteInAppInfo(InCateVo);
		return InCateVo;
	}	
  	
	@RequestMapping(value="/app/inapp/getList.html" ,method= RequestMethod.POST)
	public @ResponseBody List<InappVO> appInappGetListPOST(String storeBundleId,  HttpSession session, HttpServletRequest req) throws Exception{
		List<InappVO> list = null;

		System.out.println("storeBundleId = " + storeBundleId);
		if(storeBundleId != null && !"".equals(storeBundleId)) {
			list = inAppService.getListInappIsAvailableByStoreBundleId( storeBundleId);
		}

		return list;
	}

  	@RequestMapping(value="/app/category/category_list2.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Object[] categoryList2(HttpSession session, HttpServletRequest req) throws Exception{
		
		String storeBundleId, userId, userSeq, userGb, cateName, depth, categorySeq1 ;
		storeBundleId = userId = userSeq = userGb = cateName = depth = categorySeq1 = "";
		
		storeBundleId 		= this.paramSet(req, "storeBundleId");
		cateName 	= this.paramSet(req, "cateName");
		depth 		= this.paramSet(req, "depth");
		categorySeq1 = this.paramSet(req, "categorySeq1");
		//categorySeq1 = this.paramSet(req, "categorySeq2");
				
		InappcategoryVO InCateVo = new InappcategoryVO();
		Object[] InAppList = null;
		System.out.println("====================================================================================1");
		InCateVo.setStoreBundleId(storeBundleId);
		InCateVo.setCategoryParent(Integer.parseInt(categorySeq1));	
		System.out.println("====================================================================================2");
		
		InAppList = inAppCategoryService.selectInAppList2(InCateVo);
		
		System.out.println("====================================================================================3");
		System.out.println(InAppList);
		System.out.println("====================================================================================4");
		
		return InAppList;
	}

	@RequestMapping(value="/app/history/list.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView appModifyHistory(HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("01_app/app_pop_modify_history");

		return mav;
	}

	@RequestMapping(value="/assignment/user.html" ,method=RequestMethod.GET)
	public ModelAndView assignmentUserGET(String[] useS,HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		myUserDetails activeUser = null;

		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		List<MemberVO> addList = memberService.getUserList(activeUser.getMemberVO().getCompanySeq(), useS, null, null);
		List<MemberVO> addList2 = memberService.getPermitList(activeUser.getMemberVO().getCompanySeq(), useS);

		if(addList != null) {
			for( int i =0; i< addList.size() ;i++){
				String tempString = addList.get(i).getUserId();
				if(tempString.length() > 11) {
					tempString = tempString.substring(0, 9);
					tempString +="...";
					addList.get(i).setUserId(tempString);
				}
			}
		}
		if(addList2 != null) {
			for( int i =0; i< addList2.size() ;i++){
				String tempString = addList2.get(i).getUserId();
				if(tempString.length() > 11) {
					tempString = tempString.substring(0, 9);
					tempString +="...";
					addList2.get(i).setUserId(tempString);
				}
			}
		}
		System.out.println("");
		mav.addObject("userList", addList);
		mav.addObject("permitList", addList2);
		mav.setViewName("01_app/assignment_user");
		return mav;
	}

	@RequestMapping(value="/assignment/app.html" ,method=RequestMethod.GET)
	public ModelAndView assignmentAppGET(Integer[] useA, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		List<AppVO> addList = appService.getListIsAvailableByCompanySeq(activeUser.getMemberVO().getCompanySeq());
		//List<AppVO> addList2 = appService.getPermitList(activeUser.getMemberVO().getCompanySeq(), useA);

		System.out.println("appList = " + addList);
		mav.addObject("appList", addList);
		mav.setViewName("05_admin/select_app_contents");
		return mav;
	}
	
	@RequestMapping(value="/assignment/search.html" ,method=RequestMethod.POST)
	public @ResponseBody List assignmentSearchPOST(String[] useS,HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		myUserDetails activeUser = null;

		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		
		String searchValue, searchType;
		searchValue = searchType = "";

		searchType = paramSet(req, "searchType");
		searchValue = paramSet(req, "searchValue");
		for( int i =0; i< useS.length ; i++) {
			System.out.println( "["+i+"] useS Value = " + useS[i] );
		}

		List<MemberVO> addList = memberService.getUserList(activeUser.getMemberVO().getCompanySeq(), useS, searchValue, searchType);

		if(addList != null) {
			for( int i =0; i< addList.size() ;i++){
				String tempString = addList.get(i).getUserId();
				if(tempString.length() > 11) {
					tempString = tempString.substring(0, 9);
					tempString +="...";
					addList.get(i).setUserId(tempString);
				}
			}
		}
		
		return addList;
	}
	
	
	@RequestMapping(value="/app/inapp/checkIfInappName.html" ,method=RequestMethod.POST)
	public @ResponseBody boolean checkIfInappNamePOST(String inappName, String storeBundleId, String verNum, String[] useS,HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		boolean isExist=  inAppService.checkInappNameIfExist(inappName, storeBundleId, verNum);
		
		return isExist;
	}

	//20180327 - lsy : develop version managemenet	-> modify method
	@RequestMapping(value="/app/history.html" ,method=RequestMethod.GET)
	public ModelAndView appHistoryGET(String app_name, String store_bundle_id, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<AppVO> appList = appService.selectAppForHistory(store_bundle_id);
		
		mav.addObject("app_name", URLDecoder.decode(app_name, "UTF-8"));
		mav.addObject("type_gb", "app");
		mav.addObject("appList", appList);
		mav.setViewName("inc/popup_history");

		return mav;
	}

	@RequestMapping(value="/inapp/history.html" ,method=RequestMethod.GET)
	public ModelAndView inappHistoryGET(String inapp_name, String store_bundle_id, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<InappVO> inappList = inAppService.selectForHistory(URLDecoder.decode(inapp_name, "UTF-8"), store_bundle_id);
		
		mav.addObject("inapp_name", URLDecoder.decode(inapp_name, "UTF-8"));
		mav.addObject("type_gb", "inapp");
		mav.addObject("inappList", inappList);
		mav.setViewName("inc/popup_history");

		return mav;
	}
	//20180327 - lsy : develop version managemenet	-> modify method - end

	//20180419 : lsy - distribute restore popup
	@RequestMapping(value="/app/restoreView.html" ,method=RequestMethod.GET)
	public ModelAndView appRestoreViewGET(DistributeRestoreVO distributeRestoreVO, String name, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<DistributeRestoreVO> distributeRestoreList = distributeService.selectForRestore(distributeRestoreVO);
		
		mav.addObject("name", name);
		mav.addObject("type_gb", "app");
		mav.addObject("list", distributeRestoreList);
		mav.setViewName("09_distribute/popup_restore");

		return mav;
	}
	
	@RequestMapping(value="/inapp/restoreView.html" ,method=RequestMethod.GET)
	public ModelAndView inappRestoreViewGET(DistributeRestoreVO distributeRestoreVO, String name, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<DistributeRestoreVO> distributeRestoreList = distributeService.selectForRestore(distributeRestoreVO);
		
		mav.addObject("name", name);
		mav.addObject("type_gb", "inapp");
		mav.addObject("list", distributeRestoreList);
		mav.setViewName("09_distribute/popup_restore");

		return mav;
	}
	//20180419 : lsy - distribute restore popup - end	
	
	private String paramSet(HttpServletRequest req, String targetName) {
		String value = "";
		value = (null == req.getParameter(targetName)) ? "" : "" + req.getParameter(targetName);
		
		return value;
	}

	//20180607 : lsy - app make request -> contents select
	@RequestMapping(value = "app/request.html", method = RequestMethod.GET)
	public ModelAndView appRequestSingle(HttpSession session, HttpServletRequest request, String radioSearch, String searchDate, String searchValue, String popupGb, String dupleVerNum, String dupleCheckYN, String nowVer, String ostype) {
		ModelAndView mav = new ModelAndView();
		List<ContentVO> contentList = null;
		Entity param = new Entity();

		List<ProvisionVO> provision = provisionService.selectProvisionInfoByAppSeqForMakePbapp(Integer.parseInt(request.getParameter("appSeq")));
		
		if(searchDate != null && !(searchDate.equals("")) ) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			String dateStr = dateFormat.format(date);
			cal.setTime(date);
			
			param.setValue("nowDate", dateStr);
			
			if(searchDate.equals("week1")) {
				cal.add(Calendar.DATE, -7);
			}else if(searchDate.equals("week2")) {
				cal.add(Calendar.DATE, -14);
			}else if(searchDate.equals("month1")) {
				cal.add(Calendar.MONTH, -1);
			}else if(searchDate.equals("month3")) {
				cal.add(Calendar.MONTH, -3);			
			}else if(searchDate.equals("month6")) {
				cal.add(Calendar.MONTH, -6);
			}
			dateStr = dateFormat.format(cal.getTime());
			param.setValue("beforeDate", dateStr);
			
			if(searchDate.equals("all")) {
				param.remove("beforeDate");
			}
		}
		if(searchValue != null && !(searchValue.equals("")) ) {
			param.setValue("searchValue", searchValue);
		}
		
		contentList = contentsService.getListContentsForAppMake(param);

		mav.addObject("radioSearch", radioSearch);
		mav.addObject("searchDate", searchDate);
		mav.addObject("searchValue", searchValue);
		mav.addObject("popupGb", popupGb);
		
		mav.addObject("dupleVerNum", dupleVerNum);
		mav.addObject("dupleCheckYN", dupleCheckYN);
		mav.addObject("nowVer", nowVer);
		
		String makeNewVersion = appService.selectAppVersionMax(Integer.parseInt(request.getParameter("appSeq")));
		String viewVer = VersionCheckUtil.newVerMake(makeNewVersion);		
		mav.addObject("viewVer", viewVer);
		
		mav.addObject("list", contentList);
		mav.addObject("provision", provision);
		mav.addObject("ostype", ostype);
		
		mav.setViewName("01_app/app_pop_request");
		
		return mav;
	}
	
	//20180607 : lsy - app make request -> contents select
	@RequestMapping(value = "app/request/duplicateVerNum.html", method = RequestMethod.POST)
	public @ResponseBody int appRequestDuplicateVerNum(HttpSession session, HttpServletRequest request) {
		Entity param = new Entity();
		param.setValue("store_bundle_id", request.getParameter("storeBundleId"));
		param.setValue("ostype", request.getParameter("ostype"));
		param.setValue("ver_num", request.getParameter("inputVerNum"));

		int resultCnt = appService.duplicateVerCheck(param);
		if(resultCnt == 0) {
			int digitCheck = VersionCheckUtil.digitCheck(request.getParameter("inputVerNum"));
			if(digitCheck == 0) {
				int verCheck = VersionCheckUtil.versionCompare(request.getParameter("inputVerNum"), request.getParameter("nowVerNum"));
				
				if(verCheck > 0) {//사용가능
					return 0;
				}else if(verCheck < 0) {//기존 버전보다 낮음
					return 3;
				}else {//버전명 변경안됐음
					return 4;
				}
			}else {//자리수가 3자리가 아님
				return 5;
			}
		}else if(resultCnt > 0) {//중복
			return 1;
		}else {//중복체크 중 에러
			return 2;
		}
	}
	
	//기존 콘텐츠로 -> 앱 갱신 / 새버전 추가
	@RequestMapping(value = "app/makeExist.html", method = RequestMethod.POST)
	public @ResponseBody int appMakeExist(HttpSession session, HttpServletRequest request, String contentSeq, String relatedAppName, String relatedInAppSeq, String relatedAppSeq, String relatedAppType, String relatedAppVerNum, String provisionSeq, String popupGb) {
		//1. 해당 앱 관련 > 기존에 관련앱으로 연결되어 있는 contents 검색해서 해당 내용 삭제 처리
		
		//2. json 만들어서 > pbapp 만들기<갱신>
		//2_1. 갱신(update) -> pbapp만 만들기(DB 처리 후, 2-4)
		//2_2. 새버전(newVer) -> 1) contents.zip파일 FTP, 2) icon.zip파일 FTP, 3) tb_app에 새버전 insert, 4) pbapp 만들기(DB 처리 후, 2-4)

		//3. tb_contents 관련앱 내용 update처리
		//4. tb_contentsapp_sub에 추가될 관련앱 내용 처리(지운 관련앱 관련 row 삭제 + 새로 추가된 관련앱 row 추가)
		//5(2-4). pbapp 생성
		
		FTPClient client = null;
		FileInputStream fis = null;
		String[] url = messageSource.getMessage("FTP.Info.IP", null, localeResolver.resolveLocale(request)).split(":");
		String ip = url[0];
		String port = url[1];
		String id = messageSource.getMessage("FTP.Info.ID", null, localeResolver.resolveLocale(request));
		String pwd = messageSource.getMessage("FTP.Info.PW", null, localeResolver.resolveLocale(request));
		
		//1. 관련앱 삭제 로직 + 4-1. 관련앱 정보 삭제(tb_contentsapp_sub)
		Entity param = new Entity();
		String relatedApp = relatedAppName+"("+relatedAppVerNum+")";
		int newVersionAppSeq = 0;
		
		if(popupGb.equals("update")) {	//갱신(update)일 때만 해당 관련앱정보 -> 기존 콘텐츠/_sub에서 삭제
			ContentVO contentInfoBefore = new ContentVO();
			contentInfoBefore = contentsService.selectRelatedAppinfo(relatedAppSeq);
			
			if(contentInfoBefore != null) {
				param.setValue("contentSeq", contentInfoBefore.getContentsSeq());
				
				String deleteApp = contentInfoBefore.getAppName();
				String deleteAppType = contentInfoBefore.getAppType();
				Entity deleteAppAfter = deleteAppInfo(deleteApp, deleteAppType, relatedApp);
				deleteAppAfter.setValue("contentSeq", contentInfoBefore.getContentsSeq());

				contentsService.updateRelatedAppinfo(deleteAppAfter);
				
				String deleteAppSeq = contentsService.selectRelatedAppSeqInfo(contentInfoBefore.getContentsSeq());
				String deleteAppSeqAfter = deleteString(deleteAppSeq, relatedAppSeq);
				param.setValue("relatedAppSeq", deleteAppSeqAfter);

				contentsService.updateRelatedAppSubInfo(param);
			}
		}//1. 관련앱 삭제 로직 - end

		try {
			param.setValue("contentSeq", contentSeq);
			
			//2. 앱 만들기
			AppVO appInfo = appService.selectBeforAppInfoBySeqForNewVersion(Integer.parseInt(relatedAppSeq));			
			//2-1. 앱용 콘텐츠 복사
			String appContentsFileName = contentsService.selectContentsFileName(Integer.parseInt(contentSeq));
			String contentsPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request))+"/"+appContentsFileName.substring(0, appContentsFileName.lastIndexOf("."));
			
			File uploadFile = new File(contentsPath, appContentsFileName); // File 객체
			
			client = new FTPClient(); // FTP Client 객체				
			client.setControlEncoding("UTF-8");
			client.connect(ip, Integer.parseInt(port));
			client.login(id, pwd);
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);
			
			client.makeDirectory(appInfo.getStoreBundleId());
			client.changeWorkingDirectory("/"+appInfo.getStoreBundleId());
			
			fis = new FileInputStream(uploadFile);
			boolean isSuccess = client.storeFile("contents.zip", fis);
			fis.close();
			
			if(isSuccess) {
				isSuccess = false;
			}else {
				return 1;
			}
			//2-1. 앱용 콘텐츠 복사 - end
			
			//2-2. 앱용 아이콘 사이즈별 만들기, 압축하기, FTP 전송
			if( appInfo.getIconSaveFile() != null && !appInfo.getIconSaveFile().equals("") ) {//icon이 있을 때만 icon 처리
				//1> 아이콘 만들 준비
				String iconPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
				String iconTempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
				String iconMakePath = iconTempPath+"icon/", iconMakeName = "icon.png";

				FileUtil.copy(new File(iconPath, appInfo.getIconSaveFile()), new File(iconTempPath, appInfo.getIconSaveFile()));
				File iconMake = new File(iconMakePath);
				iconMake.mkdirs();					

				//2> size별 아이콘 만들기
				int toBeWidth[];
				if(relatedAppType.equals("1") || relatedAppType.equals("2") || relatedAppType.equals("3")) {
					toBeWidth = new int[]{57, 114, 120, 72, 144, 76, 152};
//temp					FileUtil.copy(new File(iconTempPath, appInfo.getIconSaveFile()), new File(iconMakePath, iconMakeName));
				}else{
					toBeWidth = new int[]{96};
					FileUtil.copy(new File(iconTempPath, appInfo.getIconSaveFile()), new File(iconMakePath, iconMakeName));
				}

				String fileExt = appInfo.getIconSaveFile().substring(appInfo.getIconSaveFile().lastIndexOf(".") + 1, appInfo.getIconSaveFile().length());					
				BufferedImage originalImage = ImageIO.read(new File(iconTempPath, appInfo.getIconSaveFile()));
				
				for(int i=0;i<toBeWidth.length;i++) {
					String newFileName = "icon_" + toBeWidth[i] + "." +fileExt;
					BufferedImage resizeImage = ImageTask.resizeImage(originalImage, originalImage.getType(), toBeWidth[i], toBeWidth[i]);
					ImageIO.write(resizeImage, fileExt, new File(iconMakePath + newFileName));
				}
				
				//3> 아이콘 zip파일 만들기
				FileZipUtil.createZipFile(iconMakePath, iconTempPath, "icon.zip");
				
				//4> 아이콘 zip파일 FTP 전송 
				File uploadIcon = new File(iconTempPath, "icon.zip"); // File 객체
				fis = new FileInputStream(uploadIcon);
				isSuccess = client.storeFile("icon.zip", fis);
				fis.close();
				
				if(isSuccess) {
					isSuccess = false;
				}else {
					return 1;
				}
				
				//5> 압축하기 위해 만든 파일 정리(icon dir, zip, image)
				FileUtil.delete(iconMake);
				FileUtil.delete(uploadIcon);
				FileUtil.delete( new File(iconTempPath, appInfo.getIconSaveFile()) );
			}
			//2-2. 앱용 아이콘 사이즈별 만들기, 압축하기, FTP 전송 - end

			if(popupGb.equals("newVer")) {
				//2-3. newVersion 앱 정보 등록
				AppVO newVersionAppInfo = new AppVO();
				myUserDetails activeUser = null;
				if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
					throw new AuthenticationException();
				}else {
					activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				}
				
				newVersionAppInfo.setRegGb(appInfo.getRegGb());
				newVersionAppInfo.setAppName(appInfo.getAppName());
				newVersionAppInfo.setAppContentsAmt(appInfo.getAppContentsAmt());
				newVersionAppInfo.setAppContentsGb(appInfo.getAppContentsGb());
				newVersionAppInfo.setFileName(appInfo.getFileName());
				newVersionAppInfo.setOstype(appInfo.getOstype());				
				newVersionAppInfo.setVerNum(relatedAppVerNum);				
				newVersionAppInfo.setStoreBundleId(appInfo.getStoreBundleId());
				newVersionAppInfo.setProvisionGb(appInfo.getProvisionGb());
				if(appInfo.getIconOrgFile() != null && !appInfo.getIconOrgFile().equals("")) {
					newVersionAppInfo.setIconOrgFile(appInfo.getIconOrgFile());
				}
				if(appInfo.getIconSaveFile() != null && !appInfo.getIconSaveFile().equals("")) {
					newVersionAppInfo.setIconSaveFile(appInfo.getIconSaveFile());
				}
				newVersionAppInfo.setTemplateName(appInfo.getTemplateName());
				newVersionAppInfo.setTemplateSeq(appInfo.getTemplateSeq());
				newVersionAppInfo.setUseGb(appInfo.getUseGb());
				newVersionAppInfo.setUseAvailDt(new Date());
				newVersionAppInfo.setCompletGb("2");
				newVersionAppInfo.setLimitGb("2");				
				newVersionAppInfo.setRegUserSeq(activeUser.getMemberVO().getUserSeq());
				newVersionAppInfo.setRegUserId(activeUser.getMemberVO().getUserId());
				newVersionAppInfo.setRegUserGb(activeUser.getMemberVO().getUserGb());				
				newVersionAppInfo.setRegDt(new Date());				
				newVersionAppInfo.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
				newVersionAppInfo.setChgUserId(activeUser.getMemberVO().getUserId());
				newVersionAppInfo.setChgUserGb(activeUser.getMemberVO().getUserGb());				
				newVersionAppInfo.setChgDt(new Date());
				newVersionAppInfo.setApp_resultCode("2");				
				newVersionAppInfo.setInstallGb("1");
				newVersionAppInfo.setVersionCode(appInfo.getVersionCode());
				newVersionAppInfo.setUseUserGb("1");
				newVersionAppInfo.setLoginGb("2");
				
				Date nowTime = new Date();
				String changeText = "Update", date="", hours="", minutes="";
				if(String.valueOf(nowTime.getDate()).length() == 1) {
					date="0"+String.valueOf(nowTime.getDate());
				}else {
					date = String.valueOf(nowTime.getDate());
				}
				
				if(String.valueOf(nowTime.getHours()).length() == 1) {
					hours="0"+String.valueOf(nowTime.getHours());
				}else {
					hours = String.valueOf(nowTime.getHours());
				}
				
				if(String.valueOf(nowTime.getMinutes()).length() == 1) {
					minutes="0"+String.valueOf(nowTime.getMinutes());
				}else {
					minutes = String.valueOf(nowTime.getMinutes());
				}
				
				if(appInfo.getChgText() == null || appInfo.getChgText().equals("")) {
					changeText = changeText+":"+date+":"+hours+":"+minutes;
				}else {
					changeText = appInfo.getChgText()+"\n"+changeText+":"+date+":"+hours+":"+minutes;
				}
				
				newVersionAppInfo.setChgText(changeText);
				
				newVersionAppSeq = appService.insertNewVersionAppInfo(newVersionAppInfo);
				
				List<BundleVO> beforeBundle = bundleService.selectBeforeBundleInfoByAppSeqForNewVersion(Integer.parseInt(relatedAppSeq));
				BundleVO newVersionAppBundleInfo = new BundleVO();
				for(int i=0;i<beforeBundle.size();i++) {
					newVersionAppBundleInfo.setAppSeq(newVersionAppSeq);
					newVersionAppBundleInfo.setProvSeq(beforeBundle.get(i).getProvSeq());
					newVersionAppBundleInfo.setBundleName(beforeBundle.get(i).getBundleName());
					newVersionAppBundleInfo.setOsType(beforeBundle.get(i).getOsType());
					newVersionAppBundleInfo.setProvTestGb(beforeBundle.get(i).getProvTestGb());
					
					bundleService.insertNewVersionAppBundleInfo(newVersionAppBundleInfo);
				}
				//2-3. newVersion 앱 정보 등록 - end
			}
			//2. 앱 만들기 - end
			
			//3. 관련 앱 추가 로직
			ContentVO makeApp = contentsService.selectRelatedAppInfoNew(Integer.parseInt(contentSeq));
			String makeAppAfter = makeString(makeApp.getAppName(), relatedApp);			
			param.setValue("relatedAppName", makeAppAfter);
			
			String makeAppTypeAfter = makeString(makeApp.getAppType(), relatedAppType);			
			param.setValue("relatedAppType", makeAppTypeAfter);
			
			contentsService.updateRelatedAppinfo(param);
			//3. 관련 앱 추가 로직 - end
			
    		//4. contentsapp_sub table data insert
			String makeAppSeq = contentsService.selectRelatedAppSeqInfo(Integer.parseInt(contentSeq)), makeAppSeqAfter = "";
			
			if(popupGb.equals("update")) {	//갱신일 때, 현재 앱 정보로 _sub 테이블 update
				makeAppSeqAfter = makeString(makeAppSeq, relatedAppSeq);
			}else if(popupGb.equals("newVer")) {
				makeAppSeqAfter = makeString(makeAppSeq, String.valueOf(newVersionAppSeq));
			}
			param.setValue("relatedAppSeq", makeAppSeqAfter);
			
			contentsService.updateRelatedAppSubInfo(param);
    		//4. contentsapp_sub table data insert - end

			//5(2-4). pbapp파일 만들기
			int makePbAppSeq = 0;
			String makeTempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.temp", null, localeResolver.resolveLocale(request));
			if(popupGb.equals("update")) {	//갱신일 때, 현재 앱 정보로 _sub 테이블 update
				makePbAppSeq = Integer.parseInt(relatedAppSeq);
			}else if(popupGb.equals("newVer")) {
				makePbAppSeq = newVersionAppSeq;
			}
			
    		boolean isMakeSuccess = makeJobTicket(makePbAppSeq, ip, port, id, pwd, makeTempPath, provisionSeq, popupGb);
    		
    		if(!isMakeSuccess) {
    			return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return 1;
		}finally {
			if(fis != null) {
				try {
					fis.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if(client != null && client.isConnected()) {
				try {
					client.disconnect();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}//2. 관련 앱 추가 로직 - end
		
		return 0;
	}

	//새 콘텐츠로 -> 앱 갱신 / 새버전 추가
	@RequestMapping(value = "app/makeLocal.html", method = RequestMethod.POST)
	public @ResponseBody int appMakeLocal(HttpSession session, MultipartHttpServletRequest request, ContentVO contentVO, String relatedAppName, String relatedAppType, String relatedAppSeq, String relatedAppVerNum, String provisionSeq, String popupGb) {
		//1. 해당 앱 관련 > 기존에 관련앱으로 연결되어 있는 contents 검색해서 해당 내용 삭제 처리
		//2. contents에 콘텐츠 파일 생성
		//3. tb_contents 새로 등록한 contents 정보 등록(관련앱도 같이 포함)
		
		//4. json 만들어서 > pbapp 만들기<갱신>
		//4_1. 갱신(update) -> pbapp만 만들기(DB 처리 후, 4-4)
		//4_2. 새버전(newVer) -> 1) contents.zip파일 FTP, 2) icon.zip파일 FTP, 3) tb_app에 새버전 insert, 4) pbapp 만들기(DB 처리 후, 4-4)
		
		//5. tb_contentsapp_sub에 추가될 관련앱 내용 처리
		//6(4-4). pbapp 생성 		
		FTPClient client = null;
		FileInputStream fis = null;		
		String[] url = messageSource.getMessage("FTP.Info.IP", null, localeResolver.resolveLocale(request)).split(":");
		String ip = url[0];
		String port = url[1];
		String id = messageSource.getMessage("FTP.Info.ID", null, localeResolver.resolveLocale(request));
		String pwd = messageSource.getMessage("FTP.Info.PW", null, localeResolver.resolveLocale(request));

		//1. 해당 앱 관련 > 기존에 관련앱으로 연결되어 있는 contents 검색해서 해당 내용 삭제 처리
		Entity param = new Entity();
		String relatedApp = relatedAppName+"("+relatedAppVerNum+")";
		int newVersionAppSeq = 0;
		
		if(popupGb.equals("update")) {	//갱신(update)일 때만 해당 관련앱정보 -> 기존 콘텐츠/_sub에서 삭제
			ContentVO contentInfo = new ContentVO();
			contentInfo = contentsService.selectRelatedAppinfo(relatedAppSeq);
			
			if(contentInfo != null) {
				param.setValue("contentSeq", contentInfo.getContentsSeq());
				
				String deleteApp = contentInfo.getAppName();
				String deleteAppType = contentInfo.getAppType();
				Entity deleteAppAfter = deleteAppInfo(deleteApp, deleteAppType, relatedApp);
				deleteAppAfter.setValue("contentSeq", contentInfo.getContentsSeq());

				contentsService.updateRelatedAppinfo(deleteAppAfter);
				//1-1. 관련앱 삭제 로직(tb_contents) - end
				
				//1-2. 관련앱 삭제 로직(_sub)
				String deleteAppSeq = contentsService.selectRelatedAppSeqInfo(contentInfo.getContentsSeq());
				String deleteAppSeqAfter = deleteString(deleteAppSeq, relatedAppSeq);
				param.setValue("relatedAppSeq", deleteAppSeqAfter);
				
				contentsService.updateRelatedAppSubInfo(param);
				//1-2. 관련앱 삭제 로직(_sub) - end
				
			}
		}//1. 관련앱 삭제 로직 - end

		try {
			HashMap<String, String> map = new HashMap<String, String>();
			ContentsappSubVO contentsappSubVO = new ContentsappSubVO();

			Iterator<String> files = request.getFileNames();
	        while(files.hasNext()){
	        	//2. contents 파일 지정 경로에 생성
	            String uploadFileName = files.next();
	                         
	            MultipartFile file = request.getFile(uploadFileName);
	            
	            try {
	            	map = contentsController.uploadContents(map, file, request);
	            	contentsController.unzip(map, request);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			return 1;
	    		}//2. contents 파일 지정 경로에 생성 - end

	            //3. tb_contents에 DB 정보 저장
	    		contentVO.setUploadOrgFile(map.get("orgFileName")+"."+map.get("fileExt"));
	    		contentVO.setUploadSaveFile(map.get("saveFileName")+"."+map.get("fileExt"));
	    		contentVO.setContentsSize(map.get("fileSize"));
	    		contentVO.setAppName(relatedApp);
	    		contentVO.setAppType(relatedAppType);
	    		int contentSeq = contentsService.insertContentInfo( contentVO );
	        	//3. tb_contents에 DB 정보 저장 - end

				//4. 앱 만들기
				AppVO appInfo = appService.selectBeforAppInfoBySeqForNewVersion(Integer.parseInt(relatedAppSeq));				
				//4-1. 앱용 콘텐츠 복사
				String appContentsFileName = contentsService.selectContentsFileName(contentSeq);
				String contentsPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request))+"/"+appContentsFileName.substring(0, appContentsFileName.lastIndexOf("."));
				
				File uploadFile = new File(contentsPath, appContentsFileName); // File 객체
				
				client = new FTPClient(); // FTP Client 객체				
				client.setControlEncoding("UTF-8");
				client.connect(ip, Integer.parseInt(port));
				client.login(id, pwd);
				client.enterLocalPassiveMode();
				client.setFileType(FTP.BINARY_FILE_TYPE);
				
				client.makeDirectory(appInfo.getStoreBundleId());
				client.changeWorkingDirectory("/"+appInfo.getStoreBundleId());
				
				fis = new FileInputStream(uploadFile);
				boolean isSuccess = client.storeFile("contents.zip", fis);
				fis.close();
				
				if(isSuccess) {
					isSuccess = false;
				}else {
					return 1;
				}
				//4-1. 앱용 콘텐츠 복사 - end
				
				//4-2. 앱용 아이콘 사이즈별 만들기, 압축하기, FTP 전송
				if( appInfo.getIconSaveFile() != null && !appInfo.getIconSaveFile().equals("") ) {//icon이 있을 때만 icon 처리
					//1> 아이콘 만들 준비
					String iconPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
					String iconTempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
					String iconMakePath = iconTempPath+"icon/", iconMakeName = "icon.png";

					FileUtil.copy(new File(iconPath, appInfo.getIconSaveFile()), new File(iconTempPath, appInfo.getIconSaveFile()));
					File iconMake = new File(iconMakePath);
					iconMake.mkdirs();					

					//2> size별 아이콘 만들기
					int toBeWidth[];
					if(relatedAppType.equals("1") || relatedAppType.equals("2") || relatedAppType.equals("3")) {
						toBeWidth = new int[]{57, 114, 120, 72, 144, 76, 152};
//temp					FileUtil.copy(new File(iconTempPath, appInfo.getIconSaveFile()), new File(iconMakePath, iconMakeName));
					}else{
						toBeWidth = new int[]{96};
						FileUtil.copy(new File(iconTempPath, appInfo.getIconSaveFile()), new File(iconMakePath, iconMakeName));
					}

					String fileExt = appInfo.getIconSaveFile().substring(appInfo.getIconSaveFile().lastIndexOf(".") + 1, appInfo.getIconSaveFile().length());					
					BufferedImage originalImage = ImageIO.read(new File(iconTempPath, appInfo.getIconSaveFile()));
					
					for(int i=0;i<toBeWidth.length;i++) {
						String newFileName = "icon_" + toBeWidth[i] + "." +fileExt;
						BufferedImage resizeImage = ImageTask.resizeImage(originalImage, originalImage.getType(), toBeWidth[i], toBeWidth[i]);
						ImageIO.write(resizeImage, fileExt, new File(iconMakePath + newFileName));
					}
					
					//3> 아이콘 zip파일 만들기
					FileZipUtil.createZipFile(iconMakePath, iconTempPath, "icon.zip");
					
					//4> 아이콘 zip파일 FTP 전송 
					File uploadIcon = new File(iconTempPath, "icon.zip"); // File 객체
					fis = new FileInputStream(uploadIcon);
					isSuccess = client.storeFile("icon.zip", fis);
					fis.close();
					
					if(isSuccess) {
						isSuccess = false;
						
						//5> 압축하기 위해 만든 파일 정리(icon dir, zip, image)
						FileUtil.delete(iconMake);
						FileUtil.delete(uploadIcon);
						FileUtil.delete( new File(iconTempPath, appInfo.getIconSaveFile()) );
					}else {
						return 1;
					}
					
				}
				//4-2. 앱용 아이콘 사이즈별 만들기, 압축하기, FTP 전송 - end

				if(popupGb.equals("newVer")) {
					//4-3. newVersion 앱 정보 등록
					AppVO newVersionAppInfo = new AppVO();
					myUserDetails activeUser = null;
					if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
						throw new AuthenticationException();
					}else {
						activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					}
					
					newVersionAppInfo.setRegGb(appInfo.getRegGb());
					newVersionAppInfo.setAppName(appInfo.getAppName());
					newVersionAppInfo.setAppContentsAmt(appInfo.getAppContentsAmt());
					newVersionAppInfo.setAppContentsGb(appInfo.getAppContentsGb());
					newVersionAppInfo.setFileName(appInfo.getFileName());
					newVersionAppInfo.setOstype(appInfo.getOstype());				
					newVersionAppInfo.setVerNum(relatedAppVerNum);				
					newVersionAppInfo.setStoreBundleId(appInfo.getStoreBundleId());
					newVersionAppInfo.setProvisionGb(appInfo.getProvisionGb());
					if(appInfo.getIconOrgFile() != null && !appInfo.getIconOrgFile().equals("")) {
						newVersionAppInfo.setIconOrgFile(appInfo.getIconOrgFile());
					}
					if(appInfo.getIconSaveFile() != null && !appInfo.getIconSaveFile().equals("")) {
						newVersionAppInfo.setIconSaveFile(appInfo.getIconSaveFile());
					}
					newVersionAppInfo.setTemplateName(appInfo.getTemplateName());
					newVersionAppInfo.setTemplateSeq(appInfo.getTemplateSeq());
					newVersionAppInfo.setUseGb(appInfo.getUseGb());
					newVersionAppInfo.setUseAvailDt(new Date());
					newVersionAppInfo.setCompletGb("2");
					newVersionAppInfo.setLimitGb("2");				
					newVersionAppInfo.setRegUserSeq(activeUser.getMemberVO().getUserSeq());
					newVersionAppInfo.setRegUserId(activeUser.getMemberVO().getUserId());
					newVersionAppInfo.setRegUserGb(activeUser.getMemberVO().getUserGb());				
					newVersionAppInfo.setRegDt(new Date());				
					newVersionAppInfo.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
					newVersionAppInfo.setChgUserId(activeUser.getMemberVO().getUserId());
					newVersionAppInfo.setChgUserGb(activeUser.getMemberVO().getUserGb());				
					newVersionAppInfo.setChgDt(new Date());
					newVersionAppInfo.setApp_resultCode("2");				
					newVersionAppInfo.setInstallGb("1");
					newVersionAppInfo.setVersionCode(appInfo.getVersionCode());
					newVersionAppInfo.setUseUserGb("1");
					newVersionAppInfo.setLoginGb("2");
					
					Date nowTime = new Date();
					String changeText = "Update", date="", hours="", minutes="";
					if(String.valueOf(nowTime.getDate()).length() == 1) {
						date="0"+String.valueOf(nowTime.getDate());
					}else {
						date = String.valueOf(nowTime.getDate());
					}
					
					if(String.valueOf(nowTime.getHours()).length() == 1) {
						hours="0"+String.valueOf(nowTime.getHours());
					}else {
						hours = String.valueOf(nowTime.getHours());
					}
					
					if(String.valueOf(nowTime.getMinutes()).length() == 1) {
						minutes="0"+String.valueOf(nowTime.getMinutes());
					}else {
						minutes = String.valueOf(nowTime.getMinutes());
					}
					
					if(appInfo.getChgText() == null || appInfo.getChgText().equals("")) {
						changeText = changeText+":"+date+":"+hours+":"+minutes;
					}else {
						changeText = appInfo.getChgText()+"\n"+changeText+":"+date+":"+hours+":"+minutes;
					}
					
					newVersionAppInfo.setChgText(changeText);
					
					newVersionAppSeq = appService.insertNewVersionAppInfo(newVersionAppInfo);
					
					List<BundleVO> beforeBundle = bundleService.selectBeforeBundleInfoByAppSeqForNewVersion(Integer.parseInt(relatedAppSeq));
					BundleVO newVersionAppBundleInfo = new BundleVO();
					for(int i=0;i<beforeBundle.size();i++) {
						newVersionAppBundleInfo.setAppSeq(newVersionAppSeq);
						newVersionAppBundleInfo.setProvSeq(beforeBundle.get(i).getProvSeq());
						newVersionAppBundleInfo.setBundleName(beforeBundle.get(i).getBundleName());
						newVersionAppBundleInfo.setOsType(beforeBundle.get(i).getOsType());
						newVersionAppBundleInfo.setProvTestGb(beforeBundle.get(i).getProvTestGb());
						
						bundleService.insertNewVersionAppBundleInfo(newVersionAppBundleInfo);
					}
					//4-3. newVersion 앱 정보 등록
				}
				//4. 앱 만들기 - end
	    		
	    		//5. contentsapp_sub table data insert
	    		if(popupGb.equals("update")) {	//갱신일 때, 현재 앱 정보로 _sub 테이블 update
		    		if(relatedAppSeq != null && !"".equals(relatedAppSeq)) {
		        		contentsappSubVO.setAppSeq(relatedAppSeq);
		    		}
	    		}else {
	    			contentsappSubVO.setAppSeq(String.valueOf(newVersionAppSeq));
	    		}
	    		contentsappSubVO.setContentsSeq(contentSeq);	    		
	    		contentsService.insertContentsappSubInfo(contentsappSubVO);
	    		//5. contentsapp_sub table data insert - end

				//6(4-4). pbapp파일 만들기
				int makePbAppSeq = 0;
				String makeTempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.temp", null, localeResolver.resolveLocale(request));
				if(popupGb.equals("update")) {	//갱신일 때, 현재 앱 정보로 _sub 테이블 update
					makePbAppSeq = Integer.parseInt(relatedAppSeq);
				}else if(popupGb.equals("newVer")) {
					makePbAppSeq = newVersionAppSeq;
				}
				
	    		boolean isMakeSuccess = makeJobTicket(makePbAppSeq, ip, port, id, pwd, makeTempPath, provisionSeq, popupGb);
	    		
	    		if(!isMakeSuccess) {
	    			return 1;
				}	    		
	        }
		}catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
	
	public String deleteString(String deleteApp, String relatedAppName) {
		String[] deleteAppCheck = deleteApp.split(",");
		
		for(int i=0;i<deleteAppCheck.length;i++) {
			if(deleteAppCheck[i].equals(relatedAppName)) {
				deleteAppCheck[i] = "";
			}
		}
		
		deleteApp = "";
		int cnt = 1;
		
		for(int j=0;j<deleteAppCheck.length;j++) {
			if( !(deleteAppCheck[j].equals("")) ) {
				if(cnt == 1) {
					deleteApp = deleteAppCheck[j];
					++cnt;
				}else {
					deleteApp = deleteApp +","+ deleteAppCheck[j];
				}
			}
		}
		
		return deleteApp;
	}
	
	public String makeString(String makeApp, String relatedAppName) {
		if(makeApp == null || makeApp.equals("")) {
			makeApp = relatedAppName;
		}else {
			makeApp = makeApp +","+ relatedAppName;
		}
		
		return makeApp;
	}
	
	public Entity deleteAppInfo(String deleteApp, String deleteAppType, String relatedAppName) {
		String[] deleteAppCheck = deleteApp.split(",");
		String[] deleteAppTypeCheck = deleteAppType.split(",");
		
		for(int i=0;i<deleteAppCheck.length;i++) {
			if(deleteAppCheck[i].equals(relatedAppName)) {
				deleteAppCheck[i] = "";
				deleteAppTypeCheck[i] = "";
			}
		}
		
		deleteApp = "";
		deleteAppType = "";
		int cnt_appName = 1, cnt_appType = 1;
		
		//new appName
		for(int j=0;j<deleteAppCheck.length;j++) {
			if( !(deleteAppCheck[j].equals("")) ) {
				if(cnt_appName == 1) {
					deleteApp = deleteAppCheck[j];
					++cnt_appName;
				}else {
					deleteApp = deleteApp +","+ deleteAppCheck[j];
				}
			}
		}
		
		//new appType
		for(int j=0;j<deleteAppTypeCheck.length;j++) {
			if( !(deleteAppTypeCheck[j].equals("")) ) {
				if(cnt_appType == 1) {
					deleteAppType = deleteAppTypeCheck[j];
					++cnt_appType;
				}else {
					deleteAppType = deleteAppType +","+ deleteAppTypeCheck[j];
				}
			}
		}
		
		Entity newAppInfo = new Entity();
		newAppInfo.setValue("relatedAppName", deleteApp);
		newAppInfo.setValue("relatedAppType", deleteAppType);

		return newAppInfo;
	}
	
	public boolean makeJobTicket(int makePbAppSeq, String ip, String port, String id, String pwd, String makeTempPath, String provisionSeq, String popupGb) {
		FTPClient client = null;
		FileInputStream fis = null;
		BufferedWriter writer = null;
		
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		//1. make jobTicket to String
		AppVO appVO = appService.selectAppByAppSeqForMakeJobTicket(makePbAppSeq);
		String companyName = memberService.selectCompanyName(activeUser.getMemberVO().getCompanySeq());
		
		String writeString = "{"
			+"\n\"verNum\" : \""+appVO.getVerNum()+"\","
			+"\n\"fileName\" : \""+appVO.getFileName()+"\","
			+"\n\"storeBundleId\" : \""+appVO.getStoreBundleId()+"\","
			+"\n\"devices\" : \""+appVO.getOstype()+"\","
			+"\n\"templateSeq\" : \""+appVO.getTemplateSeq()+"\","
			+"\n\"appContentsAmt\" : \""+appVO.getAppContentsAmt()+"\","
			+"\n\"regGb\" : \""+appVO.getRegGb()+"\","
			+"\n\"userId\" : \""+activeUser.getMemberVO().getUserId()+"\","
			+"\n\"appName\" : \""+appVO.getAppName()+"\","
			+"\n\"Login\" : \""+companyName+"\","
			+"\n\"appSeq\" : \""+appVO.getAppSeq()+"\","
			+"\n\"content\" : \""+"Include"+"\",";

			if(appVO.getDescriptionText() != null && !appVO.getDescriptionText().equals("")) {
				writeString += "\n\"descriptionText\" : \""+appVO.getDescriptionText()+"\",";
			}else {
				writeString += "\n\"descriptionText\" : \"\",";
			}
				
			if(appVO.getChgText() != null && !appVO.getChgText().equals("")) {
				String chg_text = appVO.getChgText().replaceAll("[\\r\\n]+", "\\\\n");
				writeString += "\n\"chgText\" : \""+chg_text+"\",";
			}else {
				writeString += "\n\"chgText\" : \"\",";
			}
			
			if(appVO.getIconSaveFile() != null && !appVO.getIconSaveFile().equals("")) {
				writeString += "\n\"iconSaveFile\" : \""+appVO.getIconSaveFile()+"\",";
			}else {
				writeString += "\n\"iconSaveFile\" : \"\",";
			}
			
			if(appVO.getOstype().equals("4")) {
				writeString += "\n\"versionCode\" : \""+appVO.getVersionCode()+"\"";
			}else if(appVO.getOstype().equals("1") || appVO.getOstype().equals("2") || appVO.getOstype().equals("3")) {
				BundleVO bundleParam = new BundleVO();
				bundleParam.setAppSeq(makePbAppSeq);
				bundleParam.setProvSeq(Integer.parseInt(provisionSeq));
				List<HashMap> bundleVO = bundleService.selectBundleInfoByAppSeqForMakePbapp(bundleParam);
				
				//universal data -> ostype 2(iphone) : 1 / ostype 3(ipad) : 2 / ostype 1(universe) : 1,2
				if( appVO.getOstype().equals("2") ) {
					writeString += "\n\"universal\" : \"1\",";
				}else if( appVO.getOstype().equals("3") ) {
					writeString += "\n\"universal\" : \"2\",";
				}else if( appVO.getOstype().equals("1") ) {
					writeString += "\n\"universal\" : \"1,2\",";
				}
				// configuration data -> provTestGb 1 : AdHoc / provTestGb 2 : Release
//				writeString += "\n\"provTestGb\" : \""+bundleVO.get(0).get("provTestGb")+"\",";
				if(bundleVO.get(0).get("provTestGb").equals("1")) {
					writeString += "\n\"configuration\" : \"AdHoc\",";
				}else if(bundleVO.get(0).get("provTestGb").equals("2")) {
					writeString += "\n\"configuration\" : \"Release\",";
				}
				writeString += "\n\"provSeq1\" : \""+bundleVO.get(0).get("provSeq")+"\",";
				writeString += "\n\"distrProfileName\" : \""+bundleVO.get(0).get("distrProfileName")+"\",";
				writeString += "\n\"provisionGb\" : \""+bundleVO.get(0).get("provTestGb")+"\"";
			}
				
		writeString +="\n}";
		
		try {
			//2. make jobTicket to file by String in localServer tempPath
			String pbappName = appVO.getStoreBundleId()+".pbapp";
			
			File file = new File(makeTempPath+pbappName);
			file.createNewFile();
			
			writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(file.getPath()), "UTF8") );
			writer.write(writeString);
			writer.close();
			
			//3. ftpUpload jobTicket at localServer tempPath
			File uploadJobTicket = new File(makeTempPath, pbappName); // File 객체
			
			client = new FTPClient(); // FTP Client 객체
			client.setControlEncoding("UTF-8");
			client.connect(ip, Integer.parseInt(port));
			client.login(id, pwd);
			client.enterLocalPassiveMode();
			client.setFileType(FTP.BINARY_FILE_TYPE);			
			client.changeWorkingDirectory("/Q");
			
			fis = new FileInputStream(uploadJobTicket);
			boolean isSuccess = client.storeFile(pbappName, fis);
			client.disconnect();
			fis.close();
			
			if(isSuccess) {
				//temp에 pbapp파일 지우기
				FileUtil.delete( uploadJobTicket );
			}else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			if(fis != null) {
				try {
					fis.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if(writer != null) {
				try {
					writer.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if(client != null && client.isConnected()) {
				try {
					client.disconnect();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}