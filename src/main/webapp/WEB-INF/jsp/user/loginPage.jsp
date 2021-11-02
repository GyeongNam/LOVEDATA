<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta lang="kr">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">--%>
    <title>로그인 | LOVEDATA</title>
<%--    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">--%>
<%--    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>--%>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

        body {
            font-family: 'Jua', sans-serif;
        }
    </style>
</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="/login">
        <h2 class="form-signin-heading">로그인 해주세요</h2>
<%--                https://to-dy.tistory.com/81--%>
            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                <div class="visible" id="login_alert">
                    <div class="alert alert-danger" role="alert">이메일과 비밀번호를 확인해주세요</div>
                </div>
            </c:if>
        <div>
        </div>
        <p>
            <label for="username" class="sr-only">Email</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="이메일" required autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" required>
        </p>
        <div class="d-flex justify-content-between align-items-start">
            <div class="align-middle">
                <input type='checkbox' name='remember-me' onclick="alert('개인 PC에서만 사용해주세요')"/><span class="mx-2">자동 로그인</span>
            </div>
            <p><button type="btn" class="btn fs-6 btn-link btn-block p-0" onclick="location.href='/find_id'">회원 정보 찾기</button></p>
        </div>
        <sec:csrfInput />
        <div class="d-flex justify-content-between">
            <button class="btn btn-lg btn-primary btn-block" onclick="location.href='/signup'">회원가입</button>
            <button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
        </div>
	</form>
    <center>
        <form method="get" action="/login_kakao">
            <sec:csrfInput />
            <button type="submit" class="btn"><img class="img-fluid" src="/image/kakao_login/ko/kakao_login_large_narrow.png" width="366" height="90"></button><br>
        </form>
        <form method="get" action="/login_naver">
            <sec:csrfInput />
            <button type="submit" class="btn"><img class="img-fluid" src="/image/naver_login/ko/NAVER_Official_Green.PNG" width="366" height="90"></button><br>
        </form>
    </center>
</div>
</body></html>

<script defer src="/js/login.js"></script>
