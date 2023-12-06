package com.exciting.dto;

import lombok.Data;

@Data
public class AmuseReviewDTO {
	private Integer review_id;
	private Integer amuse_id;
	private String member_id;
	private String r_content;
	private Integer r_grade;
	private String r_regidate;
	private Integer ref;
	private Integer level;
	private Integer seq;
}
