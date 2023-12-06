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
public class MemberDTO {
	
	
	
	String member_id;
	
	String m_pass;
	
	String m_name;
	
	String m_email;
	
	String m_birth;
	
	String m_address;
	
	Date m_regidate;

	String m_phone;
	
	String m_gender;
	
//	int m_admin;
	
	String token;
	
	String m_kakao_id;
	
	String m_github_id;
	
	String m_naver_id;
	
//	int m_point;
	
	String m_image;
	
	String roles;
	
	
}
