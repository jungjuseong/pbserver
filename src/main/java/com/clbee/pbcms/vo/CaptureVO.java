package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tb_capture database table.
 * 
 */
@Entity
//@Table(name="tb_capture", catalog="pbcms_test")
@Table(name="tb_capture", catalog="pbcms_new")
public class CaptureVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="capture_seq")
	private int captureSeq;

	@Column(name="board_seq")
	private int boardSeq;

	@Column(name="capture_gb")
	private String captureGb;

	@Column(name="img_org_file")
	private String imgOrgFile;

	@Column(name="img_save_file")
	private String imgSaveFile;

	@Column(name="user_seq")
	private int userSeq;

	public CaptureVO() {
	}

	public void setCaptureVO( CaptureVO updatedVO) {
		this.boardSeq = updatedVO.getBoardSeq();
		this.captureGb = updatedVO.getCaptureGb();
		this.imgOrgFile = updatedVO.getImgOrgFile();
		this.imgSaveFile = updatedVO.getImgSaveFile();
		this.userSeq = updatedVO.getUserSeq();
	}

	public int getCaptureSeq() {
		return this.captureSeq;
	}

	public void setCaptureSeq(int captureSeq) {
		this.captureSeq = captureSeq;
	}

	public int getBoardSeq() {
		return this.boardSeq;
	}

	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}

	public String getCaptureGb() {
		return this.captureGb;
	}

	public void setCaptureGb(String captureGb) {
		this.captureGb = captureGb;
	}

	public String getImgOrgFile() {
		return this.imgOrgFile;
	}

	public void setImgOrgFile(String imgOrgFile) {
		this.imgOrgFile = imgOrgFile;
	}

	public String getImgSaveFile() {
		return this.imgSaveFile;
	}

	public void setImgSaveFile(String imgSaveFile) {
		this.imgSaveFile = imgSaveFile;
	}

	public int getUserSeq() {
		return this.userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

}