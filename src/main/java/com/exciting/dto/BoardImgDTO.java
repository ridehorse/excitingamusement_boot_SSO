package com.exciting.dto;

import com.exciting.entity.BoardImgEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardImgDTO {
	private Integer boardimg_num;
	private Integer board_id;
	private String boardimg;
	private Integer announcement_num;
	private Integer inquiry_num;
	
	
	public BoardImgDTO(final BoardImgEntity entity) {
	    super();
	    this.boardimg_num = entity.getBoardimg_num();
	    this.board_id = entity.getBoard_id();
	    this.boardimg = entity.getBoardimg();
	    this.announcement_num = entity.getAnnouncement_num();
	    this.inquiry_num = entity.getInquiry_num();
	}
	
	public static BoardImgEntity toEntity(BoardImgDTO dto) {
	    return BoardImgEntity.builder()
	            .boardimg_num(dto.getBoardimg_num())
	            .board_id(dto.getBoard_id())
	            .boardimg(dto.getBoardimg())
	            .announcement_num(dto.getAnnouncement_num())
	            .inquiry_num(dto.getInquiry_num())
	            .build();
	}
	
	
}
