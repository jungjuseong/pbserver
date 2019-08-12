package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the tb_contents database table.
 * 
 */
@Entity
//@Table(name="tb_contents", catalog="pbcms_test")
@Table(name="tb_contents", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ContentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contents_seq")
	private Integer contentsSeq;

	@Column(name="app_name")
	private String appName;
	
	@Column(name="app_type")
	private String appType;
	
	@Column(name="company_seq")
	private Integer companySeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_dt")
	private Date chgDt;

	@Column(name="chg_user_gb")
	private String chgUserGb;

	@Column(name="chg_user_id")
	private String chgUserId;

	@Column(name="chg_user_seq")
	private Integer chgUserSeq;

	@Column(name="complet_gb")
	private String completGb;

	@Column(name="contents_name")
	private String contentsName;

	@Column(name="contents_size")
	private String contentsSize;

	@Column(name="contents_url")
	private String contentsUrl;

	@Column(name="contents_type")
	private String contentsType;

	@Column(name="coupon_gb")
	private String couponGb;

	@Column(name="coupon_num")
	private String couponNum;

	@Lob
	@Column(name="description_text")
	private String descriptionText;

	@Column(name="distr_gb")
	private String distrGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="limit_dt")
	private Date limitDt;

	@Column(name="limit_gb")
	private String limitGb;

	@Column(name="mem_down_amt")
	private String memDownAmt;

	@Column(name="mem_down_cnt")
	private String memDownCnt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mem_down_end_dt")
	private Date memDownEndDt;

	@Column(name="mem_down_gb")
	private String memDownGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mem_down_start_dt")
	private Date memDownStartDt;

	@Column(name="nonmem_down_amt")
	private String nonmemDownAmt;

	@Column(name="nonmem_down_cnt")
	private String nonmemDownCnt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="nonmem_down_end_dt")
	private Date nonmemDownEndDt;

	@Column(name="nonmem_down_gb")
	private String nonmemDownGb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="nonmem_down_star_dt")
	private Date nonmemDownStarDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	@Column(name="reg_user_gb")
	private String regUserGb;

	@Column(name="reg_user_id")
	private String regUserId;

	@Column(name="reg_user_seq")
	private Integer regUserSeq;

	@Column(name="upload_org_file")
	private String uploadOrgFile;

	@Column(name="upload_save_file")
	private String uploadSaveFile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="use_avail_dt")
	private Date useAvailDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="use_disable_dt")
	private Date useDisableDt;

	@Column(name="use_gb")
	private String useGb;

	@Column(name="ver_num")
	private String verNum;

	//20180403 : lsy - add column complet_dt, chg_contents_dt
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "distribute_req_dt")
	private Date distributeReqDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "chg_contents_dt")
	private Date chgContentsDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "distribute_complet_dt")
	private Date distributeCompletDt;
	
	@Column(name="distribute_accept_id")
	private String distributeAcceptId;
	
	@Column(name="distribute_request_id")
	private String distributeRequestId;
	//20180403 : lsy - add column complet_dt, chg_contents_dt - end

	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( name="contents_seq",  referencedColumnName="contents_seq", insertable=false, updatable=false )
	private ContentsappSubVO contentsappSubVO;

	
	
	public ContentVO() {
	}

	public void setContentVO( ContentVO updatedVO) {
		
		this.appName = updatedVO.getAppName();
		this.companySeq = updatedVO.getCompanySeq();
		this.chgDt = updatedVO.getChgDt();
		this.chgUserGb = updatedVO.getChgUserGb();
		this.chgUserId = updatedVO.getChgUserId();
		this.chgUserSeq = updatedVO.getChgUserSeq();
		this.completGb = updatedVO.getCompletGb();
		this.contentsName = updatedVO.getContentsName();
		this.contentsSize = updatedVO.getContentsSize();
		this.contentsUrl = updatedVO.getContentsUrl();
		this.contentsType = updatedVO.getContentsType();
		this.couponGb = updatedVO.getCouponGb();
		this.couponNum = updatedVO.getCouponNum();
		this.descriptionText = updatedVO.getDescriptionText();
		this.distrGb = updatedVO.getDistrGb();
		this.limitDt = updatedVO.getLimitDt();
		this.limitGb = updatedVO.getLimitGb();
		this.memDownAmt = updatedVO.getMemDownAmt();
		this.memDownCnt = updatedVO.getMemDownCnt();
		this.memDownEndDt = updatedVO.getMemDownEndDt();
		this.memDownGb = updatedVO.getMemDownGb();
		this.memDownStartDt = updatedVO.getMemDownStartDt();
		this.nonmemDownAmt = updatedVO.getNonmemDownAmt();
		this.nonmemDownCnt = updatedVO.getNonmemDownCnt();
		this.nonmemDownEndDt = updatedVO.getNonmemDownEndDt();
		this.nonmemDownGb = updatedVO.getNonmemDownGb();
		this.nonmemDownStarDt = updatedVO.getNonmemDownStarDt();
		this.regDt = updatedVO.getRegDt();
		this.regUserGb = updatedVO.getRegUserGb();
		this.regUserId = updatedVO.getRegUserId();
		this.regUserSeq = updatedVO.getRegUserSeq();
		this.uploadOrgFile = updatedVO.getUploadOrgFile();
		this.uploadSaveFile = updatedVO.getUploadSaveFile();
		this.useAvailDt = updatedVO.getUseAvailDt();
		this.useDisableDt = updatedVO.getUseDisableDt();
		this.useGb = updatedVO.getUseGb();
		this.verNum = updatedVO.getVerNum();

		//20180403 : lsy - add column complet_dt, chg_contents_dt
		this.distributeReqDt = updatedVO.getDistributeReqDt();
		this.chgContentsDt = updatedVO.getChgContentsDt();
		this.distributeCompletDt = updatedVO.getDistributeCompletDt();
		this.distributeAcceptId = updatedVO.getDistributeAcceptId();
		this.distributeRequestId = updatedVO.getDistributeRequestId();
		//20180403 : lsy - add column complet_dt, chg_contents_dt - end
	}


	public ContentsappSubVO getContentsappSubVO() {
		return contentsappSubVO;
	}

	public void setContentsappSubVO(ContentsappSubVO contentsappSubVO) {
		this.contentsappSubVO = contentsappSubVO;
	}

	public Integer getContentsSeq() {
		return this.contentsSeq;
	}

	public void setContentsSeq(Integer contentsSeq) {
		this.contentsSeq = contentsSeq;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Integer getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}

	public Date getChgDt() {
		return this.chgDt;
	}

	public void setChgDt(Date chgDt) {
		this.chgDt = chgDt;
	}

	public String getChgUserGb() {
		return this.chgUserGb;
	}

	public void setChgUserGb(String chgUserGb) {
		this.chgUserGb = chgUserGb;
	}

	public String getChgUserId() {
		return this.chgUserId;
	}

	public void setChgUserId(String chgUserId) {
		this.chgUserId = chgUserId;
	}

	public Integer getChgUserSeq() {
		return this.chgUserSeq;
	}

	public void setChgUserSeq(Integer chgUserSeq) {
		this.chgUserSeq = chgUserSeq;
	}

	public String getCompletGb() {
		return this.completGb;
	}

	public void setCompletGb(String completGb) {
		this.completGb = completGb;
	}

	public String getContentsName() {
		return this.contentsName;
	}

	public void setContentsName(String contentsName) {
		this.contentsName = contentsName;
	}

	public String getContentsSize() {
		return this.contentsSize;
	}

	public void setContentsSize(String contentsSize) {
		this.contentsSize = contentsSize;
	}

	public String getContentsUrl() {
		return this.contentsUrl;
	}

	public void setContentsUrl(String contentsUrl) {
		this.contentsUrl = contentsUrl;
	}

	public String getContentsType() {
		return this.contentsType;
	}

	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
	}

	public String getCouponGb() {
		return this.couponGb;
	}

	public void setCouponGb(String couponGb) {
		this.couponGb = couponGb;
	}

	public String getCouponNum() {
		return this.couponNum;
	}

	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}

	public String getDescriptionText() {
		return this.descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public String getDistrGb() {
		return this.distrGb;
	}

	public void setDistrGb(String distrGb) {
		this.distrGb = distrGb;
	}

	public Date getLimitDt() {
		return this.limitDt;
	}

	public void setLimitDt(Date limitDt) {
		this.limitDt = limitDt;
	}

	public String getLimitGb() {
		return this.limitGb;
	}

	public void setLimitGb(String limitGb) {
		this.limitGb = limitGb;
	}

	public String getMemDownAmt() {
		return this.memDownAmt;
	}

	public void setMemDownAmt(String memDownAmt) {
		this.memDownAmt = memDownAmt;
	}

	public String getMemDownCnt() {
		return this.memDownCnt;
	}

	public void setMemDownCnt(String memDownCnt) {
		this.memDownCnt = memDownCnt;
	}

	public Date getMemDownEndDt() {
		return this.memDownEndDt;
	}

	public void setMemDownEndDt(Date memDownEndDt) {
		this.memDownEndDt = memDownEndDt;
	}

	public String getMemDownGb() {
		return this.memDownGb;
	}

	public void setMemDownGb(String memDownGb) {
		this.memDownGb = memDownGb;
	}

	public Date getMemDownStartDt() {
		return this.memDownStartDt;
	}

	public void setMemDownStartDt(Date memDownStartDt) {
		this.memDownStartDt = memDownStartDt;
	}

	public String getNonmemDownAmt() {
		return this.nonmemDownAmt;
	}

	public void setNonmemDownAmt(String nonmemDownAmt) {
		this.nonmemDownAmt = nonmemDownAmt;
	}

	public String getNonmemDownCnt() {
		return this.nonmemDownCnt;
	}

	public void setNonmemDownCnt(String nonmemDownCnt) {
		this.nonmemDownCnt = nonmemDownCnt;
	}

	public Date getNonmemDownEndDt() {
		return this.nonmemDownEndDt;
	}

	public void setNonmemDownEndDt(Date nonmemDownEndDt) {
		this.nonmemDownEndDt = nonmemDownEndDt;
	}

	public String getNonmemDownGb() {
		return this.nonmemDownGb;
	}

	public void setNonmemDownGb(String nonmemDownGb) {
		this.nonmemDownGb = nonmemDownGb;
	}

	public Date getNonmemDownStarDt() {
		return this.nonmemDownStarDt;
	}

	public void setNonmemDownStarDt(Date nonmemDownStarDt) {
		this.nonmemDownStarDt = nonmemDownStarDt;
	}

	public Date getRegDt() {
		return this.regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getRegUserGb() {
		return this.regUserGb;
	}

	public void setRegUserGb(String regUserGb) {
		this.regUserGb = regUserGb;
	}

	public String getRegUserId() {
		return this.regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public Integer getRegUserSeq() {
		return this.regUserSeq;
	}

	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public String getUploadOrgFile() {
		return this.uploadOrgFile;
	}

	public void setUploadOrgFile(String uploadOrgFile) {
		this.uploadOrgFile = uploadOrgFile;
	}

	public String getUploadSaveFile() {
		return this.uploadSaveFile;
	}

	public void setUploadSaveFile(String uploadSaveFile) {
		this.uploadSaveFile = uploadSaveFile;
	}

	public Date getUseAvailDt() {
		return this.useAvailDt;
	}

	public void setUseAvailDt(Date useAvailDt) {
		this.useAvailDt = useAvailDt;
	}

	public Date getUseDisableDt() {
		return this.useDisableDt;
	}

	public void setUseDisableDt(Date useDisableDt) {
		this.useDisableDt = useDisableDt;
	}

	public String getUseGb() {
		return this.useGb;
	}

	public void setUseGb(String useGb) {
		this.useGb = useGb;
	}

	public String getVerNum() {
		return this.verNum;
	}

	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}

	public Date getChgContentsDt() {
		return chgContentsDt;
	}

	public void setChgContentsDt(Date chgContentsDt) {
		this.chgContentsDt = chgContentsDt;
	}

	public Date getDistributeReqDt() {
		return distributeReqDt;
	}

	public void setDistributeReqDt(Date distributeReqDt) {
		this.distributeReqDt = distributeReqDt;
	}

	public Date getDistributeCompletDt() {
		return distributeCompletDt;
	}

	public void setDistributeCompletDt(Date distributeCompletDt) {
		this.distributeCompletDt = distributeCompletDt;
	}

	public String getDistributeAcceptId() {
		return distributeAcceptId;
	}

	public void setDistributeAcceptId(String distributeAcceptId) {
		this.distributeAcceptId = distributeAcceptId;
	}

	public String getDistributeRequestId() {
		return distributeRequestId;
	}

	public void setDistributeRequestId(String distributeRequestId) {
		this.distributeRequestId = distributeRequestId;
	}

}