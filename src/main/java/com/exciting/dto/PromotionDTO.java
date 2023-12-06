package com.exciting.dto;

import java.math.BigDecimal;

import com.exciting.entity.Promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromotionDTO {
	private int promotion_id;
	private int quantity;
	private int ticket_price;
	private int order_id;
	private int ticket_id;
	private Integer amuse_id;
	private String promotion_content;
	private String promotion_name;
	private String promotion_img;
	private double discount;
	private String member_id;
	private String order_date;
	private String ticket_name;
	private BigDecimal use_point;
	private String a_name;
	private Boolean checkrefund;
	private Boolean checkorder;

	public static Promotion toEntity(PromotionDTO dto) {
		return Promotion.builder()
				.promotion_id(dto.getPromotion_id())
				//.amuse_id(dto.getAmuse_id())
				.promotion_content(dto.getPromotion_content())
				.promotion_name(dto.getPromotion_name())
				.promotion_img(dto.getPromotion_img())
				.discount(dto.getDiscount())
				.build();

	}

	public PromotionDTO(int promotion_id, String promotion_content, String promotion_name, String promotion_img,
			int ticket_price,int ticket_id,double discount) {
		this.promotion_id = promotion_id;
		this.promotion_content = promotion_content;
		this.promotion_name = promotion_name;
		this.promotion_img = promotion_img;
		this.ticket_price = ticket_price;
		this.ticket_id = ticket_id;
		this.discount = discount;
	}
	public PromotionDTO(int promotion_id, String promotion_content, String promotion_name, String promotion_img) {
		this.promotion_id = promotion_id;
		this.promotion_content = promotion_content;
		this.promotion_name = promotion_name;
		this.promotion_img = promotion_img;
	}
}

