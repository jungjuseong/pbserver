package com.clbee.pbcms.Json;

public class ConnectLicenseInfo{
	String workPath;
	String userId;
	String userPwd;
	String deviceName;
	String deviceOs;
	String deviceSeq;
	String resultCode;

	int userSeq;
	int licenseSeq;
	int userCopyCount;
	
	public String getWorkPath() {
		return workPath;
	}
	public void setWorkPath(String workPath) {
		this.workPath = workPath;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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
	public String getDeviceSeq() {
		return deviceSeq;
	}
	public void setDeviceSeq(String deviceSeq) {
		this.deviceSeq = deviceSeq;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public int getLicenseSeq() {
		return licenseSeq;
	}
	public void setLicenseSeq(int licenseSeq) {
		this.licenseSeq = licenseSeq;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public int getUserCopyCount() {
		return userCopyCount;
	}
	public void setUserCopyCount(int userCopyCount) {
		this.userCopyCount = userCopyCount;
	}

}