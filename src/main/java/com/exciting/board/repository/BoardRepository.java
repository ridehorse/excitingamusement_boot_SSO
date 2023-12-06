package com.exciting.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.dto.BoardDTO;
import com.exciting.entity.BoardEntity;



@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	
	@Query("SELECT b FROM BoardEntity b WHERE b.board_id = :board_id")
	Optional<BoardEntity> findByBoardId(@Param("board_id") int board_id);

	
	
}
