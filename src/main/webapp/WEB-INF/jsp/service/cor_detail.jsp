<%@ page import="com.project.love_data.dto.ReviewImageDTO" %>
<%@ page import="java.util.*" %>
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
	<link rel="stylesheet" href="/css/service/score.css">
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
							<button class="btn btn-link btn-block" type="button"
									style="text-decoration: none; color: #FF6699; font-weight: bold">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/cor_recommend" class="highlight-selected-text-menu">- 추천 코스</a></p>
							<p><a href="/service/cor_registration" class="highlight-not-selected-text-menu">- 코스 등록</a></p>
							<p><a href="/mypage_mycorse/1" class="highlight-not-selected-text-menu">- 코스 편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>
	<c:choose>
		<%--		없는 장소 번호로 조회시--%>
		<c:when test="${isNullCourse eq true}">
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
						<c:choose>
							<c:when test="${isBizUploader eq true}">
								<span class="best badge bg-primary col-2 ms-3 mb-1">업체</span>
							</c:when>
						</c:choose>
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
					<div class="row d-flex">
						<h5>작성자 : ${userNick}</h5>
					</div>
					<div class="row d-flex">
						<h5>리뷰 : ${revCount}</h5>
					</div>
				</div>
				<div class="row d-flex">
				</div>

				<div class="d-flex align-content-end flex-wrap">
					<sec:authorize access="isAuthenticated()">
						<c:choose>
							<c:when test="${isLiked eq true}">
								<img src="/image/icon/like/love_color.png" class="loc_icon_big me-2 ms-0" alt="찜하기"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'cor')">
							</c:when>
							<c:otherwise>
								<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2 ms-0" alt="찜하기"
									 onclick="onClickLike(this, <sec:authentication property="principal.user_no"/>, 'cor')">
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<img src="/image/icon/like/love_black.png" class="loc_icon_big me-2 ms-0" alt="찜하기">
					</sec:authorize>
					<span class="text-center align-middle fs-3 me-3" id="likeCount">${dto.likeCount}</span>
					<span class="visually-hidden">${dto.user_no}</span>
					<img src="/image/icon/share.png" class="loc_icon_big ms-0 me-2" style="max-height: 56px" onclick="copyURL()">
					<sec:authorize access="isAuthenticated()">
						<img src="/image/icon/comment.png" class="loc_icon_big me-2 mt-1" alt="리뷰" onclick="openReviewRegisterPopup()">
						<img src="/image/icon/report.png" class="loc_icon_big ms-2" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal"
								onclick="openReportModal('${dto.cor_name}', '${userNick}', 'COR', '${dto.cor_no}')">
					</sec:authorize>
				</div>
				<div class="d-flex align-content-end flex-wrap">
					<sec:authorize access="isAuthenticated()">
						<c:set var="currUserNo"><sec:authentication property="principal.user_no"></sec:authentication></c:set>
						<c:choose>
							<c:when test="${dto.user_no eq currUserNo}">
								<sec:authorize access="hasAnyRole('ADMIN')">
									<img src="/image/icon/edit.png" class="loc_icon_big ms-0" style="max-height: 56px;" onclick="location.href='/service/cor_edit?corNo=${dto.cor_no}'">
									<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="코스 삭제"
										 onclick="onClickRemoveCourse()">
									<img src="/image/icon/rollback.png" class="loc_icon_big me-2" alt="코스 복원"
										 onclick="onClickRollbackCourse()">
									<Button class="btn btn-primary ms-3" onclick="onClickPermaDeleteCourse()">영구삭제</Button>
								</sec:authorize>
								<sec:authorize access="hasRole('USER') && !hasRole('ADMIN')">
									<img src="/image/icon/edit.png" class="loc_icon_big ms-0" style="max-height: 56px;" onclick="location.href='/service/cor_edit?corNo=${dto.cor_no}'">
									<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="코스 삭제"
										 onclick="onClickRemoveCourse()">
								</sec:authorize>
							</c:when>
							<c:otherwise>
								<sec:authorize access="hasAnyRole('ADMIN')">
									<img src="/image/icon/edit.png" class="loc_icon_big ms-0" style="max-height: 56px;" onclick="location.href='/service/cor_edit?corNo=${dto.cor_no}'">
									<img src="/image/icon/trash.png" class="loc_icon_big me-2" alt="코스 삭제"
										 onclick="onClickRemoveCourse()">
									<img src="/image/icon/rollback.png" class="loc_icon_big me-2" alt="코스 복원"
										 onclick="onClickRollbackCourse()">
									<Button class="btn btn-primary" onclick="onClickPermaDeleteCourse()">영구삭제</Button>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
				</div>
				<span class="d-none" id="cor_no">${dto.cor_no}</span>
			</div>
		</div>
		<div class="row justify-content-md-start">
			<ul class="nav nav-pills nav-fill col-5" id="pills-tab" role="tablist"
				style="height:50px; padding-top: 5px; padding-bottom: 5px">
				<c:choose>
					<c:when test="${locList ne null}">
						<c:forEach var="i" begin="0" end="${locList.size()-1}">
							<c:choose>
								<c:when test="${param.containsKey('page') eq true}">
									<li class="nav-item" role="presentation">
										<button class="nav-link" id="course-loc-tab-${i}" data-bs-toggle="pill"
												data-bs-target="#course-loc-${i}" type="button" role="tab" aria-controls="course-loc-tab-${i}"
												onclick="removeURLParam('page')"
												aria-selected="true">${i+1}</button>
									</li>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${i eq 0}">
											<li class="nav-item" role="presentation">
												<button class="nav-link active" id="course-loc-tab-${i}" data-bs-toggle="pill"
														data-bs-target="#course-loc-${i}" type="button" role="tab" aria-controls="course-loc-tab-${i}"
														onclick="removeURLParam('page')"
														aria-selected="true">${i+1}</button>
											</li>
										</c:when>
										<c:otherwise>
											<li class="nav-item" role="presentation">
												<button class="nav-link" id="course-loc-tab-${i}" data-bs-toggle="pill"
														data-bs-target="#course-loc-${i}" type="button" role="tab" aria-controls="course-loc-tab-${i}"
														onclick="removeURLParam('page')"
														aria-selected="true">${i+1}</button>
											</li>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
				</c:choose>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="course-map-tab" data-bs-toggle="pill"
							data-bs-target="#course-map" type="button" role="tab" aria-controls="course-map" onclick="removeURLParam('page')"
							aria-selected="true">경로
					</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="course-info-tab" data-bs-toggle="pill"
							data-bs-target="#course-info" type="button" role="tab" aria-controls="course-info" onclick="removeURLParam('page')"
							aria-selected="true">설명
					</button>
				</li>
				<c:choose>
					<c:when test="${dto.est_type eq 'date'}">
						<li class="nav-item" role="presentation">
							<button class="nav-link" id="course-acc-tab" data-bs-toggle="pill"
									data-bs-target="#course-acc" type="button" role="tab" aria-controls="course-acc" onclick="removeURLParam('page')"
									aria-selected="true">숙박 정보
							</button>
						</li>
					</c:when>
				</c:choose>
				<li class="nav-item" role="presentation">
				<c:choose>
					<c:when test="${param.containsKey('page') eq true}">
							<button class="mw-100 mh-100 nav-link active" id="course-review-tab" data-bs-toggle="pill"
									data-bs-target="#course-review" type="button" role="tab" aria-controls="course-review"
									aria-selected="false" onclick="addURLParam('page', '1')">리뷰
							</button>
					</c:when>
					<c:otherwise>
							<button class="mw-100 mh-100 nav-link" id="course-review-tab" data-bs-toggle="pill"
									data-bs-target="#course-review" type="button" role="tab" aria-controls="course-review"
									aria-selected="false" onclick="addURLParam('page', '1')">리뷰
							</button>
					</c:otherwise>
				</c:choose>
				</li>
			</ul>
			<div class="tab-content" id="pills-tabContent">
				<c:choose>
					<c:when test="${locList ne null}">
						<c:forEach var="i" begin="0" end="${locList.size()-1}">
							<c:choose>
								<c:when test="${param.containsKey('page') eq true}">
									<div class="tab-pane fade" id="course-loc-${i}" role="tabpanel" aria-labelledby="course-loc-tab-${i}">
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${i eq 0}">
											<div class="tab-pane fade show active" id="course-loc-${i}" role="tabpanel" aria-labelledby="course-loc-tab-${i}">
										</c:when>
										<c:otherwise>
											<div class="tab-pane fade" id="course-loc-${i}" role="tabpanel" aria-labelledby="course-loc-tab-${i}">
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${locList.get(i) eq null}">
									<div class="container mt-0">
										<div class="d-flex justify-content-start row">
											<div class="col-md-10">
												<div class="d-flex my-lg-4 flex-column">
													<div class="row my-lg-4">
														<div class="col d-flex">
															<div class="row mt-3 d-flex justify-content-center">
																<span>삭제된 장소입니다.</span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="container mt-0">
										<div class="d-flex justify-content-start row">
											<div class="col-md-10">
												<div class="d-flex my-lg-4 flex-column">
													<button class="btn-lg btn-primary" onclick="location.href='/service/loc_detail?locNo=${locList.get(i).loc_no}'">장소 바로 가기</button>
													<div class="row my-lg-4">
														<div class="col-md-7">
															<div class="card shadow-sm">
																<div class="d-flex justify-content-center">
																	<img class="bd-placeholder-img card-img" width="70%"
																		 height="300"
																		 alt="${locList.get(i).loc_name}"
																		 src="${locList.get(i).thumbnail}"
																		 id="loc_img_${i}"
																		 name="loc_img_${i}"
																		 preserveAspectRatio="xMidYMid slice" focusable="false">
																</div>
															</div>
														</div>
														<div class="col d-flex">
															<div class="row mt-3 d-flex justify-content-center">
																<span>${i+1}번째 장소</span>
																<span>장소 이름 : ${locList.get(i).loc_name}</span>
																<span>장소 설명 : ${locList.get(i).info}</span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							</div>
						</c:forEach>
					</c:when>
				</c:choose>
				<%--      경로--%>
				<div class="tab-pane fade" id="course-map" role="tabpanel" aria-labelledby="course-map-tab">
					<iframe width="1200" height="800" src="/embeded/pathFinding?corNo=${dto.cor_no}"></iframe>
				</div>
				<%--      설명--%>
				<div class="tab-pane fade" id="course-info" role="tabpanel" aria-labelledby="course-info-tab">
					<div class="container">
						<div class="d-flex mt-3">
							<span class="fs-5" style="white-space: pre-wrap;">${dto.info}</span>
						</div>
					</div>
				</div>
				<c:choose>
					<c:when test="${dto.est_type eq 'date'}">
						<%--      숙박 정보--%>
						<div class="tab-pane fade" id="course-acc" role="tabpanel" aria-labelledby="course-acc-tab">
							<div class="container">
								<div class="d-flex mt-3">
									<span class="fs-5" style="white-space: pre-wrap;">${dto.accommodations_info}</span>
								</div>
							</div>
						</div>
					</c:when>
				</c:choose>
				<%--    리뷰--%>
				<c:choose>
					<c:when test="${param.containsKey('page') eq true}">
						<div class="tab-pane fade show active" id="course-review" role="tabpanel" aria-labelledby="course-review-tab">
					</c:when>
					<c:otherwise>
						<div class="tab-pane fade" id="course-review" role="tabpanel" aria-labelledby="course-review-tab">
					</c:otherwise>
				</c:choose>
					<div class="container mt-0">
						<div class="d-flex justify-content-start row">
							<div class="col-md-12">
								<div class="d-flex flex-column">
									<c:set var="revDTO" value="${resRevDTO.dtoList}"></c:set>
									<c:choose>
										<c:when test="${!empty bestRevList and paramValues.page[0] eq 1}">
											<c:forEach var="b" begin="0" end="${bestRevList.size()-1}">
												<div class="p-2" style="background: #FFC2D6; max-width: 1020px">
													<span class="best badge bg-primary fs-6 mb-2">BEST</span>
													<div class="d-flex flex-row align-items-center">
														<img src="${bestRevUserPicList.get(b)}"
															 class="loc_comment-profile-image-wh">
														<div class="flex-column">
															<p class="visually-hidden" id="best_rev_id_${b}">${bestRevList.get(b).revUuid}</p>
															<p class="visually-hidden" id="best_rev_no_${b}">${bestRevList.get(b).revNo}</p>
															<span class="d-block font-weight-bold name">
																	${bestRevList.get(b).userNickname}
															</span>
															<span class="date text-black-50 ml-5">${bestRevList.get(b).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
															<c:choose>
																<c:when test="${bestRevList.get(b).modified}">
																	<span class="date text-black-50 ml-5">(수정됨)</span>
																</c:when>
															</c:choose>
															<div class="row">
																<div class="d-flex align-items-md-baseline">
																	<span class="date text-black-50 me-1">총점</span>
																	<c:forEach var="i" begin="1" end="5">
																		<c:choose>
																			<c:when test="${bestRevList.get(b).sc_total >= i}">
																				<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																			</c:when>
																			<c:otherwise>
																				<img class="sc_icon_small ms-1" src="/image/icon/star_black.png" alt="star_black">
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<span class="date text-black-50 ms-4 me-1">장소추천도</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${bestRevList.get(b).sc_loc}</span>
																	<span class="date text-black-50 ms-4 me-1">이동편리성</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${bestRevList.get(b).sc_move}</span>
																	<span class="date text-black-50 ms-4 me-1">시간소요도</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${bestRevList.get(b).sc_time}</span>
																	<span class="date text-black-50 ms-4 me-1">재방문의사</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${bestRevList.get(b).sc_revisit}</span>
																</div>
															</div>
														</div>
													</div>
													<div class="d-flex row justify-content-between" style="margin-left: 60px">
														<sec:authorize access="isAnonymous()">
															<div class="col d-flex align-items-center">
																<image class="loc_icon_medium me-1 ms-0" src="/image/icon/rev_like_bw.png"></image>
																<span class="ms-1 text-center align-middle fs-6">${bestRevList.get(b).rev_like}</span>
																<image class="loc_icon_medium me-1" src="/image/icon/rev_dislike_bw.png"></image>
																<span class="ms-1 text-center align-middle fs-6">${bestRevList.get(b).rev_dislike}</span>
															</div>
														</sec:authorize>
														<sec:authorize access="isAuthenticated()">
															<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
															<div class="col d-flex align-items-center">
																<c:choose>
																	<c:when test="${!empty bestRevLikeList and !empty bestRevDislikeList}">
																		<c:choose>
																			<c:when test="${bestRevLikeList.get(b) eq null or bestRevDislikeList.get(b) eq null}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_bw.png" id="best_rev_btn_like_${b+1}"
																					   onclick="onClickLikeBestReview(this, ${b+1},  ${bestRevIndexList.get(b) + 1}, ${user_no}, 'rev_like', ${bestRevList.get(b).revNo})"></image>
																				<span class="ms-1 text-center align-middle fs-6" id="best_rev_like_${b+1}">${bestRevList.get(b).rev_like}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_bw.png" id="best_rev_btn_dislike_${b+1}"
																					   onclick="onClickDislikeBestReview(this, ${b+1}, ${bestRevIndexList.get(b) + 1}, ${user_no}, 'rev_dislike', ${bestRevList.get(b).revNo})"></image>
																				<span class="ms-1 text-center align-middle fs-6" id="best_rev_dislike_${b+1}">${bestRevList.get(b).rev_dislike}</span>
																			</c:when>
																			<c:when test="${bestRevLikeList.get(b) eq true or bestRevDislikeList.get(b) eq false}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_color.png" id="best_rev_btn_like_${b+1}"
																					   onclick="onClickLikeBestReview(this, ${b+1},  ${bestRevIndexList.get(b) + 1}, ${user_no}, 'rev_like', ${bestRevList.get(b).revNo})"></image>
																				<span class="ms-1 text-center align-middle fs-6" id="best_rev_like_${b+1}">${bestRevList.get(b).rev_like}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_bw.png" id="best_rev_btn_dislike_${b+1}"
																					   onclick="onClickDislikeBestReview(this, ${b+1}, ${bestRevIndexList.get(b) + 1}, ${user_no}, 'rev_dislike', ${bestRevList.get(b).revNo})"></image>
																				<span class="ms-1 text-center align-middle fs-6" id="best_rev_dislike_${b+1}">${bestRevList.get(b).rev_dislike}</span>
																			</c:when>
																			<c:when test="${bestRevLikeList.get(b) eq false or bestRevDislikeList.get(b) eq true}">
																				<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_bw.png" id ="best_rev_btn_like_${b+1}"
																					   onclick="onClickLikeBestReview(this, ${b+1},  ${bestRevIndexList.get(b) + 1}, ${user_no}, 'rev_like', ${bestRevList.get(b).revNo})"></image>
																				<span class="ms-1 text-center align-middle fs-6" id="best_rev_like_${b+1}">${bestRevList.get(b).rev_like}</span>
																				<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_color.png" id ="best_rev_btn_dislike_${b+1}"
																					   onclick="onClickDislikeBestReview(this, ${b+1}, ${bestRevIndexList.get(b) + 1},${user_no}, 'rev_dislike', ${bestRevList.get(b).revNo})"></image>
																				<span class="ms-1 text-center align-middle fs-6" id="best_rev_dislike_${b+1}">${bestRevList.get(b).rev_dislike}</span>
																			</c:when>
																		</c:choose>
																	</c:when>
																</c:choose>
															</div>
														</sec:authorize>
													</div>
													<div class="mt-2">
														<div id="best_rev_content_${b}" class="visible">
															<p class="m-0 comment-text fs-5">${bestRevList.get(b).revContent}</p>
														</div>
													</div>
												</div>
											</c:forEach>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${0 != revDTO.size()}">
											<c:forEach var="c" begin="0" end="${revDTO.size()-1}">
												<div class="bg-white p-2">
													<div class="d-flex flex-row align-items-center"><img
															src="${revUserPicList.get(c)}"
															class="loc_comment-profile-image-wh">
														<div class="flex-column">
															<p class="visually-hidden" id="rev_id_${c}">${revDTO.get(c).revUuid}</p>
															<p class="visually-hidden" id="rev_no_${c}">${revDTO.get(c).revNo}</p>
															<span class="d-block font-weight-bold name">${revDTO.get(c).userNickname}</span>
															<div>
																<span class="date text-black-50 ml-5">${revDTO.get(c).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
																<c:choose>
																	<c:when test="${revDTO.get(c).modified eq true}">
																		<span class="date text-primary ml-5">(수정됨)</span>
																	</c:when>
																</c:choose>
																<c:choose>
																	<c:when test="${revDTO.get(c)._deleted eq true}">
																		<span class="date text-danger ml-5">(삭제됨)</span>
																	</c:when>
																</c:choose>
															</div>
															<div class="row">
																<div class="d-flex align-items-md-baseline">
																	<span class="date text-black-50 me-1">총점</span>
																	<c:forEach var="i" begin="1" end="5">
																		<c:choose>
																			<c:when test="${revDTO.get(c).sc_total >= i}">
																				<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																			</c:when>
																			<c:otherwise>
																				<img class="sc_icon_small ms-1" src="/image/icon/star_black.png" alt="star_black">
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<span class="date text-black-50 ms-4 me-1">장소추천도</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${revDTO.get(c).sc_loc}</span>
																	<span class="date text-black-50 ms-4 me-1">이동편리성</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${revDTO.get(c).sc_move}</span>
																	<span class="date text-black-50 ms-4 me-1">시간소요도</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${revDTO.get(c).sc_time}</span>
																	<span class="date text-black-50 ms-4 me-1">재방문의사</span>
																	<img class="sc_icon_small ms-1" src="/image/icon/star_color.png" alt="star_color">
																	<span class="ms-1">${revDTO.get(c).sc_revisit}</span>
																</div>
															</div>
															<div class="d-flex row justify-content-between">
																<sec:authorize access="isAnonymous()">
																	<div class="col d-flex align-items-center">
																		<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_bw.png"></image>
																		<span class="ms-1 text-center align-middle fs-6">${revDTO.get(c).rev_like}</span>
																		<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_bw.png"></image>
																		<span class="ms-1 text-center align-middle fs-6">${revDTO.get(c).rev_dislike}</span>
																	</div>
																</sec:authorize>
																<sec:authorize access="isAuthenticated()">
																	<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
																	<div class="col d-flex align-items-center">
																	<c:choose>
																		<c:when test="${!empty revLikeList and !empty revDislikeList}">
																			<c:choose>
																				<c:when test="${revLikeList.get(c) eq null or revDislikeList.get(c) eq null}">
																					<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_bw.png" id="rev_btn_like_${c+1}"
																						   onclick="onClickLikeReview(this, ${c+1}, ${user_no}, 'rev_like', ${revDTO.get(c).revNo}, ${revIndexList.get(c) + 1})"></image>
																					<span class="ms-1 text-center align-middle fs-6" id="rev_like_${c+1}">${revDTO.get(c).rev_like}</span>
																					<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_bw.png" id="rev_btn_dislike_${c+1}"
																						   onclick="onClickDislikeReview(this, ${c+1}, ${user_no}, 'rev_dislike', ${revDTO.get(c).revNo}, ${revIndexList.get(c) + 1})"></image>
																					<span class="ms-1 text-center align-middle fs-6" id="rev_dislike_${c+1}">${revDTO.get(c).rev_dislike}</span>
																				</c:when>
																				<c:when test="${revLikeList.get(c) eq true or revDislike.get(c) eq false}">
																					<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_color.png" id="rev_btn_like_${c+1}"
																						   onclick="onClickLikeReview(this, ${c+1}, ${user_no}, 'rev_like', ${revDTO.get(c).revNo}, ${revIndexList.get(c) + 1})"></image>
																					<span class="ms-1 text-center align-middle fs-6" id="rev_like_${c+1}">${revDTO.get(c).rev_like}</span>
																					<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_bw.png" id="rev_btn_dislike_${c+1}"
																						   onclick="onClickDislikeReview(this, ${c+1}, ${user_no}, 'rev_dislike', ${revDTO.get(c).revNo}, ${revIndexList.get(c) + 1})"></image>
																					<span class="ms-1 text-center align-middle fs-6" id="rev_dislike_${c+1}">${revDTO.get(c).rev_dislike}</span>
																				</c:when>
																				<c:when test="${revLikeList.get(c) eq false or revDislike.get(c) eq true}">
																					<image class="loc_icon_medium me-1 ms-0 clickable-image" src="/image/icon/rev_like_bw.png" id="rev_btn_like_${c+1}"
																						   onclick="onClickLikeReview(this, ${c+1}, ${user_no}, 'rev_like', ${revDTO.get(c).revNo}, ${revIndexList.get(c) + 1})"></image>
																					<span class="ms-1 text-center align-middle fs-6" id="rev_like_${c+1}">${revDTO.get(c).rev_like}</span>
																					<image class="loc_icon_medium me-1 clickable-image" src="/image/icon/rev_dislike_color.png" id="rev_btn_dislike_${c+1}"
																						   onclick="onClickDislikeReview(this, ${c+1}, ${user_no}, 'rev_dislike', ${revDTO.get(c).revNo}, ${revIndexList.get(c) + 1})"></image>
																					<span class="ms-1 text-center align-middle fs-6" id="rev_dislike_${c+1}">${revDTO.get(c).rev_dislike}</span>
																				</c:when>
																			</c:choose>
																		</c:when>
																	</c:choose>
																	</div>
																	<c:choose>
																		<c:when test="${user_no eq revDTO.get(c).userNo}">
																			<div class="col d-flex justify-content-end align-content-center">
																				<c:choose>
																					<c:when test="${revDTO.get(c)._deleted eq true}">
																						<button class="btn btn-primary" onclick="rollbackReview(${revDTO.get(c).revNo})">롤백</button>
																					</c:when>
																					<c:otherwise>
																						<button class="btn btn-primary" onclick="openReviewEditPopup(${revDTO.get(c).revNo})">수정</button>
																						<button class="btn btn-primary ms-2" onclick="deleteReview('${revDTO.get(c).revNo}', '${revDTO.get(c).revUuid}')">삭제</button>
																					</c:otherwise>
																				</c:choose>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<button class="btn btn-outline-danger col-2" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal"
																					onclick="openReportModal('${revDTO.get(c).revContent}', '${revDTO.get(c).userNickname}', 'REV', '${revDTO.get(c).revNo}')">리뷰 신고</button>
																			<button class="btn btn-outline-danger col-2" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal"
																					onclick="openReportModal('', '${revDTO.get(c).userNickname}', 'PROFILE_PIC', '${revDTO.get(c).userNo}')">프로필 신고</button>
<%--																			<div class="dropdown col-1">--%>
<%--																				<button class="btn btn-outline-danger dropdown-toggle" type="button" id="dropdownReportMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
<%--																					신고--%>
<%--																				</button>--%>
<%--																				<div class="dropdown-menu" aria-labelledby="dropdownReportMenuButton">--%>
<%--																					<a class="dropdown-item" onclick="openReportModal('${revDTO.get(c).revContent}', '${revDTO.get(c).userNickname}', 'REV', '${revDTO.get(c).revNo}')">리뷰 신고</a>--%>
<%--																					<a class="dropdown-item" onclick="openReportModal('${revDTO.get(c).revContent}', '${revDTO.get(c).userNickname}', 'REV', '${revDTO.get(c).revNo}')">프로필 신고</a>--%>
<%--																				</div>--%>
<%--																			</div>--%>
																		</c:otherwise>
																	</c:choose>
																</sec:authorize>
															</div>
														</div>
													</div>
													<div class="mt-3">
<%--												리뷰 이미지 첨부		--%>
														<div id="rev_content_${c}" class="visible">
															<p class="comment-text">${revDTO.get(c).revContent}</p>
														</div>
														<c:set var="revImg" value="${revImgList.get(c)}"></c:set>
														<c:choose>
															<c:when test="${!empty revImg}">
																<div class="col-md-3">
																	<div class="card mb-4 shadow-sm">
																		<div class="d-flex justify-content-center">
																			<img class="bd-placeholder-img card-img" width="100%"
																				 height="200"
																				 alt="${revDTO.get(c).revNo}"
																				 src="${revImg.get(0).img_url}"
																				 id="revImg_${c}"
																				 name="revImg_${c}"
																				 preserveAspectRatio="xMidYMid slice" focusable="false">
																			<div class="d-flex justify-content-between h-25 card-img-overlay" style="top: 40%">
																				<button class="btn btn-sm" id="imgPrev" name="imgPrev" onclick="clickRevImgPrev(${c})">
																					<img src="/image/icon/left-arrow.png" width="30px" height="30px" alt="imgPrev">
																				</button>
																				<button class="btn btn-sm" id="imgNext" name="imgNext" onclick="clickRevImgNext(${c})">
																					<img src="/image/icon/right-arrow.png" width="30px" height="30px" alt="imgNext">
																				</button>
																					<%--									<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="currentColor"--%>
																					<%--										 class="bi bi-arrow-right-circle" viewBox="0 0 100 100">--%>
																					<%--										<path fill-rule="evenodd"--%>
																					<%--											  d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>--%>
																					<%--									</svg>--%>
																			</div>
																			<div class="d-flex justify-content-end h-25 card-img-overlay" style="top : 75%">
																				<div class="badge bg-dark d-flex justify-content-center" style="width: 6rem; height: 2rem">
																					<p class="text-center fs-3 align-self-center mb-0" id="rev_idx_Indicator_${c}"
																					   name="rev_idx_Indicator_${c}">
																						1/${revImgList.get(c).size()}
																					</p>
																				</div>
																			</div>
																			</img>
																			<span class="visually-hidden" id="revImgListSize_${c}"
																				  name="imgListSize">${revImgList.get(c).size()}</span>
																			<span class="visually-hidden" id="revImgListIndex_${c}" name="revImgListIndex_${c}">0</span>
																		</div>
																	</div>
																</div>
															</c:when>
														</c:choose>
													</div>
												</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<p>등록된 리뷰가 없습니다.</p>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
					<form action="/service/rev_registration" id="form" name="form" method="post" enctype="multipart/form-data">
						<input type="hidden" id="rev_no" name="rev_no">
						<input type="hidden" id="user_no_input" name="user_no" value="${user_no}">
						<input type="hidden" id="cor_no_input" name="cor_no" value="${dto.cor_no}">
						<input type="hidden" id="rev_content_input" name="rev_content_input" value="">
						<input type="hidden" id="rev_total_rate" name="rev_total_rate" value="">
						<input type="hidden" id="rev_loc_rate" name="rev_loc_rate" value="">
						<input type="hidden" id="rev_move_rate" name="rev_move_rate" value="">
						<input type="hidden" id="rev_time_rate" name="rev_time_rate" value="">
						<input type="hidden" id="rev_revisit_rate" name="rev_revisit_rate" value="">
<%--						<input class="visually-hidden" id="files" name="files" type="file" multiple--%>
<%--							   accept="image/*">--%>
						<input type="hidden" id="resultType" name="resultType" value="review"/>
						<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
						<!--
						<input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
						 -->
						<!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
					</form>


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
										   href="/service/cor_detail?corNo=${dto.cor_no}&page=${resRevDTO.pageList.get(j - 1)}">${resRevDTO.pageList.get(j - 1)}</a>
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
<%--<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"--%>
<%--		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"--%>
<%--		crossorigin="anonymous"></script>--%>
<script defer src="/js/bootstrap.js"></script>
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>
<script>
    window.onload = function() {
        let params = new URLSearchParams(location.search);

        if (params.has("page") && params.has("revNo")){
            let revCount = ${revDTO.size()};

            let rev;
            for (let i = 0; i < revCount; i++) {
                rev = document.getElementById("rev_no_" + i);
                if (rev.innerText === params.get("revNo")){
                    let offsetTop = rev.offsetTop - 100;
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

    var revImgList = [];
    let tempStr = "";

    <c:choose>
		<c:when test="${revImgStringURLList ne null}">
    		<%--console.log("${revImgStringURLList.size()}")--%>
			<c:forEach var="i" begin="0" end="${revImgStringURLList.size()-1}">
				tempStr = "${revImgStringURLList.get(i)}";
    			revImgList.push(tempStr);
			</c:forEach>
		</c:when>
	</c:choose>

    // console.log(revImgList);
    // console.log(revImgList[0]);
</script>
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

    function clickRevImgNext(currIndex) {
        var imgDisplay = document.getElementById("revImg_" + currIndex);
        var index = document.getElementById("revImgListIndex_" + currIndex);
        var temp = revImgList[currIndex];
        var imgList;
        var tempIndex = parseInt(index.innerText);
        var indexIndicator = document.getElementById("rev_idx_Indicator_" + currIndex);

        imgList = temp.split(';<>;');

        if (imgList.length > tempIndex + 1) {
            index.innerText = tempIndex + 1;
            tempIndex += 1;
            imgDisplay.src = imgList[tempIndex];
            indexIndicator.innerText = (tempIndex + 1) + "/" + imgList.length;
        }
    }

    function clickRevImgPrev(currIndex) {
        var imgDisplay = document.getElementById("revImg_" + currIndex);
        var index = document.getElementById("revImgListIndex_" + currIndex);
        var temp = revImgList[currIndex];
        var imgList;
        var tempIndex = parseInt(index.innerText);
        var indexIndicator = document.getElementById("rev_idx_Indicator_" + currIndex);

        imgList = temp.split(';<>;');

        if (0 <= tempIndex - 1) {
            index.innerText = tempIndex - 1;
            tempIndex -= 1;
            imgDisplay.src = imgList[tempIndex];
            indexIndicator.innerText = (tempIndex + 1) + "/" + imgList.length;
        }
    }

    function returnToCoruseList() {
        location.href = "/service/cor_recommend/list";
	}

    function onClickPostReview(reviewMapParam, imgInput) {
        let form = document.getElementById("form");

        let user_no_input = document.getElementById("user_no_input");
        let cor_no_input = document.getElementById("cor_no_input");
        let rev_content_input = document.getElementById("rev_content_input");
        let rev_total_rate = document.getElementById("rev_total_rate");
        let rev_loc_rate = document.getElementById("rev_loc_rate");
        let rev_move_rate = document.getElementById("rev_move_rate");
        let rev_time_rate = document.getElementById("rev_time_rate");
        let rev_revisit_rate = document.getElementById("rev_revisit_rate");
        let files_input = document.getElementById("files");

        <sec:authorize access="isAuthenticated()">
       	 	user_no_input.value = "<sec:authentication property="principal.user_no"/>";
		</sec:authorize>
        cor_no_input.value = "${dto.cor_no}";
        rev_content_input.value = reviewMapParam.get("commentInput");
		rev_total_rate.value = reviewMapParam.get("totalRate");
		rev_loc_rate.value = reviewMapParam.get("locRate");
		rev_move_rate.value = reviewMapParam.get("moveRate");
		rev_time_rate.value = reviewMapParam.get("timeRate");
		rev_revisit_rate.value = reviewMapParam.get("revisitRate");

		form.append(imgInput);

        form.submit();
    }

    function onClickEditReview(reviewMapParam, imgInput) {
        let form = document.getElementById("form");

        let rev_no = document.getElementById("rev_no");
        let user_no_input = document.getElementById("user_no_input");
        let cor_no_input = document.getElementById("cor_no_input");
        let rev_content_input = document.getElementById("rev_content_input");
        let rev_total_rate = document.getElementById("rev_total_rate");
        let rev_loc_rate = document.getElementById("rev_loc_rate");
        let rev_move_rate = document.getElementById("rev_move_rate");
        let rev_time_rate = document.getElementById("rev_time_rate");
        let rev_revisit_rate = document.getElementById("rev_revisit_rate");
        let files_input = document.getElementById("files");

        rev_no.value = reviewMapParam.get("revNo");
        <sec:authorize access="isAuthenticated()">
        user_no_input.value = "<sec:authentication property="principal.user_no"/>";
        </sec:authorize>
        cor_no_input.value = "${dto.cor_no}";
        rev_content_input.value = reviewMapParam.get("commentInput");
        rev_total_rate.value = reviewMapParam.get("totalRate");
        rev_loc_rate.value = reviewMapParam.get("locRate");
        rev_move_rate.value = reviewMapParam.get("moveRate");
        rev_time_rate.value = reviewMapParam.get("timeRate");
        rev_revisit_rate.value = reviewMapParam.get("revisitRate");

        form.action = "/service/rev_edit"
        form.append(imgInput);

        form.submit();
    }

    function onClickDeleteReview(index) {
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

    function submitUpdateReview(index) {
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
<script defer>
    let input = document.getElementById("imgInput");
    let isBuffered = false;

    function onClickAddImage() {
        $('#imgInput').trigger('click');
    }

    function toggleAddDelBtn(offset) {
        for (let i = 1; i <= 10; i++) {
            let btnAddParent = document.getElementById("imgAdd_" + i).parentElement;
            let btnDelParent = document.getElementById("imgDel_" + i).parentElement;
            let btnSelParent = document.getElementById("imgSel_" + i).parentElement;

            if (offset < i) {
                btnAddParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay');
                btnDelParent.setAttribute('class', 'd-flex justify-content-end card-img-overlay p-0 visually-hidden');
                btnSelParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay p-0 visually-hidden');
            } else {
                btnAddParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay visually-hidden');
                btnDelParent.setAttribute('class', 'd-flex justify-content-end card-img-overlay p-0');
                btnSelParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay p-0');
            }
        }
    }

    <%--						http://yoonbumtae.com/?p=3304 --%>

    function readImage() {
        let fileList = Array.from(input.files);

        console.log(input.files);
        console.log(fileList);
        fileList.forEach((file, index) => {
            let reader = new FileReader();
            console.log((index) + "번 째 아이템이 등록되었습니다.");
            let item = document.getElementById("img_" + (index + 1));
            reader.onload = e => {
                item.src = e.target.result;
                item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            if (index != 3) {
                document.getElementById("img_" + (index + 2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            reader.readAsDataURL(file);
            console.log(item);
        })

        // // 기존에 있던 이미지 지우기
        for (let i = fileList.length + 1; i <= 10; i++) {
            if (i === (fileList.length + 1)) {
                document.getElementById("img_" + i).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            } else {
                document.getElementById("img_" + i).parentElement.setAttribute("class", "card col-3 p-0 m-2 visually-hidden");
            }

            document.getElementById("img_" + i).src = "/image/icon/480px-Solid_white.png";
        }
        toggleAddDelBtn(fileList.length);
        // onSelectImage(selectedImageIndex + 1);
    }

    function deleteImage(obj) {
        <%-- https://stackoverflow.com/questions/16943605/remove-a-filelist-item-from-a-multiple-inputfile  --%>
        let dt = new DataTransfer();
        dt.files = input.files;

        let objId = obj.id.split('_');
        let index = objId[objId.length - 1];
        console.log(index);
        for (let file of input.files) {
            if (file !== input.files[index - 1]) {
                dt.items.add(file);
            }
        }

        if (selectedImageParent !== null) {
            if (selectedImageIndex < index) {
                onSelectImage(selectedImageIndex);
            } else {
                if (Number(index) + 1 == selectedImageIndex) {
                    onSelectImage(index);
                } else {
                    onClearSelecteImage();
                }
                // onClearSelecteImage();
            }
        }

        for (let i = 1; i <= 10; i++) {
            if (i >= index) {
                if (i !== 10) {
                    document.getElementById("img_" + i).src = document.getElementById("img_" + (i + 1)).src;
                } else {
                    document.getElementById("img_" + i).src = "/image/icon/480px-Solid_white.png";
                }
            }

            // if (dt.items.length+1 == i) {
            //     document.getElementById("imgAdd_"+i).parentElement.setAttribute("class", "d-flex justify-content-center card-img-overlay");
            //     document.getElementById("imgDel_"+i).parentElement.setAttribute("class", "d-flex justify-content-end card-img-overlay p-0 visually-hidden");
            // }

            if (dt.items.length + 1 < i) {
                document.getElementById("img_" + i).parentElement.setAttribute("class", "card col-3 p-0 m-2 visually-hidden");
            }
        }

        input.files = dt.files;
        console.log(dt.files);
        console.log(input.files);

        for (let i = input.files.length + 1; i <= 10; i++) {
            let img = document.getElementById("img_" + i);
            img.src = "/image/icon/480px-Solid_white.png";
        }

        toggleAddDelBtn(input.files.length);
    }
</script>
<script defer>
    function openReviewRegisterPopup() {
        var openReviewRegisterPop = window.open("/popup/reviewRegisterPopup?corName=${dto.cor_name}&corNo=${dto.cor_no}", "pop", "width=1200,height=600, scrollbars=yes, resizable=yes");
    }

    function reviewRegisterCallBack(reviewMapParam, imgInput) {
        onClickPostReview(reviewMapParam, imgInput);
    }

    function openReviewEditPopup(revNo) {
        let url = "/popup/reviewEditPopup?corName=${dto.cor_name}&revNo=" + revNo;
        var openReviewEditPop = window.open(url, "pop", "width=1200,height=600, scrollbars=yes, resizable=yes");
    }

    function reviewEditCallBack(reviewMapParam, imgInput) {
        onClickEditReview(reviewMapParam, imgInput);
	}

    function deleteReview(revNo, revUuid) {
        if (!window.confirm("리뷰를 삭제하시겠습니까?")) {
            return;
        }

        let url = "/service/rev_del";
        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action = url;

        let input = [];
        for (let i = 0; i < 4; i++) {
            input[i] = document.createElement("input");
            $(input[i]).attr("type", "hidden");

            if (i === 0) {
                $(input[0]).attr("name", "cor_no");
                $(input[0]).attr("value", "${dto.cor_no}");
            }

            if (i === 1) {
                $(input[1]).attr("name", "rev_no");
                $(input[1]).attr("value", revNo);
            }

            if (i === 2) {
                $(input[2]).attr("name", "rev_id");
                $(input[2]).attr("value", revUuid);
            }

            <sec:authorize access="isAuthenticated()">
				if (i === 3) {
					$(input[3]).attr("name", "user_no");
					$(input[3]).attr("value", "${user_no}");
				}
			</sec:authorize>

            form.appendChild(input[i]);
        }

        document.body.appendChild(form);
        form.submit();
	}

    function rollbackReview(revNo, revUuid) {
        if (!window.confirm("리뷰를 롤백하시겠습니까?")) {
            return;
        }

        let url = "/service/rev_rollback";
        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action = url;

        let input = [];
        for (let i = 0; i < 4; i++) {
            input[i] = document.createElement("input");
            $(input[i]).attr("type", "hidden");

            if (i === 0) {
                $(input[0]).attr("name", "cor_no");
                $(input[0]).attr("value", "${dto.cor_no}");
            }

            if (i === 1) {
                $(input[1]).attr("name", "rev_no");
                $(input[1]).attr("value", revNo);
            }

            if (i === 2) {
                $(input[2]).attr("name", "rev_id");
                $(input[2]).attr("value", revUuid);
            }

            <sec:authorize access="isAuthenticated()">
            if (i === 3) {
                $(input[3]).attr("name", "user_no");
                $(input[3]).attr("value", "${user_no}");
            }
            </sec:authorize>

            form.appendChild(input[i]);
        }

        document.body.appendChild(form);
        form.submit();
    }
</script>
<script>
	let postType;
    let postNo;

    function onClickRemoveCourse() {
        if (!window.confirm("코스를 삭제하시겠습니까?")) {
            return;
        }

        let param = location.search;

        console.log(param);
        console.log('/service/cor_delete' + param);

        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action= "/service/cor_delete" + param;

        document.body.appendChild(form);
        form.submit();
    }

    function onClickPermaDeleteCourse() {
        if (!window.confirm("코스를 영구삭제하시겠습니까?")) {
            return;
        }

        let param = location.search;

        console.log(param);
        console.log('/service/cor_perma_delete' + param);

        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action= "/service/cor_perma_delete" + param;

        document.body.appendChild(form);
        form.submit();
    }

    function onClickRollbackCourse() {
        alert('코스를 복원하시겠습니까?');

        let param = location.search;

        console.log(param);
        console.log('/service/cor_rollback' + param);

        let form;
        form = document.createElement("form");
        form.method = "post";
        form.action= "/service/cor_rollback" + param;

        document.body.appendChild(form);
        form.submit();
    }

    function reportSubmit() {
        let reportType = document.querySelector('input[name="report"]:checked').value;
        let reportContent = document.getElementById("repContent").value;
        let dupCheck = null;

        dupCheck = confirm("중복 체크를 해제하시겠습니까?");

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
        postNo = postNoValue;
	}
</script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
