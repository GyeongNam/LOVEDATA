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
	<sec:authorize access="isAnonymous()">
		잘못된 방식으로 접근하였습니다. 로그인 후 다시 시도하여주세요!
		<button id="hd-btn" onclick="location.href='/login'">로그인</button>
		<button id="hd-btn" class="dropbtn" onclick="gohome()">홈으로 돌아가기</button>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
<%--<form class="mypageform" action="/user/mypage" method="post">--%>
<div id="jb-container">
	<div id="jb-header">
		<h1>마이페이지</h1>
	</div>
	<div id="jb-sidebar">
		<div class="tab">
			<div>
				<p>
					<a href="/mypage" >내 정보</a>
				</p>
			</div>
			<div>
				<p>
					<a href="/mypage_myreview" >나의 리뷰</a>
				</p>
			</div>
			<div>
				<span>나의코스/장소</span>
				<div>
					<p>
						<a href="/mypage_mycorse" >나의 코스</a>
					</p>
				</div>
				<div>
					<p>
						<a href="/mypage_myplace" >나의 장소</a>
					</p>
				</div>
			</div>
			<div>
				<p>
					<a href="/mypage_mylike" >나의 찜 목록</a>
				</p>
			</div>
			<div>
				<p>
					<a href="/mypage_recent_view_corse" >최근 본 코스</a>
				</p>
			</div>
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
						<a href="/NewPassword/${UserDTO.user_pw}">비밀번호변경</a>
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