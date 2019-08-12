package com.clbee.pbcms.Json;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.MemberVO;

public class AppVOForConnection implements Serializable {
			private static final long serialVersionUID = 1L;

			private Integer appSeq;
			private String appContentsAmt;
			private String appContentsGb;
			private String appName;
			private String app_resultCode;
			private String appSize;
			private String chgDt;
			private String chgText;
			private String chgUserGb;
			private String chgUserId;
			private Integer chgUserSeq;
			private String completGb;
			private String couponGb;
			private String couponNum;
			private String descriptionText;
			private String distrGb;
			private String fileName;
			private String iconOrgFile;
			private String iconSaveFile;
			private String limitDt;
			private String limitGb;
			private String memDownAmt;
			private String memDownCnt;
			private String memDownEndDt;
			private String memDownGb;
			private String memDownStartDt;
			private String nonmemDownAmt;
			private String nonmemDownCnt;
			private String nonmemDownEndDt;
			private String nonmemDownGb;
			private String nonmemDownStarDt;
			private String installGb;
			private String ostype;
			private String regDt;
			private String regGb;
			private String regUserGb;
			private String regUserId;
			private Integer regUserSeq;
			private String storeBundleId;
			private String provisionGb;
			private String templateName;
			private Integer templateSeq;
			private String useAvailDt;
			private String useDisableDt;
			private String useGb;
			private String verNum;
			private String versionCode;
			private String useUserGb;
			private String loginTime;
			private String logoutTime;
			private String loginGb;

			public AppVOForConnection( AppVO appVO ) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//edit for the    format you need
				
				this.appSeq = appVO.getAppSeq();
				this.appContentsAmt = appVO.getAppContentsAmt();
				this.appContentsGb = appVO.getAppContentsGb();
				this.appName = appVO.getAppName();
				this.app_resultCode = appVO.getApp_resultCode();
				this.appSize = appVO.getAppSize();
				if(appVO.getChgDt() !=null)
				this.chgDt = format.format(appVO.getChgDt());
				this.chgText = appVO.getChgText();
				this.chgUserGb = appVO.getChgUserGb();
				this.chgUserId = appVO.getChgUserId();
				this.chgUserSeq = appVO.getChgUserSeq();
				this.completGb = appVO.getCompletGb();
				this.couponGb = appVO.getCouponGb();
				this.couponNum = appVO.getCouponNum();
				this.descriptionText = appVO.getDescriptionText();
				this.distrGb = appVO.getDistrGb();
				this.fileName = appVO.getFileName();
				this.iconOrgFile = appVO.getIconOrgFile();
				this.iconSaveFile = appVO.getIconSaveFile();
				if(appVO.getLimitDt() !=null)
				this.limitDt = format.format(appVO.getLimitDt());
				this.limitGb = appVO.getLimitGb();
				this.memDownAmt = appVO.getMemDownAmt();
				this.memDownCnt = appVO.getMemDownCnt();
				if(appVO.getMemDownEndDt() !=null)
				this.memDownEndDt = format.format(appVO.getMemDownEndDt());
				this.memDownGb = appVO.getMemDownGb();
				if(appVO.getMemDownStartDt() !=null)
				this.memDownStartDt = format.format(appVO.getMemDownStartDt());
				this.nonmemDownAmt = appVO.getNonmemDownAmt();
				this.nonmemDownCnt = appVO.getNonmemDownCnt();
				if(appVO.getNonmemDownEndDt() !=null)
				this.nonmemDownEndDt = format.format(appVO.getNonmemDownEndDt());
				this.nonmemDownGb = appVO.getNonmemDownGb();
				if(appVO.getNonmemDownStarDt() !=null)
				this.nonmemDownStarDt = format.format(appVO.getNonmemDownStarDt());
				this.installGb = appVO.getInstallGb();
				this.ostype = appVO.getOstype();
				if(appVO.getRegDt() !=null)
				this.regDt = format.format(appVO.getRegDt());
				this.regGb = appVO.getRegGb();
				this.regUserGb = appVO.getRegUserGb();
				this.regUserId = appVO.getRegUserId();
				this.regUserSeq = appVO.getRegUserSeq();
				this.storeBundleId = appVO.getStoreBundleId();
				this.provisionGb = appVO.getProvisionGb();
				this.templateName = appVO.getTemplateName();
				this.templateSeq = appVO.getTemplateSeq();
				if(appVO.getUseAvailDt() !=null)
				this.useAvailDt = format.format(appVO.getUseAvailDt());
				if(appVO.getUseDisableDt() !=null)
				this.useDisableDt = format.format(appVO.getUseDisableDt());
				this.useGb = appVO.getUseGb();
				this.verNum = appVO.getVerNum();
				this.versionCode = appVO.getVersionCode();
				this.useUserGb = appVO.getUseUserGb();
				this.loginTime = appVO.getLoginTime();
				this.logoutTime = appVO.getLogoutTime();
				this.loginGb = appVO.getLoginGb();
			}

			public Integer getAppSeq() {
				return this.appSeq;
			}

			public void setAppSeq(Integer appSeq) {
				this.appSeq = appSeq;
			}

			public String getAppContentsAmt() {
				return this.appContentsAmt;
			}

			public void setAppContentsAmt(String appContentsAmt) {
				this.appContentsAmt = appContentsAmt;
			}

			public String getAppContentsGb() {
				return this.appContentsGb;
			}

			public void setAppContentsGb(String appContentsGb) {
				this.appContentsGb = appContentsGb;
			}

			public String getAppName() {
				return this.appName;
			}

			public void setAppName(String appName) {
				this.appName = appName;
			}

			public String getApp_resultCode() {
				return this.app_resultCode;
			}

			public void setApp_resultCode(String app_resultCode) {
				this.app_resultCode = app_resultCode;
			}

			public String getAppSize() {
				return this.appSize;
			}

			public void setAppSize(String appSize) {
				this.appSize = appSize;
			}

			public String getChgText() {
				return this.chgText;
			}

			public void setChgText(String chgText) {
				this.chgText = chgText;
			}

			public String getChgUserGb() {
				return this.chgUserGb;
			}

			public void setChgUserGb(String chgUserGb) {
				this.chgUserGb = chgUserGb;
			}

			public String getChgUserId() {
				return this.chgUserId;
			}

			public void setChgUserId(String chgUserId) {
				this.chgUserId = chgUserId;
			}

			public Integer getChgUserSeq() {
				return this.chgUserSeq;
			}

			public void setChgUserSeq(Integer chgUserSeq) {
				this.chgUserSeq = chgUserSeq;
			}

			public String getCompletGb() {
				return this.completGb;
			}

			public void setCompletGb(String completGb) {
				this.completGb = completGb;
			}

			public String getCouponGb() {
				return this.couponGb;
			}

			public void setCouponGb(String couponGb) {
				this.couponGb = couponGb;
			}

			public String getCouponNum() {
				return this.couponNum;
			}

			public void setCouponNum(String couponNum) {
				this.couponNum = couponNum;
			}

			public String getDescriptionText() {
				return this.descriptionText;
			}

			public void setDescriptionText(String descriptionText) {
				this.descriptionText = descriptionText;
			}

			public String getDistrGb() {
				return this.distrGb;
			}

			public void setDistrGb(String distrGb) {
				this.distrGb = distrGb;
			}

			public String getFileName() {
				return this.fileName;
			}

			public void setFileName(String fileName) {
				this.fileName = fileName;
			}

			public String getIconOrgFile() {
				return this.iconOrgFile;
			}

			public void setIconOrgFile(String iconOrgFile) {
				this.iconOrgFile = iconOrgFile;
			}

			public String getIconSaveFile() {
				return this.iconSaveFile;
			}

			public void setIconSaveFile(String iconSaveFile) {
				this.iconSaveFile = iconSaveFile;
			}


			public String getLimitGb() {
				return this.limitGb;
			}

			public void setLimitGb(String limitGb) {
				this.limitGb = limitGb;
			}

			public String getMemDownAmt() {
				return this.memDownAmt;
			}

			public void setMemDownAmt(String memDownAmt) {
				this.memDownAmt = memDownAmt;
			}

			public String getMemDownCnt() {
				return this.memDownCnt;
			}

			public void setMemDownCnt(String memDownCnt) {
				this.memDownCnt = memDownCnt;
			}

			public String getMemDownGb() {
				return this.memDownGb;
			}

			public void setMemDownGb(String memDownGb) {
				this.memDownGb = memDownGb;
			}

			public String getNonmemDownAmt() {
				return this.nonmemDownAmt;
			}

			public void setNonmemDownAmt(String nonmemDownAmt) {
				this.nonmemDownAmt = nonmemDownAmt;
			}

			public String getNonmemDownCnt() {
				return this.nonmemDownCnt;
			}

			public void setNonmemDownCnt(String nonmemDownCnt) {
				this.nonmemDownCnt = nonmemDownCnt;
			}
			
			public String getNonmemDownGb() {
				return this.nonmemDownGb;
			}

			public void setNonmemDownGb(String nonmemDownGb) {
				this.nonmemDownGb = nonmemDownGb;
			}

			public String getInstallGb() {
				return installGb;
			}

			public void setInstallGb(String installGb) {
				this.installGb = installGb;
			}

			public String getVersionCode() {
				return versionCode;
			}

			public void setVersionCode(String versionCode) {
				this.versionCode = versionCode;
			}

			public String getOstype() {
				return this.ostype;
			}

			public void setOstype(String ostype) {
				this.ostype = ostype;
			}

			public String getRegGb() {
				return this.regGb;
			}

			public void setRegGb(String regGb) {
				this.regGb = regGb;
			}

			public String getRegUserGb() {
				return this.regUserGb;
			}

			public void setRegUserGb(String regUserGb) {
				this.regUserGb = regUserGb;
			}

			public String getRegUserId() {
				return this.regUserId;
			}

			public void setRegUserId(String regUserId) {
				this.regUserId = regUserId;
			}

			public Integer getRegUserSeq() {
				return this.regUserSeq;
			}

			public void setRegUserSeq(Integer regUserSeq) {
				this.regUserSeq = regUserSeq;
			}
			
			public String getStoreBundleId() {
				return storeBundleId;
			}

			public void setStoreBundleId(String storeBundleId) {
				this.storeBundleId = storeBundleId;
			}

			public String getProvisionGb() {
				return this.provisionGb;
			}

			public void setProvisionGb(String provisionGb) {
				this.provisionGb = provisionGb;
			}

			public String getTemplateName() {
				return this.templateName;
			}

			public void setTemplateName(String templateName) {
				this.templateName = templateName;
			}

			public Integer getTemplateSeq() {
				return this.templateSeq;
			}

			public void setTemplateSeq(Integer templateSeq) {
				this.templateSeq = templateSeq;
			}

			public String getUseGb() {
				return this.useGb;
			}

			public void setUseGb(String useGb) {
				this.useGb = useGb;
			}

			public String getVerNum() {
				return this.verNum;
			}

			public void setVerNum(String verNum) {
				this.verNum = verNum;
			}

			public String getChgDt() {
				return chgDt;
			}

			public void setChgDt(String chgDt) {
				this.chgDt = chgDt;
			}

			public String getLimitDt() {
				return limitDt;
			}

			public void setLimitDt(String limitDt) {
				this.limitDt = limitDt;
			}

			public String getMemDownEndDt() {
				return memDownEndDt;
			}

			public void setMemDownEndDt(String memDownEndDt) {
				this.memDownEndDt = memDownEndDt;
			}

			public String getMemDownStartDt() {
				return memDownStartDt;
			}

			public void setMemDownStartDt(String memDownStartDt) {
				this.memDownStartDt = memDownStartDt;
			}

			public String getNonmemDownEndDt() {
				return nonmemDownEndDt;
			}

			public void setNonmemDownEndDt(String nonmemDownEndDt) {
				this.nonmemDownEndDt = nonmemDownEndDt;
			}

			public String getNonmemDownStarDt() {
				return nonmemDownStarDt;
			}

			public void setNonmemDownStarDt(String nonmemDownStarDt) {
				this.nonmemDownStarDt = nonmemDownStarDt;
			}

			public String getRegDt() {
				return regDt;
			}

			public void setRegDt(String regDt) {
				this.regDt = regDt;
			}

			public String getUseAvailDt() {
				return useAvailDt;
			}

			public void setUseAvailDt(String useAvailDt) {
				this.useAvailDt = useAvailDt;
			}

			public String getUseDisableDt() {
				return useDisableDt;
			}

			public void setUseDisableDt(String useDisableDt) {
				this.useDisableDt = useDisableDt;
			}

			public String getUseUserGb() {
				return useUserGb;
			}

			public void setUseUserGb(String useUserGb) {
				this.useUserGb = useUserGb;
			}

			public String getLoginTime() {
				return loginTime;
			}

			public void setLoginTime(String loginTime) {
				this.loginTime = loginTime;
			}

			public String getLogoutTime() {
				return logoutTime;
			}

			public void setLogoutTime(String logoutTime) {
				this.logoutTime = logoutTime;
			}

			public String getLoginGb() {
				return loginGb;
			}

			public void setLoginGb(String loginGb) {
				this.loginGb = loginGb;
			}
			
			
			
}
