package com.exciting.entity;

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
@Table(name = "aimage")
@NoArgsConstructor
public class AmuseImageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aimg_id")
	private Integer aimg_id;
	
	private String aimg_name;
	
	@Column(name = "amuse_id")
	private Integer amuse_id;
	
	@Column(nullable = false)
	private String url;
}
