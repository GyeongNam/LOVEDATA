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
    <title>Question add</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
<div class="container col-lg-6 ">
    <form action="/ServiceCenter/Questions_Post_add/add" method="post" enctype="multipart/form-data">
        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
    <div class="top-100" style="padding:50px;">
    </div>
    <h2> 문의하기 </h2>
    <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
        <div class="form-group" >
            <label>제목</label>
            <select name="secret">
                <option value="0">공개</option>
                <option value="1">비공개</option>
            </select>
            <input class="form-control" type="text" id="title" name="title"  >
        </div>
        <div class="form-group">
            <label>작성자</label>
            <input class="form-control" type="text" id="name" name="name" value="<sec:authentication property="principal.user_nic"/>" readonly>
        </div>
        <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="10" maxlength="150" name="info" id="info" required></textarea>
        </div>
        <input class="visually-hidden" id="imgInput" name="files" type="file" multiple accept="image/*" onchange="readImage()">
        <div id="canvas" class="row flex-nowrap mx-0 mt-3" style="overflow-x: scroll">
            <c:forEach var="i" begin="1" end="4">
                <c:choose>
                    <c:when test="${i eq 1}">
                        <div class="card col-3 p-0 m-2">
                            <img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
                            <div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
                                <img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${i}"
                                     src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
                            </div>
                            <div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
                                <img class="btn btn-lg align-middle p-0" id="imgDel_${i}" onclick="deleteImage(this)"
                                     src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
                            </div>
                            <div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden" style="align-items: center">
                                <img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})" src="/image/icon/480px-Solid_white.png"
                                     style="opacity : 0.0; z-index: 1;">
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="card visually-hidden">
                            <img src="/image/icon/480px-Solid_white.png" alt="" id="img_${i}" class="visible bd-place card-img" style="height: 244px; width: 100%; outline: none">
                            <div class="d-flex justify-content-center card-img-overlay" style="align-items: center">
                                <img class="btn btn-lg align-middle" onclick="onClickAddImage()" id="imgAdd_${i}"
                                     src="/image/icon/black-24dp/2x/outline_add_black_24dp.png" style="height: 30%; z-index: 2">
                            </div>
                            <div class="d-flex justify-content-end card-img-overlay p-0 visually-hidden" style="align-items: flex-start">
                                <img class="btn btn-lg align-middle p-0" id="imgDel_${i}" onclick="deleteImage(this)"
                                     src="/image/icon/black-24dp/2x/outline_clear_black_24dp.png" style="z-index: 2">
                            </div>
                            <div class="d-flex justify-content-center card-img-overlay p-0 visually-hidden" style="align-items: center">
                                <img class="w-100 h-100" id="imgSel_${i}" onclick="onSelectImage(${i})" src="/image/icon/480px-Solid_white.png"
                                     style="opacity : 0.0; z-index: 1;">
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <div class="btn_wrap text-center">
            <button type="submit" id="post_add" name="post_add">추가하기</button>
        </div>
    </div>
    </form>
</div>
</body>

<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script defer src="/js/qu_post_add.js"></script>
</html>