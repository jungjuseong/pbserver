package com.clbee.pbcms.controller;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.clbee.pbcms.service.AppService;
import com.clbee.pbcms.service.DeviceService;
import com.clbee.pbcms.service.InAppCategoryService;
import com.clbee.pbcms.service.InAppService;
import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.util.AuthenticationException;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.myUserDetails;
import com.clbee.pbcms.vo.MemberVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {	
	@Autowired
	InAppCategoryService inAppCategoryService;
	
	@Autowired
	AppService appService; 
	
	@Autowired
	InAppService inAppService; 

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	SessionRegistry sessionRegistry;

	public static void main(String[] args) throws PropertyVetoException, IOException {
		int a;
		
		
/*		ApnsService service =
			    APNS.newService()
			    .withCert("/path/to/certificate.p12", "MyCertPassword")
			    .withSandboxDestination()
			    .build();
		
		
		String payload = APNS.newPayload().alertBody("Can't be simpler than this!").build();
		String token = "fedfbcfb....";
		service.push(token, payload);
		
		Map<String, Date> inactiveDevices = service.getInactiveDevices();
		for (String deviceToken : inactiveDevices.keySet()) {
		    Date inactiveAsOf = inactiveDevices.get(deviceToken);
		}*/
	}
	
	@RequestMapping(value = "index.html", method = RequestMethod.GET)
	public String home( HttpServletRequest request, HttpSession session  ) {
		System.out.println(request.getLocale());
		System.out.println(Locale.getDefault());
		System.out.println(localeResolver.resolveLocale(request));
		/*List<Object> userSessions = sessionRegistry.getAllPrincipals();
		
		
		for( int i =0; i <userSessions.size(); i++){
			if( userSessions instanceof myUserDetails){
				System.out.println(((myUserDetails) userSessions).getMemberVO().getUserId());
			}
			userSessions.
		}
		RequestContextHolder.currentRequestAttributes().getSessionId();
		sessionRegistry.removeSessionInformation(info.getSessionId());*/
		/*(myUserDetails)userSessions*/
		/*sessionRegistry.getAllSessions(principal, false);*/
		
		String addr= ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
	       .getRequest().getRemoteAddr();
		System.out.println("UUID RandomID = " + UUID.randomUUID());
		System.out.println("UUID RandomID to String = " + UUID.randomUUID().toString());
		System.out.println("UUID RandomID to String to SubString= " + UUID.randomUUID().toString().replace("-", ""));
		String userIpAddress = request.getRemoteAddr();
		System.out.println("my Addres is = "  + addr);
		System.out.println("userIpAQddress = "  + userIpAddress);
		/*List<InappcategoryVO> inappcategoryList = inAppCategoryService.getListCategoryForChildCategory();*/
		String realPath  = request.getSession().getServletContext().getRealPath("/WEB-INF/web.xml");
		System.out.println("reaPath is = " + realPath);
		int a = 1;
		int b = 16;
		int c = a | b;

		System.out.println("bit ���� c = " + c);
		return "index";
	}

	@RequestMapping( value = "couponGenerate.html", method=RequestMethod.POST)
	public @ResponseBody String contentsCouponGeratePOST( String coupon_num, HttpServletRequest request, HttpSession session ) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//edit for the    format you need
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    final int N = alphabet.length();

	    Random r = new Random();
	    int totalCount = 1;
	    String string ="";
		
		while(totalCount == 1){
			string = format.format(new Date());
			for (int i = 0; i < 4; i++) {
				string += alphabet.charAt(r.nextInt(N));
			}
			Entity param = new Entity();
			param.setValue("coupon_num", string);
			List listCnt = appService.getCountOfIdenticalCouponNumForAll(param);
			HashMap temp = (HashMap)listCnt.get(0);
			totalCount = Integer.parseInt(temp.get("IdenticalCouponCOUNT").toString());
	    }
		return string;
	}

	@RequestMapping( value = "getCurrentTime.html", method=RequestMethod.POST)
	public @ResponseBody String getCurrentTimePOST( String coupon_num, HttpServletRequest request, HttpSession session ) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//edit for the    format you need
		String date = format.format(new Date());
		return date;
	}

	@RequestMapping( value = "printAnswer.html", method=RequestMethod.POST)
	public void writeJsonAnswerPOST( @RequestBody  String jsonObject, HttpSession session ) throws JsonParseException, IOException  {
		try {
			FileWriter file = new FileWriter("c:\\data\\test.json");
			file.write(jsonObject);
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping( value = "viewJsonAnswer.html", method={RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf8")
	public ModelAndView viewJsonAnswerPOST( HttpSession session ) throws JsonParseException, IOException  {
		ModelAndView mv = new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		BufferedReader fileReader = new BufferedReader(
				new FileReader("c:\\data\\test.json"));
		JsonNode rootNode = mapper.readTree(fileReader);
		System.out.println("toString = " + rootNode.toString());
		/*Object obj = JsonParser jp = parse(new FileReader("c:\\test.json"));*/
		mv.addObject("json",	rootNode.toString());
		mv.setViewName("sampleAnswer");
		return mv;
	}
	
	/*int limitUser =  Integer.parseInt(messageSource.getMessage("limit.user.count", null, Locale.getDefault()));
	int permitUser = memberService.selectCountWithPermisionUserByCompanySeq(memberVO.getCompanySeq());
	
	
	try{
		
		if("".equals(memberVO.getEmail())) {
			if( permitUser >= limitUser ){
				modelAndView.addObject("msg", messageSource.getMessage("extend.local.088", null, localeResolver.resolveLocale(request)));
				modelAndView.addObject("type", "href");
				modelAndView.addObject("url", "/man/user/list.html?page=1");
				modelAndView.setViewName("inc/dummy");
			}*/
	
	@RequestMapping( value = "/logoutFlagOff.html", method=RequestMethod.POST )
	public @ResponseBody int logoutFlagOffPOST( HttpSession session, String userSeq ) throws JsonParseException, IOException  {
		int result = 0;
		System.out.println("Helo IN logOff Flag");
		MemberVO memberVO = new MemberVO();
		memberVO.setLoginStatus("2");
		result = memberService.updateMemberInfo(memberVO, Integer.parseInt(userSeq));
		
		return result;
	}
	
	
	@RequestMapping( value = "/userLimitIsOver.html", method=RequestMethod.POST )
	public @ResponseBody int userLimitIsOverPOST( HttpSession session, String companySeq ) throws JsonParseException, IOException  {
		int limitUser =  Integer.parseInt(messageSource.getMessage("limit.user.count", null, Locale.getDefault()));
		int permitUser = memberService.selectCountWithPermisionUserByCompanySeq(Integer.parseInt(companySeq));
		
		if(permitUser >= limitUser) return 0;
		else return 1;
	}
	
	@RequestMapping( value = "/deviceIsOver200.html", method=RequestMethod.POST )
	public @ResponseBody int deviceIsOver200POST( HttpSession session ) throws JsonParseException, IOException  {
		
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		int count = deviceService.countDeviceIsAvailable(activeUser.getMemberVO().getCompanySeq());
		return count;
	}
	
	@RequestMapping( value = "/categoryIsDuplicated.html", method=RequestMethod.POST )
	public @ResponseBody int categoryIsDuplicatedPOST( String storeBundleId, String categoryName, HttpSession session ) throws JsonParseException, IOException  {
		
		myUserDetails activeUser = null;
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			throw new AuthenticationException();
		}else {
			activeUser = (myUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		
		int count = inAppCategoryService.categoryIsDuplicated(storeBundleId, categoryName);
		return count;
	}
}