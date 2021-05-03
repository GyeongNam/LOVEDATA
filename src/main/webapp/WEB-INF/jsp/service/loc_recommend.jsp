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
	<%--	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">--%>
	<%--	<link rel="stylesheet" type="text/css" href="/css/Bootstarp_test/bootstrap.min.css">--%>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>

<div class="container-fluid d-flex align-items-center">
	<div class="col-2" id="sidebar">
		<ul class="nav nav-pills flex-column align-middle">
			<div class="accordion text-center" id="loc">
				<hr>
				<div class="card">
					<div class="card-header" id="headingLoc">
						<h2 class="mb-0">
							<button class="btn btn-link btn-block" type="button" data-toggle="collapse"
									data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne" style="text-decoration: none; color: #FF6699; font-weight: bold">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/loc_recommend" style="text-decoration: none; color: #ff5f5f; font-weight: bold">- 추천 장소</a></p>
							<p><a href="#" style="text-decoration: none; color: #BB8BD9">- 장소 등록/편집</a></p>
						</div>
					</div>
				</div>
			</div>
			<div class="accordion text-center" id="course">
				<div class="card">
					<div class="card-header" id="headingCourse">
						<h2 class="mb-0">
							<form action="/" method="get" class="form-label">
								<button type="submit" class="btn btn-link btn-block" style="text-decoration: none; color: #9448C3">코스</button>
							</form>
						</h2>
					</div>
				</div>
			</div>
			<div class="accordion text-center" id="calendar">
				<div class="card">
					<div class="card-header" id="headingCalendar">
						<h2 class="mb-0">
							<form action="/" method="get" class="form-label">
								<button type="submit" class="btn btn-link btn-block" style="text-decoration: none; color: #9448C3">캘린더</button>
							</form>
						</h2>
					</div>
				</div>
				<hr>
			</div>
		</ul>
	</div>
	<div class="container-fluid" id="display_center" style="margin-right: 30px">
		<div class="col" id="top_navbar">
			<nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				</button> <a class="navbar-brand text-dark" href="/">추천장소</a>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="navbar-nav col-9">
						<li class="nav-item dropdown">
							<%--                            @Todo 드롭다운 선택시 동적으로 화면 전환--%>
							<button class="nav-link dropdown-toggle" role="button" id="navbarDropdownMenuLink" data-toggle="dropdown" value="mostViewed">
								조회순
							</button>
							<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
								<button class="dropdown-item" value="mostViewed" onclick="changeSort(this)">조회순</button>
								<button class="dropdown-item" value="mostLiked" onclick="changeSort(this)">추천순</button>
								<button class="dropdown-item" value="mostRecent" onclick="changeSort(this)">최근</button>
								<button class="dropdown-item" value="mostOldest" onclick="changeSort(this)">가장 오래전</button>
							</div>
						</li>
					</ul>
<%--					@Todo 인풋 값이 ""일 경우 버튼 활성화 x--%>
					<form action="/service/locRoc_searchValue" method="get">
						<input type="text" placeholder="장소 검색" id="text" name="text"/>
						<button class="btn btn-primary" type="submit">Search</button>
					</form>
				</div>
			</nav>
		</div>
		<div class="col" id="top_hashtag">
			<nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
				<div class="collapse navbar-collapse" id="tag-navbar-collapse">
					<ul class="navbar-nav">
						<li class="nav-item dropdown">
							<button class="nav-link dropdown-toggle" role="button" id="tagDropdownMenuLink" data-toggle="dropdown">해시태그</button>
							<%--                            @Todo 자바스크립트 써서 동적으로 해시태그 추가하는 것 구현하기--%>
							<%--                            https://www.w3schools.com/jsref/event_onclick.asp--%>
							<div class="dropdown-menu" aria-labelledby="tagDropdownMenuLink">
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action A">Action A</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action B">Action B</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action C">Action C</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action D">Action D</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action E">Action E</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action F">Action F</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action G">Action G</button>
								<button type="button" class="dropdown-item" onclick="addTag(this)" value="Action H">Action H</button>
							</div>
						</li>
					</ul>
					<div id="tag_list">
<%--						@Todo display:inline으로 변경할때마다 빈공간 생기는 문제 수정하기--%>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그1</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그2</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그3</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그4</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그5</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그6</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
						<div class="btn-group mx-2 my-0" role="group" style="display: none">
							<button type="button" class="btn btn-primary" value="">태그7</button>
							<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeTag(this)">X</button>
						</div>
					</div>
				</div>
			</nav>
		</div>
		<div class="row justify-content-md-center">
			<div class="col-md-4">
				<div class="card mb-4 shadow-sm">
					<form action="/service/loc_detail" method="get" class="m-0">
						<button class="container p-0 btn">
							<svg class="bd-placeholder-img card-img-top" width="100%" height="225"
								 xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
								<title>Placeholder</title>
								<rect width="100%" height="100%" fill="#55595c"></rect><text x="40%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
							</svg>
						</button>
					</form>

					<div class="card-body p-2">
						<div class="d-flex justify-content-between align-items-center p-1">
							<a class="card-text text-dark" href="/service/loc_detail" id="title_1">제목1</a>
							<img src="/image/icon/like/love_black.png" width="30px" height="30px" alt="찜하기" onclick="onClickLike(this)">
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="card mb-4 shadow-sm">
					<form action="/service/loc_detail" method="get" class="m-0">
						<button class="container p-0 btn">
							<svg class="bd-placeholder-img card-img-top" width="100%" height="225"
								 xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
								<title>Placeholder</title>
								<rect width="100%" height="100%" fill="#55595c"></rect><text x="40%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
							</svg>
						</button>
					</form>

					<div class="card-body p-2">
						<div class="d-flex justify-content-between align-items-center p-1">
							<a class="card-text text-dark" href="/service/loc_detail" id="title_2">제목2</a>
							<img src="/image/icon/like/love_black.png" width="30px" height="30px" alt="찜하기" onclick="onClickLike(this)">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row justify-content-md-center">
			<div class="col-md-4">
				<div class="card mb-4 shadow-sm">
					<form action="/service/loc_detail" method="get" class="m-0">
						<button class="container p-0 btn">
							<svg class="bd-placeholder-img card-img-top" width="100%" height="225"
								 xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
								<title>Placeholder</title>
								<rect width="100%" height="100%" fill="#55595c"></rect><text x="40%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
							</svg>
						</button>
					</form>

					<div class="card-body p-2">
						<div class="d-flex justify-content-between align-items-center p-1">
							<a class="card-text text-dark" href="/service/loc_detail" id="title_3">제목3</a>
							<img src="/image/icon/like/love_black.png" width="30px" height="30px" alt="찜하기" onclick="onClickLike(this)">
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="card mb-4 shadow-sm">
					<form action="/service/loc_detail" method="get" class="m-0">
						<button class="container p-0 btn">
							<svg class="bd-placeholder-img card-img-top" width="100%" height="225"
								 xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
								<title>Placeholder</title>
								<rect width="100%" height="100%" fill="#55595c"></rect><text x="40%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
							</svg>
						</button>
					</form>

					<div class="card-body p-2">
						<div class="d-flex justify-content-between align-items-center p-1">
							<a class="card-text text-dark" href="/service/loc_detail" id="title_4">제목4</a>
							<img src="/image/icon/like/love_black.png" width="30px" height="30px" alt="찜하기" onclick="onClickLike(this)">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container d-flex" id="">
			<div class="col" id="page_number">
				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a class="page-link" href="#" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
							</a>
						</li>
						<li class="page-item">
							<a class="page-link" href="#" aria-label="Previous">
								<span aria-hidden="true">&lt;</span>
							</a>
						</li>
						<li class="page-item disabled"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">4</a></li>
						<li class="page-item"><a class="page-link" href="#">5</a></li>
						<li class="page-item">
							<a class="page-link" href="#" aria-label="Previous">
								<span aria-hidden="true">&gt;</span>
							</a>
						</li>
						<li class="page-item">
							<a class="page-link" href="#" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</div>

<!--  부트스트랩 js 사용 -->
<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<script src="/js/loc_recommend.js"></script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
