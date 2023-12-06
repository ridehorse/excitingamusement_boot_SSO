package com.exciting.board.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.exciting.dto.BoardDTO;
import com.exciting.dto.BoardFavoriteDTO;
import com.exciting.dto.BoardReplyDTO;
import com.exciting.dto.InquiryDTO;
import com.exciting.entity.BoardEntity;
import com.exciting.entity.BoardFavoriteEntity;
import com.exciting.entity.BoardImgEntity;
import com.exciting.entity.BoardReplyEntity;

public interface BoardService {
	BoardDTO boardView(int board_id);
	Long boardReplyCnt(int board_id);
	void boardVisit(final BoardEntity entity);
	List<BoardImgEntity> boardImgSelect(int board_id);
	void replyInsert(final BoardReplyEntity boardReplyEntity);
	void changefavorite(final BoardFavoriteEntity boardFavoriteEntity,int checkData);
	List<BoardReplyEntity> getCommentList(final BoardReplyEntity boardReplyEntity);
	void reReplyInsert(final BoardReplyEntity boardReplyEntity);
	BoardEntity updateBoard(final BoardEntity entity); 
	int commitUpdateBoard(final BoardEntity boardEntity);
	void boardImgInsert(final BoardImgEntity boardImgEntity);
	List<BoardImgEntity> boardImgDelete(final BoardImgEntity boardImgEntity);
	void boardReply(final BoardReplyEntity boardReplyEntity);
	void replyDelete(final BoardReplyEntity boardReplyEntity);
	List<BoardImgEntity> deleteBoard(final BoardEntity boardentity);
	int createBoard(final BoardEntity boardEntity);
}
