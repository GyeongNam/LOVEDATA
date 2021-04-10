<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta lang="kr">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">--%>
    <meta name="description" content="">
    <meta name="author" content="">
    <title>로그인 | LOVEDATA</title>
<%--    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">--%>
<%--    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>--%>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="/login">
        <h2 class="form-signin-heading">로그인 해주세요</h2>
        <p>
            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>
        <p><input type='checkbox' name='remember-me'/> Remember me on this computer.</p>
        <sec:csrfInput />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
    <center>
        <form method="get" action="/login_kakao">
            <sec:csrfInput />
            <button type="submit" class="btn"><img class="img-fluid" src="/image/kakao_login/ko/kakao_login_large_narrow.png" width="366" height="90"></button><br>
        </form>
        <form method="get" action="alert('naver Login')">
            <sec:csrfInput />
            <button type="submit" class="btn"><img class="img-fluid" src="/image/naver_login/ko/NAVER_Official_Green.PNG" width="366" height="90"></button><br>
        </form>
    </center>
</div>
</body></html>

<script src="/js/login.js"></script>