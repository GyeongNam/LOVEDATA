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
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/loc_recommend" class="highlight-not-selected-text-menu">- 추천 장소</a>
							</p>
							<p><a href="/service/loc_district_map" class="highlight-not-selected-text-menu">- 지역별 장소</a></p>
							<p><a href="/service/loc_registration" class="highlight-selected-text-menu">- 장소 등록</a>
							</p>
							<p><a href="/mypage_myplace/1" class="highlight-not-selected-text-menu">- 장소 편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>

	<c:set value="3" var="minLocImgCount"></c:set>
	<c:set value="10" var="maxLocImgCount"></c:set>
	<div class="container m-5" id="display_center" style="margin-right: 30px; margin-top: 30px">
		<sec:authorize access="!isAuthenticated()">
			<span>로그인 하지 않으면 장소를 등록할 수 없습니다.</span>
			<%
				if(true) return;
			%>
		</sec:authorize>
		<h1>장소 등록</h1>
		<div class="container-fluid">
			<form action="/service/loc_registration/regData" method="post" enctype="multipart/form-data">
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
													<button type="button" class="dropdown-item" onclick="addTag(this)" value="${tagList.get(i).name()}">
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
												<button type="button" class="btn btn-primary" value="${tagList.get(i)}">${tagList.get(i)}</button>
												<button type="button" class="btn btn-outline-danger btn" onclick="removeTag(this)">X</button>
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
						<span class="details">위치</span>
						<form method="post" name="form" id="form">
							<input type="button" onclick="goPopup()" value="주소검색">
							<input type="text" placeholder="우편번호" id="zipNo" name="zipNo" readonly value="" required>
							<input type="text" placeholder="전체 주소" id="fullRoadAddr" name="fullRoadAddr" readonly
								   required>
							<input type="text" placeholder="도로명 주소 (상세)" id="addrDetail" name="addrDetail" readonly
								   required>
							<input type="hidden" placeholder="시도명" id="siNm" name="siNm" readonly required>
							<input type="hidden" placeholder="시군구명" id="sggNm" name="sggNm" readonly required>
							<input type="hidden" placeholder="도로명 주소" id="roadAddr" name="roadAddr" readonly
								   required>
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
							<c:set var="user_no"><sec:authentication property="principal.user_no"/></c:set>
							<input type="hidden" name="user_no" id="user_no" value="${user_no}">
						</sec:authorize>
						<input type="hidden" name="user_no_debug" id="user_no_debug" value="0">
					</div>
					<div class="row d-flex align-items-end">
						<div class="col">
							<span class="details">이미지</span>
						</div>
						<div class="col d-flex justify-content-end">
							<image type="button" id="img_move_left" name="img_move_left"
								   src="/image/icon/left-arrow.png" onclick="onClickImgMoveLeft()" style="height: 30px"></image>
							<image type="button" id="img_move_right" name="img_move_right"
								   src="/image/icon/right-arrow.png" onclick="onClickImgMoveRight()" style="height: 30px"></image>
						</div>
					</div>
					<div>
						<input class="visually-hidden" id="imgInput" name="files" type="file" multiple accept="image/*" onchange="readImage()">
						<div id="canvas" class="row flex-nowrap mx-0 mt-3" style="overflow-x: scroll">
							<c:forEach var="i" begin="1" end="${maxLocImgCount}">
								<c:choose>
									<c:when test="${i eq 1}">
										<div class="card col-3 p-0 m-2">
											<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
											<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
												<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${i}"
													 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
											</div>
											<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
												<img class="btn btn-lg align-middle p-0" id="imgDel_${i}" onclick="deleteImage(this)"
													 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
											</div>
											<div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden" style="align-items: center">
												<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})" src="/image/icon/480px-Solid_white.png"
													 style="opacity : 0.0; z-index: 1;">
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="card col-3 p-0 m-2 visually-hidden">
											<img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
											<div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
												<img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${i}"
													 src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
											</div>
											<div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
												<img class="btn btn-lg align-middle p-0" id="imgDel_${i}" onclick="deleteImage(this)"
													 src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
											</div>
											<div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden" style="align-items: center">
												<img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})" src="/image/icon/480px-Solid_white.png"
													 style="opacity : 0.0; z-index: 1;">
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
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
    // document.domain = "localhost:8080"

    function goPopup() {
<%--        // 주소검색을 수행할 팝업 페이지를 호출합니다.
        // 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다. --%>
        var pop = window.open("/popup/jusoPopup", "pop", "width=570,height=420, scrollbars=yes, resizable=yes");

        <%-- // 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다. --%>
        //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes");
    }


    function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
        // 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
        document.getElementById("fullRoadAddr").value = roadAddrPart1;
        document.getElementById("addrDetail").value = addrDetail;
        document.getElementById("addrDetail").removeAttribute("disabled");
        document.getElementById("zipNo").value = zipNo;
        document.getElementById("siNm").value = siNm;
        document.getElementById("sggNm").value = sggNm;
        document.getElementById("roadAddr").value = rn + " " + buldMnnm + "-" + buldSlno;
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
            if (index >= ${maxLocImgCount}) {
                deleteFileListImage();
                return false;
            }
            // console.log(i + "번 째 아이템이 등록되었습니다.");
            let item = document.getElementById("img_" + (index+1));
            reader.onload= e => {
                item.src = e.target.result;
                item.parentElement.setAttribute("class", "card col-3 p-0 m-2");
            }
            if (index  < (${maxLocImgCount} - 1)) {
                document.getElementById("img_" + (index+2)).parentElement.setAttribute("class", "card col-3 p-0 m-2");
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

    function deleteFileListImage() {
        let dt = new DataTransfer();
        dt.files = input.files;

        for (let i = 0; i < ${maxLocImgCount}; i++) {
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
        for (let file of input.files){
            if (file !== input.files[index - 1]){
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

        for (let i = 1; i <= ${maxLocImgCount}; i++) {
            if (i >= index) {
                if (i !== ${maxLocImgCount}) {
                    document.getElementById("img_"+i).src = document.getElementById("img_"+(i+1)).src;
                } else {
                    document.getElementById("img_"+i).src = "/image/icon/480px-Solid_white.png";
                }
            }

            // if (dt.items.length+1 == i) {
            //     document.getElementById("imgAdd_"+i).parentElement.setAttribute("class", "d-flex justify-content-center card-img-overlay");
            //     document.getElementById("imgDel_"+i).parentElement.setAttribute("class", "d-flex justify-content-end card-img-overlay p-0 visually-hidden");
            // }

            if (dt.items.length+1 < i) {
                document.getElementById("img_"+i).parentElement.setAttribute("class", "card col-3 p-0 m-2 visually-hidden");
            }
        }

        input.files = dt.files;
        console.log(dt.files);
        console.log(input.files);

        for (let i = input.files.length + 1; i <= ${maxLocImgCount}; i++) {
            let img = document.getElementById("img_" + i);
            img.src = "/image/icon/480px-Solid_white.png";
        }

        toggleAddDelBtn(input.files.length);
    }

    function onClickRegister() {
        console.log("submit butten clicked");
        var $fileUpload = $("input[type='file']");
        var loginCheck = null;
        var debugCheck = {"debug": true}

        if (tagList.length < 3) {
            alert("해시태그는 최소 3개 이상 추가해야합니다.");
            return
        }

        // $.ajax({
        //     type: "POST",
        //     url: "/rest/authenticationCheck",
        //     data: JSON.stringify(debugCheck),
        //     dataType: 'json',
        //     contentType: "application/json; charset=UTF-8",
        //     beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
        //         xhr.setRequestHeader(header, token);
        //     },
        //     success: function (response) {
        //         // do something ...
        //         console.log("Login Check Success");
        //         console.log("is login : " + response);
		//
        //         if (response) {
        //
		// 		}
        //         else {
        //             alert("장소 등록 실패 : 로그인을 해주세요");
        //             console.log("장소 등록 실패 : 로그인을 해주세요");
		// 		}
        //     },error: function (e) {
        //         // console.log("Login Check Failed")
        //         // alert("장소 등록 실패");
        //         // console.log("장소 등록 실패");
		// 		// onClickRegister();
		// 		console.log(e);
        //     }
		//
        // });

        if (parseInt($fileUpload.get(0).files.length) < ${minLocImgCount}) {
            alert("최소 3개의 이미지 파일은 업로드 해야합니다.")
        } else if (parseInt($fileUpload.get(0).files.length) > ${maxLocImgCount}) {
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
					if (response == "Tag register Success") {
                        formData.submit();
                        console.log("장소 등록 성공");
                        alert("장소 등록 성공");
					} else if (response == "Tag register Fail") {
                        console.log("장소 등록 실패");
                        alert("장소 등록 실패");
					} else if (response == "Authentication Failed") {
                        console.log("현재 로그인 되어 있지 않습니다.");
                        alert("다시 로그인 해주세요");
                    }
                },
                error: function (e) {
                    console.log(e);
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

        let leftObj = input.files.item(selectedImageIndex-2);
        let selectedObj = input.files.item(selectedImageIndex-1);
        let leftObjImg = document.getElementById("img_" + (selectedImageIndex-1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex -1 === i) {
                dt.items.add(leftObj);
                document.getElementById("img_" + (i+1)).src = leftObjImg;
                continue;
            }

            if (selectedImageIndex -2 === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i+1)).src = selectedObjImg;
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
        let selectedObj = input.files.item(selectedImageIndex-1);
        let rightObjImg = document.getElementById("img_" + (selectedImageIndex+1)).src;
        let selectedObjImg = document.getElementById("img_" + (selectedImageIndex)).src;

        console.log(selectedImageIndex);
        for (let i = 0; i < input.files.length; i++) {
            if (selectedImageIndex -1 === i) {
                dt.items.add(rightObj);
                document.getElementById("img_" + (i+1)).src = rightObjImg;
                continue;
            }

            if (selectedImageIndex === i) {
                dt.items.add(selectedObj);
                document.getElementById("img_" + (i+1)).src = selectedObjImg;
                continue;
            }

            dt.items.add(input.files.item(i));
        }

        input.files = dt.files;
        console.log(input.files);
        onSelectImage(selectedImageIndex + 1);
    }

</script>
<%--<script defer src="/js/JusoAPI.js"></script>--%>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
