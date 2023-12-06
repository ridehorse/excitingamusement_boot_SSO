package com.exciting.dto;

import lombok.Data;

@Data
public class TicketDTO {
	private int ticket_id;
	private int promotion_id;
	private String ticket_name;
	private int ticket_price;
}
