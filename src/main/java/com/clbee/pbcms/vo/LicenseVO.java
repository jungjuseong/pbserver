package com.clbee.pbcms.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;


@Entity
@Table(name="tb_license", catalog="pbcms_new")
@DynamicInsert(true)
public class LicenseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="license_seq")
	private Integer licenseSeq;
	
	@Column(name="license_num")
	private String licenseNum;
	
	@Column(name="license_status")
	private String licenseStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "generate_dt")
	private Date generateDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "regist_dt")
	private Date registDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_dt")
	private Date expireDt;

	@Column(name="user_copy_count")
	private Integer userCopyCount;

	@Column(name="license_user_seq")
	private Integer licenseUserSeq;

	@Column(name="license_gb")
	private String licenseGb;

	@Column(name="period_gb")
	private String periodGb;

	@Lob
	@Column(name="disposal_reason")
	private String disposalReason;
	
	private String userId;	
	private int remainDt;	
	private int licenseUser;

	public Integer getLicenseSeq() {
		return licenseSeq;
	}

	public void setLicenseSeq(Integer licenseSeq) {
		this.licenseSeq = licenseSeq;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getLicenseStatus() {
		return licenseStatus;
	}

	public void setLicenseStatus(String licenseStatus) {
		this.licenseStatus = licenseStatus;
	}

	public Date getGenerateDt() {
		return generateDt;
	}

	public void setGenerateDt(Date generateDt) {
		this.generateDt = generateDt;
	}

	public Date getRegistDt() {
		return registDt;
	}

	public void setRegistDt(Date registDt) {
		this.registDt = registDt;
	}

	public Date getExpireDt() {
		return expireDt;
	}

	public void setExpireDt(Date expireDt) {
		this.expireDt = expireDt;
	}

	public Integer getUserCopyCount() {
		return userCopyCount;
	}

	public void setUserCopyCount(Integer userCopyCount) {
		this.userCopyCount = userCopyCount;
	}

	public Integer getLicenseUserSeq() {
		return licenseUserSeq;
	}

	public void setLicenseUserSeq(Integer licenseUserSeq) {
		this.licenseUserSeq = licenseUserSeq;
	}

	public String getLicenseGb() {
		return licenseGb;
	}

	public void setLicenseGb(String licenseGb) {
		this.licenseGb = licenseGb;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getRemainDt() {
		return remainDt;
	}

	public void setRemainDt(int remainDt) {
		this.remainDt = remainDt;
	}

	public String getPeriodGb() {
		return periodGb;
	}

	public void setPeriodGb(String periodGb) {
		this.periodGb = periodGb;
	}

	public int getLicenseUser() {
		return licenseUser;
	}

	public void setLicenseUser(int licenseUser) {
		this.licenseUser = licenseUser;
	}

	public String getDisposalReason() {
		return disposalReason;
	}

	public void setDisposalReason(String disposalReason) {
		this.disposalReason = disposalReason;
	}
}
