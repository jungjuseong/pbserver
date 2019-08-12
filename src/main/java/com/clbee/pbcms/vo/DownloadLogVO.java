package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the tb_download_log database table.
 * 
 */
@Entity
//@Table(name="tb_download_log", catalog="pbcms_test")
@Table(name="tb_download_log", catalog="pbcms_new")
public class DownloadLogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="download_seq")
	private int downloadSeq;

	@Column(name="board_seq")
	private int boardSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="download_dt")
	private Date downloadDt;

	@Column(name="download_gb")
	private String downloadGb;

	@Column(name="download_type")
	private String downloadType;

	@Column(name="user_seq")
	private int userSeq;

	public DownloadLogVO() {
	}

	
	
	public void setDownloadLogVO( DownloadLogVO updatedVO) {
		
		this.boardSeq = updatedVO.getBoardSeq();
		this.downloadDt = updatedVO.getDownloadDt();
		this.downloadGb = updatedVO.getDownloadGb();
		this.downloadType = updatedVO.getDownloadType();
		this.userSeq = updatedVO.getUserSeq();
	}



	public int getDownloadSeq() {
		return this.downloadSeq;
	}

	public void setDownloadSeq(int downloadSeq) {
		this.downloadSeq = downloadSeq;
	}

	public int getBoardSeq() {
		return this.boardSeq;
	}

	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}

	public Date getDownloadDt() {
		return this.downloadDt;
	}

	public void setDownloadDt(Date downloadDt) {
		this.downloadDt = downloadDt;
	}

	public String getDownloadGb() {
		return this.downloadGb;
	}

	public void setDownloadGb(String downloadGb) {
		this.downloadGb = downloadGb;
	}

	public String getDownloadType() {
		return this.downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	public int getUserSeq() {
		return this.userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

}