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
    <link rel="stylesheet" href=" https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
    <link rel="stylesheet" href=" https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap5.min.css"/>
    <link rel="stylesheet" href=" https://cdn.datatables.net/rowgroup/1.1.4/css/rowGroup.bootstrap5.min.css"/>
    <link rel="stylesheet" href="/css/service/loc.css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
        th, td {
            vertical-align : middle;
        }
    </style>
    <title>Admin User</title>
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
                            <div id="service_collapse" class="panel-collapse collapse">
                                <p>
                                    <a href="/admin/notice/1" class="highlight-not-selected-text-menu">- 공지사항</a>
                                </p>
                                <p>
                                    <a href="/admin/qna/1" class="highlight-not-selected-text-menu">- 문의사항</a>
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
    <div class="container-xl p-5 ms-3">
        <div class="tab-content mx-2" >
            <h2>유저 정보</h2>
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
            <h2>장소</h2>
            <table id="table" class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">장소 번호</th>
                <th scope="col">장소 이름</th>
                <th scope="col">지역</th>
                <th scope="col">등록일</th>
                <th scope="col">연락처</th>
                <th scope="col">조회수</th>
                </thead>
                <tbody>
                <c:forEach var="locs" items="${loc}">
                    <c:choose>
                        <c:when test="${locs._deleted eq false}">
                            <tr style="cursor:hand; background: #e2eeff;" onclick="location.href='/service/loc_detail?locNo=${locs.loc_no}'">
                                <td>${locs.loc_no}</td>
                                <td>${locs.loc_name}</td>
                                <td>${locs.siDo} ${locs.siGunGu}</td>
                                <td>${locs.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${locs.tel}</td>
                                <td>${locs.viewCount}</td>
                            </tr>
                        </c:when>
                        <c:when test="${locs._deleted eq true}">
                            <tr style="cursor:hand; background: #ffdef2;" onclick="location.href='/service/loc_detail?locNo=${locs.loc_no}'">
                                <td>${locs.loc_no}</td>
                                <td>${locs.loc_name}</td>
                                <td>${locs.siDo} ${locs.siGunGu}</td>
                                <td>${locs.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${locs.tel}</td>
                                <td>${locs.viewCount}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content">
            <h2>코스</h2>
            <table class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">코스 번호</th>
                <th scope="col">코스 이름</th>
                <th scope="col">등록일</th>
                <th scope="col">조회수</th>
                <th scope="col">좋아요</th>
                </thead>
                <tbody>
                <c:forEach var="cors" items="${cor}">
                    <c:choose>
                        <c:when test="${cors._deleted eq false}">
                            <tr style="cursor:hand; background: #e2eeff;" onclick="location.href='/service/cor_detail?corNo=${cors.cor_no}'">
                                <td>${cors.cor_no}</td>
                                <td>${cors.cor_name}</td>
                                <td>${cors.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${cors.viewCount}</td>
                                <td>${cors.likeCount}</td>
                            </tr>
                        </c:when>
                        <c:when test="${cors._deleted eq true}">
                            <tr style="cursor:hand; background: #ffdef2;" onclick="location.href='/service/cor_detail?corNo=${cors.cor_no}'">
                                <td>${cors.cor_no}</td>
                                <td>${cors.cor_name}</td>
                                <td>${cors.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${cors.viewCount}</td>
                                <td>${cors.likeCount}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content">
            <h2>리뷰</h2>
            <table class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">리뷰 번호</th>
                <th scope="col">코스 번호</th>
                <th scope="col">코스 이름</th>
                <th scope="col">등록일</th>
                <th scope="col">좋아요</th>
                <th scope="col">싫어요</th>
                <th scope="col">별점</th>
                </thead>
                <tbody>
                <c:forEach var="revs" varStatus="index" items="${rev}">
                    <c:choose>
                        <c:when test="${revs._deleted eq false}">
                            <tr style="cursor:hand; background: #e2eeff;" onclick="location.href='/service/cor_detail?corNo=${revs.corNo}&page=${RevPageNum.get(index.count-1)}'">
                                <td>${revs.revNo}</td>
                                <td>${revs.corNo}</td>
                                <td>${rev_cor_name.get(index.count-1).cor_name}</td>
                                <td>${revs.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${revs.rev_like}</td>
                                <td>${revs.rev_dislike}</td>
                                <td>${revs.sc_total}</td>
                            </tr>
                        </c:when>
                        <c:when test="${revs._deleted eq true}">
                            <tr style="cursor:hand; background: #ffdef2;" onclick="location.href='/service/cor_detail?corNo=${revs.corNo}&page=${RevPageNum.get(index.count-1)}'">
                                <td>${revs.revNo}</td>
                                <td>${revs.corNo}</td>
                                <td>${rev_cor_name.get(index.count-1).cor_name}</td>
                                <td>${revs.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${revs.rev_like}</td>
                                <td>${revs.rev_dislike}</td>
                                <td>${revs.sc_total}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content">
            <h2>댓글</h2>
            <table class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">댓글 번호</th>
                <th scope="col">장소 번호</th>
                <th scope="col">장소 이름</th>
                <th scope="col">등록일</th>
                <th scope="col">좋아요</th>
                </thead>
                <tbody>
                <c:forEach var="coms" varStatus="index" items="${com}">
                    <c:choose>
                        <c:when test="${coms._deleted eq false}">
                            <tr style="cursor:hand; background: #e2eeff;"
                                onclick="location.href='/service/loc_detail?locNo=${coms.getLocation().loc_no}&page=${ComPageNum.get(index.count-1)}'">
                                <td>${coms.cmtNo}</td>
                                <td>${coms.getLocation().loc_no}</td>
                                <td>${coms.getLocation().loc_name}</td>
                                <td>${coms.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${coms.likeCount}</td>
                            </tr>
                        </c:when>
                        <c:when test="${coms._deleted eq true}">
                            ${RevPageNum.size()}
                            <tr style="cursor:hand; background: #ffdef2;" onclick="location.href='/service/loc_detail?locNo=${coms.getLocation().loc_no}&page=${ComPageNum.get(index.count-1)}'">
                                <td>${coms.cmtNo}</td>
                                <td>${coms.getLocation().loc_no}</td>
                                <td>${coms.getLocation().loc_name}</td>
                                <td>${coms.regDate.format(simpleDateTimeFormatter.dateTimeFormatter)}</td>
                                <td>${coms.likeCount}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content">
            <h2>포인트 : ${point}</h2>
            <table class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">포인트 번호</th>
                <th scope="col">포인트</th>
                <th scope="col">획득처</th>
                <th scope="col">사용처</th>
                <th scope="col">획득처 및 사용처 번호</th>
                </thead>
                <tbody>
                <c:forEach var="pointL" varStatus="index" items="${pointlist}">
                    <c:choose>
                        <c:when test="${pointL.get_plus_mi eq true}">
                            <tr>
                                <td>${pointL.point_no}</td>
                                <td>${pointL.point}</td>
                                <td>${pointL.point_get_out}</td>
                                <td></td>
                                <td>${pointL.get_no_use_no}</td>
                            </tr>
                        </c:when>
                        <c:when test="${pointL.get_plus_mi eq false}">
                            <tr style="cursor:hand; background: #ffdef2;">
                                <td>${pointL.point_no}</td>
                                <td>${pointL.point}</td>
                                <td></td>
                                <td>${pointL.point_get_out}</td>
                                <td>${pointL.get_no_use_no}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content">
            <h2>이벤트 참여 내역</h2>
            <table class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">이벤트 번호</th>
                <th scope="col">이벤트 이름</th>
                <th scope="col">시작일</th>
                <th scope="col">종료일</th>
                <th scope="col">추첨일</th>
                <th scope="col">나의 참여횟수</th>
                </thead>
                <tbody>
                <c:forEach var="even" varStatus="index" items="${eve}">
                    <tr onclick="location.href='/ServiceCenter/Event_Post/${even.ev_no}';">
                        <td>${even.ev_no}</td>
                        <td>${even.ev_title}</td>
                        <td>${even.ev_start}</td>
                        <td>${even.ev_stop}</td>
                        <td>${even.ev_end}</td>
                        <td>${eveattend.get(index.count-1)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content">
            <h2>정지기록</h2>
            <table class="table-bordered table text-center tables" >
                <thead>
                <th scope="col">정지 번호</th>
                <th scope="col">사유 번호</th>
                <th scope="col">정지 사유</th>
                <th scope="col">정지 시작</th>
                <th scope="col">정지 일</th>
                <th scope="col">정지 종료</th>
                </thead>
                <tbody>
                <c:forEach var="uss" items="${us}">
                    <c:choose>
                        <c:when test="${uss.progress eq 0}">
                            <tr>
                                <td>${uss.user_suspension_no}</td>
                                <td>${uss.rc_no}</td>
                                <td>${uss.re_content}</td>
                                <td>${uss.start_day}</td>
                                <td>${uss.stop_day}</td>
                                <td>${uss.end_day}</td>
                            </tr>
                        </c:when>
                        <c:when test="${uss.progress eq 1}">
                            <tr style="cursor:hand; background: #ffdef2;">
                                <td>${uss.user_suspension_no}</td>
                                <td>${uss.rc_no}</td>
                                <td>${uss.re_content}</td>
                                <td>${uss.start_day}</td>
                                <td>${uss.stop_day}</td>
                                <td>${uss.end_day}</td>
                            </tr>
                        </c:when>
                        <c:when test="${uss.progress eq 2}">
                            <tr style="cursor:hand; background: #fff8de;">
                                <td>${uss.user_suspension_no}</td>
                                <td>${uss.rc_no}</td>
                                <td>${uss.re_content}</td>
                                <td>${uss.start_day}</td>
                                <td>${uss.stop_day}</td>
                                <td>${uss.end_day}</td>
                            </tr>
                        </c:when>
                    </c:choose>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-content" >
            <div class="d-flex container align-items-center">
                <h2 class="d-flex align-items-center">유저 관리</h2>
                <c:choose>
                    <c:when test="${user.user_Activation eq false}">
                        <button class="d-flex mx-2 align-items-center btn-primary" onclick="location.href='/admin/user_release/${user.user_no}'">정지 해제</button>
                    </c:when>
                </c:choose>
            </div>
            <form class="d-flex container" action="/admin/user_suspension" method="post">
                <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
                <input type="hidden" name="user_no" value="${user.user_no}">
                <span class="d-flex col-1 justify-content-center align-items-center border border-secondary">정지사유</span>
                <textarea class="d-flex container justify-content-center"  type="text" name="re_content" placeholder="정지사유를 입력해 주세요"></textarea>
                <span class="d-flex col-1 justify-content-center align-items-center border border-secondary">정지 일</span>
                <input class="d-flex col-1 container justify-content-center"  type="number" name="stop_day" placeholder="정지 일">
                <button class="btn-primary d-flex col-1 container align-items-center justify-content-center"  type="submit">정지</button>
            </form>
        </div>
    </div>
</div>
</body>
<!--  부트스트랩 js 사용 -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>

<script defer src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<script defer src="https://cdn.datatables.net/1.11.3/js/dataTables.jqueryui.min.js"></script>
<script defer src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
<script defer src="https://cdn.datatables.net/rowgroup/1.1.4/js/dataTables.rowGroup.min.js"></script>
<script defer src="/js/datatable.js"></script>

</html>