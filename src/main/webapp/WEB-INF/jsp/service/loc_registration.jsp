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
							<p><a href="/service/loc_recommend" class="loc_highlight-not-selected-text-menu">- 추천 장소</a>
							</p>
							<p><a href="/service/loc_registration" class="loc_highlight-selected-text-menu">- 장소 등록</a>
							</p>
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
		<h1>장소 등록</h1>
		<div class="container-fluid">
			<form action="/service/loc_registration/regData" method="post" enctype="multipart/form-data">
				<div class="user-details basic-style">
					<div class="input-box">
						<span class="details">이름</span>
						<input type="text" id="name" name="name" placeholder="Enter your name" required>
					</div>
					<div class="row">
						<div class="col" id="top_hashtag">
							<nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
								<div class="collapse navbar-collapse" id="tag-navbar-collapse">
									<ul class="navbar-nav">
										<li class="nav-item dropdown">
											<button class="nav-link dropdown-toggle" role="button"
													id="tagDropdownMenuLink"
													data-toggle="dropdown">해시태그
											</button>
											<%--                            https://www.w3schools.com/jsref/event_onclick.asp--%>
											<div class="dropdown-menu" aria-labelledby="tagDropdownMenuLink">
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action A">Action A
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action B">Action B
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action C">Action C
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action D">Action D
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action E">Action E
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action F">Action F
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action G">Action G
												</button>
												<button type="button" class="dropdown-item" onclick="addTag(this)"
														value="Action H">Action H
												</button>
											</div>
										</li>
									</ul>
									<div id="tag_list">
										<%--						@Todo display:inline으로 변경할때마다 빈공간 생기는 문제 수정하기--%>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그1</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그2</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그3</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그4</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그5</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그6</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
										<div class="btn-group mx-2 my-0" role="group" style="display: none">
											<button type="button" class="btn btn-primary" value="">태그7</button>
											<button type="button" class="btn btn-outline-danger btn-sm"
													onclick="removeTag(this)">X
											</button>
										</div>
									</div>
								</div>
							</nav>
						</div>
					</div>
					<div class="input-box">
						<span class="details">위치</span>
						<form method="post" name="form" id="form">
							<input type="button" onclick="goPopup()" value="주소검색">
							<input type="text" placeholder="우편번호" id="zipNo" name="zipNo" readonly value="" required>
							<input type="text" placeholder="도로명 주소" id="roadAddrPart1" name="roadAddrPart1" readonly
								   required>
							<input type="text" placeholder="도로명 주소 (상세)" id="addrDetail" name="addrDetail" readonly
								   required>
							<input type="hidden" placeholder="시도명" id="siNm" name="siNm" readonly required>
							<input type="hidden" placeholder="시군구명" id="sggNm" name="sggNm" readonly required>
						</form>
					</div>
					<div class="input-box">
						<span class="details">연락처</span>
						<input type="tel" id="tel" name="tel" placeholder="010-0000-0000"
							   pattern="[0-9]{3}-[0-9]{4}-[0-9]{3}" required>
					</div>
					<div class="input-box">
						<span class="details">정보</span>
						<textarea rows="4" maxlength="150" name="info" id="info" required></textarea>
						<sec:authorize access="isAuthenticated()">
							<c:set var="user_no"><sec:authentication property="user_no"></sec:authentication></c:set>
							<c:when test="${not empty user_no}">
								<input type="hidden" name="user_no" id="user_no" value="${user_no}">
							</c:when>
						</sec:authorize>
						<input type="hidden" name="user_no_debug" id="user_no_debug" value="0">
					</div>
					<div>
						<input class="visually-hidden" id="imgInput" name="files" type="file" multiple accept="image/*" onchange="readImage()">
						<div id="canvas" class="row flex-nowrap mx-0 mt-3" style="overflow-x: scroll">
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img1" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd1"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel1" onclick="deleteImage()"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img2" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd2"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel2"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img3" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd3"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel3"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img4" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd4"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel4"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img5" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd5"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel5"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img6" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd6"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel6"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img7" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd7"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel7"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img8" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd8"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel8"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img9" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd9"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel9"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
							<div class="card col-3 p-0">
								<img src="/image/icon/480px-Solid_white.png" alt="" id="img10" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
								<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
									<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd10"
										 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%">
								</div>
								<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
									<img class="btn btn-lg align-middle p-0" id="imgDel10"
										 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png">
								</div>
							</div>
						</div>
					</div>
				</div>
				<button type="submit" id="register" name="register" onclick="onClickRegister()">Register</button>
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
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>
<script defer src="/js/loc_registration.js"></script>
<script>
    document.domain = "localhost:8080"

    function goPopup() {
<%--        // 주소검색을 수행할 팝업 페이지를 호출합니다.
        // 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다. --%>
        var pop = window.open("/popup/jusoPopup", "pop", "width=570,height=420, scrollbars=yes, resizable=yes");

        <%-- // 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다. --%>
        //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes");
    }


    function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
        // 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
        document.getElementById("roadAddrPart1").value = roadAddrPart1;
        document.getElementById("addrDetail").value = addrDetail;
        document.getElementById("addrDetail").removeAttribute("disabled");
        document.getElementById("zipNo").value = zipNo;
        document.getElementById("siNm").value = siNm;
        document.getElementById("sggNm").value = sggNm;
        <%--// document.form.roadFullAddr.value = roadFullAddr;
        // document.form.roadAddrPart1.value = roadAddrPart1;
        // document.form.roadAddrPart2.value = roadAddrPart2;
        // document.form.addrDetail.value = addrDetail;
        // document.form.engAddr.value = engAddr;
        // document.form.jibunAddr.value = jibunAddr;
        // document.form.zipNo.value = zipNo;
        // document.form.admCd.value = admCd;
        // document.form.rnMgtSn.value = rnMgtSn;
        // document.form.bdMgtSn.value = bdMgtSn;
        // document.form.detBdNmList.value = detBdNmList;
        /** 2017년 2월 추가제공 **/
        // document.form.bdNm.value = bdNm;
        // document.form.bdKdcd.value = bdKdcd;
        // document.form.siNm.value = siNm;
        // document.form.sggNm.value = sggNm;
        // document.form.emdNm.value = emdNm;
        // document.form.liNm.value = liNm;
        // document.form.rn.value = rn;
        // document.form.udrtYn.value = udrtYn;
        // document.form.buldMnnm.value = buldMnnm;
        // document.form.buldSlno.value = buldSlno;
        // document.form.mtYn.value = mtYn;
        // document.form.lnbrMnnm.value = lnbrMnnm;
        // document.form.lnbrSlno.value = lnbrSlno;
        /** 2017년 3월 추가제공 **/
        // document.form.emdNo.value = emdNo; --%>
    }
</script>
<script>
    let input = document.getElementById("imgInput");
    let isBuffered = false;

	function onClickAddImage() {
	    isBuffered = true;
        $('#imgInput').trigger('click');
    }

    function toggleAddDelBtn(offset) {
	    for (let i = 1; i < 11; i++) {
            let btnAddParent = document.getElementById("imgAdd" + i).parentElement;
            let btnDelParent = document.getElementById("imgDel" + i).parentElement;

	        if (offset < i) {
                btnAddParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay');
                btnDelParent.setAttribute('class', 'd-flex justify-content-end card-img-overlay p-0 visually-hidden');
			} else {
                btnAddParent.setAttribute('class', 'd-flex justify-content-center card-img-overlay visually-hidden');
                btnDelParent.setAttribute('class', 'd-flex justify-content-end card-img-overlay p-0');
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
            // console.log(i + "번 째 아이템이 등록되었습니다.");
			let item = document.getElementById("img" + (index+1));
			reader.onload= e => {
			    // item.className = "col-3 visible";
			    item.src = e.target.result;
			}
            reader.readAsDataURL(file);
            console.log(item);
        })
		if (isBuffered) {
            for (let i = fileList.length; i < 11; i++) {
                let img = document.getElementById("img" + i);
                img.src = "/image/icon/480px-Solid_white.png";
            }
		}
        toggleAddDelBtn(fileList.length);
    }

    function deleteImage() {
        let files = Array.from(input.files);

        console.log(files);

        let temp = files.splice(files.length-1, 1);

        console.log(temp);

        input.files = temp;

        // Todo
        // fileList는 readonly 속성
		// arrary로 받고 나서 해당 목록을 어떻게 다시 input 에 넣을 건지 생각해보기
	}

    function onClickRegister() {
        console.log("submit butten clicked");
        var $fileUpload = $("input[type='file']");
        var loginCheck = null;
        var debugCheck = {"debug": true}

        $.ajax({
            type: "POST",
            url: "/rest/authenticationCheck",
            data: JSON.stringify(debugCheck),
            dataType: 'json',
            contentType: "application/json; charset=UTF-8",
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                // do something ...
                console.log("Login Check Success");
                console.log("is login : " + response);

                if (response) {
                    if (parseInt($fileUpload.get(0).files.length) < 3) {
                        alert("최소 3개의 이미지 파일은 업로드 해야합니다.")
                    } else if (parseInt($fileUpload.get(0).files.length) > 10) {
                        alert("최대 10개의 이미지 파일만 업로드 가능합니다.");
                    } else {
                        var formData = $("form");
                        $.ajax({
                            type: "POST",
                            url: "/service/loc/tags",
                            data: {
                                tags: tagList //notice that "myArray" matches the value for @RequestParam
                                //on the Java side
                            },
                            success: function (response) {
                                // do something ...
                                console.log("장소 등록 성공");
								alert("장소 등록 성공");
                            },
                            error: function (e) {
                                console.log("태그 등록 실패");
                            }
                        });
                        formData.submit();
                    }
				}
                else {
                    alert("장소 등록 실패 : 로그인을 해주세요");
                    console.log("장소 등록 실패 : 로그인을 해주세요");
				}
            },error: function (e) {
                // console.log("Login Check Failed")
                // alert("장소 등록 실패");
                // console.log("장소 등록 실패");
				// onClickRegister();
				console.log(e);
            }

        });
    }
</script>
<%--<script defer src="/js/JusoAPI.js"></script>--%>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
