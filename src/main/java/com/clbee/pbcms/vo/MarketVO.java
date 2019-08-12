package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tb_market database table.
 * 
 */
@Entity
//@Table(name="tb_market", catalog="pbcms_test")
@Table(name="tb_market", catalog="pbcms_new")
public class MarketVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="market_seq")
	private int marketSeq;

	@Column(name="app_isiap")
	private String appIsiap;

	@Column(name="market_url")
	private String marketUrl;

	@Column(name="ver_num")
	private String verNum;

	public MarketVO() {
	}

	
	
	public void setMarketVO( MarketVO updatedVO) {
		
		this.appIsiap = updatedVO.getAppIsiap();
		this.marketUrl = updatedVO.getMarketUrl();
		this.verNum = updatedVO.getVerNum();
	}



	public int getMarketSeq() {
		return this.marketSeq;
	}

	public void setMarketSeq(int marketSeq) {
		this.marketSeq = marketSeq;
	}

	public String getAppIsiap() {
		return this.appIsiap;
	}

	public void setAppIsiap(String appIsiap) {
		this.appIsiap = appIsiap;
	}

	public String getMarketUrl() {
		return this.marketUrl;
	}

	public void setMarketUrl(String marketUrl) {
		this.marketUrl = marketUrl;
	}

	public String getVerNum() {
		return this.verNum;
	}

	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}

}