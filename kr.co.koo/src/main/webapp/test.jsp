<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Date dt = new Date();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bootstrap 회원가입 + Spring-boot</title>
<link rel="stylesheet" href="./css/bootstrap.css?v=<%=dt%>">
<script src="./js/bootstrap.js?v=<%=dt%>"></script>
</head>
<body>
<%-- 
<%=dt%>

<br>
<input type="button" class="btn btn-secondary" value="로그인">

<br>

bootstrap.css외의 파일은 수정 불가능
0.5rem = 0.7px 
클래스명으로 css파일에서 검색해서 수정할 부분 수정 
클래스를 추가해도됨 
form-control클래스에 width1을 추가하려면
form-control width1 
이렇게 공백으로 이어주면 적용됨

<div class="mb-3">
  <label for="exampleFormControlInput1" class="form-label">아이디</label>
  <input type="text" class="form-control width1" placeholder="아이디를 입력하세요">
</div>
 --%>

<%-- 
부트스트랩은 반응형임 알아서 크기 조절됨 
----- --%>

	<form id="frm" method="post" action="./join.do">
	<input type="hidden" name="MCODE" value="1">
	<input type="hidden" name="MJOIN" value="WEB">
	
	
		<div class="input-group mb-3 width1">
			<span class="input-group-text">아이디</span> 
			<input type="text" name="MID" class="form-control" placeholder="아이디를 입력해주세요">
		</div>

		<div class="input-group mb-3 width1">
			<span class="input-group-text">패스워드</span> 
			<input type="password" name="MPASS" class="form-control" placeholder="패스워드를 입력해주세요">
		</div>
		
		<div class="input-group mb-3 width1">
			<span class="input-group-text">고객명</span> 
			<input type="text" name="MNAME" class="form-control" placeholder="고객명을 입력해주세요">
		</div>
		
		<div class="input-group mb-3 width1">
			<span class="input-group-text">닉네임</span> 
			<input type="text" name="MNICK" class="form-control" placeholder="닉네임을 입력해주세요">
		</div>
		
		<div class="input-group mb-3 width1">
			<span class="input-group-text">이메일</span> 
			<input type="text" name="MEMAIL" class="form-control" placeholder="이메일을 입력해주세요">
		</div>
		
		<div class="input-group mb-3 width1">
			<span class="input-group-text">연락처</span> 
			<input type="text" name="MHP" class="form-control" placeholder="'-'를 제외한 숫자만 입력해주세요">
		</div>

		<button type="button" id="btn" class="btn btn-babypink width1">회원가입</button>
	
	</form>

<script type="module">
import {memberjoin} from "./test.js?v=<%=dt%>"
</script>

</body>
</html>
















