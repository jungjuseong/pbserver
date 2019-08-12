package com.clbee.pbcms.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clbee.pbcms.service.AppService;
import com.clbee.pbcms.service.ContentsService;
import com.clbee.pbcms.service.GroupViewMenuService;
import com.clbee.pbcms.service.InAppService;
import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.service.ProvisionService;
import com.clbee.pbcms.service.TemplateService;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.StringUtil;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.MapList;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.ProvisionVO;
import com.clbee.pbcms.vo.TemplateVO;

@Controller
@RequestMapping("/*.html")
public class DownloadController  {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AppService appService;
	
	@Autowired
	ContentsService contentsService;
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	InAppService inAppService;
	
	@Autowired
	ProvisionService provisionService;
	
	@Autowired
	TemplateService templateService;

	//20180515 : lsy - GroupViewMenu Create
	@Autowired
	GroupViewMenuService groupViewMenuService;

	@RequestMapping(value="/down/list.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView downList(HttpSession session, HttpServletRequest req, MapList mapList, MemberVO memVO) throws Exception{

		ModelAndView mav = new ModelAndView();
		Entity param = new Entity();
		
		String toChk, toChk_, loginUserCompanyGb, todayDate;
		toChk = toChk_ = loginUserCompanyGb = todayDate = "";
		String authority = null;
		int loginUserSeq, loginUserCompanySeq;
		String isMobile = paramSet(req, "isMobile");

		if(!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
			myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
			loginUserSeq        = activeUser.getMemberVO().getUserSeq();
			loginUserCompanySeq = activeUser.getMemberVO().getCompanySeq();
			loginUserCompanyGb  = activeUser.getMemberVO().getCompanyGb();

			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

			GrantedAuthority element = authorities.iterator().next();
			authority = element.getAuthority();
			todayDate = appService.getSelectTodayDate();   

			param.setValue("REGSEQ", loginUserSeq); //�α��� SEQ
			param.setValue("COMPANY_SEQ", loginUserCompanySeq); //�α��� COMPANY_SEQ
			param.setValue("COMPANY_GB", loginUserCompanyGb); //�α��� ����/ȸ�� ����
			param.setValue("TODAYDATE", todayDate); //���� �����
			param.setValue("AUTHORITY", authority); //�α��� ���� Ű
			param.setValue("ISMOBILE", isMobile);
			param.setValue("sessionUserSeq", activeUser.getMemberVO().getUserSeq());
			
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			for(GrantedAuthority auth : authentication.getAuthorities()) {
				if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
				//20180515 : lsy - GroupViewMenu Util Create
				Map<String, Object> menuList = new HashMap<String, Object>();
				menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
				
				mav.addObject("menuLarge", menuList.get("menuLarge"));
				mav.addObject("menuFunction", menuList.get("menuFunction"));

				param.setValue("menuFunction", (String)menuList.get("menuFunction"));
				//20180515 : lsy - GroupViewMenu Util Create - end
				}
			}
			//20180516 : lsy - temp if/else - end
			
		}else{
			param.setValue("AUTHORITY", "ROLE_ANONYMOUS"); //�α��� ���� Ű
			param.setValue("ISMOBILE", isMobile);
			param.setValue("COMPANY_GB", ""); //�α��� ����/ȸ�� ����
		}

		toChk  = paramSet(req, "toChk");
		toChk_ = paramSet(req, "toChk_");
		
		
		int pageSize = 10;
		int maxResult = 10;
		int totalCount = 0;

		if(mapList.getCurrentPage()==null) {
			mapList.setCurrentPage(1);
		}
		
		if(toChk != null) {
			if(toChk_.toString().indexOf("5") > -1){
				param.setValue("searchChk", toChk_+",6,7");
			}else{
				param.setValue("searchChk", toChk_);
			}
		}
		//비활성화는 param.getKey("searchChk") null
		
		param.setValue("searchType", mapList.getSearchType());
		param.setValue("searchValue", mapList.getSearchValue());
		
		List listCnt = appService.getSelectListCount(param); //비활성화일 down.xml에 downSelectListCount의 배열 (AppServiceImpl -> AppDaoImpl)
		HashMap temp = (HashMap)listCnt.get(0);
		totalCount = Integer.parseInt(temp.get("DOWNTOTALCOUNT").toString());
		mapList.calc(pageSize, totalCount, mapList.getCurrentPage(), maxResult);		
		param.setValue("startNo", mapList.getStartNo());

		List downList = appService.getSelectList(param);

		System.out.println(mapList + "========================================================================");
		//maplist = (MapList) appService.getSelectList(param, maplist);
		mav.addObject("path",  messageSource.getMessage("file.path.app.file", null, localeResolver.resolveLocale(req)));
		mav.addObject("pathIos",  messageSource.getMessage("file.path.ios.down.file", null, localeResolver.resolveLocale(req)));
		mav.addObject("DownList", downList);
		mav.addObject("MapList", mapList);
		mav.addObject("ToChk", toChk);
		mav.addObject("ToChk_", toChk_);

		mav.setViewName("/03_down/down_list");
		return mav;
	}

	@RequestMapping(value="/down/down.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView downLoad(HttpSession session, HttpServletRequest req, MapList mapList, ModelMap modelMap) throws Exception{

		ModelAndView mav = new ModelAndView();
		Entity param = new Entity();

		String downGubun, downName, downKaU, downVer, downSeq, coupon_Num, ostype, fileName, downCnt, downType, isCoupon, directDown, iconName;
		downGubun = downName = downKaU = downVer = downSeq = coupon_Num= ostype = fileName = downCnt = downType = isCoupon = directDown = iconName = "";

		downGubun  		= paramSet(req, "downGubun");
		downName  		= paramSet(req, "downName");
		downKaU  		= paramSet(req, "downKaU");
		downVer	  	 	= paramSet(req, "downVer");
		downSeq	    	= paramSet(req, "downSeq");
		ostype			= paramSet(req, "ostype");
		fileName		= paramSet(req, "fileName");
		downCnt			= paramSet(req, "downCnt");
		coupon_Num		= paramSet(req, "coupon_Num");
		downType		= paramSet(req, "downType");
		isCoupon		= paramSet(req, "isCoupon");
		directDown      = paramSet(req, "directDown");
		iconName		= paramSet(req, "iconName");

		if("Android".equals(ostype)){
			if("1".equals(directDown)) {
				String path, orgName, saveName;
				path = orgName = saveName = "";

				System.out.println("ostype is = " + ostype);
				AppVO appVO = appService.selectById(Integer.parseInt(paramSet(req, "downSeq")));
				path = messageSource.getMessage("file.path.app.file", null, localeResolver.resolveLocale(req)) + "android/"+downKaU+downVer+"/";
				orgName = appVO.getFileName();
				saveName = appVO.getFileName();
				req.setAttribute("filepath", path);
				req.setAttribute("orgFileName", orgName+downVer+".apk");
				req.setAttribute("saveFileName", saveName+downVer+".apk");

				mav.setViewName("/inc/download");
			}else {
				mav.addObject("DownGubun", downGubun);
				mav.addObject("DownName", downName);
				mav.addObject("DownKaU", downKaU);
				mav.addObject("DownVer", downVer);
				mav.addObject("downSeq", downSeq);
				mav.addObject("ostype", ostype);
				mav.addObject("downCnt", downCnt);
				mav.addObject("coupon_Num", coupon_Num);
				mav.addObject("fileName", fileName);
				mav.addObject("osFlag", "Android");
				mav.addObject("downType", downType);
				mav.addObject("isCoupon", isCoupon);
				mav.addObject("iconName", iconName);
				mav.addObject("iconPath", messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(req)));
				mav.addObject("filePath", messageSource.getMessage("file.path.app.file", null, localeResolver.resolveLocale(req)) + "android/");
				mav.setViewName("android_app");
			}
		}else if("HTML5".equals(ostype) || "ePub3".equals(ostype) || "PDF".equals(ostype)){
			
			String path, orgName, saveName, onlySaveName;
			path = orgName = saveName = onlySaveName = "";

			System.out.println("ostype is = " + ostype);
			ContentVO contetnsVO = contentsService.selectByContentId(Integer.parseInt(paramSet(req, "downSeq")));
			path = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(req)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(req));
			orgName = contetnsVO.getUploadOrgFile();
			saveName = contetnsVO.getUploadSaveFile();
			onlySaveName = saveName.substring(0,saveName.lastIndexOf("."));
			req.setAttribute("filepath", path);
			req.setAttribute("orgFileName", orgName);
			req.setAttribute("saveFileName", onlySaveName+"/"+saveName);
			mav.setViewName("/inc/download");
			return mav;
		}else {
			if("1".equals(directDown)) {
				String path, orgName, saveName;
				path = orgName = saveName = "";
				System.out.println("ostype is = " + ostype);
				AppVO appVO = appService.selectById(Integer.parseInt(paramSet(req, "downSeq")));
				path = messageSource.getMessage("file.path.app.file", null, localeResolver.resolveLocale(req)) + "ios/"+downKaU+downVer+"/";
				orgName = appVO.getFileName();
				saveName = appVO.getFileName();
				req.setAttribute("filepath", path);
				req.setAttribute("orgFileName", orgName+downVer+".ipa");
				req.setAttribute("saveFileName", saveName+downVer+".ipa");
				
				mav.setViewName("/inc/download");
			}else {
				mav.addObject("DownGubun", downGubun);
				mav.addObject("DownName", downName);
				mav.addObject("DownKaU", downKaU);
				mav.addObject("DownVer", downVer);
				mav.addObject("downSeq", downSeq);
				mav.addObject("ostype", ostype);
				mav.addObject("coupon_Num", coupon_Num);
				mav.addObject("fileName", fileName);
				mav.addObject("osFlag", "iOS");
				mav.addObject("downCnt", downCnt);
				mav.addObject("downType", downType);
				mav.addObject("isCoupon", isCoupon);
				mav.addObject("iconName", iconName);
				mav.addObject("iconPath", messageSource.getMessage("file.upload.path.app.icon.file", null, localeResolver.resolveLocale(req)));
				mav.addObject("filePath", messageSource.getMessage("file.path.app.file.iOS", null, localeResolver.resolveLocale(req)));
				mav.setViewName("android_app");
			}
		}
		return mav;
	}

	@RequestMapping(value="/down/pop_coupon.html" ,method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView downLoadCoupon(HttpSession session, HttpServletRequest req, MapList mapList) throws Exception{
		ModelAndView mav = new ModelAndView();
		Entity param = new Entity();

		mav.setViewName("/03_down/down_pop_coupon");
		return mav;
	}

	@RequestMapping(value="/down/couponOk" ,method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Entity downLoadCouponOk(HttpSession session, HttpServletRequest req, MapList mapList) throws Exception{

		ModelAndView mav = new ModelAndView();
		Entity param = new Entity();
		String couponNum;
		couponNum = "";
		couponNum = paramSet(req, "fm_coupon_num");
		param.setValue("coupon_Gb", "1");
		param.setValue("coupon_Num", couponNum);
		List downList = appService.getSelectCouponList(param);

		param.setValue("TotalCnt", downList.size());
		param.setValue("DownList", downList);
		mav.setViewName("/03_down/down_pop_coupon");
		//return mav;
		return param;
	}

	@RequestMapping(value="downloadCounting.html" ,method= RequestMethod.POST)
	public @ResponseBody int downloadCountingPOST( String abc, HttpSession session, HttpServletRequest req, MapList mapList) throws Exception{
        /*"sort" : sort,
        "nonmemDownGb" : result.NONDOWNYN,
        "nonmemDownSTDT" : result.NONMEMDOWNSTDT,
        "nonmemDownENDT" : result.NONMEMDOWNENDT,
        "nonmemDownCnt" : reseult.NONDOWNCNT*/
        String sort = paramSet(req, "sort");
        String couponGb = paramSet(req, "couponGb");
        String nonmemDownGb = paramSet(req, "nonmemDownGb");
        String downCnt = paramSet(req, "downCnt");
        String nonmemDownSTDT = paramSet(req, "nonmemDownSTDT");
        String nonmemDownENDT = paramSet(req, "nonmemDownENDT");
        String downSeq = paramSet(req, "downSeq");
        String isCoupon = paramSet(req, "isCoupon");
		Entity param = new Entity();

		//DB�� ""�� ���� �������� 0���� ���� �ʱ�ȭ�Ѵ�.
		if("".equals(downCnt)) downCnt = "0";
			try{
				if("1".equals(isCoupon)){
					// ������ ���
					if("CONTENTS".equals(sort)){
						System.out.println("11111111111111111");
						// �ٿ�ε� Ƚ��
						// dgggg
						// affff
						ContentVO contentVO = new ContentVO();
						contentVO.setNonmemDownCnt(String.valueOf(Integer.parseInt(downCnt) +1 ));
						contentsService.updateContentInfo(contentVO, Integer.parseInt(downSeq));
					}else if("APP".equals(sort)){
						System.out.println("222222222222222222");
						AppVO appVO = new AppVO();
						appVO.setNonmemDownCnt(String.valueOf(Integer.parseInt(downCnt) +1 ));
						appService.updateAppInfo(appVO, Integer.parseInt(downSeq));
						System.out.println("@@@@@@@@@@@@@@@@@@@");
					}
				}else{
					// ������ �ƴ� ���
					if("CONTENTS".equals(sort)){
						// �ٿ�ε� Ƚ��
						System.out.println("33333333333333333333");
						ContentVO contentVO = new ContentVO();
						contentVO.setMemDownCnt(String.valueOf(Integer.parseInt(downCnt) +1 ));
						contentsService.updateContentInfo(contentVO, Integer.parseInt(downSeq));
					}else if("APP".equals(sort)){
						System.out.println("444444444444444444444");
						AppVO appVO = new AppVO();
						appVO.setMemDownCnt(String.valueOf(Integer.parseInt(downCnt) +1 ));
						appService.updateAppInfo(appVO, Integer.parseInt(downSeq));
					}
				}
			}catch(Exception e){
				// �����϶� 0
				e.printStackTrace();
				return 0;
			}
		// �����϶� 1
		return 1;
	}

	private String paramSet(HttpServletRequest req, String targetName) {
		String value = "";
		value = (null == req.getParameter(targetName)) ? "" : "" + req.getParameter(targetName);

		return value;
	}

	/* �ش� Reuqest �ּ� down/download_proc.html�� 
	 * RequestedInAppJsonVO ����� ������ �Ǿ������Ƿ�, �� down/download_proc.html�� �ٲٷ��� 
	 * RequestedInAppJsonVO ���� ������ �ִ� String���� �ٲ���ߵ˴ϴ�. - �ٲ� 2015-05-14 �ȹٲ� �൵��.
	 * */
	@RequestMapping(value = "down/download_proc.html", method = {RequestMethod.GET, RequestMethod.POST})
	public String app_regist(HttpSession session, HttpServletRequest req, ModelMap modelMap) {

		//����� ���� 
		/*
		 * ��� , ���ϸ�, ������ȣ, ������̵�, 
		 * 
		 */

		//�α���������, ��������, seq, Ÿ��,
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		GrantedAuthority element = authorities.iterator().next();
		String authority = element.getAuthority();
		myUserDetails activeUser = null;

		String returnUrl = "/inc/dummy";
		//message : �� �� ��
		String msg = messageSource.getMessage("down.control.001", null, localeResolver.resolveLocale(req));
		String errorType = "close";

		String path = "";
		String orgName = "";
		String saveName = "";

		String downSeq, downGubun, downKaU, downVer, couponNum, ostype, couponOrGeneral;
		downSeq = downGubun = downKaU = downVer = couponNum= ostype = couponOrGeneral = "";

		downSeq   = paramSet(req, "downSeq");
		downGubun    = paramSet(req, "DownGubun");
		downKaU  	= paramSet(req, "DownKaU");
		downVer	    = paramSet(req, "DownVer");
		couponNum	    = paramSet(req, "coupon_Num");
		ostype      = paramSet(req, "ostype");
		couponOrGeneral = paramSet ( req, "couponOrGeneral");

		if( !"INAPP".equals(downGubun) && !"PROVISION".equals(downGubun) && !"TEMPLATE".equals(downGubun))
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	

		try{
			if((downSeq==null||"".equals(downSeq))&&(downGubun==null||"".equals(downGubun))){
				//message : �߸��� �����Դϴ�.
				msg = messageSource.getMessage("down.control.002", null, localeResolver.resolveLocale(req));
				throw new Exception();
			}
			if("APP".equals(downGubun)){
				//���ΰ��
				// ���� ���� �𸣰ڰ�~~~
				if(couponNum!=null&&!"".equals(couponNum)){
					//�����ٿ�ε��� ���
				}else{
					//�׷� ����Ʈ���� �޴� ���
				}
			}else if("CONTENTS".equals(downGubun)){
				//����Ʈ�� ���
				ContentVO cvo = new ContentVO();
				ContentVO cvo1 = new ContentVO();
				if(couponNum!=null&&!"".equals(couponNum)){
					//�����ٿ�ε��� ���
					cvo.setContentsSeq(Integer.parseInt(downSeq));
					cvo.setCouponNum(couponNum);
					cvo.setCouponGb("1");
					cvo1 = contentsService.selectByUltimateCondition(cvo);
					if(cvo1.getContentsSeq()==null){
						//message : �߸��� �����Դϴ�.
						msg = messageSource.getMessage("down.control.002", null, localeResolver.resolveLocale(req));
						throw new Exception();
					}
					if(cvo1.getNonmemDownGb()!=null&&"1".equals(cvo1.getNonmemDownGb())){
						int cnt = Integer.parseInt(StringUtil.null2string(cvo1.getNonmemDownCnt(), "0"));
						int amt = Integer.parseInt(cvo1.getNonmemDownAmt());
						if(!(amt>cnt)){
							//message : �ٿ�ε� Ƚ���� �ʰ��Ǿ����ϴ�. �����ڿ��� �������ּ���.
							msg = messageSource.getMessage("down.control.003", null, localeResolver.resolveLocale(req));
							throw new Exception();
						}
					}
				}else{
					//�׷� ����Ʈ���� �޴� ���
					//�����ٿ�ε尡 �ƴ� ���
					cvo.setContentsSeq(Integer.parseInt(downSeq));
					cvo1 = contentsService.selectByUltimateCondition(cvo);
						if(cvo1.getContentsSeq()==null){
							//message : �߸��� �����Դϴ�.
							msg = messageSource.getMessage("down.control.002", null, localeResolver.resolveLocale(req));
							throw new Exception();
						}
					if(cvo1.getDistrGb()!=null&&"1".equals(cvo1.getDistrGb())){
						if(activeUser==null||activeUser.getMemberVO()==null){
							//message : ȸ�������Դϴ�.
							msg = messageSource.getMessage("down.control.006", null, localeResolver.resolveLocale(req));
							throw new Exception();
						}else{
							if("1".equals(activeUser.getMemberVO().getCompanyGb())){
								if(!cvo1.getCompanySeq().equals(activeUser.getMemberVO().getCompanySeq())){
									//message : ������ �ٸ��� �ʽ��ϴ�.
									msg = messageSource.getMessage("down.control.007", null, localeResolver.resolveLocale(req));
									throw new Exception();
								}
							}else if("2".equals(activeUser.getMemberVO().getCompanyGb())){
								if(!cvo1.getRegUserSeq().equals(activeUser.getMemberVO().getUserSeq())){
									//message : ������ �ٸ��� �ʽ��ϴ�.
									msg = messageSource.getMessage("down.control.007", null, localeResolver.resolveLocale(req));
									throw new Exception();
								}
							}
						}
					}else{
						if(cvo1.getCouponGb()!=null&&"1".equals(cvo1.getCouponGb())){
							//message : ������ �ٸ��� �ʽ��ϴ�.
							msg = messageSource.getMessage("down.control.007", null, localeResolver.resolveLocale(req));
							throw new Exception();
						}
					}
					if(cvo1.getMemDownGb()!=null&&"1".equals(cvo1.getMemDownGb())){
						int cnt = Integer.parseInt(StringUtil.null2string(cvo1.getMemDownCnt(), "0"));
						int amt = Integer.parseInt(cvo1.getMemDownAmt());
						if(!(amt>cnt)){
							//message : �ٿ�ε� Ƚ���� �ʰ��Ǿ����ϴ�. �����ڿ��� �������ּ���.
							msg = messageSource.getMessage("down.control.003", null, localeResolver.resolveLocale(req));
							throw new Exception();
						}
					}else if(cvo1.getMemDownGb()!=null&&"2".equals(cvo1.getMemDownGb())){
						Date sdt = cvo1.getMemDownStartDt();
						Date edt = cvo1.getMemDownEndDt();
						if(!(sdt.before(new Date())&&edt.after(new Date()))){
							//message : �ٿ�ε� �Ⱓ�� ��ȿ���� �ʽ��ϴ�.
							msg = messageSource.getMessage("down.control.004", null, localeResolver.resolveLocale(req));
							throw new Exception();						
						}
					}
				}
				//req.setAttribute(arg0, arg1);
				path = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(req)) + messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(req));
				orgName = cvo1.getUploadOrgFile();
				saveName = cvo1.getUploadSaveFile();
				modelMap.put("filepath", path);
				modelMap.put("orgFileName", orgName);
				modelMap.put("saveFileName", saveName);
				return "/inc/download";
			}else if("INAPP".equals(downGubun)){
				if("COUPON".equals(couponOrGeneral)){
					InappVO inappVO = inAppService.findByCustomInfo("couponNum",couponNum);
					path = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(req)) + messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(req));
					orgName = inappVO.getInappOrgFile();
					saveName = inappVO.getInappSaveFile();

					modelMap.put("filepath", path);
					modelMap.put("orgFileName", orgName);
					modelMap.put("saveFileName", saveName);
					return "/inc/download";
				}else{
					InappVO inappVO = inAppService.findByCustomInfo("inappSeq", Integer.parseInt(paramSet(req, "downSeq")));
					path = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(req)) + messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(req));
					orgName = inappVO.getInappOrgFile();
					saveName = inappVO.getInappSaveFile();

					modelMap.put("filepath", path);
					modelMap.put("orgFileName", orgName);
					modelMap.put("saveFileName", saveName);
				}
				return "/inc/download";
			}else if("PROVISION".equals(downGubun)){
				ProvisionVO provisionVO = provisionService.findByCustomInfo( "provSeq", Integer.parseInt(paramSet(req, "downSeq")));
				path = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(req)) + messageSource.getMessage("file.path.provision.file", null, localeResolver.resolveLocale(req));
				orgName = provisionVO.getDistrProfileName();
				saveName = provisionVO.getDistrProfileSaveName();

				modelMap.put("filepath", path);
				modelMap.put("orgFileName", orgName);
				modelMap.put("saveFileName", saveName+".mobileprovision");
				return "/inc/download";
			}else if("TEMPLATE".equals(downGubun)){
				TemplateVO templateVO = templateService.selectByTempId(Integer.parseInt(paramSet(req, "downSeq")));
				path = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(req)) + messageSource.getMessage("file.path.template.file", null, localeResolver.resolveLocale(req));
				orgName = templateVO.getUploadOrgFile();
				saveName = templateVO.getUploadSaveFile();

				modelMap.put("filepath", path);
				modelMap.put("orgFileName", orgName);
				modelMap.put("saveFileName", saveName);
				return "/inc/download";
			}else if("APPDOWNLOAD".equals(downGubun)){
				AppVO appVO = appService.selectById(Integer.parseInt(paramSet(req, "downSeq")));
				if("Android".equals(ostype)){
					path = messageSource.getMessage("file.path.app.file.android", null, localeResolver.resolveLocale(req));
					orgName = appVO.getFileName()+downVer+".apk";
					saveName = appVO.getFileName()+downVer+".apk";
				}else{
/*				20180518 : lsy - 권한 체계 변경	
					if("ROLE_COMPANY_DISTRIBUTOR".equals(authority)){
						${filePath}${DownKaU}${DownVer}/${fileName}${DownVer}.apk"
						path = messageSource.getMessage("file.path.app.file.iOSPath", null, localeResolver.resolveLocale(req))+downKaU+downVer+"/";
						orgName = appVO.getFileName()+".ipa";
						saveName = appVO.getFileName()+".ipa";
					}else{
						path = messageSource.getMessage("file.path.app.file.iOSPath", null, localeResolver.resolveLocale(req))+downKaU+downVer+"/";
						orgName = appVO.getFileName()+downVer+".ipa";
						saveName = appVO.getFileName()+downVer+".ipa";
					}
*/
					path = messageSource.getMessage("file.path.app.file.iOSPath", null, localeResolver.resolveLocale(req))+downKaU+downVer+"/";
					orgName = appVO.getFileName()+downVer+".ipa";
					saveName = appVO.getFileName()+downVer+".ipa";
				}
				modelMap.put("filepath", path);
				modelMap.put("orgFileName", orgName);
				modelMap.put("saveFileName", saveName);

				return "redirect:"+path+orgName;
			}
			//message : �߸��� �����Դϴ�.
			msg = messageSource.getMessage("down.control.002", null, localeResolver.resolveLocale(req));
			throw new Exception();
		}catch(Exception e){
			e.printStackTrace();
			modelMap.addAttribute("msg", msg);
			modelMap.addAttribute("type", errorType);
			return returnUrl;
		}
    }	
}