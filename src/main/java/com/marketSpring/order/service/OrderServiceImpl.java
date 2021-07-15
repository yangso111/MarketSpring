package com.marketSpring.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marketSpring.order.dao.OrderDAO;
import com.marketSpring.order.vo.OrderVO;


@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO;
	
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception{
		List<OrderVO> orderGoodsList;
		orderGoodsList=orderDAO.listMyOrderGoods(orderVO);
		return orderGoodsList;
	}
	
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception{
		orderDAO.insertNewOrder(myOrderList);		// 주문 상품 목록을 테이블에 추가
		//카트에서 주문 상품 제거
		orderDAO.removeGoodsFromCart(myOrderList);	// 장바구니에서 주문한 경우 해당 상품을 장바구니에서 삭제
		// 포인트 적립 로그에 추가
		orderDAO.insertPointLog(myOrderList);
	}	
	
	public OrderVO findMyOrder(String order_id) throws Exception{
		return orderDAO.findMyOrder(order_id);
	}

	@Override
	public OrderVO findRecentlyDelivery(String member_id) throws Exception {
		return orderDAO.findRecentlyDelivery(member_id);
	}
}
