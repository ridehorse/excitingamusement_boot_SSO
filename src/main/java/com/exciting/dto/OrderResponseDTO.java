package com.exciting.dto;

import java.util.List;

import com.exciting.entity.Mypoint;
import com.exciting.entity.Promotion;

import lombok.Data;

@Data
public class OrderResponseDTO {
	private List<PromotionDTO> price;
    private List<MypointDTO> mypoint;
    
    public OrderResponseDTO(List<PromotionDTO> price, List<MypointDTO> mypointdto) {
        this.price = price;
        this.mypoint = mypointdto;
    }

}
