package com.exciting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "aimage")
@Data
public class AimageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aimg_id")
    private int aimg_id;

    @Column(name = "amuse_id")
    private int amuse_id;

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

}