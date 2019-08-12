package com.clbee.pbcms.vo;


import java.util.List;



public class MemberList {
	private List<MemberVO> list;	// 1������ �з�
	private int pageSize;			// �ѹ��� ������ �ִ� ����������
	private int maxResult;			// �������� ����Ʈ ����
	private int totalCount;			// ��ü ����
	private int totalPage;			// ��ü ������
	private int currentPage;		// ���� ������
	private int startNo;			// ���� �۹�ȣ
	private int endNo;				// ���۹�ȣ
	private int startPage;			// ���� ������ ��ȣ
	private int endPage;			// ������ ������ ��ȣ
	
	public MemberList(int pageSize, int totalCount, int currentPage, int maxResult) {
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
	
	public List<MemberVO> getList() {
		return list;
	}
	public void setList(List<MemberVO> list) {
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

}
