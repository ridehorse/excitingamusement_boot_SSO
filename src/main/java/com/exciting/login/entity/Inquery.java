package com.exciting.login.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="inquery")
public class Inquery {
	
	@Id
	private int inquiry_num;
	private String b_type;
	private String b_title;
	private String b_content;
	private String member_id;
//	@CreationTimestamp
	private Date postdate;
	private int ref; 
	private int lev;
	private int seq;
	
}
