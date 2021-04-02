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
<h1>
	Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="/infoFind">아이디/비밀번호 찾기</a>
<a href="/signup">회원가입</a>
<a href="/login">로그인</a>
<a href="test_jsp">테스트_jsp</a>
<p>
	<a href="https://velog.io/@ghd64845/Spring-boot-MyBatis%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-MySQL-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0#7-sql-mapper-%EC%83%9D%EC%84%B1%ED%95%98%EA%B8%B0">참고 URL</a>
	<a href="/db_test">db_테스트_sql</a>
	<a href="/users">db_테스트(DB의 값 불러오는 지)</a>
	<a href="/users?country=냥국">db_테스트(DB에서 select가 되는 지)</a>
</p>
<%@ include file="layout/footer.jsp" %>
</body>
</html>
