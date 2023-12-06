package com.exciting.list.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.list.repository.ListRepository;

@Service
public class ListServiceImpl implements ListService{
	
	@Autowired
	ListRepository listRepository;

	@Override
	public List<AmusementAimageDTO> selectList(Map<String, Object> map) {
		return listRepository.selectList(map);
	}

}
