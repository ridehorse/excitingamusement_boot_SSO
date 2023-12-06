package com.exciting.entities;

import javax.persistence.*;

import lombok.Data;

import java.math.BigDecimal; // double 대신에 소수점을 처리하기 좋은 BigDecimal 사용

@Entity
@Table(name = "promotion") // 테이블 이름을 지정합니다.
@Data
public class PromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_id", nullable = false)
    private int promotion_id;

    @Column(name = "amuse_id", nullable = false)
    private int amuse_id;
    
    @Column(name = "promotion_content", nullable = false, length = 500)
    private String promotion_content;

    @Column(name = "promotion_name", nullable = false, length = 255)
    private String promotion_name;

    @Column(name = "promotion_img", nullable = false, length = 255)
    private String promotion_img;

    @Column(name = "discount", columnDefinition = "DOUBLE")
    private BigDecimal discount;

    // 생성자, getter, setter 등 필요한 메소드를 정의합니다.
}
