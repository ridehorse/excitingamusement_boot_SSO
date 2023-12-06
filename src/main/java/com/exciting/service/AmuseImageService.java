package com.exciting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exciting.entity.AmuseImageEntity;
import com.exciting.repository.AmuseImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmuseImageService {
	private final AmuseImageRepository amuseImageRepository;
	
	public List<AmuseImageEntity> amuseImgList(Integer amuse_id){
		return this.amuseImageRepository.amuseImgList(amuse_id);
	}
}
