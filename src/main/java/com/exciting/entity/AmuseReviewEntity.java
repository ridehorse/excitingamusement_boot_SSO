package com.exciting.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "amuse_review")
@NoArgsConstructor
public class AmuseReviewEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Integer review_id;
	
	@Column(name = "amuse_id")
	private Integer amuse_id;
	
	@Column(name = "member_id")
	private String member_id;
	
	@Column(nullable = false)
	private String r_content;
	
	private Integer r_grade;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String r_regidate;
	
	private Integer ref;
	private Integer level;
	private Integer seq;
}
