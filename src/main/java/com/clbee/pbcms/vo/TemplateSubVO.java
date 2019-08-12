package com.clbee.pbcms.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_template_sub database table.
 * 
 */
@Entity
//@Table(name="tb_template_sub", catalog="pbcms_test")
@Table(name="tb_template_sub", catalog="pbcms_new")
public class TemplateSubVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="templatesub_seq")
	private int templatesubSeq;

	@Column(name="template_seq")
	private int templateSeq;

	@Column(name="user_seq")
	private int userSeq;

	public TemplateSubVO() {
	}

	public int getTemplatesubSeq() {
		return templatesubSeq;
	}

	public void setTemplatesubSeq(int templatesubSeq) {
		this.templatesubSeq = templatesubSeq;
	}

	public int getTemplateSeq() {
		return this.templateSeq;
	}

	public void setTemplateSeq(int templateSeq) {
		this.templateSeq = templateSeq;
	}

	public int getUserSeq() {
		return this.userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

}