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
    <link href="/css/Notice.css" rel="stylesheet">
    <title>Home</title>
</head>
<%@ include file="../layout/header.jsp"%>
<body>
<div id="jb-container">
    <div id="jb-header">
        <h1>고객센터</h1>
    </div>
    <div id="jb-sidebar">
        <div class="tab">
            <button class="tablinks" onclick="MenuTab(event, 'Notice')" id="defaultOpen">공지사항</button>
            <button class="tablinks" onclick="MenuTab(event, 'Questions')">문의사항</button>
            <button class="tablinks" onclick="MenuTab(event, 'Policy')">LOVE DATA 정책</button>
            <button class="tablinks" onclick="MenuTab(event, 'Withdrawal')">회원탈퇴</button>
        </div>
    </div>
    <div id="jb-content">
        <div id="Notice" class="tabcontent">
            <div class="table-head">
                <div class="Notice-head">
                    <h3>공지사항</h3>
                </div>
                <div class="Nsearch">
                    <select class="selitm">
                        <option value="" selected>제목+내용</option>
                        <option value="">조회수</option>
                        <option value="">등록일</option>
                    </select>
                    <input type="text" class="Notice-search"><button>검색</button>
                </div>
            </div>
            <table>
                <thead>
                <tr>
                    <th>번호</th><th>제목</th><th>등록일</th><th>첨부파일</th><th>조회수</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="noti" items="${nots}">
                <tr onclick=location.href='/Notice/Notice/${noti.noti_no}';>
                    <td>${noti.noti_no}</td>
                    <td>${noti.noti_title}</td>
                    <td><span>${noti.noti_date}</span></td>
                    <td>파일?</td>
                    <td>${noti.noti_viewCount}</td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#">1</a>
                <a href="#">&raquo;</a>
            </div>
        </div>
        <div id="Questions" class="tabcontent">
            <h3>문의 사항</h3>
            <table>
                <thead>
                <tr>
                    <th>번호</th><th>제목</th><th>작성자</th><th>접수일</th><th>상태</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="qu" items="${qu}">
                <tr onclick=location.href='/Notice/Questions/${qu.qu_no}';>
                    <td>${qu.qu_no}</td>
                    <c:choose>
                        <c:when test="${qu.qu_secret eq true}">
                            <td>${qu.qu_title} [비밀글]</td>
                        </c:when>
                        <c:when test="${qu.qu_secret eq false}">
                            <td>${qu.qu_title}</td>
                        </c:when>
                    </c:choose>
                    <td>${qu.qu_user}</td>
                    <td>${qu.qu_date}</td>
                    <c:choose>
                        <c:when test="${qu.qu_answer eq true}">
                            <td>답변완료</td>
                        </c:when>
                        <c:otherwise>
                            <td>답변중</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#">1</a>
                <a href="#">&raquo;</a>
            </div>
        </div>
        <div id="Policy" class="tabcontent">
            <h3>LOVE DATA 정책</h3>
            <div class="Ptab">
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy1')"id="FirstTabOpen">총직</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy2')">개인정보처리방침</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy3')">이용계약 당사자의 의무</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy4')">서비스 이용 및 이용제한</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy5')">청약철회, 과오납금의 환급 및 이용계약의 해지</button>
                <button class="Ptablinks" onclick="ServiceCenterTab(event, 'Policy6')">손해배상 및 면책조항</button>
            </div>
            <div id="Policy1" class="Ptabcontent">
                <h3>${Chapter1title}</h3>
                <p>${Article1}</p>

                <p>${Article2}</p>

                <p>${Article3}</p>

                <p>${Article4}</p>

                <p>${Article5}</p>

                <p>${Article6}</p>

                <p>${Article7}</p>
            </div>

            <div id="Policy2" class="Ptabcontent">
                <h3>${Chapter2title}</h3>
                <p>${Article8}</p>

            </div>

            <div id="Policy3" class="Ptabcontent">
                <h3>${Chapter3title}</h3>
                <p>${Article9}</p>

                <p>${Article10}</p>
            </div>
            <div id="Policy4" class="Ptabcontent">
                <h3>${Chapter4title}</h3>
                <p>${Article11}</p>

                <p>${Article12}</p>

                <p>${Article13}</p>

                <p>${Article14}</p>

                <p>${Article15}</p>

                <p>${Article16}</p>

                <p>${Article17}</p>

                <p>${Article18}</p>

                <p>${Article19}</p>

                <p>${Article20}</p>
            </div>
            <div id="Policy5" class="Ptabcontent">
                <h3>${Chapter5title}</h3>
                <p>${Article21}</p>

                <p>${Article22}</p>

                <p>${Article23}</p>

                <p>${Article24}</p>
            </div>
            <div id="Policy6" class="Ptabcontent">
                <h3>${Chapter6title}</h3>
                <p>${Article25}</p>

                <p>${Article26}</p>

                <p>${Article27}</p>

                <p>${Article28}</p>

                <p>${Article29}</p>
            </div>
        </div>
        <div id="Withdrawal" class="tabcontent">
            <h3>회원 탈퇴</h3>
        </div>
    </div>
</div>
</body>
<!--   부트스트랩 js 사용  -->
<script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script defer type="text/javascript" src="/resource/js/bootstrap.js"></script>
<script defer src="/js/notice.js"></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');

    body {
        font-family: 'Jua', sans-serif;
    }
</style>
</html>