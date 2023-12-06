package com.exciting.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

import com.exciting.login.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "mypoint")
public class Mypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_id", nullable = false)
    private int point_id;

    @Column(name = "m_point", precision = 7, scale = 2)
    private BigDecimal m_point;
    
    @Column(name = "sum_point", precision = 7, scale = 2)
    private BigDecimal sum_point;
    
    @Column(name = "member_id",insertable =false, updatable = false)
    private String member_id;
    
    

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
    
    @Override
    public String toString() {
        return "Mypoint{" +
                "point_id=" + point_id +
                ", m_point=" + m_point +
                ", sum_point=" + sum_point +
                ", member_id=" + member_id +
                '}';
    }


}
