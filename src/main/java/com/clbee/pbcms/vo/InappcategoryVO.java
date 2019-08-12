package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Formula;

import com.clbee.pbcms.Json.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;


/**
 * The persistent class for the tb_inappcategory database table.
 * 
 */
@Entity
//@Table(name="tb_inappcategory", catalog="pbcms_test")
@Table(name="tb_inappcategory", catalog="pbcms_new")
public class InappcategoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_seq")
	private Integer categorySeq;

	@Column(name="store_bundle_id")
	private String storeBundleId;
	
	@Column(name="depth")
	private String depth;

	@Column(name="category_name")
	private String categoryName;

	@Column(name="category_parent")
	private Integer categoryParent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chg_user_gb")
	private String chgUserGb;

	@Column(name="chg_user_id")
	private String chgUserId;

	@Column(name="chg_user_seq")
	private Integer chgUserSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_user_gb")
	private String regUserGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="reg_user_seq")
	private Integer regUserSeq;

	public InappcategoryVO() {
	}

	public void setInappcategoryVO( InappcategoryVO updatedVO) {
		this.storeBundleId = updatedVO.getStoreBundleId();
		this.categoryName = updatedVO.getCategoryName();
		this.depth = updatedVO.getDepth();
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

	public Integer getCategorySeq() {
		return this.categorySeq;
	}

	public void setCategorySeq(Integer categorySeq) {
		this.categorySeq = categorySeq;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryParent() {
		return this.categoryParent;
	}

	public void setCategoryParent(Integer categoryParent) {
		this.categoryParent = categoryParent;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
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

	public Integer getChgUserSeq() {
		return this.chgUserSeq;
	}

	public void setChgUserSeq(Integer chgUserSeq) {
		this.chgUserSeq = chgUserSeq;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
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

	public Integer getRegUserSeq() {
		return this.regUserSeq;
	}

	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}
	
	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getStoreBundleId() {
		return storeBundleId;
	}

	public void setStoreBundleId(String storeBundleId) {
		this.storeBundleId = storeBundleId;
	}
}