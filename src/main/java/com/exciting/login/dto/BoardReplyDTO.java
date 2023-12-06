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
public class BoardReplyDTO {

	int reply_num;
	int board_id;
	String member_id;
	String b_reply;
	Date postdate;
	int ref;
	int seq;
	int lev;
	
}
