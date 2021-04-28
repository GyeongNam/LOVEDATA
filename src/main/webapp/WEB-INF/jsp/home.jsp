<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
<link href="\main\resources\static\css" rel="stylesheet">
	<title>Home</title>
</head>
<%@ include file="layout/header.jsp" %>
<body>

	<div class="container">
<h1>
	Hello world!
</h1>
	<div class="flex-container">
		<div class="class1">
			<P>  The time on the server is ${serverTime}. </P>
			<a href="/infoFind">아이디/비밀번호 찾기</a>
			<a href="/signup">회원가입</a>
		</div>
		<div class="class1">
			<sec:authorize access="isAuthenticated()">
				<form action="/logout" method="post">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<button type="submit">LOGOUT</button>
				</form>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<form action="/login" method="get">
						<%--					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
					<button type="submit">LOGIN</button>
				</form>
			</sec:authorize>
		</div>
		<div class="class1">
			<%--<a href="/login">로그인</a>--%>
			<a href="/find-info">회원정보 찾기</a>
			<a href="/find_id">회원정보 찾기 수정</a>
			<a href="/user/deleteAccount">회원탈퇴</a>
		</div>
	</div>
</div>
</body>
<%@ include file="layout/footer.jsp" %>
<!--  부트스트랩 js 사용 -->
<<<<<<< HEAD
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
</html>
=======
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
</html>
>>>>>>> 65dbe03f99dc3e7b5abf4ed82a774966c78c79b4
