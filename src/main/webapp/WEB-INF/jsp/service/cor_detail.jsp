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
							<p><a href="/service/cor_recommend" class="loc_highlight-selected-text-menu">- 추천 코스</a></p>
							<p><a href="/service/cor_registration" class="loc_highlight-not-selected-text-menu">- 코스 등록</a></p>
							<p><a href="#" class="loc_highlight-not-selected-text-menu">- 코스 편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>
	<c:choose>
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
					<c:set var="imgList" value="${ImageList}"></c:set>
					<c:choose>
						<c:when test="${!empty imgList}">
							<div class="d-flex justify-content-center">
								<img class="bd-placeholder-img card-img" width="100%"
									 height="400"
									 alt="${dto.cor_name}"
									 src="${imgList.get(0).img_url}"
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
									  name="dtoImgListSize">${imgList.size()}</span>
								<span class="visually-hidden" id="imgListIndex" name="imgListIndex">0</span>
								<input type="hidden" id="imgList" name="imgList" value="${imgList}"></input>
							</div>
						</c:when>
						<c:otherwise>
							<svg class="bd-placeholder-img card-img-top" width="100%" height="400"
								 xmlns="http://www.w3.org/2000/svg" role="img"
								 aria-label="Placeholder: Thumbnail"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
								<title>Placeholder</title>
								<rect width="100%" height="100%" fill="#55595c"></rect>
								<text x="40%" y="50%" fill="#eceeef" dy=".3em">${dto.cor_name}</text>
							</svg>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-md-5 justify-content-md-center">
				<div class="row d-flex">
					<div class="row d-flex justify-content-between p-1">
						<span class="h2">${dto.cor_name}</span>
					</div>
					<div class="row d-flex">
						<h5>해시태그 : ${dto.tagSet}</h5>
					</div>
					<c:choose>
						<c:when test="${dto.est_type eq 'time'}">
							<div class="row d-flex">
								<h5>예상 소요시간 : ${dto.est_value}</h5>
							</div>
						</c:when>
						<c:otherwise>
							<div class="row d-flex">
								<h5>예상 소요일 : ${dto.est_value}</h5>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="row d-flex">
						<h5>이동수단 : ${dto.transportation}</h5>
					</div>
					<div class="row d-flex">
						<h5>비용 : ${dto.cost}</h5>
					</div>
<%--					<div class="row d-flex">--%>
<%--						<h5 class="text-truncate">설명 : ${dto.info}</h5>--%>
<%--					</div>--%>
					<div class="row d-flex">
						<h5>등록일 : ${dto.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</h5>
					</div>
				</div>
				<div class="row d-flex">
				</div>

				<div class="d-flex align-content-end flex-wrap">
					<sec:authorize access="isAuthenticated()">
						<c:choose>
							<c:when test="${isLiked eq true}">
								<img src="/image/icon/like/love_color.png" class="loc_icon_big me-2" alt="찜하기"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'cor')">
							</c:when>
							<c:otherwise>
								<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'cor')">
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기">
					</sec:authorize>
					<span class="text-center align-middle fs-3 me-4" id="likeCount">${dto.likeCount}</span>
					<img src="/image/icon/comment.png" class="loc_icon_big me-2" alt="댓글">
					<span class="text-center align-middle fs-3 me-4">${resRevDTO.dtoList.size()}</span>
<%--					Todo 추후에 위치 수정하기--%>
<%--				loc_common onClickLike	--%>
					<span class="visually-hidden">${dto.user_no}</span>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px" onclick="copyURL()">공유</button>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px;" onclick="location.href='/service/loc_edit?locNo=${dto.cor_no}'">수정</button>
				</div>
				<span class="d-none" id="cor_no">${dto.cor_no}</span>
			</div>
		</div>
		<div class="row justify-content-md-start">
			<ul class="nav nav-pills nav-fill col-5" id="pills-tab" role="tablist"
				style="height:50px; padding-top: 5px; padding-bottom: 5px">
				<li class="nav-item" role="presentation">
					<button class="nav-link active" id="course-info-tab" data-bs-toggle="pill"
							data-bs-target="#course-info" type="button" role="tab" aria-controls="course-info"
							aria-selected="true">설명
					</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="mw-100 mh-100 nav-link" id="course-review-tab" data-bs-toggle="pill"
							data-bs-target="#course-review" type="button" role="tab" aria-controls="course-review"
							aria-selected="false">리뷰
					</button>
				</li>
			</ul>
			<div class="tab-content" id="pills-tabContent">
				<%--      설명--%>
				<div class="tab-pane fade show active" id="course-info" role="tabpanel" aria-labelledby="course-info-tab">
					<div class="container">
						<div class="d-flex mt-3">
							<span class="fs-5" style="white-space: pre-wrap;">${dto.info}</span>
						</div>
					</div>
				</div>
				<%--    리뷰--%>
				<div class="tab-pane fade" id="course-review" role="tabpanel" aria-labelledby="course-review-tab">
					<div class="container mt-0">
						<div class="d-flex justify-content-start row">
							<div class="col-md-10">
								<div class="d-flex flex-column">
									<c:set var="revDTO" value="${resRevDTO.dtoList}"></c:set>
									<c:choose>
										<c:when test="${0 != revDTO.size()}">
											<c:forEach var="c" begin="0" end="${revDTO.size()-1}">
												<div class="bg-white p-2">
													<div class="d-flex flex-row align-items-center"><img
															src="/image/icon/user/user.png"
															class="loc_comment-profile-image-wh">
														<div class="flex-column">
															<p class="visually-hidden" id="rev_id_${c}">${revDTO.get(c).revUuid}</p>
															<span class="d-block font-weight-bold name">
																	${revDTO.get(c).userName}
																		<sec:authorize access="isAuthenticated()">
																			<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
																			<c:choose>
																				<c:when test="${user_no eq revDTO.get(c).userNo}">
																					<div>
																						<button class="btn btn-primary" onclick="openCmtEditMenu(${c})">수정</button>
																						<button class="btn btn-primary" onclick="onClickDeleteComment(${c})">삭제</button>
																					</div>
																				</c:when>
																			</c:choose>
																		</sec:authorize>
															</span>
															<span class="date text-black-50 ml-5">${revDTO.get(c).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
															<c:choose>
																<c:when test="${revDTO.get(c).regDate ne revDTO.get(c).modDate}">
																	<span class="date text-black-50 ml-5">(수정됨)</span>
																</c:when>
															</c:choose>
														</div>
													</div>
													<div class="mt-2">
														<div id="cmt_content_${c}" class="visible">
															<p class="comment-text">${revDTO.get(c).revContent}</p>
														</div>
														<div id="cmt_edit_${c}" class="row visually-hidden">
															<textarea id="cmt_edit_content_${c}" rows="6" maxlength="300" class="form-control ml-1 shadow-none textarea">${revDTO.get(c).revContent}</textarea>
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
									<c:if test="${resRevDTO.prev eq true}">
										<li class="page-item">
											<a class="page-link" href="/service/cor_detail?corNo=${dto.cor_no}&page=${revDTO.start - 1}"
											   aria-label="Previous">
												<span aria-hidden="true">&laquo;</span>
											</a>
										</li>
									</c:if>
									<c:choose>
										<c:when test="${resRevDTO.start < 10}">
											<c:set var="power" value="${0}"/>
										</c:when>
										<c:otherwise>
											<c:set var="power" value="${resRevDTO.start - (resRevDTO.start mod 10)}"/>
										</c:otherwise>
									</c:choose>
									<c:forEach var="j" begin="${1}" end="${resRevDTO.end - resRevDTO.start + 1}">
									<c:choose>
									<c:when test="${resRevDTO.page eq j + power}">
									<li class="page-item active">
										<a class="page-link"
										   href="/service/cor_detail?corNo=${dto.cor_no}&page=${resRevDTO.pageList.get(j - 1)}">${revDTO.pageList.get(j - 1)}</a>
											<%--										   href="/service/loc_detail?locNo=${dto.loc_no}&page=${resComDTO.pageList.get(j)}">${resComDTO.pageList.get(j)}</a>--%>
										</c:when>
										<c:otherwise>
									<li class="page-item">
										<a class="page-link"
										   href="/service/cor_detail?corNo=${dto.cor_no}&page=${resRevDTO.pageList.get(j-1)}">${resRevDTO.pageList.get(j-1)}</a>
										</c:otherwise>
										</c:choose>
										</c:forEach>
									</li>
									<c:if test="${resRevDTO.next eq true}">
										<li class="page-item">
											<a class="page-link" href="/service/cor_detail?corNo=${dto.cor_no}&page=${resRevDTO.end + 1}"
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
<script defer>
    function clickImgNext() {
        var imgDisplay = document.getElementById("imgDisplay");
        var index = document.getElementById("imgListIndex");
        var temp = "";
        var imgList;
        var tempIndex = parseInt(index.innerText);
        var indexIndicator = document.getElementById("indexIndicator");

        <c:choose>
			<c:when test="${ImageList.size() eq 0}">
				temp = "_";
			</c:when>
			<c:otherwise>
        		<c:forEach var="i" begin="0" end="${ImageList.size()-1}">
        			temp += "${ImageList.get(i).img_url}" + "_";
        		</c:forEach>
        		temp = temp.substring(0, temp.length-1);
			</c:otherwise>
		</c:choose>
        imgList = temp.split('_');

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
        var temp = "";
        var imgList;
        var tempIndex = parseInt(index.innerText);
        var indexIndicator = document.getElementById("indexIndicator");

        <c:choose>
        	<c:when test="${ImageList.size() eq 0}">
		        temp = "_";
	        </c:when>
    	    <c:otherwise>
        		<c:forEach var="i" begin="0" end="${ImageList.size()-1}">
			        temp += "${ImageList.get(i).img_url}" + "_";
		        </c:forEach>
	    	    temp = temp.substring(0, temp.length-1);
    	    </c:otherwise>
        </c:choose>
        imgList = temp.split('_');

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
                $(input[0]).attr("name", "corNo");
                $(input[0]).attr("value", "${dto.cor_no}");
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
                $(input[0]).attr("value", "${dto.cor_no}");
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
                $(input[0]).attr("value", "${dto.cor_no}");
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
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
