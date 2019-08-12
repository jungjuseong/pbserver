package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the tb_member database table.
 * 
 */
@Entity
//@Table(name="tb_member", catalog="pbcms_test")
@Table(name="tb_member", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MemberVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_seq")
	private int userSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chg_ip")
	private String chgIp;

	@Column(name="company_gb")
	private String companyGb;

	@Column(name="company_seq")
	private int companySeq;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="email_chk_dt")
	private Date emailChkDt;

	@Column(name="email_chk_gb")
	private String emailChkGb;

	@Column(name="email_chk_session")
	private String emailChkSession;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_dt")
	private Date loginDt;

	private String phone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_ip")
	private String regIp;

	@Column(name="user_gb")
	private String userGb;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_pw")
	private String userPw;

	@Column(name="user_status")
	private String userStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="withdrawal_dt")
	private Date withdrawalDt;
	
	@Column(name="onedepartment_seq")
	private Integer onedepartmentSeq;
	
	@Column(name="twodepartment_seq")
	private Integer twodepartmentSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="user_start_dt")
	private Date userStartDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="user_end_dt")
	private Date userEndDt;
	
	@Column(name="date_gb")
	private String dateGb;
	
	@Column(name="session_id")
	private String sessionId;
	
	@Column(name="login_status")
	private String loginStatus;
	
	@Column(name="login_deviceuuid")
	private String loginDeviceuuid;
	
	@Column(name="group_name")
	private String groupName;
	
	@NotFound( action = NotFoundAction.IGNORE )
	@ManyToOne( optional = true )
	@JoinColumn( nullable=true, name="company_seq",  insertable=false, updatable=false )
	private CompanyVO companyVO;
	

	@Formula("(select tb_departmentcategory.department_name from tb_departmentcategory where tb_departmentcategory.department_seq = onedepartment_seq limit 1)")
	private String onedepartmentName;
	

	@Formula("(select tb_departmentcategory.department_name from tb_departmentcategory where tb_departmentcategory.department_seq = twodepartment_seq limit 1)")
	private String twodepartmentName;
	
	@Formula("(select tb_group_user.group_name from tb_group_user where tb_group_user.group_seq=group_name limit 1)")
	private String userGroup;
	
	public MemberVO() {
	}

	public void setMemberVO( MemberVO updatedVO) {
		this.chgDt = updatedVO.getChgDt();
		this.chgIp = updatedVO.getChgIp();
		this.companyGb = updatedVO.getCompanyGb();
		this.companySeq = updatedVO.getCompanySeq();
		this.email = updatedVO.getEmail();
		this.emailChkDt = updatedVO.getEmailChkDt();
		this.emailChkGb = updatedVO.getEmailChkGb();
		this.emailChkSession = updatedVO.getEmailChkSession();
		this.firstName = updatedVO.getFirstName();
		this.lastName = updatedVO.getLastName();
		this.loginDt = updatedVO.getLoginDt();
		this.phone = updatedVO.getPhone();
		this.regDt = updatedVO.getRegDt();
		this.regIp = updatedVO.getRegIp();
		this.userGb = updatedVO.getUserGb();
		this.userId = updatedVO.getUserId();
		this.userPw = updatedVO.getUserPw();
		this.userStatus = updatedVO.getUserStatus();
		this.withdrawalDt = updatedVO.getWithdrawalDt();
		this.onedepartmentSeq = updatedVO.getOnedepartmentSeq();
		this.twodepartmentSeq = updatedVO.getTwodepartmentSeq();
		this.userStartDt = updatedVO.getUserStartDt();
		this.userEndDt = updatedVO.getUserEndDt();
		this.dateGb = updatedVO.getDateGb();
		this.sessionId = updatedVO.getSessionId();
		this.loginStatus = updatedVO.getLoginStatus();
		this.loginDeviceuuid = updatedVO.getLoginDeviceuuid();
		this.groupName = updatedVO.getGroupName();
		this.userGroup = updatedVO.getUserGroup();
	}

	public String getEmailChkSession() {
		return emailChkSession;
	}

	public void setEmailChkSession(String emailChkSession) {
		this.emailChkSession = emailChkSession;
	}

	public int getUserSeq() {
		return this.userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public Date getChgDt() {
		return this.chgDt;
	}

	public void setChgDt(Date chgDt) {
		this.chgDt = chgDt;
	}

	public String getChgIp() {
		return this.chgIp;
	}

	public void setChgIp(String chgIp) {
		this.chgIp = chgIp;
	}

	public String getCompanyGb() {
		return this.companyGb;
	}

	public void setCompanyGb(String companyGb) {
		this.companyGb = companyGb;
	}

	public int getCompanySeq() {
		return this.companySeq;
	}

	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEmailChkDt() {
		return this.emailChkDt;
	}

	public void setEmailChkDt(Date emailChkDt) {
		this.emailChkDt = emailChkDt;
	}

	public String getEmailChkGb() {
		return this.emailChkGb;
	}

	public void setEmailChkGb(String emailChkGb) {
		this.emailChkGb = emailChkGb;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLoginDt() {
		return this.loginDt;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegDt() {
		return this.regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getUserGb() {
		return this.userGb;
	}

	public void setUserGb(String userGb) {
		this.userGb = userGb;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return this.userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Date getWithdrawalDt() {
		return this.withdrawalDt;
	}

	public void setWithdrawalDt(Date withdrawalDt) {
		this.withdrawalDt = withdrawalDt;
	}

	public Integer getOnedepartmentSeq() {
		return onedepartmentSeq;
	}

	public void setOnedepartmentSeq(Integer onedepartmentSeq) {
		this.onedepartmentSeq = onedepartmentSeq;
	}

	public Integer getTwodepartmentSeq() {
		return twodepartmentSeq;
	}

	public void setTwodepartmentSeq(Integer twodepartmentSeq) {
		this.twodepartmentSeq = twodepartmentSeq;
	}

	public CompanyVO getCompanyVO() {
		return companyVO;
	}

	public void setCompanyVO(CompanyVO companyVO) {
		this.companyVO = companyVO;
	}

	public Date getUserStartDt() {
		return userStartDt;
	}

	public void setUserStartDt(Date userStartDt) {
		this.userStartDt = userStartDt;
	}

	public Date getUserEndDt() {
		return userEndDt;
	}

	public void setUserEndDt(Date userEndDt) {
		this.userEndDt = userEndDt;
	}

	public String getDateGb() {
		return dateGb;
	}	
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public void setDateGb(String dateGb) {
		this.dateGb = dateGb;
	}

	public String getOnedepartmentName() {
		return onedepartmentName;
	}

	public void setOnedepartmentName(String onedepartmentName) {
		this.onedepartmentName = onedepartmentName;
	}

	public String getTwodepartmentName() {
		return twodepartmentName;
	}

	public void setTwodepartmentName(String twodepartmentName) {
		this.twodepartmentName = twodepartmentName;
	}

	public String getLoginDeviceuuid() {
		return loginDeviceuuid;
	}

	public void setLoginDeviceuuid(String loginDeviceuuid) {
		this.loginDeviceuuid = loginDeviceuuid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	
}