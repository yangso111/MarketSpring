<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="noticeBoardsList" value="${noticeBoardsMap.noticeBoardsList}" />
<c:set var="section" value="${noticeBoardsMap.section}" />
<c:set var="pageNum" value="${noticeBoardsMap.pageNum}" />
<c:set var="totNoticeBoards" value="${noticeBoardsMap.totNoticeBoards}" />

<%
	request.setCharacterEncoding("UTF-8");
%>
	<meta charset="UTF-8">
	<title>공지사항 게시판 목록</title>

	<style>
		div.box_content_full>div.box_title {
			height: 60px;
			align: left;
			margin-top: 10px;
		}
		.table {
			background-color: white;
			border: 1px solid #DDDDDD;
			margin-bottom: 10px;
		}
		.table th {
			/* background-color: var(- -main-color); */
			vertical-align: middle;
			text-align: center;
			/* color: white; */
			font-size: 13px
		}
		.noticeBoard_name {
			text-align: left;
			width: 240px;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
			float: left;
		}
		#_btnSearch {
			padding: 1px 10px;
   			letter-spacing: 3px;
		}
		.no-uline {text-decoration:none;}
		.sel-page{text-decoration:none;color:red;}
		.cls1 {
			text-decoration: none;
		}
		.cls2 {
			text-align: center;
			font-size: 30px;
			margin: 20px auto;
		}
		.table {
			border: 1px solid black;
		}
		.table th {
			background-color: lightgreen;
			border: 1px solid black;
		}
		.table td {
			text-align: center;
			border: 1px solid black;
		}
	</style>
	
	<script>
		$(document).ready(function(){
			/* 검색 타입이 입력된 채로 페이지를 이동&검색 했을 경우 입력된 값으로 세팅함. */
			$("#_s_category").val("${s_category}");
			
			/* 글 검색 버튼 */
			$("#_btnSearch").click(function(){
				$("#_pageNumber").val(0);
				$("#_frmFormSearch").attr({"target":"_self", "action":"${contextPath}/board/noticeBo/listNoticeBoards.do"}).submit();
			});	
			
		});
		
		/* 페이지 이동 */
		function goPage(pageNumber){
			$("#_s_keyword").val("${s_keyword}");
			$("#_pageNumber").val(pageNumber);
			$("#_frmFormSearch").attr({"target":"_self", "action":"${contextPath}/board/noticeBo/listNoticeBoards.do"}).submit();
		};
		
		
		function fn_noticeBoardForm(isLogOn, noticeBoardForm, loginForm) {
			if (isLogOn != '' && isLogOn != 'false') {
				location.href = noticeBoardForm;
			} else {
				alert("로그인 후 글쓰기가 가능합니다.")
				location.href = loginForm + '?action=/board/noticeBo/noticeBoardForm.do';
			}
		}
	</script>


<div class="box_content_full">
	<div class="box_title" align="left" style="padding-left:20px; margin-top:25px;">
		<!-- <img src="image/pds/pds.png" width="40">&nbsp; -->
		<h1>공지사항 게시판</h1>
	</div>

	<div class="noticeBoard_input_wrap" style="width:95%; margin:7px auto; height:35px">
		<div style="width:80%; float:left">
			<!-- 검색 Form -->
			<form action="" name="frmForm1" id="_frmFormSearch" method="post">
				<div class="input-group">
					<select id="_s_category" name="s_category" class="form-control" style="width:100px;">
						<option value="">선택</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="mem_id">작성자</option>
					</select>
					<label for="" class="input-group-addon" style="padding:0; border:0; width:0"></label>
					<input type="text" id="_s_keyword" name="s_keyword" class="form-control" value="${noticeBoardsMap.s_keyword }" style="width:400px"/>
					<span class="input-group-btn">
						<button type="button" class="btn btn-main-color" id="_btnSearch">검색</button>
					</span>
					<!-- 페이징 전송부분 -->
					<input type="hidden" name="pageNumber" id="_pageNumber" value="${(empty pageNumber)?0:pageNumber }" />
					<input type="hidden" name="recordCountPerPage" id="_recordCountPerPage" value="${(empty recordCountPerPage)?10:recordCountPerPage }" />
				</div>
			</form>
		</div>
		<!-- 글쓰기 버튼 -->
		 <c:if test="${isLogOn==true and memberInfo.member_id =='admin' }"> 
			<button id="_btnAdd" style="width:14%; margin-right:15px; float:right;" onclick="fn_noticeBoardForm('${isLogOn}','${contextPath}/board/noticeBo/noticeBoardForm.do', '${contextPath}/member/loginForm.do')">
				<i class="fa fa-pencil-square" >&nbsp;</i>글쓰기
			</button>
		</c:if>
	</div>

	<!-- 공지사항 게시판 table -->
	<table class="table" style="width:95%; margin:0 auto; align:center;">
		<colgroup>
			<col width="40" /><col width="100" /><col width="220" /><col width="50" /><col width="85" />
		</colgroup>
	
		<thead>
			<tr>
				<th>번호</th><th>작성자</th><th>제목</th><th>조회수</th><th>작성일</th>
			</tr>
		</thead>
		
		<%-- <c:if test="${empty noticeBoardsList }"> --%>
		<%-- </c:if> --%>
		<c:choose>
			<c:when test="${noticeBoardsList == null }">
				<tr><td colspan="5">작성/조회된 글이 없습니다.</td></tr>
			</c:when>
			<c:when test="${noticeBoardsList != null }">
				<c:forEach var="noticeBoard" items="${noticeBoardsList }" varStatus="noticeBoardS">
					<tr class="_hober_tr">
						<td>${noticeBoardS.count }</td>
						<td>${noticeBoard.mem_id }</td>
						<td>				
							<div class="noticeBoard_name">
								<c:if test="${noticeBoard.is_del eq 1}">
									삭제된 글 입니다.
								</c:if>
								<c:if test="${noticeBoard.is_del != 1}">
									<%-- <a href="noticeBoarddetail.do?seq=${noticeBoard.seq }&room_seq=${noticeBoard.room_seq}" data-toggle="modal" data-target="#myPdsModal">${noticeBoard.title }</a> --%>
									<span style="padding-right: 30px"></span>
									<c:choose>
										<c:when test='${noticeBoard.level > 1 }'>
											<c:forEach begin="1" end="${noticeBoard.level }" step="1">
												<span style="padding-left: 20px"></span>
											</c:forEach>
											<span style='font-size: 12px;'>[답변]</span>
											<a class='cls1' href="${contextPath}/board/noticeBo/viewNoticeBoard.do?noticeBoardNO=${noticeBoard.notice_bo_no}">${noticeBoard.title}</a>
										</c:when>
										<c:otherwise>
											<a class='cls1' href="${contextPath}/board/noticeBo/viewNoticeBoard.do?noticeBoardNO=${noticeBoard.notice_bo_no}">${noticeBoard.title }</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>				
						</td>
						<td>${noticeBoard.read_count }</td>
						<td><font size="2">${noticeBoard.write_date }</font></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>

	<!-- 페이징 버튼 -->
	<div id="paging_wrap" style="width: 98%; height: 40px; margin: auto;">
	</div>

	<div class="cls2">
		<c:if test="${totNoticeBoards != null }">
			<c:choose>
				<c:when test="${totNoticeBoards > 100 }">
					<!-- 글 개수가 100 초과인경우 -->
					<c:forEach var="page" begin="1" end="10" step="1">
						<c:if test="${section > 1 && page==1 }">
							<a class="no-uline" href="${contextPath }/board/noticeBo/listNoticeBoards.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp;pre </a>
						</c:if>
						<a class="no-uline" href="${contextPath }/board/noticeBo/listNoticeBoards.do?section=${section}&pageNum=${page}">${(section-1)*10 +page }</a>
						<c:if test="${page == 10 }">
							<a class="no-uline" href="${contextPath }/board/noticeBo/listNoticeBoards.do?section=${section+1}&pageNum=${section*10+1}">&nbsp;next</a>
						</c:if>
					</c:forEach>
				</c:when>
				<c:when test="${totNoticeBoards == 100 }">
					<!--등록된 글 개수가 100개인경우  -->
					<c:forEach var="page" begin="1" end="10" step="1">
						<a class="no-uline" href="#">${page } </a>
					</c:forEach>
				</c:when>

				<c:when test="${totNoticeBoards < 100 }">
					<!--등록된 글 개수가 100개 미만인 경우  -->
					<c:forEach var="page" begin="1" end="${totNoticeBoards/10 + 1}" step="1">
						<c:choose>
							<c:when test="${page==pageNum }">
								<a class="sel-page" href="${contextPath }/board/noticeBo/listNoticeBoards.do?section=${section}&pageNum=${page}">${page }</a>
							</c:when>
							<c:otherwise>
								<a class="no-uline" href="${contextPath }/board/noticeBo/listNoticeBoards.do?section=${section}&pageNum=${page}">${page }</a>
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
