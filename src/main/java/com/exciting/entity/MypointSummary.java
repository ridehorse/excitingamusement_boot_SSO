package com.exciting.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MypointSummary {
	private BigDecimal sum_point;
	private BigDecimal m_point;
	private int order_id;
	private String member_id;
	
	public MypointSummary(BigDecimal sum_point, BigDecimal m_point, int order_id, String member_id) {
        this.sum_point = sum_point;
        this.m_point = m_point;
        this.order_id = order_id;
        this.member_id = member_id;
    }

}
