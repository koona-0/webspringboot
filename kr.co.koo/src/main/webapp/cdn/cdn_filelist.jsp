<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date"%>
<% Date date = new Date();%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cdn 서버 이미지 리스트 목록</title>
<link rel="stylesheet" href="../css/bootstrap.css?v=<%=date%>">
<script src="../js/bootstrap.js?v=<%=date%>"></script>
</head>
<body>

<form id="frm" method="post" action="./cdn_delete.do">
<table class="table table-bordered">
<thead>
<tr align="center">
<th><input type="checkbox"></th>
<th>이미지</th>
<th>사용자 파일명</th>
<th>개발자 파일명</th>
<th>api 파일명</th>
</tr>
</thead>
<tbody id="ls">
<cr:forEach var="fdata" items="${all}">
<tr align="center">
<td><input type="checkbox" name="cbox" class="ck" value="${fdata.AIDX }"></td>
<td><img src="http://localhost:8080/cdn/image/${fdata.API_FILE }" style="width:100px;"></td>
<td>${fdata.ORI_FILE }</td>
<!-- 
해당 파일을 클릭시 새탭이 오픈되어 보임
<td><a href="${fdata.FILE_URL }" target="_new"> ${fdata.NEW_FILE }</a></td>
 -->
<!-- 방법1  
 <td><a href="./download.do?filenm=${fdata.FILE_URL }"> ${fdata.NEW_FILE }</a></td>
 --> 
 <!-- 방법2 
 -->
 <td><a href="./download.do/${fdata.NEW_FILE }"> ${fdata.NEW_FILE }</a></td>
 <!-- 방법2-2
 	download 속성은 상대경로에서는 발동하지만 절대경로에서는 안됨 아래는 절대경로라서 다운이 안됨
 					http, https 불가능, 단 로컬호스트에서 /abc.jpg 와 download=abc.jap 이런식이면 가능  
 <td><a href="${fdata.FILE_URL }" download="${fdata.NEW_FILE }"> ${fdata.NEW_FILE }</a></td>
 -->
<td>${fdata.API_FILE }</td>
</tr>
</cr:forEach>
</tbody>
</table>
</form>
<br>

<!-- input은 기본으로 submit 기능이 있음 -->
<form id="search" method="get" action="./cdn_filelist.do">
<div class="input-group mb-3">
  <input type="text" name="word" class="form-control" placeholder="검색할 파일명을 입력해주세요">
  <button class="btn btn-outline-secondary" type="button" id="sh_btn" >검색</button>
  <button class="btn btn-outline-secondary" type="button" id="sh_btn2" >전체목록</button>
</div>
</form>





<button type="button" id="btn" class="btn btn-babypink">선택삭제</button>
</body>
<script type="module">
import {cdn_lists} from "./cdn.js";
document.querySelector("#btn").addEventListener("click",function(){
   new cdn_lists().cdn_listdel();
});

document.querySelector("#sh_btn").addEventListener("click",function(){	//서브밋이면 "submit"하고 펑션안에 뭐 넣어서 기본값 잡아줘야함
	new  cdn_lists().cdn_search();
});

document.querySelector("#sh_btn2").addEventListener("click",function(){	//서브밋이면 "submit"하고 펑션안에 뭐 넣어서 기본값 잡아줘야함
	location.href='./cdn_filelist.do';
});

</script>
</html>






