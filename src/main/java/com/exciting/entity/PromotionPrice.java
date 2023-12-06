package com.exciting.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // double 대신에 소수점을 처리하기 좋은 BigDecimal 사용

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "promotion") // 테이블 이름을 지정합니다.
@Data
@Builder
public class PromotionPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_id", nullable = false)
    private Integer promotion_id;
    
    
    @Column(name = "promotion_content", nullable = false, length = 500)
    private String promotion_content;

    @Column(name = "promotion_name", nullable = false, length = 255)
    private String promotion_name;

    @Column(name = "promotion_img", nullable = false, length = 255)
    private String promotion_img;

    @Column(name = "discount", columnDefinition = "DOUBLE")
    private BigDecimal discount;
    
    @Column(name = "ticket_price")
    private int ticket_price;

    // 생성자, getter, setter 등 필요한 메소드를 정의합니다.
}
