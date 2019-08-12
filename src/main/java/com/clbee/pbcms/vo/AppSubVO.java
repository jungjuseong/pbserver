package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
//@Table(name="tb_app_sub", catalog="pbcms_test")
@Table(name="tb_app_sub", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AppSubVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="appsub_seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer appsubSeq;

	@Column(name="app_seq")
	private Integer appSeq;

	@Column(name="user_seq")
	private Integer userSeq;

	@Column(name="department_Seq")
	private Integer departmentSeq;

	public Integer getAppsubSeq() {
		return appsubSeq;
	}

	public void setAppsubSeq(Integer appsubSeq) {
		this.appsubSeq = appsubSeq;
	}

	public Integer getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(Integer appSeq) {
		this.appSeq = appSeq;
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
