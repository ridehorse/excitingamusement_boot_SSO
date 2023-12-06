package com.exciting.index.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.index.repository.IndexRepository;

@Service
public class IndexServiceImpl implements IndexService{
	 @Autowired
	 private IndexRepository indexRepository;

	@Override
	public List<AmusementAimageDTO> selectListAmuseInfoKoreaDSL() {
		return indexRepository.selectListAmuseInfoKoreaDSL();
	}

	@Override
	public List<AmusementAimageDTO> selectListAmuseInfoForeignDSL() {
		return indexRepository.selectListAmuseInfoForeignDSL();
	}
}
