package com.exciting.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

import com.exciting.login.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
@Getter
@Setter
public class Orders {
    public Orders(int orderId) {
    	this.order_id = orderId;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private int order_id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    @JsonBackReference
    private Member member;
    

    @Column(name = "order_date")
    private LocalDateTime order_date;

    @Column(name = "checkorder", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean checkorder;

    @Column(name = "checkrefund", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean checkrefund;

    @Column(name = "use_point", precision = 7, scale = 2, nullable = true)
    private BigDecimal use_point;
    
    @OneToMany(mappedBy = "orders")
    @JsonManagedReference
    private List<Mypoint> mypoints;
    @OneToMany(mappedBy = "orders")
    private List<OrdersDetail> ordersDetails;
    

}
