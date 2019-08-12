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
import org.hibernate.annotations.DynamicUpdate;


@Entity
//@Table(name="tb_contentsapp_sub", catalog="pbcms_test")
@Table(name="tb_contentsapp_sub", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ContentsappSubVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contentsapp_sub_seq")
	private Integer contentsappSubSeq;

	@Column(name="contents_seq")
	private Integer contentsSeq;
/*	20180625(월) : lsy - 새버전추가시 번들 중복으로 인한 변경(번들 -> seq)
	@Column(name="store_bundle_id")
	private String storeBundleId;
*/
	@Column(name="app_seq")
	private String appSeq;
	
	@Column(name="inapp_seq")
	private Integer inappSeq;

	public Integer getContentsappSubSeq() {
		return contentsappSubSeq;
	}

	public void setContentsappSubSeq(Integer contentsappSubSeq) {
		this.contentsappSubSeq = contentsappSubSeq;
	}

	public Integer getContentsSeq() {
		return contentsSeq;
	}

	public void setContentsSeq(Integer contentsSeq) {
		this.contentsSeq = contentsSeq;
	}
/*	20180625(월) : lsy - 새버전추가시 번들 중복으로 인한 변경(번들 -> seq)
	public String getStoreBundleId() {
		return storeBundleId;
	}

	public void setStoreBundleId(String storeBundleId) {
		this.storeBundleId = storeBundleId;
	}
*/
	public Integer getInappSeq() {
		return inappSeq;
	}

	public void setInappSeq(Integer inappSeq) {
		this.inappSeq = inappSeq;
	}

	public String getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(String appSeq) {
		this.appSeq = appSeq;
	}

}
