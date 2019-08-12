package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;


/**
 * The persistent class for the tb_provision database table.
 * 
 */
@Entity
//@Table(name="tb_provision", catalog="pbcms_test")
@Table(name="tb_provision", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ProvisionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="prov_seq")
	private int provSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chg_user_gb")
	private String chgUserGb;

	@Column(name="chg_user_id")
	private String chgUserId;

	@Column(name="chg_user_seq")
	private int chgUserSeq;

	@Column(name="distr_profile")
	private String distrProfile;

	@Column(name="distr_profile_name")
	private String distrProfileName;
	
	@Column(name="distr_profile_save_name")
	private String distrProfileSaveName;

	public String getDistrProfileSaveName() {
		return distrProfileSaveName;
	}

	public void setDistrProfileSaveName(String distrProfileSaveName) {
		this.distrProfileSaveName = distrProfileSaveName;
	}

	@Column(name="prov_id")
	private String provId;

	@Column(name="prov_name")
	private String provName;

	@Column(name="prov_pw")
	private String provPw;

	public String getProvPw() {
		return provPw;
	}

	public void setProvPw(String provPw) {
		this.provPw = provPw;
	}



	@Override
	public String toString() {
		return "ProvisionVO [provSeq=" + provSeq + ", chgDt=" + chgDt
				+ ", chgUserGb=" + chgUserGb + ", chgUserId=" + chgUserId
				+ ", chgUserSeq=" + chgUserSeq + ", distrProfile="
				+ distrProfile + ", distrProfileName=" + distrProfileName
				+ ", distrProfileSaveName=" + distrProfileSaveName
				+ ", provId=" + provId + ", provName=" + provName + ", provPw="
				+ provPw + ", provTestGb=" + provTestGb + ", regDt=" + regDt
				+ ", regUserGb=" + regUserGb + ", regUserId=" + regUserId
				+ ", regUserSeq=" + regUserSeq + ", regCompanySeq="
				+ regCompanySeq + "]";
	}

	@Column(name="prov_test_gb")
	private String provTestGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_user_gb")
	private String regUserGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="reg_user_seq")
	private int regUserSeq;

	@Column(name="reg_company_seq")
	private int regCompanySeq;
	
	public int getRegCompanySeq() {
		return regCompanySeq;
	}

	public void setRegCompanySeq(int regCompanySeq) {
		this.regCompanySeq = regCompanySeq;
	}

	public ProvisionVO() {
	}

	public void setProvisionVO( ProvisionVO updatedVO) {
		this.chgDt = updatedVO.getChgDt();
		this.chgUserGb = updatedVO.getChgUserGb();
		this.chgUserId = updatedVO.getChgUserId();
		this.chgUserSeq = updatedVO.getChgUserSeq();
		this.distrProfile = updatedVO.getDistrProfile();
		this.distrProfileName = updatedVO.getDistrProfileName();
		this.distrProfileSaveName = updatedVO.getDistrProfileSaveName();
		this.provId = updatedVO.getProvId();
		this.provName = updatedVO.getProvName();
		this.provPw = updatedVO.getProvPw();
		this.provTestGb = updatedVO.getProvTestGb();
		this.regDt = updatedVO.getRegDt();
		this.regUserGb = updatedVO.getRegUserGb();
		this.regUserId = updatedVO.getRegUserId();
		this.regUserSeq = updatedVO.getRegUserSeq();
		this.regCompanySeq = updatedVO.getRegCompanySeq();
	}

	public int getProvSeq() {
		return this.provSeq;
	}

	public void setProvSeq(int provSeq) {
		this.provSeq = provSeq;
	}

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

	public String getDistrProfile() {
		return this.distrProfile;
	}

	public void setDistrProfile(String distrProfile) {
		this.distrProfile = distrProfile;
	}

	public String getDistrProfileName() {
		return this.distrProfileName;
	}

	public void setDistrProfileName(String distrProfileName) {
		this.distrProfileName = distrProfileName;
	}

	public String getProvId() {
		return this.provId;
	}

	public void setProvId(String provId) {
		this.provId = provId;
	}

	public String getProvName() {
		return this.provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getProvTestGb() {
		return this.provTestGb;
	}

	public void setProvTestGb(String provTestGb) {
		this.provTestGb = provTestGb;
	}

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

}