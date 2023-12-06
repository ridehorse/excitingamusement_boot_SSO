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
@Table(name="board",uniqueConstraints = {@UniqueConstraint(columnNames = "board_id")})
public class Board {
	
	@Id
	private int board_id;
	private String b_title;
	private String b_content;
	@CreationTimestamp
	private Date postdate;
	private int visitcount;
	private String b_type;
	private int level;
	private String member_id;
	private int favorite;
	private int hate;
}
