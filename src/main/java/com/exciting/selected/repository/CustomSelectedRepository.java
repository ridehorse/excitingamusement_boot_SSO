package com.exciting.selected.repository;

import java.util.List;
import java.util.Map;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.dto.SelectedAmusementAimageDTO;

public interface CustomSelectedRepository {
	public List<SelectedAmusementAimageDTO> selectedList(Map<String, Object> map);
	public Long count(Map<String, Object> map);
}
