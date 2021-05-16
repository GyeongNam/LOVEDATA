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
<div id="jb-container">
    <div id="jb-header">
        <h1>마이페이지</h1>
    </div>
    <div id="jb-sidebar">
        <div class="tab">
            <button class="tablinks" onclick="MenuTab(event, 'Myinfo')" id="defaultOpen">내 정보</button>
            <button class="tablinks" onclick="MenuTab(event, 'MyReview')">나의 리뷰</button>
            <button class="tablinks" onclick="MenuTab(event, 'MyPlace')">나의 코스/장소</button>
            <button class="tablinks" onclick="MenuTab(event, 'LikeList')">찜 목록</button>
            <button class="tablinks" onclick="MenuTab(event, 'RecView')">최근 본 코스</button>
        </div>
    </div>
    <div id="jb-content">
        <div id="Myinfo" class="tabcontent">
            <h3>내 정보</h3>
            <table>
                <tr><th>회원정보 수정</th></tr>
                <tr>
                    <td>이름</td>
                    <td><span>최도비</span></td>
                </tr>
                <tr>
                    <td>프로필 사진</td>
                    <td>
                        <div class="file-wrapper flie-wrapper-area">
                        <div class="float-left">
                            <span class="label-plus"><i class="fas fa-plus"></i></span>
                            <div id="preview"></div>
                            <div class="file-edit-icon">
                                <input type="file" name="file" id="file" class="upload-box upload-plus" accept="image/*">
                                <button id="img-edit" class="preview-edit">수정</button>
                                <button id="img-del" class="preview-de">삭제</button>
                            </div>
                        </div>
                    </div>
                    </td>
                </tr>
                <tr>
                    <td>닉네임</td>
                    <td><input type="text"><button id="NickName">중복 확인</button></td>
                </tr>
                <tr>
                    <td>이메일</td>
                    <td><span>272518@naver.com</span></td>
                </tr>
                <tr>
                    <td>기존 비밀번호 *</td>
                    <td><input type="password"></td>
                </tr>
                <tr>
                    <td>새 비밀번호 *</td>
                    <td><input type="password"></td>
                </tr>
                <tr>
                    <td>새 비밀번호 확인 *</td>
                    <td><input type="password"></td>
                </tr>
                <tr>
                    <td>휴대폰 번호 *</td>
                    <td><select name="first-phone-number">
                        <option value="010">010</option>
                        <option value="011">011</option>
                        <option value="017">017</option>
                        <option value="018">018</option>
                    </select>
                        -
                        <input type="text"
                               oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" maxlength="4" />
                        -
                        <input type="text"
                               oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"  maxlength="4" />
                    </td>
                </tr>
                <tr><td>생년월일 *</td>
                    <td>
                        <select name="year" id="year" title="년도" class="custom-select"></select>
                        <select name="year" id="month" title="월" class="custom-select"></select>
                        <select name="year" id="day" title="일" class="custom-select"></select>
                    </td>
                </tr>
                <tr>
                    <td>성별</td>
                    <td>
                        <input type="radio" name="chk_gender" value="남자">남자
                        <input type="radio" name="chk_gender" value="여자">여자
                    </td>
                </tr>
            </table>
            <button type="button">저장</button>
        </div>

        <div id="MyReview" class="tabcontent">
            <h3>나의 리뷰</h3>
            <p>내가 쓴 리뷰를 보여주는곳이라고 리발래꺄</p>
        </div>

        <div id="MyPlace" class="tabcontent">
            <h3>나의 코스/장소</h3>
            <p>Tokyo 는 3년 안에 수장된다.</p>
        </div>
        <div id="LikeList" class="tabcontent">
            <h3>찜 목록</h3>
            <p>계란찜, 고구마찜, 감자찜, 기분이찜찜</p>
        </div>
        <div id="RecView" class="tabcontent">
            <h3>최근 본 코스</h3>
            <p>최근 본 코스들 나오는 곳 리발</p>
        </div>
    </div>
</div>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/mypage.js"></script>
</html>