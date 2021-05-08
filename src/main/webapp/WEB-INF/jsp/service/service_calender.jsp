<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <link href="/css/service/calender.css" rel="stylesheet">
    <link href='/fullcalendar/main.css' rel='stylesheet' />

    <title>LoveData Calender</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
    <div class="title">캘린더</div>
    <div class="contents">
    <div class="tab">
        <button class="tablinks" onclick="openCity(event, 'all_calendar')" id="defaultOpen">전체일정보기</button>
        <button class="add_calendar" onclick="openCity(event, 'add_calendar')">일정/기념일 추가</button>
        <button class="tablinks" onclick="openCity(event, 'Anniversary')">기념일 보기</button>
        <button class="tablinks" onclick="openCity(event, 'add_course')">코스 추가</button>
    </div>
    <div class="info-box">
        <div id="all_calendar" class="tabcontent">
            <div id='calendar'></div>
            </div>
        </div>

        <div id="add_calendar" class="tabcontent">
            <h3>일정/기념일 추가</h3>
        </div>

        <div id="Anniversary" class="tabcontent">
            <h3>기념일 보기</h3>
        </div>

        <div id="add_course" class="tabcontent">
            <h3>코스 추가</h3>
        </div>
    </div>
    </div>
</body>
<%@ include file="../layout/footer.jsp" %>
<script src="/js/calender.js"></script>
<script src='/fullcalendar/main.js'></script>
<script src='/fullcalendar/ko.js'></script>
</html>
