package com.exciting.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.exciting.dto.AmuseReviewDTO;
import com.exciting.entity.AmuseReviewEntity;
import com.exciting.repository.AmuseReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmuseReviewService {
	private final AmuseReviewRepository amuseReviewRepository;
	
	public List<AmuseReviewEntity> amuseReviewList(Integer amuse_id){
		return this.amuseReviewRepository.amuseReviewList(amuse_id);
	}
	
	public Float amuseReviewGradeAvg(Integer amuse_id) {
		return this.amuseReviewRepository.amuseReviewGradeAvg(amuse_id);
	}
	
	public Integer amuseReviewCount(Integer amuse_id) {
		return this.amuseReviewRepository.amuseReviewCount(amuse_id);
	}
	
	public Integer reviewWrite(Integer amuse_id, String member_id, String r_content, Integer r_grade) {
		return this.amuseReviewRepository.reviewWrite(amuse_id, member_id, r_content, r_grade);
	}
	
	public Integer maxReviewId() {
		return this.amuseReviewRepository.maxReviewId();
	}
	
	public void updateRef(Integer review_id) {
		this.amuseReviewRepository.updateRef(review_id);
	}
	
	public AmuseReviewEntity getOneReview(Integer review_id) {
		return this.amuseReviewRepository.getOneReview(review_id);
	}
	
	public void updateSeq(Integer ref, Integer seq) {
		this.amuseReviewRepository.updateSeq(ref, seq);
	}
	
	public Integer answerWrite(Integer review_id, Integer amuse_id, Integer ref, Integer seq, 
			Integer level, String member_id, String r_content) {
		return this.amuseReviewRepository.answerWrite(review_id, amuse_id, member_id, r_content, ref, seq, level);
	}
	
	public void deleteReview(Integer review_id) {
		this.amuseReviewRepository.deleteReview(review_id);
	}
}
