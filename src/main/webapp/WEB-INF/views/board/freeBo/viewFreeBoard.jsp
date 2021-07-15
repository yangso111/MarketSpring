<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="freeBoard" value="${freeBoardMap.freeBoard}" />
<c:set var="imgFileList" value="${freeBoardMap.imgFileList}" />


<%
	request.setCharacterEncoding("UTF-8");
%>
<head>
   <meta charset="UTF-8">
   <title>자유 게시글 상세보기</title>

	<link href="${contextPath}/resources/css/board.css" rel="stylesheet" type="text/css">
   	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
   	
   <script type="text/javascript" >
     function backToList(obj){
	    obj.action="${contextPath}/board/freeBo/listFreeBoards.do";
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
	 function fn_modify_freeBoard(obj){
		 obj.action="${contextPath}/board/freeBo/modFreeBoard.do";
		 obj.submit();
	 }
	 
	 function fn_remove_freeBoard(url,freeBoardNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var freeBoardNOInput = document.createElement("input");
	     freeBoardNOInput.setAttribute("type","hidden");
	     freeBoardNOInput.setAttribute("name","freeBoardNO");
	     freeBoardNOInput.setAttribute("value", freeBoardNO);
		 
	     form.appendChild(freeBoardNOInput);
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
		    location.href="${contextPath}/member/loginForm.do?action=/board/freeBo/replyForm.do&parentNO="+parentNO;
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

	 function fn_removeModImage(_imageFileNO, _freeBoardNO, _imageFileName ){
		 $.ajax({
			 type:"post",
			async:false,  
			url:"${contextPath}/board/freeBo/removeModImage.do",
			dataType:"text",
			data: {imageFileNO : _imageFileNO,  freeBoardNO : _freeBoardNO, imageFileName : _imageFileName},
			success:function (result, textStatus){
			   if(result == 'success'){
				    alert("이미지를 삭제했습니다.");
				 	location.href="${contextPath}/board/freeBo/viewFreeBoard.do?freeBoardNO=" + _freeBoardNO;
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
		 /* innerHtml +='<tr  width=200px  align=center>';
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
		<h1>자유게시판</h1>
	</div>
	<form name="frmFreeBoard" method="post" action="${contextPath}" enctype="multipart/form-data">
		<table class="detailFreeBoardTbl">
			<tr>
				<th>글번호</th>
				<td>
					<input type="text" value="${freeBoard.free_bo_no }" disabled />
					<input type="hidden" name="freeBoardNO" value="${freeBoard.free_bo_no}" />
				</td>
			</tr>
			<tr>
				<th>작성자 아이디</th>
				<td>
					<input type=text value="${freeBoard.mem_id }" name="writer" disabled />
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type=text value="${freeBoard.title }" name="title" id="i_title" disabled />
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content" id="i_content" disabled />${freeBoard.content }</textarea>
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
					<input type="hidden" name="imgFileNO" value="${item.free_bo_img_no }" />
					<img src="${contextPath}/freeBoImgDownload.do?freeBoardNO=${freeBoard.free_bo_no}&imageFileName=${item.img_filename}" id="preview${status.index }" style="width: 200px; height: 200px"/><br>
					<div class="tr_modEnable" style="margin-top: 10px;">
						<input type="file" name="imgFileName${status.index }" id="i_imgFileName${status.index }" onchange="readURL(this, ${status.index });" />
						<input type="button" value="이미지 삭제하기" onclick="fn_removeModImage(${item.free_bo_img_no },  ${item.free_bo_no }, '${item.img_filename }' )" class="_btnDelFile" />
					</div>
				</td>
			</tr>
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

			<tr>
				<th>등록일자</th>
				<td>
					<input type=text value="<fmt:formatDate value="${freeBoard.write_date}" />" disabled />
				</td>
			</tr>
			<tr id="tr_btn_modify">
				<td colspan="2">
					<input type=button value="수정적용" onClick="fn_modify_freeBoard(frmFreeBoard)" class="_btnFreeBoModify">
					<input type=button value="취소" onClick="backToList(this.form)" class="_btnFreeBoCancel">
				</td>
			</tr>

			<tr id="tr_btn">
				<td colspan="2" align="center">
					<c:if test="${memberInfo.member_id == freeBoard.mem_id }">
						<input type=button value="수정하기" onClick="fn_enable(this.form)" class="_btnFreeBoUpdate">
						<input type=button value="삭제하기" onClick="fn_remove_freeBoard('${contextPath}/board/freeBo/removeFreeBoard.do', ${freeBoard.free_bo_no})" class="_btnFreeBoDelte">
					</c:if>
					<input type=button value="목록보기" onClick="backToList(this.form)" class="_btnFreeBoList">
					<input type=button value="답글쓰기" onClick="fn_reply_form('${isLogOn}', '${contextPath}/board/freeBo/replyForm.do', ${freeBoard.free_bo_no})" class="_btnFreeBoReply">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>