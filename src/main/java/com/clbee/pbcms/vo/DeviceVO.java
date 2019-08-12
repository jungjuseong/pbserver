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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
//@Table(name="tb_device", catalog="pbcms_test")
@Table(name="tb_device", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class DeviceVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="device_seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int deviceSeq;

	@Column(name="company_seq")
	private Integer companySeq;

	@Column(name="device_uuid")
	private String deviceUuid;

	@Column(name="device_info")
	private String deviceInfo;

	@Column(name="device_type")
	private String deviceType;

	@Column(name="use_gb")
	private String useGb;

	@Column(name="reg_user_seq")
	private Integer regUserSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( name="reg_user_seq",  referencedColumnName="user_seq", insertable=false, updatable=false )
	private MemberVO memberVO;

	public int getDeviceSeq() {
		return deviceSeq;
	}

	public void setDeviceSeq(int deviceSeq) {
		this.deviceSeq = deviceSeq;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Date getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getUseGb() {
		return useGb;
	}

	public void setUseGb(String useGb) {
		this.useGb = useGb;
	}

	public Integer getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public Integer getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
}
