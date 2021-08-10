<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/home.css" rel="stylesheet">
    <link href="/css/fullpage.css" rel="stylesheet">
    <title>Home</title>
</head>

<%@ include file="layout/header.jsp" %>
<body>
<div id="fullpage">
    <div class='quick'>
        <ul style="list-style: none"></ul>
    </div>
    <div class="fullsection full1" pageNum="1">
        <div class="container">
            <div class="flex-container">
                <div class="main-content">
                    <div class="slideshow-container">
                        <div class="mySlides fade">
                            <div class="Slides-content">
                                <div id="img-content">
                                    <img class="banner-img" src="/image/icon/home/datapg1.png">
                                    <div class="banner-content">
                                        <div>
                                            <p>LOVEDAT를 <br> 만나보세요!</p>
                                        </div>
                                        <hr class="hr-line">
                                        <div>
                                            <ul class="ul-style">
                                                <li>데이트코스를 직접 설정하고 공유해보세요!</li>
                                                <li>다른 사람이 설정한 데이트 코스를 체험하고 리뷰를 남겨주세요!</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mySlides fade">
                            <div class="Slides-content">
                                <div id="img-content">
                                    <img class="banner-img" src="/image/icon/home/datapg2.png">
                                    <div class="banner-content">
                                        <div>
                                            <p>LOVEDAT를 <br> 만나보세요!</p>
                                        </div>
                                        <hr class="hr-line">
                                        <div>
                                            <ul class="ul-style">
                                                <li>데이트코스를 직접 설정하고 공유해보세요!</li>
                                                <li>다른 사람이 설정한 데이트 코스를 체험하고 리뷰를 남겨주세요!</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mySlides fade">
                            <div class="Slides-content">
                                <div id="img-content">
                                    <img class="banner-img" src="/image/icon/home/datepg3.jpg">
                                    <div class="banner-content">
                                        <div>
                                            <p>LOVEDAT를 <br> 만나보세요!</p>
                                        </div>
                                        <hr class="hr-line">
                                        <div>
                                            <ul class="ul-style">
                                                <li>데이트코스를 직접 설정하고 공유해보세요!</li>
                                                <li>다른 사람이 설정한 데이트 코스를 체험하고 리뷰를 남겨주세요!</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a class="prev" onclick="plusSlides(-1)"><img src="/image/icon/home/slide_btn_prev.png"></a>
                            <a class="next" onclick="plusSlides(1)"><img src="/image/icon/home/slide_btn_next.png"></a>
                        </div>


                        <div class="slider-dot">
                            <span class="dot" onclick="currentSlide(1)"></span>
                            <span class="dot" onclick="currentSlide(2)"></span>
                            <span class="dot" onclick="currentSlide(3)"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="fullsection full2" pageNum="2">
        <div class="container">
            <div class="flex-page2">
                <div class="cuchon">
                    <h2>추천 코스</h2>
                    <div class="cuchon-img">
                        <img name="" class="cos_img" src="/image/icon/home/looftoppicnic.jpeg">
                    </div>
                    <span class="cosname">루프탑 피크닉</span>
                    <div class="cuchon-img">
                        <img name="" class="cos_img" src="/image/icon/home/chungguecheon.jpeg">
                    </div>
                    <span class="cosname">청계천 걷기</span>
                </div>
                <div class="jibcok">
                    <h2>집콕 활동</h2>
                    <div class="">
                        <div class="cuchon-img">
                            <img name="" class="cos_img" src="/image/icon/home/dalgona.jpeg">
                        </div>
                        <span class="cosname">달고나 커피 만들기</span>
                        <div class="cuchon-img">
                            <img name="" class="cos_img" src="/image/icon/home/bosuksibjasu.jpg">
                        </div>
                        <span class="cosname">보석 십자수</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="fullsection full3" pageNum="3">
    </div>
    <div class="fullsection full4" pageNum="4">
    </div>
    <div class="fullsection full5" pageNum="5">
    </div>
</div>

</body>
<%--<%@ include file="layout/footer.jsp" %>--%>
<!--  부트스트랩 js 사용 -->

<script defer src="/js/main-fullpage.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>

</html>
