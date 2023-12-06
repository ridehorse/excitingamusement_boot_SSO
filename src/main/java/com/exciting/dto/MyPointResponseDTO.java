package com.exciting.dto;

import java.util.List;

import lombok.Data;

@Data
public class MyPointResponseDTO {
	private List<PromotionDTO> orderlist;
    private List<MypointDTO> mypoint;
    //private List<TicketDTO> ticket;
    //private List<AmusementDTO> amusement;
    
    public MyPointResponseDTO(List<PromotionDTO> orderlist, List<MypointDTO> mypoint) {
        this.orderlist = orderlist;
        this.mypoint = mypoint;
        //this.ticket = ticket;
        //this.amusement = amusement;
    }

}
