<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<!--------CSS-------->
	<link href="/css/header.css" rel="stylesheet">
	<meta charset="UTF-8">
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<title>Insert title here</title>
</head>
<body>
<div class="header" class="fixed-top">
	<div id="hd-logo">
		<a id="hd-name" href="/">LOVEDATA</a>
	</div>
	<div id="hd-btn-box">
		<button id="hd-btn" onclick="location.href='/login'">로그인</button>
		<button id="hd-btn" class="dropbtn" onclick="location.href='/mypage'">마이 페이지</button>
		<button id="hd-btn" onclick="location.href='/service/loc_recommend'">장소</button>
		<button id="hd-btn" href="#">코스</button>
		<button id="hd-btn" onclick="location.href='/service/calender'">캘린더</button>
	</div>
</div>
</body>
</html>
<!--------JS-------->
<script defer src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script defer type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<%--@Todo header.js 나중에 추가--%>
<%--<script defer src="/js/header.js"></script>--%>
