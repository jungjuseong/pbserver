package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

@Entity
//@Table(name="tb_noticeapp_sub", catalog="pbcms_test")
@Table(name="tb_noticeapp_sub", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class NoticeappSubVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="noticeapp_sub_seq")
	private Integer noticeappSubSeq;

	@Column(name="notice_seq")
	private Integer noticeSeq;
	
	@Column(name="inapp_seq")
	private Integer inappSeq;
	
	@Column(name="store_bundle_id")
	private String storeBundleId;

	public Integer getNoticeappSubSeq() {
		return noticeappSubSeq;
	}

	public void setNoticeappSubSeq(Integer noticeappSubSeq) {
		this.noticeappSubSeq = noticeappSubSeq;
	}

	public Integer getNoticeSeq() {
		return noticeSeq;
	}

	public void setNoticeSeq(Integer noticeSeq) {
		this.noticeSeq = noticeSeq;
	}

	public String getStoreBundleId() {
		return storeBundleId;
	}

	public void setStoreBundleId(String storeBundleId) {
		this.storeBundleId = storeBundleId;
	}

	public Integer getInappSeq() {
		return inappSeq;
	}

	public void setInappSeq(Integer inappSeq) {
		this.inappSeq = inappSeq;
	}
}
