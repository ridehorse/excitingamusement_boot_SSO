package com.exciting.dto;

import java.math.BigDecimal;

import com.exciting.login.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MypointDTO {

	private BigDecimal sum_point;
	private BigDecimal m_point;
	private int point_id;
	private int order_id;
	private String member_id;
	
	public MypointDTO(BigDecimal sum_point, String member_id) {
		this.sum_point = sum_point;
		this.member_id = member_id;
	}
	
	public MypointDTO(int order_id, String member_id) {
		this.order_id = order_id;
		this.member_id = member_id;
	}
//	public MypointDTO(BigDecimal sum_point, BigDecimal m_point, int order_id, String member_id) {
//	    this.sum_point = sum_point;
//	    this.m_point = m_point;
//	    this.order_id = order_id;
//	    this.member_id = member_id;
//	}


}
