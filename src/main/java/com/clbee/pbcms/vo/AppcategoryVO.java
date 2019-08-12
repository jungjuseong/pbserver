package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the tb_appcategory database table.
 * 
 */
@Entity
//@Table(name="tb_appcategory", catalog="pbcms_test")
@Table(name="tb_appcategory", catalog="pbcms_new")
public class AppcategoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category_seq")
	private int categorySeq;
	
	@Column(name="app_seq")
	private int appSeq;

	@Column(name="category_name")
	private String categoryName;

	@Column(name="category_parent")
	private int categoryParent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chg_user_gb")
	private String chgUserGb;

	@Column(name="chg_user_id")
	private String chgUserId;

	@Column(name="chg_user_seq")
	private int chgUserSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_user_gb")
	private String regUserGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="reg_user_seq")
	private int regUserSeq;

	public AppcategoryVO() {
	}

	public void setAppcategoryVO( AppcategoryVO updatedVO) {
		this.appSeq = updatedVO.getAppSeq();
		this.categoryName = updatedVO.getCategoryName();
		this.categoryParent = updatedVO.getCategoryParent();
		this.chgDt = updatedVO.getChgDt();
		this.chgUserGb = updatedVO.getChgUserGb();
		this.chgUserId = updatedVO.getChgUserId();
		this.chgUserSeq = updatedVO.getChgUserSeq();
		this.regDt = updatedVO.getRegDt();
		this.regUserGb = updatedVO.getRegUserGb();
		this.regUserId = updatedVO.getRegUserId();
		this.regUserSeq = updatedVO.getRegUserSeq();
	}

	public int getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(int appSeq) {
		this.appSeq = appSeq;
	}
	
	public int getCategorySeq() {
		return this.categorySeq;
	}

	public void setCategorySeq(int categorySeq) {
		this.categorySeq = categorySeq;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryParent() {
		return this.categoryParent;
	}

	public void setCategoryParent(int categoryParent) {
		this.categoryParent = categoryParent;
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