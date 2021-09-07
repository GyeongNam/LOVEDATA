<%@ page import="org.springframework.security.core.annotation.AuthenticationPrincipal" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link href="/css/ServiceCenter.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/service/loc.css">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>LOVEDATA:ServiceCenter</title>
</head>
<%@ include file="../layout/header.jsp"%>
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
                            <p><a href="/ServiceCenter/Notice/1" class="loc_highlight-not-selected-text-menu ">- 공지 사항</a></p>
                            <p><a href="/ServiceCenter/Questions/1" class="loc_highlight-not-selected-text-menu">- 문의 사항</a></p>
                            <p><a href="/ServiceCenter/Policy" class="loc_highlight-selected-text-menu">- LOVEDATA 정책</a></p>
                            <p><a href="/ServiceCenter/Withdrawal" class="loc_highlight-not-selected-text-menu">- 회원 탈퇴</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </ul>
    </div>
    <div class="container-fluid" id="display_center" style="margin-right: 30px">
        <div class="col" id="top_navbar">
            <nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
                <h3 class="mx-3">LOVE DATA 정책</h3>
            </nav>
        </div>
        <div id="Policy" class="tabcontent">
            <div class="Ptab">
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy1')"id="defaultOpen">총직</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy2')">개인정보처리방침</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy3')">이용계약 당사자의 의무</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy4')">서비스 이용 및 이용제한</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy5')">청약철회, 과오납금의 환급 및 이용계약의 해지</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy6')">손해배상 및 면책조항</button>
            </div>
            <div id="Policy1" class="Ptabcontent">
                <h3>${Chapter1title}</h3>
                <p>${Article1}</p>

                <p>${Article2}</p>

                <p>${Article3}</p>

                <p>${Article4}</p>

                <p>${Article5}</p>

                <p>${Article6}</p>

                <p>${Article7}</p>
            </div>

            <div id="Policy2" class="Ptabcontent">
                <h3>${Chapter2title}</h3>
                <p>${Article8}</p>

            </div>

            <div id="Policy3" class="Ptabcontent">
                <h3>${Chapter3title}</h3>
                <p>${Article9}</p>

                <p>${Article10}</p>
            </div>
            <div id="Policy4" class="Ptabcontent">
                <h3>${Chapter4title}</h3>
                <p>${Article11}</p>

                <p>${Article12}</p>

                <p>${Article13}</p>

                <p>${Article14}</p>

                <p>${Article15}</p>

                <p>${Article16}</p>

                <p>${Article17}</p>

                <p>${Article18}</p>

                <p>${Article19}</p>

                <p>${Article20}</p>
            </div>
            <div id="Policy5" class="Ptabcontent">
                <h3>${Chapter5title}</h3>
                <p>${Article21}</p>

                <p>${Article22}</p>

                <p>${Article23}</p>

                <p>${Article24}</p>
            </div>
            <div id="Policy6" class="Ptabcontent">
                <h3>${Chapter6title}</h3>
                <p>${Article25}</p>

                <p>${Article26}</p>

                <p>${Article27}</p>

                <p>${Article28}</p>

                <p>${Article29}</p>
            </div>
        </div>

    </div>
</div>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/ServiceCenter.js"></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    body {
        font-family: 'Jua', sans-serif;
    }
</style>
</html>