<%@ page import="org.springframework.security.core.annotation.AuthenticationPrincipal" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/mypage.css" rel="stylesheet">
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
<%--<form class="mypageform" action="/user/mypage" method="post">--%>
	<sec:authorize access="isAnonymous()">
		잘못된 방식으로 접근하였습니다. 로그인 후 다시 시도하여주세요!
		<button id="hd-btn" onclick="location.href='/login'">로그인</button>
		<button id="hd-btn" class="dropbtn" onclick="gohome()">홈으로 돌아가기</button>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
<div id="jb-container">
	<div id="jb-header">
		<h1>마이페이지</h1>
	</div>
	<div id="jb-sidebar">
		<div class="tab">
			<button class="tablinks" onclick="MenuTab(event, 'Myinfo')" id="defaultOpen">내 정보</button>
			<button class="tablinks" onclick="MenuTab(event, 'MyReview')">나의 리뷰</button>
			<button class="tablinks" onclick="MenuTab(event, 'MyPlace')">나의 코스/장소 <ul><li>나의코스</li><li>나의 장소</li></ul></button>
			<button class="tablinks" onclick="MenuTab(event, 'LikeList')">찜 목록</button>
			<button class="tablinks" onclick="MenuTab(event, 'RecView')">최근 본 코스</button>
		</div>
	</div>
	<div id="jb-content">
		<div id="Myinfo" class="tabcontent">
			<h3>내 정보</h3>
			<table>
				<tr>
					<th>회원정보 수정</th>
				</tr>
				<tr>
					<td>이름</td>
					<td id="sec_line">
						<span>${UserDTO.user_name}</span>
					</td>
				</tr>
				<tr>
					<td>프로필 사진</td>
					<td id="sec_line">
						<div class="file-wrapper flie-wrapper-area">
							<div class="float-left">
								<span class="label-plus"><i class="fas fa-plus"></i></span>
								<div id="preview"></div>
								<div class="file-edit-icon">
									<input type="file" name="file" id="file" class="upload-box upload-plus"
										   accept="image/*">
									<button id="img-edit" class="preview-edit">수정</button>
									<button id="img-del" class="preview-de">삭제</button>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>닉네임</td>
					<td id="sec_line"><input type="text" value="${UserDTO.user_nic}">

						<button type="button" onclick="double_check()" id="NickName" name="nic_check">중복 확인</button>
					</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td id="sec_line"><span>${UserDTO.user_email}</span></td>
				</tr>
				<tr>
					<td>기존 비밀번호 *</td>
					<td id="sec_line"><input type="password"  value=""></td>
				</tr>
				<tr>
					<td>새 비밀번호 *</td>
					<td id="sec_line"><input type="password" name="newPwd" id="pwd1" onKeyup="chkpw()" value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control"  required>
						<spen class="spen" id=pwd_rule>영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 10자~16자.</spen>
					</td>
				</tr>
				<tr>
					<td>새 비밀번호 확인 *</td>
					<td id="sec_line"><input name="npk" type="password" id="NewPasswordre"  onKeyup="repasswordcheck()">
						<spen class="spen" id="password_check"></spen>
					</td>
				</tr>
				<tr>
					<td>휴대폰 번호 *</td>
					<td id="sec_line">
						<input type="hidden" id="numnum" value="${UserDTO.user_phone}">
						<select name="first-phone-number" id="firnum" >
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="017">017</option>
						<option value="018">018</option>
					</select>
						-
						<input type="text" id="twonum"
							   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
							   maxlength="4"/>
						-
						<input type="text" id="lastnum"
							   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
							   maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td>생년월일 *</td>
					<td id="sec_line">
						<input type="hidden" id="mybir" value="${UserDTO.user_birth}">
						<input type="date" id="birthday" name="birthday"
							   value="<sec:authentication property="principal.user_birth"/>" min="1930-01-01" max="2050-12-31" required>
					</td>
				</tr>
				<tr>
					<td>성별</td>
					<td id="sec_line">
						<input type="hidden" id="jender" value="${UserDTO.user_sex}">
						<input type="radio" id="mann" name="chk_gender" value="" >남자</input>
						<input type="radio" id="womann" name="chk_gender" value="" >여자</input>
					</td>
				</tr>
			</table>
			<button type="button">저장</button>
		</div>
		<div id="MyReview" class="tabcontent">
			<h3>나의 리뷰</h3>
			<table>
				<thead>
				<tr>
					<th>등록 날짜</th>
					<th>제목</th>
					<th>조회수</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td><span>2022-01-01</span></td>
					<td><a href=""></a></td>
					<td>247</td>
				</tr>
				<tr>
					<td><span>2021-12-01</span></td>
					<td>인생 쌀국수 부산 분짜라붐</td>
					<td>365</td>
				</tr>
				</tbody>
			</table>
			<div class="pagination">
				<a href="#">&laquo;</a>
				<a href="#">1</a>
				<a href="#">&raquo;</a>
			</div>
		</div>

		<div id="MyPlace" class="tabcontent">
			<h3>나의 코스/장소</h3>
			<table>
				<thead>
				<tr>
					<th>등록 날짜</th>
					<th>제목</th>
					<th>조회수</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td><span>2020-10-05</span></td>
					<td>서울 삼청동 산책로</td>
					<td>192</td>
				</tr>
				<tr>
					<td><span>2021-02-01</span></td>
					<td>제주 반시계방향 3박4일</td>
					<td>365</td>
				</tr>
				<tr>
					<td>2021-03-15</td>
					<td>일산 밤리단길 카페투어</td>
					<td>247</td>
				</tr>
				<tr>
					<td>2020-05-07</td>
					<td>운정호수공원 야당역 맛집</td>
					<td>830</td>
				</tr>
				</tbody>
			</table>
			<div class="pagination">
				<a href="#">&laquo;</a>
				<a href="#">1</a>
				<a href="#">&raquo;</a>
			</div>
		</div>
		<div id="LikeList" class="tabcontent">
			<h3>찜 목록</h3>
			<table>
				<thead>
				<tr>
					<th>등록 날짜</th>
					<th>제목</th>
					<th>조회수</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td><span>2022-01-01</span></td>
					<td>인생 맛집 투어</td>
					<td>247</td>
				</tr>
				<tr>
					<td><span>2021-12-01</span></td>
					<td>서울근교 카페 데이트</td>
					<td>365</td>
				</tr>
				<tr>
					<td>2020-10-11</td>
					<td>서울역 서울로 걷기데이트</td>
					<td>117</td>
				</tr>
				</tbody>
			</table>
			<div class="pagination">
				<a href="#">&laquo;</a>
				<a href="#">1</a>
				<a href="#">&raquo;</a>
			</div>
		</div>
		<div id="RecView" class="tabcontent">
			<h3>최근 본 코스</h3>
			<table>
				<thead>
				<tr>
					<th>No.</th>
					<th>등록 날짜</th>
					<th>제목</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>1</td>
					<td><span>2022-01-01</span></td>
					<td>인생 맛집 투어</td>
				</tr>
				<tr>
					<td>2</td>
					<td><span>2021-12-01</span></td>
					<td>누가 그 집에 가보았는가</td>
				</tr>
				<tr>
					<td>3</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>4</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>5</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>6</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>7</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>8</td>
					<td></td>
					<td></td>
				</tr>
				</tbody>
			</table>
			* 최근 본 8개 장소만 표시됩니다.
		</div>
	</div>
<%--	<div id="nic_modal" class="nic_modal">--%>
<%--		<div class="nic_modal_content">--%>
<%--			<div>사용하고자 하는 닉네임을 입력하세요!</div>--%>
<%--			<input type="text" name="new_nic_check" id="nic_chk"--%>
<%--				   placeholder="">--%>
<%--			<button type="button" id="re_check" onclick="">중복확인</button>--%>
<%--			<div id="nic_new_check">닉네임을 입력하세요!</div>--%>
<%--			<button type="button" id="nic_modal_close_btn">닫기</button>--%>
<%--		</div>--%>
<%--	</div>--%>
</div>
</sec:authorize>
<%--</form>--%>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/mypage.js"></script>
</html>