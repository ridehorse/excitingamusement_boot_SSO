package com.exciting.dto;

import java.time.LocalDateTime;

import com.exciting.entity.BoardReplyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardReplyDTO {
	private int reply_num;
	private int board_id;
	private String member_id;
	private String b_reply;
	private LocalDateTime postdate;
	private int ref;
	private int seq;
	private int lev;
	private int cnt;
	
	
	public BoardReplyDTO(BoardReplyEntity entity) {
		super();
		this.reply_num = entity.getReply_num();
		this.board_id = entity.getBoard_id();
		this.member_id = entity.getMember_id();
		this.b_reply = entity.getB_reply();
		this.postdate = entity.getPostdate();
		this.ref = entity.getRef();
		this.seq = entity.getSeq();
		this.lev = entity.getLev();
	}
	
	public static BoardReplyEntity ToEntity(BoardReplyDTO entity) {
		return BoardReplyEntity.builder()
				.reply_num(entity.getReply_num())
				.board_id(entity.getBoard_id())
				.member_id(entity.getMember_id())
				.b_reply(entity.getB_reply())
				.postdate(entity.getPostdate())
				.ref(entity.getRef())
				.seq(entity.getSeq())
				.lev(entity.getLev())
				.build();
	}
	
	
	
}
