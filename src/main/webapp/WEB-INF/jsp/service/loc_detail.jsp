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
        @import url('https://fonts.googleapis.com/css2?family=Orbitron:wght@600&display=swap');

		.best {
            font-family: 'Orbitron', sans-serif;
		}

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
	<title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>

<div class="container-fluid d-flex" style="padding-top: 100px">
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
					<button class="btn-close" type="button" class="close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<span id="repPostName">제 목 : 내용</span>
						<span id="userNick">작성자 : 닉네임</span>
						<hr class="mt-2 mb-2">
						<span> 사유 선택 : </span>
						<form name="repTypeForm" id="repTypeForm" class="mb-0" onchange="(function onChangeRepTypeForm() {
							let repContentRow = document.getElementById('repContentRow');
                            let repContentHr = document.getElementById('repContentHr');

							if (document.querySelector('input[name=\'report\']:checked').value === 'ETC') {
								repContentRow.classList.replace('visually-hidden', 'visible');
                                repContentHr.classList.replace('visually-hidden', 'visible');
							} else {
								repContentRow.classList.replace('visible', 'visually-hidden');
                                repContentHr.classList.replace('visible', 'visually-hidden');
							}
							})()">
							<div class="row">
								<div class="col">
									<input type="radio" name="report" value="ADVERTISE">
									<label>광고성 게시물</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<input type="radio" name="report" value="PORNOGRAPHY">
									<label>청소년 유해물 혹은 음란물이 포함된 게시물</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<input type="radio" name="report" value="ILLEGAL">
									<label>불법 정보가 포함된 게시물</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<input type="radio" name="report" value="INSULT">
									<label>욕설 혹은 혐오발언 게시물</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<input type="radio" name="report" value="PERSONAL_INFO">
									<label>개인정보가 노출된 게시물</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<input type="radio" name="report" value="ETC">
									<label>기타</label>
								</div>
							</div>
						</form>
						<hr id="repContentHr" class="mt-2 mb-2 visually-hidden">
						<div id="repContentRow" class="row visually-hidden">
							<div class="col">
								<textarea id="repContent" cols="54" rows="5" maxlength="300" name="repContent"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-primary" onclick="reportSubmit()">신고하기</button>
				</div>
			</div>
		</div>
	</div>

	<div class="col-2" id="sidebar">
		<ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
			<div class="accordion text-center" id="loc">
				<hr>
				<div class="card">
					<div class="card-header" id="headingLoc">
						<h2 class="mb-0">
							<button class="btn btn-link btn-block" type="button" style="text-decoration: none; color: #FF6699; font-weight: bold">
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
							<p><a href="/mypage_myplace/1" class="highlight-not-selected-text-menu">- 장소 편집</a></p>
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
			<sec:authorize access="hasRole('USER') and !hasRole('ADMIN')">
				<span>현재 페이지는 삭제되었습니다.</span>
				<%
					if( true ) return;
				%>
			</sec:authorize>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${userDTO eq null}">
			<c:set var="userNick" value="삭제된 유저"/>
		</c:when>
		<c:otherwise>
			<c:set var="userNick" value="${userDTO.user_nic}"/>
		</c:otherwise>
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
						<c:choose>
							<c:when test="${isBizUploader eq true}">
								<span class="best badge bg-primary col-2 ms-3 mb-1">업체</span>
							</c:when>
						</c:choose>
						<span class="h2">${dto.loc_name}</span>
					</div>
					<div class="row d-flex">
						<h5>지역 : ${dto.siDo} ${dto.siGunGu}</h5>
					</div>
					<div class="row d-flex">
						<h5>해시태그 : ${dto.tagSet}</h5>
					</div>
					<div class="row d-flex">
						<h5>등록일 : ${dto.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</h5>
					</div>
					<div class="row d-flex">
						<h5>연락처 : ${dto.tel}</h5>
					</div>
					<div class="row d-flex">
						<h5>작성자 : ${userNick}</h5>
					</div>
					<div class="row d-flex">
						<h5>댓글 : ${dto.liveCmtCount}</h5>
					</div>
				</div>

				<div class="d-flex align-content-end flex-wrap">
<%--					이미지 소스 바꾸는 로직 추가--%>
					<sec:authorize access="isAuthenticated()">
						<c:choose>
							<c:when test="${isLiked eq true}">
								<img src="/image/icon/like/love_color.png" class="loc_icon_big me-2" alt="찜하기" style="margin-left: 0px"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'loc')">
							</c:when>
							<c:otherwise>
								<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기" style="margin-left: 0px"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'loc')">
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2" alt="찜하기">
					</sec:authorize>
					<span class="text-center align-middle fs-3 me-4" id="likeCount">${dto.likeCount}</span>
<%--				loc_common onClickLike	--%>
					<span class="visually-hidden">${dto.user_no}</span>
					<img src="/image/icon/share.png" class="loc_icon_big ms-0 me-4" style="max-height: 56px" onclick="copyURL()">
					<sec:authorize access="isAuthenticated()">
						<img src="/image/icon/report.png" class="loc_icon_big ms-0" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal"
								onclick="openReportModal('${dto.loc_name}', '${userNick}', 'LOC', '${dto.loc_no}')">
<%--						<span><sec:authentication property="principal.user_no"></sec:authentication></span>--%>
					</sec:authorize>
				</div>
				<div class="d-flex align-content-end flex-wrap">
					<sec:authorize access="isAuthenticated()">
						<c:set var="currUserNo"><sec:authentication property="principal.user_no"></sec:authentication></c:set>
						<c:choose>
							<c:when test="${dto.user_no eq currUserNo}">
								<sec:authorize access="hasAnyRole('ADMIN')">
									<img src="/image/icon/edit.png" alt="수정" class="loc_icon_big ms-0 me-2" style="max-height: 56px;" onclick="location.href='/service/loc_edit?locNo=${dto.loc_no}'">
									<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="장소 삭제"
										 onclick="onClickRemoveLocation()">
									<img src="/image/icon/rollback.png" class="loc_icon_big me-2" alt="장소 복원"
										 onclick="onClickRollbackLocation()">
									<button class="btn btn-primary ms-2" onclick="onClickPermaDeleteLocation()">영구삭제</button>
								</sec:authorize>
								<sec:authorize access="hasRole('USER') && !hasRole('ADMIN')">
									<img src="/image/icon/edit.png" class="loc_icon_big ms-0 me-2" style="max-height: 56px;" onclick="location.href='/service/loc_edit?locNo=${dto.loc_no}'">
									<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="장소 삭제"
										 onclick="onClickRemoveLocation()">
								</sec:authorize>
							</c:when>
							<c:otherwise>
								<sec:authorize access="hasAnyRole('ADMIN')">
									<img src="/image/icon/edit.png" class="loc_icon_big ms-0 me-2" style="max-height: 56px;" onclick="location.href='/service/loc_edit?locNo=${dto.loc_no}'">
									<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="장소 삭제"
										 onclick="onClickRemoveLocation()">
									<img src="/image/icon/rollback.png" class="loc_icon_big me-2" alt="장소 복원"
										 onclick="onClickRollbackLocation()">
									<button class="btn btn-primary ms-2" onclick="onClickPermaDeleteLocation()">영구삭제</button>
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
					<button class="nav-link" id="location-map-tab" data-bs-toggle="pill"
							data-bs-target="#location-map" type="button" role="tab" aria-controls="location-map" onclick="removeURLParam('page')"
							aria-selected="true">위치</button>
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
<%--							위치--%>
				<div class="tab-pane fade" id="location-map" role="tabpanel" aria-labelledby="location-map-tab">
					<iframe name="location_map_embeded" width="1200" height="1000" src="/embeded/map?locNo=${dto.loc_no}"></iframe>
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
										<c:when test="${!empty bestCmtList and paramValues.page[0] eq 1}">
											<c:forEach var="b" begin="0" end="${bestCmtList.size()-1}">
												<div class="p-2" style="background: #FFC2D6">
													<span class="best badge bg-primary fs-6 mb-2">BEST</span>
													<div class="d-flex flex-row align-items-center">
														<img src="${bestCmtList.get(b).user.profile_pic}"
															 class="loc_comment-profile-image-wh">
														<div class="flex-column">
															<p class="visually-hidden" id="best_cmt_id_${b}">${bestCmtList.get(b).cmtUuid}</p>
															<p class="visually-hidden" id="best_cmt_no_${b}">${bestCmtList.get(b).cmtNo}</p>
															<span class="d-block font-weight-bold name">
																	${bestCmtList.get(b).user.user_nic}
															</span>
															<span class="date text-black-50 ml-5">${bestCmtList.get(b).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
															<c:choose>
																<c:when test="${bestCmtList.get(b).modified}">
																	<span class="date text-black-50 ml-5">(수정됨)</span>
																</c:when>
															</c:choose>
														</div>
													</div>
													<div class="d-flex row justify-content-between" style="margin-left: 60px">
														<sec:authorize access="isAnonymous()">
															<div class="col d-flex align-items-center">
																<image class="loc_icon_medium me-1 ms-0" src="/image/icon/cmt_like_bw.png"></image>
																<span class="text-center align-middle fs-6">${bestCmtList.get(b).likeCount}</span>
																<image class="loc_icon_medium me-1" src="/image/icon/cmt_dislike_bw.png"></image>
																<span class="text-center align-middle fs-6">${bestCmtList.get(b).dislikeCount}</span>
															</div>
														</sec:authorize>
														<sec:authorize access="isAuthenticated()">
															<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
															<div class="col d-flex align-items-center">
																<c:choose>
																	<c:when test="${!empty bestCmtLikeList and !empty bestCmtDislikeList}">
																		<c:choose>
																			<c:when test="${bestCmtLikeList.get(b) eq null or bestCmtDislikeList.get(b) eq null}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/cmt_like_bw.png" id="best_cmt_btn_like_${b+1}"
																					   onclick="onClickLikeBestComment(this, ${b+1},  ${bestCmtIndexList.get(b) + 1}, ${user_no}, 'cmt_like', ${bestCmtList.get(b).cmtNo})"></image>
																				<span class="text-center align-middle fs-6" id="best_cmt_like_${b+1}">${bestCmtList.get(b).likeCount}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/cmt_dislike_bw.png" id="best_cmt_btn_dislike_${b+1}"
																					   onclick="onClickDislikeBestComment(this, ${b+1}, ${bestCmtIndexList.get(b) + 1}, ${user_no}, 'cmt_dislike', ${bestCmtList.get(b).cmtNo})"></image>
																				<span class="text-center align-middle fs-6" id="best_cmt_dislike_${b+1}">${bestCmtList.get(b).dislikeCount}</span>
																			</c:when>
																			<c:when test="${bestCmtLikeList.get(b) eq true or bestCmtDislikeList.get(b) eq false}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/cmt_like_color.png" id="best_cmt_btn_like_${b+1}"
																					   onclick="onClickLikeBestComment(this, ${b+1},  ${bestCmtIndexList.get(b) + 1}, ${user_no}, 'cmt_like', ${bestCmtList.get(b).cmtNo})"></image>
																				<span class="text-center align-middle fs-6" id="best_cmt_like_${b+1}">${bestCmtList.get(b).likeCount}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/cmt_dislike_bw.png" id="best_cmt_btn_dislike_${b+1}"
																					   onclick="onClickDislikeBestComment(this, ${b+1}, ${bestCmtIndexList.get(b) + 1}, ${user_no}, 'cmt_dislike', ${bestCmtList.get(b).cmtNo})"></image>
																				<span class="text-center align-middle fs-6" id="best_cmt_dislike_${b+1}">${bestCmtList.get(b).dislikeCount}</span>
																			</c:when>
																			<c:when test="${bestCmtLikeList.get(b) eq false or bestCmtDislikeList.get(b) eq true}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/cmt_like_bw.png" id ="best_cmt_btn_like_${b+1}"
																					   onclick="onClickLikeBestComment(this, ${b+1},  ${bestCmtIndexList.get(b) + 1}, ${user_no}, 'cmt_like', ${bestCmtList.get(b).cmtNo})"></image>
																				<span class="text-center align-middle fs-6" id="best_cmt_like_${b+1}">${bestCmtList.get(b).likeCount}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/cmt_dislike_color.png" id ="best_cmt_btn_dislike_${b+1}"
																					   onclick="onClickDislikeBestComment(this, ${b+1}, ${bestCmtIndexList.get(b) + 1},${user_no}, 'cmt_dislike', ${bestCmtList.get(b).cmtNo})"></image>
																				<span class="text-center align-middle fs-6" id="best_cmt_dislike_${b+1}">${bestCmtList.get(b).dislikeCount}</span>
																			</c:when>
																		</c:choose>
																	</c:when>
																</c:choose>
															</div>
														</sec:authorize>
													</div>
													<div class="mt-2">
														<div id="best_cmt_content_${b}" class="visible">
															<p class="m-0 comment-text fs-5">${bestCmtList.get(b).cmtContent}</p>
														</div>
													</div>
												</div>
											</c:forEach>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${!empty cmtDTO}">
											<c:forEach var="c" begin="0" end="${cmtDTO.size()-1}">
												<div class="bg-white p-2">
													<div class="d-flex flex-row align-items-center">
														<img src="${cmtDTO.get(c).user.profile_pic}"
																class="loc_comment-profile-image-wh">
														<div class="flex-column">
															<p class="visually-hidden" id="cmt_id_${c}">${cmtDTO.get(c).cmtUuid}</p>
															<p class="visually-hidden" id="cmt_no_${c}">${cmtDTO.get(c).cmtNo}</p>
															<span class="d-block font-weight-bold name">
																	${cmtDTO.get(c).user.user_nic}
															</span>
															<span class="date text-black-50 ml-5">${cmtDTO.get(c).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
															<c:choose>
																<c:when test="${cmtDTO.get(c).modified}">
																	<span class="date text-black-50 ml-5">(수정됨)</span>
																</c:when>
															</c:choose>
															<c:choose>
																<c:when test="${cmtDTO.get(c)._deleted eq true}">
																	<span class="date text-black-50 ml-5">(삭제됨)</span>
																</c:when>
															</c:choose>
														</div>
													</div>
													<div class="d-flex row justify-content-between" style="margin-left: 60px">
														<sec:authorize access="isAnonymous()">
															<div class="col d-flex align-items-center">
																<image class="loc_icon_medium me-1 ms-0" src="/image/icon/cmt_like_bw.png"></image>
																<span class="text-center align-middle fs-6">${cmtDTO.get(c).likeCount}</span>
																<image class="loc_icon_medium me-1" src="/image/icon/cmt_dislike_bw.png"></image>
																<span class="text-center align-middle fs-6">${cmtDTO.get(c).dislikeCount}</span>
															</div>
														</sec:authorize>
														<sec:authorize access="isAuthenticated()">
															<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
															<div class="col align-content-center">
																<c:choose>
																	<c:when test="${!empty cmtLikeList and !empty cmtDislikeList}">
																		<c:choose>
																			<c:when test="${cmtLikeList.get(c) eq null or cmtDislikeList.get(c) eq null}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/cmt_like_bw.png" id="cmt_btn_like_${c+1}"
																					   onclick="onClickLikeComment(this, ${c+1}, ${user_no}, 'cmt_like', ${cmtDTO.get(c).cmtNo}), ${cmtIndexList.get(c) + 1}"></image>
																				<span class="text-center align-middle fs-6" id="cmt_like_${c+1}">${cmtDTO.get(c).likeCount}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/cmt_dislike_bw.png" id="cmt_btn_dislike_${c+1}"
																					   onclick="onClickDislikeComment(this, ${c+1}, ${user_no}, 'cmt_dislike', ${cmtDTO.get(c).cmtNo}), ${cmtIndexList.get(c) + 1}"></image>
																				<span class="text-center align-middle fs-6" id="cmt_dislike_${c+1}">${cmtDTO.get(c).dislikeCount}</span>
																			</c:when>
																			<c:when test="${cmtLikeList.get(c) eq true or cmtDislikeList.get(c) eq false}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/cmt_like_color.png" id="cmt_btn_like_${c+1}"
																					   onclick="onClickLikeComment(this, ${c+1}, ${user_no}, 'cmt_like', ${cmtDTO.get(c).cmtNo}, ${cmtIndexList.get(c) + 1})"></image>
																				<span class="text-center align-middle fs-6" id="cmt_like_${c+1}">${cmtDTO.get(c).likeCount}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/cmt_dislike_bw.png" id="cmt_btn_dislike_${c+1}"
																					   onclick="onClickDislikeComment(this, ${c+1}, ${user_no}, 'cmt_dislike', ${cmtDTO.get(c).cmtNo}, ${cmtIndexList.get(c) + 1})"></image>
																				<span class="text-center align-middle fs-6" id="cmt_dislike_${c+1}">${cmtDTO.get(c).dislikeCount}</span>
																			</c:when>
																			<c:when test="${cmtLikeList.get(c) eq false or cmtDislikeList.get(c) eq true}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/cmt_like_bw.png" id ="cmt_btn_like_${c+1}"
																					   onclick="onClickLikeComment(this, ${c+1}, ${user_no}, 'cmt_like', ${cmtDTO.get(c).cmtNo}, ${cmtIndexList.get(c) + 1})"></image>
																				<span class="text-center align-middle fs-6" id="cmt_like_${c+1}">${cmtDTO.get(c).likeCount}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/cmt_dislike_color.png" id ="cmt_btn_dislike_${c+1}"
																					   onclick="onClickDislikeComment(this, ${c+1}, ${user_no}, 'cmt_dislike', ${cmtDTO.get(c).cmtNo}, ${cmtIndexList.get(c) + 1})"></image>
																				<span class="text-center align-middle fs-6" id="cmt_dislike_${c+1}">${cmtDTO.get(c).dislikeCount}</span>
																			</c:when>
																		</c:choose>
																	</c:when>
																</c:choose>
															</div>
															<c:choose>
																<c:when test="${user_no eq cmtDTO.get(c).user.user_no}">
																	<span class="d-none visually-hidden" id="cmt_user_email_${c}">${cmtDTO.get(c).user.user_email}</span>
																	<div class="d-flex col justify-content-end align-items-center">
																		<button class="btn btn-primary" onclick="openCmtEditMenu(${c})">수정</button>
																		<button class="btn btn-primary ms-2" onclick="onClickDeleteComment(${c})">삭제</button>
																	</div>
																</c:when>
																<c:otherwise>
																	<button class="btn btn-outline-danger col-2" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal"
																			onclick="openReportModal('${cmtDTO.get(c).cmtContent}', '${cmtDTO.get(c).user.user_nic}', 'COM', '${cmtDTO.get(c).cmtNo}')">댓글 신고</button>
																	<button class="btn btn-outline-danger col-2" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal"
																			onclick="openReportModal('', '${cmtDTO.get(c).user.user_nic}', 'PROFILE_PIC', '${cmtDTO.get(c).user.user_no}')">프로필 신고</button>
																</c:otherwise>
															</c:choose>
														</sec:authorize>
													</div>
													<div class="mt-2">
														<div id="cmt_content_${c}" class="visible">
															<p class="comment-text fs-5">${cmtDTO.get(c).cmtContent}</p>
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
							<div class="p-2 col-10">
								<div class="d-flex flex-row align-items-start">
									<c:set var="currentUserPic"><sec:authentication property="principal.user_profilePic"></sec:authentication></c:set>
									<img class="m-3" src="${currentUserPic}" width="60">
									<textarea id="commentArea" rows="6" maxlength="300" class="form-control ml-1 shadow-none textarea" placeholder="내용을 입력해주세요"></textarea>
								</div>
								<div class="mt-2 text-end">
									<button id="postCommentBtn" onclick="onClickPostComment()" class="btn btn-primary btn shadow-none" type="button">댓글 남기기</button>
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

		if (params.has("page") && params.has("cmtNo")){
            let cmtCount = ${cmtDTO.size()};

            let cmt;
            for (let i = 0; i < cmtCount; i++) {
				cmt = document.getElementById("cmt_no_" + i);
                if (cmt.innerText === params.get("cmtNo")){
                    let offsetTop = cmt.offsetTop - 100;
                    window.scrollTo({top:offsetTop, behavior:'smooth'});
                    break;
                }
            }
		} else {
            if (params.has("page")) {
                let offsetTop = document.querySelector("#pills-tab").offsetTop - 100;

                window.scrollTo({top:offsetTop, behavior: 'smooth'});
            }
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
        if (!window.confirm("장소를 삭제하시겠습니까?")) {
            return;
		}

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

    function onClickPermaDeleteLocation() {
        if (!window.confirm("장소를 영구삭제하시겠습니까?")) {
            return;
        }

        let param = location.search;

        console.log(param);
        console.log('/service/loc_perma_delete' + param);

        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action= "/service/loc_perma_delete" + param;

        document.body.appendChild(form);
        form.submit();
    }

    function onClickRollbackLocation() {
        if (!window.confirm("장소를 복원하시겠습니까?")) {
            return;
        }

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
<script defer>
    function reportSubmit() {
        let reportType = document.querySelector('input[name="report"]:checked').value;
        let reportContent = document.getElementById("repContent").value;
        let dupCheck = null;

        dupCheck = confirm("중복 체크를 해제하시겠습니까");

        if (reportType !== 'ETC') {
            reportContent = '';
        }

        let userNo = null;

        <sec:authorize access="isAuthenticated()">
        	userNo = Number.parseInt('<sec:authentication property="principal.user_no"/>');
        </sec:authorize>

        if (userNo === null) {
            alert('신고하기 위해서는 로그인을 해야합니다.');
            return;
        }

        $.ajax({
            type: "POST",
            url: "/rest/report_reg",
            data: {
                repType : reportType,
                repContent : reportContent,
                postNo : postNo,
                postType : postType,
                userNo : userNo,
				dupCheck : dupCheck
            },
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                if ("Found report in same post : You can only report once per post" === response){
                    alert("게시글에 대한 신고는 한번만 가능합니다.");
				}

                if ("Fail" === response){
                    alert("신고과정에 문제가 발생하였습니다.");
                }

                if ("Report Successful" === response){
                    alert("정상적으로 신고처리 되었습니다.");
                }
            }, error: function (e) {
                alert("신고 과정에 오류 발생");
                console.log(e);
            }
        });
    }

    function openReportModal(name, username, postTypeValue, postNoValue) {
        let repPostName = document.getElementById("repPostName");
        let userNick = document.getElementById("userNick");

        repPostName.innerText = "제목 : " + name;
        userNick.innerText = "작성자 : " + username;
        postType = postTypeValue;
        postNo = postNoValue
    }
</script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
