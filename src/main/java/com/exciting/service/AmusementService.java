package com.exciting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exciting.entities.AmusementEntity2;
import com.exciting.repository.AmusementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmusementService {
	
	private final AmusementRepository amusementRepository;
	
	//===================국내 목록===================
	public List<AmusementEntity2> inAmusementList(){
		return this.amusementRepository.inAmusementList();
	}
	
	public List<AmusementEntity2> inAmusementListAvg(){
		return this.amusementRepository.inAmusementListAvg();
	}
	
	public List<AmusementEntity2> inAmusementListReview(){
		return this.amusementRepository.inAmusementListReview();
	}
	
	//===================국내 목록===================
	public List<AmusementEntity2> outAmusementListAvg(){
		return this.amusementRepository.outAmusementListAvg();
	}
	
	public List<AmusementEntity2> outAmusementListReview(){
		return this.amusementRepository.outAmusementListReview();
	}
	
	
	//===================놀이공원 상세===================
	public AmusementEntity2 getOneAmusement(Integer amuse_id) {
		return this.amusementRepository.getOneAmusement(amuse_id);
	}
	
	public void updateViewCnt(Integer amuse_id) {
		this.amusementRepository.updateViewCnt(amuse_id);
	}
}























