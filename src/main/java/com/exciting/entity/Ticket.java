package com.exciting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket") // 테이블 이름을 지정합니다.
@Data
public class Ticket {
    public Ticket(int ticketId) {
    	this.ticket_id = ticketId;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id", nullable = false)
    private int ticket_id;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    @JsonBackReference
    private Promotion promotion;

    @Column(name = "ticket_name", length = 50)
    private String ticketName;

    @Column(name = "ticket_price")
    private int ticket_price;

    
}
