<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/fullcalendar/bootstrap.min.css">
    <link rel="stylesheet" href='/fullcalendar/select.min.css' />
    <link rel="stylesheet" href='/fullcalendar/bootstrap-datetimepicker.min.css' />
    <link rel="stylesheet" href="/fullcalendar/fullcalendar.min.css" />
    <link rel="stylesheet" href="/fullcalendar/main.css">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>LoveData Calender</title>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
    <div class="info-box">
        <div id="all_calendar" class="tabcontent">
            <div class="container">
                <!-- 일자 클릭시 메뉴오픈 -->
                <div id="contextMenu" class="dropdown clearfix">
                    <ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu"
                        style="display:block;position:static;margin-bottom:5px;">
                        <li><a tabindex="-1" href="#">add</a></li>
                        <li class="divider"></li>
                        <li><a tabindex="-1" href="#" data-role="close">Close</a></li>
                    </ul>
                </div>
                <div id="wrapper">
                    <div id="loading"></div>
                    <div id="calendar"></div>
                </div>

                <!-- 일정 추가 MODAL -->
                <div class="modal fade" tabindex="-1" role="dialog" id="eventModal">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title"></h4>
                            </div>
                            <div class="modal-body">

                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-allDay">하루종일</label>
                                        <input class='allDayNewEvent' id="edit-allDay" type="checkbox"></label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-title">일정명</label>
                                        <input class="inputModal" type="text" name="edit-title" id="edit-title"
                                               required="required" />

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-title">주소 <button type="button" onclick="goPopup()" value="주소검색">주소검색</button></label>
                                        <input type="hidden" placeholder="우편번호" id="zipNo" name="zipNo" readonly value="" required>
                                        <input type="text" class="roadModal" placeholder="도로명 주소" id="roadAddrPart1" name="roadAddrPart1" readonly
                                               required>
                                        <input type="text" class="roadModal" placeholder="상세 주소" id="addrDetail" name="addrDetail" readonly
                                               required>
                                        <input type="hidden" placeholder="시도명" id="siNm" name="siNm" readonly required>
                                        <input type="hidden" placeholder="시군구명" id="sggNm" name="sggNm" readonly required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-start">시작</label>
                                        <input class="inputModal" type="text" name="edit-start" id="edit-start" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-end">끝</label>
                                        <input class="inputModal" type="text" name="edit-end" id="edit-end" />
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-color">색상</label>
                                        <select class="inputModal" name="color" id="edit-color">
                                            <option value="#D25565" style="color:#D25565;">빨간색</option>
                                            <option value="#ffa94d" style="color:#ffa94d;">주황색</option>
                                            <option value="#74c0fc" style="color:#74c0fc;">파란색</option>
                                            <option value="#f06595" style="color:#f06595;">핑크색</option>
                                            <option value="#63e6be" style="color:#63e6be;">연두색</option>
                                            <option value="#a9e34b" style="color:#a9e34b;">초록색</option>
                                            <option value="#4d638c" style="color:#4d638c;">남색</option>
                                            <option value="#495057" style="color:#495057;">검정색</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-4" for="edit-desc">설명</label>
                                        <textarea rows="4" cols="50" class="inputModal" name="edit-desc"
                                                  id="edit-desc"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer modalBtnContainer-addEvent">
                                <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                                <button type="button" class="btn btn-primary" id="save-event">저장</button>
                            </div>
                            <div class="modal-footer modalBtnContainer-modifyEvent">
                                <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                                <button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
                                <button type="button" class="btn btn-primary" id="updateEvent">저장</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </div>
            <!-- /.container -->
            </div>
        </div>
    </div>
</body>
<script src="/fullcalendar/jquery.min.js"></script>
<script src="/fullcalendar/bootstrap.min.js"></script>
<script src="/fullcalendar/moment.min.js"></script>
<script src="/fullcalendar/select.min.js"></script>
<script src="/fullcalendar/bootstrap-datetimepicker.min.js"></script>
<script src="/fullcalendar/fullcalendar.min.js"></script>
<script src="/fullcalendar/ko.js"></script>
<script src="/fullcalendar/main.js"></script>


</html>
