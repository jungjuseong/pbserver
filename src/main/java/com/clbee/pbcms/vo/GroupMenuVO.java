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
//@Table(name="tb_group_menu", catalog="pbcms_test")
@Table(name="tb_group_menu", catalog="pbcms_new")
@DynamicInsert(true)
public class GroupMenuVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menu_seq")
	private Integer menuSeq;
	
	@Column(name="menu_name")
	private String menuName;
	
	@Column(name="menu_description")
	private String menuDescription;
	
	@Column(name = "menu_type")
	private String menuType;
	
	@Column(name="connect_menu")
	private String connectMenu;
	
	@Column(name = "page_url")
	private String pageUrl;
	
	@Column(name="must_yn")
	private String mustYn;

	public Integer getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getConnectMenu() {
		return connectMenu;
	}

	public void setConnectMenu(String connectMenu) {
		this.connectMenu = connectMenu;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getMustYn() {
		return mustYn;
	}

	public void setMustYn(String mustYn) {
		this.mustYn = mustYn;
	}
	
}
