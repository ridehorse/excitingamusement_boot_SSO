package com.exciting.dto;

import com.exciting.entity.BoardFavoriteEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardFavoriteDTO {
	private int favorite_num;
	private int board_id;
	private String member_id;
	private int favorite;
	private int hate;
	private String message;
	private int checkData;
	
	public BoardFavoriteDTO(final BoardFavoriteEntity board) {
        super();
        this.favorite_num = board.getFavorite_num();
        this.board_id = board.getBoard_id();
        this.member_id = board.getMember_id();
        this.favorite = board.getFavorite();
        this.hate = board.getHate();
    }
	
	public static BoardFavoriteEntity toEntity(final BoardFavoriteDTO board) {
	   return BoardFavoriteEntity.builder()
			.favorite_num(board.getFavorite_num())
	        .board_id(board.getBoard_id())
	        .member_id(board.getMember_id())
	        .favorite(board.getFavorite())
	        .hate(board.getHate())
	        .build();
	}
	
}
