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
    <link rel="stylesheet" href="/css/ServiceCenter.css" >
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
    </style>
    <title>Admin QnA</title>
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
                            <div id="service_collapse" class="collapse show">
                                <p>
                                    <a href="/admin/notice/1" class="highlight-not-selected-text-menu">- 공지사항</a>
                                </p>
                                <p>
                                    <a href="/admin/qna/1" class="highlight-selected-text-menu">- 문의사항</a>
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
    <div class="container col-lg-6 ">
        <h2> 질문:${qu.qu_no}</h2>
        <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
            <div class="form-group" >
                <label>제목</label>
                <sec:authorize access="isAuthenticated()">
                    <c:set var="name"><sec:authentication property="principal.user_no"/></c:set>
                    <c:if test="${qu.qu_user_no eq name}">
                        <c:if test="${qu.qu_answer eq false}">
                            <button onclick="onclick=location.href='/ServiceCenter/Questions_Update/'+${qu.qu_no}">수정하기</button>
                            <button onclick="onclick=location.href='/ServiceCenter/Questions_Delete/'+${qu.qu_no}">삭제하기</button>
                        </c:if>
                    </c:if>
                </sec:authorize>
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
                <label>문의 유형</label>
                <div class="form-control">
                    <div class="col-sm-12">
                        ${qu.qu_type}
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label>내용</label>
                <div class="form-control">
                    <div class="col-sm-12">
                        ${qu.qu_text}
                    </div>
                    <c:forEach var="img" items="${qu_img}">
                        <img class="bd-placeholder-img card-img" width="100%"
                             height="400"
                             src="/image/qna/${img.qu_img_url}"
                             id="imgDisplay"
                             name="imgDisplay"
                             preserveAspectRatio="xMidYMid slice" focusable="false">
                        <div style="padding: 10px"></div>
                    </c:forEach>
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
        <sec:authorize access="isAuthenticated()">
            <sec:authorize access="hasAnyRole('ADMIN')">
                <c:choose>
                    <c:when test="${qu.qu_answer eq false}">
                        <form action="/admin/Questions_answer/${qu.qu_no}" method="post">
                            <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
                            <h2> 답변 </h2>
                            <div class="card" style="padding:20px; border-radius: 15px; margin: 20px auto;">
                                <div class="form-group">
                                    <label>작성자</label>
                                    <input class="form-control" type="text" id="title" name="title" value="<sec:authentication property="principal.user_nic"/>" readonly  >
                                </div>
                                <div class="form-group">
                                    <label>답변내용</label>
                                    <textarea class="form-control" rows="10" maxlength="150" name="info" id="info" required></textarea>
                                </div>
                                <div class="btn_wrap text-center">
                                    <button type="submit" id="post_add" name="post_add">답변하기</button>
                                </div>
                            </div>
                        </form>
                    </c:when>
                </c:choose>
            </sec:authorize>
        </sec:authorize>
    </div>
</div>
</body>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/ServiceCenter.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
<script defer src="/js/qu_detail.js"></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    body {
        font-family: 'Jua', sans-serif;
    }
</style>

</html>
