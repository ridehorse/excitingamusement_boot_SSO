package com.exciting.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.dto.BoardImgDTO;
import com.exciting.entity.BoardImgEntity;

@Repository
public interface BoardImgRepository extends JpaRepository<BoardImgEntity, Integer>,JpaSpecificationExecutor<BoardImgEntity>{
	@Query("select b from BoardImgEntity b where b.board_id = ?1")
	List<BoardImgEntity> findByBoardId(int board_id);
	
	@Query("select b from BoardImgEntity b where b.announcement_num = ?1")
	List<BoardImgEntity> findByAnnouncement(int announcement_num);
	

	//@Query(value="select * from boardimg as b where b.inquiry_num = ?1",nativeQuery = true)
	@Query("select b from BoardImgEntity as b where b.inquiry_num = :inquiry_num")
	List<BoardImgEntity> findInquiryNum(@Param("inquiry_num") Integer inquiry_num);
	
	@Query("select b from BoardImgEntity as b where b.inquiry_num = :inquiry_num")
	List<BoardImgEntity> boardInquiryNum(@Param("inquiry_num") Integer inquiry_num);
	
	@Modifying
	@Query(nativeQuery = true, value = "delete from boardimg b where b.board_id = ?1")
	void deleteByBoardId(int board_id);

	
	
	
	
}
