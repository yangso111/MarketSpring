package com.marketSpring.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marketSpring.common.base.BaseController;
import com.marketSpring.goods.vo.GoodsVO;
import com.marketSpring.member.service.MemberService;
import com.marketSpring.member.vo.MemberVO;
import com.marketSpring.order.service.OrderService;
import com.marketSpring.order.vo.OrderVO;

@Controller("orderController")
@RequestMapping(value="/order")
public class OrderControllerImpl extends BaseController implements OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrderVO orderVO;

	@RequestMapping(value = "/orderEachGoods.do", method = RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO, 
									HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		session = request.getSession();

		Boolean isLogOn = (Boolean) session.getAttribute("isLogOn");
		String action = (String) session.getAttribute("action");
		// 로그인 여부 체크
		// 로그인을 하지 않았다면 먼저 로그인 후 주문으르 처리하도록 주문 정보와 주문 페이지 요청 URL을 세션에 저장함
		if (isLogOn == null || isLogOn == false) {
			session.setAttribute("orderInfo", _orderVO);
			session.setAttribute("action", "/order/orderEachGoods.do");

			return new ModelAndView("redirect:/member/loginForm.do");
		}
		// 로그인 후 세션에서 주문 정보를 가져와 바로 주문창으로 이동함
		else {
			if (action != null && action.equals("/order/orderEachGoods.do")) {
				orderVO = (OrderVO) session.getAttribute("orderInfo");
				session.removeAttribute("action");
			}
			// 미리 로그인을 했다면 바로 주문을 처리함
			else {
				orderVO = _orderVO;
			}
		}

		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		List myOrderList = new ArrayList<OrderVO>(); // 주문 정보를 저장할 주문 ArrayList를 생성
		myOrderList.add(orderVO); // 브라우저에서 전달한 주문 정보를 ArrayList에 저장
		
		MemberVO memberInfo = (MemberVO) session.getAttribute("memberInfo");
		
		// 주문 정보와 주문자 정보를 세션에 바인딩한 후 주문창으로 전달함
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberInfo);

		return mav;
	}

	@RequestMapping(value = "/orderAllCartGoods.do", method = RequestMethod.POST)
	public ModelAndView orderAllCartGoods(@RequestParam("cart_goods_qty") String[] cart_goods_qty,
										HttpServletRequest request, HttpServletResponse response) throws Exception {
		// @RequestParam("cart_goods_qty") String[] cart_goods_qty: 선택한 상품 수량을 배열로 받습니다.
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("viewName: " + viewName);
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		
		// 미리 세션에 저장한 장바구니 상품 목록을 가져옴
		Map cartMap = (Map)session.getAttribute("cartMap");
		List myOrderList = new ArrayList<OrderVO>();
		List<GoodsVO> myGoodsList = (List<GoodsVO>)cartMap.get("myGoodsList");
		
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		
		// 장바구니 상품 개수만큼 반복
		for (int i = 0; i < cart_goods_qty.length; i++) {
			String[] cart_goods = cart_goods_qty[i].split(":");	// 문자열로 결함되어 전송된 상품 번호와 주문 수량을 split() 메서드를 이용해 분리
			
			for (int j = 0; j < myGoodsList.size(); j++) {
				GoodsVO goodsVO = myGoodsList.get(j);	// 장바구니 목록에서 차례로 GoodsVo를 가져옴
				int goods_id = goodsVO.getGoods_id();	// GoodsVO의 상품 번호를 가져옴
				
				// 전송된 상품 번호와 GoodsVO의 상품 번호가 같으면 주문하는 상품이므로 OrderVO 객체를 생성한 후 상품 정보를 OrderVO에 설정함
				// 그리고 다시 myOrderList에 저장
				if(goods_id==Integer.parseInt(cart_goods[0])) {
					OrderVO _orderVO = new OrderVO();
					String goods_title = goodsVO.getGoods_title();
					int goods_sales_price = goodsVO.getGoods_sales_price();
					String goods_fileName = goodsVO.getGoods_fileName();
					_orderVO.setGoods_id(goods_id);
					_orderVO.setGoods_title(goods_title);
					_orderVO.setGoods_sales_price(goods_sales_price);
					_orderVO.setGoods_fileName(goods_fileName);
					_orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
					myOrderList.add(_orderVO);
					break;
				}
			}
		}

		// 장바구니 목록에서 주문하기 위해 선택한 상품만 myOrderList에 저장한 후 세션에 바인딩
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberVO);
		
		return mav;			
	}

	@RequestMapping(value = "/payToOrderGoods.do", method = RequestMethod.POST)
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap, 
										HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		// @RequestParam Map<String, String> receiverMap: 주문창에서 입력한 상품 수령자 정보와 배송지 정보를 Map에 바로 저장
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("orderer");
		String member_id = memberVO.getMember_id();
		String orderer_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1() + "-" + memberVO.getHp2() + "-" + memberVO.getHp3();
		List<OrderVO> myOrderList = (List<OrderVO>) session.getAttribute("myOrderList");

		for (int i = 0; i < myOrderList.size(); i++) { // 주문창에서 입력한 수령자 정보와 배송지 정보를 주문 상품 정보 목록과 합침
			OrderVO orderVO = (OrderVO) myOrderList.get(i);
			// 각 orderVO에 수령자 정보를 설정한 후 다시 myOrderList에 저장
			orderVO.setMember_id(member_id);
			orderVO.setOrderer_name(orderer_name);
			orderVO.setReceiver_name(receiverMap.get("receiver_name"));

			orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
			orderVO.setReceiver_hp2(receiverMap.get("receiver_hp2"));
			orderVO.setReceiver_hp3(receiverMap.get("receiver_hp3"));
			orderVO.setReceiver_tel1(receiverMap.get("receiver_tel1"));
			orderVO.setReceiver_tel2(receiverMap.get("receiver_tel2"));
			orderVO.setReceiver_tel3(receiverMap.get("receiver_tel3"));

			orderVO.setDelivery_address(receiverMap.get("delivery_address"));
			orderVO.setDelivery_message(receiverMap.get("delivery_message"));
			orderVO.setDelivery_method(receiverMap.get("delivery_method"));
			orderVO.setGift_wrapping(receiverMap.get("gift_wrapping"));
			orderVO.setPay_method(receiverMap.get("pay_method"));
			orderVO.setCard_com_name(receiverMap.get("card_com_name"));
			orderVO.setCard_pay_month(receiverMap.get("card_pay_month"));
			orderVO.setPay_orderer_hp_num(receiverMap.get("pay_orderer_hp_num"));
			orderVO.setOrderer_hp(orderer_hp);
			
			orderVO.setDelivery_zipcode(receiverMap.get("zipcode"));
			orderVO.setDelivery_roadAddress(receiverMap.get("roadAddress"));
			orderVO.setDelivery_jibunAddress(receiverMap.get("jibunAddress"));
			orderVO.setDelivery_namujiAddress(receiverMap.get("namujiAddress"));

			orderVO.setOrder_point(Integer.parseInt(receiverMap.get("order_point")));

			myOrderList.set(i, orderVO); // 각 orderVO에 주문자 정보를 세팅한 후 다시 myOrderList에 저장한다.
		}

		orderService.addNewOrder(myOrderList); // 주문자 정보를 SQL문으로 전달
		
		//주문 완료 후 포인트 적립
		Map  pointMap = new HashMap<String, Object>();
		pointMap.put("member_id", member_id);
		pointMap.put("member_point", Integer.parseInt(receiverMap.get("order_point")));
		memberService.addPoint(pointMap);

		mav.addObject("myOrderInfo", receiverMap); // 주문 완료 결과창에 주문자 정보를 표시하도록 전달
		mav.addObject("myOrderList", myOrderList); // 주문 완료 결과창에 주문 상품 목록을 표시하도록 전달

		return mav;
	}
	
	@Override
	@RequestMapping(value = "/findRecentlyDelivery.do", method = RequestMethod.GET,  produces = "application/json")
	public @ResponseBody HashMap <String, Object> findRecentlyDelivery(@RequestParam String member_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap <String,Object>();
		OrderVO orderVO = orderService.findRecentlyDelivery(member_id);		// 최근배송지 조회
		
		if ( orderVO == null ) {
			String Msg = "최근 구매이력이 없습니다.";
			String Code = "1";
			
			result.put("Msg", Msg);
			result.put("Code", Code);
			
			return result;
		} else {
			String Msg = "성공";
			String Code = "0";
			
			result.put("Msg", Msg);
			result.put("Code", Code);
			result.put("data", orderVO);
			
			return result;
		}
	}

}