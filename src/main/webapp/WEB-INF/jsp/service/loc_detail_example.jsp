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
  <meta name="_csrf" content="${_csrf.token}">
  <meta name="_csrf_header" content="${_csrf.headerName}">
  <%--	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">--%>
  <%--	<link rel="stylesheet" type="text/css" href="/css/Bootstarp_test/bootstrap.min.css">--%>
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
              <button class="btn btn-link btn-block" type="button" style="text-decoration: none; color: #FF6699; font-weight: bold">
                장소
              </button>
            </h2>
          </div>
          <div id="loc_collapse" class="show" aria-labelledby="headingLoc" data-parent="#loc">
            <div class="card-body center-pill">
              <p><a href="/service/loc_recommend" class="highlight-selected-text-menu">- 추천 장소</a></p>
              <p><a href="/service/loc_registration" class="highlight-not-selected-text-menu">- 장소
                등록</a></p>
              <p><a href="#" class="highlight-not-selected-text-menu">- 장소 편집</a></p>
            </div>
          </div>
        </div>
      </div>
      <%--            <div class="accordion text-center" id="course">--%>
      <%--                <div class="card">--%>
      <%--                    <div class="card-header" id="headingCourse">--%>
      <%--                        <h2 class="mb-0">--%>
      <%--                            <form action="/" method="get" class="form-label">--%>
      <%--                                <button type="submit" class="btn btn-link btn-block"--%>
      <%--                                        style="text-decoration: none; color: #9448C3">코스--%>
      <%--                                </button>--%>
      <%--                            </form>--%>
      <%--                        </h2>--%>
      <%--                    </div>--%>
      <%--                </div>--%>
      <%--            </div>--%>
      <%--            <div class="accordion text-center" id="calendar">--%>
      <%--                <div class="card">--%>
      <%--                    <div class="card-header" id="headingCalendar">--%>
      <%--                        <h2 class="mb-0">--%>
      <%--                            <form action="/" method="get" class="form-label">--%>
      <%--                                <button type="submit" class="btn btn-link btn-block"--%>
      <%--                                        style="text-decoration: none; color: #9448C3">캘린더--%>
      <%--                                </button>--%>
      <%--                            </form>--%>
      <%--                        </h2>--%>
      <%--                    </div>--%>
      <%--                </div>--%>
      <%--                <hr>--%>
      <%--            </div>--%>
    </ul>
  </div>
  <div class="container m-5" id="display_center" style="margin-right: 30px; margin-top: 30px">
    <div class="row justify-content-md-center">
      <div class="col-md-7">
        <div class="card mb-4 shadow-sm">
          <form action="/" method="get" class="m-0">
            <svg class="bd-placeholder-img card-img-top" width="100%" height="400"
                 xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
                 preserveAspectRatio="xMidYMid slice" focusable="false">
              <title>Placeholder</title>
              <rect width="100%" height="100%" fill="#55595c"></rect>
              <text x="42%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text>
            </svg>
          </form>
        </div>
      </div>
      <div class="col-md-5 justify-content-md-center">
        <div class="row d-flex">
          <div class="row d-flex justify-content-between p-1">
            <span class="h1 col-5">(Template) 서울 숲</span>
            <button class="btn btn-outline-danger col-2" style="max-height: 56px">공유</button>
          </div>
          <div class="row d-flex">
            <h3>지역 : 서울특별시 성동구</h3>
          </div>
          <div class="row d-flex">
            <h3>해시태그 : 야외, 공원, 나들이, 산책, 숲</h3>
          </div>
          <div class="row d-flex">
            <h3 class="text-truncate">설명 : 주차, 무선 인터넷, 반려동물 동반, 유아시설(놀이방), 남/녀 화장실 구분, 장애인 편의시설</h3>
          </div>
        </div>
        <div class="row d-flex">
        </div>
        <img src="/image/icon/like/love_black.png" class="loc_icon" alt="찜하기"
             onclick="onClickLike(this), 'loc">
        <span class="d-none">0</span>
      </div>
    </div>
    <div class="row justify-content-md-start">
      <ul class="nav nav-pills nav-fill col-5" id="pills-tab" role="tablist"
          style="height:50px; padding-top: 5px; padding-bottom: 5px">
        <li class="nav-item" role="presentation">
          <button class="mw-100 mh-100 nav-link active" id="location-comment-tab" data-bs-toggle="pill"
                  data-bs-target="#location-comment" type="button" role="tab" aria-controls="location-comment"
                  aria-selected="true">댓글
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button class="nav-link" id="location-info-tab" data-bs-toggle="pill"
                  data-bs-target="#location-info" type="button" role="tab" aria-controls="location-info"
                  aria-selected="false">설명
          </button>
        </li>
      </ul>
      <div class="tab-content" id="pills-tabContent">
        <%--      댓글--%>
        <div class="tab-pane fade show active" id="location-comment" role="tabpanel"
             aria-labelledby="location-comment-tab">
          <div class="container mt-0">
            <div class="d-flex justify-content-start row">
              <div class="col-md-10">
                <div class="d-flex flex-column">
                  <div class="bg-white p-2">
                    <div class="d-flex flex-row align-items-center"><img
                            src="/image/icon/user/user.png"
                            class="loc_comment-profile-image-wh">
                      <div class="flex-column">
                                        <span class="d-block font-weight-bold name">
                                            유저 닉네임
                                            <button class="btn btn-primary">수정</button>
                                            <button class="btn btn-primary">삭제</button>
                                        </span>
                        <span class="date text-black-50 ml-5">(2021/05/02 00:39)</span>
                      </div>
                    </div>
                    <div class="mt-2">
                      <p class="comment-text">Lorem ipsum dolor sit amet, consectetur adipiscing
                        elit, sed
                        do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
                        ad minim
                        veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
                        commodo
                        consequat.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="container d-flex" id="pagination">
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
                  <%--                          Todo  댓글 어떻게 화면 전환 없이 가져올 수 있을지 생각해보기        --%>
                  <li class="page-item disabled"><a class="page-link" href="#">1</a></li>
                  <li class="page-item"><a class="page-link" href="#">2</a></li>
                  <li class="page-item"><a class="page-link" href="#">3</a></li>
                  <li class="page-item"><a class="page-link" href="#">4</a></li>
                  <li class="page-item"><a class="page-link" href="#">5</a></li>
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
          <%--                    Todo 추후에 comment 섹션을 안에다 넣으면, 로그인 한 사람만 댓글 작성 가능--%>
          <%--                    <sec:authorize access="isAuthenticated()">--%>
          <%--                        --%>
          <%--                    </sec:authorize>--%>
          <div class="d-flex justify-content-start" id="comment">
            <div class="bg-light p-2 col-10">
              <div class="d-flex flex-row align-items-start">
                <img class="rounded-circle m-3" src="https://i.imgur.com/RpzrMR2.jpg" width="60">
                <textarea class="form-control ml-1 shadow-none textarea"></textarea>
              </div>
              <div class="mt-2 text-end">
                <button class="btn btn-primary btn shadow-none" type="button">Post comment</button>
              </div>
            </div>
          </div>
        </div>
        <%--    설명--%>
        <div class="tab-pane fade" id="location-info" role="tabpanel" aria-labelledby="location-info-tab">
          <div class="container">
            <div class="d-flex mt-3">
              <span class="h3">주차, 무선 인터넷, 반려동물 동반, 유아시설(놀이방), 남/녀 화장실 구분, 장애인 편의시설</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!--  부트스트랩 js 사용 -->
<%--<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js"--%>
<%--        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"--%>
<%--        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"--%>
<%--        crossorigin="anonymous"></script>--%>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script defer src="/js/bootstrap.js"></script>
<script defer src="/js/loc_detail.js"></script>
<script defer src="/js/loc_common.js"></script>
</body>
<%--<%@ include file="../layout/footer.jsp" %>--%>
</html>
