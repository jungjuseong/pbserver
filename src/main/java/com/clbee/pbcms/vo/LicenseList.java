package com.clbee.pbcms.vo;

import java.util.List;

public class LicenseList {
	private List<LicenseVO> list;
	private int pageSize;
	private int maxResult;
	private int totalCount;
	private int totalPage;
	private int currentPage;
	private int startNo;
	private int endNo;
	private int startPage;
	private int endPage;

	private String searchMember;
	private String searchStatus;
	
	public LicenseList() {
		// TODO Auto-generated constructor stub
	}
	
	public LicenseList(int pageSize, int totalCount, int currentPage, int maxResult) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.maxResult = maxResult;
		calc();
	}
	
	// ������ ������ ����ϱ�
	private void calc(){
		totalPage = (totalCount-1)/maxResult + 1;
		currentPage = currentPage>totalPage ? totalPage : currentPage;
		startNo = (currentPage-1) * pageSize;
		endNo = startNo + pageSize - 1;
		endNo = endNo>totalCount ? totalCount : endNo;
		startPage = ((currentPage-1)/pageSize) * pageSize + 1;
		endPage = startPage + pageSize -1;
		endPage  = endPage>totalPage ? totalPage : endPage;
	}

	public void calc(int pageSize, int totalCount, Integer currentPage, int maxResult){
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.maxResult = maxResult;	
		totalPage = (totalCount-1)/maxResult + 1;
		currentPage = currentPage>totalPage ? totalPage : currentPage;
		startNo = (currentPage-1) * maxResult;
		endNo = startNo + maxResult - 1;
		endNo = endNo>totalCount ? totalCount : endNo;
		startPage = ((currentPage-1)/pageSize) * pageSize + 1;
		endPage = startPage + pageSize -1;
		endPage  = endPage>totalPage ? totalPage : endPage;
	}
	
	public List<LicenseVO> getList() {
		return list;
	}
	
	public void setList(List<LicenseVO> list) {
		this.list = list;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getMaxResult() {
		return maxResult;
	}
	
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getStartNo() {
		return startNo;
	}
	
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	
	public int getEndNo() {
		return endNo;
	}
	
	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getSearchMember() {
		return searchMember;
	}

	public void setSearchMember(String searchMember) {
		this.searchMember = searchMember;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

}
