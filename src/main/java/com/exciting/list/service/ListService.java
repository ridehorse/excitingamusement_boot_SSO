package com.exciting.list.service;

import java.util.List;
import java.util.Map;

import com.exciting.dto.AmusementAimageDTO;

public interface ListService {
	public List<AmusementAimageDTO> selectList(Map<String, Object> map);
}
