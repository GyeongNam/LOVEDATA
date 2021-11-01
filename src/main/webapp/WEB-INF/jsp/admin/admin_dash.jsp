<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>
<jsp:useBean id="simpleDateTimeFormatter" class="com.project.love_data.util.SimpleLocalDateTimeFormatter"></jsp:useBean>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<%--	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">--%>
	<%--	<link rel="stylesheet" type="text/css" href="/css/Bootstarp_test/bootstrap.min.css">--%>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<link rel="stylesheet" href="/css/service/loc.css">
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Admin Dashboard</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body class="bg-light">
	<div class="container-fluid d-flex" style="padding-top: 100px">
		<div class="col-2" id="sidebar">
			<ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
				<div class="accordion text-center" id="loc">
					<hr>
					<div class="card">
						<div class="card-header" id="headingLoc">
							<h2 class="mb-0">
								<button class="btn btn-link btn-block loc_highlight-selected-nav-menu" type="button"
										data-toggle="collapse"
										data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne">
									어드민
								</button>
							</h2>
						</div>
						<div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
							<div class="card-body center-pill">
								<p><a href="/admin/dash" class="highlight-selected-text-menu">대시보드</a></p>
								<p><a href="/admin/user" class="highlight-not-selected-text-menu">유저 관리</a></p>
								<p><a href="/admin/dash" class="highlight-not-selected-text-menu">메시지 발송</a></p>
								<p><a href="/admin/dash" class="highlight-not-selected-text-menu">공지사항과 문의사항</a></p>
								<p><a href="/admin/dash" class="highlight-not-selected-text-menu">upload 파일 캐시 삭제</a></p>
<%--								<p><a href="/admin/loc_recommend" class="highlight-not-selected-text-menu">- 추천 장소(어드민)</a></p>--%>
<%--								<p><a href="/admin/cor_recommend" class="highlight-not-selected-text-menu">- 추천 코스(어드민)</a></p>--%>
								<p class="mb-0"><a href="" class="highlight-not-selected-text-menu">신고 센터</a></p>
							</div>
						</div>
					</div>
				</div>
			</ul>
		</div>

<%--	https://startbootstrap.com/previews/material-admin-pro	--%>
		<div class="container-xl p-5 ms-3">
			<div class="row justify-content-between align-items-center mb-5">
				<div class="col flex-shrink-0 mb-5 mb-md-0">
					<h1 class="display-4 mb-0">어드민 대시보드</h1>
<%--					<div class="text-muted">Sales overview &amp; summary</div>--%>
				</div>
				<div class="col-12 col-md-auto">
					<div class="d-flex flex-column flex-sm-row gap-3">
<%--						<mwc-select class="mw-50 mb-2 mb-md-0" outlined="" label="View by">--%>
<%--							<mwc-list-item selected="" value="0" mwc-list-item="" tabindex="0" aria-disabled="false" role="option" aria-selected="true">Order type</mwc-list-item>--%>
<%--							<mwc-list-item value="1" mwc-list-item="" tabindex="-1" aria-disabled="false" role="option">Segment</mwc-list-item>--%>
<%--							<mwc-list-item value="2" mwc-list-item="" tabindex="-1" aria-disabled="false" role="option">Customer</mwc-list-item>--%>
<%--						</mwc-select>--%>
<%--						<mwc-select class="mw-50" outlined="" label="Sales from">--%>
<%--							<mwc-list-item value="0" mwc-list-item="" tabindex="0" aria-disabled="false" role="option">Last 7 days</mwc-list-item>--%>
<%--							<mwc-list-item value="1" mwc-list-item="" tabindex="-1" aria-disabled="false" role="option">Last 30 days</mwc-list-item>--%>
<%--							<mwc-list-item value="2" mwc-list-item="" tabindex="-1" aria-disabled="false" role="option">Last month</mwc-list-item>--%>
<%--							<mwc-list-item selected="" value="3" mwc-list-item="" tabindex="-1" aria-disabled="false" role="option" aria-selected="true">Last year</mwc-list-item>--%>
<%--						</mwc-select>--%>
					</div>
				</div>
			</div>
			<div class="row d-flex justify-content-between">
				<div class="col-md-3 mb-5">
					<div class="card card-raised border-primary" style="border: 1px solid; border-left : 10px solid; border-radius: 10px">
						<div class="card-body px-4">
							<div class="card-text">
								<div class="row d-inline-flex align-items-center">
									<div class="h3">오늘 하루 게시글 수</div>
									<span>100</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3 mb-5">
					<div class="card card-raised border-success" style="border: 1px solid; border-left : 10px solid; border-radius: 10px">
						<div class="card-body px-4">
							<div class="card-text">
								<div class="row d-inline-flex align-items-center">
									<div class="h3">오늘 하루 댓글 수</div>
									<span>100</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3 mb-5">
					<div class="card card-raised border-danger" style="border: 1px solid; border-left : 10px solid; border-radius: 10px">
						<div class="card-body px-4">
							<div class="card-text">
								<div class="row d-inline-flex align-items-center">
									<div class="h3">오늘 하루 신고 수</div>
									<span>100</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row justify-content-md-start">
<%--				<ul class="nav nav-pills nav-fill col-5" id="pills-tab" role="tablist"--%>
<%--					style="height:50px; padding-top: 5px; padding-bottom: 5px">--%>
<%--					<li class="nav-item" role="presentation">--%>
<%--						<button class="nav-link active" id="recent-post-tab" data-bs-toggle="pill"--%>
<%--								data-bs-target="#recent-post" type="button" role="tab" aria-controls="recent-post-tab"--%>
<%--								aria-selected="true">최근 게시글</button>--%>
<%--					</li>--%>
<%--					<li class="nav-item" role="presentation">--%>
<%--						<button class="nav-link" id="hot-post-tab" data-bs-toggle="pill"--%>
<%--								data-bs-target="#hot-post" type="button" role="tab" aria-controls="hot-post-tab"--%>
<%--								aria-selected="false">핫한 게시글</button>--%>
<%--					</li>--%>
<%--				</ul>--%>
<%--				<div class="tab-content" id="pills-tabContent">--%>
<%--					<div class="tab-pane fade show active" id="recent-post" role="tabpanel" aria-labelledby="recent-post-tab">--%>
<%--						<span>test recent-post-tab</span>--%>
<%--						<div class="row my-3">--%>
<%--							<div class="col-8 d-flex justify-content-center align-items-md-center" id="searchResultArea">--%>
<%--								<table class="table text-center" id="searchResultTable">--%>
<%--									<thead>--%>
<%--										<th scope="col">#</th>--%>
<%--										<th scope="col">작성자</th>--%>
<%--										<th scope="col">제목</th>--%>
<%--										<th scope="col">--%>
<%--											<div class="dropdown">--%>
<%--												<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--													Dropdown button--%>
<%--												</button>--%>
<%--												<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">--%>
<%--													<a class="dropdown-item" href="#">Action</a>--%>
<%--													<a class="dropdown-item" href="#">Another action</a>--%>
<%--													<a class="dropdown-item" href="#">Something else here</a>--%>
<%--												</div>--%>
<%--											</div>--%>
<%--										</th>--%>
<%--										<th scope="col">등록일</th>--%>
<%--									</thead>--%>
<%--									<tbody id="tableBody">--%>
<%--										<tr onclick="location.href='/'" style="cursor:hand">--%>
<%--											<td>1</td>--%>
<%--											<td>닉네임</td>--%>
<%--											<td>광화문</td>--%>
<%--											<td>장소</td>--%>
<%--											<td>2021-10-21 19:12:00</td>--%>
<%--										</tr>--%>
<%--									</tbody>--%>
<%--								</table>--%>
<%--							</div>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="tab-pane fade" id="hot-post" role="tabpanel" aria-labelledby="hot-post-tab">--%>
<%--						<span>test hot-post-tab</span>--%>
<%--					</div>--%>
<%--				</div>--%>
				<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
					<li class="nav-item" role="presentation">
						<a class="nav-link active" id="recent-post-tab" data-toggle="pill" href="#recent-post" role="tab" aria-controls="recent-post" aria-selected="true">
							최근 장소, 코스
						</a>
					</li>
					<li class="nav-item" role="presentation">
						<a class="nav-link" id="hot-post-tab" data-toggle="pill" href="#hot-post" role="tab" aria-controls="hot-post" aria-selected="false">
							인기 장소, 코스
						</a>
					</li>
					<li class="nav-item" role="presentation">
						<a class="nav-link" id="recent-comrev-tab" data-toggle="pill" href="#recent-comrev" role="tab" aria-controls="recent-comrev" aria-selected="true">
							최근 댓글, 리뷰
						</a>
					</li>
					<li class="nav-item" role="presentation">
						<a class="nav-link" id="hot-comrev-tab" data-toggle="pill" href="#hot-comrev" role="tab" aria-controls="hot-comrev" aria-selected="false">
							인기 댓글, 리뷰
						</a>
					</li>
				</ul>
				<div class="tab-content" id="pills-tabContent">
					<div class="tab-pane fade show active" id="recent-post" role="tabpanel" aria-labelledby="recent-post-tab">
						<span>test recent-post-tab</span>
						<div class="row my-3">
							<div class="col-12 d-flex justify-content-center align-items-md-center">
								<table class="table text-center" id="recentLocCorTable">
									<thead>
									<th scope="col">#</th>
									<th scope="col">장소 번호</th>
									<th scope="col">작성자</th>
									<th scope="col">제목</th>
									<th scope="col">
										<div class="dropdown">
											<button class="btn btn-secondary dropdown-toggle" type="button" id="recentLocCorDrop" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												구분
											</button>
											<div class="dropdown-menu" aria-labelledby="recentLocCorDrop">
												<a class="dropdown-item">전체</a>
												<a class="dropdown-item">장소</a>
												<a class="dropdown-item">코스</a>
											</div>
										</div>
									</th>
									<th scope="col">추천수</th>
									<th scope="col">신고수</th>
									<th scope="col">등록일</th>
									</thead>
									<tbody id="recentLocCorTableBody">
									<c:choose>
										<c:when test="${!empty recentLocCorListIndex}">
											<c:forEach var="i" begin="0" end="${recentLocCorListIndex.size()-1}">
												<c:choose>
													<c:when test="${recentLocCorTypeList.get(i).equalsIgnoreCase('Loc')}">
														<tr onclick="location.href='/service/loc_detail?locNo=${recentLocList.get(recentLocCorListIndex.get(i)).loc_no}'"
														style="background: #ffdef2; cursor: pointer;">
															<td>${i+1}</td>
															<td>${recentLocList.get(recentLocCorListIndex.get(i)).loc_no}</td>
															<td>${recentLocUserNicList.get(recentLocCorListIndex.get(i))}</td>
															<td>${recentLocList.get(recentLocCorListIndex.get(i)).loc_name}</td>
															<td>장소</td>
															<td>${recentLocList.get(recentLocCorListIndex.get(i)).likeCount}</td>
															<td>Null</td>
															<td>${recentLocList.get(recentLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
													<c:when test="${recentLocCorTypeList.get(i).equalsIgnoreCase('Cor')}">
														<tr onclick="location.href='/service/cor_detail?corNo=${recentCorList.get(recentLocCorListIndex.get(i)).cor_no}'"
														style="background: #e2eeff; cursor:pointer;">
															<td>${i+1}</td>
															<td>${recentCorList.get(recentLocCorListIndex.get(i)).cor_no}</td>
															<td>${recentCorUserNicList.get(recentLocCorListIndex.get(i))}</td>
															<td>${recentCorList.get(recentLocCorListIndex.get(i)).cor_name}</td>
															<td>코스</td>
															<td>${recentCorList.get(recentLocCorListIndex.get(i)).likeCount}</td>
															<td>Null</td>
															<td>${recentCorList.get(recentLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<span>해당하는 정보가 없습니다.</span>
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="hot-post" role="tabpanel" aria-labelledby="hot-post-tab">
						<span>test hot-post-tab</span>
						<div class="row my-3">
							<div class="col-12 d-flex justify-content-center align-items-md-center">
								<table class="table text-center" id="hotLocCorTable">
									<thead>
									<th scope="col">#</th>
									<th scope="col">장소 번호</th>
									<th scope="col">작성자</th>
									<th scope="col">제목</th>
									<th scope="col">
										<div class="dropdown">
											<button class="btn btn-secondary dropdown-toggle" type="button" id="hotLocCorDrop" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												구분
											</button>
											<div class="dropdown-menu" aria-labelledby="hotLocCorDrop">
												<a class="dropdown-item">전체</a>
												<a class="dropdown-item">장소</a>
												<a class="dropdown-item">코스</a>
											</div>
										</div>
									</th>
									<th scope="col">추천수</th>
									<th scope="col">신고수</th>
									<th scope="col">등록일</th>
									</thead>
									<tbody id="hotLocCorTableBody">
									<c:choose>
										<c:when test="${!empty hotLocCorListIndex}">
											<c:forEach var="i" begin="0" end="${hotLocCorListIndex.size() - 1}">
												<c:choose>
													<c:when test="${hotLocCorTypeList.get(i).equalsIgnoreCase('Loc')}">
														<tr onclick="location.href='/service/loc_detail?locNo=${hotLocList.get(hotLocCorListIndex.get(i)).loc_no}'"
															style="background: #ffdef2; cursor: pointer;">
															<td>${i+1}</td>
															<td>${hotLocList.get(hotLocCorListIndex.get(i)).loc_no}</td>
															<td>${hotLocUserNicList.get(hotLocCorListIndex.get(i))}</td>
															<td>${hotLocList.get(hotLocCorListIndex.get(i)).loc_name}</td>
															<td>장소</td>
															<td>${hotLocList.get(hotLocCorListIndex.get(i)).likeCount}</td>
															<td>Null</td>
															<td>${hotLocList.get(hotLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
													<c:when test="${hotLocCorTypeList.get(i).equalsIgnoreCase('Cor')}">
														<tr onclick="location.href='/service/cor_detail?corNo=${hotCorList.get(hotLocCorListIndex.get(i)).cor_no}'"
															style="background: #e2eeff; cursor:pointer;">
															<td>${i+1}</td>
															<td>${hotCorList.get(hotLocCorListIndex.get(i)).cor_no}</td>
															<td>${hotCorUserNicList.get(hotLocCorListIndex.get(i))}</td>
															<td>${hotCorList.get(hotLocCorListIndex.get(i)).cor_name}</td>
															<td>코스</td>
															<td>${hotCorList.get(hotLocCorListIndex.get(i)).likeCount}</td>
															<td>Null</td>
															<td>${hotCorList.get(hotLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<span>해당하는 정보가 없습니다.</span>
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="recent-comrev" role="tabpanel" aria-labelledby="recent-comrev-tab">
						<span>test recent-revcom-tab</span>
					</div>
					<div class="tab-pane fade" id="hot-comrev" role="tabpanel" aria-labelledby="hot-comrev-tab">
						<span>test hot-revcom-tab</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!--  부트스트랩 js 사용 -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<%--slim 사용시 ajax 미지원--%>
<%-- https://song8420.tistory.com/236 --%>
<%--<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js"--%>
<%--		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"--%>
<%--		crossorigin="anonymous"></script>--%>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<%--<script defer src="/js/bootstrap.js"></script>--%>
</html>
