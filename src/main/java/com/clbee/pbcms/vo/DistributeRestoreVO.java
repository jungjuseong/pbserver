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


@Entity
//@Table(name="tb_distribute_restore", catalog="pbcms_test")
@Table(name="tb_distribute_restore", catalog="pbcms_new")
@DynamicInsert(true)
public class DistributeRestoreVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="restore_seq")
	private Integer restoreSeq;
	
	@Column(name="type")
	private String type;

	@Column(name="exist_seq")
	private Integer existSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="distribute_req_dt")
	private Date distributeReqDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="distribute_restore_dt")
	private Date distributeRestoreDt;
	
	@Column(name="restore_user_id")
	private String restoreUserId;
	
	@Lob
	@Column(name = "restore_text")
	private String restoreText;
	
	@Column(name="distribute_requeset_id")
	private String distributeRequestId;

	public Integer getRestoreSeq() {
		return restoreSeq;
	}

	public void setRestoreSeq(Integer restoreSeq) {
		this.restoreSeq = restoreSeq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getExistSeq() {
		return existSeq;
	}

	public void setExistSeq(Integer existSeq) {
		this.existSeq = existSeq;
	}

	public Date getDistributeReqDt() {
		return distributeReqDt;
	}

	public void setDistributeReqDt(Date distributeReqDt) {
		this.distributeReqDt = distributeReqDt;
	}

	public Date getDistributeRestoreDt() {
		return distributeRestoreDt;
	}

	public void setDistributeRestoreDt(Date distributeRestoreDt) {
		this.distributeRestoreDt = distributeRestoreDt;
	}

	public String getRestoreUserId() {
		return restoreUserId;
	}

	public void setRestoreUserId(String restoreUserId) {
		this.restoreUserId = restoreUserId;
	}

	public String getRestoreText() {
		return restoreText;
	}

	public void setRestoreText(String restoreText) {
		this.restoreText = restoreText;
	}

	public String getDistributeRequestId() {
		return distributeRequestId;
	}

	public void setDistributeRequestId(String distributeRequestId) {
		this.distributeRequestId = distributeRequestId;
	}
	
}
