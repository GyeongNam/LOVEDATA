<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
<link href="\main\resources\static\css" rel="stylesheet">
	<title>Home</title>
</head>
<body>
<%@ include file="layout/header.jsp" %>
	<div class="container">
<h1>
	Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="/infoFind">아이디/비밀번호 찾기</a>
<a href="/signup">회원가입</a>
<a href="/login">로그인</a>
<<<<<<< HEAD
=======
<a href="/find-info">회원정보 찾기</a>
<a href="test_jsp">테스트_jsp</a>
>>>>>>> origin/dorumamu
<p>

</p>
</div>
<%@ include file="layout/footer.jsp" %>
</body>
</html>
