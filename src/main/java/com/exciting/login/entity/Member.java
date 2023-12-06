package com.exciting.login.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member", uniqueConstraints = {@UniqueConstraint(columnNames = "member_id")})
public class Member {
	
	@Id
	private String member_id;
	
	private String m_name;
	
	private String m_birth;
	
	private String m_pass;
	
	private String m_email;
	
	private String m_address;
	
	@Column(name = "regidate", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
	private Date m_regidate;
	
	private String m_phone;
	
	private String m_gender;
	
//	private int m_admin;
	
	private String m_image;
	
	private String m_kakao_id;
	
	private String m_github_id;
	
	private String m_naver_id;
	
//	private int m_point;
	
	private String token;
	
	private String roles;
    

}
