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
public class MypageDTO {
	
//	회원 아이디
	String member_id;
	
	String b_content;
	
	int view_id;
	
	Date postdate;
	
	String category;
	
	String b_title;
	
//	어느 페이지의 댓글인가
//	글을 쓴것인가(1) 글의 댓글을 작성한것인가(2).
//	String depth;
//	String b_type;
	
	

	
	
}
