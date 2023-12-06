package com.exciting.promotion.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exciting.dto.PromotionDTO;
import com.exciting.entity.Mypoint;
import com.exciting.entity.Orders;
import com.exciting.entity.OrdersDetail;
import com.exciting.entity.Promotion;
import com.exciting.entity.Ticket;
import com.exciting.login.entity.Member;
import com.exciting.promotion.Repository.CheckOrderRepository;
import com.exciting.promotion.Repository.InsertOrderRepository;
import com.exciting.promotion.Repository.MyPointRepository;
import com.exciting.promotion.Repository.MyPointRepository2;
import com.exciting.promotion.Repository.OrdersDetailRepository;
import com.exciting.promotion.Repository.PromotionPriceRepository;
//import com.exciting.promotion.Repository.PromotionPriceRepository;
import com.exciting.promotion.Repository.PromotionRepository;
import com.exciting.promotion.Repository.insertUsepointRepository;

@Service
public class PromotionServiceImpl implements PromotionService{

	@Autowired
	PromotionRepository repository;
	@Autowired
	PromotionPriceRepository prorepository;
	@Autowired
	MyPointRepository pointrepository;
	@Autowired
	InsertOrderRepository insertOrderRepository;
	@Autowired	
	OrdersDetailRepository ordersDetailRepository;
	@Autowired
	MyPointRepository2 myPointRepository2;
	@Autowired
	insertUsepointRepository insertUsepointRepository;
	@Autowired
	CheckOrderRepository checkOrderRepository;

	@Override
	public List<Promotion> selectPromotion() {
		return repository.findAll();
	}

	@Override
	public List<PromotionDTO> selectPromotionprice(Integer promotion_id) {
		return prorepository.selectPromotionprice(promotion_id);
	}

	@Override
	public int insertOrder(Map<String, Object> map) {

		Orders order = new Orders();

		Member member = new Member();
		String memberId =  map.get("member_id").toString();
		member.setMember_id(memberId);

		order.setMember(member);
		
//		String stringuse_point = (String) map.get("use_point");
//		BigDecimal usePoint = new BigDecimal(stringuse_point);
		BigDecimal usePoint = new BigDecimal((Double)map.get("use_point"));
		order.setUse_point(usePoint);

		LocalDateTime orderDate = LocalDateTime.now();
		order.setOrder_date(orderDate);

		Orders savedOrder = insertOrderRepository.save(order);
		return savedOrder.getOrder_id(); // Return the ID of the inserted order
	}

	@Override
	public void insertOrderDetail(List<Map<String, Object>> list) {
		System.out.println("@@@@@@@@@@@"+list+"!!!!!!!!!!!!!");
		List<OrdersDetail> orderDetailEntities = new ArrayList<>();
		for (Map<String, Object> orderDetail : list) {
			int order_id = (int) orderDetail.get("order_id");
			int ticketId = Integer.parseInt((String) orderDetail.get("ticket_id"));
			int quantity = Integer.parseInt((String) orderDetail.get("quantity"));
			System.out.println("orderId@@"+order_id+"orderId");

			OrdersDetail orderDetailEntity = new OrdersDetail();
			Ticket ticket = new Ticket(ticketId);
			Orders order = new Orders(order_id);

			orderDetailEntity.setOrders(order);
			orderDetailEntity.setTicket_id(ticket);
			orderDetailEntity.setQuantity(quantity);

			orderDetailEntities.add(orderDetailEntity);
		}

		ordersDetailRepository.saveAll(orderDetailEntities);
	}


	//	@Override
	//	public List<Map<String, Object>> selectOrderlist(Map<String, Object> map) {
	//		return promotionTempDAO.selectOrderlist(map);
	//	}
	//	@Override
	//	public List<Map<String, Object>> selectconfirm(Map<String, Object> map) {
	//		return promotionTempDAO.selectconfirm(map);
	//	}
	@Override
	public List<Map<String, Object>> selectmypoint2(String member_id) {
		return myPointRepository2.selectMypoint2(member_id);
	}
	@Override
	public List<Mypoint> selectmypoint1(String member_id) {
		return pointrepository.selectmypoint1(member_id);
	}

	@Override
	public int refundOrder(Map<String, Object> map) {
		
		int order_id = (int)map.get("order_id");
		return checkOrderRepository.refundOrder(order_id);
	}
	@Override
	public int checkOrder(Map<String, Object> map) {

		int order_id = Integer.parseInt(map.get("order_id").toString()); 	

		return checkOrderRepository.checkorders(order_id);
	}

	@Override
	public int insertMypoint(Map<String, Object> map) {
		// map = {order_id=41, use_point=100.11, member_id=qwer1234, m_point=6930}
		System.out.println("@@@@@@@@@@@@@포인트 확인"+map);
		String mPointString = (String) map.get("m_point");
		BigDecimal m_point = new BigDecimal(mPointString);
		int order_id = (int)map.get("order_id");
		String member_id = (String)map.get("member_id");
		Member member= new Member();
		Orders order = new Orders();
		member.setMember_id(member_id);;
		order.setMember(member);
		Member member1 = order.getMember();
		return insertUsepointRepository.insertMypoint(m_point, order_id, member1);
	}

	@Override
	public int insertUsepoint(Map<String, Object> map) {		
		BigDecimal use_point = (BigDecimal)map.get("use_point");
		int order_id = (int)map.get("order_id");
		String member_id = (String)map.get("member_id");
		Member member= new Member();
		Orders order = new Orders();
		member.setMember_id(member_id);;
		order.setMember(member);
		Member member1 = order.getMember();
		return insertUsepointRepository.insertUsePoint(use_point, order_id, member1);
	}
}
