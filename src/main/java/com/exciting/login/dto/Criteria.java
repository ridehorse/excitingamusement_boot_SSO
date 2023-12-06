package com.exciting.login.dto;

public class Criteria {
	
	private int pageNum;
	private int amount;
	private String member_id;
	
	private int limitNum;
	
	public Criteria() {
	}
	
	public Criteria(int pageNum,int amount,String member_id) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.member_id = member_id;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", member_id=" + member_id + ", limitNum="
				+ limitNum + "]";
	}

}
