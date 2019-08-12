package com.clbee.pbcms.Json;

import java.util.Date;

import com.clbee.pbcms.vo.AppVO;
import com.clbee.pbcms.vo.MemberVO;

public class LoginInfomationVO {
	int loginValue;
	String loginDate;
	String authority;
	String userId;
	String companyGb;
	String companyId;
	int userSeq;
	int companySeq;
	String userName;
	String useGb;			//사용여부
	String limitGb;			//제한여부
	String distrGb;			//배포여부
	String couponGb;		//쿠폰여부
	Date memberStartDate;
	Date memberEndDate;
	Date couponStartDate;
	Date couponEndDate;
	


	String loginMessage;
	
	public LoginInfomationVO(int loginValue, String loginDate, MemberVO memberVO, String companyId, String loginMessage, AppVO appVO) {
		super();
		this.loginValue = loginValue;
		this.loginDate = loginDate;
		if(memberVO != null)
		this.authority = memberVO.getUserGb();
		if(memberVO != null)
		this.userId = memberVO.getUserId();
		if(memberVO != null)
		this.companyGb = memberVO.getCompanyGb();
		this.companyId = companyId;
		if(memberVO != null)
		this.userSeq = memberVO.getUserSeq();
		if(memberVO != null)
		this.companySeq = memberVO.getCompanySeq();
		if(memberVO != null)
		this.userName = memberVO.getLastName() + memberVO.getFirstName();
		
		if(appVO != null){
			this.useGb = appVO.getUseGb();
			this.limitGb = appVO.getLimitGb();
			this.distrGb = appVO.getDistrGb();
			this.couponGb = appVO.getCouponGb();
			if(appVO.getMemDownStartDt() != null)
			this.memberStartDate = appVO.getMemDownStartDt();
			if(appVO.getMemDownEndDt() != null)
			this.memberEndDate = appVO.getMemDownEndDt();
			if(appVO.getNonmemDownStarDt() != null)
			this.couponStartDate = appVO.getNonmemDownStarDt();
			if(appVO.getNonmemDownEndDt() != null)
			this.couponEndDate = appVO.getNonmemDownEndDt();
		}
		
		this.loginMessage = loginMessage;
	}

	public int getLoginValue() {
		return loginValue;
	}

	public void setLoginValue(int loginValue) {
		this.loginValue = loginValue;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyGb() {
		return companyGb;
	}

	public void setCompanyGb(String companyGb) {
		this.companyGb = companyGb;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public int getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getUseGb() {
		return useGb;
	}

	public void setUseGb(String useGb) {
		this.useGb = useGb;
	}

	public String getLimitGb() {
		return limitGb;
	}

	public void setLimitGb(String limitGb) {
		this.limitGb = limitGb;
	}

	public String getDistrGb() {
		return distrGb;
	}

	public void setDistrGb(String distrGb) {
		this.distrGb = distrGb;
	}

	public String getCouponGb() {
		return couponGb;
	}

	public void setCouponGb(String couponGb) {
		this.couponGb = couponGb;
	}

	public Date getMemberStartDate() {
		return memberStartDate;
	}

	public void setMemberStartDate(Date memberStartDate) {
		this.memberStartDate = memberStartDate;
	}

	public Date getMemberEndDate() {
		return memberEndDate;
	}

	public void setMemberEndDate(Date memberEndDate) {
		this.memberEndDate = memberEndDate;
	}

	public Date getCouponStartDate() {
		return couponStartDate;
	}

	public void setCouponStartDate(Date couponStartDate) {
		this.couponStartDate = couponStartDate;
	}

	public Date getCouponEndDate() {
		return couponEndDate;
	}

	public void setCouponEndDate(Date couponEndDate) {
		this.couponEndDate = couponEndDate;
	}
}
