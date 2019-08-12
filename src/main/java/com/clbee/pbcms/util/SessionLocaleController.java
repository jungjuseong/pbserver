package com.clbee.pbcms.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Controller
public class SessionLocaleController {
	 @RequestMapping(value = "setChangeLocale.html")
	 public String changeLocale(@RequestParam(required = false) String locale,
			  ModelMap map, HttpServletRequest request, HttpServletResponse response) {
			  HttpSession session = request.getSession();
			    Locale locales = null;
			  // �Ѿ�� �Ķ���Ϳ� ko�� ������ Locale�� �� �ѱ���� �ٲ��ش�.
			  // �׷��� ���� ��� ����� �����Ѵ�.
			   if(ConditionCompile.isJapan){
				  if (locale.matches("ko")) {
				   locales = Locale.JAPAN;
				  } else {
				  //locales = Locale.ENGLISH;
				   locales = Locale.JAPAN;
				  }
			   }else{
				  if(locale.matches("ko")) {
					locales = Locale.KOREAN;
				  } else {
					//locales = Locale.ENGLISH;
					locales = Locale.KOREAN;
				  } 
			   }
			  
			  // ���ǿ� �����ϴ� Locale�� ���ο� ���� �������ش�.
			  session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locales);
			  // �ش� ��Ʈ�ѷ����� ��û�� ���� �ּҷ� ���ư���.
			  return "redirect:/index.html";
	}
}
