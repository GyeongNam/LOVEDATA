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
    <link rel="stylesheet" href="/css/Bootstarp_test/">
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>

<div class="container-fluid m-3 d-flex align-items-center">

<%--    <div class="row">--%>
<%--        <ul class="nav nav-pills flex-column">--%>
<%--            <li>--%>
<%--                <div class="accordion center_text" id="accordionExample">--%>
<%--                    <div class="card">--%>
<%--                        <div class="card-header" id="headingOne">--%>
<%--                            <h2 class="mb-0">--%>
<%--                                <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"--%>
<%--                                        data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">--%>
<%--                                    Collapsible Group Item #1--%>
<%--                                </button>--%>
<%--                            </h2>--%>
<%--                        </div>--%>
<%--                        <div id="collapseOne" class="collapse" aria-labelledby="headingOne"--%>
<%--                             data-parent="#accordionExample">--%>
<%--                            <div class="card-body">--%>
<%--                                Some placeholder content for the first accordion panel. This panel is shown by default,--%>
<%--                                thanks to the <code>.show</code> class.--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--        </ul>--%>
<%--    </div>--%>

    <%--	<div class="row">--%>
    <%--		<ul class="nav nav-pills flex-column">--%>
    <%--			<li>--%>
    <%--				<hr>--%>
    <%--				<div class="accordion" id="loc">--%>
    <%--					<div class="card">--%>
    <%--						<div class="card-header" id="headingLoc">--%>
    <%--							<h2 class="mb-0">--%>
    <%--								<button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#loc_collapse" aria-expanded="true" aria-controls="collapseOne">--%>
    <%--									장소--%>
    <%--								</button>--%>
    <%--							</h2>--%>
    <%--						</div>--%>
    <%--						<div id="loc_collapse" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">--%>
    <%--							<div class="card-body center-pill">--%>
    <%--								<p><a href="#">- 추천 장소</a></p>--%>
    <%--								<p><a href="#">- 장소 등록/편집</a></p>--%>
    <%--							</div>--%>
    <%--						</div>--%>
    <%--					</div>--%>
    <%--				</div>--%>
    <%--				<div class="accordion" id="course">--%>
    <%--					<div class="card">--%>
    <%--						<div class="card-header" id="headingCourse">--%>
    <%--							<h2 class="mb-0">--%>
    <%--								<form action="/" method="get">--%>
    <%--									<button class="btn btn-link btn-block text-left" type="submit">--%>
    <%--										코스--%>
    <%--									</button>--%>
    <%--								</form>--%>
    <%--							</h2>--%>
    <%--						</div>--%>
    <%--					</div>--%>
    <%--				</div>--%>
    <%--				<div class="accordion" id="calendar">--%>
    <%--					<div class="card">--%>
    <%--						<div class="card-header" id="headingCalendar">--%>
    <%--							<h2 class="mb-0">--%>
    <%--								<form action="/" method="get">--%>
    <%--									<button class="btn btn-link btn-block text-left" type="submit">--%>
    <%--										캘린더--%>
    <%--									</button>--%>
    <%--								</form>--%>
    <%--							</h2>--%>
    <%--						</div>--%>
    <%--					</div>--%>
    <%--				</div>--%>
    <%--			</li>--%>
    <%--		</ul>--%>
    <%--	</div>--%>
    <%--	<br>--%>
    <%--	<br>--%>
    <div style="border: 1px solid blue; float: left; max-width: 16%; width: 16%; margin: 15px; height: 80%; max-height: 80%; vertical-align: middle">
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
    <div style="border: 1px solid red; float: left; width: 70%; height: 80%; max-height: 80%; margin: 20px">
        <div>

        </div>
        <div>

        </div>
        <div>

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
<%--<%@ include file="layout/footer.jsp" %>--%>
</html>
