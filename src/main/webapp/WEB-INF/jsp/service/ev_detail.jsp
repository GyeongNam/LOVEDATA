<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<jsp:useBean id="defaultDateTimeFormatter" class="com.project.love_data.util.DefaultLocalDateTimeFormatter"></jsp:useBean>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/service/loc.css">
    <link rel="stylesheet" href="/css/ServiceCenter.css">
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
                            <button class="btn btn-link btn-block" type="button" data-toggle="collapse"
                                    data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne"
                                    style="text-decoration: none; color: #FF6699; font-weight: bold">
                                고객센터
                            </button>
                        </h2>
                    </div>
                    <div id="loc_collapse" class="collapse show" aria-labelledby="headingLoc" data-parent="#loc">
                        <div class="card-body center-pill">
                            <p><a href="/ServiceCenter/Notice/1" class="highlight-not-selected-text-menu ">- 공지 사항</a></p>
                            <p><a href="/ServiceCenter/Questions/1" class="highlight-not-selected-text-menu">- 문의 사항</a></p>
                            <p><a href="/ServiceCenter/Event/1" class="highlight-selected-text-menu">- 이벤트</a></p>
                            <p><a href="/ServiceCenter/Policy" class="highlight-not-selected-text-menu">- LOVEDATA 정책</a></p>
                            <p><a href="/ServiceCenter/Withdrawal" class="highlight-not-selected-text-menu">- 회원 탈퇴</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container col-lg-6 ">
        <h2 class="mx-2">${eve.ev_title}</h2>
        <sec:authorize access="isAuthenticated()">
            <sec:authorize access="hasAnyRole('ADMIN')">
                <button class="mx-2" onclick="onclick=location.href='/ServiceCenter/Event_Update/'+${eve.ev_no}">수정하기</button>
                <form method="post" action="/ServiceCenter/Event_Delete" id="nform">
                    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
                    <input type="hidden" name="noti_no" value="${eve.ev_no}">
                    <button class="mx-2" onclick="noti_de()">삭제하기</button>
                </form>
            </sec:authorize>
        </sec:authorize>
        <table class="table-bordered table text-center " >
            <thead>
            <th scope="col">시작일</th>
            <th scope="col">종료일</th>
            <th scope="col">추첨일</th>
            <th scope="col">전체 참여 횟수</th>
            <th scope="col">나의 참여 횟수</th>
            </thead>
            <tbody id="tableBody">
            <tr>
                <td>${eve.ev_start}</td>
                <td>${eve.ev_stop}</td>
                <td>${eve.ev_end}</td>
                <td>${all_attend}</td>
                <td>${my_attend}</td>
            </tr>
            </tbody>
        </table>
        <div>
            <span>${eve.ev_text}</span>
        </div>
        <sec:authorize access="isAnonymous()">
            <div class="border text-center" >
                <span>나의 포인트 : ${point} </span>
            </div>

            <div class="d-flex">
                <button style="width: 100%;"  class="my-2 btn-primary" onclick="alert('로그인을 해주세요')">참여하기</button>
            </div>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
        <div class="border text-center" >
            <span>나의 포인트 : ${point} </span>
        </div>
        <c:choose>
            <c:when test="${eve.ev_activation eq false}">
                <div class="d-flex">
                    <button style="width: 100%;"  class="my-2 btn-primary" onclick="alert('종료된 이벤트입니다.')">참여하기</button>
                </div>
            </c:when>
            <c:when test="${eve.ev_activation eq true}">
                <c:choose>
                    <c:when test="${point < 100}">
                        <div class="d-flex">
                            <button style="width: 100%;"  class="my-2 btn-primary" onclick="alert('포인트가 부족합니다 \n한번에 100포인트가 필요합니다.')">참여하기</button>
                        </div>
                    </c:when>
                    <c:when test="${point >= 100}">
                        <div class="d-flex">
                            <button style="width: 100%;"  class="my-2 btn-primary" onclick="alert('참여하였습니다.'); location.href='/ServiceCenter/Event_attend/${eve.ev_no}';">참여하기</button>
                        </div>
                    </c:when>
                </c:choose>
            </c:when>
        </c:choose>
        </sec:authorize>
    </div>
</div>
</body>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script defer src="/js/ServiceCenter.js"></script>
</html>