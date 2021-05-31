<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<div class="container-fluid d-flex">
	<div class="col-2" id="sidebar">
		<ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
			<div class="accordion text-center" id="loc">
				<hr>
				<div class="card">
					<div class="card-header" id="headingLoc">
						<h2 class="mb-0">
							<button class="btn btn-link btn-block" type="button" data-toggle="collapse"
									data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne"
									style="text-decoration: none; color: #FF6699; font-weight: bold">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/loc_recommend" class="loc_highlight-selected-text-menu">- 추천 장소</a></p>
							<p><a href="/service/loc_registration" class="loc_highlight-not-selected-text-menu">- 장소
								등록</a></p>
							<p><a href="#" class="loc_highlight-not-selected-text-menu">- 장소 편집</a></p>
						</div>
					</div>
				</div>
			</div>
			<%--            <div class="accordion text-center" id="course">--%>
			<%--                <div class="card">--%>
			<%--                    <div class="card-header" id="headingCourse">--%>
			<%--                        <h2 class="mb-0">--%>
			<%--                            <form action="/" method="get" class="form-label">--%>
			<%--                                <button type="submit" class="btn btn-link btn-block"--%>
			<%--                                        style="text-decoration: none; color: #9448C3">코스--%>
			<%--                                </button>--%>
			<%--                            </form>--%>
			<%--                        </h2>--%>
			<%--                    </div>--%>
			<%--                </div>--%>
			<%--            </div>--%>
			<%--            <div class="accordion text-center" id="calendar">--%>
			<%--                <div class="card">--%>
			<%--                    <div class="card-header" id="headingCalendar">--%>
			<%--                        <h2 class="mb-0">--%>
			<%--                            <form action="/" method="get" class="form-label">--%>
			<%--                                <button type="submit" class="btn btn-link btn-block"--%>
			<%--                                        style="text-decoration: none; color: #9448C3">캘린더--%>
			<%--                                </button>--%>
			<%--                            </form>--%>
			<%--                        </h2>--%>
			<%--                    </div>--%>
			<%--                </div>--%>
			<%--                <hr>--%>
			<%--            </div>--%>
		</ul>
	</div>
	<div class="container m-5" id="display_center" style="margin-right: 30px; margin-top: 30px">
		<div class="row justify-content-md-center">
			<div class="col-md-7">
				<div class="card mb-4 shadow-sm">
					<c:set var="imgList" value="${dto.imgList}"></c:set>
					<c:choose>
						<c:when test="${!empty imgList}">
							<div class="d-flex justify-content-center">
								<img class="bd-placeholder-img card-img-top d-flex justify-content-center" width="100%"
									 height="400"
									 alt="${dto.loc_name}"
									 src="${dto.imgList.get(0).img_url}"
									 id="imgDisplay"
									 name="imgDisplay"
									 preserveAspectRatio="xMidYMid slice" focusable="false">
								<div class="d-flex justify-content-between h-25 card-img-overlay" style="top: 40%">
									<button class="btn btn-sm" id="imgPrev" name="imgPrev" onclick="clickImgPrev()">
										<img src="/image/icon/left-arrow.png" width="30px" height="30px" alt="imgPrev">
									</button>
									<button class="btn btn-sm" id="imgNext" name="imgNext" onclick="clickImgNext()">
										<img src="/image/icon/right-arrow.png" width="30px" height="30px" alt="imgNext">
									</button>
										<%--									<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor"--%>
										<%--										 class="bi bi-arrow-right-circle" viewBox="0 0 100 100">--%>
										<%--										<path fill-rule="evenodd"--%>
										<%--											  d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>--%>
										<%--									</svg>--%>
								</div>
								<div class="d-flex justify-content-end h-25 card-img-overlay" style="top : 75%">
									<div class="badge bg-dark d-flex justify-content-center" style="width: 6rem">
										<p class="text-center fs-3 align-self-center mb-0" id="indexIndicator" name="indexIndicator">
											1/${imgList.size()}
										</p>
									</div>
								</div>
								</img>
								<span class="visually-hidden" id="imgListSize" name="imgListSize">${imgList.size()}</span>
								<span class="visually-hidden" id="dtoImgListSize" name="dtoImgListSize">${dto.imgList.size()}</span>
								<span class="visually-hidden" id="imgListIndex" name="imgListIndex">0</span>
								<intpu type="hidden" id="imgList" name="imgList" value="${dto.imgList}"></intpu>
							</div>
						</c:when>
						<c:otherwise>
							<svg class="bd-placeholder-img card-img-top" width="100%" height="400"
								 xmlns="http://www.w3.org/2000/svg" role="img"
								 aria-label="Placeholder: Thumbnail"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
								<title>Placeholder</title>
								<rect width="100%" height="100%" fill="#55595c"></rect>
								<text x="40%" y="50%" fill="#eceeef" dy=".3em">${dto.loc_name}</text>
							</svg>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-md-5 justify-content-md-center">
				<div class="row d-flex">
					<div class="row d-flex justify-content-between p-1">
						<span class="h1 col-5">${dto.loc_name}</span>
						<button class="btn btn-outline-danger col-2" style="max-height: 56px" onclick="copyURL()">공유</button>
					</div>
					<div class="row d-flex">
						<h3>지역 : ${dto.siDo} ${dto.siGunGu}</h3>
					</div>
					<div class="row d-flex">
						<h3>해시태그 : ${dto.tagSet}</h3>
					</div>
					<div class="row d-flex">
						<h3 class="text-truncate">설명 : ${dto.info}</h3>
					</div>
				</div>
				<div class="row d-flex">
				</div>

				<div class="d-flex align-content-end flex-wrap">
					<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기"
						 onclick="onClickLike(this)">
					<span class="text-center align-middle fs-3 me-4" id="likeCount">${dto.likeCount}</span>
					<img src="/image/icon/comment.png" class="loc_icon_big me-2" alt="댓글">
					<span class="text-center align-middle fs-3 me-4">${dto.cmdSet.size()}</span>
				</div>

				<span class="d-none" id="loc_no">${dto.loc_no}</span>
			</div>
		</div>
		<div class="row justify-content-md-start">
			<ul class="nav nav-pills nav-fill col-5" id="pills-tab" role="tablist"
				style="height:50px; padding-top: 5px; padding-bottom: 5px">
				<li class="nav-item" role="presentation">
					<button class="mw-100 mh-100 nav-link active" id="location-comment-tab" data-bs-toggle="pill"
							data-bs-target="#location-comment" type="button" role="tab" aria-controls="location-comment"
							aria-selected="true">댓글
					</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="location-info-tab" data-bs-toggle="pill"
							data-bs-target="#location-info" type="button" role="tab" aria-controls="location-info"
							aria-selected="false">설명
					</button>
				</li>
			</ul>
			<div class="tab-content" id="pills-tabContent">
				<%--      댓글--%>
				<div class="tab-pane fade show active" id="location-comment" role="tabpanel"
					 aria-labelledby="location-comment-tab">
					<div class="container mt-0">
						<div class="d-flex justify-content-start row">
							<div class="col-md-10">
								<div class="d-flex flex-column">
									<div class="bg-white p-2">
										<div class="d-flex flex-row align-items-center"><img
												src="/image/icon/user/user.png"
												class="loc_comment-profile-image-wh">
											<div class="flex-column">
                                        <span class="d-block font-weight-bold name">
                                            유저 닉네임
                                            <button class="btn btn-primary">수정</button>
                                            <button class="btn btn-primary">삭제</button>
                                        </span>
												<span class="date text-black-50 ml-5">(2021/05/02 00:39)</span>
											</div>
										</div>
										<div class="mt-2">
											<p class="comment-text">Lorem ipsum dolor sit amet, consectetur adipiscing
												elit, sed
												do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
												ad minim
												veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
												commodo
												consequat.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="container d-flex" id="pagination">
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
									<%--                          Todo  댓글 어떻게 화면 전환 없이 가져올 수 있을지 생각해보기        --%>
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
					<%--                    Todo 추후에 comment 섹션을 안에다 넣으면, 로그인 한 사람만 댓글 작성 가능--%>
					<%--                    <sec:authorize access="isAuthenticated()">--%>
					<%--                        --%>
					<%--                    </sec:authorize>--%>
					<div class="d-flex justify-content-start" id="comment">
						<div class="bg-light p-2 col-10">
							<div class="d-flex flex-row align-items-start">
								<img class="rounded-circle m-3" src="https://i.imgur.com/RpzrMR2.jpg" width="60">
								<textarea class="form-control ml-1 shadow-none textarea"></textarea>
							</div>
							<div class="mt-2 text-end">
								<button class="btn btn-primary btn shadow-none" type="button">Post comment</button>
							</div>
						</div>
					</div>
				</div>
				<%--    설명--%>
				<div class="tab-pane fade" id="location-info" role="tabpanel" aria-labelledby="location-info-tab">
					<div class="container">
						<div class="d-flex mt-3">
							<span class="h3">${dto.info}</span>
						</div>
					</div>
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
<script defer src="/js/bootstrap.js"></script>
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>a
<script defer>
    function clickImgNext() {
        var imgDisplay = document.getElementById("imgDisplay");
        var index = document.getElementById("imgListIndex");
        var temp = '<c:out value="${dto.printImgURLS()}"/>';
        var imgList = temp.split('_');
        var tempIndex = parseInt(index.innerText);
        var indexIndicator = document.getElementById("indexIndicator");

        if (imgList.length > tempIndex + 1) {
            index.innerText = tempIndex + 1;
            tempIndex += 1;
            imgDisplay.src = imgList[tempIndex];
            indexIndicator.innerText = (tempIndex+1) + "/" + imgList.length;
        }
    }

    function clickImgPrev() {
        var imgDisplay = document.getElementById("imgDisplay");
        var index = document.getElementById("imgListIndex");
        var temp = '<c:out value="${dto.printImgURLS()}"/>';
        var imgList = temp.split('_');
        var tempIndex = parseInt(index.innerText);
        var indexIndicator = document.getElementById("indexIndicator");

        if (0 <= tempIndex - 1) {
            index.innerText = tempIndex - 1;
            tempIndex -= 1;
            imgDisplay.src = imgList[tempIndex];
            indexIndicator.innerText = (tempIndex+1) + "/" + imgList.length;
        }
    }
</script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
