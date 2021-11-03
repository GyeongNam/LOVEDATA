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
									<div class="h5">오늘 장소 & 코스 등록 수</div>
									<span>${todayLocCorCount}</span>
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
									<div class="h5">오늘 댓글 & 리뷰 등록 수</div>
									<span>${todayComRevCount}</span>
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
									<div class="h5">오늘 하루 신고 수</div>
									<span>100</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row justify-content-md-start">
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
						<div class="row my-3">
							<div class="col-12 d-flex justify-content-center align-items-md-center">
								<table class="table text-center" id="recentLocCorTable">
									<thead>
									<th scope="col">#</th>
									<th scope="col">ID</th>
									<th scope="col">작성자</th>
									<th scope="col">제목</th>
									<th scope="col">
										<div class="dropdown">
											<button id="recentLocCorDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												전체
											</button>
											<div class="dropdown-menu" aria-labelledby="recentLocCorDropDownBtn">
												<a class="dropdown-item" onclick="changeToTotalRecentTable()">전체</a>
												<a class="dropdown-item" onclick="changeToLocOnlyRecentTable()">장소</a>
												<a class="dropdown-item" onclick="changeToCorOnlyRecentTable()">코스</a>
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
<%--															Todo 추후 신고 추가되면 추가하기--%>
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
<%--															Todo 추후 신고 추가되면 추가하기--%>
															<td>Null</td>
															<td>${recentCorList.get(recentLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
<%--											<span>해당하는 정보가 없습니다.</span>--%>
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="hot-post" role="tabpanel" aria-labelledby="hot-post-tab">
						<div class="row my-3">
							<div class="col-12 d-flex justify-content-center align-items-md-center">
								<table class="table text-center" id="hotLocCorTable">
									<thead>
									<th scope="col">#</th>
									<th scope="col">ID</th>
									<th scope="col">작성자</th>
									<th scope="col">제목</th>
									<th scope="col">
										<div class="dropdown">
											<button id="hotLocCorDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												전체
											</button>
											<div class="dropdown-menu" aria-labelledby="hotLocCorDropDownBtn">
												<a class="dropdown-item" onclick="changeToTotalHotTable()">전체 </a>
												<a class="dropdown-item" onclick="changeToLocOnlyHotTable()">장소 </a>
												<a class="dropdown-item" onclick="changeToCorOnlyHotTable()">코스 </a>
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
<%--															Todo 추후 신고 추가되면 추가하기--%>
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
<%--													Todo 추후 신고 추가되면 추가하기		--%>
															<td>Null</td>
															<td>${hotCorList.get(hotLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
<%--											<span>해당하는 정보가 없습니다.</span>--%>
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="recent-comrev" role="tabpanel" aria-labelledby="recent-comrev-tab">
						<div class="row my-3">
							<div class="col-12 d-flex justify-content-center align-items-md-center">
								<table class="table text-center" id="recentComRevTable">
									<thead>
									<th scope="col">#</th>
									<th scope="col">ID</th>
									<th scope="col">작성자</th>
									<th scope="col">
										<div class="dropdown">
											<button id="recentComRevDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												전체
											</button>
											<div class="dropdown-menu" aria-labelledby="recentComRevDropDownBtn">
												<a class="dropdown-item" onclick="">전체</a>
												<a class="dropdown-item" onclick="">댓글</a>
												<a class="dropdown-item" onclick="">리뷰</a>
											</div>
										</div>
									</th>
									<th scope="col">추천수</th>
									<th scope="col">신고수</th>
									<th scope="col">등록일</th>
									</thead>
									<tbody id="recentComRevTableBody">
									<c:choose>
										<c:when test="${!empty recentComRevListIndex}">
											<c:forEach var="i" begin="0" end="${recentComRevListIndex.size()-1}">
												<c:choose>
													<c:when test="${recentComRevTypeList.get(i).equalsIgnoreCase('Com')}">
														<tr onclick="location.href='/service/loc_detail?locNo=${recentComList.get(recentComRevListIndex.get(i)).location.loc_no}&page=${recentComRevPageNum.get(i)}'"
															style="background: #ffdef2; cursor: pointer;">
															<td>${i+1}</td>
															<td>${recentComList.get(recentComRevListIndex.get(i)).cmtNo}</td>
															<td>${recentComUserNicList.get(recentComRevListIndex.get(i))}</td>
															<td>댓글</td>
															<td>${recentComList.get(recentComRevListIndex.get(i)).likeCount}</td>
																<%--															Todo 추후 신고 추가되면 추가하기--%>
															<td>Null</td>
															<td>${recentComList.get(recentComRevListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
													<c:when test="${recentComRevTypeList.get(i).equalsIgnoreCase('Rev')}">
														<tr onclick="location.href='/service/cor_detail?corNo=${recentRevList.get(recentComRevListIndex.get(i)).corNo}&page=${recentComRevPageNum.get(i)}'"
															style="background: #e2eeff; cursor:pointer;">
															<td>${i+1}</td>
															<td>${recentRevList.get(recentComRevListIndex.get(i)).revNo}</td>
															<td>${recentRevUserNicList.get(recentComRevListIndex.get(i))}</td>
															<td>리뷰</td>
															<td>${recentRevList.get(recentComRevListIndex.get(i)).rev_like}</td>
																<%--															Todo 추후 신고 추가되면 추가하기--%>
															<td>Null</td>
															<td>${recentRevList.get(recentComRevListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<%--											<span>해당하는 정보가 없습니다.</span>--%>
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="hot-comrev" role="tabpanel" aria-labelledby="hot-comrev-tab">
						<div class="row my-3">
							<div class="col-12 d-flex justify-content-center align-items-md-center">
								<table class="table text-center" id="hotComRevTable">
									<thead>
									<th scope="col">#</th>
									<th scope="col">ID</th>
									<th scope="col">작성자</th>
									<th scope="col">
										<div class="dropdown">
											<button id="hotComRevDropDownBtn" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
												전체
											</button>
											<div class="dropdown-menu" aria-labelledby="hotComRevDropDownBtn">
												<a class="dropdown-item" onclick="">전체</a>
												<a class="dropdown-item" onclick="">댓글</a>
												<a class="dropdown-item" onclick="">리뷰</a>
											</div>
										</div>
									</th>
									<th scope="col">추천수</th>
									<th scope="col">신고수</th>
									<th scope="col">등록일</th>
									</thead>
									<tbody id="hotComRevTableBody">
									<c:choose>
										<c:when test="${!empty hotComRevListIndex}">
											<c:forEach var="i" begin="0" end="${hotComRevListIndex.size()-1}">
												<c:choose>
													<c:when test="${hotComRevTypeList.get(i).equalsIgnoreCase('Com')}">
														<tr onclick="location.href='/service/loc_detail?locNo=${hotComList.get(hotComRevListIndex.get(i)).location.loc_no}&page=${hotComRevPageNum.get(i)}'"
															style="background: #ffdef2; cursor: pointer;">
															<td>${i+1}</td>
															<td>${hotComList.get(hotComRevListIndex.get(i)).cmtNo}</td>
															<td>${hotComUserNicList.get(hotComRevListIndex.get(i))}</td>
															<td>댓글</td>
															<td>${hotComList.get(hotComRevListIndex.get(i)).likeCount}</td>
																<%--															Todo 추후 신고 추가되면 추가하기--%>
															<td>Null</td>
															<td>${hotComList.get(hotComRevListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
													<c:when test="${hotComRevTypeList.get(i).equalsIgnoreCase('Rev')}">
														<tr onclick="location.href='/service/cor_detail?corNo=${hotRevList.get(hotComRevListIndex.get(i)).corNo}&page=${hotComRevPageNum.get(i)}'"
															style="background: #e2eeff; cursor:pointer;">
															<td>${i+1}</td>
															<td>${hotRevList.get(hotComRevListIndex.get(i)).revNo}</td>
															<td>${hotRevUserNicList.get(hotComRevListIndex.get(i))}</td>
															<td>리뷰</td>
															<td>${hotRevList.get(hotComRevListIndex.get(i)).rev_like}</td>
																<%--															Todo 추후 신고 추가되면 추가하기--%>
															<td>Null</td>
															<td>${hotRevList.get(hotComRevListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</td>
														</tr>
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<%--											<span>해당하는 정보가 없습니다.</span>--%>
										</c:otherwise>
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
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
<script defer>
	let tableColCount = 8;
	let tempHolder = [];

	let recentTotalHolder = [];
    let recentTotalType = [];
    let recentLocHolder = [];
    let recentCorHolder = [];

    let hotTotalHolder = [];
    let hotTotalType = [];
    let hotLocHolder = [];
    let hotCorHolder = [];

    let recentLocCorTableBody = document.getElementById("recentLocCorTableBody");
	let hotLocCorTableBody = document.getElementById("hotLocCorTableBody");

    <c:set var="tableColCount" value="8"/>
   <c:choose>
		<c:when test="${!empty recentLocCorListIndex}">
			<c:forEach var="i" begin="0" end="${recentLocCorListIndex.size() - 1}">
				<c:choose>
					<c:when test="${recentLocCorTypeList.get(i).equalsIgnoreCase('Loc')}">
						tempHolder.push('${recentLocList.get(recentLocCorListIndex.get(i)).loc_no}');
						tempHolder.push('${recentLocUserNicList.get(recentLocCorListIndex.get(i))}');
						tempHolder.push('${recentLocList.get(recentLocCorListIndex.get(i)).loc_name}');
						tempHolder.push('장소');
						tempHolder.push('${recentLocList.get(recentLocCorListIndex.get(i)).likeCount}');
						// TODO 신고수 (추후 값 추가되면 그 때 넣기)
						tempHolder.push('Null');
						tempHolder.push('${recentLocList.get(recentLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}');
                        recentTotalHolder.push(tempHolder);
                        tempHolder = [];
                        recentTotalType.push('Loc');
					</c:when>
					<c:when test="${recentLocCorTypeList.get(i).equalsIgnoreCase('Cor')}">
						tempHolder.push('${recentCorList.get(recentLocCorListIndex.get(i)).cor_no}');
						tempHolder.push('${recentCorUserNicList.get(recentLocCorListIndex.get(i))}');
						tempHolder.push('${recentCorList.get(recentLocCorListIndex.get(i)).cor_name}');
						tempHolder.push('코스');
						tempHolder.push('${recentCorList.get(recentLocCorListIndex.get(i)).likeCount}');
						// TODO 신고수 (추후 값 추가되면 그 때 넣기)
						tempHolder.push('Null');
						tempHolder.push('${recentCorList.get(recentLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}');
                        recentTotalHolder.push(tempHolder);
                        tempHolder = [];
    					recentTotalType.push('Cor');
					</c:when>
				</c:choose>
			</c:forEach>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${!empty recentLocList}">
			<c:forEach var="i" begin="0" end="${recentLocList.size() - 1}">
				tempHolder.push('${recentLocList.get(i).loc_no}');
				tempHolder.push('${recentLocUserNicList.get(i)}');
				tempHolder.push('${recentLocList.get(i).loc_name}');
				tempHolder.push('장소');
				tempHolder.push('${recentLocList.get(i).likeCount}');
				// TODO 신고수 (추후 값 추가되면 그 때 넣기)
				tempHolder.push('Null');
				tempHolder.push('${recentLocList.get(i).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}');
				recentLocHolder.push(tempHolder);
				tempHolder = [];
			</c:forEach>
		</c:when>
	</c:choose>
	<c:choose>
    	<c:when test="${!empty recentCorList}">
			<c:forEach var="i" begin="0" end="${recentCorList.size() - 1}">
				tempHolder.push('${recentCorList.get(i).cor_no}');
				tempHolder.push('${recentCorUserNicList.get(i)}');
				tempHolder.push('${recentCorList.get(i).cor_name}');
				tempHolder.push('코스');
				tempHolder.push('${recentCorList.get(i).likeCount}');
				// TODO 신고수 (추후 값 추가되면 그 때 넣기)
				tempHolder.push('Null');
				tempHolder.push('${recentCorList.get(i).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}');
				recentCorHolder.push(tempHolder);
				tempHolder = [];
			</c:forEach>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${!empty hotLocCorListIndex}">
			<c:forEach var="i" begin="0" end="${hotLocCorListIndex.size() - 1}">
				<c:choose>
					<c:when test="${hotLocCorTypeList.get(i).equalsIgnoreCase('Loc')}">
						tempHolder.push('${hotLocList.get(hotLocCorListIndex.get(i)).loc_no}');
						tempHolder.push('${hotLocUserNicList.get(hotLocCorListIndex.get(i))}');
						tempHolder.push('${hotLocList.get(hotLocCorListIndex.get(i)).loc_name}');
						tempHolder.push('장소');
						tempHolder.push('${hotLocList.get(hotLocCorListIndex.get(i)).likeCount}');
						// TODO 신고수 (추후 값 추가되면 그 때 넣기)
						tempHolder.push('Null');
						tempHolder.push('${hotLocList.get(hotLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}');
						hotTotalHolder.push(tempHolder);
						tempHolder = [];
						hotTotalType.push('Loc');
					</c:when>
					<c:when test="${hotLocCorTypeList.get(i).equalsIgnoreCase('Cor')}">
						tempHolder.push('${hotCorList.get(hotLocCorListIndex.get(i)).cor_no}');
						tempHolder.push('${hotCorUserNicList.get(hotLocCorListIndex.get(i))}');
						tempHolder.push('${hotCorList.get(hotLocCorListIndex.get(i)).cor_name}');
						tempHolder.push('코스');
						tempHolder.push('${hotCorList.get(hotLocCorListIndex.get(i)).likeCount}');
						// TODO 신고수 (추후 값 추가되면 그 때 넣기)
						tempHolder.push('Null');
						tempHolder.push('${hotCorList.get(hotLocCorListIndex.get(i)).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}');
						hotTotalHolder.push(tempHolder);
						tempHolder = [];
						hotTotalType.push('Cor');
					</c:when>
    			</c:choose>
			</c:forEach>
		</c:when>
	</c:choose>
    <c:choose>
		<c:when test="${!empty hotLocList}">
			<c:forEach var="i" begin="0" end="${hotLocList.size() - 1}">
				tempHolder.push('${hotLocList.get(i).loc_no}');
				tempHolder.push('${hotLocUserNicList.get(i)}');
				tempHolder.push('${hotLocList.get(i).loc_name}');
				tempHolder.push('장소');
				tempHolder.push('${hotLocList.get(i).likeCount}');
				// TODO 신고수 (추후 값 추가되면 그 때 넣기)
				tempHolder.push('Null');
				tempHolder.push('${hotLocList.get(i).regDate}');
				hotLocHolder.push(tempHolder);
				tempHolder = [];
			</c:forEach>
		</c:when>
    </c:choose>
    <c:choose>
		<c:when test="${!empty hotCorList}">
			<c:forEach var="i" begin="0" end="${hotCorList.size() - 1}">
			tempHolder.push('${hotCorList.get(i).cor_no}');
			tempHolder.push('${hotCorUserNicList.get(i)}');
			tempHolder.push('${hotCorList.get(i).cor_name}');
			tempHolder.push('코스');
			tempHolder.push('${hotCorList.get(i).likeCount}');
			// TODO 신고수 (추후 값 추가되면 그 때 넣기)
			tempHolder.push('Null');
			tempHolder.push('${hotCorList.get(i).regDate}');
			hotCorHolder.push(tempHolder);
			tempHolder = [];
			</c:forEach>
		</c:when>
    </c:choose>

	function changeToTotalRecentTable() {
        deleteRecentLocCorTableRow();

        let rows = [];
        let temp = [];
        let cells = [];

        for (let i = 0; i < recentTotalHolder.length; i++) {
            rows.push(recentLocCorTableBody.insertRow());
            for (let j = 0; j < tableColCount; j++) {
                temp.push(rows[i].insertCell());
            }
            cells.push(temp);
            temp = [];
        }

        for (let i = 0; i < recentTotalHolder.length; i++) {
            if (recentTotalType[i] === 'Loc') {
                rows[i].onclick = function() {
                    window.location = '/service/loc_detail?locNo=' + recentTotalHolder[i][0];
                }
                rows[i].style.background = "#ffdef2";
            }
            else if (recentTotalType[i] === 'Cor') {
                rows[i].onclick = function() {
                    location.href = '/service/cor_detail?corNo=' + recentTotalHolder[i][0];
                }
                rows[i].style.background = "#e2eeff";
            }
            rows[i].style.cursor = "hand";

            cells[i][0].innerHTML = i+1;
            for (let j = 0; j < tableColCount - 1; j++) {
                cells[i][j+1].innerHTML = recentTotalHolder[i][j];
            }
        }

        document.getElementById("recentLocCorDropDownBtn").innerHTML = "전체 ";
	}

    function changeToLocOnlyRecentTable() {
        deleteRecentLocCorTableRow();

        let rows = [];
        let temp = [];
        let cells = [];

        for (let i = 0; i < recentCorHolder.length; i++) {
            rows.push(recentLocCorTableBody.insertRow());
            for (let j = 0; j < tableColCount; j++) {
                temp.push(rows[i].insertCell());
            }
            cells.push(temp);
            temp = [];
        }

        for (let i = 0; i < recentCorHolder.length; i++) {
            rows[i].onclick = function() {
                window.location = '/service/loc_detail?locNo=' + recentLocHolder[i][0];
            }
            rows[i].style.background = "#ffdef2";
            rows[i].style.cursor = "hand";

            cells[i][0].innerHTML = i+1;
            for (let j = 0; j < tableColCount - 1; j++) {
                cells[i][j+1].innerHTML = recentLocHolder[i][j];
            }
        }

        document.getElementById("recentLocCorDropDownBtn").innerHTML = "장소 ";
	}

    function changeToCorOnlyRecentTable() {
        deleteRecentLocCorTableRow();

        let rows = [];
        let temp = [];
        let cells = [];

        for (let i = 0; i < recentCorHolder.length; i++) {
            rows.push(recentLocCorTableBody.insertRow());
            for (let j = 0; j < tableColCount; j++) {
                temp.push(rows[i].insertCell());
            }
            cells.push(temp);
            temp = [];
        }

        for (let i = 0; i < recentCorHolder.length; i++) {
            rows[i].onclick = function() {
                location.href = '/service/cor_detail?corNo=' + recentCorHolder[i][0];
            }
            rows[i].style.background = "#e2eeff";
            rows[i].style.cursor = "hand";

            cells[i][0].innerHTML = i+1;
            for (let j = 0; j < tableColCount - 1; j++) {
                cells[i][j+1].innerHTML = recentCorHolder[i][j];
            }
        }

        document.getElementById("recentLocCorDropDownBtn").innerHTML = "코스 ";
	}

    function deleteRecentLocCorTableRow() {
        while(recentLocCorTableBody.rows.length > 0) {
            recentLocCorTableBody.deleteRow(recentLocCorTableBody.rows.length - 1);
        }
	}

    function changeToTotalHotTable() {
        deleteHotLocCorTableRow();

        let rows = [];
        let temp = [];
        let cells = [];

        for (let i = 0; i < hotTotalHolder.length; i++) {
            rows.push(hotLocCorTableBody.insertRow());
            for (let j = 0; j < tableColCount; j++) {
                temp.push(rows[i].insertCell());
            }
            cells.push(temp);
            temp = [];
        }

        for (let i = 0; i < hotTotalHolder.length; i++) {
            if (hotTotalType[i] === 'Loc') {
                rows[i].onclick = function() {
                    window.location = '/service/loc_detail?locNo=' + hotTotalHolder[i][0];
                }
                rows[i].style.background = "#ffdef2";
            }
            else if (hotTotalType[i] === 'Cor') {
                rows[i].onclick = function() {
                    location.href = '/service/cor_detail?corNo=' + hotTotalHolder[i][0];
                }
                rows[i].style.background = "#e2eeff";
            }
            rows[i].style.cursor = "hand";

            cells[i][0].innerHTML = i+1;
            for (let j = 0; j < tableColCount - 1; j++) {
                cells[i][j+1].innerHTML = hotTotalHolder[i][j];
            }
        }

        document.getElementById("hotLocCorDropDownBtn").innerHTML = "전체 ";
    }

    function changeToLocOnlyHotTable() {
        deleteHotLocCorTableRow();

        let rows = [];
        let temp = [];
        let cells = [];

        for (let i = 0; i < hotCorHolder.length; i++) {
            rows.push(hotLocCorTableBody.insertRow());
            for (let j = 0; j < tableColCount; j++) {
                temp.push(rows[i].insertCell());
            }
            cells.push(temp);
            temp = [];
        }

        for (let i = 0; i < hotCorHolder.length; i++) {
            rows[i].onclick = function() {
                window.location = '/service/loc_detail?locNo=' + hotLocHolder[i][0];
            }
            rows[i].style.background = "#ffdef2";
            rows[i].style.cursor = "hand";

            cells[i][0].innerHTML = i+1;
            for (let j = 0; j < tableColCount - 1; j++) {
                cells[i][j+1].innerHTML = hotLocHolder[i][j];
            }
        }

        document.getElementById("hotLocCorDropDownBtn").innerHTML = "장소 ";
    }

    function changeToCorOnlyHotTable() {
        deleteHotLocCorTableRow();

        let rows = [];
        let temp = [];
        let cells = [];

        for (let i = 0; i < hotCorHolder.length; i++) {
            rows.push(hotLocCorTableBody.insertRow());
            for (let j = 0; j < tableColCount; j++) {
                temp.push(rows[i].insertCell());
            }
            cells.push(temp);
            temp = [];
        }

        for (let i = 0; i < hotCorHolder.length; i++) {
            rows[i].onclick = function() {
                location.href = '/service/cor_detail?corNo=' + hotCorHolder[i][0];
            }
            rows[i].style.background = "#e2eeff";
            rows[i].style.cursor = "hand";

            cells[i][0].innerHTML = i+1;
            for (let j = 0; j < tableColCount - 1; j++) {
                cells[i][j+1].innerHTML = hotCorHolder[i][j];
            }
        }

        document.getElementById("hotLocCorDropDownBtn").innerHTML = "코스 ";
    }

    function deleteHotLocCorTableRow() {
        while(hotLocCorTableBody.rows.length > 0) {
            hotLocCorTableBody.deleteRow(hotLocCorTableBody.rows.length - 1);
        }
    }
</script>
</html>
