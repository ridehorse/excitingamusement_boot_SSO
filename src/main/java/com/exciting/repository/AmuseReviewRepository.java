package com.exciting.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.dto.AmuseReviewDTO;
import com.exciting.entity.AmuseReviewEntity;

import lombok.val;

@Repository
public interface AmuseReviewRepository extends JpaRepository<AmuseReviewEntity, Integer>{
	@Query(value = "select review_id, amuse_id, member_id,"
			+ " r_content, r_grade, DATE_FORMAT(r_regidate, '%Y/%m/%d') as r_regidate,"
			+ " ref, level, seq"
			+ "	from amuse_review where amuse_id = :amuse_id order by ref DESC, seq asc", nativeQuery = true)
	public List<AmuseReviewEntity> amuseReviewList(@Param(value = "amuse_id") Integer amuse_id);
	
	//점수 평균 구하기
	@Query(value = "select round((sum(r_grade) / count(review_id)), 1) as avg"
			+ " from amuse_review where amuse_id = :amuse_id and level = 0", nativeQuery = true)
	public Float amuseReviewGradeAvg(@Param(value = "amuse_id") Integer amuse_id);
	
	//리뷰 개수 구하기
	@Query(value = "select count(*) from amuse_review where amuse_id = :amuse_id", nativeQuery = true)
	public Integer amuseReviewCount(@Param(value = "amuse_id") Integer amuse_id);
	
	//리뷰 하나 가져오기
	@Query(value = "select * from amuse_review where review_id = :review_id", nativeQuery = true)
	AmuseReviewEntity getOneReview(@Param(value = "review_id") Integer review_id);
	
	//리뷰 작성
	@Query(value = "insert into amuse_review (amuse_id, member_id, r_content, r_grade)"
			+ "	values(:amuse_id, :member_id, :r_content, :r_grade)", nativeQuery = true)
	@Modifying
	public Integer reviewWrite(@Param(value = "amuse_id") Integer amuse_id, 
			@Param(value = "member_id") String member_id,
			@Param(value = "r_content") String r_content,
			@Param(value = "r_grade") Integer r_grade);
	
	@Query(value = "select max(review_id) from amuse_review", nativeQuery = true)
	public Integer maxReviewId();
	
	//ref 수정
	@Query(value = "update amuse_review set ref = review_id where review_id = :review_id", nativeQuery = true)
	@Modifying
	@Transactional
	public void updateRef(@Param(value = "review_id") Integer review_id);
	
	//seq 수정
	@Query(value = "update amuse_review set seq = seq + 1 where ref = :ref and seq > :seq", nativeQuery = true)
	@Modifying
	@Transactional
	public void updateSeq(@Param(value = "ref") Integer ref, @Param(value = "seq") Integer seq);
	
	//답글 작성
	@Query(value = "insert into amuse_review (review_id, amuse_id, member_id, r_content, ref, seq, level)"
			+ "	values(:review_id, :amuse_id, :member_id, :r_content, :ref, :seq, :level)", nativeQuery = true)
	@Modifying
	public Integer answerWrite(
			@Param(value = "review_id") Integer review_id,
			@Param(value = "amuse_id") Integer amuse_id,
			@Param(value = "member_id") String member_id,
			@Param(value = "r_content") String r_content,
			@Param(value = "ref") Integer ref,
			@Param(value = "seq") Integer seq,
			@Param(value = "level") Integer level);
	
	//삭제
	@Query(value = "delete from amuse_review where review_id = :review_id", nativeQuery = true)
	@Modifying
	@Transactional
	public void deleteReview(@Param(value = "review_id") Integer review_id);
}












