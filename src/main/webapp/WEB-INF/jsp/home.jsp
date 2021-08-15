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
			<div class="class1">
				<%--			<a href="/find_id">회원정보 찾기 수정</a>--%>
				<a href="/user/deleteAccount">(임시)회원탈퇴</a>
				<a href="/admin/service/loc_recommend/list">어드민 장소 추천 페이지</a>
				<br><br>
				<%--			<a href="/bootstrap">부트스트랩 테스트</a>--%>
				<%--			<a href="/service/loc_recommend">장소_추천</a>--%>
<%--				<a href="/service/loc_recommend">장소 추천</a>--%>
<%--				<a href="/service/loc_detail/ex">장소 보기 예시</a>--%>
<%--				<a href="/service/cor_index">코스 인덱스</a>--%>
				<a href="/fullpage">fullpage테스트</a>
				<%--			<a href="/sample/index">테스트</a>--%>
				<%--			<a href="/service/loc_index">장소_인덱스</a>--%>
				<a href="/ServiceCenter">공지사항</a>
			</div>
		</div>
	</div>
	<div class="fullsection full2" pageNum="2">
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
				</div>
				<div class="jibcok">
					<h2>집콕 활동</h2>
					<div class="">
						<div class="cuchon-img">
							<img name="" class="cos_img" src="/image/icon/home/dalgona.jpeg">
							<span class="cosname">달고나 커피 만들기</span>
						</div>
						<div class="cuchon-img">
							<img name="" class="cos_img" src="/image/icon/home/bosuksibjasu.jpg">
							<span class="cosname">보석 십자수</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="fullsection full3" pageNum="3">
	</div>
</div>

</body>
<%--<%@ include file="layout/footer.jsp" %>--%>
<!--  부트스트랩 js 사용 -->

<script defer src="/js/main-fullpage.js"></script>
<script defer src="/js/home.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>

<%--<body>--%>

<%--		<div class="container">--%>
<%--			<div class="flex-container">--%>
<%--				<div class="main-content">--%>
<%--					<div class="slideshow-container">--%>
<%--						<div class="mySlides fade">--%>
<%--							<div class="Slides-content">--%>
<%--								<div id="img-content">--%>
<%--									<img class="banner-img" src="/image/icon/home/datapg1.png">--%>
<%--								</div>--%>
<%--							</div>--%>
<%--						</div>--%>

<%--						<div class="mySlides fade">--%>
<%--							<div class="Slides-content">--%>
<%--								<div id="img-content">--%>
<%--									<img class="banner-img" src="/image/icon/home/datapg2.png">--%>
<%--								</div>--%>
<%--							</div>--%>
<%--						</div>--%>

<%--						<div class="mySlides fade">--%>
<%--							<div class="Slides-content">--%>
<%--								<div id="img-content">--%>
<%--									<img class="banner-img" src="/image/icon/home/datepg3.jpg">--%>
<%--								</div>--%>
<%--							</div>--%>
<%--						</div>--%>
<%--						<div>--%>
<%--							<a class="prev" onclick="plusSlides(-1)"><img src="/image/icon/home/slide_btn_prev.png"></a>--%>
<%--							<a class="next" onclick="plusSlides(1)"><img src="/image/icon/home/slide_btn_next.png"></a>--%>
<%--						</div>--%>


<%--						<div class="slider-dot">--%>
<%--							<span class="dot" onclick="currentSlide(1)"></span>--%>
<%--							<span class="dot" onclick="currentSlide(2)"></span>--%>
<%--							<span class="dot" onclick="currentSlide(3)"></span>--%>
<%--						</div>--%>
<%--					</div>--%>


<%--				</div>--%>
<%--				<div class="class1">--%>
<%--					&lt;%&ndash;			로그인 되어 있을 경우 Logout  &ndash;%&gt;--%>
<%--					<sec:authorize access="isAuthenticated()">--%>
<%--						<c:set var="user_social"><sec:authentication property="principal.user_social"/></c:set>--%>
<%--						<c:choose>--%>
<%--							&lt;%&ndash;					소셜 로그인으로 로그인 되어 있을 경우&ndash;%&gt;--%>
<%--							<c:when test="${user_social eq true}">--%>
<%--								<c:set var="social_info"><sec:authentication property="principal.social_info"/></c:set>--%>

<%--								<c:choose>--%>
<%--									&lt;%&ndash;						소셜 로그인을 카카오로 했을 경우&ndash;%&gt;--%>
<%--									<c:when test="${social_info eq 'kakao'}">--%>
<%--										<form action="/logout_kakao" method="post">--%>
<%--											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
<%--											<button type="submit">LOGOUT</button>--%>
<%--										</form>--%>
<%--									</c:when>--%>

<%--									&lt;%&ndash;						소셜 로그인을 네이버로 했을 경우&ndash;%&gt;--%>
<%--									<c:when test="${social_info eq 'naver'}">--%>
<%--										<form action="/logout" method="post">--%>
<%--											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
<%--											<button type="submit">LOGOUT</button>--%>
<%--										</form>--%>
<%--									</c:when>--%>
<%--								</c:choose>--%>
<%--							</c:when>--%>

<%--							&lt;%&ndash;					일반 로그인으로 로그인 되어 있을 경우&ndash;%&gt;--%>
<%--							<c:when test="${user_social ne true}">--%>
<%--								<form action="/logout" method="post">--%>
<%--									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
<%--									<button type="submit">LOGOUT</button>--%>
<%--								</form>--%>
<%--							</c:when>--%>
<%--						</c:choose>--%>
<%--					</sec:authorize>--%>

<%--					&lt;%&ndash;		로그인 되어 있지 않을 경우 Login	&ndash;%&gt;--%>
<%--					<sec:authorize access="!isAuthenticated()">--%>
<%--						<form action="/login" method="get">--%>
<%--								&lt;%&ndash;					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />&ndash;%&gt;--%>
<%--								&lt;%&ndash;					<button type="submit">LOGIN</button>&ndash;%&gt;--%>
<%--						</form>--%>
<%--					</sec:authorize>--%>
<%--				</div>--%>

<%--			</div>--%>
<%--		</div>--%>
<%--</body>--%>
</html>
