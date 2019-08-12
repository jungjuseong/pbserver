package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the tb_company database table.
 * 
 */
@Entity
//@Table(name="tb_company", catalog="pbcms_test")
@Table(name="tb_company", catalog="pbcms_new")
public class CompanyVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="company_seq")
	private int companySeq;

	@Column(name="addr_detail")
	private String addrDetail;

	@Column(name="addr_first")
	private String addrFirst;

	@Column(name="company_name")
	private String companyName;

	@Column(name="company_status")
	private String companyStatus;

	@Column(name="company_tel")
	private String companyTel;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="withdrawal_dt")
	private Date withdrawalDt;

	private String zipcode;

	public CompanyVO() {
	}

	public void setCompanyVO( CompanyVO updatedVO) {
		this.addrDetail = updatedVO.getAddrDetail();
		this.addrFirst = updatedVO.getAddrFirst();
		this.companyName = updatedVO.getCompanyName();
		this.companyStatus = updatedVO.getCompanyStatus();
		this.companyTel = updatedVO.getCompanyTel();
		this.regDt = updatedVO.getRegDt();
		this.withdrawalDt = updatedVO.getWithdrawalDt();
		this.zipcode = updatedVO.getZipcode();
	}

	public int getCompanySeq() {
		return this.companySeq;
	}

	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}

	public String getAddrDetail() {
		return this.addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getAddrFirst() {
		return this.addrFirst;
	}

	public void setAddrFirst(String addrFirst) {
		this.addrFirst = addrFirst;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStatus() {
		return this.companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getCompanyTel() {
		return this.companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public Date getRegDt() {
		return this.regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public Date getWithdrawalDt() {
		return this.withdrawalDt;
	}

	public void setWithdrawalDt(Date withdrawalDt) {
		this.withdrawalDt = withdrawalDt;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}