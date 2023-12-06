package com.exciting.login.dto;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {

	
	int board_id;
	String b_title;
	String b_content;
	Date postdate;
	int visitcount;
	String b_type;
	int level;
	String member_id;
	int favorite;
	int hate;

}
