package com.exciting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exciting.entity.RidesEntity;
import com.exciting.repository.RidesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RidesService {
	
	private final RidesRepository ridesRepository;
	
	public List<RidesEntity> ridesListAll(Integer amuse_id){
		return this.ridesRepository.ridesListAll(amuse_id);
	}
	
	public RidesEntity getOneRide(Integer rides_id) {
		return this.ridesRepository.getOneRide(rides_id);
	}
}
