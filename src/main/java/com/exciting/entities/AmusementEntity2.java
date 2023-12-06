package com.exciting.entities;

import javax.persistence.*;

import com.exciting.dto.AmusementAimageDTO;

import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "amusement")
@Data
public class AmusementEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amuse_id")
    private Integer amuse_id;

    @Column(name = "a_name", nullable = false, length = 100)
    private String a_name;

    @Column(name = "a_country", nullable = false, length = 45)
    private String a_country;

    @Column(name = "a_time", length = 45)
    private String a_time;

    @Column(name = "a_img", length = 300)
    private String a_img;

    @Column(name = "a_opening")
    private Date a_opening;

    @Column(name = "a_view", nullable = false)
    private Integer a_view;

    @Column(name = "a_info", length = 500)
    private String a_info;

    @Column(name = "a_lat")
    private Double a_lat;

    @Column(name = "a_lng")
    private Double a_lng;

    @Column(name = "review_cnt")
    private Integer review_cnt;

    @Column(name = "avg_grade")
    private Float avg_grade;

    @Column(name = "a_note", length = 45)
    private String a_note;

}
