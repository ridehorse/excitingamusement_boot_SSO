package com.exciting.promotion.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exciting.dto.MyPointResponseDTO;
import com.exciting.dto.MypointDTO;
import com.exciting.dto.OrderResponseDTO;
import com.exciting.dto.PromotionDTO;
import com.exciting.entity.Mypoint;
import com.exciting.entity.Promotion;
import com.exciting.login.entity.Member;
import com.exciting.promotion.service.PromotionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RestController
public class PromotionController {

	@Autowired
	PromotionService promotionService;

	@GetMapping(value = "/promotion")
	@ResponseBody
	public List<PromotionDTO> promotionList() {
		List<Promotion> list = this.promotionService.selectPromotion();

		List<PromotionDTO> dtoList = list.stream()
				.map(promotion -> new PromotionDTO(
						promotion.getPromotion_id(),
						promotion.getPromotion_content(),
						promotion.getPromotion_name(),
						promotion.getPromotion_img()))
				.collect(Collectors.toList());

		return dtoList;
	}

	@GetMapping(value = "/promotionprice")
	@ResponseBody
	public List<PromotionDTO> promotionprice(@RequestParam("promotion_id") Integer promotion_id) {
		List<PromotionDTO> pricelist = promotionService.selectPromotionprice(promotion_id);

		List<PromotionDTO> dtoList = pricelist.stream()
				.map(promotionprice -> new PromotionDTO(
						promotionprice.getPromotion_id(),
						promotionprice.getPromotion_name(),
						promotionprice.getPromotion_content(),
						promotionprice.getPromotion_img(),
						promotionprice.getTicket_price(),
						promotionprice.getTicket_id(),
						promotionprice.getDiscount()))
				.collect(Collectors.toList());

		return dtoList;
	}

	@GetMapping(value = "/order")
	public ResponseEntity<OrderResponseDTO> orderGet(@RequestParam("promotion_id") Integer promotion_id, @RequestParam("member_id") String member_id) {
		//String member_id = "qwer1234";
		System.out.println("member_id@@@@@"+member_id+"@@@@@@");
		List<PromotionDTO> price = this.promotionService.selectPromotionprice(promotion_id);
		List<Mypoint> mypoint = this.promotionService.selectmypoint1(member_id);

		List<MypointDTO> mypointdto = mypoint.stream()
				.map(mypointrs -> new MypointDTO(
						mypointrs.getSum_point(),
						mypointrs.getMember_id()))
				.collect(Collectors.toList());

		OrderResponseDTO response = new OrderResponseDTO(price, mypointdto);
		return ResponseEntity.ok(response);
		//return null;
	}

	@ResponseBody
	@PostMapping(value = "/order")
	public int orderPost(@RequestBody Map<String, Object> map) {
		System.out.println("map@!@$!@$!@$!@$"+map+"map@!@$!@$!@$!@$");
		String jsonStr = (String)map.get("myJSON");
		// ObjectMapper 객체 생성
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> list = null;
		// List로 변환
		try{
			list = objectMapper.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>(){});
		}catch(Exception e) {
		}
		Map<String, Object> mapT = new HashMap<String, Object>();
		//		member_id값 세션에서 받아와서 mapT에 넣음
		//String member_id = "qwer1234";
		double use_point = Double.parseDouble((String) map.get("use_point"));
//		double formattedUsePoint = (double) map.get("use_point");
//		String use_point = (formattedUsePoint == 0) ? "0.00" : String.format("%.2f", formattedUsePoint);
		
//		Integer convertedUsePoint = (Integer) map.get("use_point");
//		double formattedUsePoint = convertedUsePoint != null ? convertedUsePoint.doubleValue() : 0.0;
//		String use_point = (formattedUsePoint == 0) ? "0.00" : String.format("%.2f", formattedUsePoint);


		String member_id = (String)map.get("member_id");
		mapT.put("use_point", use_point);
		mapT.put("member_id", member_id);
		//		order테이블 데이터 추가
		int check = promotionService.insertOrder(mapT);
		System.out.println("@@@@@@@@@@@@@@@@@@"+mapT+"@@@@@@@@@@@@@@");
		System.out.println("@@@@@@@@@@@@@@@@@@"+check+"@@@@@@@@@@@@@@");

		//		order테이블을 추가할 때 얻은 PK값을 list의 map에 각각 추가
		int order_id = check;
		for(int i=0; i < list.size(); i++) {
			Map<String, Object> changeMap = list.get(i);
			changeMap.put("order_id", order_id);
		}
		//		order_detail테이블에 데이터 추가
		if(check != 0) {
			promotionService.insertOrderDetail(list);
		}

		return check;
	}

	@GetMapping(value = "/mypoint")
	@ResponseBody
	public MyPointResponseDTO mypoint(@RequestParam("member_id") String member_id) {
		//String member_id = "qwer1234";
		//String member_id = (String)member_id.getMember_id();
		System.out.println("마이포인트 진입 @@@ 아이디 가져와"+member_id);

		List<Mypoint> mypoint = this.promotionService.selectmypoint1(member_id);
		List<Map<String, Object>> orderMapList = this.promotionService.selectmypoint2(member_id);

		List<MypointDTO> mypointdto = mypoint.stream()
				.map(mypointrs -> new MypointDTO(
						mypointrs.getSum_point(),
						mypointrs.getMember_id()))
				.collect(Collectors.toList());

		List<PromotionDTO> promotionDTOList = new ArrayList<>();

		for (Map<String, Object> map : orderMapList) {

			Map<String, Object> mutableMap = new HashMap<>(map);

			String order_date = mutableMap.get("order_date").toString();
			String ymd=order_date.substring(0,10);
			String ymd2=ymd.replace("-",".");
			String hms=order_date.substring(11);
			String postdate=ymd2+" "+hms;

			mutableMap.put("order_date", postdate);

			// PromotionDTO 객체를 생성하고 값을 설정합니다.
			PromotionDTO promotionDTO = new PromotionDTO();
			promotionDTO.setOrder_id((Integer) map.get("order_id"));
			//promotionDTO.setMember_id(member_id);
			promotionDTO.setOrder_date((String) mutableMap.get("order_date"));
			promotionDTO.setCheckorder((Boolean) map.get("checkorder"));
			promotionDTO.setCheckrefund((Boolean) map.get("checkrefund"));
			promotionDTO.setUse_point((BigDecimal) map.get("use_point"));
			promotionDTO.setQuantity((Integer) map.get("quantity"));
			promotionDTO.setTicket_name((String) map.get("ticket_name"));
			promotionDTO.setTicket_price((Integer) map.get("ticket_price"));
			promotionDTO.setPromotion_content((String) map.get("promotion_content"));
			promotionDTO.setPromotion_name((String) map.get("promotion_name"));
			promotionDTO.setPromotion_img((String) map.get("promotion_img"));
			promotionDTO.setDiscount((double) map.get("discount"));
			promotionDTO.setA_name((String) map.get("a_name"));

			// PromotionDTO 객체를 리스트에 추가합니다.
			promotionDTOList.add(promotionDTO);
		}


		MyPointResponseDTO response = new MyPointResponseDTO(promotionDTOList, mypointdto);
		return response;
	}

	@PostMapping(value = "/check")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> check(@RequestBody Map<String, Object> map) {
		//String member_id = (String)session.getAttribute("member_id");
		//double use_point = Double.parseDouble((String) map.get("use_point"));
		System.out.println("@@@@@@@@@@@@@@@@@"+map+"@@@@@@@@@@@@@@@@");
		
		//String member_id = "qwer1234";
//		BigDecimal use_point = (BigDecimal)map.get("use_point");
		double use_pointValue = Double.parseDouble(map.get("use_point").toString());
		BigDecimal use_point = BigDecimal.valueOf(use_pointValue);

		map.put("use_point", use_point);
		

		String member_id = (String)map.get("member_id");
		map.put("member_id", member_id);
		//{order_id=111, use_point=0, member_id=qwer1234}

		if(use_point != null) {
			int rs = this.promotionService.insertUsepoint(map);
			System.out.println("이거 실행 됨?@?@?@?@?"+rs);
		}

		int rs = this.promotionService.checkOrder(map);
		System.out.println("@@@@@@@@@@@@@@@@@"+rs+"@@@@@@@@@@@@@@@@컴온@@");

		Map<String, Object> response = new HashMap<String, Object>();
		if(rs == 1) {
			this.promotionService.insertMypoint(map);
			response.put("success", true);
		} else {
			response.put("success", false);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/refund")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> refundOrder(@RequestBody Map<String, Object> map) {
		System.out.println("@@@@@@@@@@@@@@@@@"+map+"@@@@@@@@@@@@@@@@");
	    int rs = this.promotionService.refundOrder(map);
	    System.out.println("@@@@@@@@@@@@@@@@@"+rs+"@@@@@@@@@@@@@@@@");

	    Map<String, Object> response = new HashMap<String, Object>();
	    if(rs == 1) {
	        response.put("success", true);
	    } else {
	        response.put("success", false);
	    }
	    
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	//	@GetMapping(value = "/confirm")
	//	public ModelAndView confirm(HttpSession session, @RequestParam Map<String,Object> map) {
	//		String member_id = (String)session.getAttribute("member_id");
	//		Map<String, Object> dataMap = new HashMap<>();
	//		dataMap.put("member_id", member_id);
	//		dataMap.put("order_id", map.get("order_id"));
	//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@ !!!!!!!!!!!!!!!!!!!!!"+dataMap);
	//		List<Map<String, Object>> orderlist = this.promotionTempService.selectconfirm(dataMap);
	//		
	//		for(Map<String, Object> order : orderlist) {
	//			String date = order.get("order_date").toString();
	//			String ymd=date.substring(0,10);
	//			String ymd2=ymd.replace("-",".");
	//			String hms=date.substring(11);
	//			String postdate=ymd2+" "+hms;
	//			order.put("order_date", postdate);
	//		}
	//		System.out.println("@@@@@@@@@@"+orderlist+"@@@@@@@@@@@@");
	//		ModelAndView mav = new ModelAndView();
	//		mav.addObject("data", orderlist);
	//		mav.setViewName("confirm");
	//		return mav;
	//	}
	//	
	//	@GetMapping(value = "/refund")
	//	public ModelAndView refund(HttpSession session) {
	//		String member_id = (String)session.getAttribute("member_id");
	//		Map<String, Object> dataMap = new HashMap<>();
	//		dataMap.put("member_id", member_id);
	//
	//		List<Map<String, Object>> orderlist = this.promotionTempService.selectOrderlist(dataMap);
	//
	//		for(Map<String, Object> order : orderlist) {
	//			String date = order.get("order_date").toString();
	//			String ymd=date.substring(0,10);
	//			String ymd2=ymd.replace("-",".");
	//			String hms=date.substring(11);
	//			String postdate=ymd2+" "+hms;
	//			order.put("order_date", postdate);
	//		}
	//		ModelAndView mav = new ModelAndView();
	//		mav.addObject("data", orderlist);
	//		mav.setViewName("refund");
	//		return mav;
	//	}
	//	

}
