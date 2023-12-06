package com.exciting.selected.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.exciting.dto.SelectedAmusementAimageDTO;

public interface SelectedService {
	public int check(String member_id, int amuse_id);
	public void insert(String member_id, int amuse_id);
	public void delete(String member_id, Integer amuse_id);
	public List<SelectedAmusementAimageDTO> selectedList(Map<String, Object> map);
	public Long count(Map<String, Object> map);
}
