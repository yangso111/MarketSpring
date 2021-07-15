<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="qnaBoard" value="${qnaBoardMap.qnaBoard}" />
<c:set var="imgFileList" value="${qnaBoardMap.imgFileList}" />


<%
	request.setCharacterEncoding("UTF-8");
%>
<head>
   <meta charset="UTF-8">
   <title>문의 게시글 상세보기</title>
   
   <link href="${contextPath}/resources/css/board.css" rel="stylesheet" type="text/css">
   <script  src="http://code.jquery.com/jquery-latest.min.js"></script> 
   
   <script type="text/javascript" >
     function backToList(obj){
	    obj.action="${contextPath}/board/qnaBo/listqnaBoards.do";
	    obj.submit();
     }
 
     // 수정하기 클릭시 disavled false 처리
	 function fn_enable(obj, fileListSize){
		 document.getElementById("i_title").disabled = false;
		 document.getElementById("i_content").disabled = false;
		 document.getElementById("tr_btn_modify").style.display="block";
		 document.getElementById("tr_btn").style.display="none";
		 $(".tr_modEnable").css('visibility', 'visible');
	 }
	 
     // 수정하기 반영하기
	 function fn_modify_qnaBoard(obj){
		 obj.action="${contextPath}/board/qnaBo/modqnaBoard.do";
		 obj.submit();
	 }
	 
	 function fn_remove_qnaBoard(url,qnaBoardNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var qnaBoardNOInput = document.createElement("input");
	     qnaBoardNOInput.setAttribute("type","hidden");
	     qnaBoardNOInput.setAttribute("name","qnaBoardNO");
	     qnaBoardNOInput.setAttribute("value", qnaBoardNO);
		 
	     form.appendChild(qnaBoardNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 
	 }

	 function fn_reply_form(isLogOn, url, parentNO) {
		  if(isLogOn != '' && isLogOn != 'false'){
			 var form = document.createElement("form");
			 form.setAttribute("method", "post");
			 form.setAttribute("action", url);
		     var parentNOInput = document.createElement("input");
		     parentNOInput.setAttribute("type","hidden");
		     parentNOInput.setAttribute("name","parentNO");
		     parentNOInput.setAttribute("value", parentNO);
			 
		     form.appendChild(parentNOInput);
		     document.body.appendChild(form);
			 form.submit();
		  }else{
		    alert("로그인 후 글쓰기가 가능합니다.");
		    location.href="${contextPath}/member/loginForm.do?action=/board/qnaBo/replyForm.do&parentNO="+parentNO;
		  }
	 }

	 function readURL(input, index) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview'+index).attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 }

	 function fn_removeModImage(_imageFileNO, _qnaBoardNO, _imageFileName ){
		 $.ajax({
			 type:"post",
			async:false,  
			url:"${contextPath}/board/qnaBo/removeModImage.do",
			dataType:"text",
			data: {imageFileNO : _imageFileNO,  qnaBoardNO : _qnaBoardNO, imageFileName : _imageFileName},
			success:function (result, textStatus){
			   if(result == 'success'){
				    alert("이미지를 삭제했습니다.");
				 	location.href="${contextPath}/board/qnaBo/viewqnaBoard.do?qnaBoardNO=" + _qnaBoardNO;
			   }else{
				   //$('#message').text("사용할 수 없는 ID입니다.");
			   }
			},
			error:function(data,textStatus){
			   alert("에러가 발생했습니다.");ㅣ
			},
			complete:function(data,textStatus){
			   //alert("작업을완료 했습니다");
			}
		});  //end ajax	
	}
	 
	 var pre_img_num = 0;  //수정 이전의 이미지 수
	 var img_index = 0;     //수정 후 이미지 수 
	 
	 var isFirstAddImage = true;
	 function fn_addModImage(_img_index){
		 if(isFirstAddImage == true){
			 pre_img_num = _img_index;
			 img_index = ++_img_index;
			 isFirstAddImage = false;
		 }else{
			 ++img_index;	 
		 }
		 
		 var innerHtml = "";
/* 		 innerHtml +='<tr  width=200px  align=center>';
		 innerHtml +='<td >'+
		 					"<input  type=file  name=imageFileName" + img_index + "  onchange='readURL(this,"+ img_index+")'   />"+
		 					'</td>';
		 innerHtml +='<td>'+
							"<img  id=preview"+img_index+"  />"+
		                   	'</td>';
		 innerHtml +='</tr>';
		 $("#tb_addImage").append(innerHtml);
	
		 $("#added_img_num").val(img_index);  // 추가된 이미지수를 히든 태그에 저장해서 컨트롤러로 보낸다.
 */	 
		 innerHtml +="<input type='file' name='imageFileName"+img_index+"' onchange='readURL(this,"+ img_index+")' style='margin: 5px 10px 5px 60px'/>";
		 innerHtml +="<img  id='preview"+img_index+"'  /> <br>";
		 
		 $("#d_file").append(innerHtml);
	
		 $("#added_img_num").val(img_index);  // 추가된 이미지수를 히든 태그에 저장해서 컨트롤러로 보낸다.

	 }
 </script>
</head>
<body>
	<div class="box_title">
		<h1>문의사항 게시판</h1>
	</div>
	<form name="frmqnaBoard" method="post" action="${contextPath}" enctype="multipart/form-data">
		<table class="detailFreeBoardTbl">
			<tr>
				<th>글번호</th>
				<td>
					<input type="text" value="${qnaBoard.qna_bo_no }" disabled />
					<input type="hidden" name="qnaBoardNO" value="${qnaBoard.qna_bo_no}" />
				</td>
			</tr>
			<tr>
				<th>작성자 아이디</th>
				<td>
					<input type=text value="${qnaBoard.mem_id }" name="writer" disabled />
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type=text value="${qnaBoard.title }" name="title" id="i_title" disabled />
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content" id="i_content" disabled />${qnaBoard.content }</textarea>
				</td>
			</tr>
<c:set var="img_index" />
<c:choose>
	<c:when test="${not empty imgFileList && imgFileList!='null' }">
		<c:forEach var="item" items="${imgFileList}" varStatus="status">
			<tr>
				<th>이미지${status.count }</th>
				<td>
					<input type="hidden" name="oldFileName" value="${item.img_filename }" />
					<input type="hidden" name="imgFileNO" value="${item.qna_bo_img_no }" />
					<img src="${contextPath}/qnaBoImgDownload.do?qnaBoardNO=${qnaBoard.qna_bo_no}&imageFileName=${item.img_filename}" id="preview${status.index }" /><br>
					<div class="tr_modEnable" style="margin-top: 10px;">
						<input type="file" name="imgFileName${status.index }" id="i_imgFileName${status.index }" onchange="readURL(this, ${status.index });" />
						<input type="button" value="이미지 삭제하기" onclick="fn_removeModImage(${item.qna_bo_img_no },  ${item.qna_bo_no }, '${item.img_filename }' )" />
					</div>
				</td>
<%-- 		</tr>
			<tr class="tr_modEnable">
				<td></td>
				<td>
					<input type="file" name="imgFileName${status.index }" id="i_imgFileName${status.index }" onchange="readURL(this, ${status.index });" />
					<input type="button" value="이미지 삭제하기" onclick="fn_removeModImage(${item.qna_bo_img_no },  ${item.qna_bo_no }, '${item.img_filename }' )" />
				</td>
			</tr> --%>
			<c:if test="${status.last eq true }">
				<c:set var="img_index" value="${status.count}" />
				<input type="hidden" name="pre_img_num" value="${status.count}" />
				<!-- 기존의 이미지수 -->
				<input type="hidden" id="added_img_num" name="added_img_num" value="${status.count}" />
				<!--   수정시 새로 추가된 이미지 수  -->
			</c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:set var="img_index" value="${0}" />
		<input type="hidden" name="pre_img_num" value="${0}" />
		<!-- 기존의 이미지수 -->
		<input type="hidden" id="added_img_num" name="added_img_num" value="${0}" />
		<!--   수정시 새로 추가된 이미지 수  -->
	</c:otherwise>
</c:choose>
			<tr class="tr_modEnable">
				<th style="padding: 0;">이미지 추가</th>
				<td>
					<input type="button" value="이미지 추가" onclick="fn_addModImage(${img_index})" class="_btnaddFile"/>
					<div id="d_file"></div>
				</td>
			</tr>

<%-- 			<tr class="tr_modEnable">
				<td colspan="2">
					<input type="button" value="이미지 추가" onclick="fn_addModImage(${img_index})" />
				</td>
			</tr> --%>

			<tr>
				<th>등록일자</th>
				<td> 
					<input type=text value="<fmt:formatDate value="${qnaBoard.write_date}" />" disabled />
				</td>
			</tr>
			<tr id="tr_btn_modify">
				<td colspan="2">
					<input type=button value="수정반영하기" onClick="fn_modify_qnaBoard(frmqnaBoard)">
					<input type=button value="취소" onClick="backToList(frmqnaBoard)">
				</td>
			</tr>

			<tr id="tr_btn">
				<td colspan="2" align="center">
					<c:if test="${memberInfo.member_id == qnaBoard.mem_id }">
						<input type=button value="수정하기" onClick="fn_enable(this.form)">
						<input type=button value="삭제하기" onClick="fn_remove_qnaBoard('${contextPath}/board/qnaBo/removeqnaBoard.do', ${qnaBoard.qna_bo_no})">
					</c:if>
					<input type=button value="리스트로 돌아가기" onClick="backToList(this.form)"> 
					<c:if test="${memberInfo.member_id == 'admin' }">
					<input type=button value="답글쓰기" onClick="fn_reply_form('${isLogOn}', '${contextPath}/board/qnaBo/replyForm.do', ${qnaBoard.qna_bo_no})">
					</c:if>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>