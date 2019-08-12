package com.clbee.pbcms.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
//@Table(name="tb_notice", catalog="pbcms_test")
@Table(name="tb_notice", catalog="pbcms_new")
@DynamicInsert(true)
@DynamicUpdate(true)
public class NoticeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="notice_seq")
	private Integer noticeSeq;

	@Column(name="company_seq")
	private Integer companySeq;
	
	@Column(name="reg_user_seq")
	private Integer regUserSeq;
	
	@Column(name="notice_name")
	private String noticeName;

	@Lob
	@Column(name="notice_text")
	private String noticeText;

	@Column(name="notice_start_dt")
	private Date noticeStartDt;

	@Column(name="notice_end_dt")
	private Date noticeEndDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_dt")
	private Date regDt;

	
	@Column(name="app_gb")
	private String appGb;
	
	@Column(name="use_user_gb")
	private String useUserGb;
	
	@Column(name="public_gb")
	private String publicGb;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( name="reg_user_seq",  referencedColumnName="user_seq", insertable=false, updatable=false )
	private MemberVO memberVO;
	
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(  optional = true)
	@JoinColumn( name="notice_seq",  referencedColumnName="notice_seq", insertable=false, updatable=false )
	private NoticeappSubVO noticeappSubVO;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToMany
	@JoinColumn( nullable=true,  name="notice_seq",  referencedColumnName="notice_seq", insertable=false, updatable=false )
	private List<NoticeSubVO> noticeSubVO;



	public List<NoticeSubVO> getNoticeSubVO() {
		return noticeSubVO;
	}

	public void setNoticeSubVO(List<NoticeSubVO> noticeSubVO) {
		this.noticeSubVO = noticeSubVO;
	}

	public NoticeappSubVO getNoticeappSubVO() {
		return noticeappSubVO;
	}

	public void setNoticeappSubVO(NoticeappSubVO noticeappSubVO) {
		this.noticeappSubVO = noticeappSubVO;
	}

	public Integer getNoticeSeq() {
		return noticeSeq;
	}

	public void setNoticeSeq(Integer noticeSeq) {
		this.noticeSeq = noticeSeq;
	}

	public Integer getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Integer companySeq) {
		this.companySeq = companySeq;
	}
	
	public Integer getRegUserSeq() {
		return regUserSeq;
	}

	public void setRegUserSeq(Integer regUserSeq) {
		this.regUserSeq = regUserSeq;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticeText() {
		return noticeText;
	}

	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}

	public Date getNoticeStartDt() {
		return noticeStartDt;
	}

	public void setNoticeStartDt(Date noticeStartDt) {
		this.noticeStartDt = noticeStartDt;
	}

	public Date getNoticeEndDt() {
		return noticeEndDt;
	}

	public void setNoticeEndDt(Date noticeEndDt) {
		this.noticeEndDt = noticeEndDt;
	}

	public Date getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}

	public String getAppGb() {
		return appGb;
	}

	public void setAppGb(String appGb) {
		this.appGb = appGb;
	}

	public String getUseUserGb() {
		return useUserGb;
	}

	public void setUseUserGb(String useUserGb) {
		this.useUserGb = useUserGb;
	}

	public String getPublicGb() {
		return publicGb;
	}

	public void setPublicGb(String publicGb) {
		this.publicGb = publicGb;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	@Override
	public String toString() {
		return "NoticeVO [noticeSeq=" + noticeSeq + ", companySeq="
				+ companySeq + ", regUserSeq=" + regUserSeq + ", noticeName="
				+ noticeName + ", noticeText=" + noticeText
				+ ", noticeStartDt=" + noticeStartDt + ", noticeEndDt="
				+ noticeEndDt + ", regDt=" + regDt + ", appGb=" + appGb
				+ ", useUserGb=" + useUserGb + ", publicGb=" + publicGb
				+ ", memberVO=" + memberVO + ", noticeappSubVO="
				+ noticeappSubVO + ", noticeSubVO=" + noticeSubVO + "]";
	}
	
	
	
}
