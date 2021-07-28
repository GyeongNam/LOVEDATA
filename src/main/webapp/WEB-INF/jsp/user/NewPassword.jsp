<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <link href="/css/NewPassword.css" rel="stylesheet">
    <title>비밀번호 재설정</title>
</head>
<body>
<form class="Newpw" action="/passwordsave" method="post">
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
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
							<input type="password" name="newPwd" id="pwd1" onKeyup="chkpw()" value="<%= request.getAttribute("pwd")  == null ? "" :  request.getAttribute("pwd")%>" class="form-control"  required>
						</span>
                    <spen class="spen" id=pwd_rule>영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 10자~16자.</spen>

                    <h3 class="join_title"><label >비밀번호 재확인</label></h3>
                    <span class="ps_box box_right_space">
							<input name="npk" type="password" id="NewPasswordre"  onKeyup="repasswordcheck()">
						</span>
                    <spen class="spen" id="password_check"></spen>
                </div>
            </div>
            <input name="user_no" type="hidden" value="${User.user_no}">
            <!-- // 아이디, 비밀번호 입력 -->

            <div class="btn_area">
                <button type="submit" id="btnJoin" class="btn_type btn_primary"><span>비밀번호 변경</span></button>
            </div>

        </div>
    </div>
</div>
</div>
</form>
<!-- // container -->
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/NewPw.js"></script>
</html>