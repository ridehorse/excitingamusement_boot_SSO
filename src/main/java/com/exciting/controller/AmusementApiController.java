package com.exciting.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exciting.dto.AmuseReviewDTO;
import com.exciting.entity.AmuseImageEntity;
import com.exciting.entity.AmuseReviewEntity;
import com.exciting.entities.AmusementEntity2;
import com.exciting.entity.FacilityEntity;
import com.exciting.entity.RidesEntity;
import com.exciting.service.AmuseImageService;
import com.exciting.service.AmuseReviewService;
import com.exciting.service.AmusementService;
import com.exciting.service.FacilityService;
import com.exciting.service.RidesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amusement")
@CrossOrigin(origins = "http://localhost:3000")
public class AmusementApiController {
	
	private final AmusementService amusementService;
	private final RidesService ridesService;
	private final AmuseImageService amuseImageService;
	private final AmuseReviewService amuseReviewService;
	private final FacilityService facilityService;
	
	//===================국내 목록===================
	//1) 전체
	@GetMapping("/amuseList")
	public List<AmusementEntity2> amuseList(){
		List<AmusementEntity2> amuseList = this.amusementService.inAmusementList();
		return amuseList;
	}
	//2) 평점
	@GetMapping("/inAvgGradeList")
	public List<AmusementEntity2> inAmuseListAvg(){
		List<AmusementEntity2> inAvgGradeList = this.amusementService.inAmusementListAvg();
		return inAvgGradeList;
	}
	//3) 리뷰
	@GetMapping("/inReviewCntList")
	public List<AmusementEntity2> inAmuseListReview(){
		List<AmusementEntity2> inReviewCntList = this.amusementService.inAmusementListReview();
		return inReviewCntList;
	}
	
	//===================해외 목록=====================
	//평점
	@GetMapping("/outAvgGradeList")
	public List<AmusementEntity2> outAmuseListAvg(){
		return this.amusementService.outAmusementListAvg();
	}
	
	//리뷰
	@GetMapping("/outReviewCntList")
	public List<AmusementEntity2> outAmuseListReview(){
		return this.amusementService.outAmusementListReview();
	}
	
	//===================놀이공원 상세===================
	@GetMapping("amuseDetail/{amuse_id}")
	public AmusementEntity2 amuseDetail(@PathVariable Integer amuse_id) {
		//1) 점수 평균
		Float avg = this.amuseReviewService.amuseReviewGradeAvg(amuse_id);
		
		//2) 하나 가져오기
		AmusementEntity2 dto = this.amusementService.getOneAmusement(amuse_id);
		dto.setAvg_grade(avg);
		
		//3) 리뷰 개수
		Integer review_cnt = this.amuseReviewService.amuseReviewCount(amuse_id);
		dto.setReview_cnt(review_cnt);
		
		return dto;
	}
	
	//===================놀이공원 images=============================
	@GetMapping("amuseImage/{amuse_id}")
	public List<AmuseImageEntity> amusementImages(@PathVariable Integer amuse_id){
		return this.amuseImageService.amuseImgList(amuse_id);
	}
	
	//===================놀이공원에 대한 rides 목록====================
	@GetMapping("/ridesList/{amuse_id}")
	public List<RidesEntity> ridesList(@PathVariable Integer amuse_id){
		return this.ridesService.ridesListAll(amuse_id);
	}
	
	//===================놀이공원에 대한 facilities 목록====================
	@GetMapping("/facilityList/{amuse_id}")
	public List<FacilityEntity> facList(@PathVariable Integer amuse_id){
		return this.facilityService.facAllList(amuse_id);
	}
	
	//===================ride 상세====================
	@GetMapping("/rideDetail/{rides_id}")
	public RidesEntity rideDetail(@PathVariable Integer rides_id) {
		return this.ridesService.getOneRide(rides_id);
	}
	
	//===================review 목록====================
	@GetMapping("/reviewList/{amuse_id}")
	public List<AmuseReviewEntity> reviewList(@PathVariable Integer amuse_id){
		return this.amuseReviewService.amuseReviewList(amuse_id);
	}
	
	//===================review 작성====================
	@PostMapping("/write")
	@Transactional
	public void reviewInsert(@RequestBody AmuseReviewDTO arDto) {
		Integer amuse_id = arDto.getAmuse_id();
        String member_id= arDto.getMember_id();
        String r_content = arDto.getR_content();
        Integer r_grade = arDto.getR_grade();
        
        System.out.println("=========================================== arDto = " + arDto + "===========================================");
        
        Integer writeRes = this.amuseReviewService.reviewWrite(amuse_id, member_id, r_content, r_grade);
        
        if(writeRes == 1) {
        	Integer maxReviewId = this.amuseReviewService.maxReviewId();
        	
        	this.amuseReviewService.updateRef(maxReviewId);
        }
	}
	
	//===================답글 작성====================
	@PostMapping("/answer")
	@Transactional
	public void answerInsert(@RequestBody AmuseReviewDTO arDto) {
		
		System.out.println("===== arDto = " + arDto + " ===========");
		
		Integer maxReviewId = this.amuseReviewService.maxReviewId();
		
		System.out.println("=========== maxReviewId = " + maxReviewId);
		
		//ans review_id
		Integer ansReviewId = maxReviewId + 1;
		Integer review_id = arDto.getReview_id();
		
		AmuseReviewEntity ansDto = this.amuseReviewService.getOneReview(review_id);
		
		Integer updateRef = ansDto.getRef();
		Integer updateSeq = ansDto.getSeq();
		
		System.out.println("========================= updateRef = " + updateRef);
		System.out.println("========================= updateSeq = " + updateSeq);
		
		this.amuseReviewService.updateSeq(updateRef, updateSeq);
		
		Integer ref = ansDto.getRef();
		Integer seq = ansDto.getSeq();
		Integer level = ansDto.getLevel();
		
		String r_content = arDto.getR_content();
		String member_id = arDto.getMember_id();
		 
		Integer amuse_id = arDto.getAmuse_id();
		
		Integer res = this.amuseReviewService.answerWrite(ansReviewId, amuse_id, ref, seq + 1, level + 1, member_id, r_content);
		
		if(res == 1) {
			System.out.println("answer insert");
		}
	}
	
	//글 삭제
	@GetMapping("/delete/{review_id}")
	@Transactional
	public void reviewDelete(@PathVariable Integer review_id) {
		this.amuseReviewService.deleteReview(review_id);
	}
	
	//조회수 증가
	@GetMapping("/updateCnt/{amuse_id}")
	@Transactional
	public void updateViewCnt(@PathVariable Integer amuse_id) {
		this.amusementService.updateViewCnt(amuse_id);
	}
}



