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
@Table(name = "facility")
@NoArgsConstructor
public class Facility {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "facility_id")
	private Integer facility_id;
	
	@Column(name = "amuse_id")
	private Integer amuse_id;
	
	@Column(nullable = false)
	private String f_name;
	
	private String f_location;
	
	@Column(nullable = false)
	private String f_info;
	
	private String f_note;
	private String f_img;
	
	@ManyToOne
	private Amusement amusement;
}
