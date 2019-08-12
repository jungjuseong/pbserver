package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;


/**
 * The persistent class for the tb_changelist database table.
 * 
 */
@Entity
//@Table(name="tb_changelist", catalog="pbcms_test")
@Table(name="tb_changelist", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ChangelistVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="chglist_seq")
	private int chglistSeq;

	@Column(name="board_seq")
	private int boardSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chglist_gb")
	private String chglistGb;

	@Lob
	@Column(name="chglist_text")
	private String chglistText;

	@Column(name="ostype_gb")
	private String ostypeGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="ver_num")
	private String verNum;

	public ChangelistVO() {
	}
	
	

	public void setChangelistVO( ChangelistVO updatedVO ) {
		this.boardSeq = updatedVO.getBoardSeq();
		this.chgDt = updatedVO.getChgDt();
		this.chglistGb = updatedVO.getChglistGb();
		this.chglistText = updatedVO.getChglistText();
		this.ostypeGb = updatedVO.getOstypeGb();
		this.regUserId = updatedVO.getRegUserId();
		this.verNum = updatedVO.getVerNum();
	}



	public int getChglistSeq() {
		return this.chglistSeq;
	}

	public void setChglistSeq(int chglistSeq) {
		this.chglistSeq = chglistSeq;
	}

	public int getBoardSeq() {
		return this.boardSeq;
	}

	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}

	public Date getChgDt() {
		return this.chgDt;
	}

	public void setChgDt(Date chgDt) {
		this.chgDt = chgDt;
	}

	public String getChglistGb() {
		return this.chglistGb;
	}

	public void setChglistGb(String chglistGb) {
		this.chglistGb = chglistGb;
	}

	public String getChglistText() {
		return this.chglistText;
	}

	public void setChglistText(String chglistText) {
		this.chglistText = chglistText;
	}

	public String getOstypeGb() {
		return this.ostypeGb;
	}

	public void setOstypeGb(String ostypeGb) {
		this.ostypeGb = ostypeGb;
	}

	public String getRegUserId() {
		return this.regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public String getVerNum() {
		return this.verNum;
	}

	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}

}