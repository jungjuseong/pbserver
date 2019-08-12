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
//@Table(name="tb_inapp_meta", catalog="pbcms_test")
@Table(name="tb_inapp_meta", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class InappMetaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="inappmeta_seq")
	private Integer inappMetaSeq;

	@Column(name="inapp_seq")
	private Integer inappSeq;

	@Column(name="inappmeta_subtitle")
	private String inappMetaSubtitle;

	@Column(name="inappmeta_author")
	private String inappMetaAuthor;

	@Column(name="inappmeta_translator")
	private String inappMetaTranslator;

	@Column(name="inappmeta_page")
	private String inappMetaPage;

	@Column(name="inappmeta_ISBN")
	private String inappMetaISBN;

	@Column(name="inappmeta_status")
	private String inappMetaStatus;

	@Column(name="inappmeta_price")
	private String inappMetaPrice;

	@Column(name="inappmeta_distributor")
	private String inappMetaDistributor;

	@Column(name="inappmeta_size")
	private String inappMetaSize;

	@Column(name="inappmeta_description")
	private String inappMetaDescription;

	@Column(name="inappmeta_buildflag")
	private String inappMetaBuildflag;

	@Column(name="inappmeta_cover1")
	private String inappMetaCover1;

	@Column(name="inappmeta_cover2")
	private String inappMetaCover2;

	@Column(name="inappmeta_cover3")
	private String inappMetaCover3;

	@Column(name="inappmeta_cover4")
	private String inappMetaCover4;

	@Column(name="inappmeta_body")
	private String inappMetaBody;
	
	public Integer getInappMetaSeq() {
		return inappMetaSeq;
	}

	public void setInappMetaSeq(Integer inappMetaSeq) {
		this.inappMetaSeq = inappMetaSeq;
	}

	public Integer getInappSeq() {
		return inappSeq;
	}

	public void setInappSeq(Integer inappSeq) {
		this.inappSeq = inappSeq;
	}


	public String getInappMetaSubtitle() {
		return inappMetaSubtitle;
	}

	public void setInappMetaSubtitle(String inappMetaSubtitle) {
		this.inappMetaSubtitle = inappMetaSubtitle;
	}

	public String getInappMetaAuthor() {
		return inappMetaAuthor;
	}

	public void setInappMetaAuthor(String inappMetaAuthor) {
		this.inappMetaAuthor = inappMetaAuthor;
	}

	public String getInappMetaTranslator() {
		return inappMetaTranslator;
	}

	public void setInappMetaTranslator(String inappMetaTranslator) {
		this.inappMetaTranslator = inappMetaTranslator;
	}

	public String getInappMetaPage() {
		return inappMetaPage;
	}

	public void setInappMetaPage(String inappMetaPage) {
		this.inappMetaPage = inappMetaPage;
	}

	public String getInappMetaISBN() {
		return inappMetaISBN;
	}

	public void setInappMetaISBN(String inappMetaISBN) {
		this.inappMetaISBN = inappMetaISBN;
	}

	public String getInappMetaStatus() {
		return inappMetaStatus;
	}

	public void setInappMetaStatus(String inappMetaStatus) {
		this.inappMetaStatus = inappMetaStatus;
	}

	public String getInappMetaPrice() {
		return inappMetaPrice;
	}

	public void setInappMetaPrice(String inappMetaPrice) {
		this.inappMetaPrice = inappMetaPrice;
	}

	public String getInappMetaDistributor() {
		return inappMetaDistributor;
	}

	public void setInappMetaDistributor(String inappMetaDistributor) {
		this.inappMetaDistributor = inappMetaDistributor;
	}

	public String getInappMetaSize() {
		return inappMetaSize;
	}

	public void setInappMetaSize(String inappMetaSize) {
		this.inappMetaSize = inappMetaSize;
	}

	public String getInappMetaDescription() {
		return inappMetaDescription;
	}

	public void setInappMetaDescription(String inappMetaDescription) {
		this.inappMetaDescription = inappMetaDescription;
	}

	public String getInappMetaBuildflag() {
		return inappMetaBuildflag;
	}

	public void setInappMetaBuildflag(String inappMetaBuildflag) {
		this.inappMetaBuildflag = inappMetaBuildflag;
	}

	public String getInappMetaCover1() {
		return inappMetaCover1;
	}

	public void setInappMetaCover1(String inappMetaCover1) {
		this.inappMetaCover1 = inappMetaCover1;
	}

	public String getInappMetaCover2() {
		return inappMetaCover2;
	}

	public void setInappMetaCover2(String inappMetaCover2) {
		this.inappMetaCover2 = inappMetaCover2;
	}

	public String getInappMetaCover3() {
		return inappMetaCover3;
	}

	public void setInappMetaCover3(String inappMetaCover3) {
		this.inappMetaCover3 = inappMetaCover3;
	}

	public String getInappMetaCover4() {
		return inappMetaCover4;
	}

	public void setInappMetaCover4(String inappMetaCover4) {
		this.inappMetaCover4 = inappMetaCover4;
	}

	public String getInappMetaBody() {
		return inappMetaBody;
	}

	public void setInappMetaBody(String inappMetaBody) {
		this.inappMetaBody = inappMetaBody;
	}

	@Override
	public String toString() {
		return "InappMetaVO [inappMetaSeq=" + inappMetaSeq + ", inappSeq="
				+ inappSeq
				+ ", inappMetaSubtitle=" + inappMetaSubtitle
				+ ", inappMetaAuthor=" + inappMetaAuthor
				+ ", inappMetaTranslator=" + inappMetaTranslator
				+ ", inappMetaPage=" + inappMetaPage + ", inappMetaISBN="
				+ inappMetaISBN + ", inappMetaStatus=" + inappMetaStatus
				+ ", inappMetaPrice=" + inappMetaPrice
				+ ", inappMetaDistributor=" + inappMetaDistributor
				+ ", inappMetaSize=" + inappMetaSize
				+ ", inappMetaDescription=" + inappMetaDescription
				+ ", inappMetaBuildflag=" + inappMetaBuildflag
				+ ", inappMetaCover1=" + inappMetaCover1 + ", inappMetaCover2="
				+ inappMetaCover2 + ", inappMetaCover3=" + inappMetaCover3
				+ ", inappMetaCover4=" + inappMetaCover4 + ", inappMetaBody="
				+ inappMetaBody + "]";
	}

	
	
}
