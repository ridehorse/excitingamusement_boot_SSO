package com.exciting.selected.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exciting.dto.SelectedAmusementAimageDTO;
import com.exciting.entity.SelectedEntity;
import com.exciting.selected.repository.SelectedRepository;

@Service
@Transactional
public class SelectedServiceImpl implements SelectedService{
	
	@Autowired
	SelectedRepository selectedRepository;

	@Override
	public int check(String member_id, int amuse_id) {
		return selectedRepository.check(member_id, amuse_id);
	}

	@Override
	public void insert(String member_id, int amuse_id) {
		SelectedEntity selectedEntity = new SelectedEntity();
		selectedEntity.setAmuse_id(amuse_id);
		selectedEntity.setMember_id(member_id);
		selectedRepository.save(selectedEntity);
	}

	@Override
	public void delete(String member_id, Integer amuse_id) {
		selectedRepository.delete(member_id, amuse_id);
	}

	@Override
	public List<SelectedAmusementAimageDTO> selectedList(Map<String, Object> map) {
		return selectedRepository.selectedList(map);
	}

	@Override
	public Long count(Map<String, Object> map) {
		return selectedRepository.count(map);
	}
	
}
