<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="qnaBoardsList" value="${qnaBoardsMap.qnaBoardsList}" />
<c:set var="section" value="${qnaBoardsMap.section}" />
<c:set var="pageNum" value="${qnaBoardsMap.pageNum}" />
<c:set var="totqnaBoards" value="${qnaBoardsMap.totqnaBoards}" />

<%
	request.setCharacterEncoding("UTF-8");
%>
	<meta charset="UTF-8">
	<title>문의사항 목록</title>

	<link href="${contextPath}/resources/css/board.css" rel="stylesheet" type="text/css">
	
	
	<script>
		$(document).ready(function(){
			/* 검색 타입이 입력된 채로 페이지를 이동&검색 했을 경우 입력된 값으로 세팅함. */
			$("#_s_category").val("${s_category}");
			
			/* 글 검색 버튼 */
			$("#_btnSearch").click(function(){
				$("#_pageNumber").val(0);
				$("#_frmFormSearch").attr({"target":"_self", "action":"${contextPath}/board/qnaBo/listqnaBoards.do"}).submit();
			});	
			
		});
		
		/* 페이지 이동 */
		function goPage(pageNumber){
			$("#_s_keyword").val("${s_keyword}");
			$("#_pageNumber").val(pageNumber);
			$("#_frmFormSearch").attr({"target":"_self", "action":"${contextPath}/board/qnaBo/listqnaBoards.do"}).submit();
		};
		
		
		function fn_qnaBoardForm(isLogOn, qnaBoardForm, loginForm) {
			if (isLogOn != '' && isLogOn != 'false') {
				location.href = qnaBoardForm;
			} else {
				alert("로그인 후 글쓰기가 가능합니다.")
				location.href = loginForm + '?action=/board/qnaBo/qnaBoardForm.do';
			}
		}
	</script>


<div class="box_content_full">
	<div class="box_title">
		<!-- <img src="image/pds/pds.png" width="40">&nbsp; -->
		<h1>문의사항 게시판</h1>
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
		<button id="_btnAdd" onclick="fn_qnaBoardForm('${isLogOn}','${contextPath}/board/qnaBo/qnaBoardForm.do', '${contextPath}/member/loginForm.do')">
			<i class="_btnAdd_fa" ></i>글쓰기
		</button>
	</div>

	<!-- 문의사항 게시판 table -->
	<table class="table" style="width:95%; margin:0 auto;">
		<colgroup>
			<col width="40" /><col width="80" /><col width="250" /><col width="50" /><col width="85" />
		</colgroup>
	
		<thead>
			<tr>
				<th>번호</th><th>작성자</th><th>제목</th><th>조회수</th><th>작성일</th>
			</tr>
		</thead>
		
		<%-- <c:if test="${empty freeBoardsList }"> --%>
		<%-- </c:if> --%>
		<c:choose>
			<c:when test="${qnaBoardsList == null }">
				<tr><td colspan="5">작성/조회된 글이 없습니다.</td></tr>
			</c:when>
			<c:when test="${qnaBoardsList != null }">
				<c:forEach var="qnaBoard" items="${qnaBoardsList }" varStatus="qnaBoardS">
					<tr class="_hober_tr">
						<td>${qnaBoardS.count }</td>
						<td>${qnaBoard.mem_id }</td>
						<td>				
							<div class="freeBoard_name">
							<span style="padding-right: 30px"></span>
								<c:if test="${qnaBoard.is_del eq 1}">
									삭제된 글 입니다.
								</c:if>
								<c:if test="${qnaBoard.is_del != 1}">
									<%-- <a href="freeBoarddetail.do?seq=${freeBoard.seq }&room_seq=${freeBoard.room_seq}" data-toggle="modal" data-target="#myPdsModal">${freeBoard.title }</a> --%>
									
									<c:choose>
										<c:when test='${qnaBoard.level > 1 }'>
											<c:forEach begin="1" end="${qnaBoard.level }" step="1">
												<span style="padding-left: 20px"></span>
											</c:forEach>
											<span style='font-size: 12px;'>[답변]</span>
											<a class='cls1' href="${contextPath}/board/qnaBo/viewqnaBoard.do?qnaBoardNO=${qnaBoard.qna_bo_no}">${qnaBoard.title}</a>
										</c:when>
										<c:otherwise>
											<a class='cls1' href="${contextPath}/board/qnaBo/viewqnaBoard.do?qnaBoardNO=${qnaBoard.qna_bo_no}">${qnaBoard.title }</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>				
						</td>
						<td>${qnaBoard.read_count }</td>
						<td><font size="2">${qnaBoard.write_date }</font></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>

	<!-- 페이징 버튼 -->
	<div id="paging_wrap" style="width: 98%; height: 40px; margin: auto;">
	</div>

	<div class="cls2">
		<c:if test="${totqnaBoards != null }">
			<c:choose>
				<c:when test="${totqnaBoards > 100 }">
					<!-- 글 개수가 100 초과인경우 -->
					<c:forEach var="page" begin="1" end="10" step="1">
						<c:if test="${section > 1 && page==1 }">
							<a class="no-uline" href="${contextPath }/board/qnaBo/listqnaBoards.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp;pre </a>
						</c:if>
						<a class="no-uline" href="${contextPath }/board/qnaBo/listqnaBoards.do?section=${section}&pageNum=${page}">${(section-1)*10 +page }</a>
						<c:if test="${page == 10 }">
							<a class="no-uline" href="${contextPath }/board/qnaBo/listqnaBoards.do?section=${section+1}&pageNum=${section*10+1}">&nbsp;next</a>
						</c:if>
					</c:forEach>
				</c:when>
				<c:when test="${totqnaBoards == 100 }">
					<!--등록된 글 개수가 100개인경우  -->
					<c:forEach var="page" begin="1" end="10" step="1">
						<a class="no-uline" href="#">${page } </a>
					</c:forEach>
				</c:when>

				<c:when test="${totqnaBoards < 100 }">
					<!--등록된 글 개수가 100개 미만인 경우  -->
					<c:forEach var="page" begin="1" end="${totqnaBoards/10 + 1}" step="1">
						<c:choose>
							<c:when test="${page==pageNum }">
								<a class="sel-page" href="${contextPath }/board/qnaBo/listqnaBoards.do?section=${section}&pageNum=${page}">${page }</a>
							</c:when>
							<c:otherwise>
								<a class="no-uline" href="${contextPath }/board/qnaBo/listqnaBoards.do?section=${section}&pageNum=${page}">${page }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
			</c:choose>
		</c:if>
	</div>
</div>



<form name="frmForm" id="_frmForm" method="get" action="pdswrite.do">
</form>
