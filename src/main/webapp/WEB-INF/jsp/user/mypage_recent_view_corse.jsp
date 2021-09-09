<%@ page import="org.springframework.security.core.annotation.AuthenticationPrincipal" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/mypage.css" rel="stylesheet">
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
	<sec:authorize access="isAnonymous()">
		잘못된 방식으로 접근하였습니다. 로그인 후 다시 시도하여주세요!
		<button id="hd-btn" onclick="location.href='/login'">로그인</button>
		<button id="hd-btn" class="dropbtn" onclick="gohome()">홈으로 돌아가기</button>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
<%--<form class="mypageform" action="/user/mypage" method="post">--%>
<div id="jb-container">
	<div id="jb-header">
		<h1>마이페이지</h1>
	</div>
	<div id="jb-sidebar">
		<div class="tab">
			<button class="tablinks" onclick="MenuTab(event, 'Myinfo')" id="defaultOpen">내 정보</button>
			<button class="tablinks" onclick="MenuTab(event, 'MyReview')">나의 리뷰</button>
			<button class="tablinks" onclick="MenuTab(event, 'MyPlace')">나의 코스/장소 <ul><li>나의코스</li><li>나의 장소</li></ul></button>
			<button class="tablinks" onclick="MenuTab(event, 'LikeList')">찜 목록</button>
			<button class="tablinks" onclick="MenuTab(event, 'RecView')">최근 본 코스</button>
		</div>
	</div>
	<div id="jb-content">
		<div id="RecView" class="tabcontent">
			<h3>최근 본 코스</h3>
			<table>
				<thead>
				<tr>
					<th>No.</th>
					<th>등록 날짜</th>
					<th>제목</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>1</td>
					<td><span>2022-01-01</span></td>
					<td>인생 맛집 투어</td>
				</tr>
				<tr>
					<td>2</td>
					<td><span>2021-12-01</span></td>
					<td>누가 그 집에 가보았는가</td>
				</tr>
				<tr>
					<td>3</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>4</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>5</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>6</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>7</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>8</td>
					<td></td>
					<td></td>
				</tr>
				</tbody>
			</table>
			* 최근 본 8개 장소만 표시됩니다.
		</div>
	</div>
</div>
</sec:authorize>
<%--</form>--%>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/mypage.js"></script>
</html>