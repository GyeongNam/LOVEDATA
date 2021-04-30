<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>

<div class="container-fluid m-3 d-flex align-items-center">
    <div class="col-2">
        <ul class="nav nav-pills flex-column align-middle">
            <div class="accordion text-center" id="loc">
                <hr>
                <div class="card">
                    <div class="card-header" id="headingLoc">
                        <h2 class="mb-0">
                            <button class="btn btn-link btn-block" type="button" data-toggle="collapse"
                                    data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne">
                                장소
                            </button>
                        </h2>
                    </div>
                    <div id="loc_collapse" class="collapse" aria-labelledby="headingLoc" data-parent="#loc">
                        <div class="card-body center-pill">
                            <p><a href="#">- 추천 장소</a></p>
                            <p><a href="#">- 장소 등록/편집</a></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="accordion text-center" id="course">
                <div class="card">
                    <div class="card-header" id="headingCourse">
                        <h2 class="mb-0">
                            <form action="/" method="get" class="form-label">
                                <button type="submit" class="btn btn-link btn-block">추천장소</button>
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
                                <button type="submit" class="btn btn-link btn-block">캘린더</button>
                            </form>
                        </h2>
                    </div>
                </div>
                <hr>
            </div>
        </ul>
    </div>
    <div class="container" id="display_center" style="margin-right: 30px">
        <div class="col" id="navbar">
            <nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                </button> <a class="navbar-brand text-dark" href="/">추천장소</a>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="navbar-nav col-9">
                        <li class="nav-item dropdown">
                            <button class="nav-link dropdown-toggle" role="button" id="navbarDropdownMenuLink" data-toggle="dropdown">조회순</button>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                <a class="dropdown-item" href="#">조회순</a>
                                <a class="dropdown-item" href="#">추천순</a>
                                <a class="dropdown-item" href="#">최근</a>
                                <a class="dropdown-item" href="#">가장 오래전</a>
                                <div class="dropdown-divider">
                                </div> <a class="dropdown-item" href="#">Separated link</a>
                            </div>
                        </li>
                    </ul>
<%--                    <form action="/test/service_sample/testvalue" method="get">--%>
                    <form action="/test/Bootstrap/Collapse_Test/value" method="get">
                        <input type="text" placeholder="장소 검색" id="text" name="text"/>
                        <button class="btn btn-primary" type="submit">Search</button>
                    </form>
                </div>
            </nav>
        </div>
        <div class="col" id="hashtag">
            <nav class="navbar navbar-expand-sm navbar-light bg-light static-top">
                <div class="collapse navbar-collapse" id="tag-navbar-collapse">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <button class="nav-link dropdown-toggle" role="button" id="tagDropdownMenuLink" data-toggle="dropdown">해시태그</button>
<%--                            @Todo 자바스크립트 써서 동적으로 해시태그 추가하는 것 구현하기--%>
<%--                            https://www.w3schools.com/jsref/event_onclick.asp--%>
                            <div class="dropdown-menu" aria-labelledby="tagDropdownMenuLink">
                                <button type="button" class="dropdown-item" href="#">Action A</button>
                                <button type="button" class="dropdown-item" href="#">Action B</button>
                                <button type="button" class="dropdown-item" href="#">Action C</button>
                                <button type="button" class="dropdown-item" href="#">Action D</button>
                                <button type="button" class="dropdown-item" href="#">Action E</button>
                                <button type="button" class="dropdown-item" href="#">Action F</button>
                                <button type="button" class="dropdown-item" href="#">Action G</button>
                                <button type="button" class="dropdown-item" href="#">Action H</button>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="col" id="loc_quater_display">
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
        </div>
        <div class="col" id="page_number">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&lt;</span>
                        </a>
                    </li>
                    <li class="page-item disabled"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&gt;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
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
</body>
<%@ include file="../layout/footer.jsp" %>
</html>
