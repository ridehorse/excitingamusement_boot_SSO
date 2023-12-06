package com.exciting.login.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDTO {
	
	int inquiry_num;
	String b_type;
	String b_title;
	String b_content;
	String member_id;
	
	Date postdate;
	int ref; 
	int lev;
	int seq;

}
