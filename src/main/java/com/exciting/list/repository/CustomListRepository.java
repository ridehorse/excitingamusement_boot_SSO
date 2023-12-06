package com.exciting.list.repository;

import java.util.List;
import java.util.Map;

import com.exciting.dto.AmusementAimageDTO;

public interface CustomListRepository {
	public List<AmusementAimageDTO> selectList(Map<String, Object> map); 
}
