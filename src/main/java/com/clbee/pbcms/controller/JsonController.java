package com.clbee.pbcms.controller;

import com.clbee.pbcms.Json.AppVOForConnection;
import com.clbee.pbcms.Json.ConnectLicenseInfo;
import com.clbee.pbcms.Json.ConnectNoticeInfo;
import com.clbee.pbcms.Json.ConnectionInfo;
import com.clbee.pbcms.Json.LoginInfomationVO;
import com.clbee.pbcms.Json.RequestedBookCategoryJsonVO;
import com.clbee.pbcms.Json.RequestedBookJsonVO;
import com.clbee.pbcms.Json.RequestedInAppCategoryJsonVO;
import com.clbee.pbcms.Json.RequestedInAppJsonVO;
import com.clbee.pbcms.service.AppService;
import com.clbee.pbcms.service.BundleService;
import com.clbee.pbcms.service.CaptureService;
import com.clbee.pbcms.service.ContentsService;
import com.clbee.pbcms.service.DeviceService;
import com.clbee.pbcms.service.InAppCategoryService;
import com.clbee.pbcms.service.InAppService;
import com.clbee.pbcms.service.LicenseService;
import com.clbee.pbcms.service.LogService;
import com.clbee.pbcms.service.MemberService;
import com.clbee.pbcms.service.NoticeService;
import com.clbee.pbcms.service.ProvisionService;
import com.clbee.pbcms.service.TemplateService;
import com.clbee.pbcms.util.Entity;
import com.clbee.pbcms.util.FileUtil;
import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.BundleVO;
import com.clbee.pbcms.vo.CaptureVO;
import com.clbee.pbcms.vo.ContentVO;
import com.clbee.pbcms.vo.ContentsappSubVO;
import com.clbee.pbcms.vo.DeviceVO;
import com.clbee.pbcms.vo.InappMetaVO;
import com.clbee.pbcms.vo.InappVO;
import com.clbee.pbcms.vo.InappcategoryVO;
import com.clbee.pbcms.vo.LogVO;
import com.clbee.pbcms.vo.MemberVO;
import com.clbee.pbcms.vo.NoticeVO;
import com.clbee.pbcms.vo.TemplateList;
import com.clbee.pbcms.vo.TemplateVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JsonController {
	@Autowired
	MemberService memberService;

	@Autowired
	TemplateService templateService;

	@Autowired
	AppService appService;

	@Autowired
	InAppService inAppService;

	@Autowired
	InAppCategoryService inAppCategoryService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ContentsService contentsService;

	@Autowired
	ProvisionService provisionService;

	@Autowired
	CaptureService captureService;

	@Autowired
	BundleService bundleService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	LogService logService;

	@Autowired
	NoticeService noticeService;

	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	LicenseService licenseService;

	@RequestMapping(value = "android_app.html", method = RequestMethod.GET)
	public ModelAndView requestJSON(HttpServletRequest request, String appSeq) {
		ModelAndView modelAndView = new ModelAndView();
		AppVO appVO = appService.selectById(Integer.parseInt(appSeq));
		modelAndView.addObject("filePath",
				messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))
						+ messageSource.getMessage("file.path.app.file", null, localeResolver.resolveLocale(request)));
		modelAndView.addObject("appVO", appVO);
		modelAndView.setViewName("android_app");
		return modelAndView;
	}

	@RequestMapping(value = "couponLogin.html", method = RequestMethod.POST)
	public @ResponseBody RequestedInAppJsonVO couponLoginPOST(String couponNum, HttpServletRequest request,
			HttpServletResponse response) {
		InappVO inappVO = inAppService.findByCustomInfo("couponNum", couponNum);
		InappcategoryVO inappcategoryVO = null;
		if (inappVO != null)
			inappcategoryVO = inAppCategoryService.findByCustomInfo("categorySeq", inappVO.getCategorySeq());
		CaptureVO captureVO = new CaptureVO();
		captureVO.setCaptureGb("2");
		captureVO.setBoardSeq(inappVO.getInappSeq());
		List<CaptureVO> tempList = captureService.selectListByBoardSeqWithGb(captureVO);
		return new RequestedInAppJsonVO(inappVO, inappcategoryVO, couponNum, couponNum, couponNum, couponNum, tempList);
	}

	@RequestMapping(value = "loginVerify.html", method = RequestMethod.POST)
	public @ResponseBody LoginInfomationVO jsonLoginVerify(String storeBundleId, String ostype, String deviceUuid,
			MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) {
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key + " = " + value);
		}
		int loginValue = 0;
		MemberVO memberVOForTransmition = null;
		String companyId, loginMessage;
		companyId = loginMessage = "";
		AppVO appVO = null;

		System.out.println("userId = " + memberVO.getUserId());
		System.out.println("userPw = " + memberVO.getUserPw());
		Date date = null;

		if (deviceUuid != null && !"".equals(deviceUuid)) {
			if (memberVO.getUserId() == null || "".equals(memberVO.getUserId())) {
				// 5002 : ���̵�(user_id)�� �Է����� ����
				loginValue = 5002;
				loginMessage = messageSource.getMessage("login.error.5002", null,
						localeResolver.resolveLocale(request));
			} else if (memberVO.getUserPw() == null || "".equals(memberVO.getUserPw())) {
				// 5003 : ��й�ȣ(user_pw)�� �Է����� ����
				loginValue = 5003;
				loginMessage = messageSource.getMessage("login.error.5003", null,
						localeResolver.resolveLocale(request));
			} else {
				int loginResult = memberService.logInVerify(memberVO.getUserId(), changeSHA256(memberVO.getUserPw()));
				/*
				 * loginResult ���� loginResult == 1 : login���� loginResult ==
				 * 2 : ����� ��ȿ�Ⱓ�� ���� ���� loginResult == -1 :
				 * loginVerify�޼ҵ忡�� Parsing�� ���� loginResult == 3 : Ż��
				 * loginResult == 4 : ���� loginResult == 5 : ����Ż��
				 * loginResult == 6 : �����
				 */
				if (loginResult == 1) {
					memberVOForTransmition = memberService.findByUserName(memberVO.getUserId());
					companyId = memberService
							.findCompanyMemberIdByCompanySeqAndUserGb(memberVOForTransmition.getCompanySeq());
					/*
					 * if("1".equals(memberVOForTransmition.getUserGb())) {
					 * memberVOForTransmition.getUserStartDt() }
					 */

					appVO = appService.selectByStoreId(storeBundleId);
					date = new Date();
					String deviceFlag = messageSource.getMessage("device.is.used", null,
							localeResolver.resolveLocale(request));

					int checkValue;
					if ("true".equals(deviceFlag)) {
						if (appVO == null) {
							// ��ϵ��� ���� �ĺ���
							loginValue = 4993;
							loginMessage = messageSource.getMessage("login.error.4993", null,
									localeResolver.resolveLocale(request));
							checkValue = 9999; // �ٷ� �ٸ�� ����������
						} else {
							if (appVO.getRegMemberVO().getCompanySeq() != memberVOForTransmition.getCompanySeq())
								checkValue = 6;
							else
								checkValue = deviceService.checkIfExistUUID(deviceUuid,
										memberVOForTransmition.getCompanySeq());
						}
					} else {
						if (appVO == null) {
							// ��ϵ��� ���� �ĺ���
							loginValue = 4993;
							loginMessage = messageSource.getMessage("login.error.4993", null,
									localeResolver.resolveLocale(request));
							checkValue = 9999; // �ٷ� �ٸ�� ����������
						} else {
							if (appVO.getRegMemberVO().getCompanySeq() != memberVOForTransmition.getCompanySeq())
								checkValue = 6;
							else
								checkValue = 4;
						}
					}
					/*
					 * 1 : �ش��ϴ� UUID�� �������� ���� ( UUID�� ����ؾߵ� ) 2 :
					 * �ش��ϴ� UUID�� 1�� �̻� ������. ( Duplicated ) 3 : �ش��ϴ�
					 * UUID�� 1�� �����ϴµ� ����� �� ���� UUID ( Rejected ) 4 :
					 * �ش��ϴ� UUID�� 1�� �����ϰ� ����Ҽ� �ִ� UUID�� ( Success !!
					 * )
					 */
					if (checkValue == 1) {
						if ("29".equals(memberVOForTransmition.getUserGb())) {
							// 5004 : ��ϵ��� ���� ����̽� �ν���, ��� ��û
							loginValue = 5004;
							loginMessage = messageSource.getMessage("login.error.5004", null,
									localeResolver.resolveLocale(request));
						} else {
							// 5011 : ��ϵ��� ���� ����̽� �ܼ� �ν� �޽���
							loginValue = 5011;
							loginMessage = messageSource.getMessage("login.error.5011", null,
									localeResolver.resolveLocale(request));
						}
					} else if (checkValue == 2) {
						// 5005 : 2�� �̻� ����̽��� ��ϵ�
						loginValue = 5005;
						loginMessage = messageSource.getMessage("login.error.5005", null,
								localeResolver.resolveLocale(request));
					} else if (checkValue == 3) {
						// 5006 : �ش� ����׽��� ������ ���� ���� ����
						loginValue = 5006;
						loginMessage = messageSource.getMessage("login.error.5006", null,
								localeResolver.resolveLocale(request));
					} else if (checkValue == 4) {
						// appService.
						int result = appService.checkIfAvailableAppByBundleId(memberVOForTransmition.getUserSeq(),
								ostype, storeBundleId);
						switch (result) {
						case 5000:
							if ("1".equals(memberVOForTransmition.getLoginStatus())) {
								if (deviceUuid.equals(memberVOForTransmition.getLoginDeviceuuid())) {
									// 5007 : �α��� ���� ( ����̽� �α��� )
									MemberVO loginUpdateVO = new MemberVO();
									loginUpdateVO.setLoginStatus("1");
									loginUpdateVO.setLoginDeviceuuid(deviceUuid);
									loginUpdateVO.setLoginDt(date);
									memberService.updateMemberInfo(loginUpdateVO, memberVOForTransmition.getUserSeq());
									loginValue = 5007;
									loginMessage = "";

								} else {
									if (dateIsNotSame(memberVOForTransmition.getLoginDt())) {
										// 5007 : �α��� ���� ( ����̽� �α��� )
										MemberVO loginUpdateVO = new MemberVO();
										loginUpdateVO.setLoginStatus("1");
										loginUpdateVO.setLoginDeviceuuid(deviceUuid);
										loginUpdateVO.setLoginDt(date);
										memberService.updateMemberInfo(loginUpdateVO,
												memberVOForTransmition.getUserSeq());
										loginValue = 5007;
										loginMessage = "";
									} else {
										// 5012 : �ߺ� �α��� ����
										loginValue = 5012;
										loginMessage = messageSource.getMessage("login.duplicate", null,
												localeResolver.resolveLocale(request));
									}
								}
							} else {
								//// 5007 : �α��� ���� ( ����̽� �α��� )
								MemberVO loginUpdateVO = new MemberVO();
								loginUpdateVO.setLoginStatus("1");
								loginUpdateVO.setLoginDeviceuuid(deviceUuid);
								loginUpdateVO.setLoginDt(date);
								memberService.updateMemberInfo(loginUpdateVO, memberVOForTransmition.getUserSeq());
								loginValue = 5007;
								loginMessage = "";
							}
							break;
						case 5001:
							loginValue = 5008;
							loginMessage = messageSource.getMessage("login.error.5008", null,
									localeResolver.resolveLocale(request));
							break;
						case 9999:
							loginValue = 9999;
							loginMessage = messageSource.getMessage("login.error.9999", null,
									localeResolver.resolveLocale(request));
							break;
						}
					} else if (checkValue == 5) {
						// �ٸ� ȸ��

						System.out.println("Check");
						if ("29".equals(memberVOForTransmition.getUserGb())) {
							loginValue = 5009;
							loginMessage = messageSource.getMessage("login.error.5009", null,
									localeResolver.resolveLocale(request));
						} else {
							loginValue = 5011;
							loginMessage = messageSource.getMessage("login.error.5011", null,
									localeResolver.resolveLocale(request));
						}
					} else if (checkValue == 0) {
						loginValue = 9999;
					} else if (checkValue == 6) {
						loginValue = 4994;
						loginMessage = messageSource.getMessage("login.error.4994", null,
								localeResolver.resolveLocale(request));
					} else {
					}
				} else if (loginResult == 2) {
					/* ����� ��ȿ�Ⱓ�� ���� ���� */
					loginValue = 4999;
					loginMessage = messageSource.getMessage("login.error.4999", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 3) {
					// Ż��
					loginValue = 4998;
					loginMessage = messageSource.getMessage("login.error.4998", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 4) {
					// ����
					loginValue = 4997;
					loginMessage = messageSource.getMessage("login.error.4997", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 5) {
					// ���� Ż��
					loginValue = 4996;
					loginMessage = messageSource.getMessage("login.error.4996", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 6) {
					// ��� ���
					loginValue = 4995;
					loginMessage = messageSource.getMessage("login.error.4995", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult < 0) {
					/* loginVerify�޼ҵ忡�� Parsing�� ���� */
					loginValue = 5001;
					loginMessage = messageSource.getMessage("login.error.5001", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 7) {
					loginValue = 5010;
					loginMessage = messageSource.getMessage("login.error.5010", null,
							localeResolver.resolveLocale(request));
				}
			}
		} else {
			if (memberVO.getUserPw() == null || "".equals(memberVO.getUserPw())) {
				loginValue = 5003;
				loginMessage = messageSource.getMessage("login.error.5003", null,
						localeResolver.resolveLocale(request));
			} else if (memberVO.getUserId() == null || "".equals(memberVO.getUserId())) {
				loginValue = 5002;
				loginMessage = messageSource.getMessage("login.error.5002", null,
						localeResolver.resolveLocale(request));
			} else {
				int loginResult = memberService.logInVerify(memberVO.getUserId(), changeSHA256(memberVO.getUserPw()));
				if (loginResult == 1) {
					date = new Date();
					memberVOForTransmition = memberService.findByUserName(memberVO.getUserId());
					companyId = memberService
							.findCompanyMemberIdByCompanySeqAndUserGb(memberVOForTransmition.getCompanySeq());
					appVO = appService.selectByStoreId(storeBundleId);

					/*
					 * if(appVO.getRegMemberVO().getCompanySeq() !=
					 * memberVOForTransmition.getCompanySeq()) { loginValue =
					 * 4994; loginMessage = "�ٸ� ȸ���� ���̵�� �α����ϼ̽��ϴ�."; }
					 * else
					 */
					loginValue = 5000;
				} else if (loginResult == 2) {
					/* ����� ��ȿ�Ⱓ�� ���� ���� */
					loginValue = 4999;
					loginMessage = messageSource.getMessage("login.error.4999", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 3) {
					// Ż��
					loginValue = 4998;
					loginMessage = messageSource.getMessage("login.error.4998", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 4) {
					// ����
					loginValue = 4997;
					loginMessage = messageSource.getMessage("login.error.4997", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 5) {
					// ���� Ż��
					loginValue = 4996;
					loginMessage = messageSource.getMessage("login.error.4996", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 6) {
					// ��� ���
					loginValue = 4995;
					loginMessage = messageSource.getMessage("login.error.4995", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult < 0) {
					/* loginVerify�޼ҵ忡�� Parsing�� ���� */
					loginValue = 5001;
					loginMessage = messageSource.getMessage("login.error.5001", null,
							localeResolver.resolveLocale(request));
				} else if (loginResult == 7) {
					loginValue = 5010;
					loginMessage = messageSource.getMessage("login.error.5010", null,
							localeResolver.resolveLocale(request));
				}
			}
		}

		LoginInfomationVO abc = null;
		if (date != null)
			abc = new LoginInfomationVO(loginValue, date.toString(), memberVOForTransmition, companyId, loginMessage,
					appVO);
		else
			abc = new LoginInfomationVO(loginValue, null, memberVOForTransmition, companyId, loginMessage, appVO);
		return abc;
	}

	@RequestMapping(value = "connectWithAndroid.html", method = RequestMethod.POST)
	public @ResponseBody Object connectWithAndroidPOST(DeviceVO deviceVO, LogVO logVO, AppVO appVO, ContentVO contentVO,
			ConnectionInfo connectionInfo, String template_seq) {
		/*
		 * 0. �ش� �ۿ� ���� �ٿ�ε� ������ ��� ����
		 */
		Object object = null;
		HashMap<Object, Object> map = new HashMap<Object, Object>();

		switch (Integer.parseInt(connectionInfo.getWorkPath())) {
		case 0:
			// �ش� �ۿ� ���� �ٿ�ε� ������ ��� ����
			InappVO inappVO = inAppService.findByCustomInfo("inappSeq", Integer.parseInt(connectionInfo.getInappSeq()));
			if (inappVO == null) {
				map.put("result", 9999);
				object = map;
				return object;
			}
			String distrGb = inappVO.getDistrGb();
			String completGb = inappVO.getCompletGb();
			int result = 0;

			if ("1".equals(distrGb) && "1".equals(completGb)) {
				// �Ѵ� �̿��� �� ����
				result = 4000;
			} else if ("1".equals(distrGb) && "2".equals(completGb)) {
				// �Ϲ� �ٿ�ε常 ����
				result = 4001;
			} else if ("2".equals(distrGb) && "1".equals(completGb)) {
				// ���� �ٿ�ε常 ����
				result = 4002;
			} else if ("2".equals(distrGb) && "2".equals(completGb)) {
				// �Ϲ� �ٿ�ε常 ����
				result = 4001;
			} else {
				result = 9999;
			}
			map.put("result", result);
			object = map;
			break;
		case 1:
			InappVO inappVOForCOUPON = inAppService.findByCustomInfo("couponNum", connectionInfo.getCouponNum());
			if (inappVOForCOUPON == null) {
				map.put("result", 9999);
				object = map;
				return object;
			}
			InappcategoryVO inappcategoryVO = inAppCategoryService.findByCustomInfo("categorySeq",
					inappVOForCOUPON.getCategorySeq());
			List<RequestedInAppJsonVO> tempList = new ArrayList();
			CaptureVO captureVO = new CaptureVO();
			captureVO.setCaptureGb("2");
			captureVO.setBoardSeq(inappVOForCOUPON.getInappSeq());
			List<CaptureVO> tempCaptureList = captureService.selectListByBoardSeqWithGb(captureVO);
			tempList.add(new RequestedInAppJsonVO(inappVOForCOUPON, inappcategoryVO, "", "", "", "", tempCaptureList));
			map.put("CategoryList", tempList);
			object = map;
			break;
		case 2:
			/* 13. ����̽� ��� */
			MemberVO memberVO1 = memberService.findByCustomInfo("userSeq", deviceVO.getRegUserSeq());
			deviceVO.setCompanySeq(memberVO1.getCompanySeq());
			try {
				deviceService.insertDeviceInfo(deviceVO);
				map.put("success", "5000");
			} catch (Exception e) {
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;
		case 3:
			/* 14. �α� ��� */

			try {
				logService.insertLogInfo(logVO);
				map.put("success", "5000");
			} catch (Exception e) {
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;
		}
		return object;
	}

	@RequestMapping(value = "appExcecution.html", method = RequestMethod.POST)
	public @ResponseBody Object appExecutionPOST(String userSeq, LogVO logVO, String deviceUuid,
			InappMetaVO inappMetaVO, DeviceVO deviceVO, AppVO appVO, ContentVO contentVO, InappVO inappVO,
			ConnectionInfo connectionInfo, String template_seq) throws IOException {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		Object object = null;
		int result = 0;
		/*
		 * 1. App Availabilty
		 * 
		 * 
		 */
		MemberVO memberVO = memberService.findByCustomInfo("userSeq", userSeq);

		switch (Integer.parseInt(connectionInfo.getWorkPath())) {
		case 0:
			/*
			 * 1. OK 2. ��ȿ ��¥�� ������. 3. �˼� ���� ���� 4. ������ ostype��
			 * bundleid�� ��ġ�ϴ� ���� �����ϴ�. 5. ��������� ���Դϴ�. 6. ���ѵ�
			 * ���Դϴ�.
			 */

		}
		return template_seq;
	}

	@RequestMapping(value = "connectWithPageBuilder.html", method = RequestMethod.POST)
	public @ResponseBody Object connectWithPageBuilderPOST(HttpServletRequest request, ContentsappSubVO contentsappSubVO, String userSeq, LogVO logVO, InappMetaVO inappMetaVO, DeviceVO deviceVO,
			AppVO appVO, ContentVO contentVO, InappVO inappVO, CaptureVO captureVO, String captureFlag,
			ConnectionInfo connectionInfo, String template_seq) throws IOException {
		HashMap<Object, Object> map = new HashMap();
		TemplateList ListObject = new TemplateList();
		MemberVO memberVO = null;
		Object object = null;
		String fileExt = "";

		System.out.println("connectInfo = " + connectionInfo.getWorkPath());

		switch (Integer.parseInt(connectionInfo.getWorkPath())) {
		case 0:
			HashMap<String, String> askingInfo = new HashMap();
			askingInfo.put("IP", messageSource.getMessage("FTP.Info.IP", null, localeResolver.resolveLocale(request)));
			askingInfo.put("ID", messageSource.getMessage("FTP.Info.ID", null, localeResolver.resolveLocale(request)));
			askingInfo.put("PW", messageSource.getMessage("FTP.Info.PW", null, localeResolver.resolveLocale(request)));
			object = askingInfo;
			break;

		case 1:
			ListObject.setCurrentPage(Integer.valueOf(1));
			memberVO = memberService.findByCustomInfo("userId", connectionInfo.getUserId());
			ListObject = templateService.selectList(null, memberVO, ListObject, "All");
			object = ListObject.getList();
			break;

		case 2:
			object = templateService.selectByTempId(Integer.parseInt(connectionInfo.getTemplateSeq()));
			break;

		case 3:
			System.out.println("workPath3");
			memberVO = memberService.findByCustomInfo("userId", connectionInfo.getUserId());
			appVO.setLimitGb("2");
			appVO.setRegUserSeq(Integer.valueOf(memberVO.getUserSeq()));
			appVO.setRegUserId(memberVO.getUserId());
			appVO.setRegUserGb(memberVO.getUserGb());
			appVO.setChgUserGb(memberVO.getUserGb());
			appVO.setChgUserId(memberVO.getUserId());
			appVO.setChgUserSeq(Integer.valueOf(memberVO.getUserSeq()));
			appVO.setInstallGb("1");
			appVO.setUseAvailDt(new Date());
			int appSeq = 0;
			String provId = "";

			appSeq = appService.insertAppInfo(appVO);
			map.put("appSeq", Integer.valueOf(appSeq));

			int ostype = Integer.parseInt(appVO.getOstype());
			bundleService.delete(appSeq);
			provId = appVO.getStoreBundleId();
			BundleVO bundleVO = new BundleVO();
			bundleVO.setAppSeq(Integer.valueOf(appSeq));
			bundleVO.setBundleName(provId);
			bundleVO.setOsType(Integer.valueOf(ostype));
			if (ostype != 4)
				bundleVO.setProvSeq(Integer.valueOf(Integer.parseInt(connectionInfo.getProvSeq1())));
			bundleVO.setProvTestGb(connectionInfo.getProvTestGb1());
			bundleService.insert(bundleVO);

			if ((!"".equals(connectionInfo.getProvSeq2())) && (connectionInfo.getProvSeq2() != null)) {
				bundleVO.setAppSeq(Integer.valueOf(appSeq));
				bundleVO.setBundleName(provId);
				bundleVO.setOsType(Integer.valueOf(ostype));
				if (ostype != 4)
					bundleVO.setProvSeq(Integer.valueOf(Integer.parseInt(connectionInfo.getProvSeq2())));
				bundleVO.setProvTestGb(connectionInfo.getProvTestGb2());
				bundleService.insert(bundleVO);
			}
			object = map;
			break;

		case 4:
			memberVO = memberService.findByCustomInfo("userId", connectionInfo.getUserId());
			object = provisionService.selectList(memberVO.getUserSeq(), memberVO.getCompanySeq());
			break;

		case 5:
			System.out.println("workPath5");
			object = inAppCategoryService.getListInAppCategory("storeBundleId", connectionInfo.getStoreBundleId());
			break;

		case 6:
			MemberVO memberVOForInapp = memberService.findByCustomInfo("userId", connectionInfo.getUserId());
			inappVO.setRegUserGb(memberVOForInapp.getUserGb());
			inappVO.setRegUserId(memberVOForInapp.getUserId());
			inappVO.setRegUserSeq(Integer.valueOf(memberVOForInapp.getUserSeq()));
			inappVO.setChgUserGb(memberVOForInapp.getUserGb());
			inappVO.setChgUserId(memberVOForInapp.getUserId());
			inappVO.setChgUserSeq(Integer.valueOf(memberVOForInapp.getUserSeq()));
			try {
				int inappSeq = inAppService.getSeqAfterInsertInAppInfo(inappVO);
				map.put("inappSeq", Integer.valueOf(inappSeq));
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 7:
			System.out.println("workPath7");

			object = inAppService.findListByCustomInfo("storeBundleId", connectionInfo.getStoreBundleId());
			break;

		case 8:
			System.out.println("workPath8");
			memberVO = memberService.findByCustomInfo("userId", connectionInfo.getUserId());
			List<AppVO> hello = appService.getListNotComplte(memberVO);
			Object appVOForConnection = new ArrayList();
			for (int i = 0; i < hello.size(); i++) {
				((List) appVOForConnection).add(new AppVOForConnection((AppVO) hello.get(i)));
			}
			object = appVOForConnection;
			break;

		case 9:
			System.out.println("workPath9");

			Entity param = new Entity();
			param.setValue("store_bundle_id", appVO.getStoreBundleId());
			param.setValue("ostype", appVO.getOstype());

			object = appService.selectByBundleId(param);
			break;

		case 10:
			try {
				appService.updateAppInfo(appVO, appVO.getAppSeq().intValue());
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 11:
			try {
				if (inappVO.getInappSeq() != null) {
					inAppService.updateInAppInfo(inappVO, inappVO.getInappSeq().intValue());
				}

				if (inappMetaVO.getInappMetaSeq() != null) {
					inAppService.updateInAppMetaInfo(inappMetaVO, inappMetaVO.getInappMetaSeq().intValue());
				}

				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 12:
			System.out.println("workPath12");

			List<BundleVO> list = bundleService
					.listByAppSeq(Integer.valueOf(Integer.parseInt(connectionInfo.getAppSeq())));
			Object listForProvSeq = new ArrayList();

			for (int i = 0; i < list.size(); i++) {
				((List) listForProvSeq).add(((BundleVO) list.get(i)).getProvSeq());
			}
			map.put("provSeq", listForProvSeq);
			object = map;
			break;

		case 13:
			MemberVO memberVO1 = memberService.findByCustomInfo("userSeq", deviceVO.getRegUserSeq().intValue());
			deviceVO.setCompanySeq(Integer.valueOf(memberVO1.getCompanySeq()));
			try {
				deviceService.insertDeviceInfo(deviceVO);
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 14:
			if (("logout".equals(logVO.getData())) || ("Logout".equals(logVO.getData()))) {
				MemberVO logoutMemberVO = new MemberVO();
				logoutMemberVO.setLoginStatus("2");
				memberService.updateMemberInfo(logoutMemberVO, logVO.getRegUserSeq().intValue());
			}
			try {
				logService.insertLogInfo(logVO);
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 15:
			try {
				logService.insertLogInfo(logVO);
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 16:
			try {
				Object logVO1 = logService.selectLogInfo(logVO.getStoreBundleId(), logVO.getInappSeq(),
						Integer.valueOf(Integer.parseInt(userSeq)), logVO.getPageGb(), logVO.getDataGb());
				map.put("logData", logVO1);
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("logData", "");
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;
		case 17:
			try {
				List<NoticeVO> noticeList = noticeService.getListAvailableNoticeByCompany(
						Integer.parseInt(connectionInfo.getCompanySeq()), Integer.parseInt(connectionInfo.getUserSeq()),
						connectionInfo.getStoreBundleId());
				Object addList = new ArrayList();

				if (noticeList != null) {
					for (int i = 0; i < noticeList.size(); i++) {
						if (((NoticeVO) noticeList.get(i)).getNoticeappSubVO() != null) {
							((List) addList).add(new ConnectNoticeInfo((NoticeVO) noticeList.get(i),
									((NoticeVO) noticeList.get(i)).getNoticeappSubVO().getInappSeq()));
						} else {
							((List) addList).add(new ConnectNoticeInfo((NoticeVO) noticeList.get(i), null));
						}
					}
				}

				map.put("noticeList", addList);
				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("noticeList", "");
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;
		case 18:
			try {
				if ("insert".equals(captureFlag)) {
					captureService.insert(captureVO);
					map.put("success", "5000");
				} else {
					InappVO inAppVO = inAppService.findByCustomInfo("inappSeq", captureVO.getBoardSeq());
					CaptureVO captureVOForDelete = null;
					List<CaptureVO> captureList = captureService.selectListByBoardSeqWithGb(captureVO);
					if (captureList != null) {
						for (int i = 0; i < captureList.size(); i++) {

							String deleteFilePath = messageSource.getMessage("file.path.basic.URL", null,
									localeResolver.resolveLocale(request))
									+ messageSource.getMessage("file.upload.path.app.capture.file", null,
											localeResolver.resolveLocale(request));
							if (FileUtil.delete(
									new File(deleteFilePath + ((CaptureVO) captureList.get(i)).getImgSaveFile()))) {
								captureVOForDelete = new CaptureVO();
								captureVOForDelete.setCaptureSeq(((CaptureVO) captureList.get(i)).getCaptureSeq());
								captureService.delete(captureVOForDelete);
							}
						}
					}

					map.put("success", "5000");
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
			break;

		case 19:
			ArrayList<HashMap<Object, Object>> List = new ArrayList();
			List.add(appService.selectByBundleIdAndOstype(connectionInfo.getOstype(),
					connectionInfo.getStoreBundleId()));
			object = List;
			break;
		case 20:
			try {
				memberVO = memberService.findByCustomInfo("userId", connectionInfo.getUserId());

				contentVO.setCompanySeq(Integer.valueOf(memberVO.getCompanySeq()));
				contentVO.setRegUserId(memberVO.getUserId());
				contentVO.setRegUserSeq(Integer.valueOf(memberVO.getUserSeq()));
				contentVO.setRegUserGb(memberVO.getUserGb());
				contentVO.setRegDt(new Date());

				contentVO.setChgUserId(memberVO.getUserId());
				contentVO.setChgUserSeq(Integer.valueOf(memberVO.getUserSeq()));
				contentVO.setChgUserGb(memberVO.getUserGb());
				contentVO.setChgDt(new Date());

				contentVO.setUseGb("1");
				contentVO.setCompletGb("2");
				contentVO.setLimitGb("2");
				int contentSeq = contentsService.insertContentInfo(contentVO);

				contentsappSubVO.setContentsSeq(Integer.valueOf(contentSeq));
				contentsService.insertContentsappSubInfo(contentsappSubVO);

				String saveFileName = contentVO.getUploadSaveFile().substring(0,
						contentVO.getUploadSaveFile().lastIndexOf("."));

				unzip(saveFileName, request);

				map.put("success", "5000");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("success", "5001");
			} finally {
				object = map;
			}
		}

		return object;
	}

	// Update
	@RequestMapping(value = "updateApp_json.html", method = RequestMethod.POST)
	public String updateApp_json(AppVO appVO, HttpServletRequest request) {
		System.out.println("UPDATE  @@@@@@@@@@@@@@@@JSON [APPVO]");
		AppVO updatedVO = new AppVO();
		updatedVO.setVerNum(appVO.getVerNum());
		updatedVO.setApp_resultCode(appVO.getApp_resultCode());
		updatedVO.setChgDt(new Date());
		if (appVO.getStoreBundleId() == null)
			System.out.println("[UPDATE] store_bundle_id is NULL!! so DID NOTHING");
		else
			appService.updateAppByIdentifier(updatedVO, appVO.getStoreBundleId());

		return "updateApp_json";
	}

	@RequestMapping(value = "insert_json.html", method = RequestMethod.POST)
	public String insertJSONPOST(AppVO insertAppVO, HttpServletRequest request)
			throws ParseException, UnsupportedEncodingException {
		AppVO appVO = new AppVO();
		appVO.setAppVO(insertAppVO);
		appVO.setRegDt(new Date());
		appVO.setChgDt(new Date());
		appService.insertAppInfo(appVO);

		return "insert_json";
	}

	@RequestMapping(value = "exportApp_json.html", method = RequestMethod.POST)
	public ModelAndView exportApp_jsonPOST(AppVO appVO, HttpServletRequest request) {
		List<AppVO> appList = null;
		ModelAndView modelAndView = new ModelAndView();

		if (appVO.getStoreBundleId() == null)
			appList = appService.selectByUserId(appVO.getRegUserId());
		else
			appList = appService.selectByUserIdAndIdenty(appVO.getRegUserId(), appVO.getStoreBundleId());
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("appSize", appList.size());
		modelAndView.setViewName("exportApp_json");

		return modelAndView;
	}

	@RequestMapping(value = "export_json.html", method = RequestMethod.POST)
	public ModelAndView exportJsonPOST(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<TemplateVO> tempList = templateService.selectByAll();
		modelAndView.addObject("tempSize", tempList.size());
		modelAndView.addObject("tempList", tempList);
		modelAndView.setViewName("export_json");
		return modelAndView;
	}

	public String changeSHA256(String str) {
		String SHA = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		return SHA;
	}

	@RequestMapping(value = "getJsonBook.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> jsonTest1POST(HttpServletRequest request, String ostype,
			String store_bundle_id, String userSeq, HttpSession session) {
		System.out.println("jsonTest.html@@@@@@@@@@@@@@@@@@@@@");

		String basicURL = "http://"
				+ messageSource.getMessage("basic.Info.IP", null, localeResolver.resolveLocale(request)) + ":8080";
		String iconURL = messageSource.getMessage("file.upload.path.inapp.icon.file", null,
				localeResolver.resolveLocale(request));
		String captureURL = messageSource.getMessage("file.upload.path.inapp.capture.file", null,
				localeResolver.resolveLocale(request));
		String inappURL = messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
		/*
		 * Entity param = new Entity();
		 * 
		 * param.setValue("store_bundle_id", store_bundle_id);
		 * param.setValue("ostype", ostype); List<LinkedHashMap<Object, Object>>
		 * map = (List<LinkedHashMap<Object, Object>>)
		 * appService.selectByBundleId(param);
		 */
		CaptureVO captureVO = new CaptureVO();

		/* String iconURL, String captureURL, String basicURL, */
		List<InappVO> inappList = inAppService.getListInappVO("storeBundleId", store_bundle_id,
				Integer.parseInt(userSeq));

		captureService.selectListByBoardSeqWithGb(captureVO);
		List<InappcategoryVO> inappcategoryList = inAppCategoryService.getListInAppCategory("storeBundleId",
				store_bundle_id);
		List<InappcategoryVO> inappcategoryListOneDepth = inAppCategoryService
				.getListInAppCategoryforOneDepth("storeBundleId", store_bundle_id);
		List<RequestedBookCategoryJsonVO> orderedInappList = new ArrayList<RequestedBookCategoryJsonVO>();
		List<RequestedBookJsonVO> tempForOrderedInappList = null;

		for (int i = 0; i < inappcategoryListOneDepth.size(); i++) {
			orderedInappList.add(new RequestedBookCategoryJsonVO(inappcategoryListOneDepth.get(i)));
		}

		for (int k = 0; k < inappcategoryListOneDepth.size(); k++) {
			List<RequestedBookJsonVO> decentInappList = new ArrayList<RequestedBookJsonVO>();
			List<RequestedBookCategoryJsonVO> decentInappCateList = new ArrayList<RequestedBookCategoryJsonVO>();
			orderedInappList.get(k).setDecentInAppList(decentInappList);
			orderedInappList.get(k).setDecentInAppCategoryList(decentInappCateList);
			int decentInappcategoryCnt = 0;
			for (int l = 0; l < inappcategoryList.size(); l++) {
				int categortySeq = inappcategoryListOneDepth.get(k).getCategorySeq();
				int parentSeq = inappcategoryList.get(l).getCategoryParent();
				if (categortySeq == parentSeq) {
					RequestedBookCategoryJsonVO requestedInAppJsonVO = new RequestedBookCategoryJsonVO(
							inappcategoryList.get(l));
					decentInappCateList.add(requestedInAppJsonVO);

					selfEnteringMethodForBook(l, decentInappcategoryCnt, decentInappCateList, decentInappList,
							inappcategoryList, inappList, iconURL, captureURL, basicURL, inappURL);
					decentInappcategoryCnt++;
				}
			}
			for (int j = 0; j < inappList.size(); j++) {
				int categorySeq = inappcategoryListOneDepth.get(k).getCategorySeq();
				int inappCategorySeq = inappList.get(j).getCategorySeq();
				if (categorySeq == inappCategorySeq) {
					captureVO.setCaptureGb("2");
					captureVO.setBoardSeq(inappList.get(j).getInappSeq());
					List<CaptureVO> tempCaptureList = captureService.selectListByBoardSeqWithGb(captureVO);
					RequestedBookJsonVO requestedInAppJsonVO = new RequestedBookJsonVO(inappList.get(j),
							inappcategoryList.get(k), iconURL, captureURL, basicURL, inappURL, tempCaptureList);
					decentInappList.add(requestedInAppJsonVO);
				}
			}
		}
		HashMap<Object, Object> category = new HashMap<Object, Object>();
		category.put("CategoryList", orderedInappList);

		return category;
	}
	
	//20180920 : lsy - license account auth develop
	@RequestMapping(value = "licenseWithPageBuilder.html", method = RequestMethod.POST)
	public @ResponseBody Object licenseWithPageBuilder(HttpServletRequest request, HttpServletResponse response, ConnectLicenseInfo connectLicenseInfo){
		HashMap<Object, Object> map = new HashMap();
		Object object = null;
		
		switch (Integer.parseInt(connectLicenseInfo.getWorkPath())) {
			case 0:
				connectLicenseInfo = licenseService.licenseAuthCheckWithAccount(connectLicenseInfo);
				
				if(connectLicenseInfo.getResultCode().equals("6000")) {
					connectLicenseInfo.setResultCode("");
					connectLicenseInfo = licenseService.licenseUserRegist(connectLicenseInfo);
					if(connectLicenseInfo.getResultCode().equals("6000")) {
						map.put("deviceSeq", connectLicenseInfo.getDeviceSeq());
						map.put("licenseUseName", licenseService.selectLicenseUseCompanyName(connectLicenseInfo.getUserSeq()));
					}
				}
				break;
			case 1:
				connectLicenseInfo = licenseService.licenseAuthCheckWithDevice(connectLicenseInfo);
				break;
		}
		map.put("resultCode", connectLicenseInfo.getResultCode());
		object = map;
		return object;
	}
	//20180920 : lsy - license account auth develop - end
	
	private void selfEnteringMethodForBook(int hccnt, int dccnt,
			List<RequestedBookCategoryJsonVO> hierachyInappCategoryList, List<RequestedBookJsonVO> hierachyInappList,
			List<InappcategoryVO> inappcategoryList, List<InappVO> inappList, String iconURL, String captureURL,
			String basicURL, String inappURL) {
		List<RequestedBookJsonVO> decentInappList = new ArrayList<RequestedBookJsonVO>();
		List<RequestedBookCategoryJsonVO> decentInappCateList = new ArrayList<RequestedBookCategoryJsonVO>();
		hierachyInappCategoryList.get(dccnt).setDecentInAppList(decentInappList);
		hierachyInappCategoryList.get(dccnt).setDecentInAppCategoryList(decentInappCateList);

		CaptureVO captureVO = new CaptureVO();
		int decentInappcategoryCnt = 0;

		for (int ccnt = 0; ccnt < inappcategoryList.size(); ccnt++) {
			int categortySeq = inappcategoryList.get(hccnt).getCategorySeq();
			int parentSeq = inappcategoryList.get(ccnt).getCategoryParent();
			if (categortySeq == parentSeq) {
				RequestedBookCategoryJsonVO requestedInAppJsonVO = new RequestedBookCategoryJsonVO(
						inappcategoryList.get(ccnt));
				decentInappCateList.add(requestedInAppJsonVO);

				selfEnteringMethodForBook(ccnt, decentInappcategoryCnt, decentInappCateList, decentInappList,
						inappcategoryList, inappList, iconURL, captureURL, basicURL, inappURL);
				decentInappcategoryCnt++;
			}
		}
		for (int inappCnt = 0; inappCnt < inappList.size(); inappCnt++) {
			int categorySeq = inappcategoryList.get(hccnt).getCategorySeq();
			int inappCategorySeq = inappList.get(inappCnt).getCategorySeq();
			if (categorySeq == inappCategorySeq) {
				captureVO.setCaptureGb("2");
				captureVO.setBoardSeq(inappList.get(inappCnt).getInappSeq());
				List<CaptureVO> tempCaptureList = captureService.selectListByBoardSeqWithGb(captureVO);
				decentInappList.add(new RequestedBookJsonVO(inappList.get(inappCnt), inappcategoryList.get(hccnt),
						iconURL, captureURL, basicURL, inappURL, tempCaptureList));
			}
		}
	}

	@RequestMapping(value = "jsonTest.html", method = RequestMethod.POST)
	public @ResponseBody HashMap<Object, Object> jsonTestPOST(HttpServletRequest request, String ostype,
			String store_bundle_id, String userSeq, HttpSession session) {
		System.out.println("jsonTest.html@@@@@@@@@@@@@@@@@@@@@");

		String basicURL = "http://"
				+ messageSource.getMessage("basic.Info.IP", null, localeResolver.resolveLocale(request)) + ":8080";
		String iconURL = messageSource.getMessage("file.upload.path.inapp.icon.file", null,
				localeResolver.resolveLocale(request));
		String captureURL = messageSource.getMessage("file.upload.path.inapp.capture.file", null,
				localeResolver.resolveLocale(request));
		String inappURL = messageSource.getMessage("file.path.inapp.file", null, localeResolver.resolveLocale(request));
		Entity param = new Entity();

		param.setValue("store_bundle_id", store_bundle_id);
		param.setValue("ostype", ostype);

		System.out.println(" store_bundle_id " + store_bundle_id);
		System.out.println(" ostype " + ostype);

		List<LinkedHashMap<Object, Object>> map = (List<LinkedHashMap<Object, Object>>) appService
				.selectByBundleId(param);

		System.out.println("map = " + map);
		CaptureVO captureVO = new CaptureVO();

		/* String iconURL, String captureURL, String basicURL, */
		List<InappVO> inappList = inAppService.getListInappVO("storeBundleId", store_bundle_id,
				Integer.parseInt(userSeq));

		System.out.println("inappList = " + inappList);

		captureService.selectListByBoardSeqWithGb(captureVO);
		List<InappcategoryVO> inappcategoryList = inAppCategoryService.getListInAppCategory("storeBundleId",
				store_bundle_id);
		List<InappcategoryVO> inappcategoryListOneDepth = inAppCategoryService
				.getListInAppCategoryforOneDepth("storeBundleId", store_bundle_id);
		List<RequestedInAppCategoryJsonVO> orderedInappList = new ArrayList<RequestedInAppCategoryJsonVO>();
		List<RequestedInAppJsonVO> tempForOrderedInappList = null;

		for (int i = 0; i < inappcategoryListOneDepth.size(); i++) {
			orderedInappList.add(new RequestedInAppCategoryJsonVO(inappcategoryListOneDepth.get(i)));
		}

		for (int k = 0; k < inappcategoryListOneDepth.size(); k++) {
			List<RequestedInAppJsonVO> decentInappList = new ArrayList<RequestedInAppJsonVO>();
			List<RequestedInAppCategoryJsonVO> decentInappCateList = new ArrayList<RequestedInAppCategoryJsonVO>();
			orderedInappList.get(k).setDecentInAppList(decentInappList);
			orderedInappList.get(k).setDecentInAppCategoryList(decentInappCateList);
			int decentInappcategoryCnt = 0;
			for (int l = 0; l < inappcategoryList.size(); l++) {
				int categortySeq = inappcategoryListOneDepth.get(k).getCategorySeq();
				int parentSeq = inappcategoryList.get(l).getCategoryParent();
				if (categortySeq == parentSeq) {
					RequestedInAppCategoryJsonVO requestedInAppJsonVO = new RequestedInAppCategoryJsonVO(
							inappcategoryList.get(l));
					decentInappCateList.add(requestedInAppJsonVO);

					selfEnteringMethod(l, decentInappcategoryCnt, decentInappCateList, decentInappList,
							inappcategoryList, inappList, iconURL, captureURL, basicURL, inappURL);
					decentInappcategoryCnt++;
				}
			}
			for (int j = 0; j < inappList.size(); j++) {
				int categorySeq = inappcategoryListOneDepth.get(k).getCategorySeq();
				int inappCategorySeq = inappList.get(j).getCategorySeq();
				if (categorySeq == inappCategorySeq) {
					captureVO.setCaptureGb("2");
					captureVO.setBoardSeq(inappList.get(j).getInappSeq());
					List<CaptureVO> tempCaptureList = captureService.selectListByBoardSeqWithGb(captureVO);
					RequestedInAppJsonVO requestedInAppJsonVO = new RequestedInAppJsonVO(inappList.get(j),
							inappcategoryList.get(k), iconURL, captureURL, basicURL, inappURL, tempCaptureList);
					decentInappList.add(requestedInAppJsonVO);
				}
			}
		}
		HashMap<Object, Object> category = new HashMap<Object, Object>();
		category.put("CategoryList", orderedInappList);

		return category;
	}

	private void selfEnteringMethod(int hccnt, int dccnt, List<RequestedInAppCategoryJsonVO> hierachyInappCategoryList,
			List<RequestedInAppJsonVO> hierachyInappList, List<InappcategoryVO> inappcategoryList,
			List<InappVO> inappList, String iconURL, String captureURL, String basicURL, String inappURL) {
		List<RequestedInAppJsonVO> decentInappList = new ArrayList<RequestedInAppJsonVO>();
		List<RequestedInAppCategoryJsonVO> decentInappCateList = new ArrayList<RequestedInAppCategoryJsonVO>();
		hierachyInappCategoryList.get(dccnt).setDecentInAppList(decentInappList);
		hierachyInappCategoryList.get(dccnt).setDecentInAppCategoryList(decentInappCateList);

		CaptureVO captureVO = new CaptureVO();
		int decentInappcategoryCnt = 0;

		for (int ccnt = 0; ccnt < inappcategoryList.size(); ccnt++) {
			int categortySeq = inappcategoryList.get(hccnt).getCategorySeq();
			int parentSeq = inappcategoryList.get(ccnt).getCategoryParent();
			if (categortySeq == parentSeq) {
				RequestedInAppCategoryJsonVO requestedInAppJsonVO = new RequestedInAppCategoryJsonVO(
						inappcategoryList.get(ccnt));
				decentInappCateList.add(requestedInAppJsonVO);

				selfEnteringMethod(ccnt, decentInappcategoryCnt, decentInappCateList, decentInappList,
						inappcategoryList, inappList, iconURL, captureURL, basicURL, inappURL);
				decentInappcategoryCnt++;
			}
		}
		for (int inappCnt = 0; inappCnt < inappList.size(); inappCnt++) {
			int categorySeq = inappcategoryList.get(hccnt).getCategorySeq();
			int inappCategorySeq = inappList.get(inappCnt).getCategorySeq();
			if (categorySeq == inappCategorySeq) {
				captureVO.setCaptureGb("2");
				captureVO.setBoardSeq(inappList.get(inappCnt).getInappSeq());
				List<CaptureVO> tempCaptureList = captureService.selectListByBoardSeqWithGb(captureVO);
				decentInappList.add(new RequestedInAppJsonVO(inappList.get(inappCnt), inappcategoryList.get(hccnt),
						iconURL, captureURL, basicURL, inappURL, tempCaptureList));
			}
		}
	}

	private void unzip(String saveFileName, HttpServletRequest request) {
		byte[] buffer = new byte[1024];
		String savePath = messageSource.getMessage("file.path.basic.URL", null, localeResolver.resolveLocale(request))
				+ messageSource.getMessage("file.path.contents.file", null, localeResolver.resolveLocale(request));

		File folder = new File(savePath + saveFileName + "/view");
		if (!folder.exists()) {
			folder.mkdir();
		}
		try {

			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(savePath + saveFileName + "/" + saveFileName + ".zip"));
			ZipEntry ze = zis.getNextEntry();

			System.out.println("@@@@@@ze = " + ze);

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(savePath + saveFileName + "/view" + File.separator + fileName);

				System.out.println("file unzip : " + newFile.getAbsoluteFile());

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				if (ze.isDirectory()) {
					System.out.println("[IS A DIRECTORY]");
					String temp = newFile.getCanonicalPath();
					new File(temp).mkdir();
				} else {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean dateIsNotSame(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		DateFormat inputDF = new SimpleDateFormat("MM/dd/yy");
		Date currentDate = new Date();
		Date formattedDate;
		System.out.println("date = " + date);
		System.out.println("currentDate = " + currentDate);
		try {
			formattedDate = inputDF.parse(format.format(date));

			Calendar cal = Calendar.getInstance();
			cal.setTime(formattedDate);

			int day = cal.get(Calendar.DAY_OF_MONTH);

			formattedDate = inputDF.parse(format.format(currentDate));

			cal.setTime(formattedDate);

			int cday = cal.get(Calendar.DAY_OF_MONTH);

			System.out.println("day= " + day + "cday = " + cday);
			if (day != cday)
				return true;
			else
				return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}
	}
}