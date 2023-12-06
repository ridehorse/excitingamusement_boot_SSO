package com.exciting.login.dto;

public class PagingDTO {
	
	private int pageNum;
	private int startPage;
	private int endPage;
//	존재 한다,안한다.
	private boolean prev, next;
	
//	총 개시물 갯수
	private int total;
	
//	현재 페이지(pageNum)와 한페이지에 표현하는 개시글(amount)의 갯수
	private Criteria cri;
	
	public PagingDTO() {
	};
	
//	pageNum, amount, total 매개변스
	public PagingDTO(Criteria cri,int total) {
		
		this.cri = cri;
		this.total = total;
		System.out.println("PagingDTO.pageNum : " + cri.getPageNum());
		
//		한페이지에서 나타나는 마지막 개시물의 번호
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0)*10);
		System.out.println("PagingDTO.endPage : " + endPage);
//		한페이지에서 나타나는 처음 게시물의 번호
		this.startPage = this.endPage - 9;
		
//		실제로 끝나는 페이지
		int realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
		
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

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public String toString() {
		return "PagingDTO [pageNum=" + pageNum + ", startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev
				+ ", next=" + next + ", total=" + total + ", cri=" + cri + "]";
	}

	
}
