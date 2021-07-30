<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>

<html>
<head>
    <link href="/css/home.css" rel="stylesheet">
    <link href="/css/fullpage.css" rel="stylesheet">
    <title>Home</title>
</head>

<%@ include file="layout/header.jsp" %>
<body>
<h1><img src="logo.png" alt=""></h1>
<nav>
    <ul>
        <li><a href="#sect1" class="on">clova Friends</a> </li>
        <li> <a href="#sect2">clova Friends mini</a></li>
        <li> <a href="#sect3">MINIONS edition</a></li>
        <li><a href="#sect4">Doraemon edition</a> </li>
    </ul>


</nav>
<div class="wrap">
    <section id="sect1">
        <article class="black">
            <p>원하는 프렌즈를 선택해보세요</p>
            <h2>Clova Friends</h2>
            <ul class="friends">
                <li><img src="img/article1-1.png" alt=""><img src="article1-2.png" alt=""></li>
                <li><img src="img/article2-1.png" alt=""><img src="article2-2.png" alt=""></li>
                <li><img src="img/article1-5.png" alt=""><img src="article1-6.png" alt=""></li>
                <li><img src="img/article1-7.png" alt=""></li>
        </article>
    </section>

    <section id="sect2">
        <article class="black">
            <p>원하는 명령어를 선택하고, 프렌즈 미니의 답볍을 들어보세요</p>
            <h2>ClovaFriends mini</h2>
            <img src="img/article2-1.png" alt=""><img src="img/article2-2.png" alt="">
        </article>
    </section>
    <section id="sect3">
        <article class="white">
            <p>원하는 명령어를 선택하고, 미니언즈의 답변을 들어보세요.</p>
            <h2>MINIONS edition</h2>
            <img src="img/article3-1.png" alt="">
        </article>
    </section>
    <section id="sect4">
        <article class="white">
            <p>원하는 명령어를 선택하고, 도라에몽의 답변을 들어보세요.</p>
            <h2>Doraemon edition</h2>
            <img src="img/article4-1.png" alt="">
        </article>
    </section>

</div>

<h3>0</h3>
</body>
<%--<%@ include file="layout/footer.jsp" %>--%>
<!--  부트스트랩 js 사용 -->
<script defer src="/js/main-fullpage.js"></script>
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
</html>
