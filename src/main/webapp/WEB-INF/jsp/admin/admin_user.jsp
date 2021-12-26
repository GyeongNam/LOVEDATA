<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html><jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>
<jsp:useBean id="simpleDateTimeFormatter" class="com.project.love_data.util.SimpleLocalDateTimeFormatter"></jsp:useBean>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/service/loc.css">
<%--    <link rel="stylesheet" href="/css/ServiceCenter.css" >--%>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
        th, td {
            vertical-align : middle;
        }
    </style>
    <title>Admin User</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body class="bg-light">
<div class="container-fluid d-flex" style="padding-top: 100px">
    <div class="col-2" id="sidebar">
        <ul class="nav nav-pills flex-column col-2 position-fixed" style="top: 40%">
            <div class="accordion text-center" id="loc">
                <hr>
                <div class="card">
                    <div class="card-header" id="headingLoc">
                        <h2 class="mb-0">
                            <button class="btn btn-link btn-block loc_highlight-selected-nav-menu" type="button"
                                    data-toggle="collapse"
                                    data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne">
                                어드민
                            </button>
                        </h2>
                    </div>
                    <div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
                        <div class="card-body center-pill">
                            <p><a href="/admin/dash" class="highlight-not-selected-text-menu">대시보드</a></p>
                            <p><a href="/admin/user/1" class="highlight-selected-text-menu">유저 관리</a></p>
                            <p><a href="/admin/buisnessman" class="highlight-not-selected-text-menu">사업자 관리</a></p>
                            <p><a href="/admin/event" class="highlight-not-selected-text-menu">이벤트 관리</a></p>
                            <p><a href="/admin/SendMessage" class="highlight-not-selected-text-menu">메시지 발송</a></p>
                            <p><a type="button" class="accordion highlight-not-selected-text-menu" data-toggle="collapse" data-target="#service_collapse" aria-expanded="false">공지사항과 문의사항</a></p>
                            <div id="service_collapse" class="panel-collapse collapse">
                                <p>
                                    <a href="/admin/notice/1" class="highlight-not-selected-text-menu">- 공지사항</a>
                                </p>
                                <p>
                                    <a href="/admin/qna/1" class="highlight-not-selected-text-menu">- 문의사항</a>
                                </p>
                            </div>
                            <p><a href="/admin/upload_cache" class="highlight-not-selected-text-menu">upload 파일 캐시 삭제</a></p>
                            <p class="mb-0"><a href="/admin/report_center" class="highlight-not-selected-text-menu">신고 센터</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container-xl p-5 ms-3">
        <div class="row justify-content-md-start">
            <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show active" id="recent-post" role="tabpanel" aria-labelledby="recent-post-tab">
                    <h2>유저관리</h2>
                    <div class="row my-3">
                            <div class="collapse navbar-collapse d-flex justify-content-end" id="bs-example-navbar-collapse-1">
                                    <ul class="navbar-nav"></ul>
                                    <select class="select2-dropdown mx-2" id="Notice_select">
                                        <option value="1" >유저 이름</option>
                                        <option value="2" selected>유저 닉네임</option>
                                    </select>
                                    <input type="text" class="input-box mx-2" placeholder="검색" id="keyword" name="keyword"/>
                                    <button class="btn btn-primary mx-2" type="button" id="searchBtn" onclick="Usearch()">Search</button>
                            </div>
                        <div class="col-12 d-flex justify-content-center align-items-md-center">
                            <table class="table text-center" id="searchResultTable">
                                <thead>
                                <th scope="col">No</th>
                                <th scope="col">유저 번호</th>
                                <th scope="col">유저 닉네임</th>
                                <th scope="col">유저 이름</th>
                                <th scope="col">가입 날짜</th>
                                <th scope="col">상태</th>
                                <th scope="col">신고된 게시글 수</th>
                                <th scope="col">가입 방식</th>
                                <th scope="col">관리</th>
                                </thead>
                                <tbody id="tableBody">
                                <c:forEach var="user" items="${user}" varStatus="index">
                                    <c:choose>
                                        <c:when test="${user.user_Activation eq false}">
                                        <tr style="cursor:hand; background: #ffdef2;" title="장소:${loc.get(index.count-1)}    코스:${cor.get(index.count-1)}    리뷰:${re.get(index.count-1)}    댓글:${com.get(index.count-1)}">

                                            <td>${index.count}</td>
                                            <td>${user.user_no}</td>
                                            <td>${user.user_nic}</td>
                                            <td>${user.user_name}</td>
                                            <td>${user.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                            <td>정지</td>
                                            <td>${rep.get(index.count-1)}</td>
                                            <td>${user.social_info}</td>
                                            <td><button onclick="location.href='/admin_user_detail/${user.user_no}'"class="btn btn-primary mx-2">관리</button></td>
                                        </tr>
                                        </c:when>
                                        <c:when test="${user.user_Activation eq true}">
                                            <tr style="cursor:hand; background: #e2eeff;" title="장소:${loc.get(index.count-1)}    코스:${cor.get(index.count-1)}    리뷰:${re.get(index.count-1)}    댓글:${com.get(index.count-1)}">
                                                <td>${index.count}</td>
                                                <td>${user.user_no}</td>
                                                <td>${user.user_nic}</td>
                                                <td>${user.user_name}</td>
                                                <td>${user.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                                <td>활동</td>
                                                <td>${rep.get(index.count-1)}</td>
                                                <td>${user.social_info}</td>
                                                <td><button onclick="location.href='/admin_user_detail/${user.user_no}'"  class="btn btn-primary mx-2">관리</button></td>
                                            </tr>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="col" id="pu_navbar">
                            <div class="container d-flex" id="">
                                <div class="col" id="page_number">
                                    <nav aria-label="Page navigation example">
                                        <input id="qu_pages" value="${qu_page_size}" type="hidden">
                                        <input id="qu_pagess" value="${qu_page}" type="hidden">
                                        <div class="pagination justify-content-center" id="pagination justify-content-center">
                                            <p onclick="ad_subpage()"> < </p>
                                            <c:choose>
                                                <c:when test="${search eq false}">
                                                    <c:forEach var="qu_pages" begin="1" end="${qu_page}" step="1">
                                                        <div class="page-item mx-2" id="${qu_pages}">
                                                            <li class="page-item ${qu_pages}">
                                                                <a href="/admin/user/${qu_pages}">${qu_pages}</a>
                                                            </li>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                                <c:when test="${search eq true}">
                                                    <c:forEach var="qu_pages" begin="1" end="${qu_page}" step="1">
                                                        <div class="page-item mx-2" id="${qu_pages}">
                                                            <li class="page-item ${qu_pages}">
                                                                <a href="/admin/user/search/${menu}/${text}/${qu_pages}">${qu_pages}</a>
                                                            </li>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                            </c:choose>
                                            <p onclick="ad_plupage()"> > </p>
                                        </div>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="hot-post" role="tabpanel" aria-labelledby="hot-post-tab">
                    <span>test hot-post-tab</span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<!--  부트스트랩 js 사용 -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script defer src="/js/ServiceCenter.js"></script>
</html>
