package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.clbee.pbcms.Json.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tb_template database table.
 * 
 */
@Entity
//@Table(name="tb_template", catalog="pbcms_test")
@Table(name="tb_template", catalog="pbcms_new")
public class TemplateVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="template_seq")
	private int templateSeq;

	@Column(name="app_contents_amt")
	private String appContentsAmt;

	@Column(name="app_contents_gb")
	private String appContentsGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chg_user_gb")
	private String chgUserGb;

	@Column(name="chg_user_id")
	private String chgUserId;

	@Column(name="chg_user_seq")
	private int chgUserSeq;

	@Column(name="complet_gb")
	private String completGb;

	@Lob
	@Column(name="description_text")
	private String descriptionText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="limit_dt")
	private Date limitDt;

	@Column(name="limit_gb")
	private String limitGb;

	@Column(name="ostype_gb")
	private String ostypeGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_user_gb")
	private String regUserGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="reg_user_seq")
	private int regUserSeq;

	@Column(name="template_name")
	private String templateName;

	@Column(name="template_type_gb")
	private String templateTypeGb;

	@Column(name="upload_org_file")
	private String uploadOrgFile;

	@Column(name="upload_save_file")
	private String uploadSaveFile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="use_avail_dt")
	private Date useAvailDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="use_disable_dt")
	private Date useDisableDt;
	
	@Column(name="use_user_gb")
	private String useUserGb;

	@Column(name="use_gb")
	private String useGb;

	@Column(name="ver_num")
	private String verNum;

	public TemplateVO() {
	}

	public void setTemplateVO( TemplateVO updatedVO ) {
		this.appContentsAmt = updatedVO.getAppContentsAmt();
		this.appContentsGb = updatedVO.getAppContentsGb();
		this.chgDt = updatedVO.getChgDt();
		this.chgUserGb = updatedVO.getChgUserGb();
		this.chgUserId = updatedVO.getChgUserId();
		this.chgUserSeq = updatedVO.getChgUserSeq();
		this.completGb = updatedVO.getCompletGb();
		this.descriptionText = updatedVO.getDescriptionText();
		this.limitDt = updatedVO.getLimitDt();
		this.limitGb = updatedVO.getLimitGb();
		this.ostypeGb = updatedVO.getOstypeGb();
		this.regDt = updatedVO.getRegDt();
		this.regUserGb = updatedVO.getRegUserGb();
		this.regUserId = updatedVO.getRegUserId();
		this.regUserSeq = updatedVO.getRegUserSeq();
		this.templateName = updatedVO.getTemplateName();
		this.templateTypeGb = updatedVO.getTemplateTypeGb();
		this.uploadOrgFile = updatedVO.getUploadOrgFile();
		this.uploadSaveFile = updatedVO.getUploadSaveFile();
		this.useAvailDt = updatedVO.getUseAvailDt();
		this.useDisableDt = updatedVO.getUseDisableDt();
		this.useUserGb = updatedVO.getUseUserGb();
		this.useGb = updatedVO.getUseGb();
		this.verNum = updatedVO.getVerNum();
	}

	public int getTemplateSeq() {
		return this.templateSeq;
	}

	public void setTemplateSeq(int templateSeq) {
		this.templateSeq = templateSeq;
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
	
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getChgDt() {
		return this.chgDt;
	}

	public void setChgDt(Date chgDt) {
		this.chgDt = chgDt;
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

	public int getChgUserSeq() {
		return this.chgUserSeq;
	}

	public void setChgUserSeq(int chgUserSeq) {
		this.chgUserSeq = chgUserSeq;
	}

	public String getCompletGb() {
		return this.completGb;
	}

	public void setCompletGb(String completGb) {
		this.completGb = completGb;
	}

	public String getDescriptionText() {
		return this.descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
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

	public String getOstypeGb() {
		return this.ostypeGb;
	}

	public void setOstypeGb(String ostypeGb) {
		this.ostypeGb = ostypeGb;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getRegDt() {
		return this.regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
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

	public int getRegUserSeq() {
		return this.regUserSeq;
	}

	public void setRegUserSeq(int regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateTypeGb() {
		return this.templateTypeGb;
	}

	public void setTemplateTypeGb(String templateTypeGb) {
		this.templateTypeGb = templateTypeGb;
	}

	public String getUploadOrgFile() {
		return this.uploadOrgFile;
	}

	public void setUploadOrgFile(String uploadOrgFile) {
		this.uploadOrgFile = uploadOrgFile;
	}

	public String getUploadSaveFile() {
		return this.uploadSaveFile;
	}

	public void setUploadSaveFile(String uploadSaveFile) {
		this.uploadSaveFile = uploadSaveFile;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getUseAvailDt() {
		return this.useAvailDt;
	}

	public void setUseAvailDt(Date useAvailDt) {
		this.useAvailDt = useAvailDt;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getUseDisableDt() {
		return this.useDisableDt;
	}

	public void setUseDisableDt(Date useDisableDt) {
		this.useDisableDt = useDisableDt;
	}
	
	public String getUseUserGb() {
		return useUserGb;
	}

	public void setUseUserGb(String useUserGb) {
		this.useUserGb = useUserGb;
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
	
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToMany( fetch = FetchType.EAGER)
	@JoinColumn( nullable=true, name="template_seq", referencedColumnName="template_seq", insertable=false, updatable=false)
	private List<TemplateSubVO> templateSubVO;

	public List<TemplateSubVO> getTemplateSubVO() {
		return templateSubVO;
	}

	public void setTemplateSubVO(List<TemplateSubVO> templateSubVO) {
		this.templateSubVO = templateSubVO;
	}
	
	
}