package com.clbee.pbcms.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.clbee.pbcms.Json.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
//@Table(name="tb_log", catalog="pbcms_test")
@Table(name="tb_log", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class LogVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="log_seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer logSeq;

	@Column(name="device_uuid")
	private String deviceUuid;

	@Column(name="store_bundle_id")
	private String storeBundleId;
	
	@Column(name="inapp_seq")
	private String inappSeq;

	@Column(name="reg_user_seq")
	private Integer regUserSeq;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="page_gb")
	private String pageGb;
	
	@Column(name="data_gb")
	private String dataGb;
	
	@Column(name="data")
	private String data;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true)
	@JoinColumn(nullable = true, name = "reg_user_seq", referencedColumnName = "user_seq", insertable = false, updatable = false)
	private MemberVO regMemberVO;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true)
	@JoinColumn(nullable = true, name = "inapp_seq", referencedColumnName = "inapp_seq", insertable = false, updatable = false)
	private InappVO inappVO;
	
	public Integer getLogSeq() {
		return logSeq;
	}

	public void setLogSeq(Integer logSeq) {
		this.logSeq = logSeq;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getInappSeq() {
		return inappSeq;
	}

	public void setInappSeq(String inappSeq) {
		this.inappSeq = inappSeq;
	}

	public Integer getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getPageGb() {
		return pageGb;
	}

	public void setPageGb(String pageGb) {
		this.pageGb = pageGb;
	}

	public String getDataGb() {
		return dataGb;
	}

	public void setDataGb(String dataGb) {
		this.dataGb = dataGb;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStoreBundleId() {
		return storeBundleId;
	}

	public void setStoreBundleId(String storeBundleId) {
		this.storeBundleId = storeBundleId;
	}

	public MemberVO getRegMemberVO() {
		return regMemberVO;
	}

	public void setRegMemberVO(MemberVO regMemberVO) {
		this.regMemberVO = regMemberVO;
	}

	public InappVO getInappVO() {
		return inappVO;
	}

	public void setInappVO(InappVO inappVO) {
		this.inappVO = inappVO;
	}
}