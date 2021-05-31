<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>LoveData Calender</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
    <div class="title">캘린더</div>
    <div class="contents">
    <div class="tab">
        <button class="tablinks" onclick="openCity(event, 'all_calendar')" id="defaultOpen">전체일정보기</button>
        <button class="add_calendar" onclick="openCity(event, 'add_calendar')">일정 추가</button>
        <button class="tablinks" onclick="openCity(event, 'Anniversary')">기념일 보기</button>
    </div>
    <div class="info-box">
        <div id="all_calendar" class="tabcontent">
            <div id='calendar'></div>
            </div>
        </div>

        <div id="add_calendar" class="tabcontent">
            <h3>일정 추가</h3>
            <form>
            <table id="add-content">
                <tr>
                    <td>제목*</td>
                    <td><input type="text" name="cal_title" id="cal_title"></td>
                </tr>
                <tr>
                    <td>기념일</td>
                    <td>
                        <input class="checkbox" type="hidden" name="cal_sday_chek" value=false/>
                        <input type="checkbox" id="cal_sday" name="cal_sday" value=false/>
                    </td>
                </tr>
                <tr>
                    <td>주소</td>
                    <td>
                        <input type="hidden" placeholder="우편번호" id="zipNo" name="zipNo" readonly value="" required>
                        <input type="text" placeholder="도로명 주소" id="roadAddrPart1" name="roadAddrPart1" readonly
                               required>
                        <input type="text" placeholder="상세 주소" id="addrDetail" name="addrDetail" readonly
                               required>
                        <input type="hidden" placeholder="시도명" id="siNm" name="siNm" readonly required>
                        <input type="hidden" placeholder="시군구명" id="sggNm" name="sggNm" readonly required>
                        <button type="button" onclick="goPopup()" value="주소검색">주소검색</button>
                    </td>
                </tr>
                <tr>
                    <td>일시</td>
                    <td>
                        <input type="date" id="startDate">
                        <input type="time" id="startTime">
                        ~
                        <input type="date" id="endDate">
                        <input type="time" id="endTime">
                        <input class="checkbox" type="hidden" name="cal_time_chek" value=false/>
                        <input type="checkbox" id="cal_time" name="cal_time" value=false/>
                        종일
                    </td>
                </tr>
                <tr>
                    <td>설명</td>
                    <td>
                        <textarea type="text" id="body"></textarea>
                    </td>
                </tr>
            </table>
                <button type="submit">저장</button>
            </form>
        </div>

        <div id="Anniversary" class="tabcontent">
            <h3>기념일 보기</h3>
        </div>

    </div>
    </div>
</body>
<%@ include file="../layout/footer.jsp" %>
<link href="/css/service/calender.css" rel="stylesheet">
<link href='/fullcalendar/main.css' rel='stylesheet' />
<script src="/js/calender.js"></script>
<script src='/fullcalendar/main.js'></script>
<script src='/fullcalendar/ko.js'></script>
</html>
