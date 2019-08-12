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
//@Table(name="tb_group_user", catalog="pbcms_test")
@Table(name="tb_group_user", catalog="pbcms_new")
@DynamicInsert(true)
public class GroupUserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_seq")
	private Integer groupSeq;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="member_seq")
	private Integer memberSeq;
	
	@Column(name="company_seq")
	private Integer companySeq;
	
	@Column(name = "menu_large")
	private String menuLarge;
	
	@Column(name="menu_medium")
	private String menuMedium;
	
	@Column(name="menu_function")
	private String menuFunction;
	
	@Column(name="member_gb")
	private String memberGb;

	public Integer getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getMemberSeq() {
		return memberSeq;
	}

	public void setMemberSeq(Integer memberSeq) {
		this.memberSeq = memberSeq;
	}

	public Integer getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}

	public String getMenuLarge() {
		return menuLarge;
	}

	public void setMenuLarge(String menuLarge) {
		this.menuLarge = menuLarge;
	}

	public String getMenuMedium() {
		return menuMedium;
	}

	public void setMenuMedium(String menuMedium) {
		this.menuMedium = menuMedium;
	}

	public String getMenuFunction() {
		return menuFunction;
	}

	public void setMenuFunction(String menuFunction) {
		this.menuFunction = menuFunction;
	}

	public String getMemberGb() {
		return memberGb;
	}

	public void setMemberGb(String memberGb) {
		this.memberGb = memberGb;
	}
	
}
