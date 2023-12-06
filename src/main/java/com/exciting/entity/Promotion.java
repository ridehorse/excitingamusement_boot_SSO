package com.exciting.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "promotion") // 테이블 이름을 지정합니다.
@Data
@Builder
public class Promotion {

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
    private double discount;
    
    @OneToMany(mappedBy = "promotion")
    @JsonManagedReference
    private List<Ticket> tickets;
}
