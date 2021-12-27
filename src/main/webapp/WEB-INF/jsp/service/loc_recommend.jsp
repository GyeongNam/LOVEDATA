<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

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
	<title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>

<div class="container-fluid d-flex" style="padding-top: 100px">
	<div class="col-2" id="sidebar">
		<ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
			<div class="accordion text-center" id="loc">
				<hr>
				<div class="card">
					<div class="card-header" id="headingLoc">
						<h2 class="mb-0">
							<button class="btn btn-link btn-block loc_highlight-selected-nav-menu" type="button">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/loc_recommend" class="highlight-selected-text-menu">- 추천 장소</a></p>
							<p><a href="/service/loc_district_map" class="highlight-not-selected-text-menu">- 지역별 장소</a></p>
							<p><a href="/service/loc_registration" class="highlight-not-selected-text-menu">- 장소
								등록</a></p>
							<p><a href="/mypage_myplace/1" class="highlight-not-selected-text-menu">- 장소
								편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>
	<div class="container-fluid" id="display_center" style="margin-right: 30px">
		<div class="col" id="top_navbar">
			<nav class="navbar navbar-expand-sm static-top">
				<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#bs-example-navbar-collapse-1">
				</button>
				<a class="navbar-brand text-dark">추천장소</a>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="navbar-nav col-9">
						<li class="nav-item dropdown">
							<button class="nav-link dropdown-toggle" role="button" id="navbarDropdownMenuLink"
									data-toggle="dropdown" value="mostViewed">
								조회순
							</button>
							<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
								<button class="dropdown-item" value="mostViewed" onclick="changeSort(this)">조회순</button>
								<button class="dropdown-item" value="mostRecent" onclick="changeSort(this)">최신 등록순
								</button>
								<button class="dropdown-item" value="mostOldest" onclick="changeSort(this)">오래된 등록순
								</button>
								<button class="dropdown-item" value="mostLiked" onclick="changeSort(this)">좋아요 순
								</button>
							</div>
						</li>
					</ul>
					<%--					@Todo 인풋 값이 ""일 경우 버튼 활성화 x--%>
<%--					<form action="/service/loc_recommend/search" method="get">--%>
						<input type="text" placeholder="장소 검색" id="keyword" name="keyword"/>
						<button class="btn btn-primary mx-2" type="button" id="searchBtn" onclick="onClickSearch()">Search</button>
<%--					</form>--%>
				</div>
			</nav>
		</div>
		<div class="col" id="top_hashtag">
			<nav class="navbar navbar-expand-sm static-top">
				<div class="collapse navbar-collapse" id="tag-navbar-collapse">
					<ul class="navbar-nav">
						<li class="nav-item dropdown">
							<button class="nav-link dropdown-toggle" role="button" id="districtDropdownMenuLink"
									data-toggle="dropdown">전국
							</button>
							<%--                            https://www.w3schools.com/jsref/event_onclick.asp--%>
							<div class="dropdown-menu" aria-labelledby="districtDropdownMenuLink">
								<c:forEach var="i" begin="0" end="${korDistrict.size()-1}">
									<button type="button" class="dropdown-item" onclick="changeActiveDistrict(this)" value="${korDistrict.get(i).name()}">
											${korDistrict.get(i).name()}
									</button>
								</c:forEach>
							</div>
						</li>
					</ul>
					<ul class="navbar-nav">
						<li class="nav-item dropdown">
							<button class="nav-link dropdown-toggle" role="button" id="tagDropdownMenuLink"
									data-toggle="dropdown">해시태그
							</button>
							<%--                            https://www.w3schools.com/jsref/event_onclick.asp--%>
							<div class="dropdown-menu" aria-labelledby="tagDropdownMenuLink">
								<div class="container">
									<div class="row">
									<c:forEach var="i" begin="0" end="${tagList.size()-1}">
										<button type="button" class="dropdown-item" onclick="addTag(this)" value="${tagList.get(i).name()}">
												${tagList.get(i).name()}
										</button>
									</c:forEach>
									</div>
								</div>
							</div>
						</li>
					</ul>
					<div id="tag_list">
						<%--						@Todo display:inline으로 변경할때마다 빈공간 생기는 문제 수정하기--%>
						<c:forEach var="i" begin="0" end="${tagList.size()-1}">
							<div class="btn-group ms-4 my-0" role="group" style="display: none">
								<button type="button" class="btn btn-primary" value="${tagList.get(i)}">${tagList.get(i)}</button>
								<button type="button" class="btn btn-outline-danger btn" onclick="removeTag(this)">X</button>
							</div>
						</c:forEach>
					</div>
				</div>
			</nav>
		</div>
		<div class="row justify-content-md-center">
			<c:choose>
				<c:when test="${result.end ne 0}">
					<c:forEach var="i" begin="0" end="${result.dtoList.size()-1}">
						<c:if test="${i eq 2}">
							<div class="w-100"></div>
						</c:if>
						<div class="col-md-4" id="loc_${i}">
							<div class="card mb-4 shadow-sm">
								<c:url var="loc_detail" value="/service/loc_detail">
									<c:param name="locNo" value="${result.dtoList.get(i).loc_no}"/>
								</c:url>
								<a class="container p-0 btn" href="${loc_detail}">
									<c:set var="imgList" value="${result.dtoList.get(i).imgList}"></c:set>
									<c:choose>
										<c:when test="${!empty imgList}">
											<img class="bd-placeholder-img card-img-top" width="100%" height="225"
												 alt="${result.dtoList.get(i).loc_name}"
												 src="${result.dtoList.get(i).thumbnail}"
												 preserveAspectRatio="xMidYMid slice" focusable="false">
										</c:when>
										<c:otherwise>
											<svg class="bd-placeholder-img card-img-top" width="100%" height="225"
												 xmlns="http://www.w3.org/2000/svg" role="img"
												 aria-label="Placeholder: Thumbnail"
												 preserveAspectRatio="xMidYMid slice" focusable="false">
												<title>Placeholder</title>
												<rect width="100%" height="100%" fill="#55595c"></rect>
												<text x="40%" y="50%" fill="#eceeef"
													  dy=".3em">${result.dtoList.get(i).loc_name}</text>
											</svg>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${result.dtoList.get(i)._deleted eq true}">
											<div class="d-flex justify-content-end card-img-overlay" style="outline: dotted 3px red">
												<img class="bd-placeholder-img" width="30" height="30"
													 alt="삭제된 장소 아이콘"
													 src="/image/icon/trash.png"
													 preserveAspectRatio="xMidYMid slice" focusable="false">
											</div>
										</c:when>
									</c:choose>
								</a>

								<div class="card-body p-2">
									<div class="d-flex justify-content-between align-items-center p-1">
										<div class="d-flex">
											<a class="card-text loc_rec-locTitle" href="${loc_detail}"
											   id="title_${i+0}">${result.dtoList.get(i).loc_name}</a>
										</div>
										<div class="d-flex align-items-center">
											<img src="/image/icon/view.png" class="loc_icon" alt="조회수">
											<span class="align-middle">${result.dtoList.get(i).viewCount}</span>
											<img src="/image/icon/comment.png" class="loc_icon" alt="댓글">
												<%--									Todo 댓글 항목 Location Entity에 추가하기--%>
											<span class="align-middle">${result.dtoList.get(i).liveCmtCount}</span>
											<sec:authorize access="isAuthenticated()">
												<c:choose>
													<c:when test="${isLikedList.get(i) eq true}">
														<img src="/image/icon/like/love_color.png" class="loc_icon" alt="찜하기"
															 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'loc')">
													</c:when>
													<c:otherwise>
														<img src="/image/icon/like/love_black.png" class="loc_icon" alt="찜하기"
															 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'loc')">
													</c:otherwise>
												</c:choose>
											</sec:authorize>
											<sec:authorize access="isAnonymous()">
												<img src="/image/icon/like/love_black.png" class="loc_icon" alt="찜하기">
											</sec:authorize>
											<span class="align-middle" id="loc_like_count" name="loc_like_count">${result.dtoList.get(i).likeCount}</span>
											<span class="d-none">${result.dtoList.get(i).loc_no}</span>
											<span class="d-none">${result.dtoList.get(i).loc_uuid}</span>
<%--											<sec:authorize access="isAuthenticated()">--%>
<%--												<c:set var="user_no"><sec:authentication property="principal.user_no"></sec:authentication></c:set>--%>
<%--												<span class="d-none">${user_no}</span>--%>
<%--											</sec:authorize>--%>
<%--											<sec:authorize access="isAnonymous()">--%>
<%--												<span class="d-none">-1</span>--%>
<%--											</sec:authorize>--%>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<h1 class="text-center">등록된 정보가 없습니다.</h1>
				</c:otherwise>
			</c:choose>

		</div>

		<%--	PageNumber	--%>
		<div class="container d-flex" id="">
			<div class="col" id="page_number">
				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<c:if test="${result.next eq true}">
							<li class="page-item">
								<a class="page-link" href="/service/loc_recommend/list?page=${result.start - 1}"
								   aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
						</c:if>
						<c:forEach var="j" begin="${result.start}" end="${result.end}">
						<c:choose>
						<c:when test="${result.page eq j}">
						<li class="page-item active">
							<a class="page-link"
							   href="/service/loc_recommend/list?page=${result.pageList.get(j-1)}">${result.pageList.get(j-1)}</a>
							</c:when>
							<c:otherwise>
						<li class="page-item">
							<a class="page-link"
							   href="/service/loc_recommend/list?page=${result.pageList.get(j-1)}">${result.pageList.get(j-1)}</a>
							</c:otherwise>
							</c:choose>
							</c:forEach>
						</li>
						<c:if test="${result.next eq true}">
							<li class="page-item">
								<a class="page-link" href="/service/loc_recommend/list?page=${result.end + 1}"
								   aria-label="Previous">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-header">
					<h5 class="modal-title">Modal Title</h5>
					<button type="button" class="close" data-dimiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Modal body text goes here.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-secondary">Save changes</button>
				</div>
			</div>
		</div>
	</div>
</div>

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
<script defer src="/js/loc_recommend.js"></script>
<script defer src="/js/loc_common.js"></script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
