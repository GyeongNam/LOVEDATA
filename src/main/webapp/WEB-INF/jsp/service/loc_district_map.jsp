<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

        ul { list-style:none; padding:0; margin:0; }

        ul li { float:left; padding:0; margin:10px; }
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
							<button class="btn btn-link btn-block loc_highlight-selected-nav-menu" type="button">
								장소
							</button>
						</h2>
					</div>
					<div id="loc_collapse" class="show" aria-labelledby="headingLoc" data-parent="#loc">
						<div class="card-body center-pill">
							<p><a href="/service/loc_recommend" class="highlight-not-selected-text-menu">- 추천 장소</a></p>
							<p><a href="/service/loc_district_map" class="highlight-selected-text-menu">- 지역별 장소</a></p>
							<p><a href="/service/loc_registration" class="highlight-not-selected-text-menu">- 장소
								등록</a></p>
							<p><a href="/mypage_myplace/1" class="highlight-not-selected-text-menu">- 장소
								편집</a></p>
						</div>
					</div>
				</div>
			</div>
		</ul>
	</div>
	<div class="container-fluid" id="display_center" style="margin-right: 30px">
		<div class="col" id="top_navbar">
			<h2>지역별 장소</h2>
		</div>
		<div class="row justify-content-md-center">
<%--			<map name="distmap">--%>
<%--				<area shape="rect" coords="373, 283, 563, 577" alt="인천" onclick="onClickMap('인천')" style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="563, 369, 697, 483" alt="서울" onclick="onClickMap('서울')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="485, 121, 925, 719" alt="경기도" onclick="onClickMap('경기도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="687, 1, 1654, 707" alt="강원도" onclick="onClickMap('강원도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="657, 789, 739, 927" alt="세종특별자치시" onclick="onClickMap('세종특별자치시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="681, 895, 785, 1023" alt="대전광역시" onclick="onClickMap('대전광역시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="677, 585, 1183, 1109" alt="충청북도" onclick="onClickMap('충청북도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="293, 627, 803, 1119" alt="충청남도" onclick="onClickMap('충청남도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="367, 1031, 893, 1407" alt="전라북도" onclick="onClickMap('전라북도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="427, 1409, 555, 1493" alt="광주광역시" onclick="onClickMap('광주광역시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="171, 1317, 845, 1889" alt="전라남도" onclick="onClickMap('전라남도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="1181, 1425, 1359, 1563" alt="부산광역시" onclick="onClickMap('부산광역시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="1257, 1277, 1427, 1447" alt="울산광역시" onclick="onClickMap('울산광역시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="757, 1161, 1283, 1689" alt="경상남도" onclick="onClickMap('경상남도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="1043, 1139, 1188, 1299" alt="대구광역시" onclick="onClickMap('대구광역시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="879, 639, 1585, 1331" alt="경상북도" onclick="onClickMap('경상북도')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--				<area shape="rect" coords="1, 2025, 365, 2168" alt="제주특별시" onclick="onClickMap('제주특별시')"style="cursor: pointer; display: block; outline: 2px solid red">--%>
<%--			</map>--%>

<%--			<img src="/image/service/map/korean_district_map.png" usemap="#distmap" style="max-height: 100%; max-width: 100%">--%>
			<div class="row mt-5">
				<ul class="col-5" id="distList">
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">서울특별시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">부산광역시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">대구광역시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">인천광역시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">광주광역시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">대전광역시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">울산광역시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">세종특별자치시</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">경기도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">강원도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">충청북도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">충청남도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">전라북도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">전라남도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">경상북도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">경상남도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">제주특별자치도</a></li>
					<li><a onclick="onClickDistrict(this)" style="cursor: pointer">전국</a></li>
				</ul>
			</div>

			<div class="row justify-content-md-center mt-5 visually-hidden" id="locCardList">
				<c:forEach var="i" begin="0" end="2">
					<div class="col-md-4">
						<div class="card mb-4 shadow-sm" id="loc_${i}_card" style="cursor: pointer">
							<img class="bd-placeholder-img card-img-top" width="100%" height="225" id="loc_${i}_thumnail"
								 src="/image/init/Jungbu-Chungnam-0.jpg"
								 preserveAspectRatio="xMidYMid slice" focusable="false">
							</svg>
							<div class="card-body p-2">
								<div class="d-flex justify-content-between align-items-center p-1">
									<div class="d-flex">
										<a class="card-text loc_rec-locTitle" id="loc_${i}_name">테스트_${i}</a>
									</div>
									<div class="d-flex align-items-center">
										<img src="/image/icon/view.png" class="loc_icon" alt="조회수">
										<span class="align-middle" id="loc_${i}_view">123123</span>
										<img src="/image/icon/comment.png" class="loc_icon" alt="댓글">
											<%--									Todo 댓글 항목 Location Entity에 추가하기--%>
										<span class="align-middle" id="loc_${i}_comment">99</span>
										<img src="/image/icon/like/love_black.png" class="loc_icon" alt="찜하기">
										<span class="align-middle" id="loc_${i}_like" name="loc_${i}_like">123</span>
											<%--									<span class="d-none">${result.dtoList.get(i).loc_no}</span>--%>
											<%--									<span class="d-none">${result.dtoList.get(i).loc_uuid}</span>--%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
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
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
		crossorigin="anonymous"></script>
<%--<script defer src="/js/bootstrap.js"></script>--%>
<script defer src="/js/loc_recommend.js"></script>
<script defer src="/js/loc_common.js"></script>
<%--<script src="http://mattstow.com/experiment/responsive-image-maps/jquery.rwdImageMaps.min.js"></script>--%>

<%--<script>--%>
<%--    $(function(){--%>
<%--        $('img[usemap]').rwdImageMaps();--%>
<%--        $("#img").width("100%");--%>
<%--    });--%>
<%--</script>--%>
<script defer>
	let isCardRowVisible = false;

	function onClickMap(district) {
        console.log(district);
	}

    function onClickDistrict(dist) {
        resetTextColor()
        changeHighlightTextColor(dist);
        searchDistBestLocation(dist);
    }

    function resetTextColor() {
        let distList = document.getElementById("distList");

        for (let i = 0; i < distList.childElementCount; i++) {
			distList.children.item(i).children.item(0).style.color = "#000000";
        }
	}

    function changeHighlightTextColor(textElement) {
        textElement.style.color = "#ff0000";
	}

    function searchDistBestLocation(dist) {
        let distValue = dist.innerText;

        $.ajax({
            type: "POST",
            url: "/service/loc_district_map/search_loc",
            data: {
                distName : distValue
			},
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                resetCardVisiblity();

                if (response.length == 0) {
                    setVisibleLocCardList(false);
				} else {
                    for (let i = 0; i < response.length; i++) {
                        changeLocCard(response[i], i);
                    }

                    if (response.length < 3) {
                        for (let i = response.length; i < 3; i++) {
							setVisibleCard(false, i);
                        }
					}

                    setVisibleLocCardList(true);
				}
            },
            error: function (e) {
                console.log("통신 문제 발생!")
                return false;
            }
        });
	}

    function changeLocCard(locInfo, index) {
        document.getElementById("loc_" + index + "_thumnail").src = locInfo[2];
        document.getElementById("loc_" + index + "_name").innerText = locInfo[1];
        document.getElementById("loc_" + index + "_view").innerText = locInfo[3];
        document.getElementById("loc_" + index + "_comment").innerText = locInfo[4];
        document.getElementById("loc_" + index + "_like").innerText = locInfo[5];
        document.getElementById("loc_" + index + "_card").onclick = function () {
            location.href = ('/service/loc_detail?locNo=' + locInfo[0]);
        }
    }

    function resetCardVisiblity() {
        for (let i = 0; i < 3; i++) {
			setVisibleCard(true, i);
        }
	}

    function setVisibleCard(enableValue, index) {
        let card = document.getElementById("loc_" + index + "_card");
        let cardClass = card.getAttribute("class");

        if (enableValue) {
            if (cardClass == 'card mb-4 shadow-sm visually-hidden') {
                cardClass = 'card mb-4 shadow-sm';
                card.setAttribute("class", cardClass);
			}
		} else {
            if (cardClass == 'card mb-4 shadow-sm') {
                cardClass = 'card mb-4 shadow-sm visually-hidden';
                card.setAttribute("class", cardClass);
            }
		}
	}

    function setVisibleLocCardList(enableValue) {
        let cardRow = document.getElementById("locCardList")

		let rowClass = cardRow.getAttribute("class");

        if (enableValue) {
            if (rowClass == 'row justify-content-md-center mt-5 visually-hidden') {
                rowClass = 'row justify-content-md-center mt-5 visible';
                cardRow.setAttribute("class", rowClass);
                isCardRowVisible = 'false'
			}
		} else {
            if (rowClass == 'row justify-content-md-center mt-5 visible') {
                rowClass = 'row justify-content-md-center mt-5 visually-hidden';
                cardRow.setAttribute("class", rowClass);
                isCardRowVisible = 'true'
			}
		}

        return;
	}
</script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
