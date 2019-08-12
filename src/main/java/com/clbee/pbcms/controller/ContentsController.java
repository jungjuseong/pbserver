package com.clbee.pbcms.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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



import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import thredds.inventory.FeatureCollectionConfig.UpdateConfig;

import com.clbee.pbcms.service.AppService;
import com.clbee.pbcms.service.ContentsService;
import com.clbee.pbcms.service.DistributeService;
import com.clbee.pbcms.service.GroupViewMenuService;
import com.clbee.pbcms.service.InAppService;
import com.clbee.pbcms.util.AuthenticationException;
import com.clbee.pbcms.util.DateUtil;
import com.clbee.pbcms.util.FileUtil;
import com.clbee.pbcms.util.Formatter;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.ContentList;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.DistributeRestoreVO;
import com.clbee.pbcms.vo.InappcategoryVO;
import com.clbee.pbcms.vo.MemberList;
import com.clbee.pbcms.vo.MemberVO;

@Controller
public class ContentsController {
	
	@Autowired
	ContentsService contentsService;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AppService appService;
	
	@Autowired
	InAppService inAppService;
	
	@Autowired
	LocaleResolver localeResolver;

	//20180419 : lsy - distribute restore popup
	@Autowired
	DistributeService distributeService;

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

	@RequestMapping( value = "/contents/list.html", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView contentsListGETPOST( String toChk, String[] toChk_, String page, String searchType, String searchValue, HttpServletRequest request, HttpSession session, String isPost) throws ParseException {
		System.out.println("@@@@@@@@contentList[GET, POST] is POST = " + isPost );

		ModelAndView modelAndView = new ModelAndView();

		String[] formattedDate = null;
		String[] formattedDownInfo = null;
		ContentList contentList = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");

		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String authority = null;

		GrantedAuthority element = authorities.iterator().next();
		authority = element.getAuthority();

		if( !("true".equals(isPost))) {
			toChk_ = new String[0];
		}

/*		20180518 : lsy - 권한 체계 변경
		if("ROLE_COMPANY_MEMBER".equals(authority) || "ROLE_COMPANY_MIDDLEADMIN".equals(authority) || "ROLE_COMPANY_DISTRIBUTOR".equals(authority) || "ROLE_COMPANY_CREATOR".equals(authority)) {
*/
		if("ROLE_COMPANY_MEMBER".equals(authority)	||	"ROLE_USER".equals(authority)	) {
			System.out.println("[POST] page = " + page);
			
			for(int i =0; i<toChk_.length ;i++) {
				System.out.println("toChk_["+i+"] = " + toChk_[i] );
			}
			contentList = contentsService.getListContents(Integer.parseInt(page), 10, toChk_, "companySeq", activeUser.getMemberVO().getCompanySeq(), searchType, searchValue, true);
			formattedDate = new String[contentList.getList().size()];	
			formattedDownInfo = new String[contentList.getList().size()];
			modelAndView.addObject("ToChk", toChk);
			modelAndView.addObject("ToChk_", toChk_);
		}else if("ROLE_INDIVIDUAL_MEMBER".equals(authority)){
			contentList = contentsService.getListContents(Integer.parseInt(page), 10, toChk_, "regUserSeq", activeUser.getMemberVO().getUserSeq(), searchType, searchValue, true);
			formattedDate = new String[contentList.getList().size()];
			formattedDownInfo = new String[contentList.getList().size()];
			modelAndView.addObject("ToChk", toChk);
			modelAndView.addObject("ToChk_", toChk_);
		}else if("ROLE_ADMIN_SERVICE".equals(authority)) {
			contentList = contentsService.getListContents(Integer.parseInt(page), 10, toChk_, null, null, searchType, searchValue, false);
			formattedDate = new String[contentList.getList().size()];
			formattedDownInfo = new String[contentList.getList().size()];
			modelAndView.addObject("ToChk", toChk);
			modelAndView.addObject("ToChk_", toChk_);
		}

		if(contentList != null) {
			for( int i = 0; i < contentList.getList().size() ; i++){
				formattedDate[i] = format.format(contentList.getList().get(i).getRegDt());
				if("2".equals(contentList.getList().get(i).getDistrGb()) && "1".equals(contentList.getList().get(i).getCouponGb())) {
					if("1".equals(contentList.getList().get(i).getNonmemDownGb())) {
						formattedDownInfo[i] = contentList.getList().get(i).getNonmemDownAmt() + " "+messageSource.getMessage("app.list.text1", null, localeResolver.resolveLocale(request));
					}else if("2".equals(contentList.getList().get(i).getNonmemDownGb())) {
						formattedDownInfo[i] = format.format(contentList.getList().get(i).getNonmemDownStarDt()) + " ~ "  + format.format(contentList.getList().get(i).getNonmemDownEndDt());
					}
				}else{
					if("1".equals(contentList.getList().get(i).getMemDownGb())) {
						formattedDownInfo[i] = contentList.getList().get(i).getMemDownAmt() + " "+messageSource.getMessage("app.list.text1", null, localeResolver.resolveLocale(request));
					}else if("2".equals(contentList.getList().get(i).getMemDownGb())){
						formattedDownInfo[i] = format.format(contentList.getList().get(i).getMemDownStartDt()) + " ~ "  + format.format(contentList.getList().get(i).getMemDownEndDt());
					}
				}
			}
		}

		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
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
	
		modelAndView.addObject("currentPage", page);
		modelAndView.addObject("formattedDownInfo",formattedDownInfo);
		modelAndView.addObject("formattedDate",formattedDate);
		modelAndView.addObject("contentList", contentList);
		modelAndView.addObject("searchValue", searchValue);
		modelAndView.setViewName("02_contents/contents_list");
		/*List contentListHash =  contentsService.getListContentOfCheckBox(sdf, 1, new ContentList(2, 2, 2, 2), "");
		modelAndView.addObject("contentListHash", contentListHash);*/

		return modelAndView;
	}

	@RequestMapping( value = "contents/write.html", method=RequestMethod.GET)
	public ModelAndView contentsWriteGET(ContentVO content){

		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("memberVO", activeUser.getMemberVO());
		modelAndView.setViewName("02_contents/contents_write");

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		for(GrantedAuthority auth : authentication.getAuthorities()) {
			if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
				//20180515 : lsy - GroupViewMenu Util Create
				Map<String, Object> menuList = new HashMap<String, Object>();
				menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
				
				modelAndView.addObject("menuLarge", menuList.get("menuLarge"));			
				//20180515 : lsy - GroupViewMenu Util Create - end
			}
		}
		//20180516 : lsy - temp if/else - end
		
		return modelAndView;
	}

	@RequestMapping( value = "contents/write.html", method=RequestMethod.POST)
	public ModelAndView contentsWritePOST( HttpServletRequest request, String relatedAppName, String relatedAppType, String relatedAppSeq, String relatedInAppSeq, ContentVO contentVO,  @RequestParam("uploadContentsFile") MultipartFile file){

		ModelAndView modelAndView = new ModelAndView();
		HashMap<String, String> map = new HashMap<String, String>();
		ContentsappSubVO contentsappSubVO = new ContentsappSubVO();
		try {
			map = uploadContents(map, file, request);
			unzip(map, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		contentVO.setUploadOrgFile(map.get("orgFileName")+"."+map.get("fileExt"));
		contentVO.setUploadSaveFile(map.get("saveFileName")+"."+map.get("fileExt"));
		contentVO.setContentsSize(map.get("fileSize"));
		contentVO.setAppName(relatedAppName);
		contentVO.setAppType(relatedAppType);
		int contentsSeq = contentsService.insertContentInfo( contentVO );
		
/*		20180625(월) : lsy - 관련앱 팝업 사용 중지로 인한 불필요 소스
		if(relatedBundleId != null && !"".equals(relatedBundleId)) {
			contentsappSubVO.setStoreBundleId(relatedBundleId);
		}
		if(relatedInAppSeq != null && !"".equals(relatedInAppSeq)) {
			contentsappSubVO.setInappSeq(Integer.parseInt(relatedInAppSeq));
		}
*/
		contentsappSubVO.setContentsSeq(contentsSeq);
		
		contentsService.insertContentsappSubInfo(contentsappSubVO);
		
		modelAndView.setViewName("redirect:/contents/list.html?page=1");
		return modelAndView;
	}

	@RequestMapping( value = "contents/modify.html", method=RequestMethod.GET)
	public ModelAndView contentsModifyGET( String contentsSeq, HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		ContentVO contentVO = contentsService.selectByContentId(Integer.parseInt(contentsSeq));
		modelAndView.addObject("contentVO", contentVO);
		modelAndView.setViewName("02_contents/contents_modify");
		
		//20180423 : lsy - after process, return page gubun
		if(request.getHeader("referer").indexOf("/distribute") != -1 && request.getHeader("referer").indexOf("/distribute") > 0) {
			modelAndView.addObject("referer", "distribute");
		}else if(request.getHeader("referer").indexOf("/contents") != -1 && request.getHeader("referer").indexOf("/contents") > 0) {
			modelAndView.addObject("referer", "list");
		}
		//20180423 : lsy - after process, return page gubun - end
		
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
				
				modelAndView.addObject("menuLarge", menuList.get("menuLarge"));			
				//20180515 : lsy - GroupViewMenu Util Create - end
			}
		}
		//20180516 : lsy - temp if/else - end
		modelAndView.addObject("myListGb", request.getParameter("myListGb"));
		
		return modelAndView;
	}
	
	@RequestMapping( value = "contents/deleteContents.html", method=RequestMethod.POST)
	public ModelAndView contentsDeletePOST(String contentsSeq, HttpServletRequest request, HttpSession session) {
  		ModelAndView mav = new ModelAndView();
  		String page;  		
  		page = paramSet(request, "page");
  		
  		try{
  			contentsService.deleteContentsInfo(Integer.parseInt(contentsSeq));
  			contentsService.deleteContentsSubBySeq(Integer.parseInt(contentsSeq));
  			mav.setViewName("redirect:/contents/list.html?page="+Integer.parseInt(page));
  		}catch(Exception e){
  			e.printStackTrace();
  		}

  		return mav;
	}

	@RequestMapping( value = "contents/modify.html", method=RequestMethod.POST)
	public ModelAndView contentsModifyPOST( String contentsappSubSeq,  String relatedAppName, String relatedAppType, String relatedAppSeq, String relatedInAppSeq, String page, String fileChanged, ContentVO contentFormVO, @RequestParam("contentsFile") MultipartFile uploadFile , HttpServletRequest request, HttpSession session) {
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView modelAndView = new ModelAndView();
		HashMap<String, String> map = new HashMap<String, String>();
		String savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request));
		String saveFIleName = contentFormVO.getUploadSaveFile().substring( 0, contentFormVO.getUploadSaveFile().lastIndexOf("."));
		ContentsappSubVO contentsappSubVO = new ContentsappSubVO();
		
		
		if("1".equals(fileChanged)) {
			System.out.println("퍼일이 변경되었습니다!!!!!!!!!!");
			try{
				
				/*File deleteFile = new File(savePath+contentFormVO.getUploadSaveFile());
				if(!FileUtil.delete(deleteFile)){
					modelAndView.addObject("msg", messageSource.getMessage("contents.control.001", null, localeResolver.resolveLocale(request)));
					modelAndView.addObject("type", "href");
					modelAndView.addObject("url", "/contents/modify.html?page="+page+"&contentSeq="+contentFormVO.getContentsSeq());
					modelAndView.setViewName("inc/dummy");
					//return 0;
				}*/
				
				File deleteFolder = new File(savePath+saveFIleName);

				if(!FileUtil.delete(deleteFolder)){
					modelAndView.addObject("msg", messageSource.getMessage("contents.control.001", null, localeResolver.resolveLocale(request)));
					modelAndView.addObject("type", "href");
					modelAndView.addObject("url", "/contents/modify.html?page="+page+"&contentsSeq="+contentFormVO.getContentsSeq());
					modelAndView.setViewName("inc/dummy");
				}
				
				//필요한부분만 업데이트하기위해 일단 객체를 새로 생성함..
				
				map = uploadContents(map, uploadFile, request);
				//압축 풀기
				unzip(map, request);

				//20180404 : lsy - contentsUpdate OK -> chg_contents_date update
				contentFormVO.setChgContentsDt(new Date());
				
				contentFormVO.setUploadOrgFile(map.get("orgFileName") +"."+ map.get("fileExt"));
				contentFormVO.setUploadSaveFile(map.get("saveFileName") + "." + map.get("fileExt"));
				contentFormVO.setContentsSize(map.get("fileSize"));
				contentFormVO.setAppName(relatedAppName);
				contentFormVO.setAppType(relatedAppType);
				
/*				20180619 - when contents modify, not modify _sub table info			
				if(relatedBundleId != null && !"".equals(relatedBundleId)) {
					contentsappSubVO.setStoreBundleId(relatedBundleId);
				}
				if(relatedInAppSeq != null && !"".equals(relatedInAppSeq)) {
					contentsappSubVO.setInappSeq(Integer.parseInt(relatedInAppSeq));
				}
				contentsappSubVO.setContentsSeq(contentFormVO.getContentsSeq());
*/				
				//20180327 - lsy : develop version managemenet	-> name + version : unique
				ContentVO contentVO = contentsService.selectByContentId(contentFormVO.getContentsSeq());
				if(contentFormVO.getCompletGb().equals("1") && (contentVO.getCompletGb().equals("3"))) {		//1. completgb modify yes check (seq check)
					String historySeq = contentsService.getSameContentsSeq(contentFormVO.getContentsName());	//2. same content(name+complet 'yes-->1') exist check
					if(historySeq != null) {																	//3. content exist, newVersion insert to tb_contentshistory / oldVersion delete from tb_contents
						try {
							contentsService.insertContentsHistory(historySeq);
							contentsService.deleteContentsBySeq(historySeq);
						}catch(Exception e){
							e.printStackTrace();
							modelAndView.addObject("msg", messageSource.getMessage("contents.control.003", null, localeResolver.resolveLocale(request)));
							modelAndView.addObject("type", "href");
							modelAndView.addObject("url", "/contents/modify.html?page="+page+"&contentsSeq="+contentFormVO.getContentsSeq());
							modelAndView.setViewName("inc/dummy");
						}
					}
				}
				//20180327 - lsy : develop version managemenet - end

				// 배포요청일 경우 - 배포요청일 Update		=> 20180410 : lsy - distribute process modify 
				if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeReq")) {
					//20180404 : lsy - completYN : history process OK -> distribute_req_dt update
					contentFormVO.setDistributeReqDt(new Date());
					contentFormVO.setDistributeRequestId(activeUser.getMemberVO().getUserId());
				}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) { 
					//6. 배포승인일 경우 - 배포승인일 Update		=> 20180417 : lsy - distribute process modify(distribute Complet)
					contentFormVO.setDistributeCompletDt(new Date());
					contentFormVO.setDistributeAcceptId(activeUser.getMemberVO().getUserId());
				}
				
				//20180405 : lsy - info change -> chg_dt update
				int infoUpdateCheck = contentsService.infoUpdateCheck(contentFormVO, contentFormVO.getContentsSeq());
				if(infoUpdateCheck >= 1) {
					contentFormVO.setChgDt(new Date());
				}
				
				if(contentsappSubSeq != null && !"".equals(contentsappSubSeq)) {
					contentsService.updateContentsappSubInfo(contentsappSubVO, Integer.parseInt(contentsappSubSeq));
				}else {
					contentsService.insertContentsappSubInfo(contentsappSubVO);
				}
				contentsService.updateContentInfo(contentFormVO, contentFormVO.getContentsSeq());
				
				//20180412 : lsy - 배포요청 alert 추가
				if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeReq")) {
					modelAndView.addObject("msg", messageSource.getMessage("contents.control.004", null, localeResolver.resolveLocale(request)));
				}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("modify")) {
					modelAndView.addObject("msg", messageSource.getMessage("contents.control.002", null, localeResolver.resolveLocale(request)));
				}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) {
					modelAndView.addObject("msg", messageSource.getMessage("contents.control.005", null, localeResolver.resolveLocale(request)));
				}
				//20180412 : lsy - 배포요청 alert 추가 - end
				
				//20180418 : lsy - distribute restore process modify
				modelAndView.addObject("type", "href");
				if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) {
					if(request.getParameter("referer").equals("list")) {
						modelAndView.addObject("url", "/contents/list.html?page="+page);
					}else{
						modelAndView.addObject("url", "/distribute/list.html?page=1&objectGb=contents&myListGb=n");				
					}
				}else {
					modelAndView.addObject("url", "/contents/list.html?page="+page);
				}
				//20180418 : lsy - distribute restore process modify - end
				modelAndView.setViewName("inc/dummy");
			}catch(Exception e){
				e.printStackTrace();
				//return 0;			
				modelAndView.addObject("msg", messageSource.getMessage("contents.control.003", null, localeResolver.resolveLocale(request)));
				modelAndView.addObject("type", "href");
				modelAndView.addObject("url", "/contents/modify.html?page="+page+"&contentsSeq="+contentFormVO.getContentsSeq());
				modelAndView.setViewName("inc/dummy");
			}
			//return 1;
		}else {
			contentFormVO.setAppName(relatedAppName);
			contentFormVO.setAppType(relatedAppType);
			
/*		20180625(월) : lsy - 관련앱 팝업 사용 중지로 인한 불필요 소스
			contentsappSubVO.setStoreBundleId(relatedBundleId);
*/
			if(relatedAppSeq != null && !"".equals(relatedAppSeq)) {
				contentsappSubVO.setAppSeq(relatedAppSeq);
			}
			if(relatedInAppSeq != null && !"".equals(relatedInAppSeq)) {
				contentsappSubVO.setInappSeq(Integer.parseInt(relatedInAppSeq));
			}
			contentsappSubVO.setContentsSeq(contentFormVO.getContentsSeq());

			//20180327 - lsy : develop version managemenet
			ContentVO contentVO = contentsService.selectByContentId(contentFormVO.getContentsSeq());
			if(contentFormVO.getCompletGb().equals("1") && contentVO.getCompletGb().equals("3")) {			//1. completgb modify yes check (seq check)
				String historySeq = contentsService.getSameContentsSeq(contentFormVO.getContentsName());	//2. same content(name+complet 'yes') exist check
				if(historySeq != null) {																	//3. content exist, newVersion insert to tb_contentshistory / oldVersion delete from tb_contents
					try {
						contentsService.insertContentsHistory(historySeq);
						contentsService.deleteContentsBySeq(historySeq);
					}catch(Exception e){
						e.printStackTrace();
						modelAndView.addObject("msg", messageSource.getMessage("contents.control.003", null, localeResolver.resolveLocale(request)));
						modelAndView.addObject("type", "href");
						modelAndView.addObject("url", "/contents/modify.html?page="+page+"&contentsSeq="+contentFormVO.getContentsSeq());
						modelAndView.setViewName("inc/dummy");
					}
				}
			}

			// 배포요청일 경우 - 배포요청일 Update		=> 20180410 : lsy - distribute process modify 
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeReq")) {
				//20180404 : lsy - completYN : history process OK -> distribute_req_dt update
				contentFormVO.setDistributeReqDt(new Date());
				contentFormVO.setDistributeRequestId(activeUser.getMemberVO().getUserId());
			}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) { 
				//6. 배포승인일 경우 - 배포승인일 Update		=> 20180417 : lsy - distribute process modify(distribute Complet)
				contentFormVO.setDistributeCompletDt(new Date());
				contentFormVO.setDistributeAcceptId(activeUser.getMemberVO().getUserId());
			}
			
			//20180327 - lsy : develop version managemenet - end
			int infoUpdateCheck = contentsService.infoUpdateCheck(contentFormVO, contentFormVO.getContentsSeq());
			if(infoUpdateCheck >= 1) {
				contentFormVO.setChgDt(new Date());
			}

			if(contentsappSubSeq != null && !"".equals(contentsappSubSeq)) {
				contentsService.updateContentsappSubInfo(contentsappSubVO, Integer.parseInt(contentsappSubSeq));
			}else {
				contentsService.insertContentsappSubInfo(contentsappSubVO);
			}
			contentsService.updateContentInfo(contentFormVO, contentFormVO.getContentsSeq());
			
			//20180412 : lsy - 배포요청 alert 추가
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeReq")) {
				modelAndView.addObject("msg", messageSource.getMessage("contents.control.004", null, localeResolver.resolveLocale(request)));
			}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("modify")) {
				modelAndView.addObject("msg", messageSource.getMessage("contents.control.002", null, localeResolver.resolveLocale(request)));
			}else if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) {
				modelAndView.addObject("msg", messageSource.getMessage("contents.control.005", null, localeResolver.resolveLocale(request)));
			}
			//20180412 : lsy - 배포요청 alert 추가 - end
			//20180418 : lsy - distribute restore process modify
			modelAndView.addObject("type", "href");
			if(request.getParameter("distributeProcess") != null && request.getParameter("distributeProcess").equals("distributeComplet")) {
				if(request.getParameter("referer").equals("list")) {
					modelAndView.addObject("url", "/contents/list.html?page="+page);
				}else{
					modelAndView.addObject("url", "/distribute/list.html?page=1&objectGb=contents&myListGb=n");				
				}
			}else {
				modelAndView.addObject("url", "/contents/list.html?page="+page);
			}
			//20180418 : lsy - distribute restore process modify - end
			modelAndView.setViewName("inc/dummy");
		}
		return modelAndView;
	}

	public void unzip( HashMap map, HttpServletRequest request){
		byte[] buffer = new byte[1024];
		String savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request));
		
		System.out.println("unzip!!!");
		
		try{
			File folder = new File(savePath+map.get("saveFileName")+"/view");
			if(!folder.exists()){
	    		folder.mkdir();
	    	}
			ZipInputStream zis = 
		    		new ZipInputStream(new FileInputStream(savePath+map.get("saveFileName")+"/"+map.get("saveFileName")+".zip"));
			ZipEntry ze = zis.getNextEntry();
			
			
			System.out.println("@@@@@@ze = " +ze );
			
			while(ze!=null){
    			
		    	   String fileName = ze.getName();
		           File newFile = new File(savePath+map.get("saveFileName")+"/view" + File.separator + fileName);
		                
		           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
		                
		           //create all non exists folders
		           //else you will hit FileNotFoundException for compressed folder
		           if (ze.isDirectory())
		           {	
		        	   System.out.println("[IS A DIRECTORY]");
		        	   String temp = newFile.getCanonicalPath();
		        	   new File(temp).mkdir();
		           }else{
		        	   System.out.println("[NOT A DIRECTORY]");
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
		    		
		    	System.out.println("Done");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public HashMap<String, String> uploadContents(HashMap map, MultipartFile upLoadFile,  HttpServletRequest request) throws Exception {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		float floatValue = (float)upLoadFile.getSize()/(1024*1024);
		DecimalFormat format = new DecimalFormat(".##");
		String savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request));
		if (upLoadFile != null) {
			if (upLoadFile.getSize() > 0) {
				String orgFileName = upLoadFile.getOriginalFilename();
				String orgFileOnlyName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
				String fileExt = orgFileName.substring(orgFileName.lastIndexOf(".") + 1, orgFileName.length());
				fileExt = fileExt.toLowerCase();
				
				String saveFileOnlyName = DateUtil.getDate("yyyyMMdd_hhmmss") + "_" + Formatter.getRandomNumber(100);
				String saveFileName = saveFileOnlyName + "." + fileExt;

				File folder = new File(savePath+saveFileOnlyName);
				System.out.println("folder = " + folder);
		    	if(!folder.exists()){
		    		System.out.println("Exist");
		    		try{
		    			boolean flag= folder.mkdir();
		    			System.out.println("flag = " + flag);
		    		}catch(Exception e){
		    			e.printStackTrace();
		    		}
		    	}
				
				
				inputStream = upLoadFile.getInputStream();				
				map.put("orgFileName", orgFileOnlyName);
				map.put("saveFileName", saveFileOnlyName);
				if ( floatValue < 1 ) {
					map.put("fileSize", "0"+ format.format(floatValue)+"MB");
				}else {
					map.put("fileSize", format.format(floatValue)+"MB");
				}
				
				map.put("fileExt", fileExt);				
				/*if (!("mobileprovision").equals(fileExt.toLowerCase())||("keystore").equals(fileExt.toLowerCase())) {					 
					throw new Exception("�벑濡앺븷 �닔 �뾾�뒗 �뙆�씪�삎�깭�엯�땲�떎.");
				}						 */
				
				outputStream = new FileOutputStream(savePath +saveFileOnlyName +"/"+ saveFileName);
				int readBytes = 0; 
				byte[] buffer = new byte[8192];
				 
				while ((readBytes = inputStream.read(buffer, 0 , 8192)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
			}
		}
		return map;
	}

	@RequestMapping( value = "/contents/deleteFile.html", method=RequestMethod.POST)
	public @ResponseBody int deleteF( String contentsSeq, String uploadSaveFile, HttpServletRequest request, HttpSession session ) {

		System.out.println("contentsSeq = " + contentsSeq);
		System.out.println("uploadSaveFile = " + uploadSaveFile);
		try{
			String savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request));
			File file = new File(savePath+uploadSaveFile);
			if(!FileUtil.delete(file)){
				return 0;
			}
			ContentVO contentVO = new ContentVO();
			contentVO.setContentsSeq(Integer.parseInt(contentsSeq));
			contentVO.setUploadSaveFile("");
			contentsService.updateNullableContentInfo(contentVO);
		}catch(Exception e){
			return 0;
		}
		return 1;
	}

	@RequestMapping( value = "/contents/write/popUpContents.html", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView contentsWritePopCategoryGET(ContentVO content, String isPost){
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView mav = new ModelAndView();
		MemberVO memberVO = new MemberVO();
		List<AppVO> appList = null;
		String authority = activeUser.getAuthorities().iterator().next().getAuthority();

		if("true".equals(isPost)) ;//무슨 소스?
/*		20180518 : lsy - 권한 체계 변경
		if ("ROLE_COMPANY_MEMBER".equals(authority) || "ROLE_COMPANY_DISTRIBUTOR".equals(authority) || "ROLE_COMPANY_CREATOR".equals(authority)) {
*/
		if ("ROLE_COMPANY_MEMBER".equals(authority) || "ROLE_USER".equals(authority)) {
			/*해당 정보를 넣어줘야 null값이 안떠서 특정 조건을 실행함*/
			memberVO.setUserGb(activeUser.getMemberVO().getUserGb());
			/* 기업이기 때문에 CompanySeq를 넣어줘야함*/
			memberVO.setCompanySeq(activeUser.getMemberVO().getCompanySeq());
		}else if("ROLE_INDIVIDUAL_MEMBER".equals(authority)){
			/*해당 정보를 넣어줘야 null값이 안떠서 특정 조건을 실행함*/
			memberVO.setUserGb(activeUser.getMemberVO().getUserGb());
			/* 개인이기 때문에 userSeq를 넣어줘야함*/
			memberVO.setUserSeq(activeUser.getMemberVO().getUserSeq());
		}
		appList =  appService.selectAppListForRelatedApp(memberVO);			
		mav.addObject("appList", appList);
		mav.setViewName("02_contents/contents_pop_category");
		return mav;
	}

	@RequestMapping( value = "/contents/getInAppList.html", method=RequestMethod.POST)
	public @ResponseBody Object[] contentsInAppListPOST( String contentsSeq, String uploadSaveFile, HttpServletRequest request, HttpSession session ) {
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object[] InAppList = null;
		String appSeq 		= this.paramSet(request, "storeBundleId");
		String authority = activeUser.getAuthorities().iterator().next().getAuthority();
		InAppList = inAppService.getListInAppForRelatedApp(appSeq);		
		System.out.println("====================================================================================3");
		System.out.println(InAppList);
		System.out.println("====================================================================================4");
		return InAppList;
	}

	//20180327 - lsy : develop version managemenet
	@RequestMapping(value={"/contents/verValidation.html"}, method=RequestMethod.POST)
	public @ResponseBody int verValidation( String contentsName, String ver_num ){
		return contentsService.verifyIfExists(contentsName, ver_num);
	}
	
	@RequestMapping(value="/contents/history.html" ,method=RequestMethod.GET)
	public ModelAndView contentsHistoryGET(String contents_name, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<ContentVO> contentsList = contentsService.selectForHistory(URLDecoder.decode(contents_name, "UTF-8"));
		
		mav.addObject("contents_name", URLDecoder.decode(contents_name, "UTF-8"));
		mav.addObject("type_gb", "contents");
		mav.addObject("contentsList", contentsList);
		mav.setViewName("inc/popup_history");

		return mav;
	}
	
	@RequestMapping(value="/contents/restoreView.html" ,method=RequestMethod.GET)
	public ModelAndView contentsRestoreViewGET(DistributeRestoreVO distributeRestoreVO, String name, HttpSession session, HttpServletRequest req) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<DistributeRestoreVO> distributeRestoreList = distributeService.selectForRestore(distributeRestoreVO);
		
		mav.addObject("name", name);
		mav.addObject("type_gb", "contents");
		mav.addObject("list", distributeRestoreList);
		mav.setViewName("09_distribute/popup_restore");

		return mav;
	}
	//20180327 - lsy : develop version managemenet - end

	private String paramSet(HttpServletRequest req, String targetName) {
		String value = "";
		value = (null == req.getParameter(targetName)) ? "" : "" + req.getParameter(targetName);
		
		return value;
	}	
}
