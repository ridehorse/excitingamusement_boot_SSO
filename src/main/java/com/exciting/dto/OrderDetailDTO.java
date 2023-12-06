package com.exciting.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
	private int order_detail_id;
	private int order_id;
	private int ticket_id;
	private int quantity;
}
