package com.exciting.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exciting.dto.BoardReplyDTO;
import com.exciting.entity.BoardReplyEntity;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Integer>{
	
	@Query("select count(reply_num) as cnt from BoardReplyEntity  where board_id=?1")
	Long boardReplyCnt(int board_id);
	
	@Query( nativeQuery = true, value="select b.board_id, b.member_id, b.b_reply, b.postdate, b.reply_num, b.ref, b.seq, b.lev from boardreply as b where b.board_id = ?1 order by b.ref ")
	List<BoardReplyEntity> getCommentList(int board_id); 
	
	@Query(nativeQuery = true, value="select b.seq from boardreply b where ref = ?1 order by b.seq desc limit 1")
	int replyData(int ref);
	
	@Modifying
	@Query(nativeQuery = true,value = "delete from boardreply b where b.board_id = ?1")
	void deleteByBoardId(int board_id);
}
