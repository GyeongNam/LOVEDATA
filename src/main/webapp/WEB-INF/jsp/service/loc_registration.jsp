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
            <div class="accordion text-center" id="course">
                <div class="card">
                    <div class="card-header" id="headingCourse">
                        <h2 class="mb-0">
                            <form action="/" method="get" class="form-label">
                                <button type="submit" class="btn btn-link btn-block"
                                        style="text-decoration: none; color: #9448C3">코스
                                </button>
                            </form>
                        </h2>
                    </div>
                </div>
            </div>
            <div class="accordion text-center" id="calendar">
                <div class="card">
                    <div class="card-header" id="headingCalendar">
                        <h2 class="mb-0">
                            <form action="/" method="get" class="form-label">
                                <button type="submit" class="btn btn-link btn-block"
                                        style="text-decoration: none; color: #9448C3">캘린더
                                </button>
                            </form>
                        </h2>
                    </div>
                </div>
                <hr>
            </div>
        </ul>
    </div>
    <div class="container m-5" id="display_center" style="margin-right: 30px; margin-top: 30px">
        <h1>장소 등록</h1>
        <div class="container-fluid loc_registration-box-outline">
            <div class="row ">
                <div class="col">
                    <div class="row justify-content-start">
                        <p class="h4 col-2 m-1 text-center">이름*</p>
                        <textarea class="col-6 m-1" name="" id="" cols="10" rows="1"></textarea>
                    </div>
                </div>
            </div>
            <div class="row ">
                <div class="col" id="top_hashtag">
                    <nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
                        <div class="collapse navbar-collapse" id="tag-navbar-collapse">
                            <ul class="navbar-nav">
                                <li class="nav-item dropdown">
                                    <button class="nav-link dropdown-toggle" role="button" id="tagDropdownMenuLink"
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
            <div class="row ">
                <div class="col">
                    <div class="row align-items-center">
                        <p class="h4 col-2 m-1 text-center">위치*</p>
                        <textarea class="col-6 m-1" name="" id="" cols="10" rows="1"></textarea>
                        <textarea class="col-6 m-1" name="" id="" cols="10" rows="1"></textarea>
                        <textarea class="col-6 m-1" name="" id="" cols="10" rows="1"></textarea>
                    </div>
                </div>
            </div>
            <div class="row ">
                <div class="col">
                    <div class="row justify-content-start">
                        <p class="h4 col-2 m-1 text-center">전화번호</p>
                        <textarea class="col-6 m-1" name="" id="" cols="10" rows="1"></textarea>
                    </div>
                </div>
            </div>
            <div class="row ">
                <div class="col">
                    <div class="row justify-content-start">
                        <p class="h4 col-2 m-1 text-center">이미지 등록</p>
                    </div>
                </div>
            </div>
            <div class="row d-flex align-items-center" style="height: 300px">
                <div class="overflow-auto">
                    <h1 class="col-4 text-center">img</h1>
                    <h1 class="col-4 text-center">img</h1>
                    <h1 class="col-4 text-center">img</h1>
<%--                    @Todo image 스크롤바 만들기--%>
                </div>
            </div>
        </div>
    </div>
</div>

<!--  부트스트랩 js 사용 -->
<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
