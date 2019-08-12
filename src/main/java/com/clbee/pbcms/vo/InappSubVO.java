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


@Entity
//@Table(name="tb_inapp_sub", catalog="pbcms_test")
@Table(name="tb_inapp_sub", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class InappSubVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="inappsub_seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer inappsubSeq;

	@Column(name="inapp_seq")
	private Integer inappSeq;

	@Column(name="user_seq")
	private Integer userSeq;
	
	@Column(name="department_seq")
	private Integer departmentSeq;
	

	public Integer getInappsubSeq() {
		return inappsubSeq;
	}

	public void setInappsubSeq(Integer inappsubSeq) {
		this.inappsubSeq = inappsubSeq;
	}

	public Integer getInappSeq() {
		return inappSeq;
	}

	public void setInappSeq(Integer inappSeq) {
		this.inappSeq = inappSeq;
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