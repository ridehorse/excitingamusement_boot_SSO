package com.exciting.index.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.entity.Promotion;
import com.exciting.index.service.IndexService;
import com.exciting.promotion.service.PromotionService;

@RestController
public class IndexController {
	
	@Autowired
    private IndexService indexService;
	@Autowired
	private PromotionService promotionService;
	
	@GetMapping("/")
	public Map<String, Object> ABC() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<AmusementAimageDTO> koreaData = this.indexService.selectListAmuseInfoKoreaDSL();
		List<AmusementAimageDTO> foreignData = this.indexService.selectListAmuseInfoForeignDSL();
		List<Promotion> promotionData = this.promotionService.selectPromotion();
		responseMap.put("koreaData", koreaData);
		responseMap.put("foreignData", foreignData);
		responseMap.put("promotionData", promotionData);
		return responseMap;
	}
	
}
