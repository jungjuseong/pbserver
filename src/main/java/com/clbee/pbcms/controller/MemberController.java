package com.clbee.pbcms.controller;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clbee.pbcms.service.CompanyService;
import com.clbee.pbcms.service.GroupService;
import com.clbee.pbcms.service.GroupViewMenuService;
import com.clbee.pbcms.service.LicenseService;
import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.util.ResourceNotFoundException;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.CompanyVO;
import com.clbee.pbcms.vo.GroupUserVO;
import com.clbee.pbcms.vo.LicenseList;
import com.clbee.pbcms.vo.LicenseSubList;
import com.clbee.pbcms.vo.LicenseSubVO;
import com.clbee.pbcms.vo.LicenseVO;
import com.clbee.pbcms.vo.MemberVO;

@Controller
@RequestMapping("/*.html")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired 
	CompanyService companyService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	GroupService groupService;

	//20180515 : lsy - GroupViewMenu Create
	@Autowired
	GroupViewMenuService groupViewMenuService;
	
	//20180905 : lsy - license menu develop
	@Autowired
	LicenseService licenseService;

	@RequestMapping(value = "member/join/ok.html", method = RequestMethod.GET)
	public ModelAndView memberJoinOk( HttpServletRequest request ) {		
		ModelAndView mav = new ModelAndView();	
		Random random = new Random();
		
		String validId = request.getParameter("validId");
		MemberVO memberVO = memberService.findByCustomInfo("emailChkSession", validId);
//		System.out.println(validId);
		
		int limitUser =  Integer.parseInt(messageSource.getMessage("limit.user.count", null, localeResolver.resolveLocale(request)));

		if(memberVO == null){
			mav.addObject("msg", messageSource.getMessage("app.control.006", null, localeResolver.resolveLocale(request)));
			mav.addObject("type", "href");
			mav.addObject("url", "/index.html");
			mav.setViewName("inc/dummy");
			return mav;
		}
		int permitUser = memberService.selectCountWithPermisionUserByCompanySeq(memberVO.getCompanySeq());
		if( permitUser >= limitUser ){
			mav.setViewName("07_member/member_join_ok");
			mav.addObject("emailChk", false);
			return mav;
		}
		int result = memberService.verifyIfExists("emailChkSession", validId);
		
		switch (result){
			case 0 : 
				System.out.println("i'm in case 0");
				throw new ResourceNotFoundException();
			case 1 :
				System.out.println("i'm in case 1");
				
				if("1".equals(memberVO.getCompanyGb())){ 
					int companySeq = memberVO.getCompanySeq();
					CompanyVO companyVO = companyService.findByCustomInfo("companySeq", companySeq);
					memberVO.setUserStatus("4");
					companyVO.setCompanyStatus("4");
					memberVO.setEmailChkDt(new Date());
					memberVO.setEmailChkGb("Y");
					memberVO.setEmailChkSession(changeSHA256(String.valueOf(System.currentTimeMillis()+random.nextInt())));
					memberService.updateMemberInfo(memberVO, memberVO.getUserSeq());
					companyService.updateCompanyInfo(companyVO, companySeq);
				}else {
					memberVO.setUserStatus("4");
					memberVO.setEmailChkDt(new Date());
					memberVO.setEmailChkGb("Y");
					
					memberVO.setEmailChkSession(changeSHA256(String.valueOf(System.currentTimeMillis()+random.nextInt())));
					memberService.updateMemberInfo(memberVO, memberVO.getUserSeq());
				}
				mav.setViewName("07_member/member_join_ok");
				mav.addObject("emailChk", true);
				return mav;
			case 2 : 
				System.out.println("There are two value duplicated with email_chk_session");
				throw new ResourceNotFoundException();
		}
		mav.setViewName("07_member/member_join_ok");
		mav.addObject("emailChk", true);
		return mav;
	}

	@RequestMapping(value = "member/join.html", method = RequestMethod.GET)
	public ModelAndView home( HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		
		List<GroupUserVO> groupList = groupService.getSelectListGroup(0);

		mav.addObject("groupList", groupList);
		mav.setViewName("07_member/member_join");
		
		return mav;
	}

	@RequestMapping(value = "member/join.html", method = RequestMethod.POST)
	public String join( MemberVO memberVO, CompanyVO companyVO, HttpServletRequest request ) {
		
		/*request.getParameter(arg0)*/
		memberVO.setEmailChkSession(changeSHA256(memberVO.getUserId()));
		memberVO.setUserPw(changeSHA256(memberVO.getUserPw()));
		memberVO.setRegIp(request.getRemoteAddr());
		if("1".equals(memberVO.getCompanyGb())){
			int companySeq = companyService.insertCompanyInfoWithProcedure(companyVO);
			memberVO.setCompanySeq(companySeq);
		}
		memberVO.setRegDt(new Date());
		memberService.addMember(memberVO);
		System.out.println("sending Email@@@@@@@@@@@@@@@@@@");
		
		String from=messageSource.getMessage("send.email.ID", null, localeResolver.resolveLocale(request));
		//message : PageCreator ���� �����Դϴ�.
		String subject = messageSource.getMessage("member.control.007", null, localeResolver.resolveLocale(request));
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setTo(memberVO.getEmail());
				//message : �ش� URL�� �����Ͻø� ���� ������ �Ϸ� �˴ϴ�.
				messageHelper.setText(messageSource.getMessage("member.control.008", null, localeResolver.resolveLocale(request)) +"\n"+ "http://" + messageSource.getMessage("basic.Info.IP", null, localeResolver.resolveLocale(request))+"/member/join/ok.html?validId="+changeSHA256(memberVO.getUserId()));
				messageHelper.setFrom(from);
				messageHelper.setSubject(subject); 
				mailSender.send(message);
			} catch(Exception e){
				System.out.println(e);
			}
		return "redirect:/index.html";
	}
	
	@RequestMapping(value={"/member/userIdValidation.html"}, method=RequestMethod.POST)
	public @ResponseBody int userIdValidation( String inputUserId ){
		return memberService.verifyIfExists("userId", inputUserId);
	}
	
	
	@RequestMapping(value={"/member/emailValidation.html"}, method=RequestMethod.POST)
	public @ResponseBody int emailValidation( String inputEmail ){
		return memberService.verifyIfExists("email", inputEmail);
	}
	
	@RequestMapping(value={"/userStatusValid.html"}, method=RequestMethod.POST)
	public @ResponseBody int userStatusValid( String userId, String userPw ){
		
	/*case 1 : 
		//message : Ż���� �����Դϴ�.
		alert("<spring:message code='page.index.009' />");
		return;
		break;
	case 2 :
		//message : �̿� ������ �����Դϴ�.
		alert("<spring:message code='page.index.010' />");
		return;
		break;
	case 3 : 
		//message : ���� Ż��� �����Դϴ�.
		alert("<spring:message code='page.index.011' />");
		return;
		break;
	case 4 :
		$('#log_f').submit();
		return;
		break;
	case 5 :
		//message : ���� ������ ��� �����մϴ�.
		alert("<spring:message code='page.index.012' />");
		return;
		break;
	case 6 :
		//message : ���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���ϼ���.
		alert("<spring:message code='page.index.013' />");
		return;
		break;
		//message : ��ȿ�Ⱓ�� ���� �����Դϴ�.
	case 7:
		alert("<spring:message code='page.index.016' />");
		break;*/
		
		
		MemberVO memberVO = memberService.findByUserName(userId);
		if(memberVO == null) {
			/*	���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���ϼ���.*/
			
			return 6;
		}else if(!"".equals(userId) && !"".equals(userPw)) {
			int loginResult = memberService.logInVerify(userId, changeSHA256(userPw));
			if( loginResult < 0) return 6;
			else if( loginResult == 1){
				if("4".equals(memberVO.getUserStatus())) {
					/*if("1".equals(memberVO.getLoginStatus())){
						
						if(!"CMS".equals(memberVO.getLoginDeviceuuid())){
							return 8;
						}else{
							MemberVO updatedVO = new MemberVO();
							updatedVO.setLoginDt(new Date());
							updatedVO.setUserStartDt(memberVO.getUserStartDt());
							updatedVO.setUserEndDt(memberVO.getUserEndDt());
							updatedVO.setLoginDeviceuuid("CMS");
							updatedVO.setLoginStatus("1");
							memberService.updateMemberInfo(updatedVO, memberVO.getUserSeq());
							return 4;
						}
						
					}*/
					MemberVO updatedVO = new MemberVO();
					updatedVO.setLoginDt(new Date());
					updatedVO.setUserStartDt(memberVO.getUserStartDt());
					updatedVO.setUserEndDt(memberVO.getUserEndDt());
					//updatedVO.setLoginDeviceuuid("CMS");
					//updatedVO.setLoginStatus("1");
					memberService.updateMemberInfo(updatedVO, memberVO.getUserSeq());
					return 4;
				}	
				else {
					return Integer.parseInt(memberVO.getUserStatus());
				}
			}else if( loginResult == 2) {
				return 7;
			}else {
				return Integer.parseInt(memberVO.getUserStatus());
			}
		}else {
			return 6;
		}
	}

	@RequestMapping(value="mypage/password.html", method=RequestMethod.GET)
	public ModelAndView mypagePasswordGET( String userId ){
		ModelAndView modelAndView = new ModelAndView();
		
		//20180516 : lsy - temp if/else
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
		
		modelAndView.setViewName("06_mypage/mypage_password");
		return modelAndView;
	}

	@RequestMapping(value="mypage/modify.html", method=RequestMethod.POST)
	public ModelAndView mypageModifyPOST( HttpServletRequest request,  MemberVO formVO, CompanyVO formComVO, String modify_gb ){
		ModelAndView modelAndView = new ModelAndView();
		
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if("modify_password".equals(modify_gb)){	
			if(activeUser.getPassword().equals(changeSHA256(formVO.getUserPw()))){
				/*modelAndView.addObject("ReConfirmPassword", userPw);*/
				MemberVO dbVOPassword  = memberService.findByCustomInfo( "userId", activeUser.getUsername() );
				CompanyVO companyVO = companyService.findByCustomInfo("companySeq", activeUser.getMemberVO().getCompanySeq());
				modelAndView.addObject("companyVO", companyVO);
				modelAndView.addObject("memberVO", dbVOPassword);
				modelAndView.setViewName("06_mypage/mypage_modify");
			}else {
				modelAndView.addObject("validPassword", false);
				modelAndView.setViewName("/inc/dummy");
			}
		}else{
			
			formVO.setUserPw(changeSHA256(formVO.getUserPw()));
			
			if("5".equals(formVO.getUserStatus())) {
				formVO.setEmailChkSession(changeSHA256(formVO.getUserId()));
				String from=messageSource.getMessage("send.email.ID", null, localeResolver.resolveLocale(request));

				String subject=messageSource.getMessage("member.control.007", null, localeResolver.resolveLocale(request));
					try {
						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
						messageHelper.setTo(formVO.getEmail());

						messageHelper.setText(messageSource.getMessage("member.control.008", null, localeResolver.resolveLocale(request)) + "\n"+ "http://"+messageSource.getMessage("basic.Info.IP", null, localeResolver.resolveLocale(request)) + "/member/join/ok.html?validId="+changeSHA256(formVO.getUserId()));
						messageHelper.setFrom(from);
						messageHelper.setSubject(subject); 
						mailSender.send(message);
					} catch(Exception e){
						System.out.println(e);
				}
			}
			
			
			System.out.println("formComVO.zipCode : " + formComVO.getZipcode());
			formVO.setChgDt(new Date());
			formVO.setChgIp(request.getRemoteAddr());
			memberService.updateMemberInfo( formVO, formVO.getUserSeq());
			
			MemberVO dbVOModify = memberService.findByCustomInfo( "userId", formVO.getUserId() );
			companyService.updateCompanyInfo(formComVO, dbVOModify.getCompanySeq());
			CompanyVO dbComVOModify = companyService.findByCustomInfo("companySeq", dbVOModify.getCompanySeq());
			modelAndView.addObject("modifySuccess", true);
			modelAndView.addObject("companyVO", dbComVOModify);
			modelAndView.addObject("memberVO", dbVOModify);
			modelAndView.setViewName("06_mypage/mypage_modify");
		}
		
		//20180516 : lsy - temp if/else
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
	
	@RequestMapping(value="mypage/modifyCustom.html", method=RequestMethod.POST)
	public @ResponseBody String mypaOST( String userPw ){
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return activeUser.getMemberVO().getEmail();
	}
	
	//??
	@RequestMapping(value="mypage/modify.html", method=RequestMethod.GET)
	public String mypagePOST( String userPw ){
		/* �ùٸ��� ���� �����Դϴ�. */
		
		throw new ResourceNotFoundException();
		/*return "06_mypage/mypage_modify";*/
	}

	//??
	@RequestMapping(value="mypage/withDrawal.html", method=RequestMethod.GET)
	public String mypageWithDrawal( String userPw ){
		/* �ùٸ��� ���� �����Դϴ�. */
		
		return "06_mypage/mypage_withdrawal";
		/*return "06_mypage/mypage_modify";*/
	}

	@RequestMapping(value="mypage/withDrawal.html", method=RequestMethod.POST)
	public @ResponseBody int mypageWithDrawalPOST( String userSeq, String companySeq ){
		
		int intUserSeq = Integer.parseInt(userSeq);
		int intCompanySeq = Integer.parseInt(companySeq);
		int companyResult = 1;
		
		if(intCompanySeq != 0)/* ���ȸ���̶�� �ǹ� */{
			CompanyVO updateComVO = new CompanyVO();
			updateComVO.setCompanyStatus("1"); // Ż��
			updateComVO.setWithdrawalDt(new Date());
			companyResult = companyService.updateCompanyInfo(updateComVO, intCompanySeq);
		}
		
		MemberVO updateMemVO = new MemberVO();
		updateMemVO.setUserStatus("1");	// Ż���
		updateMemVO.setWithdrawalDt(new Date());
		int memberResult = memberService.updateMemberInfo(updateMemVO, intUserSeq);

		/* 탈퇴처리 -> 삭제되는것 아님 */
		if(companyResult == 1 && memberResult == 1) {
			return 1;
		}else {
			return 0;
		}
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
	
	@RequestMapping(value = "/findid.html", method = RequestMethod.POST)
	public @ResponseBody String findId( MemberVO memberVO, HttpServletRequest request ) {
		
		String firstName = request.getParameter("fm_first_name");
		String lastName  = request.getParameter("fm_last_name");
		String email     = request.getParameter("fm_email1");
		
		memberVO.setFirstName(firstName);
		memberVO.setLastName(lastName);
		memberVO.setEmail(email);
		
		MemberVO memberRow = null;
		Integer memCnt = 0;
		
		memCnt = memberService.selectMemberCount(memberVO);	
		memberRow = memberService.selectMemberSuccessYn(memberVO);		
		
		if(memCnt == 1){			
			String from=messageSource.getMessage("send.email.ID", null, localeResolver.resolveLocale(request));
			//message : ��û�Ͻ� ���̵� �Դϴ�.
			String subject=messageSource.getMessage("member.control.001", null, localeResolver.resolveLocale(request));			
			//Random random = new Random();
			//random.nextInt();
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setTo(memberRow.getEmail());
				//message : ���� ��û�Ͻ� ���̵��
				//message : �Դϴ�. �����մϴ�.
				messageHelper.setText(memberRow.getLastName()+memberRow.getFirstName()+messageSource.getMessage("member.control.002", null, localeResolver.resolveLocale(request))+memberRow.getUserId()+messageSource.getMessage("member.control.003", null, localeResolver.resolveLocale(request)) );
				messageHelper.setFrom(from);
				messageHelper.setSubject(subject); 
				mailSender.send(message);
			} catch(Exception e){
				System.out.println(e);
			}
			
			return "successTrue";
		} else {
			// ���� ������
			return "successFalse";
		}		
	}
	
	
	@RequestMapping(value = "/findpwd.html", method = RequestMethod.POST)
	public @ResponseBody String findPwd( MemberVO memberVO, HttpServletRequest request ) {
		String ranStr   = null;
		String userId  = request.getParameter("fm_user_id");
		String email    = request.getParameter("fm_email2");
		
		ranStr =  new RandomStringBuilder()
					.putLimitedChar(RandomStringBuilder.ALPHABET)
					.putLimitedChar(RandomStringBuilder.NUMBER)
					.putExcludedChar(RandomStringBuilder.SPECIAL)
					.setLength(12).build();
		
		memberVO.setUserId(userId);
		memberVO.setEmail(email);
		memberVO.setUserPw(ranStr);
		
		MemberVO memberRow = null;
		Integer memCnt = 0;
		
		memCnt = memberService.selectMemberCount_(memberVO);	
		memberRow = memberService.selectMemberSuccessYn_(memberVO);
		
		//System.out.println(memCnt+"=====");
		if(memCnt == 1){			
			String from=messageSource.getMessage("send.email.ID", null, localeResolver.resolveLocale(request));
			//message : ��û�Ͻ� ��й�ȣ �Դϴ�.
			String subject=messageSource.getMessage("member.control.004", null, localeResolver.resolveLocale(request));
			
			try {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setTo(memberRow.getEmail());
				//message : ���� ��û�Ͻ� ��й�ȣ��
				//message : �Դϴ�. �����մϴ�.
				messageHelper.setText(memberRow.getLastName()+memberRow.getFirstName()+messageSource.getMessage("member.control.005", null, localeResolver.resolveLocale(request))+ranStr+messageSource.getMessage("member.control.006", null, localeResolver.resolveLocale(request)) );
				messageHelper.setFrom(from);
				messageHelper.setSubject(subject); 
				mailSender.send(message);
				memberService.updateMemberPw(memberVO);
			} catch(Exception e){
				System.out.println(e);
			}
			// ���� ������
			return "True!";
		} else {
			// ���� ������
			return "False!";
		}		
	}

	@RequestMapping(value="my/license.html", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView mypageLicenseManagement( LicenseList listLicense, LicenseSubList licenseUseDevice, HttpServletRequest request, HttpSession session ){
		ModelAndView modelAndView = new ModelAndView();
		
		myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		
		for(GrantedAuthority auth : authentication.getAuthorities()) {
			if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
				Map<String, Object> menuList = new HashMap<String, Object>();
				menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
				
				modelAndView.addObject("menuLarge", menuList.get("menuLarge"));
			}
		}
		
		int useLicense = licenseService.checkUseLicense(activeUser.getMemberVO().getUserSeq());
		
		if(useLicense == 0) {
			modelAndView.setViewName("06_mypage/my_license_regist");
		}else if(useLicense == 1) {
			listLicense = licenseService.selectMyList(listLicense, activeUser.getMemberVO().getUserSeq());
			modelAndView.addObject("licenseList", listLicense);
			
			if(request.getParameter("currentPage") == null || licenseUseDevice.getCurrentPage() == 0) {
				licenseUseDevice.setCurrentPage(1);
			}
			licenseUseDevice.setLicenseSeq(listLicense.getList().get(0).getLicenseSeq());
			licenseUseDevice = licenseService.selectLicenseUseDevice(licenseUseDevice);
			
			modelAndView.addObject("searchValue", licenseUseDevice.getSearchValue());
			modelAndView.addObject("currentPage", licenseUseDevice.getCurrentPage());
			modelAndView.addObject("licenseUseDevice", licenseUseDevice);
			
			modelAndView.setViewName("06_mypage/my_license_list");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="my/deleteLicenseUseDevice.html", method=RequestMethod.POST)
	public @ResponseBody int mypageDeleteLicenseUseDevicePost( HttpServletRequest request, HttpSession session ){
		return licenseService.deleteLicenseUseDevice(Integer.parseInt(request.getParameter("licensesubSeq")));
	}

	@RequestMapping(value="my/licenseRegistCheck.html", method=RequestMethod.POST)
	public @ResponseBody int mypageLicenseRegistCheck( HttpServletRequest request, HttpSession session ){
		return licenseService.licenseRegistCheck(request.getParameter("licenseNum"));
	}

	@RequestMapping(value="my/licenseRegist.html", method=RequestMethod.POST)
	public ModelAndView mypageLicenseRegist( HttpServletRequest request, HttpSession session ){
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			
			if(request.getParameter("licenseSeq") != null && !request.getParameter("licenseSeq").equals("")) {
				licenseService.licenseExpire(Integer.parseInt(request.getParameter("licenseSeq")));
			}

			licenseService.licenseRegist(activeUser.getMemberVO().getUserSeq(), request.getParameter("licenseNum"));
			
			for(GrantedAuthority auth : authentication.getAuthorities()) {
				if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
					Map<String, Object> menuList = new HashMap<String, Object>();
					menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
					
					modelAndView.addObject("menuLarge", menuList.get("menuLarge"));
				}
			}
			
			modelAndView.setViewName("redirect:/my/license.html");	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value="my/licenseRenew.html", method=RequestMethod.GET)
	public ModelAndView mypageLicenseRenewGet( HttpServletRequest request, HttpSession session ){
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			myUserDetails activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			
			for(GrantedAuthority auth : authentication.getAuthorities()) {
				if(auth.getAuthority().equals("ROLE_COMPANY_MEMBER") || auth.getAuthority().equals("ROLE_INDIVIDUAL_MEMBER") || auth.getAuthority().equals("ROLE_USER")) {
					Map<String, Object> menuList = new HashMap<String, Object>();
					menuList = groupViewMenuService.selectViewMenu(activeUser.getMemberVO().getGroupName(), menuList);
					
					modelAndView.addObject("menuLarge", menuList.get("menuLarge"));
				}
			}
			
			List<LicenseVO> license = null;
			license = licenseService.selectLicenseForRenew(activeUser.getMemberVO().getUserSeq());

			modelAndView.addObject("licenseSeq", license.get(0).getLicenseSeq());
			modelAndView.setViewName("06_mypage/my_license_regist");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	public class RandomStringBuilder {
	     
	    public static final String NUMBER = "0123456789";
	    public static final String ALPHABET_LOWER_CASE = "abcdefghijkmnlopqrstuvwxyz";
	    public static final String ALPHABET_UPPER_CASE = "ABCDEFGHIJKMNLOPQRSTUVWXYZ";
	    public static final String ALPHABET = ALPHABET_LOWER_CASE + ALPHABET_UPPER_CASE;
	    public static final String SPECIAL = "~!@#$%^&*()_+{}|\\\"`;:'<>?,./=-[]";
	     
	     
	    private HashSet mExcludedCharSet = new HashSet(); 
	    private ArrayList mLimitCharList = new ArrayList();
	             
	    int mLength = 32;
	     
	    public String build() {
	        return generateRandomString(mLength);
	    }
	     
	    public RandomStringBuilder setLength(int length) {
	        mLength = length;
	        return this;
	    }
	     
	    public int getLength() {
	        return mLength;
	    }
	     
	    public RandomStringBuilder putExcludedChar(char excluded) {
	        mExcludedCharSet.add(excluded);
	        return this;
	    }
	     
	    public RandomStringBuilder putExcludedChar(char[] excludedList) {
	        for(char excluded : excludedList) 
	                putExcludedChar(excluded);
	        return this;
	    }
	     
	    public RandomStringBuilder putExcludedChar(String excluded) { 
	                putExcludedChar(excluded.toCharArray());
	        return this;
	    }
	     
	    public RandomStringBuilder putLimitedChar(char limited) {
	        mLimitCharList.add(limited);
	        return this;
	    }
	     
	    public RandomStringBuilder putLimitedChar(char[] limitedList) {
	        for(char limited : limitedList) 
	                putLimitedChar(limited);
	        return this;
	    }
	     
	    public RandomStringBuilder putLimitedChar(String limited) {
	        putLimitedChar(limited.toCharArray());
	        return this;
	    }
	     
	    public boolean removeExcluded(char excluded) {
	        return mExcludedCharSet.remove(excluded);
	    }
	     
	    public boolean removeLimitedChar(char limited) {
	        return mLimitCharList.remove((Character)limited);
	    }
	     
	    public void clearExcluded() {
	        mExcludedCharSet.clear();
	    }
	    public void clearLimited() {
	        mLimitCharList.clear();
	    }
	     
	     
	    /**
	     * ���� ���ڿ� ����.
	     * @param length ���ڿ� ����
	     * @return ���� ���ڿ�
	     */
	    public String generateRandomString(int length) {
	        boolean runExcludeChar = !isExcludedCharInLimitedChar();            
	        StringBuffer strBuffer = new StringBuffer(length);
	        Random rand = new Random(System.nanoTime());
	        for(int i = 0; i < length; ++i ) {
	            char randomChar = makeChar(rand);
	            if(runExcludeChar)
	                randomChar = excludeChar(rand, randomChar);
	            strBuffer.append(randomChar);
	        }
	        return strBuffer.toString();
	    }
	     
	    private boolean isExcludedCharInLimitedChar() {
	        return mExcludedCharSet.containsAll(mLimitCharList);
	    }
	     
	    private char excludeChar(Random rand, char arg) {
	        while(mExcludedCharSet.contains(arg)) {
	            arg = makeChar(rand);
	        }
	        return arg;
	    }
	    private char makeChar(Random rand) {
	        if(mLimitCharList.isEmpty())
	            return (char)(rand.nextInt(94) + 33);
	        else
	            return (Character) mLimitCharList.get(rand.nextInt(mLimitCharList.size()));
	    }
	}		
	
}