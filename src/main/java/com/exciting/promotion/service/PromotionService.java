package com.exciting.promotion.service;

import java.util.List;
import java.util.Map;

import com.exciting.dto.PromotionDTO;
import com.exciting.entity.Mypoint;
import com.exciting.entity.Promotion;
import com.exciting.login.entity.Member;

public interface PromotionService {
	public List<Promotion> selectPromotion();
	public List<PromotionDTO> selectPromotionprice(Integer promotion_id);
	int insertOrder(Map<String, Object> map);
    void insertOrderDetail(List<Map<String, Object>> list);
//	public List<Map<String, Object>> selectOrderlist(Map<String, Object>map);
//	public List<Map<String, Object>> selectconfirm(Map<String, Object>map);
	public List<Map<String, Object>> selectmypoint2(String member_id);
	public List<Mypoint> selectmypoint1(String member_id);
	public int refundOrder(Map<String, Object> map);
	public int checkOrder(Map<String, Object> map);
	public int insertMypoint(Map<String, Object> map);
	public int insertUsepoint(Map<String, Object> map);
}
