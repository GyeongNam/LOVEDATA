<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<!--------CSS-------->
<link href="/css/header.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="header" class="fixed-top">
			<div id="hd-logo">
				<a id="hd-name" href="/">LOVEDATA</a>
			</div>
			<div id="hd-btn-box">
				<button id="hd-btn" href="#">로그인</button>
				<div class="dropdown">
					<button id="hd-btn" class="dropbtn" href="#">마이 페이지</button>
					<div class="dropdown-content">
						<a href="#">내 정보</a>
						<a href="#">나의 리뷰</a>
						<a href="#">나의 코스</a>
						<a href="#">찜 목록</a>
						<a href="#">최근 본 코스</a>
					</div>
				</div>
				<div class="dropdown">
					<button id="hd-btn" href="#">보기</button>
					<div class="dropdown-content">
						<a href="#">장소 보기</a>
						<a href="#">코스 보기</a>
						<a href="#">캘린더</a>
						<a href="#">이벤트</a>
					</div>
				</div>
				<button id="hd-btn" href="#">코스 만들기</button>
			</div>
		</div>
</body>
</html>
<!--------JS-------->
<script defer src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script defer type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script defer src="/js/header.js"></script>
