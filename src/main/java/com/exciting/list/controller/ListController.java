package com.exciting.list.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exciting.list.repository.ListRepository;
import com.exciting.list.service.ListService;

@RestController
public class ListController {
	
	@Autowired
	ListService listService;
	
	@GetMapping(value = "/list")
	public Map<String, Object> list(@RequestParam Map<String, Object> requestMap){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
//		//페이징
		String pageTemp = (String)requestMap.get("pageNum");
		int pageNum = 1;
		if(pageTemp != null && !(pageTemp.equals(""))){
			pageNum = Integer.parseInt(pageTemp);
		}
		int pageSize = 8;
		int start = (pageNum - 1) * pageSize;
		int end = pageSize ;
		
		requestMap.put("start", start);
		requestMap.put("end", end);
		
		System.out.println(requestMap);
		responseMap.put("listData", listService.selectList(requestMap));
		
		return responseMap;
	}
	
}
