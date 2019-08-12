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
//@Table(name="tb_notice_sub", catalog="pbcms_test")
@Table(name="tb_notice_sub", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class NoticeSubVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notice_sub_seq")
	private Integer noticeSubSeq;

	@Column(name="notice_seq")
	private Integer noticeSeq;

	@Column(name="user_seq")
	private Integer userSeq;

	@Column(name="department_seq")
	private Integer departmentSeq;

	public Integer getNoticeSubSeq() {
		return noticeSubSeq;
	}

	public void setNoticeSubSeq(Integer noticeSubSeq) {
		this.noticeSubSeq = noticeSubSeq;
	}

	public Integer getNoticeSeq() {
		return noticeSeq;
	}

	public void setNoticeSeq(Integer noticeSeq) {
		this.noticeSeq = noticeSeq;
	}

	public Integer getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}

	public Integer getDepartmentSeq() {
		return departmentSeq;
	}

	public void setDepartmentSeq(Integer departmentSeq) {
		this.departmentSeq = departmentSeq;
	}
}
