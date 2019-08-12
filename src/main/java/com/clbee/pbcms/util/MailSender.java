package com.clbee.pbcms.util;

import java.io.Console;
import java.util.Locale;
import java.util.Properties;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MailSender {

	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();

		if(ConditionCompile.isIOS) {
//			bundleMessageSource.setBasenames("file:/usr/local/context-common","classpath:messages", "file:/usr/local/limit-user");
			bundleMessageSource.setBasenames("file:/usr/local/context-common-new","classpath:messages", "file:/usr/local/limit-user");
		}else {
//			bundleMessageSource.setBasenames("file:C:/context-common","classpath:messages", "file:C:/limit-user");
			bundleMessageSource.setBasenames("file:C:/context-common-new","classpath:messages", "file:C:/limit-user");
		}
		bundleMessageSource.setCacheSeconds(1);
		bundleMessageSource.setDefaultEncoding("UTF-8");
		return bundleMessageSource;
	}
	
	@Bean(name = "localeResolver")
	 public LocaleResolver sessionLocaleResolver(){
	     SessionLocaleResolver localeResolver=new SessionLocaleResolver();
	     
	     if(ConditionCompile.isJapan){
	    	 localeResolver.setDefaultLocale(new Locale("ja"));
	     }
	     else{
	    	 localeResolver.setDefaultLocale(new Locale("ko"));
	     }
	     return localeResolver;
	 }  
	@Bean
	public JavaMailSender javaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		
		mailSender.setJavaMailProperties(props);
		
		if(ConditionCompile.isJapan){
			mailSender.setHost(messageSource().getMessage("send.email.domain", null, new Locale("ja")));
			mailSender.setUsername(messageSource().getMessage("send.email.ID", null, new Locale("ja")));
			mailSender.setPassword(messageSource().getMessage("send.email.PW", null, new Locale("ja")));
		}else{
			mailSender.setHost(messageSource().getMessage("send.email.domain", null, new Locale("ko")));
			mailSender.setUsername(messageSource().getMessage("send.email.ID", null, new Locale("ko")));
			mailSender.setPassword(messageSource().getMessage("send.email.PW", null, new Locale("ko")));
		}
		return mailSender;
	}	
	

	      

}