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
					<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
					<button class="btn-close" type="button" class="close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col d-flex justify-content-center align-items-md-center" id="searchResultArea">
							<table>
								<tbody id="tableBody">
<%--								<tr onclick="location.href='/'" style="cursor:hand">--%>
								<tr>
									<td>1</td>
									<td>광화문</td>
								</tr>
								<tr>
									<td>내용</td>
									<td><textarea rows="3" cols="30"></textarea></td>
								</tr>
								<tr>
									<td>별점</td>
									<td>별점 등록</td>
								</tr>
								<tr>
									<td>이미지</td>
									<td>
										<div id="canvas_rev" class="row flex-nowrap mx-0 my-3"
											 style="overflow-x: scroll; /*outline: blue thick solid;*/">
											<input id="imgInput" name="files" type="file"  minlength="1" maxlength="3" multiple
												   accept="image/*" onchange="readImage()">
											<c:forEach var="i" begin="1" end="3">
												<div class="card p-0 m-2 visually-hidden">
													<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}"
														 class="visible bd-place card-img"
														 style="height: 244px; width: 100%; outline: none">
													<div class="d-flex justify-content-center card-img-overlay"
														 style="align-items: center">
														<img class="btn btn-lg align-middle" onclick="onClickAddImage()"
															 id="imgAdd_${i}"
															 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
															 style="height: 30%; z-index: 2">
													</div>
													<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden"
														 style="align-items: flex-start">
														<img class="btn btn-lg align-middle p-0" id="imgDel_${i}"
															 onclick="deleteImage(this)"
															 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
															 style="z-index: 2">
													</div>
													<div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden"
														 style="align-items: center">
														<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})"
															 src="/image/icon/480px-Solid_white.png"
															 style="opacity : 0.0; z-index: 1;">
													</div>
												</div>
											</c:forEach>
										</div>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
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
							<button class="btn btn-link btn-block" type="button" data-toggle="collapse"
									data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne"
									style="text-decoration: none; color: #FF6699; font-weight: bold">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/cor_recommend" class="highlight-selected-text-menu">- 추천 코스</a></p>
							<p><a href="/service/cor_registration" class="highlight-not-selected-text-menu">- 코스 등록</a></p>
							<p><a href="#" class="highlight-not-selected-text-menu">- 코스 편집</a></p>
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
					<img src="/image/icon/comment.png" class="loc_icon_big me-2" alt="리뷰">
					<span class="text-center align-middle fs-3 me-4">${revCount}</span>
<%--					Todo 추후에 위치 수정하기--%>
<%--				loc_common onClickLike	--%>
					<span class="visually-hidden">${dto.user_no}</span>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px" onclick="copyURL()">공유</button>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px;" onclick="location.href='/service/cor_edit?corNo=${dto.cor_no}'">수정</button>
					<!-- Button trigger modal -->
					<button class="btn btn-outline-danger col-3" style="max-height: 56px" data-bs-toggle="modal" data-bs-target="#exampleModal">모달창 열기</button>
					<button class="btn btn-outline-danger col-3" style="max-height: 56px" onclick="openReviewRegisterPopup()">리뷰 팝업 열기</button>
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
							data-bs-target="#course-map" type="button" role="tab" aria-controls="course-info" onclick="removeURLParam('page')"
							aria-selected="true">경로
					</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="course-info-tab" data-bs-toggle="pill"
							data-bs-target="#course-info" type="button" role="tab" aria-controls="course-info" onclick="removeURLParam('page')"
							aria-selected="true">설명
					</button>
				</li>
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
<%--													<button class="btn-lg btn-primary" onclick="location.href='/service/loc_detail?locNo=${locList.get(i).loc_no}'">장소 바로 가기</button>--%>
													<div class="row my-lg-4">
<%--														<div class="col-md-7">--%>
<%--															<div class="card shadow-sm">--%>
<%--																<div class="d-flex justify-content-center">--%>
<%--																	<img class="bd-placeholder-img card-img" width="70%"--%>
<%--																		 height="300"--%>
<%--																		 alt="${locList.get(i).loc_name}"--%>
<%--																		 src="${locList.get(i).thumbnail}"--%>
<%--																		 id="loc_img_${i}"--%>
<%--																		 name="loc_img_${i}"--%>
<%--																		 preserveAspectRatio="xMidYMid slice" focusable="false">--%>
<%--																</div>--%>
<%--															</div>--%>
<%--														</div>--%>
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
																						<button class="btn btn-primary" onclick="openReviewEditPopup(${revDTO.get(c).revNo})">수정</button>
																						<button class="btn btn-primary" onclick="deleteReview('${revDTO.get(c).revNo}', '${revDTO.get(c).revUuid}')">삭제</button>
																					</div>
																				</c:when>
																			</c:choose>
																		</sec:authorize>
															</span>
															<span class="date text-black-50 ml-5">${revDTO.get(c).regDate.format(defaultDateTimeFormatter.dateTimeFormatter)}</span>
															<c:choose>
																<c:when test="${revDTO.get(c)._modified eq true}">
																	<span class="date text-black-50 ml-5">(수정됨)</span>
																</c:when>
															</c:choose>
															<c:choose>
																<c:when test="${revDTO.get(c)._deleted eq true}">
																	<span class="date text-black-50 ml-5">(삭제됨)</span>
																</c:when>
															</c:choose>
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
<script defer src="/js/bootstrap.js"></script>
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>
<script>
    window.onload = function() {
        let params = new URLSearchParams(location.search);

        if (params.has("page")) {
            let offsetTop = document.querySelector("#pills-tab").offsetTop - 100;

            window.scrollTo({top:offsetTop, behavior: 'smooth'});
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
</script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
