package com.exciting.dto;

import java.time.LocalDateTime;

import com.exciting.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {
	
	
    private int board_id;
    private String b_title;
    private String b_content;
    private LocalDateTime postdate;
    private int visitcount;
    private String b_type;  
    private int level;
    private String member_id;
    private int favorite;
    private int hate;
    private int viewCnt;
    
	public BoardDTO(final BoardEntity board) {
		super();
		this.board_id = board.getBoard_id();
		this.b_title = board.getB_title();
		this.b_content = board.getB_content();
		this.postdate = board.getPostdate();
		this.visitcount = board.getVisitcount();
		this.b_type = board.getB_type();
		this.level = board.getLevel();
		this.member_id = board.getMember_id();
		this.favorite = board.getFavorite();
		this.hate = board.getHate();
	}
	
	public static BoardEntity toEntity(final BoardDTO board) {
		return BoardEntity.builder()
				.board_id(board.getBoard_id())
				.b_title(board.getB_title())
				.b_content(board.getB_content())
				.postdate(board.getPostdate())
	            .visitcount(board.getVisitcount())
	            .b_type(board.getB_type())
	            .level(board.getLevel())
	            .member_id(board.getMember_id())
	            .favorite(board.getFavorite())
	            .hate(board.getHate())
				.build();
	}
	
    
}
