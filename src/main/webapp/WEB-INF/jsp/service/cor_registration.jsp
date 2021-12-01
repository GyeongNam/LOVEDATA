<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>
<%--https://www.codinglabweb.com/2021/01/responsive-registration-form-in-html-css.html--%>

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
	<link rel="stylesheet" href="/css/service/loc_registration.css">
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
							<button class="btn btn-link btn-block" type="button" style="text-decoration: none; color: #FF6699; font-weight: bold">
								코스
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/cor_recommend" class="highlight-not-selected-text-menu">- 추천 코스</a>
							</p>
							<p><a href="/service/cor_registration" class="highlight-selected-text-menu">- 코스 등록</a>
							</p>
							<p><a href="/mypage_mycorse/1" class="highlight-not-selected-text-menu">- 코스 편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>

	<c:set value="3" var="minCorImgCount"></c:set>
	<c:set value="10" var="maxCorImgCount"></c:set>
	<c:set value="7" var="maxCorCount"></c:set>
	<c:set value="2" var="minCorCount"></c:set>
	<div class="container m-5" id="display_center" style="margin-right: 30px; margin-top: 30px">
		<sec:authorize access="!isAuthenticated()">
			<span>로그인 하지 않으면 코스를 등록할 수 없습니다.</span>
			<%
				if(true) return;
			%>
		</sec:authorize>
		<h1>코스 등록</h1>
		<div class="container-fluid">
			<form action="/service/cor_registration/regData" method="post" enctype="multipart/form-data">
				<div class="user-details basic-style">
					<div class="row">
						<div class="col" id="top_hashtag">
							<nav class="navbar navbar-expand-sm navbar-light static-top">
								<div class="collapse navbar-collapse" id="tag-navbar-collapse">
									<ul class="navbar-nav">
										<li class="nav-item dropdown">
											<button class="nav-link dropdown-toggle" role="button"
													id="tagDropdownMenuLink"
													data-toggle="dropdown">해시태그
											</button>
											<%--                            https://www.w3schools.com/jsref/event_onclick.asp--%>
											<div class="dropdown-menu" aria-labelledby="tagDropdownMenuLink">
												<c:forEach var="i" begin="0" end="${tagList.size()-1}">
													<button type="button" class="dropdown-item" onclick="addTag(this)"
															value="${tagList.get(i).name()}">
															${tagList.get(i).name()}
													</button>
												</c:forEach>
											</div>
										</li>
									</ul>
									<div id="tag_list">
										<%--						@Todo display:inline으로 변경할때마다 빈공간 생기는 문제 수정하기--%>
										<c:forEach var="i" begin="0" end="${tagList.size()-1}">
											<div class="btn-group ms-4 my-0" role="group" style="display: none">
												<button type="button" class="btn btn-primary"
														value="${tagList.get(i)}">${tagList.get(i)}</button>
												<button type="button" class="btn btn-outline-danger btn"
														onclick="removeTag(this)">X
												</button>
											</div>
										</c:forEach>
									</div>
								</div>
							</nav>
						</div>
					</div>
					<div class="input-box">
						<span class="details">이름</span>
						<input type="text" id="name" name="name" placeholder="Enter your name" required>
					</div>
					<div class="input-box">
						<div class="row d-flex align-items-md-center" style="margin-left: 1px">
							<div class="col-lg me-2" id="est_time_select">
								<div class="row">
									<span class="details m-0 p-0">예상 소요시간</span>
<%--									<input type="text" id="est_time_sel" name="est_time_sel" placeholder="10분"--%>
<%--										   style="width: auto" required>--%>
									<select id="est_time_sel" class="form-select" aria-label="Default select example" onchange="onSelectChangeValue(this)">
										<option value="null" selected>시간을 선택해주세요</option>
										<option value="30분">30분</option>
										<option value="1시간">1시간</option>
										<option value="1시간 30분">1시간 30분</option>
										<option value="2시간">2시간</option>
										<option value="2시간 30분">2시간 30분</option>
										<option value="3시간">3시간</option>
										<option value="3시간 30분">3시간 30분</option>
										<option value="4시간">4시간</option>
										<option value="4시간 30분">4시간 30분</option>
										<option value="5시간">5시간</option>
										<option value="5시간 30분">5시간 30분</option>
										<option value="6시간">6시간</option>
										<option value="6시간 30분">6시간 30분</option>
										<option value="7시간">7시간</option>
										<option value="7시간 30분">7시간 30분</option>
										<option value="8시간">8시간</option>
										<option value="8시간 30분">8시간 30분</option>
										<option value="9시간">9시간</option>
										<option value="9시간 30분">9시간 30분</option>
										<option value="10시간">10시간</option>
										<option value="10시간 30분">10시간 30분</option>
										<option value="11시간">11시간</option>
										<option value="11시간 30분">11시간 30분</option>
										<option value="12시간">12시간</option>
									</select>
								</div>
							</div>
							<div class="col-lg mx-2 visually-hidden" id="est_date_select">
								<div class="row">
									<span class="details m-0 p-0">예상 소요일</span>
									<select id="est_date_sel" class="form-select" aria-label="Default select example" onchange="onSelectChangeValue(this)">
										<option value="null" selected>날짜를 선택해주세요</option>
										<option value="1박 2일">1박 2일</option>
										<option value="2박 3일">2박 3일</option>
										<option value="3박 4일">3박 4일</option>
										<option value="4박 5일">4박 5일</option>
										<option value="5박 6일">5박 6일</option>
										<option value="6박 7일">6박 7일</option>
										<option value="7박 8일">7박 8일</option>
									</select>
								</div>
							</div>
							<div class="col-lg mx-2">
								<div class="row">
									<%--							Todo 레이아웃 변경하기--%>
									<span class="details m-0 p-0">이동수단</span>
									<input type="text" id="transportation" name="transportation" placeholder="자동차"
										   style="width: auto" required>
								</div>
							</div>
						</div>
					</div>
					<div class="input-box visually-hidden">
						<span class="details">예상 시간 타입(컨트롤러 전달)</span>
						<input type="text" id="est_type" name="est_type" placeholder="Enter est type value" value="time" required>
					</div>
					<div class="input-box visually-hidden">
						<span class="details">예상 시간 값(컨트롤러 전달)</span>
						<input type="text" id="est_value" name="est_value" placeholder="Enter est time value" value="null" required>
					</div>
					<div class="input-box">
						<span class="details">예상 비용</span>
						<input type="text" id="cost" name="cost" placeholder="00 만원" required>
					</div>
					<div class="input-box">
						<span class="details">정보</span>
						<textarea rows="4" maxlength="150" name="info" id="info" required></textarea>
						<sec:authorize access="isAuthenticated()">
							<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
							<input type="hidden" name="user_no" id="user_no" value="${user_no}">
						</sec:authorize>
						<input type="hidden" name="user_no_debug" id="user_no_debug" value="0">
					</div>
					<div class="form-check form-switch">
						<input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" onchange="toggleAccommodationCheck()">
						<label class="form-check-label" for="flexSwitchCheckDefault">숙박 여부</label>
					</div>
					<div class="input-box visually-hidden" id="accom_input">
						<div class="row d-flex align-items-md-center">
							<span class="details">숙박 정보</span>
							<textarea style="margin-left: 12px; margin-right: 12px" rows="4" maxlength="50" name="accommodations" id="accommodations" required></textarea>
						</div>
					</div>
					<div class="row d-flex align-items-end">
						<div class="col">
							<span class="details">이미지</span>
						</div>
						<div class="col d-flex justify-content-end">
							<image type="button" id="img_move_left" name="img_move_left"
								   src="/image/icon/left-arrow.png" onclick="onClickImgMoveLeft()"
								   style="height: 30px"></image>
							<image type="button" id="img_move_right" name="img_move_right"
								   src="/image/icon/right-arrow.png" onclick="onClickImgMoveRight()"
								   style="height: 30px"></image>
						</div>
					</div>
					<div>
						<div id="canvas" class="row flex-nowrap mx-0 my-3"
							 style="overflow-x: scroll; /*outline: blue thick solid;*/">
							<input class="visually-hidden" id="imgInput" name="files" type="file" multiple
								   accept="image/*" onchange="readImage()">
							<c:forEach var="i" begin="1" end="${maxCorImgCount}">
								<c:choose>
									<c:when test="${i eq 1}">
										<div class="card col-3 p-0 m-2">
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
									</c:when>
									<c:otherwise>
										<div class="card col-3 p-0 m-2 visually-hidden">
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
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</div>
					<%--					Todo 팝업창 띄워서 장소 검색하는 ui 만들기--%>
					<div class="row d-flex align-items-end mt-5">
						<div class="col">
							<span class="details align-middle">장소 추가</span>
						</div>
						<div class="col d-flex justify-content-end">
							<image type="button" id="loc_move_up" name="loc_move_up"
								   src="/image/icon/up-arrow.png" onclick="onClickLocationMoveUp()" style="height: 30px; margin: 2px"></image>
							<image type="button" id="loc_move_down" name="loc_move_down"
								   src="/image/icon/down-arrow.png" onclick="onClickLocationMoveDown()"
								   style="height: 30px; margin: 2px"></image>
						</div>
					</div>
					<div>
						<input class="visually-hidden" id="imgInput" name="files" type="file" multiple accept="image/*"
							   onchange="readImage()">
						<c:forEach var="i" begin="1" end="${maxCorCount}">
							<c:choose>
								<c:when test="${i eq 1}">
									<div class="card p-0 m-2" id="loc_add_${i}">
										<img src="/image/icon/480px-Solid_white.png"
											 class="visible bd-place card-img"
											 style="height: 244px; width: 100%; outline: none">
										<div class="d-flex justify-content-center card-img-overlay"
											 style="align-items: center">
											<img class="btn btn-lg align-middle" onclick="openLocationSearchPopup()"
												 id="locAddBtn_${i}"
												 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
												 style="height: 30%; z-index: 2">
										</div>
									</div>
									<div class="card p-0 m-2 visually-hidden" id="loc_${i}">
										<div class="row">
											<div class="d-flex justify-content-end card-img-overlay pe-4 pt-5 col-4"
												 style="align-items: flex-start">
												<img class="btn btn-lg align-middle p-0" id="locDelBtn_${i}" onclick=""
													 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
													 style="z-index: 2">
											</div>
											<div class="d-flex justify-content-center card-img-overlay visually-hidden"
												 style="align-items: center">
												<img class="w-100 h-100" id="locSel_${i}" onclick=""
													 src="/image/icon/480px-Solid_white.png"
													 style="opacity : 0.0; z-index: 1">
											</div>
											<span class="details fs-3">&lt;장소 ${i}&gt;</span>
											<div class="col-4">
												<img src="/image/icon/480px-Solid_white.png" alt="loc_thumbnail_${i}"
													 id="loc_thumbnail_${i}"
													 class="visible bd-place card-img"
													 style="height: 244px; width: 100%; outline: none">
												<div class="d-flex justify-content-end card-img-overlay pe-4 pt-5 col-4"
													 style="align-items: flex-start">
													<img class="btn btn-lg align-middle p-0" id="locDel_${i}" onclick="onRemoveLocation(${i})"
														 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
														 style="z-index: 2">
												</div>
												<div class="d-flex justify-content-center card-img-overlay p-0"
													 style="align-items: center">
													<img class="w-100 h-100" id="loc_select_${i}"
														 onclick="onSelectLocation(${i})"
														 src="/image/icon/480px-Solid_white.png"
														 style="opacity : 0.0; z-index: 1;">
												</div>
											</div>
											<div class="col">
												<div class="row d-flex my-2">
													<div class="col-3 d-flex align-items-md-center">
														<span class="details mx-2">이름</span>
													</div>
													<div class="col">
														<input type="text" name="loc_name_${i}" id="loc_name_${i}"
															   value=""
															   readonly>
													</div>
												</div>
												<div class="row d-flex my-2 align-items-md-center">
													<div class="col-3">
														<span class="details mx-2 align-middle">해시태그</span>
													</div>
													<div class="col d-flex justify-content-start pe-5"
														 id="hashtag_${i}">
													</div>
												</div>
												<div class="row d-flex my-2">
													<div class="col-3 d-flex justify-content-between align-items-md-center">
														<span class="details mx-2">주소</span>
													</div>
													<div class="col">
														<input type="text" name="loc_addr_${i}" id="loc_addr_${i}"
															   value=""
															   readonly>
													</div>
												</div>
												<div class="row d-flex my-2">
													<div class="col-3 d-flex justify-content-between align-items-md-center">
														<span class="details mx-2">전화번호</span>
													</div>
													<div class="col">
														<input type="text" name="loc_tel_${i}" id="loc_tel_${i}"
															   value=""
															   readonly>
													</div>
												</div>
												<div class="row d-flex my-2 align-items-md-center">
													<div class="col-3">
														<span class="details mx-2">상세설명</span>
													</div>
													<div class="col d-flex">
													<textarea class="col ms-0 me-lg-5" name="loc_info_${i}"
															  id="loc_info_${i}"
															  rows="4" maxlength="150" readonly>
													</textarea>
													</div>
												</div>
												<div class="visually-hidden">
													<input id="loc_no_${i}" name="loc_no_${i}">
													<input id="loc_id_${i}" name="loc_id_${i}">
												</div>
											</div>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="card p-0 m-2 visually-hidden" id="loc_add_${i}">
										<img src="/image/icon/480px-Solid_white.png" class="visible bd-place card-img"
											 style="height: 244px; width: 100%; outline: none">
										<div class="d-flex justify-content-center card-img-overlay"
											 style="align-items: center">
											<img class="btn btn-lg align-middle" onclick="openLocationSearchPopup()"
												 id="locAddBtn_${i}"
												 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png"
												 style="height: 30%; z-index: 2">
										</div>
									</div>
									<div class="card p-0 m-2 visually-hidden" id="loc_${i}">
										<div class="row">
											<div class="d-flex justify-content-end card-img-overlay pe-4 pt-5 col-4"
												 style="align-items: flex-start">
												<img class="btn btn-lg align-middle p-0" id="locDelBtn_${i}" onclick=""
													 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
													 style="z-index: 2">
											</div>
											<div class="d-flex justify-content-center card-img-overlay visually-hidden"
												 style="align-items: center">
												<img class="w-100 h-100" id="locSel_${i}" onclick=""
													 src="/image/icon/480px-Solid_white.png"
													 style="opacity : 0.0; z-index: 1">
											</div>
											<span class="details fs-3">&lt;장소 ${i}&gt;</span>
											<div class="col-4">
												<img src="/image/icon/480px-Solid_white.png" alt="loc_thumbnail_${i}"
													 id="loc_thumbnail_${i}"
													 class="visible bd-place card-img"
													 style="height: 244px; width: 100%; outline: none">
												<div class="d-flex justify-content-end card-img-overlay pe-4 pt-5 col-4"
													 style="align-items: flex-start">
													<img class="btn btn-lg align-middle p-0" id="locDel_${i}" onclick="onRemoveLocation(${i})"
														 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png"
														 style="z-index: 2">
												</div>
												<div class="d-flex justify-content-center card-img-overlay p-0"
													 style="align-items: center">
													<img class="w-100 h-100" id="loc_select_${i}"
														 onclick="onSelectLocation(${i})"
														 src="/image/icon/480px-Solid_white.png"
														 style="opacity : 0.0; z-index: 1;">
												</div>
											</div>
											<div class="col">
												<div class="row d-flex my-2">
													<div class="col-3 d-flex align-items-md-center">
														<span class="details mx-2">이름</span>
													</div>
													<div class="col">
														<input type="text" name="loc_name_${i}" id="loc_name_${i}"
															   value=""
															   readonly>
													</div>
												</div>
												<div class="row d-flex my-2 align-items-md-center">
													<div class="col-3">
														<span class="details mx-2 align-middle">해시태그</span>
													</div>
													<div class="col d-flex justify-content-start pe-5"
														 id="hashtag_${i}">
													</div>
												</div>
												<div class="row d-flex my-2">
													<div class="col-3 d-flex justify-content-between align-items-md-center">
														<span class="details mx-2">주소</span>
													</div>
													<div class="col">
														<input type="text" name="loc_addr_${i}" id="loc_addr_${i}"
															   value=""
															   readonly>
													</div>
												</div>
												<div class="row d-flex my-2">
													<div class="col-3 d-flex justify-content-between align-items-md-center">
														<span class="details mx-2">전화번호</span>
													</div>
													<div class="col">
														<input type="text" name="loc_tel_${i}" id="loc_tel_${i}"
															   value=""
															   readonly>
													</div>
												</div>
												<div class="row d-flex my-2 align-items-md-center">
													<div class="col-3">
														<span class="details mx-2">상세설명</span>
													</div>
													<div class="col d-flex">
													<textarea class="col ms-0 me-lg-5" name="loc_info_${i}"
															  id="loc_info_${i}"
															  rows="4" maxlength="150" readonly>
													</textarea>
													</div>
												</div>
												<div class="visually-hidden">
													<input id="loc_no_${i}" name="loc_no_${i}">
													<input id="loc_id_${i}" name="loc_id_${i}">
												</div>
											</div>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
				<input class="visually-hidden" id="location_length" name="location_length">
				<button type="button" id="register" name="register" onclick="onClickRegister()">Register</button>
			</form>
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
<script defer src="/js/loc_common.js"></script>
<script defer>
    function openLocationSearchPopup() {
        <%--        // 주소검색을 수행할 팝업 페이지를 호출합니다.
				// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다. --%>
        var pop = window.open("/popup/locationSearchPopup", "pop", "width=1200,height=600, scrollbars=yes, resizable=yes");

        <%-- // 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다. --%>
        //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes");
    }

    function locationCallBack(locNo, locID, locName) {
        // 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
        // document.getElementById("roadAddrPart1").value = roadAddrPart1;
        // document.getElementById("addrDetail").value = addrDetail;
        // document.getElementById("addrDetail").removeAttribute("disabled");
        // console.log("locNo : " + locNo);
        // console.log("locID : " + locID);
        // console.log("locName : " + locName);

        enableLocation(locNo, locID);
    }
</script>
<script>
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
            if (index > ${maxCorImgCount}) {
                deleteFileListImage();
                return false;
            }
            // console.log(i + "번 째 아이템이 등록되었습니다.");
            let item = document.getElementById("img_" + (index + 1));
            reader.onload = e => {
                item.src = e.target.result;
                item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            if (index < ${maxCorImgCount} - 1) {
                document.getElementById("img_" + (index + 2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            reader.readAsDataURL(file);
            console.log(item);
        })

        // // 기존에 있던 이미지 지우기
        for (let i = fileList.length + 1; i <= ${maxCorImgCount}; i++) {
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

    function deleteFileListImage() {
        let dt = new DataTransfer();
        dt.files = input.files;

        for (let i = 0; i < ${maxCorImgCount}; i++) {
            dt.items.add(input.files[i]);
        }

        input.files = dt.files;
        console.log(dt.files);
        console.log(input.files);
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

        for (let i = 1; i <= ${maxCorImgCount}; i++) {
            if (i >= index) {
                if (i !== ${maxCorImgCount}) {
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

        for (let i = input.files.length + 1; i <= ${maxCorImgCount}; i++) {
            let img = document.getElementById("img_" + i);
            img.src = "/image/icon/480px-Solid_white.png";
        }

        toggleAddDelBtn(input.files.length);
    }

    function nullInputCheck() {
        let name = document.getElementById("name").value;
        let est_type = document.getElementById("est_type").value;
        let est_value = document.getElementById("est_value").value;
        let trans = document.getElementById("transportation").value;
		let cost = document.getElementById("cost").value;

		if (name == "" || est_type == "" || est_value == "" || trans == "" || cost == "") {
		    return false;
		} else {
		    return true;
		}
	}

    function onClickRegister() {
        console.log("submit butten clicked");
        var $fileUpload = $("input[type='file']");
        var loginCheck = null;
        var debugCheck = {"debug": false}

		if (!nullInputCheck()) {
		    alert("채우지 않은 항목이 있습니다.");
		    return;
		}

        <%--$.ajax({--%>
        <%--    type: "POST",--%>
        <%--    url: "/rest/authenticationCheck",--%>
        <%--    data: JSON.stringify(debugCheck),--%>
        <%--    dataType: 'json',--%>
        <%--    contentType: "application/json; charset=UTF-8",--%>
        <%--    beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/--%>
        <%--        xhr.setRequestHeader(header, token);--%>
        <%--    },--%>
        <%--    success: function (response) {--%>
        <%--        // do something ...--%>
        <%--        console.log("Login Check Success");--%>
        <%--        console.log("is login : " + response);--%>

        <%--        if (tagList.length < 3) {--%>
        <%--            alert("해시태그는 최소 3개 이상 추가해야합니다.");--%>
        <%--            return--%>
		<%--		}--%>

        <%--        if (response) {--%>
        <%--            if (parseInt($fileUpload.get(0).files.length) < 3) {--%>
        <%--                alert("최소 ${minCorImgCount}개의 이미지 파일은 업로드 해야합니다.")--%>
        <%--            } else if (parseInt($fileUpload.get(0).files.length) > 10) {--%>
        <%--                alert("최대 ${maxCorImgCount}개의 이미지 파일만 업로드 가능합니다.");--%>
        <%--            } else {--%>
        <%--                // Todo 디버깅 모드 (추후에 주석 해제하기)--%>
        <%--                // if (currentLocationLength < 3) {--%>
        <%--                //     alert("최소 3곳의 장소를 코스에 추가해야합니다.");--%>
        <%--                // } else {--%>
        <%--                    var formData = $("form");--%>
        <%--                    $.ajax({--%>
        <%--                        type: "POST",--%>
        <%--                        url: "/service/cor/tags",--%>
        <%--                        data: {--%>
        <%--                            tags: tagList--%>
        <%--                        },--%>
        <%--                        success: function (response) {--%>
        <%--                            console.log("장소 등록 성공");--%>
        <%--                            alert("장소 등록 성공");--%>
        <%--                        },--%>
        <%--                        error: function (e) {--%>
        <%--                            console.log("태그 등록 실패");--%>
        <%--                        }--%>
        <%--                    });--%>

        <%--                console.log(formData);--%>
        <%--                formData.submit();--%>
        <%--                // }--%>
        <%--            }--%>
        <%--        } else {--%>
        <%--            alert("코스 등록 실패 : 로그인을 해주세요");--%>
        <%--            console.log("코스 등록 실패 : 로그인을 해주세요");--%>
        <%--        }--%>
		<%--		--%>
        <%--    }, error: function (e) {--%>
        <%--        // console.log("Login Check Failed")--%>
        <%--        // alert("장소 등록 실패");--%>
        <%--        // console.log("장소 등록 실패");--%>
        <%--        // onClickRegister();--%>
        <%--        console.log(e);--%>
        <%--    }--%>

        <%--});--%>

        if (tagList.length < 3) {
            alert("해시태그는 최소 3개 이상 추가해야합니다.");
            return;
        }

        if (currentLocationLength < 2) {
			alert("최소 2곳의 장소를 코스에 추가해야합니다.");
            return;
		}

        if (parseInt($fileUpload.get(0).files.length) < 3) {
            alert("최소 ${minCorImgCount}개의 이미지 파일은 업로드 해야합니다.")
        } else if (parseInt($fileUpload.get(0).files.length) > 10) {
            alert("최대 ${maxCorImgCount}개의 이미지 파일만 업로드 가능합니다.");
        } else {
            // Todo 디버깅 모드 (추후에 주석 해제하기)
            // if (currentLocationLength < 3) {
            //     alert("최소 3곳의 장소를 코스에 추가해야합니다.");
            // } else {
            var formData = $("form");
            $.ajax({
                type: "POST",
                url: "/service/cor/tags",
                data: {
                    tags: tagList
                },
                success: function (response) {
                    if (response == "Tag register Success") {
                        console.log(formData);
                        formData.submit();
                        console.log("코스 등록 성공");
                        alert("코스 등록 성공");
					} else if (response == "Tag register Fail") {
                        console.log("코스 등록 실패");
                        alert("코스 등록 실패");
					} else if (response == "Authentication Failed") {
                        console.log("로그인 되어 있지 않습니다.");
                        alert("다시 로그인 해주세요");
					}
                },
                error: function (e) {
                    console.log("태그 등록 실패");
                }
            });
    	}
    }
</script>
<script defer>
    let selectedImageParent = null;
    let selectedImageIndex = null;
    let selectedImage = null;

    function onClearSelecteImage() {
        selectedImageParent.style.outline = 'none';
        selectedImageParent = null;
        selectedImageIndex = null;
    }

    function onSelectImage(index) {
        if (selectedImageIndex === index) {
            onClearSelecteImage();
            return;
        }

        selectedImageIndex = index;

        if (selectedImageParent !== null) {
            // selectedImage.style.opacity = 0.0;
            selectedImageParent.style.outline = 'none';
        }

        // console.log(document.getElementById("img_" + index));
        console.log("index : " + index);
        selectedImage = document.getElementById("img_" + index);
        selectedImageParent = selectedImage.parentElement;

        // selectedImage.style.opacity = 0.3;
        selectedImageParent.style.outline = 'solid thick red'
    }

    function onClickImgMoveLeft() {
        let dt = new DataTransfer();
        dt.files = input.files;

        if (selectedImageIndex === null) {
            return;
        }

        let leftObj = input.files.item(selectedImageIndex - 2);
        let selectedObj = input.files.item(selectedImageIndex - 1);
        let leftObjImg = document.getElementById("img_" + (selectedImageIndex - 1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex - 1 === i) {
                dt.items.add(leftObj);
                document.getElementById("img_" + (i + 1)).src = leftObjImg;
                continue;
            }

            if (selectedImageIndex - 2 === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i + 1)).src = selectedObjImg;
                continue;
            }

            dt.items.add(input.files.item(i));
        }

        input.files = dt.files;
        console.log(input.files);
        onSelectImage(selectedImageIndex - 1);
    }

    function onClickImgMoveRight() {
        let dt = new DataTransfer();
        dt.files = input.files;

        if (selectedImageIndex === null) {
            return;
        }

        let rightObj = input.files.item(selectedImageIndex);
        let selectedObj = input.files.item(selectedImageIndex - 1);
        let rightObjImg = document.getElementById("img_" + (selectedImageIndex + 1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex - 1 === i) {
                dt.items.add(rightObj);
                document.getElementById("img_" + (i + 1)).src = rightObjImg;
                continue;
            }

            if (selectedImageIndex === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i + 1)).src = selectedObjImg;
                continue;
            }

            dt.items.add(input.files.item(i));
        }

        input.files = dt.files;
        console.log(input.files);
        onSelectImage(selectedImageIndex + 1);
    }
</script>
<script defer>
    // let selectedLocationParent = null;
    let selectedLocationIndex = null;
    let selectedLocation = null;
    let currentLocationLength = 0;
    let locationMap = null;

    function onClearSelectLocation() {
        selectedLocation.style.outline = 'none';
        selectedLocation = null;
        selectedLocationIndex = null;
    }

    function onClickLocationMoveUp() {
        if (selectedLocationIndex == null) {
            alert('장소를 선택해주세요!');
            return;
		}

        if (selectedLocationIndex == 1) {
            return;
		}

        let selectedLocation_thumbnail = document.getElementById("loc_thumbnail_" + selectedLocationIndex);
        let selectedLocation_name = document.getElementById("loc_name_" + selectedLocationIndex);
        let selectedLocation_hashtag = document.getElementById("hashtag_" + selectedLocationIndex);
        let selectedLocation_addr = document.getElementById("loc_addr_" + selectedLocationIndex);
        let selectedLocation_tel = document.getElementById("loc_tel_" + selectedLocationIndex);
        let selectedLocation_info = document.getElementById("loc_info_" + selectedLocationIndex);

        let beforeLocation_thumbnail = document.getElementById("loc_thumbnail_" + (selectedLocationIndex-1));
        let beforeLocation_name = document.getElementById("loc_name_" + (selectedLocationIndex-1));
        let beforeLocation_hashtag = document.getElementById("hashtag_" + (selectedLocationIndex-1));
        let beforeLocation_addr = document.getElementById("loc_addr_" + (selectedLocationIndex-1));
        let beforeLocation_tel = document.getElementById("loc_tel_" + (selectedLocationIndex-1));
        let beforeLocation_info = document.getElementById("loc_info_" + (selectedLocationIndex-1));

        let temp_thumbnail;
        let temp_name;
        let temp_hashtag = document.createElement("button");
        let temp_addr;
        let temp_tel;
        let temp_info;

        temp_thumbnail = selectedLocation_thumbnail.src;
        temp_name = selectedLocation_name.value;
        temp_addr = selectedLocation_addr.value;
        temp_tel = selectedLocation_tel.value;
        temp_info = selectedLocation_info.value;

        for (let i = 0; i < selectedLocation_hashtag.childElementCount; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = selectedLocation_hashtag.children.item(i).value;
            button.innerText = selectedLocation_hashtag.children.item(i).innerText;
            temp_hashtag.append(button);

            selectedLocation_hashtag.removeChild(selectedLocation_hashtag.children.item(i));
        }

        selectedLocation_thumbnail.src = beforeLocation_thumbnail.src;
        selectedLocation_name.value = beforeLocation_name.value;
        selectedLocation_addr.value = beforeLocation_addr.value;
        selectedLocation_tel.value = beforeLocation_tel.value;
        selectedLocation_info.value = beforeLocation_info.value;

        for (let i = 0; i < beforeLocation_hashtag.childElementCount; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = beforeLocation_hashtag.children.item(i).value;
            button.innerText = beforeLocation_hashtag.children.item(i).innerText;
            selectedLocation_hashtag.append(button);

            beforeLocation_hashtag.removeChild(beforeLocation_hashtag.children.item(i));
        }

        beforeLocation_thumbnail.src = temp_thumbnail;
        beforeLocation_name.value = temp_name;
        beforeLocation_addr.value = temp_addr;
        beforeLocation_tel.value = temp_tel;
        beforeLocation_info.value = temp_info;

        for (let i = 0; i < temp_hashtag.childElementCount; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = temp_hashtag.children.item(i).value;
            button.innerText = temp_hashtag.children.item(i).innerText;
            beforeLocation_hashtag.append(button);
        }

        onSelectLocation(selectedLocationIndex-1);
	}

    function onClickLocationMoveDown() {
        if (selectedLocationIndex == null) {
            alert('장소를 선택해주세요!');
            return;
        }

        if (selectedLocationIndex == 10) {
            return;
        }

        // 아직 해당 장소가 추가가 되지 않았으면
        if (document.getElementById("loc_" + (selectedLocationIndex + 1)).getAttribute("class") === "card p-0 m-2 visually-hidden") {
            return;
        }

        let selectedLocation_thumbnail = document.getElementById("loc_thumbnail_" + selectedLocationIndex);
        let selectedLocation_name = document.getElementById("loc_name_" + selectedLocationIndex);
        let selectedLocation_hashtag = document.getElementById("hashtag_" + selectedLocationIndex);
        let selectedLocation_addr = document.getElementById("loc_addr_" + selectedLocationIndex);
        let selectedLocation_tel = document.getElementById("loc_tel_" + selectedLocationIndex);
        let selectedLocation_info = document.getElementById("loc_info_" + selectedLocationIndex);

        let nextLocation_thumbnail = document.getElementById("loc_thumbnail_" + (selectedLocationIndex+1));
        let nextLocation_name = document.getElementById("loc_name_" + (selectedLocationIndex+1));
        let nextLocation_hashtag = document.getElementById("hashtag_" + (selectedLocationIndex+1));
        let nextLocation_addr = document.getElementById("loc_addr_" + (selectedLocationIndex+1));
        let nextLocation_tel = document.getElementById("loc_tel_" + (selectedLocationIndex+1));
        let nextLocation_info = document.getElementById("loc_info_" + (selectedLocationIndex+1));

        let temp_thumbnail;
        let temp_name;
        let temp_hashtag = document.createElement("button");
        let temp_addr;
        let temp_tel;
        let temp_info;

        temp_thumbnail = selectedLocation_thumbnail.src;
        temp_name = selectedLocation_name.value;
        temp_addr = selectedLocation_addr.value;
        temp_tel = selectedLocation_tel.value;
        temp_info = selectedLocation_info.value;

        for (let i = 0; i < selectedLocation_hashtag.childElementCount; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = selectedLocation_hashtag.children.item(i).value;
            button.innerText = selectedLocation_hashtag.children.item(i).innerText;
            temp_hashtag.append(button);

            selectedLocation_hashtag.removeChild(selectedLocation_hashtag.children.item(i));
		}

        selectedLocation_thumbnail.src = nextLocation_thumbnail.src;
        selectedLocation_name.value = nextLocation_name.value;
        selectedLocation_addr.value = nextLocation_addr.value;
        selectedLocation_tel.value = nextLocation_tel.value;
        selectedLocation_info.value = nextLocation_info.value;

        for (let i = 0; i < nextLocation_hashtag.childElementCount; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = nextLocation_hashtag.children.item(i).value;
            button.innerText = nextLocation_hashtag.children.item(i).innerText;
            selectedLocation_hashtag.append(button);

            nextLocation_hashtag.removeChild(nextLocation_hashtag.children.item(i));
        }

        nextLocation_thumbnail.src = temp_thumbnail;
        nextLocation_name.value = temp_name;
        nextLocation_addr.value = temp_addr;
        nextLocation_tel.value = temp_tel;
        nextLocation_info.value = temp_info;

        for (let i = 0; i < temp_hashtag.childElementCount; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = temp_hashtag.children.item(i).value;
            button.innerText = temp_hashtag.children.item(i).innerText;
            nextLocation_hashtag.append(button);
        }

        onSelectLocation(selectedLocationIndex+1);
    }

    function onRemoveLocation(index) {
        if (index != currentLocationLength) {
            pullNextLocationInfo(index);
            deleteLocationInfo(currentLocationLength);
            disableVisualLocation(currentLocationLength);
		} else {
            deleteLocationInfo(index);
            disableVisualLocation(index);
		}

        if (index !== 1) {
            selectedLocationIndex -= 1;
		}

        currentLocationLength -= 1;
        changeLocationLengthInfo(index-1);
	}

	function deleteLocationInfo(index) {
        let loc_name = document.getElementById("loc_name_" + index);
        let loc_no = document.getElementById("loc_no_" + index);
        let loc_id = document.getElementById("loc_id_" + index);
        let loc_addr = document.getElementById("loc_addr_" + index);
        let loc_tel = document.getElementById("loc_tel_" + index);
        let loc_info = document.getElementById("loc_info_" + index);
        let loc_thumbnail = document.getElementById("loc_thumbnail_" + index);
        let loc_tags = document.getElementById("hashtag_" + index);

        loc_name.value = "";
        loc_no.innerText = "";
        loc_id.innerText = "";
        loc_addr.value = "";
        loc_tel.value = "";
        loc_info.value = "";
        loc_thumbnail.src = "/image/icon/480px-Solid_white.png";
        while (loc_tags.hasChildNodes()) {
            loc_tags.removeChild(loc_tags.lastChild);
        }
	}

	function pullNextLocationInfo(index) {

        for (let i = index; i < currentLocationLength; i++) {
            if (index === ${maxCorCount}) {
                return;
			}

            let loc_name = document.getElementById("loc_name_" + i);
            let loc_no = document.getElementById("loc_no_" + i);
            let loc_id = document.getElementById("loc_id_" + i);
            let loc_addr = document.getElementById("loc_addr_" + i);
            let loc_tel = document.getElementById("loc_tel_" + i);
            let loc_info = document.getElementById("loc_info_" + i);
            let loc_thumbnail = document.getElementById("loc_thumbnail_" + i);
            let loc_tags = document.getElementById("hashtag_" + i);

            let loc_next_name = document.getElementById("loc_name_" + (i + 1));
            let loc_next_no = document.getElementById("loc_no_" + (i + 1));
            let loc_next_id = document.getElementById("loc_id_" + (i + 1));
            let loc_next_addr = document.getElementById("loc_addr_" + (i + 1));
            let loc_next_tel = document.getElementById("loc_tel_" + (i + 1));
            let loc_next_info = document.getElementById("loc_info_" + (i + 1));
            let loc_next_thumbnail = document.getElementById("loc_thumbnail_" + (i + 1));
            let loc_next_tags = document.getElementById("hashtag_" + (i + 1));

            loc_name.value = loc_next_name.value;
            loc_no.innerText = loc_next_no.innerText;
            loc_id.innerText = loc_next_id.innerText;
            loc_id.value = loc_next_id.value;
            loc_addr.value = loc_next_addr.value;
            loc_tel.value = loc_next_tel.value;
            loc_info.value = loc_next_info.value;
            loc_thumbnail.src = loc_next_thumbnail.src;

            while (loc_tags.hasChildNodes()) {
                loc_tags.removeChild(loc_tags.lastChild);
            }

            for (let j = 0; j < loc_next_tags.childElementCount; j++) {
                let button = document.createElement("button");
                button.setAttribute('class', 'btn btn-primary mx-1');
                button.value = loc_next_tags.children.item(j).value;
                button.innerText = loc_next_tags.children.item(j).innerText;
                loc_tags.append(button);
            }
		}
	}

    function onSelectLocation(index) {
        if (selectedLocationIndex === index) {
            onClearSelectLocation();
            return;
        }

        selectedLocationIndex = index;

        if (selectedLocation !== null) {
            // selectedImage.style.opacity = 0.0;
            selectedLocation.style.outline = 'none';
        }

        // console.log(document.getElementById("img_" + index));
        console.log("index : " + index);
        selectedLocation = document.getElementById("loc_" + index);

        // selectedImage.style.opacity = 0.3;
        selectedLocation.style.outline = 'solid thick red'
    }

    function enableLocation(locNo, locID) {
        let json = {locNo: locNo, locID: locID};

        $.ajax({
            type: "POST",
            url: "/rest/service/loc/select",
            data: JSON.stringify(json),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                if (response == null) {
                    console.log("통신 실패!")
                    return false;
                } else {
                    console.log("통신 성공!");
                    locationMap = response;
                    // console.log(locationMap);
					
					if (isLocationDuplicated(currentLocationLength)) {
					    alert('장소가 중복되었습니다!');
					    return true;
					}
                    currentLocationLength += 1;
                    fillLocation(currentLocationLength);
                    return true;
                }
            },
            error: function (e) {
                console.log("통신 문제 발생!")
                return false;
            }
        });
    }

    function isLocationDuplicated(index) {
        for (let i = 1; i <= index; i++) {
            let locNo = document.getElementById("loc_no_" + i);
            let locID = document.getElementById("loc_id_" + i);

            // console.log(locNo);
            // console.log(locID);

            if (locNo.value === locationMap["locNo"] || locID.value === locationMap["locID"]) {
                return true;
			}
		}
        return false;
	}

    function fillLocation(index) {
        enableVisualLocation(index);
        enableNewLocationAddBtn(index + 1);
        inputLocationInfo(index);
        changeLocationLengthInfo(index);
    }

    function changeLocationLengthInfo(index) {
        let location_length_info = document.getElementById("location_length");

        location_length_info.innerText = index;
        location_length_info.value = index;
	}

    function inputLocationInfo(index) {
        let loc_name = document.getElementById("loc_name_" + index);
        let loc_no = document.getElementById("loc_no_" + index);
        let loc_id = document.getElementById("loc_id_" + index);
        let loc_addr = document.getElementById("loc_addr_" + index);
        let loc_tel = document.getElementById("loc_tel_" + index);
        let loc_info = document.getElementById("loc_info_" + index);
        let loc_thumbnail = document.getElementById("loc_thumbnail_" + index);
        let loc_tags = document.getElementById("hashtag_" + index);

        // console.log(locationMap);
        // console.log(typeof locationMap);

        console.log(locationMap["tags"]);

        loc_name.value = locationMap["locName"];
        loc_no.value = locationMap["locNo"];
        loc_id.value = locationMap["locID"];
        loc_addr.value = locationMap["locAddr"];
        loc_tel.value = locationMap["locTel"];
        loc_info.value = locationMap["locInfo"];
        loc_thumbnail.src = locationMap["locThumbnail"];

        let tagsStrAry = locationMap["tags"].replace(" ", "").split(",");

        for (let i = 0; i < tagsStrAry.length; i++) {
            let button = document.createElement("button");
            button.setAttribute('class', 'btn btn-primary mx-1');
            button.value = tagsStrAry[i];
            button.innerText = tagsStrAry[i];
            let aChild = loc_tags.appendChild(button);
        }
    }

    function enableNewLocationAddBtn(index) {

        if (index <= ${maxCorCount}) {
            let loc_add = document.getElementById("loc_add_" + index);

            // console.log(index);

            loc_add.setAttribute('class', 'card p-0 m-2');
        }
    }

    function enableVisualLocation(index) {
        let loc_add = document.getElementById("loc_add_" + index);
        let loc = document.getElementById("loc_" + index);

        // console.log(index);
        // console.log(loc_add);
        // console.log(loc);

        loc.setAttribute('class', 'card p-0 m-2');
        loc_add.setAttribute('class', 'card p-0 m-2 visually-hidden');
    }

    function disableVisualLocation(index) {
        let loc_add = document.getElementById("loc_add_" + index);
        let loc_add_next = document.getElementById("loc_add_" + (index + 1));
        let loc = document.getElementById("loc_" + index);

        console.log(index);

        loc.setAttribute("class", "card p-0 m-2 visually-hidden");
        loc_add.setAttribute("class", "card p-0 m-2");
        loc_add_next.setAttribute("class", "card p-0 m-2 visually-hidden");
    }

    function onSelectChangeValue(e) {
        let value = document.getElementById("est_value");

        value.value = e.value;
        value.innerText = e.value;
	}

    function toggleAccommodationCheck() {
        let accommodations = document.getElementById("accom_input");
        let est_time_sel = document.getElementById("est_time_select");
        let est_date_sel = document.getElementById("est_date_select");
        let est_time = document.getElementById("est_time_sel");
        let est_date = document.getElementById("est_date_sel");
        let est_value = document.getElementById("est_value");
        let est_type = document.getElementById("est_type");

        if (accommodations.getAttribute("class") === "input-box visually-hidden"){
            accommodations.setAttribute("class", "input-box visual");
            est_date_sel.setAttribute("class", "col-lg me-2 visual");
            est_time_sel.setAttribute("class", "col-lg me-2 visually-hidden");
            est_type.innerText = "date"
			est_type.value = "date";
            est_value.innerText = est_date.options[est_date.selectedIndex].text;
            est_value.value = est_date.options[est_date.selectedIndex].value;
        } else {
            accommodations.setAttribute("class", "input-box visually-hidden");
            est_time_sel.setAttribute("class", "col-lg me-2 visual");
            est_date_sel.setAttribute("class", "col-lg mx-2 visually-hidden");
            est_type.innerText = "time"
            est_type.value = "time";
            est_value.innerText = est_time.options[est_time.selectedIndex].text;
            est_value.value = est_time.options[est_time.selectedIndex].value;
		}
	}
</script>
<%--<script defer src="/js/JusoAPI.js"></script>--%>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
