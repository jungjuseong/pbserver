package com.clbee.pbcms.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class JavaMessageSource {
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
}
