<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
HttpSession se = request.getSession();
out.print(se.getAttribute("userid"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AOP에서 생성된 Session 값을 출력</title>
</head>
<body>
고객명 [${mname }]님 하이용 
<a href="./testaop_logout.do">[로그아웃]</a>
<br><br>
<%=se.getAttribute("useremail") %>
</body>
</html>