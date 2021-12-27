<%@ page import="org.springframework.security.core.annotation.AuthenticationPrincipal" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>
<jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>
<jsp:useBean id="simpleDateTimeFormatter" class="com.project.love_data.util.SimpleLocalDateTimeFormatter"></jsp:useBean>

<html>
<head>
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
    <link href="/css/mypage.css" rel="stylesheet">
<%--	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">--%>
<%--	<link rel="stylesheet" href=" https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">--%>
	<link rel="stylesheet" href=" https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap5.min.css"/>
	<link rel="stylesheet" href=" https://cdn.datatables.net/rowgroup/1.1.4/css/rowGroup.bootstrap5.min.css"/>
	<style>
		@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

		body {
			font-family: 'Jua', sans-serif;
		}
	</style>
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
			<div>
				<p>
					<a href="/mypage" >내 정보</a>
				</p>
				<p>
					<a href="/mypage_point" >내 포인트</a>
				</p>
				<p>
					<a href="/mypage_event" >이벤트 참여</a>
				</p>
			</div>
			<div>
				<button class="accordion">나의댓글/리뷰</button>
				<div class="panel">
					<p>
						<a href="/mypage_mycomment/1" >나의 댓글</a>
					</p>
					<p>
						<a href="/mypage_myreview/1" >나의 리뷰</a>
					</p>
				</div>
			</div>
			<div>
				<div>
					<button class="accordion">나의코스/장소</button>
					<div class="panel">
						<p>
							<a href="/mypage_mycorse/1" >나의 코스</a>
						</p>
						<p>
							<a href="/mypage_myplace/1" >나의 장소</a>
						</p>
					</div>
				</div>
			</div>
			<div>
				<div>
					<button class="accordion">나의 찜 목록</button>
					<div class="panel">
						<p>
							<a href="/mypage_mylike/1" >내가 찜한 장소</a>
						</p>
						<p>
							<a href="/mypage_myCorlike/1" >내가 찜한 코스</a>
						</p>
					</div>
				</div>
			</div>
			<div>
				<button class="accordion">최근 본 장소/코스</button>
				<div class="panel">
					<p>
						<a href="/mypage_recent_view_location" >최근 본 장소</a>
					</p>
					<p>
						<a href="/mypage_recent_view_corse" >최근 본 코스</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<div id="jb-content">
		<div id="MyCorse" class="tabcontent">
			<h3>참여 이벤트: </h3>
			<table class="table-bordered table text-center tables" >
				<thead>
				<th scope="col">이벤트 번호</th>
				<th scope="col">이벤트 이름</th>
				<th scope="col">시작일</th>
				<th scope="col">종료일</th>
				<th scope="col">추첨일</th>
				<th scope="col">나의 참여횟수</th>
				</thead>
				<tbody>
				<c:forEach var="even" varStatus="index" items="${eve}">
					<tr onclick="location.href='/ServiceCenter/Event_Post/${even.ev_no}';">
						<td>${even.ev_no}</td>
						<td>${even.ev_title}</td>
						<td>${even.ev_start}</td>
						<td>${even.ev_stop}</td>
						<td>${even.ev_end}</td>
						<td>${eveattend.get(index.count-1)}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
</div>
</sec:authorize>
<%--</form>--%>
</body>

<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/ServiceCenter.js"></script>
<script defer src="/js/mypage.js"></script>


<script defer src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<script defer src="https://cdn.datatables.net/1.11.3/js/dataTables.jqueryui.min.js"></script>
<script defer src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
<script defer src="https://cdn.datatables.net/rowgroup/1.1.4/js/dataTables.rowGroup.min.js"></script>
<script defer src="/js/datatable.js"></script>
</html>