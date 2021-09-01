<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
							<button class="btn btn-link btn-block" type="button" data-toggle="collapse"
									data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne"
									style="text-decoration: none; color: #FF6699; font-weight: bold">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/loc_recommend" class="highlight-selected-text-menu">- 추천 장소</a></p>
							<p><a href="/service/loc_registration" class="highlight-not-selected-text-menu">- 장소
								등록</a></p>
							<p><a href="#" class="highlight-not-selected-text-menu">- 장소 편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>
	<c:choose>
<%--		없는 장소 번호로 조회시--%>
		<c:when test="${isNullLocation eq true}">
			<span>해당 장소가 존재하지 않습니다.</span>
			<%
				if( true ) return;
			%>
		</c:when>
		<c:when test="${dto._deleted eq true}">
			<sec:authorize access="isAnonymous()">
				<span>현재 페이지는 삭제되었습니다.</span>
				<%
					if( true ) return;
				%>
			</sec:authorize>
			<sec:authorize access="hasRole('USER')">
				<span>현재 페이지는 삭제되었습니다.</span>
				<%
					if( true ) return;
				%>
			</sec:authorize>
		</c:when>
	</c:choose>
	<div class="container m-5" id="display_center" style="margin-right: 30px; margin-top: 30px">
		<div class="row justify-content-md-center">
			<div class="col-md-7">
				<div class="card mb-4 shadow-sm">
					<c:set var="imgList" value="${dto.imgList}"></c:set>
					<c:choose>
						<c:when test="${!empty imgList}">
							<div class="d-flex justify-content-center">
								<img class="bd-placeholder-img card-img" width="100%"
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
										<p class="text-center fs-3 align-self-center mb-0" id="indexIndicator"
										   name="indexIndicator">
											1/${imgList.size()}
										</p>
									</div>
								</div>
								</img>
								<span class="visually-hidden" id="imgListSize"
									  name="imgListSize">${imgList.size()}</span>
								<span class="visually-hidden" id="dtoImgListSize"
									  name="dtoImgListSize">${dto.imgList.size()}</span>
								<span class="visually-hidden" id="imgListIndex" name="imgListIndex">0</span>
								<input type="hidden" id="imgList" name="imgList" value="${dto.imgList}"></input>
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
						<span class="h2">${dto.loc_name}</span>
					</div>
					<div class="row d-flex">
						<h5>지역 : ${dto.siDo} ${dto.siGunGu}</h5>
					</div>
					<div class="row d-flex">
						<h5>해시태그 : ${dto.tagSet}</h5>
					</div>
<%--					<div class="row d-flex">--%>
<%--						<h5 class="text-truncate">설명 : ${dto.info}</h5>--%>
<%--					</div>--%>
					<div class="row d-flex">
						<h5>등록일 : ${dto.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</h5>
					</div>
					<div class="row d-flex">
						<h5>연락처 : ${dto.tel}</h5>
					</div>
				</div>
				<div class="row d-flex">
				</div>

				<div class="d-flex align-content-end flex-wrap">
<%--					이미지 소스 바꾸는 로직 추가--%>
					<sec:authorize access="isAuthenticated()">
						<c:choose>
							<c:when test="${isLiked eq true}">
								<img src="/image/icon/like/love_color.png" class="loc_icon_big me-2" alt="찜하기"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'loc')">
							</c:when>
							<c:otherwise>
								<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'loc')">
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기">
					</sec:authorize>
					<span class="text-center align-middle fs-3 me-4" id="likeCount">${dto.likeCount}</span>
					<img src="/image/icon/comment.png" class="loc_icon_big me-2" alt="댓글">
					<span class="text-center align-middle fs-3 me-4">${dto.cmtList.size()}</span>
<%--					Todo 추후에 위치 수정하기--%>
<%--				loc_common onClickLike	--%>
					<span class="visually-hidden">${dto.user_no}</span>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px" onclick="copyURL()">공유</button>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px;" onclick="location.href='/service/loc_edit?locNo=${dto.loc_no}'">수정</button>
					<sec:authorize access="isAuthenticated()">
<%--						<span><sec:authentication property="principal.user_no"></sec:authentication></span>--%>
					<c:set var="currUserNo"><sec:authentication property="principal.user_no"></sec:authentication></c:set>
					<c:choose>
						<c:when test="${dto.user_no eq currUserNo}">
							<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="장소 삭제"
							 onclick="onClickRemoveLocation()">
						</c:when>
						<c:otherwise>
							<sec:authorize access="hasRole('ADMIN')">
								<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="장소 삭제"
									 onclick="onClickRemoveLocation()">
								<img src="/image/icon/rollback.png" class="loc_icon_big me-2" alt="장소 복원"
									 onclick="onClickRollbackLocation()">
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</sec:authorize>
				</div>
				<span class="d-none" id="loc_no">${dto.loc_no}</span>
			</div>
		</div>
		<div class="row justify-content-md-start">
			<ul class="nav nav-pills nav-fill col-5" id="pills-tab" role="tablist"
				style="height:50px; padding-top: 5px; padding-bottom: 5px">
				<li class="nav-item" role="presentation">
					<c:choose>
						<c:when test="${param.containsKey('page') eq true}">
						<button class="nav-link" id="location-info-tab" data-bs-toggle="pill"
								data-bs-target="#location-info" type="button" role="tab" aria-controls="location-info" onclick="removeURLParam('page')"
								aria-selected="true">설명</button>
						</c:when>
						<c:otherwise>
							<button class="nav-link active" id="location-info-tab" data-bs-toggle="pill"
									data-bs-target="#location-info" type="button" role="tab" aria-controls="location-info" onclick="removeURLParam('page')"
									aria-selected="true">설명</button>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="nav-item" role="presentation">
					<c:choose>
						<c:when test="${param.containsKey('page') eq true}">
							<button class="mw-100 mh-100 nav-link active" id="location-comment-tab" data-bs-toggle="pill"
									data-bs-target="#location-comment" type="button" role="tab" aria-controls="location-comment"
									aria-selected="false" onclick="addURLParam('page', '1')">댓글</button>
						</c:when>
						<c:otherwise>
							<button class="mw-100 mh-100 nav-link" id="location-comment-tab" data-bs-toggle="pill"
									data-bs-target="#location-comment" type="button" role="tab" aria-controls="location-comment"
									aria-selected="false" onclick="addURLParam('page', '1')">댓글</button>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
			<div class="tab-content" id="pills-tabContent">
				<%--      설명--%>
					<c:choose>
						<c:when test="${param.containsKey('page') eq true}">
							<div class="tab-pane fade" id="location-info" role="tabpanel" aria-labelledby="location-info-tab">
						</c:when>
						<c:otherwise>
							<div class="tab-pane fade show active" id="location-info" role="tabpanel" aria-labelledby="location-info-tab">
						</c:otherwise>
					</c:choose>
					<div class="container">
						<div class="d-flex mt-3">
							<span class="fs-5" style="white-space: pre-wrap;">${dto.info}</span>
						</div>
					</div>
				</div>
				<%--    댓글--%>
				<c:choose>
					<c:when test="${param.containsKey('page') eq true}">
						<div class="tab-pane fade active show" id="location-comment" role="tabpanel" aria-labelledby="location-comment-tab">
					</c:when>
					<c:otherwise>
						<div class="tab-pane fade" id="location-comment" role="tabpanel" aria-labelledby="location-comment-tab">
					</c:otherwise>
				</c:choose>
					<div class="container mt-0">
						<div class="d-flex justify-content-start row">
							<div class="col-md-10">
								<div class="d-flex flex-column">
									<c:set var="cmtDTO" value="${resComDTO.dtoList}"></c:set>
									<c:choose>
										<c:when test="${0 != cmtDTO.size()}">
											<c:forEach var="c" begin="0" end="${cmtDTO.size()-1}">
												<div class="bg-white p-2">
													<div class="d-flex flex-row align-items-center">
														<img
															src="${cmtDTO.get(c).user.profile_pic}"
															class="loc_comment-profile-image-wh">
														<div class="flex-column">
															<p class="visually-hidden" id="cmt_id_${c}">${cmtDTO.get(c).cmtUuid}</p>
															<span class="d-block font-weight-bold name">
																	${cmtDTO.get(c).user.user_nic}
																		<sec:authorize access="isAuthenticated()">
																			<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
																			<c:choose>
																				<c:when test="${user_no eq cmtDTO.get(c).user.user_no}">
																					<span class="d-none" id="cmt_user_email_${c}">${cmtDTO.get(c).user.user_email}</span>
																					<div>
																						<button class="btn btn-primary" onclick="openCmtEditMenu(${c})">수정</button>
																						<button class="btn btn-primary" onclick="onClickDeleteComment(${c})">삭제</button>
																					</div>
																				</c:when>
																			</c:choose>
																		</sec:authorize>
															</span>
															<span class="date text-black-50 ml-5">${cmtDTO.get(c).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
															<c:choose>
																<c:when test="${cmtDTO.get(c).regDate ne cmtDTO.get(c).modDate}">
																	<span class="date text-black-50 ml-5">(수정됨)</span>
																</c:when>
															</c:choose>
														</div>
													</div>
													<div class="mt-2">
														<div id="cmt_content_${c}" class="visible">
															<p class="comment-text">${cmtDTO.get(c).cmtContent}</p>
														</div>
														<div id="cmt_edit_${c}" class="row visually-hidden">
															<textarea id="cmt_edit_content_${c}" rows="6" maxlength="300" class="form-control ml-1 shadow-none textarea">${cmtDTO.get(c).cmtContent}</textarea>
															<div class="d-flex p-0 justify-content-end">
																<button class="btn btn-primary mx-2" onclick="submitUpdateComment(${c})">등록</button>
																<button class="btn btn-primary mx-2" onclick="closeCmtEditMenu(${c})">뒤로가기</button>
															</div>
														</div>
													</div>
												</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<p>등록된 댓글이 없습니다.</p>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>


					<%--	PageNumber	--%>
					<div class="container d-flex" id="">
						<div class="col" id="page_number">
							<nav aria-label="Page navigation example">
								<ul class="pagination justify-content-center">
									<c:if test="${resComDTO.prev eq true}">
										<li class="page-item">
											<a class="page-link" href="/service/loc_detail?locNo=${dto.loc_no}&page=${resComDTO.start - 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&laquo;</span>
											</a>
										</li>
									</c:if>
									<c:choose>
										<c:when test="${resComDTO.start < 10}">
											<c:set var="power" value="${0}"/>
										</c:when>
										<c:otherwise>
											<c:set var="power" value="${resComDTO.start - (resComDTO.start mod 10)}"/>
										</c:otherwise>
									</c:choose>
									<c:forEach var="j" begin="${1}" end="${resComDTO.end - resComDTO.start + 1}">
									<c:choose>
									<c:when test="${resComDTO.page eq j + power}">
									<li class="page-item active">
										<a class="page-link"
											<%--										   pageList에는 현재 8개의 리스트 밖에 없지만 반복문 j는 11에서부터 시작하므로 인덱스 오류가 발생함--%>
										   href="/service/loc_detail?locNo=${dto.loc_no}&page=${resComDTO.pageList.get(j - 1)}">${resComDTO.pageList.get(j - 1)}</a>
											<%--										   href="/service/loc_detail?locNo=${dto.loc_no}&page=${resComDTO.pageList.get(j)}">${resComDTO.pageList.get(j)}</a>--%>
										</c:when>
										<c:otherwise>
									<li class="page-item">
										<a class="page-link"
										   href="/service/loc_detail?locNo=${dto.loc_no}&page=${resComDTO.pageList.get(j-1)}">${resComDTO.pageList.get(j-1)}</a>
										</c:otherwise>
										</c:choose>
										</c:forEach>
									</li>
									<c:if test="${resComDTO.next eq true}">
										<li class="page-item">
											<a class="page-link" href="/service/loc_detail?locNo=${dto.loc_no}&page=${resComDTO.end + 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&raquo;</span>
											</a>
										</li>
									</c:if>
								</ul>
							</nav>
						</div>
					</div>
					<%-- 댓글 작성--%>
					<sec:authorize access="isAnonymous()">
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<div class="d-flex justify-content-start" id="comment">
							<div class="bg-light p-2 col-10">
								<div class="d-flex flex-row align-items-start">
									<c:set var="currentUserPic"><sec:authentication property="principal.user_profilePic"></sec:authentication></c:set>
									<img class="rounded-circle m-3" src="${currentUserPic}" width="60">
									<textarea id="commentArea" rows="6" maxlength="300" class="form-control ml-1 shadow-none textarea" placeholder="postCommentTest"></textarea>
								</div>
								<div class="mt-2 text-end">
									<button id="postCommentBtn" onclick="onClickPostComment()" class="btn btn-primary btn shadow-none" type="button">Post comment</button>
								</div>
							</div>
						</div>
					</sec:authorize>
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
<script defer src="/js/loc_common.js"></script>
<script>
	window.onload = function() {
        let params = new URLSearchParams(location.search);
        // console.log(params);
        // console.log(params.get("locNo"));
        // console.log(params.get("page"));

		if (params.has("page")) {
            let offsetTop = document.querySelector("#pills-tab").offsetTop - 100;

		    window.scrollTo({top:offsetTop, behavior: 'smooth'});
<%--		    let location_info = document.getElementById("location-info");--%>
<%--		    let location_info_tab = document.getElementById("location-info-tab");--%>
<%--		    let location_comment = document.getElementById("location-comment");--%>
<%--		    let location_comment_tab = document.getElementById("location-comment-tab");--%>

<%--		    location_info_tab.setAttribute("class", "nav-link");--%>
<%--		    location_comment_tab.setAttribute("class", "mw-100 mh-100 nav-link active");--%>
<%--            location_info.setAttribute("class", "tab-pane fade");--%>
<%--            location_comment.setAttribute("class", "tab-pane fade active show");--%>
		}
	}
</script>
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
            indexIndicator.innerText = (tempIndex + 1) + "/" + imgList.length;
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
            indexIndicator.innerText = (tempIndex + 1) + "/" + imgList.length;
        }
    }

    function onClickPostComment() {
        let form;
        let cmt = document.getElementById("commentArea");

        form = document.createElement("form");
        form.method = "post";
        form.action="/service/com_registration"

		let input = [];
		for (let i = 0; i < 3; i++) {
		    input[i] = document.createElement("input");
		    $(input[i]).attr("type", "hidden");

            if (i === 0) {
                $(input[0]).attr("name", "locNo");
                $(input[0]).attr("value", "${dto.loc_no}");
            }
            else if (i === 1) {
                $(input[1]).attr("name", "userNo");
                <sec:authorize access="isAuthenticated()">
                $(input[1]).attr("value", "<sec:authentication property="principal.user_no"/>");
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                $(input[1]).attr("value", null);
                </sec:authorize>
            } else if (i === 2) {
                $(input[2]).attr("name", "cmtContent");
                $(input[2]).attr("value", cmt.value);
            }

		    form.appendChild(input[i]);
		}

		document.body.appendChild(form);
		form.submit();
	}

	function openCmtEditMenu(index) {
        let cmt_content = document.getElementById("cmt_content_" + index);
        let cmt_edit_menu = document.getElementById("cmt_edit_" + index);

        cmt_content.setAttribute("class", "visually-hidden");
        cmt_edit_menu.setAttribute("class", "row visible");
	}

	function closeCmtEditMenu(index) {
        let cmt_content = document.getElementById("cmt_content_" + index);
        let cmt_edit_menu = document.getElementById("cmt_edit_" + index);

        cmt_content.setAttribute("class", "visible");
        cmt_edit_menu.setAttribute("class", "row visually-hidden");
	}

	function onClickDeleteComment(index) {
        let delConfirm = confirm("댓글을 지우시겠습니까?");

        if (!delConfirm){
            return;
		}

        let cmt_uuid = document.getElementById("cmt_id_" + index).innerText;
        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action="/service/com_del"

        let input = [];
        for (let i = 0; i < 3; i++) {
            input[i] = document.createElement("input");
            $(input[i]).attr("type", "hidden");

            if (i === 0) {
                $(input[0]).attr("name", "locNo");
                $(input[0]).attr("value", "${dto.loc_no}");
            }

            if (i === 1) {
                $(input[1]).attr("name", "userEmail");
                <sec:authorize access="isAuthenticated()">
                $(input[1]).attr("value", document.getElementById("cmt_user_email_" + index).innerText);
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                $(input[1]).attr("value", null);
                </sec:authorize>
            }

            if (i === 2) {
                $(input[2]).attr("name", "cmt_uuid");
                $(input[2]).attr("value", cmt_uuid);
            }

            form.appendChild(input[i]);
        }

        document.body.appendChild(form);
        form.submit();
	}

	function submitUpdateComment(index) {
        let cmt_uuid = document.getElementById("cmt_id_" + index).innerText;
        let cmt_content = document.getElementById("cmt_edit_content_" + index).value;
        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action="/service/com_edit"

        let input = [];
        for (let i = 0; i < 4; i++) {
            input[i] = document.createElement("input");
            $(input[i]).attr("type", "hidden");

            if (i === 0) {
                $(input[0]).attr("name", "locNo");
                $(input[0]).attr("value", "${dto.loc_no}");
			}

            if (i === 1) {
                $(input[1]).attr("name", "userEmail");
                <sec:authorize access="isAuthenticated()">
                $(input[1]).attr("value", document.getElementById("cmt_user_email_" + index).innerText);
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                $(input[1]).attr("value", null);
                </sec:authorize>
            }

            if (i === 2) {
                $(input[2]).attr("name", "cmt_uuid");
                $(input[2]).attr("value", cmt_uuid);
            }

            if (i === 3) {
                $(input[3]).attr("name", "cmtContent");
                $(input[3]).attr("value", cmt_content);
            }

            form.appendChild(input[i]);
        }

        document.body.appendChild(form);
        form.submit();
	}
</script>
<script defer>
	function onClickRemoveLocation() {
	    alert('장소를 삭제하시겠습니까?');

	    let param = location.search;

        console.log(param);
        console.log('/service/loc_delete' + param);

        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action= "/service/loc_delete" + param;

        document.body.appendChild(form);
        form.submit();
	}

    function onClickRollbackLocation() {
        alert('장소를 복원하시겠습니까?');

        let param = location.search;

        console.log(param);
        console.log('/service/loc_rollback' + param);

        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action= "/service/loc_rollback" + param;

        document.body.appendChild(form);
        form.submit();
    }
</script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
