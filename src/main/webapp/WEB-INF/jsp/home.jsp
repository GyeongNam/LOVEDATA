<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
	<link href="/css/fullpage.css" rel="stylesheet">
	<link href="/css/home.css" rel="stylesheet">
	<title>Home</title>
</head>
<script src="https://www.jsdelivr.com/package/npm/fullpage.js?version=2.9.4"></script>
<%@ include file="layout/header.jsp" %>
<body>
<div id="fullpage">
<%--	<div class='quick'>--%>
<%--		<ul style="list-style: none"></ul>--%>
<%--	</div>--%>
	<div id="gotop" style="display: none;">
		<div id="object" class="floating">
			<img src="image/cl.png" alt="gotop">
		</div>
	</div>
	<div class="fullsection full1" pageNum="1">
		<div class="container">
			<div class="flex-container">
				<div class="main-content">
					<div class="mouse" style="display: block;">
						<span></span>
					</div>
					<div class="slideshow-container">
						<div class="mySlides fade">
							<div class="Slides-content">
								<div id="img-content">
									<img class="banner-img" src="/image/icon/home/mainbn1.jpg">
								</div>
							</div>
						</div>

						<div class="mySlides fade">
							<div class="Slides-content">
								<div id="img-content">
									<img class="banner-img" src="/image/icon/home/lovedatamain2.png">
								</div>
							</div>
						</div>

						<div class="mySlides fade">
							<div class="Slides-content">
								<div id="img-content">
									<img class="banner-img" src="/image/icon/home/main4.png">
								</div>
							</div>
						</div>
						<div>
							<a class="prev" id="#nextclick" onclick="plusSlides(-1)"><img src="/image/icon/home/slide_btn_prev.png"></a>
							<a class="next" id="#nextclick" onclick="plusSlides(1)"><img src="/image/icon/home/slide_btn_next.png"></a>
						</div>
						<div class="slider-dot" style="list-style: none">
							<span class="dot" onclick="currentSlide(1)"></span>
							<span class="dot" onclick="currentSlide(2)"></span>
							<span class="dot" onclick="currentSlide(3)"></span>
						</div>
					</div>

				</div>
			</div>
			<div class="class1">
				<%--			<a href="/find_id">회원정보 찾기 수정</a>--%>
				<a href="/user/deleteAccount">(임시)회원탈퇴</a>
				<a href="/admin/service/loc_recommend/list">어드민 장소 추천 페이지</a>
				<br><br>
				<%--	<a href="/bootstrap">부트스트랩 테스트</a>--%>
				<%--	<a href="/service/loc_recommend">장소_추천</a>--%>
				<%--	<a href="/service/loc_recommend">장소 추천</a>--%>
				<%--	<a href="/service/loc_detail/ex">장소 보기 예시</a>--%>
				<%--	<a href="/service/cor_index">코스 인덱스</a>--%>
				<a href="/fullpage">fullpage테스트</a>
				<%--			<a href="/sample/index">테스트</a>--%>
				<%--			<a href="/service/loc_index">장소_인덱스</a>--%>
				<a href="/ServiceCenter/Notice/1">공지사항</a>
					<a href="/ServiceCenter/Questions/1">문의사항</a>
			</div>
		</div>

	</div>
	<div class="full2">
	</div>
	<div class="full3">
		<div class="container2">
			<div class="flex-page2">
				<div class="cuchon">
					<h2>추천 코스</h2>
					<div class="cuchon-img">
						<img name="" class="cos_img" src="/image/icon/home/looftoppicnic.jpeg">
						<span class="cosname">루프탑 피크닉</span>
					</div>
					<div class="cuchon-img">
						<img name="" class="cos_img" src="/image/icon/home/chungguecheon.jpeg">
						<span class="cosname">청계천 걷기</span>
					</div>
					<div class="cuchon-img">
						<img name="" class="cos_img" src="/image/icon/home/chungguecheon.jpeg">
						<span class="cosname">코스 이름</span>
					</div>
					<div class="cuchon-img">
						<img name="" class="cos_img" src="/image/icon/home/chungguecheon.jpeg">
						<span class="cosname">코스 이름</span>
					</div>
				</div>
				<div class="jibcok">
					<h2>인기 장소</h2>
					<div class="">
						<div class="cuchon-img">
							<img name="" class="cos_img" src="/image/icon/home/dalgona.jpeg">
							<span class="cosname">달고나 커피 만들기</span>
						</div>
						<div class="cuchon-img">
							<img name="" class="cos_img" src="/image/icon/home/bosuksibjasu.jpg">
							<span class="cosname">보석 십자수</span>
						</div>
						<div class="cuchon-img">
							<img name="" class="cos_img" src="/image/icon/home/bosuksibjasu.jpg">
							<span class="cosname">장소 이름</span>
						</div>
						<div class="cuchon-img">
							<img name="" class="cos_img" src="/image/icon/home/bosuksibjasu.jpg">
							<span class="cosname">장소 이름</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>

</body>
<script defer src="/js/main-fullpage.js"></script>
<script defer src="/js/home.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
</html>
