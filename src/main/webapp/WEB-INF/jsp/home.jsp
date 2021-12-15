<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
	<link href="/css/fullpage.css" rel="stylesheet">
	<link href="/css/home.css" rel="stylesheet">
	<style>
		@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

		body {
			font-family: 'Jua', sans-serif;
		}
	</style>
	<title>Home</title>
</head>
<script src="https://www.jsdelivr.com/package/npm/fullpage.js?version=2.9.4"></script>
<%@ include file="layout/header.jsp" %>
<body>
<div id="fullpage">
	<div id="gotop" style="display: none;">
		<div id="object" class="floating">
			<img class="topbtbt" src="/image/icon/home/top.png" alt="gotop">
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
		</div>
	</div>
	<div class="full2">
		<div class="main-2">
			<div class="main-2-leftBox">
				<div class="slideshow-container2">
					<!-- Full-width images with number and caption text -->
					<div class="mySlides2 fade2">
						<img class="imgslide2" src="/image/icon/home/main2page1.png" alt="">
					</div>

					<div class="mySlides2 fade2">
						<img class="imgslide2" src="/image/icon/home/lovedatasg2pg.png" alt="">
					</div>

					<div class="mySlides2 fade2">
						<img class="imgslide2" src="/image/icon/home/lovedatasg3pg.png">
					</div>

					<!-- Next and previous buttons -->
					<div>
						<a class="preve" onclick="moveSlides2(-1)">&#10094;</a>
						<a class="nextt" onclick="moveSlides2(1)">&#10095;</a>
					</div>
					<div style="list-style: none">
						<span class="dot2" onclick="currentSlide2(0)"></span>
						<span class="dot2" onclick="currentSlide2(1)"></span>
						<span class="dot2" onclick="currentSlide2(2)"></span>
					</div>
				</div>
			</div>


			<div class=" main-2-rightBox">
				<div class="mx-1 icon-box">
					<a href="/ServiceCenter/Notice/1" >
						<img class="main-2btpic" src="/image/icon/home/notice.jpg" href="/ServiceCenter/Notice/1">
					</a>
					<a href="/ServiceCenter/Policy?affiliate=1" >
						<img class="main-2btpic" src="/image/icon/home/affiliate.png" href="/ServiceCenter/Notice/1">
					</a>
				</div>
				<div class="mx-2 icon-box">
					<a href="/ServiceCenter/Questions/1">
						<img class="main-2btpic" src="/image/icon/home/qa.jpg" href="/ServiceCenter/Questions/1">
					</a>
				</div>
			</div>
		</div>

	</div>
	<div class="full3">
		<div class="container2">
			<div class="flex-page2">
				<div class="cuchon">
					<h2>추천 코스</h2>
					<c:forEach items="${corhotList}" var="hot_cor">
					<div class="cuchon-img">
						<a href="/service/cor_detail?corNo=${hot_cor.cor_no}">
							<img name="" class="cos_img" src="${hot_cor.thumbnail}">
						</a>
						<span class="cosname">${hot_cor.cor_name}</span>
					</div>
					</c:forEach>
				</div>
				<div class="jibcok">
					<h2>인기 장소</h2>
					<div class="">
						<c:forEach items="${lochotList}" var="hot_loc">
						<div class="cuchon-img">
							<a href="/service/loc_detail?locNo=${hot_loc.loc_no}">
							<img name="" class="cos_img" src="${hot_loc.thumbnail}" href="">
							</a>
							<span class="cosname">${hot_loc.loc_name}</span>
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="full3">
		<div class="container2">
			<div class="flex-page2">
				<div class="cuchon">
					<h2>사업자 장소</h2>
					<c:forEach items="${bizloc}" var="bizloc">
						<div class="cuchon-img">
							<a href="/service/loc_detail?locNo=${bizloc.loc_no}">
								<img name="" class="cos_img" src="${bizloc.thumbnail}" href="">
							</a>
							<span class="cosname">${bizloc.loc_name}</span>
						</div>
					</c:forEach>
				</div>
				<div class="jibcok">
					<h2>이달의 장소</h2>
					<div class="">
<%--						<c:forEach items="${lochotList}" var="hot_loc">--%>
<%--							<div class="cuchon-img">--%>
<%--								<a href="/service/loc_detail?locNo=${hot_loc.loc_no}">--%>
<%--									<img name="" class="cos_img" src="${hot_loc.thumbnail}" href="">--%>
<%--								</a>--%>
<%--								<span class="cosname">${hot_loc.loc_name}</span>--%>
<%--							</div>--%>
<%--						</c:forEach>--%>
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
