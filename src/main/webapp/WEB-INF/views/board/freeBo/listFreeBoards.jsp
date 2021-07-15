<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="freeBoardsList" value="${freeBoardsMap.freeBoardsList}" />
<c:set var="section" value="${freeBoardsMap.section}" />
<c:set var="pageNum" value="${freeBoardsMap.pageNum}" />
<c:set var="totFreeBoards" value="${freeBoardsMap.totFreeBoards}" />

<%
	request.setCharacterEncoding("UTF-8");
%>
	<meta charset="UTF-8">
	<title>자유게시판 목록</title>

	<link href="${contextPath}/resources/css/board.css" rel="stylesheet" type="text/css">
	
	<script>
		$(document).ready(function(){
			/* 검색 타입이 입력된 채로 페이지를 이동&검색 했을 경우 입력된 값으로 세팅함. */
			$("#_s_category").val("${s_category}");
			
			/* 글 검색 버튼 */
			$("#_btnSearch").click(function(){
				$("#_pageNumber").val(0);
				$("#_frmFormSearch").attr({"target":"_self", "action":"${contextPath}/board/freeBo/listFreeBoards.do"}).submit();
			});	
			
		});
		
		/* 페이지 이동 */
		function goPage(pageNumber){
			$("#_s_keyword").val("${s_keyword}");
			$("#_pageNumber").val(pageNumber);
			$("#_frmFormSearch").attr({"target":"_self", "action":"${contextPath}/board/freeBo/listFreeBoards.do"}).submit();
		};
		
		
		function fn_freeBoardForm(isLogOn, freeBoardForm, loginForm) {
			if (isLogOn != '' && isLogOn != 'false') {
				location.href = freeBoardForm;
			} else {
				alert("로그인 후 글쓰기가 가능합니다.")
				location.href = loginForm + '?action=/board/freeBo/freeBoardForm.do';
			}
		}
	</script>


<div class="box_content_full">
	<div class="box_title">
		<!-- <img src="image/pds/pds.png" width="40">&nbsp; -->
		<!-- <font size="4" color="gray"  style="margin-top:20px;">자유게시판</font> -->
		<h1>자유게시판</h1>
	</div>

	<div class="freeBoard_input_wrap">
		<div class="freeBoard_Search">
			<!-- 검색 Form -->
			<form action="" name="frmForm1" id="_frmFormSearch" method="post">
				<div class="input-group">
					<select id="_s_category" name="s_category" class="_s_category">
						<option value="">선택</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="mem_id">작성자</option>
					</select>
					<label for="" class="input_s_keyLabel"></label>
					<input type="text" id="_s_keyword" name="s_keyword" class="_s_keyword" value="${freeBoardsMap.s_keyword }" />
					<span class="input_searchBtn">
						<button type="button" class="btn btn_searchBtn" id="_btnSearch">검색</button>
					</span>
					<!-- 페이징 전송부분 -->
					<input type="hidden" name="pageNumber" id="_pageNumber" value="${(empty pageNumber)?0:pageNumber }" />
					<input type="hidden" name="recordCountPerPage" id="_recordCountPerPage" value="${(empty recordCountPerPage)?10:recordCountPerPage }" />
				</div>
			</form>
		</div>
		<!-- 글쓰기 버튼 -->
		<button id="_btnAdd" onclick="fn_freeBoardForm('${isLogOn}','${contextPath}/board/freeBo/freeBoardForm.do', '${contextPath}/member/loginForm.do')">
			<i class="_btnAdd_fa" ></i>글쓰기
		</button>
	</div>

	<!-- 자유게시판 table -->
	<table class="table" style="width:95%; margin:0 auto;">
		<colgroup>
			<col width="40" /><col width="80" /><col width="250" /><col width="50" /><col width="85" />
		</colgroup>
	
		<thead>
			<tr>
				<th>번호</th><th>작성자</th><th>제목</th><th>조회수</th><th>작성일</th>
			</tr>
		</thead>
		
		<c:choose>
			<c:when test="${freeBoardsList == null }">
				<tr><td colspan="5">작성/조회된 글이 없습니다.</td></tr>
			</c:when>
			<c:when test="${freeBoardsList != null }">
				<c:forEach var="freeBoard" items="${freeBoardsList }" varStatus="freeBoardS">
					<tr class="_hober_tr">
						<td>${freeBoardS.count }</td>
						<td>${freeBoard.mem_id }</td>
						<td>				
							<div class="freeBoard_name">
								<span style="padding-right: 30px"></span>
								<c:choose>
									<c:when test='${freeBoard.level > 1 }'>
										<c:forEach begin="1" end="${freeBoard.level }" step="1">
											<span style="padding-left: 20px"></span>
										</c:forEach>
										<span style='font-size: 12px;'>[답변]</span>
										<c:if test="${freeBoard.is_del eq 1}">
											삭제된 글 입니다.
										</c:if>
										<c:if test="${freeBoard.is_del != 1}">
											<a class='cls1' href="${contextPath}/board/freeBo/viewFreeBoard.do?freeBoardNO=${freeBoard.free_bo_no}">${freeBoard.title}</a>
										</c:if>
									</c:when>
									<c:otherwise>
									<c:if test="${freeBoard.is_del eq 1}">
										삭제된 글 입니다.
									</c:if>
									<c:if test="${freeBoard.is_del != 1}">
										<a class='cls1' href="${contextPath}/board/freeBo/viewFreeBoard.do?freeBoardNO=${freeBoard.free_bo_no}">${freeBoard.title }</a>
									</c:if>
									</c:otherwise>
								</c:choose>
							</div>				
						</td>
						<td>${freeBoard.read_count }</td>
						<td><font size="2">${freeBoard.write_date }</font></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>

	<!-- 페이징 버튼 -->
	<div id="paging_wrap">
		<div class="cls2">
			<c:if test="${totFreeBoards != null }">
				<c:choose>
					<c:when test="${totFreeBoards > 100 }">
						<!-- 글 개수가 100 초과인경우 -->
						<c:forEach var="page" begin="1" end="10" step="1">
							<c:if test="${section > 1 && page==1 }">
								<%-- <a class="no-uline" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp;pre </a> --%>
								<a class="no-uline" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section-1}&pageNum=${(section-1)*10 +1 }" class="btnPre">
									<img width="20px" alt="" src="/marketSpring/resources/image/btn_pre.png">									
								</a>
							</c:if>
							<c:choose>
								<c:when test="${page == pageNum }">
									<a class="sel-page" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section}&pageNum=${page}">${(section-1)*10 +page }</a>
								</c:when>
								<c:otherwise>
									<a class="no-uline" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section}&pageNum=${page}">${(section-1)*10 +page }</a>
								</c:otherwise>
							</c:choose>
							<c:if test="${page == 10 }">
								<%-- <a class="no-uline" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section+1}&pageNum=${section*10+1}">&nbsp;next</a> --%>
								<a class="no-uline" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section+1}&pageNum=${section*10+1}" class="btnNext">
									<img width="20px" alt="" src="/marketSpring/resources/image/btn_next.png">
								</a>
							</c:if>
						</c:forEach>
					</c:when>
					<c:when test="${totFreeBoards == 100 }">
						<!--등록된 글 개수가 100개인경우  -->
						<c:forEach var="page" begin="1" end="10" step="1">
							<c:choose>
								<c:when test="${page == pageNum }">
									<a class="sel-page" href="#">${page } </a>
								</c:when>
								<c:otherwise>
									<a class="no-uline" href="#">${page } </a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
	
					<c:when test="${totFreeBoards < 100 }">
						<!--등록된 글 개수가 100개 미만인 경우  -->
						<c:forEach var="page" begin="1" end="${totFreeBoards/10 + 1}" step="1">
							<c:choose>
								<c:when test="${page == pageNum }">
									<a class="sel-page" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section}&pageNum=${page}">${page }</a>
								</c:when>
								<c:otherwise>
									<a class="no-uline" href="${contextPath }/board/freeBo/listFreeBoards.do?section=${section}&pageNum=${page}">${page }</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>



<form name="frmForm" id="_frmForm" method="get" action="pdswrite.do">
</form>
