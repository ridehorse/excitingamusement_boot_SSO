package com.exciting.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "announcement")
public class AnnouncementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "announcement_num")
	private int announcement_num;

	@Column(name = "c_type", length = 20)
	private String c_type;

	@Column(name = "c_title")
	private String c_title;

	@Column(name = "c_content")
	private String c_content;

	@Column(name = "postdate")
	private LocalDateTime postdate;

}
