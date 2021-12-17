<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html><jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>
<jsp:useBean id="simpleDateTimeFormatter" class="com.project.love_data.util.SimpleLocalDateTimeFormatter"></jsp:useBean>
<head>
    <frame-options disabled="true"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/service/loc.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
    </style>
    <title>Admin Buisnessman</title>
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
                            <p><a href="/admin/user/1" class="highlight-not-selected-text-menu">유저 관리</a></p>
                            <p><a href="/admin/buisnessman" class="highlight-selected-text-menu">사업자 관리</a></p>
                            <p><a href="/admin/SendMessage" class="highlight-not-selected-text-menu">메시지 발송</a></p>
                            <p><a type="button" class="accordion highlight-not-selected-text-menu" data-toggle="collapse" data-target="#service_collapse" aria-expanded="false">공지사항과 문의사항</a></p>
                            <div id="service_collapse" class="panel-collapse collapse">
                                <p>
                                    <a href="/admin/notice_add" class="highlight-not-selected-text-menu">- 공지사항 작성</a>
                                </p>
                                <p>
                                    <a href="/admin/qna/1" class="highlight-not-selected-text-menu">- 문의사항 답변</a>
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
    <div class="container-fluid" id="display_center" style="margin-right: 30px">
        <div class="tab-content mx-2" >
            <h2>신청유저 정보</h2>
            <table class="table-bordered table text-center " >
                <thead>
                <th scope="col">번호</th>
                <th scope="col">닉네임</th>
                <th scope="col">유저 이름</th>
                <th scope="col">가입 날짜</th>
                <th scope="col">상태</th>
                <th scope="col">가입 방식</th>
                </thead>
                <tbody id="tableBody">
                <tr>
                    <td>${user.user_no}</td>
                    <td>${user.user_nic}</td>
                    <td>${user.user_name}</td>
                    <td>${user.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                    <c:choose>
                        <c:when test="${user.user_Activation eq false}">
                            <td>정지</td>
                        </c:when>
                        <c:when test="${user.user_Activation eq true}">
                            <td>활동</td>
                        </c:when>
                    </c:choose>
                    <td>${user.social_info}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="tab-content mx-2" >
            <h2>사업자 신청 정보</h2>
            <table class="table-bordered table text-center " >
                <thead>
                <th scope="col">사업체 이름</th>
                <th scope="col">대표자 이름</th>
                <th scope="col">연락처</th>
                <th scope="col">신청 날짜</th>
                <th scope="col">신청 현황</th>
                </thead>
                <tbody >
                <tr>
                    <td>${bizReg.bizName}</td>
                    <td>${bizReg.bizCeoName}</td>
                    <td>${bizReg.bizCall}</td>
                    <td>${bizReg.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                    <c:choose>
                        <c:when test="${bizReg.certified eq false}">
                            <td>대기</td>
                        </c:when>
                        <c:when test="${bizReg.certified eq true}">
                            <td>완료</td>
                        </c:when>
                    </c:choose>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="tab-content row justify-content-center" >
            <h2>사업자 등록증</h2>
            <div class="d-flex">
                <button class="mx-2 btn-primary" onclick="location.href='/admin/buisnessman_file_download/${bizReg.brNo}'">등록증 다운로드</button>
            </div>

            <img style="width: 100%;" class="mx-2 my-2 d-flex" src="${bizReg.url}">

            <c:choose>
                <c:when test="${bizReg.certified eq false}">
                    <div class="d-flex">
                        <button style="width: 100%;"  class="mx-2 my-2 btn-primary" onclick="location.href='/admin/buisnessman_start/${bizReg.brNo}'">승인</button>
                    </div>
                </c:when>
            </c:choose>

        </div>
    </div>
</div>
</body>
</html>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/ServiceCenter.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>

</html>