package com.exciting.entity;

import javax.persistence.*;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "boardimg")
public class BoardImgEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "boardimg_num")
	private Integer boardimg_num;

	@Column(nullable = true,name="board_id")
	private Integer board_id;

	@Column(length = 1000, name = "boardimg")
	private String boardimg;
	
	@Column(nullable = true ,name="announcement_num")
	private Integer announcement_num;
	
	@Column(nullable = true, name="inquiry_num")
	private Integer inquiry_num;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "inquiry_num")
//    private InquiryEntity inquiry;
}
