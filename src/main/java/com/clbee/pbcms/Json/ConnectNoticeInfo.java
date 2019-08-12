package com.clbee.pbcms.Json;

import java.util.Date;
import java.util.List;

import com.clbee.pbcms.vo.NoticeVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ConnectNoticeInfo {

	int noticeSeq;
	int inappSeq;
	String noticeName;
	String noticeText;
	Date regDt;
	
	
	
	public ConnectNoticeInfo( NoticeVO noticeVO, Integer inappSeq) {
		super();
		
		if(noticeVO != null) {
			this.noticeSeq = noticeVO.getNoticeSeq();
			this.noticeName = noticeVO.getNoticeName();
			this.noticeText = noticeVO.getNoticeText();
			this.regDt = noticeVO.getRegDt();
		}
		
		
		if(inappSeq != null)
		this.inappSeq = inappSeq;
		
	}
	public int getNoticeSeq() {
		return noticeSeq;
	}
	public void setNoticeSeq(int noticeSeq) {
		this.noticeSeq = noticeSeq;
	}
	public int getInappSeq() {
		return inappSeq;
	}
	public void setInappSeq(int inappSeq) {
		this.inappSeq = inappSeq;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getNoticeText() {
		return noticeText;
	}
	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	
	
}
