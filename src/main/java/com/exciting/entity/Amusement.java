package com.exciting.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "amusement")
@NoArgsConstructor
public class Amusement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "amuse_id")
	private Integer amuse_id;
	
	@Column(nullable = false)
	private String a_name;
	
	@Column(nullable = false)
	private String a_country;
	
	private String a_time;
	private String a_info;
	private String a_img;
	private String a_note;
	
	private Date a_opening;
	
	@Column(columnDefinition = "Integer default 0")
	private Integer a_view;
	
	private Float a_lat;
	private Float a_lng;
	private Integer review_cnt;
	
	@Column(columnDefinition = "float default 0.0")
	private Float avg_grade;
}
