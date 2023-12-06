package com.exciting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rides")
@NoArgsConstructor
public class Rides {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rides_id")
	private Integer rides_id;
	
	@Column(name = "amuse_id", nullable = false)
	private Integer amuse_id;
	
	@Column(nullable = false)
	private String r_name;
	
	private String r_location;
	private String r_info;
	private Integer r_min_height;
	private Integer r_max_height;
	private String r_note;
	private String r_img;
	private String r_video;
	
	@ManyToOne
	private Amusement amusement;
}
