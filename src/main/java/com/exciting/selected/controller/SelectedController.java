package com.exciting.selected.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exciting.dto.SelectedAmusementAimageDTO;
import com.exciting.selected.service.SelectedService;

@RestController
public class SelectedController {
	
	@Autowired
	SelectedService selectedService;
	
	@PostMapping("/selectedapi/check")
	public Map<String, Object> check(@RequestBody Map<String, Object> requestMap) {
		System.out.println("request맵: "+requestMap);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String member_id = (String)requestMap.get("member_id");
		Integer amuse_id = (Integer)requestMap.get("amuse_id");
		int check = selectedService.check(member_id, amuse_id);
		responseMap.put("check", check);
		return responseMap;
	}
	
	@PostMapping("/selectedapi/insertDelete")
	public Map<String, Object> insertDelete(@RequestBody Map<String, Object> requestMap) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String member_id = (String)requestMap.get("member_id");
		Integer amuse_id = (Integer)requestMap.get("amuse_id");
		System.out.println("member_id: "+member_id);
		System.out.println("amuse_id: "+amuse_id);
		int check = selectedService.check(member_id, amuse_id);
		if(check == 1) {
			selectedService.delete(member_id, amuse_id);
			check = 0;
		} else {
			selectedService.insert(member_id, amuse_id);
			check = 1;
		}
		responseMap.put("check", check);
		return responseMap;
	}
	
	@GetMapping("/selectedapi/selectedlist")
	public List<SelectedAmusementAimageDTO> selectedList(@RequestParam Map<String, Object> map) {
		String member_id = (String) map.get("member_id");
		int limitCount = 6;
		if(!(member_id == null || member_id.equals(""))) {
			map.put("member_id", member_id);
		}
		map.put("limitCount", limitCount);
		List<SelectedAmusementAimageDTO> selectedData = selectedService.selectedList(map);
		return selectedData;
	}
	
	@PostMapping("/selectedapi/selectedlist")
	public List<SelectedAmusementAimageDTO> selectedListClick(@RequestBody Map<String, Object> map) {
		String member_id = (String)map.get("member_id");
//		selected_id와 limitCount 맵에 추가
		int selected_id = (Integer)map.get("selected_id");
		
		int limitCount = 6;
		if(!(member_id == null || member_id.equals(""))) {
			map.put("member_id", member_id);
		}
		map.put("selected_id", selected_id);
		map.put("limitCount", limitCount);
		List<SelectedAmusementAimageDTO> selectedData = selectedService.selectedList(map);
		
		return selectedData;
	}
	
	@PostMapping("/selectedapi/selectedcount")
	public long selectedListCount(@RequestBody Map<String, Object> map) {
		String member_id = (String)map.get("member_id");
		System.out.println("/selectedapi/selectedcount // member_id : "+member_id);
//		selected_id 맵에 추가
		if(map.get("selected_id") != null) {
			int selected_id = (Integer)map.get("selected_id");
			map.put("selected_id", selected_id);
		}
		if(!(member_id == null || member_id.equals(""))) {
			map.put("member_id", member_id);
		}
		
		long selectCount = selectedService.count(map);
		
		return selectCount;
	}

}
