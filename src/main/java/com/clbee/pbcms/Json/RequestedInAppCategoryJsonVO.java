package com.clbee.pbcms.Json;

import java.util.ArrayList;
import java.util.List;

import com.clbee.pbcms.vo.InappcategoryVO;

public class RequestedInAppCategoryJsonVO {

	Integer categorySeq;
	Integer categoryParent;
	String categoryName;
	String categoryDepth;
	
	List<RequestedInAppJsonVO> decentInAppList;
	List<RequestedInAppCategoryJsonVO> decentInAppCategoryList;
	public RequestedInAppCategoryJsonVO(InappcategoryVO inappcategoryVO) {
		super();
		if(inappcategoryVO != null){
			this.categorySeq = inappcategoryVO.getCategorySeq();
			this.categoryName = inappcategoryVO.getCategoryName();
			this.categoryParent = inappcategoryVO.getCategoryParent();
			this.categoryDepth = inappcategoryVO.getDepth();
		}
		this.decentInAppList = new ArrayList();
		this.decentInAppCategoryList =  new ArrayList();
	}
	public Integer getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(Integer categorySeq) {
		this.categorySeq = categorySeq;
	}
	public Integer getCategoryParent() {
		return categoryParent;
	}
	public void setCategoryParent(Integer categoryParent) {
		this.categoryParent = categoryParent;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDepth() {
		return categoryDepth;
	}
	public void setCategoryDepth(String categoryDepth) {
		this.categoryDepth = categoryDepth;
	}
	public List<RequestedInAppJsonVO> getDecentInAppList() {
		return decentInAppList;
	}
	public void setDecentInAppList(List<RequestedInAppJsonVO> decentInAppList) {
		this.decentInAppList = decentInAppList;
	}
	public List<RequestedInAppCategoryJsonVO> getDecentInAppCategoryList() {
		return decentInAppCategoryList;
	}
	public void setDecentInAppCategoryList(
			List<RequestedInAppCategoryJsonVO> decentInAppCategoryList) {
		this.decentInAppCategoryList = decentInAppCategoryList;
	}
	
	
	
	
}