package com.clbee.pbcms.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;


@Entity
@Table(name="tb_license_sub", catalog="pbcms_new")
@DynamicInsert(true)
public class LicenseSubVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="licensesub_seq")
	private Integer licensesubSeq;
	
	@Column(name="license_seq")
	private Integer licenseSeq;

	@Column(name="license_user_seq")
	private Integer licenseUserSeq;
	
	@Column(name="device_name")
	private String deviceName;
	
	@Column(name="device_os")
	private String deviceOs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "use_start_dt")
	private Date useStartDt;
	
	private String userId;

	public Integer getLicensesubSeq() {
		return licensesubSeq;
	}

	public void setLicensesubSeq(Integer licensesubSeq) {
		this.licensesubSeq = licensesubSeq;
	}

	public Integer getLicenseSeq() {
		return licenseSeq;
	}

	public void setLicenseSeq(Integer licenseSeq) {
		this.licenseSeq = licenseSeq;
	}

	public Integer getLicenseUserSeq() {
		return licenseUserSeq;
	}

	public void setLicenseUserSeq(Integer licenseUserSeq) {
		this.licenseUserSeq = licenseUserSeq;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceOs() {
		return deviceOs;
	}

	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}

	public Date getUseStartDt() {
		return useStartDt;
	}

	public void setUseStartDt(Date useStartDt) {
		this.useStartDt = useStartDt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
