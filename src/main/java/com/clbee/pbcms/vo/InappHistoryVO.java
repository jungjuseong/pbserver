package com.clbee.pbcms.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.engine.profile.Fetch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tb_inapp database table.
 * 
 */
@Entity
//@Table(name="tb_inapphistory", catalog="pbcms_test")
@Table(name="tb_inapphistory", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class InappHistoryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="inapp_seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer inappSeq;

	@Column(name = "store_bundle_id")
	private String storeBundleId;
	
	@Column(name="category_name")
	private String categoryName;

	@Column(name="category_seq")
	private Integer categorySeq;

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

	@Column(name="coupon_gb")
	private String couponGb;

	@Column(name="coupon_num")
	private String couponNum;

	@Lob
	@Column(name="description_text")
	private String descriptionText;

	@Column(name="distr_gb")
	private String distrGb;

	@Column(name="icon_org_file")
	private String iconOrgFile;

	@Column(name="icon_save_file")
	private String iconSaveFile;

	@Column(name="inapp_name")
	private String inappName;

	@Column(name="inapp_org_file")
	private String inappOrgFile;

	@Column(name="inapp_save_file")
	private String inappSaveFile;

	@Column(name="inapp_size")
	private String inappSize;

	@Column(name="inapp_url")
	private String inappUrl;

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
	
	@Column(name="use_user_gb")
	private String useUserGb;
	
	@Column(name="screen_type")
	private String screenType;

	//20180403 : lsy - add column complet_dt, chg_contents_dt
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "complet_dt")
	private Date completDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "chg_contents_dt")
	private Date chgContentsDt;
	
	@Column(name="distribute_accept_id")
	private String distributeAcceptId;
	
	@Column(name="distribute_request_id")
	private String distributeRequestId;
	//20180403 : lsy - add column complet_dt, chg_contents_dt - end


	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( nullable=true, name="chg_user_seq",  referencedColumnName="user_seq", insertable=false, updatable=false)
	private MemberVO chgMemberVO;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( nullable=true, name="reg_user_seq", referencedColumnName="user_seq", insertable=false, updatable=false)
	private MemberVO regMemberVO;
	
	
	
	
	public MemberVO getChgMemberVO() {
		return chgMemberVO;
	}

	public void setChgMemberVO(MemberVO chgMemberVO) {
		this.chgMemberVO = chgMemberVO;
	}

	public MemberVO getRegMemberVO() {
		return regMemberVO;
	}

	public void setRegMemberVO(MemberVO regMemberVO) {
		this.regMemberVO = regMemberVO;
	}

	
	@NotFound( action = NotFoundAction.IGNORE )
	@OneToMany 
	@Cascade(CascadeType.DELETE)
	@JoinColumn( nullable=true, name="inapp_seq", referencedColumnName="inapp_seq", insertable=false, updatable=false )
	private List<InappSubVO> inappSubVO;


	public List<InappSubVO> getInappSubVO() {
		return inappSubVO;
	}

	public void setInappSubVO(List<InappSubVO> inappSubVO) {
		this.inappSubVO = inappSubVO;
	}


	
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@Cascade(CascadeType.DELETE)
	@JoinColumn( nullable=true, name="inapp_seq",  referencedColumnName="inapp_seq", insertable=false, updatable=false)
	private InappMetaVO inappMetaVO;

	public InappMetaVO getInappMetaVO() {
		return inappMetaVO;
	}

	public void setInappMetaVO(InappMetaVO inappMetaVO) {
		this.inappMetaVO = inappMetaVO;
	}

	public void setInappVO( InappHistoryVO updatedVO) {

		this.storeBundleId = updatedVO.getStoreBundleId();
		this.categoryName = updatedVO.getCategoryName();
		this.categorySeq = updatedVO.getCategorySeq();
		this.chgDt = updatedVO.getChgDt();
		this.chgUserGb = updatedVO.getChgUserGb();
		this.chgUserId = updatedVO.getChgUserId();
		this.chgUserSeq = updatedVO.getChgUserSeq();
		this.completGb = updatedVO.getCompletGb();
		this.couponGb = updatedVO.getCouponGb();
		this.couponNum = updatedVO.getCouponNum();
		this.descriptionText = updatedVO.getDescriptionText();
		this.distrGb = updatedVO.getDistrGb();
		this.iconOrgFile = updatedVO.getIconOrgFile();
		this.iconSaveFile = updatedVO.getIconSaveFile();
		this.inappName = updatedVO.getInappName();
		this.inappOrgFile = updatedVO.getIconOrgFile();
		this.inappSaveFile = updatedVO.getIconSaveFile();
		this.inappSize = updatedVO.getInappSize();
		this.inappUrl = updatedVO.getInappUrl();
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
		this.useAvailDt = updatedVO.getUseAvailDt();
		this.useDisableDt = updatedVO.getUseDisableDt();
		this.useGb = updatedVO.getUseGb();
		this.verNum = updatedVO.getVerNum();
		this.useUserGb = updatedVO.getUseUserGb();
		this.screenType = updatedVO.getScreenType();

		//20180403 : lsy - add column complet_dt, chg_contents_dt
		this.completDt = updatedVO.getCompletDt();
		this.chgContentsDt = updatedVO.getChgContentsDt();
		this.distributeAcceptId = updatedVO.getDistributeAcceptId();
		this.distributeRequestId = updatedVO.getDistributeRequestId();
		//20180403 : lsy - add column complet_dt, chg_contents_dt - end
	}

	public InappHistoryVO() {
	}

	public Integer getInappSeq() {
		return this.inappSeq;
	}

	public void setInappSeq(Integer inappSeq) {
		this.inappSeq = inappSeq;
	}



	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategorySeq() {
		return this.categorySeq;
	}

	public void setCategorySeq(Integer categorySeq) {
		this.categorySeq = categorySeq;
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

	public String getIconOrgFile() {
		return this.iconOrgFile;
	}

	public void setIconOrgFile(String iconOrgFile) {
		this.iconOrgFile = iconOrgFile;
	}

	public String getIconSaveFile() {
		return this.iconSaveFile;
	}

	public void setIconSaveFile(String iconSaveFile) {
		this.iconSaveFile = iconSaveFile;
	}

	public String getInappName() {
		return this.inappName;
	}

	public void setInappName(String inappName) {
		this.inappName = inappName;
	}

	public String getInappOrgFile() {
		return this.inappOrgFile;
	}

	public void setInappOrgFile(String inappOrgFile) {
		this.inappOrgFile = inappOrgFile;
	}

	public String getInappSaveFile() {
		return this.inappSaveFile;
	}

	public void setInappSaveFile(String inappSaveFile) {
		this.inappSaveFile = inappSaveFile;
	}

	public String getInappSize() {
		return this.inappSize;
	}

	public void setInappSize(String inappSize) {
		this.inappSize = inappSize;
	}

	public String getInappUrl() {
		return this.inappUrl;
	}

	public void setInappUrl(String inappUrl) {
		this.inappUrl = inappUrl;
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

	public String getUseUserGb() {
		return useUserGb;
	}

	public void setUseUserGb(String useUserGb) {
		this.useUserGb = useUserGb;
	}

	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}

	public String getStoreBundleId() {
		return storeBundleId;
	}

	public void setStoreBundleId(String storeBundleId) {
		this.storeBundleId = storeBundleId;
	}

	public Date getCompletDt() {
		return completDt;
	}

	public void setCompletDt(Date completDt) {
		this.completDt = completDt;
	}

	public Date getChgContentsDt() {
		return chgContentsDt;
	}

	public void setChgContentsDt(Date chgContentsDt) {
		this.chgContentsDt = chgContentsDt;
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