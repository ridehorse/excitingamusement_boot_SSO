package com.exciting.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exciting.dto.BoardFavoriteDTO;
import com.exciting.entity.BoardFavoriteEntity;

@Repository
public interface BoardFavoriteRepasitory extends JpaRepository<BoardFavoriteEntity, Integer>,JpaSpecificationExecutor<BoardFavoriteEntity> {
	
	@Query("select b from BoardFavoriteEntity b where board_id = ?1")
	BoardFavoriteDTO findByBoardId(int board_id);
		
	@Query("select b from BoardFavoriteEntity b where board_id = ?1 and member_id = ?2")
	Optional<BoardFavoriteEntity> getFavoriteData(int board_id,String member_id);
	
	@Modifying
	@Query(nativeQuery = true,value="delete from favoriteboard b where b.board_id = ?1")
	void deleteByBoardId(int board_id);
}
