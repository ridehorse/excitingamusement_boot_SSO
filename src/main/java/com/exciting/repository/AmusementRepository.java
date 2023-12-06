package com.exciting.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exciting.entities.AmusementEntity2;

import lombok.val;

@Repository
public interface AmusementRepository extends JpaRepository<AmusementEntity2, Integer>{
	//===================국내 목록===================
	//국내 전체 목록
	@Query(value = "SELECT * FROM amusement WHERE a_country = '대한민국'", nativeQuery = true)
	List<AmusementEntity2> inAmusementList();
	
	//국내 평점 높은 순서
	@Query(value = "select a.a_info, a.a_opening, a.a_time, a.a_view,"
			+ " a.a_note, a.a_lat, a.a_lng, a.a_name as a_name, a.amuse_id as amuse_id, a.a_img as a_img, a.a_country, b.avg as avg_grade, b.review_cnt as review_cnt"
			+ " from amusement a,"
			+ " (select amuse_id, round((sum(r_grade) / count(amuse_id)), 1) as avg, count(amuse_id) as review_cnt from amuse_review group by amuse_id) b"
			+ " where a.amuse_id = b.amuse_id and a.a_country = '대한민국' order by avg desc", nativeQuery = true)
	List<AmusementEntity2> inAmusementListAvg();
	
	//국내 리뷰 많은 순서
	@Query(value = "select a.a_info, a.a_opening, a.a_time, a.a_view,"
			+ " a.a_note, a.a_lat, a.a_lng, a.a_name as a_name, a.amuse_id as amuse_id, a.a_img as a_img, a.a_country, b.avg as avg_grade, b.review_cnt as review_cnt"
			+ " from amusement a,"
			+ " (select amuse_id, round((sum(r_grade) / count(amuse_id)), 1) as avg, count(amuse_id) as review_cnt from amuse_review group by amuse_id) b"
			+ " where a.amuse_id = b.amuse_id and a.a_country = '대한민국' order by review_cnt desc", nativeQuery = true)
	List<AmusementEntity2> inAmusementListReview();
	
	//===================해외 목록===================
	@Query(value = "select a.a_info, a.a_opening, a.a_time, a.a_view,"
			+ " a.a_note, a.a_lat, a.a_lng, a.a_name as a_name, a.amuse_id as amuse_id, a.a_img as a_img, a.a_country, b.avg as avg_grade, b.review_cnt as review_cnt"
			+ " from amusement a,"
			+ " (select amuse_id, round((sum(r_grade) / count(amuse_id)), 1) as avg, count(amuse_id) as review_cnt from amuse_review group by amuse_id) b"
			+ " where a.amuse_id = b.amuse_id and a.a_country != '대한민국' order by avg desc", nativeQuery = true)
	List<AmusementEntity2> outAmusementListAvg();
	
	@Query(value = "select a.a_info, a.a_opening, a.a_time, a.a_view,"
			+ " a.a_note, a.a_lat, a.a_lng, a.a_name as a_name, a.amuse_id as amuse_id, a.a_img as a_img, a.a_country, b.avg as avg_grade, b.review_cnt as review_cnt"
			+ " from amusement a,"
			+ " (select amuse_id, round((sum(r_grade) / count(amuse_id)), 1) as avg, count(amuse_id) as review_cnt from amuse_review group by amuse_id) b"
			+ " where a.amuse_id = b.amuse_id and a.a_country != '대한민국' order by review_cnt desc", nativeQuery = true)
	List<AmusementEntity2> outAmusementListReview();
	
	//===================놀이공원 상세===================
	@Query(value = "select * from Amusement where amuse_id = :amuse_id", nativeQuery = true)
	AmusementEntity2 getOneAmusement(@Param(value = "amuse_id") Integer amuse_id);
	
	//조회수
	@Query(value = "update amusement set a_view = a_view + 1 where amuse_id = :amuse_id", nativeQuery = true)
	@Modifying
	@Transactional
	void updateViewCnt(@Param(value = "amuse_id") Integer amuse_id);
}	

















