<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spring-boot에서 라이브러리 없이 FormData 전송</title>
</head>
<body>

<input type="checkbox" id="all">전체선택<br>
<input type="checkbox" class="pd" value="pd1">상품1<br>
<input type="checkbox" class="pd" value="pd2">상품2<br>
<input type="checkbox" class="pd" value="pd3">상품3<br>
<input type="checkbox" class="pd" value="pd4">상품4<br>
<input type="checkbox" class="pd" value="pd5">상품5<br>
<input type="button" value="전송" onclick="ajax()">

</body>

<script src="./ajax.js?v=1"></script>

</html>