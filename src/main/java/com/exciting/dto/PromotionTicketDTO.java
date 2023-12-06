package com.exciting.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromotionTicketDTO {
	private Integer promotion_id;
    private String promotion_content;
    private String promotion_name;
    private String promotion_img;
    private BigDecimal discount;
    private int ticket_id;
    private String ticket_name;
    private double ticket_price;
	
	public PromotionTicketDTO(Integer promotion_id, String promotion_content, String promotion_name, String promotion_img,
			double ticket_price, BigDecimal discount) {
        this.promotion_id = promotion_id;
        this.promotion_content = promotion_content;
        this.promotion_name = promotion_name;
        this.promotion_img = promotion_img;
        this.ticket_price = ticket_price;
        this.discount = discount;
    }
	
	public PromotionTicketDTO(Integer promotion_id, String promotion_content, String promotion_name, String promotion_img,
	        double ticket_price, BigDecimal discount, int ticket_id, String ticket_name) {
	    this.promotion_id = promotion_id;
	    this.promotion_content = promotion_content;
	    this.promotion_name = promotion_name;
	    this.promotion_img = promotion_img;
	    this.ticket_price = ticket_price;
	    this.discount = discount;
	    this.ticket_id = ticket_id;
	    this.ticket_name = ticket_name;
	}

}
