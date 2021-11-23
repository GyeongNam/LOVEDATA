<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="_csrf" content="${_csrf.token}">
	<meta name="_csrf_header" content="${_csrf.headerName}">
<%--	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">--%>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<title>개인정보 동의 안내</title>
	<style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
	</style>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
	<div class="container-fluid d-flex" style="padding-top: 100px">
		<div class="container">
			<div class="row d-flex">
				<ul class="nav nav-tabs nav-fill nav-pills" id="myTab" role="tablist">
					<li class="nav-item" role="presentation">
						<button class="nav-link active" id="kakao-tab" data-bs-toggle="tab" data-bs-target="#kakao-tab-content" type="button" role="tab" aria-controls="kakao-tab-content" aria-selected="true">카카오</button>
					</li>
					<li class="nav-item" role="presentation">
						<button class="nav-link" id="naver-tab" data-bs-toggle="tab" data-bs-target="#naver-tab-content" type="button" role="tab" aria-controls="naver-tab-content" aria-selected="false">네이버</button>
					</li>
<%--					<li class="nav-item" role="presentation">--%>
<%--						<button class="nav-link" id="etc-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button" role="tab" aria-controls="contact" aria-selected="false">기타</button>--%>
<%--					</li>--%>
				</ul>
				<div class="tab-content" id="myTabContent">
					<div class="tab-pane fade show active" id="kakao-tab-content" role="tabpanel" aria-labelledby="kakao-tab">
						카카오
						<hr>
						카카오 계정 연동과정에서 필수로 제공되어야 할 정보를 동의하지 않은 경우 문제가 발생합니다. (프로필 정보, 카카오 계정 이메일)
						<br>
						본 서비스를 이용하고 싶으시다면 카카오 고객센터에서 제공하는 <a onclick="onClickOpenNewTab('kakao')" href="">안내사항</a>에 따라 카카오 계정을 연결 해제 후 다시 로그인을 시도하시면 됩니다.
						<div id="carousel-kakao" class="carousel slide mt-4" data-bs-ride="carousel">
							<div class="carousel-indicators" style="filter: invert()">
								<button type="button" data-bs-target="#carousel-kakao" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
								<button type="button" data-bs-target="#carousel-kakao" data-bs-slide-to="1" aria-label="Slide 2"></button>
								<button type="button" data-bs-target="#carousel-kakao" data-bs-slide-to="2" aria-label="Slide 3"></button>
								<button type="button" data-bs-target="#carousel-kakao" data-bs-slide-to="3" aria-label="Slide 4"></button>
								<button type="button" data-bs-target="#carousel-kakao" data-bs-slide-to="4" aria-label="Slide 5"></button>
								<button type="button" data-bs-target="#carousel-kakao" data-bs-slide-to="5" aria-label="Slide 6"></button>
							</div>
							<div class="carousel-inner">
								<div class="carousel-item active">
									<img src="/image/personal_info/Kakao/personal_info_kakao_step1.jpg" class="d-block w-100" alt="personal_info_kakao_step1.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Kakao/personal_info_kakao_step2.jpg" class="d-block w-100" alt="personal_info_kakao_step2.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Kakao/personal_info_kakao_step3.jpg" class="d-block w-100" alt="personal_info_kakao_step3.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Kakao/personal_info_kakao_step4.jpg" class="d-block w-100" alt="personal_info_kakao_step4.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Kakao/personal_info_kakao_step5.jpg" class="d-block w-100" alt="personal_info_kakao_step5.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Kakao/personal_info_kakao_step6.jpg" class="d-block w-100" alt="personal_info_kakao_step6.jpg" style="max-height: 729px">
								</div>
							</div>
							<button class="carousel-control-prev" type="button" data-bs-target="#carousel-kakao" data-bs-slide="prev" style="filter: invert()">
								<span class="carousel-control-prev-icon" aria-hidden="true"></span>
								<span class="visually-hidden">Previous</span>
							</button>
							<button class="carousel-control-next" type="button" data-bs-target="#carousel-kakao" data-bs-slide="next" style="filter: invert()">
								<span class="carousel-control-next-icon" aria-hidden="true"></span>
								<span class="visually-hidden">Next</span>
							</button>
						</div>
					</div>
					<div class="tab-pane fade" id="naver-tab-content" role="tabpanel" aria-labelledby="naver-tab">
						네이버
						<hr>
						네이버 계정 연동과정에서 필수로 제공되어야 할 정보를 동의하지 않은 경우 문제가 발생합니다. (프로필 정보, 이메일)
						<br>
						본 서비스를 이용하고 싶으시다면 네이버 고객센터에서 제공하는 <a onclick="onClickOpenNewTab('naver')" href="">안내사항</a>에 따라 네이버 계정을 연결 해제 후 다시 로그인을 시도하시면 됩니다.
						<div id="carousel-naver" class="carousel slide mt-4" data-bs-ride="carousel">
							<div class="carousel-indicators" style="filter: invert()">
								<button type="button" data-bs-target="#carousel-naver" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
								<button type="button" data-bs-target="#carousel-naver" data-bs-slide-to="1" aria-label="Slide 2"></button>
								<button type="button" data-bs-target="#carousel-naver" data-bs-slide-to="2" aria-label="Slide 3"></button>
								<button type="button" data-bs-target="#carousel-naver" data-bs-slide-to="3" aria-label="Slide 4"></button>
								<button type="button" data-bs-target="#carousel-naver" data-bs-slide-to="4" aria-label="Slide 5"></button>
								<button type="button" data-bs-target="#carousel-naver" data-bs-slide-to="5" aria-label="Slide 6"></button>
							</div>
							<div class="carousel-inner">
								<div class="carousel-item active">
									<img src="/image/personal_info/Naver/personal_info_naver_step1.jpg" class="d-block w-100" alt="personal_info_naver_step1.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Naver/personal_info_naver_step2.jpg" class="d-block w-100" alt="personal_info_naver_step2.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Naver/personal_info_naver_step3.jpg" class="d-block w-100" alt="personal_info_naver_step3.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Naver/personal_info_naver_step4.jpg" class="d-block w-100" alt="personal_info_naver_step4.jpg">
								</div>
								<div class="carousel-item">
									<img src="/image/personal_info/Naver/personal_info_naver_step5.jpg" class="d-block w-100" alt="personal_info_naver_step5.jpg">
								</div>
							</div>
							<button class="carousel-control-prev" type="button" data-bs-target="#carousel-naver" data-bs-slide="prev" style="filter: invert()">
								<span class="carousel-control-prev-icon" aria-hidden="true"></span>
								<span class="visually-hidden">Previous</span>
							</button>
							<button class="carousel-control-next" type="button" data-bs-target="#carousel-naver" data-bs-slide="next" style="filter: invert()">
								<span class="carousel-control-next-icon" aria-hidden="true"></span>
								<span class="visually-hidden">Next</span>
							</button>
						</div>
					</div>
<%--					<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="etc-tab">기타</div>--%>
				</div>
			</div>
		</div>
	</div>
</body>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<%--<script defer src="/js/bootstrap.js"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script defer>
	let naver = 'https://help.naver.com/support/contents/contents.help?serviceNo=17063&categoryNo=19757&lang=ko';
    let kakao = 'https://cs.daum.net/faq/59/7570.html#35050'

	function onClickOpenNewTab(type) {
        switch (type){
            case 'kakao' :
                window.open(kakao, '_blank');
                break;
			case 'naver' :
                window.open(naver, '_blank');
                break;
		}
	}
</script>
</html>
