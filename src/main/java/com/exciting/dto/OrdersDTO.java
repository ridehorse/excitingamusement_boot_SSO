package com.exciting.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrdersDTO {
	private int order_id;
	private Timestamp order_date;
	private Boolean checkOrder;
	private Boolean checkRefund;
	private String member_id;
	private BigDecimal use_point;
	
	 public OrdersDTO(String member_id, BigDecimal use_point) {
	        this.member_id = member_id;
	        this.use_point = use_point;
	    }
}
