<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	<style>
		@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

		body {
			font-family: 'Jua', sans-serif;
		}
	</style>
</head>
<body>
<div id="header" class="deactive" class="fixed-top">
	<div id="hd-logo">
		<img id="logo-icon"src="/image/icon/hartdot.png">
		<a id="hd-name" href="/">LOVEDATA</a>
	</div>
	<div id="hd-btn-box">
		<sec:authorize access="isAnonymous()">
			<button id="hd-btn" onclick="location.href='/login'">로그인</button>
			<button id="hd-btn" class="dropbtn" onclick="gologin()">마이 페이지</button>
			<button id="hd-btn" onclick="gologin()">캘린더</button>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<c:set var="user_social"><sec:authentication property="principal.user_social"/></c:set>
			<c:choose>
				<%--					소셜 로그인으로 로그인 되어 있을 경우--%>
				<c:when test="${user_social eq true}">
					<c:set var="social_info"><sec:authentication property="principal.social_info"/></c:set>

					<c:choose>
						<%--						소셜 로그인을 카카오로 했을 경우--%>
						<c:when test="${social_info eq 'kakao'}">
							<button id="hd-btn" onclick="location.href='/logout_kakao'">로그아웃</button>
						</c:when>

						<%--						소셜 로그인을 네이버로 했을 경우--%>
						<c:when test="${social_info eq 'naver'}">
							<button id="hd-btn" onclick="location.href='/logout'">로그아웃</button>
						</c:when>
					</c:choose>
				</c:when>

				<%--					일반 로그인으로 로그인 되어 있을 경우--%>
				<c:when test="${user_social ne true}">
					<button id="hd-btn" onclick="location.href='/logout'">로그아웃</button>
				</c:when>
			</c:choose>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<button id="hd-btn" class="dropbtn" onclick="location.href='/admin/dash'">어드민</button>
			</sec:authorize>
			<button id="hd-btn" class="dropbtn" onclick="location.href='/mypage'">마이 페이지</button>
			<button id="hd-btn" onclick="location.href='/service/calender'">캘린더</button>
		</sec:authorize>

		<button id="hd-btn" onclick="location.href='/service/loc_recommend'">장소</button>
		<button id="hd-btn" onclick="location.href='/service/cor_recommend'">코스</button>

	</div>
</div>
</body>
</html>
<!--------JS-------->
<script defer src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script defer type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<%--@Todo header.js 나중에 추가--%>
<script defer src="/js/header.js"></script>
