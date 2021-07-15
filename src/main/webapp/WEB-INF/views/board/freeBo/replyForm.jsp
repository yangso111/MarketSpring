<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%> 
<head>
	<meta charset="UTF-8">
	<title>답글쓰기</title>
	
	<link href="${contextPath}/resources/css/board.css" rel="stylesheet" type="text/css">
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<script type="text/javascript">
		function backToList(obj){
		    obj.action="${contextPath}/board/freeBo/listFreeBoards.do";
			obj.submit();
		}
		
		function readURL(input, index) {
		    if (input.files && input.files[0]) {
		        var reader = new FileReader();
		        reader.onload = function (e) {
		            $('#preview' + index).attr('src', e.target.result);
		            $('#preview' + index).removeAttr('hidden');
		        }
		        reader.readAsDataURL(input.files[0]);
		    }
		}
		  
		var cnt=1;
		function fn_addFile(){
			cnt++;
			var innerHtml = "";
			/* innerHtml +='<tr  width=100%  align=center>';
			innerHtml +='<td >'+ "<input  type=file  name='file" + cnt + "'  onchange='readURL(this,"+ cnt + ")'   />"
					  	+ '</td>';
			innerHtml +='<td>'+ "<img  id='preview" + cnt + "''   width=640 height=480/>"
						+ '</td>';
			innerHtml +='</tr>';
			$("#tb_newImage").append(innerHtml); */
			
			innerHtml +="<input type='file' name='file"+cnt+"' onchange='readURL(this,"+ cnt+")' style='margin: 5px 10px 5px 50px'/>";
			innerHtml +="<img  id='preview"+cnt+"' style='width: 200px; height: 200px;' class='inputFiles' /> <br>";

			$("#d_file").append(innerHtml);
		}  
	</script> 
	<title>답글쓰기 페이지</title>
</head>
<body>
	<div class="box_title">
		<h1>자유게시판 - 답글쓰기</h1>
	</div>
	<!-- <h1 style="text-align: center">답글쓰기</h1> -->
	<input type="hidden" name="parentNO" value="${parentNO}">
	<form name="frmFreeBoard" method="post" action="${contextPath}/board/freeBo/addNewFreeBoard.do" enctype="multipart/form-data">
		<table class="replyFreeBoardTbl">
			<tr>
				<th>작성자 아이디</th>
				<td>${memberInfo.member_id }</td>
			</tr>
			<tr>
				<th>글제목</th>
				<td colspan="2">
					<input type="text" size="67" maxlength="500" name="title" placeholder="글제목" />
				</td>
			</tr>
			<tr>
				<th valign="top">글내용</th>
				<td colspan=2>
					<textarea name="content" maxlength="4000" placeholder="이곳에 글을 쓰세요."></textarea>
				</td>
			</tr>
			<tr>
				<th>이미지파일 첨부</th>
				<td style="padding-top: 15px; width:40%;">
					<input type="file" name="imageFileName" onchange="readURL(this, 0);" />
					<img id="preview0" src="#" style="width: 200px; height: 200px;" hidden="true"/>
				</td>
				<td style="padding-top: 20px;">
					이미지파일 첨부<br>
					<input type="button" value="새 이미지 추가하기" onClick="fn_addFile()" class="_btnaddFile" />
				</td>
			</tr>
			<tr>
				<td colspan="3"><div id="d_file"></div></td>
			</tr>
			<tr>
				<!-- <th></th> -->
				<td colspan="3">
					<input type="submit" value="답글쓰기" class="_btnFreeBoWrite" /> 
					<input type=button value="목록보기" class="_btnFreeBoList" onClick="backToList(this.form)" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>