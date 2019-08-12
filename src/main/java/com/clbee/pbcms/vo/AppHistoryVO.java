package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.clbee.pbcms.Json.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;



/**
 * The persistent class for the tb_app database table.
 * 
 */
@Entity
//@Table(name="tb_apphistory", catalog="pbcms_test")
@Table(name="tb_apphistory", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AppHistoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="app_seq")
	private Integer appSeq;

	@Column(name="app_contents_amt")
	private String appContentsAmt;

	@Column(name="app_contents_gb")
	private String appContentsGb;

	@Column(name="app_name")
	private String appName;

	private String app_resultCode;

	@Column(name="app_size")
	private String appSize;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Lob
	@Column(name="chg_text")
	private String chgText;

	@Column(name="chg_user_gb")
	private String chgUserGb;

	@Column(name="chg_user_id")
	private String chgUserId;

	@Column(name="chg_user_seq")
	private Integer chgUserSeq;

	@Column(name="complet_gb")
	private String completGb;

	@Column(name="coupon_gb")
	private String couponGb;

	@Column(name="coupon_num")
	private String couponNum;

	@Lob
	@Column(name="description_text")
	private String descriptionText;

	@Column(name="distr_gb")
	private String distrGb;

	@Column(name="file_name")
	private String fileName;

	@Column(name="icon_org_file")
	private String iconOrgFile;

	@Column(name="icon_save_file")
	private String iconSaveFile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="limit_dt")
	private Date limitDt;

	@Column(name="limit_gb")
	private String limitGb;

	@Column(name="mem_down_amt")
	private String memDownAmt;

	@Column(name="mem_down_cnt")
	private String memDownCnt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mem_down_end_dt")
	private Date memDownEndDt;

	@Column(name="mem_down_gb")
	private String memDownGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mem_down_start_dt")
	private Date memDownStartDt;

	@Column(name="nonmem_down_amt")
	private String nonmemDownAmt;

	@Column(name="nonmem_down_cnt")
	private String nonmemDownCnt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="nonmem_down_end_dt")
	private Date nonmemDownEndDt;

	@Column(name="nonmem_down_gb")
	private String nonmemDownGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="nonmem_down_star_dt")
	private Date nonmemDownStarDt;

	@Column(name="install_gb")
	private String installGb;
	
	private String ostype;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_gb")
	private String regGb;

	@Column(name="reg_user_gb")
	private String regUserGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="reg_user_seq")
	private Integer regUserSeq;

	@Column(name="store_bundle_id")
/*	@Formula("(select tb_bundle.bundle_name from tb_bundle where tb_bundle.app_seq = app_seq limit 1)")*/
	private String storeBundleId;
	
	@Column(name="provision_gb")
	private String provisionGb;

	@Column(name="template_name")
	private String templateName;

	@Column(name="template_seq")
	private Integer templateSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="use_avail_dt")
	private Date useAvailDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="use_disable_dt")
	private Date useDisableDt;

	@Column(name="use_gb")
	private String useGb;

	@Column(name="ver_num")
	private String verNum;

	@Column(name="version_code")
	private String versionCode;
	
	@Column(name="use_user_gb")
	private String useUserGb;

	@Column(name="login_time")
	private String loginTime;
	
	@Column(name="logout_time")
	private String logoutTime;
	
	@Column(name="login_gb")
	private String loginGb;
	
	@Column(name="app_schema")
	private String appSchema;
	
	@Column(name="app_host")
	private String appHost;

	//20180403 : lsy - add column complet_dt, chg_contents_dt
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "complet_dt")
	private Date completDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "chg_contents_dt")
	private Date chgContentsDt;
	
	@Column(name="distribute_accept_id")
	private String distributeAcceptId;
	
	@Column(name="distribute_request_id")
	private String distributeRequestId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "distribute_req_dt")
	private Date distributeReqDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "distribute_complet_dt")
	private Date distributeCompletDt;
	//20180403 : lsy - add column complet_dt, chg_contents_dt - end

	public AppHistoryVO(){
		
	}

	public AppHistoryVO( LinkedHashMap<Object, Object> map) throws ParseException {
		super();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		this.appSeq = ((Long)map.get("appSeq")).intValue();
		this.appContentsAmt = (String)map.get("appContentsAmt");
		this.appContentsGb = (String)map.get("appContentsGb");
		this.appName = (String)map.get("appName");
		this.app_resultCode = (String)map.get("app_resultCode");
		this.appSize = (String)map.get("appSize");
		if((String)map.get("chgDt") != null)
		this.chgDt = transFormat.parse((String)map.get("chgDt"));
		this.chgText = (String)map.get("chgText");
		this.chgUserGb = (String)map.get("chgUserGb");
		this.chgUserId = (String)map.get("chgUserId");
		this.chgUserSeq =((Long)map.get("chgUserSeq")).intValue();
		this.completGb = (String)map.get("completGb");
		this.couponGb = (String)map.get("couponGb");
		this.couponNum = (String)map.get("couponNum");
		this.descriptionText = (String)map.get("descriptionText");
		this.distrGb = (String)map.get("distrGb");
		this.fileName = (String)map.get("fileName");
		this.iconOrgFile = (String)map.get("iconOrgFile");
		this.iconSaveFile = (String)map.get("iconSaveFile");
		if((String)map.get("limitDt") != null)
		this.limitDt = transFormat.parse((String)map.get("limitDt"));
		this.limitGb = (String)map.get("limitGb");
		this.memDownAmt = (String)map.get("memDownAmt");
		this.memDownCnt = (String)map.get("memDownCnt");
		if((String)map.get("memDownEndDt") != null)
		this.memDownEndDt = transFormat.parse((String)map.get("memDownEndDt"));
		this.memDownGb = (String)map.get("memDownGb");
		if((String)map.get("memDownStartDt") != null)
		this.memDownStartDt = transFormat.parse((String)map.get("memDownStartDt"));
		this.nonmemDownAmt = (String)map.get("nonmemDownAmt");
		this.nonmemDownCnt = (String)map.get("nonmemDownCnt");
		if((String)map.get("nonmemDownEndDt") != null)
		this.nonmemDownEndDt = transFormat.parse((String)map.get("nonmemDownEndDt"));
		this.nonmemDownGb = (String)map.get("nonmemDownGb");
		if((String)map.get("nonmemDownStarDt") != null)
		this.nonmemDownStarDt = transFormat.parse((String)map.get("nonmemDownStarDt"));
		this.installGb = (String)map.get("installGb");
		this.ostype = (String)map.get("ostype");
		if((String)map.get("regDt") != null)
		this.regDt = transFormat.parse((String)map.get("regDt"));
		this.regGb = (String)map.get("regGb");
		this.regUserGb = (String)map.get("regUserGb");
		this.regUserId = (String)map.get("regUserId");
		this.regUserSeq = ((Long)map.get("regUserSeq")).intValue();
		this.storeBundleId = (String)map.get("storeBundleId");
		this.provisionGb = (String)map.get("provisionGb");
		this.templateName = (String)map.get("templateName");
		if(map.get("templateSeq") != null)
		this.templateSeq = ((Long)map.get("templateSeq")).intValue();
		if((String)map.get("useAvailDt") != null)
		this.useAvailDt = transFormat.parse((String)map.get("useAvailDt"));
		if((String)map.get("useDisableDt") != null)
		this.useDisableDt = transFormat.parse((String)map.get("useDisableDt"));
		this.useGb = (String)map.get("useGb");
		this.verNum = (String)map.get("verNum");
		this.versionCode = (String)map.get("versionCode");
		this.useUserGb = (String)map.get("useUserGb");
		this.loginGb = (String)map.get("loginGb");
		this.loginTime = (String)map.get("loginTime");
		this.logoutTime = (String)map.get("logoutTime");
		this.appSchema = (String)map.get("appSchema");
		this.appHost = (String)map.get("appHost");

		//20180403 : lsy - add column complet_dt, chg_contents_dt
		if ((String) map.get("completDt") != null) {
			completDt = transFormat.parse((String) map.get("completDt"));
		}
		if ((String) map.get("distributeReqDt") != null) {
			distributeReqDt = transFormat.parse((String) map.get("distributeReqDt"));
		}
		if ((String) map.get("chgContentsDt") != null) {
			chgContentsDt = transFormat.parse((String) map.get("chgContentsDt"));
		}
		if ((String) map.get("distributeCompletDt") != null) {
			distributeCompletDt = transFormat.parse((String) map.get("distributeCompletDt"));
		}
		distributeAcceptId = ((String) map.get("distributeAcceptId"));
		distributeRequestId = ((String) map.get("distributeRequestId"));
		//20180403 : lsy - add column complet_dt, chg_contents_dt - end
	}

	public void setAppVO( AppHistoryVO updatedVO){
		
		this.appContentsAmt = updatedVO.getAppContentsAmt();
		this.appContentsGb = updatedVO.getAppContentsGb();
		this.appName = updatedVO.getAppName();
		this.app_resultCode = updatedVO.getApp_resultCode();
		this.appSize = updatedVO.getAppSize();
		this.chgDt = updatedVO.getChgDt();
		this.chgText = updatedVO.getChgText();
		this.chgUserGb = updatedVO.getChgUserGb();
		this.chgUserId = updatedVO.getChgUserId();
		this.chgUserSeq = updatedVO.getChgUserSeq();
		this.completGb = updatedVO.getCompletGb();
		this.couponGb = updatedVO.getCouponGb();
		this.couponNum = updatedVO.getCouponNum();
		this.descriptionText = updatedVO.getDescriptionText();
		this.distrGb = updatedVO.getDistrGb();
		this.fileName = updatedVO.getFileName();
		this.iconOrgFile = updatedVO.getIconOrgFile();
		this.iconSaveFile = updatedVO.getIconSaveFile();
		this.limitDt = updatedVO.getLimitDt();
		this.limitGb = updatedVO.getLimitGb();
		this.memDownAmt = updatedVO.getMemDownAmt();
		this.memDownCnt = updatedVO.getMemDownCnt();
		this.memDownEndDt = updatedVO.getMemDownEndDt();
		this.memDownGb = updatedVO.getMemDownGb();
		this.memDownStartDt = updatedVO.getMemDownStartDt();
		this.nonmemDownAmt = updatedVO.getNonmemDownAmt();
		this.nonmemDownCnt = updatedVO.getNonmemDownCnt();
		this.nonmemDownEndDt = updatedVO.getNonmemDownEndDt();
		this.nonmemDownGb = updatedVO.getNonmemDownGb();
		this.nonmemDownStarDt = updatedVO.getNonmemDownStarDt();
		this.installGb = updatedVO.getInstallGb();
		this.ostype = updatedVO.getOstype();
		this.regDt = updatedVO.getRegDt();
		this.regGb = updatedVO.getRegGb();
		this.regUserGb = updatedVO.getRegUserGb();
		this.regUserId = updatedVO.getRegUserId();
		this.regUserSeq = updatedVO.getRegUserSeq();
		this.storeBundleId = updatedVO.getStoreBundleId();
		this.provisionGb = updatedVO.getProvisionGb();
		this.templateName = updatedVO.getTemplateName();
		this.templateSeq = updatedVO.getTemplateSeq();
		this.useAvailDt = updatedVO.getUseAvailDt();
		this.useDisableDt = updatedVO.getUseDisableDt();
		this.useGb = updatedVO.getUseGb();
		this.verNum = updatedVO.getVerNum();
		this.versionCode = updatedVO.getVersionCode();
		this.useUserGb = updatedVO.getUseUserGb();
		this.loginGb = updatedVO.getLoginGb();
		this.loginTime = updatedVO.getLoginTime();
		this.logoutTime = updatedVO.getLogoutTime();
		this.appSchema = updatedVO.getAppSchema();
		this.appHost = updatedVO.getAppHost();

		//20180417 : lsy - add column distribute_accept_id
		this.distributeReqDt = updatedVO.getDistributeReqDt();
		this.chgContentsDt = updatedVO.getChgContentsDt();
		this.distributeCompletDt = updatedVO.getDistributeCompletDt();
		this.distributeAcceptId = updatedVO.getDistributeAcceptId();
		this.distributeRequestId = updatedVO.getDistributeRequestId();
		//20180417 : lsy - add column distribute_accept_id - end
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
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getChgDt() {
		return this.chgDt;
	}

	public void setChgDt(Date chgDt) {
		this.chgDt = chgDt;
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

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLimitDt() {
		return this.limitDt;
	}

	public void setLimitDt(Date limitDt) {
		this.limitDt = limitDt;
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

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getMemDownEndDt() {
		return this.memDownEndDt;
	}

	public void setMemDownEndDt(Date memDownEndDt) {
		this.memDownEndDt = memDownEndDt;
	}

	public String getMemDownGb() {
		return this.memDownGb;
	}

	public void setMemDownGb(String memDownGb) {
		this.memDownGb = memDownGb;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getMemDownStartDt() {
		return this.memDownStartDt;
	}

	public void setMemDownStartDt(Date memDownStartDt) {
		this.memDownStartDt = memDownStartDt;
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

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getNonmemDownEndDt() {
		return this.nonmemDownEndDt;
	}

	public void setNonmemDownEndDt(Date nonmemDownEndDt) {
		this.nonmemDownEndDt = nonmemDownEndDt;
	}

	public String getNonmemDownGb() {
		return this.nonmemDownGb;
	}

	public void setNonmemDownGb(String nonmemDownGb) {
		this.nonmemDownGb = nonmemDownGb;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getNonmemDownStarDt() {
		return this.nonmemDownStarDt;
	}

	public void setNonmemDownStarDt(Date nonmemDownStarDt) {
		this.nonmemDownStarDt = nonmemDownStarDt;
	}

	public String getInstallGb() {
		return installGb;
	}

	public void setInstallGb(String installGb) {
		this.installGb = installGb;
	}
	
	public String getOstype() {
		return this.ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getRegDt() {
		return this.regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
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

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUseAvailDt() {
		return this.useAvailDt;
	}

	public void setUseAvailDt(Date useAvailDt) {
		this.useAvailDt = useAvailDt;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUseDisableDt() {
		return this.useDisableDt;
	}

	public void setUseDisableDt(Date useDisableDt) {
		this.useDisableDt = useDisableDt;
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

	
	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}


	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( nullable=true, name="reg_user_seq", referencedColumnName="user_seq", insertable=false, updatable=false)
	private MemberVO regMemberVO;
	
	public MemberVO getRegMemberVO() {
		return regMemberVO;
	}

	public void setRegMemberVO(MemberVO regMemberVO) {
		this.regMemberVO = regMemberVO;
	}

	public MemberVO getChgMemberVO() {
		return chgMemberVO;
	}

	public void setChgMemberVO(MemberVO chgMemberVO) {
		this.chgMemberVO = chgMemberVO;
	}
	
	public String getAppSchema() {
		return appSchema;
	}

	public void setAppSchema(String appSchema) {
		this.appSchema = appSchema;
	}
	
	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( nullable=true, name="chg_user_seq",  referencedColumnName="user_seq", insertable=false, updatable=false)
	private MemberVO chgMemberVO;
	
	public String getUseUserGb() {
		return useUserGb;
	}

	public void setUseUserGb(String useUserGb) {
		this.useUserGb = useUserGb;
	}

	@Override
	public String toString() {
		return "AppVO [appSeq=" + appSeq + ", appContentsAmt=" + appContentsAmt
				+ ", appContentsGb=" + appContentsGb + ", appName=" + appName
				+ ", app_resultCode=" + app_resultCode + ", appSize=" + appSize
				+ ", chgDt=" + chgDt + ", chgText=" + chgText + ", chgUserGb="
				+ chgUserGb + ", chgUserId=" + chgUserId + ", chgUserSeq="
				+ chgUserSeq + ", completGb=" + completGb + ", couponGb="
				+ couponGb + ", couponNum=" + couponNum + ", descriptionText="
				+ descriptionText + ", distrGb=" + distrGb + ", fileName="
				+ fileName + ", iconOrgFile=" + iconOrgFile + ", iconSaveFile="
				+ iconSaveFile + ", limitDt=" + limitDt + ", limitGb="
				+ limitGb + ", memDownAmt=" + memDownAmt + ", memDownCnt="
				+ memDownCnt + ", memDownEndDt=" + memDownEndDt
				+ ", memDownGb=" + memDownGb + ", memDownStartDt="
				+ memDownStartDt + ", nonmemDownAmt=" + nonmemDownAmt
				+ ", nonmemDownCnt=" + nonmemDownCnt + ", nonmemDownEndDt="
				+ nonmemDownEndDt + ", nonmemDownGb=" + nonmemDownGb
				+ ", nonmemDownStarDt=" + nonmemDownStarDt + ", ostype="
				+ ostype + ", regDt=" + regDt + ", regGb=" + regGb
				+ ", regUserGb=" + regUserGb + ", regUserId=" + regUserId
				+ ", regUserSeq=" + regUserSeq + ", storeBundleId="
				+ storeBundleId + ", provisionGb=" + provisionGb
				+ ", templateName=" + templateName + ", templateSeq="
				+ templateSeq + ", useAvailDt=" + useAvailDt
				+ ", useDisableDt=" + useDisableDt + ", useGb=" + useGb
				+ ", verNum=" + verNum + ", regMemberVO=" + regMemberVO
				+ ", chgMemberVO=" + chgMemberVO
				+ ", appSchema=" + appSchema
				+ ", appHost=" + appHost + "]";
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

	public Date getCompletDt() {
		return completDt;
	}

	public void setCompletDt(Date completDt) {
		this.completDt = completDt;
	}

	public Date getChgContentsDt() {
		return chgContentsDt;
	}

	public void setChgContentsDt(Date chgContentsDt) {
		this.chgContentsDt = chgContentsDt;
	}

	public String getDistributeAcceptId() {
		return distributeAcceptId;
	}

	public void setDistributeAcceptId(String distributeAcceptId) {
		this.distributeAcceptId = distributeAcceptId;
	}

	public String getDistributeRequestId() {
		return distributeRequestId;
	}

	public void setDistributeRequestId(String distributeRequestId) {
		this.distributeRequestId = distributeRequestId;
	}	
	
	public Date getDistributeCompletDt() {
		return distributeCompletDt;
	}

	public void setDistributeCompletDt(Date distributeCompletDt) {
		this.distributeCompletDt = distributeCompletDt;
	}

	public Date getDistributeReqDt() {
		return distributeReqDt;
	}

	public void setDistributeReqDt(Date distributeReqDt) {
		this.distributeReqDt = distributeReqDt;
	}
	
}