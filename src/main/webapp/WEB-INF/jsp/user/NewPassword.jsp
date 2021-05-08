<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/NewPassword.css" rel="stylesheet">
    <title>비밀번호 재설정</title>
</head>
<body>
<div id="warp">
<!-- header -->
<div id="header" class="join_membership" role="banner">
    <h1><a href="http://lovedata.kr/" class="h_logo"><span class="blind">LOVEDATA</span></a></h1>
</div>
<!-- // header -->
<!-- container -->
<div id="container" role="main">
    <div id="content">
        <!-- tg-text=title -->
        <div class="join_content">
            <!-- 비밀번호 입력 -->
            <div class="row_group">
                <div class="join_row">

                    <h3 class="join_title"><label>비밀번호</label></h3>
                    <span class="ps_box box_right_space">
							<input type="text" id="pw1" name="name" title="이름" class="int" maxlength="40">
						</span>
                    <span>영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 10~16자</span>

                    <h3 class="join_title"><label >비밀번호 재확인</label></h3>
                    <span class="ps_box box_right_space">
							<input type="text" id="pw2" name="name" title="이름" class="int" maxlength="40">
						</span>
                </div>
            </div>
            <!-- // 아이디, 비밀번호 입력 -->
            <div class="btn_area">
                <button type="button" id="btnJoin" class="btn_type btn_primary"><span>비밀번호 변경</span></button>
            </div>
        </div>
    </div>
</div>
</div>
<!-- // container -->
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/NewPw.js"></script>
</html>