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
                            <p><a href="/ServiceCenter/Notice/1" class="loc_highlight-selected-text-menu ">- 공지 사항</a></p>
                            <p><a href="/ServiceCenter/Questions/1" class="loc_highlight-not-selected-text-menu">- 문의 사항</a></p>
                            <p><a href="/ServiceCenter/Policy" class="loc_highlight-not-selected-text-menu">- LOVEDATA 정책</a></p>
                            <p><a href="#" class="loc_highlight-not-selected-text-menu">- 회원 탈퇴</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container col-lg-6 ">
<%--        <input type="hidden" id="text" value="${noti.noti_text}">--%>
<%--       <span> ${noti.noti_no}</span>--%>
       <span>제목 : ${noti.noti_title}</span>
<%--       <span> ${noti.noti_manager}</span>--%>

       <span>${noti.noti_text}</span>
<%--       <span> ${noti.noti_date}</span>--%>
<%--       <span> ${noti.noti_viewCount}</span>--%>
<%--       <span> ${noti.noti_activation}</span>--%>
    </div>
</div>
</body>
</html>