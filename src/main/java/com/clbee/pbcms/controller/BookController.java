package com.clbee.pbcms.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clbee.pbcms.service.AppService;
import com.clbee.pbcms.service.BundleService;
import com.clbee.pbcms.service.CaptureService;
import com.clbee.pbcms.service.ChangelistService;
import com.clbee.pbcms.service.ContentsService;
import com.clbee.pbcms.service.InAppCategoryService;
import com.clbee.pbcms.service.InAppService;
import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.service.ProvisionService;
import com.clbee.pbcms.service.TemplateService;
import com.clbee.pbcms.util.AuthenticationException;
import com.clbee.pbcms.util.DateUtil;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.FileUtil;
import com.clbee.pbcms.util.FileZipUtil;
import com.clbee.pbcms.util.Formatter;
import com.clbee.pbcms.util.ImageTask;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.AppHistoryVO;
import com.clbee.pbcms.vo.AppList;
import com.clbee.pbcms.vo.AppSubVO;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.BundleVO;
import com.clbee.pbcms.vo.CaptureVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.InAppList;
import com.clbee.pbcms.vo.InappMetaVO;
import com.clbee.pbcms.vo.InappSubVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.TemplateVO;

@Controller
public class BookController {
	
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
	ContentsService contentsService;
	
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
	
	@RequestMapping(value = "book/list.html", method = RequestMethod.GET)
	public String bookListGET(HttpSession session, HttpServletRequest request, ModelMap modelMap, AppList appList) throws UnsupportedEncodingException {
		System.out.println("currentUserName" + SecurityContextHolder.getContext().getAuthentication().getName());
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		GrantedAuthority element = authorities.iterator().next();
		String authority = element.getAuthority();

		System.out.println("appList.getSearchValue() = " + appList.getSearchValue());
		if(appList.getSearchValue() != null)
		appList.setSearchValue(URLDecoder.decode(appList.getSearchValue(), "UTF-8"));
		if("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
			return "redirect:/down/list.html";			
		}else{
			if(appList.getCurrentPage()==null)appList.setCurrentPage(1);
			appList = appService.selectList(activeUser.getMemberVO(), appList);
			modelMap.addAttribute("appList", appList);
	
			return "08_book/book_list";
		}
	}

	@RequestMapping(value = "book/modify.html", method = RequestMethod.GET)
	public String app_modify(HttpSession session, HttpServletRequest request, ModelMap modelMap, InappVO inappVO, InAppList inAppList, CaptureVO vo, AppVO appVO, AppList appList) {
		List captureList = null;
		List bundleList = null;
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
			System.out.println("@@@@@@@@@@@@@@"+appVO.getAppSeq());
			appVO = appService.selectForUpdate(appVO, activeUser.getMemberVO());
			if(appVO==null){
				//throw new Exception("�옒紐삳맂 �젒洹쇱엯�땲�떎.");
				String returnUrl = "/inc/dummy";
				modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
				modelMap.addAttribute("type", "-1");
				return returnUrl;
			}

			bundleList = bundleService.listByAppSeq(appVO.getAppSeq());
			vo.setCaptureGb("1");
			vo.setBoardSeq(appVO.getAppSeq());
			captureList = captureService.selectListByBoardSeqWithGb(vo);
			//System.out.println(appVO.toString());

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
		}catch(Exception e){
			e.printStackTrace();
		}

		modelMap.addAttribute("inappCnt", inappCnt);
		modelMap.addAttribute("useSelVal", useVal);
		modelMap.addAttribute("inappCnt", inappCnt);
		modelMap.addAttribute("appVO", appVO);
		modelMap.addAttribute("appList", appList);
		modelMap.addAttribute("bundleList", bundleList);
		modelMap.addAttribute("captureList", captureList);
		modelMap.addAttribute("templateVO", templateVO);
    	return "08_book/book_modify";
    }

	@RequestMapping(value = "book/modify.html", method = RequestMethod.POST)
	public String bookoModifyPOST(HttpSession session, String[] useS, HttpServletRequest request, ModelMap modelMap, CaptureVO vo, AppVO appVO, AppList appList) {

		//�궗�슜�옄 �젙蹂� 
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

		//couponNum�씠 Null�씪 寃쎌슦 ""�쑝濡� 諛붽퓞
		if(appVO.getCouponNum() == null)appVO.setCouponNum("");
		try{
			AppVO appVO2 = appService.selectForUpdate(appVO, activeUser.getMemberVO());
			if(appVO2==null){
				//throw new Exception("�옒紐삳맂 �젒洹쇱엯�땲�떎.");
				String returnUrl = "/inc/dummy";
				modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
				modelMap.addAttribute("type", "-1");
				return returnUrl;				
			}
			//�궗�슜�궇吏� 誘몄궗�슜�궇吏�
			if(!appVO.getUseGb().equals(appVO2.getUseGb())){
				if("1".equals(appVO.getUseGb())){
					appVO.setUseAvailDt(new Date());
				}else{
					appVO.setUseDisableDt(new Date());
				}
			}

			//�젣�븳 �궇吏�
			if(appVO.getLimitGb()!=null){
				if(!appVO.getLimitGb().equals(appVO2.getLimitGb())){
					if("1".equals(appVO.getLimitGb())){
						appVO.setLimitDt(new Date());
					}
				}
			}

			// if("UPDATEOTHERYES") �븞�뿉 �뱾�뼱媛��빞�븷 �씠 2以꾩쓽 肄붾뱶媛�
			// 諛붽묑�쑝濡� �굹���꽌 癒쇱� 李얜뒗 �씠�쑀�뒗 留⑥쿂�쓬 �뀒�뒪�듃濡� �벑濡앺븳�썑, �셿猷� 踰꾪듉�쓣 �닃���쓣�븣,
			// 湲곗〈�뿉 �빋�씠 �벑濡앸맂寃� �엳�떎 �뾾�떎源뚯� 泥댄겕�빐�꽌 泥댄겕�빐�꽌 javascript濡�, UPDATEOTHERYES瑜� �븯�뒗寃껊��떊�뿉
			// �꽌踰꾨떒�뿉�꽌 size != 0�쑝濡� ���떊 泥댄궎�븳寃�
			param.setValue("store_bundle_id",storeBundleId1+storeBundleId2);
			param.setValue("OSTYPE", appVO.getOstype());
			List<LinkedHashMap<Object, Object>> appVOForBundleIdList = appService.getRowIsCompletedByBundleId(param);

			// �씠 �뾽�뜲�씠�듃瑜� �몢踰덉㎏ �뾽�뜲�씠�듃蹂대떎 癒쇱� �몦�씠�쑀�뒗
			// Sorting�븷�븣 媛��옣 留덉�留됱뿉 �뾽�뜲�씠�듃�맂 �닚�꽌��濡� Sorting�븯湲� �븣臾�
			// 洹몃━怨� �씠 �뾽�뜲�씠�듃湲곕뒫��, �몢踰덉㎏ �뾽�뜲�씠�듃媛� �셿�꽦 = '�븘�땲�삤'�씪�븣 '�삁'濡� �뻽�쓣寃쎌슦
			// bundle id媛� �삊媛숆퀬 踰꾩쟾�씠 �궙�� �빐�떦 �빋�쓣 �궗�슜以묐떒�븯湲� �쐞�븿�엫 �뵲�씪�꽌
			// Sorting�맆�븣 �씠�뾽�뜲�씠�듃�뒗 �몢踰덉㎏ �뾽�뜲�씠�듃蹂대떎 諛묒뿉 �궡�젮媛��빞�릺湲� �븣臾몄뿉 癒쇱� �뾽�뜲�씠�듃瑜� �븿
			if("UPDATEOTHERYES".equals(isCompleteNoToYes) && appVOForBundleIdList.size() != 0){
				System.out.println("@@@@@@@@@@@ UPDATEOTHERYES!!!!");
				AppHistoryVO appHistoryVOForHashMap = new AppHistoryVO(appVOForBundleIdList.get(0));
				appService.insertAppHistoryInfo(appHistoryVOForHashMap);
				appService.deleteAppInfo(((Long) appVOForBundleIdList.get(0).get("appSeq")).intValue());
			}
			appVO.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
			appVO.setChgUserId(activeUser.getMemberVO().getUserId());
			appVO.setChgUserGb(activeUser.getMemberVO().getUserGb());

			//�씠寃껋� 留⑥쿂�쓬�뿉 �떆�룄�븳 �뾽�뜲�씠�듃 �엯�땲�떎.
			if("Adhoc".equals(appVO.getProvisionGb()));
				appVO.setProvisionGb("1");
			if("AppStore".equals(appVO.getProvisionGb()));
				appVO.setProvisionGb("2");
			appService.updateAppInfo(appVO, appVO.getAppSeq());

			//�씠誘몄� 泥섎윭
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
			
			//�븘�씠肄섏씠誘몄�
			String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....荑쇰━
			}
			//罹≪퀜�씠誘몄� 泥섎━
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
						//....荑쇰━ tb_capture
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
			}
			//湲곗〈�뙆�씪�궘�젣
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
					if("capture".equals(deleteFileType)){//耳묒퀜�뙆�씪 �궘�젣
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.capture.file", null, localeResolver.resolveLocale(request));
						if(FileUtil.delete(new File(deleteFilePath+deleteSaveFileName))){
							CaptureVO captureVO = new CaptureVO();
							captureVO.setCaptureSeq(deleteFileSeq);
							captureService.delete(captureVO);
							captureVO = null;
						}
					}else if("icon".equals(deleteFileType)){//�븘�씠肄섑뙆�씪�궘�젣
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
						FileUtil.delete(new File(deleteFilePath+deleteSaveFileName));
					}
				}
			}
			//�봽濡쒕퉬�졏 �뿰寃고뀒�씠釉� �엯�젰 諛� 媛깆떊
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
			// �듅�젙�쉶�썝 由ъ뒪�듃 �젙蹂�

			/*if(useS.length >0) {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				for(int i=0; i<useS.length; i++){
					System.out.println("useS = " + useS[i]);
				}
			}*/

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

			String url = "redirect:/book/list.html";
			String parameters = "?currentPage=1";
				 /*"&appSeq="+appVO.getAppSeq()+"&searchType="+appList.getSearchType()+"&searchValue="+appList.getSearchValue();*/
			return url + parameters;
		}catch(Exception e){
			e.printStackTrace();
			String returnUrl = "/inc/dummy";
			//message : �벑濡� �떎�뙣
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
    }

	@RequestMapping(value = "book/regist.html", method = RequestMethod.GET)
	public String bookRegistGET(HttpSession session, HttpServletRequest request, AppVO appVO, ModelMap modelMap, AppList appList) {
		try{
			if(appVO.getRegGb()==null||appVO.getRegGb().length()==0){
				System.out.println("ppVO.getRegGb()1==="+appVO.getRegGb());
				appVO.setRegGb("1");
				System.out.println("ppVO.getRegGb()2==="+appVO.getRegGb());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		modelMap.addAttribute("appVO", appVO);
		modelMap.addAttribute("appList", appList);
    	return "08_book/book_write";
    }

	@RequestMapping(value = "book/regist.html", method = RequestMethod.POST)
	public String bookRegistPOST(AppVO appVO, HttpServletRequest request, ModelMap modelMap) throws Exception {
		//Map reqMap = WebUtil.getRequestMap(request); // Request Map 媛앹껜 �깮�꽦
		//Map model = new HashMap();	
		/*
			String area_idx = StringUtil.getVConv(reqMap.get("area"), "I");
			String searchAreOverseasYn = StringUtils.defaultString(StringUtil.getVConv( reqMap.get("oversea"), "S", "N")); // 援��궡 N , �빐�쇅 Y
		*/
		//WebUtil.checkParameter(request);		
		
		//System.out.println(appVO.toString());
		//�궗�슜�옄 �젙蹂� 
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
			//provSeq
			String regUserId = activeUser.getMemberVO().getUserId();
			String regUserGb = activeUser.getMemberVO().getUserGb();
			appVO.setRegUserSeq(regUserSeq);
			appVO.setRegUserId(regUserId);
			appVO.setRegUserGb(regUserGb);
	
	/*		List list = provisionService.selectList(vo);
	    	modelMap.addAttribute("provisionList", list);
	    	modelMap.addAttribute("provisionVo", vo);*/
			//db�엯�젰
					
			int appSeq = appService.getSeqAfterInsertAppInfo(appVO);
			//�씠誘몄� 泥섎윭
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))	 + messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));				
			//�븘�씠肄섏씠誘몄�
			String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....荑쇰━
			}
			//罹≪퀜�씠誘몄� 泥섎━
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
						//....荑쇰━ tb_capture
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
			//�봽濡쒕퉬�졏 �뿰寃고뀒�씠釉� �엯�젰 諛� 媛깆떊
			String[] provSeqArr = request.getParameterValues("provSeq");
			//String[] provIdArr = request.getParameterValues("provId");
			String storeBundleId1 = request.getParameter("storeBundleId1");
			String storeBundleId2 = request.getParameter("storeBundleId2");
			String[] provTestGbArr = request.getParameterValues("provTestGb");
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
			String url = "redirect:/book/list.html";
			String parameters = "?currentPage=1";
			return url + parameters;
		}catch(Exception e){
			//e.printStackTrace();
			String returnUrl = "/inc/dummy";
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
    }
	
	@RequestMapping(value = "book/inapp/list.html", method = {RequestMethod.GET, RequestMethod.POST})
	public String app_inapp_list(HttpSession session, HttpServletRequest request, ModelMap modelMap, InAppList inAppList, InappVO vo) {		

		System.out.println("currentUserName" + SecurityContextHolder.getContext().getAuthentication().getName());
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
		int completAndUsingCnt = 0;
		if(inAppList.getCurrentPage()==null)inAppList.setCurrentPage(1);
		inAppList = inAppService.getListByBundleId(vo, inAppList, activeUser.getMemberVO());

		for( int i =0 ; i< inAppList.getList().size() ; i++) {
			if( "1".equals(inAppList.getList().get(i).getCompletGb()) && "1".equals(inAppList.getList().get(i).getUseGb()) ) completAndUsingCnt++;
		}

		System.out.println("@@@@@@@@@@ availableCnt = " + completAndUsingCnt);
		modelMap.addAttribute("appContentsAmt", appVO.getAppContentsAmt());
		modelMap.addAttribute("appContentsGb", appVO.getAppContentsGb());
		modelMap.addAttribute("vo", vo);
		modelMap.addAttribute("inAppList", inAppList);
		modelMap.addAttribute("availableCnt", completAndUsingCnt);

		return "08_book/book_pop_inapp";
	}

	@RequestMapping(value = "book/inapp/regist.html", method = RequestMethod.GET)
	public String app_inapp_regist(HttpSession session, HttpServletRequest request, ModelMap modelMap, InAppList inAppList, InappVO vo) {
		modelMap.addAttribute("vo", vo);
		modelMap.addAttribute("inAppList", inAppList);
    	return "08_book/book_write_inapp";
    }

	@RequestMapping(value = "book/inapp/regist.html", method = RequestMethod.POST)
	public String app_inapp_regist_impl(AppVO appVO, String storeBundleId, String inappMetaTitle, HttpServletRequest request, InappMetaVO inappMetaVO, ModelMap modelMap, InAppList inAppList, InappVO vo, 
			@RequestParam("coverFile") MultipartFile[] cover, 
			@RequestParam("bodyFile") MultipartFile  body) throws Exception {
		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		String returnUrl = "redirect:/app/inapp/list.html";
		try{
/*			HashMap<Object, Object> map = new HashMap<Object, Object>();
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
			long fileSize = (Long) map.get("fileSize");
			vo.setInappSize( String.valueOf(fileSize));

*/
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
			vo.setInappName(inappMetaTitle);

			//db�엯�젰
			int inappSeq = inAppService.getSeqAfterInsertInAppInfo(vo);
			
			
			if(cover != null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				String fileType = "coverFile";
				map.put("fileType", fileType);
				map.put("inappSeq", inappSeq);
				map.put("error", "none");
				/*
				 * inappMetaVO.setInappMetaCover1("");
				inappMetaVO.setInappMetaCover2("");
				inappMetaVO.setInappMetaCover3("");
				inappMetaVO.setInappMetaCover4("");
				*/
				
				for(int i =0 ; i<cover.length ; i ++) {
					map.put("saveFileNameForCover", "cover"+(i+1));
					map = uploadFile(map, cover[i], request);
					
					switch(i) {
						case 0 : 
							inappMetaVO.setInappMetaCover1(((String)map.get("orgFileName")));
							break;
						case 1 :
							inappMetaVO.setInappMetaCover2(((String)map.get("orgFileName")));
							break;
						case 2 :
							inappMetaVO.setInappMetaCover3(((String)map.get("orgFileName")));
							break;
						case 3 :
							inappMetaVO.setInappMetaCover4(((String)map.get("orgFileName")));
							break;
					}
					if(!"none".equals(map.get("error"))){
						returnUrl = "/inc/dummy";
						modelMap.addAttribute("msg", map.get("error"));
						modelMap.addAttribute("type", "-1");
						return returnUrl;
					}
				}
			}

			if(body != null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				String fileType = "bodyFile";
				map.put("fileType", fileType);
				map.put("inappSeq", inappSeq);
				map.put("error", "none");
				map = uploadFile(map, body, request);
				
				inappMetaVO.setInappMetaBody(((String)map.get("orgFileName")));
				if(!"none".equals(map.get("error"))){
					returnUrl = "/inc/dummy";
					modelMap.addAttribute("msg", map.get("error"));
					modelMap.addAttribute("type", "-1");
					return returnUrl;
				}
			}

			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@3");
			InappMetaVO inappMetaVOForSelect = inAppService.findByCustomInfoForMetaVO("inappSeq", inappSeq);
			

			inappMetaVO.setInappSeq(inappSeq);
			inAppService.insertInAppMetaInfo(inappMetaVO);

			

			//�씠誘몄� 泥섎윭
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.icon.file", null, localeResolver.resolveLocale(request));				
			//�븘�씠肄섏씠誘몄�
			String iconOrgFile = appVO.getIconOrgFile();
			String iconSaveFile = appVO.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....荑쇰━
			}
			//罹≪퀜�씠誘몄� 泥섎━
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
						//....荑쇰━ tb_capture
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

		String url = "redirect:/book/inapp/list.html";
		String parameters = "?storeBundleId="+storeBundleId;
		returnUrl =  url + parameters;		
    	return returnUrl;
		}catch(Exception e){
			e.printStackTrace();
			returnUrl = "/inc/dummy";
			//message : �벑濡� �떎�뙣
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.005", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
    }

	@RequestMapping(value = "book/inapp/modify.html", method = RequestMethod.GET)
	public String app_inapp_modify(HttpSession session, HttpServletRequest request, String inappSeq, ModelMap modelMap, CaptureVO cvo, InAppList inAppList, InappVO ivo) {
		List captureList = null;
		InappMetaVO meta = null;
		String[] inappMetaISBN = null;
		String[] inappMetaDistributor = null;
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
			meta = inAppService.findByCustomInfoForMetaVO("inappSeq", ivo.getInappSeq());
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

			if(meta.getInappMetaISBN() != null)
			inappMetaISBN = meta.getInappMetaISBN().split(",");
			inappMetaDistributor = meta.getInappMetaDistributor().split(",");

			/*for(int i =0; i<inappMetaTranslator.length ; i++) {
				inappMetaTranslator[i] = inappMetaTranslator[i].trim();
			}
			for(int i =0; i< inappMetaISBN.length ; i++) {
				inappMetaISBN[i] = inappMetaISBN[i].trim();
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		modelMap.addAttribute("appContentsAmt", appVO.getAppContentsAmt());
		modelMap.addAttribute("appContentsGb", appVO.getAppContentsGb());
		modelMap.addAttribute("useS", useS);
		modelMap.addAttribute("ivo", ivo);
		modelMap.addAttribute("meta", meta);
		modelMap.addAttribute("inappMetaDistributor", inappMetaDistributor);
		modelMap.addAttribute("inappMetaISBN", inappMetaISBN);
		modelMap.addAttribute("availableCnt", completAndUsingCnt);
		modelMap.addAttribute("inAppList", inAppList);
		//modelMap.addAttribute("bundleList", bundleList);
		modelMap.addAttribute("captureList", captureList);
    	return "08_book/book_modify_inapp";
    }

	@RequestMapping(value = "book/inapp/modify.html", method = RequestMethod.POST)
	public String app_inapp_modify_impl(HttpSession session, HttpServletRequest request, 
			@RequestParam("coverFile") MultipartFile[] cover, 
			@RequestParam("bodyFile") MultipartFile  body,
			String[] useS, ModelMap modelMap, CaptureVO cvo, String inappSeq, InappMetaVO inappMetaVO, InappVO ivo, InAppList inappList) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		GrantedAuthority element = authorities.iterator().next();
		String authority = element.getAuthority();
		
		System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
		
		//�궗�슜�옄 �젙蹂�
		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		InappSubVO inappSubVO = new InappSubVO();

		try {
			InappVO ivo2 = inAppService.selectForUpdate(ivo, activeUser.getMemberVO());
			if(ivo2==null){
				//throw new Exception("�옒紐삳맂 �젒洹쇱엯�땲�떎.");
				String returnUrl = "/inc/dummy";
				//message : �옒紐삳맂 �젒洹쇱엯�땲�떎.
				modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
				modelMap.addAttribute("type", "-1");
				return returnUrl;
			}
			//�궗�슜�궇吏� 誘몄궗�슜�궇吏�

			if(!ivo2.getUseGb().equals(ivo.getUseGb())){
				if("1".equals(ivo.getUseGb())){
					ivo.setUseAvailDt(new Date());
				}else{
					ivo.setUseDisableDt(new Date());
				}
			}

			if("ROLE_ADMIN_SERVICE".equals(authority)){
				//�젣�븳 �궇吏�
				if(!ivo2.getLimitGb().equals(ivo.getLimitGb())){
					if("1".equals(ivo.getLimitGb())){
						ivo.setLimitDt(new Date());
					}
				}
			}
			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");
			/*for(int i =0 ;  i< cover.length ; i++) {
				System.out.println("@#$%%^&* = " + cover[i].getOriginalFilename());
			}*/
			if(cover != null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				String fileType = "coverFile";
				map.put("fileType", fileType);
				map.put("inappSeq", inappSeq);
				map.put("error", "none");
				/*inappMetaVO.setInappMetaCover1("");
				inappMetaVO.setInappMetaCover2("");
				inappMetaVO.setInappMetaCover3("");
				inappMetaVO.setInappMetaCover4("");*/
				
				for(int i =0 ; i<cover.length ; i ++) {
					map.put("saveFileNameForCover", "cover"+(i+1));
					map = uploadFile(map, cover[i], request);
					
					switch(i) {
						case 0 : 
							inappMetaVO.setInappMetaCover1(((String)map.get("orgFileName")));
							break;
						case 1 :
							inappMetaVO.setInappMetaCover2(((String)map.get("orgFileName")));
							break;
						case 2 :
							inappMetaVO.setInappMetaCover3(((String)map.get("orgFileName")));
							break;
						case 3 :
							inappMetaVO.setInappMetaCover4(((String)map.get("orgFileName")));
							break;
					}
					if(!"none".equals(map.get("error"))){
						String returnUrl = "/inc/dummy";
						modelMap.addAttribute("msg", map.get("error"));
						modelMap.addAttribute("type", "-1");
						return returnUrl;
					}
				}
			}

			if(body != null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				String fileType = "bodyFile";
				map.put("fileType", fileType);
				map.put("inappSeq", inappSeq);
				map.put("error", "none");
				map = uploadFile(map, body, request);
				
				inappMetaVO.setInappMetaBody(((String)map.get("orgFileName")));
				if(!"none".equals(map.get("error"))){
					String returnUrl = "/inc/dummy";
					modelMap.addAttribute("msg", map.get("error"));
					modelMap.addAttribute("type", "-1");
					return returnUrl;
				}
			}
			
			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@3");
			InappMetaVO inappMetaVOForSelect = inAppService.findByCustomInfoForMetaVO("inappSeq", Integer.parseInt(inappSeq));
			
			System.out.println("inappMetaVO subTitle = " + inappMetaVO.getInappMetaSubtitle() );
			if(inappMetaVOForSelect == null) {
				inAppService.insertInAppMetaInfo(inappMetaVO);
			}else {
				inAppService.updateInAppMetaInfo(inappMetaVO, inappMetaVOForSelect.getInappMetaSeq());
			}
			/*if(ivo.getInappSaveFile()==null){
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				String fileType = "inappFile";
				map.put("fileType", fileType);
				map = uploadFile(map, file, request);
				ivo.setInappOrgFile((String)map.get("orgFileName"));
				ivo.setInappSaveFile((String)map.get("saveFileName"));
				ivo.setInappSize(String.valueOf(map.get("fileSize")));
				System.out.println("map.get('error')====="+"["+map.get("error")+"]");
				if(!"none".equals(map.get("error"))){
					String returnUrl = "/inc/dummy";
					modelMap.addAttribute("msg", map.get("error"));
					modelMap.addAttribute("type", "-1");
					return returnUrl;
				}
			}*/

			ivo.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
			ivo.setChgUserId(activeUser.getMemberVO().getUserId());
			ivo.setChgUserGb(activeUser.getMemberVO().getUserGb());

			inAppService.updateInAppInfo(ivo, ivo.getInappSeq());
			//�씠誘몄� 泥섎윭
			String tempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
			String toPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.icon.file", null, localeResolver.resolveLocale(request));				
			//�븘�씠肄섏씠誘몄�
			String iconOrgFile = ivo.getIconOrgFile();
			String iconSaveFile = ivo.getIconSaveFile();
			if(FileUtil.movefile(iconSaveFile, tempPath, toPath)){
				//....荑쇰━
			}
			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@4");
			//罹≪퀜�씠誘몄� 泥섎━
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
						//....荑쇰━ tb_capture
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
			}
			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@5");
			//湲곗〈�뙆�씪
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
					if("capture".equals(deleteFileType)){//耳묒퀜�뙆�씪 �궘�젣
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.capture.file", null, localeResolver.resolveLocale(request));
						if(FileUtil.delete(new File(deleteFilePath+deleteSaveFileName))){
							CaptureVO captureVO = new CaptureVO();
							captureVO.setCaptureSeq(deleteFileSeq);
							captureService.delete(captureVO);	
							captureVO = null;							
						}
					}else if("icon".equals(deleteFileType)){//�븘�씠肄섑뙆�씪�궘�젣
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.upload.path.inapp.icon.file", null, localeResolver.resolveLocale(request));
						FileUtil.delete(new File(deleteFilePath+deleteSaveFileName));												
					}else if("inapp".equals(deleteFileType)){//�씤�빋�뙆�씪�궘�젣
						deleteFilePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
						FileUtil.delete(new File(deleteFilePath+deleteSaveFileName));
					}
				}
			}

			inappSubVO.setInappSeq(ivo.getInappSeq());	
			if("1".equals(ivo.getUseUserGb()) && !"".equals(ivo.getInappSeq())){
				inAppService.deleteInAppSubInfo(inappSubVO);
			}
			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@6");
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

			System.out.println("Book/inapp/Modify@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@7");
			String url = "redirect:/book/inapp/list.html";
			
			System.out.println("ReturnURL@@@@@@@@@@@@@@@@@@@@@@@@@ = " + url);
			String parameters = "?&storeBundleId="+ivo.getStoreBundleId();
			return url + parameters;
		}catch(Exception e){
			e.printStackTrace();
			//return "redirect:/app/list.html";
			//throw new Exception("�옒紐삳맂 �젒洹쇱엯�땲�떎.");
			String returnUrl = "/inc/dummy";
			//message : ���옣 �떎�뙣
			modelMap.addAttribute("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
			modelMap.addAttribute("type", "-1");
			return returnUrl;
		}
    }
	
	//PDF �몴吏� �뾽濡쒕뱶
	
	@RequestMapping(value = "book/coverFileupload.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> bookCoverFileUploadPOST(HttpServletRequest request, HttpSession session, @RequestParam("coverFile") MultipartFile file){
		System.out.println("@@@@@@@@@@@@book/coverfileupload.html");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try{
			String fileType = request.getParameter("fileType");
			String inappSeq = request.getParameter("inappSeq");
			map.put("fileType", fileType);
			map.put("inappSeq", inappSeq);
			map = uploadFile(map, file, request);
		}catch(Exception e){
			return map;			
		}
		return map;
	}
	
	//PDF 蹂몃Ц �뾽濡쒕뱶
	@RequestMapping(value = "book/bodyFileupload.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> bookBodyFileUploadPOST(HttpServletRequest request, HttpSession session, @RequestParam("bodyFile") MultipartFile file){
		System.out.println("@@@@@@@@@@@@book/coverfileupload.html");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		try{
			String fileType = request.getParameter("fileType");
			String inappSeq = request.getParameter("inappSeq");
			map.put("fileType", fileType);
			map.put("inappSeq", inappSeq);
			map = uploadFile(map, file, request);
		}catch(Exception e){
			return map;			
		}
		return map;
	}

	
	@SuppressWarnings({ "finally", "resource", "unchecked" })
	@RequestMapping(value = "book/createTextAppRequestPOST.html", method = RequestMethod.POST)
	public @ResponseBody int bookCreateTextInappSeqPOST(HttpServletRequest request, AppVO appVO, String templateSeq, String appSeq, String dataType, String inappSeq, HttpSession session){
		System.out.println("11@@@@@@@@@@@@book/bookCreateTextInappSeqPOST.html");

		myUserDetails activeUser = null;
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			return 2;
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		BufferedWriter writer = null;
		String savePath = messageSource.getMessage("file.path.request.file", null, localeResolver.resolveLocale(request));
		String saveFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		boolean isNotBuilding =false;

		//20180618 : lsy - when app make request, inapp regist contents(copy inapp file, insert tb_contents)
		//1. 변수 세팅
		boolean ret = false;
		String inappLoadPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
		String contentsSavePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request));		
		ContentVO contentVO = new ContentVO();
		ContentsappSubVO contentsAppSubVO = new ContentsappSubVO();
		InAppList inAppList = new InAppList();
		
		//2. 인앱 생성 요청 시, inapp->contents로 복사할 인앱 대상들 찾기(inapp 대상, 개수)
		inAppList = inAppService.getInAppByBundleId(inAppList, appVO.getStoreBundleId());

		//3. 검색한 인앱 대상별로 inapp->contents화 처리
		for(int i=0;i<inAppList.getTotalCount();i++) {
			String processFile = inAppList.getList().get(i).getInappSaveFile();
			
			//3-1. 인앱 대상별로 contents에 등록되어 있는지 확인(tb_contentsapp_sub로 확인)
			//	- processGb 0 : 인앱 첫 등록인 경우 
			//	- processGb 1 : 인앱 이미 등록한 경우
			Entity param = new Entity();
			param.setValue("inappSeq", inAppList.getList().get(i).getInappSeq());
			param.setValue("appSeq", appVO.getAppSeq());
			int processGb = contentsService.inappContentsProcessGb(param);
			
			//3-2. processGb별 contents 파일 분기 처리
			//	- processGb 0 : 파일 복사 처리
			//	- processGb 1 : 기존경로 찾아 파일 삭제 + 파일 복사 처리
			if(processGb == 1) {
				File deleteFolder = new File( contentsSavePath + processFile.substring(0, processFile.lastIndexOf(".")) );
				ret = FileUtil.delete(deleteFolder);
			}else {
				ret = true;
			}
			
			if(ret) {
				ret = contentsCopyFromInapp(inappLoadPath, contentsSavePath, processFile);
				ret = unzipContentsView(contentsSavePath, processFile);
			}
			
			//3-3. processGb별 DB 분기 처리
			//	- processGb 0 : 인앱을 새 등록 
			//	- processGb 1 : 인앱 이미 등록(앱/인앱 내용 수정하는 경우)
			try {
				if(ret) {
					String contentsAppName = appVO.getAppName()+"("+appVO.getVerNum()+")"+" > "+inAppList.getList().get(i).getInappName();
					if(processGb == 0) {
						contentVO.setAppName(contentsAppName);
						contentVO.setAppType(appVO.getOstype());
						contentVO.setCompanySeq(memberService.getCompanySeqForInappContentCopy(inAppList.getList().get(i).getRegUserSeq()));
						contentVO.setCompletGb("2");
						contentVO.setContentsName(inAppList.getList().get(i).getInappName());
						
						contentVO.setContentsSize(fileSizeCalc(contentsSavePath, processFile));
						contentVO.setContentsType("1");
						contentVO.setCouponNum("");
						contentVO.setDescriptionText(inAppList.getList().get(i).getDescriptionText());
						contentVO.setLimitGb("2");

						contentVO.setRegUserGb(inAppList.getList().get(i).getRegUserGb());
						contentVO.setRegUserId(inAppList.getList().get(i).getRegUserId());

						contentVO.setRegUserSeq(inAppList.getList().get(i).getRegUserSeq());
						contentVO.setUploadOrgFile(inAppList.getList().get(i).getInappOrgFile());
						contentVO.setUploadSaveFile(processFile);
						contentVO.setUseGb("1");				
						contentVO.setVerNum(inAppList.getList().get(i).getVerNum());
						
						contentVO.setRegDt(inAppList.getList().get(i).getRegDt());
						
						//3-4. 인앱정보로 콘텐츠 등록(tb_contents)
						int contentsSeq = contentsService.insertContentsFromInapp(contentVO);
						
						contentsAppSubVO.setContentsSeq(contentsSeq);
//						contentsAppSubVO.setStoreBundleId(appVO.getStoreBundleId());
						contentsAppSubVO.setAppSeq(String.valueOf(appVO.getAppSeq()));
						contentsAppSubVO.setInappSeq(inAppList.getList().get(i).getInappSeq());
						
						//3-5. 인앱정보로 콘텐츠 등록(tb_contentsapp_sub)
						contentsService.insertContentsSubFromInapp(contentsAppSubVO);
					}else if(processGb == 1) {
						int contentsSeq = contentsService.selectRelatedContentsSeq(param);												
						contentVO.setContentsSeq(contentsSeq);
						
						contentVO.setContentsName(inAppList.getList().get(i).getInappName());
						contentVO.setVerNum(inAppList.getList().get(i).getVerNum());

						ContentVO beforeContentInfo = contentsService.selectBeforeContentInfo(contentsSeq);
						contentVO.setAppName(beforeContentInfo.getAppName());
						contentVO.setAppType(beforeContentInfo.getAppType());
						
						contentVO.setDescriptionText(inAppList.getList().get(i).getDescriptionText());
						contentVO.setUploadOrgFile(inAppList.getList().get(i).getInappOrgFile());
						contentVO.setUploadSaveFile(processFile);
						
						contentVO.setChgUserSeq(activeUser.getMemberVO().getUserSeq());
						contentVO.setChgUserId(activeUser.getMemberVO().getUserId());
						contentVO.setChgUserGb(activeUser.getMemberVO().getUserGb());
						contentVO.setChgDt(new Date());
						contentVO.setContentsSize(fileSizeCalc(contentsSavePath, processFile));						
						contentVO.setChgContentsDt(new Date());
						
						//3-4. 인앱정보로 콘텐츠 수정(tb_contents)
						contentsService.updateContentsFromInapp(contentVO);
					}
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		//20180618 : lsy - when app make request, inapp regist contents(copy inapp file, insert tb_contents) - end

		String CompanyID =  memberService.findCompanyMemberIdByCompanySeqAndUserGb(activeUser.getMemberVO().getCompanySeq());
		
		System.out.println("appVO.getOstype() = " + appVO.getOstype());
//		System.out.println("22@@@@@@@@@@@@book/bookCreateTextInappSeqPOST.html");
		
		try {			
			System.out.println("dataType = " + dataType);
			System.out.println("appVO.StoreBundleId = " + appVO.getStoreBundleId());
			if("inapp".equals(dataType)) {
				File file = new File(savePath+saveFileName+".inapp");
				writer = new BufferedWriter(new FileWriter(file));
//				System.out.println("66@@@@@@@@@@@@book/bookCreateTextInappSeqPOST.html");
				AppVO appVOtemp = appService.selectByStoreId(appVO.getStoreBundleId());
				InappMetaVO inappMetaVO = inAppService.findByCustomInfoForMetaVO("inappSeq", Integer.parseInt(inappSeq));
				System.out.println("appResultCode = " + appVOtemp.getApp_resultCode());
				if(!"1".equals(appVOtemp.getApp_resultCode())) {
//					System.out.println("77@@@@@@@@@@@@book/bookCreateTextInappSeqPOST.html");
					return 3;
				}
				System.out.println("inappSeq = " + inappSeq);
				System.out.println("inappMetaVOSeq = " + inappMetaVO.getInappMetaSeq());
				
				writer.write(inappSeq+", "+inappMetaVO.getInappMetaSeq());
				writer.close();
				return 1;
			}else if("app".equals(dataType)) {
//				File file = new File(savePath+appVO.getStoreBundleId()+".pbapp");
//				writer = new BufferedWriter(new FileWriter(file));
				List<BundleVO> bundleVO = bundleService.listByAppSeq(Integer.parseInt(appSeq));
				//provisionService.findByCustomInfo("provSeq", bundleVO.get(0).getProvSeq());
//				System.out.println("33@@@@@@@@@@@@book/bookCreateTextInappSeqPOST.html");
				String writeString = "{\n"
						+"\"appContentsAmt\" : \""+appVO.getAppContentsAmt()+"\","
						+"\n\"appSeq\" : \""+appVO.getAppSeq()+"\","
						+"\n\"appName\" : \""+appVO.getAppName()+"\","
						+"\n\"fileName\" : \""+appVO.getFileName()+"\","
						+"\n\"userId\" : \""+activeUser.getMemberVO().getUserId()+"\","
						+"\n\"provisionGb\" : \""+"1"+"\","
						+"\n\"devices\" : \""+appVO.getOstype()+"\","
						+"\n\"content\" : \""+"include"+"\","
						+"\n\"appContentsGb\" : \""+appVO.getAppContentsGb()+"\","
						+"\n\"regGb\" : \""+appVO.getRegGb()+"\",";
				
				if(bundleVO.size() > 1) {
					writeString +="\n\"provTestGb2\" : \""+bundleVO.get(1).getProvTestGb()+"\",";
				}
				
				if(!"4".equals(appVO.getOstype())) {
					writeString +="\n\"provTestGb1\" : \""+bundleVO.get(0).getProvTestGb()+"\",";
					writeString += "\n\"provSeq1\" : \""+bundleVO.get(0).getProvSeq()+"\",";
					if(bundleVO.get(0).getprovisonVO() == null) {
						return 6;
					}
					writeString += "\n\"distrProfileName\" : \""+bundleVO.get(0).getprovisonVO().getDistrProfileName()+"\",";
				}
				
				writeString +="\n\"storeBundleId\" : \""+appVO.getStoreBundleId()+"\","
				+"\n\"universal\" : \""+"1,2"+"\","
				+"\n\"Login\" : \""+CompanyID+"\","
				+"\n\"configuration\" : \""+"AdHoc"+"\",";
				
				if(appVO.getDescriptionText() == null) {
					writeString +="\n\"descriptionText\" : \""+""+"\",";
				}else {
					writeString +="\n\"descriptionText\" : \""+appVO.getDescriptionText()+"\",";
				}
				
				writeString += "\n\"templateSeq\" : \""+templateSeq+"\",";
				
				if(bundleVO.size() >1) {
					writeString +="\n\"provSeq2\" : \""+bundleVO.get(1).getProvSeq()+"\",";
				}
				
				if("4".equals(appVO.getOstype())) {
					writeString += "\n\"versionCode\" : \""+appVO.getVersionCode()+"\",";
				}
				
				if(appVO.getIconSaveFile() != null && !appVO.getIconSaveFile().equals("")) {
					writeString += "\n\"iconSaveFile\" : \""+appVO.getIconSaveFile()+"\",";
				}
				
				writeString +="\n\"fixScreen\" : \""+"Y"+"\","
				+"\n\"verNum\" : \""+appVO.getVerNum()+"\"";
				
				writeString +="\n}";
				
//				writer.write(writeString);
//				writer.close();
				
				//20180712 : lsy - icon.zip Make, JobTicket Make ==> JobTicket FTP Upload : process modify
				FTPClient client = null;
				FileInputStream fis = null;
				boolean isSuccess = false;

				String[] url = messageSource.getMessage("FTP.Info.IP", null, localeResolver.resolveLocale(request)).split(":");
				String ip = url[0];
				String port = url[1];
				String id = messageSource.getMessage("FTP.Info.ID", null, localeResolver.resolveLocale(request));
				String pwd = messageSource.getMessage("FTP.Info.PW", null, localeResolver.resolveLocale(request));
				
				client = new FTPClient(); // FTP Client 객체
				client.setControlEncoding("UTF-8");
				client.connect(ip, Integer.parseInt(port));
				client.login(id, pwd);
				client.enterLocalPassiveMode();
				client.setFileType(FTP.BINARY_FILE_TYPE);
				
				//1. 앱용 아이콘 사이즈별 만들기, 압축하기, FTP 전송
				if( appVO.getIconSaveFile() != null && !appVO.getIconSaveFile().equals("") ) {//icon이 있을 때만 icon 처리					
					client.makeDirectory(appVO.getStoreBundleId());
					client.changeWorkingDirectory("/"+appVO.getStoreBundleId());
					
					//1> 아이콘 만들 준비
					String iconPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(request));
					String iconTempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
					String iconMakePath = iconTempPath+"icon/", iconMakeName = "icon.png";
				
					FileUtil.copy(new File(iconPath, appVO.getIconSaveFile()), new File(iconTempPath, appVO.getIconSaveFile()));
					File iconMake = new File(iconMakePath);
					iconMake.mkdirs();					
				
					//2> size별 아이콘 만들기
					int toBeWidth[];
					if(appVO.getOstype().equals("1") || appVO.getOstype().equals("2") || appVO.getOstype().equals("3")) {
						toBeWidth = new int[]{57, 114, 120, 72, 144, 76, 152};
//temp					FileUtil.copy(new File(iconTempPath, appInfo.getIconSaveFile()), new File(iconMakePath, iconMakeName));
					}else{
						toBeWidth = new int[]{96};
						FileUtil.copy(new File(iconTempPath, appVO.getIconSaveFile()), new File(iconMakePath, iconMakeName));
					}
				
					String fileExt = appVO.getIconSaveFile().substring(appVO.getIconSaveFile().lastIndexOf(".") + 1, appVO.getIconSaveFile().length());					
					BufferedImage originalImage = ImageIO.read(new File(iconTempPath, appVO.getIconSaveFile()));
					
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
						return 5;
					}
					
					//5> 압축하기 위해 만든 파일 정리(icon dir, zip, image)
					FileUtil.delete(iconMake);
					FileUtil.delete(uploadIcon);
					FileUtil.delete( new File(iconTempPath, appVO.getIconSaveFile()) );
				}
				//1. 앱용 아이콘 사이즈별 만들기, 압축하기, FTP 전송 - end

				//2. make jobTicket to file by String in localServer tempPath
				String makeTempPath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))+messageSource.getMessage("file.upload.path.temp", null, localeResolver.resolveLocale(request));
				
				String pbappName = appVO.getStoreBundleId()+".pbapp";
				File jobTicketfile = new File(makeTempPath+pbappName);
				BufferedWriter jobTicketWriter = new BufferedWriter(new FileWriter(jobTicketfile));
				jobTicketWriter.write(writeString);
				jobTicketWriter.close();
				
				//3. ftpUpload jobTicket at localServer tempPath
				File uploadJobTicket = new File(makeTempPath, pbappName); // File 객체
				
				client.changeWorkingDirectory("/Q");
				
				fis = new FileInputStream(uploadJobTicket);
				isSuccess = client.storeFile(pbappName, fis);
				client.disconnect();
				fis.close();

				if(isSuccess) {
					//temp에 pbapp파일 지우기
					FileUtil.delete( uploadJobTicket );
					return 1;
				}else {
					return 5;
				}
				//20180712 : lsy - icon.zip Make, JobTicket Make ==> JobTicket FTP Upload : process modify - end
			}else {
				return 5;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

/*	20180718 : lsy - checkInappNameIfExist(인앱 중복확인) 로직 변경으로 인한 로직 제거
	@RequestMapping(value="/book/inapp/checkIfInappName.html" ,method=RequestMethod.POST)
	public @ResponseBody boolean checkIfInappNamePOST(String inappMetaTitle, String storeBundleId, String[] useS,HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		boolean isExist=  inAppService.checkInappNameIfExist(inappMetaTitle, storeBundleId);
		
		return isExist;
	}
*/
	public HashMap<Object, Object> uploadFile(HashMap map, MultipartFile upLoadFile, HttpServletRequest request) throws Exception {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		//InputStream tikaInputStream = null;
		try{
			if (upLoadFile != null) {
				if (upLoadFile.getSize() > 0) {
					String savePath = "";
					String webPath = "";
					String saveFileName = "";
					String orgFileName = upLoadFile.getOriginalFilename();
					String orgFileOnlyName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
					 
					String fileExt = orgFileName.substring(orgFileName.lastIndexOf(".") + 1, orgFileName.length());
					
					fileExt = fileExt.toLowerCase();
					
					if("coverFile".equals(map.get("fileType"))) {
						String saveFileOnlyName = (String)map.get("saveFileNameForCover");
						saveFileName = saveFileOnlyName + "." + fileExt;
					}else if("bodyFile".equals(map.get("fileType"))) {
						String saveFileOnlyName = "body";
						saveFileName = saveFileOnlyName + "." + fileExt;
					}else {
						String saveFileOnlyName = DateUtil.getDate("yyyyMMdd_hhmmss") + "_" + Formatter.getRandomNumber(100);
						saveFileName = saveFileOnlyName + "." + fileExt;
					}

					inputStream = upLoadFile.getInputStream();				
					map.put("orgFileName", orgFileName);
					map.put("saveFileName", saveFileName);
					map.put("fileSize", upLoadFile.getSize());
					map.put("fileExt", fileExt);
					String mimeType = upLoadFile.getContentType();
					if("inappFile".equals(map.get("fileType"))){
/*						if (!("image/jpeg".equals(mimeType)||"image/gif".equals(mimeType)||"image/png".equals(mimeType)||"image/jpg".equals(mimeType))) {					 
							map.put("error", "�벑濡앺븷 �닔 �뾾�뒗 �뙆�씪 �삎�깭�엯�땲�떎.");
							outputStream.close();
							inputStream.close();
							return map;
							//throw new Exception("�벑濡앺븷 �닔 �뾾�뒗 �뙆�씪�삎�깭�엯�땲�떎.");
						}*/
						
						savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
						//webPath = messageSource.getMessage("file.web.path.temp.images.file", null, localeResolver.resolveLocale(request));
					}else if("coverFile".equals(map.get("fileType"))) {
						savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.path.pdf.file", null, localeResolver.resolveLocale(request)) + map.get("inappSeq") +"/";
						boolean success;
						success = (new File(savePath)).mkdirs();
						if (!success) {
						    // Directory creation failed
						}
					}else if("bodyFile".equals(map.get("fileType"))) {
						savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.path.pdf.file", null, localeResolver.resolveLocale(request)) + map.get("inappSeq")+"/";
						boolean success;
						success = (new File(savePath)).mkdirs();
						if (!success) {
						    // Directory creation failed
						}
					}else{
						if (!("image/jpeg".equals(mimeType)||"image/gif".equals(mimeType)||"image/png".equals(mimeType)||"image/jpg".equals(mimeType))) {
							// message : �벑濡앺븷 �닔 �뾾�뒗 �뙆�씪 �삎�깭�엯�땲�떎.
							map.put("error", messageSource.getMessage("app.control.002", null, localeResolver.resolveLocale(request)));
							//outputStream.close();
							inputStream.close();
							return map;
							//throw new Exception("�벑濡앺븷 �닔 �뾾�뒗 �뙆�씪�삎�깭�엯�땲�떎.");
						}
						savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) +  messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
						webPath = messageSource.getMessage("file.upload.path.temp.images.file", null, localeResolver.resolveLocale(request));
					}
	
					
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println("savePath = " + savePath);
					System.out.println("saveFileName = " + saveFileName);
					outputStream = new FileOutputStream(savePath + saveFileName);
					 
					int readBytes = 0;
					byte[] buffer = new byte[8192];
					
					while ((readBytes = inputStream.read(buffer, 0 , 8192)) != -1) {
						outputStream.write(buffer, 0, readBytes);
					}
					if("inappFile".equals(map.get("fileType"))){
						
					}else if("coverFile".equals(map.get("fileType"))) {
						
					}else if("bodyFile".equals(map.get("fileType"))) {
						
					}else{
						
						//�뙆�씪 鍮꾩쑉寃��닔 諛� 由ъ궗�씠利� 
						BufferedImage uploadImages = ImageIO.read(new File(savePath + saveFileName));	
						int orgHeight = uploadImages.getHeight();
						int orgWidth = uploadImages.getWidth();
						
						//�븘�씠肄섏씪�븣
						int toBeWidth = 300;
						int toBeHeight = 300;
						if ("iconFile".equals(map.get("fileType"))) {							
							if(orgHeight!=orgWidth){
								// message : �씠誘몄� 鍮꾩쑉�씠 留욎� �븡�뒿�땲�떎. �븘�씠肄섏씠誘몄��뒗 �젙�궗媛곹삎留� �벑濡앹씠 媛��뒫�빀�땲�떎.
								map.put("error", messageSource.getMessage("app.control.003", null, localeResolver.resolveLocale(request)));
								outputStream.close();
								inputStream.close();
								return map;						
							}							 
						}
						if ("captureFile".equals(map.get("fileType"))) {
						/*
							if(orgHeight!=orgWidth){
								map.put("error", "�씠誘몄� 鍮꾩쑉�씠 留욎� �븡�뒿�땲�떎.");
								outputStream.close();
								inputStream.close();
								return map;						
							}
								 */
								//orgHeight/orgWidth=toBeHeight/toBeWidth
								/*if(orgHeight>768){
									toBeWidth = Integer.valueOf(orgWidth/orgHeight*768);
									toBeHeight = 768;							
								}else{
									toBeWidth = orgWidth;
									toBeHeight = orgHeight;
								}*/
						}
						BufferedImage originalImage = ImageIO.read(new File(savePath + saveFileName));
						BufferedImage resizeImage = ImageTask.resizeImage(originalImage, originalImage.getType(), toBeWidth, toBeHeight);
						ImageIO.write(resizeImage, fileExt, new File(savePath + saveFileName));
					}
					
					map.put("webPath", webPath+saveFileName);
					System.out.println("webPath+saveFileName = " + webPath+saveFileName);
					map.put("error", "none");
					//�뙆�씪 鍮꾩쑉寃��닔 諛� 由ъ궗�씠利� 
					outputStream.close();
					//outputStream.flush();
					inputStream.close();
					outputStream = null;
					inputStream = null;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			// message : �뾽濡쒕뱶 以묒뿉 �삤瑜섍� 諛쒖깮�뻽�뒿�땲�떎.
			map.put("error", messageSource.getMessage("app.control.004", null, localeResolver.resolveLocale(request)));		}
		return map;
	}
	
	public static boolean contentsCopyFromInapp(String inFilePath, String outFilePath, String fileName) {
		try {
			String folderName = fileName.substring(0, fileName.lastIndexOf("."));
			String inFile = inFilePath+fileName;
			String outFile = outFilePath+folderName+File.separator+fileName;
//System.out.println("fileName = " + fileName);System.out.println("inFile = " + inFile);System.out.println("outFile = " + outFile);

			File folder = new File(outFilePath+folderName);
//System.out.println("folder = " + folder);
	    	if(!folder.exists()){
	    		try{
	    			boolean flag= folder.mkdir();
	    			System.out.println("flag = " + flag);
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    	}

			FileInputStream fis = new FileInputStream(inFile);
			FileOutputStream fos = new FileOutputStream(outFile);

			int data = 0;
			while((data=fis.read())!=-1) {
				fos.write(data);
			}
			
			fis.close();
			fos.close();
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean unzipContentsView(String savePath, String fileName) {
		byte[] buffer = new byte[1024];
		String folderName = fileName.substring(0, fileName.lastIndexOf("."));
		String zipFile = savePath+folderName+"/"+fileName;
		
		try {
			File folder = new File(savePath+folderName+File.separator+"view");
			if(!folder.exists()){
	    		folder.mkdir();
	    	}
			
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
			
			while(ze!=null){
				String zipFileName = ze.getName();
				File newFile = new File(savePath+folderName+File.separator+"view" + File.separator + zipFileName);

				//create all non exists folders
				//else you will hit FileNotFoundException for compressed folder
				if (ze.isDirectory()){	
					String temp = newFile.getCanonicalPath();
					new File(temp).mkdir();
				}else{
					FileOutputStream fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static String fileSizeCalc(String savePath, String fileName) {
		String folderName = fileName.substring(0, fileName.lastIndexOf("."));
		String zipFilePath = savePath+folderName+"/"+fileName;

		DecimalFormat format = new DecimalFormat(".##");
		File zipFile = new File(zipFilePath);
		float floatValue = (float)zipFile.length()/(1024*1024);
		String fileSize = "";
		
		if ( floatValue < 1 ) {
			fileSize = "0"+ format.format(floatValue)+"MB";
		}else {
			fileSize = format.format(floatValue)+"MB";
		}
		
		return fileSize;
	}
	
}