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
    <%--	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">--%>
    <%--	<link rel="stylesheet" type="text/css" href="/css/Bootstarp_test/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/service/loc.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
        body{
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
                            <svg class="bd-placeholder-img card-img-top" width="100%" height="400"
                                 xmlns="http://www.w3.org/2000/svg" role="img"
                                 aria-label="Placeholder: Thumbnail"
                                 preserveAspectRatio="xMidYMid slice" focusable="false">
                                <rect width="100%" height="100%" fill="#55595c">
                                    <image height="100%" width="100%" href="${imgList.get(i).img_url}"></image>
                                </rect>
                            </svg>
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
                        <button class="btn btn-outline-danger col-2">공유</button>
                    </div>
                    <div class="row d-flex">
                        <h3>지역 : ${dto.siDo}  ${dto.siGunGu}</h3>
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
                <img src="/image/icon/like/love_black.png" class="loc_icon" alt="찜하기"
                     onclick="onClickLike(this)">
            </div>
        </div>
        <div class="row justify-content-md-start">
            <ul class="nav nav-pills" id="pills-tab" role="tablist" style="height:60px; padding-top: 5px; padding-bottom: 5px">
                <li class="nav-item" role="presentation">
                    <button class="mw-100 mh-100 nav-link active" id="location-comment-tab" data-bs-toggle="pill" data-bs-target="#location-comment" type="button" role="tab" aria-controls="location-comment" aria-selected="true">댓글</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="location-info-tab" data-bs-toggle="pill" data-bs-target="#location-info" type="button" role="tab" aria-controls="location-info" aria-selected="false">설명</button>
                </li>
            </ul>
            <div class="tab-content" id="pills-tabContent">
<%--      댓글--%>
                <div class="tab-pane fade show active" id="location-comment" role="tabpanel" aria-labelledby="location-comment-tab">

                </div>
<%--    설명--%>
                <div class="tab-pane fade" id="location-info" role="tabpanel" aria-labelledby="location-info-tab">
                    <div class="container">
                        <div class="d-flex">
                            <span class="h3">${dto.info}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--  부트스트랩 js 사용 -->
<%--<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js"--%>
<%--        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"--%>
<%--        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"--%>
<%--        crossorigin="anonymous"></script>--%>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="/js/bootstrap.js"></script>
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
