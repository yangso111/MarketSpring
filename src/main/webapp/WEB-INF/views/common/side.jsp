<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
	

<nav>
<ul>

<c:choose>
<c:when test="${side_menu=='admin_mode' }">
   <li>
		<H3>주요기능</H3>
		<ul>
			<li><a href="${contextPath}/admin/goods/adminGoodsMain.do">상품관리</a></li>
			<li><a href="${contextPath}/admin/order/adminOrderMain.do">주문관리</a></li>
			<li><a href="${contextPath}/admin/member/adminMemberMain.do">회원관리</a></li>
			<li><a href="#">배송관리</a></li>
			<li><a href="#">게시판관리</a></li>
		</ul>
	</li>
</c:when>
<c:when test="${side_menu=='my_page' }">
<%-- 	<li>
		<h3>주문내역</h3>
		<ul>
			<li><a href="${contextPath}/mypage/listMyOrderHistory.do">주문내역/배송 조회</a></li>
			<li><a href="#">반품/교환 신청 및 조회</a></li>
			<li><a href="#">취소 주문 내역</a></li>
			<li><a href="#">세금 계산서</a></li>
		</ul>
	</li>
	<li>
		<h3>정보내역</h3>
		<ul>
			<li><a href="${contextPath}/mypage/myDetailInfo.do">회원정보관리</a></li>
			<li><a href="#">나의 주소록</a></li>
			<li><a href="#">개인정보 동의내역</a></li>
			<li><a href="#">회원탈퇴</a></li>
		</ul>
	</li> --%>
	<li>
		<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;마이페이지</h3>
		<ul>
			<li><a href="${contextPath}/mypage/listMyOrderHistory.do">주문내역/배송 조회</a></li>
			<li><a href="#">포인트</a></li>
			<li><a href="${contextPath}/mypage/myDetailInfo.do">회원정보관리</a></li>
			<li><a href="#">회원탈퇴</a></li>
		</ul>
</c:when>
<c:when test="${side_menu=='board' }">	<!-- 06.17 sy) 기본 게시판 생성 후 고객센터와 연결 -->
	<li>
		<h3>게시판</h3>
		<ul>

			<li><a href="${contextPath}/board/noticeBo/listNoticeBoards.do">공지사항 게시판</a></li>
			<li><a href="${contextPath}/board/reViewBo/listreViewBoards.do">상품 후기</a></li>
			<li><a href="${contextPath}/board/qnaBo/listqnaBoards.do">문의사항 게시판</a></li>
			<li><a href="${contextPath}/board/freeBo/listFreeBoards.do">자유게시판</a></li>
		</ul>
	</li>									
</c:when>
<c:otherwise>
	<li>
		<h3>&nbsp;&nbsp;&nbsp;&nbsp;전체 카테고리</h3>
		<ul>
			<li><a href="${contextPath}/goods/goodsList.do">채소</a></li>
			<li><a href="#">과일, 견과, 쌀</a></li>
			<li><a href="#">수산, 해산, 건어물</a></li>
			<li><a href="#">정육, 계란</a></li>
			<li><a href="#">완성품</a></li>
			<li><a href="#">베이커리</a></li>
		</ul>
	</li>
	<li>
		<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;마켓 추천</h3>
		<ul>
			<li><a href="#">시원한 여름</a></li>
			<li><a href="#">식단관리</a></li>
			<li><a href="#">간편한 아침식사</a></li>
			<li><a href="#">재구매 BEST</a></li>
			<li><a href="#">1인가구</a></li>
			<li><a href="#">기타</a></li>
		</ul>
	</li>
 </c:otherwise>
</c:choose>	
</ul>
</nav>
<div class="clear"></div>
<div id="banner">
	<a href="#"><img width="190" height="163" src="${contextPath}/resources/image/n-pay.jpg"> </a>
</div>
<DIV id="notice">
	<H2>공지사항</H2>
	<UL>
	
	<c:forEach  var="i" begin="1" end="5" step="1">
		<li><a href="#">공지사항입니다.${ i}</a></li>
	</c:forEach>
	</ul>
</div>


<div id="banner">
	<a href="#"><img width="190" height="362" src="${contextPath}/resources/image/side_banner1.jpg"></a>
</div>
<div id="banner">
	<a href="#"><img width="190" height="104" src="${contextPath}/resources/image/call_center_logo.jpg"></a>
</div>
<div id="banner">
	<a href="#"><img width="190" height="69" src="${contextPath}/resources/image/QnA_logo.jpg"></a>
</div>
</html>