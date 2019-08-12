package com.clbee.pbcms.util.crontab;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.clbee.pbcms.dao.LicenseDao;
import com.clbee.pbcms.util.ConditionCompile;

@Component
public class Scheduler {

	@Autowired
	LicenseDao licenseDao;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	MessageSource messageSource;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void licenseExpireEveryday() {
		licenseDao.licenseExpireEveryday("licenseExpireEveryday");
	}
	
	@Scheduled(cron = "0 0 10 * * *")
	public void licenseExpireAlertEveryday() {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			Locale locale = null;
			if(ConditionCompile.isJapan){
				locale = new Locale("ja");
			}else {
				locale = new Locale("ko");
			}
			
			messageHelper.setSubject(messageSource.getMessage("license.mail.subject", null, locale));
			messageHelper.setFrom(messageSource.getMessage("send.email.ID", null, locale));

			List<HashMap<String, String>> expireScheduleLicense = licenseDao.licenseExpireAlertEveryday("licenseExpireAlertEveryday");
			for(int i=0;i<expireScheduleLicense.size();i++) {
				messageHelper.setTo(expireScheduleLicense.get(i).get("email"));
				messageHelper.setText(
						expireScheduleLicense.get(0).get("lastName")+expireScheduleLicense.get(0).get("firstName")+messageSource.getMessage("license.mail.text.001", null, locale)+" "
						+expireScheduleLicense.get(0).get("expireDt")+messageSource.getMessage("license.mail.text.002", null, locale)
						+"\n"+messageSource.getMessage("license.mail.text.003", null, locale)
				);
				javaMailSender.send(message);
			}
		} catch(Exception e){
			System.out.println(e);
		}
			
		
	}

}
