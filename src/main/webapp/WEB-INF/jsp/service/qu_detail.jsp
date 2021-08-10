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
    <link rel="stylesheet" href="/css/qu_detail.css">


    <title>문의사항 : ${qu.qu_no}</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>

<div class="container col-lg-6 ">
    <div class="top-100" style="padding:50px;">
    </div>
    <h2> 질문:${qu.qu_no}</h2>
    <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
        <div class="form-group" >
            <label>제목</label>
            <div class="form-control">
                <div class="col-sm-12">
                    ${qu.qu_title}
                </div>
            </div>
        </div>
        <div class="form-group" >
            <label>작성 시간</label>
            <div class="form-control">
                <div class="col-sm-12">
                    ${qu.qu_date}
                </div>
            </div>
        </div>
        <div class="form-group">
            <label>작성자</label>
            <div class="form-control">
                <div class="col-sm-12">
                    ${qu.qu_user}
                </div>
            </div>
        </div>
        <div class="form-group">
            <label>내용</label>
            <div class="form-control">
                <div class="col-sm-12">
                    ${qu.qu_text}
                </div>
            </div>
        </div>
    </div>
    <c:choose>
        <c:when test="${qu.qu_answer eq true}">
            <h2> 답변 </h2>
            <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
                <div class="form-group">
                    <label>작성자</label>
                    <div class="form-control">
                        <div class="col-sm-12">
                                ${qu.qu_answer_manager}
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>답변내용</label>
                    <div class="form-control">
                        <div class="col-sm-12">
                                ${qu.qu_answer_text}
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>

</div>
</body>
</html>

<script defer src="/js/qu_detail.js"></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    body {
        font-family: 'Jua', sans-serif;
    }
</style>