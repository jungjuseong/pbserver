package com.clbee.pbcms.vo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the tb_bundle database table.
 * 
 */
@Entity
@Table(name="tb_bundle")
public class BundleVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bundle_seq")
	private Integer bundleSeq;

	@Column(name="app_seq")
	private Integer appSeq;

	@Column(name="bundle_name")
	private String bundleName;

	@Column(name="os_type")
	private Integer osType;

	@Column(name="prov_seq")
	private Integer provSeq;

	@Column(name="prov_test_gb")
	private String provTestGb;

	public BundleVO() {
	}

	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( nullable=true, name="app_seq", referencedColumnName="app_seq", insertable=false, updatable=false)
	private AppVO appVO;
	
	
	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}


	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( nullable=true, name="prov_seq", referencedColumnName="prov_seq", insertable=false, updatable=false)
	private ProvisionVO provisonVO;
	
	public ProvisionVO getprovisonVO() {
		return provisonVO;
	}

	public void setProvisonVO(ProvisionVO provisonVO) {
		this.provisonVO = provisonVO;
	}
	public Integer getBundleSeq() {
		return this.bundleSeq;
	}

	public void setBundleSeq(Integer bundleSeq) {
		this.bundleSeq = bundleSeq;
	}

	public Integer getAppSeq() {
		return this.appSeq;
	}

	public void setAppSeq(Integer appSeq) {
		this.appSeq = appSeq;
	}

	public String getBundleName() {
		return this.bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public Integer getOsType() {
		return this.osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public Integer getProvSeq() {
		return this.provSeq;
	}

	public void setProvSeq(Integer provSeq) {
		this.provSeq = provSeq;
	}

	public String getProvTestGb() {
		return this.provTestGb;
	}

	public void setProvTestGb(String provTestGb) {
		this.provTestGb = provTestGb;
	}

}