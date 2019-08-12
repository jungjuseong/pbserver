package com.clbee.pbcms.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
//@Table(name="tb_departmentcategory", catalog="pbcms_test")
@Table(name="tb_departmentcategory", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class DepartmentVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="department_seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer departmentSeq;
	
	@Column(name="department_name")
	private String departmentName;

	@Column(name="department_parent")
	private Integer departmentParent;

	@Column(name="company_seq")
	private Integer companySeq;
	
	@Column(name="depth")
	private String depth;
	
	@Column(name="reg_user_seq")
	private Integer regUserSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;
	
	@Column(name="use_gb")
	private String useGb;

	public Integer getDepartmentSeq() {
		return departmentSeq;
	}

	public void setDepartmentSeq(Integer departmentSeq) {
		this.departmentSeq = departmentSeq;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getDepartmentParent() {
		return departmentParent;
	}

	public void setDepartmentParent(Integer departmentParent) {
		this.departmentParent = departmentParent;
	}

	public Integer getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public Integer getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public Date getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getUseGb() {
		return useGb;
	}

	public void setUseGb(String useGb) {
		this.useGb = useGb;
	}

}
