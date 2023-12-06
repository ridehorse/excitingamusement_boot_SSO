package com.exciting.login.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name="boardreply")
public class BoardReply {
	
	@Id
	private int reply_num;
	private int board_id;
	private String member_id;
	private String b_reply;
	private Date postdate;
	private int ref;
	private int seq;
	private int lev;
	
	
}
